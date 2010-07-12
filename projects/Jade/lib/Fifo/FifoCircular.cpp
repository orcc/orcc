/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

/**
@brief Implementation of class FifoCircular
@author Jerome Gorin
@file FifoCircular.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <sstream>
#include <string>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/Support/CommandLine.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Port.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Fifo/FifoCircular.h"
#include "Jade/Network/Connection.h"
//------------------------------

using namespace llvm;
using namespace std;


extern cl::opt<string> ToolsDir;

FifoCircular::FifoCircular(llvm::LLVMContext& C, JIT* jit): Context(C), AbstractFifo(jit)
{
	//Initialize map
	createFifoMap();
	createStructMap();

	//Declare header
	declareFifoHeader();
	
	// Initialize fifo counter
	fifoCnt = 0;
}

FifoCircular::FifoCircular(llvm::LLVMContext& C): Context(C), AbstractFifo(NULL)
{
	// Initialize fifo counter 
	fifoCnt = 0;

	//Parse bitcode
	declareFifoHeader();
}

FifoCircular::~FifoCircular (){

}

void FifoCircular::declareFifoHeader (){
	parseHeader();
	parseFifoStructs();
	parseFifoFunctions();
}

void FifoCircular::parseHeader (){
	header = jit->LoadBitcode("FifoCircular", ToolsDir);

	if (header == NULL){
		fprintf(stderr,"Unable to parse fifo header file");
		exit(0);
	}
}

void FifoCircular::parseFifoFunctions(){
	
	// Iterate though functions of header 
	for (Module::iterator I = header->begin(), E = header->end(); I != E; ++I) {
		string name = I->getName();
		
		if (isFifoFunction(name)){
			setFifoFunction(name, I);
			continue;
		}

		otherFunctions.push_back(I);
	}
}

void FifoCircular::parseFifoStructs(){
	map<string,string>::iterator it;
	
	// Iterate though structure
	for (it = structName.begin(); it != structName.end(); ++it) {
		string name = it->second;

		Type* type = (Type*)header->getTypeByName(name);

		if (type == NULL){
			fprintf(stderr,"Error when parsing fifo, structure %s has not beend found", name.c_str());
			exit(0);
		}

		setFifoStruct(name, type);
		
	}
}

void FifoCircular::addFunctions(Decoder* decoder){
	
	std::list<llvm::Function*>::iterator itList;

	for(itList = otherFunctions.begin(); itList != otherFunctions.end(); ++itList){
		Function* function = (Function*)jit->addFunctionProtos("", *itList);
		jit->LinkProcedureBody(*itList);
		*itList = function;
	}

	std::map<std::string,llvm::Function*>::iterator itMap;

	for(itMap = fifoAccess.begin(); itMap != fifoAccess.end(); ++itMap){
		Function* function = (Function*)jit->addFunctionProtos("", (*itMap).second);
		jit->LinkProcedureBody((*itMap).second);
		(*itMap).second = function;
	}
}

void FifoCircular::setConnection(Connection* connection){
	Module* module = jit->getModule();
	
	// fifo name 
	ostringstream arrayName;
	ostringstream fifoName;

	arrayName << "array_" << fifoCnt;
	fifoName << "fifo_" << fifoCnt;

	// Get vertex of the connection
	Port* src = connection->getSourcePort();
	Port* dst = connection->getDestinationPort();
	GlobalVariable* srcVar = src->getGlobalVariable();
	GlobalVariable* dstVar = dst->getGlobalVariable();

	//Get fifo structure
	StructType* structType = getFifoType(connection->getIntegerType());

	// Initialize array 
	PATypeHolder EltTy(connection->getIntegerType());
	const ArrayType* arrayType = ArrayType::get(EltTy, connection->getFifoSize()+1);
	Constant* arrayContent = ConstantArray::get(arrayType, NULL,0);
	GlobalVariable *NewArray =
        new GlobalVariable(*module, arrayType,
		true, GlobalVariable::InternalLinkage, arrayContent, arrayName.str());
	
	// Initialize fifo elements
	Constant* size = ConstantInt::get(Type::getInt32Ty(Context), connection->getFifoSize());
	Constant* read_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* write_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* fill_count = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* expr = ConstantExpr::getBitCast(NewArray, structType->getElementType(1));
	Constant* arrConst = ConstantAggregateZero::get(structType->getElementType(5));
	
	// Add initialization vector 
	vector<Constant*> Elts;
	Elts.push_back(size);
	Elts.push_back(expr);
	Elts.push_back(read_ind);
	Elts.push_back(write_ind);
	Elts.push_back(fill_count);
	Elts.push_back(arrConst);
	Constant* fifoStruct =  ConstantStruct::get(structType, Elts);

	// Create fifo 
	GlobalVariable *NewFifo =
        new GlobalVariable(*module, structType,
		true, GlobalVariable::InternalLinkage, fifoStruct, fifoName.str());

	// Set initialize to instance port 
	srcVar->setInitializer(NewFifo);
	dstVar->setInitializer(NewFifo);

	// Increment fifo counter 
	fifoCnt++;
	
}

StructType* FifoCircular::getFifoType(IntegerType* type){
	map<string,Type*>::iterator it;

	// Struct name 
	ostringstream structName;
	structName << "struct.fifo_i" << type->getBitWidth() << "_s";

	it = structAcces.find(structName.str());
		
	return cast<StructType>(it->second);
}
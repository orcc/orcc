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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <sstream>
#include <string>
#include <iostream>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/Support/CommandLine.h"

#include "Jade/Decoder.h"
#include "Jade/Core/Port.h"
#include "Jade/Fifo/FifoCircular.h"
#include "Jade/Core/Connection.h"
#include "Jade/Jit/LLVMParser.h"
#include "Jade/Jit/LLVMWriter.h"
//------------------------------

using namespace llvm;
using namespace std;


extern cl::opt<string> ToolsDir;

FifoCircular::FifoCircular(llvm::LLVMContext& C): Context(C), AbstractFifo()
{
	//Initialize map
	createFifoMap();
	createStructMap();

	//Declare header
	declareFifoHeader();
	
	// Initialize fifo counter
	fifoCnt = 0;
}

FifoCircular::~FifoCircular (){

}

void FifoCircular::declareFifoHeader (){
	parseHeader();
	parseFifoStructs();
	parseExternFunctions();
	parseFifoFunctions();
}

void FifoCircular::parseHeader (){
	//Create the parser
	LLVMParser parser(Context, ToolsDir);

	header = parser.loadBitcode("System", "FifoCircular");

	if (header == NULL){
		cerr << "Unable to parse fifo header file";
		exit(0);
	}

	externMod = parser.loadBitcode("System", "Extern");

	if (externMod == NULL){
		cerr << "Unable to parse extern functions file";
		exit(0);
	}
}

void FifoCircular::parseExternFunctions(){
	
	// Iterate though functions of extern module 
	for (Module::iterator I = externMod->begin(), E = externMod->end(); I != E; ++I) {
		externFunct.insert(pair<std::string,llvm::Function*>(I->getName(), I));
	}
}

void FifoCircular::parseFifoFunctions(){
	
	// Iterate though functions of header 
	for (Module::iterator I = header->begin(), E = header->end(); I != E; ++I) {
		string name = I->getName();
		
		if (isFifoFunction(name)){
			setFifoFunction(name, I);
		}
	}
}

void FifoCircular::parseFifoStructs(){
	map<string,string>::iterator it;
	
	// Iterate though structure
	for (it = structName.begin(); it != structName.end(); ++it) {
		string name = it->second;

		Type* type = (Type*)header->getTypeByName(name);

		if (type == NULL){
			cerr << "Error when parsing fifo, structure "<< name << " has not beend found";
			exit(0);
		}

		setFifoStruct(name, type);
		
	}
}

void FifoCircular::addFunctions(Decoder* decoder){
	LLVMWriter writer("", decoder);

	std::map<std::string,llvm::Function*>::iterator itMap;

	for(itMap = externFunct.begin(); itMap != externFunct.end(); ++itMap){
		Function* function = writer.addFunctionProtosExternal((*itMap).second);
		(*itMap).second = function;
	}

	for(itMap = fifoAccess.begin(); itMap != fifoAccess.end(); ++itMap){
		Function* function = writer.addFunctionProtosInternal((*itMap).second);
		writer.linkProcedureBody((*itMap).second);
		(*itMap).second = function;
	}
}

void FifoCircular::setConnection(Connection* connection, Decoder* decoder){
	Module* module = decoder->getModule();
	
	// fifo name 
	ostringstream arrayName;
	ostringstream bufName;
	ostringstream fifoName;

	arrayName << "array_" << fifoCnt;
	bufName << "buffer_" << fifoCnt;
	fifoName << "fifo_" << fifoCnt;

	// Get vertex of the connection
	Port* src = connection->getSourcePort();
	Port* dst = connection->getDestinationPort();
	GlobalVariable* srcVar = src->getGlobalVariable();
	GlobalVariable* dstVar = dst->getGlobalVariable();
	IntegerType* connectionType = cast<IntegerType>(src->getType());

	//Get fifo structure
	StructType* structType = getFifoType(connectionType);

	//Get fifo array structure
	PATypeHolder EltTy(connectionType);
	const ArrayType* arrayType = ArrayType::get(EltTy, connection->getSize());

	// Initialize array for content
	Constant* arrayContent = ConstantArray::get(arrayType, NULL,0);
	GlobalVariable *NewArrayContents =
        new GlobalVariable(*module, arrayType,
		false, GlobalVariable::InternalLinkage, arrayContent, arrayName.str());
	NewArrayContents->setAlignment(32);
	
	// Initialize array for fifo buffer
	Constant* arrayFifoBuffer = ConstantArray::get(arrayType, NULL,0);
	GlobalVariable *NewArrayFifoBuffer =
        new GlobalVariable(*module, arrayType,
		false, GlobalVariable::InternalLinkage, arrayFifoBuffer, bufName.str());
	NewArrayFifoBuffer->setAlignment(32);

	// Initialize fifo elements
	Constant* size = ConstantInt::get(Type::getInt32Ty(Context), connection->getSize());
	Constant* read_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* write_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* fill_count = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* contents = ConstantExpr::getBitCast(NewArrayContents, structType->getElementType(1));
	Constant* fifo_buffer = ConstantExpr::getBitCast(NewArrayFifoBuffer, structType->getElementType(2));
	
	// Add initialization vector 
	vector<Constant*> Elts;
	Elts.push_back(size);
	Elts.push_back(contents);
	Elts.push_back(fifo_buffer);
	Elts.push_back(read_ind);
	Elts.push_back(write_ind);
	Elts.push_back(fill_count);
	Constant* fifoStruct =  ConstantStruct::get(structType, Elts);

	// Create fifo 
	GlobalVariable *NewFifo =
        new GlobalVariable(*module, structType,
		false, GlobalVariable::InternalLinkage, fifoStruct, fifoName.str());
	NewFifo->setAlignment(32);
	
	// Set initialize to instance port 
	srcVar->setInitializer(NewFifo);
	dstVar->setInitializer(NewFifo);

	//Store fifo variable in connection
	connection->setFifo(NewFifo);

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

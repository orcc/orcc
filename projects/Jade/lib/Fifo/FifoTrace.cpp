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
@brief Implementation of class FifoTrace
@author Jerome Gorin
@file FifoTrace.cpp
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
#include "llvm/ADT/STLExtras.h"
#include "llvm/Support/CommandLine.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Port.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Decoder/Procedure.h"
#include "Jade/Fifo/FifoTrace.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Network/Network.h"
#include "Jade/Network/Connection.h"
#include "Jade/Network/Vertex.h"

//------------------------------

using namespace llvm;
using namespace std;


extern cl::opt<string> ToolsDir;
extern cl::opt<std::string> OutputDir;

FifoTrace::FifoTrace(llvm::LLVMContext& C, JIT* jit): Context(C), AbstractFifo(jit)
{
	//Initialize map
	createFifoMap();
	createStructMap();

	//Declare header
	declareFifoHeader();
	
	// Initialize fifo counter
	fifoCnt = 0;
}

FifoTrace::FifoTrace(llvm::LLVMContext& C): Context(C), AbstractFifo(NULL)
{
	// Initialize fifo counter 
	fifoCnt = 0;

	//Parse bitcode
	declareFifoHeader();
}

FifoTrace::~FifoTrace (){

}

void FifoTrace::declareFifoHeader (){
	parseHeader();
	parseFifoStructs();
	parseExternFunctions();
	parseFifoFunctions();
	parseFifoVars();
}

void FifoTrace::parseFifoVars (){
	//Get str var from fifo trace
	GlobalVariable* str = header->getGlobalVariable(".str", true);
	GlobalVariable* str1 = header->getGlobalVariable(".str1", true);

	// Store into externVar
	externVar.insert(pair<string,GlobalVariable*>("str", str));
	externVar.insert(pair<string,GlobalVariable*>("str1", str1));
}

void FifoTrace::parseHeader (){
	header = jit->LoadBitcode("FifoTrace", ToolsDir);

	if (header == NULL){
		fprintf(stderr,"Unable to parse fifo header file");
		exit(0);
	}

	externMod = jit->LoadBitcode("Extern", ToolsDir);

	if (externMod == NULL){
		fprintf(stderr,"Unable to parse extern functions file");
		exit(0);
	}
}

void FifoTrace::parseFifoFunctions(){
	
	// Iterate though functions of header 
	for (Module::iterator I = header->begin(), E = header->end(); I != E; ++I) {
		string name = I->getName();
		
		if (isFifoFunction(name)){
			setFifoFunction(name, I);
		}
	}
}

void FifoTrace::parseFifoStructs(){
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

void FifoTrace::parseExternFunctions(){
	
	// Iterate though functions of extern module 
	for (Module::iterator I = externMod->begin(), E = externMod->end(); I != E; ++I) {
		externFunct.insert(pair<std::string,llvm::Function*>(I->getName(), I));
	}
}


void FifoTrace::addFunctions(Decoder* decoder){
	
	std::map<std::string,llvm::Function*>::iterator itMap;

	//Add external function
	for(itMap = externFunct.begin(); itMap != externFunct.end(); ++itMap){
		Function* function = (Function*)jit->addFunctionProtosExternal("", (*itMap).second);
		(*itMap).second = function;
	}

	//Add fifo function
	for(itMap = fifoAccess.begin(); itMap != fifoAccess.end(); ++itMap){
		Function* function = (Function*)jit->addFunctionProtosInternal("", (*itMap).second);
		jit->LinkProcedureBody((*itMap).second);
		(*itMap).second = function;
	}

	//Add fifo vars
	map<string,GlobalVariable*>::iterator itVar;
	for(itVar = externVar.begin(); itVar != externVar.end(); ++itVar){
		GlobalVariable* var = jit->addVariable("", itVar->second);
		jit->LinkGlobalInits(itVar->second);
		(*itVar).second = var;
	}
}

void FifoTrace::setConnection(Connection* connection){
	Module* module = jit->getModule();
	
	// fifo name 
	ostringstream arrayName;
	ostringstream fifoName;

	arrayName << "array_" << fifoCnt;
	fifoName << "fifo_" << fifoCnt;

	// Get vertex of the connection
	Port* src = connection->getSourcePort();
	Port* dst = connection->getDestinationPort();
	Vertex* srcInstance = (Vertex*)connection->getSource();
	Vertex* dstInstance = (Vertex*)connection->getSink();
	GlobalVariable* srcVar = src->getGlobalVariable();
	GlobalVariable* dstVar = dst->getGlobalVariable();

	//Get fifo structure
	StructType* structType = getFifoType(connection->getIntegerType());

	// Initialize array 
	PATypeHolder EltTy(connection->getIntegerType());
	const ArrayType* arrayType = ArrayType::get(EltTy, connection->getFifoSize());
	Constant* arrayContent = ConstantArray::get(arrayType, NULL,0);
	GlobalVariable *NewArray =
        new GlobalVariable(*module, arrayType,
		false, GlobalVariable::InternalLinkage, arrayContent, arrayName.str());
	
	// Initialize fifo elements
	Constant* size = ConstantInt::get(Type::getInt32Ty(Context), connection->getFifoSize());
	Constant* read_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* write_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* fill_count = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* expr = ConstantExpr::getBitCast(NewArray, structType->getElementType(1));
	
	Constant* file =  ConstantPointerNull::get(cast<PointerType>(structType->getElementType(3)));
	Constant* arrConst = ConstantPointerNull::get(cast<PointerType>(structType->getElementType(2)));
	
	// Add initialization vector 
	vector<Constant*> Elts;
	Elts.push_back(size);
	Elts.push_back(expr);
	Elts.push_back(arrConst);
	Elts.push_back(file);
	Elts.push_back(read_ind);
	Elts.push_back(write_ind);
	Elts.push_back(fill_count);
	Constant* fifoStruct =  ConstantStruct::get(structType, Elts);

	// Create fifo 
	GlobalVariable *NewFifo =
        new GlobalVariable(*module, structType,
		false, GlobalVariable::InternalLinkage, fifoStruct, fifoName.str());


	// Set initialize to instance port 
	srcVar->setInitializer(NewFifo);
	dstVar->setInitializer(NewFifo);

	//Store fifo variable in connection
	connection->setFifo(NewFifo);

	// Increment fifo counter 
	fifoCnt++;
	
}

StructType* FifoTrace::getFifoType(IntegerType* type){
	map<string,Type*>::iterator it;

	// Struct name 
	ostringstream structName;
	structName << "struct.fifo_i" << type->getBitWidth() << "_s";

	it = structAcces.find(structName.str());
		
	return cast<StructType>(it->second);
}

void FifoTrace::setConnections(Decoder* decoder){
	
	Network* network = decoder->getNetwork();
	HDAGGraph* graph = network->getGraph();
	
	int edges = graph->getNbEdges();
	
	//Setting connections
	for (int i = 0; i < edges; i++){
		setConnection((Connection*)graph->getEdge(i));
	}

	setFiles(decoder);
}


void FifoTrace::setFile(Decoder* decoder, Connection* connection, BasicBlock* bb, Function* fOpenFunc, Constant* wStr){
	ostringstream fileName;
	ostringstream fileVar;
	string strFile;
	string strVar;

	//Get module of the decoder
	Module* module = decoder->getModule();

	// Get vertex of the connection
	Port* dst = connection->getDestinationPort();
	Vertex* dstInstance = (Vertex*)connection->getSink();

	//Creating file string
	fileName << OutputDir << dstInstance->getName() <<"_" <<dst->getName() << ".txt";
	fileVar << dstInstance->getName() << "_" << dst->getName() << "_file";
	strFile = fileName.str();
	strVar = fileVar.str();
	
	//Insert trace file string
	ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context), strFile.size()+1); 
	GlobalVariable *GV = new llvm::GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context, strFile), strVar.c_str(), 0, false, 0);

	//Parameter of file of fopen
	Constant *zero_32 = Constant::getNullValue(IntegerType::getInt32Ty(Context));
    Constant *zero_array[] = {
        zero_32,
        zero_32
      };

	Constant *fileEltPtr = ConstantExpr::getGetElementPtr(GV, zero_array,
                         array_lengthof(zero_array));

	Constant *wEltPtr = ConstantExpr::getGetElementPtr(wStr, zero_array,
                         array_lengthof(zero_array));

	//Parameter "w" of fopen
	Value *params[] = {
        fileEltPtr,
		wEltPtr
      };


	//Create fopen instr
	CallInst* fopenInstr = CallInst::Create(fOpenFunc, params, array_endof(params), "", bb);

	//Get file from fifo structure
	Constant *three_32 = Constant::getIntegerValue(IntegerType::getInt32Ty(Context), APInt(32, 3));
    Value *three_array[] = {
        zero_32,
        three_32
      };

	Constant *fileElt = ConstantExpr::getGetElementPtr(connection->getFifo(), three_array,
                         array_lengthof(three_array));

	new StoreInst(fopenInstr, fileElt, bb);
}


void FifoTrace::setFiles(Decoder* decoder){
	
	// Create initialization procedure
	Module* module = decoder->getModule();
	string initName = "assignTrace";
	ConstantInt* isExtern = ConstantInt::get(IntegerType::get(Context,1),0);
	
	Function* init = cast<Function>(module->getOrInsertFunction(initName, Type::getVoidTy(Context),
                                          (Type *)0));
	Procedure* initialize = new Procedure(initName, isExtern, init);
	decoder->setInitialization(initialize);

	// Create entry basic block
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", init);

	// Set opening Files
	Network* network = decoder->getNetwork();
	HDAGGraph* graph = network->getGraph();
	int edges = graph->getNbEdges();

	// Get fopen function
	map<string,Function*>::iterator it;
	it = externFunct.find("fopen");

	//Set a variable containing "w" string for fopen 
	string wStr = "w";
	ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context), wStr.size()+1); 
	GlobalVariable *wGV = new llvm::GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context, wStr), wStr.c_str(), 0, false, 0);

	//Associate connections to a file
	for (int i = 0; i < edges; i++){
		setFile(decoder, (Connection*)graph->getEdge(i), BBEntry, it->second, wGV);
	}

	// Create the return instruction and add it to the entry block.
	ReturnInst::Create(Context, BBEntry);
}
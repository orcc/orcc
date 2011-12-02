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
@brief Implementation of class FifoMng
@author Jerome Gorin
@file FifoMng.cpp
@version 1.0
@date 17/12/2010
*/

//------------------------------
#include <iostream>
#include <sstream>

#include "llvm/LLVMContext.h"
#include "llvm/Linker.h"

#include "Jade/Decoder.h"
#include "Jade/Core/Actor.h"
#include "Jade/Fifo/FifoCircular.h"
#include "Jade/Fifo/FifoOpt.h"
#include "Jade/Fifo/FifoTrace.h"
#include "Jade/Fifo/FifoUnprotected.h"
#include "Jade/Jit/LLVMParser.h"
#include "Jade/Jit/LLVMWriter.h"
#include "Jade/Util/FifoMng.h"
#include "Jade/Util/PackageMng.h"
//------------------------------

using namespace std;
using namespace llvm;

//Initializing static elements
std::string FifoMng::fifoFiles[] = { "FifoCircular", "FifoTrace", "FifoUnprotected"};
FifoTy FifoMng::fifoTy;
string FifoMng::externFnFile;
string FifoMng::packageFolder;
string FifoMng::outputDir;
Module* FifoMng::headerMd;
map<int, FifoMng::FifoAccess*> FifoMng::fifoAccesses;
map<string, const StructType*> FifoMng::fifoStructs;
int FifoMng::defaultFifoSize = 10000;


void FifoMng::setFifoTy(FifoTy fifoTy, std::string packageFld, int defaultFifoSize, std::string outputDir){
		FifoMng::fifoTy =  fifoTy;
		FifoMng::packageFolder = packageFld;
		FifoMng::outputDir = outputDir;

		parseModules();
		parseFifos();
		FifoMng::defaultFifoSize = defaultFifoSize;
}

int FifoMng::getDefaultFifoSize(){
		return defaultFifoSize;
}

void FifoMng::parseModules(){
	LLVMContext &Context = getGlobalContext();
	
	//Create the parser
	LLVMParser parser(Context, getPackageFolder());

	//Parse fifos
	Package* packageSystem = PackageMng::getPackage("System");
	headerMd = parser.loadModule(packageSystem, FifoMng::getFifoFilename());

	if (headerMd == NULL){
		cout << "Unable to parse fifo header file";
		exit(0);
	}
}
void FifoMng::parseFifos(){
	NamedMDNode* fifoNMD = headerMd->getNamedMetadata("fifo");

	// Parse all MD elements of fifo MD
	for (unsigned i = 0, e = fifoNMD->getNumOperands(); i != e; ++i) {
		FifoMng::FifoAccess* access = parseFifo(fifoNMD->getOperand(i));
		fifoAccesses.insert(pair<int, FifoMng::FifoAccess*>(access->getTokenSize(), access));
	}
}

FifoMng::FifoAccess* FifoMng::parseFifo(MDNode* node){
	//Get token size
	ConstantInt* tokenSizeCI = cast<ConstantInt>(node->getOperand(0));
	int tokenSize = tokenSizeCI->getLimitedValue();
	
	//Get fifo information
	pair<string, StructType*> structType = parseFifoStruct(cast<MDNode>(node->getOperand(1)));
	map<string, Function*>* signedFn = parseFifoFn(cast<MDNode>(node->getOperand(2)));
	map<string, Function*>* unsignedFn = parseFifoFn(cast<MDNode>(node->getOperand(3)));

	return new FifoAccess(tokenSize, structType.first, structType.second, signedFn, unsignedFn);
}

map<string, Function*>* FifoMng::parseFifoFn(MDNode* functionMD){
	map<string, Function*>* fifoFns = new map<string, Function*>();

	for (unsigned i = 0, e = functionMD->getNumOperands(); i != e; ++i) {
		Function* function = cast<Function>(functionMD->getOperand(i));
		
		//Store function
		fifoFns->insert(pair<string, Function*>(function->getNameStr(), function));
	}

	return fifoFns;
}

pair<string, StructType*> FifoMng::parseFifoStruct(llvm::MDNode* structMD){
	MDString* name = cast<MDString>(structMD->getOperand(0));

	ConstantPointerNull* pointer = cast<ConstantPointerNull>(structMD->getOperand(1));
	const Type* type = pointer->getType();
	StructType* structType = (StructType*)cast<StructType>(type->getContainedType(0));

	fifoStructs.insert(pair<string, StructType*>(name->getString(), structType));
	return pair<string, StructType*>(name->getString(), structType);
}

AbstractFifo* FifoMng::getFifo(LLVMContext& C, Decoder* decoder, Type* type, Connection* connection){
	int size = connection->getSize();
	/*
	char* name = connection->getSource()->getName();
	if (strcmp (name,"source") == 0){
		return new FifoOpt(C, decoder->getModule(), type, size);
	}*/

	//Todo : implement static class in fifo for creation
	if (fifoTy == trace){
		return new FifoTrace(C, connection, decoder->getModule(), decoder->getConfiguration()->getNetwork(), type, size, outputDir);
	}else if (fifoTy == circular){
		return new FifoCircular(C, decoder->getModule(), type, size);
	}else if (fifoTy == unprotected){
		//return new FifoUnprotected(C, decoder->getModule(), type, size);
		return NULL;
	}else{
		return NULL;
	}
}

StructType* FifoMng::getFifoType(IntegerType* type){
	//Return corresponding structure
	return getFifoAccess(type)->getStructType();
}

std::map<std::string, llvm::Function*>* FifoMng::addFifoHeader(Decoder* decoder){
	addFifoType(decoder);
	return addFifoFunctions(decoder);
}

void FifoMng::addFifoType(Decoder* decoder){
	LLVMWriter writer("", decoder);
	
	//Get fifos
	map<string,const StructType*>::iterator it;

	for (it = fifoStructs.begin(); it != fifoStructs.end(); ++it){
		writer.addType(it->first, it->second);
	}
}

map<string, Function*>* FifoMng::addFifoFunctions(Decoder* decoder){
	LLVMWriter writer("", decoder);
	
	map<string, Function*>* functions = new map<string, Function*>();

	// Add external functions
	for (Module::iterator FI = headerMd->begin(), FE = headerMd->end(); FI != FE; ++FI) {
		if (FI->isDeclaration()){
			Function* function = writer.addFunctionProtosExternal(FI);
			functions->insert(pair<string, Function*>(FI->getName(), function));
		}
	}

	map<int, FifoAccess*>::iterator itAcc;
	map<string, Function*>::iterator itMap;
	for (itAcc = fifoAccesses.begin(); itAcc != fifoAccesses.end(); itAcc++){
		FifoAccess* fifoAccess  = itAcc->second;

		//Write signed functions
		map<string, Function*>* signedFns = fifoAccess->getSignedFn();
		for(itMap = signedFns->begin(); itMap != signedFns->end(); ++itMap){
			Function* function = writer.addFunctionProtosInternal((*itMap).second);
			writer.linkProcedureBody((*itMap).second);
			functions->insert(pair<string, Function*>(itMap->first, function));
		}

		//Write unsigned functions
		map<string, Function*>* unsignedFns = fifoAccess->getUnsignedFn();
		for(itMap = unsignedFns->begin(); itMap != unsignedFns->end(); ++itMap){
			Function* function = writer.addFunctionProtosInternal((*itMap).second);
			writer.linkProcedureBody((*itMap).second);
			functions->insert(pair<string, Function*>(itMap->first, function));
		}
	}

	return functions;
}
FifoMng::FifoAccess* FifoMng::getFifoAccess(llvm::IntegerType* type){
	map<int, FifoAccess*>::iterator itAcc;
	
	itAcc = fifoAccesses.find(type->getBitWidth());
	if (itAcc == fifoAccesses.end()){
		cout << "Error : Fifo for tokens of size " << type->getBitWidth() << "does not exist.";
		exit(1);
	}

	return itAcc->second;
}

Function* FifoMng::getPeekFunction(IntegerType* type, Decoder* decoder){
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_peek";
	it = fifoFns->find(name.str());

	return it->second;
}

Function* FifoMng::getReadFunction(IntegerType* type, Decoder* decoder){
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_read";
	it = fifoFns->find(name.str());

	return it->second;
}

Function* FifoMng::getWriteFunction(IntegerType* type, Decoder* decoder){
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_write";
	it = fifoFns->find(name.str());

	return it->second;
}

Function* FifoMng::getHasTokenFunction(IntegerType* type, Decoder* decoder){
	
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_has_tokens";
	it = fifoFns->find(name.str());

	return it->second;
}

Function* FifoMng::getHasRoomFunction(IntegerType* type, Decoder* decoder){
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_has_room";
	it = fifoFns->find(name.str());

	return it->second;
}

Function* FifoMng::getWriteEndFunction(IntegerType* type, Decoder* decoder){
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_write_end";
	it = fifoFns->find(name.str());

	return it->second;
}

Function* FifoMng::getReadEndFunction(IntegerType* type, Decoder* decoder){	
	//Todo : this is a fast programming solution, should be rewrite later
	map<string, Function*>::iterator it;
	map<string, Function*>* fifoFns = decoder->getFifoFn();
	stringstream name;
	name << "fifo_i"<<type->getBitWidth()<< "_read_end";
	it = fifoFns->find(name.str());

	return it->second;
}
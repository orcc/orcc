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

#include "Jade/Jit/LLVMParser.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace std;
using namespace llvm;

//Initializing static elements
std::string FifoMng::fifoFiles[] = { "FifoCircular", "FifoTrace", "FifoUnprotected"};
FifoTy FifoMng::fifoTy;
string FifoMng::externFnFile;
string FifoMng::packageFolder;
Module* FifoMng::headerMd;
Module* FifoMng::externFnMd;
map<string,string> FifoMng::structName;
map<string,Type*> FifoMng::structAcces;
map<string,string> FifoMng::fifoFunct;
map<string, Function*> FifoMng::externFunct;
list<Function*> FifoMng::externStruct;
map<string,Function*> FifoMng::fifoAccess;

void FifoMng::parseFifos(){
	LLVMContext &Context = getGlobalContext();
	
	//Create the parser
	LLVMParser parser(Context, getPackageFolder());

	//Parse fifos
	headerMd = parser.loadBitcode("System", FifoMng::getFifoFilename());

	if (headerMd == NULL){
		cout << "Unable to parse fifo header file";
		exit(0);
	}

	//parse extern functions
	externFnMd = parser.loadBitcode("System", "Extern");

	if (externFnMd == NULL){
		cout << "Unable to parse extern functions file";
		exit(0);
	}

}

void FifoMng::parseExternFunctions(){
	
	// Iterate though functions of extern module 
	for (Module::iterator I = externFnMd->begin(), E = externFnMd->end(); I != E; ++I) {
		externFunct.insert(pair<std::string,llvm::Function*>(I->getName(), I));
	}
}

void FifoMng::setFifoStruct(std::string name, llvm::Type* type){
		std::map<std::string,llvm::Type*>::iterator it;

		it = structAcces.find(name);

		if (it == structAcces.end()){
			cerr << "Error when setting structure of fifo";
			exit(0);
		}
	
		(*it).second = type;
}

void FifoMng::parseFifoStructs(){
	map<string,string>::iterator it;
	
	// Iterate though structure
	for (it = structName.begin(); it != structName.end(); ++it) {
		string name = it->second;

		Type* type = (Type*)headerMd->getTypeByName(name);

		if (type == NULL){
			cerr << "Error when parsing fifo, structure "<< name << " has not beend found";
			exit(0);
		}

		setFifoStruct(name, type);
		
	}
}

void FifoMng::parseFifoFunctions(){
	
	// Iterate though functions of header 
	for (Module::iterator I = headerMd->begin(), E = headerMd->end(); I != E; ++I) {
		string name = I->getName();
		
		if (isFifoFunction(name)){
			setFifoFunction(name, I);
		}
	}
}

StructType* FifoMng::getFifoType(IntegerType* type){
	map<string,Type*>::iterator it;

	// Struct name 
	ostringstream structName;
	structName << "struct.fifo_i" << type->getBitWidth() << "_s";

	it = structAcces.find(structName.str());
		
	return cast<StructType>(it->second);
}


bool FifoMng::isFifoFunction(string name){
	if (fifoAccess.find(name)==fifoAccess.end())
		return false;
	
	return true;
};

void FifoMng::setFifoFunction(std::string name, llvm::Function* function){
		std::map<std::string,llvm::Function*>::iterator it;

		it = fifoAccess.find(name);

		if (it == fifoAccess.end()){
			cerr << "Error when setting circular fifo";
			exit(0);
		}
	
		(*it).second = function;
}

string FifoMng::funcName(IntegerType* type, string func){
	ostringstream name;

	name << "i" <<type->getBitWidth()<< "_" << func;

	return name.str();
}


Function* FifoMng::getPeekFunction(Type* type){
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "peek")]];
}

Function* FifoMng::getReadFunction(Type* type){
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "read")]];
}

Function* FifoMng::getWriteFunction(Type* type){
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "write")]];
}

Function* FifoMng::getHasTokenFunction(Type* type){
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "hasToken")]];
}

Function* FifoMng::getHasRoomFunction(Type* type){
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "hasRoom")]];
}

Function* FifoMng::getWriteEndFunction(Type* type){
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "writeEnd")]];
}

Function* FifoMng::getReadEndFunction(Type* type){	
	return fifoAccess[fifoFunct[funcName(cast<IntegerType>(type), "readEnd")]];
}

void FifoMng::fifoMap(){
	fifoFunct["i8_peek"] = "fifo_i8_peek";
	fifoFunct["i8_write"] = "fifo_i8_write";
	fifoFunct["i8_read"] = "fifo_i8_read";
	fifoFunct["i8_hasToken"] = "fifo_i8_has_tokens";
	fifoFunct["i8_hasRoom"] = "fifo_i8_has_room";
	fifoFunct["i8_writeEnd"] = "fifo_i8_write_end";
	fifoFunct["i8_readEnd"] = "fifo_i8_read_end";

	fifoFunct["i16_peek"] = "fifo_i16_peek";
	fifoFunct["i16_write"] = "fifo_i16_write";
	fifoFunct["i16_read"] = "fifo_i16_read";
	fifoFunct["i16_hasToken"] = "fifo_i16_has_tokens";
	fifoFunct["i16_hasRoom"] = "fifo_i16_has_room";
	fifoFunct["i16_writeEnd"] = "fifo_i16_write_end";
	fifoFunct["i16_readEnd"] = "fifo_i16_read_end";

	fifoFunct["i32_peek"] = "fifo_i32_peek";
	fifoFunct["i32_write"] = "fifo_i32_write";
	fifoFunct["i32_read"] = "fifo_i32_read";
	fifoFunct["i32_hasToken"] = "fifo_i32_has_tokens";
	fifoFunct["i32_hasRoom"] = "fifo_i32_has_room";
	fifoFunct["i32_writeEnd"] = "fifo_i32_write_end";
	fifoFunct["i32_readEnd"] = "fifo_i32_read_end";

	fifoFunct["i64_peek"] = "fifo_i64_peek";
	fifoFunct["i64_write"] = "fifo_i64_write";
	fifoFunct["i64_read"] = "fifo_i64_read";
	fifoFunct["i64_hasToken"] = "fifo_i64_has_tokens";
	fifoFunct["i64_hasRoom"] = "fifo_i64_has_room";
	fifoFunct["i64_writeEnd"] = "fifo_i64_write_end";
	fifoFunct["i64_readEnd"] = "fifo_i64_read_end";

	fifoFunct["u8_peek"] = "fifo_u8_peek";
	fifoFunct["u8_write"] = "fifo_u8_write";
	fifoFunct["u8_read"] = "fifo_u8_read";
	fifoFunct["u8_hasToken"] = "fifo_u8_has_tokens";
	fifoFunct["u8_hasRoom"] = "fifo_u8_has_room";
	fifoFunct["u8_writeEnd"] = "fifo_u8_write_end";
	fifoFunct["u8_readEnd"] = "fifo_u8_read_end";

	fifoFunct["u16_peek"] = "fifo_u16_peek";
	fifoFunct["u16_write"] = "fifo_u16_write";
	fifoFunct["u16_read"] = "fifo_u16_read";
	fifoFunct["u16_hasToken"] = "fifo_u16_has_tokens";
	fifoFunct["u16_hasRoom"] = "fifo_u16_has_room";
	fifoFunct["u16_writeEnd"] = "fifo_u16_write_end";
	fifoFunct["u16_readEnd"] = "fifo_u16_read_end";

	fifoFunct["u32_peek"] = "fifo_u32_peek";
	fifoFunct["u32_write"] = "fifo_u32_write";
	fifoFunct["u32_read"] = "fifo_u32_read";
	fifoFunct["u32_hasToken"] = "fifo_u32_has_tokens";
	fifoFunct["u32_hasRoom"] = "fifo_u32_has_room";
	fifoFunct["u32_writeEnd"] = "fifo_u32_write_end";
	fifoFunct["u32_readEnd"] = "fifo_u32_read_end";

	fifoFunct["u64_peek"] = "fifo_u64_peek";
	fifoFunct["u64_write"] = "fifo_u64_write";
	fifoFunct["u64_read"] = "fifo_u64_read";
	fifoFunct["u64_hasToken"] = "fifo_u64_has_tokens";
	fifoFunct["u64_hasRoom"] = "fifo_u64_has_room";
	fifoFunct["u64_writeEnd"] = "fifo_u64_write_end";
	fifoFunct["u64_readEnd"] = "fifo_u64_read_end";

	// Initialized element 
	map<string, string>::iterator it;
	for(it = fifoFunct.begin(); it != fifoFunct.end(); ++it){
		fifoAccess.insert(pair<string,Function*>((*it).second,NULL));
	}
}
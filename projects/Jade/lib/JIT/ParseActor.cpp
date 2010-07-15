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
@brief Implementation of actor loading in class JIT
@author Jerome Gorin
@file LoadActor.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <iostream>

#include "llvm/Metadata.h"
#include "llvm/Constants.h"
#include "llvm/ValueSymbolTable.h"
#include "llvm/Bitcode/ReaderWriter.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/MemoryBuffer.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Actor.h"
#include "Jade/Actor/Location.h"
#include "Jade/Decoder/Decoder.h"
//------------------------------

using namespace llvm;
using namespace std;

static cl::opt<bool> Verbose("v", cl::desc("Print information about actions taken"));
extern cl::opt<std::string> VTLDir;
extern cl::opt<std::string> ToolsDir;
 
Module* JIT::LoadBitcode(string file, string directory) {
	string ErrorMessage;
	file.append(".bc");

	sys::Path Filename = getFilename(file, directory);
  
	if (Verbose) cout << "Loading '" << Filename.c_str() << "'\n";
	Module* Result = 0;

	const std::string &FNStr = Filename.c_str();
	if (MemoryBuffer *Buffer = MemoryBuffer::getFileOrSTDIN(FNStr,
															&ErrorMessage)) {
		Result = ParseBitcodeFile(Buffer, Context, &ErrorMessage);
		delete Buffer;
	}
	if (Result) return Result;   // Load successful!

	if (Verbose) {
		cout << "Error opening bitcode file: '" << Filename.c_str() << "'";
		if (ErrorMessage.size()) cout << ": " << ErrorMessage;
		cout << "\n";

	}
	
	return NULL;
}

sys::Path JIT::getFilename(string bitcode, string directory){
	sys::Path Filename;

	//Filename is correct
	if (!Filename.set(bitcode)) {
		cout << "Invalid file name: '" << bitcode << "'\n";
		exit(0);
	}

	//Test if file is located in current folder
	if (Filename.exists()) {
		return Filename;
	}

	//Test if file is located in directory
	if (!directory.empty()){
		string DirFile = bitcode;

		DirFile.insert(0,directory);
		Filename.set(DirFile);

		if (Filename.exists()) {
			return Filename;
		}
	}

	//Test if file is located in VTL Folder
	if (!VTLDir.empty()){
		string VtlFile = bitcode;

		VtlFile.insert(0,VTLDir);
		Filename.set(VtlFile);

		if (Filename.exists()) {
			return Filename;
		}
	}

	//Test if file is located in tools Folder
	if (!ToolsDir.empty()){
		string ToolFile = bitcode;

		ToolFile.insert(0,ToolsDir);
		Filename.set(ToolFile);

		if (Filename.exists()) {
			return Filename;
		}
	}

	cout << "Bitcode file: '" << Filename.c_str() << "' does not exist.\n";
	exit(0);
}


bool JIT::addType(string name, const Type* type, Decoder* decoder){
	Module* module = decoder->getModule();
	return module->addTypeName(name, type);
}

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
#include "llvm/System/Signals.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Actor.h"
#include "Jade/Actor/Location.h"
#include "Jade/Decoder/Decoder.h"
//------------------------------

using namespace llvm;
using namespace std;

static cl::opt<bool> Verbose("v", cl::desc("Print information about actions taken"));
 
static cl::opt<std::string>
VTLDir("L", cl::desc("Video Tools Library directory"), cl::value_desc("VTL Folder"));

Module* JIT::LoadBitcode(string file) {
	sys::Path Filename;
	string ErrorMessage;
	file.append(".bc");
  

	if (!VTLDir.empty()){
		file.insert(0,VTLDir);
	}


	if (!Filename.set(file)) {
		cout << "Invalid file name: '" << file << "'\n";
		return NULL;
	}

	if (Filename.exists()) {
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
	} else {
		cout << "Bitcode file: '" << Filename.c_str() << "' does not exist.\n";
	}

	return NULL;
}


bool JIT::addType(string name, const Type* type, Decoder* decoder){
	Module* module = decoder->getModule();
	return module->addTypeName(name, type);
}

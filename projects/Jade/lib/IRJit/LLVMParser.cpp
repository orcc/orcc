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
@brief Implementation of LLVMParser
@author Jerome Gorin
@file LLVMParser.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <iostream>


#include "llvm/Support/CommandLine.h"
#include "llvm/Support/IRReader.h"

#include "Jade/Jit/LLVMParser.h"
//------------------------------

using namespace llvm;
using namespace std;

static cl::opt<bool> Verbose("v", cl::desc("Print information about actions taken"));
extern cl::opt<std::string> VTLDir;
extern cl::opt<std::string> ToolsDir;
 

LLVMParser::LLVMParser(LLVMContext& C, string directory): Context(C){
	this->directory = directory;
}


Module* LLVMParser::loadBitcode(string file) {
	SMDiagnostic Err;
	string bitcode = file;
	bitcode.append(".bc");

	sys::Path Filename = getFilename(bitcode);

	//bitcode not found, looking for assembly
	if (!Filename.exists()){
		string assembly = file;
		assembly.append(".s");
		Filename = getFilename(assembly);
	}
  
	if (Verbose) cout << "Loading '" << Filename.c_str() << "'\n";
	Module* Result = 0;

	const std::string &FNStr = Filename.c_str();
	
	// Load the bitcode...
	Module *Mod = ParseIRFile(Filename.c_str(), Err, Context);
 
	if (!Mod) {
		cout << "Error opening bitcode file: '" << Filename.c_str() << "'";
		exit(1);
	}
	
	return Mod;
}

sys::Path LLVMParser::getFilename(string file){
	sys::Path Filename;

	//Filename is correct
	if (!Filename.set(file)) {
		cout << "Invalid file name: '" << file << "'\n";
		exit(0);
	}

	//Test if file is located in current folder
	if (Filename.exists()) {
		return Filename;
	}

	//Test if file is located in directory
	if (!directory.empty()){
		string DirFile = file;

		DirFile.insert(0,directory);
		Filename.set(DirFile);

		if (Filename.exists()) {
			return Filename;
		}
	}

	//Test if file is located in VTL Folder
	if (!VTLDir.empty()){
		string VtlFile = file;

		VtlFile.insert(0,VTLDir);
		Filename.set(VtlFile);

		if (Filename.exists()) {
			return Filename;
		}
	}

	//Test if file is located in tools Folder
	if (!ToolsDir.empty()){
		string ToolFile = file;

		ToolFile.insert(0,ToolsDir);
		Filename.set(ToolFile);

		if (Filename.exists()) {
			return Filename;
		}
	}

	return sys::Path("");
}
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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>

#include "llvm/Support/IRReader.h"

#include "Jade/Jit/LLVMParser.h"
#include "Jade/Util/PackageMng.h"
//------------------------------

using namespace llvm;
using namespace std;

 

LLVMParser::LLVMParser(LLVMContext& C, string directory, bool verbose): Context(C){
	this->directory = directory;
	this->verbose = verbose;
}


Module* LLVMParser::loadBitcode(string package, string file) {
	SMDiagnostic Err;

	//Get filename of the actor
	sys::Path Filename= getFilename(package, file);

	if (verbose) cout << "Loading '" << Filename.c_str() << "'\n";
	
	// Load the bitcode...
	Module *Mod = ParseIRFile(Filename.c_str(), Err, Context);

	if (!Mod) {
		//Error when parsing module
		cerr << "Error opening bitcode file: '" << file.c_str() << "\n";
		exit(1);
	}

	return Mod;
}

sys::Path LLVMParser::getFilename(string packageName, string file){
	
	//Verifying if package is existing
	string package = PackageMng::getFolder(packageName);
	sys::Path packagePath(directory + package);

	if (!packagePath.exists()){
		//Package doesn't exist
		cout << "Package" << packagePath.c_str() << " is required but not found.'\n";
		exit(0);
	}

	if (packagePath.isArchive()){
		//Archive is not supported yet
		cout << "Found " << packagePath.c_str() << " as archive.'\n";
		exit(0);
	}

	//Verifying if file is existing
	sys::Path actorFile(packagePath.str()+"/"+file);

	if (!actorFile.exists()){
		cout <<  "File  " << file.c_str() << " has not been found in package "<< packageName.c_str() <<".\n";
		exit(0);
	}
	
	return actorFile;
}
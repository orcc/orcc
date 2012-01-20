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

#include "llvm/Bitcode/Archive.h"
#include "llvm/Support/IRReader.h"

#include "Jade/Jit/LLVMParser.h"
#include "Jade/Util/PackageMng.h"
#include "Jade/Util/CompressionMng.h"
//------------------------------

using namespace llvm;
using namespace std;

 

LLVMParser::LLVMParser(LLVMContext& C, string directory, bool verbose): Context(C){
	this->directory = directory;
	this->verbose = verbose;
}


Module* LLVMParser::loadModule(Package* package, string file) {
	SMDiagnostic Err;
	Module *Mod;

	//Get filename of the actor
	sys::Path Filename(directory + package->getDirectory() + "/" + file);

	/*//Load the bitcode
	if(!Filename.exists()){
		//Archive case
		Mod = ParseArchive(package, Filename);
	}else{*/
		//Bitecode and Assembly case
		Mod = ParseIRFile(Filename.c_str(), Err, Context);
	//}

	if (verbose) cout << "Loading '" << Filename.c_str() << "'\n";	

	if (!Mod) {
		//Error when parsing module
		cerr << "Error parsing bitcode file '" << file.c_str() << "' at line " << Err.getLineNo() << "\n";
		cerr << Err.getMessage() << "\n";
		cerr << Err.getFilename() << "\n";
		exit(1);
	}

	return Mod;
}

Module* LLVMParser::ParseArchive(Package* package, sys::Path file){
	if(!package->isArchive()){
			openArchive(package);
	}

	return loadBitcodeInArchive(package, file);
}

Module* LLVMParser::loadBitcodeInArchive(Package* package, sys::Path file) {
	std::string Error;
	LLVMContext &Context = getGlobalContext();

	Module* Mod = NULL;
	Archive* archive = package->getArchive();
	Archive::iterator itArch;

	//Find and load module
	for(itArch = archive->begin(); itArch != archive->end(); itArch++){
		if(itArch->getPath() == file){
			MemoryBuffer *Buffer = MemoryBuffer::getMemBufferCopy(StringRef(itArch->getData(),
																			itArch->getSize()),
																  file.str());       
			
			Mod = ParseBitcodeFile(Buffer, Context, &Error);
			if (Error != "")
				cerr <<"Error when parse actorfile "<< file.c_str() << "in archive" <<archive->getPath().c_str();

			break;
		}	
	}

	return Mod;
}

void LLVMParser::openArchive(Package* package){
	std::string Error;
	LLVMContext &Context = getGlobalContext();

	//Get archive file name
	string archiveName = PackageMng::getFirstFolder(package->getDirectory()) + ".a";
	sys::Path archiveFile(directory + archiveName);

	//If useful, uncompress archive 
	if(CompressionMng::IsGZipFile(archiveFile.c_str())){
		string GZipFile = archiveFile.str() + ".gz";
		CompressionMng::uncompressGZip(GZipFile);
	}

	//Check if archive exists
	if(!archiveFile.exists()){
		cerr <<"Package system not found";
		exit(1);
	}

	//Load archive
	package->setArchive(Archive::OpenAndLoad(archiveFile, Context, &Error));
	
	if (Error != ""){
		cerr <<"Error when open archive "<< archiveFile.c_str();
	}

	//Set archive on all parents of package
	PackageMng::setArchive(package);
}

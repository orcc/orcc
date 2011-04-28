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
@brief  Archive manager of JadeToolbox
@author Jerome Gorin
@file ArMng.cpp
@version 1.0
@date 2011/03/22
*/

//------------------------------
#include <iostream>

#include "Jade/Util/CompressionMng.h"

#include "ArMng.h"
#include "FilesMng.h"
//------------------------------

using namespace std;
using namespace llvm;

extern cl::opt<bool> OutputCompressedArchive;

extern cl::list<string> Packages;
extern cl::opt<string> LibraryFolder;


void createArchives(map<sys::Path,string>* filesPath){
	map<string,Archive*> archives;
	map<string,Archive*>::iterator itArchive;
	map<sys::Path,string>::iterator itPaths;
	LLVMContext &Context = getGlobalContext();
	std::string errorMsg;	
	
	for (itPaths = filesPath->begin(); itPaths != filesPath->end(); itPaths++){
		Archive* archive;

		itArchive = archives.find(itPaths->second);
	
		// Create a new archive
		if(itArchive == archives.end()){
			sys::Path ArchivePath(LibraryFolder + itPaths->second + ".a");
			archive = Archive::CreateEmpty(ArchivePath, Context);
			archives.insert(pair<string,Archive*>(itPaths->second, archive));

		}else{
			archive = itArchive->second;
		}
		
		// Insert actor in archive
		archive->addFileBefore(itPaths->first, archive->end(), &errorMsg);

		if (errorMsg != "")
			cerr <<"Error when insert actor "<< itPaths->first.c_str() <<" in archive\n";
	}

	// Write archives to the disk
	for (itArchive = archives.begin(); itArchive != archives.end(); itArchive++){
		itArchive->second->writeToDisk(true, false, true, &errorMsg);
		if (errorMsg != ""){
			cerr <<"Error when creat "<< itArchive->first << ".a\n";
		}
		// Compress archives
		if(OutputCompressedArchive){
			CompressionMng::compressFile(itArchive->second->getPath().c_str());
		}
	}

	// Erase all initial files
	cl::list<string>::iterator itPack;

	for (itPack=Packages.begin() ; itPack != Packages.end(); itPack++){
		sys::Path erasePath(LibraryFolder + *itPack);
		erasePath.eraseFromDisk(true,&errorMsg);
		if (errorMsg != ""){
			cerr <<"Error when erase the directory"<< erasePath.str() << "\n";
		}
	}
}
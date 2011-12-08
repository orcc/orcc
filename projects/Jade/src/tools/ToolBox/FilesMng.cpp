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
@brief Files manager of JadeToolbox
@author Jerome Gorin
@file FilesMng.cpp
@version 1.0
@date 2011/03/22
*/

//------------------------------
#include <iostream>
#include <list>
#include <map>

#include "FilesMng.h"
//------------------------------

using namespace std;
using namespace llvm;

extern cl::list<string> Packages;
extern cl::opt<string> LibraryFolder;


void buildFilesPath(map<sys::Path,string>* filesPath){
	cl::list<string>::iterator itPack;

	for (itPack=Packages.begin() ; itPack != Packages.end(); itPack++){
		sys::Path fullFilePath(LibraryFolder + *itPack);

		// Make sure the package is valid and exist
		if (!fullFilePath.exists()){
			cerr << "Package does not exist: " << itPack->c_str();
			continue;
		}

		sys::PathWithStatus PwS(fullFilePath);
		const sys::FileStatus *si = PwS.getFileStatus(false);
		if (!si){
	        cerr << "Can not find statut (file or directory) of " << itPack->c_str();
			continue;
		}

		// Create a path for all files in the package
		if (si->isDir) {
			map<sys::Path,string> dirPaths;
			recurseMapDirectories(fullFilePath, itPack->c_str(), dirPaths);
			filesPath->insert(dirPaths.begin(), dirPaths.end());
		} else {
			filesPath->insert(pair<sys::Path,string>(fullFilePath, itPack->c_str()));
		}
	}
}

void parseFiles(map<sys::Path,string>* filesPath, map<string,Module*>* modules){
	LLVMContext &Context = getGlobalContext();
	
	SMDiagnostic Err;
	map<sys::Path,string>::iterator itPath;
	
	for (itPath = filesPath->begin() ; itPath != filesPath->end(); itPath++){	
		//Iterate though all actors to parse
		if (!itPath->first.exists()){
			cerr <<"Actor "<< itPath->first.c_str() << " not found.\n";
			continue;
		}

		if (itPath->first.str().compare("D:/projet/orcc/trunk/projects/Jade/VTL/ch/epfl/mpeg4/part10/frext/synParser/modular/decodePicture/MgntAndVLDecoding")==0){
			int test = 0;
		}

		//Parse IR file
		Module* mod = ParseIRFile(itPath->first.c_str(), Err, Context);

		if (mod == NULL){
			cerr <<"Error when parsing "<< itPath->first.c_str() << ".\n";
			continue;
		}

		//Store results
		modules->insert(pair<string,Module*>(itPath->first.c_str(), mod));
	}
}

// This function scans through the package and return all files in that directory (recursively) 
// with for everyone the name of package. It uses the sys::Path::getDirectoryContent method 
// to perform the actual directory scans.
void recurseMapDirectories(const sys::Path& path, string name, map<sys::Path,string>& result) {
	string errorMsg;
	result.clear();
	set<sys::Path> content;
	if (path.getDirectoryContents(content, &errorMsg))
		if (errorMsg != "")
			cerr <<"Recurse directory error\n";

	set<sys::Path>::iterator I;
	for (I = content.begin() ; I != content.end() ; ++I) {
		// Make sure it exists and is a directory
		sys::PathWithStatus PwS(*I);
		const sys::FileStatus *Status = PwS.getFileStatus(false, &errorMsg);
		if (errorMsg != "")
			cerr <<"Recurse directory error\n";

		if (Status->isDir) {
			map<sys::Path,string> moreResults;
			recurseMapDirectories(*I, name, moreResults);
			result.insert(moreResults.begin(), moreResults.end());
		} else {
			result.insert(pair<sys::Path,string>(*I,name));
		}
	}
}
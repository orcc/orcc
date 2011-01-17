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
@brief Implementation of class PackageMng
@author Jerome Gorin
@file PackageMng.cpp
@version 1.0
@date 17/12/2010
*/

//------------------------------
#include "Jade/Util/PackageMng.h"
#include "Jade/Core/Package.h"

#include <algorithm>
//------------------------------


using namespace std;

string PackageMng::getFolder(Actor* actor){
	return getFolder(getPackages(actor));
}

string PackageMng::getFolder(string package){
	replace( package.begin(), package.end(), '.', '/' );
	return package;
}

string PackageMng::getPackages(Actor* actor){
	return getPackages(actor->getName());
}

string PackageMng::getPackages(string name){
	int index = name.rfind('.');

	if (index == string::npos){
		return "";
	}

	return name.substr(0, index);
}

list<std::string> PackageMng::getPackageList(Actor* actor){
	return getPackageList(actor->getName());
}

list<std::string> PackageMng::getPackageList(string name){
	list<string> packageList;
	string packages = getPackages(name);

	int index = packages.find('.');

	//Split string separate by a .
	while (index != string::npos){
		packageList.push_back(packages.substr(0, index));
		packages = packages.substr(index+1, packages.size());
		index = packages.find('.');
	}

	//Insert last package
	packageList.push_back(packages);

	return packageList;
}

string PackageMng::getFirstPackage(Actor* actor){
	return getFirstPackage(actor->getName());
}

string PackageMng::getFirstPackage(string name){
	int index = name.find('.');

	if (index == string::npos){
		return "";
	}

	return name.substr(0, index);
}

string PackageMng::getSimpleName(Actor* actor){
	return getSimpleName(actor->getName());
}

string PackageMng::getSimpleName(string name){
	int index = name.rfind('.');

	if (index == string::npos){
		return "";
	}

	return name.substr(index + 1);
}

map<string, Package*>* PackageMng::setPackages(map<string, Actor*>* actors){
	map<string, Package*>::iterator itPack;
	map<string, Actor*>::iterator itAct;

	//Resulting map of package
	map<string, Package*>* packages = new map<string, Package*>();

	//Iterate though every actors to determine and order their package
	for (itAct = actors->begin(); itAct != actors->end(); itAct++){
		list<string>::iterator itStrPack;
		list<string> packageStrs = getPackageList(itAct->second);
		
		//Current position of the package
		map<string, Package*>* packagesPtr = packages;
		Package* package = NULL;

		//Iterate though the current package hierarchy
		for (itStrPack = packageStrs.begin(); itStrPack != packageStrs.end(); itStrPack++){
			itPack = packagesPtr->find(*itStrPack);

			if (itPack == packagesPtr->end()){
				//Package does not exist, creates one
				package = new Package(*itStrPack, package);
				packagesPtr->insert(pair<string, Package*>(*itStrPack, package));
			}else{
				//Package found
				package = itPack->second;
			}

			//Loop though childs package
			packagesPtr = package->getChilds();
		}
		
		//Insert current actor into the last package
		package->insertUnderneath(itAct->second);
	}

	//Return the resulting map of packages
	return packages;
}
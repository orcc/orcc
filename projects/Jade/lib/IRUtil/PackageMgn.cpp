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
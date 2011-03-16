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
@brief Implementation of class Procedure
@author Jerome Gorin
@file Procedure.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include "Jade/Core/Actor/Pattern.h"
//------------------------------

using namespace std;
using namespace llvm;

Pattern::Pattern(map<Port*, ConstantInt*>* numTokensMap, map<Port*, Variable*>* variableMap, map<Port*, Variable*>* peekedMap){
	this->numTokensMap = numTokensMap;
	this->variableMap = variableMap;
	this->peekedMap = peekedMap;

	//Fill ports with num tokens
	map<Port*, ConstantInt*>::iterator itTokens;
	for (itTokens = numTokensMap->begin(); itTokens != numTokensMap->end(); itTokens++){
		checkPortPresence(itTokens->first);
	}

	//Fill ports with variable map
	map<Port*, Variable*>::iterator itVar;
	for (itVar = variableMap->begin(); itVar != variableMap->end(); itVar++){
		checkPortPresence(itVar->first);
	}
	
	//Fill ports with peeked map
	for (itVar = peekedMap->begin(); itVar != peekedMap->end(); itVar++){
		checkPortPresence(itVar->first);
	}
}

Pattern::Pattern(){
	this->numTokensMap = new map<Port*, ConstantInt*>();
	this->variableMap = new map<Port*, Variable*>();
	this->peekedMap = new map<Port*, Variable*>();
}

void Pattern::checkPortPresence(Port* port) {
	ports.insert(port);
}

void Pattern::clear(){
	ports.clear();
	numTokensMap->clear();
	peekedMap->clear();
	variableMap->clear();
}

void Pattern::remove(Port* port) {
	ports.erase(port);
	numTokensMap->erase(port);
	peekedMap->erase(port);
	variableMap->erase(port);
}
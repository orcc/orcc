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
@brief Implementation of class CheckPinoRules
@author Jerome Gorin
@file CheckPinoRules.cpp
@version 1.0
@date 24/12/2010
*/

//------------------------------
#include <algorithm>

#include "CheckPinoRules.h"

#include "Jade/Core/Network.h"
//------------------------------

using namespace std;
using namespace llvm;

CheckPinoRules::CheckPinoRules(Network* network){
	this->network = network;
}


bool CheckPinoRules::isValide(Instance* src, Instance* dst){
	this->dst = dst;
	this->src = src;

	//Check cycle violation
	if (checkCycle(src)){
		return false;
	}
	
	//Check cycle violation
	if (checkCycle(dst)){
		return false;
	}

	// Check precedence violation
	if(checkPrecedence(src, dst)){
		return false;
	}

	return true;
}

bool CheckPinoRules::checkCycle(Instance* instance){
	list<Instance*> visited;
	paths.clear();
	
	visited.push_back(instance);
	findPath(&visited, instance);

	return paths.size() > 0;
}

bool CheckPinoRules::checkPrecedence(Instance* src, Instance* dst){
	list<Instance*> visited;
	paths.clear();
	
	visited.push_back(src);
	findPath(&visited, dst);

	return paths.size() > 1;
}


void CheckPinoRules::findPath(list<Instance*>* visited, Instance* end){
	list<Instance*>::iterator it;

	// Get last visited element
	list<Instance*> nodes = network->getSuccessorsOf(visited->back());

	// Check path to all successor
	for (it = nodes.begin(); it != nodes.end(); it++){
		Instance* node = *it;

		if (node == end){
			// End of search
			visited->push_back(node);
			storePath(visited);
			visited->pop_back();
		}

		if (find(visited->begin(), visited->end(), node) != visited->end()){
			// Element already visited check other successor
			continue;
		}
	}

	// Visit recursion needs to come after visiting adjacent nodes
	for (it = nodes.begin(); it != nodes.end(); it++){
		Instance* node = *it;

		if (find(visited->begin(), visited->end(), node) != visited->end() || node == end || node == src || node == dst){
			continue;
		}
		
		visited->push_back(node);
		findPath(visited, end);
		visited->pop_back();
	}
}

void CheckPinoRules::storePath(list<Instance*>* visited){
	set<Instance*>::iterator it;
	list<Instance*> path(visited->begin(), visited->end());

	paths.push_back(path);
}
/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
@brief Implementation of class Entity
@author Jerome Gorin
@file Port.cpp
@version 1.0
@date 31/12/2011
*/

//------------------------------
#include "Jade/Core/Entity.h"
//------------------------------

using namespace std;


Port* Entity::getInput(string name){
	std::map<std::string, Port*>::iterator it;
	
	//Look for the given name in input
	it = inputs->find(name);

	//Port not found
	if(it == inputs->end()){
		return NULL;
	}

	//Port found
	return (*it).second;
}

Port* Entity::getOutput(string name){
	std::map<std::string, Port*>::iterator it;
	
	//Look for the given name in output
	it = outputs->find(name);

	//Port not found
	if(it == outputs->end()){
		return NULL;
	}

	//Port found
	return (*it).second;
}

Procedure* Entity::getProcedure(string name){
	map<string, Procedure*>::iterator it;
	
	it = procedures->find(name);

	if(it == procedures->end()){
		return NULL;
	}

	return (*it).second;
}

Port* Entity::getPort(string name){
	Port* port = getInput(name);

	// Search inside input ports 
	if (port!= NULL){
		return port;
	}

	// Search inside output ports 
	return getOutput(name);
}

Variable* Entity::getParameter(std::string name){
	std::map<std::string, Variable*>::iterator it;
	it = parameters->find(name);

	if(it == parameters->end()){
		return NULL;
	}

	return (*it).second;
}

StateVar* Entity::getStateVar(std::string name){
	map<string, StateVar*>::iterator it;
	it = stateVars->find(name);

	if(it == stateVars->end()){
		return NULL;
	}

	return (*it).second;
}
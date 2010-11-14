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
@brief Implementation of class Instance
@author Jerome Gorin
@file Instance.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "llvm/Constants.h"
#include "llvm/GlobalVariable.h"

#include "Jade/Core/Instance.h"
#include "Jade/Core/Variable.h"
//------------------------------

using namespace std;
using namespace llvm;

Port* Instance::getPort(string portName){
	Port* port = getInput(portName);

	// Search inside input ports 
	if (port!= NULL){
		return port;
	}

	// Search inside output ports 
	return getOutput(portName);
}


Port* Instance::getInput(string portName){
	std::map<std::string, Port*>::iterator it;
	
	//Look for the given name in input
	it = inputs.find(portName);

	//Port not found
	if(it == inputs.end()){
		return NULL;
	}

	//Port found
	return (*it).second;
}

Port* Instance::getOutput(string portName){
	std::map<std::string, Port*>::iterator it;
	
	//Look for the given name in output
	it = outputs.find(portName);

	//Port not found
	if(it == outputs.end()){
		return NULL;
	}

	//Port found
	return (*it).second;
}

Variable* Instance::getStateVar(std::string name){
	map<string, Variable*>::iterator it;
	it = stateVars->find(name);

	if(it == stateVars->end()){
		return NULL;
	}

	return (*it).second;
}

Procedure* Instance::getProcedure(string name){
	map<string, Procedure*>::iterator it;
	
	it = procedures->find(name);

	if(it == procedures->end()){
		return NULL;
	}

	return (*it).second;
}

void Instance::setActor(Actor* actor){
	this->actor = actor;
	actor->addInstance(this);
}

void Instance::setAsInput(Port* port) {
	inputs.insert(pair<string, Port*>(port->getName(), port));
}

void Instance::setAsOutput(Port* port) {
	outputs.insert(pair<string, Port*>(port->getName(), port));
}

void Instance::solveParameters(){
	map<string, Expr*>::iterator itValues;
	std::map<std::string, Variable*>::iterator itParameter;
	
	for (itParameter= parameters->begin(); itParameter != parameters->end(); itParameter++){
		Variable* parameter = itParameter->second;
		llvm::GlobalVariable* variable = parameter->getGlobalVariable();
		itValues = parameterValues->find(itParameter->first);
		Expr* value = itValues->second;
		//ConstantInt* value = cast<ConstantInt>(itValues->second);
		//variable->setInitializer(value);
	}
}
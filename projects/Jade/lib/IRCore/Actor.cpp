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
@brief Implementation of class Actor
@author Jerome Gorin
@file Actor.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "Jade/Core/Actor.h"
#include "Jade/Core/Actor/Action.h"
#include "Jade/Core/Variable.h"
#include "Jade/Core/Instance.h"
//------------------------------

using namespace std;
using namespace llvm;

Actor::Actor(string name, string file, map<string, Type*>* fifoTypes, map<string, Port*>* inputs, 
		     map<string, Port*>* outputs, map<string, Variable*>* stateVars,
			 std::map<std::string, Variable*>* parameters, std::map<std::string, Procedure*>* procedures,
			 list<Action*>* initializes, list<Action*>* actions, ActionScheduler* actionScheduler){
	this->name = name;
	this->file = file;
	this->inputs = inputs;
	this->outputs = outputs;
	this->initializes = initializes;
	this->actions = actions;
	this->stateVars = stateVars;
	this->parameters = parameters;
	this->procedures = procedures;
	this->actionScheduler = actionScheduler;
	this->fifoTypes = fifoTypes;
}

Actor::~Actor (){

}

void Actor::addInstance(Instance* instance){
	if (instance->getActor() != this){
		instance->setActor(this);
	}
	instances.push_back(instance);
}

Port* Actor::getPort(string portName){
	Port* port = getInput(portName);

	// Search inside input ports 
	if (port!= NULL){
		return port;
	}

	// Search inside output ports 
	return getOutput(portName);
}

Procedure* Actor::getProcedure(string name){
	map<string, Procedure*>::iterator it;
	
	it = procedures->find(name);

	if(it == procedures->end()){
		return NULL;
	}

	return (*it).second;
}

Port* Actor::getInput(string portName){
	if (inputs->empty()){
		return NULL;
	}

	std::map<std::string, Port*>::iterator it;
	
	it = inputs->find(portName);

	if(it == inputs->end()){
		return NULL;
	}

	return (*it).second;
}

Type* Actor::getFifoType(string name) {
	map<string, Type*>::iterator it;

	it = fifoTypes->find(name);

	if(it == fifoTypes->end()){
		return NULL;
	}

	return (*it).second;
}

Port* Actor::getOutput(string portName){
	if (outputs->empty()){
		return NULL;
	}

	std::map<std::string, Port*>::iterator it;
	
	it = outputs->find(portName);

	if(it == outputs->end()){
		return NULL;
	}

	return (*it).second;
}

Variable* Actor::getParameter(std::string name){
	std::map<std::string, Variable*>::iterator it;
	it = parameters->find(name);

	if(it == parameters->end()){
		return NULL;
	}

	return (*it).second;
}

Variable* Actor::getStateVar(std::string name){
	map<string, Variable*>::iterator it;
	it = stateVars->find(name);

	if(it == stateVars->end()){
		return NULL;
	}

	return (*it).second;
}
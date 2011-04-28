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
@brief Implementation of class InstancedActor
@author Jerome Gorin
@file InstancedActor.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "Jade/Core/Actor.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/InstancedActor.h"
#include "Jade/Core/Instance.h"
//------------------------------

using namespace std;
using namespace llvm;

InstancedActor::InstancedActor(Decoder* decoder, Instance* instance,
								map<string, Port*>* inputs,
								map<string, Port*>* outputs,
								map<Variable*, GlobalVariable*>* stateVars,
								map<Variable*, GlobalVariable*>* parameters,
								map<Procedure*, Function*>* procedures,
								list<Action*>* actions,
								list<Action*>* initializes,
								ActionScheduler* scheduler){
		this->instance = instance;
		this->decoder = decoder;
		this->inputs = inputs;
		this->outputs = outputs;
		this->stateVars = stateVars;
		this->parameters = parameters;
		this->actions = actions;
		this->procedures = procedures;
		this->scheduler = scheduler;
		this->actor = instance->getActor();
		this->initializes = initializes;
}


GlobalVariable* InstancedActor::getParameterVar(Variable* parameter){
	map<Variable*, llvm::GlobalVariable*>::iterator it;
	
	it = parameters->find(parameter);

	if(it == parameters->end()){
		// This parameter has not be found in the instanced actor
		return NULL;
	}

	return (*it).second;

}

GlobalVariable* InstancedActor::getStateVar(Variable* stateVar){
	map<Variable*, GlobalVariable*>::iterator it;
	
	it = stateVars->find(stateVar);

	if(it == stateVars->end()){
		// This parameter has not be found in the instanced actor
		return NULL;
	}

	return (*it).second;
}

Port* InstancedActor::getPort(string portName){
	Port* port = getInput(portName);

	// Search inside input ports 
	if (port!= NULL){
		return port;
	}

	// Search inside output ports 
	return getOutput(portName);
}


Port* InstancedActor::getInput(string portName){
	std::map<std::string, Port*>::iterator it;
	
	it = inputs->find(portName);

	if(it == inputs->end()){
		return NULL;
	}

	return (*it).second;
}

Port* InstancedActor::getOutput(string portName){
	std::map<std::string, Port*>::iterator it;
	
	it = outputs->find(portName);

	if(it == outputs->end()){
		return NULL;
	}

	return (*it).second;
}

Function* InstancedActor::getProcedureVar(Procedure* procedure){
	map<Procedure*, Function*>::iterator it;
	
	it = procedures->find(procedure);

	if(it == procedures->end()){
		return NULL;
	}

	return (*it).second;
}
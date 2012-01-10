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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/GlobalVariable.h"

#include "Jade/Core/Actor.h"
#include "Jade/Core/Variable.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Core/Network/Vertex.h"
//------------------------------

using namespace std;
using namespace llvm;

Instance::Instance(HDAGGraph* graph, std::string id, std::string clasz, std::map<std::string, Expr*>* parameterValues, 
			 std::map<std::string, IRAttribute*>* attributes){
	this->id = id;
	this->clasz = clasz;
	this->parameterValues = parameterValues;
	this->attributes = attributes;
	this->actor = NULL;
	this->configuration = NULL;
	this->stateVars = NULL;
	this->parameters = NULL;
	this->procedures = NULL;
	this->initializes = NULL;
	this->actions = NULL;
	this->actionScheduler = NULL;
	this->parent = graph;

	// Add instance into graph
	this->vertex = new Vertex(this);
	graph->addVertex(vertex);
}

Instance::~Instance(){
	//Remove the instance from its parent
	if (actor != NULL){
		actor->remInstance(this);
	}
}

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

StateVar* Instance::getStateVar(std::string name){
	map<string, StateVar*>::iterator it;
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
string Instance::getMapping(){
	map<string, IRAttribute*>::iterator it;

	it = attributes->find("map");

	if (it == attributes->end()){
		return "";
	}
	
	return "";
}

void Instance::setActor(Actor* actor){
	this->actor = actor;
	
	//Insert this instance as a child of its parent
	if (actor != NULL){
		actor->addInstance(this);
	}
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
		Expr* expr = itValues->second;
		Constant* value = NULL;
		parameter->setInitialValue(expr);

		if (expr->isIntExpr()){
			//Get corresponding parameter value with the correct size
			GlobalVariable* GV = cast<GlobalVariable>(variable);
			Type* type = GV->getType()->getElementType();	

			value = ConstantInt::get(type, APInt(type->getScalarSizeInBits(), expr->evaluateAsInteger()));
		}else{
			value = expr->getConstant();
		}

		variable->setInitializer(value);
	}
}

Instance::Instance(std::string id, Actor* actor){
	this->id = id;
	this->actor = actor;
	this->configuration = NULL;
	this->stateVars = NULL;
	this->parameters = NULL;
	this->procedures = NULL;
	this->initializes = NULL;
	this->actions = NULL;
	this->actionScheduler = NULL;
	this->parameterValues = new map<string, Expr*>();
	this->attributes = new map<string, IRAttribute*>();
		
	if (actor != NULL){
		actor->addInstance(this);
	}
}
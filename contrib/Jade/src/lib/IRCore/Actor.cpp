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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include "Jade/Core/Actor.h"
#include "Jade/Core/Actor/Action.h"
#include "Jade/Core/StateVariable.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Util/PackageMng.h"
//------------------------------

using namespace std;
using namespace llvm;


Actor::Actor(std::string name, llvm::Module* module, std::string file) : Entity(new map<std::string, Port*>() , new map<std::string, Port*>(), NULL, NULL) {
	this->name = name;
	this->module = module;
	this->file = file;

	//Only actors with a file name are put in the package manager
	if(!this->file.empty()){
		PackageMng::setActor(this);
	}
}


Actor::Actor(string name, Module* module, string file, map<string, Port*>* inputs, 
		     map<string, Port*>* outputs, map<string, StateVar*>* stateVars,
			 std::map<std::string, Variable*>* parameters, std::map<std::string, Procedure*>* procedures,
			 list<Action*>* initializes, list<Action*>* actions, ActionScheduler* actionScheduler): Entity(inputs, outputs, initializes, actions){
	this->name = name;
	this->module = module;
	this->file = file;
	this->stateVars = stateVars;
	this->parameters = parameters;
	this->procedures = procedures;
	this->actionScheduler = actionScheduler;
	
	//Only actors with a file name are put in the package manager
	if(!this->file.empty()){
		PackageMng::setActor(this);
	}
}

Actor::Actor(string name, Module* module, string file, map<string, Port*>* inputs, 
		     map<string, Port*>* outputs, map<string, StateVar*>* stateVars,
			 std::map<std::string, Variable*>* parameters, std::map<std::string, Procedure*>* procedures,
			 list<Action*>* initializes, list<Action*>* actions, ActionScheduler* actionScheduler, MoC* moc): Entity(inputs, outputs, initializes, actions) {
	this->name = name;
	this->module = module;
	this->file = file;
	this->stateVars = stateVars;
	this->parameters = parameters;
	this->procedures = procedures;
	this->actionScheduler = actionScheduler;
	this->moc = moc;
	
	//Only actors with a file name are put in the package manager
	if(!this->file.empty()){
		PackageMng::setActor(this);
	}
}
Actor::~Actor (){
	list<Instance*>::iterator it;

	for (it = instances.begin(); it != instances.end(); it++){		
		//Avoid recursive call of destructors
		(*it)->setActor(NULL);

		delete(*it);
	}
}

void Actor::addInstance(Instance* instance){
	if (instance->getActor() != this){
		instance->setActor(this);
	}
	instances.push_back(instance);
}

void Actor::remInstance(Instance* instance){
	instances.remove(instance);
}


string Actor::getPackage() {
	return PackageMng::getPackagesName(this);
}

string Actor::getSimpleName() {
	return PackageMng::getSimpleName(this);
}

bool Actor::isNative(){
	string firstPackage = PackageMng::getFirstPackageName(this);
	return firstPackage.compare("System")== 0;
}
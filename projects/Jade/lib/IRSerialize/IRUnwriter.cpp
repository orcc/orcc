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
@brief Implementation of class IRUnwriter
@author Jerome Gorin
@file IRWriter.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>

#include "Jade/Decoder.h"
#include "Jade/Core/Port.h"
#include "Jade/Serialize/IRUnwriter.h"

#include "llvm/Module.h"

#include "IRConstant.h"
//------------------------------

using namespace std;
using namespace llvm;

IRUnwriter::IRUnwriter(Decoder* decoder){
	this->decoder = decoder;

}

IRUnwriter::~IRUnwriter(){

}

int IRUnwriter::remove(Instance* instance){
	Scheduler* scheduler = decoder->getScheduler();
	scheduler->removeInstance(instance);

	unwriteActionScheduler(instance->getActionScheduler());
	unwriteActions(instance->getActions());
	//unwritePorts(IRConstant::KEY_INPUTS, instance->getInputs());
	//unwritePorts(IRConstant::KEY_OUTPUTS, instance->getInputs());

	return 0;
}

void IRUnwriter::unwriteActionScheduler(ActionScheduler* actionScheduler){
	Function* function = actionScheduler->getSchedulerFunction();
	function->eraseFromParent();
}

void IRUnwriter::unwriteFSM(FSM* fsm){

}

std::map<std::string, Port*>* IRUnwriter::unwritePorts(string key, map<string, Port*>* ports){
	map<string, Port*>::iterator it;
	map<string, Port*>* newPorts = new map<string, Port*>();

	//Iterate though the given ports
	for (it = ports->begin(); it != ports->end(); ++it){
		string name = it->first;
		Port* port = it->second;

		//Create and store port for the instance
		unwritePort(key, port);
	}

	return newPorts;
}

void IRUnwriter::unwritePort(string key, Port* port){
	string name = port->getName();
	GlobalVariable* portVar = port->getGlobalVariable();
	portVar->eraseFromParent();
}

void IRUnwriter::unwriteActions(list<Action*>* actions){
	list<Action*>::iterator it;
	list<Action*>* newActions = new list<Action*>();

	for (it = actions->begin(); it != actions->end(); ++it){
		unwriteAction(*it);
	}
}

void IRUnwriter::unwriteAction(Action* action){
		//Write body and scheduler of the action
		unwriteProcedure(action->getScheduler());
		unwriteProcedure(action->getBody());

}

void IRUnwriter::unwriteProcedure(Procedure* procedure){
	Function* function = procedure->getFunction();
	function->eraseFromParent();
}

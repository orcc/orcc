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
	//Remove instance from the scheduler
	Scheduler* scheduler = decoder->getScheduler();
	scheduler->removeInstance(instance);

	//Remove all elements of the instance
	unwriteActionScheduler(instance->getActionScheduler());
	unwriteActions(instance->getActions());
	unwriteInitializes(instance->getInitializes());
	unwriteProcedures(instance->getProcs());
	unwriteVariables(instance->getStateVars());
	unwriteVariables(instance->getParameters());
	unwritePorts(IRConstant::KEY_INPUTS, instance->getInputs());
	unwritePorts(IRConstant::KEY_OUTPUTS, instance->getOutputs());

	return 0;
}

void IRUnwriter::unwriteActionScheduler(ActionScheduler* actionScheduler){
	Function* function = actionScheduler->getSchedulerFunction();
	function->eraseFromParent();
}

void IRUnwriter::unwriteFSM(FSM* fsm){

}

void IRUnwriter::unwriteVariables(map<string, Variable*>* vars){
	map<string, Variable*>::iterator it;

	for (it = vars->begin(); it != vars->end(); ++it){
		Variable* var = it->second;

		//Create and store new variable for the instance
		unwriteVariable(var);
	}
}

void IRUnwriter::unwriteVariable(Variable* var){
	GlobalVariable* GV = var->getGlobalVariable();
	GV->eraseFromParent();
}

void IRUnwriter::unwritePorts(string key, map<string, Port*>* ports){
	map<string, Port*>::iterator it;

	//Iterate though the given ports
	for (it = ports->begin(); it != ports->end(); ++it){
		Port* port = it->second;

		//Remove the port
		unwritePort(key, port);
	}
}

void IRUnwriter::unwriteProcedures(map<string, Procedure*>* procs){
	map<string, Procedure*>::iterator it;
	
	//Creation of procedure must be done in two times because function can call other functions
	for (it = procs->begin(); it != procs->end(); ++it){
		unwriteProcedure(it->second);
	}
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
void IRUnwriter::unwriteInitializes(list<Action*>* actions){
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

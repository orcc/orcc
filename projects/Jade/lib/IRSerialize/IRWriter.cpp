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
@brief Implementation of class IRWriter
@author Jerome Gorin
@file IRWriter.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "Jade/Core/Port.h"
#include "Jade/Jit/LLVMWriter.h"
#include "Jade/Serialize/IRWriter.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/JIT.h"

#include "llvm/Module.h"
//------------------------------

using namespace std;
using namespace llvm;

IRWriter::IRWriter(Actor* actor, Instance* instance){
	this->actor = actor;
	this->instance = instance;
}

bool IRWriter::write(Decoder* decoder){
	writer = new LLVMWriter(instance->getId()+"_", decoder->getModule());
	
	writeInstance();
	// Instanciate actor
/*	map<string, Port*>* inputs = jit->createPorts(instance, actor->getInputs());
	map<string, Port*>* outputs = jit->createPorts(instance, actor->getOutputs());
	map<Variable*, GlobalVariable*>* stateVars = jit->createVariables(instance, actor->getStateVars());
	map<Variable*, GlobalVariable*>* parameters = jit->createVariables(instance, actor->getParameters());
	map<Procedure*, Function*>* procs = jit->createProcedures(instance, actor->getProcs());
	list<Action*>* initializes = jit->createInitializes(instance, actor->getInitializes());
	list<Action*>* actions = jit->createActions(instance, actor->getActions(), inputs, outputs);
	ActionScheduler* actionScheduler = jit->createActionScheduler(instance, actor->getActionScheduler());*/

	return true;
}

void IRWriter::writeInstance(){
	map<string, Port*>* inputs = writePorts(actor->getInputs());
	map<string, Port*>* outputs = writePorts(actor->getOutputs());
	map<string, Variable*>* stateVars = writeVariables(actor->getStateVars());
	map<string, Variable*>* parameters = writeVariables(actor->getParameters());

}

std::map<std::string, Port*>* IRWriter::writePorts(map<string, Port*>* ports){
	map<string, Port*>::iterator it;
	map<string, Port*>* newPorts = new map<string, Port*>();

	//Iterate though the given ports
	for (it = ports->begin(); it != ports->end(); ++it){
		string name = it->first;
		Port* port = it->second;

		//Create and store port for the instance
		Port* newPort = writePort(port);
		newPorts->insert(pair<string, Port*>(name, newPort));
	}

	return newPorts;
}

Port* IRWriter::writePort(Port* port){
	GlobalVariable* portVar = port->getGlobalVariable();
	GlobalVariable* globalVariable = writer->createVariable(portVar);
	
	return new Port(port->getName(), port->getType(), globalVariable);
}

map<string, Variable*>* IRWriter::writeVariables(map<string, Variable*>* vars){
	map<string, Variable*>::iterator it;
	map<string, Variable*>* newVars = new map<string, Variable*>();

	for (it = vars->begin(); it != vars->end(); ++it){
		string name = it->first;
		Variable* var = it->second;

		//Create and store new variable for the instance
		Variable* newVar = writeVariable(var);
		newVars->insert(pair<string, Variable*>(name, newVar));
	}

	return newVars;
}

Variable* IRWriter::writeVariable(Variable* var){
	GlobalVariable* newVar = writer->createVariable(var->getGlobalVariable());

	return new Variable(var->getType(), var->getName(), var->isGlobal(), newVar);
}

list<Action*>* IRWriter::writeInitializes(list<Action*>* actions){
	list<Action*>::iterator it;
	list<Action*>* newActions = new list<Action*>();

	for (it = actions->begin(); it != actions->end(); ++it){
		Action* action = writeAction(instance, *it, NULL, NULL);
		newActions->push_back(action);
	}

	return newActions;
}

Action* IRWriter::createAction(Instance* instance, Action* action, map<string, Port*>* inputs, map<string, Port*>* outputs){
		map<Port*, ConstantInt*>* inputPattern = NULL;
		map<Port*, ConstantInt*>* outputPattern = NULL;

		Procedure* scheduler = action->getScheduler();
		Procedure* body = action->getBody();
	
		Procedure* newScheduler = CreateProcedure(instance, scheduler);
		Procedure* newBody = CreateProcedure(instance, body);
		
		if (inputs != NULL){
			inputPattern = createPattern(action->getInputPattern(), inputs);
		}else{
			inputPattern = new map<Port*, ConstantInt*>();
		}

		if (outputs != NULL){
			outputPattern = createPattern(action->getOutputPattern(), outputs);
		}else{
			outputPattern = new map<Port*, ConstantInt*>();
		}

		return new Action(action->getTag(), inputPattern, outputPattern, newScheduler, newBody);
}


IRWriter::~IRWriter(){
	if(!writer){
		delete writer;
	}
}
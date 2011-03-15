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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>

#include "Jade/Decoder.h"
#include "Jade/Core/Port.h"
#include "Jade/Jit/LLVMWriter.h"
#include "Jade/Serialize/IRWriter.h"
#include "Jade/Scheduler/ActionSchedulerAdder.h"

#include "llvm/Module.h"

#include "IRConstant.h"
//------------------------------

using namespace std;
using namespace llvm;

IRWriter::IRWriter(LLVMContext& C, Decoder* decoder): Context(C){
	this->decoder = decoder;

	//Set the action scheduler adder
	this->actionSchedulerAdder = new ActionSchedulerAdder(Context, decoder);
}

IRWriter::~IRWriter(){
	
	delete actionSchedulerAdder;
	
	if(!writer){
		delete writer;
	}
}

bool IRWriter::write(Instance* instance){
	this->instance = instance;
	this->actor = instance->getActor();

	//Clear stored actions
	actions.clear();
	untaggedActions.clear();

	//Write instance
	writer = new LLVMWriter(instance->getId()+"_", decoder);	
	writeInstance(instance);

	//Adding action scheduler
	actionSchedulerAdder->transform(instance);

	//Add the instance in the scheduler
	Scheduler* scheduler = decoder->getScheduler();
	scheduler->addInstance(instance);

	return true;
}

void IRWriter::writeInstance(Instance* instance){
	
	//Get ports from the instance
	inputs = instance->getInputs();
	outputs = instance->getOutputs();
	
	//Write instance elements
	writePorts(IRConstant::KEY_INPUTS, actor->getInputs());
	writePorts(IRConstant::KEY_OUTPUTS, actor->getOutputs());
	stateVars = writeStateVariables(actor->getStateVars());
	parameters = writeVariables(actor->getParameters());
	procs = writeProcedures(actor->getProcs());
	initializes = writeInitializes(actor->getInitializes());
	list<Action*>* actions = writeActions(actor->getActions());
	actionScheduler = writeActionScheduler(actor->getActionScheduler());

	//Set properties of the instance
	instance->setActions(actions);
	instance->setStateVars(stateVars);
	instance->setParameters(parameters);
	instance->setProcs(procs);
	instance->setInitializes(initializes);
	instance->setActionScheduler(actionScheduler);

	//Resolve paramaters of this instance
	instance->solveParameters();
}

std::map<std::string, Port*>* IRWriter::writePorts(string key, map<string, Port*>* ports){
	map<string, Port*>::iterator it;
	map<string, Port*>* newPorts = new map<string, Port*>();

	//Iterate though the given ports
	for (it = ports->begin(); it != ports->end(); ++it){
		string name = it->first;
		Port* port = it->second;

		//Create and store port for the instance
		writePort(key, port);
	}

	return newPorts;
}

void IRWriter::writePort(string key, Port* port){
	string name = port->getName();
	GlobalVariable* portVar = port->getGlobalVariable();
	GlobalVariable* globalVariable = writer->createVariable(portVar);
	Port* instPort = NULL;

	if (key == IRConstant::KEY_INPUTS){
		instPort = instance->getInput(name);
	}else{
		instPort = instance->getOutput(name);
	}

	//Port not found
	if (instPort == NULL){
		cerr << "Port " << name << " as not been found in instance " << instance->getId();
		exit(0);
	}

	//Set global variable to the instance port
	instPort->setGlobalVariable(globalVariable);
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

map<string, StateVar*>* IRWriter::writeStateVariables(map<string, StateVar*>* vars){
	map<string, StateVar*>::iterator it;
	map<string, StateVar*>* newVars = new map<string, StateVar*>();

	for (it = vars->begin(); it != vars->end(); ++it){
		string name = it->first;
		StateVar* var = it->second;

		//Create and store new variable for the instance
		StateVar* newVar = writeStateVariable(var);
		newVars->insert(pair<string, StateVar*>(name, newVar));
	}

	return newVars;
}

StateVar* IRWriter::writeStateVariable(StateVar* var){
	GlobalVariable* newVar = writer->createVariable(var->getGlobalVariable());

	return new StateVar(var->getType(), var->getName(), var->isAssignable(), newVar, var->getInitialValue());
}

Variable* IRWriter::writeVariable(Variable* var){
	GlobalVariable* newVar = writer->createVariable(var->getGlobalVariable());

	return new Variable(var->getType(), var->getName(), var->isGlobal(), var->isAssignable(), newVar);
}

list<Action*>* IRWriter::writeInitializes(list<Action*>* actions){
	list<Action*>::iterator it;
	list<Action*>* newActions = new list<Action*>();

	for (it = actions->begin(); it != actions->end(); ++it){
		Action* action = writeAction(*it);
		newActions->push_back(action);
	}

	return newActions;
}

Action* IRWriter::writeAction(Action* action){
		//Write body and scheduler of the action
		Procedure* newScheduler = writeProcedure(action->getScheduler());
		Procedure* newBody = writeProcedure(action->getBody());

		//Write patterns
		Pattern* inputPattern = writePattern(action->getInputPattern(), inputs);
		Pattern* outputPattern = writePattern(action->getOutputPattern(), outputs);

		//Create the action
		return new Action(action->getTag(), inputPattern, outputPattern, newScheduler, newBody);
}

Procedure* IRWriter::writeProcedure(Procedure* procedure){
	Function* function = writer->createFunction((Function*)procedure->getFunction());
	
	return new Procedure(procedure->getName(), procedure->getExternal(), function);
}

Pattern* IRWriter::writePattern(Pattern* pattern, map<string, Port*>* ports){
/*	map<Port*, ConstantInt*>::iterator itPattern;
	map<string, Port*>::iterator itPort;
	map<Port*, ConstantInt*>* newPattern = new map<Port*, ConstantInt*>();

	for (itPattern = pattern->begin(); itPattern != pattern->end(); itPattern++) {
		itPort = ports->find(itPattern->first->getName());
		newPattern->insert(pair<Port*, ConstantInt*>(itPort->second, itPattern->second));
	}

	return newPattern;*/
	return NULL;
}

map<string, Procedure*>* IRWriter::writeProcedures(map<string, Procedure*>* procs){
	map<string, Procedure*>::iterator it;
	map<string, Procedure*>* newProcs = new map<string, Procedure*>();

	
	//Creation of procedure must be done in two times because function can call other functions
	for (it = procs->begin(); it != procs->end(); ++it){
		Procedure* proc = (*it).second;
		Function* newFunction = NULL;
		
		//Write declaration of the function
		if (proc->isExternal()){
			newFunction = writer->addFunctionProtosExternal(proc->getFunction());
		}else{
			newFunction = writer->addFunctionProtosInternal(proc->getFunction());
		}
		
		//Create a new procedure
		Procedure* newProc = new Procedure(proc->getName(), proc->getExternal(), newFunction);
		newProcs->insert(pair<string, Procedure*>(proc->getName(), newProc));
	}

	//Link body of the procedure
	for (it = procs->begin(); it != procs->end(); ++it){
		Procedure* proc = (*it).second;
		writer->linkProcedureBody(proc->getFunction());
	}
	
	return newProcs;
}

ActionScheduler* IRWriter::writeActionScheduler(ActionScheduler* actionScheduler){
	FSM* fsm = NULL;
	Function* initializeFunction = NULL;
	list<Action*>* instancedActions = new list<Action*>();

	//Get actions of action scheduler
	list<Action*>::iterator it;
	map<string, Action*>::iterator itActionsMap;
	list<Action*>* actions = actionScheduler->getActions();

	for (it = actions->begin(); it != actions->end(); it++){
		Action* action = getAction(*it);
		instancedActions->push_back(action);
	}

	//Create FSM if present
	if (actionScheduler->hasFsm()){
		fsm = writeFSM(actionScheduler->getFsm());
	}
	
	return new ActionScheduler(instancedActions, fsm);
}

FSM* IRWriter::writeFSM(FSM* fsm){
	list<llvm::Function*>::iterator it;
	
	FSM* newFSM = new FSM();
	
	//Copy states of the source fsm
	std::map<std::string, FSM::State*>::iterator itState;
	std::map<std::string, FSM::State*>* states = fsm->getStates();

	for (itState = states->begin(); itState != states->end(); itState++){
		newFSM->addState(itState->first);
	}

	//Copy transitions of the source fsm
	map<string, Action*>::iterator itActionsMap;
	std::map<std::string, FSM::Transition*>::iterator itTransition;
	std::map<std::string, FSM::Transition*>* transitions = fsm->getTransitions();
	for (itTransition = transitions->begin(); itTransition != transitions->end(); itTransition++){
		FSM::Transition* transition = itTransition->second;
		FSM::State* sourceState = transition->getSourceState();
		list<FSM::NextStateInfo*>::iterator itNextStateInfo;
		list<FSM::NextStateInfo*>* nextStateInfos = transition->getNextStateInfo();

		for (itNextStateInfo = nextStateInfos->begin(); itNextStateInfo != nextStateInfos->end(); itNextStateInfo++){
			Action* actionState = NULL;
			FSM::State* targetState = (*itNextStateInfo)->getTargetState();
			Action* targetAction = (*itNextStateInfo)->getAction();

			newFSM->addTransition(sourceState->getName(), targetState->getName(), getAction(targetAction));
		}
	}
	
	//Set initiale state of the FSM
	newFSM->setInitialState(fsm->getInitialState()->getName());

	return newFSM;
}

list<Action*>* IRWriter::writeActions(list<Action*>* actions){
	list<Action*>::iterator it;
	list<Action*>* newActions = new list<Action*>();

	for (it = actions->begin(); it != actions->end(); ++it){
		//Write action
		Action* action = writeAction(*it);
		newActions->push_back(action);

		//Save it for a later use	
		putAction(action->getTag(), action);
	}

	return newActions;
}

void IRWriter::putAction(ActionTag* tag, Action* action){
	if (tag->isEmpty()){
		untaggedActions.push_back(action);
	} else {
		actions.insert(pair<std::string, Action*>(tag->getIdentifier(), action));
	}
}

Action* IRWriter::getAction(Action* action) {
	ActionTag* actionTag = action->getTag();
	map<string, Action*>::iterator it;

	if (actionTag->isEmpty()){
		// removes the first untagged action found
		Action* action = untaggedActions.front();
		untaggedActions.remove(action);
		
		return action;
	}
	
	it = actions.find(actionTag->getIdentifier());
	
	return it->second;
}
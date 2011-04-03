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
#include "Jade/Core/MoC/CSDFMoC.h"
#include "Jade/Core/MoC/SDFMoC.h"
#include "Jade/Core/MoC/QSDFMoC.h"
#include "Jade/Core/MoC/DPNMoC.h"
#include "Jade/Core/MoC/KPNMoC.h"
#include "Jade/Jit/LLVMWriter.h"
#include "Jade/Serialize/IRWriter.h"

#include "llvm/Module.h"

#include "IRConstant.h"
//------------------------------

using namespace std;
using namespace llvm;

IRWriter::IRWriter(LLVMContext& C, Decoder* decoder): Context(C){
	this->decoder = decoder;
}

IRWriter::~IRWriter(){
	if(!writer){
		delete writer;
	}
}

bool IRWriter::write(Instance* instance){
	// Define instance kind
	if (instance->isSuperInstance()){
		writeSuperInterface((SuperInstance*)instance);
	}else{
		writeInstance(instance);
	}

	return true;
}

bool IRWriter::writeSuperInterface(SuperInstance* superInstance){
	map<Instance*, int>::iterator it;
	map<Instance*, int>* subInstances = superInstance->getInstances();

	for (it = subInstances->begin(); it != subInstances->end(); it++){
		write(it->first);
	}
	
	return true;
}

void IRWriter::writeInstance(Instance* instance){
	this->instance = instance;
	this->actor = instance->getActor();

	//Clear stored actions
	actions.clear();
	untaggedActions.clear();
	
	//LLVM writer
	writer = new LLVMWriter(instance->getId()+"_", decoder);	

	// Get instance ports
	inputs = instance->getInputs();
	outputs = instance->getOutputs();

	//Write all instance property
	writePortPtrs(actor->getInputs(), inputs);
	writePortPtrs(actor->getOutputs(), outputs);


	stateVars = writeStateVariables(actor->getStateVars());
	parameters = writeVariables(actor->getParameters());
	procs = writeProcedures(actor->getProcs());
	initializes = writeInitializes(actor->getInitializes());
	list<Action*>* actions = writeActions(actor->getActions());
	actionScheduler = writeActionScheduler(actor->getActionScheduler());
	MoC* moc = writeMoC(actor->getMoC());

	//Set properties of the instance
	instance->setActions(actions);
	instance->setStateVars(stateVars);
	instance->setParameters(parameters);
	instance->setProcs(procs);
	instance->setInitializes(initializes);
	instance->setActionScheduler(actionScheduler);
	instance->setMoC(moc);

	//Resolve paramaters of this instance
	instance->solveParameters();
}

void IRWriter::writePortPtrs(map<string, Port*>* srcPorts, map<string, Port*>* dstPorts){
	map<string, Port*>::iterator itSrc;
	map<string, Port*>::iterator itDst;

	for (itSrc = srcPorts->begin(); itSrc != srcPorts->end(); itSrc++){
		Port* src = itSrc->second;
		
		// Find destination port
		itDst = dstPorts->find(itSrc->first);
		Port* dst;
		if (itDst == dstPorts->end()){
			dst = new Port(src->getName(), src->getType());
			dstPorts->insert(pair<string, Port*>(itSrc->first, dst));
		}else{
			dst = itDst->second;
		}

		Variable* srcVar = src->getPtrVar();
		GlobalVariable* globalVar = writer->createVariable(srcVar->getGlobalVariable());
		Variable* newVar = new Variable(srcVar->getType(), srcVar->getName(), srcVar->isGlobal(), srcVar->isAssignable(), globalVar);
		dst->setPtrVar(newVar);
	}

}

void IRWriter::writeInternalPorts(map<Port*, Port*>* internalPorts){
	map<Port*, Port*>::iterator it;

	for (it = internalPorts->begin(); it != internalPorts->end(); it++){
		Port* port = it->second;

		if (port->getPtrVar() == NULL){
			Type* type = port->getType();
			string name = port->getName()+"_ptr";
			
			// Create a variable corresponding to this internal port pointer
			GlobalVariable* gvPtr = new GlobalVariable(*decoder->getModule(), type->getPointerTo(), false, GlobalValue::InternalLinkage, NULL, name);
			Variable* varPtr = new Variable(type, name, true, true, gvPtr);
			port->setPtrVar(varPtr);
		}
	}
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
		return new Action(action->getTag(), inputPattern, outputPattern, newScheduler, newBody, instance);
}

Procedure* IRWriter::writeProcedure(Procedure* procedure){
	Function* function = writer->createFunction((Function*)procedure->getFunction());
	
	return new Procedure(procedure->getName(), procedure->getExternal(), function);
}

Pattern* IRWriter::writePattern(Pattern* pattern, map<string, Port*>* ports){
	map<string, Port*>::iterator itPort;
	Pattern* newPattern = new Pattern();

	//Add number of tokens for each ports
	map<Port*, ConstantInt*>::iterator itTokens;
	map<Port*, ConstantInt*>* numTokens = pattern->getNumTokensMap();

	for (itTokens = numTokens->begin(); itTokens != numTokens->end(); itTokens++) {
		Port* src = itTokens->first;
		itPort = ports->find(src->getName());

		// Find destination port
		Port* dst;
		itPort = ports->find(src->getName());
		if (itPort == ports->end()){
			dst = new Port(src->getName(), src->getType());
			ports->insert(pair<string, Port*>(src->getName(), dst));
		}else{
			dst = itPort->second;
		}

		newPattern->setNumTokens(dst, itTokens->second);
	}

	//Add variable map for each ports
	map<Port*, Variable*>::iterator itVar;
	map<Port*, Variable*>* varMap = pattern->getVariableMap();

	for (itVar = varMap->begin(); itVar != varMap->end(); itVar++) {
		Variable* var = itVar->second;

		//Get corresponding port in instance
		Port* src = itVar->first;
		itPort = ports->find(src->getName());
		
		// Find destination port
		Port* dst;
		itPort = ports->find(src->getName());
		if (itPort == ports->end()){
			dst = new Port(src->getName(), src->getType());
			ports->insert(pair<string, Port*>(src->getName(), dst));
		}else{
			dst = itPort->second;
		}

		// Add variable to pattern
		newPattern->setVariable(dst, dst->getPtrVar());
	}

	//Add peek map for each ports
	map<Port*, Variable*>* peekMap = pattern->getPeekedMap();

	for (itVar = peekMap->begin(); itVar != peekMap->end(); itVar++) {
		Variable* var = itVar->second;

		//Get corresponding port in instance
		Port* src = itVar->first;
		itPort = ports->find(src->getName());

			// Find destination port
		Port* dst;
		itPort = ports->find(src->getName());
		if (itPort == ports->end()){
			dst = new Port(src->getName(), src->getType());
			ports->insert(pair<string, Port*>(src->getName(), dst));
		}else{
			dst = itPort->second;
		}

		// Add variable to pattern
		newPattern->setPeeked(dst, dst->getPtrVar());
	}

	return newPattern;
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
		putAction(*it, action->getTag(), action);
	}

	return newActions;
}

MoC* IRWriter::writeMoC(MoC* moc){
	if (moc->isCSDF()){
		return writeCSDFMoC((CSDFMoC*)moc);
	}else if (moc->isQuasiStatic()){
		return writeQSDFMoC((QSDFMoC*)moc);
	}else if (moc->isKPN()){
		return new KPNMoC();
	}else if (moc->isDPN()){
		return new DPNMoC();
	}

	return NULL;
}

QSDFMoC* IRWriter::writeQSDFMoC(QSDFMoC* csdfMoC){
	QSDFMoC* newqsdfMoC = new QSDFMoC();
	
	// Copy configurations of the QSDFMoC
	list<pair<Action*, CSDFMoC*>>::iterator it;
	list<pair<Action*, CSDFMoC*>>* configurations = csdfMoC->getConfigurations();

	for( it = configurations->begin(); it != configurations->end(); it++){
		pair<Action*, CSDFMoC*> newConfiguration = writeConfiguration(it->first, it->second);
		newqsdfMoC->addConfiguration(newConfiguration.first, newConfiguration.second);
	}
	
	return newqsdfMoC;
}

pair<Action*, CSDFMoC*> IRWriter::writeConfiguration(Action* action, CSDFMoC* csdfMoC){
	Action* newAction = getAction(action);
	CSDFMoC* newcsdf = writeCSDFMoC(csdfMoC);

	return pair<Action*, CSDFMoC*>(newAction, newcsdf);
}

CSDFMoC* IRWriter::writeCSDFMoC(CSDFMoC* csdfMoC){
	CSDFMoC* newCsdfMoC;
		
	// Dupplicat moc
	if (csdfMoC->isSDF()){
		newCsdfMoC = new SDFMoC();
	}else{
		newCsdfMoC = new CSDFMoC();
		
		int nbPhases = csdfMoC->getNumberOfPhases();
		newCsdfMoC->setNumberOfPhases(nbPhases);
	}
		
	// Dupplicate patterns
	Pattern* inputPattern = writePattern(csdfMoC->getInputPattern(), inputs);
	Pattern* outputPattern = writePattern(csdfMoC->getOutputPattern(), outputs);

	newCsdfMoC->setInputPattern(inputPattern);
	newCsdfMoC->setOutputPattern(outputPattern);

	// Dupplicate actions
	list<Action*>::iterator it;
	list<Action*>* actions = csdfMoC->getActions();
		
	for (it = actions->begin(); it != actions->end(); it++){
		Action* newAction = getAction(*it);
		newCsdfMoC->addAction(newAction);
	}

	return newCsdfMoC;
}

void IRWriter::putAction(Action* actionSrc, ActionTag* tag, Action* action){
	if (tag->isEmpty()){
		untaggedActions.insert(pair<Action*, Action*>(actionSrc, action));
	} else {
		actions.insert(pair<std::string, Action*>(tag->getIdentifier(), action));
	}
}

Action* IRWriter::getAction(Action* action) {
	ActionTag* actionTag = action->getTag();

	if (actionTag->isEmpty()){
		map<Action*, Action*>::iterator it;
		it = untaggedActions.find(action);
		return it->second;
	}

	map<string, Action*>::iterator it;
	it = actions.find(actionTag->getIdentifier());
	
	return it->second;
}
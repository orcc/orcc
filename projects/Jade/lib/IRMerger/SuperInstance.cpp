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
@brief Implementation of class SuperInstance
@author Jerome Gorin
@file SuperInstance.cpp
@version 1.0
@date 24/12/2010
*/

//------------------------------
#include <map>
#include <list>

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/GlobalVariable.h"

#include "Jade/Core/Moc/SDFMoC.h"
#include "Jade/Merger/SuperInstance.h"
//------------------------------

using namespace std;
using namespace llvm;

SuperInstance::SuperInstance(LLVMContext& C, std::string id, Instance* srcInstance, int srcFactor, Instance* dstInstance, int dstFactor, map<Port*, Port*>* internalPorts) : Instance(id, NULL), Context(C){
	this->srcInstance = srcInstance;
	this->dstInstance = dstInstance;
	this->srcFactor = srcFactor;
	this->dstFactor = dstFactor;
	this->actor = createCompositeActor(internalPorts);
	this->internalPorts = internalPorts;
	this->moc = NULL;
	this->actionScheduler = new ActionScheduler(new list<Action*>(), NULL);

	analyzeInstance(srcInstance, srcFactor);
	analyzeInstance(dstInstance, dstFactor);

}

void SuperInstance::analyzeInstance(Instance* instance, int factor){
	// Set instances of the super instance
	if (instance->isSuperInstance()){
		map<Instance*, int>::iterator it;
		SuperInstance* superInstance = (SuperInstance*) instance;
		map<Instance*, int>* subInstances = superInstance->getInstances();

		for (it = subInstances->begin(); it != subInstances->end(); it++){
			instances.insert(pair<Instance*, int>(it->first, it->second * factor));
		}

	}else{
		instances.insert(pair<Instance*, int>(instance, factor));
	}
}

map<Port*, Port*>* SuperInstance::getInternalConnections(){
	map<Port*, Port*>::iterator it;
	
	//Get internal connection of source Instance
	if (srcInstance->isSuperInstance()){
		SuperInstance* src = (SuperInstance*)srcInstance;
		map<Port*, Port*>* srcConns = src->getInternalConnections();

		for (it = srcConns->begin(); it != srcConns->end(); it++){
			internalPorts->insert(pair<Port*, Port*>(it->first, it->second));
		}
	}

	//Get internal connection of destination Instance
	if (dstInstance->isSuperInstance()){
		SuperInstance* dst = (SuperInstance*)dstInstance;
		//Get internal connection of subInstance
		map<Port*, Port*>* dstConns = dst->getInternalConnections();

		for (it = dstConns->begin(); it != dstConns->end(); it++){
			internalPorts->insert(pair<Port*, Port*>(it->first, it->second));
		}
	}

	return internalPorts;
}

MoC* SuperInstance::getMoC(){
	if (moc == NULL){
		CSDFMoC* srcMoC = (CSDFMoC*)srcInstance->getMoC();
		CSDFMoC* dstMoC = (CSDFMoC*)dstInstance->getMoC();
		moc = createMoC(srcMoC, srcFactor, dstMoC, dstFactor);
	}

	return moc;
}


Actor* SuperInstance::createCompositeActor(map<Port*, Port*>* internalPorts){
	// Get actors of the two instances
	Actor* srcActor = srcInstance->getActor();
	Actor* dstActor = dstInstance->getActor();
	
	
	// Prepare actor properties
	map<string, StateVar*>* stateVars = new map<string, StateVar*>();
	map<string, Variable*>* parameters = new map<string, Variable*>();
	map<string, Procedure*>* procedures = new map<string, Procedure*>();
	list<Action*>* initializes = new list<Action*>();
	list<Action*>* actions = new list<Action*>();
	

	// Set of input and output of actors
	map<Port*, Port*>::iterator it;
	set<Port*> in;
	set<Port*> out;
	for (it = internalPorts->begin(); it != internalPorts->end(); it++){
		Port* output = it->first;
		Port* input = it->second;

		// Set input and output of actor as internal
		out.insert(srcActor->getOutput(output->getName()));
		in.insert(dstActor->getInput(input->getName()));
	}

	// Create a composite moc
	CSDFMoC* srcMoc = (CSDFMoC*)srcActor->getMoC();
	CSDFMoC* dstMoc = (CSDFMoC*)dstActor->getMoC();
	CSDFMoC* moc = createMoC(srcMoc, srcFactor, dstMoc, dstFactor, &in, &out);

	// Set ports ports of the actor
	Pattern* inputMoc = moc->getInputPattern();
	Pattern* outputMoc = moc->getOutputPattern();
	map<string, Port*>* inputs = createPorts(inputMoc->getPorts());
	map<string, Port*>* outputs = createPorts(outputMoc->getPorts());
	
	Actor* actorComposite = new Actor(id, NULL, "", inputs,  outputs,
		stateVars, parameters, procedures, initializes, actions, NULL, moc);


	return actorComposite;
}

map<string, Port*>* SuperInstance::createPorts(set<Port*>* portSet){
	set<Port*>::iterator it;
	map<string, Port*>* ports = new map<string, Port*>();
	
	for (it = portSet->begin(); it != portSet->end(); it++){
		ports->insert(pair<string, Port*>((*it)->getName(), (*it)));
	}
	
	return ports;
}

StateVar* SuperInstance::getInternalVar(Port* port){
	map<Port*, StateVar*>::iterator it;

	it = internalVars->find(port);

	if (it == internalVars->end()){
		return NULL;
	}

	return it->second;
};

CSDFMoC* SuperInstance::createMoC(CSDFMoC* srcMoc, int srcFactor, CSDFMoC* dstMoc, int dstFactor, set<Port*>* in, set<Port*>* out){
	CSDFMoC* moc = new CSDFMoC();

	// Merges patterns of actors
	Pattern* inputPattern = createPattern(srcMoc->getInputPattern(), srcFactor, dstMoc->getInputPattern(), dstFactor, in);
	Pattern* outputPattern = createPattern(srcMoc->getOutputPattern(), srcFactor, dstMoc->getOutputPattern(), dstFactor, out);

	// Add to moc
	moc->setInputPattern(inputPattern);
	moc->setOutputPattern(outputPattern);

	//Add actions
	for (int i = 0; i <srcFactor; i++){
		moc->addActions(srcMoc->getActions());
	}

	for (int i = 0; i <dstFactor; i++){
		moc->addActions(dstMoc->getActions());
	}

	return moc;
}

Pattern* SuperInstance::createPattern(Pattern* srcPattern,  int srcFactor, Pattern* dstPattern, int dstFactor, set<Port*>* ports){
	Pattern* newPattern = new Pattern();
	
	// Add source pattern
	map<Port*, Port*>::iterator itPort;
	map<Port*, ConstantInt*>::iterator it;
	map<Port*,ConstantInt*>* srcProds = srcPattern->getNumTokensMap();
	
	for(it = srcProds->begin(); it != srcProds->end(); it++){
		Port* port = it->first;

		if (ports != NULL){
			if (ports->find(port) != ports->end()){
				// This port is internal
				continue;
			}
		}

		// Update token production
		int val = it->second->getLimitedValue();
		
		ConstantInt* newVal = ConstantInt::get(Type::getInt32Ty(Context), val * srcFactor);
		newPattern->setNumTokens(port, newVal);


		// Update variables
		Variable* variable = srcPattern->getVariable(port);
		newPattern->setVariable(port, variable);
	}

	// Add source pattern
	map<Port*,ConstantInt*>* dstProds = dstPattern->getNumTokensMap();
	for(it = dstProds->begin(); it != dstProds->end(); it++){
		Port* port = it->first;
		
		if (ports != NULL){
			if (ports->find(port) != ports->end()){
				// This port is internal
				continue;
			}
		}

		// Update token production
		int val = it->second->getLimitedValue();
		
		ConstantInt* newVal = ConstantInt::get(Type::getInt32Ty(Context), val * dstFactor);
		newPattern->setNumTokens(port, newVal);

		// Update variables
		Variable* variable = dstPattern->getVariable(port);
		newPattern->setVariable(port, variable);
	}


	return newPattern;
}
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
@brief Implementation of class Decoder
@author Jerome Gorin
@file Decoder.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <list>
#include <iostream>
#include <fstream>

#include "llvm/LLVMContext.h"

#include "Instanciator.h"

#include "Jade/JIT.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Core/InstancedActor.h"
#include "Jade/Fifo/AbstractFifo.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/StateVariable.h"
#include "Jade/Core/Network.h"
#include "Jade/Graph/HDAGGraph.h"

#include "BroadcastAdder.h"
#include "SolveParameters.h"
//------------------------------

using namespace llvm;
using namespace std;

Decoder::Decoder(llvm::LLVMContext& C, JIT* jit, Network* network, std::map<std::string, Actor*>* actors, AbstractFifo* fifo): Context(C){
	//Set property of the decoder
	this->jit = jit;
	this->network = network;
	this->fifo = fifo;
	this->actors = actors;
	this->instances = network->getInstances();

	//Create a new module that contains the current decoder
	module = new Module("decoder", C);

	jit->setDecoder(this);
	
	// Instanciating decoder
	instanciate();

	// Setting connections of the decoder
	fifo->setConnections(this);
}

Decoder::~Decoder (){

}

int Decoder::instanciate(){
	// Add Fifo function and fifo type into the decoder
	fifo->addFifoHeader(this);
	
	// Create instance in the decoder
	createActorInstances();
	
	// Instanciate the network
	Instanciator instanciator(network);

	SolveParameters solver(this);
	solver.transform();

	// Adding broadcast 
	BroadcastAdder broadAdder(Context, this);
	broadAdder.transform();
	
	return 0;
}

void Decoder::createActorInstances(){
	// Initialize all instances into the decoder
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = network->getInstances();
	

	for(it = instances->begin(); it != instances->end(); ++it){
		map<std::string, Actor*>::iterator itActor;

		Instance* instance = (*it).second;
		itActor = actors->find(instance->getClasz());
		instance->setActor(itActor->second);

		InstancedActor* instancedActor = createInstance(instance);
		instance->setInstancedActor(instancedActor);
		instancedActors.insert(pair<Instance*, InstancedActor*>(instance, instancedActor));

	}
}

Actor* Decoder::getActor(std::string name){
	map<string, Actor*>::iterator it;

	it = actors->find(name);

	if(it != actors->end()){
		return it->second;
	}

	return NULL;
}


InstancedActor* Decoder::createInstance(Instance* instance){
	Actor* actor = instance->getActor();

	//Initialize jit
	jit->setNewInstance();

	// Instanciate actor
	map<string, Port*>* inputs = jit->createPorts(instance, actor->getInputs());
	map<string, Port*>* outputs = jit->createPorts(instance, actor->getOutputs());
	map<Variable*, GlobalVariable*>* stateVars = jit->createVariables(instance, actor->getStateVars());
	map<Variable*, GlobalVariable*>* parameters = jit->createVariables(instance, actor->getParameters());
	map<Procedure*, Function*>* procs = jit->createProcedures(instance, actor->getProcs());
	list<Action*>* initializes = jit->createInitializes(instance, actor->getInitializes());
	list<Action*>* actions = jit->createActions(instance, actor->getActions(), inputs, outputs);
	ActionScheduler* actionScheduler = jit->createActionScheduler(instance, actor->getActionScheduler());

	return new InstancedActor(this, instance, inputs, outputs, stateVars, parameters, procs, actions, initializes, actionScheduler);
}


Instance* Decoder::getInstance(std::string name){
	map<string, Instance*>::iterator it;

	it = instances->find(name);

	if (it == instances->end()){
		return NULL;
	}

	return it->second;
}
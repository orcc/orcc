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
@brief Implementation of class Instantiator
@author Jerome Gorin
@file Instantiator.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <string>
#include <stdio.h>

#include "Instantiator.h"

#include "llvm/DerivedTypes.h"

#include "Jade/Core/Actor.h"
#include "Jade/Core/Port.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Vertex.h"
#include "Jade/Core/Network.h"
#include "Jade/Core/Connection.h"

//------------------------------


using namespace std;
using namespace llvm;

Instantiator::Instantiator(Network* network, map<string, Actor*>* actors){
	this->actors = actors;
	this->network = network;
	this->graph = network->getGraph();
	updateInstances();
}


void Instantiator::updateInstances(){
	//Update instances using actors
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = network->getInstances();
		
	for (it = instances->begin(); it != instances->end(); it++){
		updateInstance(it->second);
	}

	//Update connections using graph
	int edges = graph->getNbEdges();
	for (int i = 0; i < edges; i++){
		updateConnection((Connection*)graph->getEdge(i));
	}
	
}

void Instantiator::updateInstance(Instance* instance){
	map<string, Actor*>::iterator it;

	//Look for the actor using instance clasz
	it = actors->find(instance->getClasz());

	//Actor does not exist
	if (it == actors->end()){
		fprintf(stderr,"An instance refers to non-existent actor: actor %s for instance %s", instance->getClasz(), instance->getId());
		exit(0);
	}

	//Set instance to its corresponding actor
	instance->setActor(it->second);
}

void Instantiator::updateConnection(Connection* connection){
	Type* srcPortType;
	string sourceString;
	Type* dstPortType;
	string targetString;
	
	// Get vertex of the connection
	Vertex* srcVertex = (Vertex*)connection->getSource();
	Vertex* dstVertex = (Vertex*)connection->getSink();

	// Update source
	if(srcVertex->isInstance()){
		// Get instance of the connection
		Instance* source = srcVertex->getInstance();
		
		// Get port of the connection 
		Port* srcPortInst = connection->getSourcePort();

		// Get same port from the actor
		Actor* actor = source->getActor();
		Port* srcPort = actor->getOutput(srcPortInst->getName());
		sourceString = srcPort->getName();
		srcPortType = srcPort->getType();

		if (srcPort == NULL){
			fprintf(stderr,"A Connection refers to non-existent source port: %s of instance %s", srcPortInst->getName(), source->getId());
			exit(0);
		}

		// Set port information
		srcPortInst->setType(srcPortType);
		source->setAsOutput(srcPortInst);
	}

	// Update target 
	if(dstVertex->isInstance()){
		// Get instance of the connection 
		Instance* target = dstVertex->getInstance();
		
		// Get port of the connection 
		Port* dstPortInst = connection->getDestinationPort();

		// Get same port from the actor 
		Actor* actor = target->getActor();
		Port* dstPort = actor->getInput(dstPortInst->getName());
		targetString = dstPort->getName();
		dstPortType = dstPort->getType();

		if (dstPort == NULL){
			fprintf(stderr,"A Connection refers to non-existent destination port: %s of instance %s", dstPort->getName(), target->getId());
			exit(0);
		}

		// Bound GlobalVariable to port from instance
		dstPortInst->setType(dstPortType);
		target->setAsInput(dstPortInst);
	}

	// check port types match
	IntegerType* srcType = cast<IntegerType>(srcPortType);
	IntegerType* dstType = cast<IntegerType>(dstPortType);
	if (srcType->getBitWidth() != dstType->getBitWidth()) {
		fprintf(stderr, "Type error: size of port %s is %d and different from port %s of size %d \n", sourceString.c_str(),srcType->getBitWidth(), targetString.c_str(), dstType->getBitWidth());
		exit(0);
	}
	
	connection->setType(srcType);
	
}
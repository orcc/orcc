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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>
#include <string>
#include <stdio.h>

#include "Instantiator.h"

#include "llvm/DerivedTypes.h"

#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Port.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Network.h"
//------------------------------


using namespace std;
using namespace llvm;

Instantiator::Instantiator(Configuration* configuration){
	this->actors = configuration->getActors();
	this->configuration = configuration;
	Network* network = configuration->getNetwork();
	this->graph = network->getGraph();
	updateInstances();
}


void Instantiator::updateInstances(){
	//Update instances using actors
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = configuration->getInstances();
		
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
		cerr << "An instance refers to non-existent actor: actor "<< instance->getClasz() << " for instance " << instance->getId();
		exit(0);
	}

	//Set instance to its corresponding actor
	instance->setActor(it->second);
}

void Instantiator::updateConnection(Connection* connection){
	IntegerType* srcPortType;
	string sourceString;
	IntegerType* dstPortType;
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
			cerr << "A Connection refers to non-existent source port: " << srcPortInst->getName() << "of instance " << source->getId();
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
			cerr << "A Connection refers to non-existent destination port: " << dstPort->getName() << " of instance " << target->getId();
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
		cerr << "Type error: size of port " << sourceString.c_str() << " is " << srcType->getBitWidth() << " and different from port " << targetString.c_str() << " of size " << dstType->getBitWidth() << ".\n";
		exit(0);
	}
	
}
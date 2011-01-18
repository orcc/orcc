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
@brief Implementation of class BroadcastAdder
@author Jerome Gorin
@file BroadcastAdder.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <map>
#include <sstream>

#include "Jade/Actor/BroadcastActor.h"
#include "Jade/Core/Port.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Network.h"
#include "Jade/Transform/BroadcastAdder.h"
//------------------------------

using namespace std;

BroadcastAdder::BroadcastAdder(llvm::LLVMContext& C, Configuration* configuration) : Context(C){
	this->fifo = configuration->getConnector();
	this->configuration = configuration;
	Network* network = configuration->getNetwork();
	this->graph = network->getGraph();
}

BroadcastAdder::~BroadcastAdder (){

}

struct ltstr
{
  bool operator()(Port* s1, Port* s2) const
  {
	  return s1->getName().compare(s2->getName()) < 0;
  }
};


//maximum number of edge that can be connected to a vertex
#define MAX_EDGE  500

void BroadcastAdder::examineVertex(Vertex* vertex){
	Connection* connections[MAX_EDGE];

	int nbEdges = graph->getOutputEdges(vertex, (HDAGEdge**)connections);
	map<Port*, list<Connection*>*, ltstr>* outMap = new map<Port*, list<Connection*>*, ltstr>();
	map<Port*, list<Connection*>*, ltstr>::iterator it;
	list<Connection*>* outList = NULL;

	for (int i = 0 ; i < nbEdges; i++){
		Connection* connection = connections[i];
		Port* src = connection->getSourcePort();

		it = outMap->find(src);

		if (it == outMap->end()){
			outList = new list<Connection*>();
			outMap->insert(pair<Port*, list<Connection*>*>(src, outList));
		}else {
			outList = (*it).second;
		}

		outList->push_back(connection);
	}

	examineConnections(vertex,connections, nbEdges, outMap);

}

void BroadcastAdder::examineConnections(Vertex* vertex, Connection** connections, int nbEdges,
						map<Port*, list<Connection*>*, ltstr>* outMap){

	Instance* instance = vertex->getInstance();
	map<Port*, list<Connection*>*, ltstr>::iterator it;

	for (int i = 0; i < nbEdges; i++){
		Connection* connection = connections[i];
		Port* srcPort = connection->getSourcePort();
		it = outMap->find(srcPort);
		if (it != outMap->end()){
			list<Connection*>* outList = (*it).second;

			int numOuputs = outList->size();
			if (numOuputs > 1){
				
				//Create a new actor for this broadcast
				string name = "broadcast_"+ instance->getId()+"_"+ srcPort->getName();
				BroadcastActor* actorBCast = new BroadcastActor(Context, name, numOuputs, srcPort->getType(), fifo);
				
				//Create an instance for the broadcast
				Instance* newInstance = new Instance(name, actorBCast);
				
				//Insert broadcast in configuration
				configuration->insertSpecific(actorBCast);
				
				//Set a new vertex in the graph
				Vertex* vertextBCast = new Vertex(newInstance);
				graph->addVertex(vertextBCast);
				
				//Connect the broadcast vertex in the graph
				createIncomingConnection(connections[i], vertex, vertextBCast, newInstance);
				createOutgoingConnections(vertextBCast, outList, newInstance);
			}
			outMap->erase(it);
		}
	}
}

void BroadcastAdder::createIncomingConnection(Connection* connection, Vertex* vertex, Vertex* vertexBCast, Instance* instance){
	 BroadcastActor* actorBCast = (BroadcastActor*)instance->getActor();

	//Set a new connection that connects the broadcast to the graph
	Port* bcastInput = actorBCast->getInput();
	Port* srcPort = connection->getSourcePort();
	map<string, IRAttribute*>* IRAttributes = connection->getAttributes();
	
	//Bound it to the instance
	instance->setAsInput(bcastInput);

	Connection* incoming = new Connection(srcPort,bcastInput, IRAttributes);

	graph->addEdge(vertex, vertexBCast, incoming);

}

void BroadcastAdder::createOutgoingConnections(Vertex* vertexBCast, list<Connection*>* outList, Instance* instance){
	list<Connection*>::iterator it;
	int i = 0;
	BroadcastActor* actorBCast = (BroadcastActor*)instance->getActor();

	for ( it=outList->begin() ; it != outList->end(); it++ ){
		
		//Verifying that this connection has not been already examined
		Vertex* target = (Vertex*)graph->getEdgeTarget(*it);

		if (target != NULL){
			//Create a new output connection from broadcast
			stringstream portName;
			portName << actorBCast->getName()<< "_output_" << i;
			Port* outputPort = actorBCast->getOutput(portName.str());
			
			//Bound it to the instance
			instance->setAsOutput(outputPort);
			i++;

			map<string, IRAttribute*>* attributes = (*it)->getAttributes();
			Connection* connBcastTarget = new Connection(outputPort, (*it)->getDestinationPort(), attributes);

			//Add the connection into the graph
			graph->addEdge(vertexBCast, target, connBcastTarget);

			// setting source to null so we don't examine it again
			(*it)->setSink(NULL);
			(*it)->setDestinationPort(NULL);

			//Remove previous connection
			toBeRemoved.push_back(*it);
		}
	}
}

void BroadcastAdder::transform(){
	
	// Examine vertex of the graph 
	int vertices = graph->getNbVertices();

	for (int i = 0; i < vertices; i++){
		Vertex* vertex = (Vertex*)graph->getVertex(i);
		if (vertex->isInstance()){
			examineVertex(vertex);
		}
	}

	// removes old connections
	list<Connection*>::iterator it;
	bool graphChanged = false;

	for (it = toBeRemoved.begin(); it != toBeRemoved.end(); it++){
		graphChanged |= graph->removeEdge((*it));
	}

	// refresh graph if necessary
	if (graphChanged){
		graph->refreshEdges();
	}
}
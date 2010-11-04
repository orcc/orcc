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
@brief Implementation of class FifoInit
@author Jerome Gorin
@file FifoInit.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <map>
#include <sstream>

#include "Jade/Core/Port.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Core/InstancedActor.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Instance.h"
#include "Jade/Core/Network.h"
#include "Jade/Core/Vertex.h"
#include "Jade/Core/Connection.h"

#include "BroadcastAdder.h"
#include "BroadcastActor.h"
//------------------------------

using namespace std;

BroadcastAdder::BroadcastAdder(llvm::LLVMContext& C, Decoder* decoder) : Context(C){
	this->fifo = decoder->getFifo();
	this->graph = decoder->getNetwork()->getGraph();
	this->decoder = decoder;
	this->instancedActors =  decoder->getInstancedActors();
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
				BroadcastActor* actorBCast = new BroadcastActor(Context, decoder, name, numOuputs, connection->getIntegerType(), fifo);
				
				//Instanciate broadcast
				Instance* newInstance = new Instance(name, actorBCast);
				InstancedActor* instancedActor = actorBCast->instanciate(newInstance);
				instancedActors->insert(pair<Instance*, InstancedActor*>(newInstance, instancedActor));
				
				//Set a new vertex in the graph
				Vertex* vertextBCast = new Vertex(newInstance);
				graph->addVertex(vertextBCast);
				
				//Connect the broadcast vertex in the graph
				createIncomingConnection(connections[i], vertex, vertextBCast, actorBCast);
				createOutgoingConnections(vertextBCast, outList, actorBCast);
			}
			outMap->erase(it);
		}
	}
}

void BroadcastAdder::createIncomingConnection(Connection* connection, Vertex* vertex, Vertex* vertexBCast, BroadcastActor* actorBCast){

	//Set a new connection that connects the broadcast to the graph
	Port* bcastInput = actorBCast->getInput();
	Port* srcPort = connection->getSourcePort();
	map<string, Attribute*>* attributes = connection->getAttributes();

	Connection* incoming = new Connection(srcPort,bcastInput, attributes);
	incoming->setType(connection->getIntegerType());

	graph->addEdge(vertex, vertexBCast, incoming);

}

void BroadcastAdder::createOutgoingConnections(Vertex* vertexBCast, list<Connection*>* outList, BroadcastActor* actorBCast){
	list<Connection*>::iterator it;
	int i = 0;

	for ( it=outList->begin() ; it != outList->end(); it++ ){
		
		//Verifying that this connection has not been already examined
		Vertex* target = (Vertex*)graph->getEdgeTarget(*it);

		if (target != NULL){
			//Create a new output connection from broadcast
			stringstream portName;
			portName << actorBCast->getName()<< "_output_" << i;
			Port* outputPort = actorBCast->getOutput(portName.str());
			i++;

			map<string, Attribute*>* attributes = (*it)->getAttributes();
			Connection* connBcastTarget = new Connection(outputPort, (*it)->getDestinationPort(), attributes);
			connBcastTarget->setType((*it)->getIntegerType());

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
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
@brief Implementation of class Network
@author Jerome Gorin
@file Instance.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include "Jade/Core/Network.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Graph/DotWriter.h"
//------------------------------

using namespace std;

#define MAX_CONNECTION 100

Network::~Network(){
	delete graph;
	delete inputs;
	delete outputs;
}

void Network::print(std::string file){
	DotWriter writer;
	
	writer.write(graph, (char*)file.c_str(), 0);
}

list<Instance*>* Network::getInstances() {
	// Clear previously computed instances
	instances.clear();

	// Iterate though all vertex of the graph and add instances
	int nbVertices = graph->getNbVertices();
	
	for (int i = 0; i < nbVertices; i++) {
		Vertex* vertex = (Vertex*)graph->getVertex(i);

		// In case of hierarchichal graph
		if (vertex->isInstance()){
			instances.push_back(vertex->getInstance());
		}
	}

	return &instances;
}

void Network::computeSuccessorsMaps(){
	// Determine all new successor
	graph->precomputeSuccessors();
}

list<Instance*> Network::getSuccessorsOf(Instance* instance){
	Vertex* vertex = getVertex(instance);
	int nbSucc = vertex->nbSuccessors;
	
	// Add all successor in list
	list<Instance*> successors;

	for (int i = 0; i < nbSucc; i++){
		Vertex* succ = (Vertex*)vertex->successors[i];
		if (succ->isInstance()){
			successors.push_back(succ->getInstance());
		}
	}
	
	successors.unique();

	return successors;
}

Vertex* Network::getVertex(Instance* instance){
	int nbVertex = graph->getNbVertices();
	
	for (int i = 0; i < nbVertex; i++){
		Vertex* vertex = (Vertex*)graph->getVertex(i);
		if (vertex->isInstance()){
			if (vertex->getInstance() == instance){
				// Vertex has been found
				return vertex;
			}
		}
	}
	
	// No vertex found
	return NULL;
}

bool Network::removeInstance(Instance* instance){
	instances.remove(instance);
	Vertex* vertex = getVertex(instance);
	return graph->removeVertex(vertex);
}

Vertex* Network::addInstance(Instance* instance){
	Vertex* vertex = new Vertex(instance);
	instances.push_back(instance);
	graph->addVertex(vertex);

	return vertex;
}

bool Network::removeConnection(Connection* connection){
	return graph->removeEdge(connection);
}

list<Connection*>* Network::getConnections(){	
	connections.clear();
	
	int nbEdge = graph->getNbEdges();
	
	for (int i = 0 ; i < nbEdge; i++){
		connections.push_back((Connection*)graph->getEdge(i));
	}

	return &connections;
}

list<Connection*>* Network::getAllConnections(Instance* source, Instance* target){	
	return (list<Connection*>*)graph->getAllEdges(new Vertex(source), new Vertex(target));
}

list<Connection*> Network::getInConnections(Instance* instance){
	list<Connection*> ins;
	HDAGEdge* inEdges[MAX_CONNECTION];

	int nbEdges = graph->getInputEdges(new Vertex(instance), inEdges);

	// Insert edge found in result
	for (int i = 0; i < nbEdges; i++){
		ins.push_back((Connection*)inEdges[i]);
	}

	return ins;
}

list<Connection*> Network::getOutConnections(Instance* instance){
	list<Connection*> outs;

	HDAGEdge* outEdges[MAX_CONNECTION];

	int nbEdges = graph->getOutputEdges(new Vertex(instance), outEdges);

	// Insert edge found in result
	for (int i = 0; i < nbEdges; i++){
		outs.push_back((Connection*)outEdges[i]);
	}

	return outs;
}

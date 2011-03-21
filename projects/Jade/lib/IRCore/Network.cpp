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
	list<Vertex*>::iterator it;
	list<Vertex*>* vertices = getVertices();
	
	for (it = vertices->begin(); it != vertices->end(); it++) {
		
		Vertex* vertex = *it;

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

list<Vertex*>*  Network::getVertices(){
	// Clear previously computed vertices
	vertices.clear();

	// Iterate though all vertex of the graph and adding then
	int nbVertices = graph->getNbVertices();
	
	for (int i = 0; i < nbVertices; i++) {
		Vertex* vertex = (Vertex*)graph->getVertex(i);

		vertices.push_back(vertex);
	}

	return &vertices;
}

list<Vertex*>* Network::getSuccessorsOf(Vertex* vertex){
	successors.clear();
	int nbSucc = vertex->nbSuccessors;
	
	for (int i = 0; i < nbSucc; i++){
		Vertex* succ = (Vertex*)vertex->successors[i];
		successors.push_back(succ);
	}

	return &successors;
}

bool Network::removeVertex(Vertex* vertex){
	return graph->removeVertex(vertex);
}

void Network::addVertex(Vertex* vertex){
	graph->addVertex(vertex);
}
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
@brief Implementation of class Configuration
@author Jerome Gorin
@file Configuration.cpp
@version 1.0
@date 18/01/2011
*/

//------------------------------
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Network.h"
#include "Jade/Core/Vertex.h"
#include "Jade/Graph/HDAGGraph.h"
//------------------------------

using namespace std;

Configuration::Configuration(Network* network, AbstractConnector* connector){
	this->network = network;
	this->connector = connector;

	//Calculate Configuration from a network
	setInstances();
}

void Configuration::setInstances(){
	// Create list of instance and actor
	HDAGGraph* graph = network->getGraph();
	int vertices = graph->getNbVertices();

	for (int i = 0; i < vertices; i++){
		Vertex* vertex = (Vertex*)graph->getVertex(i);

		if(vertex->isInstance()){
			Instance* instance = vertex->getInstance();
			instances.insert(pair<string,Instance*>(instance->getId(), instance));
			
			//Insert actor requiered for this network
			string clasz = instance->getClasz();
			actorFiles.push_back(clasz);
		}
	}

	//remove duplicate actors
	actorFiles.sort();
	actorFiles.unique();
}


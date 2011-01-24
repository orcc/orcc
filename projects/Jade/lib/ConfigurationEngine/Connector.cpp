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
@brief Implementation of class Connector
@author Jerome Gorin
@file Connector.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>

#include "Connector.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Network.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace std;

Connector::Connector(llvm::LLVMContext& C, Decoder* decoder) : Context(C){
	this->decoder = decoder;
}

Connector::~Connector(){

}


void Connector::setConnections(Configuration* configuration){
	Network* network = configuration->getNetwork();
	HDAGGraph* graph = network->getGraph();
	
	int edges = graph->getNbEdges();
	
	for (int i = 0; i < edges; i++){
		setConnection((Connection*)graph->getEdge(i));
	}
}

void Connector::unsetConnections(Configuration* configuration){
	Network* network = configuration->getNetwork();
	HDAGGraph* graph = network->getGraph();
	
	int edges = graph->getNbEdges();
	
	for (int i = 0; i < edges; i++){
		Connection* connection = ((Connection*)graph->getEdge(i));
		connection->unsetFifo();
	}
}

void Connector::setConnection(Connection* connection){
	//Source port is choosen as the reference type
	Port* src = connection->getSourcePort();
	
	//Create a fifo and set it to the connection
	AbstractFifo* fifo = FifoMng::getFifo(Context, decoder, src->getType(), connection->getSize());
	connection->setFifo(fifo);
}
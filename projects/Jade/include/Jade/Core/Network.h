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
@brief Description of the Network class interface
@author Jerome Gorin
@file Network.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef NETWORK_H
#define NETWORK_H

#include <map>
#include <string>

#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Network/Connection.h"
#include "Jade/Core/Network/Vertex.h"

class HDAGGraph;
class Port;
//------------------------------

/**
*
* @class Network
* @brief  This class defines a XDF network.
*
* @author Jerome Gorin
*
*/
class Network {

public:

	/*!
     *  @brief Create a network.
	 *
	 * Creates a new network with the given name, inputs, outputs, and graph.
	 * 
	 * @param name : network name
	 *
	 * @param inputs : map of input ports
	 *
	 * @param outputs : map of output ports
	 *
	 * @param graph : graph representing network
	 */
	Network(std::string name, std::map<std::string, Port*>* inputs, std::map<std::string, Port*>* outputs, HDAGGraph* graph){
		this->name = name;
		this->inputs = inputs;
		this->outputs = outputs;
		this->graph = graph;
	};


	/*!
     *  @brief Delete a network.
	 */
	~Network();

	/**
	 * @brief Getter of graph
	 *
	 * Returns the graph representing the network's contents
	 * 
	 * @return HDAGGraph representing the network's contents
	 */
	HDAGGraph* getGraph() {	return graph;};

	/**
	 * @brief Getter of name
	 *
	 * Returns the name of the network
	 * 
	 * @return the name of the network
	 */
	std::string getName() {	return name;};

	/*!
     *  @brief Print network in a dot file.
	 *
	 *  Output the parsed network into a dot file.
	 *
	 *  @param file : file to print the dot into
	 */
	void print(std::string file);

	/**
	 * @brief Returns the list of instances referenced by the graph of this network.
	 * 
	 * @return a list of instances
	 */
	std::list<Instance*>* getInstances();

	/**
	 * @brief Returns a list of instance that are connected the given instance.
	 * 
	 * @return a list of successor instances
	 */
	std::list<Instance*> getSuccessorsOf(Instance* instance);
	
	/**
	 * @brief Remove an instance from the network.
	 * 
	 * @param instance : the Instance to remove
	 *
	 * @return whether the instance has been found or not
	 */
	bool removeInstance(Instance* instance);

	/**
	 * @brief Remove a vertex in the network.
	 * 
	 * @param vertex : the Vertex to add
	 *
	 * @return the corresponding vertex in graph
	 */
	Vertex* addInstance(Instance* instance);

	/**
	 * @brief Remove a connection from the network.
	 * 
	 * @param connection : the Connection to remove
	 *
	 * @return whether the connection has been found or not
	 */
	bool removeConnection(Connection* connection);

	/**
	 * @brief Return incoming connections of the given instance
	 * 
	 * @param instance : the Instance to get incoming connections
	 *
	 * @return a list of incoming connections
	 */
	std::list<Connection*> getInConnections(Instance* instance);

	/**
	 * @brief Return outgoing connections of the given instance
	 * 
	 * @param instance : the Instance to get outgoing connections
	 *
	 * @return a list of incoming connections
	 */
	std::list<Connection*> getOutConnections(Instance* instance);

	/**
	 * @brief Compute a list of successors in the graph.
	 */
	void computeSuccessorsMaps();

	/**
	 * @brief Returns a set of all edges connecting source vertex to target vertex if such vertices exist in this graph.
	 *
	 * @param source : the source Vertex
	 *
	 * @param target : the target Vertex
	 */
	std::list<Connection*>* getAllConnections(Instance* source, Instance* target);

private:

	/**
	 * @brief Compute a successor of a vertex.
	 */
	void computeSuccessor(Vertex* vertex,
		std::map<std::string, Port*>* inputs, std::map<std::string, Port*>* outputs);

	/**
	 * @brief Returns the vertex corresponding to the instance.
	 * 
	 * @return the corresponding vertex
	 */
	Vertex* getVertex(Instance* instance);

	/** name of the network  */
	std::string name;

	/** map of input ports  */
	std::map<std::string, Port*>* inputs;

	/** map of outputs ports  */
	std::map<std::string, Port*>* outputs;

	/** graph of the network  */
	HDAGGraph* graph;

	/** instances of the network  */
	std::list<Instance*> instances;
};

#endif
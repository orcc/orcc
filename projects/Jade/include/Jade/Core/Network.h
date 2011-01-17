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
#include <list>
#include <string>

class Actor;
class Decoder;
class Instance;
class HDAGGraph;
class Port;
class Package;
class Procedure;

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
     *  @brief Return a list of the Actor classz contained in the network.
     *
     *  Return all Actors of the current network.
	 *   
	 *
     *  @return a map of Actor contained in the network
     */
	std::list<std::string>* getActorFiles(){return &actorFiles;};

	/*!
     *  @brief Return a list of the Instance contained in the network.
     *
     *  Return all Instance of the current network.
	 *   
	 *
     *  @return a map of Actor contained in the network
     */
	std::map<std::string, Instance*>* getInstances(){return &instances;};


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
	Network(std::string name, std::map<std::string, Port*>* inputs, std::map<std::string, Port*>* outputs, HDAGGraph* graph);


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

	/**
	 * @brief Getter of decoder
	 *
	 * Returns the decoder bound to the network
	 * 
	 * @return the decoder bound to the network
	 */
	Decoder* getDecoder() {return decoder;};

	/**
	 * @brief Set the package requiered by the network
	 * 
	 * @param packages : the package requiered by the decoder
	 */
	void setPackages(std::map<std::string, Package*>* packages) {this->packages = packages;};


	/**
	 * @brief Get the package requiered by the decoder
	 *
	 * Returns the packages requiered to instanciate this decoder.
	 * 
	 * @return the package requiered for the decoder
	 */
	std::map<std::string, Package*>* getPackages() {return packages;};

	/**
	 * @brief Setter of decoder
	 *
	 * Set the decoder bound to the network
	 * 
	 * @param decoder : the decoder bound to the network
	 */
	void setDecoder(Decoder* decoder) {this->decoder = decoder;};

	/*!
     *  @brief Print network in a dot file.
	 *
	 *  Output the parsed network into a dot file.
	 *
	 *  @param file : file to print the dot into
	 */
	void print(std::string file);

private:

	/*!
     *  @brief Set list of instances and actors.
	 */
	void setNetwork();

	/** name of the network  */
	std::string name;

	/** map of input ports  */
	std::map<std::string, Port*>* inputs;

	/** map of outputs ports  */
	std::map<std::string, Port*>* outputs;

	/** graph of the network  */
	HDAGGraph* graph;

	/** actors files of the network  */
	std::list<std::string> actorFiles;
	
	/** instances of the network  */
	std::map<std::string, Instance*> instances;

	/** package used by the network  */
	std::map<std::string, Package*>* packages;

	/** decoder of the instance */
	Decoder* decoder;

};

#endif
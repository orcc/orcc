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
@brief Description of the Instanciator class interface
@author Jerome Gorin
@file Instanciator.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef INSTANCIATOR_H
#define INSTANCIATOR_H

#include <map>

#include "Jade/Core/Actor.h"

class Network;
class HDAGGraph;
class Connection;
//------------------------------

/**
 * @class Instanciator
 *
 * @brief This class is used by network transformation to process the instanciation.
 *
 * @author Jerome Gorin
 * 
 */
class Instanciator {
public:

	/**
	 * @brief instanciate a network.
	 *
	 * Instantiate actors and checks that connections actually point to ports defined in actors. Instantiating an
	 * actor implies first loading it and then giving it the right parameters.
	 * 
	 * @param network : Network to instanciate
	 *
	 * @param actors : Map of actors used in the instance
	 */
	Instanciator(Network* network, std::map<std::string, Actor*>* actors);

	~Instanciator(){};

private:

	/**
	 * @brief Update instance and connections of the network.
	 *
	 * Updates the connections of this network. MUST be called before actors are
	 * instantiated.
	 * 
	 */
	void updateInstances();

	/**
	 * @brief Update instance of the network.
	 *
	 * Updates the instance of this network using its corresponding actor
	 * 
	 */
	void updateInstance(Instance* instance);

	/**
	 * @brief Update a connection in the network.
	 *
	 * Updates instance using the given connection's source and target port by getting the
	 * ports from the source and target instances, after checking the ports
	 * exist and have compatible types.
	 * 
	 * @param connection : Connection to update
	 */
	void updateConnection(Connection* connection);

	/* Graph to transform */
	HDAGGraph* graph;
	
	/** Network to instanciate */
	Network* network;

	/** List of actor from the network */
	std::map<std::string, Actor*>* actors;

};

#endif
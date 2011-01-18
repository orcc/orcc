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
@brief Description of the ConfigurationEngine class interface
@author Jerome Gorin
@file ConfigurationEngine.h
@version 1.0
@date 18/01/2011
*/

//------------------------------
#ifndef SCENARIO_H
#define SCENARIO_H

#include <list>
#include <map>

class Instance;
class Network;
//------------------------------

/**
 * @brief  This class represents configuration engine
 * 
 * @author Jerome Gorin
 * 
 */
class Scenario {
public:
	/*!
     *  @brief Constructor
     *
	 * Creates a new configuration engine.
     */
	Scenario(Network* network);

	/*!
     *  @brief Return a list of the Instance contained in the network.
     *
     *  Return all Instance of the current network to instanciate.
	 *   
     *  @return a map of Actor contained in the network
     */
	std::map<std::string, Instance*>* getInstances(){return &instances;};

	/*!
     *  @brief Return a list of the Actor classz contained in the network.
     *
     *  Return all Actors of the current network.
	 *   
	 *
     *  @return a map of Actor contained in the network
     */
	std::list<std::string>* getActorFiles(){return &actorFiles;};

	/**
     *  @brief Getter of a network
	 * 
	 *  @return network of the scenario
     */
	Network* getNetwork(){return network;};

private:

	/*!
     * @brief Set instances to instanciate
     *
	 * Set the instances of the network to instanciate.
     */
	void setInstances();

	/** Original network */
	Network* network;

	/** Instances to instanciate  */
	std::map<std::string, Instance*> instances;

	/** Actors files of the network  */
	std::list<std::string> actorFiles;

};

#endif
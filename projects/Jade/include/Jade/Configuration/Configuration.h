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
#ifndef CONFIGURATION_H
#define CONFIGURATION_H

#include <list>
#include <map>

#include "Jade/Core/Actor.h"
#include "Jade/Core/Network.h"
#include "Jade/Core/Package.h"
//------------------------------

/**
 * @brief  This class represents configuration of a network
 * 
 * @author Jerome Gorin
 * 
 */
class Configuration {
public:
	/*!
     *  @brief Constructor
     *
	 * Creates a new configuration engine.
     */
	Configuration(Network* network);

	/*!
     *  @brief Destructor
     *
	 * Delete the configuration.
     */
	~Configuration();

	/*!
     *  @brief Return a list of Instance contained in the configuration.
     *
     *  Return all Instance of the current configuration.
	 *   
     *  @return a map of Instance contained in the configuration.
     */
	std::map<std::string, Instance*>* getInstances(){return &instances;};

	/*!
     *  @brief Return a list of Instance of an actor in the configuration.
     *
     *  Return all Instance of an actor contains in the configuration.
	 *   
     *  @return a list of Instance of an Actor contained in the configuration.
     */
	std::list<Instance*> getInstances(Actor* actor);

	/**
     *  @brief Get of an instance in the configuration
	 * 
	 *	Return the instance that correspond to the given name 
	 *    in the configuration.
	 *
	 *  @return the instance if found, otherwise NULL
	 *
     */
	Instance* getInstance(std::string name);

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

	/**
     *  @brief return the actor corresponding to the given name
	 * 
	 *	Return an actor corresponding to the given name if the current actor is contained in the configuration,
	 *   return NULL if the actor is not contained in the configuration
	 *
	 *	@param name : std::string name of the actor 
	 *
	 *  @return the actor if contained in the configuration otherwise NULL
	 *
     */
	Actor* getActor(std::string name);

	/**
     *  @brief set the actors contains in the configuration
	 *
	 *	Set list of actors used by the configuration, determine required
	 *    package and instanciate the configuration.
	 *
	 *	@param name : a map of actors contains in the configuration
	 *
     */
	void setActors(std::map<std::string, Actor*>* actors);

	/**
     *  @brief get the actors contains in the configuration
	 *
	 *	@return a map of actors contains in the configuration
	 *
     */
	std::map<std::string, Actor*>* getActors(){
		return actors;
	};

	/**
	 * @brief Get the package requiered by the decoder
	 *
	 * Returns the packages requiered to instanciate this decoder.
	 * 
	 * @return the package requiered for the decoder
	 */
	std::map<std::string, Package*>* getPackages() {return packages;};

	/**
     *  @brief Get of a specific actor
	 *
	 *	Return the specifics actors created for this configuration,
	 *   mostly broadcast actors
	 * 
	 *  @return a list of dedicated actors
	 *
     */
	std::list<Actor*>* getSpecifics(){return &specificActors;};

	/**
     *  @brief Erase specific actors and instances from the configuration
	 *
     */
	void eraseSpecifics();

	/**
     *  @brief Insert a specific actor in the configuration,
	 *		all the instance of this specific actor are added in
	 *      the instance list
	 *
	 *  @param actor: specific actor to add in the configuration
     */
	void insertSpecific(Actor* actor);

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

	/** Actors files of the configuration  */
	std::list<std::string> actorFiles;

	/** Actors of the configuration */
	std::map<std::string, Actor*>* actors;

	/** package used by the configuration  */
	std::map<std::string, Package*>* packages;

	/** List of specific actors contained in the configuration */
	std::list<Actor*> specificActors;

};

#endif
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
@brief Description of the BinaryExpr class interface
@author Jerome Gorin
@file BinaryExpr.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef INSTANCE_H
#define INSTANCE_H

#include <map>
#include <list>

namespace llvm{
	class Constant;
}

#include "Jade/Core/Entity.h"
#include "Jade/Core/Expression.h"
#include "Jade/Core/Attribute/TypeAttribute.h"
#include "Jade/Core/Attribute/ValueAttribute.h"

class Action;
class Actor;
class ActionScheduler;
class BroadcastActor;
class Configuration;
class HDAGGraph;
class Network;
class Port;
class Procedure;
class StateVar;
class Variable;
class Vertex;
//------------------------------

/**
 * @class Instance
 *
 * @brief  This class defines an instance in a Network.
 *
 * This class defines an instance. An instance has an id, a class, parameters
 * and attributes. The class of the instance points to an actor or a network.
 * 
 * @author Jerome Gorin
 * 
 */
class Instance : public Entity {
public:
	/*!
     *  @brief Constructor
     *
	 *  Creates a new instance.
	 *
	 * @param id	: string of the instance id
	 * @param clasz	: string of the instance class
	 * @param parameters : list of Expr representif parameters of this instance
     *
     */
	Instance(HDAGGraph* graph, std::string id, std::string clasz, std::map<std::string, Expr*>* parameterValues, 
			 std::map<std::string, IRAttribute*>* attributes);

	/*!
     *  @brief Constructor
     *
	 * Creates a new instance of the given broadcast with the given identifier.
	 *
	 * @param id : the instance identifier
	 * @param broadcast : a Broadcast
     *
     */
	Instance(std::string id, Actor* actor);

	~Instance();
	
	/*!
     *  @brief Getter of id
     *
	 * @return id of the Instance
     *
     */
	std::string getId(){return id;};

	/*!
     *  @brief Return true of the instance is a SuperInstance
     *
	 * @return true of the instance is a SuperInstance
     *
     */
	virtual bool isSuperInstance(){return false;};

	/*!
     *  @brief Getter of clasz
     *
	 * @return clasz of the Instance
     */
	std::string getClasz(){return clasz;};

	/**
     *  @brief Get the Vertex of the instance
     *
	 * @return the vertex of the instance
     */
	Vertex* getVertex(){return vertex;};

	/**
     * @brief Get the internal state variable corresponding to a port
     *
	 * @param port : the internal Port 
	 *
	 * @return the corresponding state variables
     */
	virtual StateVar* getInternalVar(Port* port){return NULL;};

	/**
	 * @brief Returns true if this entity is an instance.
	 * 
	 * @return true if this entity is an instance
	 */
	bool isInstance(){return true;};

	/**
     * @brief Get the internal state variables of the instance
	 *
	 * @return a list of state variable
     */
	virtual std::map<Port*, StateVar*>* getInternalPorts(){return NULL;};

	/**
     * @brief Return true if this instance has internal port
	 *
	 * @return true if this instance has internal port, otherwise false
     */
	virtual bool hasInternalPort(){return false;};

	/*!
     *  @brief Setter of clasz
     *
	 * @param clasz: the new clasz of the Instance
     *
     */
	void setClasz(std::string clasz){this->clasz = clasz;};

	/*!
     *  @brief Getter of actor
     *
	 * @return Actor of this instance
     *
     */
	Actor* getActor(){return actor;};

	/*!
     *  @brief Setter of actor
     *
	 *	Set the actor of this instance and refresh actor instance list
	 * @param actor : Actor of this instance
     *
     */
	void setActor(Actor* actor);

	/*!
     *  @brief Set the configuration of instance
     *
	 *	Set the configuration where this instance is references
	 *
	 *  @param configuration : the Configuration where instance is refered.
     */
	void setConfiguration(Configuration* configuration){this->configuration = configuration;};

	/*!
     *  @brief Get the configuration of instance
     *
	 *	Get the configuration where this instance is references
	 *
	 *  @return the Configuration where instance is refered.
     */
	Configuration* getConfiguration(){return configuration;};

	/**
     *  @brief Getter of paramters
     *
	 * @return a map on the Instance paramter
     *
     */
	std::map<std::string, Expr*>* getParameterValues(){return parameterValues;};

	/**
     *  @brief Set a new input port for the instance
	 *
	 *  Add a new input port in the instance
	 *
	 *  @param port : Port to add as input in the instance
	 *
     */
	void setAsInput(Port* port);

	/**
     *  @brief Set a new output port for the instance
	 *
	 *  Add a new output port in the instance
	 *
	 *  @param port : Port to add as output in the instance
	 *
     */
	void setAsOutput(Port* port);

	 /**
	 * @brief Returns a map of attributes.
	 * 
	 * @return a map of attributes
	 */
	std::map<std::string, IRAttribute*>* getAttributes() {return attributes;};

	 /**
	 * @brief Returns the mapping name.
	 * 
	 * @return the name of the mapping
	 */
	std::string getMapping();

	/**
	 * @brief Set the map of attributes of this instance.
	 * 
	 * @param attributes: a map of attributes
	 */
	 void setAttributes(std::map<std::string, IRAttribute*>* attributes) {this->attributes = attributes;};

	/**
	*
	* @brief Solves all parameters.
	*
	* Resolves parameters of this instance by using parameter and their values.
	*/
	void solveParameters();

	/**
	*
	* @brief Setter of Id
	*
	* @name : the new Id of instance
	*/
	void setId(std::string name){this->id = name;};

	/**
	*
	* @brief Activate or deactivate trace for the given instance
	*
	* @activate : whether or not trace must be activated
	*/
	void setTrace(bool activate){this->enableTrace = activate;};

	/**
	*
	* @brief Activate or deactivate trace for the given instance
	*
	* @activate : whether or not trace must be activated
	*/
	bool isTraceActivate(){return enableTrace;};
	
protected:

	/* Parameters of an instance */
	std::map<std::string, Expr*>* parameterValues;	
	
	/* Id of an instance */
	std::string id;
	
	/* Class of an instance */
	std::string clasz;	

	/**
	 * the actor referenced by this instance. May be null if this
	 * instance references a network or a broadcast.
	 */
	Actor* actor;

	/**
	 * The network referenced by this instance. May be <code>null</code> if this
	 * instance references an actor or a broadcast.
	 */
	Network* network;

	/** Vertex in the network */
	Vertex* vertex;

	/**
	 * The configuration that references this instance.
	 */
	Configuration* configuration;

	/** A map of the attributes of this actor */
	std::map<std::string, IRAttribute*>* attributes;

	/** Parent graph of the instance */
	HDAGGraph* parent;

	/** Traces are enabled for the given instance */
	bool enableTrace;
};

#endif
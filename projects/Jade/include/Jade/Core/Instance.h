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

#include "Jade/Core/Actor.h"
#include "Jade/Core/Expression.h"


class BroadcastActor;
class Decoder;
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
class Instance {
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
	Instance(std::string id, std::string clasz, std::map<std::string, Expr*>* parameterValues){
		this->id = id;
		this->clasz = clasz;
		this->parameterValues = parameterValues;
		this->actor = NULL;
		this->decoder = NULL;
		this->stateVars = NULL;
		this->parameters = NULL;
		this->procedures = NULL;
		this->initializes = NULL;
		this->actions = NULL;
		this->actionScheduler = NULL;
	}

	/*!
     *  @brief Constructor
     *
	 * Creates a new instance of the given broadcast with the given identifier.
	 *
	 * @param id : the instance identifier
	 * @param broadcast : a Broadcast
     *
     */
	Instance(std::string id, Actor* actor){
		this->id = id;
		this->actor = actor;
		this->decoder = NULL;
		this->stateVars = NULL;
		this->parameters = NULL;
		this->procedures = NULL;
		this->initializes = NULL;
		this->actions = NULL;
		this->actionScheduler = NULL;
	}

	~Instance();
	
	/*!
     *  @brief Getter of id
     *
	 * @return id of the Instance
     *
     */
	std::string getId(){return id;};

	/*!
     *  @brief Getter of clasz
     *
	 * @return clasz of the Instance
     *
     */
	std::string getClasz(){return clasz;};

	/*!
     *  @brief Getter of actor
     *
	 * @return Actor of this instance
     *
     */
	Actor* getActor(){return actor;};


	/*!
     * @brief Return true if this instance is concrete
     *
	 * @return true if the instance is concrete, otherwise false
     *
     */
	bool isConcrete(){return decoder!=NULL;};

	/*!
     *  @brief Setter of actor
     *
	 *	Set the actor of this instance and refresh actor instance list
	 * @param actor : Actor of this instance
     *
     */
	void setActor(Actor* actor);

	/**
     *  @brief get the Port corresponding to string name
	 *
	 *  @param portName : Name of the port
	 *
	 *  @return the corresponding Port if port found, otherwise NULL 
	 *
     */
	Port* getPort(std::string portName);

	/**
     *  @brief get the input Port corresponding to string name
	 *
	 *  @param portName : Name of the input port
	 *
	 *  @return the corresponding Port if port found, otherwise NULL 
	 *
     */
	Port* getInput(std::string portName);

	/**
     *  @brief get the output Port corresponding to string name
	 *
	 *  @param portName : Name of the input port
	 *
	 *  @return the corresponding Port if port found, otherwise NULL 
	 *
     */
	Port* getOutput(std::string portName);

	/*!
     *  @brief Getter of paramters
     *
	 * @return a map on the Instance paramter
     *
     */
	std::map<std::string, Expr*>* getParameterValues(){return parameterValues;};

	/**
     *  @brief Getter of the action scheduler of this instanced functional unit
   	 *
	 *  @return ActionScheduler of the instanced functional unit
     */
	ActionScheduler* getActionScheduler(){return actionScheduler;};

	/**
	 * @brief Getter of stateVars
	 *
	 * Returns a map of state variables.
	 * 
	 * @return a map of state variables
	 */
	std::map<std::string, Variable*>* getStateVars() {return stateVars;}

	/**
	 * @brief Getter of a state variable
	 *
	 * Return the state var corresponding to the given name
	 *
	 * @param name : string name of the state var
	 * 
	 * @return the corresponding state variable
	 */
	Variable* getStateVar(std::string name);

	/**
	 * @brief Getter of procedures
	 *
	 * Returns a map of procedure of this actor.
	 * 
	 * @return a map of ProcedureActionScheduler of this actor
	 */
	std::map<std::string, Procedure*>* getProcs() {return procedures;}

	/**
	 * @brief Getter of a procedure
	 *
	 * Returns the procedure corresponding to the given name
	 * 
	 * @param name: std::string of the procedure
	 *
	 * @return the corresponding procedure
	 */
	Procedure* getProcedure(std::string name);

	/**
     *  @brief get initializes actions of the instance
	 *
	 *  @return a list of initializes actions
	 *
     */
	std::list<Action*>* getInitializes(){return initializes;};

	/**
     *  @brief return true if the instance has initialize actions
	 *
	 *  @return true if instance has initializes actions, otherwise false
	 *
     */
	bool hasInitializes() {return !initializes->empty();};

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
     *  @brief getter of input ports
	 *
	 *  @return a map of Port representing inputs of the instance
	 *
     */
	std::map<std::string, Port*>* getInputs() {return &inputs;};

	/**
     *  @brief getter of input ports
	 *
	 *  @return a map of Port representing inputs of the instance
	 *
     */
	std::map<std::string, Port*>* getOutputs() {return &outputs;};

	/**
	 * @brief make the instance Concrete
	 *
	 * Bound this decoder to an actor and a decoder
	 * 
	 * @param decoder: Decoder that store the instance
	 *
	 * @param stateVars: state variable in decoder of the instance
	 *
	 * @param parameters: parameters in decoder of the instance
	 *
	 * @param procs: procedures in decoder of the instance
	 *
	 * @param initializes: initialization actions in decoder of the instance
	 *
	 * @param actions: actions in decoder of the instance
	 *
	 * @param actionScheduler: actionScheduler in decoder of the instance
	 *
	 * @return the corresponding procedure
	 */
	void makeConcrete(Decoder* decoder,
					  std::map<std::string, Variable*>* stateVars,
					  std::map<std::string, Variable*>* parameters,	
					  std::map<std::string, Procedure*>* procs,	
					  std::list<Action*>* initializes,
					  std::list<Action*>* actions,
					  ActionScheduler* actionScheduler){
	this->actor = actor;
	this->decoder = decoder;
	this->inputs = inputs;
	this->outputs = outputs;
	this->stateVars = stateVars;
	this->parameters = parameters;
	this->procedures = procs;
	this->initializes = initializes;
	this->actions = actions;
	this->actionScheduler = actionScheduler;
	solveParameters(); 
};

	
private:

	/**
	*
	* @brief Solves all parameters.
	*
	* Resolves parameters of this instance by using parameter and their values.
	*/

	void solveParameters();

	/* Parameters of an instance */
	std::map<std::string, Expr*>* parameterValues;	
	
	/* Id of an instance */
	std::string id;
	
	/* Class of an instance */
	std::string clasz;	

	/**
	 * the actor referenced by this instance. May be null< if this
	 * instance references a network or a broadcast.
	 */
	Actor* actor;


	/**
	 * the decoder where the instance has been made concrete.
	 */
	Decoder* decoder;

	/** Port of the instance */
	std::map<std::string, Port*> inputs;
	std::map<std::string, Port*> outputs;

	/** A map of the parameters of this actor */
	std::map<std::string, Variable*>* parameters;

	/** State variables of this actor */
	std::map<std::string, Variable*>* stateVars;

	/** Procedures of this actor */
	std::map<std::string, Procedure*>* procedures;

	/** Actions of the instance */
	std::list<Action*>* actions;
	
	/** Action scheduler of the instance */
	ActionScheduler* actionScheduler;

	/** Initialize actions of the instance */
	std::list<Action*>* initializes;
};

#endif
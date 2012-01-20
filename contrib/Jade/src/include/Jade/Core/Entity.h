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
@brief Description of the Entry class interface
@author Jerome Gorin
@file Entry.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef ENTITY_H
#define ENTITY_H
#include <list>
#include <map>
#include <string>

class Action;
class ActionScheduler;
class MoC;
class Port;
class Procedure;
class StateVar;
class Variable;
//------------------------------

/**
 * @class Entity
 *
 * @brief This class defines either an instance, an actor or a broadcast.
 *
 * @author Jerome Gorin
 * 
 */
class Entity {
public:

	/*!
     *  @brief Constructor
     *
	 * Creates an abstract entry.
	 *
     */
	Entity(std::map<std::string, Port*>* inputs, std::map<std::string, Port*>* outputs, std::list<Action*>* initializes, std::list<Action*>* actions){
		this->inputs = inputs;
		this->outputs = outputs;
		this->actions = actions;
		this->initializes = initializes;
	};
	
	~Entity(){};

	/**
	 * @brief Returns true if this entity is an instance.
	 * 
	 * @return true if this entity is an instance
	 */
	virtual bool isInstance(){return false;};

	/**
	 * @brief Returns true if this entity is an actor.
	 * 
	 * @return true if this entity is actor
	 */
	virtual bool isActor(){return false;};

	/**
	 * @brief Returns true if this entity is a broadcast.
	 * 
	 * @return true if this entity is broadcast
	 */
	virtual bool isBroadcast(){return false;};

	/**
     *  @brief getter of input ports
	 *
	 *  @return a map of port representing inputs of the entity
     */
	virtual std::map<std::string, Port*>* getInputs() {return inputs;};

		/**
     *  @brief getter of input ports
	 *
	 *  @return a map of port representing outputs of the entity
	 *
     */
	virtual std::map<std::string, Port*>* getOutputs() {return outputs;};

	/**
     *  @brief get the input port corresponding to string name
	 *
	 *  @param name : name of the input port
	 *
	 *  @return the corresponding port if found, otherwise NULL 
	 *
     */
	virtual Port* getInput(std::string name);

	/**
     *  @brief get the output port corresponding to string name
	 *
	 *  @param name : name of the input port
	 *
	 *  @return the corresponding port if found, otherwise NULL 
	 *
     */
	virtual Port* getOutput(std::string name);

	/**
     *  @brief get initializes actions of the entity
	 *
	 *  @return a list of initializes actions
	 *
     */
	virtual std::list<Action*>* getInitializes(){return initializes;};

	/**
	 * @brief Returns all the actions of this entity.
	 * 
	 * @return all the actions of this entity
	 */
	virtual std::list<Action*>* getActions() {return actions;};

	/**
	 * @brief Set the actions of this entity.
	 * 
	 * @param actions : a list of actions of this entity
	 */
	virtual void setActions(std::list<Action*>* actions) {this->actions = actions;};

	/**
     *  @brief set initializes actions of this entity
	 *
	 *  @param initializes : a list of initializing actions
	 *
     */
	 virtual void setInitializes(std::list<Action*>* initializes){this->initializes = initializes;};

	 /**
     *  @brief return true if the instance has initializing actions
	 *
	 *  @return true if instance has initializing actions, otherwise false
	 *
     */
	virtual bool hasInitializes() { 
		if (initializes == NULL){
			return false;
		}		
		return !initializes->empty();
	};

	/**
	 * @brief Returns the parameters of this entity.
	 * 
	 * @return a map of parameters
	 */
	virtual std::map<std::string, Variable*>* getParameters() {return parameters;};

	/**
	 * @brief Set the map of parameters of this entity.
	 * 
	 * @param parameters: a map of parameters
	 */
	 virtual void setParameters(std::map<std::string, Variable*>* parameters) {this->parameters = parameters;};

	 /**
	 * @brief Get procedures of this entity
	 *
	 * Returns the procedure corresponding to the given name
	 * 
	 * @param name: name of the procedure
	 *
	 * @return the corresponding procedure
	 */
	virtual Procedure* getProcedure(std::string name);

	/**
     *  @brief Return the parameter with the given name in this entity
	 *
	 * @param name: name of the parameter
	 *
	 *  @return the package of this actor
	 *
     */
	virtual Variable* getParameter(std::string name);

	/**
     *  @brief Getter of the action scheduler of this instanced functional unit
   	 *
	 *  @return ActionScheduler of the instanced functional unit
     */
	virtual ActionScheduler* getActionScheduler(){return actionScheduler;};

	/**
     *  @brief Set the action scheduler of this instance
   	 *
	 *  @param actionScheduler : ActionScheduler of the instanced functional unit
     */
	virtual void setActionScheduler(ActionScheduler* actionScheduler){this->actionScheduler = actionScheduler;};

	/**
	 * @brief Getter of stateVars
	 *
	 * Returns a map of state variables.
	 * 
	 * @return a map of state variables
	 */
	virtual std::map<std::string, StateVar*>* getStateVars() {return stateVars;}


	/**
	 * @brief Setter of stateVars
	 *
	 * Set the map of state variables.
	 * 
	 * @param stateVars : a map of state variables
	 */
	virtual void setStateVars(std::map<std::string, StateVar*>* stateVars) {this->stateVars = stateVars;}

	/**
	 * @brief Getter of a state variable
	 *
	 * Return the state var corresponding to the given name
	 *
	 * @param name : string name of the state var
	 * 
	 * @return the corresponding state variable
	 */
	virtual StateVar* getStateVar(std::string name);

	/**
	 * @brief Getter of procedures
	 *
	 * Returns a map of procedure of this instance.
	 * 
	 * @return a map of ProcedureActionScheduler of this instance
	 */
	virtual std::map<std::string, Procedure*>* getProcs() {return procedures;}

	/**
	 * @brief Setter of procedures
	 *
	 * Set the map of procedure of this entity.
	 * 
	 * @return procedures : a map of Procedure.
	 */
	virtual void setProcs(std::map<std::string, Procedure*>* procedures) {this->procedures = procedures;};

	/**
     *  @brief get the port corresponding to string name
	 *
	 *  @param portName : Name of the port
	 *
	 *  @return the corresponding Port if port found, otherwise NULL 
	 *
     */
	virtual Port* getPort(std::string portName);

	/**
     * @brief Get the internal state variables of this entity
	 *
	 * @return a list of state variable
     */
	virtual std::map<Port*, StateVar*>* getInternalVars(){return NULL;};

	/*!
     *  @brief Set the MoC of the instance
     *
	 * @param moc : the MoC of the Instance
     */
	virtual void setMoC(MoC* moc){this->moc = moc;};

	/**
     *  @brief Get the MoC of this entity
     *
	 * @return MoC of the Instance
     */
	virtual MoC* getMoC(){return moc;};

protected:

	/** Port of the entity */
	std::map<std::string, Port*>* inputs;
	std::map<std::string, Port*>* outputs;

	/** A map of initializing actions of this entity */
	std::list<Action*>* initializes;

	/** A map of actions of this entity */
	std::list<Action*>* actions;

	/** State variables of this actor */
	std::map<std::string, StateVar*>* stateVars;

	/** Procedures of this actor */
	std::map<std::string, Procedure*>* procedures;

	/** Action scheduler of the instance */
	ActionScheduler* actionScheduler;

	/** A map of the parameters of this actor */
	std::map<std::string, Variable*>* parameters;

	/** MoC of the instance */
	MoC* moc;
};

#endif
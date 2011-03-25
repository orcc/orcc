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
@brief Description of the Actor class interface
@author Jerome Gorin
@file Actor.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef ACTOR_H
#define ACTOR_H

#include<string>
#include<map>
#include<list>

#include "Jade/Core/StateVariable.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/Actor/Action.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Core/MoC.h"
#include "Jade/Core/Parent.h"

namespace llvm{
	class Module;
	class Type;
	class StructType;
}

class ActionScheduler;
class FuncUnit;
class Instance;
class Package;
class Procedure;
class Variable;
//------------------------------

/**
 * @class Actor
 *
 * @brief  This class defines an actor in the network
 *
  * This class defines an actor. An actor has parameters, input and output ports,
 *	state variables, procedures, actions and an action scheduler. The action
 *	scheduler has information about the FSM if the actor has one, and the order
 *	in which actions should be scheduled.
 *
 * @author Jerome Gorin
 * 
 */
class Actor : public Parent {
public:
	
	/**
     *  @brief Constructor
     *
	 *	Creates a new empty actor.

	 * @param name : string on the actor name
	 *
	 * @param module : the llvm::Module of the actor
	 *
	 * @param file : string of the bitcode file this actor was defined in
	 *
     */
	Actor(std::string name, llvm::Module* module, std::string file);
	
	
	/**
     *  @brief Constructor
     *
	 *	Creates a new actor.
	 *
	 * @param name : string on the actor name
	 *
	 * @param module : the llvm::Module of the actor
	 *
	 * @param file : string of the bitcode file this actor was defined in
	 *
     * @param inputs : a map of input ports
	 *
	 * @param outputs : a map of output ports
	 *
	 * @param parameters : a map of parameters
	 *
	 * @param procedures : a map of procedures
	 *
	 * @param stateVars : a map of state variables
	 *
	 * @param actions : a list of actions
	 *
     */
	Actor(std::string name, llvm::Module* module, std::string file, std::map<std::string, Port*>* inputs, 
		std::map<std::string, Port*>* outputs, std::map<std::string, StateVar*>* stateVars, 
		std::map<std::string, Variable*>* parameters, std::map<std::string, Procedure*>* procedures, 
		std::list<Action*>* initializes, std::list<Action*>* actions, ActionScheduler* actionScheduler);

	/**
     *  @brief Constructor
     *
	 *	Creates a new actor with the given moc.
	 *
	 * @param name : string on the actor name
	 *
	 * @param file : string of the bitcode file this actor was defined in
	 *
	 * @param module : the llvm::Module of the actor
	 *
     * @param inputs : a map of input ports
	 *
	 * @param outputs : a map of output ports
	 *
	 * @param parameters : a map of parameters
	 *
	 * @param procedures : a map of procedures
	 *
	 * @param stateVars : a map of state variables
	 *
	 * @param actions : a list of actions
	 *
	 * @param moc : a MoC
     */
	Actor(std::string name, llvm::Module* module, std::string file, std::map<std::string, Port*>* inputs, 
		std::map<std::string, Port*>* outputs, std::map<std::string, StateVar*>* stateVars, 
		std::map<std::string, Variable*>* parameters, std::map<std::string, Procedure*>* procedures, 
		std::list<Action*>* initializes, std::list<Action*>* actions, ActionScheduler* actionScheduler,
		MoC* moc);

	virtual ~Actor();

	/**
     *  @brief Add an instance bound to this actor
     *
	 * @param instance : Instance bound to the current actor
	 *
     */
	void addInstance(Instance* instance);

	/**
     *  @brief Remove an instance possibly bound to this actor
     *
	 * @param instance : Instance to remove
     */
	void remInstance(Instance* instance);
	
	/**
     *  @brief Return parameters of actor
	 *
	 *  @return a map of Variable that contains actor parameters
	 *
     */
	std::map<std::string, Variable*>* getParameters(){return parameters;};

	/**
     *  @brief Set parameters of actor
	 *
	 *  @param parameters : a map of Variable that contains actor parameters
     */
	 void setParameters(std::map<std::string, Variable*>* parameters){this->parameters = parameters;};

	/**
     *  @brief Return the package of this actor
	 *
	 *  @return the package of this actor
	 *
     */
	Variable* getParameter(std::string name);

	/**
     *  @brief Return the llvm::Module of the Actor
	 *
	 *  @return the llvm::Module of the Actor
	 *
     */
	llvm::Module* getModule(){return module;};

	/**
     *  @brief Return the Parameter corresponding to the given name
	 *
	 *  @return the Parameter corresponding to the given name
	 *
     */
	std::string getPackage();

	bool isActor(){return true;};

	/**
     *  @brief Returns the simple name of this actor
	 *
	 *  @return the simple name of this actor
	 *
     */
	std::string getSimpleName();

	/**
     *  @brief Getter of actor name
	 *
	 *  @return a string of the actor name
	 *
     */
	std::string getName(){return name;};

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
     *  @brief get the Port corresponding to string name
	 *
	 *  @param portName : Name of the port
	 *
	 *  @return the corresponding Port if port found, otherwise NULL 
	 *
     */
	Port* getPort(std::string portName);

	/**
     *  @brief getter of input ports
	 *
	 *  @return a map of Port representing inputs of the actor
	 *
     */
	std::map<std::string, Port*>* getInputs() {return inputs;};

	/**
     *  @brief setter of input ports
	 *
	 *  @param inputs : a map of Port representing inputs of the actor
     */
	void setInputs(std::map<std::string, Port*>* inputs) {this->inputs = inputs;};

	/**
     *  @brief getter of output ports
	 *
	 *  @return a map of Port representing inputs of the actor
	 *
     */
	std::map<std::string, Port*>* getOutputs() {return outputs;};

	/**
     *  @brief setter of output ports
	 *
	 *  @param outputs : a map of Port representing output of the actor
     */
	void setOutputs(std::map<std::string, Port*>* outputs) {this->outputs = outputs;};


	/**
     *  @brief getter of actions
	 *
	 *  @return a list of actions
	 *
     */
	std::list<Action*>* getActions() {return actions;};

	/***
     *  @brief setter of actions
	 *
	 *  @param actions : a list of actions
     */
	void setActions(std::list<Action*>* actions) {this->actions = actions;};

	/**
     *  @brief getter of initialize actions
	 *
	 *  @return a list of initialize actions
	 *
     */
	std::list<Action*>* getInitializes() {return initializes;};

	/**
     *  @brief Setter of initialize actions
	 *
	 *  @param initializes : a list of initialize actions
	 *
     */
	 void setInitializes(std::list<Action*>* initializes) {this->initializes = initializes;};

	/**
     *  @brief return true if actior has initialize actions
	 *
	 *  @return true if actor has initializes actions
	 *
     */
	bool hasInitializes() {
		if (initializes != NULL){
			return !initializes->empty();
		}
		
		return false;
	};

	/**
     *  @brief Create an input port
	 *
	 *  Create an input port inside the actor similar to the given Port
	 *  
	 *  @param name : string of the port identifier
	 *
	 *  @param port : Port to add in the actor
	 *
     */
	void createInput(std::string name, Port* port);

	/**
     *  @brief get the output Port corresponding to string name
	 *
	 *  @param portName : Name of the input port
	 *
	 *  @return the corresponding Port if port found, otherwise NULL 
	 *
     */
	Port* getOutput(std::string portName);

	/**
     *  @brief Create an output port
	 *
	 *  Create an output port inside the actor similar to the given Port
	 *  
	 *  @param name : string of the port identifier
	 *
	 *  @param port : Port to add in the actor
	 *
     */
	void createOutput(std::string name, Port* port);


	/**
	 * @brief Getter of stateVars
	 *
	 * Returns a map of state variables.
	 * 
	 * @return a map of state variables
	 */
	std::map<std::string, StateVar*>* getStateVars() {
		return stateVars;
	}

	/**
	 * @brief Setter of stateVars
	 * 
	 * @param  a map of state variables
	 */
	void setStateVars(std::map<std::string, StateVar*>* stateVars) {
		this->stateVars = stateVars;
	}

	/**
	 * @brief Getter of a state variable
	 *
	 *  @param name : name of string of the port identifier
	 * 
	 * @return the corresponding state variable
	 */
	StateVar* getStateVar(std::string name);


	/**
	 * @brief Getter of actionScheduler
	 *
	 * Returns the action scheduler of this actor.
	 * 
	 * @return ActionScheduler of this actor
	 */
	ActionScheduler* getActionScheduler() {
		return actionScheduler;
	}

	/**
	 * @brief Setter of actionScheduler
	 * 
	 * @return ActionScheduler of this actor
	 */
	 void setActionScheduler(ActionScheduler* actionScheduler) {
		this->actionScheduler = actionScheduler;
	}

	/**
	 * @brief Getter of procedures
	 *
	 * Returns a map of procedure of this actor.
	 * 
	 * @return a map of Procedure of this actor
	 */
	std::map<std::string, Procedure*>* getProcs() {
		return procedures;
	}

	/**
	 * @brief Setter of procedures
	 * 
	 * @param procs  : a map of Procedure of this actor
	 */
	void setProcs(std::map<std::string, Procedure*>* procs) {
		this->procedures = procs;
	}

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
	 * @brief Get instances of the actor
	 *
	 * Returns a list of instances of this actor in decoders
	 * 
	 * @return a list of Instance
	 */
	std::list<Instance*>* getInstances(){return &instances;};

	/**
     *  @brief Create a port
	 *
	 *  Create a port inside the actor similar to the given Port
	 *  
	 *  @param name : string of the port identifier
	 *
	 *  @param port : Port to add in the actor
	 *
     */
	void createPort(std::string name, Port* port);

	/**
     *  @brief add an port inside the actor
	 *
	 *  Add a new port inside the current list of actor's port. This function does'nt check
	 *   if this port already exist in this actor.
	 *  
	 *  @param name : string of the port identifier
	 *
	 *  @param port : Port to add in the actor
	 *
     */
	void addPort(std::string name, Port* port);

	/**
     *  @brief Indicate whether this actor is parseable or not
	 *  
	 *  @return boolean designing the actor parsing ability
	 *
     */
	virtual bool isParseable(){return true;};

	/**
	 * @brief Returns the MoC of this actor.
	 * 
	 * @return an MoC
	 */
	MoC* getMoC() {
		return moc;
	}

	/**
	 * Sets the MoC of this actor.
	 * 
	 * @param moc : a MoC
	 */
	void setMoC(MoC* moc) {
		this->moc = moc;
	}

	/**
	 * @brief Returns true if this actor is a native actor.
	 * 
	 * @return true if this actor is a system actor,
	 *         false otherwise
	 */
	bool isNative();

protected:
	/** Name of the actor */
	std::string name;

	/** llvm::Module of the actor */
	llvm::Module* module;

	/** Location of the actor */
	std::string file;

	/** A map of the parameters of this actor */
	std::map<std::string, Variable*>* parameters;

	/** MoC of this Actor */
	MoC* moc;

	/** A map of the initialize action of this actor */
	std::list<Action*>* initializes;

	/** A map of the action of this actor */
	std::list<Action*>* actions;
	
	/** Instances of this actor */
	std::list<Instance*> instances;

	/** Input ports of this actor */
	std::map<std::string, Port*>* inputs;
	
	/** Output ports of this actor */
	std::map<std::string, Port*>* outputs;

	/** State variables of this actor */
	std::map<std::string, StateVar*>* stateVars;

	/** Procedures of this actor */
	std::map<std::string, Procedure*>* procedures;

	/** Action scheduler of this actor */
	ActionScheduler* actionScheduler;
};

#endif
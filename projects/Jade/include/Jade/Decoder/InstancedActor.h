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
@brief Description of the InstancedActor class interface
@author Jerome Gorin
@file InstancedActor.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef INSTANCEDACTOR_H
#define INSTANCEDACTOR_H

#include <string>
#include <map>
#include <list>

namespace llvm {
	class GlobalVariable;
	class Function;
}

class Action;
class ActionScheduler;
class Actor;
class Decoder;
class Instance;
class JIT;
class Port;
class Procedure;
class Variable;
class FifoCircular;
//------------------------------

/**
 * @brief  This class defines an instanced Actor inside a decoder.
 *
 * @author Jerome Gorin
 * 
 */
class InstancedActor {
public:
	InstancedActor(Decoder* decoder, Instance* instance,
				std::map<Port*, llvm::GlobalVariable*>* inputs,
				std::map<Port*, llvm::GlobalVariable*>* outputs,
				std::map<Variable*, llvm::GlobalVariable*>* stateVars,
				std::map<Variable*, llvm::GlobalVariable*>* parameters,
				std::map<Procedure*, llvm::Function*>* procedures,
				std::list<Action*>* actions,
				ActionScheduler* scheduler);

	~InstancedActor();

	/**
     *  @brief Getter of the action scheduler of this instanced functional unit
   	 *
	 *  @return ActionScheduler of the instanced functional unit
     */
	ActionScheduler* getActionScheduler(){return scheduler;};

	/**
     *  @brief get the Port llvm::GlobalVariable corresponding to the port
	 *
	 *  @param port : Port to look for
	 *
	 *  @return the corresponding llvm::GlobalVariable 
	 *
     */
	llvm::GlobalVariable* getVar(Port* port);

	/**
     *  @brief get the output Port llvm::GlobalVariable corresponding to the port
	 *
	 *  @param port : Port to look for
	 *
	 *  @return the corresponding llvm::GlobalVariable 
	 *
     */
	llvm::GlobalVariable* getOutputVar(Port* port);

	/**
     *  @brief get the input Port llvm::GlobalVariable corresponding to the port
	 *
	 *  @param port : Port to look for
	 *
	 *  @return the corresponding llvm::GlobalVariable  
	 *
     */
	llvm::GlobalVariable* getInputVar(Port* port);

	/**
     *  @brief get the llvm::Function corresponding to the procedure
	 *
	 *  @param parameter : Procedure to look for
	 *
	 *  @return the corresponding llvm::Function  
	 *
     */
	llvm::Function* getProcedureVar(Procedure* procedure);

	/**
     *  @brief get the llvm::GlobalVariable corresponding to the parameter
	 *
	 *  @param parameter : Variable of the parameter
	 *
	 *  @return the corresponding llvm::GlobalVariable  
	 *
     */
	llvm::GlobalVariable* getParameterVar(Variable* parameter);

	/**
     *  @brief get the Parameter corresponding to the given name
	 *
	 *  @param name : string name of the paramter
	 *
	 *  @return the corresponding Variable  
	 *
     */
	Variable* getParameter(std::string name);


	/**
     *  @brief get the llvm::GlobalVariable corresponding to the parameter
	 *
	 *  @param parameter : Variable of the parameter
	 *
	 *  @return the corresponding llvm::GlobalVariable  
	 *
     */
	//llvm::GlobalVariable* getParameterVar(Variable* parameter);

	/**
     *  @brief add an input connection to the instance
     *
	 *  Add a connection to the instanced actor
	 *
	 *  @param port : Port from which the port is connected
	 *
	 *  @param variable : llvm::GlobalVariable of the port
	 *
     */
	void addInputConnection(Port* port, llvm::GlobalVariable* variable);


	/**
     *  @brief add an output connection to the instance
     *
	 *  Add a connection to the instanced actor
	 *
	 *  @param port : Port from which the port is connected
	 *
	 *  @param variable : llvm::GlobalVariable of the port
	 *
     */
	void addOutputConnection(Port* port, llvm::GlobalVariable* variable);


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

	/**
     *  @brief get the instance corresponding to the InstancedActor
	 *
	 *
	 *  @return the corresponding Instance
	 *
     */
	Instance* getInstance(){return instance;};

	/**
     *  @brief get actions of the InstancedActor
	 *
	 *
	 *  @return a list of actions
	 *
     */
	std::list<Action*>* getActions(){return actions;};

	llvm::GlobalVariable* getStateVar(Variable* stateVar);


private:
	/** Original actor of this instance */
	Actor* actor;

	/** GlobalVariable container */
	std::map<Variable*, llvm::GlobalVariable*>* parameters;
	std::map<Variable*, llvm::GlobalVariable*>* stateVars;
	std::map<Procedure*, llvm::Function*>* procedures;

	/** Action scheduler of the instanced FU */
	ActionScheduler* scheduler;

	/** Name of the instance */
	std::string name;

	/** Instance corresponding to this FU */
	Instance* instance;
	
	/** Decoder Module */
	Decoder* decoder;

	/** List of port from the actor */
	std::map<std::string, Port*> inputsName;
	std::map<std::string, Port*> outputsName;
	std::map<Port*, llvm::GlobalVariable*>* inputs;
	std::map<Port*, llvm::GlobalVariable*>* outputs;

	/** List of connected port of the instance */
	std::map<Port*, llvm::GlobalVariable*> inputConnection;
	std::map<Port*, llvm::GlobalVariable*> outputConnection;

	/** Jit of decoder engine */
	JIT* jit;

	/** Fifo bounds to the instanced FU */
	FifoCircular* fifo;

	/** Action list of the instanced Functional Unit */
	std::list<Action*>* actions;
};

#endif
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
@brief Description of the IRWriter class interface
@author Jerome Gorin
@file IRWriter.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef IRWRITER_H
#define IRWRITER_H

#include <map>

namespace llvm{
	class ConstantInt;
	class Module;
}

class Decoder;
class JIT;
class LLVMWriter;

#include "Jade/Core/Actor.h"
#include "Jade/Core/Instance.h"
//------------------------------


/**
 * @class IRWriter
 *
 * @brief This class defines a writer that serializes an actor in IR form to an instance in LLVM form.
 *
 * @author Jerome Gorin
 * 
 */
class IRWriter{
public:

	/**
	 * Creates an instance writer on the given actor.
	 * 
	 * @param actor an actor
	 */
	IRWriter(Actor* actor, Instance* instance);

	/**
	 * Write the instance inside the given decoder.
	 * 
	 * @param decoder: the decoder to write the instance into
     *
	 * @return true if the actor is written, otherwise false
	 */
	bool write(Decoder* decoder);

	~IRWriter();

private:

	/**
	 * @brief Write the instance
	 */
	void writeInstance(Decoder* decoder);

	/**
	 * @brief Writer a list of ports
	 *
	 * Writer a list of ports in the module
	 *
	 * @param module : llvm::Module to parse
	 *
	 * @return a map of ports
	 */
	std::map<std::string, Port*>* writePorts(std::map<std::string, Port*>* ports);

	/**
	 * @brief Write a port
	 *
	 * Write the given Port for an Instance.
	 * 
	 * @param port : the Port to write
	 *
	 * @return the corresponding Port in the decoder
	 */
	Port* writePort(Port* port);

	/**
	 * @brief Write a list of variable
	 *
	 * Write a list of variable for an Instance.
	 * 
	 * @param vars : the variables to write
	 *
	 * @return a map of the corresponding variable in the decoder
	 */
	std::map<std::string, Variable*>* writeVariables(std::map<std::string, Variable*>* vars);

	/**
	 * @brief Write a variable
	 *
	 * Write the given variable for an Instance.
	 * 
	 * @param var : the variable to write
	 *
	 * @return the corresponding variable in the decoder
	 */
	Variable* writeVariable(Variable* var);

	/**
	 * @brief Write initializes actions
	 *
	 * Write the given initializes actions for an Instance.
	 * 
	 * @param initializes : the initializes actions to write
	 *
	 * @return the corresponding initializes actions in the decoder
	 */
	std::list<Action*>* writeInitializes(std::list<Action*>* initializes);

	/**
	 * @brief Write a list of actions
	 *
	 * Write a list of actions for an Instance.
	 * 
	 * @param vars : the actions to write
	 *
	 * @return a map of the actions in the decoder
	 */
	std::list<Action*>* writeActions(std::list<Action*>* actions);

	/**
	 * @brief Write an action
	 *
	 * Write the given action for an Instance.
	 * 
	 * @param action : the action to write
	 *
	 * @return the corresponding action in the decoder
	 */
	Action* writeAction(Action* action);

	/**
	 * @brief Write a procedure
	 *
	 * Write the given procedure for an Instance.
	 * 
	 * @param procedure : the procedure to write
	 *
	 * @return the corresponding procedure in the decoder
	 */
	Procedure* writeProcedure(Procedure* procedure);

	/**
	 * @brief Write a pattern
	 *
	 * Write the given pattern for an Instance.
	 * 
	 * @param action : the pattern to write
	 *
	 * @param ports : the corresponding ports of the pattern
	 *
	 * @return the corresponding pattern in the decoder
	 */
	std::map<Port*, llvm::ConstantInt*>* writePattern(std::map<Port*, llvm::ConstantInt*>* pattern, std::map<std::string, Port*>* ports);

	/**
	 * @brief Write a list of procedures
	 *
	 * Write the given list of procedure for an Instance.
	 * 
	 * @param procs : the procedures to write
	 *
	 * @return the corresponding procedures in the decoder
	 */
	std::map<std::string, Procedure*>* writeProcedures(std::map<std::string, Procedure*>* procs);
	
	/**
	 * @brief Write an action scheduler
	 *
	 * Write the given action scheduler for an Instance.
	 * 
	 * @param actionScheduler : the actionScheduler to write
	 *
	 * @return the corresponding actionScheduler in the decoder
	 */
	ActionScheduler* writeActionScheduler(ActionScheduler* actionScheduler);

	/**
	 * @brief Write an FSM
	 *
	 * Write the given FSM for an Instance.
	 * 
	 * @param fsm : the fsm to write
	 *
	 * @return the corresponding fsm in the decoder
	 */
	FSM* writeFSM(FSM* fsm);

	/**
	 * @brief Store the action for a later use.
	 *
	 * Remember this action depending on the given Tag, ie. tagged or not. 
	 * 
	 * @param tag : tag of the action
	 *
	 * @param action : action to remember
	 */
	void putAction(ActionTag* tag, Action* action);

	/**
	 *
	 * @brief Return the corresponding action in the decoder.
	 *
	 * Returns the action associated with the tag inside the decoder.
	 * 
	 * @param action : the action to look for.
	 *
	 * @return the corresponding action in the decoder.
	 */
	Action* getAction(Action* action);

	/** Source actor */
	Actor* actor;

	/** Destinated instance */
	Instance* instance;
	
	/** list of actions of the current instance */
	std::map<std::string, Action*> actions;

	/** list of untagged actions of the instance */
	std::list<Action*> untaggedActions;

	/** list of initialization actions of the instance */
	std::list<Action*>* initializes;

	/** Action scheduler of the instance */
	ActionScheduler* actionScheduler;

	/** Ports of the instance */
	std::map<std::string, Port*>* inputs;
	std::map<std::string, Port*>* outputs;

	/** Variables of the instance */
	std::map<std::string, Variable*>* stateVars;
	std::map<std::string, Variable*>* parameters;

	/**Procedures of the instance */
	std::map<std::string, Procedure*>* procs;
	

	/** writer used to manage LLVM infrastructure */
	LLVMWriter* writer;
};

#endif
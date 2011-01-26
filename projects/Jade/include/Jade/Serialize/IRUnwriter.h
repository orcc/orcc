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
@brief Description of the IRUnwriter class interface
@author Jerome Gorin
@file IRUnwriter.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef IRUNWRITER_H
#define IRUNWRITER_H

#include <map>

namespace llvm{
	class ConstantInt;
	class Module;
}

class Connection;
class Decoder;
class JIT;
class LLVMWriter;

#include "Jade/Core/Actor.h"
#include "Jade/Core/Network/Instance.h"
//------------------------------


/**
 * @class IRUnwriter
 *
 * @brief This class defines a unwriter that removes instances from a decoder.
 *
 * @author Jerome Gorin
 * 
 */
class IRUnwriter{
public:

	/**
	 * @brief Creates an instance remove for the given decoder.
	 * 
	 * @param decoder : Decoder where instances are erased
	 */
	IRUnwriter(Decoder* decoder);

	/**
	 * @brief Unwrite the instance from the given decoder.
	 * 
	 * @param instance: the Instance to remove
     *
	 * @return true if the actor is unwritten, otherwise false
	 */
	int remove(Instance* instance);

	~IRUnwriter();

private:
	/**
	 * @brief Unwrite a list of ports
	 *
	 * Erase a list of ports in the module
	 *
	 * @param key : whether ports are input or output ports
	 *
	 * @param ports : map of Port to erase
	 */
	void unwritePorts(std::string key, std::map<std::string, Port*>* ports);

	/**
	 * @brief Unwrite a port
	 *
	 * Erase the given Port from an Instance.
	 * 
	 * @param key : whether the ports is an input or output port
	 *
	 * @param port : the Port to write
	 */
	void unwritePort(std::string key, Port* port);

	/**
	 * @brief Unwrite a list of state variable
	 *
	 * Erase a list of statevariable from a decoder.
	 * 
	 * @param vars : a list of state variable to erase
	 */
	void unwriteStateVariables(std::map<std::string, StateVar*>* vars);

	/**
	 * @brief Unwrite a variable
	 *
	 * Erase the given state variable from the decoder.
	 * 
	 * @param var : the state variable to erase
	 */
	void unwriteStateVariable(StateVar* var);

	/**
	 * @brief Unwrite a list of variable
	 *
	 * Erase a list of variable from a decoder.
	 * 
	 * @param vars : the variables to erase
	 */
	void unwriteVariables(std::map<std::string, Variable*>* vars);

	/**
	 * @brief Unwrite a variable
	 *
	 * Erase the given variable from the decoder.
	 * 
	 * @param var : the variable to erase
	 */
	void unwriteVariable(Variable* var);

	/**
	 * @brief Unwrite an action scheduler
	 *
	 * erase the given action scheduler from the decoder.
	 * 
	 * @param actionScheduler : the actionScheduler to erase
	 */
	void unwriteActionScheduler(ActionScheduler* actionScheduler);

	/**
	 * @brief Unwrite a list of actions
	 *
	 * Erase a list of actions from a decoder.
	 * 
	 * @param actions : a list of Action to write
	 */
	void unwriteActions(std::list<Action*>* actions);

	/**
	 * @brief Unwrite an FSM
	 *
	 * Erase the given FSM from the decoder.
	 * 
	 * @param fsm : the fsm to erase
	 */
	void unwriteFSM(FSM* fsm);

	/**
	 * @brief Unwrite an action
	 *
	 * Erase the given action from a decoder.
	 * 
	 * @param action : the action to erase
	 */
	void unwriteAction(Action* action);

	/**
	 * @brief Unwrite initializes actions
	 *
	 * erase the given initializes actions from a decoder.
	 * 
	 * @param initializes : the initializes actions to erase
	 */
	void unwriteInitializes(std::list<Action*>* initializes);

	/**
	 * @brief Unwrite a procedure
	 *
	 * Erase the given procedure from a decoder.
	 * 
	 * @param procedure : the procedure to erase
	 */
	void unwriteProcedure(Procedure* procedure);

	/**
	 * @brief Unwrite a list of procedures
	 *
	 * Erase the given list of procedure from a decoder.
	 * 
	 * @param procs : the procedures to erase
	 */
	void unwriteProcedures(std::map<std::string, Procedure*>* procs);

	/** Decoder where instance is unwrite*/
	Decoder* decoder;

};

#endif
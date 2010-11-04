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
@brief Description of the IRParser class interface
@author Jerome Gorin
@file IRParser.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef IRPARSER_H
#define IRPARSER_H

namespace llvm{
	class ConstantInt;
	class Module;
	class MDNode;
	class Type;
}

#include "Jade/Core/Actor/ActionTag.h"

class Action;
class ActionScheduler;
class ActionTag;
class Location;
class FSM;
class JIT;
class Port;
class Procedure;
class Variable;
//------------------------------


/**
 * @class IRParser
 *
 * @brief This class defines a parser that loads a module and translate it into Actor form.
 *
 * @author Jerome Gorin
 * 
 */
class IRParser{
public:

	/*!
     *  @brief Constructor
     *
	 * Creates an IRParser.
	 *
	 * @param C : llvm::LLVMContext of environnement
	 * 
	 * @param jit : JIT used to parse actor
	 *
	 * @param fifo : AbstractFifo used in actors of the VTL
	 *
     */
	IRParser(llvm::LLVMContext& C, JIT* jit, AbstractFifo* fifo);

	~IRParser();

	/**
     *  @brief Parse the given actor
     *
	 *  Parse the actor corresponding to the given classz and
	 *   create a new Actor
	 *
	 * @param classz : classz corresponding to the actor
	 *
	 * @return actor resulting from the classz
	 *
     */
	Actor* parseActor(std::string classz);

private:

	/**
     * @brief parse a state variable
	 *
	 * Parses the given llvm::MDNode as a state variables. 
	 * 
	 * @param module : the llvm::MDNode to parse
	 *
	 * @return the corresponding Variable.
	 */
	Variable* parseStateVar(llvm::MDNode* node);

	/**
     * @brief parse state variables
	 *
	 * Parses the given module as a map of state variables. 
	 * 
	 * @param module : the llvm::Module to parse
	 *
	 * @return A map of Variable.
	 */
	std::map<std::string, Variable*>* parseStateVars(llvm::Module* module);

	/**
     * @brief parse an action scheduler
	 *
	 * Parses the given module as an action scheduler. 
	 * 
	 * @param module : the llvm::Module to parse
	 *
	 * @return an action scheduler.
	 */
	ActionScheduler* parseActionScheduler(llvm::Module* module);

	/**
     * @brief parse parameters of a module
	 *
	 * Parses the given module as a list of parameters.
	 * 
	 * @param module : the llvm::Module to parse
	 *
	 * @return a list of parameters.
	 */
	std::map<std::string, Variable*>* parseParameters(llvm::Module* module);


	std::map<Port*, llvm::ConstantInt*>* parsePattern(std::map<std::string, Port*>* ports, llvm::Value* value);

	/**
     * @brief parse procedures of a module
	 *
	 * Parses the given module as a list of procedures.
	 * 
	 * @param module : the llvm::Module to parse
	 *
	 * @return a map of procedure.
	 */
	std::map<std::string, Procedure*>* parseProcs(llvm::Module* module);

	/**
     * @brief parse an MDNode as a procedure
	 *
	 * Parses the given MDNode as a procedure.
	 * 
	 * @param node : the llvm::MDNode to parse
	 *
	 * @return a Procedure.
	 */
	Procedure* parseProc(llvm::MDNode* node);

	/**
	 * @brief Parses a list of ports
	 *
	 * Parses the given module to produce a list of ports.
	 * 
	 * @param key : whether ports are input or output ports
	 *
	 * @param module : llvm::Module to parse
	 *
	 * @return a map of ports
	 */
	std::map<std::string, Port*>* parsePorts(std::string key, llvm::Module* module);

	/**
	 * @brief Parses fifo structures
	 *
	 * Parses the given module to produce a map of fifo structure.
	 *
	 * @param module : llvm::Module to parse
	 *
	 * @return a map of llvm::StructType corresponding to an llvm::Type
	 */
	std::map<std::string, llvm::Type*>* parseFifos(llvm::Module* module);

	
	/**
	 * @brief Parses a fifo structure
	 *
	 * Parses the given fifo structure in the module
	 *
	 * @param name : std::string of the structure name
	 *
	 * @param module : llvm::Module to parse
	 *
	 * @return the corresponding llvm::Type
	 */
	llvm::Type* parseFifo(std::string name, llvm::Module* module);

	/**
	 * @brief Parses a port
	 *
	 * Parses the llvm::node as a port.
	 * 
	 * @param node : llvm::MDNode to parse
	 *
	 * @return the corresponding Port
	 */
	Port* parsePort(llvm::MDNode* node);
	
	/**
	 *  @brief Parse a type
	 *
	 * Parses the given node as an llvm::Type definition.
	 * 
	 * @param module : llvm::MDNode representing a type
	 *
	 * @return the corresponging llvm::IntegerType
	 */
	Location* parseLocation(llvm::MDNode* node);

	/**
	 *  @brief Parse a type
	 *
	 * Parses the given node as an llvm::Type definition.
	 * 
	 * @param module : llvm::MDNode representing a type
	 *
	 * @return the corresponging llvm::IntegerType
	 */
	llvm::Type* parseType(llvm::MDNode* node);


	/**
	 *  @brief Parse actions
	 *
	 * Parses the given llvm::Module as a list of actions.
	 * 
	 * @param module : llvm::Module representing actions.
	 *
	 * @return a list of actions
	 */
	std::list<Action*>* parseActions(std::string key, llvm::Module* module);


	/**
	 *  @brief Parse an FSM
	 *
	 * Parses the given llvm::NODE as an fsm.
	 * 
	 * @param node : llvm::MDNode representing actions.
	 *
	 * @return the corresponding FSM
	 */
	FSM* parseFSM(llvm::MDNode* node);
	
	/**
	 *  @brief Parse an action
	 *
	 * Parses the given llvm::MDNode as an action.
	 * 
	 * @param node : an llvm::MDNode that defines an action
	 *
	 * @return the corresponding Action
	 */
	Action* parseAction(llvm::MDNode* node);

	/**
	 * @brief Returns the action corresponding to the llvm::Node
	 *
	 * Returns the action associated with the tag represented by the given llvm::Node
	 * 
	 * @param node : llvm::Node corresponding to an action
	 * @return the action (or initialize) associated with the tag
	 */
	Action* getAction(llvm::MDNode* node);


	void putAction(ActionTag* tag, Action* action);


	/** JIT used to load bitcode */
	JIT* jit;

private:

	/** list of actions of the current actor */
	std::map<std::string, Action*> actions;

	/** list of untagged actions of the current actor */
	std::list<Action*> untaggedActions;

	/** list of ports of the current actor */
	std::map<std::string, Port*>* inputs;
	std::map<std::string, Port*>* outputs;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Abstract fifos of actors */
	AbstractFifo* fifo;
};

#endif
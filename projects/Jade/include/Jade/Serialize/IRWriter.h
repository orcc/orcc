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
	void writeInstance();

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

	/** Source actor */
	Actor* actor;
	
	/** list of actions of the current instance */
	std::map<std::string, Action*> actions;

	/** list of untagged actions of the current instance */
	std::list<Action*> untaggedActions;

	/** Destinated instance */
	Instance* instance;

	/** writer used to manage LLVM infrastructure */
	LLVMWriter* writer;
};

#endif
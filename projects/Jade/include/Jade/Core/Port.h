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
@brief Description of the Connection class interface
@author Jerome Gorin
@file Connection.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef PORT_H
#define PORT_H

#include <string>
#include <list>
namespace llvm {
	class Type;
	class StringRef;
	class GlobalVariable;
}

#include "Jade/Core/Type.h"

class Actor;
class AbstractFifo;
//------------------------------


/**
 * @class Connection
 *
 * @brief  This class defines a Connection
 *
 * This class represents a connection in a network. A connection can have a
 * number of attributes, that can be Types or Expression.
 * 
 * @author Jerome Gorin
 * 
 */

class Port {
public:
	/**
	 * @brief Constructor
	 *
	 * Creates a new Port on an actor with the given location, Type, name and llvm variable.
	 *
	 * @param Type		:	the Port Type
	 * @param name		:	the Port name
	 * @param variable	:	the llvm variable corresponding to this port
	 */
	Port(std::string name, llvm::Type* type, llvm::GlobalVariable* variable){
		this->name = name; 
		this->type = type; 
		this->variable = variable;
	};

	/**
	 * @brief Constructor
	 *
	 * Creates a new Port on an actor with the given location, Type, name.
	 *
	 * @param Type		:	the Port Type
	 * @param name		:	the Port name
	 */
	Port(std::string name, llvm::Type* type){
		this->name = name; 
		this->type = type; 
		this->variable = NULL;
	};

	/**
	 * @brief Destructor
	 *
	 */
	~Port(){};


	/**
	 * @brief Getter of name
	 *
	 * Get the name of the port
	 * 
	 * @return name of the port
	 *
	 */
	std::string getName(){return name;};

	/**
	 * @brief Getter of type
	 *
	 * Get the type of the port
	 * 
	 * @return Type of the port
	 *
	 */
	llvm::Type* getType(){return type;};

	/**
	 * @brief Setter of type
	 *
	 * Set the type of the port
	 * 
	 * @param type : llvm::Type of the port
	 *
	 */
	void setType(llvm::Type* type){this->type = type;};


	/**
	 * @brief Add a fifo connection to the port
	 *
	 * Add a new fifo connectioned to this port
	 * 
	 * @return ir::Type of the port
	 *
	 */
	void addFifoConnection(AbstractFifo* fifo){fifos.push_back(fifo);};

	/**
	 * @brief Get the number of fifo connected to this port
	 * 
	 * @return Number of fifo connected to the port
	 *
	 */
	int getFifoConnectionNb(){return fifos.size();};

	/**
	 * @brief Getter the of variable
	 * 
	 * Get the llvm::GlobalVariable that corresponds to the Port
	 *
	 * @return corresponding llvm::GlobalVariable
	 */
	llvm::GlobalVariable* getGlobalVariable(){return variable;};


	/**
	 * @brief Setter the of variable
	 * 
	 * Set the llvm::GlobalVariable that corresponds to the Port
	 *
	 * @param variable : llvm::GlobalVariable that corresponds to the port
	 */
	void setGlobalVariable(llvm::GlobalVariable* variable){this->variable = variable;};

protected:
	
	/** name of this port. */
	std::string name;
	
	/** type of this port. */
	llvm::Type* type;

	/** the number of tokens consumed by this port. */
	int tokensConsumed;

	/** the number of tokens produced by this port. */
	int tokensProduced;

	/** Fifos bound to the port */
	std::list<AbstractFifo*> fifos;
	
	/** Corresponding global variable*/
	llvm::GlobalVariable* variable;
};
#endif
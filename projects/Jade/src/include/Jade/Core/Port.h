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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef PORT_H
#define PORT_H

#include <string>
#include <list>
namespace llvm {
	class IntegerType;
	class StringRef;
	class GlobalVariable;
	class Value;
}

#include "Jade/Core/IRType.h"

class Actor;
class AbstractConnector;
class Variable;
//------------------------------


/**
 * @class Port
 *
 * @brief  This class defines a Port in a Network.
 * 
 * @author Jerome Gorin
 * 
 */

class Port {
public:
	/**
	 * @brief Constructor
	 *
	 * Creates a new Port on an actor with the given location, Type, name.
	 *
	 * @param Type		:	the Port Type
	 * @param name		:	the Port name
	 */
	Port(std::string name, llvm::IntegerType* type){
		this->name = name; 
		this->type = type; 
		this->ptrVar = NULL;
		this->fifoVar = NULL;
		this->index = NULL;
		this->numTokenFree = NULL;
		this->intern = false;
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
	llvm::IntegerType* getType(){return type;};

	/**
	 * @brief Setter of type
	 *
	 * Set the type of the port
	 * 
	 * @param type : llvm::Type of the port
	 *
	 */
	void setType(llvm::IntegerType* type){this->type = type;};


	/**
	 * @brief Add a fifo connection to the port
	 *
	 * Add a new fifo connectioned to this port
	 * 
	 * @param fifo: the new fifo to add
	 *
	 */
	void addFifoConnection(AbstractConnector* fifo){fifos.push_back(fifo);};

	/**
	 * @brief Get fifo connections to the port
	 * 
	 * @return a list of fifos
	 *
	 */
	std::list<AbstractConnector*>* getFifoConnections(){return &fifos;};

	/**
	 * @brief Get the number of fifo connected to this port
	 * 
	 * @return Number of fifo connected to the port
	 *
	 */
	int getFifoConnectionNb(){return fifos.size();};

	/**
	 * @brief Getter the port pointer
	 * 
	 * Get the llvm::GlobalVariable that corresponds to the Port pointer
	 *
	 * @return the corresponding Variable
	 */
	Variable* getPtrVar(){return ptrVar;};

	/**
	 * @brief Set the port as internal
	 *
	 * @param the corresponding Variable
	 */
	void setInternal(bool intern){this->intern = intern;};

	/**
	 * @brief Set the port as internal
	 *
	 * @return whether or not this port is internal
	 */
	bool isInternal(){return intern;};

	/**
	 * @brief Getter fifo variable
	 * 
	 * Get the llvm::GlobalVariable that corresponds to the Port fifo
	 *
	 * @return corresponding llvm::GlobalVariable
	 */
	llvm::GlobalVariable* getFifoVar(){return fifoVar;};


	/**
	 * @brief Setter of the port pointer
	 * 
	 * Set the llvm::GlobalVariable that corresponds to the Port pointer
	 *
	 * @param variable : llvm::GlobalVariable that corresponds to the port pointer
	 */
	void setPtrVar(Variable* ptrVar){this->ptrVar = ptrVar;};

	/**
	 * @brief Setter of the port pointer
	 * 
	 * Set the Variable that corresponds to the Port pointer
	 *
	 * @param variable : the Variable that corresponds to the port pointer
	 */
	void setFifoVar(llvm::GlobalVariable* fifoVar){this->fifoVar = fifoVar;};

	/**
	 * @brief Setter of the index pointer
	 * 
	 * Set the llvm::GlobalVariable that corresponds to the index variable
	 *
	 * @param variable : llvm::GlobalVariable that corresponds to the index
	 */
	void setIndex(llvm::GlobalVariable* index){this->index = index;};

	/**
	 * @brief getter of the index pointer
	 * 
	 * Get the llvm::GlobalVariable that corresponds to the index variable
	 *
	 * @return llvm::GlobalVariable that corresponds to the index
	 */
	llvm::GlobalVariable* getIndex(){return index;};

	/**
	 * @brief Setter of the room/hasToken pointer
	 * 
	 * Set the llvm::GlobalVariable that corresponds to the room or hasToken variable
	 *
	 * @param variable : llvm::GlobalVariable that corresponds to the room or hasToken
	 */
	void setRoomToken(llvm::GlobalVariable* roomToken){this->numTokenFree = roomToken;};

	/**
	 * @brief Setter of the room/hasToken pointer
	 * 
	 * Get the llvm::GlobalVariable that corresponds to the room or hasToken variable
	 *
	 * @return llvm::GlobalVariable that corresponds to the room or hasToken
	 */
	llvm::GlobalVariable* getRoomToken(){return numTokenFree;};

	/**
	 * @brief Setter of the id pointer
	 * 
	 * Set the llvm::GlobalVariable that corresponds to the id of the port
	 *
	 * @param variable : llvm::GlobalVariable that corresponds to the id of the port
	 */
	void setId(llvm::GlobalVariable* id){this->id = id;};

	/**
	 * @brief Getter of the id pointer
	 * 
	 * Get the llvm::GlobalVariable that corresponds to the id of the port
	 *
	 * @return llvm::GlobalVariable that corresponds to the id of the port
	 */
	llvm::GlobalVariable* getId(){return id;}

	/**
	 * @brief Set of the fifo size of the port
	 *
	 * @param size : fifo size
	 */
	void setSize(int size){this->size = size;}

	/**
	 * @brief Get of the fifo size of the port
	 *
	 * @return fifo size
	 */
	int getSize(){return size;}

protected:
	
	/** name of this port. */
	std::string name;
	
	/** type of this port. */
	llvm::IntegerType* type;

	/** the number of tokens consumed by this port. */
	int tokensConsumed;

	/** the number of tokens produced by this port. */
	int tokensProduced;

	/** Fifos bound to the port */
	std::list<AbstractConnector*> fifos;
	
	/** Corresponding global variable pointer */
	Variable* ptrVar;

	/** Corresponding global variable index */
	llvm::GlobalVariable* index;

	/** Corresponding global variable numFree/TokenFree */
	llvm::GlobalVariable* numTokenFree;

	/** Corresponding global variable id */
	llvm::GlobalVariable* id;


	/** Corresponding fifo size */
	int size;


	/** Internal port */
	bool intern;

	/** Corresponding global variable fifo*/
	llvm::GlobalVariable* fifoVar;
};
#endif
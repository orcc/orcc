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
@brief Description of the Port class
@author Jerome Gorin
@file Port.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef PORT_H
#define PORT_H

#include <string>
#include <list>
#include <set>

namespace llvm {
	class IntegerType;
	class StringRef;
	class GlobalVariable;
	class Value;
}

#include "Jade/Core/IRType.h"
#include "Jade/Core/Network/Vertex.h"

class Actor;
class AbstractConnector;
class Connection;
class HDAGGraph;
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
	 * @brief Creates a new port in a graph
	 *
	 * @param name	:  name of the port
	 * @param type	:  type of the port
	 * @param graph :  parent graph of the port
	 */
	Port(std::string name, llvm::IntegerType* type, HDAGGraph* graph) {
		this->name = name; 
		this->type = type; 
		this->ptrVar = NULL;
		this->fifoVar = NULL;
		this->index = NULL;
		this->numTokenFree = NULL;
		this->intern = false;
		this->read = false;
		this->write = false;
		this->graph = graph;
		this->instance = NULL;
		this->actor = NULL;
	};

	/**
	 * @brief Creates a new port in an actor
	 *
	 * @param name	:  name of the port
	 * @param type	:  type of the port
	 * @param actor :  parent actor of the port
	 */
	Port(std::string name, llvm::IntegerType* type, Actor* actor) {
		this->name = name; 
		this->type = type; 
		this->ptrVar = NULL;
		this->fifoVar = NULL;
		this->index = NULL;
		this->numTokenFree = NULL;
		this->intern = false;
		this->read = false;
		this->write = false;
		this->graph = NULL;
		this->instance = NULL;
		this->actor = actor;
	};

	/**
	 * @brief Creates a new port in an instance
	 *
	 * @param name	:  name of the port
	 * @param type	:  type of the port
	 * @param actor :  parent actor of the port
	 */
	Port(std::string name, llvm::IntegerType* type, Instance* instance) {
		this->name = name; 
		this->type = type; 
		this->ptrVar = NULL;
		this->fifoVar = NULL;
		this->index = NULL;
		this->numTokenFree = NULL;
		this->intern = false;
		this->read = false;
		this->write = false;
		this->graph = NULL;
		this->instance = instance;
		this->actor = NULL;
	};


	/**
	 * @brief Destructor
	 *
	 */
	~Port(){};


	/**
	 * @brief Get name of the port
	 * 
	 * @return name of the port
	 *
	 */
	std::string getName(){return name;};

	/**
	 * @brief Get the type of the port
	 * 
	 * @return Type of the port
	 */
	llvm::IntegerType* getType(){return type;};

	/**
	 * @brief Set the type of the port
	 * 
	 * @param type : llvm::Type of the port
	 */
	void setType(llvm::IntegerType* type){this->type = type;};

	/**
	 * @brief Get instance bound to the port
	 * 
	 * @return the instance bound to the port
	 */
	Instance* getInstance(){return instance;};

	/**
	 * @brief Set instance bound to the port
	 * 
	 * @param instance : the instance bound to the port
	 */
	void setInstance(Instance* instance){this->instance = instance;};

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
	 * @brief Set access of the port
	 *
	 * @param read : whether or not this port has read access
	 *
	 * @param write : whether or not this port has write acces
	 */
	void setAccess(bool read, bool write){
		this->read = read;
		this->write = write;
	}

	/**
	 * @brief Return true if the port can be read 
	 *
	 * @return true if the port can be read
	 */
	bool isReadable(){ return read;};
	
	/**
	 * @brief Return true if we can write into the port
	 *
	 * @return true if the port can be writen
	 */
	bool isWritable(){ return write;};

	std::set<Connection*>* getConnections();
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

	/** Whether or not this port can be read*/
	bool read;

	/** Whether or not this port can be writen */
	bool write;

	/** Internal port */
	bool intern;

	/** Corresponding global variable fifo*/
	llvm::GlobalVariable* fifoVar;

	/** Parent graph of the port */
	HDAGGraph* graph;

	/** Parent instance of the port */
	Instance* instance;

	/** Parent actor of the port */
	Actor* actor;
};

#endif
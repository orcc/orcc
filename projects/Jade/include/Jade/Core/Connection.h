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
#ifndef Connection_H
#define Connection_H
#include <stdlib.h>

#include <map>
#include <string>

#include "Jade/Graph/HDAGEdge.h"

namespace llvm{
	class Constant;
	class IntegerType;
	class GlobalVariable;
}

class Port;
class Attribute;
class Expr;

#define SIZE 10000
//------------------------------

/**
*
* @class Connection
* @brief  This class defines a Connection in a Network.
*
* This class represents a Connection in a Network. A Connection can have a
* number of Attribute, that can be ir::Type or Expr.
*
* @author Jerome Gorin
*
*/
class Connection : public HDAGEdge {

public:
	/**
     *  @brief Constructor
     *
	 * Creates a connection from source port to target port with the given
	 * attributes.
	 *
	 * @param source : source port
	 *
	 * @param target : target port
	 *
	 * @param parameters : map of Attr that contains actor parameters
	 *
     */
	Connection (Port* source, Port* target, std::map<std::string, Attribute*>* attributes);

	~Connection ();

	/*!
     *  @brief Getter of the source port
     *
	 *  @return source port.
     *
     */
	Port* getSourcePort(){return source;};

    /*!
     *  @brief Getter of the source port
     *
	 *  @return source port.
     *
     */
	void getSourcePort(Port* source){this->source = source;};

	/*!
     *  @brief Getter of the destination port
     *
	 *  @return destination port.
     *
     */
	Port* getDestinationPort(){return target;};


	/*!
     *  @brief Setter of the destination port
     *
	 *  @param target: the destination Port.
     */
	void setDestinationPort(Port* target){this->target = target;};

	/*!
     *  @brief Setter of the source port
     *
	 *  @param source: the source Port.
     */
	void setSourcePort(Port* source){this->source = source;};



	/*!
     *  @brief Getter of attributes
     *
	 *  @return a map of Attribute.
     *
     */
	std::map<std::string, Attribute*>* getAttributes(){return attributes;};

	/*!
     *  @brief Get integer size of the connection
     *
	 *  @return an integer representing the size of the connection.
     *
     */
	int getFifoSize();

	/*!
     *  @brief Get fifo bound to the connection
     *
	 *  @return a llvm::GlobalVariable representing the fifo of the connection
     *
     */
	llvm::GlobalVariable* getFifo(){return fifo;};

	/*!
     *  @brief set the fifo bound to the connection
     *
	 *  @param fifo: a llvm::GlobalVariable representing the fifo of the connection
     *
     */
	 void setFifo(llvm::GlobalVariable* fifo){this->fifo = fifo;};

	/*!
     *  @brief Get integer size of the token carried by this connection
     *
	 *  @return an integer representing the size of token.
     *
     */
	int getType();

	llvm::IntegerType* getIntegerType(){return type;};

	void setType(llvm::IntegerType* type) {this->type = type;};

private:
	/*!
     *  @brief Evaluate this expression as an integer
     *
	 *  @return an integer value of the expression.
     */
	int evaluateAsInteger(Expr* expr);

	std::map<std::string, Attribute*>* attributes;	/** Map of attributes */
	Port* source;						/** Source Port */
	Port* target;						/** Destination Port */
	llvm::IntegerType* type;			/** Type of the connection */
	llvm::GlobalVariable* fifo;			/** Fifo of the connection */
};

#endif

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
@brief Description of the Connector class interface
@author Jerome Gorin
@file Connector.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef CONNECTOR_H
#define CONNECTOR_H

namespace llvm{
	class Module;
}

class Connection;
class Configuration;
class Decoder;

#include "llvm/LLVMContext.h"

#include "Jade/Fifo/AbstractFifo.h"
//------------------------------

/**
 * @class Connector
 *
 * @brief This class connects instance from a network to fifos.
 *
 * @author Jerome Gorin
 * 
 */
class Connector {
public:

	/**
	 * @brief Constructor of connector.
	 *
	 * 
	 * @param C : the LLVMContext
	 *
	 * @param decoder : the Decoder where connections are printed
	 *
	 */
	Connector(llvm::LLVMContext& C, Decoder* decoder);

	~Connector();

	/**
	 * @brief print connections in the given configuration.
	 *
	 */
	void setConnections(Configuration* configuration);

	/**
	 * @brief Remove connections
	 *
	 * Remove connections from the given configuration.
	 * 
	 * @param configuration : the Configuration where connections are removed
	 */
	void unsetConnections(Configuration* configuration);


private:
	/**
	 * @brief Print a connection
	 * 
	 * @param connection : the Connection to print
	 */
	void setConnection(Connection* connection);
	
	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Decoder to print connection */
	Decoder* decoder;

};

#endif
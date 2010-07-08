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
@brief Description of the UnprotectedFifo class interface
@author Jerome Gorin
@file FifoCircular.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef UNPROTECTEDFIFO_H
#define UNPROTECTEDFIFO_H

#include "Jade/Fifo/AbstractFifo.h"
//------------------------------

/**
 * @brief  This class defines UnprotectedFifo.
 * 
 * @author Jerome Gorin
 * 
 */
class UnprotectedFifo: public AbstractFifo {
private:
	/** Fifo function name */
	std::map<std::string,std::string> fifoMap()
	{
		std::map<std::string,std::string> fifo;
		fifo["peek"] = "getPeekPtr";
		fifo["write"] = "getWritePtr";
		fifo["read"] = "getReadPtr";
		fifo["hasToken"] = "hasTokens";
		fifo["hasRoom"] = "hasRoom";
		fifo["writeEnd"] = "setWriteEnd";
		fifo["readEnd"] = "setReadEnd";
		
		fifo["char_peek"] = "fifo_char_peek";
		fifo["char_write"] = "fifo_char_write";
		fifo["char_read"] = "fifo_char_read";
		fifo["char_hasToken"] = "fifo_char_has_tokens";
		fifo["char_hasRoom"] = "fifo_char_has_room";
		fifo["char_writeEnd"] = "fifo_char_write_end";
		fifo["char_readEnd"] = "fifo_char_read_end";

		fifo["printf"] = "printf";
		return fifo;
	}

	/** Fifo function name */
	std::map<std::string,std::string> structMap()
	{
		std::map<std::string,std::string> fifoStruct;	
		fifoStruct["default"] = "struct.fifo_s";
		fifoStruct["char_s"] = "struct.fifo_char_s";
		fifoStruct["int_s"] = "struct.fifo_int_s";
		fifoStruct["short_s"] = "struct.fifo_short_s";
		fifoStruct["u_char_s"] = "struct.fifo_u_char_s";
		fifoStruct["u_int_s"] = "struct.fifo_u_int_s";
		fifoStruct["u_short_s"] = "struct.fifo_u_short_s";
		return fifoStruct;
	}

public:
	/**
     *  @brief Constructor
     *
	 *	Load and add fifo declaration inside the given decoder
	 *
	 *  @param jit : JIT use to load bitcoder
	 *
     */
	UnprotectedFifo(llvm::LLVMContext& C, JIT* jit);

	/**
     *  @brief Constructor
     *
	 *	Declare fifo type
	 *
     */
	UnprotectedFifo(llvm::LLVMContext& C);
	
	~UnprotectedFifo();

	/**
     *  @brief Getter of fifo structure
     *
	 *	Return the llvm::Type of the fifo structure
	 *
	 *  @return llvm::Type of the fifo
	 *
     */
	void addFifoHeader(Decoder* decoder);

	void setConnection(Connection* connection);
	

private:

	/** Decoder engine's jit */
	JIT* jit;

	/** Counter of fifo */
	int fifoCnt;

	/** Other functions declared in the header */
	std::list<llvm::Function*> otherFunctions;

	/** LLVM Context */
	llvm::LLVMContext &Context;
	
	/**
    *  @brief Parse fifo module
    */
	void parseHeader();

	/**
    * @brief add fifo structure
	*
	* @return llvm::Type of the fifo structure
    */
	void addFifoType(Decoder* decoder);

	/**
    * @brief add fifo functions into the given decoder
	*
	* @param decoder : Decoder to had fifo functions
    */
	void parseFifoFunctions();

	void parseFifoStructs();

	/**
    * @brief add fifo function corresponding to the given name into the given decoder
	*
	* @param name : string of the function name into the header
	*
	* @param decoder : Decoder to had fifo function
	*
	* @return llvm::Function of the fifo function into the final decoder
    */
	void addFunctions(Decoder* decoder);

	/**
    * @brief declareFifoHeader inside decoder
    */
	void declareFifoHeader();

};

#endif
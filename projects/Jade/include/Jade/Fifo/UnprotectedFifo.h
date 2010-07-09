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
	
		fifo["i8_peek"] = "fifo_i8_peek";
		fifo["i8_write"] = "fifo_i8_write";
		fifo["i8_read"] = "fifo_i8_read";
		fifo["i8_hasToken"] = "fifo_i8_has_tokens";
		fifo["i8_hasRoom"] = "fifo_i8_has_room";
		fifo["i8_writeEnd"] = "fifo_i8_write_end";
		fifo["i8_readEnd"] = "fifo_i8_read_end";

		fifo["i32_peek"] = "fifo_i32_peek";
		fifo["i32_write"] = "fifo_i32_write";
		fifo["i32_read"] = "fifo_i32_read";
		fifo["i32_hasToken"] = "fifo_i32_has_tokens";
		fifo["i32_hasRoom"] = "fifo_i32_has_room";
		fifo["i32_writeEnd"] = "fifo_i32_write_end";
		fifo["i32_readEnd"] = "fifo_i32_read_end";

		fifo["i16_peek"] = "fifo_i16_peek";
		fifo["i16_write"] = "fifo_i16_write";
		fifo["i16_read"] = "fifo_i16_read";
		fifo["i16_hasToken"] = "fifo_i16_has_tokens";
		fifo["i16_hasRoom"] = "fifo_i16_has_room";
		fifo["i16_writeEnd"] = "fifo_i16_write_end";
		fifo["i16_readEnd"] = "fifo_i16_read_end";

		fifo["u_i8_peek"] = "fifo_u_i8_peek";
		fifo["u_i8_write"] = "fifo_u_i8_write";
		fifo["u_i8_read"] = "fifo_u_i8_read";
		fifo["u_i8_hasToken"] = "fifo_u_i8_has_tokens";
		fifo["u_i8_hasRoom"] = "fifo_u_i8_has_room";
		fifo["u_i8_writeEnd"] = "fifo_u_i8_write_end";
		fifo["u_i8_readEnd"] = "fifo_u_i8_read_end";

		fifo["u_i32_peek"] = "fifo_u_i32_peek";
		fifo["u_i32_write"] = "fifo_u_i32_write";
		fifo["u_i32_read"] = "fifo_u_i32_read";
		fifo["u_i32_hasToken"] = "fifo_u_i32_has_tokens";
		fifo["u_i32_hasRoom"] = "fifo_u_i32_has_room";
		fifo["u_i32_writeEnd"] = "fifo_u_i32_write_end";
		fifo["u_i32_readEnd"] = "fifo_u_i32_read_end";

		fifo["i8_peek"] = "fifo_i8_peek";
		fifo["i8_write"] = "fifo_i8_write";
		fifo["i8_read"] = "fifo_i8_read";
		fifo["i8_hasToken"] = "fifo_i8_has_tokens";
		fifo["i8_hasRoom"] = "fifo_i8_has_room";
		fifo["i8_writeEnd"] = "fifo_i8_write_end";
		fifo["i8_readEnd"] = "fifo_i8_read_end";

		fifo["printf"] = "printf";
		return fifo;
	}

	/** Fifo function name */
	std::map<std::string,std::string> structMap()
	{
		std::map<std::string,std::string> fifoStruct;	
		fifoStruct["char_s"] = "struct.fifo_i8_s";
		fifoStruct["int_s"] = "struct.fifo_i32_s";
		fifoStruct["short_s"] = "struct.fifo_i16_s";
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

	void setConnection(Connection* connection);
	

private:

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

	llvm::StructType* getFifoType(llvm::IntegerType* type);

};

#endif
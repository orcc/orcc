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
@brief Description of the LLVMExecution interface
@author Jerome Gorin
@file LLVMExecution.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef LLVMEXECUTION_H
#define LLVMEXECUTION_H

namespace llvm{
	class Function;
	class ExecutionEngine;
	class Module;
}

#include <pthread.h>

#include "llvm/LLVMContext.h"

class AbstractFifo;
class Procedure;
class Port;
//------------------------------

/**
 * @brief  This class manages the LLVM infrastructure to write elements
 * 
 * @author Jerome Gorin
 * 
 */
class LLVMExecution {
public:

	/**
     *  @brief Constructor
     *
	 *	Initialize the execution engine
	 *
     */
	LLVMExecution(llvm::LLVMContext& C, Decoder* decoder);

	/**
     *  @brief Destructor
     *
	 *	Delete the execution engione
     */
	~LLVMExecution();

	/**
     *  @brief map a function in the decider
     *
	 *	Map an external procedure in the decoder
	 *
	 *  @param procedure : the procedure to map
	 *
	 *  @param adrr : mapping address of the procedure
     */
	void mapProcedure(Procedure* procedure, void *Addr);

	/**
     *  @brief Set the input stimulus for the  decoder
     *
	 *	@param input : input string of the decoder
     */
	void setInputStimulus(std::string input);

	/**
     *  @brief run the current decoder
     *
	 *	Run the decoder using the given function name
     */
	void run();

	/**
     *  @brief stop the current decoder
     *
	 *	Stop the decoder
     */
	void stop(pthread_t* thread);

	/**
     *  @brief cleart the current decoder
     *
	 *	Clear the decoder
     */
	void clear();

	void* getExit(); 
	void recompile(llvm::Function* function);
	void mapFifo(Port* port, AbstractFifo* fifo);

private:

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Module that representing the decoder*/
	Decoder* decoder;

	/** Execution engine*/
	llvm::ExecutionEngine *EE;

	/** Exit function */
	llvm::Function *Exit;	

	/** Result of the execution */
	int result;
};

#endif
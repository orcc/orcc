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
@brief Description of the AbstractFifo class interface
@author Jerome Gorin
@file AbstractFifo.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef ABSTRACTFIFO_H
#define ABSTRACTFIFO_H
#include <string>
#include <map>

#include "llvm/GlobalVariable.h"

namespace llvm{
	class LLVMContext;
	class Type;
	class GlobalVariable;
	class Module;
}

class Connection;
//------------------------------
/**
 * @brief  This class defines an abstract fifo.
 * 
 * @author Jerome Gorin
 * 
 */
class AbstractFifo{
public:
	/**
     *  @brief Constructor of fifo
	 *
	 *  @param connection : the Connection representing the fifo
	 *
     */
	AbstractFifo(llvm::LLVMContext& C, llvm::Module* module, llvm::Type* type, int size) : Context(C){
		this->connection;
		this->module = module;
		this->fifoType = type;
		this->fifoSize = size;
	};

	/**
     *  @brief Get the llvm::GlobalVariable that represents the fifo
	 *
	 *  @return the llvm::GlobalVariable of the fifo
	 *
     */
	llvm::GlobalVariable* getGV(){
		return fifoGV;
	}

	virtual ~AbstractFifo(){
		delete fifoGV;
	};


protected:
	/** Fifo builder */
	virtual void createConnection(){};

	/** Size of the fifo */
	int fifoSize;

	/** llvm::Type of the fifo */
	llvm::Type* fifoType;

	/** llvm::GlobalVariable of the fifo */
	llvm::GlobalVariable* fifoGV;
		
	/** Module where fifo is instancied */
	llvm::Module* module;

	/** Connection representing the fifo*/
	Connection* connection;

	/** LLVM Context */
	llvm::LLVMContext &Context;
};

#endif
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
@brief Description of the FifoCircular class interface
@author Jerome Gorin
@file FifoOpt.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef FIFOOPT_H
#define FIFOOPT_H

namespace llvm{
	class Constant;
}

#include "Jade/Fifo/AbstractFifo.h"
//------------------------------

/**
 * @brief  This class defines a Circular Opt.
 * 
 * @author Jerome Gorin
 * 
 */

class FifoOpt : public AbstractFifo {
	public:
		FifoOpt(llvm::LLVMContext& C, llvm::Module* module, llvm::Type* type, int size);

		~FifoOpt();

	protected:
		llvm::GlobalVariable* ArrayFifoBuffer;
		llvm::GlobalVariable* ArrayContent;

		virtual void createConnection();
	
	public:
		static llvm::StructType* getOrInsertFifoStruct(llvm::Module* module, llvm::IntegerType* connectionType);
		static llvm::Function* getOrInsertRoomFn(llvm::Module* module, llvm::IntegerType* connectionType);
		static llvm::Function* getOrInsertNumTokensFn(llvm::Module* module, llvm::IntegerType* connectionType);
		static llvm::Function* initializeIn(llvm::Module* module, Port* port);
		static llvm::Function* initializeOut(llvm::Module* module, Port* port);

		static llvm::Function* closeIn(llvm::Module* module, Port* port);
		static llvm::Function* closeOut(llvm::Module* module, Port* port);
};

#endif

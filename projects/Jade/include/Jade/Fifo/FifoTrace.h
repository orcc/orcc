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
@brief Description of the FifoTrace class interface
@author Jerome Gorin
@file FifoTrace.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef FIFOTRACE_H
#define FIFOTRACE_H

#include "Jade/Fifo/AbstractFifo.h"
class Network;
//------------------------------

/**
 * @brief  This class defines Fifo that traces its values.
 * 
 * @author Jerome Gorin
 * 
 */

class FifoTrace : public AbstractFifo {
public:
	FifoTrace(llvm::LLVMContext& C, Connection* connection, llvm::Module* module, Network* network, llvm::Type* type, int size, std::string outputDir) : AbstractFifo(C, module, type, size) {
		this->OutputDir = outputDir;
		this->network = network;
		this->connection = connection;
		this->outputDir = outputDir;
		createConnection();
	};


	~FifoTrace(){};
	
protected:
		llvm::GlobalVariable *fifo;
		llvm::Constant* size;
		llvm::Constant* read_ind;
		llvm::Constant* write_ind;
		llvm::Constant* fill_count;
		llvm::GlobalVariable* contents;
		llvm::GlobalVariable* fifo_buffer;
		llvm::GlobalVariable* file;
		std::string OutputDir;
		void createConnection();
		Network* network;
		std::string outputDir;
	};

#endif
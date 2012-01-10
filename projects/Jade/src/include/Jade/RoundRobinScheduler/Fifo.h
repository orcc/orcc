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
@file Fifo.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef FIFOOPT_H
#define FIFOOPT_H

namespace llvm{
	class BasicBlock;
	class Constant;
	class ConstantInt;
	class IntegerType;
	class GlobalVariable;
	class GetElementPtrInst;
	class Function;
	class LLVMContext;
	class Module;
	class StructType;
	class Type;
	class Value;
}

class Action;
class Pattern;
class Procedure;
class Port;
//------------------------------

/**
 * @brief  This class defines a Circular Opt.
 * 
 * @author Jerome Gorin
 * 
 */

class FifoOpt {
	public:
		FifoOpt(llvm::LLVMContext& C, llvm::Module* module, llvm::Type* type, int size);

		~FifoOpt();
	
	public:
		static llvm::StructType* getOrInsertFifoStruct(llvm::Module* module, llvm::IntegerType* connectionType);
		static llvm::Function* getOrInsertRoomFn(llvm::Module* module, llvm::IntegerType* connectionType);
		static llvm::Function* getOrInsertNumTokensFn(llvm::Module* module, llvm::IntegerType* connectionType);
		static llvm::Function* initializeIn(llvm::Module* module, Port* port);
		static llvm::Function* initializeOut(llvm::Module* module, Port* port);

		static llvm::Function* closeIn(llvm::Module* module, Port* port);
		static llvm::Function* closeOut(llvm::Module* module, Port* port);
		llvm::GlobalVariable* getGV(){return fifoGV;}

	/**
	 * @brief Creates read/write/peek accesses
	 *
	 * @param action : action where accesses are added
	 */
	static void createReadWritePeek(Action* action, bool debug = false);

	/**
	 * @brief Creates a hasToken test for a Port
	 * 
	 * @param port : the Port to test
	 *
	 * @param BB : llvm::BasicBlock where test is add
	 *
	 * @param incBB : llvm::BasicBlock where test has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where test has to branch in case of return
	 *
	 * @param function : llvm::Function where the test is added
	 */
	static llvm::Value* createInputTest(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

	/**
	 * @brief Creates a hasRoom test for a Port
	 * 
	 * @param port : the Port to test
	 *
	 * @param BB : llvm::BasicBlock where test is added
	 *
	 * @param incBB : llvm::BasicBlock where test has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where test has to branch in case of return
	 *
	 * @param function : llvm::Function where the test is added
	 */
	static llvm::Value* createOutputTest(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

private:

	/**
	 * @brief Creates write accesses
	 *
	 * @param procedure : procedure where write is added
	 *
	 * @parm pattern : the writing pattern
	 */
	static void createWrites (Procedure* procedure, Pattern* pattern);

	/**
	 * @brief Creates read accesses
	 *
	 * @param procedure : procedure where read is added
	 *
	 * @parm pattern : the read pattern
	 */
	static void createReads (Procedure* procedure, Pattern* pattern);

	/**
	 * @brief Creates peek accesses
	 *
	 * @param procedure : procedure where peek is added
	 *
	 * @parm pattern : the peek pattern
	 */
	static void createPeeks (Procedure* procedure, Pattern* pattern);

	/**
	 * @brief Port procedure variable
	 *
	 * @param port : the port to get the variable from
	 *
	 * @parm procedure : the procedure to get var from
	 */
	static llvm::Value* replaceAccess (Port* port, Procedure* proc);

	
	/**
	 * @brief Trace value in port
	 *
	 * @param port : the port to trace
	 *
	 * @parm gep : the gep instruction that contains the value
	 */
	static void createFifoTrace(llvm::Module* module, Port* port, llvm::GetElementPtrInst* gep);

private:
	llvm::GlobalVariable* fifoGV;
	llvm::GlobalVariable* gv_array;
	llvm::GlobalVariable* gv_read_inds;

	// Display debugging information
	static bool debug;
};

#endif

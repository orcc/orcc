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
@brief Description of the DPNScheduler interface
@author Jerome Gorin
@file DPNScheduler.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef OPTSCHEDULER_H
#define OPTSCHEDULER_H

#include <list>
#include <map>

#include "DPNScheduler.h"
#include "Jade/Core/Actor/FSM.h"

namespace llvm{
	class BasicBlock;
	class ConstantInt;
	class Function;
	class LLVMContext;
	class CallInst;
	class Value;
}

class Action;
class ActionScheduler;
class Actor;
class Instance;
class Decoder;
class Port;
//------------------------------

/**
 * @brief  This class defines an action scheduler for a DPN actor.
 * 
 * @author Jerome Gorin
 * 
 */
class OPTScheduler : public DPNScheduler {
public:
	/**
     *  @brief Constructor
     *
	 *	Create a new action scheduler for dynamic actors
	 *
	 *	@param C : the llvm::Context
	 *
	 *	@param decoder : the Decoder where dynamic action scheduler is inserted
     */
	OPTScheduler(llvm::LLVMContext& C, Decoder* decoder);
	~OPTScheduler(){};

protected:
	
	/**
     *  @brief Create the scheduler of actions
	 *
	 * @param instance: the Instance to add the scheduler
	 *
	 * @param BB : llvm::BasicBlock where scheduler is add
	 *
	 * @param incBB : llvm::BasicBlock where scheduler has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where scheduler has to branch in case of return
	 *
	 * @param function : llvm::Function where the scheduler is added
	 */
	virtual void createScheduler(Instance* instance, llvm::BasicBlock* BB, llvm::BasicBlock* incBB, llvm::BasicBlock* returnBB, llvm::Function* scheduler);

		/**
	 * @brief Create a peek for an Action
	 * 
	 * @param port : the port to peek
	 *
	 * @param variable : the Variable where peek is stored
	 *
	 * @param numToken : ConstantInt representing the number of tokens to peek
	 *
	 * @param BB : llvm::BasicBlock where instructions are added
	 */
	virtual void createPeek(Port* port, Variable* variable, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

	/**
	 * @brief Create a read for an Action
	 * 
	 * @param port : the port to read
	 *
	 * @param variable : the Variable where read is stored
	 *
	 * @param numToken : ConstantInt representing the number of tokens to read
	 *
	 * @param BB : llvm::BasicBlock where instructions are added
	 */
	virtual void createRead(Port* port, Variable* variable, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

	/**
	 * @brief Create a ReadEnd for an Action
	 * 
	 * @param port : the port to set to ReadEnd
	 *
	 * @param numToken : ConstantInt representing the number of tokens that has been read
	 *
	 * @param BB : llvm::BasicBlock where instructions are added
	 */
	virtual void createReadEnd(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

	/**
	 * @brief Create a WriteEnd for an Action
	 * 
	 * @param port : the port to set to WriteEnd
	 *
	 * @param numToken : ConstantInt representing the number of tokens that has been read
	 *
	 * @param BB : llvm::BasicBlock where instructions are added
	 */
	virtual void createWriteEnd(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

	/**
	 * @brief Create a write for an Action
	 * 
	 * @param port : the port to write
	 *
	 * @param variable : the Variable where write is stored
	 *
	 * @param numToken : ConstantInt representing the number of tokens to write
	 *
	 * @param BB : llvm::BasicBlock where instructions are added
	 */
	void createWrite(Port* port, Variable* variable, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);


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
	virtual llvm::Value* createInputTest(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);

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
	virtual llvm::Value* createOutputTest(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);
};

#endif
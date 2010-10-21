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
@brief Description of the ActionSchedulerAdder interface
@author Jerome Gorin
@file ActionSchedulerAdder.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef ACTIONSCHEDULERADDER_H
#define ACTIONSCHEDULERADDER_H

#include <list>
#include <map>
#include "Jade/Actor/FSM.h"

namespace llvm{
	class BasicBlock;
	class ConstantInt;
	class Function;
	class LLVMContext;
	class CallInst;
}

class Action;
class ActionScheduler;
class Actor;
class InstancedActor;
class Instance;
class Decoder;
class Port;
//------------------------------

/**
 * @brief  This class defines a robin robin scheduler of a decoder.
 * 
 * @author Jerome Gorin
 * 
 */
class ActionSchedulerAdder {
public:
	/**
     *  @brief Constructor
     *
	 *	Create a new round robin scheduler for the given decoder
	 *
	 *	@param jit : JIT for including instructions
	 *
	 *	@param decoder : the Decoder to insert the round robin scheduler into
     */
	ActionSchedulerAdder(Instance* instance, Decoder* decoder, llvm::LLVMContext& C);
	~ActionSchedulerAdder(){};

private:
	void createScheduler(ActionScheduler* actionScheduler);
	llvm::Function* createSchedulerFn(ActionScheduler* actionScheduler);
	llvm::BasicBlock* createSchedulerNoFSM(std::list<Action*>* actions, llvm::BasicBlock* BB, llvm::BasicBlock* returnBB, llvm::BasicBlock* incBB, llvm::Function* function);
	llvm::BasicBlock* createSchedulerFSM(ActionScheduler* actionScheduler, llvm::BasicBlock* BB, llvm::BasicBlock* returnBB, llvm::BasicBlock* incBB, llvm::Function* function);
	llvm::BasicBlock* createActionTest(Action* action, llvm::BasicBlock* BB, llvm::BasicBlock* incBB, llvm::Function* function);
	llvm::BasicBlock* createOutputPattern(Action* action, llvm::BasicBlock* BB, llvm::Function* function);
	llvm::CallInst* createOutputTest(Port* port, llvm::ConstantInt* numTokens, llvm::BasicBlock* BB);
	void createSwitchTransitions(std::map<std::string, FSM::Transition*>* transitions);
	void createSwitchTransition(FSM::Transition* transition);
	void createTransitions(std::map<std::string, FSM::Transition*>* transitions);
	void createTransition(FSM::Transition* transition);
	void createSchedulingTestState(std::list<FSM::NextStateInfo*>* nextStates);

	/** LLVM Context */
	llvm::LLVMContext &Context;
	Decoder* decoder;
	InstancedActor* instancedActor;
	Instance* instance;
	Actor* actor;
};

#endif
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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef DPNSCHEDULER_H
#define DPNSCHEDULER_H

#include <list>
#include <map>

#include "ActionSchedulerAdder.h"
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
class DPNScheduler : public ActionSchedulerAdder {
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
	DPNScheduler(llvm::LLVMContext& C, Decoder* decoder);
	~DPNScheduler(){};

private:
	
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
	void createScheduler(Instance* instance, llvm::BasicBlock* BB, llvm::BasicBlock* incBB, llvm::BasicBlock* returnBB, llvm::Function* scheduler);

	/**
     *  @brief Create scheduler of action outside the FSM
	 *
	 *	Create an action scheduler for the action outside the FSM
	 *
	 *  @param instance : the Instance to add the action scheduler
     */
	llvm::Function* createSchedulerOutsideFSM(Instance* instance);

	/**
	 * @brief Creates states of the FSM
	 *
	 * Creates a basic bloc for each states of the FSM
	 * 
	 * @param states: map of FSM::State to create
	 *
	 * @param function : llvm::Function where basic blocs are added
	 *
	 * @return a map of FSM::State and their corresponding llvm::BasicBlock
	 */
	std::map<FSM::State*, llvm::BasicBlock*>* createStates(std::map<std::string, FSM::State*>* states, llvm::Function* function);

	/**
	 * @brief Creates a scheduler with no FSM
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
	void createSchedulerNoFSM(Instance* instance, llvm::BasicBlock* BB, llvm::BasicBlock* incBB, 
											llvm::BasicBlock* returnBB, llvm::Function* function);


	/**
	 * @brief Creates a scheduler with FSM
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
	void createSchedulerFSM(Instance* instance, llvm::BasicBlock* BB, llvm::BasicBlock* returnBB, 
								llvm::BasicBlock* incBB, llvm::Function* function);

	/**
	 * @brief Creates a scheduling test for an Action
	 * 
	 * @param action : the Action to test
	 *
	 * @param BB : llvm::BasicBlock where test is add
	 *
	 * @param incBB : llvm::BasicBlock where test has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where test has to branch in case of return
	 *
	 * @param function : llvm::Function where the test is added
	 */
	llvm::BasicBlock* createActionTest(Action* action, llvm::BasicBlock* BB, 
										llvm::BasicBlock* incBB, llvm::Function* function);

	/**
	 * @brief Create a Action execution
	 * 
	 * @param action : the action to execute
	 *
	 * @param BB : llvm::BasicBlock where instructions are added
	 */
	void createActionCall(Action* action, llvm::BasicBlock* BB);

	/**
	 * @brief Creates switcth instruction for the FSM
	 * 
	 * @param value : the condition llvm::Value of switch instruction
	 *
	 * @param BB : llvm::BasicBlock where switch is add
	 *
	 * @param incBB : llvm::BasicBlock where switch has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where switch has to branch in case of return
	 *
	 * @param BBTransitions : map of FSM::State and their corresponding llvm::BasicBlock
	 */
	void createSwitchTransition(llvm::Value* stateVar, llvm::BasicBlock* BB, llvm::BasicBlock* returnBB, 
		std::map<FSM::State*, llvm::BasicBlock*>* BBTransitions);


	/**
	 * @brief Creates transitions for the FSM
	 * 
	 * @param transitions : the transitions to create
	 *
	 * @param BB : llvm::BasicBlock where transitions are added
	 *
	 * @param incBB : llvm::BasicBlock where transitions has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where transitions has to branch in case of return
	 *
	 * @param function : llvm::Function where the transitions are added
	 *
	 * @param outsideSchedulerFn : llvm::Function to call for outside scheduler functions
	 *
	 * @param BBTransitions : map of FSM::State and their corresponding llvm::BasicBlock
	 */
	void createTransitions(std::map<std::string, FSM::Transition*>* transitions, llvm::BasicBlock* incBB, 
							llvm::BasicBlock* returnBB, llvm::GlobalVariable* stateVar, llvm::Function* function, 
							llvm::Function* outsideSchedulerFn, std::map<FSM::State*, llvm::BasicBlock*>* BBTransitions);


	/**
	 * @brief Creates a transition
	 * 
	 * @param transitions : the transition to create
	 *
	 * @param BB : llvm::BasicBlock where transition is added
	 *
	 * @param incBB : llvm::BasicBlock where transition has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where transition has to branch in case of return
	 *
	 * @param function : llvm::Function where the transition is added
	 *
	 * @param outsideSchedulerFn : llvm::Function to call for outside scheduler functions
	 *
	 * @param BBTransitions : map of FSM::State and their corresponding llvm::BasicBlock
	 */
	void createTransition(FSM::Transition* transition, llvm::BasicBlock* incBB, llvm::BasicBlock* returnBB, llvm::GlobalVariable* stateVar, llvm::Function* function, llvm::Function* outsideSchedulerFn, std::map<FSM::State*, llvm::BasicBlock*>* BBTransitions);
	
	/**
	 * @brief Creates a test for FSM to  change state
	 * 
	 * @param nextStates : the next state of the transition
	 *
	 * @param sourceState :  the source state of the transition
	 *
	 * @param incBB : llvm::BasicBlock where transition has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where transition has to branch in case of return
	 *
	 * @param function : llvm::Function where the transition is added
	 *
	 * @param outsideSchedulerFn : llvm::Function to call for outside scheduler functions
	 *
	 * @param BBTransitions : map of FSM::State and their corresponding llvm::BasicBlock
	 */
	llvm::BasicBlock* createSchedulingTestState(std::list<FSM::NextStateInfo*>* nextStates, FSM::State* sourceState, 
													llvm::BasicBlock* incBB, llvm::BasicBlock* returnBB, 
													llvm::GlobalVariable* stateVar, llvm::Function* function, 
													llvm::Function* outsideSchedulerFn, std::map<FSM::State*, 
													llvm::BasicBlock*>* BBTransitions);


	/**
	 * @brief Creates an action test state for FSM
	 * 
	 * @param nextStates : the next state of the transition
	 *
	 * @param sourceState :  the source state of the transition
	 *
	 * @param incBB : llvm::BasicBlock where transition has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where transition has to branch in case of return
	 *
	 * @param function : llvm::Function where the transition is added
	 *
	 * @param outsideSchedulerFn : llvm::Function to call for outside scheduler functions
	 *
	 * @param BBTransitions : map of FSM::State and their corresponding llvm::BasicBlock
	 */
	llvm::BasicBlock* createActionTestState(FSM::NextStateInfo* nextStateInfo, FSM::State* sourceState, 
												llvm::BasicBlock* stateBB, llvm::BasicBlock* incBB, 
												llvm::BasicBlock* returnBB, llvm::GlobalVariable* stateVar, 
												llvm::Function* function, std::map<FSM::State*, llvm::BasicBlock*>* BBTransitions);


	/**
	 * @brief Creates an action call for FSM
	 * 
	 * @param nextStateInfo : FSM::NextStateInfo of the current state
	 *
	 * @param BB : llvm::BasicBlock where the action call is added
	 *
	 * @param BBTransitions : map of FSM::State and their corresponding llvm::BasicBlock
	 */
	void createActionCallState(FSM::NextStateInfo* nextStateInfo, llvm::BasicBlock* BB, std::map<FSM::State*, llvm::BasicBlock*>* BBTransitions);
};

#endif
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
@brief Description of the ActionScheduler interface
@author Jerome Gorin
@file ActionScheduler.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef ACTIONSCHEDULER_H
#define ACTIONSCHEDULER_H

#include <string>
#include <list>

namespace llvm {
	class Function;
}

class Procedure;
class Action;
class FSM;
//------------------------------

/**
 * @brief  This class defines an action scheduler for a Functional Unit.
 * 
 * @author Jerome Gorin
 * 
 */
class ActionScheduler {
public:
	/**
     *  @brief Constructor
     *
	 *	Create a new Action Scheduler without fsm
	 *
	 *	@param function : llvm::Function corresponding to the action scheduler
     */
	ActionScheduler(std::list<Action*>* actions, llvm::Function* schedulerFunction, llvm::Function* initializeFunction, FSM* fsm){
		this->fsm = fsm;
		this->actions = actions;
		this->schedulerFunction = schedulerFunction;
		this->initializeFunction = initializeFunction;
	};

	ActionScheduler(std::list<Action*>* actions, llvm::Function* initializeFunction, FSM* fsm){
		this->fsm = fsm;
		this->actions = actions;
		this->schedulerFunction = NULL;
		this->initializeFunction = initializeFunction;
	};

	/**
     *  @brief Getter of scheduler function
     *
	 *	Return the corresponding llvm::function of the action scheduler
	 *
	 *	@return corresponding llvm::Function  of action scheduler
     */
	llvm::Function* getSchedulerFunction(){ return schedulerFunction;};

	/**
     *  @brief Setter of scheduler function
     *
	 *	Set the corresponding llvm::function of the action scheduler
	 *
	 *	@param corresponding llvm::Function  of action scheduler
     */
	 void setSchedulerFunction(llvm::Function* schedulerFunction){ this->schedulerFunction = schedulerFunction;};

	/**
     *  @brief Getter of initialize scheduler function
     *
	 *	Return the corresponding llvm::function of the initialize scheduler
	 *
	 *	@return corresponding llvm::Function  of initialize scheduler
     */
	llvm::Function* getInitializeFunction(){ return initializeFunction;};

	/**
     *  @brief Getter of fsm
     *
	 * Returns the FSM of this action scheduler, or NULL if it does
	 * not have one.
	 *
	 *	@return the FSM of the action scheduler
     */
	FSM* getFsm(){ return fsm;};

	/**
     *  @brief Returns true if this action scheduler has an FSM.
	 *
	 *	@return true if the action scheduler has an FSM
     */
	bool hasFsm(){ return fsm != NULL;};

	/**
	 *  @brief Returns actions of ActionScheduler.
	 *
	 * Returns the actions that are outside of an FSM. If this action scheduler
	 * has no FSM, all actions of the actor are returned. The actions are sorted
	 * by decreasing priority.
	 * 
	 * @return a list of actions
	 */
	std::list<Action*>* getActions() {
		return actions;
	}

	/**
     *  @brief Returns true if this action scheduler has a scheduler function.
	 *
	 *	@return true if the action scheduler has a scheduler function.
     */
	bool hasInitializeScheduler(){ return initializeFunction != NULL;};


	~ActionScheduler();

private:
	/** llvm::Function corresponding to the action scheduler */
	llvm::Function* schedulerFunction;

	/** llvm::Function corresponding to the intialize action scheduler */
	llvm::Function* initializeFunction;

	/** FSM controled by the action scheduler */
	FSM* fsm;

	std::list<Action*>* actions;
};

#endif
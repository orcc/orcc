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
@brief Description of the FSM interface
@author Jerome Gorin
@file FSM.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef FSM_H
#define FSM_H

#include <map>
#include <list>
#include <string>

namespace llvm {
	class Function;
	class GlobalVariable;
}

class Action;
//------------------------------

/**
 * @brief  This class defines a Finite State Machine (FSM)
 * 
 * @author Jerome Gorin
 * 
 */
class FSM {
public:
	/**
     *  @brief Constructor
     *
	 *	Create a new Action Scheduler without fsm
	 *
	 *	@param function : llvm::Function corresponding to the action scheduler
     */
	FSM(){
		index = 0;
		functions = NULL;
		fsm_state = NULL;
		outFsm = NULL;
	};

	~FSM();

	/**
     *  @brief Set functions used by the fsm
	 *
	 *	@param function : llvm::Function used by the fsm
     */
	void setFunctions(std::list<llvm::Function*>* functions){
		this->functions = functions;
	};

	/**
     *  @brief Get functions used by the fsm
	 *
	 *	@return a list of llvm::Function used by the fsm
     */
	std::list<llvm::Function*>* getFunctions(){ return functions;}


	/**
     *  @brief Set fsm_state used by the fsm
	 *
	 *  Set the llvm::GlobalVariable fsm_state used to manage fsm in llvm
	 *
	 *	@param function : llvm::Function used by the fsm
     */
	void setFsmState(llvm::GlobalVariable* fsm_state){
		this->fsm_state = fsm_state;
	};

	/**
     *  @brief Set outside_fsm used by the fsm
	 *
	 *  Set the llvm::Function outside_fsm used to manage actions outside the fsm.
	 *   This function is set to null when no actions are outside the fsm.
	 *
	 *	@param function : llvm::Function used by the fsm
     */
	void setOutFsm(llvm::Function* outFsm){
		this->outFsm = outFsm;
	};

	/**
     *  @brief Get outside_fsm used by the fsm
	 *
	 *  Get the llvm::Function outside_fsm used to manage actions outside the fsm in llvm
	 *
	 *	@return llvm::Function corresponding to outside_fsm
     */
	llvm::Function* getOutFsm(){ return outFsm;}

	/**
     *  @brief Get fsm_state used by the fsm
	 *
	 *  Get the llvm::GlobalVariable fsm_state used to manage fsm in llvm
	 *
	 *	@return llvm::GlobalVariable corresponding to fsm_state
     */
	llvm::GlobalVariable* getFsmState(){ return fsm_state;}
	

public:
	class State {			
	public:
		/**
		 * @brief Creates a new state
		 *
		 * Creates a new state with the given name and index.
		 * 
		 * @param name : string name of the state
		 * @param index : index of the state
		 */
		State(std::string name, int index){
			this->index = index;
			this->name = name;
		}

		/**
		 * @brief Returns the index of this state.
		 * 
		 * @return the index of this state
		 */
		int getIndex() {
			return index;
		}

		/**
		 * @brief Returns the name of this state.
		 * 
		 * @return the name of this state
		 */
		std::string getName() {
			return name;
		}
		
	private:
		/** index of the state */
		int index;

		/** name of the state */
		std::string name;

	};
	
	class NextStateInfo{
	public:
		NextStateInfo(Action* action, State* state){
			this->action = action;
			this->targetState = state;

		};
	private:
		Action* action;

		State* targetState;
	};

	class Transition {
	public:
		/**
		 * @brief Create a transition
		 *
		 * Creates a transition from a source state.
		 * 
		 * @param sourceState : source State
		 */
		Transition(State* state)
		{
			this->sourceState = state;
		};

		std::list<NextStateInfo*>* getNextStateInfo() {
			return &nextStateInfo;
		}

	private:
		/** next state of the transition */
		std::list<NextStateInfo*> nextStateInfo;

		/** source state */
		State* sourceState;

	};

public:
	/**
	 * @brief add a transition into the fsm
	 *
	 * Adds a transition between two state with the given action.
	 * 
	 * @param source: string name of the source state
	 *
	 * @param target: string name of the target state
	 *
	 * @param action: an Action
	 */
	void addTransition(std::string source, std::string target, Action* action){
		std::map<std::string, State*>::iterator itState;
		std::map<std::string, Transition*>::iterator itTransition;
		
		itState = states.find(target);
		itTransition = transitions.find(source);
		Transition* transition = itTransition->second;

		std::list<NextStateInfo*>* nextState = transition->getNextStateInfo();
		nextState->push_back(new NextStateInfo(action, itState->second));
	}

	/**
	 * @brief add a state into the fsm
	 *
	 * Adds a state with the given name only if the given state is not already
	 * present.
	 * 
	 * @param name : string name of a state
	 *
	 * @return the state created
	 */
	State* addState(std::string name){
		std::map<std::string, State*>::iterator it;
		it = states.find(name);

		if(it == states.end()){
			State* state = new State(name, index++);
			states.insert(std::pair<std::string, State*>(name, state));
			transitions.insert(std::pair<std::string, Transition*>(name, new Transition(state)));
			return state;
		}

		return (*it).second;
	};


	/**
	 * @brief Sets the initial state
	 *
	 * Sets the initial state of this FSM to the given state.
	 * 
	 * @param state
	 *            a state name
	 */
	void setInitialState(std::string state) {
		initialState = addState(state);
	}

	/**
	 * @brief Get the initial state
	 *
	 * Gets the initial state of this FSM.
	 * 
	 * @return the initial state
	 */
	State* getInitialState() {return initialState;};


private:
	/**	index of last state added to the state map */
	int index;

	/** initial state	 */
	State* initialState;

	/** map of state name to state	 */
	std::map<std::string, State*> states;

	/** map of state name to transition	 */
	std::map<std::string, Transition*> transitions;

	/** llvm::Function of the fsm */
	std::list<llvm::Function*>* functions;

	/** llvm::GlobalVariable that contains state of the fsm */
	llvm::GlobalVariable* fsm_state;
	
	/** llvm::Function that contains function outside the fsm */
	llvm::Function* outFsm;
};

#endif
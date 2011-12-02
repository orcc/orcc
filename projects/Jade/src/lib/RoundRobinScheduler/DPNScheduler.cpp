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
@brief Implementation of class DPNScheduler
@author Jerome Gorin
@file DPNScheduler.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"

#include "DPNScheduler.h"

#include "Jade/Decoder.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Actor/Action.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/Actor/ActionTag.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace llvm;
using namespace std;

DPNScheduler::DPNScheduler(llvm::LLVMContext& C, Decoder* decoder) : ActionSchedulerAdder(C, decoder) {

}

void DPNScheduler::createScheduler(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* scheduler){
	if (actionScheduler->hasFsm()){
		createSchedulerFSM(instance, BB, incBB, returnBB , scheduler);
	}else{
		createSchedulerNoFSM(instance, BB, incBB, returnBB, scheduler);
	}
}

void DPNScheduler::createSchedulerFSM(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* function){
	//Create a variable that store the current state of the FSM
	Module* module = decoder->getModule();
	ActionScheduler* actionScheduler = instance->getActionScheduler();
	FSM* fsm = actionScheduler->getFsm();
	string name = instance->getId();
	name.append("_FSM_state");
	Function* outsideSchedulerFn = NULL;
	
	//Create state variable
	GlobalVariable* stateVar = cast<GlobalVariable>(module->getOrInsertGlobal(name, Type::getInt32Ty(Context)));
	fsm->setFsmState(stateVar);
	
	//Set initial state to the state variable
	FSM::State* state = fsm->getInitialState();
	stateVar->setInitializer(ConstantInt::get(Context, APInt(32, state->getIndex())));

	//Load state variable
	LoadInst* loadStateVar = new LoadInst(stateVar, "", BB);

	//Create action outside fsm
	std::list<Action*>* actions = actionScheduler->getActions();
	
	if (!actions->empty()){
		outsideSchedulerFn = createSchedulerOutsideFSM(instance);
		fsm->setOutFsmFn(outsideSchedulerFn);
	}

	//Create fsm scheduler
	map<FSM::State*, BasicBlock*>* BBTransitions = createStates(fsm->getStates(), function);
	createTransitions(fsm->getTransitions(), incBB, returnBB, stateVar, function, outsideSchedulerFn, BBTransitions);
	createSwitchTransition(loadStateVar, BB, returnBB, BBTransitions);
}

Function* DPNScheduler::createSchedulerOutsideFSM(Instance* instance){
	Module* module = decoder->getModule();
	string name = instance->getId();
	name.append("_outside_FSM_scheduler");
	ActionScheduler* actionScheduler = instance->getActionScheduler();
	std::list<Action*>* actions = actionScheduler->getActions();
	
	Function* outsideScheduler = cast<Function>(module->getOrInsertFunction(name, Type::getVoidTy(Context),
										  (Type *)0));
	
	//Create values
	Value *Zero = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Value *One = ConstantInt::get(Type::getInt32Ty(Context), 1);

	// Add a basic block entry and BB to the outside scheduler.
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", outsideScheduler);
	BasicBlock* BB1  = BasicBlock::Create(Context, "bb", outsideScheduler);
	BranchInst::Create(BB1, BBEntry);
	bb1 = BB1;


	//Iterate tough actions
	list<Action*>::iterator it;
	BasicBlock* BB = BB1;	
	for ( it=actions->begin() ; it != actions->end(); it++ ){
		BB = createActionTest(*it, BB, BB1, outsideScheduler);
	}

	//Return if no action can be fired
	ReturnInst::Create(Context, BB);

	return outsideScheduler;
}

void DPNScheduler::createSchedulerNoFSM(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* function){
	list<Action*>::iterator it;
	ActionScheduler* actionScheduler = instance->getActionScheduler();
	list<Action*>* actions = actionScheduler->getActions();

	for ( it=actions->begin() ; it != actions->end(); it++ ){
		BB = createActionTest(*it, BB, incBB, function);
	}

	//Create branch from skip to return
	BranchInst::Create(returnBB, BB);
}

BasicBlock* DPNScheduler::createActionTest(Action* action, BasicBlock* BB, BasicBlock* incBB, Function* function){
	map<Port*, ConstantInt*>::iterator it;
	string name = action->getName();
	string skipBrName = "skip_";
	string hasRoomBrName = "hasroom_";
	string fireBrName = "fire_";
	skipBrName.append(name);
	hasRoomBrName.append(name);
	fireBrName.append(name);
	Procedure* body = action->getBody();

	// Add a basic block to bb for firing instructions
	BasicBlock* fireBB = BasicBlock::Create(Context, fireBrName, function);

	// Add a basic block to bb for ski instructions
	BasicBlock* skipBB = BasicBlock::Create(Context, skipBrName, function);


	//Create check input pattern
	BB = checkInputPattern(action->getInputPattern(), function, skipBB, BB);
	
	//Test firing condition of an action
	checkPeekPattern(action->getPeekPattern(), function, BB);
	Procedure* scheduler = action->getScheduler();
	CallInst* schedInst = CallInst::Create(scheduler->getFunction(), "",  BB);
	BranchInst* branchInst	= BranchInst::Create(fireBB, skipBB, schedInst, BB);

	//Create output pattern
	fireBB = checkOutputPattern(action->getOutputPattern(), function, skipBB, fireBB);
	
	//Execute the action
	createActionCall(action, fireBB);

	//Branch fire basic block to BB basic block
	BranchInst::Create(incBB, fireBB);
	
	return skipBB;
}

void DPNScheduler::createActionCall(Action* action, BasicBlock* BB){
	// Create read/write for the action
	createReads(action->getInputPattern(), BB);
	createWrites(action->getOutputPattern(), BB);
	
	//Launch action body
	Procedure* body = action->getBody();
	CallInst* bodyInst = CallInst::Create(body->getFunction(), "",  BB);

	//Create ReadEnd/WriteEnd
	createReadEnds(action->getInputPattern(), BB);
	createWriteEnds(action->getOutputPattern(), BB);
}

map<FSM::State*, BasicBlock*>* DPNScheduler::createStates(map<string, FSM::State*>* states, Function* function){
	map<string, FSM::State*>::iterator it;
	map<FSM::State*, BasicBlock*>* BBTransitions = new map<FSM::State*, BasicBlock*>();
	
	//Create a basic block for each state
	for (it = states->begin(); it != states->end(); it++){
		// Add a basic block for the current state
		BasicBlock* stateBB = BasicBlock::Create(Context, it->first, function);

		//Store the basic block
		BBTransitions->insert(pair<FSM::State*, BasicBlock*>(it->second, stateBB));
	}

	return BBTransitions;
}

void DPNScheduler::createSwitchTransition(Value* stateVar, BasicBlock* BB, BasicBlock* returnBB, map<FSM::State*, BasicBlock*>* BBTransitions){
	map<FSM::State*, BasicBlock*>::iterator it;

	 SwitchInst* stateSwitch = SwitchInst::Create(stateVar, returnBB, BBTransitions->size(), BB);

	 for (it = BBTransitions->begin(); it != BBTransitions->end(); it++){
		 FSM::State* state = it->first;
		 BasicBlock* stateBB = it->second;
		 ConstantInt* stateIndex = ConstantInt::get(Type::getInt32Ty(Context),state->getIndex());

		 stateSwitch->addCase(stateIndex, stateBB);
	 }
}

void DPNScheduler::createTransitions(map<string, FSM::Transition*>* transitions, BasicBlock* incBB, BasicBlock* returnBB, GlobalVariable* stateVar, Function* function, Function* outsideSchedulerFn, map<FSM::State*, BasicBlock*>* BBTransitions){
	map<string, FSM::Transition*>::iterator it;

	for (it = transitions->begin(); it != transitions->end(); it++){
		createTransition(it->second, incBB, returnBB, stateVar, function, outsideSchedulerFn, BBTransitions);
	}
}

void DPNScheduler::createTransition(FSM::Transition* transition, BasicBlock* incBB, BasicBlock* returnBB, GlobalVariable* stateVar, Function* function, Function* outsideSchedulerFn, map<FSM::State*, BasicBlock*>* BBTransitions){
	createSchedulingTestState(transition->getNextStateInfo(), transition->getSourceState(), incBB, returnBB, stateVar, function, outsideSchedulerFn, BBTransitions);
}

BasicBlock* DPNScheduler::createSchedulingTestState(list<FSM::NextStateInfo*>* nextStates, 
															FSM::State* sourceState, BasicBlock* incBB, 
															BasicBlock* returnBB, GlobalVariable* stateVar, 
															Function* function, Function* outsideSchedulerFn,
															map<FSM::State*, BasicBlock*>* BBTransitions){
	//Get source state basic block
	std::map<FSM::State*, llvm::BasicBlock*>::iterator itState;
	itState = BBTransitions->find(sourceState);
	BasicBlock* stateBB = itState->second;

	if (outsideSchedulerFn != NULL){
		CallInst::Create(outsideSchedulerFn, "", stateBB);
	}

	//Iterate though next states of the transition
	list<FSM::NextStateInfo*>::iterator it;
	for (it = nextStates->begin(); it != nextStates->end(); it++){
		stateBB = createActionTestState(*it, sourceState, stateBB, incBB, returnBB, stateVar, function, BBTransitions);
	}

	//Store current state in skip basic block and brancg to return basic block
	ConstantInt* index = ConstantInt::get(Type::getInt32Ty(Context), sourceState->getIndex());
	StoreInst* storeInst = new StoreInst(index, stateVar, stateBB);
	BranchInst::Create(returnBB, stateBB);

	return NULL;
}

BasicBlock* DPNScheduler::createActionTestState(FSM::NextStateInfo* nextStateInfo, FSM::State* sourceState, BasicBlock* stateBB, BasicBlock* incBB, BasicBlock* returnBB, GlobalVariable* stateVar, Function* function, map<FSM::State*, BasicBlock*>* BBTransitions){
	map<Port*, ConstantInt*>::iterator it;

	//Get information about next state
	Action* action = nextStateInfo->getAction();
	FSM::State* targetState = nextStateInfo->getTargetState();

	//Create a branch for firing next state
	string fireStateBrName = "fire";
	fireStateBrName.append(action->getName());
	BasicBlock* fireStateBB = BasicBlock::Create(Context, fireStateBrName, function);

	//Create a branch for skip next state
	string skipStateBrName = "skip";
	skipStateBrName.append(action->getName());
	BasicBlock* skipStateBB = BasicBlock::Create(Context, skipStateBrName, function);


	//Test input firing condition of an action
	bb1 = stateBB;
	stateBB = checkInputPattern(action->getInputPattern(), function, skipStateBB, stateBB);
	
	//Test firing condition of an action
	checkPeekPattern(action->getPeekPattern(), function, stateBB);
	Procedure* scheduler = action->getScheduler();
	CallInst* callInst = CallInst::Create(scheduler->getFunction(), "",  stateBB);
	BranchInst::Create(fireStateBB, skipStateBB, callInst, stateBB);


	//Create a basic block skip_hasRoom that store state and return from function
	string skipHasRoomBrName = "skipHasRoom";
	skipHasRoomBrName.append(action->getName());
	BasicBlock* skipRoomBB = BasicBlock::Create(Context, skipHasRoomBrName, function);
	ConstantInt* index = ConstantInt::get(Type::getInt32Ty(Context), sourceState->getIndex());
	new StoreInst(index, stateVar, skipRoomBB);
	BranchInst::Create(returnBB, skipRoomBB);

	//Create output pattern
	fireStateBB = checkOutputPattern(action->getOutputPattern(), function, skipRoomBB, fireStateBB);
	
	//Create call state
	createActionCallState(nextStateInfo, fireStateBB, BBTransitions);

	return skipStateBB;
}

void DPNScheduler::createActionCallState(FSM::NextStateInfo* nextStateInfo, llvm::BasicBlock* BB, map<FSM::State*, BasicBlock*>* BBTransitions){
	std::map<FSM::State*, llvm::BasicBlock*>::iterator it;
	
	//Get next state information
	Action* action = nextStateInfo->getAction();
	FSM::State* nextState = nextStateInfo->getTargetState();

	//Execute the action
	createActionCall(action, BB);

	//Create a branch to the next state
	it = BBTransitions->find(nextState);
	BranchInst* brInst = BranchInst::Create(it->second, BB);

}

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
@brief Implementation of class ActionSchedulerAdder
@author Jerome Gorin
@file ActionSchedulerAdder.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>
#include "CSDFScheduler.h"

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"

#include "Jade/Decoder.h"
#include "Jade/Core/MoC/CSDFMoC.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Merger/SuperInstance.h"
//------------------------------

using namespace llvm;
using namespace std;

CSDFScheduler::CSDFScheduler(llvm::LLVMContext& C, Decoder* decoder) : DPNScheduler(C, decoder) {

}

void CSDFScheduler::createScheduler(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* scheduler){
	if (instance->isSuperInstance()){
		// Create internal communication of superinstance
		createStateVars((SuperInstance*)instance);
	}
	
	BB = createPatternTest((CSDFMoC*)instance->getMoC(), BB, incBB, scheduler);

	//Create branch from skip to return
	BranchInst::Create(returnBB, BB);
}

void CSDFScheduler::createStateVars(SuperInstance* instance){
	Module* module = decoder->getModule();
	map<Port*, Port*>::iterator it;
	map<Port*, Port*>* conns = instance->getInternalConnections();
	CSDFMoC* moc = (CSDFMoC*)instance->getMoC();
	Pattern* input = moc->getInputPattern();
	Pattern* output = moc->getOutputPattern();
	map<Port*, StateVar*>* internalVars = new map<Port*, StateVar*>();

	for (it = conns->begin(); it != conns->end(); it++){
		Port* src = it->first;
		Port* dst = it->second;

		ConstantInt* srcPat = input->getNumTokens(dst);
		ConstantInt* dstPat = output->getNumTokens(src);

		if (srcPat->getLimitedValue() != dstPat->getLimitedValue()){
			cout << "Found a consumption/production error between port " << src->getName() << " and port " << dst->getName();
			exit(1);
		}
		

		//Create the corresponding state variable
		string name = src->getName() +"_"+ dst->getName();
		Type* type = ArrayType::get(src->getType(), srcPat->getLimitedValue());
		GlobalVariable* gv = new GlobalVariable(*module, type, false, GlobalVariable::InternalLinkage, ConstantAggregateZero::get(type), name);
		StateVar* stateVar = new StateVar(type, name, true, gv);
		
		// Set state var to port
		src->setFifoVar(gv);
		dst->setFifoVar(gv);
		internalVars->insert(pair<Port*, StateVar*>(src, stateVar));
		internalVars->insert(pair<Port*, StateVar*>(dst, stateVar));
	}

	// Set internal vars of the super instance
	instance->setInternalVars(internalVars);
}

BasicBlock* CSDFScheduler::createPatternTest(CSDFMoC* moc, BasicBlock* BB, BasicBlock* incBB, Function* function){
	map<Port*, ConstantInt*>::iterator it;
	string skipBrName = "skip";
	string hasRoomBrName = "hasroom";

	// Add a basic block to bb for ski instructions
	BasicBlock* skipBB = BasicBlock::Create(Context, skipBrName, function);

	//Create check input pattern
	BB = checkInputPattern(moc->getInputPattern(), function, skipBB, BB);

	//Create output pattern
	BasicBlock* fireBB = checkOutputPattern(moc->getOutputPattern(), function, skipBB, BB);

	// Initialize internal vars
	if (instance->hasInternalPort()){
		initializeStateVars(instance->getInternalVars(), fireBB);
	}

	createActionsCall(moc, fireBB);
	
	//Branch fire basic block to BB basic block
	BranchInst::Create(incBB, fireBB);

	return skipBB;
}

void CSDFScheduler::initializeStateVars(map<Port*, StateVar*>* stateVars, BasicBlock* BB){
	map<Port*, StateVar*>::iterator it;

	for (it = stateVars->begin(); it != stateVars->end(); it++){
		Port* port = it->first;
		StateVar* stateVar = it->second;

		// Get zero element of the state var
		ConstantInt* zero = ConstantInt::get(Type::getInt32Ty(Context), 0);
		Value* values[] = {zero, zero};
		GetElementPtrInst* gepInst = GetElementPtrInst::Create(stateVar->getGlobalVariable(), values, "", BB);

		//Store to port pointer
		new StoreInst(gepInst, port->getPtrVar()->getGlobalVariable(), BB);
	}

}

void CSDFScheduler::createActionsCall(CSDFMoC* moc, BasicBlock* BB){
	// Create read/write for the action
	createReads(moc->getInputPattern(), BB);
	createWrites(moc->getOutputPattern(), BB);
	
	// Call actions successively
	list<Action*>::iterator it;
	list<Action*>* actions = moc->getActions();

	for ( it=actions->begin() ; it != actions->end(); it++ ){
		Action* action = *it;
		Procedure* body = action->getBody();
		CallInst* schedInst = CallInst::Create(body->getFunction(), "",  BB);
		updatePattern(action->getInputPattern(), BB);
		updatePattern(action->getOutputPattern(), BB);
	}

	//Create ReadEnd/WriteEnd
	createReadEnds(moc->getInputPattern(), BB);
	createWriteEnds(moc->getOutputPattern(), BB);
}

void CSDFScheduler::updatePattern(Pattern* pattern, BasicBlock* BB){
	//Get tokens and var
	map<Port*, ConstantInt*>::iterator it;
	map<Port*, Variable*>::iterator itVar;
	map<Port*, ConstantInt*>* numTokensMap = pattern->getNumTokensMap();

	for (it = numTokensMap->begin(); it != numTokensMap->end(); it++){
		// Get associated port variable
		Port* port = it->first;
		ConstantInt* numTokens = it->second;

		//Load selected port
		Variable* ptrVar = port->getPtrVar();
		LoadInst* loadPort = new LoadInst(ptrVar->getGlobalVariable(), "", BB);
		
		// Bitcast the given port
		ArrayType* type = ArrayType::get(port->getType(), numTokens->getLimitedValue());
		BitCastInst* bitCastInst = new BitCastInst(loadPort, type->getPointerTo(), "", BB);

		// Get next elements in pointer
		ConstantInt* Zero = ConstantInt::get(Type::getInt32Ty(Context), 0);
		Value* values[]={Zero, numTokens};
		GetElementPtrInst* getElementPtrInst = GetElementPtrInst::Create(bitCastInst, values, "", BB);

		//Store next pointer into fifo counter
		StoreInst* storeInst = new StoreInst(getElementPtrInst, ptrVar->getGlobalVariable(), BB);
	}
}

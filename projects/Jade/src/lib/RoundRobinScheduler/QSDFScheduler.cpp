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
#include "QSDFScheduler.h"

#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"

#include "Jade/Core/Network/Instance.h"
#include "Jade/Core/MoC/QSDFMoC.h"
//------------------------------

using namespace llvm;
using namespace std;

QSDFScheduler::QSDFScheduler(llvm::LLVMContext& C, Decoder* decoder) : CSDFScheduler(C, decoder) {

}

void QSDFScheduler::createScheduler(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* scheduler){
	list<pair<Action*, CSDFMoC*> >::iterator it;
	QSDFMoC* qsdMoC = (QSDFMoC*)instance->getMoC();
	list<pair<Action*, CSDFMoC*> >* configurations = qsdMoC->getConfigurations();
	
	for(it = configurations->begin(); it != configurations->end(); it++){
		BB = createConfigurationTest(it->first, it->second, BB, incBB, returnBB, scheduler);
	}

	//Create branch from skip to return
	BranchInst::Create(returnBB, BB);
}

BasicBlock*  QSDFScheduler::createConfigurationTest(Action* action, CSDFMoC* csdfMoC, BasicBlock* BB, 
													BasicBlock* incBB, BasicBlock* returnBB, Function* function){

	map<Port*, ConstantInt*>::iterator it;
	string skipBrName = "skip";
	string hasRoomBrName = "hasroom";
	string fireBrName = "fire";
	string returnBrName = "return";

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
	BranchInst::Create(fireBB, skipBB, schedInst, BB);

	//Create output pattern
	fireBB = checkOutputPattern(action->getOutputPattern(), function, returnBB, fireBB);
	
	//Create check input pattern of the associated CSDF MoC
	BB = checkInputPattern(csdfMoC->getInputPattern(), function, skipBB, fireBB);

	//Create output pattern of the associated CSDF MoC
	fireBB = checkOutputPattern(csdfMoC->getOutputPattern(), function, returnBB, BB);

	createActionsCall(csdfMoC, fireBB);

	//Branch fire basic block to BB basic block
	BranchInst::Create(incBB, fireBB);
	
	return skipBB;
}

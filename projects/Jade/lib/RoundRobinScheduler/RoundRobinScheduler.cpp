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
@brief Implementation of class RoundRobinScheduler
@author Jerome Gorin
@file RoundRobinScheduler.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <time.h>
#include <iostream>
#include <map>
#include <sys/stat.h> 

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/ADT/STLExtras.h"
#include "llvm/ADT/SmallString.h"
#include "llvm/Support/CommandLine.h"

#include "DPNScheduler.h"
#include "CSDFScheduler.h"

#include "Jade/Decoder.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Core/Variable.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/RoundRobinScheduler/RoundRobinScheduler.h"

#include "Jade/Actor/display.h"
//------------------------------

using namespace std;
using namespace llvm;
extern cl::opt<bool> nodisplay;
extern cl::opt<std::string> YuvFile;

static int Filesize(){
	FILE* f = fopen(YuvFile.c_str(), "rb");
	struct stat st;
	fstat(fileno(f), &st);
	return st.st_size;
}

RoundRobinScheduler::RoundRobinScheduler(llvm::LLVMContext& C, Decoder* decoder, bool verbose): Context(C) {
	this->decoder = decoder;
	this->configuration = decoder->getConfiguration();
	this->scheduler = NULL;
	this->initBrInst = NULL;
	this->schedBrInst = NULL;
	this->verbose = verbose;

	createScheduler();	
}

void RoundRobinScheduler::createScheduler(){
	//Add action schedulers in instances
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = configuration->getInstances();
	DPNScheduler DPNSchedulerAdder(Context, decoder);
	CSDFScheduler CSDFSchedulerAdder(Context, decoder);
	
	for (it = instances->begin(); it != instances->end(); it++){
		Instance* instance = it->second;
		MoC* moc = instance->getMoC();

		if (moc->isCSDF() && !moc->isSDF() && configuration->mergeActors()){
			CSDFSchedulerAdder.transform(instance);
		}else{
			DPNSchedulerAdder.transform(instance);
		}
	}

	//Create the scheduler function
	createNetworkScheduler();

	for (it = instances->begin(); it != instances->end(); it++){
		addInstance(it->second);
	}
}


RoundRobinScheduler::~RoundRobinScheduler (){
	scheduler->eraseFromParent();
}

void RoundRobinScheduler::createNetworkScheduler(){
	map<string, Instance*>::iterator it;
	
	Module* module = decoder->getModule();
	LLVMContext &Context = getGlobalContext();

	//Create a global value that stop the scheduler and set it to false
	GlobalVariable* stopGV = (GlobalVariable*)module->getOrInsertGlobal("stop", Type::getInt1Ty(Context));
	stopGV->setInitializer(ConstantInt::get(Type::getInt1Ty(Context), 0));
	
	// create main scheduler function
	scheduler = cast<Function>(module->getOrInsertFunction("main", Type::getInt32Ty(Context),
                                          (Type *)0));
										  

	// Add a basic block entry to the scheduler.
	BasicBlock* initializeBB = BasicBlock::Create(Context, "entry", scheduler);

	// Add a basic block to bb to the scheduler.
	BasicBlock* schedulerBB = BasicBlock::Create(Context, "bb", scheduler);
	
	// Create a branch to bb and store it for later insertions
	initBrInst = BranchInst::Create(schedulerBB, initializeBB);
	
	// Add a basic block return to the scheduler.
	BasicBlock* BBReturn = BasicBlock::Create(Context, "return", scheduler);
	ConstantInt* one = ConstantInt::get(Type::getInt32Ty(Context), 1);
	ReturnInst* returnInst = ReturnInst::Create(Context, one, BBReturn);

	// Load stop value and test if the scheduler must be stop
	LoadInst* stopVal = new LoadInst(stopGV, "", schedulerBB);
	schedBrInst = BranchInst::Create(BBReturn, schedulerBB, stopVal, schedulerBB);
}

void RoundRobinScheduler::addInstance(Instance* instance){
	ActionScheduler* actionScheduler = instance->getActionScheduler();
	
	if (actionScheduler->hasInitializeScheduler()){
		Function* initialize = actionScheduler->getInitializeFunction();
		CallInst* CallInit = CallInst::Create(initialize, "", initBrInst);
		functionCall.insert(pair<Function*, CallInst*>(initialize, CallInit));
	}

	Function* scheduler = actionScheduler->getSchedulerFunction();
	CallInst* CallSched = CallInst::Create(scheduler, "", schedBrInst);
	CallSched->setTailCall();
	functionCall.insert(pair<Function*, CallInst*>(scheduler, CallSched));
}

void RoundRobinScheduler::removeInstance(Instance* instance){
	ActionScheduler* actionScheduler = instance->getActionScheduler();

	if (actionScheduler->hasInitializeScheduler()){
		removeCall(actionScheduler->getInitializeFunction());
	}

	removeCall(actionScheduler->getSchedulerFunction());

}

void RoundRobinScheduler::removeCall(llvm::Function* function){
	map<llvm::Function*, llvm::CallInst*>::iterator it;
	
	it = functionCall.find(function);
	CallInst* call = it->second;
	call->eraseFromParent();
	functionCall.erase(it);
}
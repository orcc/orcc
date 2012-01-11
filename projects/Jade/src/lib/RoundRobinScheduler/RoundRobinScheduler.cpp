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
#include <cstdio>

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
#include "QSDFScheduler.h"

#include "Jade/Decoder.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Core/Variable.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/RoundRobinScheduler/RoundRobinScheduler.h"
#include "Jade/Util/FunctionMng.h"
//------------------------------

using namespace std;
using namespace llvm;

RoundRobinScheduler::RoundRobinScheduler(llvm::LLVMContext& C, Decoder* decoder, list<Instance*>* instances, bool optimized, bool noMultiCore, bool verbose, bool debug): Context(C) {
	this->decoder = decoder;
	this->instances = instances;
	this->scheduler = NULL;
	this->initialize = NULL;
	this->initInst = NULL;
	this->schedInst = NULL;
	this->stopGV = NULL;
	this->verbose = verbose;
	this->debug = debug;
	this->optimized = optimized;

	createScheduler();	
}

void RoundRobinScheduler::createScheduler(){
	
	//Create the initialize function
	createNetworkInitialize();
	
	//Create the scheduler function
	createNetworkScheduler();

	//Add the instance in the scheduler
	list<Instance*>::iterator it;
	for (it = instances->begin(); it != instances->end(); it++){
		addInstance(*it);
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
	stopGV = new GlobalVariable(*module, Type::getInt32Ty(Context), false, GlobalValue::ExternalLinkage,0,"stop", 0, false);
	
	// create main scheduler function
	FunctionType *FT = FunctionType::get(Type::getInt32Ty(getGlobalContext()), false);
	scheduler = Function::Create(FT,  Function::ExternalLinkage, "main", module);								  

	// Add a basic block entry to the scheduler.
	BasicBlock* initializeBB = BasicBlock::Create(Context, "entry", scheduler);

	// Add a basic block to bb to the scheduler.
	BasicBlock* schedulerBB = BasicBlock::Create(Context, "bb", scheduler);
	
	// Create a branch to bb and store it for later insertions
	BranchInst* brInst = BranchInst::Create(schedulerBB, initializeBB);
	
	// Add a basic block return to the scheduler.
	BasicBlock* BBReturn = BasicBlock::Create(Context, "return", scheduler);
	ConstantInt* one = ConstantInt::get(Type::getInt32Ty(Context), 1);
	ReturnInst* returnInst = ReturnInst::Create(Context, one, BBReturn);

	// Load stop value and test if the scheduler must be stop
	schedInst = new LoadInst(stopGV, "", schedulerBB);
	ICmpInst* test = new ICmpInst(*schedulerBB, ICmpInst::ICMP_EQ, schedInst, one);
	BranchInst* schedBrInst = BranchInst::Create(BBReturn, schedulerBB, test, schedulerBB);
}

void RoundRobinScheduler::createNetworkInitialize(){
	map<string, Instance*>::iterator it;
	
	Module* module = decoder->getModule();
	LLVMContext &Context = getGlobalContext();
	
	// create main scheduler function
	initialize = cast<Function>(module->getOrInsertFunction("initialize", Type::getVoidTy(Context),
                                          (Type *)0));
										  
	if (initialize->empty()){
		// Add a basic block entry to the scheduler.
		BasicBlock* initializeBB = BasicBlock::Create(Context, "entry", initialize);

		initInst = ReturnInst::Create(Context, 0, initializeBB);
	}else{
		initInst = initialize->getEntryBlock().begin();
	}

}

void RoundRobinScheduler::createCall(Instance* instance){
	ActionScheduler* actionScheduler = instance->getActionScheduler();
	
	// Call initialize function if present
	if (actionScheduler->hasInitializeScheduler()){
		Function* initialize = actionScheduler->getInitializeFunction();
		CallInst* CallInit = CallInst::Create(initialize, "", initInst);
		functionCall.insert(pair<Function*, CallInst*>(initialize, CallInit));
	}

	// Add debugging information if needed
	if (debug){
		string message = "---> enabling ";
		message.append(instance->getId());

		FunctionMng::createPuts(decoder->getModule(), message, schedInst);
	}
	
	// Call scheduler function of the instance
	Function* scheduler = actionScheduler->getSchedulerFunction();
	CallInst* CallSched = CallInst::Create(scheduler, "", schedInst);
	CallSched->setTailCall();

	functionCall.insert(pair<Function*, CallInst*>(scheduler, CallSched));
}

void RoundRobinScheduler::addInstance(Instance* instance){
	// Create an action scheduler for the instance
	DPNScheduler DPNSchedulerAdder(Context, decoder, debug);
	CSDFScheduler CSDFSchedulerAdder(Context, decoder, debug);
	QSDFScheduler QSDFSchedulerAdder(Context, decoder, debug);
	
	MoC* moc = instance->getMoC();

	if (moc->isQuasiStatic() && optimized){
		QSDFSchedulerAdder.transform(instance);
	}else if (moc->isCSDF() && optimized){
		CSDFSchedulerAdder.transform(instance);
	}else{
		DPNSchedulerAdder.transform(instance);
	}

	// Call the action scheduler
	createCall(instance);
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

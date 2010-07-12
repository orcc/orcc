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
@version 0.1
@date 2010/04/12
*/

//------------------------------

#include <map>

#include "Jade/Scheduler/RoundRobinScheduler.h"

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/ADT/STLExtras.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Actor.h"
#include "Jade/Actor/ActionScheduler.h"
#include "Jade/Decoder/InstancedActor.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Network/Instance.h"
//------------------------------

using namespace std;
using namespace llvm;



extern "C" void source_initialize();
extern "C" void source_scheduler();
extern "C" void display_scheduler();
extern "C" struct fifo_char_s *display_B;
extern "C" struct fifo_short_s *display_WIDTH;
extern "C" struct fifo_short_s *display_HEIGHT;
extern "C" struct fifo_char_s *source_O;

RoundRobinScheduler::RoundRobinScheduler(llvm::LLVMContext& C, JIT* jit, Decoder* decoder): Context(C) {
	this->jit = jit;
	this->decoder = decoder;
	createScheduler();
}

RoundRobinScheduler::~RoundRobinScheduler (){

}

void RoundRobinScheduler::createScheduler(){
	map<Instance*, InstancedActor*>::iterator it;
	
	Module* module = decoder->getModule();
	LLVMContext &Context = getGlobalContext();

	// create scheduler
	map<Instance*, InstancedActor*>* InstancedActors = decoder->getInstancedActors();

	scheduler = cast<Function>(module->getOrInsertFunction("scheduler", Type::getVoidTy(Context),
                                          (Type *)0));
										  

	// Add a basic block entry to the scheduler.
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", scheduler);

	// Add a basic block to bb to the scheduler.
	BasicBlock* BB = BasicBlock::Create(Context, "bb", scheduler);

	//Add initialize scheduler
	for (it = InstancedActors->begin(); it != InstancedActors->end(); ++it){
		Instance* instanceTest = (*it).first;
		InstancedActor* instance = (*it).second;
		ActionScheduler* scheduler = instance->getActionScheduler();
		if (scheduler->hasInitializeScheduler()){
			CallInst *Add1CallRes = CallInst::Create(scheduler->getInitializeFunction(), "", BBEntry);
		}
	}

	// Create a branch to bb
	Instruction* brEntryInst = BranchInst::Create(BB, BBEntry);
 
	//Add action scheduler
	for (it = InstancedActors->begin(); it != InstancedActors->end(); ++it){
		Instance* instanceTest = (*it).first;
		InstancedActor* instance = (*it).second;
		ActionScheduler* scheduler = instance->getActionScheduler();
		CallInst *Add1CallRes = CallInst::Create(scheduler->getSchedulerFunction(), "", BB);
	}
	
	// Create a branch to entry
	Instruction* brBbInst = BranchInst::Create(BB, BB);
/*
	Value *Ten = ConstantInt::get(Type::getInt32Ty(Context), 0);

	// Create the return instruction and add it to the basic block.
	ReturnInst::Create(Context, BB);*/
}

void RoundRobinScheduler::execute(){
	Module* module = decoder->getModule();
	
	// Get elements from source and display 
	Function* sourceFunc = module->getFunction("source_scheduler");
	Function* displayFunc = module->getFunction("display_scheduler");
	GlobalVariable* displayB = module->getGlobalVariable("display_B");
	GlobalVariable* displayW = module->getGlobalVariable("display_WIDTH");
	GlobalVariable* displayH = module->getGlobalVariable("display_HEIGHT");
	GlobalVariable* sourceO = module->getGlobalVariable("source_O");
	


	// Get pointer 
	jit->GlobalMapped(sourceFunc, (void*) source_scheduler);
	jit->GlobalMapped(displayFunc, (void*) display_scheduler);
	display_B = (fifo_char_s*)(*(fifo_char_s**)jit->getFifoPointer(displayB));
	display_WIDTH = (fifo_short_s*)(*(fifo_short_s**)jit->getFifoPointer(displayW));
	display_HEIGHT = (fifo_short_s*)(*(fifo_short_s**)jit->getFifoPointer(displayH));
	source_O = (fifo_char_s*)(*(fifo_char_s**)jit->getFifoPointer(sourceO));
	
	

	source_initialize();

	Function* main = module->getFunction("scheduler");
	jit->run(main);
}
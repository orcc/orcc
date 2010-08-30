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
#include "llvm/ADT/SmallString.h"
#include "llvm/Support/CommandLine.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Actor.h"
#include "Jade/Actor/ActionScheduler.h"
#include "Jade/Decoder/InstancedActor.h"
#include "Jade/Decoder/Procedure.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Network/Instance.h"

#include "display.h"
//------------------------------

using namespace std;
using namespace llvm;


extern cl::opt<std::string> VidFile;
extern cl::opt<bool> nodisplay;

RoundRobinScheduler::RoundRobinScheduler(llvm::LLVMContext& C, JIT* jit, Decoder* decoder): Context(C) {
	this->jit = jit;
	this->decoder = decoder;
	createScheduler();
	
	//Connect decoder to input and output of Jade
	setSource();

	if(!nodisplay){
		setDisplay();
	}
}

RoundRobinScheduler::~RoundRobinScheduler (){

}

void RoundRobinScheduler::createScheduler(){
	map<Instance*, InstancedActor*>::iterator it;
	
	Module* module = decoder->getModule();
	LLVMContext &Context = getGlobalContext();

	// create scheduler
	map<Instance*, InstancedActor*>* InstancedActors = decoder->getInstancedActors();
	scheduler = cast<Function>(module->getOrInsertFunction("main", Type::getVoidTy(Context),
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

	if(decoder->hasInitialization()){
		CallInst *Add1CallRes = CallInst::Create(decoder->getInitialization()->getFunction(), "", BBEntry);
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
}

void RoundRobinScheduler::execute(){

	//Run decoder
	Module* module = decoder->getModule();
	Function* main = module->getFunction("main");
	jit->run(main);
}

void RoundRobinScheduler::setSource(){
	Module* module = decoder->getModule();
	
	//Get source instance
	Instance* sourceInst = decoder->getInstance("source");
	InstancedActor* source = sourceInst->getInstancedActor();

	
	//Insert source file string
	ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context),VidFile.size()+1); 
	GlobalVariable *GV = new llvm::GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context,VidFile), "fileName", 0, false, 0);

	//Store adress in input file of source
	GlobalVariable* sourceFile = source->getStateVar(sourceInst->getActor()->getStateVar("input_file"));
	Constant *Indices[2] = {ConstantInt::get(Type::getInt32Ty(Context), 0), ConstantInt::get(Type::getInt32Ty(Context), 0)};
	sourceFile->setInitializer(ConstantExpr::getGetElementPtr(GV, Indices, 2));
}

void RoundRobinScheduler::setDisplay(){
	//Get display instance
	Instance* displayInst = decoder->getInstance("display");
	InstancedActor* display = displayInst->getInstancedActor();

	//Get procedures from display
	Function* setVideo = display->getProcedureVar(displayInst->getActor()->getProcedure("set_video"));
	Function* setInit = display->getProcedureVar(displayInst->getActor()->getProcedure("set_init"));
	Function* writeMb = display->getProcedureVar(displayInst->getActor()->getProcedure("write_mb"));

	//Map procedure to display
	jit->MapFunction(setVideo, (void *)display_set_video);
	jit->MapFunction(setInit, (void *)display_init);
	jit->MapFunction(writeMb, (void *)display_write_mb);
}
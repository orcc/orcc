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

#include "ActionSchedulerAdder.h"
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
#include "Jade/Core/Actor.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/InstancedActor.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Core/Instance.h"

#include "display.h"
//------------------------------

using namespace std;
using namespace llvm;


extern cl::opt<std::string> VidFile;
extern cl::opt<bool> nodisplay;
extern cl::opt<std::string> YuvFile;

static int Filesize(){
	FILE* f = fopen(YuvFile.c_str(), "rb");
	struct stat st;
	fstat(fileno(f), &st);
	return st.st_size;
}

RoundRobinScheduler::RoundRobinScheduler(llvm::LLVMContext& C, JIT* jit, Decoder* decoder): Context(C) {
	this->jit = jit;
	this->decoder = decoder;
	
	//Create action schedulers
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = decoder->getInstances();
	
	for (it = instances->begin(); it != instances->end(); ++it){
		Instance* instance = (*it).second;
		string id = instance->getId();
		ActionSchedulerAdder(instance, decoder, Context);

	}
	
	
	createScheduler();
	
	//Connect decoder to source
	setSource();

	//Set compare file if needed
	if(YuvFile.compare("") != 0){
		setCompare();
	}

	//Connect decoder to display if needed
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

void RoundRobinScheduler::setCompare(){
	list<Instance*>::iterator it;
	Actor* compare = decoder->getActor("Compare");
	
	//Actor compare must be present in the decoder
	if ( compare == NULL){
		printf("Actor Compare is not present in the current description");
	}
	
	//Get all instances of actor compare
	list<Instance*>* instances = compare->getInstances();
	Module* module = decoder->getModule();
	

	//Fix compare file in all instance
	for ( it=instances->begin() ; it != instances->end(); it++ ){
		InstancedActor* instancedActor = (*it)->getInstancedActor();

		//Insert compare file string
		ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context),YuvFile.size()+1); 
		GlobalVariable *GV = new llvm::GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context,YuvFile), "compareFile", 0, false, 0);

		//Store adress in input file of source
		GlobalVariable* yuvVar = instancedActor->getStateVar(compare->getStateVar("yuv_file"));
		Constant *Indices[2] = {ConstantInt::get(Type::getInt32Ty(Context), 0), ConstantInt::get(Type::getInt32Ty(Context), 0)};
		yuvVar->setInitializer(ConstantExpr::getGetElementPtr(GV, Indices, 2));
		
		//Map fstat function used in compare actor
		Procedure* filesizeFuncProc = compare->getProcedure("Filesize");
		Function* filesizeFunc = instancedActor->getProcedureVar(filesizeFuncProc);
		jit->MapFunction(filesizeFunc, (void *)Filesize);
	}
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
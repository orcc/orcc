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

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/ADT/STLExtras.h"
#include "llvm/ADT/SmallString.h"
#include "llvm/Support/CommandLine.h"

#include "Jade/Decoder.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Core/Variable.h"
#include "Jade/Core/Instance.h"
#include "Jade/Jit/LLVMExecution.h"
#include "Jade/Scheduler/RoundRobinScheduler.h"

#include "display.h"
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

RoundRobinScheduler::RoundRobinScheduler(llvm::LLVMContext& C, bool verbose): Context(C) {
	this->executionEngine = NULL;
	this->verbose = verbose;
}

RoundRobinScheduler::~RoundRobinScheduler (){
	if (executionEngine){
		delete executionEngine;
	}
}

void RoundRobinScheduler::createScheduler(Decoder* decoder, string stimulus){
	this->decoder = decoder;
	
	//Set input file
	setSource(stimulus);

	//Create the scheduler function
	createSchedulerFn();
	
	//Set compare file if needed
	setCompare();
}

void RoundRobinScheduler::createSchedulerFn(){
	map<string, Instance*>::iterator it;
	
	Module* module = decoder->getModule();
	LLVMContext &Context = getGlobalContext();

	//Create a global value that stop the scheduler and set it to false
	GlobalVariable* stopGV = (GlobalVariable*)module->getOrInsertGlobal("stop", Type::getInt1Ty(Context));
	stopGV->setInitializer(ConstantInt::get(Type::getInt1Ty(Context), 0));
	
	// create scheduler
	map<string, Instance*>* instances = decoder->getInstances();
	scheduler = cast<Function>(module->getOrInsertFunction("main", Type::getInt32Ty(Context),
                                          (Type *)0));
										  

	// Add a basic block entry to the scheduler.
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", scheduler);

	// Add a basic block to bb to the scheduler.
	BasicBlock* BB = BasicBlock::Create(Context, "bb", scheduler);

	//Add initialize scheduler
	for (it = instances->begin(); it != instances->end(); ++it){
		Instance* instance = (*it).second;
		ActionScheduler* scheduler = instance->getActionScheduler();
		if (scheduler->hasInitializeScheduler()){
			CallInst *Add1CallRes = CallInst::Create(scheduler->getInitializeFunction(), "", BBEntry);
		}
	}

	// Create a branch to bb
	Instruction* brEntryInst = BranchInst::Create(BB, BBEntry);
 
	//Add action scheduler
	for (it = instances->begin(); it != instances->end(); ++it){
		Instance* instance = (*it).second;
		ActionScheduler* scheduler = instance->getActionScheduler();
		CallInst *Add1CallRes = CallInst::Create(scheduler->getSchedulerFunction(), "", BB);
		Add1CallRes->setTailCall();
	}
	
	// Add a basic block return to the scheduler.
	BasicBlock* BBReturn = BasicBlock::Create(Context, "return", scheduler);
	ConstantInt* one = ConstantInt::get(Type::getInt32Ty(Context), 1);
	ReturnInst* returnInst = ReturnInst::Create(Context, one, BBReturn);

	// Load stop value and test if the scheduler must be stop
	LoadInst* stopVal = new LoadInst(stopGV, "", BB);
	Instruction* brBbInst = BranchInst::Create(BBReturn, BB, stopVal, BB);
}

void RoundRobinScheduler::execute(){

	clock_t timer = clock ();
	executionEngine = new LLVMExecution(Context, decoder);

	setExternalFunctions();

	if (verbose){
		cout << "--> Engine initialized in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		cout << "-->  Start decoding :\n";
	}

	//Run decoder
	executionEngine->run("main");
}

void RoundRobinScheduler::setExternalFunctions(){

	//Set exit function
	exit_decoder = (void(*)(int))executionEngine->getExit();

	if(YuvFile.compare("") != 0){
		Instance* compare = decoder->getInstance("Compare");

		//Map fstat function used in compare actor
		Procedure* filesize = compare->getProcedure("Filesize");
		executionEngine->mapProcedure(filesize, (void *)Filesize);
	}

	//Get display instance
	Instance* display = decoder->getInstance("display");

	//Get procedures from display
	Procedure* setVideo = display->getProcedure("set_video");
	Procedure* setInit = display->getProcedure("set_init");
	Procedure* writeMb = display->getProcedure("write_mb");

	if(nodisplay){
		executionEngine->mapProcedure(setVideo, (void *)emptyFunc);
		executionEngine->mapProcedure(setInit, (void *)initT);
		executionEngine->mapProcedure(writeMb, (void *)display_write_mb);
	}else{
		//Map procedure to display
		executionEngine->mapProcedure(setVideo, (void *)display_set_video);
		executionEngine->mapProcedure(setInit, (void *)display_init);
		executionEngine->mapProcedure(writeMb, (void *)display_write_mb);
	}
}

void RoundRobinScheduler::setSource(string input){
	Module* module = decoder->getModule();
	
	//Get source instance
	Instance* source = decoder->getInstance("source");
	
	//Insert source file string
	ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context),input.size()+1); 
	GlobalVariable *GV = new llvm::GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context, input), "fileName", 0, false, 0);

	//Store adress in input file of source
	Variable* sourceFileVar = source->getStateVar("input_file");
	GlobalVariable* sourceFile = sourceFileVar->getGlobalVariable();
	Constant *Indices[2] = {ConstantInt::get(Type::getInt32Ty(Context), 0), ConstantInt::get(Type::getInt32Ty(Context), 0)};
	sourceFile->setInitializer(ConstantExpr::getGetElementPtr(GV, Indices, 2));
}

void RoundRobinScheduler::setCompare(){
	list<Instance*>::iterator it;
	Actor* compare = decoder->getActor("Compare");
	
	//Actor compare is not present in the decoder
	if ((compare == NULL) && (YuvFile.compare("") != 0)){
		printf("Actor Compare is not present in the current description");
		exit(1);
	}
	
	//Actor compare is present but the option not set
	if ((compare != NULL) && (YuvFile.compare("") == 0)){
		printf("Actor Compare is present in the current description but not set");
		exit(1);
	}

	//Actor compare is not present and the option is not set
	if ((compare == NULL) && (YuvFile.compare("") == 0)){
		return;
	}
	
	//Get all instances of actor compare
	list<Instance*>* instances = compare->getInstances();
	Module* module = decoder->getModule();
	

	//Fix compare file in all instance
	for ( it=instances->begin() ; it != instances->end(); it++ ){
		Instance* instance = *it;

		//Insert compare file string
		ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context),YuvFile.size()+1); 
		GlobalVariable *GV = new llvm::GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context,YuvFile), "compareFile", 0, false, 0);

		//Store adress in input file of source
		GlobalVariable* yuvVar = instance->getStateVar("yuv_file")->getGlobalVariable();
		Constant *Indices[2] = {ConstantInt::get(Type::getInt32Ty(Context), 0), ConstantInt::get(Type::getInt32Ty(Context), 0)};
		yuvVar->setInitializer(ConstantExpr::getGetElementPtr(GV, Indices, 2));
	}
}
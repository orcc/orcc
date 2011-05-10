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
@brief Implementation of LLVMExecution
@author Jerome Gorin
@file LLVMExecution.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>
#include <errno.h>
#include <time.h>

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Module.h"
#include "llvm/Type.h"
#include "llvm/ADT/Triple.h"
#include "llvm/ExecutionEngine/GenericValue.h"
#include "llvm/ExecutionEngine/JIT.h"
#include "llvm/ExecutionEngine/JITEventListener.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/ManagedStatic.h"
#include "llvm/Support/raw_ostream.h"
#include "llvm/Support/Signals.h"
#include "llvm/Support/Process.h"
#include "llvm/Target/TargetSelect.h"

#include "Jade/Decoder.h"
#include "Jade/Actor/FileDisp.h"
#include "Jade/Actor/GpacDisp.h"
#include "Jade/Actor/FileSrc.h"
#include "Jade/Actor/GpacSrc.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Fifo/AbstractFifo.h"
#include "Jade/Jit/LLVMExecution.h"
//------------------------------

using namespace llvm;
using namespace std;

//Execution engine options
extern cl::opt<bool> ForceInterpreter;
extern cl::opt<std::string> MArch;
extern cl::opt<bool> DisableCoreFiles;
extern cl::opt<bool> NoLazyCompilation;
extern cl::list<std::string> MAttrs;
extern cl::opt<std::string> MCPU;
extern cl::opt<std::string> TargetTriple;
cl::opt<bool> UseMCJIT(
    "use-mcjit", cl::desc("Enable use of the MC-based JIT (if available)"),
    cl::init(false));

//===----------------------------------------------------------------------===//
// main Driver function
//
LLVMExecution::LLVMExecution(LLVMContext& C, Decoder* decoder, bool verbose): Context(C)  {
  std::string ErrorMsg;

  this->decoder = decoder;
  this->verbose = verbose;

  this->stopSchVal = 0;

  Module* module = decoder->getModule();

  // If the user doesn't want core files, disable them.
  if (DisableCoreFiles)
    sys::Process::PreventCoreFiles();
  
   // If not jitting lazily, load the whole bitcode file eagerly too.
  if (NoLazyCompilation) {
    if (module->MaterializeAllPermanently(&ErrorMsg)) {
      cout << "bitcode didn't read correctly.\n";
      cout << "Reason: " << ErrorMsg << "\n";
      exit(1);
    }
  }
  
  EngineBuilder builder(module);
  builder.setMArch(MArch);
  builder.setMCPU(MCPU);
  builder.setMAttrs(MAttrs);
  builder.setErrorStr(&ErrorMsg);
  builder.setEngineKind(ForceInterpreter
                        ? EngineKind::Interpreter
                        : EngineKind::JIT);

  // If we are supposed to override the target triple, do so now.
  if (!TargetTriple.empty())
    module->setTargetTriple(Triple::normalize(TargetTriple));

    // Enable MCJIT, if desired.
  if (UseMCJIT)
    builder.setUseMCJIT(true);

  //TODO : select optimization level
  char OptLevel = '2';
  CodeGenOpt::Level OLvl = CodeGenOpt::Default;
  switch (OptLevel) {
  default:
   exit (1);
  case ' ': break;
  case '0': OLvl = CodeGenOpt::None; break;
  case '1': OLvl = CodeGenOpt::Less; break;
  case '2': OLvl = CodeGenOpt::Default; break;
  case '3': OLvl = CodeGenOpt::Aggressive; break;
  }
  builder.setOptLevel(OLvl);

  EE = builder.create();
  if (!EE) {
    if (!ErrorMsg.empty())
      cout << ": error creating EE: " << ErrorMsg << "\n";
    else
      cout << ": unknown error creating EE!\n";
    exit(1);
  }

  //Set properties of the EE
  EE->RegisterJITEventListener(createOProfileJITEventListener());

  EE->DisableLazyCompilation(NoLazyCompilation);
  
  // If the program doesn't explicitly call exit, we will need the Exit 
  // function later on to make an explicit call, so get the function now. 
  Exit = (Function*) module->getOrInsertFunction("exit", Type::getVoidTy(Context),
                                                    Type::getInt32Ty(Context),
                                                    NULL);
}

void* LLVMExecution::getExit() {
	return EE->getPointerToFunction(Exit);
}

void LLVMExecution::mapProcedure(Procedure* procedure, void *Addr) {
	Function* function = procedure->getFunction();
	
	if (EE->getPointerToGlobalIfAvailable(function) == NULL){
		EE->addGlobalMapping(function, Addr);
	}
}

bool LLVMExecution::mapFifo(Port* port, AbstractFifo* fifo) {
	void **portGV = (void**)EE->getPointerToGlobalIfAvailable(port->getFifoVar());
	
	//Port has already been compiled
	if (portGV != NULL){
		//Initialize fifo
		void* fifoGV = EE->getOrEmitGlobalVariable(fifo->getGV());

		//Connect to compiled port
		*portGV = fifoGV;

		return true;
	}

	return false;
	
}

void LLVMExecution::run(string stimulus) {
	std::string ErrorMsg;
	Module* module = decoder->getModule();
	clock_t timer = clock ();
	this->stimulus = stimulus;

	// Set IO of the network
	setIO();
	
	// Set scheduler as an infinite loop
	Scheduler* scheduler = decoder->getScheduler();
	GlobalVariable* stopGV = scheduler->getStopGV();
	stopGV->setInitializer(ConstantInt::get(Type::getInt32Ty(Context), 0));

	// Run static constructors.
    EE->runStaticConstructorsDestructors(false);

	// In case of no lazy compilation, compile all
   if (NoLazyCompilation) {
		for (Module::iterator I = module->begin(), E = module->end(); I != E; ++I) {
			Function *Fn = &*I;
			if (!Fn->isDeclaration())
				EE->getPointerToFunction(Fn);
		}
		cout << "--> No lazy compilation enable, the decoder has been compiled in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC << " ms \n";
	}
	
	// Get scheduler functions
	Function* func = scheduler->getMainFunction();
	Function* init = scheduler->getInitFunction();

	// Run scheduler
	std::vector<GenericValue> noargs;
	EE->runFunction(init, noargs);
	GenericValue Result = EE->runFunction(func, noargs);
}

void LLVMExecution::setIO(){
	Configuration* configuration = decoder->getConfiguration();
	
	//Set input of the decoder
	Instance* in = configuration->getInstance("source");
	
	if (in != NULL){
		setIn(in);
	}

	//Set output of the decoder
	Instance* out = configuration->getInstance("display");

	if (out != NULL){
		setOut(out);
	}
}

void LLVMExecution::initialize(){
	GpacSrc* gpacSrc = new GpacSrc(1);
	GpacDisp* gpacDisp = new GpacDisp(1);

	Configuration* configuration = decoder->getConfiguration();

	//Set input of the decoder
	Instance* in = configuration->getInstance("source");
	source = gpacSrc;

	//Set var gpac source
	StateVar* stateVarIn = in->getStateVar("source");
	Source** ptrSource = (Source**)getGVPtr(stateVarIn->getGlobalVariable());
	*ptrSource = gpacSrc;

	//Set setvideo procedure
	Procedure* getSrcProc = in->getProcedure("get_src");
	if (getSrcProc != NULL){
		mapProcedure(getSrcProc, (void*)get_src);
	}


	//Set output of the decoder
	Instance* out = configuration->getInstance("display");
	display = gpacDisp;

	//Set var gpac display
	StateVar* stateVarOut = out->getStateVar("display");
	Display** ptrDisplay = (Display**)getGVPtr(stateVarOut->getGlobalVariable());
	*ptrDisplay = gpacDisp;

	//Set setvideo procedure
	Procedure* setVideoProc = out->getProcedure("set_video");
	if (setVideoProc != NULL){
		mapProcedure(setVideoProc, (void*)set_video);
	}

	//Set writemb procedure
	Procedure* writeMbProc = out->getProcedure("write_mb");
	if (writeMbProc != NULL){
		mapProcedure(writeMbProc, (void*)write_mb);
	}

	
	Module* module = decoder->getModule();
	
	// Set stop condition of the scheduler
	Scheduler* scheduler = decoder->getScheduler();
	GlobalVariable* stopGV = scheduler->getStopGV();
	EE->addGlobalMapping(stopGV, getStopSchPtr());

	gpacSrc->setStopSchPtr(getStopSchPtr());
	gpacDisp->setStopSchPtr(getStopSchPtr());

	// Run static constructors.
    EE->runStaticConstructorsDestructors(false);

	// In case of no lazy compilation, compile all
	if (NoLazyCompilation) {
		for (Module::iterator I = module->begin(), E = module->end(); I != E; ++I) {
			Function *Fn = &*I;
			if (!Fn->isDeclaration())
				EE->getPointerToFunction(Fn);
		}
	}

   	// Initialize the network
	Function* init = scheduler->getInitFunction();
	std::vector<GenericValue> noargs;
	EE->runFunction(init, noargs);
}

void LLVMExecution::start(unsigned char* nal, int nal_length, RVCFRAME* rvcFrame){
	GpacSrc* gpacSrc = (GpacSrc*)source;
	gpacSrc->setNal(nal, nal_length);

	GpacDisp* gpacDisp = (GpacDisp*)display;
	gpacDisp->setFramePtr(rvcFrame);

	startScheduler();

	Scheduler* scheduler = decoder->getScheduler();
	Function* main = scheduler->getMainFunction();
	std::vector<GenericValue> noargs;
	EE->runFunction(main, noargs);
}

void LLVMExecution::setIn(Instance* instance){
	// Create a specific source for the decoder made to read files
	FileSrc* fileSrc = new FileSrc(1);
	fileSrc->setStimulus(stimulus);	
	
	source = fileSrc;

	//Set var source
	StateVar* stateVar = instance->getStateVar("source");
	Source** ptrSource = (Source**)getGVPtr(stateVar->getGlobalVariable());
	*ptrSource = source;

	//Set setvideo procedure
	Procedure* getSrcProc = instance->getProcedure("get_src");
	if (getSrcProc != NULL){
		mapProcedure(getSrcProc, (void*)get_src);
	}
}

void  LLVMExecution::setOut(Instance* instance){
	FileDisp* fileDisp = new FileDisp(1, verbose);

	display = fileDisp;

	//Set var display
	StateVar* stateVar = instance->getStateVar("display");
	Display** ptrDisplay = (Display**)getGVPtr(stateVar->getGlobalVariable());
	*ptrDisplay = display;

	//Set setvideo procedure
	Procedure* setVideoProc = instance->getProcedure("set_video");
	if (setVideoProc != NULL){
		mapProcedure(setVideoProc, (void*)set_video);
	}

	//Set writemb procedure
	Procedure* writeMbProc = instance->getProcedure("write_mb");
	if (writeMbProc != NULL){
		mapProcedure(writeMbProc, (void*)write_mb);
	}

}

bool LLVMExecution::waitForFirstFrame(){
	FileDisp* fileDisp = (FileDisp*)display;
	fileDisp->waitForFirstFrame();
	return true;
}

void LLVMExecution::runFunction(Function* function) {
	std::vector<GenericValue> noargs;
	GenericValue Result = EE->runFunction(function, noargs);
}

void LLVMExecution::stop(pthread_t* thread) {
	if (thread != NULL){
		FileDisp* fileDisp = (FileDisp*)display;
		fileDisp->forceStop(thread);
	}
}

void LLVMExecution::recompile(Function* function) {
	EE->recompileAndRelinkFunction(function);
}

bool LLVMExecution::isCompiledGV(llvm::GlobalVariable* gv){
	return EE->getPointerToGlobalIfAvailable(gv) != NULL;
}

void* LLVMExecution::getGVPtr(llvm::GlobalVariable* gv){
	return EE->getPointerToGlobal(gv);
}

void LLVMExecution::clear() {
	Module* module = decoder->getModule();
	EE->runStaticConstructorsDestructors(true);
	EE->clearAllGlobalMappings();
	
/*
	for (Module::iterator I = module->begin(), E = module->end(); I != E; ++I) {
		EE->freeMachineCodeForFunction(I);
	}*/
}


LLVMExecution::~LLVMExecution(){
	// Run static destructors.
	EE->runStaticConstructorsDestructors(true);

	delete EE;
	llvm_shutdown();
}
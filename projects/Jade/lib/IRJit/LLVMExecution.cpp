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

#include "llvm/Module.h"
#include "llvm/Type.h"
#include "llvm/ADT/Triple.h"
#include "llvm/ExecutionEngine/GenericValue.h"
#include "llvm/ExecutionEngine/JIT.h"
#include "llvm/ExecutionEngine/JITEventListener.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/ManagedStatic.h"
#include "llvm/Support/raw_ostream.h"
#include "llvm/System/Signals.h"
#include "llvm/System/Process.h"
#include "llvm/Target/TargetSelect.h"

#include "Jade/Decoder.h"
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

//===----------------------------------------------------------------------===//
// main Driver function
//
LLVMExecution::LLVMExecution(LLVMContext& C, Decoder* decoder): Context(C)  {
  std::string ErrorMsg;

  this->decoder = decoder;
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
	EE->addGlobalMapping(procedure->getFunction(), Addr);
}

bool LLVMExecution::mapFifo(Port* port, AbstractFifo* fifo) {
	void **portGV = (void**)EE->getPointerToGlobalIfAvailable(port->getGlobalVariable());
	
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

void LLVMExecution::run() {
	std::string ErrorMsg;
	Module* module = decoder->getModule();
	clock_t timer = clock ();
	
	// Run static constructors.
    EE->runStaticConstructorsDestructors(false);

   if (NoLazyCompilation) {
		for (Module::iterator I = module->begin(), E = module->end(); I != E; ++I) {
			Function *Fn = &*I;
			if (!Fn->isDeclaration())
				EE->getPointerToFunction(Fn);
		}
		cout << "--> No lazy compilation enable, the decoder has been compiled in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC << " ms \n";
	}
   
	Scheduler* scheduler = decoder->getScheduler();
	Function* func = scheduler->getMainFunction();

	std::vector<GenericValue> noargs;
	GenericValue Result = EE->runFunction(func, noargs);
}

void LLVMExecution::runFunction(Function* function) {
	std::vector<GenericValue> noargs;
	GenericValue Result = EE->runFunction(function, noargs);
}

void LLVMExecution::stop(pthread_t* thread) {
	Scheduler* scheduler = decoder->getScheduler();
	scheduler->stop(thread);
}

void LLVMExecution::recompile(Function* function) {
	EE->recompileAndRelinkFunction(function);
}

bool LLVMExecution::isCompiledGV(llvm::GlobalVariable* gv){
	return EE->getPointerToGlobalIfAvailable(gv) != NULL;
}

void* LLVMExecution::getGVPtr(llvm::GlobalVariable* gv){
	return EE->getPointerToGlobalIfAvailable(gv);
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


void LLVMExecution::setInputStimulus(std::string input){
	
	decoder->getScheduler()->setSource(input);
}

LLVMExecution::~LLVMExecution(){
	// Run static destructors.
	EE->runStaticConstructorsDestructors(true);

	delete EE;
	llvm_shutdown();
}
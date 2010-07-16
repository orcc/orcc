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
@brief Implementation of decoder execution function in class JIT
@author Jerome Gorin
@file DecoderExecution.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <iostream>
#include <errno.h>

#include "llvm/Type.h"
#include "llvm/ExecutionEngine/GenericValue.h"
#include "llvm/ExecutionEngine/JITEventListener.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/System/Signals.h"
#include "llvm/System/Process.h"
#include "llvm/Target/TargetSelect.h"

#include "Jade/JIT.h"
#include "Jade/Actor/ActionScheduler.h"
#include "Jade/Decoder/Decoder.h"
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


//===----------------------------------------------------------------------===//
// main Driver function
//
int JIT::initEngine(Decoder* decoder) {
  std::string ErrorMsg;

  sys::PrintStackTraceOnErrorSignal();
   
  module = decoder->getModule();

  //atexit(do_shutdown());  // Call llvm_shutdown() on exit.

  // If we have a native target, initialize it to ensure it is linked in and
  // usable by the JIT.
  InitializeNativeTarget();

  // If the user doesn't want core files, disable them.
  if (DisableCoreFiles)
    sys::Process::PreventCoreFiles();
  
  EngineBuilder builder(module);
  builder.setMArch(MArch);
  builder.setMCPU(MCPU);
  builder.setMAttrs(MAttrs);
  builder.setErrorStr(&ErrorMsg);
  builder.setEngineKind(ForceInterpreter
                        ? EngineKind::Interpreter
                        : EngineKind::JIT);


  EE = builder.create();
  if (!EE) {
    if (!ErrorMsg.empty())
      cout << ": error creating EE: " << ErrorMsg << "\n";
    else
      cout << ": unknown error creating EE!\n";
    exit(1);
  }

  EE->RegisterJITEventListener(createOProfileJITEventListener());

  EE->DisableLazyCompilation(NoLazyCompilation);
  
  // If the program doesn't explicitly call exit, we will need the Exit 
  // function later on to make an explicit call, so get the function now. 
  Constant *Exit = module->getOrInsertFunction("exit", Type::getVoidTy(Context),
                                                    Type::getInt32Ty(Context),
                                                    NULL);
  
  // Reset errno to zero on entry to main.
  errno = 0;

  // Run static constructors.
  EE->runStaticConstructorsDestructors(false);
}

void JIT::MapFunction(Function* function, void *Addr) {
	EE->addGlobalMapping(function, Addr);
}

void JIT::run(Function* func) {
	std::vector<GenericValue> noargs;
	EE->runFunction(func, noargs);
}
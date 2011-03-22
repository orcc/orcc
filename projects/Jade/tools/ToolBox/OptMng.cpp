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
@brief Optimization manager of JadeToolbox
@author Jerome Gorin
@file OptMng.cpp
@version 1.0
@date 2011/03/22
*/

//------------------------------
#include <iostream>

#include "OptMng.h"
//------------------------------

using namespace std;
using namespace llvm;

extern cl::opt<bool> OptLevelO0;
extern cl::opt<bool> OptLevelO1;
extern cl::opt<bool> OptLevelO2;
extern cl::opt<bool> OptLevelO3;

extern cl::opt<bool> OutputAssembly;
extern cl::opt<bool> OutputBitcode;
extern cl::opt<bool> OutputArchive;
extern cl::opt<bool> OutputCompressedArchive;

extern cl::opt<bool> UnitAtATime;
extern cl::opt<bool> DisableSimplifyLibCalls;
extern cl::opt<std::string> DefaultDataLayout;
extern cl::list<const PassInfo*, bool, PassNameParser> PassList;
extern cl::opt<bool> StandardCompileOpts;
extern cl::opt<bool> StandardLinkOpts;
extern cl::opt<bool> VerifyEach;
extern cl::opt<bool> StripDebug;
extern cl::opt<bool> DisableOptimizations;
extern cl::opt<bool> DisableInline;
extern cl::opt<bool> DisableInternalize;


//This part is taken as it is from opt tool of LLVM
inline void addPass(PassManagerBase &PM, Pass *P) {
  // Add the pass to the pass manager...
  PM.add(P);

  // If we are verifying all of the intermediate steps, add the verifier...
  if (VerifyEach) PM.add(createVerifierPass());
}

/// AddOptimizationPasses - This routine adds optimization passes
/// based on selected optimization level, OptLevel. This routine
/// duplicates llvm-gcc behaviour.
///
/// OptLevel - Optimization Level
void AddOptimizationPasses(PassManagerBase &MPM, PassManagerBase &FPM,
                           unsigned OptLevel) {
  createStandardFunctionPasses(&FPM, OptLevel);

  llvm::Pass *InliningPass = 0;
  if (OptLevel) {
    unsigned Threshold = 200;
    if (OptLevel > 2)
      Threshold = 250;
    InliningPass = createFunctionInliningPass(Threshold);
  } else {
    InliningPass = createAlwaysInlinerPass();
  }
  createStandardModulePasses(&MPM, OptLevel,
                             /*OptimizeSize=*/ false,
                             UnitAtATime,
                             /*UnrollLoops=*/ OptLevel > 1,
                             !DisableSimplifyLibCalls,
                             /*HaveExceptions=*/ true,
                             InliningPass);
}

void AddStandardCompilePasses(PassManagerBase &PM) {
  PM.add(createVerifierPass());                  // Verify that input is correct

  addPass(PM, createLowerSetJmpPass());          // Lower llvm.setjmp/.longjmp

  // If the -strip-debug command line option was specified, do it.
  if (StripDebug)
    addPass(PM, createStripSymbolsPass(true));

  if (DisableOptimizations) return;

  llvm::Pass *InliningPass = !DisableInline ? createFunctionInliningPass() : 0;

  // -std-compile-opts adds the same module passes as -O3.
  createStandardModulePasses(&PM, 3,
                             /*OptimizeSize=*/ false,
                             /*UnitAtATime=*/ true,
                             /*UnrollLoops=*/ true,
                             /*SimplifyLibCalls=*/ true,
                             /*HaveExceptions=*/ true,
                             InliningPass);
}

void AddStandardLinkPasses(PassManagerBase &PM) {
  PM.add(createVerifierPass());                  // Verify that input is correct

  // If the -strip-debug command line option was specified, do it.
  if (StripDebug)
    addPass(PM, createStripSymbolsPass(true));

  if (DisableOptimizations) return;

  createStandardLTOPasses(&PM, /*Internalize=*/ !DisableInternalize,
                          /*RunInliner=*/ !DisableInline,
                          /*VerifyEach=*/ VerifyEach);
}

void opt(string file, Module* M){
  // Create a PassManager to hold and optimize the collection of passes we are
  // about to build...
  //
  PassManager Passes;

  // Add an appropriate TargetData instance for this module...
  TargetData *TD = 0;
  const std::string &ModuleDataLayout = M->getDataLayout();
  if (!ModuleDataLayout.empty())
    TD = new TargetData(ModuleDataLayout);
  else if (!DefaultDataLayout.empty())
    TD = new TargetData(DefaultDataLayout);

  if (TD)
    Passes.add(TD);

  OwningPtr<PassManager> FPasses;
  if (OptLevelO0 ||OptLevelO1 || OptLevelO2 || OptLevelO3) {
    FPasses.reset(new PassManager());
    if (TD)
      FPasses->add(new TargetData(*TD));
  }

    // Create a new optimization pass for each one specified on the command line
  for (unsigned i = 0; i < PassList.size(); ++i) {
    // Check to see if -std-compile-opts was specified before this option.  If
    // so, handle it.
    if (StandardCompileOpts &&
		StandardCompileOpts.getPosition() < PassList.getPosition(i)) {
      AddStandardCompilePasses(Passes);
      StandardCompileOpts = false;
    }

    if (StandardLinkOpts &&
        StandardLinkOpts.getPosition() < PassList.getPosition(i)) {
      AddStandardLinkPasses(Passes);
      StandardLinkOpts = false;
    }

    if (OptLevelO1 && OptLevelO1.getPosition() < PassList.getPosition(i)) {
      AddOptimizationPasses(Passes, *FPasses, 1);
      OptLevelO1 = false;
    }

    if (OptLevelO2 && OptLevelO2.getPosition() < PassList.getPosition(i)) {
      AddOptimizationPasses(Passes, *FPasses, 2);
      OptLevelO2 = false;
    }

    if (OptLevelO3 && OptLevelO3.getPosition() < PassList.getPosition(i)) {
      AddOptimizationPasses(Passes, *FPasses, 3);
      OptLevelO3 = false;
    }

    const PassInfo *PassInf = PassList[i];
    Pass *P = 0;
    if (PassInf->getNormalCtor())
      P = PassInf->getNormalCtor()();
    else
      errs() << ": cannot create pass: "
             << PassInf->getPassName() << "\n";
    if (P) {
      PassKind Kind = P->getPassKind();
      addPass(Passes, P);
      }
    }

   // If -std-compile-opts was specified at the end of the pass list, add them.
  if (StandardCompileOpts) {
    AddStandardCompilePasses(Passes);
    StandardCompileOpts = false;
  }

  if (StandardLinkOpts) {
    AddStandardLinkPasses(Passes);
    StandardLinkOpts = false;
  }

  if (OptLevelO1)
    AddOptimizationPasses(Passes, *FPasses, 1);

  if (OptLevelO2)
    AddOptimizationPasses(Passes, *FPasses, 2);

  if (OptLevelO3)
    AddOptimizationPasses(Passes, *FPasses, 3);

  if (OptLevelO1 || OptLevelO2 || OptLevelO3)
    FPasses->run(*M);

  //Write output if need
  std::string ErrorInfo;

  raw_fd_ostream Out(file.c_str(), ErrorInfo,raw_fd_ostream::F_Binary);

  if (OutputAssembly){
      Passes.add(createPrintModulePass(&Out));
  }else if(OutputBitcode || OutputArchive || OutputCompressedArchive){
      Passes.add(createBitcodeWriterPass(Out));
  }

  if (!ErrorInfo.empty()) {
		 errs() << ErrorInfo << '\n';
		 exit(1);
  }


  // Now that we have all of the passes ready, run them.
  Passes.run(*M);

  Out.close();
}
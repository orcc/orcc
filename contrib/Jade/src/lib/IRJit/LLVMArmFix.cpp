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


#include "Jade/Jit/LLVMArmFix.h"
#include "Jade/Util/FunctionMng.h"

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/PassManager.h"
#include "llvm/ADT/Triple.h"
#include "llvm/MC/SubtargetFeature.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/FileUtilities.h"
#include "llvm/Support/FormattedStream.h"
#include "llvm/Support/Host.h"
#include "llvm/Support/Program.h"
#include "llvm/Support/Signals.h"
#include "llvm/Support/TargetRegistry.h"
#include "llvm/Support/ToolOutputFile.h"
#include "llvm/Target/TargetData.h"
#include "llvm/Target/TargetMachine.h"
//------------------------------

using namespace llvm;
using namespace std;

extern cl::opt<std::string> MArch;
extern cl::list<std::string> MAttrs;
extern cl::opt<std::string> MCPU;
extern cl::opt<std::string> VidFile;

extern char **environnement;

LLVMArmFix::LLVMArmFix(LLVMContext& C, Decoder* decoder, bool verbose): LLVMExecution(C, decoder, verbose)  {

}

void LLVMArmFix::run() {
    // Intermediate files to generate
	sys::Path AssemblyFile ("tmpAssembly.s");
	sys::Path DecoderFile ("tempDecoder");

	// Get first intruction of @main
	Module* module = decoder->getModule();
	Scheduler* scheduler = decoder->getScheduler();
	Function* mainFn = scheduler->getMainFunction();
	Function* initFn = scheduler->getInitFunction();
	Instruction* firstInst = &mainFn->getEntryBlock().front();

	// Set stop variable to produce an infinite loop
	GlobalVariable* stopVar = scheduler->getStopGV();
	stopVar->setInitializer(ConstantInt::get(Type::getInt32Ty(Context), 0));

	// Call initialize function inside main
	vector< Value *> Args;
	CallInst* callInst = CallInst::Create(initFn, Args, "", firstInst);

	// Get input file variable from source
	GlobalVariable* inputFileVar = new GlobalVariable(*module, Type::getInt8PtrTy(Context), false, GlobalValue::ExternalLinkage,0,"input_file", 0, false);

	// Initialize it to the input file
	Constant* inputChr = FunctionMng::createStdMessage(module, VidFile);
	new StoreInst(inputChr, inputFileVar, callInst);

	tool_output_file* file = generateNativeCode(AssemblyFile);

    // Mark the output files for removal.
    FileRemover AssemblyFileRemover(AssemblyFile.c_str());
    sys::RemoveFileOnSignal(AssemblyFile);
    FileRemover DecoderFileRemover(DecoderFile.c_str());
    sys::RemoveFileOnSignal(DecoderFile);

    // Compile and link assembly file with GCC
	compileAndLink(AssemblyFile, DecoderFile);

	// Launch decoder
	  sys::Program::ExecuteAndWait(
			  DecoderFile, 0, 0, 0, 0, 0, 0);
}

char ** LLVMArmFix::CopyEnv(char ** const envp) {
  // Count the number of entries in the old list;
  unsigned entries;   // The number of entries in the old environment list
  for (entries = 0; envp[entries] != NULL; entries++)
    /*empty*/;

  // Add one more entry for the NULL pointer that ends the list.
  ++entries;

  // If there are no entries at all, just return NULL.
  if (entries == 0)
    return NULL;

  // Allocate a new environment list.
  char **newenv = new char* [entries];
  if (newenv == NULL)
    return NULL;

  // Make a copy of the list.  Don't forget the NULL that ends the list.
  entries = 0;
  while (envp[entries] != NULL) {
    size_t len = strlen(envp[entries]) + 1;
    newenv[entries] = new char[len];
    memcpy(newenv[entries], envp[entries], len);
    ++entries;
  }
  newenv[entries] = NULL;

  return newenv;
}

void LLVMArmFix::RemoveEnv(const char * name, char ** const envp) {
  for (unsigned index=0; envp[index] != NULL; index++) {
    // Find the first equals sign in the array and make it an EOS character.
    char *p = strchr (envp[index], '=');
    if (p == NULL)
      continue;
    else
      *p = '\0';

    // Compare the two strings.  If they are equal, zap this string.
    // Otherwise, restore it.
    if (!strcmp(name, envp[index]))
      *envp[index] = '\0';
    else
      *p = '=';
  }

  return;
}

void LLVMArmFix::compileAndLink(sys::Path IntermediateAssemblyFile, sys::Path IntermediateDecoderFile) {
	string ErrMsg;
	sys::Path gcc = sys::Program::FindProgramByName("gcc");

	if (gcc.isEmpty()){
		errs() << "Can't find Gcc compiler, exiting without linking module \n";
		exit(1);
	}

	// Remove these environment variables from the environment
	  char ** clean_env = CopyEnv(environnement);
	  if (clean_env == NULL){
	    errs() << "No compiling environment found \n";
	    exit(1);
	  }


	  RemoveEnv("LIBRARY_PATH", clean_env);
	  RemoveEnv("COLLECT_GCC_OPTIONS", clean_env);
	  RemoveEnv("GCC_EXEC_PREFIX", clean_env);
	  RemoveEnv("COMPILER_PATH", clean_env);
	  RemoveEnv("COLLECT_GCC", clean_env);

	  // Run GCC to assemble and link the program into native code.
	  std::vector<std::string> args;
	  args.push_back(gcc.c_str());
	  args.push_back("-fno-strict-aliasing");
	  args.push_back("-O3");
	  args.push_back("-o");
	  args.push_back(IntermediateDecoderFile.c_str());
	  args.push_back(IntermediateAssemblyFile.c_str());

	  args.push_back("-lorcc");
	  args.push_back("-lSDL");
	  args.push_back("-lSDLmain");


	  // Now that "args" owns all the std::strings for the arguments, call the c_str
	  // method to get the underlying string array.  We do this game so that the
	  // std::string array is guaranteed to outlive the const char* array.
	  std::vector<const char *> Args;
	  for (unsigned i = 0, e = args.size(); i != e; ++i)
	    Args.push_back(args[i].c_str());
	  Args.push_back(0);

	  if (verbose) {
	     errs() << "Generating Native Executable With:\n";
	     PrintCommand(Args);
	  }

	  // Run the compiler to assembly and link together the program.
	  int R = sys::Program::ExecuteAndWait(
	    gcc, &Args[0], const_cast<const char **>(clean_env), 0, 0, 0, &ErrMsg);
	  delete [] clean_env;
}

void LLVMArmFix::PrintCommand(const std::vector<const char*> &args) {
  std::vector<const char*>::const_iterator I = args.begin(), E = args.end();
  for (; I != E; ++I)
    if (*I)
      errs() << "'" << *I << "'" << " ";
  errs() << "\n";
}

tool_output_file* LLVMArmFix::generateNativeCode( sys::Path IntermediateAssemblyFile) {
	Module* module = decoder->getModule();

	Triple targetTriple(module->getTargetTriple());

	if (targetTriple.getTriple().empty())
		targetTriple.setTriple(sys::getHostTriple());

	// Allocate target machine.  First, check whether the user has explicitly
	  // specified an archistring intermediateFile("tmpAssembly.s");tecture to compile for. If so we have to look it up by
	  // name, because it might be a backend that has no mapping to a target triple.
	  const Target *TheTarget = 0;
	  if (!MArch.empty()) {
	    for (TargetRegistry::iterator it = TargetRegistry::begin(),
	           ie = TargetRegistry::end(); it != ie; ++it) {
	      if (MArch == it->getName()) {
	        TheTarget = &*it;
	        break;
	      }
	    }

	    if (!TheTarget) {
	      errs() << ": error: invalid target '" << MArch << "'.\n";
	      exit(1);
	    }

	    // Adjust the triple to match (if known), otherwise stick with the
	    // module/host triple.
	    Triple::ArchType Type = Triple::getArchTypeForLLVMName(MArch);
	    if (Type != Triple::UnknownArch)
	    	targetTriple.setArch(Type);
	  } else {
	    std::string Err;
	    TheTarget = TargetRegistry::lookupTarget(targetTriple.getTriple(), Err);
	    if (TheTarget == 0) {
	      errs() << ": error auto-selecting target for module '"
	             << Err << "'.  Please use the -march option to explicitly "
	             << "pick a target.\n";
	      exit(1);
	    }
	  }

	  // Package up features to be passed to target/subtarget
	   std::string FeaturesStr;
	   if (MAttrs.size()) {
	     SubtargetFeatures Features;
	     for (unsigned i = 0; i != MAttrs.size(); ++i)
	       Features.AddFeature(MAttrs[i]);
	     FeaturesStr = Features.getString();
	   }

	   std::auto_ptr<TargetMachine>
	     target(TheTarget->createTargetMachine(targetTriple.getTriple(),
	                                           MCPU, FeaturesStr,
	                                           Reloc::Default, CodeModel::Default));
	   assert(target.get() && "Could not allocate target machine!");
	   TargetMachine &Target = *target.get();

	   // Open the file.
	   string error;
	   unsigned OpenFlags = 0;

	   tool_output_file *FDOut = new tool_output_file(IntermediateAssemblyFile.c_str(), error,
	                                                  false);
	   if (!error.empty()) {
	     errs() << error << '\n';
	     delete FDOut;
	     exit(1);
	   }


	   // Build up all of the passes that we want to do to the module.
	    PassManager PM;

	    // Add the target data from the target machine, if it exists, or the module.
	    if (const TargetData *TD = Target.getTargetData())
	      PM.add(new TargetData(*TD));
	    else
	      PM.add(new TargetData(module));

	    // Override default to generate verbose assembly.
	    Target.setAsmVerbosityDefault(true);

	    formatted_raw_ostream FOS(FDOut->os());

	    // Ask the target to add backend passes as necessary.
	    if (Target.addPassesToEmitFile(PM, FOS, TargetMachine::CGFT_AssemblyFile, CodeGenOpt::Default, true)) {
	      errs() << ": target does not support generation of this"
	             << " file type!\n";
	      exit(1);
	    }


	    PM.run(*module);


	    return FDOut;

}




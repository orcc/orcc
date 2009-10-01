#ifndef ORCCLLVM_H
#define ORCCLLVM_H

#include <fstream>

#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/ModuleProvider.h"
#include "llvm/LinkAllPasses.h"
#include "llvm/Linker.h"
#include "llvm/Target/TargetData.h" 
#include "llvm/ExecutionEngine/JIT.h"
#include "llvm/ExecutionEngine/Interpreter.h"
#include "llvm/ExecutionEngine/GenericValue.h"
#include "llvm/Support/IRBuilder.h"
#include "llvm/Support/MemoryBuffer.h"
#include "llvm/Support/raw_ostream.h"
#include "llvm/Bitcode/ReaderWriter.h"
#include "llvm/PassManager.h"
#include "llvm/Assembly/PrintModulePass.h"
#include "llvm/Analysis/Verifier.h"
#include "llvm/CallingConv.h"
#include "llvm/Transforms/Utils/Cloning.h"
#include "llvm/TypeSymbolTable.h"
#include "llvm/Transforms/Utils/ValueMapper.h"



using namespace llvm;

Module* mod;
ExistingModuleProvider* mp;
ExecutionEngine* engine;
PassManager passes;


void (*funcInitInput)(lff_t **fifo);
void (*funcInitOutput)(lff_t **fifo);

int initPasses(Module &gp_module, int optLevel) { //optlevel [0123]
    
	PassManager passes;
    passes.add(new TargetData(&gp_module));           // some passes need this as a first pass
    passes.add(createVerifierPass(PrintMessageAction)); // Make sure we start with a good graph
    //passes.add(new PrintModulePass());               // Visual feedback

    if (optLevel >= 1) {
        // Clean up disgusting code
        passes.add(createCFGSimplificationPass());
        // Remove unused globals
        passes.add(createGlobalDCEPass());
        // IP Constant Propagation
        passes.add(createIPConstantPropagationPass());
        // Clean up after IPCP
        passes.add(createInstructionCombiningPass());
        // Clean up after IPCP
        passes.add(createCFGSimplificationPass());
        // Inline small definitions (functions)
        passes.add(createFunctionInliningPass());
        // Simplify cfg by copying code
        passes.add(createTailDuplicationPass());
        if (optLevel >= 2) {
            // Merge & remove BBs
            passes.add(createCFGSimplificationPass());
            // Compile silly sequences
            passes.add(createInstructionCombiningPass());
            // Reassociate expressions
            passes.add(createReassociatePass());
            // Combine silly seq's
            passes.add(createInstructionCombiningPass());
            // Eliminate tail calls
            passes.add(createTailCallEliminationPass());
            // Merge & remove BBs
            passes.add(createCFGSimplificationPass());
            // Hoist loop invariants
            passes.add(createLICMPass());
            // Clean up after the unroller
            passes.add(createInstructionCombiningPass());
            // Canonicalize indvars
            passes.add(createIndVarSimplifyPass());
            // Unroll small loops
            passes.add(createLoopUnrollPass());
            // Clean up after the unroller
            passes.add(createInstructionCombiningPass());
            // GVN for load instructions
//            passes.add(createLoadValueNumberingPass());
            // Remove common subexprs
//            passes.add(createGCSEPass());
            // Constant prop with SCCP
            passes.add(createSCCPPass());
        }
        if (optLevel >= 3) {
            // Run instcombine again after redundancy elimination
            passes.add(createInstructionCombiningPass());
            // Delete dead stores
            passes.add(createDeadStoreEliminationPass());
            // SSA based 'Aggressive DCE'
            passes.add(createAggressiveDCEPass());
            // Merge & remove BBs
            passes.add(createCFGSimplificationPass());
            // Merge dup global constants
            passes.add(createConstantMergePass());
        }
    }

    // Merge & remove BBs
    passes.add(createCFGSimplificationPass());
    // Memory To Register
    passes.add(createPromoteMemoryToRegisterPass());
    // Compile silly sequences
    passes.add(createInstructionCombiningPass());
    // Make sure everything is still good.
    passes.add(createVerifierPass(PrintMessageAction));
    //passes.add(new PrintModulePass());               // Visual feedback

	 
  return passes.run(gp_module);

}


void* initModule(std::string moduleName, lff_t **fifoIn, lff_t **fifoOut)
{
  std::string error;
  std::string file;
  
  MemoryBuffer* buffer;
  Module* jit;

  Function* initInput;
  Function* initOutput;
  Function* sched;

  // Input FIle
  file = std::string(".//VTL//"+moduleName+".bc");


  // Load in the bitcode file containing the functions for each
  // bytecode operation.
  buffer = MemoryBuffer::getFile(file.c_str(), &error);
  jit = ParseBitcodeFile(buffer, &error);
  delete buffer;

  if ((moduleName.compare("clip")==0)||(moduleName.compare("serialize")==0)||(moduleName.compare("downsample")==0)||
	  (moduleName.compare("dcsplit")==0)||(moduleName.compare("final")==0)||(moduleName.compare("zzaddr")==0)||
	  (moduleName.compare("dequant")==0)||(moduleName.compare("interpolate")==0)||(moduleName.compare("retrans")==0)||
	  (moduleName.compare("add")==0))
  {
	buffer = MemoryBuffer::getFile("D:\\Projets\\orcc\\trunk\\runtime\\liborcc\\include\\lock_free_fifo.bc", &error);
	Module* mod = ParseBitcodeFile(buffer, &error);
	Linker::LinkModules(jit, mod, 0 /* error string */);
	delete buffer;
  }


  //// Get usefull function from module
  //initInput =  mod->getFunction(std::string(moduleName+"_initInput"));
  //initOutput =  mod->getFunction(std::string(moduleName+"_initOutput"));
  //sched =  mod->getFunction(std::string(moduleName+"_scheduler")); 


  initInput =  jit->getFunction(std::string(moduleName+"_initInput"));
  initOutput =  jit->getFunction(std::string(moduleName+"_initOutput"));
  sched =  jit->getFunction(std::string(moduleName+"_scheduler")); 

  // Get pointer function
  funcInitInput = (void(*)(lff_t **))(engine->getPointerToFunction(initInput));
  funcInitOutput = (void(*)(lff_t **))(engine->getPointerToFunction(initOutput));

  funcInitInput(fifoIn);
  funcInitOutput(fifoOut);

  return engine->getPointerToFunction(sched);

 }

#endif

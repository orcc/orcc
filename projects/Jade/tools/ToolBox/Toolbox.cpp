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
@brief Main function of JadeToolbox
@author Jerome Gorin
@file Toolbox.cpp
@version 1.0
@date 2010/12/21
*/

//------------------------------
#include <iostream>
#include <list>
#include <map>
#include <signal.h>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/PassManager.h"
#include "llvm/LinkAllPasses.h"
#include "llvm/Bitcode/Archive.h"
#include "llvm/Bitcode/ReaderWriter.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/IRReader.h"
#include "llvm/Support/ManagedStatic.h"
#include "llvm/Support/PassNameParser.h"
#include "llvm/Support/PrettyStackTrace.h"
#include "llvm/Support/StandardPasses.h"
#include "llvm/Target/TargetData.h"
#include "llvm/Target/TargetSelect.h"
#include "llvm/System/Signals.h"

#include "Jade/Util/OptionMng.h"
#include "Jade/Util/PackageMng.h"
//------------------------------
using namespace llvm;
using namespace std;

static cl::opt<bool>
OptLevelO0("O0",
           cl::desc("Optimization level 0. Similar to llvm-gcc -O0"));

static cl::opt<bool>
OptLevelO1("O1",
           cl::desc("Optimization level 1. Similar to llvm-gcc -O1"));

static cl::opt<bool>
OptLevelO2("O2",
           cl::desc("Optimization level 2. Similar to llvm-gcc -O2"));

static cl::opt<bool>
OptLevelO3("O3",
           cl::desc("Optimization level 3. Similar to llvm-gcc -O3"));


static cl::list<string>
Packages(cl::Positional, cl::OneOrMore, cl::desc("Input actors"));

static cl::opt<bool> 
OutputAssembly("S", cl::desc("Generate LLVM in assembly representation"));

static cl::opt<bool> 
OutputBitcode("c", cl::desc("Generate LLVM in bytecode representation"));

static cl::opt<bool> 
OutputArchive("a", cl::desc("Generate package in archives"));

static cl::opt<string> 
LibraryFolder("L", cl::Required, cl::ValueRequired, cl::desc("Input folder of Video Tool Library"));

//Optimization specific options
static cl::opt<bool>
UnitAtATime("funit-at-a-time",
            cl::desc("Enable IPO. This is same as llvm-gcc's -funit-at-a-time"),
            cl::init(true));

static cl::opt<bool>
DisableSimplifyLibCalls("disable-simplify-libcalls",
                        cl::desc("Disable simplify-libcalls"));

static cl::opt<std::string>
DefaultDataLayout("default-data-layout", 
          cl::desc("data layout string to use if not specified by module"),
          cl::value_desc("layout-string"), cl::init(""));
	
static cl::list<const PassInfo*, bool, PassNameParser>
PassList(cl::desc("Optimizations available:"));

static cl::opt<bool>
StandardCompileOpts("std-compile-opts",
                   cl::desc("Include the standard compile time optimizations"));

static cl::opt<bool>
StandardLinkOpts("std-link-opts",
                 cl::desc("Include the standard link time optimizations"));
static cl::opt<bool>
VerifyEach("verify-each", cl::desc("Verify after each transform"));

static cl::opt<bool>
StripDebug("strip-debug",
           cl::desc("Strip debugger symbol info from translation unit"));

static cl::opt<bool>
DisableOptimizations("disable-opt",
                     cl::desc("Do not run any optimization passes"));

static cl::opt<bool>
DisableInline("disable-inlining", cl::desc("Do not run the inliner pass"));

static cl::opt<bool>
DisableInternalize("disable-internalize",
                   cl::desc("Do not mark all symbols as internal"));


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
  }else if(OutputBitcode || OutputArchive){
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


void createArchives(map<sys::Path,string>* filesPath){
	map<string,Archive*> archives;
	map<string,Archive*>::iterator itArchive;
	map<sys::Path,string>::iterator itPaths;
	LLVMContext &Context = getGlobalContext();
	std::string errorMsg;	
	
	for (itPaths = filesPath->begin(); itPaths != filesPath->end(); itPaths++){
		Archive* archive;

		itArchive = archives.find(itPaths->second);
	
		// Create a new archive
		if(itArchive == archives.end()){
			sys::Path ArchivePath(LibraryFolder + itPaths->second + ".a");
			archive = Archive::CreateEmpty(ArchivePath, Context);
			archives.insert(pair<string,Archive*>(itPaths->second, archive));

		}else{
			archive = itArchive->second;
		}
		
		// Insert actor in archive
		archive->addFileBefore(itPaths->first, archive->end(), &errorMsg);

		if (errorMsg != "")
			cerr <<"Error when insert actor "<< itPaths->first.c_str() <<" in archive\n";
	}

	// Write archives to the disk
	for (itArchive = archives.begin(); itArchive != archives.end(); itArchive++){
		itArchive->second->writeToDisk(true, false, true, &errorMsg);
		if (errorMsg != "")
			cerr <<"Error when creat "<< itArchive->first << ".a\n";
	}

	// Erase all initial files
	cl::list<string>::iterator itPack;

	for (itPack=Packages.begin() ; itPack != Packages.end(); itPack++){
		sys::Path erasePath(LibraryFolder + *itPack);
		erasePath.eraseFromDisk(true,&errorMsg);
		if (errorMsg != "")
			cerr <<"Error when erase the directory"<< erasePath.str() << "\n";
	}
}

// This function scans through the package and return all files in that directory (recursively) 
// with for everyone the name of package. It uses the sys::Path::getDirectoryContent method 
// to perform the actual directory scans.
void recurseMapDirectories(const sys::Path& path, string name, map<sys::Path,string>& result) {
	string errorMsg;
	result.clear();
	set<sys::Path> content;
	if (path.getDirectoryContents(content, &errorMsg))
		if (errorMsg != "")
			cerr <<"Recurse directory error\n";

	set<sys::Path>::iterator I;
	for (I = content.begin() ; I != content.end() ; ++I) {
		// Make sure it exists and is a directory
		sys::PathWithStatus PwS(*I);
		const sys::FileStatus *Status = PwS.getFileStatus(false, &errorMsg);
		if (errorMsg != "")
			cerr <<"Recurse directory error\n";

		if (Status->isDir) {
			map<sys::Path,string> moreResults;
			recurseMapDirectories(*I, name, moreResults);
			result.insert(moreResults.begin(), moreResults.end());
		} else {
			result.insert(pair<sys::Path,string>(*I,name));
		}
	}
}

void buildFilesPath(map<sys::Path,string>* filesPath){
	cl::list<string>::iterator itPack;

	for (itPack=Packages.begin() ; itPack != Packages.end(); itPack++){
		sys::Path fullFilePath(LibraryFolder + *itPack);

		// Make sure the package is valid and exist
		if (!fullFilePath.exists()){
			cerr << "Package does not exist: " << itPack->c_str();
			continue;
		}

		sys::PathWithStatus PwS(fullFilePath);
		const sys::FileStatus *si = PwS.getFileStatus(false);
		if (!si){
	        cerr << "Can not find statut (file or directory) of " << itPack->c_str();
			continue;
		}

		// Create a path for all files in the package
		if (si->isDir) {
			map<sys::Path,string> dirPaths;
			recurseMapDirectories(fullFilePath, itPack->c_str(), dirPaths);
			filesPath->insert(dirPaths.begin(), dirPaths.end());
		} else {
			filesPath->insert(pair<sys::Path,string>(fullFilePath, itPack->c_str()));
		}
	}
}

void parseFiles(map<sys::Path,string>* filesPath, map<string,Module*>* modules){
	LLVMContext &Context = getGlobalContext();
	
	SMDiagnostic Err;
	map<sys::Path,string>::iterator itPath;
	
	for (itPath = filesPath->begin() ; itPath != filesPath->end(); itPath++){	
		//Iterate though all actors to parse
		if (!itPath->first.exists()){
			cerr <<"Actor "<< itPath->first.c_str() << " not found.\n";
			continue;
		}

		//Parse IR file
		Module* mod = ParseIRFile(itPath->first.c_str(), Err, Context);

		if (mod == NULL){
			cerr <<"Error when parsing "<< itPath->first.c_str() << ".\n";
			continue;
		}

		//Store results
		modules->insert(pair<string,Module*>(itPath->first.c_str(), mod));
	}
}

//main function of Jade toolbox
int main(int argc, char **argv) {
	sys::PrintStackTraceOnErrorSignal();
	PrettyStackTraceProgram X(argc, argv);

	cl::ParseCommandLineOptions(argc, argv, "Just-In-Time Adaptive Decoder Engine (Jade) \n");
	
	InitializeNativeTarget();
	
	//Verify options
	OptionMng::setDirectory(&LibraryFolder);

	//Build files Path
	map<sys::Path,string> filesPath;
	buildFilesPath(&filesPath);

	//Parsing files
	map<string,Module*> modules;
	parseFiles(&filesPath, &modules);

	//Make optimizations
	map<string,Module*>::iterator itModule;
	for (itModule=modules.begin() ; itModule != modules.end(); itModule++){
		opt(itModule->first, itModule->second);
	}

	//Create archives
	if(OutputArchive){
		createArchives(&filesPath);
	}
}
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
#include <map>

#include "llvm/LLVMContext.h"
#include "llvm/Support/PrettyStackTrace.h"
#include "llvm/Target/TargetSelect.h"

#include "Jade/XDFSerialize/InstanceIdRemoval.h"
#include "Jade/XDFSerialize/XDFParser.h"
#include "Jade/XDFSerialize/XDFWriter.h"
#include "Jade/Util/OptionMng.h"
#include "Jade/Util/CompressionMng.h"

#include "FilesMng.h"
#include "OptMng.h"
#include "ArMng.h"
//------------------------------
using namespace llvm;
using namespace std;

cl::opt<bool>
OptLevelO0("O0",
           cl::desc("Optimization level 0. Similar to llvm-gcc -O0"));

cl::opt<bool>
OptLevelO1("O1",
           cl::desc("Optimization level 1. Similar to llvm-gcc -O1"));

cl::opt<bool>
OptLevelO2("O2",
           cl::desc("Optimization level 2. Similar to llvm-gcc -O2"));

cl::opt<bool>
OptLevelO3("O3",
           cl::desc("Optimization level 3. Similar to llvm-gcc -O3"));


cl::list<string>
Packages(cl::Positional, cl::OneOrMore, cl::desc("Input actors"));

cl::opt<bool> 
OutputAssembly("S", cl::desc("Generate LLVM in assembly representation"));

cl::opt<bool> 
OutputBitcode("c", cl::desc("Generate LLVM in bytecode representation"));

cl::opt<bool> 
OutputArchive("a", cl::desc("Generate package in archives"));

cl::opt<bool> 
OutputCompressedArchive("ca", cl::desc("Generate package in compressed archives"));

cl::opt<string> 
LibraryFolder("L", cl::Required, cl::ValueRequired, cl::desc("Input folder of Video Tool Library"));

cl::opt<string> 
XDFFile("xdf", cl::desc("Compress XDF file"), cl::value_desc("XDF file"), cl::init(""));

//Optimization specific options
cl::opt<bool>
UnitAtATime("funit-at-a-time",
            cl::desc("Enable IPO. This is same as llvm-gcc's -funit-at-a-time"),
            cl::init(true));

cl::opt<bool>
DisableSimplifyLibCalls("disable-simplify-libcalls",
                        cl::desc("Disable simplify-libcalls"));

cl::opt<std::string>
DefaultDataLayout("default-data-layout", 
          cl::desc("data layout string to use if not specified by module"),
          cl::value_desc("layout-string"), cl::init(""));
	
cl::list<const PassInfo*, bool, PassNameParser>
PassList(cl::desc("Optimizations available:"));

cl::opt<bool>
StandardCompileOpts("std-compile-opts",
                   cl::desc("Include the standard compile time optimizations"));

cl::opt<bool>
StandardLinkOpts("std-link-opts",
                 cl::desc("Include the standard link time optimizations"));
cl::opt<bool>
VerifyEach("verify-each", cl::desc("Verify after each transform"));

cl::opt<bool>
StripDebug("strip-debug",
           cl::desc("Strip debugger symbol info from translation unit"));

cl::opt<bool>
DisableOptimizations("disable-opt",
                     cl::desc("Do not run any optimization passes"));

cl::opt<bool>
DisableInline("disable-inlining", cl::desc("Do not run the inliner pass"));

cl::opt<bool>
DisableInternalize("disable-internalize",
                   cl::desc("Do not mark all symbols as internal"));



//main function of Jade toolbox
int main(int argc, char **argv) {
	sys::PrintStackTraceOnErrorSignal();
	PrettyStackTraceProgram X(argc, argv);

	cl::ParseCommandLineOptions(argc, argv, "Just-In-Time Adaptive Decoder Engine (Jade) \n");
	
	InitializeNativeTarget();

	//Verify options
	OptionMng::setDirectory(&LibraryFolder);

	//Compress XDF
	if(XDFFile != ""){
		//Parse XDF
		LLVMContext &Context = getGlobalContext();
		XDFParser xdfParser(false);
		Network* network = xdfParser.parseFile(XDFFile, Context);

		//Remove all instance id
		InstanceIdRemoveAll instanceIdRemoveAll(network);
		instanceIdRemoveAll.removal();

		//Write new XDF
		XDFWriter xdfWriter(XDFFile, network);
		xdfWriter.writeXDF();

		//Create a XDF GZip file
		CompressionMng::compressFile(XDFFile);
	}

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
	if(OutputArchive || OutputCompressedArchive){
		createArchives(&filesPath);
	}
}
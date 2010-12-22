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
@brief Main function of JadeTools
@author Jerome Gorin
@file JadeTools.cpp
@version 1.0
@date 2010/12/21
*/

//------------------------------
#include <list>
#include <map>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/IRReader.h"
#include "llvm/Support/PrettyStackTrace.h"
#include "llvm/System/Signals.h"
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
Files(cl::Positional, cl::OneOrMore, cl::desc("Input actors"));

cl::opt<bool> 
AssemblyFlag("S", cl::desc("Generate LLVM in assembly representation"));

cl::opt<bool> 
BitcodeFlag("c", cl::desc("Generate LLVM in bytecode representation"));

cl::opt<bool> 
ArchiveFlag("a", cl::desc("Generate package in archives"));

cl::opt<string> 
LibraryFolder("L", cl::Required, cl::ValueRequired, cl::desc("Input folder of Video Tool Library"));

int main(int argc, char **argv) {
	sys::PrintStackTraceOnErrorSignal();
	PrettyStackTraceProgram X(argc, argv);

	SMDiagnostic Err;
	LLVMContext &Context = getGlobalContext();
	
	cl::list<string>::iterator it;
	map<string,Module*> modules;

	for (it=Files.begin() ; it != Files.end(); it++){
		Module* mod = ParseIRFile(it->c_str(), Err, Context);
		modules.insert(pair<string,Module*>(*it, mod));
	}
	
}
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
@brief Main function of Jade
@author Jerome Gorin
@file Jade.cpp
@version 0.1
@date 2010/04/13
*/

//------------------------------
#include <time.h>
#include <signal.h>

#include "llvm/LLVMContext.h"
#include "llvm/Target/TargetSelect.h"


#include "Jade/XDFParser.h"
#include "Jade/DecoderEngine.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/System/Signals.h"
#include "llvm/Support/PrettyStackTrace.h"

#ifdef BSDL
#include "BSDLParser/BSDLParser.h"
#endif
//------------------------------

using namespace llvm;


// options

static cl::opt<std::string>
XDFFile("xdf", cl::Required, cl::ValueRequired, cl::desc("XDF network file"), cl::value_desc("XDF filename"));
static cl::opt<std::string>
BSDLFile("bsdl", cl::desc("Bitstream description file"), cl::value_desc("BSDL filename"));
static cl::opt<std::string>
VidFile("i", cl::Required, cl::ValueRequired, cl::desc("Encoded video file"), cl::value_desc("Video filename"));

extern "C"{
char *input_file;
}

void clean_exit(int sig){
	exit(0);
}

int main(int argc, char **argv) {
	// Print a stack trace if we signal out.
	llvm::sys::PrintStackTraceOnErrorSignal();
	llvm::PrettyStackTraceProgram X(argc, argv);
	llvm::cl::ParseCommandLineOptions(argc, argv, "Just-In-Time Adaptive Decoder Engine (Jade) \n");
	(void) signal(SIGINT, clean_exit);
    
	//Initialize context
	InitializeNativeTarget();
	LLVMContext &Context = getGlobalContext();

	clock_t timer = clock ();
	input_file = (char*)VidFile.c_str();

	#ifdef BSDL
	if (!BSDLFile.empty()){
		BSDLParser *bsdlParser = new BSDLParser(BSDLFile);
		bsdlParser->ParseBSDL();
		delete bsdlParser;
	}
	#endif

	printf("Jade started, parsing file %s. \n", XDFFile.getValue().c_str());
	XDFParser *xdfParser = new XDFParser(XDFFile);
	Network* network = xdfParser->ParseXDF(Context);
	//delete xdfParser;
	printf("Network parsed in : %d ms, start engine :\n", (clock () - timer) * 1000 / CLOCKS_PER_SEC);

	DecoderEngine engine(Context);
	engine.load(network);
	
	printf("Engine created in : %d ms , start decoding.\n", (clock () - timer) * 1000 / CLOCKS_PER_SEC);
}

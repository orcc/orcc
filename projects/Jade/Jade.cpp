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

#ifdef __APPLE__
#include "SDL.h"
#endif

using namespace llvm;
using namespace llvm::cl;


// Jade options
opt<std::string>
XDFFile("xdf", Required, ValueRequired, desc("XDF network file"), value_desc("XDF filename"));

opt<std::string> 
BSDLFile("bsdl", desc("Bitstream description file"), value_desc("BSDL filename"));

opt<std::string> 
VidFile("i", Required, ValueRequired, desc("Encoded video file"), value_desc("Video filename"));

opt<std::string>
VTLDir("L", desc("Video Tools Library directory"),
	   Required,
	   ValueRequired,
	   value_desc("VTL Folder"), 
	   init(""));

opt<std::string> 
ToolsDir("T", desc("Jade tools directory"),
			  Required,
			  ValueRequired,
			  value_desc("Tools Folder"), 
			  init(""));

opt<std::string> 
OutputDir("o", desc("Output folder for trace/Module/Error files"), 
			  value_desc("Trace folder"), 
			  init(""));

opt<bool> 
ForceInterpreter("force-interpreter", desc("Force interpretation: disable JIT"),
									  init(false));

opt<bool> 
nodisplay("nodisplay", desc("Deactivate display"),
					   init(false));

opt<std::string> 
MArch("march", desc("Architecture to generate assembly for (see --version)"));

opt<bool> 
DisableCoreFiles("disable-core-files", Hidden,
                   desc("Disable emission of core files if possible"));

opt<bool> 
NoLazyCompilation("disable-lazy-compilation",
                  desc("Disable JIT lazy compilation"),
                  init(false));

list<std::string> 
MAttrs("mattr", CommaSeparated,
         desc("Target specific attributes (-mattr=help for details)"),
         value_desc("a1,+a2,-a3,..."));

opt<std::string> 
MCPU("mcpu", desc("Target a specific cpu type (-mcpu=help for details)"),
       value_desc("cpu-name"),
       init(""));

opt<std::string> 
Fifo("fifo", CommaSeparated,
			 desc("Specify fifo to be used in the decoder"),
			 value_desc("trace, circular, fast"),
			 init("circular"));

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

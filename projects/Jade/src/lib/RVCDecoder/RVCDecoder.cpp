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
@brief Implementation of RVCDecoder library
@author Olivier Labois
@file RVCDecoder.cpp
@version 1.0
@date 21/04/2011
*/

//------------------------------

#include "llvm/LLVMContext.h"
#include "llvm/Target/TargetSelect.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/Signals.h"
#include "llvm/Support/PassNameParser.h"
#include "llvm/PassSupport.h"

#include "Jade/XDFSerialize/XDFParser.h"
#include "Jade/Jit/LLVMExecution.h"
#include "Jade/Jit/LLVMOptimizer.h"
#include "Jade/Jit/LLVMUtility.h"
#include "Jade/Util/FifoMng.h"
#include "Jade/RVCEngine.h"

#include "source.h"
#include "display.h"

using namespace std;
using namespace llvm;
using namespace llvm::cl;


cl::opt<FifoTy> Fifo(values(clEnumVal(trace,   "trace"),
							clEnumVal(circular,  "circular"),
							clEnumVal(unprotected, "fast"),
							clEnumValEnd),
					 init(circular));

// Jade options
cl::opt<std::string> VTLDir(init(""));

cl::opt<std::string> SystemDir(init(""));

cl::opt<std::string> OutputDir(init("c://trace//"));

cl::opt<bool> ForceInterpreter(init(false));

cl::opt<std::string> MArch("");

cl::opt<bool> DisableCoreFiles("");

cl::opt<bool> NoLazyCompilation(init(false));

cl::list<std::string> MAttrs("");

cl::opt<std::string> MCPU(init(""));

cl::opt<int> FifoSize(init(4096));


Decoder* decoder;
#include "llvm/Support/Threading.h"

#ifdef __cplusplus
extern "C" {
#endif

#include "Jade/RVCDecoder/RVCDecoder.h"
#include "source.h"


int nalState;

int bufferBusy;
int safeguardFrameEmpty = 1;
	
int* stopVar;

//Configure verbose mode
bool verbose = true;


//Native elts
char *yuv_file;
char *write_file;
void print_usage(){
}

using namespace std;

void rvc_init(char *XDF, char* VTLFolder, int isAVCFile){

	//Initialize context
	llvm_start_multithreaded();	
	InitializeNativeTarget();
	LLVMContext &Context = getGlobalContext();


	//Parsing XDF
	XDFParser xdfParser(false);
	Network* network = xdfParser.parseChar(XDF, Context);

	//Create the Configuration from the network
	Configuration* configuration = new Configuration(network);

	// Parsing actor and bound it to the configuration
	RVCEngine engine(Context, VTLFolder, Fifo, FifoSize, "", "", false, false, verbose);
	map<string, Actor*>* requieredActors = engine.parseActors(configuration);
	configuration->setActors(requieredActors);

	//Create decoder
	decoder = new Decoder(Context, configuration, verbose);

	// Optimize decoder
	LLVMOptimizer opt(decoder);
	opt.optimize(3);

	//Initialize the execution engine
	LLVMExecution* llvmEE = decoder->getEE();
	stopVar = llvmEE->initialize();

	if (isAVCFile){
		source_isAVCFile();
	}
}

int rvc_decode(unsigned char* nal, int nal_length, char* outBuffer, int newBuffer){

	//Initialize buffer state
	if(newBuffer){
		bufferBusy = 0;
	}
	
	//Prepare source and display
	source_prepare(nal, nal_length);
	displayYUV_prepare(outBuffer);

	//Start decoder
	decoder->getEE()->run();


	//Reset reading nal state for the next use
	if(nalState == NAL_IS_READ){
		if(safeguardFrameEmpty){
			nalState = NAL_NOT_READ;
		}else{
			nalState = NAL_ALREADY_READ;
		}
	}

	//Return the number of generated frames
	if(!safeguardFrameEmpty) return 2;
	if(bufferBusy) return 1;
	return 0;
}


void rvc_close(){
	llvm_stop_multithreaded();
	delete decoder;
}

#ifdef __cplusplus
}
#endif

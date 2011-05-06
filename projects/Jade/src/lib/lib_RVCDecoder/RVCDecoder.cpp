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
@brief Implementation of RVCDecoder
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
#include "Jade/Util/FifoMng.h"
#include "Jade/RVCEngine.h"
#include "Jade/Actor/Display.h"

using namespace std;
using namespace llvm;
using namespace llvm::cl;


cl::opt<FifoTy> Fifo(values(clEnumVal(trace,   "trace"),
							clEnumVal(circular,  "circular"),
							clEnumVal(unprotected, "fast"),
							clEnumValEnd),
					 init(circular));

// Jade options
cl::opt<std::string> XDFFile(init("D:\\Users\\olabois\\orcc\\trunk\\projects\\Jade\\VTL\\Top_RVC.xdf"));

cl::opt<std::string> VTLDir(init("D:\\Users\\olabois\\orcc\\trunk\\projects\\Jade\\VTL\\"));

cl::opt<std::string> BSDLFile("");

cl::opt<std::string> VidFile(init("D:\\Users\\olabois\\sequences\\MPEG4\\SIMPLE\\P-VOP\\hit001.m4v"));

cl::opt<std::string> InputDir(init(""));

cl::opt<std::string> SystemDir(init(""));

cl::opt<std::string> YuvFile(init(""));

cl::opt<std::string> ScFile(init(""));

cl::opt<std::string> OutputDir(init(""));

cl::opt<bool> ForceInterpreter(init(false));

cl::opt<bool> nodisplay(init(false));

cl::opt<std::string> MArch("");

cl::opt<bool> DisableCoreFiles("");

cl::opt<bool> disableMultiCore(init(false));

cl::opt<bool> NoLazyCompilation(init(false));

cl::list<std::string> MAttrs("");

cl::opt<std::string> TargetTriple("");

cl::opt<std::string> MCPU(init(""));

cl::opt<bool> OptLevelO1("");

cl::opt<bool> OptLevelO2("");

cl::opt<bool> OptLevelO3("");

cl::opt<int> FifoSize(init(10000));

cl::opt<int> StopAt(init(0));

static cl::opt<bool> Verbose(init(false));

static cl::opt<bool> Verify(init(false));

static cl::opt<bool> Console(init(false));

static cl::opt<bool> noMerging(init(false));

cl::list<const PassInfo*, bool, PassNameParser> PassList("");


int Display::stopAfter = StopAt;

RVCEngine* engine;
Decoder* decoder;

#ifdef __cplusplus
extern "C" {
#endif

#include "Jade/lib_RVCDecoder/RVCDecoder.h"

void rvc_init(char *XDF){

	//Initialize context
	LLVMContext &Context = getGlobalContext();
	InitializeNativeTarget();
	//setOptions();

	//Loading decoderEngine
	engine = new RVCEngine(Context, VTLDir, Fifo, FifoSize, SystemDir, OutputDir, noMerging, disableMultiCore, Verbose);

	//Parsing XDF
	XDFParser xdfParser(false);
	Network* network = xdfParser.parseChar(XDF, Context);

	//Load network
	engine->load(network, 3);

	//Prepare network
	decoder = engine->prepare(network);
		//TODO : Prepare output of the network
	


	// Optimizing decoder
	/*if (optLevel > 0){
		engine->optimize(network, optLevel);
	}*/
}

int rvc_decode(void *PlayerStruct, unsigned char* nal, int nal_length, RVCFRAME *Frame, int *LayerCommand){
	
	//Start decoder
	decoder->getEE()->start(nal, nal_length);

//engine->run(network, VidFile);
	return 0;
}

int rvc_close(void *PlayerStruct){

	return 0;
}

#ifdef __cplusplus
}
#endif

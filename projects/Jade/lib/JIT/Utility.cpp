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
@brief Implementation of utility function in class JIT
@author Jerome Gorin
@file Utility.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <iostream>
#include <fstream>

#include "llvm/Instructions.h"
#include "llvm/Analysis/Verifier.h"
#include "llvm/Support/raw_ostream.h"
#include "llvm/Support/ManagedStatic.h"

#include "Jade/JIT.h"
#include "Jade/Actor/Port.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Decoder/InstancedActor.h"
#include "Jade/Fifo/AbstractFifo.h"
//------------------------------

using namespace llvm;
using namespace std;


JIT::JIT(llvm::LLVMContext& C): Context(C){
	module = NULL;
	//llvm_shutdown_obj Y;  // Call llvm_shutdown() on exit.
}

JIT::~JIT (){
	if (EE != NULL){
		EE->runStaticConstructorsDestructors(true);
	}
	
	do_shutdown();

}

void JIT::do_shutdown() {
	delete EE;
	llvm_shutdown();
}


void JIT::printModule(string file, Decoder* decoder){
	std::string ErrorInfo;
	Module* module = decoder->getModule();

	std::auto_ptr<raw_fd_ostream> Out(new raw_fd_ostream(file.c_str(), ErrorInfo, raw_fd_ostream::F_Binary));

	if (!ErrorInfo.empty()) {
		std::cout << ErrorInfo << '\n';
		return;
	}

	*Out << *module;
}


void JIT::verify(string file, Decoder* decoder){
	std::string Err;
	Module* module = decoder->getModule();

	if (verifyModule(*module, ReturnStatusAction, &Err)) {
		ofstream output;
		output.open(file.c_str());
        output << Err;
		cout << "Error found in the current decoder, output " << file << " error file \n";
	} else {
		cout << "Generated decoder is ok. \n";
	}

}

void* JIT::getFifoPointer(GlobalVariable* variable){
	return EE->getPointerToGlobal(variable);
}


void* JIT::getFunctionPointer(llvm::Function* function){
	return EE->getPointerToFunction(function);
}


void JIT::addPrintf(std::string text, Decoder* decoder, Function* function, BasicBlock* bb){
	
	Module* module = decoder->getModule();
	AbstractFifo* fifo = decoder->getFifo();

	Constant* arrayContent = ConstantArray::get(Context, text,true);
	GlobalVariable *NewArray =
		new GlobalVariable(*module, arrayContent->getType(),
		true, GlobalVariable::InternalLinkage, arrayContent, text);


	vector<Value*> vector;
	vector.push_back(ConstantExpr::getBitCast(NewArray,Type::getInt8PtrTy(Context)));
	CallInst* retVal = CallInst::Create(fifo->getPrintfFunction(), vector.begin(), vector.end(), "test", bb);
}
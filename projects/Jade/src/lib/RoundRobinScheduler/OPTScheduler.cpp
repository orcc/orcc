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
@brief Implementation of class OPTScheduler
@author Jerome Gorin
@file OPTScheduler.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"

#include "OPTScheduler.h"

#include "Jade/Decoder.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Actor/Action.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/Actor/ActionTag.h"
#include "Jade/Core/Actor.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/Actor/Procedure.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Fifo/FifoOpt.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace llvm;
using namespace std;

OPTScheduler::OPTScheduler(llvm::LLVMContext& C, Decoder* decoder) : DPNScheduler(C, decoder) {

}

void OPTScheduler::createScheduler(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* scheduler){
	map<string,Port*>::iterator it;

	//Initialize inputs
	map<string,Port*>* inputs = instance->getInputs();
	for (it = inputs->begin(); it != inputs->end(); it++){
		Function* init = FifoOpt::initializeIn(module, it->second);
		CallInst::Create(init, "", entryBB);
	}

	//Initialize outputs
	map<string,Port*>* outputs = instance->getOutputs();
	for (it = outputs->begin(); it != outputs->end(); it++){
		Function* init = FifoOpt::initializeOut(module, it->second);
		CallInst::Create(init, "", entryBB);
	}


	if (actionScheduler->hasFsm()){
		createSchedulerFSM(instance, BB, incBB, returnBB , scheduler);
	}else{
		createSchedulerNoFSM(instance, BB, incBB, returnBB, scheduler);
	}

	//Close inputs
	for (it = inputs->begin(); it != inputs->end(); it++){
		Function* close = FifoOpt::closeIn(module, it->second);
		CallInst::Create(close, "", returnBB->getTerminator());
	}

	//Close outputs
	for (it = outputs->begin(); it != outputs->end(); it++){
		Function* close = FifoOpt::closeOut(module, it->second);
		CallInst::Create(close, "", returnBB->getTerminator());
	}
}

void OPTScheduler::createRead(Port* port, Variable* variable, ConstantInt* numTokens, BasicBlock* BB){

}

Value* OPTScheduler::createInputTest(Port* port, ConstantInt* numTokens, BasicBlock* BB){
	return NULL;
}

Value* OPTScheduler::createOutputTest(Port* port, ConstantInt* numTokens, BasicBlock* BB){
	
	int test = port->getSize();
	/*
	LoadInst* indexVal = new LoadInst(port->getIndex(), "", false, BB);
	BinaryOperator* subVal = BinaryOperator::Create(Instruction::Sub, const_int32_11, int32_24, "", label_14);*/


	return NULL;
}

void OPTScheduler::createWrite(Port* port, Variable* variable, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createReadEnd(Port* port, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createWriteEnd(Port* port, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createPeek(Port* port, Variable* variable, ConstantInt* numTokens, BasicBlock* BB){


}

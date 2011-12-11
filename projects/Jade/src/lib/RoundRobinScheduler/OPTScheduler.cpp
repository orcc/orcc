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
#include "llvm/Constants.h"
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

OPTScheduler::OPTScheduler(llvm::LLVMContext& C, Decoder* decoder, bool debug) : DPNScheduler(C, decoder, debug) {

}

void OPTScheduler::createScheduler(Instance* instance, BasicBlock* BB, BasicBlock* incBB, BasicBlock* returnBB, Function* scheduler){
	map<string,Port*>::iterator it;

	//Initialize inputs
	map<string,Port*>* inputs = instance->getInputs();
	for (it = inputs->begin(); it != inputs->end(); it++){
		Function* init = FifoOpt::initializeIn(module, it->second);
		CallInst::Create(init, "", entryBB->getTerminator());
	}

	//Initialize outputs
	map<string,Port*>* outputs = instance->getOutputs();
	for (it = outputs->begin(); it != outputs->end(); it++){
		Function* init = FifoOpt::initializeOut(module, it->second);
		CallInst::Create(init, "", entryBB->getTerminator());
	}

	// Add read/write/peek access
	std::list<Action*>::iterator itAct;
	std::list<Action*>* actions = instance->getActions();
	for (itAct = actions->begin(); itAct != actions->end(); itAct++){
		createReadWritePeek(*itAct);
	}


	// Add scheduler
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

void OPTScheduler::createReadWritePeek(Action* action){
	
	// Create read accesses
	Pattern* input = action->getInputPattern();

	if (!input->isEmpty()){
		createReads(action->getBody(), input);
	}

	// Create write accesses
	Pattern* output = action->getOutputPattern();

	if (!output->isEmpty()){
		createWrites (action->getBody(), output);
	}

	// Create peek accesses
	Pattern* peek = action->getPeekPattern();

	if (!peek->isEmpty()){
		createPeeks (action->getScheduler(), peek);
	}

}

void OPTScheduler::createRead(Port* port, Variable* variable, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createWrites (Procedure* procedure, Pattern* pattern){
	Function* function = procedure->getFunction();
	BasicBlock* BB = &function->back();
	
	//Get tokens and var
	map<Port*, ConstantInt*>::iterator it;
	map<Port*, Variable*>::iterator itVar;
	map<Port*, ConstantInt*>* numTokensMap = pattern->getNumTokensMap();

	for (it = numTokensMap->begin(); it != numTokensMap->end(); it++){
		Port* port = it->first;
		ConstantInt* numTokens = it->second;
		
		//Create write
		Value* value = replaceAccess(port, procedure);

		BinaryOperator* add = BinaryOperator::Create(Instruction::Add, value, numTokens, "", BB->getTerminator());
		new StoreInst(add, port->getIndex(), BB->getTerminator());
	}
}

void OPTScheduler::createReads (Procedure* procedure, Pattern* pattern){
	Function* function = procedure->getFunction();
	BasicBlock* BB = &function->back();
	
	//Get tokens and var
	map<Port*, ConstantInt*>::iterator it;
	map<Port*, Variable*>::iterator itVar;
	map<Port*, ConstantInt*>* numTokensMap = pattern->getNumTokensMap();

	for (it = numTokensMap->begin(); it != numTokensMap->end(); it++){
		Port* port = it->first;
		ConstantInt* numTokens = it->second;
		
		//Create read
		Value* value = replaceAccess(port, procedure);

		BinaryOperator* add = BinaryOperator::Create(Instruction::Add, value, numTokens, "", BB->getTerminator());
		new StoreInst(add, port->getIndex(), BB->getTerminator());
	}
}


void OPTScheduler::createPeeks (Procedure* procedure, Pattern* pattern){
	Function* function = procedure->getFunction();
	BasicBlock* BB = &function->back();
	
	//Get tokens and var
	map<Port*, ConstantInt*>::iterator it;
	map<Port*, Variable*>::iterator itVar;
	map<Port*, ConstantInt*>* numTokensMap = pattern->getNumTokensMap();

	for (it = numTokensMap->begin(); it != numTokensMap->end(); it++){
		Port* port = it->first;
		ConstantInt* numTokens = it->second;
		
		//Create peek
		Value* value = replaceAccess(port, procedure);
	}
}

Value* OPTScheduler::replaceAccess (Port* port, Procedure* proc){
	Function* function = proc->getFunction();
	ConstantInt* sizeVal = ConstantInt::get(Context, APInt(32, port->getSize()));
	ConstantInt* zero = ConstantInt::get(Context, APInt(32, 0));
	

	//Get load instruction on port
	GlobalVariable* portPtr = port->getPtrVar()->getGlobalVariable();
	for (Value::use_iterator UI = portPtr->use_begin(), UE = portPtr->use_end();
		UI != UE; ++UI) {
			Use *U = &UI.getUse();
			Instruction *I = cast<Instruction>(U->getUser());
			BasicBlock* BB = I->getParent();
			if (BB->getParent() == function){
				LoadInst* loadInst = cast<LoadInst>(I);
				
				//Get bitcast instruction on load
				for (Value::use_iterator LI = loadInst->use_begin(), LE = loadInst->use_end();
					LI != LE; ++LI) {
					Use* CastU = &LI.getUse();
					if (isa<BitCastInst>(CastU->getUser())){
						BitCastInst* CastInst = cast<BitCastInst>(CastU->getUser());
						ArrayType* arrayTy = ArrayType::get(port->getType(), port->getSize());

						// Load index and create a new cast
						LoadInst* indexVal = new LoadInst(port->getIndex(), "", loadInst);
						BitCastInst* newCastInst = new BitCastInst(loadInst, PointerType::getUnqual(arrayTy), "", CastInst);
						
						//Get GET instruction on bitcast
						std::vector<GetElementPtrInst*> GEPs;
						for (Value::use_iterator GI = CastInst->use_begin(), GE = CastInst->use_end();
							GI != GE; ++GI) {
								User* user = GI.getUse().getUser();
								if (isa<GetElementPtrInst>(user)){
									GetElementPtrInst* GEPInst = cast<GetElementPtrInst>(user);

									// Set new GEP idx
									 std::vector<Value*> GEPIdx;
									 GEPIdx.push_back(zero);
									  for (User::op_iterator I = GEPInst->idx_begin()+1, E = GEPInst->idx_end(); I != E; ++I) {
										  Value *OpC = cast<Value>(*I);
										  if (OpC->getType()->getScalarSizeInBits() < 32) {
											  OpC = new ZExtInst(OpC, Type::getInt32Ty(Context), "", GEPInst);
										  }else if (OpC->getType()->getScalarSizeInBits() > 32){
											  OpC = new TruncInst(OpC, Type::getInt32Ty(Context), "", GEPInst);
										  }

										  BinaryOperator* add = BinaryOperator::Create(Instruction::Add, indexVal, OpC, "", GEPInst);
										  BinaryOperator* modulo = BinaryOperator::Create(Instruction::URem, add, sizeVal, "", GEPInst);
										  GEPIdx.push_back(modulo);
									  }
									  
									  // Create the new GEP
									  GetElementPtrInst* newGEPInst = GetElementPtrInst::Create(newCastInst, GEPIdx, "", GEPInst);
									  GEPInst->replaceAllUsesWith(newGEPInst);

									  // Remove old GEP
									  GEPs.push_back(GEPInst);
								}
						}
						
						// Remove useless nodes
						std::vector<GetElementPtrInst*>::iterator it;
						for (it = GEPs.begin(); it != GEPs.end(); it++){
							(*it)->eraseFromParent();
						}
						CastInst->eraseFromParent();
						return indexVal;
					}
				}
			}

				
	}
}

Value* OPTScheduler::createInputTest(Port* port, ConstantInt* numTokens, BasicBlock* BB){
	LoadInst* indexVal = new LoadInst(port->getIndex(), "", false, BB);
	BinaryOperator* addVal = BinaryOperator::Create(Instruction::Add, indexVal, numTokens, "", BB);
	LoadInst* tokenVal = new LoadInst(port->getRoomToken(), "", false, BB);
	return new ICmpInst(*BB, ICmpInst::ICMP_ULE, addVal, tokenVal, "");
}

Value* OPTScheduler::createOutputTest(Port* port, ConstantInt* numTokens, BasicBlock* BB){
	// Usefull constants
	Constant* fifoSizeCst = ConstantInt::get(Type::getInt32Ty(Context), port->getSize());
	ConstantInt* zero = ConstantInt::get(Context, APInt(32, 0));
	ConstantInt* three = ConstantInt::get(Context, APInt(32, 3));

	//Create subs
	LoadInst* indexVal = new LoadInst(port->getIndex(), "", false, BB);
	BinaryOperator* subVal = BinaryOperator::Create(Instruction::Sub,  fifoSizeCst , indexVal, "", BB);

	// Get read ind
	LoadInst* portStructPtr = new LoadInst(port->getFifoVar(), "", false, BB);
	std::vector<Value*> readind_indices;
    readind_indices.push_back(zero);
    readind_indices.push_back(three);
    Instruction* readIndPtr = GetElementPtrInst::Create(portStructPtr, readind_indices, "", BB);
	LoadInst* readIndVal = new LoadInst(readIndPtr, "", false, BB);
	GetElementPtrInst* readIndValPtr = GetElementPtrInst::Create(readIndVal, zero, "", BB); // source_O->read_inds[0]
	LoadInst* readIndVal0 = new LoadInst(readIndValPtr, "", false, BB);

	// Add and compare to numTokens
	BinaryOperator* resultVal = BinaryOperator::Create(Instruction::Add, subVal, readIndVal0, "", BB);
    ICmpInst* compVal = new ICmpInst(*BB, ICmpInst::ICMP_ULE, numTokens, resultVal, "");
	return compVal;
}

void OPTScheduler::createWrite(Port* port, Variable* variable, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createReadEnd(Port* port, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createWriteEnd(Port* port, ConstantInt* numTokens, BasicBlock* BB){

}

void OPTScheduler::createPeek(Port* port, Variable* variable, ConstantInt* numTokens, BasicBlock* BB){


}

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
@brief Implementation of class BroadcastActor
@author Jerome Gorin
@file BroadcastActor.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <sstream>
#include <vector>

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/LLVMContext.h"
#include "llvm/Instructions.h"
#include "llvm/Module.h"

#include "Jade/Decoder.h"
#include "Jade/Actor/BroadcastActor.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/Actor/ActionScheduler.h"
#include "Jade/Core/MoC/SDFMoC.h"
#include "Jade/Core/Network/Instance.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace std;
using namespace llvm;

BroadcastActor::BroadcastActor(llvm::LLVMContext& C, Decoder* decoder, string name, int numOutputs, IntegerType* type): Actor(name, module, "",
		  new map<string, Port*>(), new map<string, Port*>(), new map<string, StateVar*>(), new map<string, Variable*>(), new map<string, Procedure*>(), new list<Action*> (),
		  new list<Action*> (), NULL) , Context(C)
{
	this->type = cast<IntegerType>(type);
	this->numOutputs = numOutputs;
	this->fifo = fifo;
	this->decoder = decoder;
	this->actionScheduler = new ActionScheduler(new list<Action*>(), NULL);
	
	module = new Module(name, Context);
	this->decoder = decoder;
	
	//Create the broadcast actor
	createActor();
}

BroadcastActor::~BroadcastActor(){

}

void BroadcastActor::createActor(){
	const PointerType* fifoType = type->getPointerTo();

	// Creating input variable of name input
	string inputPortName = "input";
	GlobalVariable* inputGlobalVar = new GlobalVariable(*module, fifoType, false, GlobalValue::InternalLinkage, ConstantPointerNull::get(fifoType), name+"_"+inputPortName+"_ptr");
	
	//Create a new port
	Port* inputPort = new Port(inputPortName, type);
	Variable* inputVar = new Variable(type, inputPortName, true, true, inputGlobalVar);
	inputPort->setPtrVar(inputVar);
	
	
	inputs->insert(pair<string, Port*>(inputPort->getName(), inputPort));

	// Creating output port of name input
	for (int i = 0; i < numOutputs; i++) {
		stringstream outputPortName;
		
		// Creating output variable of name output
		outputPortName <<name<<"_output_" << i;
		GlobalVariable* outputGlobalVar = new GlobalVariable(*module, fifoType, false, GlobalValue::InternalLinkage, ConstantPointerNull::get(fifoType), outputPortName.str()+"_ptr");

		//Create a new port
		Port* outputPort = new Port(outputPortName.str(), type);
		Variable* outputVar = new Variable(type, outputPortName.str(), true, true, outputGlobalVar);
		
		outputPort->setPtrVar(outputVar);
		outputs->insert(pair<string, Port*>(outputPort->getName(), outputPort));
	}

	//Create action of the broadcast actor
	createAction();
	
	// Create a MoC
	moc = createMoC();

	//Create action scheduler
	actionScheduler = new ActionScheduler(actions, NULL);
}

MoC* BroadcastActor::createMoC(){
	// Set broadcast actor as SDF
	SDFMoC* sdfMoC = new SDFMoC();

	//Set properties of the MoC
	Action* action = actions->front();
	sdfMoC->addAction(action);
	sdfMoC->setInputPattern(action->getInputPattern());
	sdfMoC->setOutputPattern(action->getOutputPattern());

	return sdfMoC;
}

void BroadcastActor::createAction(){
	//Set properties of the action
	ActionTag* actionTag = new ActionTag();
	Procedure* scheduler = createScheduler();
	Procedure* body = createBody();
	Pattern* inputPattern = createPattern(inputs);
	Pattern* outputPattern = createPattern(outputs);
	
	//Add action to the actor
	Action* action = new Action(actionTag, inputPattern, outputPattern, scheduler, body, this);

	actions->push_back(action);
}

Procedure* BroadcastActor::createScheduler(){
	//Name of the scheduler
	string isSchedulableName = "isSchedulable_" + name;
	
	// Creating scheduler function
	FunctionType *FTy = FunctionType::get(Type::getInt1Ty(Context),false);
	Function *NewF = Function::Create(FTy, Function::InternalLinkage , isSchedulableName, module);

	// Add the first basic block entry into the function.
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", NewF);

	//Return resut
	ConstantInt* one = ConstantInt::get(Type::getInt1Ty(Context), 1);
	ReturnInst* returnInst = ReturnInst::Create(Context, one, BBEntry);

	return new Procedure(isSchedulableName, ConstantInt::get(Type::getInt1Ty(Context), 0), NewF);
}

Procedure* BroadcastActor::createBody(){
	// Creating scheduler function
	FunctionType *FTy = FunctionType::get(Type::getVoidTy(Context), false);
	Function *NewF = Function::Create(FTy, Function::InternalLinkage , name, module);

	// Add the first basic block entry into the function.
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", NewF);

	//Read fifo from output
	Value* token = createReadFifo(getInput(), BBEntry);

	//Write token to output
	map<string, Port*>::iterator it;
	
	for (it = outputs->begin(); it != outputs->end(); it++){
		createWriteFifo(it->second, token , BBEntry);
	}

	//Return value
	ReturnInst::Create(Context, NULL, BBEntry);

	return new Procedure(name, ConstantInt::get(Type::getInt1Ty(Context), 0), NewF);;
}

Pattern* BroadcastActor::createPattern(map<string, Port*>* ports){
	map<string, Port*>::iterator it;
	Pattern* pattern = new Pattern();

	//Set pattern to one token to test on port
	for (it = ports->begin(); it != ports->end(); it++){
		Port* port = it->second;
		ConstantInt* one = ConstantInt::get(Type::getInt32Ty(Context), 1);

		pattern->setNumTokens(port, one);
		pattern->setVariable(port, port->getPtrVar());
	}

	return pattern;
}

Value* BroadcastActor::createHasTokenTest(Port* port, BasicBlock* current){
	//Load port structure
	LoadInst* inputStruct = new LoadInst(port->getPtrVar()->getGlobalVariable(), "l"+ port->getName(), current);
	
	// Call hasToken
	vector<Value*> vector;
	vector.push_back(inputStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	Function* hasTokenFn = FifoMng::getHasTokenFunction(port->getType(), decoder);
	CallInst* retVal = CallInst::Create(hasTokenFn, vector.begin(), vector.end(), "c"+port->getName(), current);
	TruncInst* truncInst = new TruncInst(retVal, Type::getInt1Ty(Context), "t"+port->getName(), current);

	//Return the result
	return truncInst;

}

Value* BroadcastActor::createReadFifo(Port* port, BasicBlock* current){
	GlobalVariable* var = port->getPtrVar()->getGlobalVariable();
	
	//Create first bitcast
	Type* arrayType = ArrayType::get(port->getType(), 1);
	LoadInst* loadInst = new LoadInst(var,"", current);
	BitCastInst* bitcastInst = new BitCastInst(loadInst, arrayType->getPointerTo(), "", current);

	//Get first element
	Value *Idxs[2];
	Value *Zero = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Idxs[0] = Zero;
    Idxs[1] = Zero;
	
	GetElementPtrInst* getInstr = GetElementPtrInst::Create(bitcastInst, Idxs, Idxs+2, "", current);
	
	//Return token value
	return new LoadInst(getInstr,"token", current);
}

void BroadcastActor::createWriteFifo(Port* port, Value* token ,BasicBlock* current){
	GlobalVariable* var = port->getPtrVar()->getGlobalVariable();
	
	//Create first bitcast
	Type* arrayType = ArrayType::get(port->getType(), 1);
	LoadInst* loadInst = new LoadInst(var,"", current);
	BitCastInst* bitcastInst = new BitCastInst(loadInst, arrayType->getPointerTo(), "", current);

	//Get first element
	Value *Idxs[2];
	Value *Zero = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Idxs[0] = Zero;
    Idxs[1] = Zero;
	
	GetElementPtrInst* getInstr = GetElementPtrInst::Create(bitcastInst, Idxs, Idxs+2, "", current);

	//Return token value
	new StoreInst(token,getInstr, current);
}

void BroadcastActor::createSetReadEnd(Port* port, BasicBlock* current){
	//Load port structure
	LoadInst* inputStruct = new LoadInst(port->getPtrVar()->getGlobalVariable(), "l"+ port->getName(), current);

	// Call readPtr
	vector<Value*> vector;
	vector.push_back(inputStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	Function* readEndFn = FifoMng::getReadEndFunction(port->getType(), decoder);
	CallInst* retVal = CallInst::Create(readEndFn, vector.begin(), vector.end(), "", current);
}
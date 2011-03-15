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
	// Getting type of fifo
	StructType* structType = FifoMng::getFifoType(cast<IntegerType>(type));
	PointerType* fifoType = (PointerType*)structType->getPointerTo();
	Constant* portValue = ConstantPointerNull::get(cast<PointerType>(fifoType));

	// Creating input port of name input
	string inputName = "input";
	GlobalVariable* inputVar = new GlobalVariable(*module, fifoType, true, GlobalValue::ExternalLinkage, portValue, name+"_"+inputName);
	Port* inputPort = new Port(inputName, type, inputVar);
	inputs->insert(pair<string, Port*>(inputPort->getName(), inputPort));

	// Creating output port of name input
	for (int i = 0; i < numOutputs; i++) {
		stringstream outputName;
		outputName <<name<<"_output_" << i;
		GlobalVariable* outputVar = new GlobalVariable(*module, fifoType, true, GlobalValue::ExternalLinkage, portValue, outputName.str());
		Port* outputPort = new Port(outputName.str(), type, outputVar);
		outputs->insert(pair<string, Port*>(outputPort->getName(), outputPort));
	}

	//Create action of the broadcast actor
	createAction();
	
	//Create action scheduler
	actionScheduler = new ActionScheduler(actions, NULL);
}

void BroadcastActor::createAction(){
	//Set properties of the action
	ActionTag* actionTag = new ActionTag();
	Procedure* scheduler = createScheduler();
	Procedure* body = createBody();
	Pattern* inputPattern = createPattern(inputs);
	Pattern* outputPattern = createPattern(outputs);
	
	//Add action to the actor
	Action* action = new Action(actionTag, inputPattern, outputPattern, scheduler, body);
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

	//Create a test for input port
	Value* hasTokenValue = createHasTokenTest(getInput(), BBEntry);

	//Return resut
	ReturnInst* returnInst = ReturnInst::Create(Context, hasTokenValue, BBEntry);

	return new Procedure(isSchedulableName, ConstantInt::get(Type::getInt1Ty(Context), 0), NewF);
}

Procedure* BroadcastActor::createBody(){
	// Creating scheduler function
	FunctionType *FTy = FunctionType::get(Type::getInt32Ty(Context),false);
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

	//Create setReadEnd on input
	createSetReadEnd(getInput(), BBEntry);

	//Return value
	ConstantInt* one = ConstantInt::get(Type::getInt32Ty(Context), 1);
	ReturnInst::Create(Context, one, BBEntry);

	return new Procedure(name, ConstantInt::get(Type::getInt1Ty(Context), 0), NewF);;
}

Pattern* BroadcastActor::createPattern(map<string, Port*>* ports){
	/*map<string, Port*>::iterator it;
	map<Port*, ConstantInt*>* pattern = new map<Port*, ConstantInt*>();

	//Set pattern to one token to test on port
	for (it = ports->begin(); it != ports->end(); it++){
		ConstantInt* one = ConstantInt::get(Type::getInt32Ty(Context), 1);
		pattern->insert(pair<Port*, ConstantInt*>(it->second, one));
	}

	return pattern;*/
	return NULL;
}

Value* BroadcastActor::createHasTokenTest(Port* port, BasicBlock* current){
	//Load port structure
	LoadInst* inputStruct = new LoadInst(port->getGlobalVariable(), "l"+ port->getName(), current);
	
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
	//Load port structure
	LoadInst* inputStruct = new LoadInst(port->getGlobalVariable(), "l"+ port->getName(), current);
	
	// Call readPtr
	vector<Value*> vector;
	vector.push_back(inputStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));
	
	Function* readFn = FifoMng::getReadFunction(port->getType(), decoder);
	CallInst* retVal = CallInst::Create(readFn, vector.begin(), vector.end(), "tokenPtr", current);
	
	//Return token value
	return new LoadInst(retVal,"token", current);
}

void BroadcastActor::createWriteFifo(Port* port, Value* token ,BasicBlock* current){

	//Load port structure
	LoadInst* portStruct = new LoadInst(port->getGlobalVariable(), "l"+ port->getName(), current);

	// Call readPtr
	vector<Value*> vector;
	vector.push_back(portStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));
	Function* writeFn = FifoMng::getWriteFunction(port->getType(), decoder);
	CallInst* retVal = CallInst::Create(writeFn, vector.begin(), vector.end(), "w"+port->getName(), current);
	
	//Store token value
	StoreInst* storeInst = new StoreInst (token, retVal, current);

	// Call setWriteEnd
	Function* writeEndFn = FifoMng::getWriteEndFunction(port->getType(), decoder);
	CallInst::Create(writeEndFn, vector.begin(), vector.end(), "", current);

}

void BroadcastActor::createSetReadEnd(Port* port, BasicBlock* current){
	//Load port structure
	LoadInst* inputStruct = new LoadInst(port->getGlobalVariable(), "l"+ port->getName(), current);

	// Call readPtr
	vector<Value*> vector;
	vector.push_back(inputStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	Function* readEndFn = FifoMng::getReadEndFunction(port->getType(), decoder);
	CallInst* retVal = CallInst::Create(readEndFn, vector.begin(), vector.end(), "", current);
}
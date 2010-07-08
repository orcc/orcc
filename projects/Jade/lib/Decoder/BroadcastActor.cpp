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
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <sstream>
#include <vector>

#include "BroadcastActor.h"

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/LLVMContext.h"
#include "llvm/Instructions.h"
#include "llvm/Module.h"

#include "Jade/Actor/ActionScheduler.h"
#include "Jade/Actor/Port.h"
#include "Jade/Actor/Location.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Decoder/InstancedActor.h"
#include "Jade/Fifo/AbstractFifo.h"
//------------------------------

using namespace std;
using namespace llvm;

BroadcastActor::BroadcastActor(llvm::LLVMContext& C, Decoder* decoder, string name, int numOutputs, Type* type, AbstractFifo* fifo): Actor(name, "", fifo->getFifoTypes(), 
		  new map<string, Port*>(), new map<string, Port*>(), new map<string, Variable*>(), new map<string, Variable*>(), new map<string, Procedure*>(), new list<Action*> (),
		  new list<Action*> (), NULL) , Context(C)
{
	this->type = type;
	this->numOutputs = numOutputs;
	this->fifo = fifo;
	this->decoder = decoder;

	module = decoder->getModule();

	// Getting type of fifo
	map<string, Type*>::iterator it;
	map<string, Type*>* fifoTypes = fifo->getFifoTypes();
	it = fifoTypes->find("struct.fifo_s");
	PointerType* fifoType = (PointerType*)it->second->getPointerTo();
	Constant* portValue = ConstantPointerNull::get(cast<PointerType>(fifoType));

	Location* location = new Location();

	// Creating input port of name input
	string inputName = "input";
	GlobalVariable* inputVar = new GlobalVariable(*module, fifoType, true, GlobalValue::ExternalLinkage, portValue, name+"_"+inputName);
	Port* inputPort = new Port(location, inputName, type, inputVar);
	inputs->insert(pair<string, Port*>(inputName, inputPort));

	// Creating output port of name input
	for (int i = 0; i < numOutputs; i++) {
		stringstream outputName;
		outputName <<name<<"_output_" << i;
		GlobalVariable* outputVar = new GlobalVariable(*module, fifoType, true, GlobalValue::ExternalLinkage, portValue, outputName.str());
		Port* outputPort = new Port(location, outputName.str(), type, outputVar);
		outputs->insert(pair<string, Port*>(outputName.str(), outputPort));
	}

	this->actionScheduler = new ActionScheduler(createActionScheduler(), NULL, NULL);
}

InstancedActor* BroadcastActor::instanciate(Instance* instance){
	map<string, Port*>::iterator it;
	map<Port*, GlobalVariable*>* instancedInputs = new map<Port*, GlobalVariable*>();
	map<Port*, GlobalVariable*>* instancedOutputs = new map<Port*, GlobalVariable*>();
	
	for (it = inputs->begin(); it != inputs->end(); it++){
		instancedInputs->insert(pair<Port*, GlobalVariable*>((*it).second, (*it).second->getGlobalVariable()));
	}
	
	for (it = outputs->begin(); it != outputs->end(); it++){
		instancedOutputs->insert(pair<Port*, GlobalVariable*>((*it).second, (*it).second->getGlobalVariable()));
	}


	return new InstancedActor(decoder, instance, instancedInputs, instancedOutputs, new map<Variable*, GlobalVariable*>(), new map<Variable*, GlobalVariable*>(), new map<Procedure*, Function*>(), new list<Action*>(),  actionScheduler);

}


Function* BroadcastActor::createActionScheduler(){
	// Create an new broadcast module
	map<string, Port*>::iterator it;

	// Creating action scheduler 
	FunctionType *FTy = FunctionType::get(Type::getVoidTy(Context),false);
	Function *NewF = Function::Create(FTy, Function::InternalLinkage , name+"_scheduler", module);
	
	// Add the first basic block entry into the function.
	BasicBlock* BBEntry = BasicBlock::Create(Context, "entry", NewF);


	// Add a basic block to bb to the scheduler.
	BasicBlock* Bret = BasicBlock::Create(Context, "ret", NewF);
	

	//Create a test for input port
	Port* input = getInput();
	LoadInst* inputStruct = new LoadInst(input->getGlobalVariable(), "l"+input->getName(), BBEntry);
	BasicBlock* bbInput = createHasTokenTest(NewF, inputStruct, getInput(),BBEntry, Bret);

	//Create a test for output port
	BasicBlock* bbFifo = bbInput;
	map<string, Port*>* outputs = getOutputs();
	list<LoadInst*> outputsStruct;
	
	for (it = outputs->begin(); it != outputs->end(); it++){
		Port* port = (*it).second;
		LoadInst* outputStruct = new LoadInst(port->getGlobalVariable(), "l"+ port->getName(), bbFifo);
		outputsStruct.push_back(outputStruct);
		bbFifo = createHasRoomTest(NewF, outputStruct, port , bbFifo, Bret);

	}

	//Read fifo from output
	Value* token = createReadFifo(input, inputStruct, bbFifo);

	//Write token to output
	list<LoadInst*>::iterator itOut = outputsStruct.begin();
	for (it = outputs->begin(); it != outputs->end(); it++){
		createWriteFifo((*it).second, (*itOut), token , bbFifo);
		itOut++;
	}

	//Create setReadEnd on input
	createSetReadEnd(input, inputStruct, bbFifo);

	//Create setWriteEnd on outputs
	itOut = outputsStruct.begin();
	for (it = outputs->begin(); it != outputs->end(); it++){
		createSetWriteEnd((*it).second, (*itOut),  bbFifo);
		itOut++;
	}

	//Branch to return basic bloc
	BranchInst::Create( Bret, bbFifo);

	// Create the return instruction and add it to the basic block.
	ReturnInst::Create(Context, Bret);

	return NewF;
}


BasicBlock* BroadcastActor::createHasTokenTest(Function* func, LoadInst* fifoStruct, Port* port, BasicBlock* current, BasicBlock* ret){

	// Call hasToken
	vector<Value*> vector;
	vector.push_back(fifoStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	CallInst* retVal = CallInst::Create(fifo->getHasTokenFunction(), vector.begin(), vector.end(), "c"+port->getName(), current);
	TruncInst* truncInst = new TruncInst(retVal, Type::getInt1Ty(Context), "t"+port->getName(), current);

	// Add a basic block to bb to the scheduler.
	BasicBlock* newBB = BasicBlock::Create(Context, port->getName(), func);

	BranchInst::Create( newBB, ret, truncInst, current);

	return newBB;

}

BasicBlock* BroadcastActor::createHasRoomTest(Function* func, LoadInst* fifoStruct, Port* port, BasicBlock* current, BasicBlock* ret){

	// Call hasToken
	vector<Value*> vector;
	vector.push_back(fifoStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	CallInst* retVal = CallInst::Create(fifo->getHasRoomFunction(), vector.begin(), vector.end(), "c"+port->getName(), current);
	TruncInst* truncInst = new TruncInst(retVal, Type::getInt1Ty(Context), "t"+port->getName(), current);

	// Add a basic block to bb to the scheduler.
	BasicBlock* newBB = BasicBlock::Create(Context, port->getName(), func);

	BranchInst::Create( newBB, ret, truncInst, current);

	return newBB;

}


Value* BroadcastActor::createReadFifo(Port* port, LoadInst* fifoStruct, BasicBlock* current){
	// Call readPtr
	vector<Value*> vector;
	vector.push_back(fifoStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	CallInst* retVal = CallInst::Create(fifo->getReadFunction(), vector.begin(), vector.end(), "tokenPtri8", current);
	BitCastInst* bitCastInst = new BitCastInst(retVal, Type::getInt32PtrTy(Context), "tokenPtr",current);
	
	return new LoadInst(bitCastInst,"token", current);

}

void BroadcastActor::createWriteFifo(Port* port, LoadInst* fifoStruct, Value* token ,BasicBlock* current){

	// Call readPtr
	vector<Value*> vector;
	vector.push_back(fifoStruct);
	vector.push_back(ConstantInt::get(Type::getInt32Ty(Context), 1));

	CallInst* retVal = CallInst::Create(fifo->getWriteFunction(), vector.begin(), vector.end(), "w"+port->getName(), current);
	
	BitCastInst* bitCastInst = new BitCastInst(retVal, Type::getInt32PtrTy(Context), "wb"+port->getName(),current);
	
	new StoreInst (token, bitCastInst, current);

}

void BroadcastActor::createSetReadEnd(Port* port, LoadInst* fifoStruct,BasicBlock* current){

	// Call readPtr
	vector<Value*> vector;
	vector.push_back(fifoStruct);

	CallInst* retVal = CallInst::Create(fifo->getReadEndFunction(), vector.begin(), vector.end(), "", current);
}

void BroadcastActor::createSetWriteEnd(Port* port, LoadInst* fifoStruct,BasicBlock* current){

	// Call readPtr
	vector<Value*> vector;
	vector.push_back(fifoStruct);

	CallInst* retVal = CallInst::Create(fifo->getWriteEndFunction(), vector.begin(), vector.end(), "", current);
}
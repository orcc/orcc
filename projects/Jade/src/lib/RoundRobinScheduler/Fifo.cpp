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
@brief Implementation of class FifoOpt
@author Jerome Gorin
@file FifoOpt.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <sstream>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"

#include "Jade/Decoder.h"
#include "Jade/RoundRobinScheduler/FIFO.h"
//------------------------------

using namespace llvm;
using namespace std;

FifoOpt::FifoOpt(llvm::LLVMContext& C, llvm::Module* module, llvm::Type* type, int size){
	IntegerType* connectionType = cast<IntegerType>(type);

	//Get fifo structure
	StructType* structType = FifoOpt::getOrInsertFifoStruct(module, connectionType);

	// Initialize array content
	ArrayType* arrayType = ArrayType::get(connectionType, size);
	gv_array = new GlobalVariable(*module, arrayType, false, GlobalValue::InternalLinkage, ConstantAggregateZero::get(arrayType), "array");
	gv_array->setAlignment(16);


	// Initialize read_ind
	ArrayType* read_indTy = ArrayType::get(IntegerType::get(module->getContext(), 32), 1);
	gv_read_inds = new GlobalVariable(*module, read_indTy, false, GlobalValue::InternalLinkage, ConstantAggregateZero::get(read_indTy), "read_inds");
	gv_read_inds->setAlignment(4);

	//Usefull values
	Constant *Zero = ConstantInt::get(Type::getInt32Ty(C), 0);
	Constant *One = ConstantInt::get(Type::getInt32Ty(C), 1);
	vector<Constant*> indices;
	indices.push_back(Zero);
	indices.push_back(Zero);
	
	// Initialize fifo elements
	std::vector<Constant*> elts;
	elts.push_back(ConstantInt::get(Type::getInt32Ty(C), size)); // size of the ringbuffer
	elts.push_back(ConstantExpr::getGetElementPtr(gv_array, indices)); // the memory containing the ringbuffer
	elts.push_back(One); // the number of fifo's readers
	elts.push_back(ConstantExpr::getGetElementPtr(gv_read_inds, indices)); // the current position of the reader
	elts.push_back(Zero); //the current position of the writer
	Constant* fifoStruct = ConstantStruct::get(structType, elts);

	// Create FIFO
	fifoGV = new GlobalVariable(*module, structType, false, GlobalValue::InternalLinkage, fifoStruct, "fifo");
	fifoGV->setAlignment(8);
}


void FifoOpt::createReadWritePeek(Action* action){
	
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

void FifoOpt::createWrites (Procedure* procedure, Pattern* pattern){
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


void FifoOpt::createReads (Procedure* procedure, Pattern* pattern){
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


void FifoOpt::createPeeks (Procedure* procedure, Pattern* pattern){
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

Value* FifoOpt::replaceAccess (Port* port, Procedure* proc){
	Function* function = proc->getFunction();
	ConstantInt* sizeVal = ConstantInt::get(function->getContext(), APInt(32, port->getSize()));
	ConstantInt* zero = ConstantInt::get(function->getContext(), APInt(32, 0));
	

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
											  OpC = new ZExtInst(OpC, Type::getInt32Ty(function->getContext()), "", GEPInst);
										  }else if (OpC->getType()->getScalarSizeInBits() > 32){
											  OpC = new TruncInst(OpC, Type::getInt32Ty(function->getContext()), "", GEPInst);
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

Value* FifoOpt::createInputTest(Port* port, ConstantInt* numTokens, BasicBlock* BB){
	LoadInst* indexVal = new LoadInst(port->getIndex(), "", false, BB);
	BinaryOperator* addVal = BinaryOperator::Create(Instruction::Add, indexVal, numTokens, "", BB);
	LoadInst* tokenVal = new LoadInst(port->getRoomToken(), "", false, BB);
	return new ICmpInst(*BB, ICmpInst::ICMP_ULE, addVal, tokenVal, "");
}

Value* FifoOpt::createOutputTest(Port* port, ConstantInt* numTokens, BasicBlock* BB){
	// Usefull constants
	Constant* fifoSizeCst = ConstantInt::get(Type::getInt32Ty(BB->getContext()), port->getSize());
	ConstantInt* zero = ConstantInt::get(BB->getContext(), APInt(32, 0));
	ConstantInt* three = ConstantInt::get(BB->getContext(), APInt(32, 3));

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

Function* FifoOpt::initializeIn(llvm::Module* module, Port* port){
	//Usefull constant
	ConstantInt* zero = ConstantInt::get(module->getContext(), APInt(32, 0));
	ConstantInt* one = ConstantInt::get(module->getContext(), APInt(32, 1));
	ConstantInt* three = ConstantInt::get(module->getContext(), APInt(32, 3));

  	// Create Fifo access elements
	GlobalVariable* index = new GlobalVariable(*module, IntegerType::get(module->getContext(), 32), false, GlobalValue::InternalLinkage, zero, "index");
	GlobalVariable* numFreeVar = new GlobalVariable(*module, IntegerType::get(module->getContext(), 32), false, GlobalValue::InternalLinkage, zero, "numFreeVar");
	GlobalVariable* id = new GlobalVariable(*module, IntegerType::get(module->getContext(), 32), false, GlobalValue::InternalLinkage, zero, "id");
	
	index->setAlignment(4);
	numFreeVar->setAlignment(4);
	id->setAlignment(4);
	
	port->setIndex(index);
	port->setRoomToken(numFreeVar);
	port->setId(id);

	//Create read function
	string funcName("read_");
	funcName.append(port->getName());
	FunctionType* FuncTy_void = FunctionType::get(Type::getVoidTy(module->getContext()),false);
	Function* readFn_port = Function::Create(FuncTy_void, GlobalValue::InternalLinkage, funcName, module);

	//Body of the function
	BasicBlock* bb = BasicBlock::Create(module->getContext(), "",readFn_port,0);

	// Load fifo and id
	 LoadInst* fifoVarPtr = new LoadInst(port->getFifoVar(), "", false, bb);
	 LoadInst* fifoId = new LoadInst(port->getId(), "", false, bb);

	 // Get read ind
	 std::vector<Value*> read_indices;
     read_indices.push_back(zero);
     read_indices.push_back(three);
     Instruction* read_indicesPtr = GetElementPtrInst::Create(fifoVarPtr, read_indices, "", bb);

	 // Get read ind id
	 LoadInst* read_indicePtr = new LoadInst(read_indicesPtr, "", false, bb);
	 std::vector<Value*> read_indice;
     read_indice.push_back(fifoId);
	 GetElementPtrInst* read_indiceValPtr = GetElementPtrInst::Create(read_indicePtr, read_indice, "", bb);
	 LoadInst* read_indiceVal = new LoadInst(read_indiceValPtr, "", false, bb);
	 new StoreInst(read_indiceVal, index, false, bb);

	 // Call numToken function
	  Function* getNumTokensFn = getOrInsertNumTokensFn(module, port->getType());
	  std::vector<Value*> num_tokens_params;
	  num_tokens_params.push_back(fifoVarPtr);
      num_tokens_params.push_back(fifoId);
      CallInst* numTokenRes = CallInst::Create(getNumTokensFn, num_tokens_params, "", bb);

      BinaryOperator* binRes = BinaryOperator::Create(Instruction::Add, read_indiceVal, numTokenRes, "", bb);
      new StoreInst(binRes, numFreeVar, false, bb);
      

	  // Load content in FIFO ptr
	  std::vector<Value*> content_indices;
      content_indices.push_back(zero);
      content_indices.push_back(one);
      Instruction* contentsPtr = GetElementPtrInst::Create(fifoVarPtr, content_indices, "", bb);
	  LoadInst* contentsAdr = new LoadInst(contentsPtr, "", false, bb);
	  new StoreInst(contentsAdr, port->getPtrVar()->getGlobalVariable(), bb);

	  // Return from function
	  ReturnInst::Create(module->getContext(), bb);

	return readFn_port;
}


Function* FifoOpt::initializeOut(llvm::Module* module, Port* port){
	
	//Usefull constant
	ConstantInt* zero = ConstantInt::get(module->getContext(), APInt(32, 0));
	ConstantInt* one = ConstantInt::get(module->getContext(), APInt(32, 1));
	ConstantInt* four = ConstantInt::get(module->getContext(), APInt(32, 4));

	// Create Fifo access elements
	GlobalVariable* index = new GlobalVariable(*module, IntegerType::get(module->getContext(), 32), false, GlobalValue::InternalLinkage, zero, "index");
	index->setAlignment(4);
	GlobalVariable* numFreeVar = new GlobalVariable(*module, IntegerType::get(module->getContext(), 32), false, GlobalValue::InternalLinkage, zero, "numFreeVar");
	numFreeVar->setAlignment(4);
	port->setIndex(index);
	port->setRoomToken(numFreeVar);

 
	
	//Create write function
	string funcName("write_");
	funcName.append(port->getName());
	FunctionType* FuncTy_void = FunctionType::get(Type::getVoidTy(module->getContext()),false);
	Function* writeFn_port = Function::Create(FuncTy_void, GlobalValue::InternalLinkage, funcName, module);

	//Body of the function
	BasicBlock* bb = BasicBlock::Create(module->getContext(), "",writeFn_port,0);
  
	  // Load fifo
	  LoadInst* ptr_74 = new LoadInst(port->getFifoVar(), "", false, bb);
	  
	  // Get write ind
	  std::vector<Value*> write_ind;
	  write_ind.push_back(zero);
	  write_ind.push_back(four);
	  Instruction* writeElts = GetElementPtrInst::Create(ptr_74, write_ind, "", bb);
	  LoadInst* writeElt = new LoadInst(writeElts, "", false, bb);
	  new StoreInst(writeElt, index, false, bb);
	  
	  // Call getRoom function
	  Function* getRoomFn = getOrInsertRoomFn(module, port->getType());
	  LoadInst* fifoVar = new LoadInst(port->getFifoVar(), "", false, bb);
	  std::vector<Value*> room_params;
	  room_params.push_back(fifoVar);
	  room_params.push_back(one);
	  CallInst* roomValue = CallInst::Create(getRoomFn, room_params, "", bb);
	  roomValue->setTailCall(false);
  
	  // Add results
	  BinaryOperator* addOp = BinaryOperator::Create(Instruction::Add, writeElt, writeElt, "", bb);
	  new StoreInst(addOp, numFreeVar, false, bb);

	  // Load content in FIFO ptr
	  std::vector<Value*> content_indices;
      content_indices.push_back(zero);
      content_indices.push_back(one);
      Instruction* contentsPtr = GetElementPtrInst::Create(ptr_74, content_indices, "", bb);
	  LoadInst* contentsAdr = new LoadInst(contentsPtr, "", false, bb);
	  new StoreInst(contentsAdr, port->getPtrVar()->getGlobalVariable(), bb);

	  ReturnInst::Create(module->getContext(), bb);

	return writeFn_port;
}

Function* FifoOpt::closeIn(llvm::Module* module, Port* port){
	//Usefull constant
	ConstantInt* zero = ConstantInt::get(module->getContext(), APInt(32, 0));
	ConstantInt* one = ConstantInt::get(module->getContext(), APInt(32, 1));
	ConstantInt* three = ConstantInt::get(module->getContext(), APInt(32, 3));
	
	//Create read end function
	string funcName("read_end_");
	funcName.append(port->getName());
	FunctionType* FuncTy_void = FunctionType::get(Type::getVoidTy(module->getContext()),false);
	Function* readEndFn_port = Function::Create(FuncTy_void, GlobalValue::InternalLinkage, funcName, module);
	
	//Body of the function
	BasicBlock* bb = BasicBlock::Create(module->getContext(), "",readEndFn_port,0);
  
  // Load read inds
	LoadInst* indexPtr = new LoadInst(port->getIndex(), "", false, bb);
	LoadInst* idPtr = new LoadInst(port->getId(), "", false, bb);
	LoadInst* FifoVarPtr = new LoadInst(port->getFifoVar(), "", false, bb);
  std::vector<Value*> read_inds_indices;
  read_inds_indices.push_back(zero);
  read_inds_indices.push_back(three);
  Instruction* ptr_42 = GetElementPtrInst::Create(FifoVarPtr, read_inds_indices, "", bb);
  LoadInst* ptr_43 = new LoadInst(ptr_42, "", false, bb);
  GetElementPtrInst* ptr_44 = GetElementPtrInst::Create(ptr_43, idPtr, "", bb);
   new StoreInst(indexPtr, ptr_44, false, bb);
  ReturnInst::Create(module->getContext(), bb);
  
	return readEndFn_port;
}

Function* FifoOpt::closeOut(llvm::Module* module, Port* port){
	//Usefull constant
	ConstantInt* zero = ConstantInt::get(module->getContext(), APInt(32, 0));
	ConstantInt* four = ConstantInt::get(module->getContext(), APInt(32, 4));

	//Create read end function
	string funcName("write_end_");
	funcName.append(port->getName());
	FunctionType* FuncTy_void = FunctionType::get(Type::getVoidTy(module->getContext()),false);
	Function* writeEndFn_port = Function::Create(FuncTy_void, GlobalValue::InternalLinkage, funcName, module);

	//Body of the function
	 BasicBlock* bb = BasicBlock::Create(module->getContext(), "",writeEndFn_port,0);
  
  // Block  (label_17)
	 LoadInst* int32_18 = new LoadInst(port->getIndex(), "", false, bb);
	 LoadInst* ptr_19 = new LoadInst(port->getFifoVar(), "", false, bb);
  std::vector<Value*> ptr_20_indices;
  ptr_20_indices.push_back(zero);
  ptr_20_indices.push_back(four);
  Instruction* ptr_20 = GetElementPtrInst::Create(ptr_19, ptr_20_indices, "", bb);
   new StoreInst(int32_18, ptr_20, false, bb);
  ReturnInst::Create(module->getContext(), bb);

	return writeEndFn_port;
}


FifoOpt::~FifoOpt(){
	//Erase fifo elements
	fifoGV->eraseFromParent();
	gv_array->eraseFromParent();
	gv_read_inds->eraseFromParent();
}


StructType* FifoOpt::getOrInsertFifoStruct(Module* module, IntegerType* connectionType){	
	int size = connectionType->getBitWidth();

	// Set structure name
	stringstream name;
	name << "fifo_i" << size;


	Type* fifoType = module->getTypeByName(name.str());
	
	if (fifoType != NULL){
		return cast<StructType>(fifoType);
	}

	// Create a new fifo structure
	std::vector<llvm::Type*> StructFields;
	StructFields.push_back(IntegerType::get(module->getContext(), 32)); // size of the ringbuffer
	StructFields.push_back(PointerType::get(IntegerType::get(module->getContext(), size), 0)); // the memory containing the ringbuffer
	StructFields.push_back(IntegerType::get(module->getContext(), 32)); // the number of fifo's readers
	StructFields.push_back(PointerType::get(IntegerType::get(module->getContext(), 32), 0)); // the current position of the reader
	StructFields.push_back(IntegerType::get(module->getContext(), 32)); // the current position of the writer
	
	//Insert fifo structure in module
	return StructType::create(module->getContext(), StructFields, name.str(), false);
}


Function* FifoOpt::getOrInsertNumTokensFn(llvm::Module* module, llvm::IntegerType* connectionType){
	StructType* fifoStruct = getOrInsertFifoStruct(module, connectionType);

	// Set function name
	int size = connectionType->getBitWidth();
	stringstream name;
	name <<"get_num_Tokens_i"  << size;

	Function* numTokensFn = module->getFunction(name.str());
	
	if (numTokensFn != NULL){
		return numTokensFn;
	}

		//Usefull constant
	ConstantInt* zero = ConstantInt::get(module->getContext(), APInt(32, 0));
	ConstantInt* one = ConstantInt::get(module->getContext(), APInt(32, 1));
	ConstantInt* three = ConstantInt::get(module->getContext(), APInt(32, 3));
	ConstantInt* four = ConstantInt::get(module->getContext(), APInt(32, 4));

	 PointerType* PointerStructTy = PointerType::get(fifoStruct, 0);
	 std::vector<Type*>FuncTokenArg;
	FuncTokenArg.push_back(PointerStructTy);
	FuncTokenArg.push_back(IntegerType::get(module->getContext(), 32));
	FunctionType* FuncTokenTy = FunctionType::get(IntegerType::get(module->getContext(), 32), FuncTokenArg, false);

	Function* getNumTokensFn = Function::Create(FuncTokenTy, GlobalValue::ExternalLinkage, name.str(), module);

	Function::arg_iterator args = getNumTokensFn->arg_begin();
  Value* ptr_fifo = args++;
  ptr_fifo->setName("fifo");
  Value* int32_reader_id = args++;
  int32_reader_id->setName("reader_id");
  
  BasicBlock* label_15 = BasicBlock::Create(module->getContext(), "",getNumTokensFn,0);
  
  // Block  (label_15)
  AllocaInst* ptr_16 = new AllocaInst(PointerStructTy, "", label_15);
  ptr_16->setAlignment(8);
  AllocaInst* ptr_17 = new AllocaInst(IntegerType::get(module->getContext(), 32), "", label_15);
  ptr_17->setAlignment(4);
   new StoreInst(ptr_fifo, ptr_16, false, label_15);
   new StoreInst(int32_reader_id, ptr_17, false, label_15);
  LoadInst* ptr_20 = new LoadInst(ptr_16, "", false, label_15);
  std::vector<Value*> ptr_21_indices;
  ptr_21_indices.push_back(zero);
  ptr_21_indices.push_back(four);
  Instruction* ptr_21 = GetElementPtrInst::Create(ptr_20, ptr_21_indices, "", label_15);
  LoadInst* int32_22 = new LoadInst(ptr_21, "", false, label_15);
  LoadInst* int32_23 = new LoadInst(ptr_17, "", false, label_15);
  CastInst* int64_24 = new ZExtInst(int32_23, IntegerType::get(module->getContext(), 64), "", label_15);
  LoadInst* ptr_25 = new LoadInst(ptr_16, "", false, label_15);
  std::vector<Value*> ptr_26_indices;
  ptr_26_indices.push_back(zero);
  ptr_26_indices.push_back(three);
  Instruction* ptr_26 = GetElementPtrInst::Create(ptr_25, ptr_26_indices, "", label_15);
  LoadInst* ptr_27 = new LoadInst(ptr_26, "", false, label_15);
  GetElementPtrInst* ptr_28 = GetElementPtrInst::Create(ptr_27, int64_24, "", label_15);
  LoadInst* int32_29 = new LoadInst(ptr_28, "", false, label_15);
  BinaryOperator* int32_30 = BinaryOperator::Create(Instruction::Sub, int32_22, int32_29, "", label_15);
  ReturnInst::Create(module->getContext(), int32_30, label_15);

	return getNumTokensFn;

}


Function* FifoOpt::getOrInsertRoomFn(Module* module, IntegerType* connectionType){	
	StructType* fifoStruct = getOrInsertFifoStruct(module, connectionType);

	// Set function name
	int size = connectionType->getBitWidth();
	stringstream name;
	name <<"get_room_i"  << size;

	Function* roomFn = module->getFunction(name.str());
	
	if (roomFn != NULL){
		return roomFn;
	}


	PointerType* fifoStructPtrTy = PointerType::get(fifoStruct, 0);

	std::vector<Type*> FuncRoomTy;
	FuncRoomTy.push_back(fifoStructPtrTy);
	FuncRoomTy.push_back(IntegerType::get(module->getContext(), 32));
	FunctionType* RoomTys = FunctionType::get(IntegerType::get(module->getContext(), 32), FuncRoomTy, false);
	
	Function* get_room = Function::Create(RoomTys, GlobalValue::ExternalLinkage, name.str(), module); 
 
  Function::arg_iterator args = get_room->arg_begin();
  Value* ptr_fifo = args++;
  ptr_fifo->setName("fifo");
  Value* int32_num_readers = args++;
  int32_num_readers->setName("num_readers");
  
  BasicBlock* label_15 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_16 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_17 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_18 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_19 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_20 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_21 = BasicBlock::Create(module->getContext(), "",get_room,0);
  BasicBlock* label_22 = BasicBlock::Create(module->getContext(), "",get_room,0);
  ConstantInt* zero = ConstantInt::get(module->getContext(), APInt(32, 0));
  ConstantInt* one = ConstantInt::get(module->getContext(), APInt(32, 1));
  ConstantInt* three = ConstantInt::get(module->getContext(), APInt(32, 3));
  ConstantInt* four = ConstantInt::get(module->getContext(), APInt(32, 4));

  // Block  (label_15)
  AllocaInst* ptr_23 = new AllocaInst(fifoStructPtrTy, "", label_15);
  ptr_23->setAlignment(8);
  AllocaInst* ptr_24 = new AllocaInst(IntegerType::get(module->getContext(), 32), "", label_15);
  ptr_24->setAlignment(4);
  AllocaInst* ptr_i = new AllocaInst(IntegerType::get(module->getContext(), 32), "i", label_15);
  ptr_i->setAlignment(4);
  AllocaInst* ptr_max_num_tokens = new AllocaInst(IntegerType::get(module->getContext(), 32), "max_num_tokens", label_15);
  ptr_max_num_tokens->setAlignment(4);
  AllocaInst* ptr_num_tokens = new AllocaInst(IntegerType::get(module->getContext(), 32), "num_tokens", label_15);
  ptr_num_tokens->setAlignment(4);
   new StoreInst(ptr_fifo, ptr_23, false, label_15);
   new StoreInst(int32_num_readers, ptr_24, false, label_15);
   new StoreInst(zero, ptr_max_num_tokens, false, label_15);
   new StoreInst(zero, ptr_i, false, label_15);
  BranchInst::Create(label_16, label_15);
  
  // Block  (label_16)
  LoadInst* int32_30 = new LoadInst(ptr_i, "", false, label_16);
  LoadInst* int32_31 = new LoadInst(ptr_24, "", false, label_16);
  ICmpInst* int1_32 = new ICmpInst(*label_16, ICmpInst::ICMP_ULT, int32_30, int32_31, "");
  BranchInst::Create(label_17, label_22, int1_32, label_16);
  
  // Block  (label_17)
  LoadInst* ptr_34 = new LoadInst(ptr_23, "", false, label_17);
  std::vector<Value*> ptr_35_indices;
  ptr_35_indices.push_back(zero);
  ptr_35_indices.push_back(four);
  Instruction* ptr_35 = GetElementPtrInst::Create(ptr_34, ptr_35_indices, "", label_17);
  LoadInst* int32_36 = new LoadInst(ptr_35, "", false, label_17);
  LoadInst* int32_37 = new LoadInst(ptr_i, "", false, label_17);
  CastInst* int64_38 = new ZExtInst(int32_37, IntegerType::get(module->getContext(), 64), "", label_17);
  LoadInst* ptr_39 = new LoadInst(ptr_23, "", false, label_17);
  std::vector<Value*> ptr_40_indices;
  ptr_40_indices.push_back(zero);
  ptr_40_indices.push_back(three);
  Instruction* ptr_40 = GetElementPtrInst::Create(ptr_39, ptr_40_indices, "", label_17);
  LoadInst* ptr_41 = new LoadInst(ptr_40, "", false, label_17);
 GetElementPtrInst* ptr_42 = GetElementPtrInst::Create(ptr_41, int64_38, "", label_17);
  LoadInst* int32_43 = new LoadInst(ptr_42, "", false, label_17);
  BinaryOperator* int32_44 = BinaryOperator::Create(Instruction::Sub, int32_36, int32_43, "", label_17);
   new StoreInst(int32_44, ptr_num_tokens, false, label_17);
  LoadInst* int32_46 = new LoadInst(ptr_max_num_tokens, "", false, label_17);
  LoadInst* int32_47 = new LoadInst(ptr_num_tokens, "", false, label_17);
  ICmpInst* int1_48 = new ICmpInst(*label_17, ICmpInst::ICMP_UGT, int32_46, int32_47, "");
  BranchInst::Create(label_18, label_19, int1_48, label_17);
  
  // Block  (label_18)
  LoadInst* int32_50 = new LoadInst(ptr_max_num_tokens, "", false, label_18);
  BranchInst::Create(label_20, label_18);
  
  // Block  (label_19)
  LoadInst* int32_52 = new LoadInst(ptr_num_tokens, "", false, label_19);
  BranchInst::Create(label_20, label_19);
  
  // Block  (label_20)
  PHINode* int32_54 = PHINode::Create(IntegerType::get(module->getContext(), 32), 2, "", label_20);
  int32_54->addIncoming(int32_50, label_18);
  int32_54->addIncoming(int32_52, label_19);
  
   new StoreInst(int32_54, ptr_max_num_tokens, false, label_20);
  BranchInst::Create(label_21, label_20);
  
  // Block  (label_21)
  LoadInst* int32_57 = new LoadInst(ptr_i, "", false, label_21);
  BinaryOperator* int32_58 = BinaryOperator::Create(Instruction::Add, int32_57, one, "", label_21);
   new StoreInst(int32_58, ptr_i, false, label_21);
  BranchInst::Create(label_16, label_21);
  
  // Block  (label_22)
  LoadInst* ptr_61 = new LoadInst(ptr_23, "", false, label_22);
  std::vector<Value*> ptr_62_indices;
  ptr_62_indices.push_back(zero);
  ptr_62_indices.push_back(zero);
  Instruction* ptr_62 = GetElementPtrInst::Create(ptr_61, ptr_62_indices, "", label_22);
  LoadInst* int32_63 = new LoadInst(ptr_62, "", false, label_22);
  LoadInst* int32_64 = new LoadInst(ptr_max_num_tokens, "", false, label_22);
  BinaryOperator* int32_65 = BinaryOperator::Create(Instruction::Sub, int32_63, int32_64, "", label_22);
  ReturnInst::Create(module->getContext(), int32_65, label_22);

	return get_room;
}
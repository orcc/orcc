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
#include "Jade/Fifo/FifoOpt.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace llvm;
using namespace std;

FifoOpt::FifoOpt(llvm::LLVMContext& C, llvm::Module* module, llvm::Type* type, int size) : AbstractFifo(C, module, type, size){
			createConnection();
}


void FifoOpt::createConnection(){	
	IntegerType* connectionType = cast<IntegerType>(fifoType);

	//Get fifo structure
	const StructType* structType = getOrInserFifoStruct(connectionType);

	// Initialize array content
	PATypeHolder EltTy(connectionType);
	const ArrayType* arrayType = ArrayType::get(EltTy, fifoSize);
	GlobalVariable* gv_array = new GlobalVariable(*module, arrayType, false, GlobalValue::InternalLinkage, ConstantAggregateZero::get(arrayType), "array");
	gv_array->setAlignment(16);


	// Initialize read_ind
	ArrayType* read_indTy = ArrayType::get(IntegerType::get(module->getContext(), 32), 1);
	GlobalVariable* gv_read_inds = new GlobalVariable(*module, read_indTy, false, GlobalValue::InternalLinkage, ConstantAggregateZero::get(read_indTy), "read_inds");
	gv_read_inds->setAlignment(4);

	//Usefull values
	Constant *Zero = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant *One = ConstantInt::get(Type::getInt32Ty(Context), 1);
	vector<Constant*> indices;
	indices.push_back(Zero);
	indices.push_back(Zero);
	
	// Initialize fifo elements
	std::vector<Constant*> elts;
	elts.push_back(ConstantInt::get(Type::getInt32Ty(Context), fifoSize)); // size of the ringbuffer
	elts.push_back(ConstantExpr::getGetElementPtr(gv_array, &indices[0], indices.size())); // the memory containing the ringbuffer
	elts.push_back(One); // the number of fifo's readers
	elts.push_back(ConstantExpr::getGetElementPtr(gv_read_inds, &indices[0], indices.size())); // the current position of the reader
	elts.push_back(Zero); //the current position of the writer
	Constant* fifoStruct = ConstantStruct::get(structType, elts);

	// Create FIFO
	fifoGV = new GlobalVariable(*module, structType, false, GlobalValue::InternalLinkage, fifoStruct, "fifo");
	fifoGV->setAlignment(8);
}

const StructType* FifoOpt::getOrInserFifoStruct(IntegerType* connectionType){	
	int size = connectionType->getBitWidth();

	// Set structure name
	stringstream name;
	name << "fifo_i" << size;


	const Type* fifoType = module->getTypeByName(name.str());
	
	if (fifoType != NULL){
		return cast<StructType>(fifoType);
	}

	// Create a new fifo structure
	std::vector<const llvm::Type*> StructFields;
	StructFields.push_back(IntegerType::get(module->getContext(), 32)); // size of the ringbuffer
	StructFields.push_back(PointerType::get(IntegerType::get(module->getContext(), size), 0)); // the memory containing the ringbuffer
	StructFields.push_back(IntegerType::get(module->getContext(), 32)); // the number of fifo's readers
	StructFields.push_back(PointerType::get(IntegerType::get(module->getContext(), 32), 0)); // the current position of the reader
	StructFields.push_back(IntegerType::get(module->getContext(), 32)); // the current position of the writer
	
	//Insert fifo structure in module
	fifoType = StructType::get(module->getContext(), StructFields, false);
	module->addTypeName(name.str(), fifoType);

	return cast<StructType>(fifoType);
}

FifoOpt::~FifoOpt(){
	//Erase fifo elements
	fifoGV->eraseFromParent();
	ArrayContent->eraseFromParent();
	ArrayFifoBuffer->eraseFromParent();
}

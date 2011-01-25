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
@brief Implementation of class CircularConnector
@author Jerome Gorin
@file CircularConnector.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"

#include "Jade/Decoder.h"
#include "Jade/Fifo/FifoCircular.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace llvm;
using namespace std;

void FifoCircular::createConnection(){	
	IntegerType* connectionType = cast<IntegerType>(fifoType);

	//Get fifo structure
	StructType* structType = FifoMng::getFifoType(connectionType);

	//Get fifo array structure
	PATypeHolder EltTy(connectionType);
	const ArrayType* arrayType = ArrayType::get(EltTy, fifoSize);

	// Initialize array for content
	Constant* arrayCt = ConstantArray::get(arrayType, NULL,0);
	ArrayContent = new GlobalVariable(*module, arrayType, false, GlobalVariable::InternalLinkage, arrayCt, "content");
	ArrayContent->setAlignment(32);
	
	// Initialize array for fifo buffer
	Constant* arrayBuffer = ConstantArray::get(arrayType, NULL,0);
	ArrayFifoBuffer = new GlobalVariable(*module, arrayType, false, GlobalVariable::InternalLinkage, arrayBuffer, "buffer");
	ArrayFifoBuffer->setAlignment(32);

	// Initialize fifo elements
	Constant* size = ConstantInt::get(Type::getInt32Ty(Context), fifoSize);
	Constant* read_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* write_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* fill_count = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* contents = ConstantExpr::getBitCast(ArrayContent, structType->getElementType(1));
	Constant* fifo_buffer = ConstantExpr::getBitCast(ArrayFifoBuffer, structType->getElementType(2));
	
	// Add initialization vector 
	vector<Constant*> Elts;
	Elts.push_back(size);
	Elts.push_back(contents);
	Elts.push_back(fifo_buffer);
	Elts.push_back(read_ind);
	Elts.push_back(write_ind);
	Elts.push_back(fill_count);
	Constant* fifoStruct =  ConstantStruct::get(structType, Elts);

	// Create fifo 
	fifoGV = new GlobalVariable(*module, structType, false, GlobalVariable::InternalLinkage, fifoStruct, "fifo");
	fifoGV->setAlignment(32);
}

FifoCircular::~FifoCircular(){
	//Erase fifo elements
	fifoGV->eraseFromParent();
	ArrayContent->eraseFromParent();
	ArrayFifoBuffer->eraseFromParent();
}
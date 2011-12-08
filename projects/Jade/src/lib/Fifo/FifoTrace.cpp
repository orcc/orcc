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
@brief Implementation of class TraceConnector
@author Jerome Gorin
@file TraceConnector.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <sstream>
#include <string>
#include <iostream>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"
#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Instructions.h"

#include "Jade/Decoder.h"
#include "Jade/Fifo/FifoTrace.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace llvm;
using namespace std;

void FifoTrace::createConnection(){
	IntegerType* connectionType = cast<IntegerType>(fifoType);

	//Get fifo structure
	StructType* structType = FifoMng::getFifoType(connectionType);

	//Get fifo array structure
	ArrayType* arrayType = ArrayType::get(structType, fifoSize);

	// Initialize array for content
	GlobalVariable * ArrayContent = new GlobalVariable(*module, arrayType, false, GlobalVariable::InternalLinkage, ConstantAggregateZero::get(arrayType), "content");
	ArrayContent->setAlignment(32);
	
	// Initialize array for fifo buffer
	GlobalVariable * ArrayFifoBuffer = new GlobalVariable(*module, arrayType, false, GlobalVariable::InternalLinkage, ConstantAggregateZero::get(arrayType), "buffer");
	ArrayFifoBuffer->setAlignment(32);
	
	//Creating file string
	ostringstream fileName;
	ostringstream fileVar;
	string strFile;
	string strVar;
	Vertex* srcVer = (Vertex*)connection->getSource();
	Instance* instance = srcVer->getInstance();
	Port* port = connection->getSourcePort();
	fileName << outputDir << instance->getId() <<"_" <<port->getName() << ".txt";
	fileVar << instance->getId() << "_" << port->getName() << "_file";
	strFile = fileName.str();
	strVar = fileVar.str();
	
	//Insert trace file string
	ArrayType *Ty = ArrayType::get(Type::getInt8Ty(Context), strFile.size()+1); 
	GlobalVariable *GV = new GlobalVariable(*module, Ty, true, GlobalVariable::InternalLinkage , ConstantArray::get(Context, strFile), strVar.c_str(), 0, false, 0);

	// Initialize fifo elements
	Constant* size = ConstantInt::get(Type::getInt32Ty(Context), connection->getSize());
	Constant* read_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* write_ind = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* fill_count = ConstantInt::get(Type::getInt32Ty(Context), 0);
	Constant* contents = ConstantExpr::getBitCast(ArrayContent, structType->getElementType(1));
	Constant* fifo_buffer = ConstantExpr::getBitCast(ArrayFifoBuffer, structType->getElementType(2));
	Constant* file =  ConstantExpr::getBitCast(GV, structType->getElementType(3));
	
	// Add initialization vector 
	vector<Constant*> Elts;
	Elts.push_back(size);
	Elts.push_back(contents);
	Elts.push_back(fifo_buffer);
	Elts.push_back(file);
	Elts.push_back(read_ind);
	Elts.push_back(write_ind);
	Elts.push_back(fill_count);
	Constant* fifoStruct =  ConstantStruct::get(structType, Elts);

	// Create fifo 
	fifoGV =
        new GlobalVariable(*module, structType,
		false, GlobalVariable::InternalLinkage, fifoStruct,"fifo");
	fifoGV->setAlignment(32);	
}
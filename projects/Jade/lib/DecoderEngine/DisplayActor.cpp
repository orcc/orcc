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
@brief Implementation of class DisplayActor
@author Jerome Gorin
@file DisplayActor.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "DisplayActor.h"

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/LLVMContext.h"
#include "llvm/Module.h"

#include "Jade/Actor/ActionScheduler.h"
#include "Jade/Actor/Port.h"
#include "Jade/Actor/Location.h"
#include "Jade/Fifo/AbstractFifo.h"
//------------------------------


using namespace std;
using namespace llvm;


DisplayActor::DisplayActor(llvm::LLVMContext& C, AbstractFifo* fifo): Actor("display", "", new map<string, Type*>(), new std::map<std::string, Port*>(), 
		  new std::map<std::string, Port*>(), new map<string, Variable*>(), new map<string, Variable*>(), 
		  new map<string, Procedure*>(), new std::list<Action*> (), new std::list<Action*> (), NULL) , Context(C)
{
	
	module = new Module("display", Context);

	//Add fifo type
	OpaqueType* fifoStruct = OpaqueType::get(C);
	fifoTypes->insert(pair<string, Type*>("struct.fifo_s", fifoStruct));

	// Getting type of fifo
	PointerType* fifoType = (PointerType*)fifoStruct->getPointerTo();
	Constant* portValue = ConstantPointerNull::get(cast<PointerType>(fifoType));
	
	
	// Creating port B of size 16
	string portNameB("B");
	Type* portTypeB = (Type*)IntegerType::get(Context, 8);
	GlobalVariable* varB = new GlobalVariable(fifoType, true, GlobalValue::ExternalLinkage, portValue, "B");
	Port* portB = new Port(new Location(), portNameB, portTypeB, varB);

	// Creating port WIDTH of size 16 
	string portNameW("WIDTH");
	Type* portTypeW = (Type*)IntegerType::get(Context, 16);
	GlobalVariable* varW = new GlobalVariable(fifoType, true, GlobalValue::ExternalLinkage, portValue, "WIDTH");
	Port* portW = new Port(new Location(), portNameW, portTypeW, varW);


	// Creating port HEIGHT of size 16 
	string portNameH("HEIGHT");
	Type* portTypeH = (Type*)IntegerType::get(Context, 16);
	GlobalVariable* varH = new GlobalVariable(fifoType, true, GlobalValue::ExternalLinkage, portValue, "HEIGHT");
	Port* portH = new Port(new Location(), portNameH, portTypeH, varH);
	
	// Creating action scheduler 
	FunctionType *FTy = FunctionType::get(Type::getVoidTy(Context),false);
	Function *NewF = Function::Create(FTy, Function::InternalLinkage , "scheduler", module);
	this->actionScheduler = new ActionScheduler(NewF, NULL, NULL);

	// Inserting ports in inputs list 
	inputs->insert(pair<string, Port*>(portNameB, portB));
	inputs->insert(pair<string, Port*>(portNameW, portW));
	inputs->insert(pair<string, Port*>(portNameH, portH));
}
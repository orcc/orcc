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
@brief Implementation of class FifoCircular
@author Jerome Gorin
@file FifoCircular.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include "llvm/Module.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Support/CommandLine.h"

#include "Jade/JIT.h"
#include "Jade/Fifo/AbstractFifo.h"
#include "Jade/Actor/Actor.h"
//------------------------------

using namespace llvm;
using namespace std;

void AbstractFifo::refineActor(Actor* actor){
	map<string, Type*>::iterator it;
	
	for (it = structAcces.begin(); it != structAcces.end(); ++it){
		
		//Get opaquetype of the current fifo in the actor
		Type* type = actor->getFifoType(it->first);
		if (type == NULL){
			fprintf(stderr,"Structure of fifo %d hasn't been found in actor %d", it->first, actor->getName());
			exit(0);
		}

		if (isa<OpaqueType>(type)){
			OpaqueType* opaqueFifoType = cast<OpaqueType>(type);

			//Refine opaque type of the corresponding fifo structure
			opaqueFifoType->refineAbstractTypeTo(it->second);
		}
	}
}


void AbstractFifo::setFifoFunction(std::string name, llvm::Function* function){
		std::map<std::string,llvm::Function*>::iterator it;

		it = fifoAccess.find(name);

		if (it == fifoAccess.end()){
			fprintf(stderr,"Error when setting circular fifo");
			exit(0);
		}
	
		(*it).second = function;
}

void AbstractFifo::createFifoMap (){
	std::map<std::string,std::string>::iterator it;

	// Create a map that bound fifo access to their function name
	fifoFunct = fifoMap();
	
	// Initialized element 
	for(it = fifoFunct.begin(); it != fifoFunct.end(); ++it){
		fifoAccess.insert(pair<string,Function*>((*it).second,NULL));
	}
}

void AbstractFifo::createStructMap (){
	std::map<std::string,std::string>::iterator it;

	// Create a map that bound fifo access to their function name
	structName = structMap();
	
	// Initialized element 
	for(it = structName.begin(); it != structName.end(); ++it){
		structAcces.insert(pair<string,Type*>((*it).second,NULL));
	}
}

void AbstractFifo::setFifoStruct(std::string name, llvm::Type* type){
		std::map<std::string,llvm::Type*>::iterator it;

		it = structAcces.find(name);

		if (it == structAcces.end()){
			fprintf(stderr,"Error when setting structure of fifo");
			exit(0);
		}
	
		(*it).second = type;
}

void AbstractFifo::addFifoType(Decoder* decoder){
	//Get fifos
	map<string, Type*>::iterator it;

	for (it = structAcces.begin(); it != structAcces.end(); ++it){
		jit->addType(it->first, it->second, decoder);
	}
}

void AbstractFifo::addFifoHeader(Decoder* decoder){
	addFifoType(decoder);
	addFunctions(decoder);
}

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
@brief Implementation of class InstanceInternalize
@author Jerome Gorin
@file InstanceInternalize.cpp
@version 1.0
@date 24/12/2010
*/

//------------------------------
#include <map>

#include "llvm/Function.h"

#include "Jade/Core/Instance.h"
#include "Jade/Optimize/InstanceInternalize.h"

//------------------------------

using namespace std;
using namespace llvm;

void InstanceInternalize::transform(Decoder* decoder){
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = decoder->getInstances();

	for (it = instances->begin(); it != instances->end(); it++){
		doInternalize(it->second);
	}
}

void InstanceInternalize::doInternalize(Instance* instance){
	//Visit actions
	list<Action*>::iterator it;
	list<Action*>* actions = instance->getActions();

	for (it = actions->begin(); it != actions->end(); it++){
		setProcInternal((*it)->getScheduler());
	}

	//Visit initializes
	list<Action*>* initializes = instance->getInitializes();

	for (it = initializes->begin(); it != initializes->end(); it++){
		setProcInternal((*it)->getScheduler());
	}

	//Visit procedures
	map<string, Procedure*>::iterator itProc;
	map<string, Procedure*>* procs = instance->getProcs();

	for (itProc = procs->begin(); itProc != procs->end(); itProc++){
		Procedure* proc = itProc->second;
		if  (!proc->isExternal()){
			setProcInternal(proc);
		}
	}

}

void InstanceInternalize::setProcInternal(Procedure* procedure){
	Function* function = procedure->getFunction();
	function->setLinkage(Function::InternalLinkage);
}
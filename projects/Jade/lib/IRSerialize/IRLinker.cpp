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
@brief Implementation of class IRLinker
@author Jerome Gorin
@file IRLinker.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>

#include "Jade/Decoder.h"
#include "Jade/Core/Port.h"
#include "Jade/Serialize/IRLinker.h"

#include "llvm/Module.h"

#include "IRConstant.h"
//------------------------------

using namespace std;
using namespace llvm;

IRLinker::IRLinker(Decoder* decoder){
	this->decoder = decoder;

}

IRLinker::~IRLinker(){

}

int IRLinker::link(list<pair<Instance*, Instance*>>* instances){
	list<pair<Instance*, Instance*>>::iterator it;

	//Iterate though instances to link
	for (it = instances->begin(); it != instances->end(); it++){
		linkInstance(it->first, it->second);
	}

	return 0;
}

void IRLinker::linkInstance(Instance* refinstance, Instance* instance){

	instance->setActions(refinstance->getActions());
	instance->setStateVars(refinstance->getStateVars());
	instance->setParameters(refinstance->getParameters());
	instance->setProcs(refinstance->getProcs());
	instance->setInitializes(refinstance->getInitializes());
	instance->setActionScheduler(refinstance->getActionScheduler());
}


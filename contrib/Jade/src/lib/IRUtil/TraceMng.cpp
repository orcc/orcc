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
@brief Implementation of class TraceMng
@author Jerome Gorin
@file TraceMng.cpp
@version 1.0
@date 22/10/2011
*/

//------------------------------
#include <sstream>

#include "llvm/Constants.h"
#include "llvm/LLVMContext.h"
#include "llvm/Instructions.h"
#include "llvm/Module.h"

#include "Jade/Core/Port.h"
#include "Jade/Core/StateVariable.h"
#include "Jade/Core/Actor/Action.h"
#include "Jade/Util/FunctionMng.h"
#include "Jade/Util/TraceMng.h"
//------------------------------

using namespace llvm;
using namespace std;

void TraceMng::createActionTrace(Module* module, Action* action, Instruction* instruction){
	// Print action fired
		stringstream message;
		
		message << "-> firing ";
		if (!action->getTag()->isEmpty()){
			message << action->getCalName() << ": ";
		}
		
		message << "action ";

		// Print pattern
		map<Port*, llvm::ConstantInt*>::iterator it;

		Pattern* input = action->getInputPattern();
		if (!input->isEmpty()){
			map<Port*, llvm::ConstantInt*>* inputNumToken = input->getNumTokensMap();

			for (it = inputNumToken->begin(); it != inputNumToken->end(); ){
							
				// Print port name
				Port* port = it->first;
				ConstantInt* tokens = it->second;
				message << port->getName() << ":[]";

				if (!tokens->isOne()){
					// Print port consumption
					message << " repeat "<< tokens->getValue().getLimitedValue();

				}

				// Port separation
				if (++it != inputNumToken->end()){
					message << ", ";
				}
			}

		}

		message << "==> ";

		Pattern* output = action->getOutputPattern();
		if (!output->isEmpty()){
			map<Port*, llvm::ConstantInt*>* outputNumToken = output->getNumTokensMap();

			for (it = outputNumToken->begin(); it != outputNumToken->end();){
				
				// Print port name
				Port* port = it->first;
				ConstantInt* tokens = it->second;
				message << port->getName() << ":[]";

				if (!tokens->isOne()){
					// Print port production
					message << " repeat "<< tokens->getValue().getLimitedValue();

				}

				// Port separation
				if (++it != outputNumToken->end()){
					message << ", ";

				}
			}

		}
		
		message << "\n";
		
		FunctionMng::createPuts(module, message.str(), instruction);
}

void TraceMng::createStateVarTrace(Module* module, map<std::string, StateVar*>* stateVars, Instruction* instruction){
	map<std::string, StateVar*>::iterator it;
	
	if (stateVars->empty()){
		return;
	}
	
	FunctionMng::createPuts(module, "State variables snapshot:", instruction);

	for (it = stateVars->begin(); it != stateVars->end(); ++it){
		StateVar* var = it->second;
		string name = it->first;

		Type* type = var->getType();

		if (isa<ArrayType>(type)){
			// TODO
		}else{
			// Load value
			LoadInst* loadInst = new LoadInst(var->getGlobalVariable(), "", instruction);
			
			// Create message
			string message(name);

			message.append(" = %d \n");
			FunctionMng::createPrintf(module, message, instruction, loadInst);
		}

	}

	FunctionMng::createPuts(module, "", instruction);
}

void TraceMng::createCallTrace(Module* module, Instance* instance, Instruction* instruction){
	string message = "************************** enabling ";
	message.append(instance->getId());
	message.append(" **************************");

	FunctionMng::createPuts(module, message, instruction);

	createStateVarTrace(module, instance->getStateVars(), instruction);

	string message2 = "Scheduling actions: \n";
	FunctionMng::createPuts(module, message2, instruction);
}
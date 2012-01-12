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
@brief Description of the FifoMng class interface
@author Jerome Gorin
@file FifoMng.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef TRACEMNG_H
#define TRACEMNG_H
#include <string>
#include <map>


class Action;
class Instance;
class StateVar;
//------------------------------

/**
 * @class FunctionMng
 *
 * @brief This class contains methods for managing llvm functions.
 *
 * @author Jerome Gorin
 * 
 */
class TraceMng {

public:

	/**
	 * @brief Create traces for the given action
	 *
	 * @param module : module where traces are placed
	 *
	 * @param action : the Action to trace
	 *
	 * @param instruction : the instruction where trace are added
	 */
	static void createActionTrace (llvm::Module* module, Action* action, llvm::Instruction* instruction);

	/**
	 * @brief Create traces for the given list of state variable
	 *
	 * @param module : module where traces are placed
	 *
	 * @param stateVars : a map of stateVariable to trace
	 *
	 * @param instruction : the instruction where trace are added
	 */
	static void createStateVarTrace(llvm::Module* module, std::map<std::string, StateVar*>* stateVars, llvm::Instruction* instruction);

	/**
	 * @brief Create traces when calling the given instance
	 *
	 * @param module : module where traces are placed
	 *
	 * @param stateVars : a map of stateVariable to trace
	 *
	 * @param instruction : the instruction where trace are added
	 */
	static void createCallTrace(llvm::Module* module, Instance* instance, llvm::Instruction* instruction);
	
};

#endif
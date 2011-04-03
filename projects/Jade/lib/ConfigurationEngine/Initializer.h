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
@brief Description of the Initializer class interface
@author Jerome Gorin
@file Instantiator.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef INITIALIZER_H
#define INITIALIZER_H
#include <map>

#include "Jade/Core/Network/Instance.h"
#include "Jade/Core/Expr/BoolExpr.h"
#include "Jade/Core/Expr/IntExpr.h"
#include "Jade/Core/Expr/ListExpr.h"

namespace llvm{
	class BasicBlock;
}
class Decoder;
class Instance;
class LLVMExecution;
//------------------------------

/**
 * @class Instantiator
 *
 * @brief This class is used by the configuration engine to reinitialized 
 *    already compiled instances.
 *
 * @author Jerome Gorin
 * 
 */
class Initializer {
public:

	/**
	 * @brief Constructor.
	 *
	 *	Set a new initializer for a decoder
	 *
	 * @param decoder : Decoder where instance has to be reinitialized
	 */
	Initializer(llvm::LLVMContext& C, Decoder* decoder);

	/**
	 * @brief Add an instance to initialize.
	 *
	 *	Add an already compiled instance for reinitialization
	 *
	 * @param instance : Instance to reinitialize
	 */
	void add(Instance* instance);

	/**
	 * @brief Launch the initialization process.
	 *
	 */
	void initialize();

	~Initializer();

private:

	/**
	 * @brief Create an initialize function
	 *
	 *	@param module : the Module to write the function
	 */
	void createInitializeFn(llvm::Module* module);

	/**
	 * @brief Initialize an FSM
	 * 
	 * @param fsm : the FSM to initialize
	 */
	void initializeFSM(FSM* fsm);

	/**
	 * @brief Initialize a list of state variable
	 * 
	 * @param vars : the state variables to initialize
	 */
	void initializeStateVariables(std::map<std::string, StateVar*>* vars);

	/**
	 * @brief Initialize a list of parameter
	 * 
	 * @param vars : the state variables to initialize
	 */
	void initializeParameters(std::map<std::string, Variable*>* parameters);

	/**
	 * @brief Initialize a variable
	 * 
	 * @param var : the variable to initialize
	 */
	void initializeVariable(Variable* var);


	/**
	 * @brief Initialize an integer expression
	 * 
	 * @param var : the GlobalVariable to initialize
	 *
	 * @param expr : the initial value
	 */
	void initializeIntExpr(llvm::GlobalVariable* var, IntExpr* expr);

	/**
	 * @brief Initialize a list expression
	 * 
	 * @param var : the GlobalVariable to initialize
	 *
	 * @param expr : the initial values
	 */
	void initializeListExpr(llvm::GlobalVariable* var, ListExpr* expr);

	/**
	 * @brief Initialize a bool expression
	 * 
	 * @param var : the GlobalVariable to initialize
	 *
	 * @param expr : the initial values
	 */
	void initializeBoolExpr(llvm::GlobalVariable* var, BoolExpr* expr);

	/** Decoder to initialize */
	Decoder* decoder;

	/** LLVMExecution that compiled the given decoder */
	LLVMExecution* executionEngine;

	/** Initialization function and its entry BB*/
	llvm::Function* initFn;
	llvm::BasicBlock* entryBB;

	/** LLVM Context */
	llvm::LLVMContext &Context;
};

#endif
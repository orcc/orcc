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
@brief Description of the Procedure ActionScheduler interface
@author Jerome Gorin
@file ActionScheduler.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef ROUNDROBINSCHEDULER_H
#define ROUNDROBINSCHEDULER_H

namespace llvm{
	class CallInst;
	class Function;
	class LLVMContext;
}

class Decoder;

#include "Jade/Scheduler/Scheduler.h"
#include "Jade/Jit/LLVMExecution.h"
//------------------------------

/**
 * @brief  This class defines a robin robin scheduler of a decoder.
 * 
 * @author Jerome Gorin
 * 
 */
class RoundRobinScheduler : public Scheduler {
public:
	/**
     *  @brief Constructor
     *
	 *	Create a new round robin scheduler for the given decoder
	 *
	 *	@param C : the LLVM Context
	 *
	 *	@param decoder : the Decoder to insert the round robin scheduler into
     */
	RoundRobinScheduler(llvm::LLVMContext& C, Decoder* decoder, bool noMultiCore = false, bool verbose = false);
	~RoundRobinScheduler();

	/**
     *  @brief Return the main function of the scheduler
     *
	 *	@return the main llvm::Function of scheduler
     */
	llvm::Function* getMainFunction(){return scheduler;};
	
	/**
     *  @brief Add an instance in the scheduler
     *
	 *	@param instance : the Instance to add
     */
	void addInstance(Instance* instance);

	/**
     *  @brief Remove an instance in the scheduler
     *
	 *	@param instance : the Instance to remove
     */
	void removeInstance(Instance* instance);

private:
	/**
     *  @brief Insert a scheduler into the decoder
     */
	void createScheduler();
	
	/**
     *  @brief Create the network scheduler
     */
	void createNetworkScheduler();

	/**
     *  @brief Create a call to the action scheduler of an instance in the instance
	 *
	 *  @param instance : the Instance to call
     */
	void createCall(Instance* instance);
	
	/**
     *  @brief Remove a call
	 *
	 *  @param function : the llvm::Function to remove
     */
	void removeCall(llvm::Function* function);

	/** Decoder bound to the round robin scheduler */
	Decoder* decoder;

	/** Configuration of the decoder */
	Configuration* configuration;

	/** Main scheduling function */
	llvm::Function* scheduler;

	/** Main scheduling component */
	llvm::Instruction* initBrInst;
	llvm::Instruction* schedBrInst;

	/** Function calls */
	std::map<llvm::Function*, llvm::CallInst*> functionCall;

	/** LLVM Context */
	llvm::LLVMContext &Context;
	
	/** Deactivate multicore support */
	bool noMultiCore;

	/** Print all actions made by LLVM execution engine*/
	bool verbose;
};

#endif
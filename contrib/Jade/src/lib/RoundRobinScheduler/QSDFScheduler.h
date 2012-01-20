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
@brief Description of the QSDFScheduler interface
@author Jerome Gorin
@file QSDFScheduler.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef QSDFSCHEDULER_H
#define QSDFSCHEDULER_H

#include "CSDFScheduler.h"
//------------------------------

/**
 * @brief  This class defines an action scheduler for a QSDF actor.
 * 
 * @author Jerome Gorin
 * 
 */
class QSDFScheduler : public CSDFScheduler {
public:
	/**
     *  @brief Constructor
     *
	 *	Create a new action scheduler for dynamic actors
	 *
	 *	@param C : the llvm::Context
	 *
	 *	@param decoder : the Decoder where dynamic action scheduler is inserted
     */
	QSDFScheduler(llvm::LLVMContext& C, Decoder* decoder, bool debug);
	~QSDFScheduler(){};

protected:
	/**
     *  @brief Create the scheduler of actions
	 *
	 * @param instance: the Instance to add the scheduler
	 *
	 * @param BB : llvm::BasicBlock where scheduler is add
	 *
	 * @param incBB : llvm::BasicBlock where scheduler has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where scheduler has to branch in case of return
	 *
	 * @param function : llvm::Function where the scheduler is added
	 */
	void createScheduler(Instance* instance, llvm::BasicBlock* BB, llvm::BasicBlock* incBB, llvm::BasicBlock* returnBB, llvm::Function* scheduler);

	/**
	 * @brief Creates a scheduling test for a qsdf
	 * 
	 * @param action : the Action to test
	 *
 	 * @param csdfMoC : the csdfMoC to execute
	 *
	 * @param BB : llvm::BasicBlock where test is add
	 *
	 * @param incBB : llvm::BasicBlock where test has to branch in case of success
	 *
	 * @param returnBB : llvm::BasicBlock where test has to branch in case of return
	 *
	 * @param function : llvm::Function where the test is added
	 */
	llvm::BasicBlock* createConfigurationTest(Action* action, CSDFMoC* csdfMoC, llvm::BasicBlock* BB, 
										llvm::BasicBlock* incBB, llvm::BasicBlock* returnBB, llvm::Function* function);

};

#endif
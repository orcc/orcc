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
@brief Description of the Decoder class interface
@author Jerome Gorin
@file Decoder.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef DECODER_H
#define DECODER_H

#include <string>
#include <map>
#include <list>
#include <pthread.h>

namespace llvm{
	class LLVMContext;
	class Module;
}

class AbstractConnector;
class Actor;
class BroadcastActor;
class ConfigurationEngine;
class Decoder;
class Instance;
class JIT;
class LLVMExecution;
class Configuration;
class BroadcastAdder;

#include "Jade/Scheduler/Scheduler.h"
//------------------------------

class Decoder {
public:
	
	/**
	 * Create a new decoder using an XDF network.
	 * 
	 * @param C : the LLVMContext
	 *            
	 * @param jit : jit use in this decoder
	 *            
	 * @param scenario : Scenario to instanciate the decoder
	 *
	 * @param fifo : Fifo used in the decoder
	 *
	 */
	Decoder(llvm::LLVMContext& C, Configuration* configuration);
	~Decoder();
	
	/**
     *  @brief Getter of module
	 *
	 *  @return llvm::Module bound to this decoder
	 *
     */
	llvm::Module* getModule(){return module;};

	/**
     *  @brief Getter of fifo
	 *
	 *  @return Fifo bound to this decoder
	 *
     */
	AbstractConnector* getFifo(){return fifo;};


	/**
     *  @brief Getter of fifos
	 *
	 *  @return a list of fifos in the decoder
	 *
     */
	std::list<AbstractConnector*>* getFifos(){return &fifos;};

	/**
     *  @brief Getter of configuration
	 *
	 *	Return the configuration used by the decoder
	 * 
	 *  @return configuration used by the decoder
	 *
     */
	Configuration* getConfiguration(){return configuration;};

	/**
     *  @brief Getter of a stimulus file
	 * 
	 *  @return the stimulus file
     */
	std::string getStimulus(){return stimulus;};

	/**
     *  @brief Getter of scheduler
	 *
	 *  Returns the scheduler used in the decoder
	 * 
	 *  @return the scheduler used by the decoder
     */
	 Scheduler* getScheduler(){return scheduler;};

	/**
     *  @brief Setter of a stimulus file
	 * 
	 *  @param file : the stimulus file
     */
	void setStimulus(std::string file);


	/**
     *  @brief Start the decoder
	 *
     */
	void start();

	/**
     *  @brief Start the decoder in a specific thread
	 *
     */
	void startInThread(pthread_t* thread);

	/**
     *  @brief Stop the execution of the decoder
	 *
     */
	void stop();

private:
	/**
     *  @brief Static method for launching decoder in a thread
	 *
     */
	static void* threadStart( void* args );

	/** Module containing the final decoder */
	llvm::Module* module;

	/** Configuration of the decoder */
	Configuration* configuration;

	/** Input stimulus */
	std::string stimulus;
	
	/** List of Fifo in the decoder */
	std::list<AbstractConnector*> fifos;

	/** Scheduler of the decoder */
	Scheduler* scheduler;

	/** Fifo of the decoder */
	AbstractConnector* fifo;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Execution engine of the decoder */
	LLVMExecution* executionEngine;

	/** Current thread used by the decoder */
	pthread_t* thread;
};

#endif
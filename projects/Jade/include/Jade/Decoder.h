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

class AbstractFifo;
class Actor;
class BroadcastActor;
class Decoder;
class Instance;
class JIT;
class LLVMExecution;
class Network;
class BroadcastAdder;

#include "Jade/Scheduler/Scheduler.h"
//------------------------------

class Decoder {
public:
	
	/**
	 * Create a new decoder using an XDF network.
	 * 
	 * @param C : LLVMContext of LLVM JIT
	 *            
	 * @param jit : jit use in this decoder
	 *            
	 * @param network : Network that represents the decoder
	 *
	 * @param fifo : Fifo used in the decoder
	 *
	 */
	Decoder(llvm::LLVMContext& C, Network* network, AbstractFifo* fifo);
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
	AbstractFifo* getFifo(){return fifo;};


	/**
     *  @brief Getter of fifos
	 *
	 *  @return a list of fifos in the decoder
	 *
     */
	std::list<AbstractFifo*>* getFifos(){return &fifos;};

	/**
     *  @brief Getter of instances
	 * 
	 *	Return a map of instances link with their name in the decoder
	 *
	 *  @return a map of instances
	 *
     */
	std::map<std::string, Instance*>* getInstances(){return instances;};

	/**
     *  @brief Add an instance
	 * 
	 *	Add an instance in the decoder
	 *
	 *  @param instance: Instance to add in the decoder
     */
	void addInstance(Instance* instance);

	/**
     *  @brief Add a specific actor in the decoder
	 * 
	 *	Add an actor specific to this decoder
	 *
	 *  @param actor: specific actor to add
     */
	void addSpecific(Actor* actor);

	/**
     *  @brief return the actor corresponding to the given name
	 * 
	 *	Return an actor corresponding to the given name if the current decoder is contained in the decoder,
	 *   return NULL if the actor is not contained in the decoder
	 *
	 *	@param name : std::string name of the actor 
	 *
	 *  @return the actor if contained in the decoder otherwise NULL
	 *
     */
	Actor* getActor(std::string name);

	/**
     *  @brief Getter of an instance
	 * 
	 *	Return the instance with the given name
	 *
	 *  @return the instance if found, otherwise NULL
	 *
     */
	Instance* getInstance(std::string name);

	/**
     *  @brief Set actors contained in the decoder
	 * 
	 *	Set a map of actor that are contained in the decoder
	 *
	 *	@param actors : a map of actor 
	 *
     */
	void setActorList(std::map<std::string, Actor*>* actors){this->actors = actors;};

	/**
     *  @brief Getter of a network
	 * 
	 *  @return network of the decoder
	 *
     */
	Network* getNetwork(){return network;};

	/**
     *  @brief Getter of a specific actor
	 *
	 *	Return the specifics actors to this decoder
	 * 
	 *  @return a list of actors
	 *
     */
	std::list<Actor*>* getSpecifics(){return &specificActors;};

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

	/**
	 * @brief Make the decoder
	 * 
	 * Make the decoder using an XDF Network and the VTL. Compilation may include
	 * instantiation, flattening, transforming, printing the network, or a subset of these steps.
	 * 
	 * @param actors : a map of loaded actors
	 *
	 * @return true if compilation ok, otherwise false
	 */
	bool make(std::map<std::string, Actor*>* actors);

	/**
     *  @brief Compile the decoder
	 *
	 *	Compile the decoder to make it ready for execution.
	 *
     */
	void compile();

	/**
     *  @brief Set a new decoder
	 *
	 *	Change the network of the decoder into a new network
	 *
	 *	@param network : the new Network
	 *
     */
	void setNetwork(Network* network);


private:
	/**
     *  @brief Static method for launching decoder in a thread
	 *
     */
	static void* threadStart( void* args );

	/** Module containing the final decoder */
	llvm::Module* module;

	/** Input network */
	Network* network;

	/** Input stimulus */
	std::string stimulus;

	/** List of actors contained in the decoder */
	std::map<std::string, Actor*>* actors;


	/** List of specific actors contained in the decoder */
	std::list<Actor*> specificActors;

	/** Map of instances in the decoder */
	std::map<std::string, Instance*>* instances;
	
	/** List of Fifo in the decoder */
	std::list<AbstractFifo*> fifos;

	/** Scheduler of the decoder */
	Scheduler* scheduler;

	/** Fifo of the decoder */
	AbstractFifo* fifo;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Execution engine of the decoder */
	LLVMExecution* executionEngine;

	/** Current thread used by the decoder */
	pthread_t* thread;

};

#endif
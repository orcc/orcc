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
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef DECODER_H
#define DECODER_H

#include <map>
#include <list>

namespace llvm{
	class LLVMContext;
	class Module;
}

class AbstractFifo;
class Actor;
class Decoder;
class Instance;
class JIT;
class Network;
class BroadcastAdder;
class RoundRobinScheduler;
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
	Decoder(llvm::LLVMContext& C, JIT* jit, Network* network, AbstractFifo* fifo);
	~Decoder();
	
	/**
     *  @brief Getter of module
	 *
	 *  @return llvm::Module bound to this decoder
	 *
     */
	llvm::Module* getModule(){return module;};

	/**
     *  @brief Getter of jit
	 *
	 *  @return JIT bound to this decoder
	 *
     */
	JIT* getJIT(){return jit;};

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
     *  @brief Instanciate decoder
	 * 
	 *	Instanciate decoder using the given actors
	 *
	 *  @return true if instanciate ok
	 *
     */
	int instanciate();

	/**
     *  @brief Setter of the decoder scheduler
	 *
	 *  @param scheduler : the scheduler of the decoder
	 *
     */
	void setScheduler(RoundRobinScheduler* scheduler);

	/**
     *  @brief Start the decoder
	 *
     */
	void start();
	
	/**
	 * @brief Compile the decoder
	 * 
	 * Compile the decoder using an XDF Network and the VTL. Compilation may include
	 * instantiation, flattening, transforming, printing the network, or a subset of these steps.
	 * 
	 * @param actors : a map of loaded actors
	 *
	 * @return true if compilation ok, otherwise false
	 */
	bool compile(std::map<std::string, Actor*>* actors);


private:
	/** Module containing the final decoder */
	llvm::Module* module;

	/** Input network */
	Network* network;

	/** List of actors contained in the decoder */
	std::map<std::string, Actor*>* actors;


	/** List of instances in the decoder */
	std::map<std::string, Instance*>* instances;
	
	/** List of Fifo in the decoder */
	std::list<AbstractFifo*> fifos;

	/** Scheduler of the decoder */
	RoundRobinScheduler* scheduler;

	/** JIT of decoder engine */
	JIT* jit;

	/** Fifo of the decoder */
	AbstractFifo* fifo;

	/** LLVM Context */
	llvm::LLVMContext &Context;

};

#endif
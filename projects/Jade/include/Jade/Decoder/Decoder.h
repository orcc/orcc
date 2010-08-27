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

//#include "llvm/Module.h"

namespace llvm{
	class LLVMContext;
	class Module;
}

class AbstractFifo;
class Actor;
class Decoder;
class Instance;
class InstancedActor;
class JIT;
class Network;
class BroadcastAdder;
//------------------------------

class Decoder {
public:
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
     *  @brief Getter of instancedFus
	 *
	 *  @return a map of instanced FU
	 *
     */
	std::map<Instance*, InstancedActor*>* getInstancedActors(){return &instancedActors;};

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
     *  @brief Getter of an instance
	 * 
	 *	Return the instance with the given name
	 *
	 *  @return the instance if found, otherwise NULL
	 *
     */
	Instance* getInstance(std::string name);

	/**
     *  @brief Getter of initialization
	 * 
	 *
	 *  @return initialization procedure of the decoder
	 *
     */
	Procedure* getInitialization(){return initialization;};

	/**
     *  @brief return true if decoder has an initialization procedure
	 * 
	 *
	 *  @return true if decoder has an initialization procedure otherwise false
	 *
     */
	bool hasInitialization(){return initialization != NULL;};

	/**
     *  @brief Setter of initialization
	 * 
	 *
	 *  @param initialization : initialization procedure of the decoder
	 *
     */
	void setInitialization(Procedure* initialization){this->initialization = initialization;};

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

private:
	/** Module containing the final decoder */
	llvm::Module* module;

	/** Input network */
	Network* network;

	/** List of actors in the decoder */
	std::map<std::string, Actor*>* actors;

	/** List of instances in the decoder */
	std::map<std::string, Instance*>* instances;
	
	/** List of instanced Functional Unit in the decoder */
	std::map<Instance*, InstancedActor*> instancedActors;

	/** List of Fifo in the decoder */
	std::list<AbstractFifo*> fifos;

	/** JIT of decoder engine */
	JIT* jit;

	/** Fifo of the decoder */
	AbstractFifo* fifo;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Initialization procedure */
	Procedure* initialization;

	/**
     *  @brief Create instance of actors
	 * 
	 *	Instanciate actors of the decoder. DecoderEngine MUST have parsed
	 *	 the necessary actor before instanciating.
	 *
     */
	void createActorInstances();

	/**
     *  @brief Create the current instance inside the final decoder Module
	 *
	 *	@param instance : Instance to create.
     */
	InstancedActor* createInstance(Instance* instance);
};

#endif
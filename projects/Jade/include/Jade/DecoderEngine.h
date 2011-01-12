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
@brief Description of the DecoderEngine class interface
@author Jerome Gorin
@file DecoderEngine.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef DECODERENGINE_H
#define DECODERENGINE_H

#include <map>
#include <string>
#include <pthread.h>

namespace llvm{
	class LLVMContext;
}

class AbstractFifo;
class JIT;
class Decoder;
class FifoCircular;
class Network;
class Instance;
class FuncUnit;
class Actor;
class IRParser;
//------------------------------

class DecoderEngine {
public:
	/*!
     *  @brief Create a new Decoder Engine
     *
     *  Create a new Decoder Engine with the given llvm::LLVMContext.
	 *   
	 *  @param C : the LLVM Context used by JIT
     */
	DecoderEngine(llvm::LLVMContext& C, AbstractFifo* fifo, std::string library, std::string system = "", bool verbose = false);
	~DecoderEngine();

	/*!
     *  @brief Load the given network
     *
     *  Load, create and execute the given network.
	 *   
	 *  @param network : the Network to load
	 *
	 *  @param input : the input stimulus
     *
	 *  @param optLevel : the level of optimization to apply
	 *
	 *  @param thread : the thread where network is execute
	 *
	 *  @return HDAGGraph representing the network's contents
     */
	int load(Network* network, std::string input, int optLevel, pthread_t* thread = NULL);

private:
	/*!
     *  @brief Parse actors
     *
     *  Parse the actors of the given network.
	 *   
	 *  @param network : the Network to parse
     */
	void parseActors(Network* network);

	/*!
     *  @brief Optimize decoder
     *
     *  Perform special optimization for the decoder
	 *   
	 *  @param decoder : the Decoder to optimize
     */
	void doOptimizeDecoder(Decoder* decoder);
	
	IRParser* irParser;
	std::map<std::string, Actor*> actors;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Fifo of Jade */
	AbstractFifo* fifo;

	/** Library location */
	std::string library;

	/** System package location */
	std::string systemPackage;
	
	/** Decoder used by the decoder engine */
	Decoder* decoder;

	/** Print all actions made by decoder engine*/
	bool verbose;
};

#endif
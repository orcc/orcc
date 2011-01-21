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
@brief Implementation of class Decoder
@author Jerome Gorin
@file Decoder.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <list>
#include <iostream>
#include <fstream>

#include "llvm/LLVMContext.h"
#include "llvm/Module.h"

#include "Jade/Decoder.h"
#include "Jade/Fifo/AbstractConnector.h"
#include "Jade/Core/Network.h"
#include "Jade/Configuration/ConfigurationEngine.h"
#include "Jade/Fifo/AbstractConnector.h"
#include "Jade/Jit/LLVMExecution.h"
#include "Jade/Scheduler/RoundRobinScheduler.h"
//------------------------------

using namespace llvm;
using namespace std;

Decoder::Decoder(LLVMContext& C, Configuration* configuration): Context(C){
	//Set property of the decoder
	this->configuration = configuration;
	this->thread = NULL;
	this->executionEngine = NULL;

	//Create a new module that contains the current decoder
	module = new Module("decoder", C);

	//Set elements of the decoder
	this->scheduler = new RoundRobinScheduler(Context, this);
	this->fifo = configuration->getConnector();

	//Configure the decoder
	ConfigurationEngine engine(Context);
	engine.configure(this);

	//Create execution engine
	executionEngine = new LLVMExecution(Context, this);
	((RoundRobinScheduler*)scheduler)->setExternalFunctions(executionEngine); //Todo : simplify process
}

Decoder::~Decoder (){
	delete scheduler;
	delete module;
}

void Decoder::setConfiguration(Configuration* configuration){
	
	//Reconfigure the decoder
	ConfigurationEngine engine(Context);
	engine.reconfigure(this, configuration);

	//executionEngine->recompile(scheduler->getMainFunction());
	//executionEngine->run();
}

void Decoder::start(){
	scheduler->setSource(stimulus);
		
	executionEngine->run();
}

void Decoder::stop(){
	executionEngine->stop(thread);
}

void Decoder::startInThread(pthread_t* thread){
	this->thread = thread;
	pthread_create( thread, NULL, &Decoder::threadStart, this );
}

void* Decoder::threadStart( void* args ){
	Decoder* decoder = static_cast<Decoder*>(args);
	decoder->start();
	return NULL;
}

void Decoder::setStimulus(std::string file){
	this->stimulus = file;
}
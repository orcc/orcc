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
#include "Jade/Core/Network.h"
#include "Jade/Configuration/ConfigurationEngine.h"
#include "Jade/Jit/LLVMExecution.h"
#include "Jade/RoundRobinScheduler/RoundRobinScheduler.h"
#include "Jade/Util/FifoMng.h"
//------------------------------

using namespace llvm;
using namespace std;

Decoder::Decoder(LLVMContext& C, Configuration* configuration, bool verbose, bool noMultiCore): Context(C){
	clock_t timer = clock ();
	
	//Set property of the decoder
	this->configuration = configuration;
	this->verbose = verbose;
	this->thread = NULL;
	this->executionEngine = NULL;
	this->fifoFn = NULL;
	this->running = false;
	this->scheduler = NULL;
	this->noMultiCore = noMultiCore;

	//Create a new module that contains the current decoder
	module = new Module("decoder", C);

	// Add Fifo function and fifo type into the decoder
	this->fifoFn = FifoMng::addFifoHeader(this);

	//Configure the decoder
	ConfigurationEngine engine(Context, verbose);
	engine.configure(this);

	//Set elements of the decoder
	scheduler = new RoundRobinScheduler(Context, this);

	//Create execution engine
	executionEngine = new LLVMExecution(Context, this, verbose);
}

Decoder::~Decoder (){
	delete scheduler;
	delete module;
}

list<Procedure*> Decoder::getExternalProcs(){
	list<Procedure*> externs;

	// Look across all instances for external procedures
	map<std::string, Instance*>::iterator it;
	map<std::string, Instance*>* instances = configuration->getInstances();
	for (it = instances->begin(); it != instances->end(); it++){
		Instance* instance = it->second;

		// Look accross all procs for external property
		map<string, Procedure*>::iterator itProc;
		map<string, Procedure*>* procs = instance->getProcs();

		for (itProc = procs->begin(); itProc != procs->end(); itProc++){
			Procedure* proc = itProc->second;

			if (proc->isExternal()){
				externs.push_back(proc);
			}

		}
	}

	return externs;
}

void Decoder::setConfiguration(Configuration* newConfiguration){
	clock_t start = clock ();
	if (running){
		//Decoder is currently running
		cout << "Can't set a configuration, the decoder is currently running.";
		exit(1);
	}

	//Reconfigure the decoder
	ConfigurationEngine engine(Context, verbose);
	engine.reconfigure(this, newConfiguration);
	if (verbose){
		cout<< "---> Reconfiguring times of engines takes " << (clock () - start) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		start = clock();
	}

	//Delete old configuration and set the new one
	delete configuration;
	configuration = newConfiguration;

	executionEngine->recompile(scheduler->getMainFunction());

	if (verbose){
		cout<< "---> Scheduling recompilation takes " << (clock () - start) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
}

void Decoder::run(){
	running = true;

	executionEngine->run();
}

void Decoder::stop(){
	executionEngine->stop(thread);
	
	running = false;

	ConfigurationEngine engine(Context);
	engine.reinit(this);
}

void Decoder::runInThread(pthread_t* thread){
	this->thread = thread;
	
	//Lock display mutex until the first image arrive
	pthread_create( thread, NULL, &Decoder::threadRun, this );
}

void* Decoder::threadRun( void* args ){
	Decoder* decoder = static_cast<Decoder*>(args);
	decoder->run();
	return NULL;
}
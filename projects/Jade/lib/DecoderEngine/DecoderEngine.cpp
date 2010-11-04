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
@brief Implementation of class DecoderEngine
@author Jerome Gorin
@file DecoderEngine.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <time.h>
#include <iostream>

#include "llvm/Constants.h"
#include "llvm/Support/CommandLine.h"
#include "Jade/JIT.h"
#include "Jade/DecoderEngine.h"
#include "Jade/Actor/IRParser.h"
#include "Jade/Core/Port.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Fifo/AbstractFifo.h"
#include "Jade/Core/Network.h"
#include "Jade/Scheduler/RoundRobinScheduler.h"

//------------------------------

using namespace std;
using namespace llvm;


DecoderEngine::DecoderEngine(llvm::LLVMContext& C, JIT* jit, AbstractFifo* fifo): Context(C) {	
	//Set properties
	this->jit = jit;
	this->fifo = fifo;
	
	//Load IR Parser
	irParser = new IRParser(C, jit, fifo);	
}

DecoderEngine::~DecoderEngine(){

}

int DecoderEngine::load(Network* network) {
	map<string, Actor*>::iterator it;
	clock_t timer = clock ();
	XDFnetwork = network;

	// Parsing actor
	parseActors(network);

	//Create decoder
	decoder = new Decoder(Context, jit, network, &actors, fifo);

	jit->initEngine(decoder);
	
	RoundRobinScheduler scheduler(Context, jit, decoder);

	jit->optimize(decoder);

	jit->printModule("module.txt", decoder);

	jit->verify("error.txt", decoder);

	scheduler.execute();


	cout << "--> Modules parsed in : "<<(clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	timer = clock ();

	cout << "--> ExecutionEngine initialized in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	timer = clock ();

	cout << "--> Creating decoder : \n";
	cout << "--> Decoder created in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	timer = clock ();

	system("pause");
	return 0;
}

void DecoderEngine::parseActors(Network* network) {
	list<string>::iterator it;
	list<string>* files = network->getActorFiles();
	
	for ( it = files->begin(); it != files->end(); ++it ){
		Actor* actor = irParser->parseActor(*it);
		
		fifo->refineActor(actor);
		
		actors.insert(pair<string, Actor*>(*it, actor));
	}
}

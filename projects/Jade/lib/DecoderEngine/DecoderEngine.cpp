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

#include "Jade/DecoderEngine.h"
#include "Jade/JIT.h"
#include "Jade/Actor/IRParser.h"
#include "Jade/Actor/Port.h"
#include "Jade/Decoder/Decoder.h"
#include "Jade/Fifo/FifoCircular.h"
#include "Jade/Fifo/FifoTrace.h"
#include "Jade/Fifo/UnprotectedFifo.h"
#include "Jade/Network/Network.h"
#include "Jade/Scheduler/RoundRobinScheduler.h"

//------------------------------

using namespace std;
using namespace llvm;

//Options of Jade
extern cl::opt<std::string> VTLDir;
extern cl::opt<std::string> ToolsDir;
extern cl::opt<std::string> Fifo;
extern cl::opt<std::string> OutputDir;


//Verify if directory is well formed
void setDirectory(std::string* dir){
	size_t found = dir->find_last_of("/\\");
	if(found != dir->length()-1){
		dir->insert(dir->length(),"/");
	}
}


//Check options of the decoder engine
void setOptions(){
	setDirectory(&VTLDir);
	setDirectory(&ToolsDir);
	setDirectory(&OutputDir);
}	

DecoderEngine::DecoderEngine(llvm::LLVMContext& C): Context(C) {
	// Set Jade options
	setOptions();
	
	//Create JIT
	jit = new JIT(C);
	
	//Set type of fifos
	this->fifo = getFifo();
	
	//Load IR Parser
	irParser = new IRParser(C, jit, fifo);	
}

DecoderEngine::~DecoderEngine(){

}

int DecoderEngine::load(Network* network) {
	map<string, Actor*>::iterator it;
	clock_t timer = clock ();
	XDFnetwork = network;

	//Create decoder
	decoder = new Decoder(Context, jit, network, fifo);
	
	// Parsing actor
	parseActors(network);

	// Remove opaque type from actor
	for ( it = actors.begin(); it != actors.end(); ++it ){
		fifo->refineActor(it->second);
	}


	// Instanciating decoder
	decoder->instanciate();

	// Setting connections of the decoder
	fifo->setConnections(decoder);

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

	map<string, Instance*>::iterator it;
	map<string, Actor*>::iterator itActor;
	map<string, Instance*>* instances = network->getInstances();

	for ( it = instances->begin(); it != instances->end(); ++it ){
		Instance* instance = (*it).second;
		Actor* actor;
		string classz = instance->getClasz();
		itActor = actors.find(classz);

		if (itActor == actors.end()){
			actor = irParser->parseActor(classz);
			actors.insert(pair<string, Actor*>(classz, actor));
		}else{
			actor = (*itActor).second;
		}

		//Bound actor and instance
		actor->addInstance(instance);

	}

	decoder->setActorList(&actors);
}

AbstractFifo* DecoderEngine::getFifo(){
	if(Fifo.compare("trace")==0){
		return new FifoTrace(Context, jit);
	}else if(Fifo.compare("circular")==0){
		return new FifoCircular(Context, jit);
	}else if(Fifo.compare("fast")==0){
		return new UnprotectedFifo(Context, jit);
	}else{
		cout <<"Fifo selection error: type undefined.\n";
		exit(0);
	}
}

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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <time.h>
#include <iostream>

#include "llvm/Constants.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/StandardPasses.h"

#include "Jade/Decoder.h"
#include "Jade/DecoderEngine.h"
#include "Jade/Serialize/IRParser.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Port.h"
#include "Jade/Fifo/AbstractConnector.h"
#include "Jade/Core/Network.h"
#include "Jade/Jit/LLVMUtility.h"
#include "Jade/Jit/LLVMOptimizer.h"
#include "Jade/Optimize/FifoFnRemoval.h"
#include "Jade/Optimize/InstanceInternalize.h"
#include "llvm/Support/PassNameParser.h"
#include "Jade/Util/PackageMng.h"
//------------------------------

using namespace std;
using namespace llvm;

extern cl::list<const PassInfo*, bool, PassNameParser> PassList;

DecoderEngine::DecoderEngine(llvm::LLVMContext& C, 
							 AbstractConnector* fifo, 
							 string library, 
							 string system, 
							 bool verbose): Context(C) {	
	//Set properties
	this->fifo = fifo;
	
	//Load IR Parser
	irParser = new IRParser(C, fifo);	
	this->library = library;
	this->systemPackage = system;
	this->verbose = verbose;

	llvm_start_multithreaded();

}

DecoderEngine::~DecoderEngine(){
	llvm_stop_multithreaded();
}

int DecoderEngine::load(Network* network, int optLevel) {
	map<string, Actor*>::iterator it;
	clock_t timer = clock ();

	//Create a Configuration
	Configuration* configuration = new Configuration(network, fifo);

	// Parsing actor
	parseActors(configuration);

	if (verbose){
		cout << "--> Modules parsed in : "<<(clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}

	timer = clock ();

	//Create decoder
	Decoder* decoder = new Decoder(Context, configuration);

	//Compile the decoder
	decoder->make(&actors);
	
	if (verbose){
		cout << "--> Decoder created in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
	timer = clock ();

	//doOptimizeDecoder(decoder);

	LLVMOptimizer opt(decoder);
	opt.optimize();

	if (verbose){
		cout << "--> Decoder optimized in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
	timer = clock ();
	
	LLVMUtility utility;
	string outName;

	if (PassList.size() > 0){
		outName = PassList[0]->getPassArgument();
	}else{
		outName = "module";
	}
	
	utility.verify("error.txt", decoder);

	if (verbose){
		cout << "--> Decoder verified in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
	timer = clock ();

	decoder->compile();
	
	decoders.insert(pair<Network*, Decoder*>(network, decoder));

	return 0;
}

int DecoderEngine::unload(Network* network) {
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders load for this network.\n";
		return 1;
	}
	
	Decoder* decoder = it->second;
	decoders.erase(it);
	
	return 0;	
}


int DecoderEngine::stop(Network* network){
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}
	
	Decoder* decoder = it->second;
	decoder->stop();

	return 0;
}

int DecoderEngine::run(Network* network, string input, pthread_t* thread){	
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}

	Decoder* decoder = it->second;
	decoder->setStimulus(input);

	//Start decoding
	if (thread != NULL){
		decoder->startInThread(thread);
	} else {
		decoder->start();
	}

	return 0;
}

int DecoderEngine::reconfigure(Network* oldNetwork, Network* newNetwork){
	/*map<Network*, Decoder*>::iterator it;

	it = decoders.find(oldNetwork);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}

	Decoder* decoder = it->second;

	parseActors(newNetwork);

	decoder->setNetwork(newNetwork);*/
	
	return 0;
}

void DecoderEngine::parseActors(Configuration* Configuration) {
	list<string>::iterator it;
	list<string>* files = Configuration->getActorFiles();
	map<string, Actor*> netActors;

	for ( it = files->begin(); it != files->end(); ++it ){
		Actor* actor = irParser->parseActor(*it);
		fifo->refineActor(actor);
		
		netActors.insert(pair<string, Actor*>(*it, actor));
	}

	//network->setPackages(PackageMng::setPackages(&netActors));
	actors.insert(netActors.begin(), netActors.end());
}

void DecoderEngine::doOptimizeDecoder(Decoder* decoder){

	InstanceInternalize internalize;
	internalize.transform(decoder);
	
	PassManager Passes;
	Passes.add(createFunctionInliningPass());
	Passes.run(*decoder->getModule());

	FifoFnRemoval removeFifo;
	removeFifo.transform(decoder);
}

int DecoderEngine::printNetwork(Network* network, string outputFile){
	LLVMUtility utility;
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}

	Decoder* decoder = it->second;
	utility.printModule(outputFile, decoder);
	return 0;
}
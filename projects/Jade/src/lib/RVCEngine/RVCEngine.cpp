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

//#include "llvm/Constants.h"
#include "llvm/Support/CommandLine.h"
#include "llvm/Support/StandardPasses.h"
#include "Jade/Util/FifoMng.h"
#include "Jade/Decoder.h"
#include "Jade/RVCEngine.h"
#include "Jade/Serialize/IRParser.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Port.h"
#include "Jade/Core/Network.h"
#include "Jade/Jit/LLVMUtility.h"
#include "Jade/Jit/LLVMOptimizer.h"
#include "Jade/Jit/LLVMExecution.h"
#include "Jade/lib_RVCDecoder/RVCDecoder.h" 
#include "Jade/Optimize/FifoFnRemoval.h"
#include "Jade/Optimize/InstanceInternalize.h"
#include "llvm/Support/PassNameParser.h"
//------------------------------

using namespace std;
using namespace llvm;

//extern cl::list<const PassInfo*, bool, PassNameParser> PassList;

RVCEngine::RVCEngine(llvm::LLVMContext& C,
							 string library, 
							 FifoTy fifo,
							 int defaultFifoSize,
							 string system, 
							 string outputDir, 
							 bool noMerging,
							 bool noMultiCore,
							 bool verbose): Context(C) {	
	//Set properties	
	this->library = library;
	this->systemPackage = system;
	this->verbose = verbose;
	this->fifoty = fifoty;
	this->noMerging = noMerging;
	this->outputDir = outputDir;
	this->noMultiCore = noMultiCore;

	//Select the fifo used
	FifoMng::setFifoTy(fifo, library, defaultFifoSize, outputDir);

	//Load IR Parser
	irParser = new IRParser(C, library);
}

RVCEngine::~RVCEngine(){
	
}

int RVCEngine::load(Network* network, int optLevel) {
	map<string, Actor*>::iterator it;
	clock_t timer = clock ();

	//Create the Configuration from the network
	Configuration* configuration = new Configuration(network, noMerging);

	// Parsing actor and bound it to the configuration
	map<string, Actor*>* requieredActors = parseActors(configuration);
	configuration->setActors(requieredActors);

	if (verbose){
		cout << "---> The given configuration requiered " << requieredActors->size() << " actors for "<<configuration->getInstances()->size() << " instances.\n";
		cout << "--> Modules parsed in : "<<(clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		timer = clock ();
	}
	
	//Create decoder
	Decoder* decoder = new Decoder(Context, configuration, verbose, noMultiCore);
	
	if (verbose){
		cout << "--> Decoder created in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		timer = clock ();
	}
	
	//doOptimizeDecoder(decoder);
	
	//Insert decoder into the list of created decoders
	decoders.insert(pair<Network*, Decoder*>(network, decoder));

	return 0;
}

int RVCEngine::unload(Network* network) {
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


int RVCEngine::stop(Network* network){
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

Decoder* RVCEngine::prepare(Network* network){
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		exit(1);
	}
	
	Decoder* decoder = it->second;

	LLVMExecution* llvmEE = decoder->getEE();
	llvmEE->initialize();

	return decoder;
}


int RVCEngine::verify(Network* network, std::string errorFile){
	map<Network*, Decoder*>::iterator it;
	clock_t	timer = clock ();
	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}
	
	LLVMUtility utility;
	utility.verify(errorFile, it->second);

	if (verbose){
		cout << "--> Decoder verified in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
	return 0;
}

int RVCEngine::run(Network* network, pthread_t* thread){	
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}

	Decoder* decoder = it->second;

	//Start decoding
	if (thread != NULL){
		decoder->runInThread(thread);
	} else {
		decoder->run();
	}

	return 0;
}

int RVCEngine::optimize(Network* network, int optLevel){
	clock_t	timer = clock ();
	
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(network);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}

	cout << "-> Start optimization of : " << network->getName().c_str() << "\n";

	LLVMOptimizer opt(it->second);
	opt.optimize(optLevel);
	
	cout << "--> Decoder optimized in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
}

int RVCEngine::reconfigure(Network* oldNetwork, Network* newNetwork){
	map<Network*, Decoder*>::iterator it;

	it = decoders.find(oldNetwork);

	if (it == decoders.end()){
		cout << "No decoders found for this network.\n";
		return 1;
	}

	Decoder* decoder = it->second;

	//Create the new Configuration
	Configuration* configuration = new Configuration(newNetwork);

	// Parsing actor and bound it to the new configuration
	map<string, Actor*>* requieredActors = parseActors(configuration);
	configuration->setActors(requieredActors);

	// Set the new configuration
	decoder->setConfiguration(configuration);

	//Set the new decoder
	decoders.erase(it);
	decoders.insert(pair<Network*, Decoder*>(newNetwork, decoder));

	return 0;
}

map<string, Actor*>* RVCEngine::parseActors(Configuration* Configuration) {
	list<string>::iterator it;
	
	//Resulting map of actors
	map<string, Actor*>* configurationActors = new map<string, Actor*>();

	//Get files requiered by the configuration
	list<string>* files = Configuration->getActorFiles();

	//Iterate though files and parses actors if requiered
	for ( it = files->begin(); it != files->end(); ++it ){
		map<string, Actor*>::iterator itAct;
		
		//Check if actor has been already parsed before
		Actor* actor = NULL;
		itAct = actors.find(*it);

		if(itAct == actors.end()){
			//Actor has not been parsed
			actor = irParser->parseActor(*it);
		}else{
			//Actor has been parsed
			actor = itAct->second;
		}
		
		//Set actors as requiered by the configuration
		configurationActors->insert(pair<string, Actor*>(*it, actor));
	}

	//Insert all actors into the list of all parsed actor by the decoder engine
	actors.insert(configurationActors->begin(), configurationActors->end());
	
	return configurationActors;
}

void RVCEngine::doOptimizeDecoder(Decoder* decoder){
//TODO : add CFGSimplification and mem2reg
	InstanceInternalize internalize;
	internalize.transform(decoder);
	
	PassManager Passes;
	Passes.add(createFunctionInliningPass());
	Passes.run(*decoder->getModule());

	FifoFnRemoval removeFifo;
	removeFifo.transform(decoder);
}

int RVCEngine::print(Network* network, string outputFile){
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
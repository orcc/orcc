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
@brief Implementation of class ConfigurationEngine
@author Jerome Gorin
@file ConfigurationEngine.cpp
@version 1.0
@date 18/01/2011
*/

//------------------------------
#include <map>
#include <iostream>

#include "Reconfiguration.h"
#include "Connector.h"
#include "Initializer.h"

#include "Jade/Decoder.h"
#include "Jade/Actor/BroadcastAdder.h"
#include "Jade/Configuration/ConfigurationEngine.h"
#include "Jade/Core/Actor.h"
#include "Jade/Merger/Merger.h"
#include "Jade/Scheduler/Scheduler.h"
#include "Jade/Serialize/IRLinker.h"
#include "Jade/Serialize/IRUnwriter.h"
#include "Jade/Serialize/IRWriter.h"
//------------------------------

using namespace std;
using namespace llvm;

ConfigurationEngine::ConfigurationEngine(llvm::LLVMContext& C, bool verbose) : Context(C){
	this->verbose = verbose;
}

void ConfigurationEngine::configure(Decoder* decoder){
	map<string, Instance*>::iterator it;
	Configuration* configuration = decoder->getConfiguration();

	// Adding broadcast 
	BroadcastAdder broadAdder(Context,configuration, decoder);
	broadAdder.transform();
/*
	//Merge static actors together if needed
	if (configuration->mergeActors()){
		Merger merger();
		merger.transform(configuration);
	}*/

	//Write instance
	IRWriter writer(Context, decoder);
	map<string, Instance*>* instances = configuration->getInstances();

	for (it = instances->begin(); it != instances->end(); it++){
		writer.write(it->second);
	}

	// Setting connections of the decoder
	Connector connector(Context, decoder);
	connector.setConnections(configuration);
}

void ConfigurationEngine::reconfigure(Decoder* decoder, Configuration* configuration){
	list<Instance*>::iterator it;
	
	//Clear connections of the decoder
	clearConnections(decoder);

	//Process reconfiguration scenario
	Reconfiguration reconfiguration(decoder, configuration, verbose);

	//Remove unused instances
	IRUnwriter unwriter(decoder);

	
	//Iterate though all instances to remove
	list<Instance*>* removes = reconfiguration.getToRemove();
	
	if (verbose){
		cout << "Detected " << removes->size() << " useless instance, remove them from current decoder. \n";
	}

	for (it = removes->begin(); it != removes->end(); it++){
		unwriter.remove(*it);
	}
	
	//Write new instances
	IRWriter writer(Context, decoder);

	// Adding new broadcast 
	BroadcastAdder broadAdder(Context, configuration, decoder);
	broadAdder.transform();
	list<Instance*>* broads = broadAdder.getBroads();
	for (it = broads->begin(); it != broads->end(); it++){
		writer.write(*it);
	}
	
	//Iterate though all instances to add
	list<Instance*>* adds = reconfiguration.getToAdd();

	if (verbose){
		cout << "Detected " << adds->size() << " instance to add. \n";
	}

	for (it = adds->begin(); it != adds->end(); it++){
		writer.write(*it);
	}

	//Link and initialize instances to keep from one configuration to another
	list<pair<Instance*, Instance*> >::iterator itKeep;
	list<pair<Instance*, Instance*> >* keeps = reconfiguration.getToKeep();
	
	if (verbose){
		cout << "Detected " << keeps->size() << " instance to keep, create links. \n";
	}

	IRLinker linker(decoder);
	linker.link(keeps);
	
	Initializer initializer(Context, decoder);
	for (itKeep = keeps->begin(); itKeep != keeps->end(); itKeep++){
		initializer.add((*itKeep).second);
	}
	
	initializer.initialize();

	// Setting connections of the decoder
	Connector connector(Context, decoder);
	connector.setConnections(configuration, decoder->getEE());
}

void ConfigurationEngine::reinit(Decoder* decoder){
	Initializer initializer(Context, decoder);
	Configuration* configuration = decoder->getConfiguration();
	
	//Remove connections
	Connector connector(Context, decoder);
	connector.unsetConnections(configuration);

	//Get all instances of the decoder
	map<string, Instance*>::iterator it;
	map<string, Instance*>* instances = configuration->getInstances();

	for (it = instances->begin(); it != instances->end(); it++){
		initializer.add(it->second);
	}
	
	initializer.initialize();

	// Reset connections of the decoder
	connector.setConnections(configuration, decoder->getEE());
}

void ConfigurationEngine::clearConnections(Decoder* decoder){
	//Retrieve orignal configuration from the decoder
	Configuration* configuration = decoder->getConfiguration();

	//Remove connections
	Connector connector(Context, decoder);
	connector.unsetConnections(configuration);

	//Unwrite broadcasts
	list<Actor*>::iterator itActor;
	IRUnwriter unwriter(decoder);
	list<Actor*>* specificActors = configuration->getSpecifics();

	for (itActor = specificActors->begin(); itActor != specificActors->end(); itActor++){
		list<Instance*>::iterator itInst;
		list<Instance*>* instances = (*itActor)->getInstances();

		for (itInst = instances->begin(); itInst != instances->end(); itInst++){
			unwriter.remove(*itInst);
		} 
	}
}



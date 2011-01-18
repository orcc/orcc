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

#include "Jade/Decoder.h"
#include "Jade/Configuration/ConfigurationEngine.h"
#include "Jade/Configuration/Instantiator.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Actor.h"
#include "Jade/Fifo/AbstractConnector.h"
#include "Jade/Transform/BroadcastAdder.h"
#include "Jade/Scheduler/Scheduler.h"
#include "Jade/Serialize/IRWriter.h"
#include "Jade/Transform/ActionSchedulerAdder.h"
//------------------------------

using namespace std;
using namespace llvm;

ConfigurationEngine::ConfigurationEngine(llvm::LLVMContext& C) : Context(C){
}

void ConfigurationEngine::configure(Decoder* decoder){
	map<string, Instance*>::iterator it;
	Configuration* configuration = decoder->getConfiguration();

	// Add Fifo function and fifo type into the decoder
	AbstractConnector* connector = configuration->getConnector();
	connector->addFifoHeader(decoder);
	
	// Instanciate the network
	Instantiator instantiator(configuration);

	// Adding broadcast 
	BroadcastAdder broadAdder(Context, decoder);
	broadAdder.transform();

	//Write instance
	map<string, Instance*>* instances = configuration->getInstances();

	for (it = instances->begin(); it != instances->end(); it++){
		IRWriter writer(it->second);
		writer.write(decoder);
	}

	//Adding action scheduler
	ActionSchedulerAdder actionSchedulerAdder(Context, decoder);
	actionSchedulerAdder.transform();

	// Setting connections of the decoder
	connector->setConnections(decoder);

	//Set the scheduler
	Scheduler* scheduler = decoder->getScheduler();
	scheduler->createScheduler(decoder);
}



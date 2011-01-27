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
@brief Implementation of class Scenario
@author Jerome Gorin
@file Scenario.cpp
@version 1.0
@date 26/01/2011
*/

//------------------------------
#include <iostream>
#include <pthread.h>

#include "llvm/LLVMContext.h"
#include "llvm/System/Alarm.h"

#include "Jade/XDFParser.h"
#include "Jade/DecoderEngine.h"
#include "Jade/Scenario/Manager.h"

#include "ScenarioParser.h"


//------------------------------

using namespace std;
using namespace llvm;

Manager::Manager(DecoderEngine* engine){
	this->engine = engine;
}

bool Manager::start(std::string scFile){
	// Parse the scenario file
	ScenarioParser parser(scFile);
	Scenario* scenario = parser.parse();

	if (scenario == NULL){
		cout << "Error of scenario parsing ";
		return false;
	}

	while (!scenario->end()){
		//Get a first event
		Event* curEvent = scenario->getEvent();

		//Execute event
		if (!startEvent(curEvent)){
			cerr << "Getting an while running events. \n ";
			return false;
		}
	}

	//Manager executed all events properly
	return true;
}

bool Manager::startEvent(Event* newEvent){
	if (newEvent->isStartEvent()){
		return runStartEvent((StartEvent*)newEvent);
	}else if (newEvent->isLoadEvent()){
		return runLoadEvent((LoadEvent*)newEvent);
	}else if (newEvent->isSetEvent()){
		return runSetEvent((SetEvent*)newEvent);
	}else if (newEvent->isStopEvent()){
		return runStopEvent((StopEvent*)newEvent);
	}else if (newEvent->isWaitEvent()){
		return runWaitEvent((WaitEvent*)newEvent);
	}else if (newEvent->isPauseEvent()){
		return runPauseEvent((PauseEvent*)newEvent);
	}else{
		cerr << "Unrecognize event. \n ";
		return false;
	}


	return true;
}

bool Manager::runLoadEvent(LoadEvent* loadEvent){
	//Load network
	LLVMContext &Context = getGlobalContext();
	XDFParser xdfParser(loadEvent->getFile());
	Network* network = xdfParser.ParseXDF(Context);

	if (network == NULL){
		cerr << "Event error ! No network load. \n";
		return false;
	}

	int id = loadEvent->getId();
	engine->load(network, 0);

	networks.insert(pair<int, Network*>(id, network));
	return true;
}

bool Manager::runStartEvent(StartEvent* startEvent){
	//Get network
	netPtr = networks.find(startEvent->getId());
	
	if(netPtr == networks.end()){
		cerr << "Event error ! No network loads at id " << startEvent->getId();
		return false;
	}
	
	//Start a thread if needed
	pthread_t* thread = NULL;
	if(startEvent->isThreaded()){
		thread = new pthread_t();
	}
	
	//Execute network
	engine->run(netPtr->second, startEvent->getInput(), thread);
	return true;
}

bool Manager::runWaitEvent(WaitEvent* waitEvent){
	sys::Sleep(waitEvent->getTime());
	return true;
}

bool Manager::runPauseEvent(PauseEvent* waitEvent){
	std::string crs;
	cout << "Scenario pause, press any key to continue. \n";
	cin >> crs;
	//getchar();
	return true;
}

bool Manager::runStopEvent(StopEvent* stopEvent){
	//Get network
	netPtr = networks.find(stopEvent->getId());
	
	if(netPtr == networks.end()){
		cerr << "Event error ! No network loads at the given id.\n";
		return false;
	}

	//Stop the given network
	engine->stop(netPtr->second);

	return true;
}

bool Manager::runSetEvent(SetEvent* setEvent){
	//Get the network
	int id = setEvent->getId();
	netPtr = networks.find(id);
	if(netPtr == networks.end()){
		cout << "Event error ! No network loads at the given id.\n";
		return false;
	}

	//Load network
	LLVMContext &Context = getGlobalContext();
	XDFParser xdfParser(setEvent->getFile());
	Network* network = xdfParser.ParseXDF(Context);

	if (network == NULL){
		cout << "No network load. \n";
		return false;
	}

	//Reconfiguration decoder
	engine->reconfigure(netPtr->second, network);

	//Set the new network
	networks.erase(id);
	networks.insert(pair<int, Network*>(id, network));

	return true;
}

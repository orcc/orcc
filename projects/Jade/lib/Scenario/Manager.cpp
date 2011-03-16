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

Manager::Manager(DecoderEngine* engine, bool verify, bool verbose){
	this->engine = engine;
	this->verbose = verbose;
	this->verify = verify;
}

bool Manager::start(std::string scFile){
	clock_t start = clock ();
	if (verbose){
		cout << "-> Parsing scenario file : "<<scFile.c_str() <<" \n";
	}

	// Parse the scenario file
	ScenarioParser parser(scFile);
	Scenario* scenario = parser.parse();

	if (scenario == NULL){
		cout << "Error of scenario parsing ";
		return false;
	}

	if (verbose){
		cout << "-> Scenario parsing finished in "<< (clock () - start) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		cout << "-> Start scenario :\n";
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
	}else if (newEvent->isVerifyEvent()){
		return runVerifyEvent((VerifyEvent*)newEvent);
	}else if (newEvent->isPrintEvent()){
		return runPrintEvent((PrintEvent*)newEvent);
	}else if (newEvent->isRemoveEvent()){
		return runRemoveEvent((RemoveEvent*)newEvent);
	}else if (newEvent->isListEvent()){
		return runListEvent((ListEvent*)newEvent);
	}else{
		cerr << "Unrecognize event. \n ";
		return false;
	}


	return true;
}

bool Manager::runLoadEvent(LoadEvent* loadEvent){
	clock_t timer1 = clock ();
	clock_t timer2 = clock ();
	
	if (verbose){
		cout << "-> Execute load event :\n";
		cout << "--> Parsing network :\n";
	}
	//Load network
	LLVMContext &Context = getGlobalContext();
	XDFParser xdfParser(loadEvent->getFile(), verbose);
	Network* network = xdfParser.ParseXDF(Context);

	if (network == NULL){
		cerr << "Event error ! No network load. \n";
		return false;
	}

	if (verbose){
		cout << "--> Parsing network finished in : "<< (clock () - timer1) * 1000 / CLOCKS_PER_SEC << "ms.\n";
		cout << "--> Loading decoder :\n";
		timer1 = clock ();
	}

	//Loading decoder
	int id = loadEvent->getId();
	engine->load(network, id);

	//Store resulting network
	networks.insert(pair<int, Network*>(id, network));

	if (verify){
		engine->verify(network, "error.txt");
	}
	
	if (verbose){
		cout << "--> Load decoder finished in : "<< (clock () - timer1) * 1000 / CLOCKS_PER_SEC << "ms.\n";
		cout << "-> Load event executed in :" << (clock () - timer2) * 1000 / CLOCKS_PER_SEC <<"\n";
	}
	return true;
}

bool Manager::runStartEvent(StartEvent* startEvent){
	clock_t timer = clock ();
	if (verbose){
		cout << "-> Execute start event :\n";
	}
	
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
	
	if (verbose){
		cout << "-> Decoder started in :"<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}

	return true;
}

bool Manager::runWaitEvent(WaitEvent* waitEvent){
	if (verbose){
		cout << "-> Execute wait event :\n";
	}
	sys::Sleep(waitEvent->getTime());

	if (verbose){
		cout << "-> Wait is event.\n";
	}
	return true;
}

bool Manager::runVerifyEvent(VerifyEvent* verifyEvent){
	clock_t timer = clock ();
	if (verbose){
		cout << "-> Execute verify event :\n";
	}
	
	//Get network
	netPtr = networks.find(verifyEvent->getId());
	
	if(netPtr == networks.end()){
		cerr << "Event error ! No network loads at id " << verifyEvent->getId();
		return false;
	}

	engine->verify(netPtr->second, verifyEvent->getFile());

	if (verbose){
		cout << "-> Decoder verified in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
	return true;
}

bool Manager::runPrintEvent(PrintEvent* printEvent){
	clock_t timer = clock ();
	if (verbose){
		cout << "-> Execute print event :\n";
	}
	//Get network
	netPtr = networks.find(printEvent->getId());
	
	if(netPtr == networks.end()){
		cerr << "Event error ! No network loads at id " << printEvent->getId();
		return false;
	}

	engine->print(netPtr->second, printEvent->getFile());

	if (verbose){
		cout << "-> Decoder printed in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}
	return true;
}

bool Manager::runPauseEvent(PauseEvent* waitEvent){
	if (verbose){
		cout << "-> Execute pause event :\n";
	}

	std::string crs;
	cout << "Scenario pause, press any key to continue. \n";
	cin >> crs;

	if (verbose){
		cout << "-> End of pause event.\n";
	}
	return true;
}

bool Manager::runStopEvent(StopEvent* stopEvent){
	clock_t timer = clock ();
	if (verbose){
		cout << "-> Execute stop event :\n";
	}
	
	//Get network
	netPtr = networks.find(stopEvent->getId());
	
	if(netPtr == networks.end()){
		cerr << "Event error ! No network loads at the given id.\n";
		return false;
	}

	//Stop the given network
	engine->stop(netPtr->second);

	if (verbose){
		cout << "-> Decoder stopped in : "<< (clock () - timer) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
	}

	return true;
}

bool Manager::runSetEvent(SetEvent* setEvent){
	clock_t timer1 = clock ();
	clock_t timer2 = clock ();
	if (verbose){
		cout << "-> Execute set event :\n";
	}
	
	//Get the network
	int id = setEvent->getId();
	netPtr = networks.find(id);
	if(netPtr == networks.end()){
		cout << "Event error ! No network loads at the given id.\n";
		return false;
	}

	if (verbose){
		cout << "--> Start parsing network :\n";
	}

	//Load network
	LLVMContext &Context = getGlobalContext();
	XDFParser xdfParser(setEvent->getFile(), verbose);
	Network* network = xdfParser.ParseXDF(Context);

	if (network == NULL){
		cout << "No network load. \n";
		return false;
	}

	if (verbose){
		cout << "--> Network parsed in : "<< (clock () - timer1) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		cout << "--> Reconfiguring decoder :\n";
		timer1 = clock();
	}

	//Reconfiguration decoder
	engine->reconfigure(netPtr->second, network);

	if (verbose){
		cout << "--> Decoder reconfigured in : "<< (clock () - timer1) * 1000 / CLOCKS_PER_SEC <<" ms.\n";
		cout << "--> Executed set event in "<< (clock () - timer2) * 1000 / CLOCKS_PER_SEC  << " ms.\n";
	}

	//Set the new network
	networks.erase(id);
	networks.insert(pair<int, Network*>(id, network));

	return true;
}

bool Manager::runRemoveEvent(RemoveEvent* removeEvent){
	clock_t timer = clock ();
	if (verbose){
		cout << "-> Execute remove event :\n";
	}

	//Get the network
	int id = removeEvent->getId();
	netPtr = networks.find(id);

	if(netPtr == networks.end()){
		cout << "Event error ! No network loads at the given id.\n";
		return false;
	}

	//Remove network
	engine->unload(netPtr->second);
	networks.erase(netPtr);
	delete netPtr->second;

	if (verbose){
		cout << "-> Remove event executed in :"<< (clock () - timer) * 1000 / CLOCKS_PER_SEC  << " ms.\n";
	}

	return true;
}

bool Manager::runListEvent(ListEvent* listEvent){
	if (verbose){
		cout << "-> Execute list event :\n";
	}

	map<int, Network*>::iterator it;
	string input;

	for (it = networks.begin(); it != networks.end(); it++){
		Network* network = it->second;
		cout << it->first << " : " << network->getName() << "\n";
	}

	return true;
}
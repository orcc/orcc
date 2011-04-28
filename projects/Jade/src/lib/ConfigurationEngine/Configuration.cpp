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
@brief Implementation of class Configuration
@author Jerome Gorin
@file Configuration.cpp
@version 1.0
@date 18/01/2011
*/

//------------------------------
#include "Instantiator.h"

#include "Jade/Configuration/Configuration.h"
#include "Jade/Core/Network.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Util/PackageMng.h"
//------------------------------

using namespace std;

Configuration::Configuration(Network* network, bool noMerging){
	this->network = network;
	this->noMerging = noMerging;

	//Set configuration property from network
	setInstances();
}

Configuration::~Configuration(){
	//Erase specific actors and instances
	eraseSpecifics();

	//Erase instances
	map<string, Instance*>::iterator itInst;
	for(itInst = instances.begin(); itInst != instances.end(); itInst++){
		Instance* instance = itInst->second;

		delete instance;
	}
}

void Configuration::update(){
	//Set configuration property from network
	setInstances();
}

void Configuration::setInstances(){
	instances.clear();
	
	// Create list of instance and actor
	HDAGGraph* graph = network->getGraph();
	int vertices = graph->getNbVertices();

	for (int i = 0; i < vertices; i++){
		Vertex* vertex = (Vertex*)graph->getVertex(i);

		if(vertex->isInstance()){
			Instance* instance = vertex->getInstance();
			instances.insert(pair<string,Instance*>(instance->getId(), instance));
			
			//Reference the configuration in the instance
			instance->setConfiguration(this);

			//Insert actor requiered for this network
			string clasz = instance->getClasz();
			//TODO : Support external
			if (clasz.compare("std.video.DisplayYUV") == 0){
				clasz = "System.Display";
				instance->setClasz(clasz);
			}

			if (clasz.compare("std.io.Source") == 0){
				clasz = "System.Source";
				instance->setClasz(clasz);
			}

			actorFiles.push_back(clasz);
		}
	}

	//remove duplicate actors
	actorFiles.sort();
	actorFiles.unique();
}

Actor* Configuration::getActor(std::string name){
	map<string, Actor*>::iterator it;

	it = actors->find(name);

	//Actor not found
	if(it == actors->end()){
		return NULL;
	}

	//Actor found
	return it->second;
}

void Configuration::setActors(std::map<std::string, Actor*>* actors){
	this->actors = actors;
	this->packages = PackageMng::setPackages(actors);

	// Instanciate the configuration
	Instantiator instantiator(this);
}

void Configuration::insertSpecific(Actor* actor){
	//Add all instance of the actor
	list<Instance*>::iterator it;
	list<Instance*>* specifInstances = actor->getInstances();
	for (it = specifInstances->begin(); it != specifInstances->end(); it++){
		Instance* instance = *it;
		instances.insert(pair<string, Instance*>(instance->getId(), instance));
	}

	//Insert actor
	specificActors.push_back(actor);
}

Instance* Configuration::getInstance(std::string name){
	map<string, Instance*>::iterator it;

	it = instances.find(name);

	if (it == instances.end()){
		return NULL;
	}

	return it->second;
}

list<Instance*> Configuration::getInstances(Actor* actor){
	list<Instance*>::iterator it;
	
	//Resulting list
	list<Instance*> result;

	//Loop other all instances of the actor
	list<Instance*>* childs = actor->getInstances();

	for (it = childs->begin(); it != childs->end(); it++){
		//If configuration of the instance correspond to this configuration
		if ((*it)->getConfiguration() == this){
			//Store into the resulting list
			result.push_back(*it);
		}
	}

	return result;
}

void Configuration::eraseSpecifics(){
	list<Actor*>::iterator it;

	for (it = specificActors.begin(); it != specificActors.end(); it++){
		list<Instance*>::iterator itChilds;
		
		list<Instance*>* childs = (*it)->getInstances();
		for (itChilds = childs->begin(); itChilds != childs->end(); itChilds++){
			map<string, Instance*>::iterator itChild;
			
			itChild = instances.find((*itChilds)->getId());
			
			if (itChild != instances.end()){
				instances.erase(itChild);
			}
		}
	}

	list<Actor*>::iterator itSpec;
	for(itSpec = specificActors.begin(); itSpec != specificActors.end(); itSpec++){
		delete (*itSpec);
	}
}
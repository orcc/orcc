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
@brief Implementation of class Reconfiguration
@author Jerome Gorin
@file Reconfiguration.cpp
@version 1.0
@date 18/01/2011
*/

//------------------------------
#include <algorithm>
#include <vector>

#include "Reconfiguration.h"

#include "Jade/Decoder.h"
//------------------------------

using namespace std;

Reconfiguration::Reconfiguration(Decoder* decoder, Configuration* configuration){
	//Set properties
	this->refConfiguration = decoder->getConfiguration();
	this->curConfiguration = configuration;

	//Compare packages and underneath actors between the two networks
	comparePackages(refConfiguration->getPackages(), curConfiguration->getPackages(), &removed, &intersect);
	comparePackages(curConfiguration->getPackages(), refConfiguration->getPackages(), &added);

	//Mark instance to process
	markInstances(&removed, &toRemove);
	markInstances(&added, &toAdd);

	//Couple similar instances
	detectInstances(&intersect, &toKeep);
}

void Reconfiguration::comparePackages(map<string, Package*>* ref,
									  map<string, Package*>* cur, 
									  map<string, Actor*>* diff,
									  map<string, Actor*>* intersect){
	map<string, Package*>::iterator itRef;

	//Iterate though the package of reference network
	for (itRef = ref->begin(); itRef != ref->end(); itRef++){
		map<string, Package*>::iterator itCur;
		Package* refPackage = itRef->second;
		string name = itRef->first;
		
		//Look for the same package in cur package
		itCur = cur->find(name);

		if(itCur == cur->end()){
			//Package does'nt exist in the current network, mark all actors as removed
			refPackage->getAllUnderneathActors(diff);
		}else{
			//Package exist in the current network, compares its actors
			Package* curPackage = itCur->second;
			compareActors(refPackage->getUnderneathActors(), 
						  curPackage->getUnderneathActors(),
						  diff, intersect);

			//Compare the child packages of the two networks
			comparePackages(refPackage->getChilds(), curPackage->getChilds(), diff, intersect);
		}
	}
}

void Reconfiguration::compareActors(map<string, Actor*>* ref, 
									map<string, Actor*>* cur,
									map<string, Actor*>* diff,
									map<string, Actor*>* intersect){
	map<string, Actor*>::iterator itRef;

	//Iterate though actors of the reference list
	for (itRef = ref->begin(); itRef != ref->end(); itRef++){
		map<string, Actor*>::iterator itCur;
		Actor* refActor = itRef->second;
		string name = itRef->first;
		
		//Look for the same actor in cur list
		itCur = cur->find(name);

		if(itCur == cur->end()){
			//Actor does'nt exist in the current list, mark actor as removed
			diff->insert(pair<string, Actor*>(name, refActor));
		}else if (intersect != NULL){
			//Actor does exist in the current list, mark actor as intersection if needed
			intersect->insert(pair<string, Actor*>(name, refActor));
		}
	}
}

void Reconfiguration::markInstances(map<string, Actor*>* actors, list<Instance*>* instances){
	map<string, Actor*>::iterator it;

	//Iterate though all marked actors
	for (it = actors->begin(); it != actors->end(); it++){

		//Get instances of the actor
		Actor* actor = it->second;
		list<Instance*>* childs = actor->getInstances();

		//Store instances into marked list
		list<Instance*>::iterator itInsert = instances->begin();
		instances->insert(itInsert, childs->begin(), childs->end());
	}
}

void Reconfiguration::detectInstances(map<string, Actor*>* actors, list<pair<Instance*, Instance*>>* instances){
	map<string, Actor*>::iterator it;

	//Iterate though all marked actors
	for (it = actors->begin(); it != actors->end(); it++){

		//Get instances of the actor
		Actor* actor = it->second;
		list<Instance*>* childs = actor->getInstances();



	}

}
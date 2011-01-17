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
@brief Implementation of class FifoFnRemoval
@author Jerome Gorin
@file FifoFnRemoval.cpp
@version 1.0
@date 24/12/2010
*/

//------------------------------
#include <algorithm>
#include <vector>

#include "Jade/Core/Network.h"
#include "Jade/Core/Package.h"
#include "Jade/Configuration/ReconfigurationScenario.h"
//------------------------------

using namespace std;

ReconfigurationScenario::ReconfigurationScenario(Network* refNetwork, Network* curNetwork){
	//Set properties
	this->refNetwork = refNetwork;
	this->curNetwork = curNetwork;

	//Compare packages and underneath actors between the two networks
	comparePackages(refNetwork->getPackages(), curNetwork->getPackages(), &removed, &intersect);
	comparePackages(curNetwork->getPackages(), refNetwork->getPackages(), &added);

}

void ReconfigurationScenario::comparePackages(map<string, Package*>* ref,
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

void ReconfigurationScenario::compareActors(map<string, Actor*>* ref, 
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
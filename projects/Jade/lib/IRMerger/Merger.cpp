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
@brief Implementation of class Merger
@author Jerome Gorin
@file Merger.cpp
@version 1.0
@date 24/12/2010
*/

//------------------------------
#include <map>
#include <list>

#include "Jade/Core/Network.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Merger/Merger.h"
#include "Jade/Merger/SuperInstance.h"
//------------------------------

using namespace std;

Merger::Merger(Configuration* configuration){
	network = configuration->getNetwork();
}

void Merger::transform(){
	bool hasCondidate = true;
		
	// Iterate though all vertices until no candidate left
	while(hasCondidate){
		list<Instance*>::iterator it;
		list<Instance*>* instances = network->getInstances();
		
		// First compute all successors in the network
		network->computeSuccessorsMaps();
		bool recompute = false;

		for (it = instances->begin(); it != instances->end(); it++){
			Instance* src = *it;
			Actor* srcActor = src->getActor();

			if (srcActor->getMoC()->isCSDF()){
				//Iterate though successors, try to find a static actor
				list<Instance*>::iterator itDst;
				list<Instance*> dsts = network->getSuccessorsOf(src);

				for (itDst = dsts.begin(); itDst !=  dsts.end(); itDst++){
					Instance* dst = *itDst;
					Actor* dstActor = dst->getActor();

					if (dstActor->getMoC()->isCSDF()){
						// These two actors can be merged
						mergeInstance(src, dst);

						// Recompute graph
						recompute = true;
						break;
					}
				}
			}
			
			if (recompute){
				break;
			}
		}
		
		if (!recompute){
			// No merging found, end the analysis
			hasCondidate = false;
		}
	}
}

void Merger::mergeInstance(Instance* src, Instance* dst){
	list<Connection*>* connection = network->getAllConnections(src, dst);

	SuperInstance* superInstance = new SuperInstance("merged", src, 1, dst, 1);

	network->removeInstance(src);
	network->removeInstance(dst);
	network->addInstance(superInstance);
}
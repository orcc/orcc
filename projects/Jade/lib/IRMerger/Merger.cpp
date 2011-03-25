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
#include <sstream>

#include "Rational.h"

#include "llvm/Constants.h"

#include "Jade/Core/Network.h"
#include "Jade/Core/MoC/CSDFMoC.h"
#include "Jade/Configuration/Configuration.h"
#include "Jade/Merger/Merger.h"
#include "Jade/Merger/SuperInstance.h"
//------------------------------

using namespace std;
using namespace llvm;

Merger::Merger(LLVMContext& C, Configuration* configuration): Context(C){
	// Set merger property
	this->configuration = configuration;
	this->index = 0;
	this->network = configuration->getNetwork();
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

	// Update configuration
	configuration->update();
}

void Merger::mergeInstance(Instance* src, Instance* dst){
	// Get all connections between the two instances
	list<Connection*>* connections = network->getAllConnections(src, dst);

	SuperInstance* superInstance =  getSuperInstance(src, dst, connections);
	
	updateConnections(connections, src, dst, superInstance);

	network->removeInstance(src);
	network->removeInstance(dst);
}

void Merger::updateConnections(list<Connection*>* connections, Instance* src, Instance* dst, SuperInstance* superInstance){
	// Add the new instance
	Vertex* vertex = network->addInstance(superInstance);

	// Remove internal connections
	list<Connection*>::iterator it;
	for (it = connections->begin(); it != connections->end(); it++){
		network->removeConnection(*it);
	}

	// Update input connections of source
	list<Connection*> srcIn = network->getInConnections(src);
	for (it = srcIn.begin(); it != srcIn.end(); it++){
		(*it)->setSink(vertex);
		
		// Add input port to superinstance
		superInstance->setAsInput((*it)->getDestinationPort());
	}

	// Update outputs connections of source
	list<Connection*> srcOut = network->getOutConnections(src);
	for (it = srcOut.begin(); it != srcOut.end(); it++){
		(*it)->setSource(vertex);

		// Add output port to superinstance
		superInstance->setAsOutput((*it)->getSourcePort());
	}

	// Update input connections of destination
	list<Connection*> dstIn = network->getInConnections(dst);
	for (it = dstIn.begin(); it != dstIn.end(); it++){
		(*it)->setSink(vertex);

		// Add input port to superinstance
		superInstance->setAsInput((*it)->getDestinationPort());
	}

	// Update output connections of destination
	list<Connection*> dstOut = network->getOutConnections(dst);
	for (it = dstOut.begin(); it != dstOut.end(); it++){
		(*it)->setSource(vertex);

		// Add output port to superinstance
		superInstance->setAsOutput((*it)->getSourcePort());
	}


}

SuperInstance*  Merger::getSuperInstance(Instance* src, Instance* dst, list<Connection*>* connections ){
	// Superinstance name
	stringstream id;
	id << "merger";
	id << index++;

	//Get property of instances
	Actor* srcAct = src->getActor();
	MoC* srcMoC = srcAct->getMoC();
	Actor* dstAct = dst->getActor();
	MoC* dstMoC = dstAct->getMoC();
	Pattern* srcPattern = ((CSDFMoC*)srcMoC)->getOutputPattern();
	Pattern* dstPattern = ((CSDFMoC*)dstMoC)->getInputPattern();
	
	map<Port*, Port*>* internPorts = new map<Port*, Port*>();

	// Calculate rate and set internal ports
	Rational rate;
	
	list<Connection*>::iterator it;
	for (it = connections->begin(); it != connections->end(); it++){
		Connection* connection = *it;
		
		// Get ports of the connection
		Port* src = connection->getSourcePort();
		Port* dst = connection->getDestinationPort();

		// Get corresponding port in actor
		Port* srcActPort = srcAct->getOutput(src->getName());
		Port* dstActPort = dstAct->getInput(dst->getName());
		
		// Verify that rate of the two instances are consistent
		Rational compareRate = getRational(srcPattern->getNumTokens(srcActPort), dstPattern->getNumTokens(dstActPort));
		if ( rate == 0){
			rate = compareRate;
		}else if (rate != compareRate){
			// This two instances can't be merged
			return NULL;
		}

		// Set internal ports of each instances
		internPorts->insert(pair<Port*, Port*>(src, dst));
	}

	
	return new SuperInstance(Context, id.str() , src, rate.numerator(), dst, rate.denominator(), internPorts);
}

Rational Merger::getRational(ConstantInt* srcProd, ConstantInt* dstCons){
	return Rational(dstCons->getLimitedValue(), srcProd->getLimitedValue());
}
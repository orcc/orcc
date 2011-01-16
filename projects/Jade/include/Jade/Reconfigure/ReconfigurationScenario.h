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
@brief Description of the ReconfigurationScenario class interface
@author Jerome Gorin
@file ReconfigurationScenario.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef RECONFIGURATIONSCENARIO_H
#define RECONFIGURATIONSCENARIO_H

class Network;
//------------------------------

/**
 * @brief  This class represents a scenario of reconfiguration
 * 
 * @author Jerome Gorin
 * 
 */
class ReconfigurationScenario{
public:
	/*!
     *  @brief Constructor
     *
	 * Creates a new scenario of reconfiguration.
	 *
	 * @param network : the original network to reconfigure
	 *
     */
	ReconfigurationScenario(Network* refNetwork, Network* curNetwork){
		this->refNetwork = refNetwork;
		this->curNetwork = curNetwork;
	};

	/*!
     *  @brief Compute the scenario
     *
	 * Compute the difference between two networks.
	 *
     */
	void compute();

private:
	Network* refNetwork;
	Network* curNetwork;

	std::list<std::string> actorDiff;
};

#endif
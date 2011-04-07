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
@brief Description of the CheckPinoRules class interface
@author Jerome Gorin
@file CheckPinoRules.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef CHECKPINORULES_H
#define CHECKPINORULES_H
#include <set>
#include <list>

class Instance;
class Network;
//------------------------------

/**
 * @brief  This class check pino rules before merging two instance.
 * 
 * @author Jerome Gorin
 * 
 */
class CheckPinoRules {
public:
	/**
	 * @brief Check if pino rules are not reach.
	 * 
	 * @param network : the network to check
	 */
	CheckPinoRules(Network* network);

	bool isValide(Instance* src, Instance* dst);

private:

	/**
	 * @brief Find path using breadth-first search
	 * 
	 * @param visited : a list of visited instance
	 *
	 * @param end : the end Instance
	 */
	void findPath(std::list<Instance*>* visited, Instance* end);

	/**
	 * @brief Add the given path to path found
	 * 
	 * @param visited : a list of visited instance
	 */
	void storePath(std::list<Instance*>* visited);

	/**
	 * @brief Look for dynamic cycle to the given instance
	 * 
	 * @return true if cycles are found, otherwise false
	 */
	bool checkCycle(Instance* instance);

	
	/**
	 * @brief Look for precedence in between the two instance to merge
	 * 
	 * @return true if precedence are found, otherwise false
	 */
	bool checkZeroDelay(Instance* src, Instance* dst);

	/** Instances to check */
	Instance* src;
	Instance* dst;

	/** The network to check */
	Network* network;

	/** Path found*/
	std::list<std::list<Instance*> > paths;
};


#endif

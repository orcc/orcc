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
class Package;
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
	ReconfigurationScenario(Network* refNetwork, Network* curNetwork);

private:
	
	/**
     *  @brief Compare two packages
	 *
	 *  Compare two package and returns the actors contained in package ref but not in package cur.
	 *    intersect defines the intersection between the two packages and can be NULL.
	 *
	 *	@param ref : the reference map of package
	 *
	 *	@param cur : the map of package to compare
	 *
	 *	@param diff : the resulting difference
	 *
	 *	@param intersect : the intersection between package, can be NULL.
     */
	void comparePackages(std::map<std::string, Package*>* ref, 
						 std::map<std::string, Package*>* cur, 
						 std::map<std::string, Actor*>* diff,
						 std::map<std::string, Actor*>* intersect = NULL);

	/**
     *  @brief Compare a list of actors
	 *
	 *  Compare two list of actors and returns the actors contained in list ref but not in list cur.
	 *    intersect defines the intersection between the two lists.
	 *
	 *	@param ref : the reference map of package
	 *
	 *	@param cur : the map of package to compare
	 *
	 *	@param diff : the resulting difference
	 *
	 *	@param intersect : the intersection between list, can be NULL.
     */
	void compareActors(std::map<std::string, Actor*>* ref, 
					   std::map<std::string, Actor*>* cur,
					   std::map<std::string, Actor*>* diff,
					   std::map<std::string, Actor*>* intersect);

	/** Reference network*/
	Network* refNetwork;
	
	/** New network*/
	Network* curNetwork;

	/** List of marked actors*/
	std::map<std::string, Actor*> removed;
	std::map<std::string, Actor*> added;
	std::map<std::string, Actor*> intersect;
};

#endif
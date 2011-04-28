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
@brief Description of the Merger class interface
@author Jerome Gorin
@file Merger.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef SUPERINSTANCE_H
#define SUPERINSTANCE_H

#include "Jade/Core/Network/Instance.h"
class CSDFMoC;
//------------------------------

/**
 * @brief  This class defines a SuperInstance. A SuperInstance is an instance 
 *   that contains two instances.
 * 
 * @author Jerome Gorin
 * 
 */
class SuperInstance : public Instance {
public:
	/**
     *  @brief Create a SuperInstance.
	 * 
	 * @param name : the SuperInstance name
	 *
	 * @param instances : the instances encapsulated by the SuperInstance
	 */
	SuperInstance(llvm::LLVMContext& C, std::string id, Instance* srcInstance, int srcFactor, Instance* dstInstance, int dstFactor, std::map<Port*, Port*>* internalPorts );

	~SuperInstance(){};

	bool isSuperInstance(){return true;};

	/**
     * @brief Return true if this instance has internal port
	 *
	 * @return true if this instance has internal port, otherwise false
     */
	bool hasInternalPort(){return true;};

	/**
     * @brief Get the internal state variable corresponding to a port
     *
	 * @param port : the internal Port 
	 *
	 * @return the corresponding state variables
     */
	StateVar* getInternalVar(Port* port);

	/**
     * @brief Get the internal state variables of the instance
	 *
	 * @return a list of state variable
     */
	virtual std::map<Port*, StateVar*>* getInternalVars(){return internalVars;};

	/**
     * @brief Get the internal connections of the instance
	 *
	 * @return a map of internal connection
     */
	virtual std::map<Port*, Port*>* getInternalConnections();

	/**
     * @brief Get the internal state variables of the instance
	 *
	 * @return a list of state variable
     */
	virtual void setInternalVars(std::map<Port*, StateVar*>* internalVars){this->internalVars = internalVars;};

	/**
     * @brief Get instances of the superinstance with their repition factor
	 *
	 * @return a map of instance with their repetition factor
     */
	std::map<Instance*, int>* getInstances(){return &instances;};

	/**
     *  @brief Get the MoC of the super instance
     *
	 * @return MoC of the super Instance
     */
	MoC* getMoC();

private:

	/*!
     *  @brief Create a composite actor of the SuperInstance.
	 *
	 *  @param internalPorts : a map of internal ports
	 *
	 *  @return the resulting Actor
	 */
	Actor* createCompositeActor(std::map<Port*, Port*>* internalPorts);

	/*!
     *  @brief Create a moc of the composite actor.
	 *
	 * @return the resulting moc
	 */
	CSDFMoC* createMoC(CSDFMoC* srcMoc, int srcFactor, CSDFMoC* dstMoc, int dstFactor, std::set<Port*>* in = NULL, std::set<Port*>* out = NULL);

	/**
     *  @brief Create a pattern of the composite actor.
	 *
	 * Merge two patterns of actors according to the repetition factor
	 *
	 * @param srcPattern : the source Pattern
	 *
	 * @param srcFactor : the repetition factor of source pattern
	 *
	 * @param dstPattern : the destination Pattern
	 *
	 * @param dstFactor : the repetition factor of destination pattern
	 *
	 * @return the resulting pattern
	 */
	Pattern* createPattern(Pattern* srcPattern,  int srcFactor, Pattern* dstPattern, int dstFactor, std::set<Port*>* ports);

	/**
     *  @brief Create ports of the composite actor.
	 *
	 * @param portSet : a set of port
	 *
	 * @return the corresponding port map
	 */
	std::map<std::string, Port*>* createPorts(std::set<Port*>* portSet);

	void analyzeInstance(Instance* instance, int factor);

	/** Instance source */
	Instance* srcInstance;
	
	/** Repetition of source instance*/
	int srcFactor;
	
	/** Internal port of instance */
	std::map<Port*, StateVar*>* internalVars;

	/** Destination instance */
	Instance* dstInstance;
	
	/** Instance of the Superinstance */
	std::map<Instance*, int> instances;

	/** Repetition of destination instance*/
	int dstFactor;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Internal ports of the Super interface */
	std::map<Port*, Port*>* internalPorts;
};

#endif
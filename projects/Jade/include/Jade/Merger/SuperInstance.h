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
	/*!
     *  @brief Create a SuperInstance.
	 * 
	 * @param name : the SuperInstance name
	 *
	 * @param instances : the instances encapsulated by the SuperInstance
	 */
	SuperInstance(llvm::LLVMContext& C, std::string id, Instance* srcInstance, std::list<Port*>* intSrcPorts, int srcFactor, Instance* dstInstance, std::list<Port*>* intDstPorts, int dstFactor);

	~SuperInstance(){};

	bool isSuperInstance(){return true;};
private:

	/*!
     *  @brief Create a composite actor of the SuperInstance.
	 *
	 * @return the resulting Actor
	 */
	Actor* createCompositeActor();

	/*!
     *  @brief Create a moc of the composite actor.
	 *
	 * @return the resulting moc
	 */
	CSDFMoC* createMoC(Actor* srcActor, int srcFactor, Actor* dstActor, int dstFactor);

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
	Pattern* createPattern(Pattern* srcPattern,  int srcFactor, Pattern* dstPattern, int dstFactor);

	/**
     *  @brief Create ports of the composite actor.
	 *
	 * @param portSet : a set of port
	 *
	 * @return the corresponding port map
	 */
	std::map<std::string, Port*>* createPorts(std::set<Port*>* portSet);

	/**
     *  @brief Filter the given pattern with internal ports
	 *
	 * @param pattern : the Pattern to filter
	 *
	 * @param actor : the actor from which pattern as to be filter
	 *
	 * @param intPorts : the internal ports
	 *
	 */
	void filterPattern(Pattern* pattern,  Actor* actor, std::list<Port*>* intPorts);

	/**
     *  @brief Add a map of state variables to another
	 *
	 * @param src : the source map
	 *
	 * @param dst : the destination map
	 *
	 */
	void addStateVars(std::map<std::string, StateVar*>* src, std::map<std::string, StateVar*>* dst);


	/**
     *  @brief Add a map of paramters to another
	 *
	 * @param src : the source map
	 *
	 * @param dst : the destination map
	 *
	 */
	void addParameters(std::map<std::string, Variable*>* src, std::map<std::string, Variable*>* dst);

	/**
     *  @brief Add a map of procedures to another
	 *
	 * @param src : the source map
	 *
	 * @param dst : the destination map
	 *
	 */
	void addProcedures(std::map<std::string, Procedure*>* src, std::map<std::string, Procedure*>* dst);

	/**
     *  @brief Add a list of Action to another
	 *
	 * @param src : the source list
	 *
	 * @param dst : the destination list
	 *
	 */
	void addActions(std::list<Action*>* src, std::list<Action*>* dst);

	/** Instance source */
	Instance* srcInstance;
	
	/** Repetition of source instance*/
	int srcFactor;
	
	/** Internal port of source instance */
	std::list<Port*>* intSrcPorts;

	/** Internal port of destination instance */
	std::list<Port*>* intDstPorts;

	/** Destination instance */
	Instance* dstInstance;
	
	/** Repetition of destination instance*/
	int dstFactor;

	/** LLVM Context */
	llvm::LLVMContext &Context;
};

#endif
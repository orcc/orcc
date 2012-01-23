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
@brief Description of the CSDFMoC class interface
@author Jerome Gorin
@file CSDFMoC.h
@version 1.0
@date 17/03/2011
*/

//------------------------------
#ifndef CSDFMOC_H
#define CSDFMOC_H

#include <list>

#include "Jade/Core/MoC.h"

class Action;
class Pattern;
//------------------------------

/**
 * @class CSDFMoC
 *
 * @brief  This class defines a CSDF.
 *
 * This class defines the CSDF MoC. A CSDF actor has a sequence of fixed
 * production/consumption rates.
 * 
 * @author Jerome Gorin
 * 
 */
class CSDFMoC : public MoC {
public:
	CSDFMoC(Entity* parent) : MoC(parent){};
	~CSDFMoC(){};

	/**
	 * @brief Returns true if this MoC is CSDF.
	 * 
	 * @return true if this MoC is CSDF
	 */
	bool isCSDF(){return true;};

	/**
	 * @brief Add action to the MoC.
	 *
	 * Adds the given action to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action : an Action
	 */
	void addAction(Action* action) {
		actions.push_back(action);
	}

	/**
	 * @brief Add a list of actions to the MoC.
	 *
	 * Adds the given actions to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action : a list of Action
	 */
	void addActions(std::list<Action*>* actions) {
		std::list<Action*>::iterator it;
		for (it = actions->begin(); it != actions->end(); it++){
			addAction(*it);
		}
	}

	/**
	 * @brief Returns the input pattern of this CSDF MoC.
	 * 
	 * @return the input pattern of this CSDF MoC
	 */
	Pattern* getInputPattern() {
		return inputPattern;
	}

	/**
	 * @brief Returns the output pattern of this CSDF MoC.
	 * 
	 * @return the output pattern of this CSDF MoC
	 */
	Pattern* getOutputPattern() {
		return outputPattern;
	}

	/**
	 * @brief Returns the number of phases of this CSDF MoC.
	 * 
	 * @return the number of phases of this CSDF MoC
	 */
	int getNumberOfPhases() {
		return numberOfPhases;
	}

	/**
	 * @brief Set the number of phases of this CSDF MoC.
	 * 
	 * @param numberOfPhases : the number of phases of this CSDF MoC
	 */
	void setNumberOfPhases(int numberOfPhases) {
		this->numberOfPhases = numberOfPhases;
	}

	/**
	 * @brief Set the output pattern of this CSDF MoC.
	 * 
	 * @param pattern : the output Pattern
	 */
	void setOutputPattern(Pattern* pattern) {
		this->outputPattern = pattern;
	}

	/**
	 * @brief Set the output pattern of this CSDF MoC.
	 * 
	 * @param pattern : the input Pattern
	 */
	void setInputPattern(Pattern* pattern) {
		this->inputPattern = pattern;
	}
	
	/**
	 * @return Get the actions of the CSDFMoC
	 *
	 * Returns the list of actions that can be scheduled statically.
	 * 
	 * @return the list of actions that can be scheduled statically
	 */
	std::list<Action*>* getActions() {
		return &actions;
	}

protected:
	/** Sequence of actions */
	std::list<Action*> actions;

	/** Input pattern of the MoC */
	Pattern* inputPattern;

	/** Number of phases of the MoC */
	int numberOfPhases;

	/** Output pattern of the MoC */
	Pattern* outputPattern;
};

#endif

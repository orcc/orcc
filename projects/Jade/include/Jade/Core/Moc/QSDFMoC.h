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
@brief Description of the QSDFMoC class interface
@author Jerome Gorin
@file QSDFMoC.h
@version 1.0
@date 17/03/2011
*/

//------------------------------
#ifndef QSDFMOC_H
#define QSDFMOC_H

#include "Jade/Core/MoC/CSDFMoC.h"
//------------------------------

/**
 * @class QSDFMoC
 *
 * @brief  This class defines a QSDF MoC.
 *
 * This class defines a quasi-static dataflow (QSDF) MoC. QSDF is a model that
 * exhibits static behavior for a given configuration. An actor that has the
 * quasi-static class has one action for each configuration, therefore this
 * class associates one action with one static class.
 * 
 * @author Jerome Gorin
 * 
 */
class QSDFMoC : public MoC {
public:

	/**
	 * @brief Creates a new DPN MoC.
	 */
	QSDFMoC(){
	};
	
	~QSDFMoC(){};

	/**
	 * @brief Returns true if this MoC is QCSDF.
	 * 
	 * @return true if this MoC is QCSDF
	 */
	bool isQuasiStatic(){return true;};

	/**
	 * @brief Returns true if this MoC is QCSDF.
	 *
	 * Adds a configuration to this quasi-static MoC. A configuration is given
	 * by an action and associated with a SDF MoC.
	 * 
	 * @param action : a configuration action
	 * @param moc : a CSDF MoC
	 */
	void addConfiguration(Action* action, CSDFMoC* moc) {
		configurations.insert(std::pair<Action*, CSDFMoC*>(action, moc));
	}


	/**
	 * @brief Return the configurations of this quasi-static MoC.
	 * 
	 * @return a map of configurations
	 */
	std::map<Action*, CSDFMoC*>* getConfigurations() {
		return &configurations;
	}
private:
	std::map<Action*, CSDFMoC*> configurations;

};

#endif
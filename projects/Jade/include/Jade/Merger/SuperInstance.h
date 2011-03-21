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
	SuperInstance(std::string id, Instance* srcInstance, int srcFactor, Instance* dstInstance, int dstFactor);

	~SuperInstance(){};

	bool isSuperInstance(){return true;};
private:

	/*!
     *  @brief Create a composite actor of the SuperInstance.
	 *
	 * @return the resulting Actor
	 */
	Actor* createCompositeActor();

	Instance* srcInstance;
	int srcFactor;
	Instance* dstInstance;
	int dstFactor;
};

#endif
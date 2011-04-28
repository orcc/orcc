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
@brief Description of the Parent class interface
@author Jerome Gorin
@file Parent.h
@version 1.0
@date 25/03/2011
*/

//------------------------------
#ifndef PARENT_H
#define PARENT_H

//------------------------------


/**
 * @class Parent
 *
 * @brief  This class defines a Parent
 *
 * This class represents Parent, which can be an instance, an actor or a network. This class
 *  is can be used to go though the hierarchy of an actor
 * 
 * @author Jerome Gorin
 * 
 */

class Parent {
public:

	/**
     *  @brief Return true if this class is an instance
	 *
	 *  @return Return true if this class is an instance, otherwise false
     */
	virtual bool isInstance(){return false;};

	/**
     *  @brief Return true if this class is an actor
	 *
	 *  @return Return true if this class is an actor, otherwise false
     */
	virtual bool isActor(){return false;};
	
	/**
     *  @brief Return true if this class is a network
	 *
	 *  @return Return true if this class is an network, otherwise false
     */
	virtual bool isNetwork(){return false;};
};
#endif
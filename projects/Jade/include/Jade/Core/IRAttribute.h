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
@brief Description of the IRAttribute
@author Jerome Gorin
@file IRAttribute.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef IRAttribute_H
#define IRAttribute_H
//------------------------------

/**
 * @class IRAttribute
 *
 * @brief  This class defines a IRAttribute in a network
 *
 * This interface represents an abstract IRAttribute that can be put on instances or
 * connections in a network.
 * 
 * @author Jerome Gorin
 * 
 */
class IRAttribute{
public:

	/*!
     *  @brief Constructor
     *
	 * Creates a new abstract IRAttribute
     */
	IRAttribute(){};
	~IRAttribute(){};

	/**
	 * @brief Returns true if this type is custom IRAttribute.
	 * 
	 * @return true if this type is custom IRAttribute
	 */
	virtual bool isCustom(){return false;};

	/**
	 * @brief Returns true if this type is a string IRAttribute.
	 * 
	 * @return true if this type is a string IRAttribute
	 */
	virtual bool isString(){return false;};

	/**
	 * @brief Returns true if this type is a type IRAttribute.
	 * 
	 * @return true if this type is a type IRAttribute
	 */
	virtual bool isType(){return false;};

	/**
	 * @brief Returns true if this type is a value IRAttribute.
	 * 
	 * @return true if this type is a value IRAttribute
	 */
	virtual bool isValue(){return false;};
};

#endif
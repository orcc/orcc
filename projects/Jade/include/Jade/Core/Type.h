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
@brief Description of the Type class interface
@author Jerome Gorin
@file Type.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef TYPE_H
#define TYPE_H

class Expr;

#define INT_SIZE 32
//------------------------------

namespace ir
{
/**
 * @class Type
 *
 * @brief  This class defines a Type
 *
 * This class represents an abstract Type in a network. This class
 * intend to be inherit by class corresponding to specific kind 
 * of Type.
 * 
 * @author Jerome Gorin
 * 
 */
class Type {
public:

	/*!
     *  @brief Constructor
     *
	 * Creates a new abstract Type
     *
     */
	Type(){};
	~Type(){};


	/**
	 * @brief Returns true if this type is <tt>bool</tt>.
	 * 
	 * @return true if this type is <tt>bool</tt>
	 */
	virtual bool isBoolType(){return false;};

	/**
	 * @brief Returns true if this type is <tt>int</tt>.
	 * 
	 * @return true if this type is <tt>int</tt>
	 */
	virtual bool isIntType(){return false;};

	/**
	 * @brief Returns true if this type is <tt>List</tt>.
	 * 
	 * @return true if this type is <tt>List</tt>
	 */
	virtual bool isListType(){return false;};

	/**
	 * @brief Returns true if this type is <tt>String</tt>.
	 * 
	 * @return true if this type is <tt>String</tt>
	 */
	virtual bool isStringType(){return false;};

	/**
	 * @brief Returns true if this type is <tt>uint</tt>.
	 * 
	 * @return true if this type is <tt>uint</tt>
	 */
	virtual bool isUintType(){return false;};

	/**
	 * @brief Returns true if this type is <tt>void</tt>.
	 * 
	 * @return true if this type is <tt>void</tt>
	 */
	virtual bool isVoidType(){return false;};
};
}
#endif
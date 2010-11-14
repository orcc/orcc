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
@brief Description of the ValueIRAttribute
@author Jerome Gorin
@file ValueIRAttribute.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef VALUEIRAttribute_H
#define VALUEIRAttribute_H

namespace llvm{
	class Constant;
}

#include "Jade/Core/IRAttribute.h"
#include "Jade/Core/Expression.h"
//------------------------------

/**
 * @class IRAttribute
 *
 * @brief  This class defines a ValueIRAttribute
 *
 * This interface represents an IRAttribute with Expression value.
 * 
 * @author Jerome Gorin
 * 
 */
class ValueAttribute : public IRAttribute{
public:

	/*!
     *  @brief Constructor
     *
	 * Creates a new type IRAttribute
     */
	ValueAttribute(Expr* value){this->value = value;};
	~ValueAttribute();

	/**
	 * @brief Getter of value.
	 * 
	 * @return Value of the IRAttribute
	 */
	Expr* getValue(){return value;};

	/**
	 * @brief Returns true if this type is a value IRAttribute.
	 * 
	 * @return true if this type is a value IRAttribute
	 */
	bool isValue(){return true;};

private:
	Expr* value;
};

#endif
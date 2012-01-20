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
@brief Description of the TypeEntry class interface
@author Jerome Gorin
@file ExprType.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef EXPRENTRY_H
#define EXPRENTRY_H

#include "Jade/Core/Entry.h"
#include "Jade/Core/Expression.h"

namespace llvm{
	class Constant;
}
//------------------------------

/**
 * @class ExprEntry
 *
 * @brief This class defines a expression entry.
 *
 * @author Jerome Gorin
 * 
 */
class ExprEntry : public Entry {
public:

	/*!
     *  @brief Constructor
     *
	 * Creates an expression entry.
	 *
     */
	ExprEntry(Expr* expr){this->expr = expr;};
	~ExprEntry();

	/**
	 * @brief Returns true if this entry is type of ExprEntry.
	 * 
	 * @return true if this type is ExprEntry
	 */
	bool isExprEntry(){return true;};

	/**
	 * @brief Getter of expr.
	 * 
	 * @return Expr of the Entry
	 */
	Expr* getExprEntry(){return expr;};

private:
	Expr* expr;

};

#endif
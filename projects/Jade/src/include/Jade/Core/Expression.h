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
@brief Description of the Expr class interface
@author Jerome Gorin
@file Expression.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef EXPRESSION_H
#define EXPRESSION_H

namespace llvm{
	class Constant;
}

#include "llvm/LLVMContext.h"

#include "Jade/Core/IRType.h"
//------------------------------

/**
 * @class Expr
 *
 * @brief  This class defines an Expression
 *
 * This class represents an abstract Expression in a network. This class
 * intend to be inherit by class corresponding to specific kind 
 * of Expression.
 * 
 * @author Jerome Gorin
 * 
 */
class Expr {
public:

	/*!
     *  @brief Constructor
     *
	 * Creates a new abstract expression
     *
	 * @param C : llvm::Context of the expression
     */
	Expr(llvm::LLVMContext &C): Context(C){};
	~Expr(){};

	/**
	 * @brief Returns IRType corresponding to the type of this expression.
	 * 
	 * @return IRType of this expression
	 */
	virtual IRType* getIRType() = 0;

	/**
	 * @brief Returns llvm::Constant corresponding to the llvm value of this expression.
	 * 
	 * @return llvm::Constant of this expression
	 */
	virtual llvm::Constant* getConstant() = 0;


	/**
	 * @brief Returns true if the expression is an instance of BinaryExpr
	 * 
	 * @return True if the expression is an instance of BinaryExpr
	 */
	virtual bool isBinaryExpr(){return false;};

	/**
	 * @brief Returns true if the expression is an instance of BooleanExpr
	 * 
	 * @return True if the expression is an instance of BooleanExpr
	 */
	virtual bool isBooleanExpr(){return false;};

	/**
	 * @brief Returns true if the expression is an instance of IntExpr
	 * 
	 * @return True if the expression is an instance of IntExpr
	 */
	virtual bool isIntExpr(){return false;};

	/**
	 * @brief Returns true if the expression is an instance of ListExpr
	 * 
	 * @return True if the expression is an instance of ListExpr
	 */
	virtual bool isListExpr(){return false;};

	/**
	 * @brief Returns true if the expression is an instance of StringExpr
	 * 
	 * @return True if the expression is an instance of StringExpr
	 */
	virtual bool isStringExpr(){return false;};

	/**
	 * @brief Returns true if the expression is an instance of UnaryExpr
	 * 
	 * @return True if the expression is an instance of UnaryExpr
	 */
	virtual bool isUnaryExpr(){return false;};

	/**
	 * @brief Returns true if the expression is an instance of VarExpr
	 * 
	 * @return True if the expression is an instance of VarExpr
	 */
	virtual bool isVarExpr(){return false;};

	/**
	 * @brief evalue this expression as an integer
	 *
	 * Evaluates this expression and return its value as an integer.
	 * 
	 * @return the expression evaluated as an integer
	 */
	int evaluateAsInteger();

protected:
	/** LLVM Context */
	llvm::LLVMContext &Context;

};

#endif
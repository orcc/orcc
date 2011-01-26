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
@brief Description of the ListExpr class interface
@author Jerome Gorin
@file ListExpr.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef LISTEXPR_H
#define LISTEXPR_H

#include <list>

#include "Jade/Core/Expression.h"
//------------------------------

/**
 * @class ListExpr
 *
 * @brief  This class defines a list of Expression.
 * 
 * @author Jerome Gorin
 * 
 */

class ListExpr : public Expr {
public:
	/*!
     *  @brief Constructor
     *
	 * Creates a new integer expression with an int value.
	 *
	 *  @param C : llvm::LLVMContext.
	 *
	 *  @param value : integer value of the IntExpr.
     *
     */
	ListExpr(llvm::LLVMContext &C, std::list<Expr*>* value) : Expr(C){
		this->expressions = value;
	};

	/*!
     *  @brief Constructor
     *
	 * Creates a new list expression with a ConstantArray value.
	 *
	 *  @param C : llvm::LLVMContext.
	 *
	 *  @param values : llvm::ConstantArray value of the ListExpr.
     *
     */
	ListExpr(llvm::LLVMContext &C, llvm::ConstantArray* values) : Expr(C){
		this->constantArray = values;
	};

	~ListExpr();

	/*!
     *  @brief Return IRType of the list expression
     *
	 *  @return IRType of the list expression.
     *
     */
	IRType* getIRType(){
		//Todo : implement ListType	
		return NULL;
	};

	/*!
     *  @brief Getter of expression value
     *
	 *  @return value of the expression.
     *
     */
	std::list<Expr*>* getValue(){return expressions;};

	/**
	 * @brief Returns llvm::Constant corresponding to the llvm value of this expression.
	 * 
	 * @return llvm::Constant of this expression
	 */
	llvm::Constant* getConstant();

	/**
	 * @brief Returns true if the expression is an instance of ListExpr
	 * 
	 * @return True if the expression is an instance of ListExpr
	 */
	bool isListExpr(){return true;};

private:
	/** Value of IntExpr */
	std::list<Expr*>* expressions;
	
	llvm::Constant* constantArray;
};

#endif
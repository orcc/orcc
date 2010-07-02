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
@brief Description of the BinaryExpr class interface
@author Jerome Gorin
@file BinaryExpr.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef BINARYEXPR_H
#define BINARYEXPR_H
#include "Jade/Type/Type.h"

#include "Expression.h"
#include "BinaryOp.h"
//------------------------------

/**
 * @class BinaryExpr
 *
 * @brief  This class defines an binary expression.
 *
 * This class represents an binary Expression in a network.
 * 
 * @author Jerome Gorin
 * 
 */

class BinaryExpr : public Expr {
public:
	
	/**
     *  @brief Constructor of the class BinaryExpr
     *
     *  @param	e1 : first Expr of binary expression
	 *  @param	op : BinaryOp of the binary expression
	 *  @param	e2 : second Expr of binary expression
	 *
     */
	BinaryExpr(Expr* e1, BinaryOp* op, Expr* e2){this->e1 = e1;this->op = op;this->e2 = e2;};
	~BinaryExpr();

	/*!
     *  @brief Return ir::Type of the binary expression
     *
	 *  @return ir::Type of the binary expression.
     *
     */
	ir::Type* getType(){return type;};

private:
	Expr* e1;
	Expr* e2;
	BinaryOp* op;
	ir::Type* type;
};

#endif
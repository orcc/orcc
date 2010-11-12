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

/*!
 * @file ExprParser.h
 * @brief Interface of ExprParser
 * @author Jerome Gorin
 * @version 0.1
 */

//------------------------------
#ifndef EXPRPARSER_H
#define EXPRPARSER_H

#include <stdio.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include "Jade/Core/Expression.h"
#include "Jade/Core/Expr/BinaryOp.h"
#include "Jade/TinyXML/TinyXml.h"

#include "XDFConstant.h"
#include "ParseContinuation.h"

namespace llvm{
	class Constant;
	class ConstantInt;
	class LLVMContext;
}
//------------------------------

/*!
* \class ExprParser
* @brief This class defines a parser of XDF Expression.
*/
class ExprParser {

public:

	/*!
     *  @brief Constructor
     *
     *  Constructor of the class ExprParser
     *
     */
	ExprParser (llvm::LLVMContext& C);

	/*!
     *  @brief Destructor
     *
     *  Destructor of the class ExprParser
     */
	~ExprParser ();
	
	
	/*!
     *  @brief Parses the given TiXmlNode as an Expression.
     *
     *  Parses the given node as an Expression and returns the matching
	 *  Expression.
	 *
	 *  @param node : TiXmlNode representation of an Expression.
	 *
	 *  @return  an Expression.
     */
	Expr* parseExpr(TiXmlNode* node);

private:
	/*!
     *  @brief Parses the given TiXmlNode as an Expression.
     *
     *  Parses the given node as an Expression and returns the matching
	 *  Expression.
	 *
	 *  @param element : TiXmlNode representation of an Expression.
	 *
	 *  @return  an Expression.
     */
	ParseContinuation<Expr*> parseExprCont(TiXmlNode* node);

	/*!
     *  @brief Parses the given TiXmlElement as a literal.
     *
	 * Parses the given TiXmlElement as a literal and returns the matching
	 * Expression.
	 *
	 *  @param element : TiXmlElement representation of an Expression.
	 *
	 *  @return  an Expression.
     */
	Expr* parseExprLiteral(TiXmlElement* elt);

	/*!
     *  @brief Parses the given TiXmlNode as a binary operation.
     *
	 * Parses the given element as a sequence of binary
	 * operations, aka "BinOpSeq". A BinOpSeq is a sequence of expr, op,
	 * expr, op, expr...
	 *
	 *  @param element : TiXmlNode representation of a BinaryExpr.
	 *
	 *  @return  an expression.
     */
	ParseContinuation<Expr*> parseExprBinOpSeq(TiXmlNode* node);

	/*!
     *  @brief Parses the given node as a binary operator.
     *
	 * Parses the given node as a binary operator and returns a parse
	 * continuation with the operator parsed.
	 *
	 *  @param element : TiXmlNode representation of a Binary Operation.
	 *
	 *  @return a parse continuation with the operator parsed
     */
	ParseContinuation<BinaryOp*> parseExprBinaryOp(TiXmlNode* node);

	/*!
     *  @brief Parses the given TiXmlString as a boolean constant.
     *
	 * Parses the given TiXmlString as a boolean constant and returns the corresponding 
	 *  llvm::constantInt value.
	 *
	 *  @param value : TiXmlString to parse.
	 *
	 *  @return  the corresponding BoolExpr value.
     */
	Expr* parseBoolean(TiXmlString value);

	/** LLVM Context */
	llvm::LLVMContext &Context;
};

#endif

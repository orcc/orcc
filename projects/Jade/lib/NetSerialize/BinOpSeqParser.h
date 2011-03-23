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
@brief Description of the BinOpSeqParser class interface
@author Jerome Gorin
@file IRParser.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef BINOPSEQPARSER_H
#define BINOPSEQPARSER_H

#include <list>

#include "llvm/LLVMContext.h"

#include "Jade/Core/Expression.h"
#include "Jade/Core/Expr/BinaryOp.h"
//------------------------------


/**
 * @brief This class defines a parser of binary operation sequences.
 *
 * This class defines a parser of binary operation sequences. This parser
 * translates expressions such as "e(1) op(1) e(2) ... op(n-1) e(n)" to a binary
 * expression tree with respect to operator precedence.
 * 
 * @author Jérôme Gorin
 * 
 */
class BinOpSeqParser {
public:
	/**
	 * @brief Parses a sequence of expressions and binary operators to a binary
	 * expression tree.
	 * 
	 * @param exprs : a list of expressions
	 * @param ops : a list of binary operators
	 *
	 * @return a binary expression tree
	 */
	static Expr* parse(llvm::LLVMContext &C, std::list<Expr*>* exprs, std::list<BinaryOp*>* ops);

private:
	/**
	 * @brief Returns the index of the pivot.
	 *
	 * Returns the index of the pivot, which is the operator that has the
	 * highest precedence between start index and stop index. The pivot is
	 * therefore the operator that binds the least with its operands.
	 * 
	 * @param ops : a list of operators
	 * @param startIndex : start index
	 * @param stopIndex : stop index
	 * @return the index of the pivot operator
	 */
	static int findPivot(std::list<BinaryOp*>* ops, int startIndex, int stopIndex);

	/**
	 * @brief Creates the precedence tree
	 *
	 * Creates the precedence tree from the given list of expressions,
	 * operators, and the start and stop indexes.
	 * 
	 * @param exprs : a list of expressions
	 * @param ops : a list of binary operators
	 * @param startIndex : start index
	 * @param stopIndex : stop index
	 * @return an expression
	 */
	static Expr* createPrecedenceTree(llvm::LLVMContext &C, std::list<Expr*>* exprs, std::list<BinaryOp*>* ops,
			int startIndex, int stopIndex);
};

#endif

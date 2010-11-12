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
@brief Description of the BinOpSeqParser
@author Jerome Gorin
@file BinOpSeqParser.cpp
@version 0.1
@date 22/03/2010
*/

//------------------------------
#include "BinOpSeqParser.h"

#include "Jade/Core/Expr/BinaryExpr.h"
//------------------------------
using namespace std;

Expr* BinOpSeqParser::parse(list<Expr*>* exprs, list<BinaryOp*>* ops){
	return createPrecedenceTree(exprs, ops, 0, exprs->size() - 1);
}

int BinOpSeqParser::findPivot(list<BinaryOp*>* ops, int startIndex, int stopIndex){
	int pivot = startIndex;
	list<BinaryOp*>::iterator it;
	
	it = ops->begin();
	advance(it, pivot);
	BinaryOp* bop = *it;
	int pivotRank = bop->getPrecedence();
	
	for (int i = startIndex + 1; i <= stopIndex; i++) {
		it = ops->begin();
		advance(it, i);
		bop = *it;
		int current = bop->getPrecedence();
		bool rtl = bop->isRightAssociative();
		if (pivotRank < current || (current == pivotRank && rtl)) {
			pivot = i;
			pivotRank = current;
		}
	}

	return pivot;
}

Expr* BinOpSeqParser::createPrecedenceTree(list<Expr*>* exprs, list<BinaryOp*>* ops, int startIndex, int stopIndex){
	list<BinaryOp*>::iterator itOp;
	list<Expr*>::iterator itExpr;

	if (stopIndex == startIndex) {
		itExpr = exprs->begin();
		advance(itExpr, startIndex);
		return *itExpr;
	}

	int pivot = findPivot(ops, startIndex, stopIndex - 1);
	itOp = ops->begin();
	advance(itOp, pivot);
	BinaryOp* op = *itOp;
	Expr* e1 = createPrecedenceTree(exprs, ops, startIndex, pivot);
	Expr* e2 = createPrecedenceTree(exprs, ops, pivot + 1, stopIndex);

	return new BinaryExpr(e1, op, e2);
}
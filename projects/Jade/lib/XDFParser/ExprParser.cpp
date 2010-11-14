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
@brief Implementation of ExprParser
@author Jerome Gorin
@file ExprParser.cpp
@version 0.1
@date 2010/04/12
*/

//------------------------------
#include <iostream>
#include <string>
#include <list>

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/LLVMContext.h"

#include "BinOpSeqParser.h"
#include "ExprParser.h"
#include "Jade/Core/Expr/BoolExpr.h"
#include "Jade/Core/Expr/IntExpr.h"
#include "Jade/Core/Expr/BinaryExpr.h"
#include "Jade/Core/Expr/StringExpr.h"
//------------------------------

using namespace std;
using namespace llvm;

ExprParser::ExprParser (llvm::LLVMContext& C): Context(C){
}

ExprParser::~ExprParser (){

}

Expr* ExprParser::parseExpr(TiXmlNode* node){
	ParseContinuation<Expr*> cont = parseExprCont(node);

	Expr* expr = cont.getResult();

	if (expr == NULL) {
		cerr << "Expected an expression element";
		exit(0);
	} 

	return expr;	
}

ParseContinuation<Expr*> ExprParser::parseExprCont(TiXmlNode* node){
	Expr* expression = NULL;

	while(node != NULL){
		if (TiXmlString(node->Value()) == XDFNetwork::EXPR) {
			TiXmlElement* elt = (TiXmlElement*)node;
			TiXmlString kind(elt->Attribute(XDFNetwork::KIND));

			if (kind == XDFNetwork::KIND_BINOPSEQ) {
				return parseExprBinOpSeq(node->FirstChild());
			} else if (kind == XDFNetwork::KIND_LITERAL) {
				expression = parseExprLiteral(elt);
				break;
			} else if (kind == XDFNetwork::KIND_LIST) {
				cerr << "List not supported yet";
				exit(0);
			} else if (kind == XDFNetwork::KIND_UNARYOP) {
				cerr << "UnaryOp not supported yet";
				exit(0);
			} else if (kind == XDFNetwork::KIND_VAR) {
				cerr << "Var not supported yet";
				exit(0);
			} else {
				cerr << "Unsupported expression kind: "<< kind.c_str();
				exit(0);
			}
		}
		
		node = node->NextSibling();	
	}

	return ParseContinuation<Expr*>(node, expression);
}

Expr* ExprParser::parseExprLiteral(TiXmlElement* elt){
	TiXmlString kind(elt->Attribute(XDFNetwork::LITERAL_KIND));
	TiXmlString value(elt->Attribute(XDFNetwork::LITERAL_VALUE));
	
	if (kind == XDFNetwork::LITERAL_BOOL) {
		return parseBoolean(value);	
	} else if (kind == XDFNetwork::LITERAL_CHAR) {
		fprintf(stderr,"Characters not supported yet");
		exit(0);
	} else if (kind == XDFNetwork::LITERAL_INT) {
		int integer = atoi (value.c_str());
		if (integer == 0 && value != "0"){
			fprintf(stderr,"Expression is not an integer");
			exit(0);
		}
		return new IntExpr(Context, integer);
	} else if (kind == XDFNetwork::LITERAL_REAL) {
		fprintf(stderr,"Reals not supported yet");
		exit(0);
	} else if (kind == XDFNetwork::LITERAL_STRING) {
		fprintf(stderr,"String not supported yet");
		exit(0);
	} else {
		fprintf(stderr,"Unsupported expression literal kind: \"%s\"", kind);
		exit(0);
	}
}


ParseContinuation<Expr*> ExprParser::parseExprBinOpSeq(TiXmlNode* node){
	list<Expr*> exprs;
	list<BinaryOp*> ops;

	ParseContinuation<Expr*> contE = parseExprCont(node);
	exprs.push_back(contE.getResult());
	node = contE.getNode();

	while (node != NULL){
		ParseContinuation<BinaryOp*> contO = parseExprBinaryOp(node);
		BinaryOp* op = contO.getResult();
		node = contO.getNode();

		if (op != NULL) {
			ops.push_back(op);

			contE = parseExprCont(node);
			Expr* expr = contE.getResult();
			if (expr == NULL) {
				cerr << "Missing an Expr element in Network.";
				exit(1);
			}

			exprs.push_back(expr);
			node = contE.getNode();
		}

	}

	Expr* expr = BinOpSeqParser().parse(Context, &exprs, &ops);
	return ParseContinuation<Expr*>(node, expr);
}

Expr* ExprParser::parseBoolean(TiXmlString value){
	if (value == "true") {
		return new BoolExpr(Context, true);
	}else if (value =="false") {
		return new BoolExpr(Context, false);
	}
	
	fprintf(stderr,"Expected a boolean value");
	exit(0);
}

ParseContinuation<BinaryOp*> ExprParser::parseExprBinaryOp(TiXmlNode* node){
	while (node != NULL){
		if (TiXmlString(node->Value()) == XDFNetwork::EXPR_OP) {
			TiXmlElement* op = (TiXmlElement*)node;
			string opName = op->Attribute(XDFNetwork::NAME);
			return ParseContinuation<BinaryOp*>(node, new BinaryOp(opName));
		}
		node = node->NextSibling();
	}
	return ParseContinuation<BinaryOp*>(node, NULL);
}


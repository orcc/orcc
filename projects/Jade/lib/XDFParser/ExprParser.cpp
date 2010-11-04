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
#include <string>
#include <list>

#include "llvm/Constants.h"
#include "llvm/DerivedTypes.h"
#include "llvm/LLVMContext.h"

#include "ExprParser.h"
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

Constant* ExprParser::parseExpr(xmlNode* node){
	Constant* expression = parseExprCont(node);

	if (expression == NULL) {
		fprintf(stderr,"Expected an expression element");
		exit(0);
	} 

	return expression;	
}

Constant* ExprParser::parseExprCont(xmlNode* element){
	xmlNode* node = NULL;
	Constant* expression = NULL;

	for (node = element; node; node = node->next) {
		if (xmlStrcmp(node->name, (const xmlChar *)"Expr")==0) {
			xmlAttr *node_attribute = node->properties;
			const xmlChar* kind = node_attribute->children->content;

			if (xmlStrcmp(kind, (const xmlChar *)"BinOpSeq")==0) {
				expression = parseExprBinOpSeq(node->children);
			} else if (xmlStrcmp(kind, (const xmlChar *)"Literal")==0) {
				expression = parseExprLiteral(node);
				break;
			} else if (xmlStrcmp(kind, (const xmlChar *)"List")==0) {
				fprintf(stderr,"List not supPorted yet");
				exit(0);
			} else if (xmlStrcmp(kind, (const xmlChar *)"UnaryOp")==0) {
				fprintf(stderr,"UnaryOp not supPorted yet");
				exit(0);
			} else if (xmlStrcmp(kind, (const xmlChar *)"Var")==0) {
				fprintf(stderr,"Var not supPorted yet");
				exit(0);
			} else {
				fprintf(stderr,"Unsupported expression kind: \"%s\"", kind);
				exit(0);
			}
		}

	}
	return expression;
}

ConstantInt* ExprParser::parseExprLiteral(xmlNode* element){
	xmlAttr *node_attribute = element->properties->next;
	const xmlChar* kind = node_attribute->children->content;
	node_attribute = node_attribute->next;
	const xmlChar* value = node_attribute->children->content;
	ConstantInt* expression = NULL;
	
	if (xmlStrcmp(kind, (const xmlChar *)"Boolean")==0) {
		return parseBoolean(value);	
	} else if (xmlStrcmp(kind, (const xmlChar *)"Character")==0) {
		fprintf(stderr,"Characters not supported yet");
		exit(0);
	} else if (xmlStrcmp(kind, (const xmlChar *)"Integer")==0) {
		int integer = atoi ((char*)value);
		if (integer == 0 && xmlStrcmp(value, (const xmlChar *)"0")!= 0){
			fprintf(stderr,"Expression is not an integer");
			exit(0);
		}
		return ConstantInt::get(IntegerType::get(Context, 32),integer);
	} else if (xmlStrcmp(kind, (const xmlChar *)"Real")==0) {
		fprintf(stderr,"Reals not supPorted yet");
		exit(0);
	} else if (xmlStrcmp(kind, (const xmlChar *)"String")==0) {
		fprintf(stderr,"String not supPorted yet");
		exit(0);
	} else {
		fprintf(stderr,"Unsupported expression literal kind: \"%s\"", kind);
		exit(0);
	}

	return expression;
}


Constant* ExprParser::parseExprBinOpSeq(xmlNode* element){
	list<Constant*> exprs;
	list<BinaryOp*> ops;

	Constant* expression = parseExprCont(element);
	exprs.push_back(expression);

	//TODO: BinOpParser does only support operation with one operator
	element = element->next;
	BinaryOp* op = parseExprBinaryOp(element);
	ops.push_back(op);
	element = element->next;
	expression = parseExprCont(element);
	exprs.push_back(expression);
	
	return ConstantExpr::getMul(exprs.front(), exprs.back());
}

ConstantInt* ExprParser::parseBoolean(const xmlChar* value){
	if (xmlStrcmp(value, (const xmlChar *)"true")==0) {
		return ConstantInt::get(IntegerType::get(Context,1),1);
	}else if (xmlStrcmp(value, (const xmlChar *)"false")==0) {
		return ConstantInt::get(IntegerType::get(Context,1), 0);
	}
	
	fprintf(stderr,"Expected a boolean value");
	exit(0);
}

BinaryOp* ExprParser::parseExprBinaryOp(xmlNode* element){
	while (element){
		const xmlChar* name = element->name;
		if (xmlStrcmp(name, (const xmlChar *)"Op")==0) {
			xmlAttr *node_attribute = element->properties;
			const xmlChar* name = node_attribute->children->content;
			return new BinaryOp(string((char*)name));
		}
		element = element->next;		
	}
	
	fprintf(stderr,"Binary operation was expected for the current expression");
	exit(0);
	
	return NULL;
}


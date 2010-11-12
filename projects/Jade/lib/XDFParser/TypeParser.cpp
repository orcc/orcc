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
@version 0.1
@date 22/03/2010
*/

//------------------------------
#include <iostream>

#include "llvm/Constants.h"
#include "llvm/LLVMContext.h"
#include "llvm/DerivedTypes.h"

#include "Jade/Core/Entry/ExprEntry.h"
#include "Jade/Core/Entry/TypeEntry.h"
#include "Jade/Core/Expr/IntExpr.h"
#include "Jade/Type/BoolType.h"
#include "Jade/Type/IntType.h"
#include "Jade/Type/UIntType.h"

#include "TypeParser.h"
//------------------------------

using namespace std;
using namespace llvm;

TypeParser::TypeParser (llvm::LLVMContext& C) : Context(C){
	exprParser = new ExprParser(C);
}

TypeParser::~TypeParser (){

}

Type* TypeParser::parseType(TiXmlNode* node){	
	
	while(node != NULL){
		if (TiXmlString(node->Value()) == XDFNetwork::TYPE) {
			TiXmlElement* eltType = (TiXmlElement*)node;
			TiXmlString name(eltType->Attribute( XDFNetwork::NAME));

			if (name == XDFNetwork::TYPE_BOOL) {
				return (Type*)Type::getInt1Ty(Context);
			}else if (name == XDFNetwork::TYPE_INT) {
				map<string, Entry*> *entries = parseTypeEntries(node->FirstChild());

				delete entries;

				return parseTypeSize(entries);

				
			}else if (name == XDFNetwork::TYPE_LIST) {
				cerr << "List elements are not supPorted yet";
				exit(0);
			}else if (name == XDFNetwork::TYPE_STRING) {
				cerr << "String elements are not supPorted yet";
				exit(0);
			}else if (name == XDFNetwork::TYPE_UINT) {
				map<string, Entry*> *entries = parseTypeEntries(node->FirstChild());
				delete entries;
				return parseTypeSize(entries);
			}else {
				cerr << "Unknown Type name: " << name.c_str();
				exit(0);
			}
		}
		
		node = node->NextSibling();
	}
	return NULL;
}

map<string, Entry*>* TypeParser::parseTypeEntries(TiXmlNode* node){
	map<string, Entry*> *entries = new map<string, Entry*>;
	
	while(node != NULL){
		if (TiXmlString(node->Value()) == XDFNetwork::ENTRY) {
			Entry* entry = NULL;
			TiXmlElement* element = (TiXmlElement*)node;

			TiXmlString name(element->Attribute(XDFNetwork::NAME));
			TiXmlString kind(element->Attribute(XDFNetwork::KIND));

			if (kind == XDFNetwork::EXPR) {
				Expr* expression = exprParser->parseExpr(node->FirstChild());
				entry = new ExprEntry(expression);
			}else if (kind == XDFNetwork::TYPE) {
				Type* type = parseType(node->FirstChild());
				entry = new TypeEntry(type);
			}else {
				fprintf(stderr, "UnsupPorted entry Type: \"%s\"", kind);
				exit(0);
			}
			
			entries->insert(pair<string, Entry*>(string(name.c_str()),entry));
		}
		node = node->NextSibling();
	}
	
	return entries;
}

IntegerType* TypeParser::parseTypeSize(map<string, Entry*>* entries){
	LLVMContext &context = getGlobalContext();

	map<string, Entry*>::iterator it;

	
	for ( it=entries->begin() ; it != entries->end(); it++ ){
		string entryName = (*it).first;
		if(entryName.compare("size")==0){
			//Size attribute found
			
			Entry* entry = (*it).second;

			if (entry->isTypeEntry()){
				fprintf(stderr,"Entry does not contain an Expression");
				exit(0);
			}

			///Return size
			//return cast<IntegerType>(((ExprEntry*)entry)->getExprEntry());
			return (IntegerType*)IntegerType::get(context,INT_SIZE);
		}
	}

	///Size attribute not found, return default int size
	return (IntegerType*)IntegerType::get(context,INT_SIZE);
}
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

Type* TypeParser::parseType(xmlNode* element){	
	xmlNode* node = NULL;

	for (node = element; node; node = node->next) {
		const xmlChar* name =  node->name;

		if (xmlStrcmp(name, (const xmlChar *)"Type")==0) {
			xmlAttr *node_attribute = node->properties;
			const xmlChar* name =  node_attribute->children->content;

			if (xmlStrcmp(name, (const xmlChar *)"bool")==0) {
				return (Type*)Type::getInt1Ty(Context);
			}else if (xmlStrcmp(name, (const xmlChar *)"int")==0) {
				map<string, Entry*> *entries = parseTypeEntries(node->children);

				delete entries;

				return parseTypeSize(entries);

				
			}else if (xmlStrcmp(name, (const xmlChar *)"List")==0) {
				cerr << "List elements are not supPorted yet";
				exit(0);
			}else if (xmlStrcmp(name, (const xmlChar *)"String")==0) {
				cerr << "String elements are not supPorted yet";
				exit(0);
			}else if (xmlStrcmp(name, (const xmlChar *)"uint")==0) {
				map<string, Entry*> *entries = parseTypeEntries(node->children);
				delete entries;
				return parseTypeSize(entries);
			}else {
				cerr << "Unknown Type name: " << name;
				exit(0);
			}
		}	
	}
	return NULL;
}

map<string, Entry*>* TypeParser::parseTypeEntries(xmlNode* element){
	xmlNode* node = NULL;

	map<string, Entry*> *entries = new map<string, Entry*>;
	
	for (node = element; node; node = node->next) {
		const xmlChar* nodeName =  node->name;

		if (xmlStrcmp(nodeName, (const xmlChar *)"Entry")==0) {
			Entry* entry;

			xmlAttr *node_attribute = node->properties;
			const xmlChar* kind =  node_attribute->children->content;

			node_attribute = node_attribute->next;
			const xmlChar* name =  node_attribute->children->content;

			if (xmlStrcmp(kind, (const xmlChar *)"Expr")==0) {
				Constant* expression = exprParser->parseExpr(node->children);
				entry = new ExprEntry(expression);
			}else if (xmlStrcmp(kind, (const xmlChar *)"Type")==0) {
				fprintf(stderr,"Type elements are not supPorted yet");
				exit(0);
			}else {
				fprintf(stderr, "UnsupPorted entry Type: \"%s\"", kind);
				exit(0);
			}
			
			entries->insert(pair<string, Entry*>(string((char*)name),entry));
		}

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
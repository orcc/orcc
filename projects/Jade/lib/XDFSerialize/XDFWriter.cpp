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
@brief Implementation of XDFWriter
@author Olivier Labois
@file XDFWriter.cpp
@version 1.0
@date 23/03/2011
*/

//------------------------------
#include <iostream>

#include "llvm/LLVMContext.h"

#include "Jade/XDFSerialize/XDFWriter.h"
#include "Jade/Core/Attribute/ValueAttribute.h"
#include "Jade/Core/Attribute/TypeAttribute.h"
#include "Jade/Core/Type/IntType.h"
#include "Jade/Core/Type/UIntType.h"
#include "Jade/Core/Expr/IntExpr.h"
#include "Jade/Core/Expr/BoolExpr.h"
#include "Jade/Core/Expr/StringExpr.h"
#include "Jade/Core/Expr/ListExpr.h"
//------------------------------

using namespace std;


const char* XDF_ROOT = "XDF";
const char* NAME = "name";
const char* ATTRIBUTE = "Attribute";
const char* CONNECTION = "Connection";
const char* CONNECTION_SRC = "src";
const char* CONNECTION_SRC_PORT = "src-port";
const char* CONNECTION_DST = "dst";
const char* CONNECTION_DST_PORT = "dst-port";
const char* DECL = "Decl";
const char* ENTRY = "Entry";
const char* EXPR = "Expr";
const char* EXPR_OP = "Op";
const char* INSTANCE = "Instance";
const char* INSTANCE_CLASS = "Class";
const char* INSTANCE_ID = "id";
const char* INSTANCE_PARAMETER = "Parameter";
const char* KIND = "kind";
const char* KIND_BINOPSEQ = "BinOpSeq";
const char* KIND_CUSTOM = "Custom";
const char* KIND_FLAG = "Flag";
const char* KIND_LIST = "List";
const char* KIND_LITERAL = "Literal";
const char* KIND_STRING = "String";
const char* KIND_TYPE = "Type";
const char* KIND_UNARYOP = "UnaryOp";
const char* KIND_VALUE = "Value";
const char* KIND_VAR = "Var";
const char* LITERAL_BOOL = "Boolean";
const char* LITERAL_CHAR = "Character";
const char* LITERAL_INT = "Integer";
const char* LITERAL_KIND = "literal-kind";
const char* LITERAL_REAL = "Real";
const char* LITERAL_STRING = "String";
const char* LITERAL_VALUE = "value";
const char* PACKAGE = "Package";
const char* PORT = "Port";
const char* TYPE = "Type";
const char* TYPE_BOOL = "bool";
const char* TYPE_INT = "int";
const char* TYPE_LIST = "List";
const char* TYPE_STRING = "String";
const char* TYPE_UINT = "uint";
const char* TYPE_SIZE = "size";


XDFWriter::XDFWriter (string filename, Network* network){
	this->filename = filename;
	this->xdfDoc = new TiXmlDocument(filename.c_str());
	this->network = network;
}



XDFWriter::~XDFWriter (){
	delete xdfDoc;
}


void XDFWriter::WriteXDF(){
	//Write declaration
	TiXmlDeclaration* decl = new TiXmlDeclaration("1.0","UTF-8","");
	xdfDoc->LinkEndChild(decl);

	//Write root element
	TiXmlElement* root = new TiXmlElement(XDF_ROOT); 
	root->SetAttribute(NAME, network->getName().c_str());
	xdfDoc->LinkEndChild(root);

	//Write inputs
	//Inputs are not supported yet

	//Write outputs
	//Outputs are not supported yet

	//Write parameters
	//Parameters are not supported yet

	//Write Variables
	//Variables are not supported yet

	//Write instances
	list<Instance*>* instances = network->getInstances();
	list<Instance*>::iterator itInst;

	for(itInst = instances->begin(); itInst != instances->end(); itInst++){
		root->LinkEndChild(writeInstance(*itInst));
	}

	//Write connections
	list<Connection*>* connections = network->getConnections();
	list<Connection*>::iterator itConnect;

	for(itConnect = connections->begin(); itConnect != connections->end(); itConnect++){
		root->LinkEndChild(writeConnection(*itConnect));
	}

	xdfDoc->SaveFile();
}

TiXmlElement* XDFWriter::writeInstance(Instance* instance){
	TiXmlElement* instanceElt = new TiXmlElement(INSTANCE);
	instanceElt->SetAttribute(INSTANCE_ID, instance->getId().c_str());

	// Class
	TiXmlElement* classElt = new TiXmlElement(INSTANCE_CLASS);
	classElt->SetAttribute(NAME, instance->getClasz().c_str());
	instanceElt->LinkEndChild(classElt);

	// Parameters
	map<string, Expr*>* parameters = instance->getParameterValues();
	map<string, Expr*>::iterator itPar;

	for(itPar = parameters->begin(); itPar != parameters->end(); itPar++){
		TiXmlElement* parameterElt = new TiXmlElement(INSTANCE_PARAMETER);
		parameterElt->SetAttribute(NAME, itPar->first.c_str());
		instanceElt->LinkEndChild(parameterElt);

		// Expr
		writeExpr(parameterElt, itPar->second);
	}

	// Attributes
	writeAttributes(instanceElt, instance->getAttributes());

	return instanceElt;
}

TiXmlElement* XDFWriter::writeConnection(Connection* connection){
	TiXmlElement* connectionElt = new TiXmlElement(CONNECTION);

	Vertex* srcVertex = (Vertex*) connection->getSource();
	Vertex* dstVertex = (Vertex*) connection->getSink();
	Instance* source = srcVertex->getInstance();
	Instance* dest = dstVertex->getInstance();

	string srcPort = connection->getSourcePort()->getName();
	string dstPort = connection->getDestinationPort()->getName();

	connectionElt->SetAttribute(CONNECTION_SRC, source->getId().c_str());
	connectionElt->SetAttribute(CONNECTION_SRC_PORT, srcPort.c_str());
	connectionElt->SetAttribute(CONNECTION_DST, dest->getId().c_str());
	connectionElt->SetAttribute(CONNECTION_DST_PORT, dstPort.c_str());

	// Attributes
	writeAttributes(connectionElt, connection->getAttributes());
	
	return connectionElt;
}

void XDFWriter::writeAttributes(TiXmlElement* parent, map<string,IRAttribute*>* attributes){
	map<string,IRAttribute*>::iterator itAtt;

	for(itAtt = attributes->begin(); itAtt != attributes->end(); itAtt++){
		//Write attribute
		TiXmlElement* attElt = new TiXmlElement(ATTRIBUTE);
		attElt->SetAttribute(NAME, itAtt->first.c_str());
		parent->LinkEndChild(attElt);

		//Write kind
		IRAttribute* attribute = itAtt->second;
		string kind;
		if(attribute->isCustom()){
			cerr << "Custom attribute is not supported yet";
		}else if(attribute->isString()){
			cerr << "String attribute is not supported yet";
		}else if(attribute->isType()){
			kind = KIND_TYPE;
			TypeAttribute* typeAttribute = (TypeAttribute*)attribute;			
			attElt->LinkEndChild(writeType(typeAttribute->getType()));
		}else if(attribute->isValue()){
			kind = KIND_VALUE;	
			ValueAttribute* valueAttribute = (ValueAttribute*)attribute;		
			writeExpr(attElt, valueAttribute->getValue());
		}else{
			cerr << "unknown attribute type";
		}
		
		attElt->SetAttribute(KIND, kind.c_str());
	}
}

void XDFWriter::writeExpr(TiXmlElement* parent, Expr* expr){
	TiXmlElement* exprElt = new TiXmlElement(EXPR);
	parent->LinkEndChild(exprElt);
	
	if(expr->isBinaryExpr()){
		exprElt->SetAttribute(KIND, KIND_BINOPSEQ);
		cerr << "Binary expression is not supported yet";
	}else if(expr->isBooleanExpr()){
		exprElt->SetAttribute(KIND, KIND_LITERAL);
		exprElt->SetAttribute(LITERAL_KIND, LITERAL_BOOL);
		if (((BoolExpr*)expr)->getValue()){
			exprElt->SetAttribute(LITERAL_VALUE, "true");
		}else{
			exprElt->SetAttribute(LITERAL_VALUE, "false");
		}
	}else if(expr->isIntExpr()){
		exprElt->SetAttribute(KIND, KIND_LITERAL);
		exprElt->SetAttribute(LITERAL_KIND, LITERAL_INT);
		int value = expr->evaluateAsInteger();
		exprElt->SetAttribute(LITERAL_VALUE, value);	
	}else if(expr->isListExpr()){
		cerr << "List expression is not supported yet";
	}else if(expr->isStringExpr()){
		exprElt->SetAttribute(KIND, KIND_LITERAL);
		exprElt->SetAttribute(LITERAL_KIND, LITERAL_STRING);
		string value = ((StringExpr*)expr)->getValue();
		exprElt->SetAttribute(LITERAL_VALUE, value.c_str());
	}else if(expr->isUnaryExpr()){
		cerr << "Unary expression is not supported yet";
	}else if(expr->isVarExpr()){
		cerr << "Var expression is not supported yet";
	}else{
		cerr << "unknown expression type";
	}
}

TiXmlElement* XDFWriter::writeType(IRType* type){
	TiXmlElement* typeElt = new TiXmlElement(TYPE);

	string name;
	int size;

	if(type->isBoolType()){
		name = TYPE_BOOL;
	}else if(type->isIntType()){
		name = TYPE_INT;
		size = ((IntType*)type)->getSize();
		llvm::LLVMContext &Context = llvm::getGlobalContext();
		typeElt->LinkEndChild(writeEntry(TYPE_SIZE, new IntExpr(Context, size)));
	}else if(type->isListType()){
		name = TYPE_LIST;
		cerr << "List type is not supported yet";
	}else if(type->isStringType()){
		name = TYPE_STRING;
	}else if(type->isUintType()){
		name = TYPE_UINT;
		size = ((UIntType*)type)->getSize();
		llvm::LLVMContext &Context = llvm::getGlobalContext();
		typeElt->LinkEndChild(writeEntry(TYPE_SIZE, new IntExpr(Context, size)));
	}else if(type->isVoidType()){
		cerr << "void type is invalid in XDF";
	}else{
		cerr << "unknown type";
	}

	typeElt->SetAttribute(NAME, name.c_str());
	return typeElt;
}

TiXmlElement* XDFWriter::writeEntry(string name, Expr *expr){
	TiXmlElement* entry = new TiXmlElement(ENTRY);
	
	entry->SetAttribute(KIND, KIND_TYPE);
	entry->SetAttribute(NAME, name.c_str());
	writeExpr(entry, expr);

	return entry;
}

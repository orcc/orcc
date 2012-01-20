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

#include "XDFConstant.h"
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


XDFWriter::XDFWriter (string filename, Network* network){
	this->filename = filename;
	this->xdfDoc = new TiXmlDocument(filename.c_str());
	this->network = network;
}



XDFWriter::~XDFWriter (){
	delete xdfDoc;
}


void XDFWriter::writeXDF(){
	//Write declaration
	TiXmlDeclaration* decl = new TiXmlDeclaration("1.0","UTF-8","");
	xdfDoc->LinkEndChild(decl);

	//Write root element
	TiXmlElement* root = new TiXmlElement(XDFNetwork::XDF_ROOT); 
	root->SetAttribute(XDFNetwork::NAME, network->getName().c_str());
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
	TiXmlElement* instanceElt = new TiXmlElement(XDFNetwork::INSTANCE);
	instanceElt->SetAttribute(XDFNetwork::INSTANCE_ID, instance->getId().c_str());

	// Class
	TiXmlElement* classElt = new TiXmlElement(XDFNetwork::INSTANCE_CLASS);
	classElt->SetAttribute(XDFNetwork::NAME, instance->getClasz().c_str());
	instanceElt->LinkEndChild(classElt);

	// Parameters
	map<string, Expr*>* parameters = instance->getParameterValues();
	map<string, Expr*>::iterator itPar;

	for(itPar = parameters->begin(); itPar != parameters->end(); itPar++){
		TiXmlElement* parameterElt = new TiXmlElement(XDFNetwork::INSTANCE_PARAMETER);
		parameterElt->SetAttribute(XDFNetwork::NAME, itPar->first.c_str());
		instanceElt->LinkEndChild(parameterElt);

		// Expr
		writeExpr(parameterElt, itPar->second);
	}

	// Attributes
	writeAttributes(instanceElt, instance->getAttributes());

	return instanceElt;
}

TiXmlElement* XDFWriter::writeConnection(Connection* connection){
	TiXmlElement* connectionElt = new TiXmlElement(XDFNetwork::CONNECTION);

	Vertex* srcVertex = (Vertex*) connection->getSource();
	Vertex* dstVertex = (Vertex*) connection->getSink();
	Instance* source = srcVertex->getInstance();
	Instance* dest = dstVertex->getInstance();

	string srcPort = connection->getSourcePort()->getName();
	string dstPort = connection->getDestinationPort()->getName();

	connectionElt->SetAttribute(XDFNetwork::CONNECTION_SRC, source->getId().c_str());
	connectionElt->SetAttribute(XDFNetwork::CONNECTION_SRC_PORT, srcPort.c_str());
	connectionElt->SetAttribute(XDFNetwork::CONNECTION_DST, dest->getId().c_str());
	connectionElt->SetAttribute(XDFNetwork::CONNECTION_DST_PORT, dstPort.c_str());

	// Attributes
	writeAttributes(connectionElt, connection->getAttributes());
	
	return connectionElt;
}

void XDFWriter::writeAttributes(TiXmlElement* parent, map<string,IRAttribute*>* attributes){
	map<string,IRAttribute*>::iterator itAtt;

	for(itAtt = attributes->begin(); itAtt != attributes->end(); itAtt++){
		//Write attribute
		TiXmlElement* attElt = new TiXmlElement(XDFNetwork::IRAttribute);
		attElt->SetAttribute(XDFNetwork::NAME, itAtt->first.c_str());
		parent->LinkEndChild(attElt);

		//Write kind
		IRAttribute* attribute = itAtt->second;
		string kind;
		if(attribute->isCustom()){
			cerr << "Custom attribute is not supported yet";
		}else if(attribute->isString()){
			cerr << "String attribute is not supported yet";
		}else if(attribute->isType()){
			kind = XDFNetwork::KIND_TYPE;
			TypeAttribute* typeAttribute = (TypeAttribute*)attribute;			
			attElt->LinkEndChild(writeType(typeAttribute->getType()));
		}else if(attribute->isValue()){
			kind = XDFNetwork::KIND_VALUE;	
			ValueAttribute* valueAttribute = (ValueAttribute*)attribute;		
			writeExpr(attElt, valueAttribute->getValue());
		}else{
			cerr << "unknown attribute type";
		}
		
		attElt->SetAttribute(XDFNetwork::KIND, kind.c_str());
	}
}

void XDFWriter::writeExpr(TiXmlElement* parent, Expr* expr){
	TiXmlElement* exprElt = new TiXmlElement(XDFNetwork::EXPR);
	parent->LinkEndChild(exprElt);
	
	if(expr->isBinaryExpr()){
		exprElt->SetAttribute(XDFNetwork::KIND, XDFNetwork::KIND_BINOPSEQ);
		cerr << "Binary expression is not supported yet";
	}else if(expr->isBooleanExpr()){
		exprElt->SetAttribute(XDFNetwork::KIND, XDFNetwork::KIND_LITERAL);
		exprElt->SetAttribute(XDFNetwork::LITERAL_KIND, XDFNetwork::LITERAL_BOOL);
		if (((BoolExpr*)expr)->getValue()){
			exprElt->SetAttribute(XDFNetwork::LITERAL_VALUE, "true");
		}else{
			exprElt->SetAttribute(XDFNetwork::LITERAL_VALUE, "false");
		}
	}else if(expr->isIntExpr()){
		exprElt->SetAttribute(XDFNetwork::KIND, XDFNetwork::KIND_LITERAL);
		exprElt->SetAttribute(XDFNetwork::LITERAL_KIND, XDFNetwork::LITERAL_INT);
		int value = expr->evaluateAsInteger();
		exprElt->SetAttribute(XDFNetwork::LITERAL_VALUE, value);	
	}else if(expr->isListExpr()){
		cerr << "List expression is not supported yet";
	}else if(expr->isStringExpr()){
		exprElt->SetAttribute(XDFNetwork::KIND, XDFNetwork::KIND_LITERAL);
		exprElt->SetAttribute(XDFNetwork::LITERAL_KIND, XDFNetwork::LITERAL_STRING);
		string value = ((StringExpr*)expr)->getValue();
		exprElt->SetAttribute(XDFNetwork::LITERAL_VALUE, value.c_str());
	}else if(expr->isUnaryExpr()){
		cerr << "Unary expression is not supported yet";
	}else if(expr->isVarExpr()){
		cerr << "Var expression is not supported yet";
	}else{
		cerr << "unknown expression type";
	}
}

TiXmlElement* XDFWriter::writeType(IRType* type){
	TiXmlElement* typeElt = new TiXmlElement(XDFNetwork::TYPE);

	string name;
	int size;

	if(type->isBoolType()){
		name = XDFNetwork::TYPE_BOOL;
	}else if(type->isIntType()){
		name = XDFNetwork::TYPE_INT;
		size = ((IntType*)type)->getSize();
		llvm::LLVMContext &Context = llvm::getGlobalContext();
		typeElt->LinkEndChild(writeEntry(XDFNetwork::TYPE_SIZE, new IntExpr(Context, size)));
	}else if(type->isListType()){
		name = XDFNetwork::TYPE_LIST;
		cerr << "List type is not supported yet";
	}else if(type->isStringType()){
		name = XDFNetwork::TYPE_STRING;
	}else if(type->isUintType()){
		name = XDFNetwork::TYPE_UINT;
		size = ((UIntType*)type)->getSize();
		llvm::LLVMContext &Context = llvm::getGlobalContext();
		typeElt->LinkEndChild(writeEntry(XDFNetwork::TYPE_SIZE, new IntExpr(Context, size)));
	}else if(type->isVoidType()){
		cerr << "void type is invalid in XDF";
	}else{
		cerr << "unknown type";
	}

	typeElt->SetAttribute(XDFNetwork::NAME, name.c_str());
	return typeElt;
}

TiXmlElement* XDFWriter::writeEntry(string name, Expr *expr){
	TiXmlElement* entry = new TiXmlElement(XDFNetwork::ENTRY);
	
	entry->SetAttribute(XDFNetwork::KIND, XDFNetwork::KIND_TYPE);
	entry->SetAttribute(XDFNetwork::NAME, name.c_str());
	writeExpr(entry, expr);

	return entry;
}

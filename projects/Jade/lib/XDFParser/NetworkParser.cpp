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
 * @file NetworkParser.cpp
 * @brief Implementation of NetworkParser
 * @author Jerome Gorin
 * @version 0.1
 */

//------------------------------
#include <iostream>

#include "Jade/Core/Port.h"
#include "Jade/Core/Attribute/TypeAttribute.h"
#include "Jade/Core/Attribute/ValueAttribute.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Vertex.h"
#include "Jade/Core/Connection.h"
#include "Jade/TinyXML/TinyStr.h"

#include "NetworkParser.h"
//------------------------------



using namespace std;
using namespace llvm;

NetworkParser::NetworkParser (llvm::LLVMContext& C, string filename){
	/* Initialize attributes */
	xdfDoc = new TiXmlDocument (filename.c_str());
    network = NULL;

	/* Parsing file error */
	if (!xdfDoc->LoadFile()) {
        cerr << "Failed to parse %s\n" << filename.c_str();
		exit(0);
    }

	Connections = new list<Connection*>();

	/* Xml type parser */
	typeParser = new TypeParser(C);

	/** XDF expression parser. */
	exprParser = new ExprParser(C);
}

NetworkParser::~NetworkParser (){
	//xmlFreeDoc(xdfDoc);

	///*
 //    * Cleanup function for the XML library.
 //    */
 //   xmlCleanupParser();
 //   /*
 //    * this is to debug memory for regression tests
 //    */
 //   xmlMemoryDump();
}



Network* NetworkParser::parseNetwork (){
	// Get the root element node
	TiXmlElement* root_element = xdfDoc->RootElement();
	
	// xml document doesn't start with XDF root
	if (TiXmlString(root_element->Value()) != XDFNetwork::XDF_ROOT){
		cerr << "XML description does not represent an XDF network";
		exit(1);
	}
	
	// Return NULL if network doesn't have a name
	TiXmlAttribute* root_attribute = root_element->FirstAttribute();
	if (TiXmlString(root_attribute->Name()) != XDFNetwork::NAME){
		cerr << "Expected a \"name\" in XDF network";
		exit(1);
	}
	
	// Set network properties
	string name = root_attribute->Value();
	graph = new HDAGGraph();
	inputs = new map<string, Port*>();
	outputs = new map<string, Port*>();
	instances = new map<string, Instance*>();

	/* Parse root_element */
	parseBody(root_element);

	/* Initialize network */
	network = new Network(name, inputs, outputs, graph);
	
	return network;
}

Instance* NetworkParser::parseInstance(TiXmlElement* instance){
	TiXmlString id (instance->Attribute(XDFNetwork::INSTANCE_ID));

	if (id.empty()) {
		cerr << "An Instance element must have a valid \"id\" attribute";
		exit(0);
	}

	// instance class
	TiXmlString clasz;
	TiXmlNode* child = instance->FirstChild();

	while(child != NULL){
		if (TiXmlString(child->Value()) == XDFNetwork::INSTANCE_CLASS){
			clasz = TiXmlString(((TiXmlElement*) child)->Attribute(XDFNetwork::NAME));
			break;
		}
	}

	if (clasz.empty()) {
		cerr << "An Instance element must have a valid \"Class\" child.";
		exit(0);
	}
	
	map<string, Expr*>* parameters = parseParameters(child);

	return new Instance(string(id.c_str()), string(clasz.c_str()), parameters);
}


map<string, Expr*> *NetworkParser::parseParameters(TiXmlNode* node){
	map<string, Expr*> *parameters = new map<string, Expr*>;

	while(node != NULL){
		if (TiXmlString(node->Value()) == XDFNetwork::INSTANCE_PARAMETER){
			TiXmlString name(((TiXmlElement*) node)->Attribute(XDFNetwork::NAME));
		
			if (name == "") {
				cerr <<"A Parameter element must have a valid \"name\" attribute";
				exit(0);
			}
			Expr* expression = exprParser->parseExpr(node->FirstChild());
			parameters->insert(pair<string, Expr*>(string(name.c_str()), expression));
		}
		node = node->NextSibling();
	}

	return parameters;

}

void  NetworkParser::parseBody(TiXmlElement* root){
	TiXmlNode* node = root->FirstChild();

	while (node != NULL){
		if (node->Type() == TiXmlNode::TINYXML_ELEMENT) {
			TiXmlElement* element = (TiXmlElement*)node;
			TiXmlString name(node->Value());

			if (name == XDFNetwork::CONNECTION) {
				parseConnection(element);
			}else if (name == XDFNetwork::DECL) {
				cerr << "Decl elements are not supPorted yet";
				exit(0);
			}else if (name == XDFNetwork::INSTANCE) {
				Instance* instance = parseInstance(element);
				instances->insert(pair<string, Instance*>(instance->getId(), instance));
				graph->addVertex(new Vertex(instance));
			}else if (name == XDFNetwork::PACKAGE) {
				cerr << "Package elements are not supPorted yet";
				exit(0);
			}else if (name == XDFNetwork::PORT) {
				cerr << "Port elements are not supPorted yet";
				exit(0);
			}else {
				cerr << "invalid node \"%s\"\n" << name.c_str();
				exit(0);
			}
			
			node = node->NextSibling();
        }
    }
}

void NetworkParser::parseConnection(TiXmlElement* connection){
	
	// Parse information from fifo 
	const char* src = connection->Attribute(XDFNetwork::CONNECTION_SRC);
	const char* src_port = connection->Attribute(XDFNetwork::CONNECTION_SRC_PORT);
	const char* dst = connection->Attribute(XDFNetwork::CONNECTION_DST);
	const char* dst_port = connection->Attribute(XDFNetwork::CONNECTION_DST_PORT);

	Vertex* source = getVertex(src, src_port, "Input", inputs);
	Port* srcPort = getPort(src, src_port);
	Vertex* target = getVertex(dst, dst_port, "Output", outputs);
	Port* dstPort = getPort(dst, dst_port);

	// Get attributes 
	map<string, IRAttribute*>* attributes = parseAttributes(connection->FirstChild());
	Connection* conn = new Connection(srcPort, dstPort, attributes);
	
	graph->addEdge(source, target, conn);
}

map<string, IRAttribute*>* NetworkParser::parseAttributes(TiXmlNode* node){
	map<string, IRAttribute*> *IRAttributes = new map<string, IRAttribute*>;

	while(node != NULL){
		// only IRAttribute nodes are parsed, other one are ignored. 
		if (TiXmlString(node->Value()) == XDFNetwork::IRAttribute) {
			IRAttribute* attr = NULL;
			TiXmlElement* attribute = (TiXmlElement*)node;
			TiXmlString kind(attribute->Attribute(XDFNetwork::KIND));
			TiXmlString attrName(attribute->Attribute(XDFNetwork::NAME));		

			if (kind == XDFNetwork::KIND_CUSTOM) {
				cerr << "Custom elements are not supPorted yet";
				exit(0);
			}else if (kind == XDFNetwork::KIND_FLAG) {
				cerr << "Flag elements are not supPorted yet";
				exit(0);
			}else if (kind == XDFNetwork::KIND_STRING) {
				cerr << "String elements are not supPorted yet";
				exit(0);
			}else if (kind == XDFNetwork::KIND_TYPE) {
				IRType* type = typeParser->parseType(attribute->FirstChild());
				attr = new TypeAttribute(type);
			}else if (kind == XDFNetwork::KIND_VALUE) {
				Expr* expression = exprParser->parseExpr(attribute->FirstChild());
				attr = new ValueAttribute(expression);
			}else {
				cerr << "unsupported IRAttribute kind: " << kind.c_str() ;
				exit(0);
			}

			IRAttributes->insert(pair<string, IRAttribute*>(string(attrName.c_str()), attr));
        }
	}
	return IRAttributes;
}

Port* NetworkParser::getPort(string vertexName, string portName) {
	if (vertexName.empty()){
		return NULL;
	}else{
		return new Port(portName, NULL);
	}
}


Vertex* NetworkParser::getVertex(string vertexName, string portName, string kind, map<std::string, Port*>* ports){
	if(vertexName.empty()){
		map<string, Port*>::iterator it;

		it = ports->find(portName);

		if (it == ports->end()){
			cerr << "Connection element %s has an invalid IRAttribute" << portName;
			exit(0);
		}

		return new Vertex(kind, it->second);
	} else {
		map<string, Instance*>::iterator it;

		it = instances->find(vertexName);

		if (it == instances->end()){
			cerr << "Connection element %s has an invalid IRAttribute" << vertexName;
			exit(0);
		}

		return new Vertex(it->second);
	}

}
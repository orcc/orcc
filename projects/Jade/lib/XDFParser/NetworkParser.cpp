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
#include "NetworkParser.h"
#include "Jade/Attribute/TypeAttribute.h"
#include "Jade/Attribute/ValueAttribute.h"
#include "Jade/Core/Port.h"
#include "Jade/Graph/HDAGGraph.h"
#include "Jade/Core/Vertex.h"
#include "Jade/Core/Connection.h"
//------------------------------



using namespace std;
using namespace llvm;

NetworkParser::NetworkParser (llvm::LLVMContext& C, string filename){
	LIBXML_TEST_VERSION
	
	/* Initialize attributes */
	xdfDoc = xmlReadFile(filename.c_str(), NULL, 0);
    network = NULL;

	/* Parsing file error */
	if (xdfDoc == NULL) {
        fprintf(stderr, "Failed to parse %s\n", filename.c_str());
		exit(0);
    }

	Connections = new list<Connection*>();
		/* Xml type parser */
	typeParser = new TypeParser(C);

	/** XDF expression parser. */
	exprParser = new ExprParser(C);
}

NetworkParser::~NetworkParser (){
	xmlFreeDoc(xdfDoc);

	/*
     * Cleanup function for the XML library.
     */
    xmlCleanupParser();
    /*
     * this is to debug memory for regression tests
     */
    xmlMemoryDump();
}



Network* NetworkParser::parseNetwork (){
	/*Initialize xml element and attribute container*/
	xmlNode *root_element = NULL;
	xmlAttr *root_attribute = NULL;


	/*Get the root element node */
    root_element = xmlDocGetRootElement(xdfDoc);

	/*Return NULL if xml document doesn't start with XDF root */
	if (xmlStrcmp(root_element->name, (const xmlChar *)"XDF")!=0){
		fprintf(stderr, "Expected \"XDF\" start element");
		exit(1);
	}
	
	/*Return NULL if network doesn't have a name */
	root_attribute = root_element->properties;
	if (xmlStrcmp(root_attribute->name, (const xmlChar *)"name")!=0){
		fprintf(stderr, "Expected a \"name\" attribute");
		exit(1);
	}
	
	string name = string((char*)root_attribute->children->content);
	graph = new HDAGGraph();
	inputs = new map<string, Port*>();
	outputs = new map<string, Port*>();
	instances = new map<string, Instance*>();

	/* Parse root_element */
	parseBody(root_element->children);

	/* Initialize network */
	network = new Network(name, inputs, outputs, graph);
	
	return network;
}

Instance* NetworkParser::parseInstance(xmlNode* instance){
	xmlAttr *instance_attribute = instance->properties;
	xmlNode* child = instance_attribute->children;

	const xmlChar* id = child->content;
	const xmlChar* clasz = NULL;

	if (xmlStrcmp(id, (const xmlChar *)"")==0) {
		fprintf(stderr,"An Instance element must have a valid \"id\" attribute");
		exit(0);
	}

	for (child = instance->children; child; child = child->next) {
		if (xmlStrcmp(child->name, (const xmlChar *)"Class")==0){
			xmlAttr *class_attribute = child->properties;
			xmlNode* class_child = class_attribute->children;
			clasz = class_child->content;
			break;
		}
	}

	if (clasz == NULL || (xmlStrcmp(clasz, (const xmlChar *)"")==0)) {
		fprintf(stderr,"An Instance element must have a valid \"Class\" child.");
		exit(0);
	}
	
	map<string, Constant*>* parameters = parseParameters(child->next);

	return new Instance(string((char*)id), string((char*)clasz), parameters);
}


map<string, Constant*> *NetworkParser::parseParameters(xmlNode* element){
	Constant* expression = NULL;
	xmlNode* node = NULL;
	map<string, Constant*> *parameters = new map<string, Constant*>;

	for (node = element; node; node = node->next) {
		if (xmlStrcmp(node->name, (const xmlChar *)"Parameter")==0) {
			xmlAttr *node_attribute = node->properties;

			const xmlChar* name = node_attribute->children->content;
			
			if (xmlStrcmp(name, (const xmlChar *)"")==0) {
				fprintf(stderr,"A Parameter element must have a valid \"name\" attribute");
				exit(0);
			}
			expression = exprParser->parseExpr(node->children);
			parameters->insert(pair<string, Constant*>(string((char*)name), expression));
		}
	}

	return parameters;

}

void  NetworkParser::parseBody(xmlNode* root){
	xmlNode* node = NULL;
	for (node = root; node; node = node->next) {
        if (node->type == XML_ELEMENT_NODE) {
			const xmlChar* name =  node->name;

			if (xmlStrcmp(name, (const xmlChar *)"Connection")==0) {
				parseConnection(node);
			}else if (xmlStrcmp(name, (const xmlChar *)"Decl")==0) {
				fprintf(stderr,"Decl elements are not supPorted yet");
				exit(0);
			}else if (xmlStrcmp(name, (const xmlChar *)"Instance")==0) {
				Instance* instance = parseInstance(node);
				instances->insert(pair<string, Instance*>(instance->getId(), instance));
				graph->addVertex(new Vertex(instance));
			}else if (xmlStrcmp(name, (const xmlChar *)"Package")==0) {
				fprintf(stderr,"Package elements are not supPorted yet");
				exit(0);
			}else if (xmlStrcmp(name, (const xmlChar *)"Port")==0) {
				fprintf(stderr,"Port elements are not supPorted yet");
				exit(0);
			}else {
				fprintf(stderr,"invalid node \"%s\"\n", name);
				exit(0);
			}
        }
    }
}

void NetworkParser::parseConnection(xmlNode* connection){
	
	// Parse information from fifo 
	xmlAttr *node_attribute = connection->properties;
	char* dst =  (char*)node_attribute->children->content;
	node_attribute = node_attribute->next;
	char* dst_port =  (char*)node_attribute->children->content;
	node_attribute = node_attribute->next;
	char* src =  (char*)node_attribute->children->content;
	node_attribute = node_attribute->next;
	char* src_port =  (char*)node_attribute->children->content;


	Vertex* source = getVertex(src, src_port, "Input", inputs);
	Port* srcPort = getPort(src, src_port);
	Vertex* target = getVertex(dst, dst_port, "Output", outputs);
	Port* dstPort = getPort(dst, dst_port);

	// Get attributes 
	map<string, Attribute*>* attributes = parseAttributes(connection->children);
	Connection* conn = new Connection(srcPort, dstPort, attributes);
	
	graph->addEdge(source, target, conn);
}

map<string, Attribute*>* NetworkParser::parseAttributes(xmlNode* element){
	xmlNode* node = NULL;
	map<string, Attribute*> *attributes = new map<string, Attribute*>;

	for (node = element; node; node = node->next) {
		const xmlChar* name =  node->name;

		// only Attribute nodes are parsed, other one are ignored. 
		if (xmlStrcmp(name, (const xmlChar *)"Attribute")==0) {
			Attribute* attr;
			xmlAttr *node_attribute = node->properties;
			const xmlChar* kind =  node_attribute->children->content;

			node_attribute = node_attribute->next;
			const xmlChar* attrName =  node_attribute->children->content;
			

			if (xmlStrcmp(kind, (const xmlChar *)"Custom")==0) {
				fprintf(stderr,"Custom elements are not supPorted yet");
				exit(0);
			}else if (xmlStrcmp(kind, (const xmlChar *)"Flag")==0) {
				fprintf(stderr,"Flag elements are not supPorted yet");
				exit(0);
			}else if (xmlStrcmp(kind, (const xmlChar *)"String")==0) {
				fprintf(stderr,"String elements are not supPorted yet");
				exit(0);
			}else if (xmlStrcmp(kind, (const xmlChar *)"Type")==0) {
				Type* type = typeParser->parseType(node->children);
				attr = new TypeAttribute(type);
			}else if (xmlStrcmp(kind, (const xmlChar *)"Value")==0) {
				Constant* expression = exprParser->parseExpr(node->children);
				attr = new ValueAttribute(expression);
			}else {
				fprintf(stderr,"invalid node \"%s\"\n", name);
				exit(0);
			}

			attributes->insert(pair<string, Attribute*>(string((char*)attrName), attr));
        }
	}
	return attributes;
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
			fprintf(stderr,"Connection element %s has an invalid attribute", portName);
			exit(0);
		}

		return new Vertex(kind, it->second);
	} else {
		map<string, Instance*>::iterator it;

		it = instances->find(vertexName);

		if (it == instances->end()){
			fprintf(stderr,"Connection element %s has an invalid attribute", vertexName);
			exit(0);
		}

		return new Vertex(it->second);
	}

}
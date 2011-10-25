/*
 * Copyright (c) 2011, ARTEMIS SudParis - IETR/INSA of Rennes
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
@brief Implementation of XCFParser
@author Jerome Gorin
@file XCFParser.cpp
@version 1.0
@date 20/09/2011
*/

//------------------------------
#include <iostream>

#include "XCFConstant.h"

#include "Jade/XCFSerialize/XCFParser.h"

#include "Jade/TinyXml/TinyStr.h"
#include "Jade/TinyXml/TinyXml.h"
//------------------------------

using namespace std;


XCFParser::XCFParser (bool verbose){
	this->verbose = verbose;
}

map<string, string>* XCFParser::parseFile (string filename){
	// Initialize XCF document
	this->xcfDoc = new TiXmlDocument (filename.c_str());

	/* Parsing XML file error */
	if (!xcfDoc->LoadFile()) {
		cerr << "Error : the given file does not exist. \n";
		return NULL;
    }

	return parseXCFDoc();
}

map<string, string>* XCFParser::parseXCFDoc(){

	// Get the root element node
	TiXmlElement* root_element = xcfDoc->RootElement();
	
	// xml document doesn't start with XDF root
	if (TiXmlString(root_element->Value()) != XCFMapping::CONFIGURATION_ROOT){
		cerr << "XML description does not contains mapping configuration information";
		return NULL;
	}
	
	 mapStr = new map<string, string>();

	/* Parse partitions */
	parsePartitioning(root_element);

	return mapStr;
}

void XCFParser::parsePartitioning(TiXmlElement* root){
	TiXmlNode* node = root->FirstChild();
	
	
	while (node != NULL){
		if (node->Type() == TiXmlNode::TINYXML_ELEMENT) {
			TiXmlElement* element = (TiXmlElement*)node;
			TiXmlString name(node->Value());
			
			if (name == XCFMapping::PARTITIONING) {
				parsePartitioning(element);
			}else if (name == XCFMapping::PARTITION) {
				parsePartition(element);
			}else {
				cerr << "Invalid node "<< name.c_str() <<"\n";
				exit(1);
			}

		}
		node = node->NextSibling();
	}
}

void XCFParser::parsePartition(TiXmlElement* partition){
	TiXmlString partitionId (partition->Attribute(XCFMapping::PARTITION_ID));
	TiXmlNode* node = partition->FirstChild();

	while (node != NULL){
		if (node->Type() == TiXmlNode::TINYXML_ELEMENT) {
			TiXmlElement* element = (TiXmlElement*)node;
			TiXmlString name(node->Value());
			if (name == XCFMapping::INSTANCE) {
				TiXmlString instanceId (element->Attribute(XCFMapping::INSTANCE_ID));
				mapStr->insert(pair<string,string>(instanceId.c_str(), partitionId.c_str()));
			}else {
				cerr << "Invalid node "<< name.c_str() <<"\n";
				exit(1);
			}
		}

		node = node->NextSibling();
	}
}
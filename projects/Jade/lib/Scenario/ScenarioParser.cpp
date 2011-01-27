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
@brief Implementation of class ScenarioParser
@author Jerome Gorin
@file ScenarioParser.cpp
@version 1.0
@date 26/01/2011
*/

//------------------------------
#include <iostream>

#include "Jade/Scenario/Event/LoadEvent.h"
#include "Jade/Scenario/Event/PauseEvent.h"
#include "Jade/Scenario/Event/StartEvent.h"
#include "Jade/Scenario/Event/StopEvent.h"
#include "Jade/Scenario/Event/SetEvent.h"
#include "Jade/Scenario/Event/WaitEvent.h"
#include "Jade/TinyXml/TinyStr.h"

#include "ScenarioParser.h"
//------------------------------

using namespace std;

//Initializing xml elements
const char* ScenarioParser::JSC_ROOT = "JSC";
const char* ScenarioParser::JSC_LOAD = "Load";
const char* ScenarioParser::JSC_START = "Start";
const char* ScenarioParser::JSC_STOP = "Stop";
const char* ScenarioParser::JSC_SET = "Set";
const char* ScenarioParser::JSC_WAIT = "Wait";
const char* ScenarioParser::JSC_PAUSE = "Pause";
const char* ScenarioParser::JSC_XDF = "xdf";
const char* ScenarioParser::JSC_IN = "input";
const char* ScenarioParser::JSC_ID = "id";
const char* ScenarioParser::JSC_TIME = "time";
const char* ScenarioParser::JSC_THREADED = "threaded";

ScenarioParser::ScenarioParser(string scFile){
	this->scFile = scFile;
	this->scenario = new Scenario();
}

Scenario* ScenarioParser::parse(){
	TiXmlDocument xmlSc(scFile.c_str());

	/* Parsing XML file error */
	if (!xmlSc.LoadFile()) {
		cerr << "Error : the given file does not exist. \n";
		return NULL;
    }

	// Get the root element node
	TiXmlElement* root_element = xmlSc.RootElement();
	
	// xml document doesn't start with XDF root
	if (TiXmlString(root_element->Value()) != JSC_ROOT){
		cerr << "XML description does not represent a Jade scenario";
		return NULL;
	}
	
	if(!parseEvents(root_element)){
		//Parsing error
		return NULL;
	}
	
	return scenario;
}

bool ScenarioParser::parseEvents(TiXmlElement* root){
	TiXmlNode* node = root->FirstChild();

	while (node != NULL){
		if (node->Type() == TiXmlNode::TINYXML_ELEMENT) {
			TiXmlElement* element = (TiXmlElement*)node;
			TiXmlString name(node->Value());
			Event* curEvent = NULL;

			if (name == JSC_LOAD) {
				curEvent = parseLoadEvent(element);
			}else if (name == JSC_START){
				curEvent = parseStartEvent(element);
			}else if (name == JSC_STOP){
				curEvent = parseStopEvent(element);
			}else if (name == JSC_SET){
				curEvent = parseSetEvent(element);
			}else if (name == JSC_WAIT){
				curEvent = parseWaitEvent(element);
			}else if (name == JSC_PAUSE){
				curEvent = parsePauseEvent(element);
			}else{
				cerr << "Invalid node "<< name.c_str() <<"\n";
				return false;
			}

			if (curEvent == NULL){
				cerr << "Failed to parse the "<< name.c_str() <<" event \n";
				return false;
			}

			scenario->addEvent(curEvent);
		}
		
		node = node->NextSibling();
	}

	//End of parsing
	return true;
}

Event* ScenarioParser::parseLoadEvent(TiXmlElement* loadEvent){
	const char* xdfFile = loadEvent->Attribute(JSC_XDF);
	const char* id = loadEvent->Attribute(JSC_ID);

	return new LoadEvent(string(xdfFile), atoi(id));
}

Event* ScenarioParser::parseStartEvent(TiXmlElement* startEvent){
	const char* id = startEvent->Attribute(JSC_ID);
	const char* threaded = startEvent->Attribute(JSC_THREADED);
	const char* input = startEvent->Attribute(JSC_IN);

	return new StartEvent(atoi(id), string(input), atoi(threaded));
}

Event* ScenarioParser::parseStopEvent(TiXmlElement* startEvent){
	const char* id = startEvent->Attribute(JSC_ID);

	return new StopEvent(atoi(id));
}

Event* ScenarioParser::parseSetEvent(TiXmlElement* setEvent){
	const char* id = setEvent->Attribute(JSC_ID);
	const char* xdfFile = setEvent->Attribute(JSC_XDF);

	return new SetEvent(atoi(id), string(xdfFile));
}

Event* ScenarioParser::parseWaitEvent(TiXmlElement* waitEvent){
	const char* time = waitEvent->Attribute(JSC_TIME);

	return new WaitEvent(atoi(time));
}

Event* ScenarioParser::parsePauseEvent(TiXmlElement* waitEvent){
	return new PauseEvent();
}
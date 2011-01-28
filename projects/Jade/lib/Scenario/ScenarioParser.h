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
@brief Description of the ScenarioParser class interface
@author Jerome Gorin
@file ScenarioParser.h
@version 1.0
@date 26/01/2011
*/

//------------------------------
#ifndef SCENARIOPARSER_H
#define SCENARIOPARSER_H
#include <string>

#include "Jade/TinyXml/TinyXml.h"

#include "Scenario.h"
//------------------------------

/**
 * @brief  This class represents a parser of a scenario.
 * 
 * @author Jerome Gorin
 * 
 */
class ScenarioParser {
public:
	/*!
     *  @brief Constructor
     *
	 * Creates a new scenario parser.
	 *
	 * @param scFile : the scenario file to parse.
     */
	ScenarioParser(std::string scFile);

	/*!
     *  @brief Start the manager
     *
	 * Start the manager with the given scenario.
	 *
	 * @param scFile : the file that contains a scenario.
	 *
	 * @return true if scenario finished correctly, otherwise false
     */
	Scenario* parse();

	/*!
     *  @brief Destructor
     *
	 * Delete the scenario parser.
     */
	~ScenarioParser(){};

private:
	/*!
     *  @brief Parses the events of a scenario.
	 *
     *  @param root : TiXmlElement representation of event elements
     */
	bool parseEvents(TiXmlElement* root);

	/*!
     *  @brief Parses the given TiXmlElement as a Load event.
	 *
     *  @param loadEvent : TiXmlElement representation of LoadEvent element 
     */
	Event* parseLoadEvent(TiXmlElement* loadEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Start event.
	 *
     *  @param startEvent : TiXmlElement representation of StartEvent element 
     */
	Event* parseStartEvent(TiXmlElement* startEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Stop event.
	 *
     *  @param stopEvent : TiXmlElement representation of StopEvent element 
     */
	Event* parseStopEvent(TiXmlElement* stopEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Set event.
	 *
     *  @param setEvent : TiXmlElement representation of SetEvent element 
     */
	Event* parseSetEvent(TiXmlElement* setEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Wait event.
	 *
     *  @param waitEvent : TiXmlElement representation of WaitEvent element 
     */
	Event* parseWaitEvent(TiXmlElement* waitEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Pause event.
	 *
     *  @param pauseEvent : TiXmlElement representation of PauseEvent element 
     */
	Event* parsePauseEvent(TiXmlElement* pauseEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Verify event.
	 *
     *  @param pauseEvent : TiXmlElement representation of VerifyEvent element 
     */
	Event* parseVerifyEvent(TiXmlElement* verifyEvent);

	/*!
     *  @brief Parses the given TiXmlElement as a Print event.
	 *
     *  @param printEvent : TiXmlElement representation of PrintEvent element 
     */
	Event* parsePrintEvent(TiXmlElement* verifyEvent);

	/** Xml elements of Scenario */
	static const char* JSC_ROOT;
	static const char* JSC_LOAD;
	static const char* JSC_START;
	static const char* JSC_STOP;
	static const char* JSC_SET;
	static const char* JSC_WAIT;
	static const char* JSC_PAUSE;
	static const char* JSC_PRINT;
	static const char* JSC_VERIFY;
	static const char* JSC_XDF;
	static const char* JSC_ID;
	static const char* JSC_IN;
	static const char* JSC_TIME;
	static const char* JSC_THREADED;
	static const char* JSC_OUT;


	/** File to parse */
	std::string scFile;

	/** Resulting scenario */
	Scenario* scenario;
};

#endif
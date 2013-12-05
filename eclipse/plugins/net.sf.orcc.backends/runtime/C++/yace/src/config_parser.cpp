/**
 * \file
 * \author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * \version 1.0
 *
 * \section LICENSE
 *
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
 * 
 * \section DESCRIPTION
 *
 */

#include <iostream>
#include <sstream>
#include <algorithm>

#include "config_parser.h"

ConfigParser::ConfigParser(std::string& filename, std::map<std::string, Actor*>& actors) 
	: filename(filename)
	, actors(actors)
{
	if(filename.empty())
	{
		Scheduler* scheduler = new Scheduler(thread_pool.done);
		std::map<std::string, Actor*>::iterator it;

		for(it = actors.begin(); it != actors.end(); it++)
		{
			scheduler->add(it->second);
		}
		thread_pool.addWorker(scheduler);
	}
	else
	{
		parse();
	}
}

ConfigParser::~ConfigParser()
{
}

void ConfigParser::parseInstances(TiXmlElement* parent)
{
	Scheduler* scheduler = new Scheduler(thread_pool.done);
	for(TiXmlNode* child = parent->FirstChild("Instance"); child != NULL; child = child->NextSiblingElement("Instance"))
	{
		TiXmlElement* elt = child->ToElement();
		std::string instId = std::string(elt->Attribute("id"));
		scheduler->add(this->actors[instId]);
	}
	thread_pool.addWorker(scheduler);
}

void ConfigParser::parsePartitions(TiXmlElement* parent)
{
	for(TiXmlElement* child = parent->FirstChildElement("Partition"); child != NULL; child = child->NextSiblingElement("Partition"))
	{
		id = std::string(child->Attribute("id"));
		parseInstances(child);
	}
}

void ConfigParser::parse()
{
	TiXmlDocument doc(filename.c_str());
	doc.LoadFile();
	TiXmlElement* configElt = doc.FirstChildElement("Configuration");
	TiXmlElement* elt = configElt->FirstChildElement("Partitioning");
	parsePartitions(elt);
}


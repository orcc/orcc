/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne / AKATECH SA
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
 *   * Neither the name of theEcole Polytechnique Fédérale de Lausanne / AKATECH SA nor the names of its
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

#define _CRT_SECURE_NO_WARNINGS

#include "traceaka.h"

#include "stringaka.h"

TraceAka::TraceAka()
{
	m_oMapFile.clear();
	m_poFileList.clear();
//	m_oMapMsgList.clear();

	m_uDescId = 0;
	m_uVirtualTime = 0;
}

TraceAka::~TraceAka()
{

#if 0
	std::map<unsigned, std::list<std::string>*>::iterator itMsg=m_oMapMsgList.begin();
	while (itMsg != m_oMapMsgList.end())
	{
		FILE* poFile = NULL;
		std::map<unsigned,FILE*>::iterator itTokens=m_oMapFile.find(itMsg->first);
		if (itTokens == m_oMapFile.end())
		{
			printf("Severe Error.........................!!!!!!!!!!\n");
			break;
		}
		else
		{
			poFile = itTokens->second;
		}

		unsigned uCount = (unsigned)((itMsg->second)->size());
		for(unsigned i=0; i<uCount; i++)
		{
			fprintf(poFile, ((itMsg->second)->front()).c_str());
			(itMsg->second)->pop_front();
		}
		fflush(poFile);
		(itMsg->second)->clear();
		delete(itMsg->second);
		itMsg++;
	}
#endif

	std::map<unsigned,FILE*>::iterator itTokens=m_oMapFile.begin();
	while (itTokens != m_oMapFile.end())
	{
		fclose(itTokens->second);
		itTokens++;
	}

	std::map<unsigned,FILE*>::iterator itDesc=m_poFileList.begin();
	while (itDesc != m_poFileList.end())
	{
		fclose(itDesc->second);
		itDesc++;
	}

	m_oMapFile.clear();
	m_poFileList.clear();
//	m_oMapMsgList.clear();
}


unsigned TraceAka::createFileDescriptor(std::string &fileName)
{
	FILE *poFile = NULL;
	unsigned uIdx = m_uDescId;

	std::map<unsigned,FILE*>::iterator it=m_poFileList.find(uIdx);
	if (it == m_poFileList.end())
	{
		std::string strDescName("./descriptors/");
		strDescName.append(toString(uIdx));
		strDescName.append(".txt");
		poFile = fopen(strDescName.c_str(),"wb");
		fprintf(poFile, "%s\n", fileName.c_str());
		fflush(poFile);
		m_poFileList[uIdx] = poFile;
		m_uDescId++;
	}

	return uIdx;
}

// Add a port to the file descriptor
void TraceAka::addPort(unsigned uIdx, std::string &msg)
{
	FILE *poFile = NULL;

	std::map<unsigned,FILE*>::iterator it=m_poFileList.find(uIdx);
	if (it != m_poFileList.end())
	{
		poFile = it->second;
		fprintf(poFile, "%s\n", msg.c_str());
		fflush(poFile);
	}
}

// Capture token
void TraceAka::captureToken(FifoAPI* poFifo, std::string &msg)
{
	FILE *poFile = NULL;
	unsigned uIdx = (unsigned)poFifo;
	std::list<std::string>* poList = NULL;

	std::map<unsigned,FILE*>::iterator it=m_oMapFile.find(uIdx);
	if (it == m_oMapFile.end())
	{
		std::string strName("./tokens/");
		strName.append(toString(uIdx));
		strName.append(".txt");
		poFile = fopen(strName.c_str(),"wb");
		m_oMapFile[uIdx] = poFile;
	}
	else
	{
		poFile = it->second;
	}

#if 0
	std::map<unsigned, std::list<std::string>*>::iterator itMsg=m_oMapMsgList.find(uIdx);
	if (itMsg == m_oMapMsgList.end())
	{
		poList = (std::list<std::string>*)new std::list<std::string>;
		m_oMapMsgList[uIdx]=poList;
	}
	else
	{
		poList = itMsg->second;
	}
#endif

	std::string strTmp(toStringHeightChar(m_uVirtualTime));
	m_uVirtualTime++;
	strTmp.append("\t");
	strTmp.append(msg);
	strTmp.append("\n");
#if 0
	poList->push_back(strTmp);
	if ((unsigned)poList->size() >= TRACE_CACHE_THRESHOLD)
	{
		unsigned uCount = (unsigned)poList->size();
		for(unsigned i=0; i<uCount;i++)
		{
			fprintf(poFile, (poList->front()).c_str());
			poList->pop_front();
		}
		fflush(poFile);
	}
#else
	fprintf(poFile, strTmp.c_str());
	fflush(poFile);
#endif
}

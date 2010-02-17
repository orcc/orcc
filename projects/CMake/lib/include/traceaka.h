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

#ifndef __TRACE_AKA_H__
#define __TRACE_AKA_H__

#include "fifoapi.h"

#include <map>
#include <string>
#include <list>

#define TRACE_CACHE_THRESHOLD 1000
#define TRACE_FIFO_SIZE (TRACE_CACHE_THRESHOLD<<2)

#define TRACE_MSG_MAX_LENGTH 1024

class TraceAka
{
public:
	TraceAka();
	~TraceAka();


	// Created an actor port descriptor (return an index on the created descriptor)
	unsigned createFileDescriptor(std::string &msg);

	// Add a port to the file descriptor
	void addPort(unsigned uIdx, std::string &msg);

	// Capture token
	void captureToken(FifoAPI* poFifo, std::string &msg);


private:

	// Files Descriptors (Keys correspond to names of the file of the actor source code and pointers to the associated ports descriptors)
	std::map<unsigned,FILE*> m_poFileList;

	// Tokens Map (Keys correspond to Fifo @ and pointers to the associatred File of captured tokens)
	std::map<unsigned,FILE*> m_oMapFile;


	unsigned m_uDescId;

	unsigned m_uVirtualTime;

//	std::map<unsigned, std::list<std::string>*> m_oMapMsgList;

};


#endif

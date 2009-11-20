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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne / AKATECH SA nor the names of its
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
#ifndef __ACTOR_AKA_H__
#define __ACTOR_AKA_H__

#include "fifoapi.h"
#include "fifoFanOut.h"

#ifdef __TRACE_TOKENS__
#include "traceaka.h"
#include "stringaka.h"
extern TraceAka g_oTracer;
#endif


typedef enum
{
	ACTOR_PORT_IN = 0,
	ACTOR_PORT_OUT = 1
} ACTOR_PORT_DIR;

class ActorGen
{
public:
	ActorGen(unsigned uNbrPortIn, unsigned uNbrPortOut);
	~ActorGen();

	void connect(ACTOR_PORT_DIR ePortDir, unsigned uIdx, FifoAPI *poFifo);

	void startActorExecution();
	void stop();

	// Pointer to input fifos array
	FifoAPI ** m_poTabIn;

	// Pointer to output fifos array
	FifoAPI ** m_poTabOut;

private:
// nothing
	bool m_bStarted;	
	bool m_bExit;

	unsigned m_uNbrPortIn;
	unsigned m_uNbrPortOut;

	FifoFanOut ** m_poFifoFanOut;

};

#endif

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

#include "actoraka.h"

#include <iostream>

ActorGen::ActorGen(unsigned uNbrPortIn, unsigned uNbrPortOut)
{

	// Allocate Table for Port In addresses
	if (uNbrPortIn != 0)
	{
//		create an array of FifoAPI pointers
		m_poTabIn = new FifoAPI *[uNbrPortIn];
		for(unsigned i=0; i<uNbrPortIn; i++)
		{
			m_poTabIn[i]=NULL;
		}
	}
	else
	{
		m_poTabIn=NULL;
	}
	// Allocate Table for Port In addresses
	if (uNbrPortOut != 0)
	{
//		create an array of FifoAPI pointers
		m_poTabOut = new FifoAPI *[uNbrPortOut];
		for(unsigned i=0; i<uNbrPortOut; i++)
		{
			m_poTabOut[i]=NULL;
		}
	}
	else
	{
		m_poTabOut=NULL;
	}
	m_uNbrPortIn = uNbrPortIn;
	m_uNbrPortOut = uNbrPortOut;
	m_bStarted = false;
	m_bExit = false;
	
	if (uNbrPortOut != 0)
	{
//		create an array of FifoFanOut pointers
		m_poFifoFanOut  = new FifoFanOut *[uNbrPortOut];

		for(unsigned i = 0; i < uNbrPortOut; i++)
		{
			m_poFifoFanOut[i]=NULL;
		}
	}
}

ActorGen::~ActorGen()
{
	if (m_poTabIn != NULL)
	{
		for(unsigned i=0; i<m_uNbrPortIn; i++)
		{
			m_poTabIn[i]=NULL;
		}
		delete [] m_poTabIn;
	}
	if (m_poTabOut != NULL)
	{
		for(unsigned i=0; i<m_uNbrPortOut; i++)
		{
			m_poTabOut[i]=NULL;

		}
		delete [] m_poTabOut;
	}
	if (m_poFifoFanOut != NULL)
	{
		for(unsigned i=0; i<m_uNbrPortOut; i++)
		{
			m_poFifoFanOut[i]=NULL;
		}
		delete [] m_poFifoFanOut;
	}
}

void ActorGen::connect(ACTOR_PORT_DIR ePortDir, unsigned uIdx, FifoAPI *poFifo)
{
	// TODO: verify that uIdx does not exceed Tab size

	// Connect input port to Fifo
	if (ePortDir == ACTOR_PORT_IN)
	{
		m_poTabIn[uIdx]=poFifo;
	}
	// Connect output port to Fifo
	else
	{
		// If no port was already connected to the fifo 
		if (m_poTabOut[uIdx] == NULL)
		{
			m_poTabOut[uIdx]=poFifo;
		}
		// there is already one port connected to the fifo -> fan out required
		else
		{
			// if the module that manage the Fan Out does not exist then create it
			if ( m_poFifoFanOut[uIdx] == NULL)
			{
				// Create Fan Out manager
				m_poFifoFanOut[uIdx] = new FifoFanOut();
				// Backup previous port that was connected to the fifo in the module
				m_poFifoFanOut[uIdx]->addOut(m_poTabOut[uIdx]);
				// Now the port is connected to the fifo throught the Fan Out Manager
				m_poTabOut[uIdx]=m_poFifoFanOut[uIdx];
			}
			// Save the new connection
			m_poFifoFanOut[uIdx]->addOut(poFifo);
		}
	}
}

void ActorGen::startActorExecution()
{
	m_bStarted = true;
	m_bExit = false;
}

void ActorGen::stop()
{
	m_bExit = true;
	for (unsigned i = 0; i < m_uNbrPortIn; i++)
	{
//		m_poTabIn[i]->cancel();
	}
	for (unsigned i = 0; i < m_uNbrPortOut; i++)
	{
//		m_poTabOut[i]->cancel();
	}
	m_bStarted = false;
}

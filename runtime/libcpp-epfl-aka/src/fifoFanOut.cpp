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

#include "fifoFanOut.h"

FifoFanOut::FifoFanOut()
{
}


FifoFanOut::~FifoFanOut()
{
	m_oVectFifo.clear();
}

void FifoFanOut::put(void *pVal)
{
	std::vector <FifoAPI *>::iterator it = m_oVectFifo.begin();
	while(it != m_oVectFifo.end())
	{
		(*it)->put(pVal);
		it++;
	}
}


void FifoFanOut::put(void *pVal, unsigned uNbVal)
{
	std::vector <FifoAPI *>::iterator it = m_oVectFifo.begin();
	while(it != m_oVectFifo.end())
	{
		(*it)->put(pVal, uNbVal);
		it++;
	}
}


void FifoFanOut::get(void *pVal)
{
	// Force an exception while this method should not be called
	_ASSERT(0);
}

void FifoFanOut::get(void *pVal, unsigned uNbVal)
{
	// Force an exception while this method should not be called
	_ASSERT(0);
}


void FifoFanOut::peek(void *pVal)
{
	// Force an exception while this method should not be called
	_ASSERT(0);
}

void FifoFanOut::peek(void *pVal, unsigned uNbVal)
{
	// Force an exception while this method should not be called
	_ASSERT(0);
}


/*void FifoFanOut::cancel()
{
	std::vector <FifoAPI *>::iterator it = m_oVectFifo.begin();
	while(it != m_oVectFifo.end())
	{
		(*it)->cancel();
		it++;
	}
}*/


void FifoFanOut::addOut(FifoAPI *poFifo)
{
	m_oVectFifo.push_back(poFifo);
}


/*void FifoFanOut::getCountDataAvailable(unsigned &pVal)
{
	// Force an exception while this method should not be called
	_ASSERT(0);
}*/


/*void FifoFanOut::getCountRoomAvailable(unsigned &pVal)
{
	// Force an exception while this method should not be called
	_ASSERT(0);
}*/


bool FifoFanOut::hasRooms(unsigned uNbVal)
{
 // Return false if one of the FIFOs has not rooms
	bool bRet = true;
	std::vector <FifoAPI *>::iterator it = m_oVectFifo.begin();
	while(it != m_oVectFifo.end())
	{
		bRet &= (*it)->hasRooms(uNbVal);
		it++;
	}
	return bRet;
}


bool FifoFanOut::hasTokens(unsigned pVal)
{
		// Force an exception while this method should not be called
	_ASSERT(0);
	return false;
}

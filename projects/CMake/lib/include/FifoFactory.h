/*
 * Copyright (c) 2010, Ecole Polytechnique Fédérale de Lausanne / AKATECH SA
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
#ifndef __FIFO_FACTORY__
#define __FIFO_FACTORY__

#include "fifoapi.h"
#include "CalOrientedBoundedFifo.h"

#include <vector>
 enum
{
	FifoFactNoLock = 0,
	FifoFactLock
};

template <typename T>
class FifoFactory
{

public:

	static FifoAPI * allocateFifo(unsigned uSize, unsigned uFifoModel);

	static void deallocateFifo(FifoAPI * poFifo);
	
};

static std::vector<FifoAPI *> m_oVecFifoList;

template <typename T>
FifoAPI * FifoFactory<T>::allocateFifo(unsigned uSize, unsigned uFifoModel)
{
	FifoAPI * poFifo = NULL;
	switch(uFifoModel)
	{
		case FifoFactNoLock:
			poFifo =  (FifoAPI *)new CalOrientedBoundedFifo<T>(uSize);
			m_oVecFifoList.push_back(poFifo);
			break;
	}
	return poFifo;
}

template <typename T>
void FifoFactory<T>::deallocateFifo(FifoAPI * poFifo)
{
	std::vector<FifoAPI *>::iterator it = m_oVecFifoList.begin();
	
	for(it; it != m_oVecFifoList.end(); ++it) 
	{
		if(*it == poFifo) {
			m_oVecFifoList.erase(it);
			delete(poFifo);
			it = m_oVecFifoList.end();
		}
	}
}

#endif
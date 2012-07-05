/**
 * \file
 * \author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * \version 1.0
 *
 * \section LICENSE
 *
 * Copyright (c) 2012, EPFL
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

#ifndef __YACE_PORT_H__
#define __YACE_PORT_H__

#include <vector>

#include "fifo.h"


template<typename T>
struct NullPort
{
	T* getRdPtr(unsigned int=0) { return 0; }
	T* getWrPtr() { return 0; }
	void incRdPtr(unsigned int=0) {}
	void incWrPtr(T*, unsigned int=0) {}
	unsigned int getCount() { return 0; }
	unsigned int getRooms() { return 0; }
};

template<typename T>
struct PortIn
{
	void operator() (Fifo<T>* pfifo)  {  this->pfifo = pfifo; }

	T* getRdPtr() { return  pfifo->getRdPtr(); }

	T* getRdPtr(unsigned int val) { return pfifo->getRdPtr(val); }

	void release() { pfifo->incRdPtr(); }

	void release(unsigned int val) { pfifo->incRdPtr(val); }

	unsigned int getCount() { return pfifo->getCount(); }

	Fifo<T>* pfifo;
};

template<typename T, int num>
struct PortOut
{
	void operator() (Fifo<T>* pfifo) { fifos.push_back(pfifo); }

	T* getWrPtr() { return  fifos[0]->getWrPtr(); }

	void release(T* buf) 
	{
		fifos[0]->incWrPtr();
		for(int i = 1; i<num; i++)
		{
			fifos[i]->put(buf);
		}
	}

	void release(T* buf, unsigned int val)
	{
		fifos[0]->incWrPtr(val);
		for(int i = 1; i<num; i++)
		{
			fifos[i]->put(buf, val);
		}
	}

	unsigned int getRooms()
	{
		int rooms = fifos[0]->getRooms();
		for(int i = 1; i<num; i++)
		{
			int room = fifos[i]->getRooms();
			rooms = (rooms < room) ? rooms : room;
		}
		return rooms;
	}

	std::vector<Fifo<T>* > fifos;
};

/*! 
 *  partial specialization for a single connected fifo
 */
template<typename T>
struct PortOut<T, 1>
{
	void operator() (Fifo<T>* pfifo) { this->pfifo = pfifo; }

	T* getWrPtr() { return pfifo->getWrPtr(); }

	void release(T* buf) { pfifo->incWrPtr(); }

	void release(T* buf, unsigned int val)	{ pfifo->incWrPtr(val); }

	unsigned int getRooms() { return pfifo->getRooms(); }

	Fifo<T>* pfifo;
};

#endif


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


template<typename T, int size=4096>
struct NullPort
{
	T* getRdPtr(unsigned int=0) { return 0; }
	T* getWrPtr() { return 0; }
	void incRdPtr(unsigned int=0) {}
	void incWrPtr(T*, unsigned int=0) {}
	unsigned int getCount() { return 0; }
	unsigned int getRooms() { return 0; }
};

template<typename T, int size=4096>
struct PortIn
{
	void operator() (Fifo<T, size>* pfifo)  {  this->pfifo = pfifo; }

	T* getRdPtr() { return  pfifo->getRdPtr(); }

	T* getRdPtr(unsigned int val) { return pfifo->getRdPtr(val); }

	void incRdPtr() { pfifo->incRdPtr(); }

	void incRdPtr(unsigned int val) { pfifo->incRdPtr(val); }

	unsigned int getCount() { return pfifo->getCount(); }

	Fifo<T, size>* pfifo;
};

template<typename T, int num, int size=4096>
struct PortOut
{
	void operator() (Fifo<T, size>* pfifo) { fifos.push_back(pfifo); }

	T* getWrPtr() { return  fifos[0]->getWrPtr(); }

	void incWrPtr(T* buf) 
	{
		fifos[0]->incWrPtr();
		for(int i = 1; i<num; i++)
		{
			fifos[i]->put(buf);
		}
	}

	void incWrPtr(T* buf, unsigned int val)
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

	std::vector<Fifo<T, size>* > fifos;
};

/*! 
 *  partial specialization for a single connected fifo
 */
template<typename T, int size>
struct PortOut<T, 1, size>
{
	void operator() (Fifo<T, size>* pfifo) { this->pfifo = pfifo; }

	T* getWrPtr() { return pfifo->getWrPtr(); }

	void incWrPtr(T* buf) { pfifo->incWrPtr(); }

	void incWrPtr(T* buf, unsigned int val)	{ pfifo->incWrPtr(val); }

	unsigned int getRooms() { return pfifo->getRooms(); }

	Fifo<T, size>* pfifo;
};

/*! 
 *  partial specialization for 2 connected fifos
 */
template<typename T, int size>
struct PortOut<T, 2, size>
{
	void operator() (Fifo<T, size>* pfifo) { fifos.push_back(pfifo); }

	T* getWrPtr() { return  fifos[0]->getWrPtr(); }

	void incWrPtr(T* buf) 
	{
		fifos[0]->incWrPtr();
		fifos[1]->put(buf);
	}

	void incWrPtr(T* buf, unsigned int val)
	{
		fifos[0]->incWrPtr(val);
		fifos[1]->put(buf, val);
	}

	unsigned int getRooms()
	{
		int rooms0 = fifos[0]->getRooms();
		int rooms1 = fifos[1]->getRooms();
		return (rooms0 < rooms1) ? rooms0 : rooms1;
	}

	std::vector<Fifo<T, size>* > fifos;
};

/*! 
 *  partial specialization for 4 connected fifos
 */
template<typename T, int size>
struct PortOut<T, 4, size>
{
	void operator() (Fifo<T, size>* pfifo) { fifos.push_back(pfifo); }

	T* getWrPtr() { return  fifos[0]->getWrPtr(); }

	void incWrPtr(T* buf) 
	{
		fifos[0]->incWrPtr();
		fifos[1]->put(buf);
		fifos[2]->put(buf);
		fifos[3]->put(buf);
	}

	void incWrPtr(T* buf, unsigned int val)
	{
		fifos[0]->incWrPtr(val);
		fifos[1]->put(buf, val);
		fifos[2]->put(buf, val);
		fifos[3]->put(buf, val);
	}

	unsigned int getRooms()
	{
		int rooms0 = fifos[0]->getRooms();
		int rooms1 = fifos[1]->getRooms();
		int rooms2 = fifos[2]->getRooms();
		int rooms3 = fifos[3]->getRooms();
		rooms0 = (rooms0 < rooms1) ? rooms0 : rooms1;
		rooms0 = (rooms0 < rooms2) ? rooms0 : rooms2;
		rooms0 = (rooms0 < rooms3) ? rooms0 : rooms3;
		return rooms0;
	}

	std::vector<Fifo<T, size>* > fifos;
};

#endif

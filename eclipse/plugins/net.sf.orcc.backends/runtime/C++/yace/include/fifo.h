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
#ifndef __YACE_FIFO_H__
#define __YACE_FIFO_H__

#include <string.h>

#include "barrier.h"

/*! \class Fifo fifo.h
 *  \brief A template class that implements a non-bocking ring buffer.
 */
template <typename T, int nb_reader>
class Fifo
{
public:
	Fifo(int size=4096, int threshold=4096);
	~Fifo();

	T* write_address() const;

	void write_advance();

	void write_advance(unsigned int nb_data);

	T* read_address(int reader_id) const;

	T* read_address(int reader_id, unsigned nb_data);

	void read_advance(int reader_id, unsigned int nb_data=1);
	
	unsigned int count(int reader_id) const
	{
		return (size + wr_ptr - rd_ptr[reader_id]) & (size - 1);
	}

	unsigned int rooms() const
	{
		unsigned int min_rooms = 0xFFFFFFFF;
		for (int i = 0; i < nb_reader; i++) {
			unsigned int rooms = (size + rd_ptr[i] - wr_ptr - 1) & (size - 1);
			min_rooms = min_rooms < rooms ? min_rooms : rooms;
		}
		return min_rooms;
	}

private:
	T * buffer;

	unsigned int rd_ptr[nb_reader];

	unsigned int wr_ptr;

	unsigned int size;
};


template <typename T, int nb_reader>
Fifo<T, nb_reader>::Fifo(int size, int threshold)
	: buffer(new T[size + threshold])
	, wr_ptr(0)
	, size(size) 
{
	for(int i=0; i<nb_reader; i++)
		rd_ptr[i] = 0;
}

template <typename T, int nb_reader>
Fifo<T, nb_reader>::~Fifo()
{
	delete [] buffer;
}

template <typename T, int nb_reader>
inline T* Fifo<T, nb_reader>::write_address() const
{
	return buffer + wr_ptr;
}

template <typename T, int nb_reader>
void Fifo<T, nb_reader>::write_advance()
{
	++ wr_ptr;
	wr_ptr &= (size - 1);
}

template <typename T, int nb_reader>
void Fifo<T, nb_reader>::write_advance(unsigned int nb_val)
{
	int rest = wr_ptr + nb_val - size;
	if(rest > 0)
	{
		memcpy(buffer, buffer + size, rest*sizeof(T));
	}
	wr_ptr += nb_val;
	wr_ptr &= (size - 1);
}

template <typename T, int nb_reader>
inline T* Fifo<T, nb_reader>::read_address(int reader_id) const
{
	return buffer + rd_ptr[reader_id];
}

template <typename T, int nb_reader>
inline T* Fifo<T, nb_reader>::read_address(int reader_id, unsigned uNbVal)
{
	T * pVal = buffer + rd_ptr[reader_id];
	int rest = rd_ptr[reader_id] + uNbVal - size;
	if(rest > 0)
	{
		memcpy(buffer + size, buffer, rest*sizeof(T)); 
	} 
	return pVal;
}

template <typename T, int nb_reader>
void Fifo<T, nb_reader>::read_advance(int reader_id, unsigned int nb_val)
{
	rd_ptr[reader_id] += nb_val;
	rd_ptr[reader_id] &= (size - 1);
}

#endif


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
template <typename T>
class Fifo
{
public:
	/*! \brief Constructor
	 */
	Fifo(int size=4096, int threshold=4096);

	/*! \brief Destructor
	 */
	~Fifo();

	/*! \brief copy of one data element from pBuf to buffer + wr_ptr
	 *  \param[in] pBuf
	 */
	void put(T pBuf[]);

	/*! \brief copy of nb_data element from pBuf to buffer + wr_ptr
	 *  \param[in] pBuf The buffer to copy
	 *  \param[in] nb_data The number of data to copy
	 */
	void put(T pBuf[], unsigned int nb_data);

	/*! \brief get the write pointer
	 *  \return The write pointer
	 */
	T* getWrPtr() const;

	/*! \brief increment by one the write pointer
	 */
	void incWrPtr();

	/*! \brief increment by nb_data the write pointer
	 *  \param[in] nb_data The number of data to increment
	 */
	void incWrPtr(unsigned int nb_data);

	/*! \brief get the read pointer
	 *  \return The read pointer
	 */
	T* getRdPtr() const;

	/*! \brief get the read pointer
	 *  \param[in] nb_data posible copy to the 
	 *  \return The read pointer
	 */
	T* getRdPtr(unsigned nb_data);

	/*! \brief increment the write counter
	 */
	void incRdPtr();

	/*! \brief increment the write counter
	 *  \return The write counter
	 */
	void incRdPtr(unsigned int nb_data);
	
	/*! \brief get the number of available data
	 *  \return The number of available data elements in the ring buffer
	 */
	unsigned int getCount() const { return (size + wr_ptr - rd_ptr) & (size - 1); }
	
	/*! \brief get the number of available rooms
	 *	keep one slot empty for full/empty disambiguation
	 *  \return The number of available rooms in the ring buffer
	 */
	unsigned int getRooms() const { return (size + rd_ptr - wr_ptr - 1) & (size - 1); }

private:
	T * buffer; /*!< start address of the ring buffer*/

	unsigned int rd_ptr; /*!< read counter*/

	unsigned int wr_ptr; /*!< write counter*/

	unsigned int size;
};


template <typename T>
Fifo<T>::Fifo(int size, int threshold)
	: buffer(new T[size + threshold])
	, rd_ptr(0)
	, wr_ptr(0)
	, size(size) 
{
	if((size) & (size - 1) != 0) {
		// size is not a power of 2
	}
}

template <typename T> 
Fifo<T>::~Fifo()
{
	delete [] buffer;
}

template <typename T>
inline T* Fifo<T>::getWrPtr() const
{
	return buffer + wr_ptr;
}

template <typename T>
void Fifo<T>::incWrPtr()
{
	++ wr_ptr;
	wr_ptr &= (size - 1);
}

template <typename T>
void Fifo<T>::incWrPtr(unsigned int nb_val)
{
	if((wr_ptr + nb_val) > size)
	{
		memcpy(buffer, buffer + size, (wr_ptr + nb_val - size) * sizeof(T));
	}
	wr_ptr += nb_val;
	wr_ptr &= (size - 1);
}

template <typename T>
inline T* Fifo<T>::getRdPtr() const
{
	return buffer + rd_ptr;
}

template <typename T>
inline T* Fifo<T>::getRdPtr(unsigned uNbVal)
{
	T * pVal = buffer + rd_ptr;
	if((rd_ptr + uNbVal) > size)
	{
		memcpy(buffer + size, buffer, (rd_ptr + uNbVal - size) * sizeof(T)); 
	} 
	return pVal;
}

template <typename T>
void Fifo<T>::incRdPtr()
{
	++rd_ptr;
	rd_ptr &= (size - 1);
}

template <typename T>
void Fifo<T>::incRdPtr(unsigned int nb_val)
{
	rd_ptr += nb_val;
	rd_ptr &= (size - 1);
}

template <typename T> 
inline void Fifo<T>::put(T * pVal)
{
	buffer[wr_ptr] = *pVal;
	++wr_ptr;
	wr_ptr &= (size - 1);
}

template <typename T> 
inline void Fifo<T>::put(T * pVal, unsigned uNbVal) 
{
	if((wr_ptr + uNbVal) > size) 
	{
		unsigned int head = size - wr_ptr;
		memcpy(buffer + wr_ptr, pVal, head * sizeof(T)); 
		memcpy(buffer, pVal + head, (uNbVal - head) * sizeof(T)); 
	} 
	else 
	{
		memcpy(buffer + wr_ptr, pVal, uNbVal * sizeof(T));
	}
	wr_ptr += uNbVal;
	wr_ptr &= (size - 1);
}

#endif

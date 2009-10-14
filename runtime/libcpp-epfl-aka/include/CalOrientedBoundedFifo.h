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

template <typename T>
class CalOrientedBoundedFifo: public FifoAPI
{

public:
	CalOrientedBoundedFifo(unsigned uSize = FIFO_SIZE);
	~CalOrientedBoundedFifo();

	void init();
	void release();


	void put(void * pBuf);
	void put(void * pBuf, unsigned uNbVal);
	void get(void * pBuf);	
	void get(void * pBuf, unsigned uNbVal);	
	void peek(void * pVal);
	void peek(void * pVal, unsigned uNbVal);

	bool hasTokens(unsigned uNbVal = 1);
	bool hasRooms(unsigned uNbVal = 1);

private:
	bool m_bInitiliazed;

	T *m_poFifo;
	T* m_poFifoPut;
	T* m_poFifoGet;
	T* m_poFifoEnd;
	
	unsigned m_uSize;
	unsigned m_uCount;
};

//////////////////////////////////////////////////////////////////////////
// CalOrientedBoundedFifo

template <typename T>
CalOrientedBoundedFifo<T>::CalOrientedBoundedFifo( unsigned uSize )
{
	m_poFifo	= NULL;
	m_poFifoPut = NULL;
	m_poFifoGet = NULL;
	m_poFifoEnd = NULL;

	m_bInitiliazed	= false;
	m_uSize = uSize;
	
	m_uCount = 0;

	init();
}

template <typename T> 
CalOrientedBoundedFifo<T>::~CalOrientedBoundedFifo(void)
{
	release();
}

template <typename T> 
void CalOrientedBoundedFifo<T>::init()
{	
	if (m_bInitiliazed	== true)
	{
		return;
	}


	// Allocate Memory
	m_poFifo = (T *)new T[m_uSize];
	if (m_poFifo == NULL)
	{
		release();
	}

	m_poFifoPut = m_poFifo;
	m_poFifoGet = m_poFifo;
	m_poFifoEnd = m_poFifo + m_uSize;


	m_bInitiliazed	= true;
}

template <typename T> 
void CalOrientedBoundedFifo<T>::release()
{
	// Free memory
	if (m_poFifo != NULL)
	{
		delete [] m_poFifo;
		m_poFifo = NULL;
		m_poFifoPut = m_poFifo;
		m_poFifoGet = m_poFifo;
		m_poFifoEnd = NULL;
	}

	m_bInitiliazed	= false;
}

template <typename T>
inline void CalOrientedBoundedFifo<T>::get(void * poVal)
{
	T * pVal = (T *)poVal;

/*	if( hasTokens(1) == false)
	{
		// TODO: Error
		return;
	}*/

		*pVal = *m_poFifoGet;
		m_poFifoGet ++;
		if(m_poFifoGet >= m_poFifoEnd)
		{
			m_poFifoGet = m_poFifo;
		}
	m_uCount--;

}


template <typename T>
inline void CalOrientedBoundedFifo<T>::get(void * poVal, unsigned int uNbVal)
{
	T * pVal = (T *)poVal;

/*	if( hasTokens(uNbVal) == false)
	{
		// TODO: Error
		return;
	}*/

	// Two case:
	// - There are too few data to get from the end of the circular buffer. The segment should be read in two packets.
	if ( (m_poFifoGet + uNbVal) > m_poFifoEnd)
	{
		unsigned uSubSegmentSize = (unsigned)(m_poFifoEnd - m_poFifoGet);
		memcpy(pVal, m_poFifoGet, uSubSegmentSize * sizeof (T) ); 
		memcpy((pVal+ uSubSegmentSize), m_poFifo, (uNbVal - uSubSegmentSize) * sizeof (T) ); 
		m_poFifoGet = m_poFifo + (uNbVal - uSubSegmentSize);
	}
	// - The are sufficient space to do it in one packet
	else
	{
		memcpy(pVal, m_poFifoGet, uNbVal * sizeof (T) );
		m_poFifoGet += uNbVal;
		if(m_poFifoGet >= m_poFifoEnd)
		{
			m_poFifoGet = m_poFifo;
		}
	}
	m_uCount -= uNbVal;

}

template <typename T> 
inline void CalOrientedBoundedFifo<T>::put(void * poVal)
{
	T * pVal = (T *)poVal;

	if( hasRooms(1) == false)
	{
		// TODO: Error
		return;
	}
	*m_poFifoPut = *pVal;
	m_poFifoPut++;
	if(m_poFifoPut >= m_poFifoEnd)
	{
		m_poFifoPut = m_poFifo;
	}
	m_uCount++;
}


template <typename T> 
inline void CalOrientedBoundedFifo<T>::put(void * poVal, unsigned uNbVal)
{
	T *pVal = (T*)poVal;

/*	if( hasRooms(uNbVal) == false)
	{
		// TODO: Error
		return;
	}*/

	// Two case:
	// - There are too few space to store the Segment to add at the end of the circular buffer (the copy must be done in two packets)
	if ( (m_poFifoPut + uNbVal) > m_poFifoEnd)
	{
		unsigned uSubSegmentSize = (unsigned)(m_poFifoEnd - m_poFifoPut);
		memcpy(m_poFifoPut, pVal, uSubSegmentSize * sizeof(T)); 
		memcpy(m_poFifo, (pVal+ uSubSegmentSize), (uNbVal - uSubSegmentSize) * sizeof(T)); 
		m_poFifoPut = m_poFifo + (uNbVal - uSubSegmentSize);
	}
	// - The are sufficient space to do it in one packet
	else
	{
		memcpy(m_poFifoPut, pVal, uNbVal * sizeof(T));
		m_poFifoPut += uNbVal;
		if(m_poFifoPut >= m_poFifoEnd)
		{
			m_poFifoPut = m_poFifo;
		}
	}
	m_uCount += uNbVal;
}


template <typename T>
inline void CalOrientedBoundedFifo<T>::peek(void * poVal)
{
	T * pVal = (T *)poVal;

/*	if( hasTokens(1) == false)
	{
		// TODO: Error
		return;
	}*/
	*pVal = *m_poFifoGet;
}

template <typename T>
inline void CalOrientedBoundedFifo<T>::peek(void * poVal, unsigned int uNbVal )
{
	T * pVal = (T *)poVal;

	/*if( hasTokens(uNbVal) == false)
	{
		// TODO: Error
		return;
	}*/
	// Two case:
	// - There are too few data to get from the end of the circular buffer. The segment should be read in two packets.
	if ( (m_poFifoGet + uNbVal) > m_poFifoEnd)
	{
		unsigned uSubSegmentSize = (unsigned)(m_poFifoEnd - m_poFifoGet);
		memcpy(pVal, m_poFifoGet, uSubSegmentSize * sizeof (T) ); 
		memcpy((pVal+ uSubSegmentSize), m_poFifo, (uNbVal - uSubSegmentSize) * sizeof (T) ); 
	}
	// - The are sufficient space to do it in one packet
	else
	{
		memcpy(pVal, m_poFifoGet, uNbVal * sizeof (T) );
	}

}


template <typename T>
inline bool CalOrientedBoundedFifo<T>::hasRooms(unsigned uNbVal)
{
	return (m_uSize - m_uCount) >= uNbVal ? true : false;
}

template <typename T>
inline bool CalOrientedBoundedFifo<T>::hasTokens(unsigned uNbVal)
{
	return m_uCount >= uNbVal ? true : false;
}
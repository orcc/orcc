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

#include "condition.h"

Condition::Condition()
{
#ifdef _WIN32
	m_waiters = 0;
	m_event = CreateEvent(NULL, FALSE, FALSE, NULL);
	m_sema = CreateSemaphore(NULL,0, 0x7fffffff,NULL);
#else
	pthread_cond_init(&mHandle, NULL);
#endif
}

Condition::~Condition()
{
#ifdef _WIN32
	CloseHandle(m_event);
	CloseHandle(m_sema);
#else
	pthread_cond_destroy(&mHandle);
#endif
}

void Condition::wait(Mutex& mutex)
{
#ifdef _WIN32
	InterlockedIncrement(&m_waiters);

	mutex.unlock();

	WaitForSingleObject(m_sema, INFINITE);

	InterlockedDecrement(&m_waiters);
	long w = InterlockedExchangeAdd(&m_waiters, 0);
	if (m_was_broadcast && w == 0)
		SetEvent(m_event);

	mutex.lock();
#else
	pthread_cond_wait(&mHandle, mutex.getHandle());
#endif
}

void Condition::signal()
{
#ifdef _WIN32
	long w = InterlockedExchangeAdd(&m_waiters, 0);
	int have_waiters = w > 0;

	if (have_waiters)
		ReleaseSemaphore(m_sema, 1, NULL);
#else
	pthread_cond_signal(&mHandle);
#endif
}

void Condition::broadcast()
{
#ifdef _WIN32
	int have_waiters = 0;
	long w = InterlockedExchangeAdd(&m_waiters, 0);
	if (w > 0)
	{
		m_was_broadcast = 1;
		have_waiters = 1;
	}

	int result = 0;
	if (have_waiters)
	{
		ReleaseSemaphore(m_sema, m_waiters, NULL);
		WaitForSingleObject(m_event, INFINITE) ;
		m_was_broadcast = 0;
	}
#else
	pthread_cond_broadcast(&mHandle);
#endif
}

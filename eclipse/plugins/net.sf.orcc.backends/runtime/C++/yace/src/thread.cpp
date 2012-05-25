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

#include "thread.h"

#include <iostream>
#include <stdlib.h>

#ifdef _WIN32
#include <process.h>
#else
#include <sys/types.h>
#include <unistd.h> 
#endif



#ifdef _WIN32
DWORD Thread::tls;
#else
pthread_key_t Thread::tls;
#endif

Thread::Thread(const int priority) 
	: m_priority(priority)
{
	init();
}

Thread::~Thread()
{
#ifdef _WIN32
	TlsFree(tls);
#endif
}

void Thread::init()
{
#ifdef _WIN32
	tls = TlsAlloc();
#else
    pthread_key_create(&Thread::tls, NULL);
#endif
}

void Thread::cancel()
{
#ifdef _WIN32
	TerminateThread(m_thread, 0);
#else
	pthread_cancel(m_thread);
#endif
}

void Thread::start(void * args)
{
	m_args = args;
#ifdef _WIN32
	m_thread = (HANDLE) _beginthreadex(NULL, 0, Thread::entryPoint, this, 0, &m_tid);
#else
	if (pthread_create(&m_thread, NULL, Thread::entryPoint, this) != 0)
	{
		m_thread = 0;
	}
#endif
}

void Thread::join()
{
#ifdef _WIN32
	WaitForSingleObject(m_thread, INFINITE);
	CloseHandle(m_thread);
#else
	pthread_join(m_thread, NULL);
#endif
}

void Thread::yield()
{
#ifdef _WIN32
	Sleep(0);
#else
	sched_yield();
#endif
}

void Thread::sleep(int ms)
{
#ifdef _WIN32
	Sleep(ms);
#else
	usleep(1000*ms);
#endif
}

#ifdef _WIN32
unsigned WINAPI
#else
void*
#endif
	Thread::entryPoint(void* pthis)
{
	Thread* t = (Thread*) pthis;

#ifdef _WIN32
	TlsSetValue(tls, pthis);
#else
	pthread_setspecific(Thread::tls, pthis);
#endif
	t->run((void*) t->m_args);
	return 0;
}

Thread* Thread::currentThread()
{
#ifdef _WIN32
	return (Thread*) TlsGetValue(tls);
#else
    return (Thread*) pthread_getspecific(Thread::tls);
#endif
}

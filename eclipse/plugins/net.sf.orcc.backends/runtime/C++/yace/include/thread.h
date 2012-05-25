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

#ifndef __YACE_THREAD_H__
#define __YACE_THREAD_H__

#include <string>
#include <stdlib.h>

#ifdef _WIN32
#include <windows.h>
#else
#include <pthread.h>
#include <signal.h>
#include <sched.h>
#include <unistd.h>
enum
{
	THREAD_PRIORITY_BELOW_NORMAL,
	THREAD_PRIORITY_NORMAL,
	THREAD_PRIORITY_ABOVE_NORMAL,
	THREAD_PRIORITY_TIME_CRITICAL,
	THREAD_PRIORITY_HIGHEST
};
#endif


class Thread
{
public:
	Thread(int priority = THREAD_PRIORITY_NORMAL);

	virtual ~Thread() = 0;

	void init();

	virtual void run(void *arg) = 0;

	void join();

	void sleep(int);

	void start(void *);

	void cancel();

	static void yield();

	static Thread* currentThread();

public:
#ifdef _WIN32
	static unsigned WINAPI entryPoint(void* pThreadInfo);

	HANDLE getHandle() const { return m_thread; }

	HANDLE m_thread;

	unsigned m_tid;

	static DWORD tls;

#else
	static void* entryPoint(void* pThreadInfo);

	pthread_t getHandle() const { return m_thread; }

	pthread_t m_thread;

	int m_tid;

	static pthread_key_t tls;
#endif

	int m_priority;

	void* m_args;
};
#endif

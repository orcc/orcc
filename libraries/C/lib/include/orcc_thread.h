/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
#ifndef THREAD_H
#define THREAD_H

#ifdef _WIN32
	#include <windows.h>
	
	// Thread
	typedef long cpu_set_t;
	#define clear_cpu_set(cpuset) cpuset = 0
	#define set_affinity(cpuset, proc_num, thread) SetThreadAffinityMask(thread, proc_num)
	
	#define threadCreate(thread, function, argument, id) thread = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE) function, (LPVOID) &(argument), 0, &(id))
	#define threadJoin(thread) WaitForSingleObject(thread, INFINITE)
	
	#define thread_struct HANDLE
	#define thread_id_struct DWORD
	
	// Semaphore
	#define MAX_SEM_COUNT 10
	
	#define semaphoreCreate(semaphore, number) semaphore = CreateSemaphore(NULL, number, MAX_SEM_COUNT, NULL)
	#define semaphoreWait(semaphore) WaitForSingleObject(semaphore, INFINITE)
	#define semaphoreSet(semaphore) ReleaseSemaphore(semaphore, 1, NULL)
	#define semaphoreDestroy(semaphore) CloseHandle(semaphore)
	
	#define semaphore_struct HANDLE
	
	
#else
	#include <pthread.h>
	#include <semaphore.h>
	
	// Thread
	#define clear_cpu_set(cpuset) CPU_ZERO(&(cpuset))
	#define set_affinity(cpuset, proc_num, thread) {						\
			CPU_SET(proc_num, &(cpuset));									\
			pthread_setaffinity_np(thread, sizeof(cpu_set_t), &(cpuset));	\
	}

	
	#define threadCreate(thread, function, argument, id) id = pthread_create(&(thread), NULL, function, (void *) &(argument))
	#define threadJoin(thread) pthread_join(thread, NULL);
	
	#define thread_struct pthread_t
	#define thread_id_struct int
	
	// Semaphore
	#define semaphoreCreate(semaphore, number) sem_init(&(semaphore), 0, (number))
	#define semaphoreWait(semaphore) sem_wait(&(semaphore))
	#define semaphoreSet(semaphore) sem_post(&(semaphore))
	#define semaphoreDestroy(semaphore) sem_destroy(&(semaphore))
	
	#define semaphore_struct sem_t
#endif


struct sync_s {
	semaphore_struct sem_monitor;
	semaphore_struct sem_threads;
	int active_sync;
};

void sync_init(struct sync_s *sync);

#endif

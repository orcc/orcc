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
	// ok here is some dark Windows magic
	// we must define WIN32_LEAN_AND_MEAN here so that windows.h will NOT include winsock.h
	// because later on we include winsock2.h, which conflicts with winsock.h
	// ...
	// ...
	#define WIN32_LEAN_AND_MEAN
	#include <windows.h>

	// Thread
	typedef long cpu_set_t;
	#define thread_struct HANDLE
	#define thread_id_struct DWORD

	#define clear_cpu_set(cpuset) cpuset = 0

	/**
	 * Sets the affinity of the given thread to the given processor.
	 */
	static void set_thread_affinity(cpu_set_t cpuset, int proc_num, thread_struct hThread) {
		DWORD_PTR dwThreadAffinityMask = 1 << proc_num;
		SetThreadAffinityMask(hThread, dwThreadAffinityMask);
	}

	/**
	 * Sets the affinity of this process to the given processor.
	 */
	static void set_this_process_affinity(cpu_set_t cpuset, int proc_num) {
		HANDLE hProcess = GetCurrentProcess();
		DWORD_PTR dwProcessAffinityMask = 1 << proc_num;
		SetProcessAffinityMask(hProcess, dwProcessAffinityMask);
	}
	
	#define thread_create(thread, function, argument, id) thread = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE) function, (LPVOID) &(argument), 0, &(id))
	#define thread_join(thread) WaitForSingleObject(thread, INFINITE)
	
	// Semaphore
	#define MAX_SEM_COUNT 10
	
	#define semaphore_create(semaphore, number) semaphore = CreateSemaphore(NULL, number, MAX_SEM_COUNT, NULL)
	#define semaphore_wait(semaphore) WaitForSingleObject(semaphore, INFINITE)
	#define semaphore_set(semaphore) ReleaseSemaphore(semaphore, 1, NULL)
	#define semaphore_destroy(semaphore) CloseHandle(semaphore)
	
	#define semaphore_struct HANDLE
	
	
#else
	#include <pthread.h>
	#include <semaphore.h>
	
	// Thread
#ifdef __APPLE__
	typedef long cpu_set_t;
	#define clear_cpu_set(cpuset)
	#define set_thread_affinity(cpuset, proc_num, thread)
	#define set_this_process_affinity(cpuset, proc_num)
#else
	#define clear_cpu_set(cpuset) CPU_ZERO(&(cpuset))
	#define set_thread_affinity(cpuset, proc_num, thread) {					\
			CPU_SET(proc_num, &(cpuset));									\
			pthread_setaffinity_np(thread, sizeof(cpu_set_t), &(cpuset));	\
	}
	#define set_this_process_affinity(cpuset, proc_num) {					\
			CPU_SET(proc_num, &(cpuset));									\
			sched_setaffinity(0, sizeof(cpu_set_t), &(cpuset));				\
	}
#endif

	
	#define thread_create(thread, function, argument, id) id = pthread_create(&(thread), NULL, function, (void *) &(argument))
	#define thread_join(thread) pthread_join(thread, NULL);
	
	#define thread_struct pthread_t
	#define thread_id_struct int
	
	// Semaphore
	#define semaphore_create(semaphore, number) sem_init(&(semaphore), 0, (number))
	#define semaphore_wait(semaphore) sem_wait(&(semaphore))
	#define semaphore_set(semaphore) sem_post(&(semaphore))
	#define semaphore_destroy(semaphore) sem_destroy(&(semaphore))
	
	#define semaphore_struct sem_t
#endif


struct sync_s {
	semaphore_struct sem_monitor;
	int active_sync;
};

void sync_init(struct sync_s *sync);

#endif

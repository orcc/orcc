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
#ifndef _ORCC_THREAD_H_
#define _ORCC_THREAD_H_

#include "orcc.h"

#ifndef _WIN32
#define __USE_GNU
#endif

#define MAX_THREAD_NB 10

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
    #define orcc_thread_t HANDLE
    #define orcc_thread_id_t DWORD

    #define orcc_clear_cpu_set(cpuset) cpuset = 0

    /**
     * Sets the affinity of the given thread to the given processor.
     */
    static void orcc_set_thread_affinity(cpu_set_t cpuset, int proc_num, orcc_thread_t hThread) {
        DWORD_PTR dwThreadAffinityMask = 1 << proc_num;
        SetThreadAffinityMask(hThread, dwThreadAffinityMask);
    }

    /**
     * Sets the affinity of this process to the given processor.
     */
    static void orcc_set_this_process_affinity(cpu_set_t cpuset, int proc_num) {
        HANDLE hProcess = GetCurrentProcess();
        DWORD_PTR dwProcessAffinityMask = 1 << proc_num;
        SetProcessAffinityMask(hProcess, dwProcessAffinityMask);
    }

    #define orcc_thread_create(thread, function, argument, id) thread = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE) function, (LPVOID) &(argument), 0, &(id))
    #define orcc_thread_join(thread) WaitForSingleObject(thread, INFINITE)

    // Semaphore
    #define MAX_SEM_COUNT 10

    #define orcc_semaphore_create(semaphore, number) semaphore = CreateSemaphore(NULL, number, MAX_SEM_COUNT, NULL)
    #define orcc_semaphore_wait(semaphore) WaitForSingleObject(semaphore, INFINITE)
    #define orcc_semaphore_set(semaphore) ReleaseSemaphore(semaphore, 1, NULL)
    #define orcc_semaphore_destroy(semaphore) CloseHandle(semaphore)

    #define orcc_semaphore_t HANDLE


#else
    #include <pthread.h>

    #define orcc_thread_create(thread, function, argument, id) id = pthread_create(&(thread), NULL, function, (void *) &(argument))
    #define orcc_thread_join(thread) pthread_join(thread, NULL);

    #define orcc_thread_t pthread_t
    #define orcc_thread_id_t int

#ifdef __APPLE__

    #include <mach/mach_traps.h>
    #include <mach/semaphore.h>
    #include <mach/task.h>
    #include <mach/mach.h>

    typedef long cpu_set_t;
    #define orcc_clear_cpu_set(cpuset)
    #define orcc_set_thread_affinity(cpuset, proc_num, thread)
    #define orcc_set_this_process_affinity(cpuset, proc_num)

    #define orcc_semaphore_create(semaphore, number) semaphore_create(mach_task_self(), &(semaphore), SYNC_POLICY_FIFO, (number))
    #define orcc_semaphore_wait(semaphore) semaphore_wait(semaphore)
    #define orcc_semaphore_set(semaphore) semaphore_signal(semaphore)
    #define orcc_semaphore_destroy(semaphore) semaphore_destroy(mach_task_self(), &(semaphore))
    #define orcc_semaphore_t semaphore_t

#else
    #include <sched.h>
    #include <semaphore.h>

    #define orcc_clear_cpu_set(cpuset) CPU_ZERO(&(cpuset))
    #define orcc_set_thread_affinity(cpuset, proc_num, thread) {                    \
            CPU_SET(proc_num, &(cpuset));                                    \
            pthread_setaffinity_np(thread, sizeof(cpu_set_t), &(cpuset));    \
    }
    #define orcc_set_this_process_affinity(cpuset, proc_num) {                    \
            CPU_SET(proc_num, &(cpuset));                                    \
            sched_setaffinity(0, sizeof(cpu_set_t), &(cpuset));                \
    }

    #define orcc_semaphore_create(semaphore, number) sem_init(&(semaphore), 0, (number))
    #define orcc_semaphore_wait(semaphore) sem_wait(&(semaphore))
    #define orcc_semaphore_set(semaphore) sem_post(&(semaphore))
    #define orcc_semaphore_destroy(semaphore) sem_destroy(&(semaphore))
    #define orcc_semaphore_t sem_t

#endif

#endif

void set_realtime_priority();

#endif  /* _ORCC_THREAD_H_ */

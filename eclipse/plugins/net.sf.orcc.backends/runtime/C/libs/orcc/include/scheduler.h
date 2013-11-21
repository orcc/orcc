/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
#ifndef _ORCC_SCHEDULER_H_
#define _ORCC_SCHEDULER_H_

#include "orcc.h"
#include "thread.h"

#define MAX_ACTORS 1024

struct waiting_s {
    actor_t *waiting_actors[MAX_ACTORS];
    volatile unsigned int next_entry;
    unsigned int next_waiting;
};

struct scheduler_s {
	int id; /** Unique ID of this scheduler */
	int schedulers_nb;

	/* Round robin */
	int num_actors; /** number of actors managed by this scheduler */
    actor_t **actors; /** static list of actors managed by this scheduler */
	int rr_next_schedulable; /** index of the next actor to schedule in last list */

	/* Data demand/driven scheduler */
    actor_t *schedulable[MAX_ACTORS]; /** dynamic list of the next actors to schedule */
	unsigned int ddd_next_entry; /** index of the next actor to schedule in last list */
	unsigned int ddd_next_schedulable; /** index of next actor added in the list */

	/* Multicore with data demand/driven scheduler */
	int round_robin; /** set to 1 when last scheduled actor is a result of round robin scheduling */
	/* ring topology */
    waiting_t *ring_waiting_schedulable; /** receiving list of some actors to schedule */
    waiting_t *ring_sending_schedulable; /** sending list of some actors to schedule */
	/* mesh topology */
    waiting_t **mesh_waiting_schedulable; /** receiving lists from other schedulers of some actors to schedule */

	/* Genetic algorithm */
    sync_t *sync;
	semaphore_struct sem_thread;
};

typedef enum reasons {
    starved,
    full
} reasons_t;

struct schedinfo_s {
    int num_firings;
    reasons_t reason;
    int ports; /** contains a mask that indicate the ports affected */
};

/**
 * Initialize the given scheduler.
 */
void sched_init(scheduler_t *sched, int id, int num_actors, actor_t **actors, waiting_t *ring_waiting_schedulable,
        waiting_t *ring_sending_schedulable, int schedulers_nb, sync_t *sync);

/**
 * Initialize the actors mapped to the given scheduler.
 */
void sched_init_actors(scheduler_t *sched, schedinfo_t *si);

/**
 * Reinitialize the given scheduler.
 */
void sched_reinit(scheduler_t *sched, int num_actors, actor_t **actors, int use_ring_topology, int schedulers_nb);

/**
 * Returns the next actor in actors list.
 * This method is used by the round-robin scheduler.
 */
actor_t *sched_get_next(scheduler_t *sched);

/**
 * Add the actor to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 */
void sched_add_schedulable(scheduler_t *sched, actor_t *actor, int use_ring_topology);

/**
 * Add waited actors to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 * This function use ring topology of communications.
 */
void sched_add_ring_waiting_list(scheduler_t *sched);

/**
 * Add waited actors to the schedulable list.
 * This function use mesh topology of communications.
 */
void sched_add_mesh_waiting_list(scheduler_t *sched);

/**
 * Returns the next schedulable actor, or NULL if no actor is schedulable.
 * The actor is removed from the schedulable list.
 * This method is used by the data/demand driven scheduler.
 */
actor_t *sched_get_next_schedulable(scheduler_t *sched, int use_ring_topology);

#endif

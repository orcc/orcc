/*
 * Copyright (c) 2011, IRISA
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
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "scheduler.h"
#include "dataflow.h"
#include "mapping.h"

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

global_scheduler_t *allocate_global_scheduler(int nb_schedulers, sync_t *sync) {
    int i,j;
    global_scheduler_t *sched = (global_scheduler_t*) malloc(sizeof(global_scheduler_t));
    waiting_t *waiting_schedulables = (waiting_t *) malloc(nb_schedulers * sizeof(waiting_t));
    sched->nb_schedulers = nb_schedulers;
    sched->schedulers = (local_scheduler_t**) malloc(nb_schedulers * sizeof(local_scheduler_t*));
    for (i = 0; i < nb_schedulers; i++) {
        local_scheduler_t *l_sched = (local_scheduler_t*) malloc(sizeof(local_scheduler_t));

        l_sched->id = i;
        l_sched->nb_schedulers = nb_schedulers;
        l_sched->ring_waiting_schedulable = &waiting_schedulables[i];
        l_sched->ring_sending_schedulable = &waiting_schedulables[(i+1) % nb_schedulers];
        l_sched->mesh_waiting_schedulable = (waiting_t **) malloc(nb_schedulers * sizeof(waiting_t *));
        for (j = 0; j < nb_schedulers; j++) {
            l_sched->mesh_waiting_schedulable[j] = (waiting_t *) malloc(sizeof(waiting_t));
        }
        l_sched->sync = sync;
        semaphore_create(l_sched->sem_thread, 0);

        sched->schedulers[i] = l_sched;
    }
    return sched;
}

void global_scheduler_init(global_scheduler_t *sched, mapping_t *mapping) {
    int i;
    for (i = 0; i < sched->nb_schedulers; i++) {
        local_scheduler_init(sched->schedulers[i], mapping->partitions_size[i], mapping->partitions_of_actors[i]);
    }
}

/**
 * Initializes the given scheduler.
 */
void local_scheduler_init(local_scheduler_t *sched, int num_actors, actor_t **actors) {
    int i;

    sched->num_actors = num_actors;
    sched->actors = actors;
    for (i = 0; i < num_actors; i++) {
        actors[i]->sched = sched;
        actors[i]->in_list = 0;
        actors[i]->in_waiting = 0;
    }

    sched->round_robin = 1;
	sched->rr_next_schedulable = 0;
	sched->ddd_next_entry = 0;
    sched->ddd_next_schedulable = 0;
	sched->ring_waiting_schedulable->next_entry = 0;
    sched->ring_waiting_schedulable->next_waiting = 0;
	sched->ring_sending_schedulable->next_entry = 0;
	sched->ring_sending_schedulable->next_waiting = 0;

    for (i = 0; i < sched->nb_schedulers; i++) {
		sched->mesh_waiting_schedulable[i]->next_entry = 0;
		sched->mesh_waiting_schedulable[i]->next_waiting = 0;
	}
}

/**
 * Reinitialize the given scheduler with new actors list.
 */
void sched_reinit(local_scheduler_t *sched, int num_actors, actor_t **actors, int use_ring_topology) {
	int i;

	sched->actors = actors;
	sched->num_actors = num_actors;
	sched->rr_next_schedulable = 0;
	sched->ddd_next_entry = 0;
	sched->ddd_next_schedulable = 0;
	sched->round_robin = 1;

	sched->ring_waiting_schedulable->next_entry = 0;
	sched->ring_waiting_schedulable->next_waiting = 0;
	sched->ring_sending_schedulable->next_entry = 0;
	sched->ring_sending_schedulable->next_waiting = 0;

    for (i = 0; i < sched->nb_schedulers; i++) {
		sched->mesh_waiting_schedulable[i]->next_entry = 0;
		sched->mesh_waiting_schedulable[i]->next_waiting = 0;
	}

	for (i = 0; i < num_actors; i++) {
		actors[i]->sched = sched;
		actors[i]->in_list = 0;
		actors[i]->in_waiting = 0;
		if (!strcmp(actors[i]->name, "source")) {
			sched_add_schedulable(sched, actors[i], use_ring_topology);
		}
	}

}

/**
 * Initialize the actors mapped to the given scheduler.
 */
void sched_init_actors(local_scheduler_t *sched, schedinfo_t *si) {
	int i;

	for (i = 0; i < sched->num_actors; i++) {
		sched->actors[i]->init_func(si);
	}
	
}

/**
 * Re-initialize the actors mapped to the given scheduler.
 */
void sched_reinit_actors(local_scheduler_t *sched, schedinfo_t *si) {
	int i;

	for (i = 0; i < sched->num_actors; i++) {
		sched->actors[i]->reinit_func(si);
	}
	
}

///////////////////////////////////////////////////////////////////////////////
// Scheduling list
///////////////////////////////////////////////////////////////////////////////

/**
 * Returns the next actor in actors list.
 * This method is used by the round-robin scheduler.
 */
actor_t *sched_get_next(local_scheduler_t *sched) {
    actor_t *actor;
	if (sched->num_actors == 0) {
		return NULL;
	}
	actor = sched->actors[sched->rr_next_schedulable];
	sched->rr_next_schedulable++;
	if (sched->rr_next_schedulable == sched->num_actors) {
		sched->rr_next_schedulable = 0;
	}
	return actor;
}

/**
 * Add the actor to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 */
void sched_add_schedulable(local_scheduler_t *sched, actor_t *actor, int use_ring_topology) {
	// only add the actor in the lists if it is not already there
	// like a list.contains(actor) but in O(1) instead of O(n)
	if (!actor->in_list) {
		if (sched == actor->sched) {
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			sched->ddd_next_entry++;
		} else if (!actor->in_waiting) {
			// this actor isn't launch by this scheduler so it is sent to the next one
            waiting_t *send =
					use_ring_topology ? sched->ring_sending_schedulable
							: actor->sched->mesh_waiting_schedulable[sched->id];
			send->waiting_actors[send->next_entry % MAX_ACTORS] = actor;
			actor->in_waiting = 1;
			send->next_entry++;
		}
	}
}

/**
 * Add waited actors to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 * This function use ring topology of communications.
 */
void sched_add_ring_waiting_list(local_scheduler_t *sched) {
    actor_t *actor;
    waiting_t *wait = sched->ring_waiting_schedulable;
	while (wait->next_entry - wait->next_waiting >= 1) {
		actor = wait->waiting_actors[wait->next_waiting % MAX_ACTORS];
		if (sched == actor->sched) {
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			actor->in_waiting = 0;
			sched->ddd_next_entry++;
		} else {
			// this actor isn't launch by this scheduler so it is sent to the next one
            waiting_t *send = sched->ring_sending_schedulable;
			send->waiting_actors[send->next_entry % MAX_ACTORS] = actor;
			send->next_entry++;
		}
		wait->next_waiting++;
	}
}

/**
 * Add waited actors to the schedulable list.
 * This function use mesh topology of communications.
 */
void sched_add_mesh_waiting_list(local_scheduler_t *sched) {
	int i;
    actor_t *actor;
    for (i = 0; i < sched->nb_schedulers; i++) {
        waiting_t *wait = sched->mesh_waiting_schedulable[i];
		while (wait->next_entry - wait->next_waiting >= 1) {
			actor = wait->waiting_actors[wait->next_waiting % MAX_ACTORS];
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			actor->in_waiting = 0;
			sched->ddd_next_entry++;
			wait->next_waiting++;
		}
	}
}

/**
 * Returns the next schedulable actor, or NULL if no actor is schedulable.
 * The actor is removed from the schedulable list.
 * This method is used by the data/demand driven scheduler.
 */
actor_t *sched_get_next_schedulable(local_scheduler_t *sched, int use_ring_topology) {
    actor_t *actor;
	// check if other schedulers sent some schedulable actors
	use_ring_topology ? sched_add_ring_waiting_list(sched)
			: sched_add_mesh_waiting_list(sched);
	if (sched->ddd_next_schedulable == sched->ddd_next_entry) {
		// static actors list is used when schedulable list is empty
		actor = sched_get_next(sched);
		sched->round_robin = 1;
	} else {
		actor = sched->schedulable[sched->ddd_next_schedulable % MAX_ACTORS];
		// actor is not a member of the list anymore
		actor->in_list = 0;
		sched->ddd_next_schedulable++;
		sched->round_robin = 0;
	}

	return actor;
}

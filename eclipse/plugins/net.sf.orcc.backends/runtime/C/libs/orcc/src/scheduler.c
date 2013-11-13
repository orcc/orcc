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

#include "types.h"
#include "fifo.h"
#include "scheduler.h"
#include "util.h"
#include "roxml.h"

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

/**
 * Initializes the given scheduler.
 */
void sched_init(struct scheduler_s *sched, int id, int num_actors,
		struct actor_s **actors, struct waiting_s *ring_waiting_schedulable,
		struct waiting_s *ring_sending_schedulable, int schedulers_nb,
		struct sync_s *sync) {
	int i;

	sched->id = id;
	sched->schedulers_nb = schedulers_nb;

	sched->num_actors = num_actors;
	sched->actors = actors;
	if (actors != NULL) {
		for (i = 0; i < num_actors; i++) {
			actors[i]->sched = sched;
			actors[i]->in_list = 0;
			actors[i]->in_waiting = 0;
		}
	}

	sched->rr_next_schedulable = 0;
	sched->ddd_next_entry = 0;
	sched->ddd_next_schedulable = 0;

	sched->round_robin = 1;

	sched->ring_waiting_schedulable = ring_waiting_schedulable;
	sched->ring_waiting_schedulable->next_entry = 0;
	sched->ring_waiting_schedulable->next_waiting = 0;
	sched->ring_sending_schedulable = ring_sending_schedulable;
	sched->ring_sending_schedulable->next_entry = 0;
	sched->ring_sending_schedulable->next_waiting = 0;

	sched->mesh_waiting_schedulable = (struct waiting_s **) malloc(
			schedulers_nb * sizeof(struct waiting_s *));
	for (i = 0; i < schedulers_nb; i++) {
		sched->mesh_waiting_schedulable[i] = (struct waiting_s *) malloc(
				sizeof(struct waiting_s));
		sched->mesh_waiting_schedulable[i]->next_entry = 0;
		sched->mesh_waiting_schedulable[i]->next_waiting = 0;
	}

	sched->sync = sync;
	semaphore_create(sched->sem_thread, 0);
}

/**
 * Reinitialize the given scheduler with new actors list.
 */
void sched_reinit(struct scheduler_s *sched, int num_actors,
		struct actor_s **actors, int use_ring_topology, int schedulers_nb) {
	int i;

	if (sched->actors != NULL) {
		free(sched->actors);
	}

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

	for (i = 0; i < schedulers_nb; i++) {
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
void sched_init_actors(struct scheduler_s *sched, struct schedinfo_s *si) {
	int i;

	for (i = 0; i < sched->num_actors; i++) {
		sched->actors[i]->init_func(si);
	}
	
}

/**
 * Re-initialize the actors mapped to the given scheduler.
 */
void sched_reinit_actors(struct scheduler_s *sched, struct schedinfo_s *si) {
	int i;

	for (i = 0; i < sched->num_actors; i++) {
		sched->actors[i]->reinit_func(si);
	}
	
}

///////////////////////////////////////////////////////////////////////////////
// Mapping functions
///////////////////////////////////////////////////////////////////////////////

/**
 * Find actor by its name in the given table.
 */
struct actor_s * find_actor(char *name, struct actor_s **actors,
		int actors_size) {
	int i;
	for (i = 0; i < actors_size; i++) {
		if (strcmp(name, actors[i]->name) == 0) {
			return actors[i];
		}
	}
	return NULL;
}

/**
 * Give the id of the mapped core of the given actor in the given mapping structure.
 */
int find_mapped_core(struct mapping_s *mapping, struct actor_s *actor) {
	int i;
	for (i = 0; i < mapping->number_of_threads; i++) {
		if (find_actor(actor->name, mapping->partitions_of_actors[i],
				mapping->partitions_size[i]) != NULL) {
			return i;
		}
	}
	return -1;
}

/**
 * Creates a mapping structure.
 */
struct mapping_s* allocate_mapping(int number_of_threads) {
	struct mapping_s *mapping = (struct mapping_s *) malloc(
			sizeof(struct mapping_s));
	mapping->number_of_threads = number_of_threads;
	mapping->threads_affinities = (int*) malloc(number_of_threads * sizeof(int));
	mapping->partitions_of_actors = (struct actor_s ***) malloc(
			number_of_threads * sizeof(struct actor_s **));
	mapping->partitions_size = (int*) malloc(number_of_threads * sizeof(int));
	return mapping;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_mapping(struct mapping_s* mapping, int clean_all) {
	if (clean_all) {
		int i;
		for (i = 0; i < mapping->number_of_threads; i++) {
			free(mapping->partitions_of_actors[i]);
		}
	}
	free(mapping->partitions_of_actors);
	free(mapping->partitions_size);
	free(mapping->threads_affinities);
	free(mapping);
}

/**
 * Computes a partitionment of actors on threads from an XML file given in parameter.
 */
struct mapping_s* map_actors(struct actor_s **actors, int actors_size) {
    if (mapping_file == NULL) {
        struct mapping_s *mapping = allocate_mapping(1);
        mapping->threads_affinities[0] = 0;
        mapping->partitions_size[0] = actors_size;
        mapping->partitions_of_actors[0] = actors;
        return mapping;
    } else {
        struct mappings_set_s *mappings_set = compute_mappings_from_file(
                mapping_file, actors, actors_size);
        return mappings_set->mappings[0];
    }
}

///////////////////////////////////////////////////////////////////////////////
// Scheduling list
///////////////////////////////////////////////////////////////////////////////

/**
 * Returns the next actor in actors list.
 * This method is used by the round-robin scheduler.
 */
struct actor_s *sched_get_next(struct scheduler_s *sched) {
	struct actor_s *actor;
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
void sched_add_schedulable(struct scheduler_s *sched,
		struct actor_s *actor, int use_ring_topology) {
	// only add the actor in the lists if it is not already there
	// like a list.contains(actor) but in O(1) instead of O(n)
	if (!actor->in_list) {
		if (sched == actor->sched) {
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			sched->ddd_next_entry++;
		} else if (!actor->in_waiting) {
			// this actor isn't launch by this scheduler so it is sent to the next one
			struct waiting_s *send =
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
void sched_add_ring_waiting_list(struct scheduler_s *sched) {
	struct actor_s *actor;
	struct waiting_s *wait = sched->ring_waiting_schedulable;
	while (wait->next_entry - wait->next_waiting >= 1) {
		actor = wait->waiting_actors[wait->next_waiting % MAX_ACTORS];
		if (sched == actor->sched) {
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			actor->in_waiting = 0;
			sched->ddd_next_entry++;
		} else {
			// this actor isn't launch by this scheduler so it is sent to the next one
			struct waiting_s *send = sched->ring_sending_schedulable;
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
void sched_add_mesh_waiting_list(struct scheduler_s *sched) {
	int i;
	struct actor_s *actor;
	for (i = 0; i < sched->schedulers_nb; i++) {
		struct waiting_s *wait = sched->mesh_waiting_schedulable[i];
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
struct actor_s *sched_get_next_schedulable(struct scheduler_s *sched,
		int use_ring_topology) {
	struct actor_s *actor;
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

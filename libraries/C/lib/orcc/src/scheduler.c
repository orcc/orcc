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

#include "orcc_types.h"
#include "orcc_fifo.h"
#include "orcc_scheduler.h"
#include "orcc_util.h"
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

static struct actor_s * find_actor(char *name, struct actor_s **actors,
		int actors_nb) {
	int i;
	for (i = 0; i < actors_nb; i++) {
		if (strcmp(name, actors[i]->name) == 0) {
			return actors[i];
		}
	}
	printf("Error: Actor %s doesn't exist in the description.\n", name);
	exit(0);
}

struct mapping_s* allocate_mapping(int number_of_threads) {
	struct mapping_s *mapping = (struct mapping_s *) malloc(
			sizeof(struct mapping_s));
	mapping->number_of_threads = number_of_threads;
	mapping->threads_ids = (int*) malloc(number_of_threads * sizeof(int));
	mapping->partitions_of_actors = (struct actor_s ***) malloc(
			number_of_threads * sizeof(struct actor_s **));
	mapping->partitions_size = (int*) malloc(number_of_threads * sizeof(int));
	return mapping;
}

void delete_mapping(struct mapping_s* mapping) {
	free(mapping->partitions_of_actors);
	free(mapping->partitions_size);
	free(mapping->threads_ids);
	free(mapping);
}

struct mapping_s * map_actors(struct actor_s **actors, int actors_nb) {
	int i, j, size;
	char *nb, *name;
	node_t *configuration, *partitioning, *partition, *instance, *attribute;
	struct mapping_s *mapping;

	if (mapping_file == NULL) {
		mapping = allocate_mapping(1);
		mapping->threads_ids[0] = 0;
		mapping->partitions_size[0] = actors_nb;
		mapping->partitions_of_actors[0] = actors;
	} else {
		configuration = roxml_load_doc(mapping_file);
		if (configuration == NULL) {
			printf("I/O error when reading mapping file.\n");
			exit(1);
		}

		partitioning = roxml_get_chld(configuration, NULL, 0);
		name = roxml_get_name(partitioning, NULL, 0);

		mapping = allocate_mapping(roxml_get_chld_nb(partitioning));

		for (i = 0; i < mapping->number_of_threads; i++) {
			partition = roxml_get_chld(partitioning, NULL, i);
			name = roxml_get_name(partition, NULL, 0);
			mapping->partitions_size[i] = roxml_get_chld_nb(partition);

			attribute = roxml_get_attr(partition, "id", 0);
			nb = roxml_get_content(attribute, NULL, 0, &size);
			mapping->threads_ids[i] = atoi(nb);

			mapping->partitions_of_actors[i] = (struct actor_s **) malloc(
					mapping->partitions_size[i] * sizeof(struct actor_s *));

			for (j = 0; j < mapping->partitions_size[i]; j++) {
				instance = roxml_get_chld(partition, NULL, j);
				name = roxml_get_name(instance, NULL, 0);
				attribute = roxml_get_attr(instance, "id", 0);
				name = roxml_get_content(attribute, NULL, 0, &size);
				mapping->partitions_of_actors[i][j] = find_actor(name, actors,
						actors_nb);
			}
		}
		roxml_close(configuration);
	}

	return mapping;
}

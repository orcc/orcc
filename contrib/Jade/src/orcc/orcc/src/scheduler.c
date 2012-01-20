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
	mapping->threads_ids = (int*) malloc(number_of_threads * sizeof(int));
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
	free(mapping->threads_ids);
	free(mapping);
}

/**
 * Computes a partitionment of actors on threads from an XML file given in parameter.
 */
struct mapping_s* map_actors(struct actor_s **actors, int actors_size) {
	if (mapping_file == NULL) {
		struct mapping_s *mapping = allocate_mapping(1);
		mapping->threads_ids[0] = 0;
		mapping->partitions_size[0] = actors_size;
		mapping->partitions_of_actors[0] = actors;
		return mapping;
	} else {
		struct mappings_set_s *mappings_set = compute_mappings_from_file(
				mapping_file, actors, actors_size);
		return mappings_set->mappings[0];
	}
}

/**
 * Generate some mapping structure from an XCF file.
 */
struct mappings_set_s* compute_mappings_from_file(char *xcf_file,
		struct actor_s **actors, int actors_size) {
	int i, j, k, size;
	char *nb, *name;
	node_t *configuration, *partitioning, *partition, *instance, *attribute;
	struct mappings_set_s *mappings_set = (struct mappings_set_s *) malloc(
			sizeof(struct mappings_set_s));

	configuration = roxml_load_doc(mapping_file);
	if (configuration == NULL) {
		printf("I/O error when reading mapping file.\n");
		exit(1);
	}

	mappings_set->size = roxml_get_chld_nb(configuration);
	mappings_set->mappings = (struct mapping_s **) malloc(
			mappings_set->size * sizeof(struct mapping_s *));

	for (i = 0; i < mappings_set->size; i++) {
		partitioning = roxml_get_chld(configuration, NULL, i);
		name = roxml_get_name(partitioning, NULL, 0);

		mappings_set->mappings[i] = allocate_mapping(
				roxml_get_chld_nb(partitioning));

		for (j = 0; j < mappings_set->mappings[i]->number_of_threads; j++) {
			partition = roxml_get_chld(partitioning, NULL, j);
			name = roxml_get_name(partition, NULL, 0);
			mappings_set->mappings[i]->partitions_size[j] = roxml_get_chld_nb(
					partition);

			attribute = roxml_get_attr(partition, "id", 0);
			nb = roxml_get_content(attribute, NULL, 0, &size);
			mappings_set->mappings[i]->threads_ids[j] = atoi(nb);

			mappings_set->mappings[i]->partitions_of_actors[j]
					= (struct actor_s **) malloc(
							mappings_set->mappings[i]->partitions_size[j]
									* sizeof(struct actor_s *));

			for (k = 0; k < mappings_set->mappings[i]->partitions_size[j]; k++) {
				instance = roxml_get_chld(partition, NULL, k);
				name = roxml_get_name(instance, NULL, 0);
				attribute = roxml_get_attr(instance, "id", 0);
				name = roxml_get_content(attribute, NULL, 0, &size);
				mappings_set->mappings[i]->partitions_of_actors[j][k]
						= find_actor(name, actors, actors_size);
			}
		}
	}
	roxml_close(configuration);

	return mappings_set;
}

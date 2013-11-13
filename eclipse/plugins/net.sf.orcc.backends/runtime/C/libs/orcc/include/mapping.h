/*
 * Copyright (c) 2013, INSA of Rennes
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
 *   * Neither the name of the INSA of Rennes nor the names of its
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

#ifndef ORCC_MAPPING_H
#define ORCC_MAPPING_H

typedef struct sync_s sync_t;
typedef struct options_s options_t;
typedef struct scheduler_s scheduler_t;
typedef struct network_s network_t;
typedef struct actor_s actor_t;

typedef struct mapping_s {
    int number_of_threads;
    int *threads_affinities;
    actor_t ***partitions_of_actors;
    int *partitions_size;
} mapping_t;

typedef struct mappings_set_s {
    int size;
    mapping_t **mappings;
} mappings_set_t;

typedef struct agent_s {
    sync_t *sync; /** Synchronization resources */
    options_t *options; /** Mapping options */
    scheduler_t *schedulers;
    network_t *network;
    int actors_nb;
    int threads_nb;
    int use_ring_topology;
} agent_t;

/**
 * Main routine of the mapping agent.
 */
void *map(void *data);

/**
 * Initialize the given agent structure.
 */
void agent_init(agent_t *agent, sync_t *sync, options_t *options);

/**
 * Create a mapping structure.
 */
mapping_t* allocate_mapping(int number_of_threads);

/**
 * Release memory of the given mapping structure.
 */
void delete_mapping(mapping_t* mapping, int clean_all);

/**
 * Give the id of the mapped core of the given actor in the given mapping structure.
 */
int find_mapped_core(mapping_t *mapping, actor_t *actor);

/**
 * Compute a partitionment of actors on threads from an XML file given in parameter.
 */
mapping_t* map_actors(actor_t **actors, int actors_size);

#endif

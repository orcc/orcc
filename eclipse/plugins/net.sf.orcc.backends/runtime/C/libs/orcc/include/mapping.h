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
 *   * Neither the name of INSA Rennes nor the names of its
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

#ifndef _ORCC_MAPPING_H_
#define _ORCC_MAPPING_H_

#include "metis.h"
#include "orcc.h"

/*
 * Mapping structure store the mapping result
 */
struct mapping_s {
    int number_of_threads;
    int *threads_affinities;
    actor_t ***partitions_of_actors;
    int *partitions_size;
};

struct agent_s {
    sync_t *sync; /** Synchronization resources */
    options_t *options; /** Mapping options */
    local_scheduler_t **schedulers;
    network_t *network;
    mapping_t *mapping;
    int threads_nb;
};

/**
 * Main routine of the mapping agent.
 */
void *map(void *data);

/**
 * Initialize the given agent structure.
 */
agent_t* agent_init(sync_t *sync, options_t *options, local_scheduler_t *schedulers, network_t *network, mapping_t *mapping);

int needMapping();
void resetMapping();

/**
 * Give the id of the mapped core of the given actor in the given mapping structure.
 */
int find_mapped_core(mapping_t *mapping, actor_t *actor);

/**
 * Compute a partitionment of actors on threads from an XML file given in parameter.
 */
mapping_t* map_actors(network_t *network);


/********************************************************************************************
 *
 * Allocate / Delete / Init functions
 *
 ********************************************************************************************/

/**
 * Creates a mapping structure.
 */
mapping_t *allocate_mapping(int number_of_threads);

/**
 * Releases memory of the given mapping structure.
 */
void delete_mapping(mapping_t *mapping);


/********************************************************************************************
 *
 * Functions for results printing
 *
 ********************************************************************************************/

/**
 * Print to the stdout the load balancing resulting from the mapping.
 */
void print_load_balancing(mapping_t *mapping);

/**
 * Print to the stdout the edgecut resulting of the mapping. The function assumes that the
 * mapping has been previously defined on the network.
 */
void print_edge_cut(network_t *network);


/********************************************************************************************
 *
 * Functions for Network managing
 *
 ********************************************************************************************/

/**
 * !TODO
 */
int swap_actors(actor_t **actors, int index1, int index2, int nb_actors);

/**
 * Sort a list of actors
 */
int sort_actors(actor_t **actors, int nb_actors);


/********************************************************************************************
 *
 * Functions for Mapping data structure
 *
 ********************************************************************************************/

/**
 * Build a mapping structure from metis partition
 */
int set_mapping_from_partition(network_t *network, idx_t *part, mapping_t *mapping);


/********************************************************************************************
 *
 * Mapping functions
 *
 ********************************************************************************************/

/**
 * Apply actor mapping using metis recursive strategy
 */
int do_metis_recursive_partition(network_t *network, options_t *opt, idx_t *part);

/**
 * Apply actor mapping using metis kway strategy
 */
int do_metis_kway_partition(network_t *network, options_t *opt, idx_t *part);

/**
 * Apply actor mapping using round-robin strategy
 * @author Long Nguyen
 */
int do_round_robbin_mapping(network_t *network, options_t *opt, idx_t *part);

/**
 * Apply the given mapping to the schedulers
 */
void apply_mapping(mapping_t *mapping, local_scheduler_t **schedulers, int nbThreads);

int do_mapping(network_t *network, options_t *opt, mapping_t *mapping);


#endif  /* _ORCCMAP_MAPPING_H_ */

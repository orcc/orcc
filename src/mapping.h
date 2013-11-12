/*
 * Copyright (c) 2013, INSA Rennes
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
 * about
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

#include <stdio.h>
#include <stdlib.h>
#include "metis.h"
#include "orccmap.h"

/********************************************************************************************
 *
 * Enums et constants
 *
 ********************************************************************************************/

typedef enum { FALSE, TRUE } boolean;


/********************************************************************************************
 *
 * Orcc-Map utils functions
 *
 ********************************************************************************************/

#define arrayCopy(DST,SRC,LEN) \
            { size_t TMPSZ = sizeof(*(SRC)) * (LEN); \
              if ( ((DST) = malloc(TMPSZ)) != NULL ) \
                memcpy((DST), (SRC), TMPSZ); }

/**
 * !TODO
 */
void print_orcc_error(orccmap_error_et error);

/**
 * !TODO
 */
void check_metis_error(rstatus_et error);

/**
 * !TODO
 */
void check_orcc_error(orccmap_error_et error);

/**
 * !TODO
 */
boolean print_trace_block(verbose_level_et level);

/**
 * !TODO
 */
void print_orcc_trace(verbose_level_et level, const char *trace, ...);

/**
 * !TODO
 */
void set_trace_level(verbose_level_et level);

/********************************************************************************************
 *
 * Allocate / Delete / Init functions
 *
 ********************************************************************************************/

/**
 * Creates and init options structure.
 */
options_t *set_default_options();

/**
 * Releases memory of the given options structure.
 */
void delete_options(options_t *opt);

/**
 * Creates a graph CSR structure.
 */
adjacency_list *allocate_graph(int nb_actors, int nb_edges);

/**
 * Releases memory of the given graph CSR structure.
 */
void delete_graph(adjacency_list *graph);

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
 * !TODO
 */
void print_load_balancing(mapping_t *mapping);

/**
 * !TODO
 */
void print_edge_cut(network_t *network);


/********************************************************************************************
 *
 * Functions for Graph CSR data structure
 *
 ********************************************************************************************/

/**
 * !TODO
 */
void print_graph(adjacency_list graph);

/**
 * !TODO
 */
adjacency_list *set_graph_from_network(network_t network);

/**
 * !TODO
 */
void check_graph_for_metis(adjacency_list graph);

/**
 * !TODO
 */
adjacency_list *fix_graph_for_metis(adjacency_list graph);


/********************************************************************************************
 *
 * Functions for Network managing
 *
 ********************************************************************************************/

/**
 * !TODO
 */
actor_t *find_actor_by_name(actor_t **actors, char *name, int nb_actors);

/**
 * !TODO
 */
int swap_actors(actor_t **actors, int index1, int index2, int nb_actors);

/**
 * !TODO
 */
int sort_actors(actor_t **actors, int nb_actors);

/**
 * !TODO
 */
int load_network(char *fileName, network_t *network);

/********************************************************************************************
 *
 * Functions for Mapping data structure
 *
 ********************************************************************************************/

/**
 * !TODO
 */
int set_mapping_from_partition(network_t *network, idx_t *part, mapping_t *mapping);

/**
 * !TODO
 */
int save_mapping(char* fileName, mapping_t *mapping);

/********************************************************************************************
 *
 * Mapping functions
 *
 ********************************************************************************************/

/**
 * !TODO
 */
int do_metis_recursive_partition(network_t network, options_t opt, idx_t *part);

/**
 * !TODO
 */
int do_metis_kway_partition(network_t network, options_t opt, idx_t *part);

/**
 * !TODO
 * @author Long Nguyen
 */
int do_round_robbin_mapping(network_t *network, options_t opt, idx_t *part);

/**
 * !TODO
 */
void start_orcc_mapping(options_t *opt);

#endif  /* _ORCC_MAPPING_H_ */

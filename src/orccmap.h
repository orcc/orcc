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

#ifndef _ORCC_GRAPH_H_
#define _ORCC_GRAPH_H_

#include <stdio.h>
#include <stdlib.h>
#include "metis.h"


/********************************************************************************************
 *
 * Enums et constants
 *
 ********************************************************************************************/

typedef enum { FALSE, TRUE } boolean;

/* Mapping strategy codes */
typedef enum {
    ORCC_MS_METIS_REC,
    ORCC_MS_METIS_KWAY,
    ORCC_MS_ROUND_ROBIN,
    ORCC_MS_OTHER,
    ORCC_MS_SIZE /* only used for string tab declaration */
} mappingstrategy_et;

/* Error codes */
/* !TODO : Add more errors */
typedef enum {
    ORCC_OK = 0,
    ORCC_ERR_BAD_ARGS,
    ORCC_ERR_BAD_ARGS_NBPROC,
    ORCC_ERR_BAD_ARGS_MS,
    ORCC_ERR_BAD_ARGS_VERBOSE,
    ORCC_ERR_MANDATORY_ARGS,
    ORCC_ERR_DEF_OUTPUT,
    ORCC_ERR_METIS,
    ORCC_ERR_SWAP_ACTORS,
    ORCC_ERR_ROXML_OPEN,
    ORCC_ERR_ROXML_NODE_ROOT,
    ORCC_ERR_ROXML_NODE_CONF,
    ORCC_ERR_ROXML_NODE_PART,
    ORCC_ERR_SIZE /* only used for string tab declaration */
} orccmap_error_et;

/* Verbose level */
typedef enum {
    ORCC_VL_QUIET,
    ORCC_VL_VERBOSE_1,
    ORCC_VL_VERBOSE_2
} verbose_level_et;

/********************************************************************************************
 *
 * Data structures
 *
 ********************************************************************************************/

typedef int int32_t;

/*
 * Options for mapping
 */
typedef struct options_s
{
    int nb_processors;
    mappingstrategy_et strategy;
    char *input_file;
    char *output_file;
} options_t;

/*
 * The adjacency structure of the graph is stored using the compressed storage format (CSR)
 * This structure is used by METIS only with undirected graphs
 */
typedef struct adjacency_list
{
    /* is_directed : True if the graph is directed, esle False */
    boolean is_directed;

    /* xadj : For each vertex i, xadj[i] gives the entry index in the array adjncy */
    int32_t *xadj;

    /* adjncy : For each vertex, its adjacency list is stored in consecutive locations in the array adjncy */
    int32_t *adjncy;

    /* vwgt : The weights of the vertices */
    int32_t *vwgt;

    /* adjwgt : The weights of the edges */
    int32_t *adjwgt;

    int nb_vertices;
    int nb_edges;
} adjacency_list;

/*
 * Actors are the vertices of orcc Networks
 */
typedef struct actor_s {
    char *name;
    double workload;
    int id;
    int processor_id;
} actor_t;

/*
 * Connections are the edges of orcc Networks
 */
typedef struct connection_s {
    actor_t *src;
    actor_t *dst;
    double workload;
} connection_t;

/*
 * Orcc Networks are directed graphs
 */
typedef struct network_s {
    actor_t **actors;
    connection_t **connections;
    int nb_actors;
    int nb_connections;
} network_t;

/*
 * Mapping structure store the mapping result
 */
typedef struct mapping_s {
    int number_of_threads;
    actor_t ***partitions_of_actors;
    int *partitions_size;
} mapping_t;


/********************************************************************************************
 *
 * Exportable function prototypes
 *
 ********************************************************************************************/

#ifdef _WINDLL
#define ORCC_API(type) __declspec(dllexport) type __cdecl
#elif defined(__cdecl)
#define ORCC_API(type) type __cdecl
#else
#define ORCC_API(type) type
#endif

#ifdef __cplusplus
extern "C" {
#endif

ORCC_API(int) do_mapping(network_t *network, options_t opt, mapping_t *mapping);

#ifdef __cplusplus
}
#endif

/********************************************************************************************
 *
 * Orcc-Map utils functions
 *
 ********************************************************************************************/

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
 * If the graph is supposed to be undirected, each edge will appears 2 times.
 */
adjacency_list *allocate_graph(network_t network, boolean is_directed);

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

/**
 * !TODO
 */
void print_communication_volume(network_t *network);


/********************************************************************************************
 *
 * Functions for Graph CSR data structure
 *
 ********************************************************************************************/

/**
 * !TODO
 */
boolean is_directed(adjacency_list al);

/**
 * !TODO
 */
int set_directed_graph_from_network(adjacency_list *graph, network_t network);

/**
 * !TODO
 */
int set_undirected_graph_from_network(adjacency_list *graph, network_t network);

/**
 * !TODO
 */
int set_graph_from_network(adjacency_list *graph, network_t network);

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

#endif  /* _ORCC_GRAPH_H_ */

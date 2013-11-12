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

#ifndef _ORCCMAP_H_
#define _ORCCMAP_H_

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
    int workload;
    int id;
    int processor_id;
} actor_t;

/*
 * Connections are the edges of orcc Networks
 */
typedef struct connection_s {
    actor_t *src;
    actor_t *dst;
    int workload;
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

#endif /* _ORCCMAP_H_ */

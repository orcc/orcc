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

/* Load Balancing strategy codes */
typedef enum {
  ORCC_LB_METIS_REC,
  ORCC_LB_METIS_KWAY,
  ORCC_LB_OTHER
} mappingstrategy_et;

typedef int int32_t;

typedef enum { FALSE, TRUE } boolean;

/*
* Graph data structures
*
*/

/* The adjacency structure of the graph is stored using the compressed storage format (CSR) */
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

    int nb_vertex;
    int nb_edges;
} adjacency_list;

typedef struct options_s
{
    int nb_processors;
    mappingstrategy_et strategy;
} options_t;

typedef struct actor_s {
    int id;
    char *name;
    double workload;
} actor_t;

typedef struct connection_s {
    int id;
    actor_t *src;
    actor_t *dst;
    double workload;
} connection_t;

typedef struct network_s
{
    actor_t **actors;
    connection_t **connections;
    int nbActors;
    int nbConnections;
} network_t;

typedef struct mapping_s {
    int number_of_threads;
    int *threads_affinities;
    actor_t ***partitions_of_actors;
    int *partitions_size;
} mapping_t;

typedef struct mappings_set_s {
    int size;
    struct mapping_s **mappings;
} mappings_set_t;

/*
* Function prototypes
*
*/

#ifdef _WINDLL
#define ORCC_API(type) __declspec(dllexport) type __cdecl
#elif defined(__cdecl)
#define ORCC_API(type) type __cdecl
#else
#define ORCC_API(type) type
#endif

boolean isDirected(adjacency_list al);

int addVertex(adjacency_list al);

int addEdge(adjacency_list al);

actor_t *findActorByNameInNetwork(char *name, network_t network);

mapping_t *allocate_mapping(int number_of_threads);

void delete_mapping(mapping_t *mapping);

//int setMappingFromMETIS(network_t network, idx_t *part, mapping_t *mapping);

//int runPartitionRecWithMETIS(adjacency_list graph, options_t opt, idx_t *part);

//int runPartitionKwayWithMETIS(adjacency_list graph, options_t opt, idx_t *part);

adjacency_list *allocate_graph(network_t network, boolean is_directed);

void delete_graph(adjacency_list *graph);

int doMapping(network_t network, options_t opt, mapping_t *mapping);

int setUndirectedGraphFromNetwork(adjacency_list *graph, network_t network);

int setGraphFromNetwork(adjacency_list *graph, network_t network);

int initNetwork(char* fileName, network_t *network);

int loadNetwork(char *fileName, network_t *network);

int loadWeights(char *fileName, network_t *network);

int saveMapping(char* fileName, mapping_t *mapping);

#endif  /* _ORCC_GRAPH_H_ */

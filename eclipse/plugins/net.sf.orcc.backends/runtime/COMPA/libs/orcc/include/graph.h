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

#ifndef _ORCC_GRAPH_H_
#define _ORCC_GRAPH_H_

#include "orcc.h"

typedef int int32_t;

/*
 * The adjacency structure of the graph is stored using the compressed storage format (CSR)
 * This structure is used by METIS only with undirected graphs
 */
struct adjacency_list_s
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
};


/**
 * Creates a graph CSR structure.
 */
adjacency_list *allocate_graph(int nb_actors, int nb_edges);

/**
 * Releases memory of the given graph CSR structure.
 */
void delete_graph(adjacency_list *graph);

/********************************************************************************************
 *
 * Functions for Graph CSR data structure
 *
 ********************************************************************************************/

/**
 * Print to the stdout the given graph as an adjacency list.
 */
void print_graph(adjacency_list *graph);

/**
 * Build an adjacency list from the given network.
 */
adjacency_list *set_graph_from_network(network_t *network);

/**
 * Check the viability of the given graph for metis.
 */
int check_graph_for_metis(adjacency_list *graph);

/**
 * Fix the given graph for metis.
 */
adjacency_list *fix_graph_for_metis(adjacency_list *graph);

#endif  /* _ORCC_GRAPH_H_ */

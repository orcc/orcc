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

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "graph.h"
#include "trace.h"
#include "dataflow.h"

/**
 * Creates a graph CSR structure.
 */
adjacency_list *allocate_graph(int nb_vertices, int nb_edges) {
    adjacency_list *graph;

    graph = (adjacency_list*) malloc(sizeof(adjacency_list));
    graph->xadj = (idx_t*) malloc(sizeof(idx_t) * (nb_vertices + 1));
    graph->vwgt = (idx_t*) malloc(sizeof(idx_t) * nb_vertices);
    graph->adjncy = (idx_t*) malloc(sizeof(idx_t) * nb_edges);
    graph->adjwgt = (idx_t*) malloc(sizeof(idx_t) * nb_edges);

    graph->nb_edges = nb_edges;
    graph->nb_vertices = nb_vertices;
    graph->xadj[graph->nb_vertices] = graph->nb_edges;
    return graph;
}

/**
 * Releases memory of the given graph CSR structure.
 */
void delete_graph(adjacency_list *graph) {
    free(graph->adjncy);
    free(graph->adjwgt);
    free(graph->vwgt);
    free(graph->xadj);
    free(graph);
}


/********************************************************************************************
 *
 * Functions for Graph CSR data structure
 *
 ********************************************************************************************/

void print_graph(adjacency_list *graph) {
    int i, j;
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Src:weight | nbEdges | Dest:weight ...");
    for (i = 0; i < graph->nb_vertices; i++) {
        printf("\n %3d:%d | %3d | ", i, graph->vwgt[i], graph->xadj[i+1] - graph->xadj[i]);
        for (j = graph->xadj[i]; j < graph->xadj[i+1]; j++) {
            printf("%3d:%d ", graph->adjncy[j], graph->adjwgt[j]);
        }
    }
}

adjacency_list *set_graph_from_network(network_t *network) {
    int i, j, k = 0;

    adjacency_list *graph = allocate_graph(network->nb_actors, network->nb_connections);

    for (i = 0; i < network->nb_actors; i++) {
        graph->xadj[i] = k;
        graph->vwgt[i] = network->actors[i]->workload;
        for (j = 0; j < network->nb_connections; j++) {
            if (network->connections[j]->src == network->actors[i]) {
                graph->adjncy[k] = network->connections[j]->dst->id;
                graph->adjwgt[k] = network->connections[j]->workload;
                k++;
            }
        }
    }

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : CSR Graph generated from Network :");
        print_graph(graph);
    }

    return graph;
}

int check_graph_for_metis(adjacency_list *graph) {
    int i, j, k;
    int nbSelf = 0, nbNullEdgeWeight = 0, nbNullVerticeWeight = 0, nbDuplicateEdge = 0, nbDirectedEdge = 0;
    int ret = ORCC_OK;

    for (i = 0; i < graph->nb_vertices; i++) {
        if (graph->vwgt[i] == 0) {
            nbNullVerticeWeight++;
        }

        for (j = graph->xadj[i]; j < graph->xadj[i+1]; j++) {
            boolean isDirected = TRUE;
            if (graph->adjncy[j] == i) {
                nbSelf++;
            }
            if (graph->adjwgt[j] == 0) {
                nbNullEdgeWeight++;
            }
            // Count directed edges
            for (k = graph->xadj[graph->adjncy[j]]; k < graph->xadj[graph->adjncy[j]+1]; k++) {
                isDirected = isDirected && (graph->adjncy[k] != i);
            }
            if (isDirected == TRUE) {
                nbDirectedEdge++;
            }
            for (k = graph->xadj[i]; k < graph->xadj[i+1]; k++) {
                if ((j != k) && (graph->adjncy[k] == graph->adjncy[j])) {
                    nbDuplicateEdge++;
                }
            }
        }
    }
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Self-edges=%d   Duplicate edges=%d   Directed edges=%d",nbSelf ,nbDuplicateEdge, nbDirectedEdge);
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Nb vertices with null weight = %d",nbNullVerticeWeight);
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Nb edges with null weight = %d", nbNullEdgeWeight);

    if (nbNullVerticeWeight + nbNullEdgeWeight > 0) {
        ret = ORCC_ERR_METIS_FIX_WEIGHTS;
    } else if (nbSelf + nbDirectedEdge + nbDuplicateEdge > 0) {
        ret = ORCC_ERR_METIS_FIX_NEEDED;
    }

    return ret;
}


/**
 * Creates a graph whose topology is consistent with Metis' requirements that:
 *    - There are no self-edges.
 *    - It is undirected; i.e., (u,v) and (v,u) should be present and of the
 *    same weight.
 *    - The adjacency list should not contain multiple edges to the same
 *    other vertex.
 *  - Weights are > 0
 *
 *    The above errors are fixed by performing the following operations:
 *    - Self-edges are removed.
 *    - The undirected graph is formed by the union and merge of edges (adding weights)
 *  - If Weights <= 0 : exit with explicit error message
 *
 *  A warning message will be printed if any fix has been required
 */
adjacency_list *fix_graph_for_metis(adjacency_list *graph) {
    int nb_edges = 0;
    adjacency_list *metis_graph;
    int i = 0, j = 0, k = 0;
    int **edges;
    int ret = ORCC_OK;

    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Fixing CSR graph for Metis");
    ret = check_graph_for_metis(graph);
    if (ret == ORCC_ERR_METIS_FIX_WEIGHTS) {
        check_orcc_error(ret);
    } else if (ret != ORCC_OK) {
        print_orcc_trace(ORCC_VL_VERBOSE_1, "WARNING : Corrections applied to the network for Metis conformance.");
    }

    /*
     * Create a matrix (vertices*vertices) to :
     *      - remove self-edges
     *      - merge duplicated edges by adding their weights
     *      - add (v,u) when (u,v) exists with same weight (making graph undirected)
     *      - get the final number of edges
     */

    edges = malloc(graph->nb_vertices * sizeof(*edges));
    for(i=0 ; i < graph->nb_vertices ; i++){
        edges[i] = malloc(graph->nb_vertices * sizeof(**edges) );
        memset(edges[i], -1, sizeof(int) * graph->nb_vertices);
    }

    for (i = 0; i < graph->nb_vertices; i++) {
        for (j = graph->xadj[i]; j < graph->xadj[i+1]; j++) {
            /* First test prevents from self-edges */
            if (graph->adjncy[j] != i) {
                if (edges[i][graph->adjncy[j]] == -1) {
                    nb_edges += 2;
                    edges[i][graph->adjncy[j]] = graph->adjwgt[j];
                    edges[graph->adjncy[j]][i] = graph->adjwgt[j];
                } else {
                    edges[i][graph->adjncy[j]] += graph->adjwgt[j];
                    edges[graph->adjncy[j]][i] += graph->adjwgt[j];
                }
            }
        }
    }

    /* Use previous matrix to set the fixed CSR graph for Metis */
    metis_graph = allocate_graph(graph->nb_vertices, nb_edges);
    arrayCopy(metis_graph->vwgt, graph->vwgt, graph->nb_vertices);
    for (i=0; i<metis_graph->nb_vertices; i++) {
        metis_graph->xadj[i] = k;
        for (j=0; j<metis_graph->nb_vertices; j++) {
            if (edges[i][j] != -1) {
                metis_graph->adjncy[k] = j;
                metis_graph->adjwgt[k] = edges[i][j];
                k++;
            }
        }
    }

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Fixed CSR Graph for Metis :");
        print_graph(metis_graph);
    }
    ret = check_graph_for_metis(metis_graph);
    check_orcc_error(ret);

    free(edges);
    return metis_graph;
}

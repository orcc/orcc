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

#include <assert.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "mapping.h"
#include "util.c"

/********************************************************************************************
 *
 * Allocate / Delete / Init functions
 *
 ********************************************************************************************/

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

/**
 * Creates a mapping structure.
 */
mapping_t *allocate_mapping(int number_of_threads) {
    mapping_t *mapping = (mapping_t *) malloc(sizeof(mapping_t));
    mapping->number_of_threads = number_of_threads;
    mapping->partitions_of_actors = malloc(number_of_threads * sizeof(*mapping->partitions_of_actors));
    mapping->partitions_size = (int*) malloc(number_of_threads * sizeof(int));
    return mapping;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_mapping(mapping_t *mapping) {
    free(mapping->partitions_size);
    free(mapping);
}


/********************************************************************************************
 *
 * Functions for results printing
 *
 ********************************************************************************************/

void print_load_balancing(mapping_t *mapping) {
    assert(mapping != NULL);
    int i, j, nb_proc = 0;
    double totalWeight = 0, avgWeight = 0, maxWeight = 0, partWeight = 0;

    for (i = 0; i < mapping->number_of_threads; i++) {
        partWeight = 0;
        for (j = 0; j < mapping->partitions_size[i]; j++) {
            totalWeight += mapping->partitions_of_actors[i][j]->workload;
            partWeight += mapping->partitions_of_actors[i][j]->workload;
            nb_proc++;
        }

        print_orcc_trace(ORCC_VL_VERBOSE_2, "Weight of partition %d : %.2lf", i+1, partWeight);
        if (maxWeight < partWeight)
            maxWeight = partWeight;
    }

    avgWeight = (totalWeight / mapping->number_of_threads);
    print_orcc_trace(ORCC_VL_VERBOSE_2, "Average weight: %.2lf   Max weight: %.2lf", avgWeight, maxWeight);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "Load balancing %.2lf", maxWeight/avgWeight);
}

void print_edge_cut(network_t *network) {
    int i, cut = 0, comm = 0;

    for (i = 0; i < network->nb_connections; i++) {
        if (network->connections[i]->src->processor_id != network->connections[i]->dst->processor_id) {
            comm += network->connections[i]->workload;
            cut++;
        }
    }

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Edgecut : %d   Communication volume : %d", cut, comm);
}



/********************************************************************************************
 *
 * Functions for Graph CSR data structure
 *
 ********************************************************************************************/

void print_graph(adjacency_list graph) {
    int i, j;
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Src:weight | nbEdges | Dest:weight ...");
    for (i = 0; i < graph.nb_vertices; i++) {
        printf("\n %3d:%d | %3d | ", i, graph.vwgt[i], graph.xadj[i+1] - graph.xadj[i]);
        for (j = graph.xadj[i]; j < graph.xadj[i+1]; j++) {
            printf("%3d:%d ", graph.adjncy[j], graph.adjwgt[j]);
        }
    }
}

adjacency_list *set_graph_from_network(network_t network) {
    int i, j, k = 0;

    adjacency_list *graph = allocate_graph(network.nb_actors, network.nb_connections);

    for (i = 0; i < network.nb_actors; i++) {
        graph->xadj[i] = k;
        graph->vwgt[i] = network.actors[i]->workload;
        for (j = 0; j < network.nb_connections; j++) {
            if (network.connections[j]->src == network.actors[i]) {
                graph->adjncy[k] = network.connections[j]->dst->id;
                graph->adjwgt[k] = network.connections[j]->workload;
                k++;
            }
        }
    }

    if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : CSR Graph generated from Network :");
        print_graph(*graph);
    }

    return graph;
}

int check_graph_for_metis(adjacency_list graph) {
    int i, j, k;
    int nbSelf = 0, nbNullEdgeWeight = 0, nbNullVerticeWeight = 0, nbDuplicateEdge = 0, nbDirectedEdge = 0;
    int ret = ORCC_OK;

    for (i = 0; i < graph.nb_vertices; i++) {
        if (graph.vwgt[i] == 0) {
            nbNullVerticeWeight++;
        }

        for (j = graph.xadj[i]; j < graph.xadj[i+1]; j++) {
            if (graph.adjncy[j] == i) {
                nbSelf++;
            }
            if (graph.adjwgt[j] == 0) {
                nbNullEdgeWeight++;
            }
            // !TODO : count directed edges (ie : graph not undirected) */
//            for (k = graph.xadj[graph.adjncy[j]]; k < graph.xadj[graph.adjncy[j]+1]; k++) {
//                if (graph.adjncy[k] == i) {
//                    nbDirectedEdge++;
//                }
//            }
            for (k = graph.xadj[i]; k < graph.xadj[i+1]; k++) {
                if ((j != k) && (graph.adjncy[k] == graph.adjncy[j])) {
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
 *	- There are no self-edges.
 *	- It is undirected; i.e., (u,v) and (v,u) should be present and of the
 *	same weight.
 *	- The adjacency list should not contain multiple edges to the same
 *	other vertex.
 *  - Weights are > 0
 *
 *	The above errors are fixed by performing the following operations:
 *	- Self-edges are removed.
 *	- The undirected graph is formed by the union and merge of edges (adding weights)
 *  - If Weights <= 0 : exit with explicit error message
 *
 *  A warning message will be printed if any fix has been required
 */
adjacency_list *fix_graph_for_metis(adjacency_list graph) {
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

    edges = malloc(graph.nb_vertices * sizeof(*edges));
    for(i=0 ; i < graph.nb_vertices ; i++){
        edges[i] = malloc(graph.nb_vertices * sizeof(**edges) );
        memset(edges[i], -1, sizeof(int) * graph.nb_vertices);
    }

    for (i = 0; i < graph.nb_vertices; i++) {
        for (j = graph.xadj[i]; j < graph.xadj[i+1]; j++) {
            /* First test prevents from self-edges */
            if (graph.adjncy[j] != i) {
                if (edges[i][graph.adjncy[j]] == -1) {
                    nb_edges += 2;
                    edges[i][graph.adjncy[j]] = graph.adjwgt[j];
                    edges[graph.adjncy[j]][i] = graph.adjwgt[j];
                } else {
                    edges[i][graph.adjncy[j]] += graph.adjwgt[j];
                    edges[graph.adjncy[j]][i] += graph.adjwgt[j];
                }
            }
        }
    }

    /* Use previous matrix to set the fixed CSR graph for Metis */
    metis_graph = allocate_graph(graph.nb_vertices, nb_edges);
    arrayCopy(metis_graph->vwgt, graph.vwgt, graph.nb_vertices);
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

    if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Fixed CSR Graph for Metis :");
        print_graph(*metis_graph);
    }
    ret = check_graph_for_metis(*metis_graph);
    check_orcc_error(ret);

    free(edges);
    return metis_graph;
}


/********************************************************************************************
 *
 * Functions for Network managing
 *
 ********************************************************************************************/

int swap_actors(actor_t **actors, int index1, int index2, int nb_actors) {
    assert(actors != NULL);
    int ret = ORCC_OK;
    actor_t *actor;

    if (index1 < nb_actors && index2 < nb_actors) {
        actor = actors[index1];
        actors[index1] = actors[index2];
        actors[index1] = actor;
    } else {
        ret = ORCC_ERR_SWAP_ACTORS;
    }

    return ret;
}

int sort_actors(actor_t **actors, int nb_actors) {
    assert(actors != NULL);
    int ret = ORCC_OK;
    int i, j;

    for (i = 0; i < nb_actors; i++) {
        for (j = 0; j < nb_actors - i - 1; j++) {
            if (actors[j]->workload <= actors[j+1]->workload) {
                swap_actors(actors, j, j+1, nb_actors);
            }
        }
    }

    if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : The sorted list:");
        for (i = 0; i < nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tid = %s\tworkload = %d", i, actors[i]->name, actors[i]->workload);
        }
    }
    return ret;
}



/********************************************************************************************
 *
 * Functions for Mapping data structure
 *
 ********************************************************************************************/

int set_mapping_from_partition(network_t *network, idx_t *part, mapping_t *mapping) {
    assert(network != NULL);
    assert(part != NULL);
    assert(mapping != NULL);
    int ret = ORCC_OK;
    int i, j;
    int *counter = malloc(mapping->number_of_threads * sizeof(counter));

    for (i = 0; i < mapping->number_of_threads; i++) {
        mapping->partitions_size[i] = 0;
        counter[i] = 0;
    }
    for (i = 0; i < network->nb_actors; i++) {
        mapping->partitions_size[part[i]]++;
    }
    for (i = 0; i < mapping->number_of_threads; i++) {
        mapping->partitions_of_actors[i] = malloc(mapping->partitions_size[i] * sizeof(**mapping->partitions_of_actors));
    }
    for (i = 0; i < network->nb_actors; i++) {
        mapping->partitions_of_actors[part[i]][counter[part[i]]] = network->actors[i];
        counter[part[i]]++;
    }

    // Update network too
    for (i=0; i < network->nb_actors; i++) {
        network->actors[i]->processor_id = part[i];
    }

    // Print results
    if (print_trace_block(ORCC_VL_VERBOSE_1) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_1, "Mapping result : ");
        for (i = 0; i < mapping->number_of_threads; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_1, "\tPartition %d : %d actors", i+1, mapping->partitions_size[i]);
            for (j=0; j < mapping->partitions_size[i]; j++) {
                print_orcc_trace(ORCC_VL_VERBOSE_1, "\t\t%s", mapping->partitions_of_actors[i][j]->name);
            }
        }

        print_load_balancing(mapping);
        print_edge_cut(network);
    }

    free(counter);
    return ret;
}



/********************************************************************************************
 *
 * Mapping functions
 *
 ********************************************************************************************/

int do_metis_recursive_partition(network_t network, options_t opt, idx_t *part) {
    assert(part != NULL);
    int ret = ORCC_OK;
    idx_t ncon = 1;
    idx_t metis_opt[METIS_NOPTIONS];
    idx_t objval;
    adjacency_list *graph, *metis_graph;

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying METIS Recursive partition for mapping");

    METIS_SetDefaultOptions(metis_opt);

    graph = set_graph_from_network(network);
    metis_graph = fix_graph_for_metis(*graph);

    ret = METIS_PartGraphRecursive(&metis_graph->nb_vertices, /* idx_t *nvtxs */
                                   &ncon, /*idx_t *ncon*/
                                   metis_graph->xadj, /*idx_t *xadj*/
                                   metis_graph->adjncy, /*idx_t *adjncy*/
                                   metis_graph->vwgt, /*idx_t *vwgt*/
                                   NULL, /*idx_t *vsize*/
                                   metis_graph->adjwgt, /*idx_t *adjwgt*/
                                   &opt.nb_processors, /*idx_t *nparts*/
                                   NULL, /*real t *tpwgts*/
                                   NULL, /*real t ubvec*/
                                   metis_opt, /*idx_t *options*/
                                   &objval, /*idx_t *objval*/
                                   part); /*idx_t *part*/
    check_metis_error(ret);

    print_orcc_trace(ORCC_VL_VERBOSE_2, "METIS Edgecut : %d", objval);

    delete_graph(graph);
    delete_graph(metis_graph);
    return ret;
}

int do_metis_kway_partition(network_t network, options_t opt, idx_t *part) {
    assert(part != NULL);
    int ret = ORCC_OK;
    idx_t ncon = 1;
    idx_t metis_opt[METIS_NOPTIONS];
    idx_t objval;
    adjacency_list *graph, *metis_graph;

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying METIS Kway partition for mapping");

    METIS_SetDefaultOptions(metis_opt);
    metis_opt[METIS_OPTION_OBJTYPE] = METIS_OBJTYPE_VOL; /* METIS_OBJTYPE_VOL or METIS_OBJTYPE_CUT */

    graph = set_graph_from_network(network);
    metis_graph = fix_graph_for_metis(*graph);

    ret = METIS_PartGraphKway(&metis_graph->nb_vertices, /* idx_t *nvtxs */
                              &ncon, /*idx_t *ncon*/
                              metis_graph->xadj, /*idx_t *xadj*/
                              metis_graph->adjncy, /*idx_t *adjncy*/
                              metis_graph->vwgt, /*idx_t *vwgt*/
                              NULL, /*idx_t *vsize*/
                              metis_graph->adjwgt, /*idx_t *adjwgt*/
                              &opt.nb_processors, /*idx_t *nparts*/
                              NULL, /*real t *tpwgts*/
                              NULL, /*real t ubvec*/
                              metis_opt, /*idx_t *options*/
                              &objval, /*idx_t *objval*/
                              part); /*idx_t *part*/
    check_metis_error(ret);

    print_orcc_trace(ORCC_VL_VERBOSE_2, "METIS Edgecut : %d", objval);

    delete_graph(graph);
    delete_graph(metis_graph);
    return ret;
}

int do_round_robbin_mapping(network_t *network, options_t opt, idx_t *part) {
    assert(network != NULL);
    assert(part != NULL);
    int ret = ORCC_OK;
    int i, k;
    k = 0;

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying Round Robin strategy for mapping");

    sort_actors(network->actors, network->nb_actors);

    for (i = 0; i < network->nb_actors; i++) {
        network->actors[i]->processor_id = k++;
        part[i] = network->actors[i]->processor_id;
        // There must be something needing to be improved here, i.e. invert
        // the direction of the distribution to have more balancing.
        if (k >= opt.nb_processors)
            k = 0;
    }

    if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Round Robin result");
        for (i = 0; i < network->nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tname = %s\tworkload = %d\tprocessorId = %d",
                             i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
    }

    return ret;
}

int do_mapping(network_t *network, options_t opt, mapping_t *mapping) {
    assert(network != NULL);
    assert(mapping != NULL);
    int i;
    int ret = ORCC_OK;
    idx_t *part = (idx_t*) malloc(sizeof(idx_t) * (network->nb_actors));

    if (opt.nb_processors != 1) {
        switch (opt.strategy) {
        case ORCC_MS_METIS_REC:
            ret = do_metis_recursive_partition(*network, opt, part);
            break;
        case ORCC_MS_METIS_KWAY:
            ret = do_metis_kway_partition(*network, opt, part);
            break;
        case ORCC_MS_ROUND_ROBIN:
            ret = do_round_robbin_mapping(network, opt, part);
            break;
        case ORCC_MS_OTHER:
            break;
        default:
            break;
        }
    } else {
        for (i = 0; i < network->nb_actors; i++) {
            part[i] = network->actors[i]->processor_id;
        }
    }

    set_mapping_from_partition(network, part, mapping);

    free(part);
    return ret;
}

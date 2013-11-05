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

#include <assert.h>
#include <stdarg.h>
#include <math.h>
#include "orccmap.h"
#include "roxml.h"

static verbose_level_et verbose_level = ORCC_VL_QUIET;

static const char *ORCC_ERRORS_TXT[ORCC_ERR_SIZE] = {
    "",
    "Bad arguments. Please check usage print.",
    "Arg value for -n is not valide.",
    "Arg value for -m is not valide.",
    "Arg value for -v is not valide.",
    "Mandatory argument missing. Please check usage print.",
    "Cannot generate default output file name.",
    "METIS error",
    "Actors swap fails.",
    "Cannot open input file.",
    "Cannot create root node.",
    "Cannot create Configuration node.",
    "Cannot create Partition node."
};

static const char *ORCC_STRATEGY_TXT[ORCC_MS_SIZE] = {
    "METIS Recursive",
    "METIS Kway",
    "Round Robin"
};

/********************************************************************************************
 *
 * Orcc-Map utils functions
 *
 ********************************************************************************************/

void print_orcc_error(orccmap_error_et error) {
    if (error != ORCC_OK && error < ORCC_ERR_SIZE) {
        fprintf(stderr,"\nOrcc-Map : ERROR : %s", ORCC_ERRORS_TXT[error]);
    }

}

void check_metis_error(rstatus_et error) {
    if (error != METIS_OK) {
        print_orcc_error(ORCC_ERR_METIS);
        fprintf(stderr,"\t Code: %d", error);
        exit(error);
    }
}

void check_orcc_error(orccmap_error_et error) {
    if (error != ORCC_OK) {
        print_orcc_error(error);
        exit(error);
    }
}

boolean print_trace_block(verbose_level_et level) {
    return (level<=verbose_level)?TRUE:FALSE;
}

void print_orcc_trace(verbose_level_et level, const char *trace, ...) {
    assert(trace != NULL);

    va_list args;
    va_start (args, trace);

    if (level <= verbose_level) {
        printf("\nOrcc-Map : ");
        vprintf(trace, args);
    }

    va_end (args);
}

void set_trace_level(verbose_level_et level) {
    verbose_level = level;
}

/********************************************************************************************
 *
 * Allocate / Delete / Init functions
 *
 ********************************************************************************************/

/**
 * Creates and init options structure.
 */
options_t *set_default_options() {
    options_t *opt = (options_t*) malloc(sizeof(options_t));
    opt->strategy = ORCC_MS_ROUND_ROBIN;
    opt->nb_processors = 1;
    opt->input_file = "";
    opt->output_file = "";

    return opt;
}

/**
 * Releases memory of the given options structure.
 */
void delete_options(options_t *opt) {
    free(opt);
}

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
    mapping->partitions_of_actors = (actor_t ***) malloc(number_of_threads * sizeof(actor_t **));
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

void check_graph_for_metis(adjacency_list graph) {
    int i, j, k;
    int nbSelf = 0, nbNullEdgeWeight = 0, nbNullVerticeWeight = 0, nbDuplicateEdge = 0, nbUndirectedEdge = 0;

    for (i = 0; i < graph.nb_vertices; i++) {
        if (graph.vwgt[i] == 0) {
            //TODO
            nbNullVerticeWeight++;
        }

        for (j = graph.xadj[i]; j < graph.xadj[i+1]; j++) {
            /* Remove self-edges */
            if (graph.adjncy[j] == i) {
                //TODO
                nbSelf++;
            }
            if (graph.adjwgt[j] == 0) {
                //TODO
                nbNullEdgeWeight++;
            }
            for (k = graph.xadj[graph.adjncy[j]]; k < graph.xadj[graph.adjncy[j]+1]; k++) {
                if (graph.adjncy[k] == i) {
                    //TODO
                    nbUndirectedEdge++;
                }
            }
            for (k = graph.xadj[i]; k < graph.xadj[i+1]; k++) {
                if ((j != k) && (graph.adjncy[k] == graph.adjncy[j])) {
                    //TODO
                    nbDuplicateEdge++;
                }
            }
        }
    }
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Self-edges=%d   Duplicate edges=%d   Undirected edges=%d",nbSelf ,nbDuplicateEdge, nbUndirectedEdge);
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Nb vertices with null weight = %d",nbNullVerticeWeight);
    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Nb edges with null weight = %d", nbNullEdgeWeight);
}


/*************************************************************************
 *
 *      This function creates a graph whose topology is consistent with
 *	    Metis' requirements that:
 *	    - There are no self-edges.
 *	    - It is undirected; i.e., (u,v) and (v,u) should be present and of the
 *	      same weight.
 *	    - The adjacency list should not contain multiple edges to the same
 *	      other vertex.
 *      - Weights are > 0
 *
 *	    The above errors are fixed by performing the following operations:
 *	    - Self-edges are removed.
 *	    - The undirected graph is formed by the union and merge of edges (adding weights)
 *      - If Weights <= 0 : exit with explicit error message
 *
 *      A warning message will be printed if any fix has been required
 *
 **************************************************************************/
adjacency_list *fix_graph_for_metis(adjacency_list graph) {
    int nb_edges = 0;
    adjacency_list *metis_graph;
    int i = 0, j = 0, k = 0;
    int **edges;

    if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Fixing CSR graph for Metis");
        check_graph_for_metis(graph);
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
        check_graph_for_metis(*metis_graph);
    }

    free(edges);
    return metis_graph;
}


/********************************************************************************************
 *
 * Functions for Network managing
 *
 ********************************************************************************************/

actor_t *find_actor_by_name(actor_t **actors, char *name, int nb_actors) {
    assert(actors != NULL);
    assert(name != NULL);
    actor_t *ret = NULL;
    int i = 0;

    while (i < nb_actors && ret == NULL) {
        if (strcmp(name, actors[i]->name) == 0) {
            ret = actors[i];
        }
        i++;
    }

    return ret;
}

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

int load_network(char *fileName, network_t *network) {
    assert(fileName != NULL);
    assert(network != NULL);
    int ret = ORCC_OK;
    int i;

    node_t* rootNode = roxml_load_doc(fileName);

    if (rootNode == NULL) {
        check_orcc_error(ORCC_ERR_ROXML_OPEN);
    }

    network->nb_actors = 0;
    network->nb_connections = 0;

    while (1) {
        node_t* actorNode = roxml_get_chld(rootNode, NULL, network->nb_actors + network->nb_connections);

        if (actorNode == NULL) {
            break;
        }

        char* nodeName = roxml_get_name(actorNode, NULL, 0);
        if (strcmp(nodeName, "Instance") == 0) {
            network->nb_actors++;
        }
        else if (strcmp(nodeName, "Connection") == 0) {
            network->nb_connections++;
        }
        else {
            break;
        }
    }

    network->actors = (actor_t**) malloc(network->nb_actors * sizeof(actor_t*));
    network->connections = (connection_t**) malloc(network->nb_connections * sizeof(connection_t*));
    for (i=0; i < network->nb_connections; i++) {
        network->actors[i] = (actor_t*) malloc(sizeof(actor_t*));
        network->connections[i] = (connection_t*) malloc(sizeof(connection_t*));
    }
    for (i=0; i < network->nb_connections; i++) {
        network->connections[i]->src = (actor_t*) malloc(sizeof(actor_t));
        network->connections[i]->dst = (actor_t*) malloc(sizeof(actor_t));
    }

    print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Loading network");
    for (i = 0; i < network->nb_actors; i++) {
        node_t* actorNode = roxml_get_chld(rootNode, NULL, i);

        if (actorNode == NULL) {
            break;
        }

        char* nodeName = roxml_get_name(actorNode, NULL, 0);
        if (strcmp(nodeName, "Instance") == 0) {
            node_t* nodeAttrActorId = roxml_get_attr(actorNode, "id", 0);
            network->actors[i]->name = roxml_get_content(nodeAttrActorId, NULL, 0, NULL);
            network->actors[i]->id = i;
            network->actors[i]->processor_id = 0;

            node_t* nodeAttrWorkload = roxml_get_attr(actorNode, "workload", 0);
            if (nodeAttrWorkload != NULL) {
                network->actors[i]->workload = atoi(roxml_get_content(nodeAttrWorkload, NULL, 0, NULL));
            } else {
                network->actors[i]->workload = 1;
            }

            if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
                print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Load Actor[%d]\tname = %s\tworkload = %d",
                                 i, network->actors[i]->name, network->actors[i]->workload);
            }
        }
        else {
            break;
        }
    }

    for (i = 0; i < network->nb_connections; i++) {
        node_t* connectionNode = roxml_get_chld(rootNode, NULL, i + network->nb_actors);

        if (connectionNode == NULL) {
            break;
        }

        char* nodeName = roxml_get_name(connectionNode, NULL, 0);
        if (strcmp(nodeName, "Connection") == 0) {
            node_t* nodeAttrActorSrc = roxml_get_attr(connectionNode, "src", 0);
            char *src = roxml_get_content(nodeAttrActorSrc, NULL, 0, NULL);
            network->connections[i]->src = find_actor_by_name(network->actors, src, network->nb_actors);

            node_t* nodeAttrActorDst = roxml_get_attr(connectionNode, "dst", 0);
            char *dst = roxml_get_content(nodeAttrActorDst, NULL, 0, NULL);
            network->connections[i]->dst = find_actor_by_name(network->actors, dst, network->nb_actors);

            node_t* nodeAttrWorkload = roxml_get_attr(connectionNode, "workload", 0);
            if (nodeAttrWorkload != NULL) {
                network->connections[i]->workload = atoi(roxml_get_content(nodeAttrWorkload, NULL, 0, NULL));
            } else {
                network->connections[i]->workload = 1;
            }

            if (print_trace_block(ORCC_VL_VERBOSE_2) == TRUE) {
                print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Load Connection[%d]\tsrc = %s\t  dst = %s",
                                 i, network->connections[i]->src->name, network->connections[i]->dst->name);
            }

        }
        else {
            break;
        }
    }

    if (print_trace_block(ORCC_VL_VERBOSE_1) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_1, "Network loaded successfully");
        print_orcc_trace(ORCC_VL_VERBOSE_1, "Number of actors is : %d", network->nb_actors);
        print_orcc_trace(ORCC_VL_VERBOSE_1, "Number of connections is : %d", network->nb_connections);
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
        mapping->partitions_of_actors[i] = (actor_t **) malloc(mapping->partitions_size[i] * sizeof(actor_t *));
        for (j=0; j < mapping->partitions_size[part[i]]; j++) {
            mapping->partitions_of_actors[i][j] = (actor_t *) malloc(sizeof(actor_t));
        }
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

int save_mapping(char* fileName, mapping_t *mapping) {
    assert(fileName != NULL);
    assert(mapping != NULL);
    int ret = ORCC_OK;
    int i, j;

    /* !TODO: if the output file already exists, backup of the file and advert the user */

    node_t* rootNode = roxml_add_node(NULL, 0, ROXML_PI_NODE, "xml", "version=\"1.0\" encoding=\"UTF-8\"");
    if (rootNode == NULL) {
        printf("ORCC_ERR_ROXML_NODE_ROOT");
    }

    node_t* configNode = roxml_add_node(rootNode, 0, ROXML_ELM_NODE, "Configuration", NULL);
    if (configNode == NULL) {
        check_orcc_error(ORCC_ERR_ROXML_NODE_CONF);
    }

    node_t* partitionNode = roxml_add_node(configNode, 0, ROXML_ELM_NODE, "Partitioning", NULL);
    if (partitionNode == NULL) {
        check_orcc_error(ORCC_ERR_ROXML_NODE_PART);
    }

    for (i = 0; i < mapping->number_of_threads; i++) {
        node_t* processorNode = roxml_add_node(partitionNode, 0, ROXML_ELM_NODE, "Partition", NULL);

        char* procId = (char*) malloc(sizeof(int));
        sprintf(procId, "%d", i);
        roxml_add_node(processorNode, 0, ROXML_ATTR_NODE, "id", procId);

        for (j = 0; j < mapping->partitions_size[i]; j++) {
            node_t* instanceNode = roxml_add_node(processorNode, 0, ROXML_ELM_NODE, "Instance", NULL);
            roxml_add_node(instanceNode, 0, ROXML_ATTR_NODE, "id", mapping->partitions_of_actors[i][j]->name);
        }
    }

    roxml_commit_changes(rootNode, fileName, NULL, 1);
    roxml_close(rootNode);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Mapping saved successfully\n");
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

void start_orcc_mapping(options_t *opt) {
    assert(opt != NULL);
    int ret = ORCC_OK;

    network_t *network = (network_t*) malloc(sizeof(network_t));
    mapping_t *mapping = allocate_mapping(opt->nb_processors);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Starting Orcc-Map");
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Nb of processors\t: %d", opt->nb_processors);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Input file\t\t: %s", opt->input_file);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Output file\t: %s", opt->output_file);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Mapping strategy\t: %s", ORCC_STRATEGY_TXT[opt->strategy]);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "  Verbose level\t: %d", verbose_level);

    ret = load_network(opt->input_file, network);

    ret = do_mapping(network, *opt, mapping);

    ret = save_mapping(opt->output_file, mapping);

    delete_mapping(mapping);
    free(network);
}

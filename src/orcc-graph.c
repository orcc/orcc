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
#include "orcc-graph.h"
#include "roxml.h"

trace_level_et trace_level = ORCC_TL_QUIET;

/********************************************************************************************
 *
 * Orcc-Map utils functions
 *
 ********************************************************************************************/

void print_orcc_error(orccmap_error_et error) {
    char *msg;
    if (error != ORCC_OK) {
        printf("\nORCC-MAP : ERROR : %s", msg);
    }
}

void check_metis_error(rstatus_et error) {
    if (error != METIS_OK) {
        print_orcc_error(ORCC_ERR_METIS);
        printf("\t Code: %d", error);
        exit(error);
    }
}

void check_orcc_error(orccmap_error_et error) {
    if (error != ORCC_OK) {
        print_orcc_error(error);
        exit(error);
    }
}

boolean print_trace_block(trace_level_et level) {
    return (level<=trace_level)?TRUE:FALSE;
}

void print_orcc_trace(trace_level_et level, char *trace, ...) {
    assert(trace != NULL);

    if (level <= trace_level) {
        printf("\nORCC-MAP : %s", trace);
    }
}

void set_trace_level(trace_level_et level) {
    trace_level = level;
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
    opt->strategy = ORCC_MS_METIS_KWAY;
    opt->nb_processors = 1;

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
 * If the graph is supposed to be undirected, each edge will appears 2 times.
 */
adjacency_list *allocate_graph(network_t network, boolean is_directed) {
    adjacency_list *graph;
    int mult = (is_directed == TRUE)?1:2;
    graph = (adjacency_list*) malloc(sizeof(adjacency_list));
    graph->xadj = (idx_t*) malloc(sizeof(idx_t) * (network.nb_actors + 1));
    graph->vwgt = (idx_t*) malloc(sizeof(idx_t) * (network.nb_actors));
    graph->adjncy = (idx_t*) malloc(sizeof(idx_t) * network.nb_connections * mult);
    graph->adjwgt = (idx_t*) malloc(sizeof(idx_t) * network.nb_connections * mult);

    graph->is_directed = is_directed;
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
 * Functions for Graph CSR data structure
 *
 ********************************************************************************************/

boolean is_directed(adjacency_list al) {
    return al.is_directed;
}

int set_directed_graph_from_network(adjacency_list *graph, network_t network) {
    int ret = ORCC_OK;
    int i, j, k = 0;

    print_orcc_trace(ORCC_TL_TRACES, "Function : set_directed_graph_from_network");

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

    graph->xadj[network.nb_actors] = network.nb_connections;
    graph->nb_vertices = network.nb_actors;
    graph->nb_edges = network.nb_connections;

    if (print_trace_block(ORCC_TL_DEBUG) == TRUE) {
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : Directed graph data");
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : CSR xadj : ");
        for (i = 0; i < network.nb_actors + 1; i++) {
            printf("%d ", graph->xadj[i]);
        }
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : CSR adjncy : ");
        for (i = 0; i < network.nb_connections; i++) {
            printf("%d ", graph->adjncy[i]);
        }
    }

    return ret;
}

int set_undirected_graph_from_network(adjacency_list *graph, network_t network) {
    int ret = ORCC_OK;
    int i, j, k = 0;

    print_orcc_trace(ORCC_TL_TRACES, "Function : set_undirected_graph_from_network");

    for (i = 0; i < network.nb_actors; i++) {
        graph->xadj[i] = k;
        graph->vwgt[i] = network.actors[i]->workload;
        for (j = 0; j < network.nb_connections; j++) {
            if (network.connections[j]->src == network.actors[i]) {
                graph->adjncy[k] = network.connections[j]->dst->id;
                graph->adjwgt[k] = network.connections[j]->workload;
                k++;
            } else if (network.connections[j]->dst == network.actors[i]) {
                graph->adjncy[k] = network.connections[j]->src->id;
                graph->adjwgt[k] = network.connections[j]->workload;
                k++;
            }
        }
    }

    graph->xadj[network.nb_actors] = network.nb_connections * 2;
    graph->nb_vertices = network.nb_actors;
    graph->nb_edges = network.nb_connections;

    if (print_trace_block(ORCC_TL_DEBUG) == TRUE) {
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : Undirected graph data");
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : CSR xadj : ");
        for (i = 0; i < network.nb_actors + 1; i++) {
            printf("%d ", graph->xadj[i]);
        }
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : CSR adjncy : ");
        for (i = 0; i < network.nb_connections * 2; i++) {
            printf("%d ", graph->adjncy[i]);
        }
    }

    return ret;
}

int set_graph_from_network(adjacency_list *graph, network_t network) {
    int ret = ORCC_OK;

    print_orcc_trace(ORCC_TL_TRACES, "Function : setGraphFromNetwork");

    if (is_directed(*graph) == TRUE) {
        ret = set_directed_graph_from_network(graph, network);
    } else {
        ret = set_undirected_graph_from_network(graph, network);
    }

    return ret;
}

/********************************************************************************************
 *
 * Functions for Network managing
 *
 ********************************************************************************************/

actor_t *find_actor_by_name(actor_t **actors, char *name, int nb_actors) {
    actor_t *ret = NULL;
    int i = 0;

    print_orcc_trace(ORCC_TL_TRACES, "Function : find_actor_by_name");

    while (i < nb_actors && ret == NULL) {
        if (strcmp(name, actors[i]->name) == 0) {
            ret = actors[i];
        }
        i++;
    }

    return ret;
}

int swap_actors(actor_t **actors, int index1, int index2) {
    int ret = ORCC_OK;
    char* tmpActorId;
    int tmpProcessorId, tmpId;
    double tmpWorkload;
    int nb_actors = sizeof(actors)/sizeof(actor_t *);

    print_orcc_trace(ORCC_TL_TRACES, "Function : swap_actors");

    if (index1 < nb_actors && index2 < nb_actors) {
        tmpActorId = actors[index1]->name;
        actors[index1]->name = actors[index2]->name;
        actors[index2]->name = tmpActorId;

        tmpId = actors[index1]->id;
        actors[index1]->id = actors[index2]->id;
        actors[index2]->id = tmpId;

        tmpProcessorId = actors[index1]->processor_id;
        actors[index1]->processor_id = actors[index2]->processor_id;
        actors[index2]->processor_id = tmpProcessorId;

        tmpWorkload = actors[index1]->workload;
        actors[index1]->workload = actors[index2]->workload;
        actors[index2]->workload = tmpWorkload;
    } else {
        ret = ORCC_ERR_SWAP_ACTORS;
    }

    return ret;
}

int sort_actors(actor_t **actors) {
    int ret = ORCC_OK;
    int i, j;

    int nb_actors = sizeof(actors)/sizeof(actor_t *);

    print_orcc_trace(ORCC_TL_TRACES, "Function : sort_actors");

    for (i = 0; i < nb_actors; i++) {
        for (j = 0; j < nb_actors - i - 1; j++) {
            if (actors[j]->workload <= actors[j+1]->workload) {
                swap_actors(actors, j, j+1);
            }
        }
    }

    return ret;
}

int init_network(char* fileName, network_t *network) {
    int ret = ORCC_OK;
    int i = 0;

    print_orcc_trace(ORCC_TL_TRACES, "Function : init_network");

    node_t* rootNode = roxml_load_doc(fileName);

    if (rootNode == NULL) {
        printf("Error: Cannot open input file.\n");
        exit(1);
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

    roxml_close(rootNode);

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

    return ret;
}

int load_network(char *fileName, network_t *network) {
    int ret = ORCC_OK;
    int i;

    print_orcc_trace(ORCC_TL_TRACES, "Function : load_network");

    ret = init_network(fileName, network);

    node_t* rootNode = roxml_load_doc(fileName);

    if (rootNode == NULL) {
        printf("Error: Cannot open input file.\n");
        exit(1);
    }

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
            network->actors[i]->workload = 1;
            network->actors[i]->processor_id = 0;

            if (print_trace_block(ORCC_TL_DEBUG) == TRUE) {
                print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : Load ");
                printf("Actor[%d]\tname = %s\tworkload = %.2lf",
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

            network->connections[i]->workload = 1;

            if (print_trace_block(ORCC_TL_DEBUG) == TRUE) {
                print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : Load ");
                printf("Connection[%d]\tsrc = %s\tdst = %s",
                       i, network->connections[i]->src->name, network->connections[i]->dst->name);
            }

        }
        else {
            break;
        }
    }

    if (print_trace_block(ORCC_TL_VERBOSE) == TRUE) {
        print_orcc_trace(ORCC_TL_VERBOSE, "Network loaded successfully");
        print_orcc_trace(ORCC_TL_VERBOSE, "Number of actors is : ");
        printf("%d", network->nb_actors);
        print_orcc_trace(ORCC_TL_VERBOSE, "Number of connections is : ");
        printf("%d", network->nb_connections);
    }

    return ret;
}

int load_weights(char *fileName, network_t *network) {
    int ret = ORCC_OK;

    print_orcc_trace(ORCC_TL_TRACES, "Function : load_weights");

    return ret;
}


/********************************************************************************************
 *
 * Functions for Mapping data structure
 *
 ********************************************************************************************/

int set_mapping_from_partition(network_t *network, idx_t *part, mapping_t *mapping) {
    int ret = ORCC_OK;
    int i, j;
    int *counter = (int*) malloc(mapping->number_of_threads * sizeof(int));

    print_orcc_trace(ORCC_TL_TRACES, "Function : set_mapping_from_partition");

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

    if (print_trace_block(ORCC_TL_VERBOSE) == TRUE) {
        print_orcc_trace(ORCC_TL_VERBOSE, "Mapping result : ");
        for (i = 0; i < mapping->number_of_threads; i++) {
            printf("\n\tPartition %d : %d actors =>", i+1, mapping->partitions_size[i]);
            for (j=0; j < mapping->partitions_size[i]; j++) {
                printf(" %s", mapping->partitions_of_actors[i][j]->name);
            }
        }
    }
    return ret;
}

int save_mapping(char* fileName, mapping_t *mapping) {
    int ret = ORCC_OK;
    int i, j;

    print_orcc_trace(ORCC_TL_TRACES, "Function : save_mapping");

    node_t* rootNode = roxml_add_node(NULL, 0, ROXML_ELM_NODE, "xml", NULL);
    if (rootNode == NULL) {
        printf("Error: Cannot create root node.\n");
        exit(1);
    }

    node_t* configNode = roxml_add_node(rootNode, 0, ROXML_ELM_NODE, "Configuration", NULL);
    if (configNode == NULL) {
        printf("Error: Cannot create Configuration node.\n");
        exit(1);
    }

    node_t* partitionNode = roxml_add_node(configNode, 0, ROXML_ELM_NODE, "Partitioning", NULL);
    if (partitionNode == NULL) {
        printf("Error: Cannot create Partition node.\n");
        exit(1);
    }

    for (i = 0; i < mapping->number_of_threads; i++) {
        node_t* processorNode = roxml_add_node(partitionNode, 0, ROXML_ELM_NODE, "Partition", NULL);

        char* procId = (char*) malloc(sizeof(int));
        sprintf(procId, "proc_%d", i+1);
        roxml_add_node(processorNode, 0, ROXML_ATTR_NODE, "id", procId);

        for (j = 0; j < mapping->partitions_size[i]; j++) {
            node_t* instanceNode = roxml_add_node(processorNode, 0, ROXML_ELM_NODE, "Instance", NULL);
            roxml_add_node(instanceNode, 0, ROXML_ATTR_NODE, "id", mapping->partitions_of_actors[i][j]->name);
        }
    }

    roxml_commit_changes(rootNode, fileName, NULL, 1);
    roxml_close(rootNode);

    print_orcc_trace(ORCC_TL_VERBOSE, "Mapping saved successfully");
    return ret;
}

/********************************************************************************************
 *
 * Mapping functions
 *
 ********************************************************************************************/

int do_metis_recursive_partition(network_t network, options_t opt, idx_t *part) {
    int ret = ORCC_OK;
    int i;
    idx_t ncon = 1;
    idx_t objval;
    adjacency_list *graph = allocate_graph(network, (opt.strategy != ORCC_MS_METIS_REC && opt.strategy != ORCC_MS_METIS_KWAY)?TRUE:FALSE);

    print_orcc_trace(ORCC_TL_TRACES, "Function : do_metis_recursive_partition");
    print_orcc_trace(ORCC_TL_VERBOSE, "Applying METIS Recursive partition for mapping");

    ret = set_graph_from_network(graph, network);
    check_orcc_error(ret);

    ret = METIS_PartGraphRecursive(&graph->nb_vertices, /* idx_t *nvtxs */
                                   &ncon, /*idx_t *ncon*/
                                   graph->xadj, /*idx_t *xadj*/
                                   graph->adjncy, /*idx_t *adjncy*/
                                   graph->vwgt, /*idx_t *vwgt*/
                                   NULL, /*idx_t *vsize*/
                                   graph->adjwgt, /*idx_t *adjwgt*/
                                   &opt.nb_processors, /*idx_t *nparts*/
                                   NULL, /*real t *tpwgts*/
                                   NULL, /*real t ubvec*/
                                   NULL, /*idx_t *options*/
                                   &objval, /*idx_t *objval*/
                                   part); /*idx_t *part*/
    check_metis_error(ret);

    delete_graph(graph);
    return ret;
}

int do_metis_kway_partition(network_t network, options_t opt, idx_t *part) {
    int ret = ORCC_OK;
    int i;
    idx_t ncon = 1;
    idx_t objval;
    adjacency_list *graph = allocate_graph(network, (opt.strategy != ORCC_MS_METIS_REC && opt.strategy != ORCC_MS_METIS_KWAY)?TRUE:FALSE);

    print_orcc_trace(ORCC_TL_TRACES, "Function : do_metis_kway_partition");
    print_orcc_trace(ORCC_TL_VERBOSE, "Applying METIS Kway partition for mapping");

    ret = set_graph_from_network(graph, network);
    check_orcc_error(ret);

    ret = METIS_PartGraphKway(&graph->nb_vertices, /* idx_t *nvtxs */
                              &ncon, /*idx_t *ncon*/
                              graph->xadj, /*idx_t *xadj*/
                              graph->adjncy, /*idx_t *adjncy*/
                              graph->vwgt, /*idx_t *vwgt*/
                              NULL, /*idx_t *vsize*/
                              graph->adjwgt, /*idx_t *adjwgt*/
                              &opt.nb_processors, /*idx_t *nparts*/
                              NULL, /*real t *tpwgts*/
                              NULL, /*real t ubvec*/
                              NULL, /*idx_t *options*/
                              &objval, /*idx_t *objval*/
                              part); /*idx_t *part*/
    check_metis_error(ret);

    delete_graph(graph);
    return ret;
}

int do_round_robbin_mapping(network_t *network, options_t opt, idx_t *part) {
    int ret = ORCC_OK;
    int i, k;
    k = 0;

    print_orcc_trace(ORCC_TL_TRACES, "Function : do_round_robbin_mapping");
    print_orcc_trace(ORCC_TL_VERBOSE, "Applying Round Robin strategy for mapping");

    sort_actors(network->actors);

    for (i = 0; i < network->nb_actors; i++) {
        network->actors[i]->processor_id = k++;
        part[i] = network->actors[i]->processor_id;
        // There must be something needing to be improved here, i.e. invert
        // the direction of the distribution to have more balancing.
        if (k >= opt.nb_processors)
                k = 0;
    }

    if (print_trace_block(ORCC_TL_DEBUG) == TRUE) {
        print_orcc_trace(ORCC_TL_DEBUG, "DEBUG : Round Robin result");
        for (i = 0; i < network->nb_actors; i++) {
            printf("\n\tActor[%d]\tname = %s\tworkload = %.2lf\tprocessorId = %d",
                                            i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
    }
    return ret;
}

int do_mapping(network_t *network, options_t opt, mapping_t *mapping) {
    int ret = ORCC_OK;
    idx_t *part = (idx_t*) malloc(sizeof(idx_t) * (network->nb_actors));

    print_orcc_trace(ORCC_TL_TRACES, "Function : do_mapping");

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

    set_mapping_from_partition(network, part, mapping);

    free(part);
    return ret;
}

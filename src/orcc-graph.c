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

#include "orcc-graph.h"
#include "metis.h"
#include "roxml.h"

boolean isDirected(adjacency_list al) {
    return al.is_directed;
}

int addVertex(adjacency_list al) {
    int ret = 0;

    /*!TODO */

    return ret;
}

int addEdge(adjacency_list al) {
    int ret = 0;

    /*!TODO */

    return ret;
}

actor_t *findActorByNameInNetwork(char *name, network_t network) {
    actor_t *ret = NULL;

    int i = 0;
    while (i < network.nbActors && ret == NULL) {
        if (strcmp(name, network.actors[i]->name) == 0) {
            ret = network.actors[i];
        }
        i++;
    }

    return ret;
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

void swap_actors(actor_t **actors, int index1, int index2) {
    char* tmpActorId;
    int tmpProcessorId, tmpId;
    double tmpWorkload;

    tmpActorId = actors[index1]->name;
    actors[index1]->name = actors[index2]->name;
    actors[index2]->name = tmpActorId;

    tmpId = actors[index1]->id;
    actors[index1]->id = actors[index2]->id;
    actors[index2]->id = tmpId;

    tmpProcessorId = actors[index1]->processorId;
    actors[index1]->processorId = actors[index2]->processorId;
    actors[index2]->processorId = tmpProcessorId;

    tmpWorkload = actors[index1]->workload;
    actors[index1]->workload = actors[index2]->workload;
    actors[index2]->workload = tmpWorkload;
}

int sort_actors(actor_t **actors, int nb_actors) {
    int i, j, ret;

    printf("\n Sorting actors by Workload \n");

    for (i = 0; i < nb_actors; i++) {
        for (j = 0; j < nb_actors - i - 1; j++) {
            if (actors[j]->workload <= actors[j+1]->workload) {
                swap_actors(actors, j, j+1);
            }
        }
    }

    printf("The sorted list:\n");
    for (i = 0; i < nb_actors; i++) {
        printf("Actor[%d]\tid = %s\tworkload = %.2lf\n", i, actors[i]->name, actors[i]->workload);
    }

    return ret;
}

int runRoundRobinMapping(network_t *network, options_t opt, idx_t *part) {
    int i, k, ret;
    k = 0;

    sort_actors(network->actors, network->nbActors);

    printf("\n Setting mapping from Round Robin Mapping \n");

    for (i = 0; i < network->nbActors; i++) {
        network->actors[i]->processorId = k++;
        part[i] = network->actors[i]->processorId;
        // There must be something needing to be improved here, i.e. invert
        // the direction of the distribution to have more balancing.
        if (k >= opt.nb_processors)
                k = 0;
    }

    for (i = 0; i < network->nbActors; i++) {
        printf("Actor[%d]\tid = %s\tworkload = %.2lf\tprocessorId = %d\n",
                                        i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processorId);
    }

    return ret;
}

int setMappingFromPartition(network_t *network, idx_t *part, mapping_t *mapping) {
    int ret, i, j;
    int *counter = (int*) malloc(mapping->number_of_threads * sizeof(int));

    printf("\n Setting mapping  \n");
    for (i = 0; i < mapping->number_of_threads; i++) {
        mapping->partitions_size[i] = 0;
        counter[i] = 0;
    }
    for (i = 0; i < network->nbActors; i++) {
        mapping->partitions_size[part[i]]++;
    }
    for (i = 0; i < mapping->number_of_threads; i++) {
        mapping->partitions_of_actors[i] = (actor_t **) malloc(mapping->partitions_size[i] * sizeof(actor_t *));
        for (j=0; j < mapping->partitions_size[part[i]]; j++) {
            mapping->partitions_of_actors[i][j] = (actor_t *) malloc(sizeof(actor_t));
        }
    }
    for (i = 0; i < network->nbActors; i++) {
        mapping->partitions_of_actors[part[i]][counter[part[i]]] = network->actors[i];
        counter[part[i]]++;
    }

    // Update network too
    for (i=0; i < network->nbActors; i++) {
        network->actors[i]->processorId = part[i];
    }

    for (i = 0; i < mapping->number_of_threads; i++) {
        printf("Part %d = %d  ", i, mapping->partitions_size[i]);
    }

    return ret;
}

int runPartitionRecWithMETIS(adjacency_list graph, options_t opt, idx_t *part) {
    int ret, i;
    idx_t ncon = 1;
    idx_t objval;

    printf("\n METIS Recursive Partition \n");
    ret = METIS_PartGraphRecursive(&graph.nb_vertex, /* idx_t *nvtxs */
                                   &ncon, /*idx_t *ncon*/
                                   graph.xadj, /*idx_t *xadj*/
                                   graph.adjncy, /*idx_t *adjncy*/
                                   graph.vwgt, /*idx_t *vwgt*/
                                   NULL, /*idx_t *vsize*/
                                   graph.adjwgt, /*idx_t *adjwgt*/
                                   &opt.nb_processors, /*idx_t *nparts*/
                                   NULL, /*real t *tpwgts*/
                                   NULL, /*real t ubvec*/
                                   NULL, /*idx_t *options*/
                                   &objval, /*idx_t *objval*/
                                   part); /*idx_t *part*/

    for (i = 0; i < graph.nb_vertex; i++) {
        printf("%d ", part[i]);
    }
    printf("Edgecut = %d ", objval);

    return ret;
}

int runPartitionKwayWithMETIS(adjacency_list graph, options_t opt, idx_t *part) {
    int ret, i;
    idx_t ncon = 1;
    idx_t objval;

    printf("\n METIS KWay Partition \n");
    ret = METIS_PartGraphKway(&graph.nb_vertex, /* idx_t *nvtxs */
                              &ncon, /*idx_t *ncon*/
                              graph.xadj, /*idx_t *xadj*/
                              graph.adjncy, /*idx_t *adjncy*/
                              graph.vwgt, /*idx_t *vwgt*/
                              NULL, /*idx_t *vsize*/
                              graph.adjwgt, /*idx_t *adjwgt*/
                              &opt.nb_processors, /*idx_t *nparts*/
                              NULL, /*real t *tpwgts*/
                              NULL, /*real t ubvec*/
                              NULL, /*idx_t *options*/
                              &objval, /*idx_t *objval*/
                              part); /*idx_t *part*/

    for (i = 0; i < graph.nb_vertex; i++) {
        printf("%d ", part[i]);
    }
    printf("Edgecut = %d ", objval);

    return ret;
}

/**
 * Creates a mapping structure.
 */
adjacency_list *allocate_graph(network_t network, boolean is_directed) {
    adjacency_list *graph;
    int mult = (is_directed == TRUE)?1:2;
    graph = (adjacency_list*) malloc(sizeof(adjacency_list));
    graph->xadj = (idx_t*) malloc(sizeof(idx_t) * (network.nbActors + 1));
    graph->vwgt = (idx_t*) malloc(sizeof(idx_t) * (network.nbActors));
    graph->adjncy = (idx_t*) malloc(sizeof(idx_t) * network.nbConnections * mult);
    graph->adjwgt = (idx_t*) malloc(sizeof(idx_t) * network.nbConnections * mult);

    graph->is_directed = is_directed;
    return graph;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_graph(adjacency_list *graph) {
    free(graph->adjncy);
    free(graph->adjwgt);
    free(graph->vwgt);
    free(graph->xadj);
    free(graph);
}

int doMapping(network_t *network, options_t opt, mapping_t *mapping) {
    int i, ret = 0;

    adjacency_list *graph = allocate_graph(*network, (opt.strategy != ORCC_MS_METIS_REC && opt.strategy != ORCC_MS_METIS_KWAY)?TRUE:FALSE);
    setGraphFromNetwork(graph, *network);

    idx_t *part = (idx_t*) malloc(sizeof(idx_t) * (network->nbActors));
    switch (opt.strategy) {
    case ORCC_MS_METIS_REC:
        ret = runPartitionRecWithMETIS(*graph, opt, part);
        break;
    case ORCC_MS_METIS_KWAY:
        ret = runPartitionKwayWithMETIS(*graph, opt, part);
        break;
    case ORCC_MS_ROUND_ROBIN:
        ret = runRoundRobinMapping(network, opt, part);
        break;
    case ORCC_MS_OTHER:
        break;
    default:
        break;
    }

    setMappingFromPartition(network, part, mapping);

    free(part);
    delete_graph(graph);
    return ret;
}

int setDirectedGraphFromNetwork(adjacency_list *graph, network_t network) {
    int ret = 0;
    int i, j, k = 0;

    for (i = 0; i < network.nbActors; i++) {
        graph->xadj[i] = k;
        graph->vwgt[i] = network.actors[i]->workload;
        for (j = 0; j < network.nbConnections; j++) {
            if (network.connections[j]->src == network.actors[i]) {
                graph->adjncy[k] = network.connections[j]->dst->id;
                graph->adjwgt[k] = network.connections[j]->workload;
                k++;
            }
        }
    }

    graph->xadj[network.nbActors] = network.nbConnections;
    graph->nb_vertex = network.nbActors;
    graph->nb_edges = network.nbConnections;

    printf("\n DIRECTED \n");
    for (i = 0; i < network.nbActors + 1; i++) {
        printf("%d ", graph->xadj[i]);
    }
    printf("\n--------\n");
    for (i = 0; i < network.nbConnections; i++) {
        printf("%d ", graph->adjncy[i]);
    }
    graph->nb_vertex = network.nbActors;
    graph->nb_edges = network.nbConnections;

    return ret;
}

int setUndirectedGraphFromNetwork(adjacency_list *graph, network_t network) {
    int ret = 0;
    int i, j, k = 0;

    for (i = 0; i < network.nbActors; i++) {
        graph->xadj[i] = k;
        graph->vwgt[i] = network.actors[i]->workload;
        for (j = 0; j < network.nbConnections; j++) {
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

    graph->xadj[network.nbActors] = network.nbConnections * 2;
    graph->nb_vertex = network.nbActors;
    graph->nb_edges = network.nbConnections;

    printf("\n UNDIRECTED \n");
    for (i = 0; i < network.nbActors + 1; i++) {
        printf("%d ", graph->xadj[i]);
    }
    printf("\n--------\n");
    for (i = 0; i < network.nbConnections * 2; i++) {
        printf("%d ", graph->adjncy[i]);
    }

    return ret;
}

int setGraphFromNetwork(adjacency_list *graph, network_t network) {
    int ret = 0;

    if (isDirected(*graph) == TRUE) {
        ret = setDirectedGraphFromNetwork(graph, network);
    } else {
        ret = setUndirectedGraphFromNetwork(graph, network);
    }

    return ret;
}

int initNetwork(char* fileName, network_t *network) {
    int ret;

    node_t* rootNode = roxml_load_doc(fileName);

    if (rootNode == NULL) {
        printf("Error: Cannot open input file.\n");
        exit(1);
    }

    network->nbActors = 0;
    network->nbConnections = 0;

    while (1) {
        node_t* actorNode = roxml_get_chld(rootNode, NULL, network->nbActors + network->nbConnections);

        if (actorNode == NULL) {
                break;
        }

        char* nodeName = roxml_get_name(actorNode, NULL, 0);
        if (strcmp(nodeName, "Instance") == 0) {
                network->nbActors++;
        }
        else if (strcmp(nodeName, "Connection") == 0) {
                network->nbConnections++;
        }
        else {
                break;
        }
    }

    roxml_close(rootNode);

    printf("Msg: Number of actors is: %d\n", network->nbActors);
    printf("Msg: Number of connections is: %d\n", network->nbConnections);

    int i = 0;
    network->actors = (actor_t**) malloc(network->nbActors * sizeof(actor_t*));
    network->connections = (connection_t**) malloc(network->nbConnections * sizeof(connection_t*));
    for (i=0; i < network->nbConnections; i++) {
        network->actors[i] = (actor_t*) malloc(sizeof(actor_t*));
        network->connections[i] = (connection_t*) malloc(sizeof(connection_t*));
    }
    for (i=0; i < network->nbConnections; i++) {
        network->connections[i]->src = (actor_t*) malloc(sizeof(actor_t));
        network->connections[i]->dst = (actor_t*) malloc(sizeof(actor_t));
    }

    return ret;
}

int loadNetwork(char *fileName, network_t *network) {
    int ret, i, size;

    ret = initNetwork(fileName, network);

    node_t* rootNode = roxml_load_doc(fileName);

    if (rootNode == NULL) {
        printf("Error: Cannot open input file.\n");
        exit(1);
    }

    for (i = 0; i < network->nbActors; i++) {
        node_t* actorNode = roxml_get_chld(rootNode, NULL, i);

        if (actorNode == NULL) {
            break;
        }

        char* nodeName = roxml_get_name(actorNode, NULL, 0);
        if (strcmp(nodeName, "Instance") == 0) {
            node_t* nodeAttrActorId = roxml_get_attr(actorNode, "id", 0);
            network->actors[i]->name = roxml_get_content(nodeAttrActorId, NULL, 0, &size);
            network->actors[i]->id = i;
            network->actors[i]->workload = 1;
            network->actors[i]->processorId = 0;

            printf("Actor[%d]\tname = %s\tworkload = %.2lf\n",
                   i, network->actors[i]->name, network->actors[i]->workload);
        }
        else {
            break;
        }
    }

    for (i = 0; i < network->nbConnections; i++) {
        node_t* connectionNode = roxml_get_chld(rootNode, NULL, i + network->nbActors);

        if (connectionNode == NULL) {
            break;
        }

        char* nodeName = roxml_get_name(connectionNode, NULL, 0);
        if (strcmp(nodeName, "Connection") == 0) {
            node_t* nodeAttrActorSrc = roxml_get_attr(connectionNode, "src", 0);
            char *src = roxml_get_content(nodeAttrActorSrc, NULL, 0, &size);
            network->connections[i]->src = findActorByNameInNetwork(src, *network);

            node_t* nodeAttrActorDst = roxml_get_attr(connectionNode, "dst", 0);
            char *dst = roxml_get_content(nodeAttrActorDst, NULL, 0, &size);
            network->connections[i]->dst = findActorByNameInNetwork(dst, *network);

            network->connections[i]->workload = 1;

            printf("Connection[%d]\tsrc = %s\tdst = %s\n",
                   i, network->connections[i]->src->name, network->connections[i]->dst->name);

        }
        else {
            break;
        }
    }

    return ret;
}

int loadWeights(char *fileName, network_t *network) {

}

int saveMapping(char* fileName, mapping_t *mapping) {
    int ret, i, j;

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

    return ret;
}

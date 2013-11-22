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
#include "dataflow.h"
#include "graph.h"
#include "util.h"
#include "serialize.h"
#include "scheduler.h"
#include "options.h"

/**
 * Give the id of the mapped core of the given actor in the given mapping structure.
 */
int find_mapped_core(mapping_t *mapping, actor_t *actor) {
    int i;
    for (i = 0; i < mapping->number_of_threads; i++) {
        if (find_actor_by_name(mapping->partitions_of_actors[i], actor->name,
                mapping->partitions_size[i]) != NULL) {
            return i;
        }
    }
    return -1;
}

/**
 * Creates a mapping structure.
 */
mapping_t *allocate_mapping(int number_of_threads) {
    mapping_t *mapping = (mapping_t *) malloc(sizeof(mapping_t));
    mapping->number_of_threads = number_of_threads;
    mapping->partitions_of_actors = malloc(number_of_threads * sizeof(*mapping->partitions_of_actors));
    mapping->partitions_size = (int*) malloc(number_of_threads * sizeof(int));
    mapping->threads_affinities = (int*) malloc(number_of_threads * sizeof(int));
    return mapping;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_mapping(mapping_t *mapping) {
    free(mapping->partitions_size);
    free(mapping);
}

/**
 * Computes a partitionment of actors on threads from an XML file given in parameter.
 */
mapping_t* map_actors(network_t *network) {
    mapping_t *mapping;
    if (mapping_file == NULL) {
        mapping = allocate_mapping(1);
        mapping->threads_affinities[0] = 0;
        mapping->partitions_size[0] = network->nb_actors;
        mapping->partitions_of_actors[0] = network->actors;
        return mapping;
    } else {
        mapping = load_mapping(mapping_file, network);
    }
    return mapping;
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

int do_metis_recursive_partition(network_t *network, options_t *opt, idx_t *part) {
    assert(part != NULL);
    int ret = ORCC_OK;
    idx_t ncon = 1;
    idx_t metis_opt[METIS_NOPTIONS];
    idx_t objval;
    adjacency_list *graph, *metis_graph;

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying METIS Recursive partition for mapping");

    METIS_SetDefaultOptions(metis_opt);

    graph = set_graph_from_network(network);
    metis_graph = fix_graph_for_metis(graph);

    ret = METIS_PartGraphRecursive(&metis_graph->nb_vertices, /* idx_t *nvtxs */
                                   &ncon, /*idx_t *ncon*/
                                   metis_graph->xadj, /*idx_t *xadj*/
                                   metis_graph->adjncy, /*idx_t *adjncy*/
                                   metis_graph->vwgt, /*idx_t *vwgt*/
                                   NULL, /*idx_t *vsize*/
                                   metis_graph->adjwgt, /*idx_t *adjwgt*/
                                   &opt->nb_processors, /*idx_t *nparts*/
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

int do_metis_kway_partition(network_t *network, options_t *opt, idx_t *part) {
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
    metis_graph = fix_graph_for_metis(graph);

    ret = METIS_PartGraphKway(&metis_graph->nb_vertices, /* idx_t *nvtxs */
                              &ncon, /*idx_t *ncon*/
                              metis_graph->xadj, /*idx_t *xadj*/
                              metis_graph->adjncy, /*idx_t *adjncy*/
                              metis_graph->vwgt, /*idx_t *vwgt*/
                              NULL, /*idx_t *vsize*/
                              metis_graph->adjwgt, /*idx_t *adjwgt*/
                              &opt->nb_processors, /*idx_t *nparts*/
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

int do_round_robbin_mapping(network_t *network, options_t *opt, idx_t *part) {
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
        if (k >= opt->nb_processors)
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

int do_mapping(network_t *network, options_t *opt, mapping_t *mapping) {
    assert(network != NULL);
    assert(mapping != NULL);
    int i;
    int ret = ORCC_OK;
    idx_t *part = (idx_t*) malloc(sizeof(idx_t) * (network->nb_actors));

    if (opt->nb_processors != 1) {
        switch (opt->strategy) {
        case ORCC_MS_METIS_REC:
            ret = do_metis_recursive_partition(network, opt, part);
            break;
        case ORCC_MS_METIS_KWAY:
            ret = do_metis_kway_partition(network, opt, part);
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

/**
 * Main routine of the mapping agent.
 */
void *agent_routine(void *data) {
    agent_t *agent = (agent_t*) data;
    int i;

    while (1) {
        // wait threads synchro
        for (i = 0; i < agent->nb_threads; i++) {
            semaphore_wait(agent->sync->sem_monitor);
        }

        printf("\nRemap...\n\n");

        do_mapping(agent->network, agent->options, agent->mapping);
        apply_mapping(agent->mapping, agent->scheduler, agent->nb_threads);

        resetMapping();
        // wakeup all threads
        for (i = 0; i < agent->nb_threads; i++) {
            semaphore_set(agent->scheduler->schedulers[i]->sem_thread);
        }

    }

    return 0;
}

/**
 * Initialize the given agent structure.
 */
agent_t* agent_init(sync_t *sync, options_t *options, global_scheduler_t *scheduler, network_t *network, int nb_threads) {
    agent_t *agent = (agent_t *) malloc(sizeof(agent_t));
    agent->sync = sync;
    agent->options = options;
    agent->scheduler = scheduler;
    agent->network = network;
    agent->mapping = allocate_mapping(nb_threads);
    agent->nb_threads = nb_threads;
    return agent;
}

int needMapping() {
    return 0;
}

void resetMapping() {

}

/**
 * Apply the given mapping to the schedulers
 */
void apply_mapping(mapping_t *mapping, global_scheduler_t *scheduler, int nbThreads) {
    int i;
    for (i = 0; i < nbThreads; i++) {
        sched_reinit(scheduler->schedulers[i], mapping->partitions_size[i], mapping->partitions_of_actors[i], 0);
    }
}

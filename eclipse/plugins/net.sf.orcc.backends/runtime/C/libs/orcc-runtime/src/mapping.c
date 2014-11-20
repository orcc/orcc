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
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "mapping.h"
#include "dataflow.h"
#include "graph.h"
#include "util.h"
#include "scheduler.h"
#include "options.h"
#include "trace.h"
#include "cycle.h"
#include "profiling.h"

#ifdef THREADS_ENABLE
#include "thread.h"
#endif
#ifdef ROXML_ENABLE
#include "serialize.h"
#endif

/*
 * Functions declared in fps_print.c
 * No header file is associated
 */
extern void fpsPrintInit_mapping();
extern int get_partialNumPicturesDecoded();
extern void reset_partialNumPicturesDecoded();

int need_remap = TRUE;

/**
 * Give the id of the mapped core of the given actor in the given mapping structure.
 */
int find_mapped_core(mapping_t *mapping, actor_t *actor) {
    int i;
    assert(mapping != NULL);
    assert(actor != NULL);
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
mapping_t *allocate_mapping(int nb_procs, int nb_actors) {
    int i;
    mapping_t *mapping;
    assert(nb_procs > 0);
    assert(nb_actors > 0);
    mapping = (mapping_t *) malloc(sizeof(mapping_t));
    mapping->number_of_threads = nb_procs;
    mapping->partitions_of_actors = (actor_t***) malloc(nb_procs * sizeof(actor_t**));
    for(i = 0; i < nb_procs; i++) {
        mapping->partitions_of_actors[i] = (actor_t**) malloc(nb_actors * sizeof(actor_t*));
    }
    mapping->partitions_size = (int*) malloc(nb_procs * sizeof(int));
    mapping->threads_affinities = (int*) malloc(nb_procs * sizeof(int));
    return mapping;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_mapping(mapping_t *mapping) {
    assert(mapping != NULL);
    free(mapping->partitions_size);
    free(mapping);
}

/**
 * Computes a partitionment of actors on threads from an XML file given in parameter.
 */
mapping_t* map_actors(network_t *network, options_t *opt) {
    mapping_t *mapping;
    assert(network != NULL);

    if (opt->mapping_input_file == NULL) {
        // Create mapping with only one partition
        mapping = allocate_mapping(1, network->nb_actors);
        mapping->threads_affinities[0] = 0;
        mapping->partitions_size[0] = network->nb_actors;
        memcpy(mapping->partitions_of_actors[0], network->actors, network->nb_actors * sizeof(actor_t*));
        return mapping;
    } else {
#ifdef ROXML_ENABLE
        mapping = load_mapping(opt->mapping_input_file, network);
        if(mapping->number_of_threads > opt->nb_processors){
            opt->nb_processors = mapping->number_of_threads;
        }
#endif
    }
    return mapping;
}

/**
 * Creates a mapping structure.
 */
proc_info_t* init_processors(int number_of_threads) {
    int i;

    proc_info_t* processors = (proc_info_t *) malloc(number_of_threads * sizeof(proc_info_t));
    for (i = 0; i < number_of_threads; i++) {
        processors[i].processor_id = i;
        processors[i].utilization = 0;
    }

    return processors;
}

/**
 * Releases memory of the given mapping structure.
 */
void delete_processors(proc_info_t *processors) {
    free(processors);
}

/********************************************************************************************
 *
 * Functions for results printing
 *
 ********************************************************************************************/

void print_load_balancing(mapping_t *mapping) {
    int i, j;
    int nb_proc = 0, nbPartitions = 0;
    int totalWeight = 0, maxWeight = 0, partWeight = 0;
    double avgWeight = 0;
    assert(mapping != NULL);

    for (i = 0; i < mapping->number_of_threads; i++) {
        partWeight = 0;
        for (j = 0; j < mapping->partitions_size[i]; j++) {
            totalWeight += mapping->partitions_of_actors[i][j]->workload;
            partWeight += mapping->partitions_of_actors[i][j]->workload;
            nb_proc++;
        }

        if (mapping->partitions_size[i] > 0) {
            nbPartitions++;
        }

        print_orcc_trace(ORCC_VL_VERBOSE_2, "Weight of partition %d : %d", i+1, partWeight);
        if (maxWeight < partWeight)
            maxWeight = partWeight;
    }

    avgWeight = (totalWeight / mapping->number_of_threads);
    print_orcc_trace(ORCC_VL_VERBOSE_2, "Average weight: %.2lf   Max weight: %d", avgWeight, maxWeight);
    print_orcc_trace(ORCC_VL_VERBOSE_1, "Load balancing %.2lf on %d partitions", maxWeight/avgWeight, nbPartitions);
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
    int ret = ORCC_OK;
    actor_t *actor;
    assert(actors != NULL);

    if (index1 < nb_actors && index2 < nb_actors) {
        actor = actors[index1];
        actors[index1] = actors[index2];
        actors[index1] = actor;
        actors[index1]->id = index1;
        actors[index2]->id = index2;
    } else {
        ret = ORCC_ERR_SWAP_ACTORS;
    }

    return ret;
}

int sort_actors(actor_t **actors, int nb_actors) {
    int ret = ORCC_OK;
    int i, j;
    assert(actors != NULL);

    for (i = 0; i < nb_actors; i++) {
        for (j = 0; j < nb_actors - i - 1; j++) {
            if (actors[j]->workload <= actors[j+1]->workload) {
                swap_actors(actors, j, j+1, nb_actors);
            }
        }
    }

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
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
    int ret = ORCC_OK;
    int i;
    int *counter;
    assert(network != NULL);
    assert(part != NULL);
    assert(mapping != NULL);

    counter = malloc(mapping->number_of_threads * sizeof(counter));

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
    for (i = 0; i < network->nb_actors; i++) {
        network->actors[i]->processor_id = part[i];
    }

    free(counter);
    return ret;
}

void print_mapping(mapping_t *mapping) {
    int i, j;
    print_orcc_trace(ORCC_VL_VERBOSE_2, "Mapping result : ");
    for (i = 0; i < mapping->number_of_threads; i++) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "\tPartition %d : %d actors", i+1, mapping->partitions_size[i]);
        for (j = 0; j < mapping->partitions_size[i]; j++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "\t\t%s", mapping->partitions_of_actors[i][j]->name);
        }
    }
}


/********************************************************************************************
 *
 * Mapping functions
 *
 ********************************************************************************************/

#ifdef METIS_ENABLE
int do_metis_recursive_partition(network_t *network, options_t *opt, idx_t *part) {
    int ret = ORCC_OK;
    idx_t ncon = 1;
    idx_t metis_opt[METIS_NOPTIONS];
    idx_t objval;
    adjacency_list *graph, *metis_graph;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

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

int do_metis_kway_partition(network_t *network, options_t *opt, idx_t *part, idx_t mode) {
    int ret = ORCC_OK;
    idx_t ncon = 1;
    idx_t metis_opt[METIS_NOPTIONS];
    idx_t objval;
    adjacency_list *graph, *metis_graph;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying METIS Kway partition for mapping");

    METIS_SetDefaultOptions(metis_opt);
    metis_opt[METIS_OPTION_OBJTYPE] = mode;
    metis_opt[METIS_OPTION_CONTIG] = 0;  /* 0 or 1 */

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
#endif

/**
 * Round Robin strategy
 * @author Long Nguyen
 */
int do_round_robbin_mapping(network_t *network, options_t *opt, idx_t *part) {
    int ret = ORCC_OK;
    int i, k = 0;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

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

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Round Robin result");
        for (i = 0; i < network->nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tname = %s\tworkload = %d\tprocessorId = %d",
                             i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
    }

    return ret;
}

/**
 * Quick Mapping strategy
 * @author Long Nguyen
 */
int get_processor_id_of_actor(network_t *network, int actorId) {
    int i;

    for (i = 0; i < network->nb_actors; i++) {
        if (network->actors[i]->id == actorId) {
            return network->actors[i]->processor_id;
        }
    }

    return 0;
}

int do_quick_mapping(network_t *network, options_t *opt, idx_t *part) {
    int ret = ORCC_OK;
    int i, unMappedActors, selectedProcIndex, maxCommCost, maxIndex, total_workload = 0;
    proc_info_t *processors;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

    processors = init_processors(opt->nb_processors);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying Quick Mapping strategy for mapping");

    unMappedActors = network->nb_actors;
    selectedProcIndex = 0;

    for (i = 0; i < network->nb_actors; i++) {
        total_workload += network->actors[i]->workload;
    }

    while (unMappedActors > 0) {
        maxIndex = 0;
        maxCommCost = 0;

        for (i = 0; i < network->nb_actors; i++) {
            if (network->actors[i]->processor_id == -1) {
                if (network->actors[i]->triedProcId != processors[selectedProcIndex].processor_id) {
                    network->actors[i]->commCost = 0;
                    network->actors[i]->triedProcId = processors[selectedProcIndex].processor_id;
                }

                if (network->actors[i]->commCost >= maxCommCost) {
                    maxCommCost = network->actors[i]->commCost;
                    maxIndex = i;
                }
            }
        }

        network->actors[maxIndex]->processor_id = processors[selectedProcIndex].processor_id;
        part[maxIndex] = network->actors[maxIndex]->processor_id;
        processors[selectedProcIndex].utilization += network->actors[maxIndex]->workload;

        for (i = 0; i < network->nb_connections; i++) {
            if (network->connections[i]->src->id == network->actors[maxIndex]->id) {
                network->actors[network->connections[i]->dst->id]->commCost += network->connections[i]->workload;
            }

            if (network->connections[i]->dst->id == network->actors[maxIndex]->id) {
                network->actors[network->connections[i]->src->id]->commCost += network->connections[i]->workload;
            }
        }

        if (processors[selectedProcIndex].utilization >= total_workload / opt->nb_processors
            && selectedProcIndex < opt->nb_processors - 1) {
            selectedProcIndex++;
        }

        unMappedActors--;
    }

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : QM result");
        for (i = 0; i < network->nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tname = %s\tworkload = %d\tprocessorId = %d",
                             i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
        for (i = 0; i < opt->nb_processors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Workload Proc[%d]: %d",
                             i, processors[i].utilization);
        }
    }

    delete_processors(processors);
    return ret;
}

/**
 * Weighted Load Balancing strategy
 * @author Long Nguyen
 */
void assign_actor_to_min_utilized_processor(network_t *network, idx_t *part, proc_info_t *processors, int nb_processors, int actorIndex) {
    int i, minIndex = 0;
    assert(processors != NULL);
    assert(part != NULL);
    assert(processors != NULL);

    for (i = 0; i < nb_processors; i++) {
        if (processors[i].utilization < processors[minIndex].utilization) {
            minIndex = i;
        }
    }

    network->actors[actorIndex]->processor_id = processors[minIndex].processor_id;
    part[actorIndex] = network->actors[actorIndex]->processor_id;

    processors[minIndex].utilization += network->actors[actorIndex]->workload;
}

int do_weighted_round_robin_mapping(network_t *network, options_t *opt, idx_t *part) {
    int ret = ORCC_OK;
    int i;
    proc_info_t *processors;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

    processors = init_processors(opt->nb_processors);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying Weighted Load Balancing strategy for mapping");

    sort_actors(network->actors, network->nb_actors);

    for (i = 0; i < network->nb_actors; i++) {
        assign_actor_to_min_utilized_processor(network, part, processors, opt->nb_processors, i);
    }

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : WLB result");
        for (i = 0; i < network->nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tname = %s\tworkload = %d\tprocessorId = %d",
                             i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
    }

    delete_processors(processors);
    return ret;
}

/**
 * Communication Optimized Weighted Load Balancing strategy
 * @author Long Nguyen
 */
int find_min_utilized_processor(proc_info_t *processors, int nb_processors) {
    int i, minIndex = 0;
    assert(processors != NULL);

    for (i = 0; i < nb_processors; i++) {
        if (processors[i].utilization < processors[minIndex].utilization) {
            minIndex = i;
        }
    }

    return minIndex;
}

int calculate_comm_of_actor(network_t *network, proc_info_t *processors, int actorIndex, int procIndex) {
    int i, procId, comm = 0;
    assert(processors != NULL);

    for (i = 0; i < network->nb_connections; i++) {
        if (network->connections[i]->src->id == network->actors[actorIndex]->id) {
            procId = get_processor_id_of_actor(network, network->connections[i]->dst->id);
            if (procId != -1 && procId != processors[procIndex].processor_id) {
                comm += network->connections[i]->workload;
            }
        }

        if (network->connections[i]->dst->id == network->actors[actorIndex]->id) {
            procId = get_processor_id_of_actor(network, network->connections[i]->src->id);
            if (procId != -1 && procId != processors[procIndex].processor_id) {
                comm += network->connections[i]->workload;
            }
        }
    }

    return comm;
}

int do_weighted_round_robin_comm_mapping(network_t *network, options_t *opt, idx_t *part) {
    int ret = ORCC_OK;
    int i, j;
    int selectedProc, minCommIndex;
    proc_info_t *processors;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying Communication Optimized Weighted strategy for mapping");

    processors = init_processors(opt->nb_processors);
    sort_actors(network->actors, network->nb_actors);

    for (i = 0; i < network->nb_actors; i++) {
        selectedProc = find_min_utilized_processor(processors, opt->nb_processors);

        minCommIndex = -1;

        for (j = 0; j < network->nb_actors; j++) {
            if (network->actors[j]->processor_id == -1) {
                if (minCommIndex == -1) {
                    minCommIndex = j;
                }

                if (calculate_comm_of_actor(network, processors, j, selectedProc)
                    <= calculate_comm_of_actor(network, processors, minCommIndex, selectedProc)) {
                    minCommIndex = j;
                }
            }
        }

        network->actors[minCommIndex]->processor_id = processors[selectedProc].processor_id;
        part[minCommIndex] = network->actors[minCommIndex]->processor_id;
        processors[selectedProc].utilization += network->actors[minCommIndex]->workload;
    }

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : COWLB result");
        for (i = 0; i < network->nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tname = %s\tworkload = %d\tprocessorId = %d",
                             i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
        for (i = 0; i < opt->nb_processors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Workload Proc[%d]: %d",
                             i, processors[i].utilization);
        }
    }

    delete_processors(processors);
    return ret;
}

/**
 * Kernighan Lin Refinement Weighted Load Balancing strategy
 * @author Long Nguyen
 */
int get_gain_of_actor(network_t *network, proc_info_t *processors, int actorIndex, int commProcessorIndex) {
    int i, comm1, comm2;
    assert(network != NULL);
    assert(processors != NULL);

    comm1 = 0;
    comm2 = 0;

    for (i = 0; i < network->nb_connections; i++) {
        if (network->connections[i]->src->id == network->actors[actorIndex]->id) {
            if (get_processor_id_of_actor(network, network->connections[i]->dst->id) == network->actors[actorIndex]->processor_id) {
                comm1 += network->connections[i]->workload;
            }

            if (get_processor_id_of_actor(network, network->connections[i]->dst->id) == processors[commProcessorIndex].processor_id) {
                comm2 += network->connections[i]->workload;
            }
        }

        if (network->connections[i]->dst->id == network->actors[actorIndex]->id) {
            if (get_processor_id_of_actor(network, network->connections[i]->src->id) == network->actors[actorIndex]->processor_id) {
                comm1 += network->connections[i]->workload;
            }

            if (get_processor_id_of_actor(network, network->connections[i]->src->id) == processors[commProcessorIndex].processor_id) {
                comm2 += network->connections[i]->workload;
            }
        }
    }

    return (comm2 - comm1);
}

void optimize_communication(network_t *network, idx_t *part, proc_info_t *processors, int procIndex1, int procIndex2) {
    int index1 = 0, index2 = 0;
    assert(network != NULL);
    assert(processors != NULL);
    assert(part != NULL);

    while(index1 < network->nb_actors && index2 < network->nb_actors) {
        if (processors[procIndex1].utilization >= processors[procIndex2].utilization) {
            if (index1 >= network->nb_actors) {
                break;
            }

            while ((network->actors[index1]->evaluated == 1
                || network->actors[index1]->processor_id != processors[procIndex1].processor_id)
                    && index1 < network->nb_actors) {
                index1++;
            }
            if (index1 < network->nb_actors) {
                if (get_gain_of_actor(network, processors, index1, procIndex2) > 0) {
                        network->actors[index1]->processor_id = processors[procIndex2].processor_id;
                        part[index1] = network->actors[index1]->processor_id;
                        processors[procIndex2].utilization += network->actors[index1]->workload;
                        processors[procIndex1].utilization -= network->actors[index1]->workload;
                        network->actors[index1]->evaluated = 1;
                }

                index1++;
            }
        }
        else {
            if (index2 >= network->nb_actors) {
                break;
            }

            while ((network->actors[index2]->evaluated == 1
                || network->actors[index2]->processor_id != processors[procIndex2].processor_id)
                    && index2 < network->nb_actors) {
                index2++;
            }
            if (index2 < network->nb_actors) {
                if (get_gain_of_actor(network, processors, index2, procIndex1) > 0) {
                        network->actors[index2]->processor_id = processors[procIndex1].processor_id;
                        part[index2] = network->actors[index2]->processor_id;
                        processors[procIndex1].utilization += network->actors[index2]->workload;
                        processors[procIndex2].utilization -= network->actors[index2]->workload;
                        network->actors[index2]->evaluated = 1;
                }

                index2 ++;
            }
        }
    }
}

void do_KL_algorithm(network_t *network, idx_t *part, proc_info_t *processors, int nb_processors) {
    int i;
    assert(network != NULL);
    assert(processors != NULL);
    assert(part != NULL);

    for (i = 0; i < nb_processors - 1; i += 2) {
        optimize_communication(network, part, processors, i, i + 1);
    }
}

int do_KLR_mapping(network_t *network, options_t *opt, idx_t *part) {
    int ret = ORCC_OK;
    int i;
    proc_info_t *processors;
    assert(network != NULL);
    assert(opt != NULL);
    assert(part != NULL);

    processors = init_processors(opt->nb_processors);

    print_orcc_trace(ORCC_VL_VERBOSE_1, "Applying Kernighan Lin Refinement Weighted strategy for mapping");

    sort_actors(network->actors, network->nb_actors);

    for (i = 0; i < network->nb_actors; i++) {
        assign_actor_to_min_utilized_processor(network, part, processors, opt->nb_processors, i);
    }

    do_KL_algorithm(network, part, processors, opt->nb_processors);

    if (check_verbosity(ORCC_VL_VERBOSE_2) == TRUE) {
        print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : KLRLB result");
        for (i = 0; i < network->nb_actors; i++) {
            print_orcc_trace(ORCC_VL_VERBOSE_2, "DEBUG : Actor[%d]\tname = %s\tworkload = %d\tprocessorId = %d",
                             i, network->actors[i]->name, network->actors[i]->workload, network->actors[i]->processor_id);
        }
    }

    delete_processors(processors);
    return ret;
}

/**
 * Entry point for all mapping strategies
 */
int do_mapping(network_t *network, options_t *opt, mapping_t *mapping) {
    int ret = ORCC_OK;
    idx_t *part;
    ticks startTime, endTime;
    assert(network != NULL);
    assert(opt != NULL);
    assert(mapping != NULL);

    part = (idx_t*) malloc(sizeof(idx_t) * (network->nb_actors));

    if(check_verbosity(ORCC_VL_VERBOSE_2)) {
        print_network(network);
    }

    startTime = getticks();

    if (opt->nb_processors != 1) {
        switch (opt->mapping_strategy) {
#ifdef METIS_ENABLE
        case ORCC_MS_METIS_REC:
            ret = do_metis_recursive_partition(network, opt, part);
            break;
        case ORCC_MS_METIS_KWAY_CV:
            ret = do_metis_kway_partition(network, opt, part, METIS_OBJTYPE_CUT); /*TODO : should be METIS_OBJTYPE_VOL : Metis seem's to invert its options */
            break;
        case ORCC_MS_METIS_KWAY_EC:
            ret = do_metis_kway_partition(network, opt, part, METIS_OBJTYPE_VOL); /*TODO : should be METIS_OBJTYPE_CUT : Metis seem's to invert its options */
            break;
#endif
        case ORCC_MS_ROUND_ROBIN:
            ret = do_round_robbin_mapping(network, opt, part);
            break;
        case ORCC_MS_QM:
            ret = do_quick_mapping(network, opt, part);
            break;
        case ORCC_MS_WLB:
            ret = do_weighted_round_robin_mapping(network, opt, part);
            break;
        case ORCC_MS_COWLB:
            ret = do_weighted_round_robin_comm_mapping(network, opt, part);
            break;
        case ORCC_MS_KRWLB:
            ret = do_KLR_mapping(network, opt, part);
            break;
        default:
            break;
        }
    } else {
        int i;
        for (i = 0; i < network->nb_actors; i++) {
            part[i] = 0;
        }
    }

    endTime = getticks();

    set_mapping_from_partition(network, part, mapping);

    if(check_verbosity(ORCC_VL_VERBOSE_1)) {
        print_mapping(mapping);
        print_load_balancing(mapping);
        print_edge_cut(network);
        print_orcc_trace(ORCC_VL_VERBOSE_2, "Mapping time : %2.lf", elapsed(endTime, startTime));
    }

    free(part);
    return ret;
}

/**
 * Main routine of the mapping agent.
 */
void *agent_routine(void *data) {
    agent_t *agent = (agent_t*) data;
    assert(agent != NULL);

    while (1) {
        int i;

#ifdef THREADS_ENABLE
        // wait threads synchro
        for (i = 0; i < agent->nb_threads; i++) {
            orcc_semaphore_wait(agent->sem_agent);
        }
#endif

        print_orcc_trace(ORCC_VL_VERBOSE_1, "Remap the actors...");
        compute_workloads(agent->network);
        do_mapping(agent->network, agent->options, agent->mapping);
		if (agent->options->mapping_output_file) {
			save_mapping(agent->options->mapping_output_file, agent->mapping);
		}
        apply_mapping(agent->mapping, agent->scheduler, agent->nb_threads);

        if(opt->mapping_repetition == REMAP_ALWAYS) {
            reset_profiling(agent->network);
            resetMapping();
        } else {
            need_remap = FALSE;
            fpsPrintInit_mapping();
        }

#ifdef THREADS_ENABLE
        // wakeup all threads
        for (i = 0; i < agent->nb_threads; i++) {
            orcc_semaphore_set(agent->scheduler->schedulers[i]->sem_thread);
        }
#endif

    }
		
    return 0;
}

/**
 * Initialize the given agent structure.
 */
agent_t* agent_init(options_t *options, global_scheduler_t *scheduler, network_t *network, int nb_threads) {
    agent_t *agent = (agent_t *) malloc(sizeof(agent_t));
    agent->options = options;
    agent->scheduler = scheduler;
    agent->network = network;
    agent->mapping = allocate_mapping(nb_threads, network->nb_actors);
    agent->nb_threads = nb_threads;
#ifdef THREADS_ENABLE
    orcc_semaphore_create(agent->sem_agent, 0);
#endif
    return agent;
}

int needMapping() {
    return get_partialNumPicturesDecoded() > opt->nbProfiledFrames && need_remap && opt->enable_dynamic_mapping;
}

void resetMapping() {
    reset_partialNumPicturesDecoded();
}

/**
 * Apply the given mapping to the schedulers
 */
void apply_mapping(mapping_t *mapping, global_scheduler_t *scheduler, int nbThreads) {
    int i;
    assert(mapping != NULL);
    assert(scheduler != NULL);

    for (i = 0; i < nbThreads; i++) {
        sched_reinit(scheduler->schedulers[i], mapping->partitions_size[i], mapping->partitions_of_actors[i]);
    }
}

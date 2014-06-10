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
 *   * Neither the name of the INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIREnum_inputsCT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#ifndef _ORCC_DATAFLOW_H_
#define _ORCC_DATAFLOW_H_

#include "orcc.h"

/*
 * Actions
 */
struct action_s {
    char *name;
    double workload; /** action's workload */
    double ticks; /** elapsed ticks obtained by profiling */
    double min_ticks; /** elapsed min clockcycles obtained by profiling */
    double avg_ticks; /** elapsed average clockcycles obtained by profiling */
    double max_ticks; /** elapsed max clockcycles obtained by profiling */
    double variance_ticks; /** elapsed clockcycles variance obtained by profiling */
    int firings; /** nb of firings for profiling */
};

/*
 * Actors are the vertices of orcc Networks
 */
struct actor_s {
    char *name;
    void (*init_func)();
    void (*sched_func)(schedinfo_t *);
    int num_inputs; /** number of input ports */
    int num_outputs; /** number of output ports */
    int in_list; /** set to 1 when the actor is in the schedulable list. Used by add_schedulable to do the membership test in O(1). */
    int in_waiting; /** idem with the waiting list. */
    local_scheduler_t *sched; /** scheduler which execute this actor. */
    int processor_id; /** id of the processor core mapped to this actor. */
    int id;
    int commCost;  /** Used by Quick Mapping algo */
    int triedProcId;  /** Used by Quick Mapping algo */
    int evaluated;  /** Used by KL algo */
    int workload; /** actor's workload */
    double ticks; /** elapsed ticks obtained by profiling */
    action_t **actions;
    int nb_actions;
    double scheduler_workload;
    char *class_name;
    int firings; /** nb of firings for profiling */
    int switches; /** nb of switches for profiling */
    int misses; /** nb of misses for profiling */
};

/*
 * Connections are the edges of orcc Networks
 */
struct connection_s {
    actor_t *src;
    actor_t *dst;
    int workload; /** connections's workload */
    long rate; /** communication rate obtained by profiling */
};

/*
 * Orcc Networks are directed graphs
 */
struct network_s {
    char *name;
    actor_t **actors;
    connection_t **connections;
    int nb_actors;
    int nb_connections;
};

/**
 * Find actor by its name in the given table.
 */
actor_t *find_actor_by_name(actor_t **actors, char *name, int nb_actors);

network_t* allocate_network(int nb_actors, int nb_connections);

void print_network(network_t *network);

#endif  /* _ORCC_DATAFLOW_H_ */

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
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#include <stdlib.h>
#include <assert.h>

#include "dataflow.h"
#include "trace.h"

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

network_t* allocate_network(int nb_actors, int nb_connections) {
    int i;
    network_t *network = (network_t*) malloc(sizeof(network_t));

    network->nb_actors = nb_actors;
    network->nb_connections = nb_connections;
    network->actors = (actor_t**) malloc(nb_actors * sizeof(actor_t*));
    network->connections = (connection_t**) malloc(nb_connections * sizeof(connection_t*));

    for (i=0; i < network->nb_connections; i++) {
        network->connections[i] = (connection_t*) malloc(sizeof(connection_t));
    }
    for (i=0; i < network->nb_actors; i++) {
        network->actors[i] = (actor_t*) malloc(sizeof(actor_t));
    }

    return network;
}

void reset_profiling(network_t *network) {
    int i;
    for (i = 0; i < network->nb_actors; i++) {
        network->actors[i]->ticks = 0;
    }
    for (i = 0; i < network->nb_connections; i++) {
        network->connections[i]->rate = 0;
    }
}

void compute_workloads(network_t *network) {
    int i;
    double sum_actor_ticks = 0;
    long sum_conn_rate = 0;
    for (i = 0; i < network->nb_actors; i++) {
        sum_actor_ticks += network->actors[i]->ticks;
    }
    for (i = 0; i < network->nb_connections; i++) {
        sum_conn_rate += network->connections[i]->rate;
    }
    for (i = 0; i < network->nb_actors; i++) {
        network->actors[i]->workload = (int) (network->actors[i]->ticks / sum_actor_ticks * 10000) + 1;
    }
    for (i = 0; i < network->nb_connections; i++) {
        network->connections[i]->workload = (int) (network->connections[i]->rate / sum_conn_rate * 10000) + 1;
    }
}

void print_network(network_t *network) {
    int i;
    for (i = 0; i < network->nb_actors; i++) {
        print_orcc_trace(ORCC_VL_VERBOSE_1, "Actor[%d] = %s (workload = %d)",
                         i, network->actors[i]->name, network->actors[i]->workload);
    }
    for (i = 0; i < network->nb_connections; i++) {
        print_orcc_trace(ORCC_VL_VERBOSE_1, "Connection[%d] = %s --> %s (workload = %d)",
                             i, network->connections[i]->src->name, network->connections[i]->dst->name, network->connections[i]->workload);
    }
}

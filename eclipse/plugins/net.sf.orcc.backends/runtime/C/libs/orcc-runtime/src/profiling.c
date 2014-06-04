/*
 * Copyright (c) 2014, INSA of Rennes
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

#include "profiling.h"

#include "dataflow.h"

void reset_profiling(network_t *network) {
    int i,j;
    for (i = 0; i < network->nb_actors; i++) {
        network->actors[i]->commCost = 0;
        network->actors[i]->evaluated = 0;
        network->actors[i]->triedProcId = 1;
        network->actors[i]->processor_id = -1;
        network->actors[i]->ticks = 0;
        network->actors[i]->scheduler_workload = 0;
        network->actors[i]->firings = 0;
        network->actors[i]->switches = 0;
        network->actors[i]->misses = 0;
        for (j = 0; j < network->actors[i]->nb_actions; j++) {
            network->actors[i]->actions[j]->ticks = 0;
            network->actors[i]->actions[j]->min_ticks = -1;
            network->actors[i]->actions[j]->avg_ticks = -1;
            network->actors[i]->actions[j]->max_ticks = -1;
            network->actors[i]->actions[j]->variance_ticks = 0;
            network->actors[i]->actions[j]->workload = 0;
            network->actors[i]->actions[j]->firings = 0;
        }
    }
    for (i = 0; i < network->nb_connections; i++) {
        network->connections[i]->rate = 0;
    }
}

void compute_workloads(network_t *network) {
    int i, j;
    double sum_actor_ticks = 0;
    double sum_action_ticks = 0;
    long sum_conn_rate = 0;
    for (i = 0; i < network->nb_actors; i++) {
        sum_actor_ticks += network->actors[i]->ticks;
    }
    for (i = 0; i < network->nb_connections; i++) {
        sum_conn_rate += network->connections[i]->rate;
    }
    for (i = 0; i < network->nb_actors; i++) {
        sum_action_ticks = 0;
         for (j = 0; j < network->actors[i]->nb_actions; j++) {
            network->actors[i]->actions[j]->workload +=  (network->actors[i]->actions[j]->ticks / sum_actor_ticks * 10000);
            if (network->actors[i]->actions[j]->firings > 0) {
                network->actors[i]->actions[j]->avg_ticks = network->actors[i]->actions[j]->ticks / network->actors[i]->actions[j]->firings;
                network->actors[i]->firings += network->actors[i]->actions[j]->firings;
                network->actors[i]->actions[j]->variance_ticks = (network->actors[i]->actions[j]->variance_ticks/network->actors[i]->actions[j]->firings)-(network->actors[i]->actions[j]->avg_ticks*network->actors[i]->actions[j]->avg_ticks);
            }
            sum_action_ticks += network->actors[i]->actions[j]->ticks;
        }
        network->actors[i]->workload = (int) (network->actors[i]->ticks / sum_actor_ticks * 10000) + 1;
        network->actors[i]->scheduler_workload = ((network->actors[i]->ticks - sum_action_ticks) / sum_actor_ticks * 10000);
    }
    for (i = 0; i < network->nb_connections; i++) {
        network->connections[i]->workload = (int) (((float) network->connections[i]->rate) / sum_conn_rate * 10000) + 1;
    }
}

void update_ticks_stats(action_t *action, double diff_tick) {
    if (action->min_ticks > diff_tick || action->min_ticks < 0) {
        action->min_ticks = diff_tick;
    }
    if (action->max_ticks < diff_tick || action->max_ticks < 0) {
        action->max_ticks = diff_tick;
    }
    action->ticks += diff_tick;
    action->variance_ticks += diff_tick*diff_tick;
}

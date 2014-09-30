/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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

#include "scheduler.h"
#include "dataflow.h"
#include "mapping.h"
#include "cycle.h"
#include "options.h"
#include "util.h"

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

global_scheduler_t *allocate_global_scheduler(int nb_schedulers) {
    int i;
    global_scheduler_t *sched = (global_scheduler_t*) malloc(sizeof(global_scheduler_t));
    sched->nb_schedulers = nb_schedulers;
    sched->schedulers = (local_scheduler_t**) malloc(nb_schedulers * sizeof(local_scheduler_t*));
    for (i = 0; i < nb_schedulers; i++) {
        sched->schedulers[i] = allocate_local_scheduler(i, nb_schedulers);
    }
    return sched;
}

local_scheduler_t *allocate_local_scheduler(int id, int nb_schedulers) {
    int i;
    local_scheduler_t *sched = (local_scheduler_t*) malloc(sizeof(local_scheduler_t));

    sched->id = id;
    sched->nb_schedulers = nb_schedulers;
    sched->waiting_schedulable = (waiting_t **) malloc(nb_schedulers * sizeof(waiting_t *));
    for (i = 0; i < nb_schedulers; i++) {
        sched->waiting_schedulable[i] = (waiting_t *) malloc(sizeof(waiting_t));
    }

#ifdef THREADS_ENABLE
    orcc_semaphore_create(sched->sem_thread, 0);
#endif

    return sched;
}

void global_scheduler_init(global_scheduler_t *sched, mapping_t *mapping, agent_t *agent, options_t *opt) {
    int i;
    for (i = 0; i < sched->nb_schedulers; i++) {
        if(i < mapping->number_of_threads) {
            local_scheduler_init(sched->schedulers[i], mapping->partitions_size[i], mapping->partitions_of_actors[i], agent, opt);
        } else {
            local_scheduler_init(sched->schedulers[i], 0, NULL, agent, opt);
        }
    }

    sched->agent = agent;
}

/**
 * Initializes the given scheduler.
 */
void local_scheduler_init(local_scheduler_t *sched, int num_actors, actor_t **actors, agent_t *agent, options_t *opt) {
    int i;

    sched->num_actors = num_actors;
    sched->actors = actors;
    for (i = 0; i < num_actors; i++) {
        actors[i]->sched = sched;
        actors[i]->in_list = 0;
        actors[i]->in_waiting = 0;
    }

    sched->strategy = opt->sched_strategy;
    sched->round_robin = 1;
    sched->rr_next_schedulable = 0;
    sched->ddd_next_entry = 0;
    sched->ddd_next_schedulable = 0;

    for (i = 0; i < sched->nb_schedulers; i++) {
        sched->waiting_schedulable[i]->next_entry = 0;
        sched->waiting_schedulable[i]->next_waiting = 0;
    }

    sched->agent = agent;
#ifdef THREADS_ENABLE
    orcc_semaphore_create(sched->sem_thread, 0);
#endif
}

/**
 * Reinitialize the given scheduler with new actors list.
 */
void sched_reinit(local_scheduler_t *sched, int num_actors, actor_t **actors) {
    int i;

    sched->actors = actors;
    sched->num_actors = num_actors;
    sched->rr_next_schedulable = 0;
    sched->ddd_next_entry = 0;
    sched->ddd_next_schedulable = 0;
    sched->round_robin = 1;

    for (i = 0; i < sched->nb_schedulers; i++) {
        sched->waiting_schedulable[i]->next_entry = 0;
        sched->waiting_schedulable[i]->next_waiting = 0;
    }

    for (i = 0; i < num_actors; i++) {
        actors[i]->sched = sched;
        actors[i]->in_list = 0;
        actors[i]->in_waiting = 0;
    }

}

/**
 * Initialize the actors mapped to the given scheduler.
 */
void sched_init_actors(local_scheduler_t *sched, schedinfo_t *si) {
    int i;

    for (i = 0; i < sched->num_actors; i++) {
        sched->actors[i]->init_func(si);
    }
    
}

///////////////////////////////////////////////////////////////////////////////
// Scheduling list
///////////////////////////////////////////////////////////////////////////////


actor_t *sched_get_next_schedulable(local_scheduler_t *sched) {
    if(sched->strategy == ORCC_SS_ROUND_ROBIN) {
        return sched_get_next_rr(sched);
    } else {
        return sched_get_next_ddd(sched);
    }
}

/**
 * Returns the next actor in actors list.
 * This method is used by the round-robin scheduler.
 */
actor_t *sched_get_next_rr(local_scheduler_t *sched) {
    actor_t *actor;
    if (sched->num_actors == 0) {
        return NULL;
    }
    actor = sched->actors[sched->rr_next_schedulable];
    sched->rr_next_schedulable++;
    if (sched->rr_next_schedulable == sched->num_actors) {
        sched->rr_next_schedulable = 0;
    }
    return actor;
}

/**
 * Returns the next schedulable actor, or NULL if no actor is schedulable.
 * The actor is removed from the schedulable list.
 * This method is used by the data/demand driven scheduler.
 */
actor_t *sched_get_next_ddd(local_scheduler_t *sched) {
    actor_t *actor;
    // check if other schedulers sent some schedulable actors
    sched_add_waiting_list(sched);
    if (sched->ddd_next_schedulable == sched->ddd_next_entry) {
        // static actors list is used when schedulable list is empty
        actor = sched_get_next_rr(sched);
        sched->round_robin = 1;
    } else {
        actor = sched->schedulable[sched->ddd_next_schedulable % MAX_ACTORS];
        // actor is not a member of the list anymore
        actor->in_list = 0;
        sched->ddd_next_schedulable++;
        sched->round_robin = 0;
    }

    return actor;
}

/**
 * Add the actor to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 */
void sched_add_schedulable(local_scheduler_t *sched, actor_t *actor) {
    // only add the actor in the lists if it is not already there
    // like a list.contains(actor) but in O(1) instead of O(n)
    if (!actor->in_list) {
        if (sched == actor->sched) {
            sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
            actor->in_list = 1;
            sched->ddd_next_entry++;
        } else if (!actor->in_waiting) {
            // this actor isn't launch by this scheduler so it is sent to the next one
            waiting_t *send = actor->sched->waiting_schedulable[sched->id];
            send->waiting_actors[send->next_entry % MAX_ACTORS] = actor;
            actor->in_waiting = 1;
            send->next_entry++;
        }
    }
}

/**
 * Add waited actors to the schedulable list.
 * This function use mesh topology of communications.
 */
void sched_add_waiting_list(local_scheduler_t *sched) {
    int i;
    actor_t *actor;
    for (i = 0; i < sched->nb_schedulers; i++) {
        waiting_t *wait = sched->waiting_schedulable[i];
        while (wait->next_entry - wait->next_waiting >= 1) {
            actor = wait->waiting_actors[wait->next_waiting % MAX_ACTORS];
            sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
            actor->in_list = 1;
            actor->in_waiting = 0;
            sched->ddd_next_entry++;
            wait->next_waiting++;
        }
    }
}

void *scheduler_routine(void *data) {
    local_scheduler_t *sched = (local_scheduler_t *) data;
    actor_t *my_actor;
    schedinfo_t si;
    int j;
    ticks tick_in, tick_out;
    double diff_tick;

#ifdef THREADS_ENABLE
    set_realtime_priority();
#endif
    sched_init_actors(sched, &si);

    while (1) {
        my_actor = sched_get_next_schedulable(sched);
        if(my_actor != NULL){
            tick_in = getticks();
            si.num_firings = 0;

            my_actor->sched_func(&si);

            if (si.num_firings != 0) {
                tick_out = getticks();
                diff_tick = elapsed(tick_out, tick_in);
                my_actor->ticks += diff_tick;
            } else {
                my_actor->misses++;
            }
            my_actor->switches++;

            if(opt->print_firings) {
                printf("%2i  %5i\t%s\t%s\n", sched->id, si.num_firings, si.reason == starved ? "starved" : "full", my_actor->name);
            }
        }

#ifdef THREADS_ENABLE
        if(my_actor == NULL || needMapping()) {
            orcc_semaphore_set(sched->agent->sem_agent);
            orcc_semaphore_wait(sched->sem_thread);
        }
#endif
    }
}

void launcher(options_t *opt, network_t *network) {
    int i;
    mapping_t *mapping = map_actors(network, opt);
    int nb_threads = opt->nb_processors;

#ifdef THREADS_ENABLE
    cpu_set_t cpuset;
    orcc_thread_t threads[MAX_THREAD_NB];
    orcc_thread_id_t threads_id[MAX_THREAD_NB];
    orcc_thread_t thread_agent;
    orcc_thread_id_t thread_agent_id;
#endif

    global_scheduler_t *scheduler = allocate_global_scheduler(nb_threads);
    agent_t *agent = agent_init(opt, scheduler, network, nb_threads);
    global_scheduler_init(scheduler, mapping, agent, opt);

#ifdef THREADS_ENABLE
    orcc_clear_cpu_set(cpuset);

    for(i=0 ; i < nb_threads; i++){
        orcc_thread_create(threads[i], scheduler_routine, *scheduler->schedulers[i], threads_id[i]);
        orcc_set_thread_affinity(cpuset, i, threads[i]);
    }
    orcc_thread_create(thread_agent, agent_routine, *agent, thread_agent_id);

    for(i=0 ; i < nb_threads; i++){
        orcc_thread_join(threads[i]);
    }
    orcc_thread_join(thread_agent);
#else
    (*scheduler_routine)((void *) scheduler->schedulers[i]);
#endif
}

#include <stdio.h>

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_scheduler.h"

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

/**
 * Initializes the given scheduler.
 */
void sched_init(struct scheduler_s *sched, int num_actors, struct actor_s **actors, struct sync_s *sync, hwloc_topology_t topology, hwloc_cpuset_t cpuset) {
	int i;
	sched->actors = actors;
	sched->sync = sync;
	sched->num_actors = num_actors;
	sched->next_entry = 0;
	sched->next_schedulable = 0;
	sched->next_else_schedulable = 0;
	if(actors != NULL){
		for(i=0; i<num_actors; i++){
			actors[i]->sched = sched;
		}
	}
	semaphoreCreate(sched->sem_thread, 0);
	sched->topology = topology;
	sched->cpuset = cpuset;
}

/**
 * Reinitialize the given scheduler with new actors list.
 */
void sched_reinit(struct scheduler_s *sched, int num_actors, struct actor_s **actors){
	int i;
	sched->actors = actors;
	sched->num_actors = num_actors;
	sched->next_entry = 0;
	sched->next_schedulable = 0;
	sched->next_else_schedulable = 0;
	for(i=0; i<num_actors; i++){
		actors[i]->sched = sched;
		if(!strcmp(actors[i]->name,"source")){
			sched_add_schedulable(sched, actors[i]);
		}
	}
}

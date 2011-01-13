#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_scheduler.h"

#include <stdio.h>

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

/**
 * Initializes the given scheduler.
 */
void sched_init(struct scheduler_s *sched, int num_actors, struct actor_s **actors, struct sync_s *sync) {
	sched->actors = actors;
	sched->sync = sync;
	sched->num_actors = num_actors;
	sched->next_entry = 0;
	sched->next_schedulable = 0;
}

/**
 * Reinitialize the given scheduler with new actors list.
 */
void sched_reinit(struct scheduler_s *sched, int num_actors, struct actor_s **actors){
	sched->actors = actors;
	sched->num_actors = num_actors;
	sched->next_entry = 0;
	sched->next_schedulable = 0;
}

char* get_actor_name(struct actor_s *actor){
	return actor->name;
}

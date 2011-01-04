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

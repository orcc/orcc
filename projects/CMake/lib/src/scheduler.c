#define BRAINDEAD_FIFO 1

#include "fifo.h"
#include "scheduler.h"

#include <stdio.h>

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

/**
 * Initializes the given scheduler.
 */
void sched_init(struct scheduler_s *sched, int num_actors, struct actor_s **actors) {
	struct list_s *list = &(sched->schedulable);
	sched->actors = actors;
	sched->num_actors = num_actors;

	list_init(&(sched->schedulable));
	list_init(&(sched->scheduled));
}

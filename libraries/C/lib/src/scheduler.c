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
void sched_init(struct scheduler_s *sched, int id, int num_actors,
		struct actor_s **actors, struct waiting_s *waiting_schedulable,
		struct waiting_s *sending_schedulable, struct sync_s *sync) {
	int i;

	sched->id = id;
	sched->i = 0;
	sched->num_actors = num_actors;
	sched->actors = actors;
	if (actors != NULL) {
		for (i = 0; i < num_actors; i++) {
			actors[i]->sched = sched;
			actors[i]->in_list = 0;
			actors[i]->in_waiting = 0;
		}
	}
	sched->next_entry = 0;
	sched->next_schedulable = 0;
	sched->next_else_schedulable = 0;
	sched->round_robin = 1;

	sched->waiting_schedulable = waiting_schedulable;
	sched->waiting_schedulable->next_entry = 0;
	sched->waiting_schedulable->next_waiting = 0;
	sched->sending_schedulable = sending_schedulable;
	sched->sending_schedulable->next_entry = 0;
	sched->sending_schedulable->next_waiting = 0;

	sched->sync = sync;
	semaphore_create(sched->sem_thread, 0);
}

/**
 * Reinitialize the given scheduler with new actors list.
 */
void sched_reinit(struct scheduler_s *sched, int num_actors,
		struct actor_s **actors) {
	int i;

	sched->actors = actors;
	sched->num_actors = num_actors;
	sched->next_entry = 0;
	sched->next_schedulable = 0;
	sched->next_else_schedulable = 0;
	sched->round_robin = 1;
	sched->waiting_schedulable->next_entry = 0;
	sched->waiting_schedulable->next_waiting = 0;
	sched->sending_schedulable->next_entry = 0;
	sched->sending_schedulable->next_waiting = 0;

	for (i = 0; i < num_actors; i++) {
		actors[i]->sched = sched;
		actors[i]->in_list = 0;
		actors[i]->in_waiting = 0;
		if (!strcmp(actors[i]->name, "source")) {
			sched_add_schedulable(sched, actors[i]);
		}
	}

}

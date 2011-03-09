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
void sched_init(struct scheduler_s *sched, int id, int schedulers_nb,
		int num_actors, struct actor_s **actors, struct sync_s *sync) {
	int i;

	sched->id = id;
	sched->schedulers_nb = schedulers_nb;

	sched->num_actors = num_actors;
	sched->actors = actors;
	if (actors != NULL) {
		for (i = 0; i < num_actors; i++) {
			actors[i]->sched = sched;
		}
	}
	sched->next_entry = 0;
	sched->next_schedulable = 0;
	sched->next_else_schedulable = 0;

	sched->waiting_schedulable = (struct waiting_s **) calloc(
			schedulers_nb, sizeof(struct waiting_s *));
	for (i = 0; i < schedulers_nb; i++) {
		sched->waiting_schedulable[i] = (struct waiting_s *) calloc(1, sizeof(struct waiting_s));
		//sched->waiting_schedulable[i]->next_entry = 0;
		//sched->waiting_schedulable[i]->next_waiting = 0;
	}

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
	for (i = 0; i < num_actors; i++) {
		actors[i]->sched = sched;
		if (!strcmp(actors[i]->name, "source")) {
			sched_add_schedulable(sched, actors[i]);
		}
	}
	sched->num_actors = num_actors;
	sched->next_entry = 0;
	sched->next_schedulable = 0;
	sched->next_else_schedulable = 0;

	for (i = 0; i < sched->schedulers_nb; i++) {
		sched->waiting_schedulable[i]->next_entry = 0;
		sched->waiting_schedulable[i]->next_waiting = 0;
	}
}

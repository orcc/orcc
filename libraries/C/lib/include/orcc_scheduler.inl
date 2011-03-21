/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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

///////////////////////////////////////////////////////////////////////////////
// Scheduling list
///////////////////////////////////////////////////////////////////////////////

/**
 * Returns the next actor in actors list.
 * This method is used by the round-robin scheduler.
 */
static struct actor_s *sched_get_next(struct scheduler_s *sched) {
	struct actor_s *actor;
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
 * Add the actor to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 */
static void sched_add_schedulable(struct scheduler_s *sched,
		struct actor_s *actor) {
	// only add the actor in the lists if it is not already there
	// like a list.contains(actor) but in O(1) instead of O(n)
	if (!actor->in_list) {
		if (sched == actor->sched) {
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			sched->ddd_next_entry++;
		} else if (!actor->in_waiting) {
			// this actor isn't launch by this scheduler so it is sent to the next one
			struct waiting_s *send = sched->ring_sending_schedulable;
			send->waiting_actors[send->next_entry % MAX_ACTORS] = actor;
			actor->in_waiting = 1;
			send->next_entry++;
		}
	}
}

/**
 * Add waited actors to the schedulable or waiting list.
 * The list is chosen according to associate scheduler of the actor.
 */
static void sched_add_waiting_list(struct scheduler_s *sched) {
	struct actor_s *actor;
	struct waiting_s *wait = sched->ring_waiting_schedulable;
	while (wait->next_entry - wait->next_waiting >= 1) {
		actor = wait->waiting_actors[wait->next_waiting % MAX_ACTORS];
		if (sched == actor->sched) {
			sched->schedulable[sched->ddd_next_entry % MAX_ACTORS] = actor;
			actor->in_list = 1;
			actor->in_waiting = 0;
			sched->ddd_next_entry++;
		} else {
			// this actor isn't launch by this scheduler so it is sent to the next one
			struct waiting_s *send = sched->ring_sending_schedulable;
			send->waiting_actors[send->next_entry % MAX_ACTORS] = actor;
			send->next_entry++;
		}
		wait->next_waiting++;
	}
}

/**
 * Returns the next schedulable actor, or NULL if no actor is schedulable.
 * The actor is removed from the schedulable list.
 * This method is used by the data/demand driven scheduler.
 */
static struct actor_s *sched_get_next_schedulable(struct scheduler_s *sched) {
	struct actor_s *actor;
	// check if other schedulers sent some schedulable actors
	sched_add_waiting_list(sched);
	if (sched->ddd_next_schedulable == sched->ddd_next_entry) {
		// static actors list is used when schedulable list is empty
		actor = sched_get_next(sched);
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

static void sched_add_predecessors(struct scheduler_s *sched,
		struct actor_s *actor, int ports) {
	int i, n;
	n = actor->num_inputs;
	for (i = 0; i < n; i++) {
		if ((ports & (1 << i)) != 0 && actor->predecessors[i] != NULL) {
			struct actor_s *pred = actor->predecessors[i];
			sched_add_schedulable(sched, pred);
		}
	}
}

static void sched_add_successors(struct scheduler_s *sched,
		struct actor_s *actor, int ports) {
	int i, j, n;
	n = actor->num_outputs;
	for (i = 0; i < n; i++) {
		if ((ports & (1 << i)) != 0 && actor->successors[i] != NULL) {
			for (j = 0; j < actor->num_successors[i]; j++) {
				struct actor_s *succ = actor->successors[i][j];
				sched_add_schedulable(sched, succ);
			}
		}
	}
}

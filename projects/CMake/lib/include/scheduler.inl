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

#define MAX_ACTORS 1000

static struct actor_s *schedulable[MAX_ACTORS];
static int next_entry;
static int next_schedulable;

/**
* Returns the next schedulable actor, or NULL if no actor is schedulable.
* The actor is removed from the schedulable list.
*/
static struct actor_s *sched_get_next_schedulable(struct scheduler_s *sched) {
	struct actor_s *actor = schedulable[next_schedulable];
	next_schedulable++;
	if (next_schedulable >= MAX_ACTORS) {
		next_schedulable = 0;
	}

	// actor is not a member of the list anymore
	actor->in_list = 0;
	return actor;
}

/**
* add the actor to the schedulable list
*/
static void sched_add_schedulable(struct scheduler_s *sched, struct actor_s *actor) {	
	// only add the actor in the schedulable list if it is not already there
	// like a list.contains(actor) but in O(1) instead of O(n)
	if (!actor->in_list) {
		schedulable[next_entry] = actor;
		next_entry++;
		if (next_entry >= MAX_ACTORS) {
			next_entry = 0;
		}

		actor->in_list = 1;
	}
}

static void sched_add_predecessors(struct scheduler_s *sched, struct actor_s *actor, int ports) {
	int i, n;
	n = actor->num_inputs;
	if (ports == 0) {
		// add all predecessors. workaround for parseheaders
		for (i = 0; i < n; i++) {
			struct actor_s *pred = actor->predecessors[i];
			sched_add_schedulable(sched, pred);
		}
	} else {
		for (i = 0; i < n; i++) {
			if ((ports & (1 << i)) != 0) {
				struct actor_s *pred = actor->predecessors[i];
				sched_add_schedulable(sched, pred);
			}
		}
	}
}

static void sched_add_successors(struct scheduler_s *sched, struct actor_s *actor, int ports) {
	int i, n;
	n = actor->num_outputs;
	for (i = 0; i < n; i++) {
		if ((ports & (1 << i)) != 0) {
			struct actor_s *succ = actor->successors[i];
			sched_add_schedulable(sched, succ);
		}
	}
}

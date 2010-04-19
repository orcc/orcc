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
// List allocation
///////////////////////////////////////////////////////////////////////////////

// should be at least twice the maximum number of actors to be on the safe side
static struct list_s list_pool[1000];
static int pool_size = sizeof(list_pool) / sizeof(list_pool[0]);
static int next_free;

static int is_pool_entry_free(struct list_s *list) {
	return (list->prev == NULL) && (list->next == NULL);
}

/**
 * Creates a new list_head.
 */
static struct list_s* new_list() {
	struct list_s *new_list_head = &list_pool[next_free];
	struct list_s *some_list_head;

	next_free++;
	if (next_free == pool_size) {
		next_free = 0;
	}

	some_list_head = &list_pool[next_free];
	while (!is_pool_entry_free(some_list_head)) {
		next_free++;
		if (next_free == pool_size) {
			next_free = 0;
		}
		some_list_head = &list_pool[next_free];
	}

	return new_list_head;
}

/**
 * Deletes the given list_head.
 */
static void delete_list(struct list_s *list) {
	list->prev = NULL;
	list->next = NULL;
}

///////////////////////////////////////////////////////////////////////////////
// List functions
///////////////////////////////////////////////////////////////////////////////

static __inline void list_internal_remove(struct list_s *prev, struct list_s *next) {
	next->prev = prev;
	prev->next = next;
}

static __inline void list_remove(struct list_s *entry) {
	list_internal_remove(entry->prev, entry->next);
	delete_list(entry);
}

static __inline int list_is_empty(struct list_s *list) {
	return list == list->next;
}

static __inline void list_init(struct list_s *list) {
	list->prev = list;
	list->next = list;
	list->payload = NULL;
}

static __inline void list_internal_add(struct list_s *prev, struct list_s *next, struct list_s *new_entry) {
	prev->next = new_entry;
	new_entry->prev = prev;
	new_entry->next = next;
	next->prev = new_entry;
}

static __inline void list_add_head(struct list_s *list, struct list_s *new_entry) {
	list_internal_add(list, list->next, new_entry);
}

static __inline void list_add_tail(struct list_s *list, struct list_s *new_entry) {
	list_internal_add(list->prev, list, new_entry);
}

/**
 * Returns the next schedulable actor, or NULL if no actor is schedulable.
 * The actor is removed from the schedulable list.
 */
static struct actor_s *sched_get_next_schedulable(struct scheduler_s *sched) {
	struct list_s *list = &(sched->schedulable);
	struct list_s *first;
	struct actor_s *actor;

	if (list_is_empty(list)) {
		return NULL;
	}

	first = list->next;
	actor = (struct actor_s *)first->payload;
	list_remove(first);

	// actor is not a member of the list anymore
	actor->in_list = 0;

	return actor;
}

/**
 * add the actor to the schedulable list
 */
static void sched_add_schedulable(struct scheduler_s *sched, struct actor_s *actor) {
	struct list_s *list;
	
	// only add the actor in the schedulable list if it is not already there
	// like a list.contains(actor) but in O(1) instead of O(n)
	if (!actor->in_list) {
		list = new_list();
		list->payload = actor;
		list_add_tail(&(sched->schedulable), list);
		actor->in_list = 1;
	}
}

static void sched_add_predecessors(struct scheduler_s *sched, struct actor_s *actor) {
	int i, n;
	n = actor->num_predecessors;
	for (i = 0; i < n; i++) {
		struct actor_s *pred = actor->predecessors[i];
		sched_add_schedulable(sched, pred);
	}
}

static void sched_add_successors(struct scheduler_s *sched, struct actor_s *actor) {
	int i, n;
	n = actor->num_successors;
	for (i = 0; i < n; i++) {
		struct actor_s *succ = actor->successors[i];
		sched_add_schedulable(sched, succ);
	}
}

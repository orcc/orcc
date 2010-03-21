#define BRAINDEAD_FIFO 1

#include "fifo.h"
#include "scheduler.h"

///////////////////////////////////////////////////////////////////////////////
// List allocation
///////////////////////////////////////////////////////////////////////////////

// should be at least twice the maximum number of actors to be on the safe side
static struct list_head list_pool[1000];
static int pool_size = sizeof(list_pool) / sizeof(list_pool[0]);
static int next_free;

static int is_pool_entry_free(struct list_head *list) {
	return (list->prev == NULL) && (list->next == NULL);
}

/**
 * Creates a new list_head.
 */
static struct list_head* new_list() {
	struct list_head *new_list_head = &list_pool[next_free];
	struct list_head *some_list_head;

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
	}

	return new_list_head;
}

/**
 * Deletes the given list_head.
 */
static void delete_list(struct list_head *list) {
	list->prev = NULL;
	list->next = NULL;
}

///////////////////////////////////////////////////////////////////////////////
// List functions
///////////////////////////////////////////////////////////////////////////////

static __inline void list_internal_add(struct list_head *prev, struct list_head *next, struct list_head *new_entry) {
	prev->next = new_entry;
	new_entry->prev = prev;
	new_entry->next = next;
	next->prev = new_entry;
}

static __inline void list_add_head(struct list_head *list, struct list_head *new_entry) {
	list_internal_add(list, list->next, new_entry);
}

static __inline void list_add_tail(struct list_head *list, struct list_head *new_entry) {
	list_internal_add(list->prev, list, new_entry);
}

static __inline void list_internal_remove(struct list_head *prev, struct list_head *next) {
	next->prev = prev;
	prev->next = next;
}

static __inline void list_remove(struct list_head *entry) {
	list_internal_remove(entry->prev, entry->next);
}

static __inline int list_is_empty(struct list_head *list) {
	return list == list->next;
}

///////////////////////////////////////////////////////////////////////////////
// Scheduling functions
///////////////////////////////////////////////////////////////////////////////

/**
 * Initializes the given scheduler.
 */
void scheduler_init(struct scheduler *sched, int num_actors, struct actor **actors) {
	struct list_head *list = &(sched->sched1);
	sched->actors = actors;
	sched->num_actors = num_actors;

	list->next = list;
	list->prev = list;
	list->payload = NULL;
}

void add_schedulable(struct scheduler *sched, struct actor *actor) {
	struct list_head *list = new_list();
	list->payload = actor;
	list_add_tail(&(sched->sched1), list);
}

int is_schedulable(struct actor *actor) {
	int i;
	for (i = 0; i < actor->num_inputs; i++) {
		if (!hasTokens(actor->inputs[i]->fifo, 1)) {
			return 0;
		}
	}

	return 1;
}

/**
 * Returns the next schedulable actor, or NULL if no actor is schedulable.
 * The actor is removed from the schedulable list.
 */
struct actor *get_next_schedulable(struct scheduler *sched) {
	struct list_head *list = &(sched->sched1);
	struct list_head *first;
	struct actor *actor;

	if (list_is_empty(list)) {
		return NULL;
	}

	first = list->next;
	actor = (struct actor *)first->payload;
	list_remove(first);
	delete_list(first);

	return actor;
}

/**
 * If the FIFO is empty, moves pointers back. If there is data in it, put it at the beginning.
 */
static void update_fifo(struct fifo_s *fifo) {
	int num_tokens = fifo->write_ptr - fifo->read_ptr;
	if (num_tokens == 0) {
		fifo->read_ptr = 0;
		fifo->write_ptr = 0;
	} else {
		if (fifo->read_ptr >= num_tokens) {
			// there is room to copy the not-read-yet tokens at the beginning of the FIFO
			memcpy(fifo->contents, contents(fifo, fifo->read_ptr), num_tokens * fifo->elt_size);
			fifo->read_ptr = 0;
			fifo->write_ptr = num_tokens;
		}
	}
}

/**
 * updates FIFOs: move read/write pointers.
 */
void update_fifos(struct scheduler *sched) {
	int i;
	int n = sched->num_actors;

	for (i = 0; i < n; i++) {
		struct actor *actor = sched->actors[i];
		int j;
		int num_outputs = actor->num_outputs;
		
		for (j = 0; j < num_outputs; j++) {
			struct conn_s *conn = actor->outputs[j];
			update_fifo(conn->fifo);
		}
	}
}

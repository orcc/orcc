#include "fifo.h"
#include "scheduler.h"

int is_schedulable(struct actor *actor) {
	int i;
	for (i = 0; i < actor->num_inputs; i++) {
		if (!hasTokens(actor->inputs[i]->fifo, 1)) {
			return 0;
		}
	}

	return 1;
}

struct actor *get_next_schedulable(struct scheduler *my_scheduler) {
	return NULL;
}

void add_schedulable(struct scheduler *my_scheduler, struct actor *actor) {
	
}

void add_schedulable_last(struct scheduler *my_scheduler, struct actor *actor) {
	
}
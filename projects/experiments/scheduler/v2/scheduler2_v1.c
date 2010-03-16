// (a bit more) intelligent scheduler

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "defs.h"

static struct fifo_s {
	int size;
	int *contents;
	int read_ptr;
	int write_ptr;
	int is_full;
};

#define SIZE 1000

#define contents(fifo, ptr) (& (fifo)->contents[(ptr)])

static void *getPeekPtr(struct fifo_s *fifo, int n) {
	return contents(fifo, fifo->read_ptr);
}

static void *getReadPtr(struct fifo_s *fifo, int n) {
	int ptr = fifo->read_ptr;
	fifo->read_ptr += n;
	return contents(fifo, ptr);
}

static struct fifo_s *full_fifos[2];
static int last_idx = 0;

static int hasRoom(struct fifo_s *fifo, int n) {
	int num_free = fifo->size - fifo->write_ptr;
	int res = (num_free >= n);
	if (!res) {
		if (fifo->is_full == 0) {
			fifo->is_full = 1;
			full_fifos[last_idx] = fifo;
			last_idx++;
		} else {
		}
	}

	return res;
}

static int hasTokens(struct fifo_s *fifo, int n) {
	int num_tokens = fifo->write_ptr - fifo->read_ptr;
	return (num_tokens >= n);
}

static void *getWritePtr(struct fifo_s *fifo, int n) {
	int ptr = fifo->write_ptr;
	fifo->write_ptr += n;
	return contents(fifo, ptr);
}

static int array_0[SIZE * sizeof(int)];
static struct fifo_s fifo_0 = { SIZE, array_0, 0, 0 };

static int array_1[SIZE * sizeof(int)];
static struct fifo_s fifo_1 = { SIZE, array_1, 0, 0 };

static struct fifo_s *source_O = &fifo_0;
static struct fifo_s *compute_I = &fifo_0;
static struct fifo_s *compute_O = &fifo_1;
static struct fifo_s *sink_I = &fifo_1;

static void action_source() {
	int *ptr = getWritePtr(source_O, n_token);
	ptr[0] = source_X;
	source_X++;
}

static int source_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (source_X < N) {
			if (hasRoom(source_O, n_token)) {
				action_source();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_compute() {
	int i;
	int *rptr = getReadPtr(compute_I, n_token);
	int *wptr = getWritePtr(compute_O, n_token);

	for (i=0; i <n_token; i++){
		wptr[i] = rptr[i] + i;
	}
}

static int compute_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (hasTokens(compute_I, n_token)) {
			if (hasRoom(compute_O, n_token)) {
				action_compute();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_sink() {
	getReadPtr(sink_I, n_token);
}

static int sink_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (hasTokens(sink_I, n_token)) {
			action_sink();			
			res = 1;
			i++;
		}
	}

	return i;
}

static void move_tokens(struct fifo_s *fifo) {
	// number of tokens in the FIFO
	int num_tokens = fifo->write_ptr - fifo->read_ptr;

	// if the read pointer is greater than the number of tokens, we can safely move
	// the tokens at the beginning of the FIFO
	if (fifo->read_ptr >= num_tokens) {
		// there is room to copy the not-read-yet tokens at the beginning of the FIFO
		if (num_tokens > 0) {
			// only copy if there are tokens
			memcpy(fifo->contents, contents(fifo, fifo->read_ptr), num_tokens);
		}
		fifo->read_ptr = 0;
		fifo->write_ptr = num_tokens;
	}

	// we assume the FIFO is now empty.
	fifo->is_full = 0;
}

struct conn_s {
	struct fifo_s *fifo;
	struct actor *source;
	struct actor *target;
};

struct actor {
	int (*sched_func)();
	int num_inputs;
	struct conn_s **inputs;
	int num_outputs;
	struct conn_s **outputs;
};

struct scheduler {
	int sched_idx;
	int num_schedulables;
	struct actor **actors;
};

struct actor source = {source_scheduler};
struct actor compute = {compute_scheduler};
struct actor sink = {sink_scheduler};

struct actor *actors[] = {
	&source,
	&compute,
	&sink
};

struct conn_s conn_0 = {&fifo_0, &source, &compute};
struct conn_s conn_1 = {&fifo_1, &compute, &sink};

struct conn_s **source_inputs = NULL;
struct conn_s *source_outputs[] = {&conn_0};

struct conn_s *compute_inputs[] = {&conn_0};
struct conn_s *compute_outputs[] = {&conn_1};

struct conn_s *sink_inputs[] = {&conn_1};
struct conn_s **sink_outputs = NULL;

void initialize2_v1() {
	source.num_inputs = 0;
	source.inputs = source_inputs;
	source.num_outputs = 1;
	source.outputs = source_outputs;

	compute.num_inputs = 1;
	compute.inputs = compute_inputs;
	compute.num_outputs = 1;
	compute.outputs = compute_outputs;

	sink.num_inputs = 1;
	sink.inputs = sink_inputs;
	sink.num_outputs = 0;
	sink.outputs = sink_outputs;
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

void scheduler2_v1() {
	int i;

	struct scheduler my_scheduler = {
		0,
		0,
		actors
	};

	my_scheduler.sched_idx = 0;
	my_scheduler.num_schedulables = 1;

	while (my_scheduler.num_schedulables > 0) {
		struct actor *my_actor = my_scheduler.actors[my_scheduler.sched_idx];
		my_actor->sched_func();

		for (i = 0; i < my_actor->num_outputs; i++) {
			struct conn_s *connection = my_actor->outputs[i];
			if (is_schedulable(connection->target)) {
				my_scheduler.num_schedulables = 1;
				my_scheduler.actors[my_scheduler.sched_idx] = connection->target;
			}
		}
	}
}

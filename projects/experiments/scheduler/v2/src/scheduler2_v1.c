// (a bit more) intelligent scheduler

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "defs.h"
#include "fifo.h"
#include "scheduler.h"

#define SIZE 10000

static char array_0[SIZE * sizeof(int)];
static struct fifo_s fifo_0 = { sizeof(int), SIZE, array_0, 0, 0 };

static char array_1[SIZE * sizeof(int)];
static struct fifo_s fifo_1 = { sizeof(int), SIZE, array_1, 0, 0 };

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

/////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////

struct actor source = {source_scheduler};
struct actor compute = {compute_scheduler};
struct actor sink = {sink_scheduler};

struct actor *source_successors[] = {&compute};
struct actor *compute_successors[] = {&sink};

struct conn_s conn_0 = {&fifo_0, &source, &compute};
struct conn_s conn_1 = {&fifo_1, &compute, &sink};

struct conn_s *source_outputs[] = {&conn_0};

struct conn_s *compute_inputs[] = {&conn_0};
struct conn_s *compute_outputs[] = {&conn_1};

struct conn_s *sink_inputs[] = {&conn_1};

void initialize2_v1() {
	source.num_inputs = 0;
	source.num_outputs = 1;
	source.outputs = source_outputs;
	source.num_successors = 1;
	source.successors = source_successors;

	compute.num_inputs = 1;
	compute.inputs = compute_inputs;
	compute.num_outputs = 1;
	compute.outputs = compute_outputs;
	compute.num_successors = 1;
	compute.successors = compute_successors;

	sink.num_inputs = 1;
	sink.inputs = sink_inputs;
	sink.num_outputs = 0;
	sink.num_successors = 0;
}

struct actor *actors[] = {&source, &compute, &sink};

void scheduler2_v1() {
	int i;
	struct actor *my_actor;
	struct scheduler my_scheduler;

	// initialize scheduler
	scheduler_init(&my_scheduler, sizeof(actors) / sizeof(actors[0]), actors);

	add_schedulable(&my_scheduler, &source);
	my_actor = get_next_schedulable(&my_scheduler);
	while (my_actor != NULL) {
		while (my_actor != NULL) {
			int num_firings = my_actor->sched_func();
			if (num_firings > 0) {
				// the actor has fired, so it is likely it has produced data
				// we consider its successors as schedulable

				for (i = 0; i < my_actor->num_successors; i++) {
					struct actor *succ = my_actor->successors[i];
					if (is_schedulable(succ)) {
						add_schedulable(&my_scheduler, succ);
					}
				}
			}

			my_actor = get_next_schedulable(&my_scheduler);
		}

		update_fifos(&my_scheduler);
		if (source_X == N) {
			return;
		}
		add_schedulable(&my_scheduler, &source);
		my_actor = get_next_schedulable(&my_scheduler);
	}
}

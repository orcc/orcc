// v3: intelligent FIFO management AND no more elt_size

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
	int *ptr = getWritePtr(source_O, 1);
	ptr[0] = source_X;
	source_X++;
}

static int source_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (source_X < N) {
			if (hasRoom(source_O, 1)) {
				action_source();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_compute() {
	int *rptr = getReadPtr(compute_I, 1);
	int *wptr = getWritePtr(compute_O, 1);
	wptr[0] = rptr[0] + 1;
}

static int compute_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (hasTokens(compute_I, 1)) {
			if (hasRoom(compute_O, 1)) {
				action_compute();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_sink() {
	getReadPtr(sink_I, 1);
}

static int sink_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (hasTokens(sink_I, 1)) {
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

void scheduler_v3() {
	int go_on = 1;
	while (go_on) {
		go_on = 0;
		go_on += source_scheduler();
		go_on += compute_scheduler();
		go_on += sink_scheduler();

		if (!go_on && last_idx > 0) {
			// at least one FIFO full!
			int i;
			for (i = 0; i < last_idx; i++) {
				struct fifo_s *fifo = full_fifos[i];
				move_tokens(fifo);
				full_fifos[i] = NULL;
			}

			last_idx = 0;
			go_on = 1;
		}
	}
}

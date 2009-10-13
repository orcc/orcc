// basic scheduler: weird FIFO management

#include <string.h>

#include "defs.h"

static struct fifo_s {
	int elt_size;
	int size;
	char *contents;
	int read_ptr;
	int write_ptr;
};

#define SIZE 1000

#define contents(fifo, ptr) (& (fifo)->contents[(ptr) * (fifo)->elt_size])

static void *getPeekPtr(struct fifo_s *fifo, int n) {
	return contents(fifo, fifo->read_ptr);
}

static void *getReadPtr(struct fifo_s *fifo, int n) {
	int ptr = fifo->read_ptr;
	fifo->read_ptr += n;
	return contents(fifo, ptr);
}

static int hasRoom(struct fifo_s *fifo, int n) {
	int num_free = fifo->size - fifo->write_ptr;
	int res = (num_free >= n);
	if (!res) {
		// the FIFO is full, check if it is artificial
		int num_tokens = fifo->write_ptr - fifo->read_ptr;
		if (fifo->read_ptr >= num_tokens + n) {
			// there is room to copy the not-read-yet tokens at the beginning of the FIFO
			if (num_tokens > 0) {
				// only copy if there are tokens
				memcpy(fifo->contents, contents(fifo, fifo->read_ptr), num_tokens * fifo->elt_size);
			}
			fifo->read_ptr = 0;
			fifo->write_ptr = num_tokens;

			res = ((fifo->size - num_tokens) >= n);
		}
	}
	return res;
}

static int hasTokens(struct fifo_s *fifo, int n) {
	int num_tokens = fifo->write_ptr - fifo->read_ptr;
	int res = (num_tokens >= n);
	if (!res) {
		if (num_tokens == 0) {
			// there are no tokens in the FIFO, just resets the read/write pointers
			// this might allow fireable actions with no room on output ports to fire
			fifo->read_ptr = 0;
			fifo->write_ptr = 0;
		}
	}

	return res;
}

static void *getWritePtr(struct fifo_s *fifo, int n) {
	int ptr = fifo->write_ptr;
	fifo->write_ptr += n;
	return contents(fifo, ptr);
}

static int array_0[SIZE * sizeof(int)];
static struct fifo_s fifo_0 = { sizeof(int), SIZE, (char *)array_0, 0, 0 };

static int array_1[SIZE * sizeof(int)];
static struct fifo_s fifo_1 = { sizeof(int), SIZE, (char *)array_1, 0, 0 };

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

void scheduler_v1() {
	int go_on = 1;
	while (go_on) {
		go_on = 0;
		go_on += source_scheduler();
		go_on += compute_scheduler();
		go_on += sink_scheduler();
	}
}

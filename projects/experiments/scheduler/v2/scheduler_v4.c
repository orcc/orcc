// v4: ring buffer FIFO

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "defs.h"

/** lock free fifo ring buffer structure */
typedef struct lock_free_fifo {
  /** Size of the ringbuffer (in elements) */
  unsigned int size;
  /** the memory containing the ringbuffer */
  void * data;
  /** the size of an element */
  size_t object_size;
  /** the current position of the reader */
  unsigned int read_index;
  /** the current position of the writer */
  unsigned int write_index;
} lff_t;

#define SIZE 1000

static int lff_hasRoom (lff_t * lff, unsigned int room) {
	static unsigned int ri;

	/* got to read read_index only once for safety */
	ri = lff->read_index;

	/* lots of logic for when we're allowed to write to the fifo which basically
	boils down to "don't write if we're one element behind the read index" */  
	if ((ri > lff->write_index && ri - lff->write_index > room) 
		|| (lff->write_index >= ri && lff->size - lff->write_index + ri > room)) { 
		return 1;
	} else {
		return 0;
	}
}

static int lff_hasTokens (lff_t * lff, unsigned int nbTokens) {
	static unsigned int wi;

	wi=lff->write_index;
	if ((wi > lff->read_index && wi - lff->read_index >= nbTokens) 
		|| (lff->read_index > wi && lff->size - lff->read_index + wi >= nbTokens)) { 
		return 1;
	} else {
		return 0;
	}
}

static void lff_read (lff_t * lff, void * data) {
	memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
		 lff->object_size);
	lff->read_index++;
	if(lff->read_index==lff->size)
		lff->read_index=0;
}

static void lff_write (lff_t * lff, void * data) {
	
	memcpy (((char *)lff->data) + (lff->write_index * lff->object_size),
		data, lff->object_size);
	/* FIXME: is this safe? */
	lff->write_index++;
	if(lff->write_index==lff->size)
		lff->write_index=0;
}


static void* array_0[SIZE * sizeof(int)];
static struct lock_free_fifo fifo_0 = { SIZE, (char *)array_0, sizeof(int), 0, 0 };

static void* array_1[SIZE * sizeof(int)];
static struct lock_free_fifo fifo_1 = { SIZE, (char *)array_1, sizeof(int), 0, 0 };

static struct lff_t *source_O = &fifo_0;
static struct lff_t *compute_I = &fifo_0;
static struct lff_t *compute_O = &fifo_1;
static struct lff_t *sink_I = &fifo_1;

static struct lff_t *full_fifos[2];
static int last_idx = 0;


static void action_source() {
	
	lff_write(source_O, &source_X);
	source_X++;
}


static int source_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (source_X < N) {
			if (lff_hasRoom(source_O, n_token)) {
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
	int rptr[NTOKEN];
	int wptr[NTOKEN];

	lff_read (compute_I, rptr);
	for (i=0; i <n_token; i++){
		wptr[i] = rptr[i] + i;
	}
	lff_write (compute_O, wptr);
}

static int compute_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (lff_hasTokens(compute_I, n_token)) {
			if (lff_hasRoom(compute_O, n_token)) {
				action_compute();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_sink() {
	int rptr[1];
	lff_read(sink_I,rptr);
}

static int sink_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (lff_hasTokens(sink_I, n_token)) {
			action_sink();			
			res = 1;
			i++;
		}
	}

	return i;
}

void scheduler_v4() {
	int go_on = 1;
	while (go_on) {
		go_on = 0;
		go_on += source_scheduler();
		go_on += compute_scheduler();
		go_on += sink_scheduler();
	}
}

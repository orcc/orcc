// basic schedule

#include "defs.h"

#define SIZE 10000

static int dummyInt;

static int source_out[SIZE];
static int compute_out[SIZE];

static void source() {
	int i;

	for (i = 0; i < SIZE; i++) {
		source_out[i] = source_X++;
	}
}

static void compute() {
	int i;

	for (i = 0; i < SIZE; i++) {
		compute_out[i] = source_out[i] + 1;
	}
}

static void sink() {
	int i;

	for (i = 0; i < SIZE; i++) {
		dummyInt = compute_out[i];
	}
}

void scheduler_vSDF() {
	int i;

	for (i = 0; i < N / SIZE; i++) {
		source();
		compute();
		sink();
	}
}

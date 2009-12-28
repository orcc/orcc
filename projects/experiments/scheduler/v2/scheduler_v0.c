// basic schedule

#include "defs.h"

static int dummyInt;

void scheduler_v0() {
	while (source_X < N) {
		source_X++;
		dummyInt = source_X;
		dummyInt++;
	}
}

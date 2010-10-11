// Generated from "TestFifo"

#include <locale.h>
#include <stdio.h>
#include <stdlib.h>
#ifdef __APPLE__
#include "SDL.h"
#endif

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

#define SIZE 512
// #define PRINT_FIRINGS

////////////////////////////////////////////////////////////////////////////////
// FIFO allocation
DECLARE_FIFO(i32, SIZE, 0)
////////////////////////////////////////////////////////////////////////////////
// FIFO pointer assignments
struct fifo_i32_s *data_src_O = &fifo_0;
struct fifo_i32_s *compute_I = &fifo_0;

////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
// Action schedulers
extern void data_src_scheduler(struct schedinfo_s *si);
extern void compute_scheduler(struct schedinfo_s *si);

////////////////////////////////////////////////////////////////////////////////
// Actor scheduler

static void scheduler() {
	struct schedinfo_s si;
	int num_firings = 0;

	while (1) {
		si.num_firings = 0;
		data_src_scheduler(&si);
		#ifdef PRINT_FIRINGS
		printf("data_src_scheduler: %i\n", si.num_firings);
		#endif
		num_firings += si.num_firings;
		si.num_firings = 0;
		compute_scheduler(&si);
		#ifdef PRINT_FIRINGS
		printf("compute_scheduler: %i\n", si.num_firings);
		#endif
		num_firings += si.num_firings;
		#ifdef PRINT_FIRINGS
		printf("\n");
		#endif
		if (num_firings == 0) {
			break;
		} else {
			num_firings = 0;
		}
	}
}

////////////////////////////////////////////////////////////////////////////////
int main(int argc, char *argv[]) {
	init_orcc(argc, argv);

	scheduler();

	return 0;
}

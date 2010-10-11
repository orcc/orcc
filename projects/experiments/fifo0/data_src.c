// /TestFifo/data_src
// Source file is "/home/moot/orcc/trunk/test/Pass/VTL/Fifo/DataSource.cal"

#include <stdio.h>
#include <stdlib.h>

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"
////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
////////////////////////////////////////////////////////////////////////////////
// Output FIFOs
extern struct fifo_i32_s *data_src_O;


////////////////////////////////////////////////////////////////////////////////
// Parameter values of the instance
#define NUM 300000000

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor
static i32 count = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures
////////////////////////////////////////////////////////////////////////////////
// Actions

static void done() {
	i32 O_buf[1];
	i32 *O;

	O = fifo_i32_write(data_src_O, O_buf, 1);
	O[0] = -1;fifo_i32_write_end(data_src_O, O_buf, 1);
}



static i32 isSchedulable_done() {
	i32 local_count_1;
	i32 local_NUM_1;
	i32 result_1;

	local_count_1 = count;
	local_NUM_1 = NUM;
	result_1 = local_count_1 == local_NUM_1;
	return result_1;
}



static void loop() {
	i32 O_buf[1];
	i32 *O;
	i32 local_count_1;
	i32 current_1;
	i32 local_count_2;

	O = fifo_i32_write(data_src_O, O_buf, 1);
	local_count_1 = count;
	current_1 = local_count_1;
	local_count_2 = local_count_1 + 1;
	O[0] = current_1;fifo_i32_write_end(data_src_O, O_buf, 1);
	count = local_count_2;
}



static i32 isSchedulable_loop() {
	i32 result_1;

	result_1 = 1;
	return result_1;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler
enum states {
	s_s0 = 0,
	s_s1
};

static char *stateNames[] = {
	"s_s0",
	"s_s1"
};

static enum states _FSM_state = s_s0;

void data_src_scheduler(struct schedinfo_s *si) {
	int i = 0;

	// jump to FSM state 
	switch (_FSM_state) {
	case s_s0:
		goto l_s0;
	case s_s1:
		goto l_s1;
	default:
		printf("unknown state: %s\n", stateNames[_FSM_state]);
		//wait_for_key();
		//exit(1);
		return;
	}

	// FSM transitions

l_s0:
	if (isSchedulable_done()) {
		int ports = 0;
		if (!fifo_i32_has_room(data_src_O, 1)) {
			ports |= 0x01;
		}
		if (ports != 0) {
			_FSM_state = s_s0;
			si->num_firings = i;
			si->reason = full;
			si->ports = ports;
			return;
		}
		done();
		i++;
		goto l_s1;
	} else if (isSchedulable_loop()) {
		int ports = 0;
		if (!fifo_i32_has_room(data_src_O, 1)) {
			ports |= 0x01;
		}
		if (ports != 0) {
			_FSM_state = s_s0;
			si->num_firings = i;
			si->reason = full;
			si->ports = ports;
			return;
		}
		loop();
		i++;
		goto l_s0;
	} else {
		_FSM_state = s_s0;
		si->num_firings = i;
		si->reason = starved;
		si->ports = 0x00;
		return;
	}

l_s1:
	printf("stuck in state \"s1\"\n");
	//wait_for_key();
	//exit(1);

}


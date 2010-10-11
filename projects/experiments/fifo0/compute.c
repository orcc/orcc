// /TestFifo/compute
// Source file is "/home/moot/orcc/trunk/test/Pass/VTL/Fifo/Compute.cal"

#include <stdio.h>
#include <stdlib.h>

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"
////////////////////////////////////////////////////////////////////////////////
// Input FIFOs
extern struct fifo_i32_s *compute_I;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs
extern struct fifo_i32_s *compute_O;


////////////////////////////////////////////////////////////////////////////////
// Parameter values of the instance
////////////////////////////////////////////////////////////////////////////////
// State variables of the actor
static i64 sum = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures
////////////////////////////////////////////////////////////////////////////////
// Actions

static void do_sum() {
	i32 I_buf[1];
	i32 *I;
	i64 local_sum_1;
	i32 i_1;
	i64 local_sum_2;

	local_sum_1 = sum;
	I = fifo_i32_read(compute_I, I_buf, 1);
	i_1 = I[0];
	local_sum_2 = local_sum_1 + i_1;
	sum = local_sum_2;
	fifo_i32_read_end(compute_I, 1);
}



static i32 isSchedulable_do_sum() {
	i32 _tmp_hasTokens_1;
	i32 result_1;
	i32 result_2;
	i32 result_3;

	_tmp_hasTokens_1 = fifo_i32_has_tokens(compute_I, 1);
	if (_tmp_hasTokens_1) {
		result_1 = 1;
		result_2 = result_1;
	} else {
		result_3 = 0;
		result_2 = result_3;
	}
	return result_2;
}



static void print_sum() {
	i64 local_sum_1;

	local_sum_1 = sum;
	printf("sum = " "%lli" "\n", local_sum_1);
	fifo_i32_read_end(compute_I, 1);
}



static i32 isSchedulable_print_sum() {
	i32 I_buf[1];
	i32 *I;
	i32 _tmp_hasTokens_1;
	i32 i_1;
	i32 result_1;
	i32 result_2;
	i32 result_3;

	_tmp_hasTokens_1 = fifo_i32_has_tokens(compute_I, 1);
	if (_tmp_hasTokens_1) {
		I = fifo_i32_peek(compute_I, I_buf, 1);
		i_1 = I[0];
		result_1 = i_1 < 0;
		result_2 = result_1;
	} else {
		result_3 = 0;
		result_2 = result_3;
	}
	return result_2;
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

void compute_scheduler(struct schedinfo_s *si) {
	int i = 0;

	// jump to FSM state 
	switch (_FSM_state) {
	case s_s0:
		goto l_s0;
	case s_s1:
		goto l_s1;
	default:
		printf("unknown state: %s\n", stateNames[_FSM_state]);
		return;
		//wait_for_key();
		//exit(1);
	}

	// FSM transitions

l_s0:
	if (isSchedulable_print_sum()) {
		print_sum();
		i++;
		goto l_s1;
	} else if (isSchedulable_do_sum()) {
		do_sum();
		i++;
		goto l_s0;
	} else {
		_FSM_state = s_s0;
		si->num_firings = i;
		si->reason = starved;
		si->ports = 0x01;
		return;
	}

l_s1:
	printf("stuck in state \"s1\"\n");
	//wait_for_key();
	//exit(1);

}


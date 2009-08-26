// Generated from "shufflefly"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *shufflefly_X0;
extern struct fifo_s *shufflefly_X1;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *shufflefly_Y0;
extern struct fifo_s *shufflefly_Y1;
extern struct fifo_s *shufflefly_Y2;
extern struct fifo_s *shufflefly_Y3;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int sz = 24;
static int D0;
static int D1;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void a0() {
  int *X0;
  int *X1;
  int a_1;
  int b_1;
  
  X0 = getReadPtr(shufflefly_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(shufflefly_X1, 1);
  b_1 = X1[0];
  D0 = a_1;
  D1 = b_1;
}

static int isSchedulable_a0() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(shufflefly_X0, 1);
  _tmp2_1 = hasTokens(shufflefly_X1, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a1() {
  int *Y3;
  int *Y2;
  int *Y1;
  int *Y0;
  int *X0;
  int *X1;
  int d2_1;
  int d3_1;
  int _tmp0_1;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  
  Y0 = getWritePtr(shufflefly_Y0, 1);
  Y1 = getWritePtr(shufflefly_Y1, 1);
  Y2 = getWritePtr(shufflefly_Y2, 1);
  Y3 = getWritePtr(shufflefly_Y3, 1);
  X0 = getReadPtr(shufflefly_X0, 1);
  d2_1 = X0[0];
  X1 = getReadPtr(shufflefly_X1, 1);
  d3_1 = X1[0];
  _tmp0_1 = D0;
  Y0[0] = _tmp0_1 + d2_1;
  _tmp1_1 = D0;
  Y1[0] = _tmp1_1 - d2_1;
  _tmp2_1 = D1;
  Y2[0] = _tmp2_1 + d3_1;
  _tmp3_1 = D1;
  Y3[0] = _tmp3_1 - d3_1;
}

static int isSchedulable_a1() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(shufflefly_X0, 1);
  _tmp2_1 = hasTokens(shufflefly_X1, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
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

static int s0_state_scheduler() {
  int res;
  
  if (isSchedulable_a0()) {
    a0();
    _FSM_state = s_s1;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int s1_state_scheduler() {
  int res;
  
  if (isSchedulable_a1()) {
    if (hasRoom(shufflefly_Y0, 1) && hasRoom(shufflefly_Y1, 1) && hasRoom(shufflefly_Y2, 1) && hasRoom(shufflefly_Y3, 1)) {
      a1();
      _FSM_state = s_s0;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

int shufflefly_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_s0:
        res = s0_state_scheduler();
        break;
      case s_s1:
        res = s1_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

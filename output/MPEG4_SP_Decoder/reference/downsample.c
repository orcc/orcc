// Generated from "downsample"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *downsample_R;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *downsample_R2;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor


////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void a0() {
  int *R;
  
  R = getReadPtr(downsample_R, 1);
}

static int isSchedulable_a0() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(downsample_R, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a1() {
  int *R2;
  int *R;
  int r_1;
  
  R2 = getWritePtr(downsample_R2, 1);
  R = getReadPtr(downsample_R, 1);
  r_1 = R[0];
  R2[0] = r_1;
}

static int isSchedulable_a1() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(downsample_R, 1);
  if (_tmp1_1) {
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
    if (hasRoom(downsample_R2, 1)) {
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

int downsample_scheduler() {
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

// Generated from "shuffle"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *shuffle_X0;
extern struct fifo_s *shuffle_X1;
extern struct fifo_s *shuffle_X2;
extern struct fifo_s *shuffle_X3;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *shuffle_Y0;
extern struct fifo_s *shuffle_Y1;
extern struct fifo_s *shuffle_Y2;
extern struct fifo_s *shuffle_Y3;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int sz = 24;
static int x4;
static int x5;
static int x6h;
static int x7h;
static int x6l;
static int x7l;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void a0() {
  int *Y1;
  int *Y0;
  int *X0;
  int *X1;
  int *X2;
  int *X3;
  int a_1;
  int b_1;
  int c_1;
  int d_1;
  
  Y0 = getWritePtr(shuffle_Y0, 1);
  Y1 = getWritePtr(shuffle_Y1, 1);
  X0 = getReadPtr(shuffle_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(shuffle_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(shuffle_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(shuffle_X3, 1);
  d_1 = X3[0];
  x4 = c_1;
  x5 = d_1;
  Y0[0] = a_1;
  Y1[0] = b_1;
}

static int isSchedulable_a0() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(shuffle_X0, 1);
  _tmp2_1 = hasTokens(shuffle_X1, 1);
  _tmp3_1 = hasTokens(shuffle_X2, 1);
  _tmp4_1 = hasTokens(shuffle_X3, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
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
  int *X0;
  int *X1;
  int *X2;
  int *X3;
  int x2_1;
  int a_1;
  int x3_1;
  int b_1;
  short ah0_1;
  short bh0_1;
  short al0_1;
  short bl0_1;
  
  Y2 = getWritePtr(shuffle_Y2, 1);
  Y3 = getWritePtr(shuffle_Y3, 1);
  X0 = getReadPtr(shuffle_X0, 1);
  x2_1 = X0[0];
  X1 = getReadPtr(shuffle_X1, 1);
  a_1 = X1[0];
  X2 = getReadPtr(shuffle_X2, 1);
  x3_1 = X2[0];
  X3 = getReadPtr(shuffle_X3, 1);
  b_1 = X3[0];
  ah0_1 = a_1 >> 8;
  bh0_1 = b_1 >> 8;
  al0_1 = a_1 & 255;
  bl0_1 = b_1 & 255;
  x6h = 181 * (ah0_1 + bh0_1);
  x7h = 181 * (ah0_1 - bh0_1);
  x6l = 181 * (al0_1 + bl0_1) + 128;
  x7l = 181 * (al0_1 - bl0_1) + 128;
  Y2[0] = x2_1;
  Y3[0] = x3_1;
}

static int isSchedulable_a1() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(shuffle_X0, 1);
  _tmp2_1 = hasTokens(shuffle_X1, 1);
  _tmp3_1 = hasTokens(shuffle_X2, 1);
  _tmp4_1 = hasTokens(shuffle_X3, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a2() {
  int *Y3;
  int *Y2;
  int *Y1;
  int *Y0;
  int _tmp0_1;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  
  Y0 = getWritePtr(shuffle_Y0, 1);
  Y1 = getWritePtr(shuffle_Y1, 1);
  Y2 = getWritePtr(shuffle_Y2, 1);
  Y3 = getWritePtr(shuffle_Y3, 1);
  _tmp0_1 = x4;
  Y0[0] = _tmp0_1;
  _tmp1_1 = x5;
  Y1[0] = _tmp1_1;
  _tmp2_1 = x6h;
  _tmp3_1 = x6l;
  Y2[0] = _tmp2_1 + (_tmp3_1 >> 8);
  _tmp4_1 = x7h;
  _tmp5_1 = x7l;
  Y3[0] = _tmp4_1 + (_tmp5_1 >> 8);
}

static int isSchedulable_a2() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_s0 = 0,
  s_s1,
  s_s2
};

static char *stateNames[] = {
  "s_s0",
  "s_s1",
  "s_s2"
};

static enum states _FSM_state = s_s0;

static int s0_state_scheduler() {
  int res;
  
  if (isSchedulable_a0()) {
    if (hasRoom(shuffle_Y0, 1) && hasRoom(shuffle_Y1, 1)) {
      a0();
      _FSM_state = s_s1;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int s1_state_scheduler() {
  int res;
  
  if (isSchedulable_a1()) {
    if (hasRoom(shuffle_Y2, 1) && hasRoom(shuffle_Y3, 1)) {
      a1();
      _FSM_state = s_s2;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int s2_state_scheduler() {
  int res;
  
  if (isSchedulable_a2()) {
    if (hasRoom(shuffle_Y0, 1) && hasRoom(shuffle_Y1, 1) && hasRoom(shuffle_Y2, 1) && hasRoom(shuffle_Y3, 1)) {
      a2();
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

int shuffle_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_s0:
        res = s0_state_scheduler();
        break;
      case s_s1:
        res = s1_state_scheduler();
        break;
      case s_s2:
        res = s2_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

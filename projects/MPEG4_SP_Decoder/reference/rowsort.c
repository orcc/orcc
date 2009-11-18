// Generated from "rowsort"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *rowsort_ROW;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *rowsort_Y0;
extern struct fifo_s *rowsort_Y1;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int sz = 13;
static short x0;
static short x1;
static short x2;
static short x3;
static short x5;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void a0() {
  short *ROW;
  short a_1;
  
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  x0 = a_1;
}

static int isSchedulable_a0() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
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
  short *ROW;
  short a_1;
  
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  x1 = a_1;
}

static int isSchedulable_a1() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a2() {
  short *ROW;
  short a_1;
  
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  x2 = a_1;
}

static int isSchedulable_a2() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a3() {
  short *ROW;
  short a_1;
  
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  x3 = a_1;
}

static int isSchedulable_a3() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a4() {
  short *Y1;
  short *Y0;
  short *ROW;
  short a_1;
  short _tmp0_1;
  
  Y0 = getWritePtr(rowsort_Y0, 1);
  Y1 = getWritePtr(rowsort_Y1, 1);
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  _tmp0_1 = x0;
  Y0[0] = _tmp0_1;
  Y1[0] = a_1;
}

static int isSchedulable_a4() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a5() {
  short *ROW;
  short a_1;
  
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  x5 = a_1;
}

static int isSchedulable_a5() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a6() {
  short *Y1;
  short *Y0;
  short *ROW;
  short a_1;
  short _tmp0_1;
  
  Y0 = getWritePtr(rowsort_Y0, 1);
  Y1 = getWritePtr(rowsort_Y1, 1);
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  _tmp0_1 = x2;
  Y0[0] = _tmp0_1;
  Y1[0] = a_1;
}

static int isSchedulable_a6() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a7() {
  short *Y1;
  short *Y0;
  short *ROW;
  short a_1;
  short _tmp0_1;
  
  Y0 = getWritePtr(rowsort_Y0, 1);
  Y1 = getWritePtr(rowsort_Y1, 1);
  ROW = getReadPtr(rowsort_ROW, 1);
  a_1 = ROW[0];
  _tmp0_1 = x1;
  Y0[0] = _tmp0_1;
  Y1[0] = a_1;
}

static int isSchedulable_a7() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rowsort_ROW, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void a9() {
  short *Y1;
  short *Y0;
  short _tmp0_1;
  short _tmp1_1;
  
  Y0 = getWritePtr(rowsort_Y0, 1);
  Y1 = getWritePtr(rowsort_Y1, 1);
  _tmp0_1 = x5;
  Y0[0] = _tmp0_1;
  _tmp1_1 = x3;
  Y1[0] = _tmp1_1;
}

static int isSchedulable_a9() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_s0 = 0,
  s_s1,
  s_s2,
  s_s3,
  s_s4,
  s_s5,
  s_s6,
  s_s7,
  s_s8
};

static char *stateNames[] = {
  "s_s0",
  "s_s1",
  "s_s2",
  "s_s3",
  "s_s4",
  "s_s5",
  "s_s6",
  "s_s7",
  "s_s8"
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
    a1();
    _FSM_state = s_s2;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int s2_state_scheduler() {
  int res;
  
  if (isSchedulable_a2()) {
    a2();
    _FSM_state = s_s3;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int s3_state_scheduler() {
  int res;
  
  if (isSchedulable_a3()) {
    a3();
    _FSM_state = s_s4;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int s4_state_scheduler() {
  int res;
  
  if (isSchedulable_a4()) {
    if (hasRoom(rowsort_Y0, 1) && hasRoom(rowsort_Y1, 1)) {
      a4();
      _FSM_state = s_s5;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int s5_state_scheduler() {
  int res;
  
  if (isSchedulable_a5()) {
    a5();
    _FSM_state = s_s6;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int s6_state_scheduler() {
  int res;
  
  if (isSchedulable_a6()) {
    if (hasRoom(rowsort_Y0, 1) && hasRoom(rowsort_Y1, 1)) {
      a6();
      _FSM_state = s_s7;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int s7_state_scheduler() {
  int res;
  
  if (isSchedulable_a7()) {
    if (hasRoom(rowsort_Y0, 1) && hasRoom(rowsort_Y1, 1)) {
      a7();
      _FSM_state = s_s8;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int s8_state_scheduler() {
  int res;
  
  if (isSchedulable_a9()) {
    if (hasRoom(rowsort_Y0, 1) && hasRoom(rowsort_Y1, 1)) {
      a9();
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

int rowsort_scheduler() {
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
      case s_s3:
        res = s3_state_scheduler();
        break;
      case s_s4:
        res = s4_state_scheduler();
        break;
      case s_s5:
        res = s5_state_scheduler();
        break;
      case s_s6:
        res = s6_state_scheduler();
        break;
      case s_s7:
        res = s7_state_scheduler();
        break;
      case s_s8:
        res = s8_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

// Generated from "fairmerge"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *fairmerge_R0;
extern struct fifo_s *fairmerge_R1;
extern struct fifo_s *fairmerge_C0;
extern struct fifo_s *fairmerge_C1;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *fairmerge_Y0;
extern struct fifo_s *fairmerge_Y1;
extern struct fifo_s *fairmerge_ROWOUT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int rsz = 13;
static int csz = 16;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void row() {
  int *ROWOUT;
  short *Y1;
  short *Y0;
  short *R0;
  short *R1;
  short a_1;
  short b_1;
  
  Y0 = getWritePtr(fairmerge_Y0, 1);
  Y1 = getWritePtr(fairmerge_Y1, 1);
  ROWOUT = getWritePtr(fairmerge_ROWOUT, 1);
  R0 = getReadPtr(fairmerge_R0, 1);
  a_1 = R0[0];
  R1 = getReadPtr(fairmerge_R1, 1);
  b_1 = R1[0];
  Y0[0] = a_1;
  Y1[0] = b_1;
  ROWOUT[0] = 1;
}

static int isSchedulable_row() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(fairmerge_R0, 1);
  _tmp2_1 = hasTokens(fairmerge_R1, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void col() {
  int *ROWOUT;
  short *Y1;
  short *Y0;
  short *C0;
  short *C1;
  short a_1;
  short b_1;
  
  Y0 = getWritePtr(fairmerge_Y0, 1);
  Y1 = getWritePtr(fairmerge_Y1, 1);
  ROWOUT = getWritePtr(fairmerge_ROWOUT, 1);
  C0 = getReadPtr(fairmerge_C0, 1);
  a_1 = C0[0];
  C1 = getReadPtr(fairmerge_C1, 1);
  b_1 = C1[0];
  Y0[0] = a_1;
  Y1[0] = b_1;
  ROWOUT[0] = 0;
}

static int isSchedulable_col() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(fairmerge_C0, 1);
  _tmp2_1 = hasTokens(fairmerge_C1, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void row_low() {
  int *ROWOUT;
  short *Y1;
  short *Y0;
  short *R0;
  short *R1;
  short a_1;
  short b_1;
  
  Y0 = getWritePtr(fairmerge_Y0, 1);
  Y1 = getWritePtr(fairmerge_Y1, 1);
  ROWOUT = getWritePtr(fairmerge_ROWOUT, 1);
  R0 = getReadPtr(fairmerge_R0, 1);
  a_1 = R0[0];
  R1 = getReadPtr(fairmerge_R1, 1);
  b_1 = R1[0];
  Y0[0] = a_1;
  Y1[0] = b_1;
  ROWOUT[0] = 1;
}

static int isSchedulable_row_low() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(fairmerge_R0, 1);
  _tmp2_1 = hasTokens(fairmerge_R1, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void col_low() {
  int *ROWOUT;
  short *Y1;
  short *Y0;
  short *C0;
  short *C1;
  short a_1;
  short b_1;
  
  Y0 = getWritePtr(fairmerge_Y0, 1);
  Y1 = getWritePtr(fairmerge_Y1, 1);
  ROWOUT = getWritePtr(fairmerge_ROWOUT, 1);
  C0 = getReadPtr(fairmerge_C0, 1);
  a_1 = C0[0];
  C1 = getReadPtr(fairmerge_C1, 1);
  b_1 = C1[0];
  Y0[0] = a_1;
  Y1[0] = b_1;
  ROWOUT[0] = 0;
}

static int isSchedulable_col_low() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(fairmerge_C0, 1);
  _tmp2_1 = hasTokens(fairmerge_C1, 1);
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
  s_c0 = 0,
  s_c1,
  s_c2,
  s_c3,
  s_r0,
  s_r1,
  s_r2,
  s_r3
};

static char *stateNames[] = {
  "s_c0",
  "s_c1",
  "s_c2",
  "s_c3",
  "s_r0",
  "s_r1",
  "s_r2",
  "s_r3"
};

static enum states _FSM_state = s_r0;

static int c0_state_scheduler() {
  int res;
  
  if (isSchedulable_col()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      col();
      _FSM_state = s_c1;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_row_low()) {
      if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
        row_low();
        _FSM_state = s_r1;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int c1_state_scheduler() {
  int res;
  
  if (isSchedulable_col()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      col();
      _FSM_state = s_c2;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int c2_state_scheduler() {
  int res;
  
  if (isSchedulable_col()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      col();
      _FSM_state = s_c3;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int c3_state_scheduler() {
  int res;
  
  if (isSchedulable_col()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      col();
      _FSM_state = s_r0;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int r0_state_scheduler() {
  int res;
  
  if (isSchedulable_row()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      row();
      _FSM_state = s_r1;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_col_low()) {
      if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
        col_low();
        _FSM_state = s_c1;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int r1_state_scheduler() {
  int res;
  
  if (isSchedulable_row()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      row();
      _FSM_state = s_r2;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int r2_state_scheduler() {
  int res;
  
  if (isSchedulable_row()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      row();
      _FSM_state = s_r3;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int r3_state_scheduler() {
  int res;
  
  if (isSchedulable_row()) {
    if (hasRoom(fairmerge_ROWOUT, 1) && hasRoom(fairmerge_Y0, 1) && hasRoom(fairmerge_Y1, 1)) {
      row();
      _FSM_state = s_c0;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

int fairmerge_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_c0:
        res = c0_state_scheduler();
        break;
      case s_c1:
        res = c1_state_scheduler();
        break;
      case s_c2:
        res = c2_state_scheduler();
        break;
      case s_c3:
        res = c3_state_scheduler();
        break;
      case s_r0:
        res = r0_state_scheduler();
        break;
      case s_r1:
        res = r1_state_scheduler();
        break;
      case s_r2:
        res = r2_state_scheduler();
        break;
      case s_r3:
        res = r3_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

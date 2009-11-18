// Generated from "acpred"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *acpred_AC;
extern struct fifo_s *acpred_PTR;
extern struct fifo_s *acpred_START;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *acpred_OUT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MAXW_IN_MB = 121;
static int MB_COORD_SZ = 8;
static int SAMPLE_SZ = 13;
static char count;
static int BUF_SIZE = 984;
static short ptr;
static short pred_ptr;
static short buf[15744] = {0};
static char comp;
static int top;
static int acpred_flag;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void newvop() {
  char *START;
  
  START = getReadPtr(acpred_START, 1);
  comp = 0;
  ptr = 8;
}

static int isSchedulable_newvop() {
  char *START;
  int _tmp1_1;
  char s_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(acpred_START, 1);
  if (_tmp1_1) {
    START = getPeekPtr(acpred_START, 1);
    s_1 = START[0];
    _tmp0_1 = s_1 == -2;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void skip() {
  char *START;
  
  START = getReadPtr(acpred_START, 1);
  count = 64;
}

static int isSchedulable_skip() {
  char *START;
  int _tmp1_1;
  char s_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(acpred_START, 1);
  if (_tmp1_1) {
    START = getPeekPtr(acpred_START, 1);
    s_1 = START[0];
    _tmp0_1 = s_1 < 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void start() {
  char *START;
  short *PTR;
  char s_1;
  short p_1;
  
  START = getReadPtr(acpred_START, 1);
  s_1 = START[0];
  PTR = getReadPtr(acpred_PTR, 1);
  p_1 = PTR[0];
  count = 1;
  pred_ptr = p_1;
  top = s_1 == 2;
  acpred_flag = s_1 != 0;
}

static int isSchedulable_start() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(acpred_START, 1);
  _tmp2_1 = hasTokens(acpred_PTR, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void advance() {
  char _tmp1_1;
  short _tmp2_1;
  int _tmp3_1;
  int _tmp3_2;
  short _tmp5_1;
  int _tmp3_3;
  
  comp++;
  _tmp1_1 = comp;
  if (_tmp1_1 == 6) {
    comp = 0;
    _tmp2_1 = ptr;
    if (_tmp2_1 == 8) {
      _tmp3_1 = 976;
      _tmp3_2 = _tmp3_1;
    } else {
      _tmp5_1 = ptr;
      _tmp3_3 = _tmp5_1 - 8;
      _tmp3_2 = _tmp3_3;
    }
    ptr = _tmp3_2;
  }
}

static int isSchedulable_advance() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 == 64;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void copy() {
  short *OUT;
  short *AC;
  short ac_1;
  short pred0_1;
  char _tmp0_1;
  char v0_1;
  char _tmp1_1;
  char h0_1;
  int top_edge0_1;
  int left_edge0_1;
  char index_0_1;
  char index_0_2;
  char index_0_3;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  short _tmp5_1;
  short _tmp6_1;
  short pred0_2;
  short pred0_3;
  short _tmp7_1;
  char _tmp8_1;
  
  OUT = getWritePtr(acpred_OUT, 1);
  AC = getReadPtr(acpred_AC, 1);
  ac_1 = AC[0];
  pred0_1 = ac_1;
  _tmp0_1 = count;
  v0_1 = _tmp0_1 & 7;
  _tmp1_1 = count;
  h0_1 = _tmp1_1 >> 3 & 7;
  top_edge0_1 = h0_1 == 0;
  left_edge0_1 = v0_1 == 0;
  if (top_edge0_1) {
    index_0_1 = v0_1;
    index_0_2 = index_0_1;
  } else {
    index_0_3 = h0_1 | 8;
    index_0_2 = index_0_3;
  }
  _tmp2_1 = acpred_flag;
  _tmp3_1 = top;
  _tmp4_1 = top;
  if (_tmp2_1 && (_tmp3_1 && top_edge0_1 || !_tmp4_1 && left_edge0_1)) {
    _tmp5_1 = pred_ptr;
    _tmp6_1 = buf[_tmp5_1 << 4 | index_0_2];
    pred0_2 = pred0_1 + _tmp6_1;
    pred0_3 = pred0_2;
  } else {
    pred0_3 = pred0_1;
  }
  if (left_edge0_1 || top_edge0_1) {
    _tmp7_1 = ptr;
    _tmp8_1 = comp;
    buf[(_tmp7_1 | _tmp8_1) << 4 | index_0_2] = pred0_3;
  }
  count++;
  OUT[0] = pred0_3;
}

static int isSchedulable_copy() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(acpred_AC, 1);
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
  s_pred = 0,
  s_start
};

static char *stateNames[] = {
  "s_pred",
  "s_start"
};

static enum states _FSM_state = s_start;

static int pred_state_scheduler() {
  int res;
  
  if (isSchedulable_advance()) {
    advance();
    _FSM_state = s_start;
    res = 1;
  } else {
    if (isSchedulable_copy()) {
      if (hasRoom(acpred_OUT, 1)) {
        copy();
        _FSM_state = s_pred;
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

static int start_state_scheduler() {
  int res;
  
  if (isSchedulable_newvop()) {
    newvop();
    _FSM_state = s_start;
    res = 1;
  } else {
    if (isSchedulable_skip()) {
      skip();
      _FSM_state = s_pred;
      res = 1;
    } else {
      if (isSchedulable_start()) {
        start();
        _FSM_state = s_pred;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

int acpred_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_pred:
        res = pred_state_scheduler();
        break;
      case s_start:
        res = start_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

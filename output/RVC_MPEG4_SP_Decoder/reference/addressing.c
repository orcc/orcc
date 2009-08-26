// Generated from "addressing"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *addressing_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *addressing_A;
extern struct fifo_s *addressing_B;
extern struct fifo_s *addressing_C;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MAXW_IN_MB = 121;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int NEWVOP = 2048;
static int INTRA = 1024;
static char mbx = 0;
static int top_edge = 1;
static int left_edge;
static char mbwidth = 0;
static int BUF_SIZE = 123;
static int coded[123] = {0};
static char ptr;
static char ptr_left;
static char ptr_above;
static char ptr_above_left;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static char decrement(char p) {
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  if (p == 1) {
    _tmp0_1 = 122;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = p - 1;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void start() {
  short *BTYPE;
  
  BTYPE = getReadPtr(addressing_BTYPE, 1);
  mbx = 0;
  top_edge = 1;
  left_edge = 1;
}

static int isSchedulable_start() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(addressing_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(addressing_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2048) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getw_() {
  short *BTYPE;
  short w_1;
  
  BTYPE = getReadPtr(addressing_BTYPE, 1);
  w_1 = BTYPE[0];
  mbwidth = w_1;
  ptr = 1;
  ptr_left = 2;
  ptr_above = 1 + w_1;
  ptr_above_left = 2 + w_1;
}

static int isSchedulable_getw() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(addressing_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void geth() {
  short *BTYPE;
  
  BTYPE = getReadPtr(addressing_BTYPE, 1);
}

static int isSchedulable_geth() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(addressing_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_intra() {
  short *BTYPE;
  char _tmp0_1;
  
  BTYPE = getReadPtr(addressing_BTYPE, 1);
  _tmp0_1 = ptr;
  coded[_tmp0_1] = 1;
}

static int isSchedulable_read_intra() {
  short *BTYPE;
  int _tmp1_1;
  short type_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(addressing_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(addressing_BTYPE, 1);
    type_1 = BTYPE[0];
    _tmp0_1 = (type_1 & 1024) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_other() {
  short *BTYPE;
  char _tmp0_1;
  
  BTYPE = getReadPtr(addressing_BTYPE, 1);
  _tmp0_1 = ptr;
  coded[_tmp0_1] = 0;
}

static int isSchedulable_read_other() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(addressing_BTYPE, 1);
  if (_tmp1_1) {
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
  char _tmp2_1;
  char _tmp3_1;
  char _tmp4_1;
  char _tmp5_1;
  char _tmp6_1;
  char _tmp7_1;
  char _tmp8_1;
  char _tmp9_1;
  char _tmp10_1;
  
  mbx++;
  left_edge = 0;
  _tmp1_1 = mbx;
  _tmp2_1 = mbwidth;
  if (_tmp1_1 == _tmp2_1) {
    mbx = 0;
    top_edge = 0;
    left_edge = 1;
  }
  _tmp3_1 = ptr;
  _tmp4_1 = decrement(_tmp3_1);
  ptr = _tmp4_1;
  _tmp5_1 = ptr_left;
  _tmp6_1 = decrement(_tmp5_1);
  ptr_left = _tmp6_1;
  _tmp7_1 = ptr_above;
  _tmp8_1 = decrement(_tmp7_1);
  ptr_above = _tmp8_1;
  _tmp9_1 = ptr_above_left;
  _tmp10_1 = decrement(_tmp9_1);
  ptr_above_left = _tmp10_1;
}

static int isSchedulable_advance() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void predict_b0() {
  char *C;
  char *B;
  char *A;
  short a0_1;
  short b0_1;
  short c0_1;
  int _tmp0_1;
  char _tmp1_1;
  short a0_2;
  short a0_3;
  int _tmp2_1;
  short a0_4;
  short a0_5;
  int _tmp3_1;
  char _tmp4_1;
  short b0_2;
  short b0_3;
  int _tmp5_1;
  short b0_4;
  short b0_5;
  short b0_6;
  int _tmp6_1;
  char _tmp7_1;
  short c0_2;
  short c0_3;
  int _tmp8_1;
  short c0_4;
  short c0_5;
  
  A = getWritePtr(addressing_A, 1);
  B = getWritePtr(addressing_B, 1);
  C = getWritePtr(addressing_C, 1);
  a0_1 = 0;
  b0_1 = 0;
  c0_1 = 0;
  _tmp0_1 = left_edge;
  if (!_tmp0_1) {
    _tmp1_1 = ptr_left;
    a0_2 = _tmp1_1;
    _tmp2_1 = coded[a0_2];
    if (!_tmp2_1) {
      a0_4 = 0;
      a0_5 = a0_4;
    } else {
      a0_5 = a0_2;
    }
    _tmp3_1 = top_edge;
    if (!_tmp3_1) {
      _tmp4_1 = ptr_above_left;
      b0_2 = _tmp4_1;
      _tmp5_1 = coded[b0_2];
      if (!_tmp5_1) {
        b0_4 = 0;
        b0_5 = b0_4;
      } else {
        b0_5 = b0_2;
      }
      b0_3 = b0_5;
    } else {
      b0_3 = b0_1;
    }
    a0_3 = a0_5;
    b0_6 = b0_3;
  } else {
    a0_3 = a0_1;
    b0_6 = b0_1;
  }
  _tmp6_1 = top_edge;
  if (!_tmp6_1) {
    _tmp7_1 = ptr_above;
    c0_2 = _tmp7_1;
    _tmp8_1 = coded[c0_2];
    if (!_tmp8_1) {
      c0_4 = 0;
      c0_5 = c0_4;
    } else {
      c0_5 = c0_2;
    }
    c0_3 = c0_5;
  } else {
    c0_3 = c0_1;
  }
  A[0] = a0_3;
  B[0] = b0_6;
  C[0] = c0_3;
}

static int isSchedulable_predict_b0() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_advance = 0,
  s_geth,
  s_getw,
  s_predict,
  s_read
};

static char *stateNames[] = {
  "s_advance",
  "s_geth",
  "s_getw",
  "s_predict",
  "s_read"
};

static enum states _FSM_state = s_read;

static int advance_state_scheduler() {
  int res;
  
  if (isSchedulable_advance()) {
    advance();
    _FSM_state = s_read;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_geth()) {
    geth();
    _FSM_state = s_read;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int getw_state_scheduler() {
  int res;
  
  if (isSchedulable_getw()) {
    getw_();
    _FSM_state = s_geth;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int predict_state_scheduler() {
  int res;
  
  if (isSchedulable_predict_b0()) {
    if (hasRoom(addressing_A, 1) && hasRoom(addressing_B, 1) && hasRoom(addressing_C, 1)) {
      predict_b0();
      _FSM_state = s_advance;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int read_state_scheduler() {
  int res;
  
  if (isSchedulable_start()) {
    start();
    _FSM_state = s_getw;
    res = 1;
  } else {
    if (isSchedulable_read_intra()) {
      read_intra();
      _FSM_state = s_predict;
      res = 1;
    } else {
      if (isSchedulable_read_other()) {
        read_other();
        _FSM_state = s_advance;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

int addressing_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_advance:
        res = advance_state_scheduler();
        break;
      case s_geth:
        res = geth_state_scheduler();
        break;
      case s_getw:
        res = getw_state_scheduler();
        break;
      case s_predict:
        res = predict_state_scheduler();
        break;
      case s_read:
        res = read_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

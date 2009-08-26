// Generated from "mvseq"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *mvseq_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *mvseq_A;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MAXW_IN_MB = 121;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int NEWVOP = 2048;
static int INTER = 512;
static int MOTION = 8;
static int FOURMV = 4;
static char mbx = 0;
static int top_edge = 1;
static int right_edge = 0;
static char comp = 0;
static char mbwidth = 0;
static int BUF_SIZE = 121;
static char ptr;
static char ptr_left;
static char ptr_above;
static char ptr_above_right;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static char decrement(char p) {
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  if (p == 1) {
    _tmp0_1 = 120;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = p - 1;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static short access(char mbptr, char c) {
  return mbptr << 3 | c & 3;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void start() {
  short *BTYPE;
  
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  mbx = 0;
  top_edge = 1;
  right_edge = 0;
  comp = 0;
}

static int isSchedulable_start() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvseq_BTYPE, 1);
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
  
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  w_1 = BTYPE[0];
  mbwidth = w_1;
  ptr = 1;
  ptr_left = 2;
  ptr_above = w_1 + 1;
  ptr_above_right = w_1;
}

static int isSchedulable_getw() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
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
  
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
}

static int isSchedulable_geth() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_noPredict() {
  short *BTYPE;
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
  int _tmp11_1;
  char _tmp12_1;
  char _tmp13_1;
  
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  comp++;
  _tmp1_1 = comp;
  if (_tmp1_1 == 6) {
    comp = 0;
    _tmp2_1 = mbx;
    mbx = _tmp2_1 + 1;
    _tmp3_1 = ptr;
    _tmp4_1 = decrement(_tmp3_1);
    ptr = _tmp4_1;
    _tmp5_1 = ptr_left;
    _tmp6_1 = decrement(_tmp5_1);
    ptr_left = _tmp6_1;
    _tmp7_1 = ptr_above;
    _tmp8_1 = decrement(_tmp7_1);
    ptr_above = _tmp8_1;
    _tmp9_1 = ptr_above_right;
    _tmp10_1 = decrement(_tmp9_1);
    ptr_above_right = _tmp10_1;
    _tmp11_1 = right_edge;
    if (_tmp11_1) {
      mbx = 0;
      right_edge = 0;
      top_edge = 0;
    } else {
      _tmp12_1 = mbx;
      _tmp13_1 = mbwidth;
      if (_tmp12_1 == _tmp13_1 - 1) {
        right_edge = 1;
      }
    }
  }
}

static int isSchedulable_read_noPredict() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  char _tmp3_1;
  char _tmp6_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvseq_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp3_1 = comp;
    _tmp6_1 = comp;
    _tmp0_1 = (cmd_1 & 2048) == 0 && (_tmp3_1 > 3 || (cmd_1 & 512) == 0 || (cmd_1 & 8) == 0 || _tmp6_1 != 0 && (cmd_1 & 4) == 0);
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_predict_y0() {
  short *A;
  short *BTYPE;
  char _tmp0_1;
  char pl0_1;
  char pl0_2;
  char _tmp1_1;
  char pl0_3;
  int _tmp2_1;
  char pa0_1;
  char pa0_2;
  char _tmp3_1;
  char pa0_3;
  int _tmp4_1;
  int _tmp5_1;
  char par0_1;
  char par0_2;
  char _tmp6_1;
  char par0_3;
  short _tmp8_1;
  short _tmp9_1;
  short _tmp10_1;
  
  A = getWritePtr(mvseq_A, 3);
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  _tmp0_1 = mbx;
  if (_tmp0_1 == 0) {
    pl0_1 = 0;
    pl0_2 = pl0_1;
  } else {
    _tmp1_1 = ptr_left;
    pl0_3 = _tmp1_1;
    pl0_2 = pl0_3;
  }
  _tmp2_1 = top_edge;
  if (_tmp2_1) {
    pa0_1 = 0;
    pa0_2 = pa0_1;
  } else {
    _tmp3_1 = ptr_above;
    pa0_3 = _tmp3_1;
    pa0_2 = pa0_3;
  }
  _tmp4_1 = top_edge;
  _tmp5_1 = right_edge;
  if (_tmp4_1 || _tmp5_1) {
    par0_1 = 0;
    par0_2 = par0_1;
  } else {
    _tmp6_1 = ptr_above_right;
    par0_3 = _tmp6_1;
    par0_2 = par0_3;
  }
  comp++;
  _tmp8_1 = access(pl0_2, 1);
  _tmp9_1 = access(pa0_2, 2);
  _tmp10_1 = access(par0_2, 2);
  A[0] = _tmp8_1;
  A[1] = _tmp9_1;
  A[2] = _tmp10_1;
}

static int isSchedulable_read_predict_y0() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  char _tmp5_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvseq_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp5_1 = comp;
    _tmp0_1 = (cmd_1 & 2048) == 0 && (cmd_1 & 512) != 0 && (cmd_1 & 8) != 0 && _tmp5_1 == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_predict_y1() {
  short *A;
  short *BTYPE;
  int _tmp0_1;
  char pa0_1;
  char pa0_2;
  char _tmp1_1;
  char pa0_3;
  int _tmp2_1;
  int _tmp3_1;
  char par0_1;
  char par0_2;
  char _tmp4_1;
  char par0_3;
  char _tmp6_1;
  short _tmp7_1;
  short _tmp8_1;
  short _tmp9_1;
  
  A = getWritePtr(mvseq_A, 3);
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  _tmp0_1 = top_edge;
  if (_tmp0_1) {
    pa0_1 = 0;
    pa0_2 = pa0_1;
  } else {
    _tmp1_1 = ptr_above;
    pa0_3 = _tmp1_1;
    pa0_2 = pa0_3;
  }
  _tmp2_1 = top_edge;
  _tmp3_1 = right_edge;
  if (_tmp2_1 || _tmp3_1) {
    par0_1 = 0;
    par0_2 = par0_1;
  } else {
    _tmp4_1 = ptr_above_right;
    par0_3 = _tmp4_1;
    par0_2 = par0_3;
  }
  comp++;
  _tmp6_1 = ptr;
  _tmp7_1 = access(_tmp6_1, 0);
  _tmp8_1 = access(pa0_2, 3);
  _tmp9_1 = access(par0_2, 2);
  A[0] = _tmp7_1;
  A[1] = _tmp8_1;
  A[2] = _tmp9_1;
}

static int isSchedulable_read_predict_y1() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  char _tmp6_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvseq_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp6_1 = comp;
    _tmp0_1 = (cmd_1 & 2048) == 0 && (cmd_1 & 512) != 0 && (cmd_1 & 8) != 0 && (cmd_1 & 4) != 0 && _tmp6_1 == 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_predict_y2() {
  short *A;
  short *BTYPE;
  char _tmp0_1;
  char pl0_1;
  char pl0_2;
  char _tmp1_1;
  char pl0_3;
  short _tmp3_1;
  char _tmp4_1;
  short _tmp5_1;
  char _tmp6_1;
  short _tmp7_1;
  
  A = getWritePtr(mvseq_A, 3);
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  _tmp0_1 = mbx;
  if (_tmp0_1 == 0) {
    pl0_1 = 0;
    pl0_2 = pl0_1;
  } else {
    _tmp1_1 = ptr_left;
    pl0_3 = _tmp1_1;
    pl0_2 = pl0_3;
  }
  comp++;
  _tmp3_1 = access(pl0_2, 3);
  _tmp4_1 = ptr;
  _tmp5_1 = access(_tmp4_1, 0);
  _tmp6_1 = ptr;
  _tmp7_1 = access(_tmp6_1, 1);
  A[0] = _tmp3_1;
  A[1] = _tmp5_1;
  A[2] = _tmp7_1;
}

static int isSchedulable_read_predict_y2() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  char _tmp6_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvseq_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp6_1 = comp;
    _tmp0_1 = (cmd_1 & 2048) == 0 && (cmd_1 & 512) != 0 && (cmd_1 & 8) != 0 && (cmd_1 & 4) != 0 && _tmp6_1 == 2;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_predict_y3() {
  short *A;
  short *BTYPE;
  char _tmp1_1;
  short _tmp2_1;
  char _tmp3_1;
  short _tmp4_1;
  char _tmp5_1;
  short _tmp6_1;
  
  A = getWritePtr(mvseq_A, 3);
  BTYPE = getReadPtr(mvseq_BTYPE, 1);
  comp++;
  _tmp1_1 = ptr;
  _tmp2_1 = access(_tmp1_1, 2);
  _tmp3_1 = ptr;
  _tmp4_1 = access(_tmp3_1, 0);
  _tmp5_1 = ptr;
  _tmp6_1 = access(_tmp5_1, 1);
  A[0] = _tmp2_1;
  A[1] = _tmp4_1;
  A[2] = _tmp6_1;
}

static int isSchedulable_read_predict_y3() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  char _tmp6_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvseq_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvseq_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp6_1 = comp;
    _tmp0_1 = (cmd_1 & 2048) == 0 && (cmd_1 & 512) != 0 && (cmd_1 & 8) != 0 && (cmd_1 & 4) != 0 && _tmp6_1 == 3;
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
  s_geth = 0,
  s_getw,
  s_read
};

static char *stateNames[] = {
  "s_geth",
  "s_getw",
  "s_read"
};

static enum states _FSM_state = s_read;

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

static int read_state_scheduler() {
  int res;
  
  if (isSchedulable_start()) {
    start();
    _FSM_state = s_getw;
    res = 1;
  } else {
    if (isSchedulable_read_noPredict()) {
      read_noPredict();
      _FSM_state = s_read;
      res = 1;
    } else {
      if (isSchedulable_read_predict_y0()) {
        if (hasRoom(mvseq_A, 3)) {
          read_predict_y0();
          _FSM_state = s_read;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        if (isSchedulable_read_predict_y1()) {
          if (hasRoom(mvseq_A, 3)) {
            read_predict_y1();
            _FSM_state = s_read;
            res = 1;
          } else {
            res = 0;
          }
        } else {
          if (isSchedulable_read_predict_y2()) {
            if (hasRoom(mvseq_A, 3)) {
              read_predict_y2();
              _FSM_state = s_read;
              res = 1;
            } else {
              res = 0;
            }
          } else {
            if (isSchedulable_read_predict_y3()) {
              if (hasRoom(mvseq_A, 3)) {
                read_predict_y3();
                _FSM_state = s_read;
                res = 1;
              } else {
                res = 0;
              }
            } else {
              res = 0;
            }
          }
        }
      }
    }
  }
  
  return res;
}

int mvseq_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_geth:
        res = geth_state_scheduler();
        break;
      case s_getw:
        res = getw_state_scheduler();
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

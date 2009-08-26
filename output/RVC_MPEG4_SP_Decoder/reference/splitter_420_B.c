// Generated from "splitter_420_B"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *splitter_420_B_B;
extern struct fifo_s *splitter_420_B_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *splitter_420_B_Y;
extern struct fifo_s *splitter_420_B_U;
extern struct fifo_s *splitter_420_B_V;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int BLOCK_SZ = 64;
static int SAMPLE_SZ = 13;
static int BTYPE_SZ = 12;
static int NEWVOP = 2048;
static int INTRA = 1024;
static int INTER = 512;
static int ACCODED = 2;
static char comp = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void start() {
  short *BTYPE;
  
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
}

static int isSchedulable_start() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(splitter_420_B_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2048) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void consume() {
  short *BTYPE;
  
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
}

static int isSchedulable_consume() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void skip_uncoded() {
  short *BTYPE;
  char _tmp1_1;
  
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
  comp++;
  _tmp1_1 = comp;
  if (_tmp1_1 == 6) {
    comp = 0;
  }
}

static int isSchedulable_skip_uncoded() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(splitter_420_B_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 512) != 0 && (cmd_1 & 2) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void skip_other() {
  short *BTYPE;
  char _tmp1_1;
  
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
  comp++;
  _tmp1_1 = comp;
  if (_tmp1_1 == 6) {
    comp = 0;
  }
}

static int isSchedulable_skip_other() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(splitter_420_B_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 512) == 0 && (cmd_1 & 1024) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void splitY() {
  short *Y;
  short *BTYPE;
  short *B;
  short x[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp2_1;
  int _i1_2;
  int _i1_3;
  
  Y = getWritePtr(splitter_420_B_Y, 64);
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
  B = getReadPtr(splitter_420_B_B, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = B[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  comp++;
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp2_1 = x[_i1_3];
    Y[1 * _i1_3 + 0] = _tmp2_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_splitY() {
  short x[64];
  short *B;
  short *BTYPE;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  char _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  _tmp2_1 = hasTokens(splitter_420_B_B, 64);
  if (_tmp1_1 && _tmp2_1) {
    BTYPE = getPeekPtr(splitter_420_B_BTYPE, 1);
    B = getPeekPtr(splitter_420_B_B, 64);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 64) {
      _tmp3_1 = B[1 * _i0_4 + 0];
      x[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    _tmp4_1 = comp;
    _tmp0_1 = _tmp4_1 < 4;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void splitU() {
  short *U;
  short *BTYPE;
  short *B;
  short x[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp2_1;
  int _i1_2;
  int _i1_3;
  
  U = getWritePtr(splitter_420_B_U, 64);
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
  B = getReadPtr(splitter_420_B_B, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = B[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  comp++;
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp2_1 = x[_i1_3];
    U[1 * _i1_3 + 0] = _tmp2_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_splitU() {
  short x[64];
  short *B;
  short *BTYPE;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  char _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  _tmp2_1 = hasTokens(splitter_420_B_B, 64);
  if (_tmp1_1 && _tmp2_1) {
    BTYPE = getPeekPtr(splitter_420_B_BTYPE, 1);
    B = getPeekPtr(splitter_420_B_B, 64);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 64) {
      _tmp3_1 = B[1 * _i0_4 + 0];
      x[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    _tmp4_1 = comp;
    _tmp0_1 = _tmp4_1 == 4;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void splitV() {
  short *V;
  short *BTYPE;
  short *B;
  short x[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  
  V = getWritePtr(splitter_420_B_V, 64);
  BTYPE = getReadPtr(splitter_420_B_BTYPE, 1);
  B = getReadPtr(splitter_420_B_B, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = B[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  comp = 0;
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp1_1 = x[_i1_3];
    V[1 * _i1_3 + 0] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_splitV() {
  short x[64];
  short *B;
  short *BTYPE;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  char _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_420_B_BTYPE, 1);
  _tmp2_1 = hasTokens(splitter_420_B_B, 64);
  if (_tmp1_1 && _tmp2_1) {
    BTYPE = getPeekPtr(splitter_420_B_BTYPE, 1);
    B = getPeekPtr(splitter_420_B_B, 64);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 64) {
      _tmp3_1 = B[1 * _i0_4 + 0];
      x[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    _tmp4_1 = comp;
    _tmp0_1 = _tmp4_1 == 5;
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
  s_GETH = 0,
  s_GETW,
  s_READ
};

static char *stateNames[] = {
  "s_GETH",
  "s_GETW",
  "s_READ"
};

static enum states _FSM_state = s_READ;

static int GETH_state_scheduler() {
  int res;
  
  if (isSchedulable_consume()) {
    consume();
    _FSM_state = s_READ;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int GETW_state_scheduler() {
  int res;
  
  if (isSchedulable_consume()) {
    consume();
    _FSM_state = s_GETH;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int READ_state_scheduler() {
  int res;
  
  if (isSchedulable_start()) {
    start();
    _FSM_state = s_GETW;
    res = 1;
  } else {
    if (isSchedulable_skip_uncoded()) {
      skip_uncoded();
      _FSM_state = s_READ;
      res = 1;
    } else {
      if (isSchedulable_skip_other()) {
        skip_other();
        _FSM_state = s_READ;
        res = 1;
      } else {
        if (isSchedulable_splitY()) {
          if (hasRoom(splitter_420_B_Y, 64)) {
            splitY();
            _FSM_state = s_READ;
            res = 1;
          } else {
            res = 0;
          }
        } else {
          if (isSchedulable_splitU()) {
            if (hasRoom(splitter_420_B_U, 64)) {
              splitU();
              _FSM_state = s_READ;
              res = 1;
            } else {
              res = 0;
            }
          } else {
            if (isSchedulable_splitV()) {
              if (hasRoom(splitter_420_B_V, 64)) {
                splitV();
                _FSM_state = s_READ;
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

int splitter_420_B_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_GETH:
        res = GETH_state_scheduler();
        break;
      case s_GETW:
        res = GETW_state_scheduler();
        break;
      case s_READ:
        res = READ_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

// Generated from "interpolation_01"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *interpolation_01_RD;
extern struct fifo_s *interpolation_01_halfpel;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *interpolation_01_MOT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int PIX_SZ = 9;
static int FLAG_SZ = 4;
static int _CAL_tokenMonitor = 1;
static char x;
static char y;
static char flags;
static char round;
static short d0;
static short d1;
static short d2;
static short d3;
static short d4;
static short d5;
static short d6;
static short d7;
static short d8;
static short d9;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int compensate(int p00, int p10, int p01, int p11) {
  char _tmp0_1;
  int _tmp1_1;
  int _tmp1_2;
  char _tmp2_1;
  char _tmp3_1;
  int _tmp1_3;
  int _tmp1_4;
  char _tmp4_1;
  char _tmp5_1;
  int _tmp1_5;
  int _tmp1_6;
  char _tmp6_1;
  int _tmp1_7;
  
  _tmp0_1 = flags;
  if (_tmp0_1 == 0) {
    _tmp1_1 = p00;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp2_1 = flags;
    if (_tmp2_1 == 1) {
      _tmp3_1 = round;
      _tmp1_3 = (p00 + p01 + 1 - _tmp3_1) >> 1;
      _tmp1_4 = _tmp1_3;
    } else {
      _tmp4_1 = flags;
      if (_tmp4_1 == 2) {
        _tmp5_1 = round;
        _tmp1_5 = (p00 + p10 + 1 - _tmp5_1) >> 1;
        _tmp1_6 = _tmp1_5;
      } else {
        _tmp6_1 = round;
        _tmp1_7 = (p00 + p10 + p01 + p11 + 2 - _tmp6_1) >> 2;
        _tmp1_6 = _tmp1_7;
      }
      _tmp1_4 = _tmp1_6;
    }
    _tmp1_2 = _tmp1_4;
  }
  return _tmp1_2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void start() {
  char *halfpel;
  char f_1;
  
  halfpel = getReadPtr(interpolation_01_halfpel, 1);
  f_1 = halfpel[0];
  x = 0;
  y = 0;
  flags = f_1 >> 1;
  round = f_1 & 1;
}

static int isSchedulable_start() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(interpolation_01_halfpel, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void done() {
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = y;
  _tmp0_1 = _tmp1_1 == 9;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void row_col_0() {
  short *RD;
  short d_1;
  short _tmp0_1;
  short _tmp1_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  short _tmp6_1;
  short _tmp7_1;
  short _tmp8_1;
  char _tmp10_1;
  char _tmp11_1;
  
  RD = getReadPtr(interpolation_01_RD, 1);
  d_1 = RD[0];
  _tmp0_1 = d8;
  d9 = _tmp0_1;
  _tmp1_1 = d7;
  d8 = _tmp1_1;
  _tmp2_1 = d6;
  d7 = _tmp2_1;
  _tmp3_1 = d5;
  d6 = _tmp3_1;
  _tmp4_1 = d4;
  d5 = _tmp4_1;
  _tmp5_1 = d3;
  d4 = _tmp5_1;
  _tmp6_1 = d2;
  d3 = _tmp6_1;
  _tmp7_1 = d1;
  d2 = _tmp7_1;
  _tmp8_1 = d0;
  d1 = _tmp8_1;
  d0 = d_1;
  x++;
  _tmp10_1 = x;
  if (_tmp10_1 >= 9) {
    x = 0;
    _tmp11_1 = y;
    y = _tmp11_1 + 1;
  }
}

static int isSchedulable_row_col_0() {
  short *RD;
  int _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(interpolation_01_RD, 1);
  if (_tmp1_1) {
    RD = getPeekPtr(interpolation_01_RD, 1);
    _tmp2_1 = x;
    _tmp3_1 = y;
    _tmp0_1 = _tmp2_1 == 0 || _tmp3_1 == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void other() {
  short *MOT;
  short *RD;
  short d_1;
  short _tmp0_1;
  short _tmp1_1;
  short _tmp2_1;
  int p0_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  short _tmp6_1;
  short _tmp7_1;
  short _tmp8_1;
  short _tmp9_1;
  short _tmp10_1;
  short _tmp11_1;
  char _tmp13_1;
  char _tmp14_1;
  
  MOT = getWritePtr(interpolation_01_MOT, 1);
  RD = getReadPtr(interpolation_01_RD, 1);
  d_1 = RD[0];
  _tmp0_1 = d9;
  _tmp1_1 = d8;
  _tmp2_1 = d0;
  p0_1 = compensate(_tmp0_1, _tmp1_1, _tmp2_1, d_1);
  _tmp3_1 = d8;
  d9 = _tmp3_1;
  _tmp4_1 = d7;
  d8 = _tmp4_1;
  _tmp5_1 = d6;
  d7 = _tmp5_1;
  _tmp6_1 = d5;
  d6 = _tmp6_1;
  _tmp7_1 = d4;
  d5 = _tmp7_1;
  _tmp8_1 = d3;
  d4 = _tmp8_1;
  _tmp9_1 = d2;
  d3 = _tmp9_1;
  _tmp10_1 = d1;
  d2 = _tmp10_1;
  _tmp11_1 = d0;
  d1 = _tmp11_1;
  d0 = d_1;
  x++;
  _tmp13_1 = x;
  if (_tmp13_1 >= 9) {
    x = 0;
    _tmp14_1 = y;
    y = _tmp14_1 + 1;
  }
  MOT[0] = p0_1;
}

static int isSchedulable_other() {
  short *RD;
  int _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  char _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(interpolation_01_RD, 1);
  if (_tmp1_1) {
    RD = getPeekPtr(interpolation_01_RD, 1);
    _tmp2_1 = x;
    _tmp3_1 = y;
    _tmp4_1 = y;
    _tmp0_1 = _tmp2_1 != 0 && _tmp3_1 != 0 && _tmp4_1 != 9;
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
  s_interpolate = 0,
  s_start
};

static char *stateNames[] = {
  "s_interpolate",
  "s_start"
};

static enum states _FSM_state = s_start;

static int interpolate_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_start;
    res = 1;
  } else {
    if (isSchedulable_row_col_0()) {
      row_col_0();
      _FSM_state = s_interpolate;
      res = 1;
    } else {
      if (isSchedulable_other()) {
        if (hasRoom(interpolation_01_MOT, 1)) {
          other();
          _FSM_state = s_interpolate;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int start_state_scheduler() {
  int res;
  
  if (isSchedulable_start()) {
    start();
    _FSM_state = s_interpolate;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

int interpolation_01_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_interpolate:
        res = interpolate_state_scheduler();
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

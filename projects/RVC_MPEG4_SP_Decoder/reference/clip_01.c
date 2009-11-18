// Generated from "clip_01"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *clip_01_I;
extern struct fifo_s *clip_01_SIGNED;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *clip_01_O;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SAMPLE_SZ = 13;
static int osz = 9;
static int sflag;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void read_signed() {
  int *SIGNED;
  int s_1;
  
  SIGNED = getReadPtr(clip_01_SIGNED, 1);
  s_1 = SIGNED[0];
  sflag = s_1;
}

static int isSchedulable_read_signed() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(clip_01_SIGNED, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void clip() {
  short _tmp1[64];
  short *O;
  short *I;
  short x[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int i0_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp3_2;
  int _tmp4_1;
  int _tmp4_2;
  short _tmp5_1;
  short _tmp5_2;
  short _tmp3_3;
  short _tmp3_4;
  short _tmp6_1;
  short _tmp6_2;
  short _tmp3_5;
  short _tmp3_6;
  short _tmp7_1;
  short _tmp7_2;
  short _tmp3_7;
  short _tmp7_3;
  short _tmp6_3;
  short _tmp7_4;
  int _tmp4_3;
  short _tmp5_3;
  short _tmp6_4;
  short _tmp7_5;
  int i0_2;
  int i0_3;
  int _i1_1;
  short _tmp8_1;
  int _i1_2;
  int _i1_3;
  
  O = getWritePtr(clip_01_O, 64);
  I = getReadPtr(clip_01_I, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = I[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  i0_1 = 0;
  _tmp4_3 = 0;
  _tmp5_3 = 0;
  _tmp6_4 = 0;
  _tmp7_5 = 0;
  i0_3 = i0_1;
  while (i0_3 < 64) {
    _tmp2_1 = x[i0_3];
    if (_tmp2_1 > 255) {
      _tmp3_1 = 255;
      _tmp3_2 = _tmp3_1;
      _tmp4_2 = _tmp4_3;
      _tmp5_2 = _tmp5_3;
      _tmp6_3 = _tmp6_4;
      _tmp7_4 = _tmp7_5;
    } else {
      _tmp4_1 = sflag;
      _tmp5_1 = x[i0_3];
      if (!_tmp4_1 && _tmp5_1 < 0) {
        _tmp3_3 = 0;
        _tmp3_4 = _tmp3_3;
        _tmp6_2 = _tmp6_4;
        _tmp7_3 = _tmp7_5;
      } else {
        _tmp6_1 = x[i0_3];
        if (_tmp6_1 < -255) {
          _tmp3_5 = -255;
          _tmp3_6 = _tmp3_5;
          _tmp7_2 = _tmp7_5;
        } else {
          _tmp7_1 = x[i0_3];
          _tmp3_7 = _tmp7_1;
          _tmp3_6 = _tmp3_7;
          _tmp7_2 = _tmp7_1;
        }
        _tmp3_4 = _tmp3_6;
        _tmp6_2 = _tmp6_1;
        _tmp7_3 = _tmp7_2;
      }
      _tmp3_2 = _tmp3_4;
      _tmp4_2 = _tmp4_1;
      _tmp5_2 = _tmp5_1;
      _tmp6_3 = _tmp6_2;
      _tmp7_4 = _tmp7_3;
    }
    _tmp1[i0_3] = _tmp3_2;
    i0_2 = i0_3 + 1;
    _tmp4_3 = _tmp4_2;
    _tmp5_3 = _tmp5_2;
    _tmp6_4 = _tmp6_3;
    _tmp7_5 = _tmp7_4;
    i0_3 = i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp8_1 = _tmp1[_i1_3];
    O[1 * _i1_3 + 0] = _tmp8_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_clip() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(clip_01_I, 64);
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
  s_clip = 0,
  s_set_sign
};

static char *stateNames[] = {
  "s_clip",
  "s_set_sign"
};

static enum states _FSM_state = s_set_sign;

static int clip_state_scheduler() {
  int res;
  
  if (isSchedulable_clip()) {
    if (hasRoom(clip_01_O, 64)) {
      clip();
      _FSM_state = s_set_sign;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int set_sign_state_scheduler() {
  int res;
  
  if (isSchedulable_read_signed()) {
    read_signed();
    _FSM_state = s_clip;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

int clip_01_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_clip:
        res = clip_state_scheduler();
        break;
      case s_set_sign:
        res = set_sign_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

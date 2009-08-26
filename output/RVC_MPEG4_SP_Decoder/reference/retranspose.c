// Generated from "retranspose"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *retranspose_X;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *retranspose_Y;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 13;
static int osz = 13;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short _tmp1[64];
  short *Y;
  short *X;
  short x[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int r0_1;
  int c0_1;
  short _tmp2_1;
  short _tmp2_2;
  int c0_3;
  int c0_4;
  short _tmp2_3;
  int r0_2;
  int r0_3;
  int _i1_1;
  short _tmp3_1;
  int _i1_2;
  int _i1_3;
  
  Y = getWritePtr(retranspose_Y, 64);
  X = getReadPtr(retranspose_X, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = X[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  r0_1 = 0;
  _tmp2_3 = 0;
  r0_3 = r0_1;
  while (r0_3 < 8) {
    c0_1 = 0;
    _tmp2_2 = _tmp2_3;
    c0_4 = c0_1;
    while (c0_4 < 8) {
      _tmp2_1 = x[8 * c0_4 + r0_3];
      _tmp1[r0_3 * 8 + c0_4] = _tmp2_1;
      c0_3 = c0_4 + 1;
      _tmp2_2 = _tmp2_1;
      c0_4 = c0_3;
    }
    r0_2 = r0_3 + 1;
    _tmp2_3 = _tmp2_2;
    r0_3 = r0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp3_1 = _tmp1[_i1_3];
    Y[1 * _i1_3 + 0] = _tmp3_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(retranspose_X, 64);
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

int retranspose_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(retranspose_Y, 64)) {
        untagged01();
        res = 1;
      } else {
        res = 0;
      }
    } else {
      res = 0;
    }
  }
  
  return 0;
}

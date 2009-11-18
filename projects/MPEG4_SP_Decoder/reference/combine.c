// Generated from "combine"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *combine_X0;
extern struct fifo_s *combine_X1;
extern struct fifo_s *combine_X2;
extern struct fifo_s *combine_X3;
extern struct fifo_s *combine_ROW;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *combine_Y0;
extern struct fifo_s *combine_Y1;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 30;
static int osz = 24;
static int count = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void row() {
  int *Y1;
  int *Y0;
  int *X0;
  int *X1;
  int *X2;
  int *X3;
  int *ROW;
  int a_1;
  int b_1;
  int c_1;
  int d_1;
  int _tmp0_1;
  int s0_1;
  int o0_1;
  int o0_2;
  int o0_3;
  int y00_1;
  int y10_1;
  int _tmp1_1;
  
  Y0 = getWritePtr(combine_Y0, 1);
  Y1 = getWritePtr(combine_Y1, 1);
  X0 = getReadPtr(combine_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(combine_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(combine_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(combine_X3, 1);
  d_1 = X3[0];
  ROW = getReadPtr(combine_ROW, 1);
  _tmp0_1 = count;
  s0_1 = _tmp0_1 == 0;
  if (s0_1) {
    o0_1 = 128;
    o0_2 = o0_1;
  } else {
    o0_3 = 0;
    o0_2 = o0_3;
  }
  y00_1 = a_1 + d_1 + o0_2;
  y10_1 = b_1 - c_1 + o0_2;
  _tmp1_1 = count;
  count = _tmp1_1 + 1 & 3;
  Y0[0] = y00_1;
  Y1[0] = y10_1;
}

static int isSchedulable_row() {
  int *ROW;
  int *X3;
  int *X2;
  int *X1;
  int *X0;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  int r_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(combine_X0, 1);
  _tmp2_1 = hasTokens(combine_X1, 1);
  _tmp3_1 = hasTokens(combine_X2, 1);
  _tmp4_1 = hasTokens(combine_X3, 1);
  _tmp5_1 = hasTokens(combine_ROW, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
    X0 = getPeekPtr(combine_X0, 1);
    X1 = getPeekPtr(combine_X1, 1);
    X2 = getPeekPtr(combine_X2, 1);
    X3 = getPeekPtr(combine_X3, 1);
    ROW = getPeekPtr(combine_ROW, 1);
    r_1 = ROW[0];
    _tmp0_1 = r_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void col() {
  int *Y1;
  int *Y0;
  int *X0;
  int *X1;
  int *X2;
  int *X3;
  int *ROW;
  int a_1;
  int b_1;
  int c_1;
  int d_1;
  int _tmp0_1;
  int s0_1;
  int o0_1;
  int o0_2;
  int o0_3;
  int y00_1;
  int y10_1;
  int _tmp1_1;
  
  Y0 = getWritePtr(combine_Y0, 1);
  Y1 = getWritePtr(combine_Y1, 1);
  X0 = getReadPtr(combine_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(combine_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(combine_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(combine_X3, 1);
  d_1 = X3[0];
  ROW = getReadPtr(combine_ROW, 1);
  _tmp0_1 = count;
  s0_1 = _tmp0_1 == 0;
  if (s0_1) {
    o0_1 = 65536;
    o0_2 = o0_1;
  } else {
    o0_3 = 4;
    o0_2 = o0_3;
  }
  y00_1 = a_1 + d_1 + o0_2;
  y10_1 = b_1 - c_1 + o0_2;
  _tmp1_1 = count;
  count = _tmp1_1 + 1 & 3;
  Y0[0] = y00_1 >> 3;
  Y1[0] = y10_1 >> 3;
}

static int isSchedulable_col() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(combine_X0, 1);
  _tmp2_1 = hasTokens(combine_X1, 1);
  _tmp3_1 = hasTokens(combine_X2, 1);
  _tmp4_1 = hasTokens(combine_X3, 1);
  _tmp5_1 = hasTokens(combine_ROW, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
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

int combine_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_row()) {
      if (hasRoom(combine_Y0, 1) && hasRoom(combine_Y1, 1)) {
        row();
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_col()) {
        if (hasRoom(combine_Y0, 1) && hasRoom(combine_Y1, 1)) {
          col();
          res = 1;
        } else {
          res = 0;
        }
      } else {
        res = 0;
      }
    }
  }
  
  return 0;
}

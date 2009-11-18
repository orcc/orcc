// Generated from "sep"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *sep_X0;
extern struct fifo_s *sep_X1;
extern struct fifo_s *sep_X2;
extern struct fifo_s *sep_X3;
extern struct fifo_s *sep_ROW;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *sep_R0;
extern struct fifo_s *sep_R1;
extern struct fifo_s *sep_R2;
extern struct fifo_s *sep_R3;
extern struct fifo_s *sep_C0;
extern struct fifo_s *sep_C1;
extern struct fifo_s *sep_C2;
extern struct fifo_s *sep_C3;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 16;
static int rsz = 16;
static int csz = 10;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short *R3;
  short *R2;
  short *R1;
  short *R0;
  short *X0;
  short *X1;
  short *X2;
  short *X3;
  int *ROW;
  short a_1;
  short b_1;
  short c_1;
  short d_1;
  
  R0 = getWritePtr(sep_R0, 1);
  R1 = getWritePtr(sep_R1, 1);
  R2 = getWritePtr(sep_R2, 1);
  R3 = getWritePtr(sep_R3, 1);
  X0 = getReadPtr(sep_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(sep_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(sep_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(sep_X3, 1);
  d_1 = X3[0];
  ROW = getReadPtr(sep_ROW, 1);
  R0[0] = a_1;
  R1[0] = b_1;
  R2[0] = c_1;
  R3[0] = d_1;
}

static int isSchedulable_untagged01() {
  int *ROW;
  short *X3;
  short *X2;
  short *X1;
  short *X0;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  int r_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(sep_X0, 1);
  _tmp2_1 = hasTokens(sep_X1, 1);
  _tmp3_1 = hasTokens(sep_X2, 1);
  _tmp4_1 = hasTokens(sep_X3, 1);
  _tmp5_1 = hasTokens(sep_ROW, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
    X0 = getPeekPtr(sep_X0, 1);
    X1 = getPeekPtr(sep_X1, 1);
    X2 = getPeekPtr(sep_X2, 1);
    X3 = getPeekPtr(sep_X3, 1);
    ROW = getPeekPtr(sep_ROW, 1);
    r_1 = ROW[0];
    _tmp0_1 = r_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void untagged02() {
  short *C3;
  short *C2;
  short *C1;
  short *C0;
  short *X0;
  short *X1;
  short *X2;
  short *X3;
  int *ROW;
  short a_1;
  short b_1;
  short c_1;
  short d_1;
  
  C0 = getWritePtr(sep_C0, 1);
  C1 = getWritePtr(sep_C1, 1);
  C2 = getWritePtr(sep_C2, 1);
  C3 = getWritePtr(sep_C3, 1);
  X0 = getReadPtr(sep_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(sep_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(sep_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(sep_X3, 1);
  d_1 = X3[0];
  ROW = getReadPtr(sep_ROW, 1);
  C0[0] = a_1 >> 6;
  C1[0] = b_1 >> 6;
  C2[0] = c_1 >> 6;
  C3[0] = d_1 >> 6;
}

static int isSchedulable_untagged02() {
  int *ROW;
  short *X3;
  short *X2;
  short *X1;
  short *X0;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  int r_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(sep_X0, 1);
  _tmp2_1 = hasTokens(sep_X1, 1);
  _tmp3_1 = hasTokens(sep_X2, 1);
  _tmp4_1 = hasTokens(sep_X3, 1);
  _tmp5_1 = hasTokens(sep_ROW, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
    X0 = getPeekPtr(sep_X0, 1);
    X1 = getPeekPtr(sep_X1, 1);
    X2 = getPeekPtr(sep_X2, 1);
    X3 = getPeekPtr(sep_X3, 1);
    ROW = getPeekPtr(sep_ROW, 1);
    r_1 = ROW[0];
    _tmp0_1 = !r_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

int sep_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(sep_C0, 1) && hasRoom(sep_C1, 1) && hasRoom(sep_C2, 1) && hasRoom(sep_C3, 1)) {
        untagged01();
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_untagged02()) {
        if (hasRoom(sep_C0, 1) && hasRoom(sep_C1, 1) && hasRoom(sep_C2, 1) && hasRoom(sep_C3, 1)) {
          untagged02();
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

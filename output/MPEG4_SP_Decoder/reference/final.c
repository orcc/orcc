// Generated from "final"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *final_X0;
extern struct fifo_s *final_X1;
extern struct fifo_s *final_X2;
extern struct fifo_s *final_X3;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *final_Y0;
extern struct fifo_s *final_Y1;
extern struct fifo_s *final_Y2;
extern struct fifo_s *final_Y3;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 24;
static int osz = 16;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short *Y3;
  short *Y2;
  short *Y1;
  short *Y0;
  int *X0;
  int *X1;
  int *X2;
  int *X3;
  int a_1;
  int b_1;
  int c_1;
  int d_1;
  
  Y0 = getWritePtr(final_Y0, 1);
  Y1 = getWritePtr(final_Y1, 1);
  Y2 = getWritePtr(final_Y2, 1);
  Y3 = getWritePtr(final_Y3, 1);
  X0 = getReadPtr(final_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(final_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(final_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(final_X3, 1);
  d_1 = X3[0];
  Y0[0] = (a_1 + c_1) >> 8;
  Y1[0] = (a_1 - c_1) >> 8;
  Y2[0] = (b_1 + d_1) >> 8;
  Y3[0] = (b_1 - d_1) >> 8;
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(final_X0, 1);
  _tmp2_1 = hasTokens(final_X1, 1);
  _tmp3_1 = hasTokens(final_X2, 1);
  _tmp4_1 = hasTokens(final_X3, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
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

int final_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(final_Y0, 1) && hasRoom(final_Y1, 1) && hasRoom(final_Y2, 1) && hasRoom(final_Y3, 1)) {
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

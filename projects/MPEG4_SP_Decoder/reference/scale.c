// Generated from "scale"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *scale_X0;
extern struct fifo_s *scale_X1;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *scale_Y0;
extern struct fifo_s *scale_Y1;
extern struct fifo_s *scale_Y2;
extern struct fifo_s *scale_Y3;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 16;
static int osz = 30;
static int csz = 13;
static short W0[4] = {2048, 2676, 2841, 1609};
static short W1[4] = {2048, 1108, 565, 2408};
static short ww0 = 2048;
static short ww1 = 2048;
static int index_ = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  int *Y3;
  int *Y2;
  int *Y1;
  int *Y0;
  short *X0;
  short *X1;
  short a_1;
  short b_1;
  short _tmp0_1;
  short w00_1;
  short _tmp1_1;
  short w10_1;
  int _tmp2_1;
  int _tmp3_1;
  short _tmp4_1;
  int _tmp5_1;
  short _tmp6_1;
  
  Y0 = getWritePtr(scale_Y0, 1);
  Y1 = getWritePtr(scale_Y1, 1);
  Y2 = getWritePtr(scale_Y2, 1);
  Y3 = getWritePtr(scale_Y3, 1);
  X0 = getReadPtr(scale_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(scale_X1, 1);
  b_1 = X1[0];
  _tmp0_1 = ww0;
  w00_1 = _tmp0_1;
  _tmp1_1 = ww1;
  w10_1 = _tmp1_1;
  _tmp2_1 = index_;
  index_ = _tmp2_1 + 1 & 3;
  _tmp3_1 = index_;
  _tmp4_1 = W0[_tmp3_1];
  ww0 = _tmp4_1;
  _tmp5_1 = index_;
  _tmp6_1 = W1[_tmp5_1];
  ww1 = _tmp6_1;
  Y0[0] = a_1 * w00_1;
  Y1[0] = a_1 * w10_1;
  Y2[0] = b_1 * w00_1;
  Y3[0] = b_1 * w10_1;
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(scale_X0, 1);
  _tmp2_1 = hasTokens(scale_X1, 1);
  if (_tmp1_1 && _tmp2_1) {
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

int scale_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(scale_Y0, 1) && hasRoom(scale_Y1, 1) && hasRoom(scale_Y2, 1) && hasRoom(scale_Y3, 1)) {
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

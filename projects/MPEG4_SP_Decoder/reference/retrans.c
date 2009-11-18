// Generated from "retrans"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *retrans_X0;
extern struct fifo_s *retrans_X1;
extern struct fifo_s *retrans_X2;
extern struct fifo_s *retrans_X3;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *retrans_Y;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 10;
static int osz = 10;
static short mem[128] = {0};
static int rcount = 0;
static int ccount = 0;
static int select = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short *Y;
  int _tmp0_1;
  int col0_1;
  int _tmp1_1;
  int row0_1;
  int _tmp2_1;
  int i0_1;
  short _tmp4_1;
  
  Y = getWritePtr(retrans_Y, 1);
  _tmp0_1 = ccount;
  col0_1 = (64 - _tmp0_1) >> 3;
  _tmp1_1 = ccount;
  row0_1 = 64 - _tmp1_1 & 7;
  _tmp2_1 = select;
  i0_1 = _tmp2_1 ^ 1;
  ccount--;
  _tmp4_1 = mem[i0_1 * 64 + row0_1 * 8 + col0_1];
  Y[0] = _tmp4_1;
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = ccount;
  _tmp0_1 = _tmp1_1 > 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void untagged02() {
  short *X0;
  short *X1;
  short *X2;
  short *X3;
  short a_1;
  short b_1;
  short c_1;
  short d_1;
  int _tmp0_1;
  int row0_1;
  int _tmp1_1;
  int quad0_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  int _tmp6_1;
  int _tmp7_1;
  int _tmp8_1;
  int _tmp9_1;
  
  X0 = getReadPtr(retrans_X0, 1);
  a_1 = X0[0];
  X1 = getReadPtr(retrans_X1, 1);
  b_1 = X1[0];
  X2 = getReadPtr(retrans_X2, 1);
  c_1 = X2[0];
  X3 = getReadPtr(retrans_X3, 1);
  d_1 = X3[0];
  _tmp0_1 = rcount;
  row0_1 = _tmp0_1 >> 3;
  _tmp1_1 = rcount;
  quad0_1 = _tmp1_1 >> 2 & 1;
  if (quad0_1 == 0) {
    _tmp2_1 = select;
    mem[_tmp2_1 * 64 + row0_1 * 8 + 0] = a_1;
    _tmp3_1 = select;
    mem[_tmp3_1 * 64 + row0_1 * 8 + 7] = b_1;
    _tmp4_1 = select;
    mem[_tmp4_1 * 64 + row0_1 * 8 + 3] = c_1;
    _tmp5_1 = select;
    mem[_tmp5_1 * 64 + row0_1 * 8 + 4] = d_1;
  } else {
    _tmp6_1 = select;
    mem[_tmp6_1 * 64 + row0_1 * 8 + 1] = a_1;
    _tmp7_1 = select;
    mem[_tmp7_1 * 64 + row0_1 * 8 + 6] = b_1;
    _tmp8_1 = select;
    mem[_tmp8_1 * 64 + row0_1 * 8 + 2] = c_1;
    _tmp9_1 = select;
    mem[_tmp9_1 * 64 + row0_1 * 8 + 5] = d_1;
  }
  rcount += 4;
}

static int isSchedulable_untagged02() {
  short *X3;
  short *X2;
  short *X1;
  short *X0;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp5_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(retrans_X0, 1);
  _tmp2_1 = hasTokens(retrans_X1, 1);
  _tmp3_1 = hasTokens(retrans_X2, 1);
  _tmp4_1 = hasTokens(retrans_X3, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
    X0 = getPeekPtr(retrans_X0, 1);
    X1 = getPeekPtr(retrans_X1, 1);
    X2 = getPeekPtr(retrans_X2, 1);
    X3 = getPeekPtr(retrans_X3, 1);
    _tmp5_1 = rcount;
    _tmp0_1 = _tmp5_1 < 64;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void untagged03() {
  select ^= 1;
  ccount = 64;
  rcount = 0;
}

static int isSchedulable_untagged03() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = ccount;
  _tmp2_1 = rcount;
  _tmp0_1 = _tmp1_1 == 0 && _tmp2_1 == 64;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

int retrans_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      untagged01();
      res = 1;
    } else {
      if (isSchedulable_untagged02()) {
        untagged02();
        res = 1;
      } else {
        if (isSchedulable_untagged03()) {
          untagged03();
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return 0;
}

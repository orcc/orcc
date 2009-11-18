// Generated from "buffer_01"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *buffer_01_RA;
extern struct fifo_s *buffer_01_WA;
extern struct fifo_s *buffer_01_WD;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *buffer_01_RD;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int BUF_SZ = 3322176;
static int ADDR_SZ = 24;
static int PIX_SZ = 9;
static short buf[3322176] = {0};

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short *RD;
  int *RA;
  int a_1;
  short _tmp0_1;
  
  RD = getWritePtr(buffer_01_RD, 1);
  RA = getReadPtr(buffer_01_RA, 1);
  a_1 = RA[0];
  _tmp0_1 = buf[a_1];
  RD[0] = _tmp0_1;
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(buffer_01_RA, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void untagged02() {
  int *WA;
  short *WD;
  int a_1;
  short d_1;
  
  WA = getReadPtr(buffer_01_WA, 1);
  a_1 = WA[0];
  WD = getReadPtr(buffer_01_WD, 1);
  d_1 = WD[0];
  buf[a_1] = d_1;
}

static int isSchedulable_untagged02() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(buffer_01_WA, 1);
  _tmp2_1 = hasTokens(buffer_01_WD, 1);
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

int buffer_01_scheduler() {
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
        res = 0;
      }
    }
  }
  
  return 0;
}

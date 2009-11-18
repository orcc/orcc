// Generated from "DCsplit"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *DCsplit_IN;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *DCsplit_DC;
extern struct fifo_s *DCsplit_AC;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SAMPLE_SZ = 13;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short _tmp2[63];
  short *AC;
  short *DC;
  short *IN;
  short x[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  int i0_1;
  short _tmp3_1;
  int i0_2;
  int i0_3;
  int _i1_1;
  short _tmp4_1;
  int _i1_2;
  int _i1_3;
  
  DC = getWritePtr(DCsplit_DC, 1);
  AC = getWritePtr(DCsplit_AC, 63);
  IN = getReadPtr(DCsplit_IN, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = IN[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _tmp1_1 = x[0];
  DC[0] = _tmp1_1;
  i0_1 = 1;
  i0_3 = i0_1;
  while (i0_3 < 64) {
    _tmp3_1 = x[i0_3];
    _tmp2[i0_3 - 1] = _tmp3_1;
    i0_2 = i0_3 + 1;
    i0_3 = i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 63) {
    _tmp4_1 = _tmp2[_i1_3];
    AC[1 * _i1_3 + 0] = _tmp4_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(DCsplit_IN, 64);
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

int DCsplit_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(DCsplit_AC, 63) && hasRoom(DCsplit_DC, 1)) {
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

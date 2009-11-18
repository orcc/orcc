// Generated from "dcsplit"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *dcsplit_IN;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *dcsplit_DC;
extern struct fifo_s *dcsplit_AC;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SAMPLE_SZ = 13;
static char count = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void dc() {
  short *DC;
  short *IN;
  short x_1;
  
  DC = getWritePtr(dcsplit_DC, 1);
  IN = getReadPtr(dcsplit_IN, 1);
  x_1 = IN[0];
  count = 1;
  DC[0] = x_1;
}

static int isSchedulable_dc() {
  short *IN;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(dcsplit_IN, 1);
  if (_tmp1_1) {
    IN = getPeekPtr(dcsplit_IN, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void ac() {
  short *AC;
  short *IN;
  short x_1;
  char _tmp0_1;
  
  AC = getWritePtr(dcsplit_AC, 1);
  IN = getReadPtr(dcsplit_IN, 1);
  x_1 = IN[0];
  _tmp0_1 = count;
  count = _tmp0_1 + 1 & 63;
  AC[0] = x_1;
}

static int isSchedulable_ac() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(dcsplit_IN, 1);
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

int dcsplit_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_dc()) {
      if (hasRoom(dcsplit_DC, 1)) {
        dc();
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_ac()) {
        if (hasRoom(dcsplit_AC, 1)) {
          ac();
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

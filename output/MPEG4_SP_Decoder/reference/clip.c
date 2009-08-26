// Generated from "clip"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *clip_I;
extern struct fifo_s *clip_SIGNED;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *clip_O;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int isz = 10;
static int osz = 9;
static char count = -1;
static int sflag;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void read_signed() {
  int *SIGNED;
  int s_1;
  
  SIGNED = getReadPtr(clip_SIGNED, 1);
  s_1 = SIGNED[0];
  sflag = s_1;
  count = 63;
}

static int isSchedulable_read_signed() {
  int *SIGNED;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(clip_SIGNED, 1);
  if (_tmp1_1) {
    SIGNED = getPeekPtr(clip_SIGNED, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 < 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void limit() {
  short *O;
  short *I;
  short i_1;
  int _tmp0_1;
  int min0_1;
  int min0_2;
  int min0_3;
  int _tmp2_1;
  int _tmp2_2;
  int _tmp2_3;
  int _tmp2_4;
  int _tmp2_5;
  
  O = getWritePtr(clip_O, 1);
  I = getReadPtr(clip_I, 1);
  i_1 = I[0];
  _tmp0_1 = sflag;
  if (_tmp0_1) {
    min0_1 = -255;
    min0_2 = min0_1;
  } else {
    min0_3 = 0;
    min0_2 = min0_3;
  }
  count--;
  if (i_1 > 255) {
    _tmp2_1 = 255;
    _tmp2_2 = _tmp2_1;
  } else {
    if (i_1 < min0_2) {
      _tmp2_3 = min0_2;
      _tmp2_4 = _tmp2_3;
    } else {
      _tmp2_5 = i_1;
      _tmp2_4 = _tmp2_5;
    }
    _tmp2_2 = _tmp2_4;
  }
  O[0] = _tmp2_2;
}

static int isSchedulable_limit() {
  short *I;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(clip_I, 1);
  if (_tmp1_1) {
    I = getPeekPtr(clip_I, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 >= 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

int clip_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_read_signed()) {
      read_signed();
      res = 1;
    } else {
      if (isSchedulable_limit()) {
        if (hasRoom(clip_O, 1)) {
          limit();
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

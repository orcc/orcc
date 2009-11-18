// Generated from "serialize"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *serialize_in8;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *serialize_out;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int _CAL_tokenMonitor = 1;
static char count = 0;
static short buf;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void reload() {
  char *in8;
  char i_1;
  
  in8 = getReadPtr(serialize_in8, 1);
  i_1 = in8[0];
  buf = i_1;
  count = 8;
}

static int isSchedulable_reload() {
  char *in8;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(serialize_in8, 1);
  if (_tmp1_1) {
    in8 = getPeekPtr(serialize_in8, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void shift() {
  int *out;
  short _tmp0_1;
  int bit0_1;
  
  out = getWritePtr(serialize_out, 1);
  _tmp0_1 = buf;
  bit0_1 = (_tmp0_1 & 128) != 0;
  count--;
  buf <<= 1;
  out[0] = bit0_1;
}

static int isSchedulable_shift() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 != 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

int serialize_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_reload()) {
      reload();
      res = 1;
    } else {
      if (isSchedulable_shift()) {
        if (hasRoom(serialize_out, 1)) {
          shift();
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

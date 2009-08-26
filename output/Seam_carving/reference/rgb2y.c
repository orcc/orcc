// Generated from "rgb2y"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *rgb2y_red;
extern struct fifo_s *rgb2y_green;
extern struct fifo_s *rgb2y_blue;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *rgb2y_y;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor


////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  unsigned char *y;
  unsigned char *red;
  unsigned char *green;
  unsigned char *blue;
  unsigned char r_1;
  unsigned char g_1;
  unsigned char b_1;
  
  y = getWritePtr(rgb2y_y, 1);
  red = getReadPtr(rgb2y_red, 1);
  r_1 = red[0];
  green = getReadPtr(rgb2y_green, 1);
  g_1 = green[0];
  blue = getReadPtr(rgb2y_blue, 1);
  b_1 = blue[0];
  y[0] = ((66 * r_1 + 129 * g_1 + 25 * b_1 + 128) >> 8) + 16;
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(rgb2y_red, 1);
  _tmp2_1 = hasTokens(rgb2y_green, 1);
  _tmp3_1 = hasTokens(rgb2y_blue, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1) {
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

int rgb2y_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(rgb2y_y, 1)) {
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

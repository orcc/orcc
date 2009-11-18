// Generated from "y2rgb"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *y2rgb_y;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *y2rgb_red;
extern struct fifo_s *y2rgb_green;
extern struct fifo_s *y2rgb_blue;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor


////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  unsigned char *blue;
  unsigned char *green;
  unsigned char *red;
  unsigned char *y;
  unsigned char g_1;
  
  red = getWritePtr(y2rgb_red, 1);
  green = getWritePtr(y2rgb_green, 1);
  blue = getWritePtr(y2rgb_blue, 1);
  y = getReadPtr(y2rgb_y, 1);
  g_1 = y[0];
  red[0] = g_1;
  green[0] = g_1;
  blue[0] = g_1;
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(y2rgb_y, 1);
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

int y2rgb_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(y2rgb_blue, 1) && hasRoom(y2rgb_green, 1) && hasRoom(y2rgb_red, 1)) {
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

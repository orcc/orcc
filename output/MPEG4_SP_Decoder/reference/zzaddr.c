// Generated from "zzaddr"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *zzaddr_START;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *zzaddr_ADDR;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static char zigzag[192] = {0, 1, 5, 6, 14, 15, 27, 28, 2, 4, 7, 13, 16, 26, 29, 42, 
3, 8, 12, 17, 25, 30, 41, 43, 9, 11, 18, 24, 31, 40, 44, 53, 10, 19, 23, 32, 39, 
45, 52, 54, 20, 22, 33, 38, 46, 51, 55, 60, 21, 34, 37, 47, 50, 56, 59, 61, 35, 36, 
48, 49, 57, 58, 62, 63, 0, 4, 6, 20, 22, 36, 38, 52, 1, 5, 7, 21, 23, 37, 39, 53, 
2, 8, 19, 24, 34, 40, 50, 54, 3, 9, 18, 25, 35, 41, 51, 55, 10, 17, 26, 30, 42, 46, 
56, 60, 11, 16, 27, 31, 43, 47, 57, 61, 12, 15, 28, 32, 44, 48, 58, 62, 13, 14, 29, 
33, 45, 49, 59, 63, 0, 1, 2, 3, 10, 11, 12, 13, 4, 5, 8, 9, 17, 16, 15, 14, 6, 7, 
19, 18, 26, 27, 28, 29, 20, 21, 24, 25, 30, 31, 32, 33, 22, 23, 34, 35, 42, 43, 44, 
45, 36, 37, 40, 41, 46, 47, 48, 49, 38, 39, 50, 51, 56, 57, 58, 59, 52, 53, 54, 55, 
60, 61, 62, 63};
static short addr;
static char count = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void skip() {
  char *START;
  
  START = getReadPtr(zzaddr_START, 1);
}

static int isSchedulable_skip() {
  char *START;
  int _tmp1_1;
  char i_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zzaddr_START, 1);
  if (_tmp1_1) {
    START = getPeekPtr(zzaddr_START, 1);
    i_1 = START[0];
    _tmp0_1 = i_1 < 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void start() {
  char *START;
  char i_1;
  unsigned char _tmp0_1;
  unsigned char _tmp0_2;
  unsigned char _tmp0_3;
  unsigned char _tmp0_4;
  unsigned char _tmp0_5;
  
  START = getReadPtr(zzaddr_START, 1);
  i_1 = START[0];
  if (i_1 == 0) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    if (i_1 == 1) {
      _tmp0_3 = 65;
      _tmp0_4 = _tmp0_3;
    } else {
      _tmp0_5 = 129;
      _tmp0_4 = _tmp0_5;
    }
    _tmp0_2 = _tmp0_4;
  }
  addr = _tmp0_2;
  count = 63;
}

static int isSchedulable_start() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zzaddr_START, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void done() {
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void zz() {
  char *ADDR;
  short _tmp0_1;
  char _tmp1_1;
  char i0_1;
  
  ADDR = getWritePtr(zzaddr_ADDR, 1);
  _tmp0_1 = addr;
  _tmp1_1 = zigzag[_tmp0_1];
  i0_1 = _tmp1_1;
  addr++;
  count--;
  ADDR[0] = i0_1;
}

static int isSchedulable_zz() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_start = 0,
  s_zz
};

static char *stateNames[] = {
  "s_start",
  "s_zz"
};

static enum states _FSM_state = s_start;

static int start_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_start;
    res = 1;
  } else {
    if (isSchedulable_start()) {
      start();
      _FSM_state = s_zz;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int zz_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_start;
    res = 1;
  } else {
    if (isSchedulable_zz()) {
      if (hasRoom(zzaddr_ADDR, 1)) {
        zz();
        _FSM_state = s_zz;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      res = 0;
    }
  }
  
  return res;
}

int zzaddr_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_start:
        res = start_state_scheduler();
        break;
      case s_zz:
        res = zz_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

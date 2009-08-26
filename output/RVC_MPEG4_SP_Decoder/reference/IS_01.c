// Generated from "IS_01"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *IS_01_AC_PRED_DIR;
extern struct fifo_s *IS_01_QFS_AC;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *IS_01_PQF_AC;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SAMPLE_SZ = 13;
static char Scanmode[192] = {0, 1, 5, 6, 14, 15, 27, 28, 2, 4, 7, 13, 16, 26, 29, 
42, 3, 8, 12, 17, 25, 30, 41, 43, 9, 11, 18, 24, 31, 40, 44, 53, 10, 19, 23, 32, 
39, 45, 52, 54, 20, 22, 33, 38, 46, 51, 55, 60, 21, 34, 37, 47, 50, 56, 59, 61, 35, 
36, 48, 49, 57, 58, 62, 63, 0, 4, 6, 20, 22, 36, 38, 52, 1, 5, 7, 21, 23, 37, 39, 
53, 2, 8, 19, 24, 34, 40, 50, 54, 3, 9, 18, 25, 35, 41, 51, 55, 10, 17, 26, 30, 42, 
46, 56, 60, 11, 16, 27, 31, 43, 47, 57, 61, 12, 15, 28, 32, 44, 48, 58, 62, 13, 14, 
29, 33, 45, 49, 59, 63, 0, 1, 2, 3, 10, 11, 12, 13, 4, 5, 8, 9, 17, 16, 15, 14, 6, 
7, 19, 18, 26, 27, 28, 29, 20, 21, 24, 25, 30, 31, 32, 33, 22, 23, 34, 35, 42, 43, 
44, 45, 36, 37, 40, 41, 46, 47, 48, 49, 38, 39, 50, 51, 56, 57, 58, 59, 52, 53, 54, 
55, 60, 61, 62, 63};
static int BUF_SIZE = 128;
static char count = 1;
static short addr;
static char add_buf;
static short buf[128] = {0};
static int half = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int wa() {
  char _tmp0_1;
  int _tmp1_1;
  unsigned char _tmp2_1;
  unsigned char _tmp2_2;
  unsigned char _tmp2_3;
  
  _tmp0_1 = count;
  _tmp1_1 = half;
  if (_tmp1_1) {
    _tmp2_1 = 64;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp2_3 = 0;
    _tmp2_2 = _tmp2_3;
  }
  return _tmp0_1 & 63 | _tmp2_2;
}

static int ra(int address) {
  int _tmp0_1;
  unsigned char _tmp1_1;
  unsigned char _tmp1_2;
  unsigned char _tmp1_3;
  
  _tmp0_1 = half;
  if (_tmp0_1) {
    _tmp1_1 = 0;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 64;
    _tmp1_2 = _tmp1_3;
  }
  return address & 63 | _tmp1_2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void skip() {
  char *AC_PRED_DIR;
  
  AC_PRED_DIR = getReadPtr(IS_01_AC_PRED_DIR, 1);
}

static int isSchedulable_skip() {
  char *AC_PRED_DIR;
  int _tmp1_1;
  char i_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(IS_01_AC_PRED_DIR, 1);
  if (_tmp1_1) {
    AC_PRED_DIR = getPeekPtr(IS_01_AC_PRED_DIR, 1);
    i_1 = AC_PRED_DIR[0];
    _tmp0_1 = i_1 < 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void start() {
  char *AC_PRED_DIR;
  char i_1;
  
  AC_PRED_DIR = getReadPtr(IS_01_AC_PRED_DIR, 1);
  i_1 = AC_PRED_DIR[0];
  add_buf = i_1;
}

static int isSchedulable_start() {
  char *AC_PRED_DIR;
  int _tmp1_1;
  char i_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(IS_01_AC_PRED_DIR, 1);
  if (_tmp1_1) {
    AC_PRED_DIR = getPeekPtr(IS_01_AC_PRED_DIR, 1);
    i_1 = AC_PRED_DIR[0];
    _tmp0_1 = i_1 >= 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void done() {
  int _tmp0_1;
  char _tmp1_1;
  unsigned char _tmp2_1;
  unsigned char _tmp2_2;
  char _tmp3_1;
  unsigned char _tmp2_3;
  unsigned char _tmp2_4;
  unsigned char _tmp2_5;
  
  count = 1;
  _tmp0_1 = half;
  half = !_tmp0_1;
  _tmp1_1 = add_buf;
  if (_tmp1_1 == 0) {
    _tmp2_1 = 0;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp3_1 = add_buf;
    if (_tmp3_1 == 1) {
      _tmp2_3 = 64;
      _tmp2_4 = _tmp2_3;
    } else {
      _tmp2_5 = 128;
      _tmp2_4 = _tmp2_5;
    }
    _tmp2_2 = _tmp2_4;
  }
  addr = _tmp2_2;
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 == 64;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void read_only() {
  short *QFS_AC;
  short ac_1;
  int _tmp0_1;
  
  QFS_AC = getReadPtr(IS_01_QFS_AC, 1);
  ac_1 = QFS_AC[0];
  _tmp0_1 = wa();
  buf[_tmp0_1] = ac_1;
  count++;
}

static int isSchedulable_read_only() {
  short *QFS_AC;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(IS_01_QFS_AC, 1);
  if (_tmp1_1) {
    QFS_AC = getPeekPtr(IS_01_QFS_AC, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 < 64;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void write_only() {
  short *PQF_AC;
  short _tmp2_1;
  char _tmp3_1;
  int _tmp4_1;
  short _tmp5_1;
  
  PQF_AC = getWritePtr(IS_01_PQF_AC, 1);
  addr++;
  count++;
  _tmp2_1 = addr;
  _tmp3_1 = Scanmode[_tmp2_1];
  _tmp4_1 = ra(_tmp3_1);
  _tmp5_1 = buf[_tmp4_1];
  PQF_AC[0] = _tmp5_1;
}

static int isSchedulable_write_only() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 < 64;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void read_write() {
  short *PQF_AC;
  short *QFS_AC;
  short ac_1;
  int _tmp0_1;
  short _tmp3_1;
  char _tmp4_1;
  int _tmp5_1;
  short _tmp6_1;
  
  PQF_AC = getWritePtr(IS_01_PQF_AC, 1);
  QFS_AC = getReadPtr(IS_01_QFS_AC, 1);
  ac_1 = QFS_AC[0];
  _tmp0_1 = wa();
  buf[_tmp0_1] = ac_1;
  count++;
  addr++;
  _tmp3_1 = addr;
  _tmp4_1 = Scanmode[_tmp3_1];
  _tmp5_1 = ra(_tmp4_1);
  _tmp6_1 = buf[_tmp5_1];
  PQF_AC[0] = _tmp6_1;
}

static int isSchedulable_read_write() {
  short *QFS_AC;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(IS_01_QFS_AC, 1);
  if (_tmp1_1) {
    QFS_AC = getPeekPtr(IS_01_QFS_AC, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 < 64;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_both = 0,
  s_full,
  s_read,
  s_rest,
  s_write
};

static char *stateNames[] = {
  "s_both",
  "s_full",
  "s_read",
  "s_rest",
  "s_write"
};

static enum states _FSM_state = s_rest;

static int both_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_full;
    res = 1;
  } else {
    if (isSchedulable_read_write()) {
      if (hasRoom(IS_01_PQF_AC, 1)) {
        read_write();
        _FSM_state = s_both;
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

static int full_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_write;
    res = 1;
  } else {
    if (isSchedulable_start()) {
      start();
      _FSM_state = s_both;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int read_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_full;
    res = 1;
  } else {
    if (isSchedulable_read_only()) {
      read_only();
      _FSM_state = s_read;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int rest_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_rest;
    res = 1;
  } else {
    if (isSchedulable_start()) {
      start();
      _FSM_state = s_read;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int write_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_rest;
    res = 1;
  } else {
    if (isSchedulable_write_only()) {
      if (hasRoom(IS_01_PQF_AC, 1)) {
        write_only();
        _FSM_state = s_write;
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

int IS_01_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_both:
        res = both_state_scheduler();
        break;
      case s_full:
        res = full_state_scheduler();
        break;
      case s_read:
        res = read_state_scheduler();
        break;
      case s_rest:
        res = rest_state_scheduler();
        break;
      case s_write:
        res = write_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

// Generated from "zigzag"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *zigzag_AC;
extern struct fifo_s *zigzag_START;
extern struct fifo_s *zigzag_ADDR;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *zigzag_OUT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SAMPLE_SZ = 13;
static int BUF_SIZE = 128;
static char count = 1;
static int half = 0;
static short buf[128] = {0};

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

static int ra(int addr) {
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
  return addr & 63 | _tmp1_2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void skip() {
  char *START;
  
  START = getReadPtr(zigzag_START, 1);
}

static int isSchedulable_skip() {
  char *START;
  int _tmp1_1;
  char s_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zigzag_START, 1);
  if (_tmp1_1) {
    START = getPeekPtr(zigzag_START, 1);
    s_1 = START[0];
    _tmp0_1 = s_1 < 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void start() {
  char *START;
  
  START = getReadPtr(zigzag_START, 1);
}

static int isSchedulable_start() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zigzag_START, 1);
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
  int _tmp0_1;
  
  count = 1;
  _tmp0_1 = half;
  half = !_tmp0_1;
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
  short *AC;
  short ac_1;
  int _tmp0_1;
  
  AC = getReadPtr(zigzag_AC, 1);
  ac_1 = AC[0];
  _tmp0_1 = wa();
  buf[_tmp0_1] = ac_1;
  count++;
}

static int isSchedulable_read_only() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zigzag_AC, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void write_only() {
  short *OUT;
  char *ADDR;
  char addr_1;
  int _tmp1_1;
  short _tmp2_1;
  
  OUT = getWritePtr(zigzag_OUT, 1);
  ADDR = getReadPtr(zigzag_ADDR, 1);
  addr_1 = ADDR[0];
  count++;
  _tmp1_1 = ra(addr_1);
  _tmp2_1 = buf[_tmp1_1];
  OUT[0] = _tmp2_1;
}

static int isSchedulable_write_only() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zigzag_ADDR, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_write() {
  short *OUT;
  char *ADDR;
  short *AC;
  char addr_1;
  short ac_1;
  int _tmp0_1;
  int _tmp2_1;
  short _tmp3_1;
  
  OUT = getWritePtr(zigzag_OUT, 1);
  ADDR = getReadPtr(zigzag_ADDR, 1);
  addr_1 = ADDR[0];
  AC = getReadPtr(zigzag_AC, 1);
  ac_1 = AC[0];
  _tmp0_1 = wa();
  buf[_tmp0_1] = ac_1;
  count++;
  _tmp2_1 = ra(addr_1);
  _tmp3_1 = buf[_tmp2_1];
  OUT[0] = _tmp3_1;
}

static int isSchedulable_read_write() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(zigzag_ADDR, 1);
  _tmp2_1 = hasTokens(zigzag_AC, 1);
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

enum states {
  s_both = 0,
  s_drain,
  s_empty,
  s_full,
  s_read
};

static char *stateNames[] = {
  "s_both",
  "s_drain",
  "s_empty",
  "s_full",
  "s_read"
};

static enum states _FSM_state = s_empty;

static int both_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_full;
    res = 1;
  } else {
    if (isSchedulable_read_write()) {
      if (hasRoom(zigzag_OUT, 1)) {
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

static int drain_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_empty;
    res = 1;
  } else {
    if (isSchedulable_write_only()) {
      if (hasRoom(zigzag_OUT, 1)) {
        write_only();
        _FSM_state = s_drain;
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

static int empty_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_empty;
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

static int full_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_drain;
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

int zigzag_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_both:
        res = both_state_scheduler();
        break;
      case s_drain:
        res = drain_state_scheduler();
        break;
      case s_empty:
        res = empty_state_scheduler();
        break;
      case s_full:
        res = full_state_scheduler();
        break;
      case s_read:
        res = read_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

// Generated from "mbpack"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *mbpack_DI;
extern struct fifo_s *mbpack_AI;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *mbpack_DO;
extern struct fifo_s *mbpack_AO;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int PIX_SZ = 9;
static int ADDR_SZ = 24;
static int TC = 384;
static short pix_count = 0;
static int buf = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void tc() {
}

static int isSchedulable_tc() {
  short _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = pix_count;
  _tmp0_1 = _tmp1_1 == 384;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void data_out() {
  int *DO;
  short *DI;
  short d_1;
  int _tmp1_1;
  
  DO = getWritePtr(mbpack_DO, 1);
  DI = getReadPtr(mbpack_DI, 1);
  d_1 = DI[0];
  pix_count++;
  _tmp1_1 = buf;
  DO[0] = _tmp1_1 << 8 | d_1;
}

static int isSchedulable_data_out() {
  short *DI;
  int _tmp1_1;
  short _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mbpack_DI, 1);
  if (_tmp1_1) {
    DI = getPeekPtr(mbpack_DI, 1);
    _tmp2_1 = pix_count;
    _tmp0_1 = (_tmp2_1 & 3) == 3;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void data_inp() {
  short *DI;
  short d_1;
  int _tmp0_1;
  
  DI = getReadPtr(mbpack_DI, 1);
  d_1 = DI[0];
  _tmp0_1 = buf;
  buf = _tmp0_1 << 8 | d_1;
  pix_count++;
}

static int isSchedulable_data_inp() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mbpack_DI, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void addr() {
  int *AO;
  int *AI;
  int a_1;
  
  AO = getWritePtr(mbpack_AO, 1);
  AI = getReadPtr(mbpack_AI, 1);
  a_1 = AI[0];
  pix_count = 0;
  AO[0] = a_1;
}

static int isSchedulable_addr() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mbpack_AI, 1);
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

enum states {
  s_addr = 0,
  s_rw
};

static char *stateNames[] = {
  "s_addr",
  "s_rw"
};

static enum states _FSM_state = s_rw;

static int addr_state_scheduler() {
  int res;
  
  if (isSchedulable_addr()) {
    if (hasRoom(mbpack_AO, 1)) {
      addr();
      _FSM_state = s_rw;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int rw_state_scheduler() {
  int res;
  
  if (isSchedulable_tc()) {
    tc();
    _FSM_state = s_addr;
    res = 1;
  } else {
    if (isSchedulable_data_out()) {
      if (hasRoom(mbpack_DO, 1)) {
        data_out();
        _FSM_state = s_rw;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_data_inp()) {
        data_inp();
        _FSM_state = s_rw;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

int mbpack_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_addr:
        res = addr_state_scheduler();
        break;
      case s_rw:
        res = rw_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

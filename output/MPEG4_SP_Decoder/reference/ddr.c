// Generated from "ddr"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *ddr_RA;
extern struct fifo_s *ddr_WA;
extern struct fifo_s *ddr_WD;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *ddr_RD;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MAXW_IN_MB = 121;
static int MAXH_IN_MB = 69;
static int COMPONENTS = 6;
static int BLOCKSIZE = 64;
static int PIXELS_PER_WORD = 4;
static int FRAMEBITS = 1;
static int COMPBITS = 3;
static int YBITS = 5;
static int XBITS = 6;
static int BLOCKBITS = 4;
static int MEMBITS = 19;
static int MEMSIZE = 524268;
static int BURSTSIZE = 96;
static int buf[524268] = {0};
static int address_t = 0;
static int burstSize = 0;
static int preferRead = 1;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void select_read_prefer() {
  int *RA;
  int a_1;
  
  RA = getReadPtr(ddr_RA, 1);
  a_1 = RA[0];
  address_t = a_1;
  burstSize = 96;
  preferRead = 0;
}

static int isSchedulable_select_read_prefer() {
  int *RA;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(ddr_RA, 1);
  if (_tmp1_1) {
    RA = getPeekPtr(ddr_RA, 1);
    _tmp2_1 = preferRead;
    _tmp0_1 = _tmp2_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void select_write_prefer() {
  int *WA;
  int a_1;
  
  WA = getReadPtr(ddr_WA, 1);
  a_1 = WA[0];
  address_t = a_1;
  burstSize = 96;
  preferRead = 1;
}

static int isSchedulable_select_write_prefer() {
  int *WA;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(ddr_WA, 1);
  if (_tmp1_1) {
    WA = getPeekPtr(ddr_WA, 1);
    _tmp2_1 = preferRead;
    _tmp0_1 = !_tmp2_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void select_read_low() {
  int *RA;
  int a_1;
  
  RA = getReadPtr(ddr_RA, 1);
  a_1 = RA[0];
  address_t = a_1;
  burstSize = 96;
  preferRead = 0;
}

static int isSchedulable_select_read_low() {
  int *RA;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(ddr_RA, 1);
  if (_tmp1_1) {
    RA = getPeekPtr(ddr_RA, 1);
    _tmp2_1 = preferRead;
    _tmp0_1 = !_tmp2_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void select_write_low() {
  int *WA;
  int a_1;
  
  WA = getReadPtr(ddr_WA, 1);
  a_1 = WA[0];
  address_t = a_1;
  burstSize = 96;
  preferRead = 1;
}

static int isSchedulable_select_write_low() {
  int *WA;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(ddr_WA, 1);
  if (_tmp1_1) {
    WA = getPeekPtr(ddr_WA, 1);
    _tmp2_1 = preferRead;
    _tmp0_1 = _tmp2_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void data_read() {
  int *RD;
  int _tmp0_1;
  int _tmp1_1;
  int dat0_1;
  
  RD = getWritePtr(ddr_RD, 1);
  _tmp0_1 = address_t;
  _tmp1_1 = buf[_tmp0_1];
  dat0_1 = _tmp1_1;
  address_t++;
  burstSize--;
  RD[0] = dat0_1;
}

static int isSchedulable_data_read() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = burstSize;
  _tmp0_1 = _tmp1_1 > 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void data_write() {
  int *WD;
  int dat_1;
  int _tmp0_1;
  int wa_t0_1;
  
  WD = getReadPtr(ddr_WD, 1);
  dat_1 = WD[0];
  _tmp0_1 = address_t;
  wa_t0_1 = _tmp0_1;
  burstSize--;
  address_t++;
  buf[wa_t0_1] = dat_1;
}

static int isSchedulable_data_write() {
  int *WD;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(ddr_WD, 1);
  if (_tmp1_1) {
    WD = getPeekPtr(ddr_WD, 1);
    _tmp2_1 = burstSize;
    _tmp0_1 = _tmp2_1 > 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void data_done() {
}

static int isSchedulable_data_done() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = burstSize;
  _tmp0_1 = _tmp1_1 == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_doDataRead = 0,
  s_doDataWrite,
  s_getAddr
};

static char *stateNames[] = {
  "s_doDataRead",
  "s_doDataWrite",
  "s_getAddr"
};

static enum states _FSM_state = s_getAddr;

static int doDataRead_state_scheduler() {
  int res;
  
  if (isSchedulable_data_read()) {
    if (hasRoom(ddr_RD, 1)) {
      data_read();
      _FSM_state = s_doDataRead;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_data_done()) {
      data_done();
      _FSM_state = s_getAddr;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int doDataWrite_state_scheduler() {
  int res;
  
  if (isSchedulable_data_write()) {
    data_write();
    _FSM_state = s_doDataWrite;
    res = 1;
  } else {
    if (isSchedulable_data_done()) {
      data_done();
      _FSM_state = s_getAddr;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int getAddr_state_scheduler() {
  int res;
  
  if (isSchedulable_select_read_prefer()) {
    select_read_prefer();
    _FSM_state = s_doDataRead;
    res = 1;
  } else {
    if (isSchedulable_select_write_prefer()) {
      select_write_prefer();
      _FSM_state = s_doDataWrite;
      res = 1;
    } else {
      if (isSchedulable_select_read_low()) {
        select_read_low();
        _FSM_state = s_doDataRead;
        res = 1;
      } else {
        if (isSchedulable_select_write_low()) {
          select_write_low();
          _FSM_state = s_doDataWrite;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

int ddr_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_doDataRead:
        res = doDataRead_state_scheduler();
        break;
      case s_doDataWrite:
        res = doDataWrite_state_scheduler();
        break;
      case s_getAddr:
        res = getAddr_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

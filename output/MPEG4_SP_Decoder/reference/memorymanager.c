// Generated from "memorymanager"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *memorymanager_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *memorymanager_RA;
extern struct fifo_s *memorymanager_WA;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SEARCHWIN_IN_MB = 3;
static int MAXW_IN_MB = 121;
static int MAXH_IN_MB = 69;
static int ADDR_SZ = 24;
static int FLAG_SZ = 4;
static int MV_SZ = 9;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int PIX_SZ = 9;
static int INTRA = 1024;
static int INTER = 512;
static int NEWVOP = 2048;
static int MOTION = 8;
static int ROUND_TYPE = 32;
static int COMPONENTS = 6;
static int BLOCKSIZE = 64;
static int PIXELS_PER_WORD = 4;
static int FRAMEBITS = 1;
static int COMPBITS = 3;
static int YBITS = 5;
static int XBITS = 6;
static int BLOCKBITS = 4;
static int COMPSHIFT = 4;
static int XSHIFT = 7;
static int YSHIFT = 13;
static int FRAMESHIFT = 18;
static char comp = 0;
static char this_mby;
static char this_mbx;
static char next_mby;
static char next_mbx;
static char width;
static char this_frame = 0;
static char last_frame;
static int prediction_is_IVOP;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int mask_bits(int v, int n) {
  return v & (1 << n) - 1;
}

static int address(int f, int y, int x) {
  int _tmp1_1;
  int xm0_1;
  int _tmp4_1;
  int ym0_1;
  int _tmp7_1;
  int fm0_1;
  
  _tmp1_1 = mask_bits(x, 6);
  xm0_1 = _tmp1_1 << 7;
  _tmp4_1 = mask_bits(y, 5);
  ym0_1 = _tmp4_1 << 13;
  _tmp7_1 = mask_bits(f, 1);
  fm0_1 = _tmp7_1 << 18;
  return fm0_1 | ym0_1 | xm0_1;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *BTYPE;
  short cmd_1;
  char _tmp1_1;
  char _tmp2_1;
  int _tmp4_1;
  
  BTYPE = getReadPtr(memorymanager_BTYPE, 1);
  cmd_1 = BTYPE[0];
  next_mbx = 0;
  next_mby = 0;
  comp = 0;
  prediction_is_IVOP = (cmd_1 & 1024) != 0;
  _tmp1_1 = this_frame;
  last_frame = _tmp1_1;
  _tmp2_1 = this_frame;
  _tmp4_1 = mask_bits(_tmp2_1 + 1, 1);
  this_frame = _tmp4_1;
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(memorymanager_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(memorymanager_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2048) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void set_width() {
  short *BTYPE;
  short w_1;
  
  BTYPE = getReadPtr(memorymanager_BTYPE, 1);
  w_1 = BTYPE[0];
  width = w_1;
}

static int isSchedulable_set_width() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(memorymanager_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void height() {
  short *BTYPE;
  
  BTYPE = getReadPtr(memorymanager_BTYPE, 1);
}

static int isSchedulable_height() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(memorymanager_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_y0() {
  int *WA;
  short *BTYPE;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp3_1;
  char _tmp4_1;
  char _tmp5_1;
  char _tmp6_1;
  char _tmp7_1;
  char _tmp8_1;
  int _tmp9_1;
  
  WA = getWritePtr(memorymanager_WA, 1);
  BTYPE = getReadPtr(memorymanager_BTYPE, 1);
  _tmp0_1 = next_mbx;
  this_mbx = _tmp0_1;
  _tmp1_1 = next_mby;
  this_mby = _tmp1_1;
  next_mbx++;
  _tmp3_1 = next_mbx;
  _tmp4_1 = width;
  if (_tmp3_1 == _tmp4_1) {
    next_mbx = 0;
    _tmp5_1 = next_mby;
    next_mby = _tmp5_1 + 1;
  }
  comp = 1;
  _tmp6_1 = this_frame;
  _tmp7_1 = this_mby;
  _tmp8_1 = this_mbx;
  _tmp9_1 = address(_tmp6_1, _tmp7_1, _tmp8_1);
  WA[0] = _tmp9_1;
}

static int isSchedulable_cmd_y0() {
  short *BTYPE;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(memorymanager_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(memorymanager_BTYPE, 1);
    _tmp2_1 = comp;
    _tmp0_1 = _tmp2_1 == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_other() {
  short *BTYPE;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp1_2;
  char _tmp2_1;
  char _tmp1_3;
  
  BTYPE = getReadPtr(memorymanager_BTYPE, 1);
  _tmp0_1 = comp;
  if (_tmp0_1 == 5) {
    _tmp1_1 = 0;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp2_1 = comp;
    _tmp1_3 = _tmp2_1 + 1;
    _tmp1_2 = _tmp1_3;
  }
  comp = _tmp1_2;
}

static int isSchedulable_cmd_other() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(memorymanager_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_none() {
}

static int isSchedulable_read_none() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = prediction_is_IVOP;
  _tmp0_1 = _tmp1_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void read_above() {
  int *RA;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  int _tmp3_1;
  
  RA = getWritePtr(memorymanager_RA, 1);
  _tmp0_1 = last_frame;
  _tmp1_1 = this_mby;
  _tmp2_1 = this_mbx;
  _tmp3_1 = address(_tmp0_1, _tmp1_1 - 1, _tmp2_1);
  RA[0] = _tmp3_1;
}

static int isSchedulable_read_above() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void read_this() {
  int *RA;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  int _tmp3_1;
  
  RA = getWritePtr(memorymanager_RA, 1);
  _tmp0_1 = last_frame;
  _tmp1_1 = this_mby;
  _tmp2_1 = this_mbx;
  _tmp3_1 = address(_tmp0_1, _tmp1_1, _tmp2_1);
  RA[0] = _tmp3_1;
}

static int isSchedulable_read_this() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void read_below() {
  int *RA;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  int _tmp3_1;
  
  RA = getWritePtr(memorymanager_RA, 1);
  _tmp0_1 = last_frame;
  _tmp1_1 = this_mby;
  _tmp2_1 = this_mbx;
  _tmp3_1 = address(_tmp0_1, _tmp1_1 + 1, _tmp2_1);
  RA[0] = _tmp3_1;
}

static int isSchedulable_read_below() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_cmd = 0,
  s_geth,
  s_getw,
  s_readAbove,
  s_readBelow,
  s_readThis
};

static char *stateNames[] = {
  "s_cmd",
  "s_geth",
  "s_getw",
  "s_readAbove",
  "s_readBelow",
  "s_readThis"
};

static enum states _FSM_state = s_cmd;

static int cmd_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_newVop()) {
    cmd_newVop();
    _FSM_state = s_getw;
    res = 1;
  } else {
    if (isSchedulable_cmd_y0()) {
      if (hasRoom(memorymanager_WA, 1)) {
        cmd_y0();
        _FSM_state = s_readAbove;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_cmd_other()) {
        cmd_other();
        _FSM_state = s_cmd;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_height()) {
    height();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int getw_state_scheduler() {
  int res;
  
  if (isSchedulable_set_width()) {
    set_width();
    _FSM_state = s_geth;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int readAbove_state_scheduler() {
  int res;
  
  if (isSchedulable_read_none()) {
    read_none();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    if (isSchedulable_read_above()) {
      if (hasRoom(memorymanager_RA, 1)) {
        read_above();
        _FSM_state = s_readThis;
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

static int readBelow_state_scheduler() {
  int res;
  
  if (isSchedulable_read_below()) {
    if (hasRoom(memorymanager_RA, 1)) {
      read_below();
      _FSM_state = s_cmd;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int readThis_state_scheduler() {
  int res;
  
  if (isSchedulable_read_this()) {
    if (hasRoom(memorymanager_RA, 1)) {
      read_this();
      _FSM_state = s_readBelow;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

int memorymanager_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_cmd:
        res = cmd_state_scheduler();
        break;
      case s_geth:
        res = geth_state_scheduler();
        break;
      case s_getw:
        res = getw_state_scheduler();
        break;
      case s_readAbove:
        res = readAbove_state_scheduler();
        break;
      case s_readBelow:
        res = readBelow_state_scheduler();
        break;
      case s_readThis:
        res = readThis_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

// Generated from "unpack"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *unpack_MV;
extern struct fifo_s *unpack_BTYPE;
extern struct fifo_s *unpack_DI;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *unpack_DO;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SEARCHWIN_IN_MB = 3;
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
static char comp;
static char mbw_max;
static char mbx;
static char count;
static char row;
static short xstart;
static short mvx;
static int data;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int maskBits(int v, int n) {
  return v & (1 << n) - 1;
}

static int extractByte(int v, int n) {
  return v >> ((3 - n) * 8) & 255;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *BTYPE;
  
  BTYPE = getReadPtr(unpack_BTYPE, 1);
  comp = 0;
  mbx = 0;
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(unpack_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2048) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getw_() {
  short *BTYPE;
  short w_1;
  
  BTYPE = getReadPtr(unpack_BTYPE, 1);
  w_1 = BTYPE[0];
  mbw_max = w_1 - 1;
}

static int isSchedulable_getw() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void geth() {
  short *BTYPE;
  
  BTYPE = getReadPtr(unpack_BTYPE, 1);
}

static int isSchedulable_geth() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_motion() {
  short *BTYPE;
  
  BTYPE = getReadPtr(unpack_BTYPE, 1);
}

static int isSchedulable_cmd_motion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(unpack_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 520) == 520;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_noMotion() {
  short *BTYPE;
  char _tmp0_1;
  char _tmp2_1;
  char _tmp3_1;
  unsigned char _tmp4_1;
  unsigned char _tmp4_2;
  unsigned char _tmp4_3;
  int _tmp1_1;
  int _tmp1_2;
  char _tmp5_1;
  int _tmp1_3;
  
  BTYPE = getReadPtr(unpack_BTYPE, 1);
  mvx = 0;
  count = 0;
  row = 0;
  _tmp0_1 = comp;
  if (_tmp0_1 < 4) {
    _tmp2_1 = mbx;
    _tmp3_1 = comp;
    if ((_tmp3_1 & 1) != 0) {
      _tmp4_1 = 8;
      _tmp4_2 = _tmp4_1;
    } else {
      _tmp4_3 = 0;
      _tmp4_2 = _tmp4_3;
    }
    _tmp1_1 = (_tmp2_1 << 4) + _tmp4_2;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp5_1 = mbx;
    _tmp1_3 = _tmp5_1 << 3;
    _tmp1_2 = _tmp1_3;
  }
  xstart = _tmp1_2;
}

static int isSchedulable_cmd_noMotion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(unpack_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 512) == 512;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_other() {
  short *BTYPE;
  
  BTYPE = getReadPtr(unpack_BTYPE, 1);
  row = 9;
}

static int isSchedulable_cmd_other() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getmvx() {
  short *MV;
  short v_1;
  short vv0_1;
  char _tmp0_1;
  char _tmp2_1;
  char _tmp3_1;
  unsigned char _tmp4_1;
  unsigned char _tmp4_2;
  unsigned char _tmp4_3;
  int _tmp1_1;
  int _tmp1_2;
  char _tmp5_1;
  int _tmp1_3;
  
  MV = getReadPtr(unpack_MV, 1);
  v_1 = MV[0];
  vv0_1 = v_1 >> 1;
  mvx = vv0_1;
  count = 0;
  row = 0;
  _tmp0_1 = comp;
  if (_tmp0_1 < 4) {
    _tmp2_1 = mbx;
    _tmp3_1 = comp;
    if ((_tmp3_1 & 1) != 0) {
      _tmp4_1 = 8;
      _tmp4_2 = _tmp4_1;
    } else {
      _tmp4_3 = 0;
      _tmp4_2 = _tmp4_3;
    }
    _tmp1_1 = vv0_1 + (_tmp2_1 << 4) + _tmp4_2;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp5_1 = mbx;
    _tmp1_3 = vv0_1 + (_tmp5_1 << 3);
    _tmp1_2 = _tmp1_3;
  }
  xstart = _tmp1_2;
}

static int isSchedulable_getmvx() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_MV, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getmvy() {
  short *MV;
  
  MV = getReadPtr(unpack_MV, 1);
}

static int isSchedulable_getmvy() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_MV, 1);
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
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  char _tmp3_2;
  char _tmp4_1;
  char _tmp3_3;
  char _tmp5_1;
  
  _tmp0_1 = comp;
  if (_tmp0_1 == 5) {
    comp = 0;
    _tmp1_1 = mbx;
    _tmp2_1 = mbw_max;
    if (_tmp1_1 == _tmp2_1) {
      _tmp3_1 = 0;
      _tmp3_2 = _tmp3_1;
    } else {
      _tmp4_1 = mbx;
      _tmp3_3 = _tmp4_1 + 1;
      _tmp3_2 = _tmp3_3;
    }
    mbx = _tmp3_2;
  } else {
    _tmp5_1 = comp;
    comp = _tmp5_1 + 1;
  }
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = row;
  _tmp0_1 = _tmp1_1 == 9;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void extract_noRead() {
  short *DO;
  short _tmp0_1;
  char _tmp1_1;
  short x0_1;
  int left_clip0_1;
  char _tmp2_1;
  char _tmp3_1;
  int right_clip_luma0_1;
  char _tmp4_1;
  char _tmp5_1;
  int right_clip_chroma0_1;
  char shift0_1;
  char shift0_2;
  char shift0_3;
  char shift0_4;
  char shift0_5;
  char _tmp6_1;
  char _tmp7_1;
  char _tmp8_1;
  int _tmp9_1;
  int _tmp10_1;
  
  DO = getWritePtr(unpack_DO, 1);
  _tmp0_1 = xstart;
  _tmp1_1 = count;
  x0_1 = _tmp0_1 + _tmp1_1;
  left_clip0_1 = x0_1 < 0;
  _tmp2_1 = comp;
  _tmp3_1 = mbw_max;
  right_clip_luma0_1 = (_tmp2_1 & 4) == 0 && x0_1 >> 4 > _tmp3_1;
  _tmp4_1 = comp;
  _tmp5_1 = mbw_max;
  right_clip_chroma0_1 = (_tmp4_1 & 4) != 0 && x0_1 >> 3 > _tmp5_1;
  if (left_clip0_1) {
    shift0_1 = 0;
    shift0_2 = shift0_1;
  } else {
    if (right_clip_luma0_1 || right_clip_chroma0_1) {
      shift0_3 = 3;
      shift0_4 = shift0_3;
    } else {
      shift0_5 = x0_1 & 3;
      shift0_4 = shift0_5;
    }
    shift0_2 = shift0_4;
  }
  _tmp6_1 = count;
  if (_tmp6_1 == 8) {
    count = 0;
    _tmp7_1 = row;
    row = _tmp7_1 + 1;
  } else {
    _tmp8_1 = count;
    count = _tmp8_1 + 1;
  }
  _tmp9_1 = data;
  _tmp10_1 = extractByte(_tmp9_1, shift0_2);
  DO[0] = _tmp10_1;
}

static int isSchedulable_extract_noRead() {
  short _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = xstart;
  _tmp2_1 = count;
  _tmp3_1 = count;
  _tmp0_1 = (_tmp1_1 + _tmp2_1 & 3) != 0 && _tmp3_1 != 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void extract_read() {
  short *DO;
  int *DI;
  int d_1;
  short _tmp0_1;
  char _tmp1_1;
  short x0_1;
  int left_clip0_1;
  char _tmp2_1;
  char _tmp3_1;
  int right_clip_luma0_1;
  char _tmp4_1;
  char _tmp5_1;
  int right_clip_chroma0_1;
  char shift0_1;
  char shift0_2;
  char shift0_3;
  char shift0_4;
  char shift0_5;
  char _tmp6_1;
  char _tmp7_1;
  char _tmp8_1;
  int _tmp9_1;
  int _tmp10_1;
  
  DO = getWritePtr(unpack_DO, 1);
  DI = getReadPtr(unpack_DI, 1);
  d_1 = DI[0];
  _tmp0_1 = xstart;
  _tmp1_1 = count;
  x0_1 = _tmp0_1 + _tmp1_1;
  left_clip0_1 = x0_1 < 0;
  _tmp2_1 = comp;
  _tmp3_1 = mbw_max;
  right_clip_luma0_1 = (_tmp2_1 & 4) == 0 && x0_1 >> 4 > _tmp3_1;
  _tmp4_1 = comp;
  _tmp5_1 = mbw_max;
  right_clip_chroma0_1 = (_tmp4_1 & 4) != 0 && x0_1 >> 3 > _tmp5_1;
  if (left_clip0_1) {
    shift0_1 = 0;
    shift0_2 = shift0_1;
  } else {
    if (right_clip_luma0_1 || right_clip_chroma0_1) {
      shift0_3 = 3;
      shift0_4 = shift0_3;
    } else {
      shift0_5 = x0_1 & 3;
      shift0_4 = shift0_5;
    }
    shift0_2 = shift0_4;
  }
  data = d_1;
  _tmp6_1 = count;
  if (_tmp6_1 == 8) {
    count = 0;
    _tmp7_1 = row;
    row = _tmp7_1 + 1;
  } else {
    _tmp8_1 = count;
    count = _tmp8_1 + 1;
  }
  _tmp9_1 = data;
  _tmp10_1 = extractByte(_tmp9_1, shift0_2);
  DO[0] = _tmp10_1;
}

static int isSchedulable_extract_read() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(unpack_DI, 1);
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
  s_cmd = 0,
  s_extract,
  s_geth,
  s_getmvx,
  s_getmvy,
  s_getw
};

static char *stateNames[] = {
  "s_cmd",
  "s_extract",
  "s_geth",
  "s_getmvx",
  "s_getmvy",
  "s_getw"
};

static enum states _FSM_state = s_cmd;

static int cmd_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_newVop()) {
    cmd_newVop();
    _FSM_state = s_getw;
    res = 1;
  } else {
    if (isSchedulable_cmd_motion()) {
      cmd_motion();
      _FSM_state = s_getmvx;
      res = 1;
    } else {
      if (isSchedulable_cmd_noMotion()) {
        cmd_noMotion();
        _FSM_state = s_extract;
        res = 1;
      } else {
        if (isSchedulable_cmd_other()) {
          cmd_other();
          _FSM_state = s_extract;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int extract_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    if (isSchedulable_extract_noRead()) {
      if (hasRoom(unpack_DO, 1)) {
        extract_noRead();
        _FSM_state = s_extract;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_extract_read()) {
        if (hasRoom(unpack_DO, 1)) {
          extract_read();
          _FSM_state = s_extract;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_geth()) {
    geth();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int getmvx_state_scheduler() {
  int res;
  
  if (isSchedulable_getmvx()) {
    getmvx();
    _FSM_state = s_getmvy;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int getmvy_state_scheduler() {
  int res;
  
  if (isSchedulable_getmvy()) {
    getmvy();
    _FSM_state = s_extract;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int getw_state_scheduler() {
  int res;
  
  if (isSchedulable_getw()) {
    getw_();
    _FSM_state = s_geth;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

int unpack_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_cmd:
        res = cmd_state_scheduler();
        break;
      case s_extract:
        res = extract_state_scheduler();
        break;
      case s_geth:
        res = geth_state_scheduler();
        break;
      case s_getmvx:
        res = getmvx_state_scheduler();
        break;
      case s_getmvy:
        res = getmvy_state_scheduler();
        break;
      case s_getw:
        res = getw_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

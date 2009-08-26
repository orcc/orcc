// Generated from "add_02"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *add_02_MOT;
extern struct fifo_s *add_02_TEX;
extern struct fifo_s *add_02_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *add_02_VID;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int PIX_SZ = 9;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int NEWVOP = 2048;
static int INTRA = 1024;
static int ACCODED = 2;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *BTYPE;
  
  BTYPE = getReadPtr(add_02_BTYPE, 1);
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(add_02_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2048) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_textureOnly() {
  short *BTYPE;
  
  BTYPE = getReadPtr(add_02_BTYPE, 1);
}

static int isSchedulable_cmd_textureOnly() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(add_02_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 1024) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_motionOnly() {
  short *BTYPE;
  
  BTYPE = getReadPtr(add_02_BTYPE, 1);
}

static int isSchedulable_cmd_motionOnly() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(add_02_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_other() {
  short *BTYPE;
  
  BTYPE = getReadPtr(add_02_BTYPE, 1);
}

static int isSchedulable_cmd_other() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void texture() {
  short *VID;
  short *TEX;
  short tex[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  
  VID = getWritePtr(add_02_VID, 64);
  TEX = getReadPtr(add_02_TEX, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = TEX[1 * _i0_3 + 0];
    tex[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp1_1 = tex[_i1_3];
    VID[1 * _i1_3 + 0] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_texture() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_TEX, 64);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void motion() {
  short *VID;
  short *MOT;
  short mot[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  
  VID = getWritePtr(add_02_VID, 64);
  MOT = getReadPtr(add_02_MOT, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = MOT[1 * _i0_3 + 0];
    mot[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp1_1 = mot[_i1_3];
    VID[1 * _i1_3 + 0] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_motion() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_MOT, 64);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void combine() {
  short _tmp2[64];
  short *VID;
  short *MOT;
  short mot[64];
  short *TEX;
  short tex[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  int i0_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  short _tmp5_2;
  short _tmp6_1;
  short _tmp6_2;
  short _tmp7_1;
  short _tmp7_2;
  short _tmp5_3;
  short _tmp5_4;
  short _tmp8_1;
  short _tmp8_2;
  short _tmp9_1;
  short _tmp9_2;
  short _tmp5_5;
  short _tmp8_3;
  short _tmp9_3;
  short _tmp6_3;
  short _tmp7_3;
  short _tmp8_4;
  short _tmp9_4;
  int i0_2;
  int i0_3;
  int _i2_1;
  short _tmp10_1;
  int _i2_2;
  int _i2_3;
  
  VID = getWritePtr(add_02_VID, 64);
  MOT = getReadPtr(add_02_MOT, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = MOT[1 * _i0_3 + 0];
    mot[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  TEX = getReadPtr(add_02_TEX, 64);
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp1_1 = TEX[1 * _i1_3 + 0];
    tex[_i1_3] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
  i0_1 = 0;
  _tmp6_3 = 0;
  _tmp7_3 = 0;
  _tmp8_4 = 0;
  _tmp9_4 = 0;
  i0_3 = i0_1;
  while (i0_3 < 64) {
    _tmp3_1 = tex[i0_3];
    _tmp4_1 = mot[i0_3];
    if (_tmp3_1 + _tmp4_1 < 0) {
      _tmp5_1 = 0;
      _tmp5_2 = _tmp5_1;
      _tmp6_2 = _tmp6_3;
      _tmp7_2 = _tmp7_3;
      _tmp8_3 = _tmp8_4;
      _tmp9_3 = _tmp9_4;
    } else {
      _tmp6_1 = tex[i0_3];
      _tmp7_1 = mot[i0_3];
      if (_tmp6_1 + _tmp7_1 > 255) {
        _tmp5_3 = 255;
        _tmp5_4 = _tmp5_3;
        _tmp8_2 = _tmp8_4;
        _tmp9_2 = _tmp9_4;
      } else {
        _tmp8_1 = tex[i0_3];
        _tmp9_1 = mot[i0_3];
        _tmp5_5 = _tmp8_1 + _tmp9_1;
        _tmp5_4 = _tmp5_5;
        _tmp8_2 = _tmp8_1;
        _tmp9_2 = _tmp9_1;
      }
      _tmp5_2 = _tmp5_4;
      _tmp6_2 = _tmp6_1;
      _tmp7_2 = _tmp7_1;
      _tmp8_3 = _tmp8_2;
      _tmp9_3 = _tmp9_2;
    }
    _tmp2[i0_3] = _tmp5_2;
    i0_2 = i0_3 + 1;
    _tmp6_3 = _tmp6_2;
    _tmp7_3 = _tmp7_2;
    _tmp8_4 = _tmp8_3;
    _tmp9_4 = _tmp9_3;
    i0_3 = i0_2;
  }
  _i2_1 = 0;
  _i2_3 = _i2_1;
  while (_i2_3 < 64) {
    _tmp10_1 = _tmp2[_i2_3];
    VID[1 * _i2_3 + 0] = _tmp10_1;
    _i2_2 = _i2_3 + 1;
    _i2_3 = _i2_2;
  }
}

static int isSchedulable_combine() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_02_MOT, 64);
  _tmp2_1 = hasTokens(add_02_TEX, 64);
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
  s_cmd = 0,
  s_combine,
  s_motion,
  s_skiph,
  s_skipw,
  s_texture
};

static char *stateNames[] = {
  "s_cmd",
  "s_combine",
  "s_motion",
  "s_skiph",
  "s_skipw",
  "s_texture"
};

static enum states _FSM_state = s_cmd;

static int cmd_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_newVop()) {
    cmd_newVop();
    _FSM_state = s_skipw;
    res = 1;
  } else {
    if (isSchedulable_cmd_textureOnly()) {
      cmd_textureOnly();
      _FSM_state = s_texture;
      res = 1;
    } else {
      if (isSchedulable_cmd_motionOnly()) {
        cmd_motionOnly();
        _FSM_state = s_motion;
        res = 1;
      } else {
        if (isSchedulable_cmd_other()) {
          cmd_other();
          _FSM_state = s_combine;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int combine_state_scheduler() {
  int res;
  
  if (isSchedulable_combine()) {
    if (hasRoom(add_02_VID, 64)) {
      combine();
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

static int motion_state_scheduler() {
  int res;
  
  if (isSchedulable_motion()) {
    if (hasRoom(add_02_VID, 64)) {
      motion();
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

static int skiph_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_other()) {
    cmd_other();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int skipw_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_other()) {
    cmd_other();
    _FSM_state = s_skiph;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int texture_state_scheduler() {
  int res;
  
  if (isSchedulable_texture()) {
    if (hasRoom(add_02_VID, 64)) {
      texture();
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

int add_02_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_cmd:
        res = cmd_state_scheduler();
        break;
      case s_combine:
        res = combine_state_scheduler();
        break;
      case s_motion:
        res = motion_state_scheduler();
        break;
      case s_skiph:
        res = skiph_state_scheduler();
        break;
      case s_skipw:
        res = skipw_state_scheduler();
        break;
      case s_texture:
        res = texture_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

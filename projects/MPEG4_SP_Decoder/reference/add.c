// Generated from "add"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *add_MOT;
extern struct fifo_s *add_TEX;
extern struct fifo_s *add_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *add_VID;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int PIX_SZ = 9;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int NEWVOP = 2048;
static int INTRA = 1024;
static int ACCODED = 2;
static int _CAL_tokenMonitor = 1;
static char count = 0;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *BTYPE;
  
  BTYPE = getReadPtr(add_BTYPE, 1);
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(add_BTYPE, 1);
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
  
  BTYPE = getReadPtr(add_BTYPE, 1);
}

static int isSchedulable_cmd_textureOnly() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(add_BTYPE, 1);
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
  
  BTYPE = getReadPtr(add_BTYPE, 1);
}

static int isSchedulable_cmd_motionOnly() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(add_BTYPE, 1);
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
  
  BTYPE = getReadPtr(add_BTYPE, 1);
}

static int isSchedulable_cmd_other() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_BTYPE, 1);
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
  count = 0;
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

static void texture() {
  short *VID;
  short *TEX;
  short tex_1;
  
  VID = getWritePtr(add_VID, 1);
  TEX = getReadPtr(add_TEX, 1);
  tex_1 = TEX[0];
  count++;
  VID[0] = tex_1;
}

static int isSchedulable_texture() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_TEX, 1);
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
  short mot_1;
  
  VID = getWritePtr(add_VID, 1);
  MOT = getReadPtr(add_MOT, 1);
  mot_1 = MOT[0];
  count++;
  VID[0] = mot_1;
}

static int isSchedulable_motion() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_MOT, 1);
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
  short *VID;
  short *MOT;
  short *TEX;
  short mot_1;
  short tex_1;
  short s0_1;
  short _tmp1_1;
  short _tmp1_2;
  short _tmp1_3;
  short _tmp1_4;
  short _tmp1_5;
  
  VID = getWritePtr(add_VID, 1);
  MOT = getReadPtr(add_MOT, 1);
  mot_1 = MOT[0];
  TEX = getReadPtr(add_TEX, 1);
  tex_1 = TEX[0];
  s0_1 = tex_1 + mot_1;
  count++;
  if (s0_1 < 0) {
    _tmp1_1 = 0;
    _tmp1_2 = _tmp1_1;
  } else {
    if (s0_1 > 255) {
      _tmp1_3 = 255;
      _tmp1_4 = _tmp1_3;
    } else {
      _tmp1_5 = s0_1;
      _tmp1_4 = _tmp1_5;
    }
    _tmp1_2 = _tmp1_4;
  }
  VID[0] = _tmp1_2;
}

static int isSchedulable_combine() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(add_MOT, 1);
  _tmp2_1 = hasTokens(add_TEX, 1);
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
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    if (isSchedulable_combine()) {
      if (hasRoom(add_VID, 1)) {
        combine();
        _FSM_state = s_combine;
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

static int motion_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    if (isSchedulable_motion()) {
      if (hasRoom(add_VID, 1)) {
        motion();
        _FSM_state = s_motion;
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
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    if (isSchedulable_texture()) {
      if (hasRoom(add_VID, 1)) {
        texture();
        _FSM_state = s_texture;
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

int add_scheduler() {
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

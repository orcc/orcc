// Generated from "splitter_BTYPE"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *splitter_BTYPE_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *splitter_BTYPE_Y;
extern struct fifo_s *splitter_BTYPE_U;
extern struct fifo_s *splitter_BTYPE_V;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int BTYPE_SZ = 12;
static int NEWVOP = 2048;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *V;
  short *U;
  short *Y;
  short *BTYPE;
  short cmd_1;
  
  Y = getWritePtr(splitter_BTYPE_Y, 1);
  U = getWritePtr(splitter_BTYPE_U, 1);
  V = getWritePtr(splitter_BTYPE_V, 1);
  BTYPE = getReadPtr(splitter_BTYPE_BTYPE, 1);
  cmd_1 = BTYPE[0];
  Y[0] = cmd_1;
  U[0] = cmd_1;
  V[0] = cmd_1;
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_BTYPE_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(splitter_BTYPE_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2048) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void skip() {
  short *V;
  short *U;
  short *Y;
  short *BTYPE;
  short cmd[2];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  short _tmp6_1;
  
  Y = getWritePtr(splitter_BTYPE_Y, 2);
  U = getWritePtr(splitter_BTYPE_U, 2);
  V = getWritePtr(splitter_BTYPE_V, 2);
  BTYPE = getReadPtr(splitter_BTYPE_BTYPE, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = BTYPE[1 * _i0_3 + 0];
    cmd[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _tmp1_1 = cmd[0];
  _tmp2_1 = cmd[1];
  Y[0] = _tmp1_1;
  Y[1] = _tmp2_1;
  _tmp3_1 = cmd[0];
  _tmp4_1 = cmd[1];
  U[0] = _tmp3_1;
  U[1] = _tmp4_1;
  _tmp5_1 = cmd[0];
  _tmp6_1 = cmd[1];
  V[0] = _tmp5_1;
  V[1] = _tmp6_1;
}

static int isSchedulable_skip() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_BTYPE_BTYPE, 2);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_split() {
  short *V;
  short *U;
  short *Y;
  short *BTYPE;
  short list[6];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  short _tmp6_1;
  
  Y = getWritePtr(splitter_BTYPE_Y, 4);
  U = getWritePtr(splitter_BTYPE_U, 1);
  V = getWritePtr(splitter_BTYPE_V, 1);
  BTYPE = getReadPtr(splitter_BTYPE_BTYPE, 6);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 6) {
    _tmp0_1 = BTYPE[1 * _i0_3 + 0];
    list[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _tmp1_1 = list[0];
  _tmp2_1 = list[1];
  _tmp3_1 = list[2];
  _tmp4_1 = list[3];
  Y[0] = _tmp1_1;
  Y[1] = _tmp2_1;
  Y[2] = _tmp3_1;
  Y[3] = _tmp4_1;
  _tmp5_1 = list[4];
  U[0] = _tmp5_1;
  _tmp6_1 = list[5];
  V[0] = _tmp6_1;
}

static int isSchedulable_cmd_split() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_BTYPE_BTYPE, 6);
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
  s_skip
};

static char *stateNames[] = {
  "s_cmd",
  "s_skip"
};

static enum states _FSM_state = s_cmd;

static int cmd_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_newVop()) {
    if (hasRoom(splitter_BTYPE_U, 1) && hasRoom(splitter_BTYPE_V, 1) && hasRoom(splitter_BTYPE_Y, 1)) {
      cmd_newVop();
      _FSM_state = s_skip;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_cmd_split()) {
      if (hasRoom(splitter_BTYPE_U, 1) && hasRoom(splitter_BTYPE_V, 1) && hasRoom(splitter_BTYPE_Y, 4)) {
        cmd_split();
        _FSM_state = s_cmd;
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

static int skip_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    if (hasRoom(splitter_BTYPE_U, 2) && hasRoom(splitter_BTYPE_V, 2) && hasRoom(splitter_BTYPE_Y, 2)) {
      skip();
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

int splitter_BTYPE_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_cmd:
        res = cmd_state_scheduler();
        break;
      case s_skip:
        res = skip_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

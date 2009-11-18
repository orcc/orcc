// Generated from "splitter_MV"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *splitter_MV_MV;
extern struct fifo_s *splitter_MV_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *splitter_MV_Y;
extern struct fifo_s *splitter_MV_U;
extern struct fifo_s *splitter_MV_V;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MV_SZ = 9;
static int BTYPE_SZ = 12;
static int MOTION = 8;
static int NEWVOP = 2048;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void newvop() {
  short *BTYPE;
  
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
}

static int isSchedulable_newvop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
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
  short *BTYPE;
  short btype[2];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  
  BTYPE = getReadPtr(splitter_MV_BTYPE, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = BTYPE[1 * _i0_3 + 0];
    btype[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
}

static int isSchedulable_skip() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_BTYPE, 2);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void skipbtype() {
  short *BTYPE;
  
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
}

static int isSchedulable_skipbtype() {
  short *BTYPE;
  int _tmp1_1;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) == 0 && (btype_1 & 2048) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void B1() {
  short *Y;
  short *MV;
  short mv[2];
  short *BTYPE;
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  
  Y = getWritePtr(splitter_MV_Y, 2);
  MV = getReadPtr(splitter_MV_MV, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = MV[1 * _i0_3 + 0];
    mv[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
  _tmp1_1 = mv[0];
  _tmp2_1 = mv[1];
  Y[0] = _tmp1_1;
  Y[1] = _tmp2_1;
}

static int isSchedulable_B1() {
  short *BTYPE;
  short mv[2];
  short *MV;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_MV, 2);
  _tmp2_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1 && _tmp2_1) {
    MV = getPeekPtr(splitter_MV_MV, 2);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 2) {
      _tmp3_1 = MV[1 * _i0_4 + 0];
      mv[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void B2() {
  short *Y;
  short *MV;
  short mv[2];
  short *BTYPE;
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  
  Y = getWritePtr(splitter_MV_Y, 2);
  MV = getReadPtr(splitter_MV_MV, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = MV[1 * _i0_3 + 0];
    mv[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
  _tmp1_1 = mv[0];
  _tmp2_1 = mv[1];
  Y[0] = _tmp1_1;
  Y[1] = _tmp2_1;
}

static int isSchedulable_B2() {
  short *BTYPE;
  short mv[2];
  short *MV;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_MV, 2);
  _tmp2_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1 && _tmp2_1) {
    MV = getPeekPtr(splitter_MV_MV, 2);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 2) {
      _tmp3_1 = MV[1 * _i0_4 + 0];
      mv[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void B3() {
  short *Y;
  short *MV;
  short mv[2];
  short *BTYPE;
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  
  Y = getWritePtr(splitter_MV_Y, 2);
  MV = getReadPtr(splitter_MV_MV, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = MV[1 * _i0_3 + 0];
    mv[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
  _tmp1_1 = mv[0];
  _tmp2_1 = mv[1];
  Y[0] = _tmp1_1;
  Y[1] = _tmp2_1;
}

static int isSchedulable_B3() {
  short *BTYPE;
  short mv[2];
  short *MV;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_MV, 2);
  _tmp2_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1 && _tmp2_1) {
    MV = getPeekPtr(splitter_MV_MV, 2);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 2) {
      _tmp3_1 = MV[1 * _i0_4 + 0];
      mv[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void B4() {
  short *Y;
  short *MV;
  short mv[2];
  short *BTYPE;
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  
  Y = getWritePtr(splitter_MV_Y, 2);
  MV = getReadPtr(splitter_MV_MV, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = MV[1 * _i0_3 + 0];
    mv[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
  _tmp1_1 = mv[0];
  _tmp2_1 = mv[1];
  Y[0] = _tmp1_1;
  Y[1] = _tmp2_1;
}

static int isSchedulable_B4() {
  short *BTYPE;
  short mv[2];
  short *MV;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_MV, 2);
  _tmp2_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1 && _tmp2_1) {
    MV = getPeekPtr(splitter_MV_MV, 2);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 2) {
      _tmp3_1 = MV[1 * _i0_4 + 0];
      mv[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void B5() {
  short *U;
  short *MV;
  short mv[2];
  short *BTYPE;
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  
  U = getWritePtr(splitter_MV_U, 2);
  MV = getReadPtr(splitter_MV_MV, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = MV[1 * _i0_3 + 0];
    mv[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
  _tmp1_1 = mv[0];
  _tmp2_1 = mv[1];
  U[0] = _tmp1_1;
  U[1] = _tmp2_1;
}

static int isSchedulable_B5() {
  short *BTYPE;
  short mv[2];
  short *MV;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_MV, 2);
  _tmp2_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1 && _tmp2_1) {
    MV = getPeekPtr(splitter_MV_MV, 2);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 2) {
      _tmp3_1 = MV[1 * _i0_4 + 0];
      mv[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void B6() {
  short *V;
  short *MV;
  short mv[2];
  short *BTYPE;
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp1_1;
  short _tmp2_1;
  
  V = getWritePtr(splitter_MV_V, 2);
  MV = getReadPtr(splitter_MV_MV, 2);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 2) {
    _tmp0_1 = MV[1 * _i0_3 + 0];
    mv[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  BTYPE = getReadPtr(splitter_MV_BTYPE, 1);
  _tmp1_1 = mv[0];
  _tmp2_1 = mv[1];
  V[0] = _tmp1_1;
  V[1] = _tmp2_1;
}

static int isSchedulable_B6() {
  short *BTYPE;
  short mv[2];
  short *MV;
  int _tmp1_1;
  int _tmp2_1;
  int _i0_1;
  short _tmp3_1;
  int _i0_3;
  int _i0_4;
  short btype_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(splitter_MV_MV, 2);
  _tmp2_1 = hasTokens(splitter_MV_BTYPE, 1);
  if (_tmp1_1 && _tmp2_1) {
    MV = getPeekPtr(splitter_MV_MV, 2);
    _i0_1 = 0;
    _i0_4 = _i0_1;
    while (_i0_4 < 2) {
      _tmp3_1 = MV[1 * _i0_4 + 0];
      mv[_i0_4] = _tmp3_1;
      _i0_3 = _i0_4 + 1;
      _i0_4 = _i0_3;
    }
    BTYPE = getPeekPtr(splitter_MV_BTYPE, 1);
    btype_1 = BTYPE[0];
    _tmp0_1 = (btype_1 & 8) != 0;
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
  s_block2 = 0,
  s_block3,
  s_block4,
  s_block5,
  s_block6,
  s_cmd,
  s_skip
};

static char *stateNames[] = {
  "s_block2",
  "s_block3",
  "s_block4",
  "s_block5",
  "s_block6",
  "s_cmd",
  "s_skip"
};

static enum states _FSM_state = s_cmd;

static int block2_state_scheduler() {
  int res;
  
  if (isSchedulable_B2()) {
    if (hasRoom(splitter_MV_Y, 2)) {
      B2();
      _FSM_state = s_block3;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_skipbtype()) {
      skipbtype();
      _FSM_state = s_block3;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int block3_state_scheduler() {
  int res;
  
  if (isSchedulable_B3()) {
    if (hasRoom(splitter_MV_Y, 2)) {
      B3();
      _FSM_state = s_block4;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_skipbtype()) {
      skipbtype();
      _FSM_state = s_block4;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int block4_state_scheduler() {
  int res;
  
  if (isSchedulable_B4()) {
    if (hasRoom(splitter_MV_Y, 2)) {
      B4();
      _FSM_state = s_block5;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_skipbtype()) {
      skipbtype();
      _FSM_state = s_block5;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int block5_state_scheduler() {
  int res;
  
  if (isSchedulable_B5()) {
    if (hasRoom(splitter_MV_U, 2)) {
      B5();
      _FSM_state = s_block6;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_skipbtype()) {
      skipbtype();
      _FSM_state = s_block6;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int block6_state_scheduler() {
  int res;
  
  if (isSchedulable_B6()) {
    if (hasRoom(splitter_MV_V, 2)) {
      B6();
      _FSM_state = s_cmd;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_skipbtype()) {
      skipbtype();
      _FSM_state = s_cmd;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int cmd_state_scheduler() {
  int res;
  
  if (isSchedulable_newvop()) {
    newvop();
    _FSM_state = s_skip;
    res = 1;
  } else {
    if (isSchedulable_skipbtype()) {
      skipbtype();
      _FSM_state = s_block2;
      res = 1;
    } else {
      if (isSchedulable_B1()) {
        if (hasRoom(splitter_MV_Y, 2)) {
          B1();
          _FSM_state = s_block2;
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

static int skip_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

int splitter_MV_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_block2:
        res = block2_state_scheduler();
        break;
      case s_block3:
        res = block3_state_scheduler();
        break;
      case s_block4:
        res = block4_state_scheduler();
        break;
      case s_block5:
        res = block5_state_scheduler();
        break;
      case s_block6:
        res = block6_state_scheduler();
        break;
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

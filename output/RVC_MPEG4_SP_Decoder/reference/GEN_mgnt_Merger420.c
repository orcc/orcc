// Generated from "GEN_mgnt_Merger420"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *GEN_mgnt_Merger420_Y;
extern struct fifo_s *GEN_mgnt_Merger420_U;
extern struct fifo_s *GEN_mgnt_Merger420_V;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *GEN_mgnt_Merger420_YUV;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int PIX_SZ = 9;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void Y() {
  short *YUV;
  short *Y;
  short y[256];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  
  YUV = getWritePtr(GEN_mgnt_Merger420_YUV, 256);
  Y = getReadPtr(GEN_mgnt_Merger420_Y, 256);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 256) {
    _tmp0_1 = Y[1 * _i0_3 + 0];
    y[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 256) {
    _tmp1_1 = y[_i1_3];
    YUV[1 * _i1_3 + 0] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_Y() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(GEN_mgnt_Merger420_Y, 256);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void U() {
  short *YUV;
  short *U;
  short u[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  
  YUV = getWritePtr(GEN_mgnt_Merger420_YUV, 64);
  U = getReadPtr(GEN_mgnt_Merger420_U, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = U[1 * _i0_3 + 0];
    u[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp1_1 = u[_i1_3];
    YUV[1 * _i1_3 + 0] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_U() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(GEN_mgnt_Merger420_U, 64);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void V() {
  short *YUV;
  short *V;
  short v[64];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int _i1_1;
  short _tmp1_1;
  int _i1_2;
  int _i1_3;
  
  YUV = getWritePtr(GEN_mgnt_Merger420_YUV, 64);
  V = getReadPtr(GEN_mgnt_Merger420_V, 64);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 64) {
    _tmp0_1 = V[1 * _i0_3 + 0];
    v[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 64) {
    _tmp1_1 = v[_i1_3];
    YUV[1 * _i1_3 + 0] = _tmp1_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_V() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(GEN_mgnt_Merger420_V, 64);
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
  s_U = 0,
  s_V,
  s_Y
};

static char *stateNames[] = {
  "s_U",
  "s_V",
  "s_Y"
};

static enum states _FSM_state = s_Y;

static int U_state_scheduler() {
  int res;
  
  if (isSchedulable_U()) {
    if (hasRoom(GEN_mgnt_Merger420_YUV, 64)) {
      U();
      _FSM_state = s_V;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int V_state_scheduler() {
  int res;
  
  if (isSchedulable_V()) {
    if (hasRoom(GEN_mgnt_Merger420_YUV, 64)) {
      V();
      _FSM_state = s_Y;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int Y_state_scheduler() {
  int res;
  
  if (isSchedulable_Y()) {
    if (hasRoom(GEN_mgnt_Merger420_YUV, 256)) {
      Y();
      _FSM_state = s_U;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

int GEN_mgnt_Merger420_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_U:
        res = U_state_scheduler();
        break;
      case s_V:
        res = V_state_scheduler();
        break;
      case s_Y:
        res = Y_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

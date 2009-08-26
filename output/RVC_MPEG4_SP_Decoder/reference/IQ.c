// Generated from "IQ"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *IQ_DC;
extern struct fifo_s *IQ_AC;
extern struct fifo_s *IQ_QP;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *IQ_OUT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int QUANT_SZ = 6;
static int SAMPLE_SZ = 13;
static char count;
static char quant;
static char round;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int saturate(int x) {
  int minus0_1;
  int plus0_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  int _tmp0_4;
  int _tmp0_5;
  
  minus0_1 = x < -2048;
  plus0_1 = x > 2047;
  if (minus0_1) {
    _tmp0_1 = -2048;
    _tmp0_2 = _tmp0_1;
  } else {
    if (plus0_1) {
      _tmp0_3 = 2047;
      _tmp0_4 = _tmp0_3;
    } else {
      _tmp0_5 = x;
      _tmp0_4 = _tmp0_5;
    }
    _tmp0_2 = _tmp0_4;
  }
  return _tmp0_2;
}

static int abs_(int x) {
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  if (x < 0) {
    _tmp0_1 = -x;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = x;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void get_qp() {
  short *OUT;
  char *QP;
  short *DC;
  char q_1;
  short i_1;
  
  OUT = getWritePtr(IQ_OUT, 1);
  QP = getReadPtr(IQ_QP, 1);
  q_1 = QP[0];
  DC = getReadPtr(IQ_DC, 1);
  i_1 = DC[0];
  quant = q_1;
  round = q_1 & 1 ^ 1;
  count = 0;
  OUT[0] = i_1;
}

static int isSchedulable_get_qp() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(IQ_QP, 1);
  _tmp2_1 = hasTokens(IQ_DC, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void ac() {
  short *OUT;
  short *AC;
  short i[63];
  short v0[63];
  short o0[63];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  int j0_1;
  char _tmp1_1;
  short _tmp2_1;
  int _tmp3_1;
  char _tmp4_1;
  int j0_2;
  int j0_3;
  int k0_1;
  short _tmp5_1;
  int _tmp6_1;
  int _tmp6_2;
  short _tmp7_1;
  short _tmp7_2;
  short _tmp8_1;
  short _tmp8_2;
  int _tmp9_1;
  int _tmp9_2;
  int _tmp6_3;
  int _tmp6_4;
  short _tmp10_1;
  short _tmp10_2;
  int _tmp11_1;
  int _tmp11_2;
  int _tmp6_5;
  short _tmp8_3;
  int _tmp9_3;
  short _tmp10_3;
  int _tmp11_3;
  short _tmp7_3;
  short _tmp8_4;
  int _tmp9_4;
  short _tmp10_4;
  int _tmp11_4;
  int k0_2;
  int k0_3;
  int _i1_1;
  short _tmp12_1;
  int _i1_2;
  int _i1_3;
  
  OUT = getWritePtr(IQ_OUT, 63);
  AC = getReadPtr(IQ_AC, 63);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 63) {
    _tmp0_1 = AC[1 * _i0_3 + 0];
    i[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  j0_1 = 0;
  j0_3 = j0_1;
  while (j0_3 < 63) {
    _tmp1_1 = quant;
    _tmp2_1 = i[j0_3];
    _tmp3_1 = abs_(_tmp2_1);
    _tmp4_1 = round;
    v0[j0_3] = _tmp1_1 * ((_tmp3_1 << 1) + 1) - _tmp4_1;
    j0_2 = j0_3 + 1;
    j0_3 = j0_2;
  }
  k0_1 = 0;
  _tmp7_3 = 0;
  _tmp8_4 = 0;
  _tmp9_4 = 0;
  _tmp10_4 = 0;
  _tmp11_4 = 0;
  k0_3 = k0_1;
  while (k0_3 < 63) {
    _tmp5_1 = i[k0_3];
    if (_tmp5_1 == 0) {
      _tmp6_1 = 0;
      _tmp6_2 = _tmp6_1;
      _tmp7_2 = _tmp7_3;
      _tmp8_3 = _tmp8_4;
      _tmp9_3 = _tmp9_4;
      _tmp10_3 = _tmp10_4;
      _tmp11_3 = _tmp11_4;
    } else {
      _tmp7_1 = i[k0_3];
      if (_tmp7_1 < 0) {
        _tmp8_1 = v0[k0_3];
        _tmp9_1 = saturate(-_tmp8_1);
        _tmp6_3 = _tmp9_1;
        _tmp8_2 = _tmp8_1;
        _tmp9_2 = _tmp9_1;
        _tmp6_4 = _tmp6_3;
        _tmp10_2 = _tmp10_4;
        _tmp11_2 = _tmp11_4;
      } else {
        _tmp10_1 = v0[k0_3];
        _tmp11_1 = saturate(_tmp10_1);
        _tmp6_5 = _tmp11_1;
        _tmp8_2 = _tmp8_4;
        _tmp9_2 = _tmp9_4;
        _tmp6_4 = _tmp6_5;
        _tmp10_2 = _tmp10_1;
        _tmp11_2 = _tmp11_1;
      }
      _tmp6_2 = _tmp6_4;
      _tmp7_2 = _tmp7_1;
      _tmp8_3 = _tmp8_2;
      _tmp9_3 = _tmp9_2;
      _tmp10_3 = _tmp10_2;
      _tmp11_3 = _tmp11_2;
    }
    o0[k0_3] = _tmp6_2;
    k0_2 = k0_3 + 1;
    _tmp7_3 = _tmp7_2;
    _tmp8_4 = _tmp8_3;
    _tmp9_4 = _tmp9_3;
    _tmp10_4 = _tmp10_3;
    _tmp11_4 = _tmp11_3;
    k0_3 = k0_2;
  }
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 63) {
    _tmp12_1 = o0[_i1_3];
    OUT[1 * _i1_3 + 0] = _tmp12_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_ac() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(IQ_AC, 63);
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
  s_ac = 0,
  s_start
};

static char *stateNames[] = {
  "s_ac",
  "s_start"
};

static enum states _FSM_state = s_start;

static int ac_state_scheduler() {
  int res;
  
  if (isSchedulable_ac()) {
    if (hasRoom(IQ_OUT, 63)) {
      ac();
      _FSM_state = s_start;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int start_state_scheduler() {
  int res;
  
  if (isSchedulable_get_qp()) {
    if (hasRoom(IQ_OUT, 1)) {
      get_qp();
      _FSM_state = s_ac;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

int IQ_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_ac:
        res = ac_state_scheduler();
        break;
      case s_start:
        res = start_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

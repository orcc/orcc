// Generated from "dequant"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *dequant_DC;
extern struct fifo_s *dequant_AC;
extern struct fifo_s *dequant_QP;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *dequant_OUT;

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
  
  OUT = getWritePtr(dequant_OUT, 1);
  QP = getReadPtr(dequant_QP, 1);
  q_1 = QP[0];
  DC = getReadPtr(dequant_DC, 1);
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
  
  _tmp1_1 = hasTokens(dequant_QP, 1);
  _tmp2_1 = hasTokens(dequant_DC, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void done() {
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = count;
  _tmp0_1 = _tmp1_1 == 63;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void ac() {
  short *OUT;
  short *AC;
  short i_1;
  char _tmp0_1;
  int _tmp1_1;
  char _tmp2_1;
  short v0_1;
  short o0_1;
  short o0_2;
  short o0_3;
  short o0_4;
  short o0_5;
  int _tmp4_1;
  
  OUT = getWritePtr(dequant_OUT, 1);
  AC = getReadPtr(dequant_AC, 1);
  i_1 = AC[0];
  _tmp0_1 = quant;
  _tmp1_1 = abs_(i_1);
  _tmp2_1 = round;
  v0_1 = _tmp0_1 * ((_tmp1_1 << 1) + 1) - _tmp2_1;
  if (i_1 == 0) {
    o0_1 = 0;
    o0_2 = o0_1;
  } else {
    if (i_1 < 0) {
      o0_3 = -v0_1;
      o0_4 = o0_3;
    } else {
      o0_5 = v0_1;
      o0_4 = o0_5;
    }
    o0_2 = o0_4;
  }
  count++;
  _tmp4_1 = saturate(o0_2);
  OUT[0] = _tmp4_1;
}

static int isSchedulable_ac() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(dequant_AC, 1);
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
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_start;
    res = 1;
  } else {
    if (isSchedulable_ac()) {
      if (hasRoom(dequant_OUT, 1)) {
        ac();
        _FSM_state = s_ac;
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

static int start_state_scheduler() {
  int res;
  
  if (isSchedulable_get_qp()) {
    if (hasRoom(dequant_OUT, 1)) {
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

int dequant_scheduler() {
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

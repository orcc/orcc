// Generated from "invpred_02"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *invpred_02_BTYPE;
extern struct fifo_s *invpred_02_A;
extern struct fifo_s *invpred_02_B;
extern struct fifo_s *invpred_02_C;
extern struct fifo_s *invpred_02_QFS_DC;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *invpred_02_QF_DC;
extern struct fifo_s *invpred_02_PTR;
extern struct fifo_s *invpred_02_AC_PRED_DIR;
extern struct fifo_s *invpred_02_SIGNED;
extern struct fifo_s *invpred_02_QUANT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int DCVAL = 1024;
static int MAXW_IN_MB = 121;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int SAMPLE_SZ = 13;
static int NEWVOP = 2048;
static int INTRA = 1024;
static int INTER = 512;
static int QUANT_MASK = 31;
static int ACCODED = 2;
static int ACPRED = 1;
static int QUANT_SZ = 6;
static int SCALER_SZ = 7;
static char QP;
static char round;
static int BUF_SIZE = 492;
static int ptr = 4;
static char comp = 0;
static short dc_buf[492] = {1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 
1024, 1024, 1024, 1024, 1024, 1024, 1024};
static short dc_pred;
static char scaler;
static int is_signed;
static short dc_val;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

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

static char dc_scaler() {
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  char _tmp2_2;
  char _tmp3_1;
  char _tmp4_1;
  char _tmp5_1;
  char _tmp2_3;
  char _tmp2_4;
  char _tmp6_1;
  char _tmp7_1;
  char _tmp8_1;
  char _tmp2_5;
  char _tmp2_6;
  char _tmp9_1;
  char _tmp2_7;
  
  _tmp0_1 = QP;
  _tmp1_1 = QP;
  if (_tmp0_1 > 0 && _tmp1_1 < 5) {
    _tmp2_1 = 8;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp3_1 = QP;
    _tmp4_1 = QP;
    if (_tmp3_1 > 4 && _tmp4_1 < 9) {
      _tmp5_1 = QP;
      _tmp2_3 = 2 * _tmp5_1;
      _tmp2_4 = _tmp2_3;
    } else {
      _tmp6_1 = QP;
      _tmp7_1 = QP;
      if (_tmp6_1 > 8 && _tmp7_1 < 25) {
        _tmp8_1 = QP;
        _tmp2_5 = _tmp8_1 + 8;
        _tmp2_6 = _tmp2_5;
      } else {
        _tmp9_1 = QP;
        _tmp2_7 = 2 * _tmp9_1 - 16;
        _tmp2_6 = _tmp2_7;
      }
      _tmp2_4 = _tmp2_6;
    }
    _tmp2_2 = _tmp2_4;
  }
  return _tmp2_2;
}

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


////////////////////////////////////////////////////////////////////////////////
// Actions

static void start() {
  char *AC_PRED_DIR;
  short *BTYPE;
  short cmd_1;
  char _tmp1_1;
  
  AC_PRED_DIR = getWritePtr(invpred_02_AC_PRED_DIR, 1);
  BTYPE = getReadPtr(invpred_02_BTYPE, 1);
  cmd_1 = BTYPE[0];
  comp = 0;
  ptr = 4;
  QP = cmd_1 & 31;
  _tmp1_1 = QP;
  round = _tmp1_1 & 1 ^ 1;
  AC_PRED_DIR[0] = -2;
}

static int isSchedulable_start() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(invpred_02_BTYPE, 1);
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
  
  BTYPE = getReadPtr(invpred_02_BTYPE, 1);
}

static int isSchedulable_skip() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_inter_ac() {
  char *QUANT;
  int *SIGNED;
  short *PTR;
  char *AC_PRED_DIR;
  short *BTYPE;
  char _tmp0_1;
  
  AC_PRED_DIR = getWritePtr(invpred_02_AC_PRED_DIR, 1);
  PTR = getWritePtr(invpred_02_PTR, 1);
  SIGNED = getWritePtr(invpred_02_SIGNED, 1);
  QUANT = getWritePtr(invpred_02_QUANT, 1);
  BTYPE = getReadPtr(invpred_02_BTYPE, 1);
  is_signed = 1;
  AC_PRED_DIR[0] = 0;
  PTR[0] = 0;
  SIGNED[0] = 1;
  _tmp0_1 = QP;
  QUANT[0] = _tmp0_1;
}

static int isSchedulable_read_inter_ac() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(invpred_02_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 512) != 0 && (cmd_1 & 2) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_other() {
  char *AC_PRED_DIR;
  short *BTYPE;
  
  AC_PRED_DIR = getWritePtr(invpred_02_AC_PRED_DIR, 1);
  BTYPE = getReadPtr(invpred_02_BTYPE, 1);
  AC_PRED_DIR[0] = -1;
}

static int isSchedulable_read_other() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(invpred_02_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 2) == 0 && (cmd_1 & 1024) == 0 && (cmd_1 & 2048) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_intra() {
  char *QUANT;
  int *SIGNED;
  char *AC_PRED_DIR;
  short *PTR;
  short *BTYPE;
  short *A;
  short *B;
  short *C;
  short cmd_1;
  short a_1;
  short b_1;
  short c_1;
  short _tmp0_1;
  short dca0_1;
  short _tmp1_1;
  short dcb0_1;
  short _tmp2_1;
  short dcc0_1;
  short horiz0_1;
  short vert0_1;
  int top0_1;
  int ac0_1;
  char _tmp4_1;
  short _tmp5_1;
  short _tmp5_2;
  short _tmp5_3;
  char _tmp6_1;
  char _tmp7_1;
  char s0_1;
  char s0_2;
  char s0_3;
  char s0_4;
  char s0_5;
  char _tmp8_1;
  short _tmp9_1;
  short _tmp9_2;
  short _tmp9_3;
  int _tmp10_1;
  char _tmp11_1;
  
  PTR = getWritePtr(invpred_02_PTR, 1);
  AC_PRED_DIR = getWritePtr(invpred_02_AC_PRED_DIR, 1);
  SIGNED = getWritePtr(invpred_02_SIGNED, 1);
  QUANT = getWritePtr(invpred_02_QUANT, 1);
  BTYPE = getReadPtr(invpred_02_BTYPE, 1);
  cmd_1 = BTYPE[0];
  A = getReadPtr(invpred_02_A, 1);
  a_1 = A[0];
  B = getReadPtr(invpred_02_B, 1);
  b_1 = B[0];
  C = getReadPtr(invpred_02_C, 1);
  c_1 = C[0];
  _tmp0_1 = dc_buf[a_1];
  dca0_1 = _tmp0_1;
  _tmp1_1 = dc_buf[b_1];
  dcb0_1 = _tmp1_1;
  _tmp2_1 = dc_buf[c_1];
  dcc0_1 = _tmp2_1;
  horiz0_1 = abs_(dcb0_1 - dcc0_1);
  vert0_1 = abs_(dca0_1 - dcb0_1);
  top0_1 = vert0_1 < horiz0_1;
  ac0_1 = (cmd_1 & 1) != 0;
  _tmp4_1 = dc_scaler();
  scaler = _tmp4_1;
  if (top0_1) {
    _tmp5_1 = dcc0_1;
    _tmp5_2 = _tmp5_1;
  } else {
    _tmp5_3 = dca0_1;
    _tmp5_2 = _tmp5_3;
  }
  _tmp6_1 = scaler;
  _tmp7_1 = scaler;
  dc_pred = (_tmp5_2 + (_tmp6_1 >> 1)) / _tmp7_1;
  if (!ac0_1) {
    s0_1 = 0;
    s0_2 = s0_1;
  } else {
    if (top0_1) {
      s0_3 = 2;
      s0_4 = s0_3;
    } else {
      s0_5 = 1;
      s0_4 = s0_5;
    }
    s0_2 = s0_4;
  }
  _tmp8_1 = scaler;
  is_signed = _tmp8_1 == 0;
  if (top0_1) {
    _tmp9_1 = c_1;
    _tmp9_2 = _tmp9_1;
  } else {
    _tmp9_3 = a_1;
    _tmp9_2 = _tmp9_3;
  }
  PTR[0] = _tmp9_2;
  AC_PRED_DIR[0] = s0_2;
  _tmp10_1 = is_signed;
  SIGNED[0] = _tmp10_1;
  _tmp11_1 = QP;
  QUANT[0] = _tmp11_1;
}

static int isSchedulable_read_intra() {
  short *C;
  short *B;
  short *A;
  short *BTYPE;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_BTYPE, 1);
  _tmp2_1 = hasTokens(invpred_02_A, 1);
  _tmp3_1 = hasTokens(invpred_02_B, 1);
  _tmp4_1 = hasTokens(invpred_02_C, 1);
  if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
    BTYPE = getPeekPtr(invpred_02_BTYPE, 1);
    cmd_1 = BTYPE[0];
    A = getPeekPtr(invpred_02_A, 1);
    B = getPeekPtr(invpred_02_B, 1);
    C = getPeekPtr(invpred_02_C, 1);
    _tmp0_1 = (cmd_1 & 1024) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getdc_inter() {
  short *QFS_DC;
  short dc_1;
  char _tmp0_1;
  int _tmp1_1;
  char _tmp2_1;
  short v0_1;
  short _tmp3_1;
  short _tmp3_2;
  int _tmp4_1;
  short _tmp3_3;
  short _tmp3_4;
  short _tmp3_5;
  short _tmp3_6;
  short _tmp3_7;
  
  QFS_DC = getReadPtr(invpred_02_QFS_DC, 1);
  dc_1 = QFS_DC[0];
  _tmp0_1 = QP;
  _tmp1_1 = abs_(dc_1);
  _tmp2_1 = round;
  v0_1 = _tmp0_1 * ((_tmp1_1 << 1) + 1) - _tmp2_1;
  if (dc_1 == 0) {
    _tmp3_1 = 0;
    _tmp3_2 = _tmp3_1;
  } else {
    _tmp4_1 = is_signed;
    if (!_tmp4_1) {
      _tmp3_3 = dc_1;
      _tmp3_4 = _tmp3_3;
    } else {
      if (dc_1 < 0) {
        _tmp3_5 = -v0_1;
        _tmp3_6 = _tmp3_5;
      } else {
        _tmp3_7 = v0_1;
        _tmp3_6 = _tmp3_7;
      }
      _tmp3_4 = _tmp3_6;
    }
    _tmp3_2 = _tmp3_4;
  }
  dc_val = _tmp3_2;
}

static int isSchedulable_getdc_inter() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_QFS_DC, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getdc_intra() {
  short *QFS_DC;
  short dc_1;
  short _tmp0_1;
  char _tmp1_1;
  
  QFS_DC = getReadPtr(invpred_02_QFS_DC, 1);
  dc_1 = QFS_DC[0];
  _tmp0_1 = dc_pred;
  _tmp1_1 = scaler;
  dc_val = (dc_1 + _tmp0_1) * _tmp1_1;
}

static int isSchedulable_getdc_intra() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(invpred_02_QFS_DC, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void sat() {
  short *QF_DC;
  short _tmp0_1;
  short dc0_1;
  int _tmp1_1;
  char _tmp2_1;
  
  QF_DC = getWritePtr(invpred_02_QF_DC, 1);
  _tmp0_1 = dc_val;
  dc0_1 = saturate(_tmp0_1);
  _tmp1_1 = ptr;
  _tmp2_1 = comp;
  dc_buf[_tmp1_1 | _tmp2_1] = dc0_1;
  QF_DC[0] = dc0_1;
}

static int isSchedulable_sat() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void advance() {
  char _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp3_2;
  int _tmp5_1;
  int _tmp3_3;
  
  comp++;
  _tmp1_1 = comp;
  if (_tmp1_1 == 4) {
    comp = 0;
    _tmp2_1 = ptr;
    if (_tmp2_1 == 4) {
      _tmp3_1 = 488;
      _tmp3_2 = _tmp3_1;
    } else {
      _tmp5_1 = ptr;
      _tmp3_3 = _tmp5_1 - 4;
      _tmp3_2 = _tmp3_3;
    }
    ptr = _tmp3_2;
  }
}

static int isSchedulable_advance() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_advance = 0,
  s_geth,
  s_getw,
  s_inter,
  s_intra,
  s_read,
  s_sat
};

static char *stateNames[] = {
  "s_advance",
  "s_geth",
  "s_getw",
  "s_inter",
  "s_intra",
  "s_read",
  "s_sat"
};

static enum states _FSM_state = s_read;

static int advance_state_scheduler() {
  int res;
  
  if (isSchedulable_advance()) {
    advance();
    _FSM_state = s_read;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_read;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int getw_state_scheduler() {
  int res;
  
  if (isSchedulable_skip()) {
    skip();
    _FSM_state = s_geth;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int inter_state_scheduler() {
  int res;
  
  if (isSchedulable_getdc_inter()) {
    getdc_inter();
    _FSM_state = s_sat;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int intra_state_scheduler() {
  int res;
  
  if (isSchedulable_getdc_intra()) {
    getdc_intra();
    _FSM_state = s_sat;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int read_state_scheduler() {
  int res;
  
  if (isSchedulable_start()) {
    if (hasRoom(invpred_02_AC_PRED_DIR, 1)) {
      start();
      _FSM_state = s_getw;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_read_inter_ac()) {
      if (hasRoom(invpred_02_AC_PRED_DIR, 1) && hasRoom(invpred_02_PTR, 1) && hasRoom(invpred_02_QUANT, 1) && hasRoom(invpred_02_SIGNED, 1)) {
        read_inter_ac();
        _FSM_state = s_inter;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_read_other()) {
        if (hasRoom(invpred_02_AC_PRED_DIR, 1)) {
          read_other();
          _FSM_state = s_advance;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        if (isSchedulable_read_intra()) {
          if (hasRoom(invpred_02_AC_PRED_DIR, 1) && hasRoom(invpred_02_PTR, 1) && hasRoom(invpred_02_QUANT, 1) && hasRoom(invpred_02_SIGNED, 1)) {
            read_intra();
            _FSM_state = s_intra;
            res = 1;
          } else {
            res = 0;
          }
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int sat_state_scheduler() {
  int res;
  
  if (isSchedulable_sat()) {
    if (hasRoom(invpred_02_QF_DC, 1)) {
      sat();
      _FSM_state = s_advance;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

int invpred_02_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_advance:
        res = advance_state_scheduler();
        break;
      case s_geth:
        res = geth_state_scheduler();
        break;
      case s_getw:
        res = getw_state_scheduler();
        break;
      case s_inter:
        res = inter_state_scheduler();
        break;
      case s_intra:
        res = intra_state_scheduler();
        break;
      case s_read:
        res = read_state_scheduler();
        break;
      case s_sat:
        res = sat_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

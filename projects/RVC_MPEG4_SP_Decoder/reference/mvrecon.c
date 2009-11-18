// Generated from "mvrecon"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *mvrecon_BTYPE;
extern struct fifo_s *mvrecon_MVIN;
extern struct fifo_s *mvrecon_A;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *mvrecon_MV;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MAXW_IN_MB = 121;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int MV_SZ = 9;
static int NEWVOP = 2048;
static int INTER = 512;
static int FCODE_MASK = 448;
static int FCODE_SHIFT = 6;
static int FOURMV = 4;
static int MOTION = 8;
static char mbx = 0;
static int top_edge = 1;
static char comp = 0;
static char mbwidth = 0;
static int BUF_SIZE = 968;
static char Y_SELECT = 4;
static short ptr;
static short buf[968] = {0};
static short mv_rsize;
static short mv_range;
static short mv_low;
static short mv_high;
static int fourmv;
static short aptr;
static short bptr;
static short cptr;
static short apred;
static short bpred;
static short cpred;
static short mag;
static short mv_x;
static short mv_y;
static short pred_x;
static short pred_y;
static short sum_x;
static short sum_y;
static int x_flag;
static short res_shift;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int middle(int a, int b, int c) {
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  int _tmp0_4;
  int _tmp0_5;
  int _tmp0_6;
  int _tmp0_7;
  int _tmp0_8;
  int _tmp0_9;
  int _tmp0_10;
  int _tmp0_11;
  
  if (a < b) {
    if (a > c) {
      _tmp0_1 = a;
      _tmp0_2 = _tmp0_1;
    } else {
      if (b < c) {
        _tmp0_3 = b;
        _tmp0_4 = _tmp0_3;
      } else {
        _tmp0_5 = c;
        _tmp0_4 = _tmp0_5;
      }
      _tmp0_2 = _tmp0_4;
    }
    _tmp0_6 = _tmp0_2;
  } else {
    if (b > c) {
      _tmp0_7 = b;
      _tmp0_8 = _tmp0_7;
    } else {
      if (a < c) {
        _tmp0_9 = a;
        _tmp0_10 = _tmp0_9;
      } else {
        _tmp0_11 = c;
        _tmp0_10 = _tmp0_11;
      }
      _tmp0_8 = _tmp0_10;
    }
    _tmp0_6 = _tmp0_8;
  }
  return _tmp0_6;
}

static int mvcalc(int pred, int mv_mag, int mag_shift) {
  short _tmp0_1;
  int _tmp1_1;
  int _tmp1_2;
  int _tmp1_3;
  int _tmp1_4;
  int _tmp1_5;
  
  _tmp0_1 = mv_rsize;
  if (_tmp0_1 == 0 || mv_mag == 0) {
    _tmp1_1 = pred + mv_mag;
    _tmp1_2 = _tmp1_1;
  } else {
    if (mv_mag < 0) {
      _tmp1_3 = pred - mag_shift;
      _tmp1_4 = _tmp1_3;
    } else {
      _tmp1_5 = pred + mag_shift;
      _tmp1_4 = _tmp1_5;
    }
    _tmp1_2 = _tmp1_4;
  }
  return _tmp1_2;
}

static int mvclip(int v) {
  short _tmp0_1;
  short _tmp2_1;
  int _tmp1_1;
  int _tmp1_2;
  short _tmp3_1;
  short _tmp4_1;
  int _tmp1_3;
  int _tmp1_4;
  int _tmp1_5;
  
  _tmp0_1 = mv_low;
  if (v < _tmp0_1) {
    _tmp2_1 = mv_range;
    _tmp1_1 = v + _tmp2_1;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp3_1 = mv_high;
    if (v > _tmp3_1) {
      _tmp4_1 = mv_range;
      _tmp1_3 = v - _tmp4_1;
      _tmp1_4 = _tmp1_3;
    } else {
      _tmp1_5 = v;
      _tmp1_4 = _tmp1_5;
    }
    _tmp1_2 = _tmp1_4;
  }
  return _tmp1_2;
}

static int uvclip_1(int v) {
  int vv0_1;
  unsigned char _tmp0_1;
  unsigned char _tmp0_2;
  unsigned char _tmp0_3;
  
  vv0_1 = v >> 1;
  if ((v & 3) == 0) {
    _tmp0_1 = 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 1;
    _tmp0_2 = _tmp0_3;
  }
  return vv0_1 | _tmp0_2;
}

static int uvclip_4(int v) {
  int sign0_1;
  int absv0_1;
  int absv0_2;
  int absv0_3;
  int resv0_1;
  int delta0_1;
  int delta0_2;
  int delta0_3;
  int delta0_4;
  int delta0_5;
  int vv0_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  sign0_1 = v < 0;
  if (sign0_1) {
    absv0_1 = -v;
    absv0_2 = absv0_1;
  } else {
    absv0_3 = v;
    absv0_2 = absv0_3;
  }
  resv0_1 = absv0_2 - ((absv0_2 >> 4) << 4);
  if (resv0_1 < 3) {
    delta0_1 = 0;
    delta0_2 = delta0_1;
  } else {
    if (resv0_1 > 13) {
      delta0_3 = 2;
      delta0_4 = delta0_3;
    } else {
      delta0_5 = 1;
      delta0_4 = delta0_5;
    }
    delta0_2 = delta0_4;
  }
  vv0_1 = ((absv0_2 >> 4) << 1) + delta0_2;
  if (sign0_1) {
    _tmp0_1 = -vv0_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = vv0_1;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void start() {
  short *BTYPE;
  short cmd_1;
  char fcode0_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  
  BTYPE = getReadPtr(mvrecon_BTYPE, 1);
  cmd_1 = BTYPE[0];
  fcode0_1 = (cmd_1 & 448) >> 6;
  mbx = 0;
  top_edge = 1;
  comp = 0;
  if (fcode0_1 > 0) {
    mv_rsize = fcode0_1 - 1;
    _tmp2_1 = mv_rsize;
    mv_range = 1 << (_tmp2_1 + 5);
    _tmp3_1 = mv_range;
    mv_low = -_tmp3_1;
    _tmp4_1 = mv_range;
    mv_high = _tmp4_1 - 1;
    _tmp5_1 = mv_range;
    mv_range = _tmp5_1 << 1;
  }
}

static int isSchedulable_start() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvrecon_BTYPE, 1);
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
  
  BTYPE = getReadPtr(mvrecon_BTYPE, 1);
  w_1 = BTYPE[0];
  mbwidth = w_1;
  ptr = 8;
}

static int isSchedulable_getw() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_BTYPE, 1);
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
  
  BTYPE = getReadPtr(mvrecon_BTYPE, 1);
}

static int isSchedulable_geth() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_noMotion() {
  short *BTYPE;
  short _tmp0_1;
  char _tmp1_1;
  short p0_1;
  char _tmp2_1;
  
  BTYPE = getReadPtr(mvrecon_BTYPE, 1);
  _tmp0_1 = ptr;
  _tmp1_1 = comp;
  p0_1 = _tmp0_1 | _tmp1_1;
  _tmp2_1 = comp;
  if (_tmp2_1 < 4) {
    buf[p0_1] = 0;
    buf[p0_1 | 4] = 0;
  }
}

static int isSchedulable_read_noMotion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvrecon_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = ((cmd_1 & 512) == 0 || (cmd_1 & 8) == 0) && (cmd_1 & 2048) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_motion() {
  short *BTYPE;
  short cmd_1;
  char _tmp0_1;
  
  BTYPE = getReadPtr(mvrecon_BTYPE, 1);
  cmd_1 = BTYPE[0];
  _tmp0_1 = comp;
  if (_tmp0_1 == 0) {
    fourmv = (cmd_1 & 4) != 0;
    sum_x = 0;
    sum_y = 0;
  }
}

static int isSchedulable_read_motion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(mvrecon_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 512) != 0 && (cmd_1 & 8) != 0 && (cmd_1 & 2048) == 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void compute_done() {
}

static int isSchedulable_compute_done() {
  char _tmp1_1;
  char _tmp2_1;
  int _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = comp;
  _tmp2_1 = comp;
  _tmp3_1 = fourmv;
  _tmp0_1 = _tmp1_1 > 3 || _tmp2_1 != 0 && !_tmp3_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void compute_start() {
  short *A;
  short a_1;
  short _tmp0_1;
  short _tmp1_1;
  
  A = getReadPtr(mvrecon_A, 1);
  a_1 = A[0];
  _tmp0_1 = bptr;
  aptr = _tmp0_1;
  _tmp1_1 = cptr;
  bptr = _tmp1_1;
  cptr = a_1;
}

static int isSchedulable_compute_start() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_A, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void get_pred() {
  short _tmp0_1;
  short t0_1;
  short _tmp1_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  
  _tmp0_1 = aptr;
  t0_1 = _tmp0_1;
  _tmp1_1 = bpred;
  apred = _tmp1_1;
  _tmp2_1 = cpred;
  bpred = _tmp2_1;
  _tmp3_1 = buf[t0_1];
  cpred = _tmp3_1;
  _tmp4_1 = bptr;
  aptr = _tmp4_1;
  _tmp5_1 = cptr;
  bptr = _tmp5_1;
  cptr = t0_1 | 4;
}

static int isSchedulable_get_pred() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void do_pred() {
  char _tmp0_1;
  int _tmp1_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  int _tmp5_1;
  
  _tmp0_1 = comp;
  _tmp1_1 = top_edge;
  if (_tmp0_1 >= 2 || !_tmp1_1) {
    _tmp2_1 = apred;
    _tmp3_1 = bpred;
    _tmp4_1 = cpred;
    _tmp5_1 = middle(_tmp2_1, _tmp3_1, _tmp4_1);
    apred = _tmp5_1;
  }
}

static int isSchedulable_do_pred() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_mag() {
  short *MVIN;
  short m_1;
  
  MVIN = getReadPtr(mvrecon_MVIN, 1);
  m_1 = MVIN[0];
  mag = m_1;
}

static int isSchedulable_get_mag() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_MVIN, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void get_residual_init() {
  short _tmp0_1;
  short _tmp2_1;
  int _tmp1_1;
  int _tmp1_2;
  short _tmp3_1;
  int _tmp1_3;
  
  _tmp0_1 = mag;
  if (_tmp0_1 < 0) {
    _tmp2_1 = mag;
    _tmp1_1 = ~_tmp2_1;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp3_1 = mag;
    _tmp1_3 = _tmp3_1 - 1;
    _tmp1_2 = _tmp1_3;
  }
  res_shift = _tmp1_2;
}

static int isSchedulable_get_residual_init() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_residual_shift() {
  short _tmp0_1;
  short count0_1;
  short _tmp1_1;
  short count0_2;
  short count0_3;
  
  _tmp0_1 = mv_rsize;
  count0_1 = _tmp0_1;
  count0_3 = count0_1;
  while (count0_3 > 0) {
    _tmp1_1 = res_shift;
    res_shift = _tmp1_1 << 1;
    count0_2 = count0_3 - 1;
    count0_3 = count0_2;
  }
}

static int isSchedulable_get_residual_shift() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_residual_adjust() {
  short *MVIN;
  short s_1;
  short _tmp0_1;
  
  MVIN = getReadPtr(mvrecon_MVIN, 1);
  s_1 = MVIN[0];
  _tmp0_1 = res_shift;
  res_shift = _tmp0_1 + s_1 + 1;
}

static int isSchedulable_get_residual_adjust() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(mvrecon_MVIN, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void get_residual_calc() {
  short _tmp0_1;
  short _tmp1_1;
  short _tmp2_1;
  int _tmp3_1;
  
  _tmp0_1 = apred;
  _tmp1_1 = mag;
  _tmp2_1 = res_shift;
  _tmp3_1 = mvcalc(_tmp0_1, _tmp1_1, _tmp2_1);
  res_shift = _tmp3_1;
}

static int isSchedulable_get_residual_calc() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_residual_clip() {
  short _tmp0_1;
  int _tmp1_1;
  
  _tmp0_1 = res_shift;
  _tmp1_1 = mvclip(_tmp0_1);
  res_shift = _tmp1_1;
}

static int isSchedulable_get_residual_clip() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_residual_final() {
  short _tmp0_1;
  short _tmp1_1;
  short sum0_1;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  
  _tmp0_1 = sum_x;
  _tmp1_1 = res_shift;
  sum0_1 = _tmp0_1 + _tmp1_1;
  _tmp2_1 = mv_y;
  mv_x = _tmp2_1;
  _tmp3_1 = res_shift;
  mv_y = _tmp3_1;
  _tmp4_1 = sum_y;
  sum_x = _tmp4_1;
  sum_y = sum0_1;
  x_flag = 1;
}

static int isSchedulable_get_residual_final() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void write_luma() {
  short *MV;
  short _tmp0_1;
  char _tmp1_1;
  short p0_1;
  short _tmp2_1;
  short t0_1;
  int _tmp3_1;
  char _tmp4_1;
  char _tmp4_2;
  char _tmp4_3;
  short _tmp6_1;
  short _tmp7_1;
  int _tmp8_1;
  
  MV = getWritePtr(mvrecon_MV, 1);
  _tmp0_1 = ptr;
  _tmp1_1 = comp;
  p0_1 = _tmp0_1 | _tmp1_1;
  _tmp2_1 = mv_x;
  t0_1 = _tmp2_1;
  _tmp3_1 = x_flag;
  if (_tmp3_1) {
    _tmp4_1 = 0;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp4_3 = 4;
    _tmp4_2 = _tmp4_3;
  }
  _tmp6_1 = mv_x;
  buf[p0_1 | _tmp4_2] = _tmp6_1;
  _tmp7_1 = mv_y;
  mv_x = _tmp7_1;
  mv_y = t0_1;
  _tmp8_1 = x_flag;
  x_flag = !_tmp8_1;
  MV[0] = t0_1;
}

static int isSchedulable_write_luma() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = comp;
  _tmp0_1 = _tmp1_1 < 4;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void write_chroma() {
  short *MV;
  int _tmp0_1;
  short _tmp1_1;
  int _tmp2_1;
  short mv0_1;
  short mv0_2;
  short _tmp3_1;
  int _tmp4_1;
  short mv0_3;
  short _tmp5_1;
  short t0_1;
  short _tmp6_1;
  
  MV = getWritePtr(mvrecon_MV, 1);
  _tmp0_1 = fourmv;
  if (_tmp0_1) {
    _tmp1_1 = sum_x;
    _tmp2_1 = uvclip_4(_tmp1_1);
    mv0_1 = _tmp2_1;
    mv0_2 = mv0_1;
  } else {
    _tmp3_1 = sum_x;
    _tmp4_1 = uvclip_1(_tmp3_1);
    mv0_3 = _tmp4_1;
    mv0_2 = mv0_3;
  }
  _tmp5_1 = sum_x;
  t0_1 = _tmp5_1;
  _tmp6_1 = sum_y;
  sum_x = _tmp6_1;
  sum_y = t0_1;
  MV[0] = mv0_2;
}

static int isSchedulable_write_chroma() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void advance() {
  char _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  char _tmp4_1;
  short _tmp5_1;
  int _tmp6_1;
  int _tmp6_2;
  short _tmp8_1;
  int _tmp6_3;
  
  comp++;
  _tmp1_1 = comp;
  if (_tmp1_1 == 6) {
    comp = 0;
    _tmp2_1 = mbx;
    mbx = _tmp2_1 + 1;
    _tmp3_1 = mbx;
    _tmp4_1 = mbwidth;
    if (_tmp3_1 == _tmp4_1) {
      top_edge = 0;
    }
    _tmp5_1 = ptr;
    if (_tmp5_1 == 8) {
      _tmp6_1 = 960;
      _tmp6_2 = _tmp6_1;
    } else {
      _tmp8_1 = ptr;
      _tmp6_3 = _tmp8_1 - 8;
      _tmp6_2 = _tmp6_3;
    }
    ptr = _tmp6_2;
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
  s_compute,
  s_do_pred_x,
  s_do_pred_y,
  s_get_mag_x,
  s_get_mag_y,
  s_get_pred_p,
  s_get_pred_p1,
  s_get_pred_x,
  s_get_pred_x1,
  s_get_pred_x2,
  s_get_pred_y,
  s_get_pred_y1,
  s_get_pred_y2,
  s_get_res_x,
  s_get_res_x_a,
  s_get_res_x_b,
  s_get_res_x_c,
  s_get_res_x_d,
  s_get_res_x_e,
  s_get_res_y,
  s_get_res_y_a,
  s_get_res_y_b,
  s_get_res_y_c,
  s_get_res_y_d,
  s_get_res_y_e,
  s_geth,
  s_getw,
  s_read,
  s_write,
  s_write_y
};

static char *stateNames[] = {
  "s_advance",
  "s_compute",
  "s_do_pred_x",
  "s_do_pred_y",
  "s_get_mag_x",
  "s_get_mag_y",
  "s_get_pred_p",
  "s_get_pred_p1",
  "s_get_pred_x",
  "s_get_pred_x1",
  "s_get_pred_x2",
  "s_get_pred_y",
  "s_get_pred_y1",
  "s_get_pred_y2",
  "s_get_res_x",
  "s_get_res_x_a",
  "s_get_res_x_b",
  "s_get_res_x_c",
  "s_get_res_x_d",
  "s_get_res_x_e",
  "s_get_res_y",
  "s_get_res_y_a",
  "s_get_res_y_b",
  "s_get_res_y_c",
  "s_get_res_y_d",
  "s_get_res_y_e",
  "s_geth",
  "s_getw",
  "s_read",
  "s_write",
  "s_write_y"
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

static int compute_state_scheduler() {
  int res;
  
  if (isSchedulable_compute_done()) {
    compute_done();
    _FSM_state = s_write;
    res = 1;
  } else {
    if (isSchedulable_compute_start()) {
      compute_start();
      _FSM_state = s_get_pred_p;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int do_pred_x_state_scheduler() {
  int res;
  
  if (isSchedulable_do_pred()) {
    do_pred();
    _FSM_state = s_get_mag_x;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int do_pred_y_state_scheduler() {
  int res;
  
  if (isSchedulable_do_pred()) {
    do_pred();
    _FSM_state = s_get_mag_y;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_mag_x_state_scheduler() {
  int res;
  
  if (isSchedulable_get_mag()) {
    get_mag();
    _FSM_state = s_get_res_x;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_mag_y_state_scheduler() {
  int res;
  
  if (isSchedulable_get_mag()) {
    get_mag();
    _FSM_state = s_get_res_y;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_p_state_scheduler() {
  int res;
  
  if (isSchedulable_compute_start()) {
    compute_start();
    _FSM_state = s_get_pred_p1;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_p1_state_scheduler() {
  int res;
  
  if (isSchedulable_compute_start()) {
    compute_start();
    _FSM_state = s_get_pred_x;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_x_state_scheduler() {
  int res;
  
  if (isSchedulable_get_pred()) {
    get_pred();
    _FSM_state = s_get_pred_x1;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_x1_state_scheduler() {
  int res;
  
  if (isSchedulable_get_pred()) {
    get_pred();
    _FSM_state = s_get_pred_x2;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_x2_state_scheduler() {
  int res;
  
  if (isSchedulable_get_pred()) {
    get_pred();
    _FSM_state = s_do_pred_x;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_y_state_scheduler() {
  int res;
  
  if (isSchedulable_get_pred()) {
    get_pred();
    _FSM_state = s_get_pred_y1;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_y1_state_scheduler() {
  int res;
  
  if (isSchedulable_get_pred()) {
    get_pred();
    _FSM_state = s_get_pred_y2;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_pred_y2_state_scheduler() {
  int res;
  
  if (isSchedulable_get_pred()) {
    get_pred();
    _FSM_state = s_do_pred_y;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_x_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_init()) {
    get_residual_init();
    _FSM_state = s_get_res_x_a;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_x_a_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_shift()) {
    get_residual_shift();
    _FSM_state = s_get_res_x_b;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_x_b_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_adjust()) {
    get_residual_adjust();
    _FSM_state = s_get_res_x_c;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_x_c_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_calc()) {
    get_residual_calc();
    _FSM_state = s_get_res_x_d;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_x_d_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_clip()) {
    get_residual_clip();
    _FSM_state = s_get_res_x_e;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_x_e_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_final()) {
    get_residual_final();
    _FSM_state = s_get_pred_y;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_y_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_init()) {
    get_residual_init();
    _FSM_state = s_get_res_y_a;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_y_a_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_shift()) {
    get_residual_shift();
    _FSM_state = s_get_res_y_b;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_y_b_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_adjust()) {
    get_residual_adjust();
    _FSM_state = s_get_res_y_c;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_y_c_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_calc()) {
    get_residual_calc();
    _FSM_state = s_get_res_y_d;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_y_d_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_clip()) {
    get_residual_clip();
    _FSM_state = s_get_res_y_e;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_res_y_e_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_final()) {
    get_residual_final();
    _FSM_state = s_write;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_geth()) {
    geth();
    _FSM_state = s_read;
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

static int read_state_scheduler() {
  int res;
  
  if (isSchedulable_start()) {
    start();
    _FSM_state = s_getw;
    res = 1;
  } else {
    if (isSchedulable_read_noMotion()) {
      read_noMotion();
      _FSM_state = s_advance;
      res = 1;
    } else {
      if (isSchedulable_read_motion()) {
        read_motion();
        _FSM_state = s_compute;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int write_state_scheduler() {
  int res;
  
  if (isSchedulable_write_luma()) {
    if (hasRoom(mvrecon_MV, 1)) {
      write_luma();
      _FSM_state = s_write_y;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_write_chroma()) {
      if (hasRoom(mvrecon_MV, 1)) {
        write_chroma();
        _FSM_state = s_write_y;
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

static int write_y_state_scheduler() {
  int res;
  
  if (isSchedulable_write_luma()) {
    if (hasRoom(mvrecon_MV, 1)) {
      write_luma();
      _FSM_state = s_advance;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_write_chroma()) {
      if (hasRoom(mvrecon_MV, 1)) {
        write_chroma();
        _FSM_state = s_advance;
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

int mvrecon_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_advance:
        res = advance_state_scheduler();
        break;
      case s_compute:
        res = compute_state_scheduler();
        break;
      case s_do_pred_x:
        res = do_pred_x_state_scheduler();
        break;
      case s_do_pred_y:
        res = do_pred_y_state_scheduler();
        break;
      case s_get_mag_x:
        res = get_mag_x_state_scheduler();
        break;
      case s_get_mag_y:
        res = get_mag_y_state_scheduler();
        break;
      case s_get_pred_p:
        res = get_pred_p_state_scheduler();
        break;
      case s_get_pred_p1:
        res = get_pred_p1_state_scheduler();
        break;
      case s_get_pred_x:
        res = get_pred_x_state_scheduler();
        break;
      case s_get_pred_x1:
        res = get_pred_x1_state_scheduler();
        break;
      case s_get_pred_x2:
        res = get_pred_x2_state_scheduler();
        break;
      case s_get_pred_y:
        res = get_pred_y_state_scheduler();
        break;
      case s_get_pred_y1:
        res = get_pred_y1_state_scheduler();
        break;
      case s_get_pred_y2:
        res = get_pred_y2_state_scheduler();
        break;
      case s_get_res_x:
        res = get_res_x_state_scheduler();
        break;
      case s_get_res_x_a:
        res = get_res_x_a_state_scheduler();
        break;
      case s_get_res_x_b:
        res = get_res_x_b_state_scheduler();
        break;
      case s_get_res_x_c:
        res = get_res_x_c_state_scheduler();
        break;
      case s_get_res_x_d:
        res = get_res_x_d_state_scheduler();
        break;
      case s_get_res_x_e:
        res = get_res_x_e_state_scheduler();
        break;
      case s_get_res_y:
        res = get_res_y_state_scheduler();
        break;
      case s_get_res_y_a:
        res = get_res_y_a_state_scheduler();
        break;
      case s_get_res_y_b:
        res = get_res_y_b_state_scheduler();
        break;
      case s_get_res_y_c:
        res = get_res_y_c_state_scheduler();
        break;
      case s_get_res_y_d:
        res = get_res_y_d_state_scheduler();
        break;
      case s_get_res_y_e:
        res = get_res_y_e_state_scheduler();
        break;
      case s_geth:
        res = geth_state_scheduler();
        break;
      case s_getw:
        res = getw_state_scheduler();
        break;
      case s_read:
        res = read_state_scheduler();
        break;
      case s_write:
        res = write_state_scheduler();
        break;
      case s_write_y:
        res = write_y_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

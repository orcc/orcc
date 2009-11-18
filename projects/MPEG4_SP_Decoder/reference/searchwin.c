// Generated from "searchwin"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *searchwin_MV;
extern struct fifo_s *searchwin_BTYPE;
extern struct fifo_s *searchwin_DI;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *searchwin_DO;
extern struct fifo_s *searchwin_FLAGS;

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
static int LUMA_X_BITS = 4;
static int LUMA_Y_BITS = 6;
static int LUMA_X_SIZE = 16;
static int LUMA_Y_SIZE = 64;
static int CHROMA_X_BITS = 3;
static int CHROMA_Y_BITS = 5;
static int CHROMA_X_SIZE = 8;
static int CHROMA_Y_SIZE = 32;
static int luma[1024] = {0};
static int chroma[512] = {0};
static int prediction_is_INTRA;
static int is_motion;
static int round;
static char count;
static char comp;
static char mbw_max;
static char mbh_max;
static char mbx;
static char mby;
static char mbx_ptr = 0;
static char y_offset;
static short mvx;
static short mvy;
static int interp_x;
static char mvxcount;
static char mvycount;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int maskBits(int v, int n) {
  return v & (1 << n) - 1;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *BTYPE;
  short cmd_1;
  
  BTYPE = getReadPtr(searchwin_BTYPE, 1);
  cmd_1 = BTYPE[0];
  round = (cmd_1 & 32) != 0;
  prediction_is_INTRA = (cmd_1 & 1024) != 0;
  comp = 0;
  is_motion = 0;
  mbx = -1;
  mby = 0;
  y_offset = -1;
  count = 0;
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(searchwin_BTYPE, 1);
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
  
  BTYPE = getReadPtr(searchwin_BTYPE, 1);
  w_1 = BTYPE[0];
  mbw_max = w_1 - 1;
}

static int isSchedulable_getw() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_BTYPE, 1);
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
  short h_1;
  
  BTYPE = getReadPtr(searchwin_BTYPE, 1);
  h_1 = BTYPE[0];
  mbh_max = h_1 - 1;
}

static int isSchedulable_geth() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_BTYPE, 1);
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
  
  BTYPE = getReadPtr(searchwin_BTYPE, 1);
  is_motion = 1;
}

static int isSchedulable_cmd_motion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(searchwin_BTYPE, 1);
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
  char *FLAGS;
  short *BTYPE;
  
  FLAGS = getWritePtr(searchwin_FLAGS, 1);
  BTYPE = getReadPtr(searchwin_BTYPE, 1);
  mvx = 0;
  mvy = 0;
  is_motion = 1;
  FLAGS[0] = 0;
}

static int isSchedulable_cmd_noMotion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(searchwin_BTYPE, 1);
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
  
  BTYPE = getReadPtr(searchwin_BTYPE, 1);
  is_motion = 0;
}

static int isSchedulable_cmd_other() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void bypass() {
  char _tmp0_1;
  char _tmp1_1;
  
  _tmp0_1 = comp;
  if (_tmp0_1 == 0) {
    _tmp1_1 = mbx_ptr;
    mbx_ptr = _tmp1_1 + 1;
  }
}

static int isSchedulable_bypass() {
  int _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  char _tmp4_1;
  char _tmp5_1;
  char _tmp6_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = prediction_is_INTRA;
  _tmp2_1 = comp;
  _tmp3_1 = mbx;
  _tmp4_1 = mbw_max;
  _tmp5_1 = mby;
  _tmp6_1 = mbh_max;
  _tmp0_1 = _tmp1_1 || _tmp2_1 != 0 || _tmp3_1 == _tmp4_1 && _tmp5_1 == _tmp6_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void next() {
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  char _tmp4_1;
  char _tmp5_1;
  char _tmp6_1;
  
  _tmp0_1 = mbx;
  if (_tmp0_1 < 0) {
    mbx = 0;
  } else {
    _tmp1_1 = comp;
    if (_tmp1_1 == 5) {
      comp = 0;
      _tmp2_1 = mbx;
      _tmp3_1 = mbw_max;
      if (_tmp2_1 == _tmp3_1) {
        mbx = 0;
        _tmp4_1 = mby;
        mby = _tmp4_1 + 1;
      } else {
        _tmp5_1 = mbx;
        mbx = _tmp5_1 + 1;
      }
    } else {
      _tmp6_1 = comp;
      comp = _tmp6_1 + 1;
    }
  }
}

static int isSchedulable_next() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void done() {
  mbx_ptr++;
  y_offset = -1;
  count = 0;
}

static int isSchedulable_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = y_offset;
  _tmp0_1 = _tmp1_1 == 2;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void read_luma() {
  int *DI;
  int d_1;
  char _tmp0_1;
  char comp_local0_1;
  char _tmp1_1;
  int _tmp2_1;
  unsigned char _tmp3_1;
  unsigned char _tmp3_2;
  unsigned char _tmp3_3;
  char _tmp4_1;
  char y0_1;
  char _tmp6_1;
  int _tmp7_1;
  unsigned char _tmp8_1;
  unsigned char _tmp8_2;
  unsigned char _tmp8_3;
  char _tmp9_1;
  char x0_1;
  
  DI = getReadPtr(searchwin_DI, 1);
  d_1 = DI[0];
  _tmp0_1 = count;
  comp_local0_1 = maskBits(_tmp0_1 >> 4, 2);
  _tmp1_1 = count;
  _tmp2_1 = maskBits(_tmp1_1 >> 1, 3);
  if ((comp_local0_1 & 2) != 0) {
    _tmp3_1 = 8;
    _tmp3_2 = _tmp3_1;
  } else {
    _tmp3_3 = 0;
    _tmp3_2 = _tmp3_3;
  }
  _tmp4_1 = y_offset;
  y0_1 = maskBits(_tmp2_1 + _tmp3_2 + (_tmp4_1 << 4), 6);
  _tmp6_1 = count;
  _tmp7_1 = maskBits(_tmp6_1, 1);
  if ((comp_local0_1 & 1) != 0) {
    _tmp8_1 = 2;
    _tmp8_2 = _tmp8_1;
  } else {
    _tmp8_3 = 0;
    _tmp8_2 = _tmp8_3;
  }
  _tmp9_1 = mbx_ptr;
  x0_1 = maskBits(_tmp7_1 + _tmp8_2 + (_tmp9_1 << 2), 4);
  luma[(y0_1 << 4) + x0_1] = d_1;
  count++;
}

static int isSchedulable_read_luma() {
  int *DI;
  int _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_DI, 1);
  if (_tmp1_1) {
    DI = getPeekPtr(searchwin_DI, 1);
    _tmp2_1 = count;
    _tmp0_1 = _tmp2_1 <= 63;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_chroma() {
  int *DI;
  int d_1;
  char _tmp0_1;
  char comp_local0_1;
  char _tmp1_1;
  int _tmp2_1;
  char _tmp3_1;
  char y0_1;
  char _tmp5_1;
  int _tmp6_1;
  char _tmp7_1;
  char x0_1;
  char _tmp13_1;
  char _tmp14_1;
  
  DI = getReadPtr(searchwin_DI, 1);
  d_1 = DI[0];
  _tmp0_1 = count;
  comp_local0_1 = maskBits(_tmp0_1 >> 4, 1);
  _tmp1_1 = count;
  _tmp2_1 = maskBits(_tmp1_1 >> 1, 3);
  _tmp3_1 = y_offset;
  y0_1 = maskBits(_tmp2_1 + (_tmp3_1 << 3), 5);
  _tmp5_1 = count;
  _tmp6_1 = maskBits(_tmp5_1, 1);
  _tmp7_1 = mbx_ptr;
  x0_1 = maskBits(_tmp6_1 + (_tmp7_1 << 1), 3);
  chroma[(comp_local0_1 << 8) + (y0_1 << 3) + x0_1] = d_1;
  count++;
  _tmp13_1 = count;
  if (_tmp13_1 == 96) {
    count = 0;
    _tmp14_1 = y_offset;
    y_offset = _tmp14_1 + 1;
  }
}

static int isSchedulable_read_chroma() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_DI, 1);
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
  
  MV = getReadPtr(searchwin_MV, 1);
  v_1 = MV[0];
  interp_x = (v_1 & 1) != 0;
  mvx = v_1 >> 1;
}

static int isSchedulable_getmvx() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_MV, 1);
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
  char *FLAGS;
  short *MV;
  short v_1;
  int _tmp0_1;
  unsigned char _tmp1_1;
  unsigned char _tmp1_2;
  unsigned char _tmp1_3;
  unsigned char _tmp2_1;
  unsigned char _tmp2_2;
  unsigned char _tmp2_3;
  int _tmp3_1;
  unsigned char _tmp4_1;
  unsigned char _tmp4_2;
  unsigned char _tmp4_3;
  char f0_1;
  
  FLAGS = getWritePtr(searchwin_FLAGS, 1);
  MV = getReadPtr(searchwin_MV, 1);
  v_1 = MV[0];
  _tmp0_1 = interp_x;
  if (_tmp0_1) {
    _tmp1_1 = 4;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 0;
    _tmp1_2 = _tmp1_3;
  }
  if ((v_1 & 1) != 0) {
    _tmp2_1 = 2;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp2_3 = 0;
    _tmp2_2 = _tmp2_3;
  }
  _tmp3_1 = round;
  if (_tmp3_1) {
    _tmp4_1 = 1;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp4_3 = 0;
    _tmp4_2 = _tmp4_3;
  }
  f0_1 = _tmp1_2 + _tmp2_2 + _tmp4_2;
  mvy = v_1 >> 1;
  FLAGS[0] = f0_1;
}

static int isSchedulable_getmvy() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(searchwin_MV, 1);
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
  mvxcount = 0;
  mvycount = 0;
}

static int isSchedulable_motion() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = is_motion;
  _tmp0_1 = _tmp1_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void noMotion() {
}

static int isSchedulable_noMotion() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void search_done() {
}

static int isSchedulable_search_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = mvycount;
  _tmp0_1 = _tmp1_1 == 9;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void search_luma() {
  int *DO;
  short _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  unsigned char _tmp3_1;
  unsigned char _tmp3_2;
  unsigned char _tmp3_3;
  short y0_1;
  short _tmp4_1;
  char _tmp5_1;
  char _tmp6_1;
  unsigned char _tmp7_1;
  unsigned char _tmp7_2;
  unsigned char _tmp7_3;
  short x0_1;
  char _tmp8_1;
  short y0_2;
  short y0_3;
  short y0_4;
  char _tmp9_1;
  char _tmp10_1;
  short y0_5;
  short y0_6;
  short y0_7;
  char _tmp11_1;
  short x0_2;
  short x0_3;
  short x0_4;
  char _tmp12_1;
  char _tmp13_1;
  short x0_5;
  short x0_6;
  short x0_7;
  char _tmp15_1;
  char _tmp16_1;
  int _tmp18_1;
  char _tmp20_1;
  int _tmp22_1;
  int _tmp23_1;
  
  DO = getWritePtr(searchwin_DO, 1);
  _tmp0_1 = mvy;
  _tmp1_1 = mvycount;
  _tmp2_1 = comp;
  if ((_tmp2_1 & 2) != 0) {
    _tmp3_1 = 8;
    _tmp3_2 = _tmp3_1;
  } else {
    _tmp3_3 = 0;
    _tmp3_2 = _tmp3_3;
  }
  y0_1 = _tmp0_1 + _tmp1_1 + _tmp3_2;
  _tmp4_1 = mvx;
  _tmp5_1 = mvxcount;
  _tmp6_1 = comp;
  if ((_tmp6_1 & 1) != 0) {
    _tmp7_1 = 8;
    _tmp7_2 = _tmp7_1;
  } else {
    _tmp7_3 = 0;
    _tmp7_2 = _tmp7_3;
  }
  x0_1 = _tmp4_1 + (_tmp5_1 << 2) + _tmp7_2;
  _tmp8_1 = mby;
  if (_tmp8_1 == 0) {
    if (y0_1 < 0) {
      y0_2 = 0;
      y0_3 = y0_2;
    } else {
      y0_3 = y0_1;
    }
    y0_4 = y0_3;
  } else {
    _tmp9_1 = mby;
    _tmp10_1 = mbh_max;
    if (_tmp9_1 == _tmp10_1) {
      if (y0_1 > 15) {
        y0_5 = 15;
        y0_6 = y0_5;
      } else {
        y0_6 = y0_1;
      }
      y0_7 = y0_6;
    } else {
      y0_7 = y0_1;
    }
    y0_4 = y0_7;
  }
  _tmp11_1 = mbx;
  if (_tmp11_1 == 0) {
    if (x0_1 < 0) {
      x0_2 = 0;
      x0_3 = x0_2;
    } else {
      x0_3 = x0_1;
    }
    x0_4 = x0_3;
  } else {
    _tmp12_1 = mbx;
    _tmp13_1 = mbw_max;
    if (_tmp12_1 == _tmp13_1) {
      if (x0_1 > 12) {
        x0_5 = 12;
        x0_6 = x0_5;
      } else {
        x0_6 = x0_1;
      }
      x0_7 = x0_6;
    } else {
      x0_7 = x0_1;
    }
    x0_4 = x0_7;
  }
  mvxcount++;
  _tmp15_1 = mvxcount;
  if (_tmp15_1 == 3) {
    mvxcount = 0;
    _tmp16_1 = mvycount;
    mvycount = _tmp16_1 + 1;
  }
  _tmp18_1 = maskBits(y0_4, 6);
  _tmp20_1 = mbx_ptr;
  _tmp22_1 = maskBits((x0_4 + ((_tmp20_1 - 2) << 4)) >> 2, 4);
  _tmp23_1 = luma[(_tmp18_1 << 4) + _tmp22_1];
  DO[0] = _tmp23_1;
}

static int isSchedulable_search_luma() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = comp;
  _tmp0_1 = _tmp1_1 <= 3;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void search_chroma() {
  int *DO;
  short _tmp0_1;
  char _tmp1_1;
  short y0_1;
  short _tmp2_1;
  char _tmp3_1;
  short x0_1;
  char _tmp4_1;
  short y0_2;
  short y0_3;
  short y0_4;
  char _tmp5_1;
  char _tmp6_1;
  short y0_5;
  short y0_6;
  short y0_7;
  char _tmp7_1;
  short x0_2;
  short x0_3;
  short x0_4;
  char _tmp8_1;
  char _tmp9_1;
  short x0_5;
  short x0_6;
  short x0_7;
  char _tmp11_1;
  char _tmp12_1;
  char _tmp13_1;
  int _tmp14_1;
  int _tmp18_1;
  char _tmp20_1;
  int _tmp22_1;
  int _tmp23_1;
  
  DO = getWritePtr(searchwin_DO, 1);
  _tmp0_1 = mvy;
  _tmp1_1 = mvycount;
  y0_1 = _tmp0_1 + _tmp1_1;
  _tmp2_1 = mvx;
  _tmp3_1 = mvxcount;
  x0_1 = _tmp2_1 + (_tmp3_1 << 2);
  _tmp4_1 = mby;
  if (_tmp4_1 == 0) {
    if (y0_1 < 0) {
      y0_2 = 0;
      y0_3 = y0_2;
    } else {
      y0_3 = y0_1;
    }
    y0_4 = y0_3;
  } else {
    _tmp5_1 = mby;
    _tmp6_1 = mbh_max;
    if (_tmp5_1 == _tmp6_1) {
      if (y0_1 > 7) {
        y0_5 = 7;
        y0_6 = y0_5;
      } else {
        y0_6 = y0_1;
      }
      y0_7 = y0_6;
    } else {
      y0_7 = y0_1;
    }
    y0_4 = y0_7;
  }
  _tmp7_1 = mbx;
  if (_tmp7_1 == 0) {
    if (x0_1 < 0) {
      x0_2 = 0;
      x0_3 = x0_2;
    } else {
      x0_3 = x0_1;
    }
    x0_4 = x0_3;
  } else {
    _tmp8_1 = mbx;
    _tmp9_1 = mbw_max;
    if (_tmp8_1 == _tmp9_1) {
      if (x0_1 > 4) {
        x0_5 = 4;
        x0_6 = x0_5;
      } else {
        x0_6 = x0_1;
      }
      x0_7 = x0_6;
    } else {
      x0_7 = x0_1;
    }
    x0_4 = x0_7;
  }
  mvxcount++;
  _tmp11_1 = mvxcount;
  if (_tmp11_1 == 3) {
    mvxcount = 0;
    _tmp12_1 = mvycount;
    mvycount = _tmp12_1 + 1;
  }
  _tmp13_1 = comp;
  _tmp14_1 = maskBits(_tmp13_1, 1);
  _tmp18_1 = maskBits(y0_4, 5);
  _tmp20_1 = mbx_ptr;
  _tmp22_1 = maskBits((x0_4 + ((_tmp20_1 - 2) << 3)) >> 2, 3);
  _tmp23_1 = chroma[(_tmp14_1 << 8) + (_tmp18_1 << 3) + _tmp22_1];
  DO[0] = _tmp23_1;
}

static int isSchedulable_search_chroma() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_cmd = 0,
  s_fill,
  s_geth,
  s_getmvx,
  s_getmvy,
  s_getw,
  s_motion,
  s_next,
  s_search
};

static char *stateNames[] = {
  "s_cmd",
  "s_fill",
  "s_geth",
  "s_getmvx",
  "s_getmvy",
  "s_getw",
  "s_motion",
  "s_next",
  "s_search"
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
        if (hasRoom(searchwin_FLAGS, 1)) {
          cmd_noMotion();
          _FSM_state = s_fill;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        if (isSchedulable_cmd_other()) {
          cmd_other();
          _FSM_state = s_fill;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int fill_state_scheduler() {
  int res;
  
  if (isSchedulable_bypass()) {
    bypass();
    _FSM_state = s_motion;
    res = 1;
  } else {
    if (isSchedulable_done()) {
      done();
      _FSM_state = s_motion;
      res = 1;
    } else {
      if (isSchedulable_read_luma()) {
        read_luma();
        _FSM_state = s_fill;
        res = 1;
      } else {
        if (isSchedulable_read_chroma()) {
          read_chroma();
          _FSM_state = s_fill;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_geth()) {
    geth();
    _FSM_state = s_fill;
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
    if (hasRoom(searchwin_FLAGS, 1)) {
      getmvy();
      _FSM_state = s_fill;
      res = 1;
    } else {
      res = 0;
    }
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

static int motion_state_scheduler() {
  int res;
  
  if (isSchedulable_motion()) {
    motion();
    _FSM_state = s_search;
    res = 1;
  } else {
    if (isSchedulable_noMotion()) {
      noMotion();
      _FSM_state = s_next;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int next_state_scheduler() {
  int res;
  
  if (isSchedulable_next()) {
    next();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int search_state_scheduler() {
  int res;
  
  if (isSchedulable_search_done()) {
    search_done();
    _FSM_state = s_next;
    res = 1;
  } else {
    if (isSchedulable_search_luma()) {
      if (hasRoom(searchwin_DO, 1)) {
        search_luma();
        _FSM_state = s_search;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_search_chroma()) {
        if (hasRoom(searchwin_DO, 1)) {
          search_chroma();
          _FSM_state = s_search;
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

int searchwin_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_cmd:
        res = cmd_state_scheduler();
        break;
      case s_fill:
        res = fill_state_scheduler();
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
      case s_motion:
        res = motion_state_scheduler();
        break;
      case s_next:
        res = next_state_scheduler();
        break;
      case s_search:
        res = search_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

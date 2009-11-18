// Generated from "address"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *address_MV;
extern struct fifo_s *address_BTYPE;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *address_RA;
extern struct fifo_s *address_WA;
extern struct fifo_s *address_halfpel;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int SEARCHWIN_IN_MB = 3;
static int MAXW_IN_MB = 121;
static int MAXH_IN_MB = 69;
static int ADDR_SZ = 24;
static int FLAG_SZ = 4;
static int MV_SZ = 9;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int INTRA = 1024;
static int NEWVOP = 2048;
static int MOTION = 8;
static int ROUND_TYPE = 32;
static int LAYOUT = 0;
static int maxw_in_pixels = 1936;
static int maxh_in_pixels = 1104;
static int half_search_in_pixels = 40;
static int luma_pixel_offset = 77440;
static int chroma_pixel_offset = 19360;
static int max_luma_pixels = 2214784;
static int max_chroma_pixels = 553696;
static int luma_buf_size = 2214784;
static int chroma_buf_size = 553696;
static int luma_base = 0;
static int chroma_base = 0;
static int luma_write_ring_start = 0;
static int chroma_write_ring_start = 0;
static int luma_read_ring_start;
static int chroma_read_ring_start;
static char vop_width;
static char vop_height;
static short vop_luma_width;
static short vop_luma_height;
static short vop_chroma_width;
static short vop_chroma_height;
static short next_mbx;
static short next_mby;
static short next_comp;
static int next_luma_x;
static int next_luma_y;
static int next_chroma_x;
static int next_chroma_y;
static char read_count;
static char read_row;
static char read_col;
static char write_count;
static int current_w;
static int current_h;
static int current_size;
static int current_base;
static int current_read_ring_start;
static int current_write_ring_start;
static int current_x;
static int current_y;
static int round;
static short mvx;
static short mvy;
static int interp_x;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int generate_read_addr(int dx, int dy) {
  int _tmp0_1;
  int x0_1;
  int x_sat0_1;
  int x_sat0_2;
  int _tmp1_1;
  int _tmp2_1;
  int x_sat0_3;
  int x_sat0_4;
  int x_sat0_5;
  int _tmp3_1;
  int y0_1;
  int y_sat0_1;
  int y_sat0_2;
  int _tmp4_1;
  int _tmp5_1;
  int y_sat0_3;
  int y_sat0_4;
  int y_sat0_5;
  int _tmp6_1;
  int _tmp7_1;
  int i0_1;
  int _tmp8_1;
  int i_wrap0_1;
  int i_wrap0_2;
  int _tmp9_1;
  int _tmp10_1;
  int i_wrap0_3;
  int i_wrap0_4;
  int i_wrap0_5;
  int _tmp11_1;
  
  _tmp0_1 = current_x;
  x0_1 = _tmp0_1 + dx;
  if (x0_1 < 0) {
    x_sat0_1 = 0;
    x_sat0_2 = x_sat0_1;
  } else {
    _tmp1_1 = current_w;
    if (x0_1 >= _tmp1_1) {
      _tmp2_1 = current_w;
      x_sat0_3 = _tmp2_1 - 1;
      x_sat0_4 = x_sat0_3;
    } else {
      x_sat0_5 = x0_1;
      x_sat0_4 = x_sat0_5;
    }
    x_sat0_2 = x_sat0_4;
  }
  _tmp3_1 = current_y;
  y0_1 = _tmp3_1 + dy;
  if (y0_1 < 0) {
    y_sat0_1 = 0;
    y_sat0_2 = y_sat0_1;
  } else {
    _tmp4_1 = current_h;
    if (y0_1 >= _tmp4_1) {
      _tmp5_1 = current_h;
      y_sat0_3 = _tmp5_1 - 1;
      y_sat0_4 = y_sat0_3;
    } else {
      y_sat0_5 = y0_1;
      y_sat0_4 = y_sat0_5;
    }
    y_sat0_2 = y_sat0_4;
  }
  _tmp6_1 = current_read_ring_start;
  _tmp7_1 = current_w;
  i0_1 = _tmp6_1 + x_sat0_2 + y_sat0_2 * _tmp7_1;
  if (i0_1 < 0) {
    _tmp8_1 = current_size;
    i_wrap0_1 = i0_1 + _tmp8_1;
    i_wrap0_2 = i_wrap0_1;
  } else {
    _tmp9_1 = current_size;
    if (i0_1 >= _tmp9_1) {
      _tmp10_1 = current_size;
      i_wrap0_3 = i0_1 - _tmp10_1;
      i_wrap0_4 = i_wrap0_3;
    } else {
      i_wrap0_5 = i0_1;
      i_wrap0_4 = i_wrap0_5;
    }
    i_wrap0_2 = i_wrap0_4;
  }
  _tmp11_1 = current_base;
  return _tmp11_1 + i_wrap0_2;
}

static int generate_write_addr(int dx, int dy) {
  int _tmp0_1;
  int x0_1;
  int _tmp1_1;
  int y0_1;
  int _tmp2_1;
  int _tmp3_1;
  int i0_1;
  int _tmp4_1;
  int _tmp5_1;
  int i_wrap0_1;
  int i_wrap0_2;
  int i_wrap0_3;
  int _tmp6_1;
  
  _tmp0_1 = current_x;
  x0_1 = _tmp0_1 + dx;
  _tmp1_1 = current_y;
  y0_1 = _tmp1_1 + dy;
  _tmp2_1 = current_write_ring_start;
  _tmp3_1 = current_w;
  i0_1 = _tmp2_1 + x0_1 + y0_1 * _tmp3_1;
  _tmp4_1 = current_size;
  if (i0_1 >= _tmp4_1) {
    _tmp5_1 = current_size;
    i_wrap0_1 = i0_1 - _tmp5_1;
    i_wrap0_2 = i_wrap0_1;
  } else {
    i_wrap0_3 = i0_1;
    i_wrap0_2 = i_wrap0_3;
  }
  _tmp6_1 = current_base;
  return _tmp6_1 + i_wrap0_2;
}

static void advance_vop() {
  char _tmp0_1;
  char _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  int _tmp4_1;
  int _tmp7_1;
  int _tmp8_1;
  int _tmp10_1;
  int _tmp13_1;
  int _tmp14_1;
  
  _tmp0_1 = vop_width;
  vop_luma_width = _tmp0_1 << 4;
  _tmp1_1 = vop_height;
  vop_luma_height = _tmp1_1 << 4;
  _tmp2_1 = vop_width;
  vop_chroma_width = _tmp2_1 << 3;
  _tmp3_1 = vop_height;
  vop_chroma_height = _tmp3_1 << 3;
  _tmp4_1 = luma_write_ring_start;
  luma_read_ring_start = _tmp4_1;
  luma_write_ring_start -= 77440;
  _tmp7_1 = luma_write_ring_start;
  if (_tmp7_1 < 0) {
    _tmp8_1 = luma_write_ring_start;
    luma_write_ring_start = _tmp8_1 + 2214784;
  }
  _tmp10_1 = chroma_write_ring_start;
  chroma_read_ring_start = _tmp10_1;
  chroma_write_ring_start -= 19360;
  _tmp13_1 = chroma_write_ring_start;
  if (_tmp13_1 < 0) {
    _tmp14_1 = chroma_write_ring_start;
    chroma_write_ring_start = _tmp14_1 + 553696;
  }
  next_mbx = 0;
  next_mby = 0;
  next_comp = 0;
  next_luma_x = 0;
  next_luma_y = 0;
  next_chroma_x = 0;
  next_chroma_y = 0;
}

static void advance_block() {
  short _tmp1_2;
  short _tmp3_1;
  short _tmp1_3;
  short _tmp5_2;
  short _tmp7_1;
  short _tmp5_3;
  int _tmp9_2;
  int _tmp13_2;
  int _tmp15_1;
  int _tmp13_3;
  int _tmp17_2;
  int _tmp19_1;
  int _tmp17_3;
  int _tmp21_2;
  int _tmp23_1;
  int _tmp21_3;
  int _tmp25_2;
  int _tmp27_1;
  int _tmp25_3;
  int _tmp29_2;
  short _tmp50_1;
  char _tmp51_1;
  short _tmp52_1;
  int _tmp53_1;
  
  _tmp3_1 = vop_chroma_width;
  _tmp1_3 = _tmp3_1;
  _tmp1_2 = _tmp1_3;
  current_w = _tmp1_2;
  _tmp7_1 = vop_chroma_height;
  _tmp5_3 = _tmp7_1;
  _tmp5_2 = _tmp5_3;
  current_h = _tmp5_2;
  _tmp9_2 = 553696;
  current_size = _tmp9_2;
  _tmp15_1 = next_chroma_x;
  _tmp13_3 = _tmp15_1;
  _tmp13_2 = _tmp13_3;
  current_x = _tmp13_2;
  _tmp19_1 = next_chroma_y;
  _tmp17_3 = _tmp19_1;
  _tmp17_2 = _tmp17_3;
  current_y = _tmp17_2;
  _tmp23_1 = chroma_read_ring_start;
  _tmp21_3 = _tmp23_1;
  _tmp21_2 = _tmp21_3;
  current_read_ring_start = _tmp21_2;
  _tmp27_1 = chroma_write_ring_start;
  _tmp25_3 = _tmp27_1;
  _tmp25_2 = _tmp25_3;
  current_write_ring_start = _tmp25_2;
  _tmp29_2 = 0;
  current_base = _tmp29_2;
  next_mbx++;
  next_chroma_x += 8;
  _tmp50_1 = next_mbx;
  _tmp51_1 = vop_width;
  if (_tmp50_1 >= _tmp51_1) {
    next_mbx = 0;
    next_chroma_x = 0;
    _tmp52_1 = next_mby;
    next_mby = _tmp52_1 + 1;
    _tmp53_1 = next_chroma_y;
    next_chroma_y = _tmp53_1 + 8;
  }
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void cmd_newVop() {
  short *BTYPE;
  short cmd_1;
  
  BTYPE = getReadPtr(address_BTYPE, 1);
  cmd_1 = BTYPE[0];
  round = (cmd_1 & 32) != 0;
}

static int isSchedulable_cmd_newVop() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(address_BTYPE, 1);
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
  
  BTYPE = getReadPtr(address_BTYPE, 1);
  w_1 = BTYPE[0];
  vop_width = w_1;
}

static int isSchedulable_getw() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_BTYPE, 1);
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
  
  BTYPE = getReadPtr(address_BTYPE, 1);
  h_1 = BTYPE[0];
  vop_height = h_1;
  advance_vop();
}

static int isSchedulable_geth() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_noMotion() {
  short *BTYPE;
  
  BTYPE = getReadPtr(address_BTYPE, 1);
  read_count = 81;
  write_count = 0;
}

static int isSchedulable_cmd_noMotion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(address_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 1024) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_motion() {
  short *BTYPE;
  
  BTYPE = getReadPtr(address_BTYPE, 1);
}

static int isSchedulable_cmd_motion() {
  short *BTYPE;
  int _tmp1_1;
  short cmd_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_BTYPE, 1);
  if (_tmp1_1) {
    BTYPE = getPeekPtr(address_BTYPE, 1);
    cmd_1 = BTYPE[0];
    _tmp0_1 = (cmd_1 & 8) != 0;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void getmvx() {
  short *MV;
  short x_1;
  
  MV = getReadPtr(address_MV, 1);
  x_1 = MV[0];
  interp_x = (x_1 & 1) == 1;
  mvx = x_1 >> 1;
}

static int isSchedulable_getmvx() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_MV, 1);
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
  char *halfpel;
  short *MV;
  short y_1;
  int interp_y0_1;
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
  
  halfpel = getWritePtr(address_halfpel, 1);
  MV = getReadPtr(address_MV, 1);
  y_1 = MV[0];
  interp_y0_1 = (y_1 & 1) == 1;
  mvy = y_1 >> 1;
  read_count = 0;
  read_row = 0;
  read_col = 0;
  write_count = 0;
  _tmp0_1 = interp_x;
  if (_tmp0_1) {
    _tmp1_1 = 4;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 0;
    _tmp1_2 = _tmp1_3;
  }
  if (interp_y0_1) {
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
  halfpel[0] = _tmp1_2 + _tmp2_2 + _tmp4_2;
}

static int isSchedulable_getmvy() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_MV, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void cmd_neither() {
  char *halfpel;
  short *BTYPE;
  
  halfpel = getWritePtr(address_halfpel, 1);
  BTYPE = getReadPtr(address_BTYPE, 1);
  mvx = 0;
  mvy = 0;
  read_count = 0;
  read_row = 0;
  read_col = 0;
  write_count = 0;
  halfpel[0] = 0;
}

static int isSchedulable_cmd_neither() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(address_BTYPE, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void init() {
  advance_block();
}

static int isSchedulable_init() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void done() {
}

static int isSchedulable_done() {
  char _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = write_count;
  _tmp2_1 = read_count;
  _tmp0_1 = _tmp1_1 == 64 && _tmp2_1 == 81;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void write_addr() {
  int *WA;
  char _tmp0_1;
  char _tmp1_1;
  int a0_1;
  
  WA = getWritePtr(address_WA, 1);
  _tmp0_1 = write_count;
  _tmp1_1 = write_count;
  a0_1 = generate_write_addr(_tmp0_1 & 7, _tmp1_1 >> 3);
  write_count++;
  WA[0] = a0_1;
}

static int isSchedulable_write_addr() {
  char _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = write_count;
  _tmp2_1 = write_count;
  _tmp3_1 = read_count;
  _tmp0_1 = _tmp1_1 < 64 && _tmp2_1 < _tmp3_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void read_addr() {
  int *RA;
  short _tmp0_1;
  char _tmp1_1;
  short _tmp2_1;
  char _tmp3_1;
  int a0_1;
  char _tmp6_1;
  char _tmp7_1;
  
  RA = getWritePtr(address_RA, 1);
  _tmp0_1 = mvx;
  _tmp1_1 = read_col;
  _tmp2_1 = mvy;
  _tmp3_1 = read_row;
  a0_1 = generate_read_addr(_tmp0_1 + _tmp1_1, _tmp2_1 + _tmp3_1);
  read_count++;
  read_col++;
  _tmp6_1 = read_col;
  if (_tmp6_1 >= 9) {
    read_col = 0;
    _tmp7_1 = read_row;
    read_row = _tmp7_1 + 1;
  }
  RA[0] = a0_1;
}

static int isSchedulable_read_addr() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = read_count;
  _tmp0_1 = _tmp1_1 < 81;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_cmd = 0,
  s_generate,
  s_geth,
  s_getmvx,
  s_getmvy,
  s_getw,
  s_init
};

static char *stateNames[] = {
  "s_cmd",
  "s_generate",
  "s_geth",
  "s_getmvx",
  "s_getmvy",
  "s_getw",
  "s_init"
};

static enum states _FSM_state = s_cmd;

static int cmd_state_scheduler() {
  int res;
  
  if (isSchedulable_cmd_newVop()) {
    cmd_newVop();
    _FSM_state = s_getw;
    res = 1;
  } else {
    if (isSchedulable_cmd_noMotion()) {
      cmd_noMotion();
      _FSM_state = s_init;
      res = 1;
    } else {
      if (isSchedulable_cmd_motion()) {
        cmd_motion();
        _FSM_state = s_getmvx;
        res = 1;
      } else {
        if (isSchedulable_cmd_neither()) {
          if (hasRoom(address_halfpel, 1)) {
            cmd_neither();
            _FSM_state = s_init;
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

static int generate_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_cmd;
    res = 1;
  } else {
    if (isSchedulable_write_addr()) {
      if (hasRoom(address_WA, 1)) {
        write_addr();
        _FSM_state = s_generate;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_read_addr()) {
        if (hasRoom(address_RA, 1)) {
          read_addr();
          _FSM_state = s_generate;
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

static int geth_state_scheduler() {
  int res;
  
  if (isSchedulable_geth()) {
    geth();
    _FSM_state = s_cmd;
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
    if (hasRoom(address_halfpel, 1)) {
      getmvy();
      _FSM_state = s_init;
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

static int init_state_scheduler() {
  int res;
  
  if (isSchedulable_init()) {
    init();
    _FSM_state = s_generate;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

int address_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_cmd:
        res = cmd_state_scheduler();
        break;
      case s_generate:
        res = generate_state_scheduler();
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
      case s_init:
        res = init_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

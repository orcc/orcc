// Generated from "sobel"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *sobel_width;
extern struct fifo_s *sobel_height;
extern struct fifo_s *sobel_y;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *sobel_gradient;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static unsigned short MAXW = 2048;
static unsigned short img_w;
static unsigned short wcnt = 0;
static unsigned short img_h;
static unsigned short hcnt = 0;
static unsigned short fill_ptr = 0;
static unsigned short consume_ptr = 0;
static int CACHE_LINES = 3;
static unsigned char pixels[6144];
static int res;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static unsigned char get_pixel(int i, int j) {
  unsigned short _tmp0_1;
  unsigned char _tmp1_1;
  unsigned char _tmp1_2;
  unsigned short _tmp3_1;
  unsigned char _tmp4_1;
  unsigned char _tmp1_3;
  unsigned char _tmp1_4;
  unsigned short _tmp7_1;
  unsigned char _tmp8_1;
  unsigned char _tmp1_5;
  unsigned char _tmp1_6;
  unsigned short _tmp9_1;
  unsigned char _tmp10_1;
  unsigned char _tmp1_7;
  
  _tmp0_1 = img_w;
  if (i < 0 || i >= _tmp0_1) {
    _tmp1_1 = 0;
    _tmp1_2 = _tmp1_1;
  } else {
    if (j < 0) {
      _tmp3_1 = img_w;
      _tmp4_1 = pixels[(3 + j) * _tmp3_1 + i];
      _tmp1_3 = _tmp4_1;
      _tmp1_4 = _tmp1_3;
    } else {
      if (j >= 3) {
        _tmp7_1 = img_w;
        _tmp8_1 = pixels[(3 - j) * _tmp7_1 + i];
        _tmp1_5 = _tmp8_1;
        _tmp1_6 = _tmp1_5;
      } else {
        _tmp9_1 = img_w;
        _tmp10_1 = pixels[j * _tmp9_1 + i];
        _tmp1_7 = _tmp10_1;
        _tmp1_6 = _tmp1_7;
      }
      _tmp1_4 = _tmp1_6;
    }
    _tmp1_2 = _tmp1_4;
  }
  return _tmp1_2;
}

static void soft_sqrt(int num) {
  int _tmp2;
  int _tmp1;
  int op0_1;
  int one0_1;
  int one0_2;
  int one0_3;
  int _tmp0_1;
  int _tmp1_1;
  int _tmp1_2;
  int op0_2;
  int op0_3;
  int _tmp2_1;
  int _tmp2_2;
  int _tmp1_3;
  int op0_4;
  int _tmp2_3;
  int _tmp3_1;
  int one0_4;
  int one0_5;
  
  op0_1 = num;
  one0_1 = 16384;
  res = 0;
  one0_3 = one0_1;
  while (one0_5 > op0_4) {
    one0_2 = one0_5 >> 2;
    one0_3 = one0_2;
  }
  _tmp1_3 = _tmp1;
  op0_4 = op0_1;
  _tmp2_3 = _tmp2;
  one0_5 = one0_3;
  while (one0_5 != 0) {
    _tmp0_1 = res;
    if (op0_4 >= _tmp0_1 + one0_5) {
      _tmp1_1 = res;
      op0_2 = op0_4 - _tmp1_1 + one0_5;
      _tmp2_1 = res;
      res = _tmp2_1 + (one0_5 << 1);
      _tmp1_2 = _tmp1_1;
      op0_3 = op0_2;
      _tmp2_2 = _tmp2_1;
    } else {
      _tmp1_2 = _tmp1_3;
      op0_3 = op0_4;
      _tmp2_2 = _tmp2_3;
    }
    _tmp3_1 = res;
    res = _tmp3_1 >> 1;
    one0_4 = one0_5 >> 2;
    _tmp1_3 = _tmp1_2;
    op0_4 = op0_3;
    _tmp2_3 = _tmp2_2;
    one0_5 = one0_4;
  }
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void get_dim() {
  unsigned short *width;
  unsigned short *height;
  unsigned short w_1;
  unsigned short h_1;
  
  width = getReadPtr(sobel_width, 1);
  w_1 = width[0];
  height = getReadPtr(sobel_height, 1);
  h_1 = height[0];
  img_w = w_1;
  img_h = h_1;
}

static int isSchedulable_get_dim() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(sobel_width, 1);
  _tmp2_1 = hasTokens(sobel_height, 1);
  if (_tmp1_1 && _tmp2_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void read_pixel() {
  unsigned char *y;
  unsigned char y_component_1;
  unsigned short _tmp0_1;
  unsigned short _tmp1_1;
  unsigned short _tmp2_1;
  
  y = getReadPtr(sobel_y, 1);
  y_component_1 = y[0];
  _tmp0_1 = fill_ptr;
  _tmp1_1 = img_w;
  _tmp2_1 = wcnt;
  pixels[_tmp0_1 * _tmp1_1 + _tmp2_1] = y_component_1;
  wcnt++;
}

static int isSchedulable_read_pixel() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(sobel_y, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void one_line_done() {
  unsigned short _tmp2_1;
  
  wcnt = 0;
  hcnt++;
  fill_ptr++;
  _tmp2_1 = fill_ptr;
  if (_tmp2_1 >= 3) {
    fill_ptr = 0;
  }
}

static int isSchedulable_one_line_done() {
  unsigned short _tmp1_1;
  unsigned short _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = wcnt;
  _tmp2_1 = img_w;
  _tmp0_1 = _tmp1_1 == _tmp2_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void compute_sobel() {
  unsigned char *gradient;
  int coord0[9][2];
  int val0[9];
  unsigned short _tmp0_1;
  int i0_1;
  unsigned short _tmp1_1;
  int j0_1;
  int k0_1;
  int _tmp2_1;
  int _tmp3_1;
  unsigned char _tmp4_1;
  int k0_2;
  int k0_3;
  int _tmp5_1;
  int _tmp6_1;
  int _tmp7_1;
  int _tmp8_1;
  int _tmp9_1;
  int _tmp10_1;
  int gx0_1;
  int _tmp11_1;
  int _tmp12_1;
  int _tmp13_1;
  int _tmp14_1;
  int _tmp15_1;
  int _tmp16_1;
  int gy0_1;
  unsigned char g0_1;
  
  gradient = getWritePtr(sobel_gradient, 1);
  _tmp0_1 = wcnt;
  i0_1 = _tmp0_1;
  _tmp1_1 = consume_ptr;
  j0_1 = _tmp1_1;
  coord0[0][0] = i0_1 - 1;
  coord0[0][1] = j0_1 - 1;
  coord0[1][0] = i0_1;
  coord0[1][1] = j0_1 - 1;
  coord0[2][0] = i0_1 + 1;
  coord0[2][1] = j0_1 - 1;
  coord0[3][0] = i0_1 - 1;
  coord0[3][1] = j0_1;
  coord0[4][0] = i0_1;
  coord0[4][1] = j0_1;
  coord0[5][0] = i0_1 + 1;
  coord0[5][1] = j0_1;
  coord0[6][0] = i0_1 - 1;
  coord0[6][1] = j0_1 + 1;
  coord0[7][0] = i0_1;
  coord0[7][1] = j0_1 + 1;
  coord0[8][0] = i0_1 + 1;
  coord0[8][1] = j0_1 + 1;
  k0_1 = 0;
  k0_3 = k0_1;
  while (k0_3 < 9) {
    _tmp2_1 = coord0[k0_3][0];
    _tmp3_1 = coord0[k0_3][1];
    _tmp4_1 = get_pixel(_tmp2_1, _tmp3_1);
    val0[k0_3] = _tmp4_1;
    k0_2 = k0_3 + 1;
    k0_3 = k0_2;
  }
  _tmp5_1 = val0[0];
  _tmp6_1 = val0[2];
  _tmp7_1 = val0[3];
  _tmp8_1 = val0[5];
  _tmp9_1 = val0[6];
  _tmp10_1 = val0[8];
  gx0_1 = -_tmp5_1 + _tmp6_1 - (_tmp7_1 << 1) + (_tmp8_1 << 1) - _tmp9_1 + _tmp10_1;
  _tmp11_1 = val0[0];
  _tmp12_1 = val0[1];
  _tmp13_1 = val0[2];
  _tmp14_1 = val0[6];
  _tmp15_1 = val0[7];
  _tmp16_1 = val0[8];
  gy0_1 = -_tmp11_1 - (_tmp12_1 << 1) - _tmp13_1 + _tmp14_1 + (_tmp15_1 << 1) + _tmp16_1;
  g0_1 = sqrt(gx0_1 * gx0_1 + gy0_1 * gy0_1);
  wcnt++;
  gradient[0] = g0_1;
}

static int isSchedulable_compute_sobel() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void done() {
  unsigned short _tmp1_1;
  
  wcnt = 0;
  consume_ptr++;
  _tmp1_1 = consume_ptr;
  if (_tmp1_1 >= 3) {
    consume_ptr = 0;
  }
}

static int isSchedulable_done() {
  unsigned short _tmp1_1;
  unsigned short _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = wcnt;
  _tmp2_1 = img_w;
  _tmp0_1 = _tmp1_1 == _tmp2_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_compute_sobel = 0,
  s_fill_line1,
  s_fill_line2,
  s_get_dim,
  s_get_line
};

static char *stateNames[] = {
  "s_compute_sobel",
  "s_fill_line1",
  "s_fill_line2",
  "s_get_dim",
  "s_get_line"
};

static enum states _FSM_state = s_get_dim;

static int compute_sobel_state_scheduler() {
  int res;
  
  if (isSchedulable_done()) {
    done();
    _FSM_state = s_get_line;
    res = 1;
  } else {
    if (isSchedulable_compute_sobel()) {
      if (hasRoom(sobel_gradient, 1)) {
        compute_sobel();
        _FSM_state = s_compute_sobel;
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

static int fill_line1_state_scheduler() {
  int res;
  
  if (isSchedulable_one_line_done()) {
    one_line_done();
    _FSM_state = s_fill_line2;
    res = 1;
  } else {
    if (isSchedulable_read_pixel()) {
      read_pixel();
      _FSM_state = s_fill_line1;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int fill_line2_state_scheduler() {
  int res;
  
  if (isSchedulable_one_line_done()) {
    one_line_done();
    _FSM_state = s_compute_sobel;
    res = 1;
  } else {
    if (isSchedulable_read_pixel()) {
      read_pixel();
      _FSM_state = s_fill_line2;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int get_dim_state_scheduler() {
  int res;
  
  if (isSchedulable_get_dim()) {
    get_dim();
    _FSM_state = s_fill_line1;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_line_state_scheduler() {
  int res;
  
  if (isSchedulable_one_line_done()) {
    one_line_done();
    _FSM_state = s_compute_sobel;
    res = 1;
  } else {
    if (isSchedulable_read_pixel()) {
      read_pixel();
      _FSM_state = s_get_line;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

int sobel_scheduler() {
  int res = 1;
  
  while (res) {
    switch (_FSM_state) {
      case s_compute_sobel:
        res = compute_sobel_state_scheduler();
        break;
      case s_fill_line1:
        res = fill_line1_state_scheduler();
        break;
      case s_fill_line2:
        res = fill_line2_state_scheduler();
        break;
      case s_get_dim:
        res = get_dim_state_scheduler();
        break;
      case s_get_line:
        res = get_line_state_scheduler();
        break;
      default:
        printf("unknown state\n");
        break;
    }
  }
  
  return 0;
}

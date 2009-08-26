// Generated from "row_02"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *row_02_X;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *row_02_Y;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int ROW = 1;
static int isz = 13;
static int osz = 13;
static int W0[4] = {2048, 2676, 2841, 1609};
static int W1[4] = {2048, 1108, 565, 2408};

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  short *Y;
  short *X;
  short x[8];
  short w0[8];
  int y0[8];
  int z0[8];
  int o0[8];
  int _i0_1;
  short _tmp0_1;
  int _i0_2;
  int _i0_3;
  short _tmp2_1;
  short _tmp3_1;
  short _tmp4_1;
  short _tmp5_1;
  short _tmp6_1;
  short _tmp7_1;
  short _tmp8_1;
  short _tmp9_1;
  int i0_1;
  int _tmp10_1;
  int _tmp11_1;
  int _tmp12_1;
  int _tmp13_1;
  int _tmp14_1;
  int _tmp15_1;
  int _tmp16_1;
  int _tmp17_1;
  int i0_2;
  int i0_3;
  int i1_1;
  int _tmp18_1;
  int _tmp19_1;
  unsigned char _tmp21_2;
  int i1_2;
  int i1_3;
  int i2_1;
  int _tmp22_1;
  int _tmp23_1;
  int _tmp24_1;
  int _tmp25_1;
  int _tmp26_1;
  int _tmp27_1;
  int _tmp28_1;
  int _tmp29_1;
  int i2_2;
  int i2_3;
  int _tmp30_1;
  int _tmp31_1;
  int y60_1;
  int _tmp32_1;
  int _tmp33_1;
  int y70_1;
  int _tmp34_1;
  int _tmp35_1;
  int _tmp36_1;
  int _tmp37_1;
  int _tmp38_1;
  int _tmp39_1;
  int i3_1;
  int _tmp40_1;
  int _tmp41_1;
  int _tmp42_1;
  int _tmp43_1;
  int _tmp44_1;
  int _tmp45_1;
  int _tmp46_1;
  int _tmp47_1;
  int i3_2;
  int i3_3;
  int i4_1;
  int _tmp48_1;
  unsigned char _tmp50_2;
  int i4_2;
  int i4_3;
  int _tmp51_1;
  int _tmp52_1;
  int _tmp53_1;
  int _tmp54_1;
  int _tmp55_1;
  int _tmp56_1;
  int _tmp57_1;
  int _tmp58_1;
  int _i1_1;
  short _tmp59_1;
  int _i1_2;
  int _i1_3;
  
  Y = getWritePtr(row_02_Y, 8);
  X = getReadPtr(row_02_X, 8);
  _i0_1 = 0;
  _i0_3 = _i0_1;
  while (_i0_3 < 8) {
    _tmp0_1 = X[1 * _i0_3 + 0];
    x[_i0_3] = _tmp0_1;
    _i0_2 = _i0_3 + 1;
    _i0_3 = _i0_2;
  }
  o0[0] = 128;
  o0[1] = 128;
  o0[2] = 0;
  o0[3] = 0;
  o0[4] = 0;
  o0[5] = 0;
  o0[6] = 0;
  o0[7] = 0;
  _tmp2_1 = x[0];
  y0[0] = _tmp2_1;
  _tmp3_1 = x[4];
  y0[1] = _tmp3_1;
  _tmp4_1 = x[2];
  y0[2] = _tmp4_1;
  _tmp5_1 = x[6];
  y0[3] = _tmp5_1;
  _tmp6_1 = x[1];
  y0[4] = _tmp6_1;
  _tmp7_1 = x[7];
  y0[5] = _tmp7_1;
  _tmp8_1 = x[5];
  y0[6] = _tmp8_1;
  _tmp9_1 = x[3];
  y0[7] = _tmp9_1;
  i0_1 = 0;
  i0_3 = i0_1;
  while (i0_3 < 4) {
    _tmp10_1 = y0[2 * i0_3];
    _tmp11_1 = W0[i0_3];
    _tmp12_1 = y0[2 * i0_3 + 1];
    _tmp13_1 = W1[i0_3];
    z0[i0_3 * 2 + 0] = _tmp10_1 * _tmp11_1 + _tmp12_1 * _tmp13_1;
    _tmp14_1 = y0[2 * i0_3];
    _tmp15_1 = W1[i0_3];
    _tmp16_1 = y0[2 * i0_3 + 1];
    _tmp17_1 = W0[i0_3];
    z0[i0_3 * 2 + 1] = _tmp14_1 * _tmp15_1 - _tmp16_1 * _tmp17_1;
    i0_2 = i0_3 + 1;
    i0_3 = i0_2;
  }
  i1_1 = 0;
  i1_3 = i1_1;
  while (i1_3 < 8) {
    _tmp18_1 = z0[i1_3];
    _tmp19_1 = o0[i1_3];
    _tmp21_2 = 0;
    y0[i1_3] = (_tmp18_1 + _tmp19_1) >> _tmp21_2;
    i1_2 = i1_3 + 1;
    i1_3 = i1_2;
  }
  i2_1 = 0;
  i2_3 = i2_1;
  while (i2_3 < 2) {
    _tmp22_1 = y0[4 * i2_3];
    _tmp23_1 = y0[4 * i2_3 + 2];
    z0[i2_3 * 4 + 0] = _tmp22_1 + _tmp23_1;
    _tmp24_1 = y0[4 * i2_3];
    _tmp25_1 = y0[4 * i2_3 + 2];
    z0[i2_3 * 4 + 1] = _tmp24_1 - _tmp25_1;
    _tmp26_1 = y0[4 * i2_3 + 1];
    _tmp27_1 = y0[4 * i2_3 + 3];
    z0[i2_3 * 4 + 2] = _tmp26_1 + _tmp27_1;
    _tmp28_1 = y0[4 * i2_3 + 1];
    _tmp29_1 = y0[4 * i2_3 + 3];
    z0[i2_3 * 4 + 3] = _tmp28_1 - _tmp29_1;
    i2_2 = i2_3 + 1;
    i2_3 = i2_2;
  }
  _tmp30_1 = z0[5];
  _tmp31_1 = z0[7];
  y60_1 = (181 * (_tmp30_1 + _tmp31_1) + 128) >> 8;
  _tmp32_1 = z0[5];
  _tmp33_1 = z0[7];
  y70_1 = (181 * (_tmp32_1 - _tmp33_1) + 128) >> 8;
  _tmp34_1 = z0[0];
  y0[0] = _tmp34_1;
  _tmp35_1 = z0[1];
  y0[1] = _tmp35_1;
  _tmp36_1 = z0[4];
  y0[2] = _tmp36_1;
  _tmp37_1 = z0[6];
  y0[3] = _tmp37_1;
  _tmp38_1 = z0[2];
  y0[4] = _tmp38_1;
  _tmp39_1 = z0[3];
  y0[5] = _tmp39_1;
  y0[6] = y60_1;
  y0[7] = y70_1;
  i3_1 = 0;
  i3_3 = i3_1;
  while (i3_3 < 2) {
    _tmp40_1 = y0[4 * i3_3];
    _tmp41_1 = y0[4 * i3_3 + 2];
    z0[i3_3 * 4 + 0] = _tmp40_1 + _tmp41_1;
    _tmp42_1 = y0[4 * i3_3];
    _tmp43_1 = y0[4 * i3_3 + 2];
    z0[i3_3 * 4 + 1] = _tmp42_1 - _tmp43_1;
    _tmp44_1 = y0[4 * i3_3 + 1];
    _tmp45_1 = y0[4 * i3_3 + 3];
    z0[i3_3 * 4 + 2] = _tmp44_1 + _tmp45_1;
    _tmp46_1 = y0[4 * i3_3 + 1];
    _tmp47_1 = y0[4 * i3_3 + 3];
    z0[i3_3 * 4 + 3] = _tmp46_1 - _tmp47_1;
    i3_2 = i3_3 + 1;
    i3_3 = i3_2;
  }
  i4_1 = 0;
  i4_3 = i4_1;
  while (i4_3 < 8) {
    _tmp48_1 = z0[i4_3];
    _tmp50_2 = 8;
    y0[i4_3] = _tmp48_1 >> _tmp50_2;
    i4_2 = i4_3 + 1;
    i4_3 = i4_2;
  }
  _tmp51_1 = y0[0];
  w0[0] = _tmp51_1;
  _tmp52_1 = y0[4];
  w0[1] = _tmp52_1;
  _tmp53_1 = y0[6];
  w0[2] = _tmp53_1;
  _tmp54_1 = y0[2];
  w0[3] = _tmp54_1;
  _tmp55_1 = y0[3];
  w0[4] = _tmp55_1;
  _tmp56_1 = y0[7];
  w0[5] = _tmp56_1;
  _tmp57_1 = y0[5];
  w0[6] = _tmp57_1;
  _tmp58_1 = y0[1];
  w0[7] = _tmp58_1;
  _i1_1 = 0;
  _i1_3 = _i1_1;
  while (_i1_3 < 8) {
    _tmp59_1 = w0[_i1_3];
    Y[1 * _i1_3 + 0] = _tmp59_1;
    _i1_2 = _i1_3 + 1;
    _i1_3 = _i1_2;
  }
}

static int isSchedulable_untagged01() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(row_02_X, 8);
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

int row_02_scheduler() {
  int res = 1;
  
  while (res) {
    if (isSchedulable_untagged01()) {
      if (hasRoom(row_02_Y, 8)) {
        untagged01();
        res = 1;
      } else {
        res = 0;
      }
    } else {
      res = 0;
    }
  }
  
  return 0;
}

/*
 * Copyright (c) 2013, INSA of Rennes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#include "openhevc.h"

#include "hevcpred.h"
#include "hevcdsp.h"

#include <emmintrin.h>
#ifdef __SSSE3__
#include <tmmintrin.h>
#endif
#ifdef __SSE4_1__
#include <smmintrin.h>
#endif

static HEVCPredContext hevcPred;
static HEVCDSPContext hevcDsp;


/* Log2CbSize in openHEVC */
/* 1 -  3 -  5 -  7 - 11 - 15 - 23 - 31 - 47 - 63 --> _width
   2 -  4 -  6 -  8 - 12 - 16 - 24 - 32 - 48 - 64 --> width
   2 -  4 -  2 -  8 -  4 - 16 -  8 - 16 - 16 - 16 --> vector size
   0 -  1 -  0 -  2 -  1 -  3 -  2 -  3 -  3 -  3 --> function id
   */
static const int lookup_tab_openhevc_function[64] = {
   -1,  0, -1,  1, -1,  0, -1,  2,
   -1, -1, -1,  1, -1, -1, -1,  3,
   -1, -1, -1, -1, -1, -1, -1,  2,
   -1, -1, -1, -1, -1, -1, -1,  3,
   -1, -1, -1, -1, -1, -1, -1, -1,
   -1, -1, -1, -1, -1, -1, -1,  3,
   -1, -1, -1, -1, -1, -1, -1, -1,
   -1, -1, -1, -1, -1, -1, -1,  3};

int openhevc_init_context()
{
	ff_hevc_dsp_init(&hevcDsp, 8);
	ff_hevc_pred_init(&hevcPred, 8);
	return 0;
}

void put_hevc_qpel_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
u8 _width, u8 _height, i32 mx, i32 my)
{
    u8  *src = &_src[3+3*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;
    int idx = lookup_tab_openhevc_function[_width] - 1;

    hevcDsp.put_hevc_qpel[idx][!!my][!!mx](dst, width, src, srcstride, height, mx, my, width);
}

void put_hevc_epel_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
u8 _width, u8 _height, i32 mx, i32 my)
{
    u8  *src = &_src[1+1*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;
    int idx = lookup_tab_openhevc_function[_width];

    hevcDsp.put_hevc_epel[idx][!!my][!!mx](dst, width, src, srcstride, height, mx, my, width);
}

/* WEIGHTED PREDICTION */

void put_unweighted_pred_orcc(i16 _src[2][64*64], int _width, int _height, u8 rdList, u8 _dst[64*64])
{
    i16 * src = _src[rdList];
    u8 * dst = _dst;
    u8 width = _width + 1;
    u8 height = _height + 1;
    int idx = lookup_tab_openhevc_function[_width];

    hevcDsp.put_unweighted_pred[idx](dst, width, src, width, width, height);
}

void put_weighted_pred_avg_orcc (i16 src[2][64*64], int _width, int _height, u8 dst[64*64])
{
  u8 width = _width + 1;
  u8 height = _height + 1;
  int idx = lookup_tab_openhevc_function[_width];

  hevcDsp.put_weighted_pred_avg[idx](dst, width,
    src[0], src[1], width, width, height);
}

void weighted_pred_orcc(int logWD, int weightCu[2], int offsetCu[2] ,
		i16 _src[2][64*64], int _width, int _height, u8 rdList, u8 _dst[64*64])
{
  i16 * src = _src[rdList];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int wX = weightCu[rdList];
  int oX = offsetCu[rdList];
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

  hevcDsp.weighted_pred[idx](locLogWD, wX, oX, dst, width, src, width, width, height);
}

void weighted_pred_avg_orcc(int logWD , int weightCu[2], int offsetCu[2] ,
		i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64])
{
  i16 * src = _src[0];
  i16 * src1 = _src[1];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int w0 = weightCu[0];
  int w1 = weightCu[1];
  int o0 = offsetCu[0];
  int o1 = offsetCu[1];
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

  hevcDsp.weighted_pred_avg[idx](locLogWD, w0, w1, o0, o1, dst, width, src, src1, width, width, height);
}


void pred_planar_orcc(u8 _src[4096], u8 _top[129], u8 _left[129], i32 stride, i32 log2size)
{
    u8 *src        = _src;
    u8 *top  = _top + 1;
    u8 *left = _left + 1;

    hevcPred.pred_planar[log2size - 2](src, top, left, stride);
}

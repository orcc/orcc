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


static HEVCPredContext hevcPred;
static HEVCDSPContext hevcDsp;

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

    if(width == 16 || width == 32 || width == 64) {
        hevcDsp.put_hevc_qpel[2][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    } else if(width == 8 || width == 24) {
        hevcDsp.put_hevc_qpel[1][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    } else {
        hevcDsp.put_hevc_qpel[0][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    }
}

void put_hevc_epel_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
u8 _width, u8 _height, i32 mx, i32 my)
{
    u8  *src = &_src[1+1*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 16 || width == 32 || width == 64) {
        hevcDsp.put_hevc_epel[3][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    } else if(width == 8 || width == 24) {
        hevcDsp.put_hevc_epel[2][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    } else if(width == 4 || width == 12 || width == 20) {
        hevcDsp.put_hevc_epel[1][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    } else {
        hevcDsp.put_hevc_epel[0][!!my][!!mx](dst, width, src, srcstride, width, height, mx, my);
    }
}

void put_hevc_qpel_h_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
i32 filterIdx,  u8 _width, u8 _height)
{
    u8  *src = &_src[3+3*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32 || width == 64) {
        hevcDsp.put_hevc_qpel[1][0][1](dst, width, src, srcstride, width, height, filterIdx, 0);
    } else {
        hevcDsp.put_hevc_qpel[0][0][1](dst, width, src, srcstride, width, height, filterIdx, 0);
    }
}

void put_hevc_qpel_v_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
i32 filterIdx,  u8 _width, u8 _height)
{
    u8  *src = &_src[3+3*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32 || width == 64) {
        hevcDsp.put_hevc_qpel[1][1][0](dst, width, src, srcstride, width, height, 0, filterIdx);

    } else {
        hevcDsp.put_hevc_qpel[0][1][0](dst, width, src, srcstride, width, height, 0, filterIdx);
    }
}

void put_hevc_epel_h_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
u8 _width, u8 _height, i32 mx, i32 my)
{
    u8  *src = &_src[1+1*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32) {
        hevcDsp.put_hevc_epel[2][0][1](dst, width, src, srcstride, width, height, mx, my);
    } else if(width == 4 || width == 12) {
        hevcDsp.put_hevc_epel[1][0][1](dst, width, src, srcstride, width, height, mx, my);
    } else {
        hevcDsp.put_hevc_epel[0][0][1](dst, width, src, srcstride, width, height, mx, my);
    }
}

void put_hevc_epel_v_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
u8 _width, u8 _height, i32 mx, i32 my)
{
    u8  *src = &_src[1+1*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32) {
        hevcDsp.put_hevc_epel[2][1][0](dst, width, src, srcstride, width, height, mx, my);
    } else if(width == 4 || width == 12) {
        hevcDsp.put_hevc_epel[1][1][0](dst, width, src, srcstride, width, height, mx, my);
    } else {
        hevcDsp.put_hevc_epel[0][1][0](dst, width, src, srcstride, width, height, mx, my);
    }
}

void put_hevc_qpel_hv_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
i32 filterIdx[2],  u8 _width, u8 _height)
{
    u8  *src = &_src[3+ 3*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32 || width == 64) {
        hevcDsp.put_hevc_qpel[1][1][1](dst, width, src, srcstride, width, height, filterIdx[0], filterIdx[1]);
    } else {
        hevcDsp.put_hevc_qpel[0][1][1](dst, width, src, srcstride, width, height, filterIdx[0], filterIdx[1]);
    }
}

void put_hevc_epel_hv_orcc(i16 _dst[2][64*64], u8 listIdx,
u8 _src[71*71], u8 srcstride,
u8 _width, u8 _height, i32 mx, i32 my)
{
    u8  *src = &_src[1+1*srcstride];
    i16 *dst = _dst[listIdx];
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32) {
        hevcDsp.put_hevc_epel[2][1][1](dst, width, src, srcstride, width, height, mx, my);
    } else if(width == 4 || width == 12) {
        hevcDsp.put_hevc_epel[1][1][1](dst, width, src, srcstride, width, height, mx, my);
    } else {
        hevcDsp.put_hevc_epel[0][1][1](dst, width, src, srcstride, width, height, mx, my);
    }
}

void put_unweighted_pred_orcc(i16 _src[2][64*64], int _width, int _height, u8 rdList, u8 _dst[64*64])
{
    i16 * src = _src[rdList];
    u8 * dst = _dst;
    u8 width = _width + 1;
    u8 height = _height + 1;

    if(width == 8 || width == 16 || width == 24 || width == 32) {
        hevcDsp.put_unweighted_pred[2](dst, width, src, width, width, height);
      } else if(width == 4 || width == 12) {
        hevcDsp.put_unweighted_pred[1](dst, width, src, width, width, height);
      } else {
        hevcDsp.put_unweighted_pred[0](dst, width, src, width, width, height);
      }
}

void put_unweighted_pred_avg_orcc(u8 _dst[2][64*64], i16 _src[2][64*64],
u8 _width, u8 _height)
{
    i16 *src1 = &_src[0];
    i16 *src2 = &_src[1];
    u8 *dst = _dst[0];
    u8 width = _width + 1;
    u8 height = _height + 1;

    hevcDsp.put_weighted_pred_avg[0](dst, width, src1, src2, width, width, height);
}

// DR 1402
void put_weighted_pred_avg_orcc (i16 src[2][64*64], int _width, int _height, u8 dst[64*64])
{
  u8 width = _width + 1;
  u8 height = _height + 1;

  if (width == 16 || width == 32 || width == 64) {
	  hevcDsp.put_weighted_pred_avg[2](dst, width,
		  src[0], src[1], width, width, height);
  } else if(width == 8 || width == 24) {
	  hevcDsp.put_weighted_pred_avg[1](dst, width,
		  src[0], src[1], width, width, height);
  } else {
	  hevcDsp.put_weighted_pred_avg[0](dst, width,
		  src[0], src[1], width, width, height);
  }
}

void pred_planar_orcc(u8 _src[4096], u8 _top[129], u8 _left[129], i32 stride, i32 log2size)
{
    u8 *src        = _src;
    const u8 *top  = _top + 1;
    const u8 *left = _left + 1;

    hevcPred.pred_planar[log2size - 2](src, top, left, stride);
}

void pred_angular_orcc(u8 _src[4096], u8 _top[129], u8 _left[129], i32 stride, i32 idx, u8 mode, i32 log2size){
	u8 *src        = _src;
    const u8 *top  = _top + 1;
    const u8 *left = _left + 1;

    hevcPred.pred_angular[log2size - 2](src, top, left, stride, idx, mode);
}

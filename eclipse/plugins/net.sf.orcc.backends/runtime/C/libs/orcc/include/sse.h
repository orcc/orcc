/*
 * Copyright (c) 2014, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
 
 
/**
 * Optimized procedures (SSE).
 * 
 * @author Daniele Renzi (EPFL) <daniele.renzi@epfl.ch>
 */

#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <stdint.h>

#include "types.h"

/*****************************************************************************************************************/

#define DPB_SIZE      17
#define PICT_WIDTH  4096
#define PICT_HEIGHT 2048
#define BORDER_SIZE  128

/*****************************************************************************************************************/


#if defined(__ICC) && __ICC < 1200 || defined(__SUNPRO_C)
    #define DECLARE_ALIGNED(n,t,v)      t __attribute__ ((aligned (n))) v
#elif defined(__TI_COMPILER_VERSION__)
    #define DECLARE_ALIGNED(n,t,v)                      \
        AV_PRAGMA(DATA_ALIGN(v,n))                      \
        t __attribute__((aligned(n))) v
#elif defined(__GNUC__)
    #define DECLARE_ALIGNED(n,t,v)      t __attribute__ ((aligned (n))) v
#elif defined(_MSC_VER)
    #define DECLARE_ALIGNED(n,t,v)      __declspec(align(n)) t v
#else
    #define DECLARE_ALIGNED(n,t,v)      t v
#endif

int sse_init_context();

/***********************************************************************************************************************************
 SelectCu 
 ***********************************************************************************************************************************/

void copy_8_8_16_64x64_orcc(
  u8 outputSample[64 * 64],
  u8 inputSample[16],
  u32 idxBlkStride);

void copy_8_8_16_32x32_orcc(
  u8 outputSample[32 * 32],
  u8 inputSample[16],
  u32 idxBlkStride);

void copy_8_8_16_1x4352_orcc(
  u8 outputSample[1 * 4352],
  u8 inputSample[16],
  u32 idxBlkStride);

void copy_8_8_8_1x2304_orcc(
  u8 outputSample[1 * 2304],
  u8 inputSample[16],
  u32 idxBlkStride);

void copy_8_8_var_orcc(
  u8 * outputSample,
  u8 * inputSample,
  u32 idxBlkStride,
  u8 size);

void add_8_16_clip_16_1x16_orcc(
  u8 predSample[1 * 16],
  i16 resSample[16],
  u8 Sample[16],
  u16 idxBlkStride);

void add_8_16_clip_8_64x64_orcc(
  u8 predSample[64 * 64],
  i16 resSample[8],
  u8 Sample[8],
  u16 idxBlkStride);

void add_8_16_clip_16_64x64_orcc(
  u8 predSample[64 * 64],
  i16 resSample[16],
  u8 Sample[16],
  u16 idxBlkStride);

void add_8_16_clip_32_64x64_orcc(
  u8 predSample[64 * 64],
  i16 resSample[32],
  u8 Sample[32],
  u16 idxBlkStride);

void add_8_16_clip_64_64x64_orcc(
  u8 predSample[64 * 64],
  i16 resSample[64],
  u8 Sample[64],
  u16 idxBlkStride);

void add_8_16_clip_256_64x64_orcc(
  u8 predSample[64 * 64],
  i16 resSample[256],
  u8 Sample[256],
  u16 idxBlkStride);

void add_8_16_clip_1024_64x64_orcc(
  u8 predSample[64 * 64],
  i16 resSample[1024],
  u8 Sample[1024],
  u16 idxBlkStride);

void add_8_16_clip_16_32x32_orcc(
  u8 predSample[32 * 32],
  i16 resSample[16],
  u8 Sample[16],
  u16 idxBlkStride);

void add_8_16_clip_64_32x32_orcc(
  u8 predSample[32 * 32],
  i16 resSample[64],
  u8 Sample[64],
  u16 idxBlkStride);

void add_8_16_clip_256_32x32_orcc(
  u8 predSample[32 * 32],
  i16 resSample[256],
  u8 Sample[256],
  u16 idxBlkStride);

void addClip_orcc(
  u16 blkAddr[2],
  u16 blkAddrChr[2],
  u16 blkAddrRes[2],
  u16 blkAddrResChr[2],
  u32 intraIdx,
  u32 idxRes,
  u8 dbfIdx,
  u8 numBlkSide,
  i16 puAddr[2],
  i16 puAddrChr[2],
  u16 tuAddr[2],
  u16 tuAddrChr[2],
  u8 dbfPict[2][3][4096][2048],
  u8 lumaPred[1024][64][64],
  u8 chromaPred[1024][2][32][32],
  i16 residual[8192][6144]);

/*************************/
/* DecodingPictureBuffer */
/*************************/

void copy_cu_dpb_luma_orcc(
  u8 samp[256],
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT+2*BORDER_SIZE][PICT_WIDTH+2*BORDER_SIZE],
  i32 xPixIdx,
  i32 yPixIdx,
  i8  lastIdx);

void copy_cu_dpb_chroma_orcc(
  u8 samp[64],
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT/2+2*BORDER_SIZE][PICT_WIDTH/2+2*BORDER_SIZE],
  i32 xPixIdx,
  i32 yPixIdx,
  i8  lastIdx);

void getmvinfo_dpb_64_luma_orcc(
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT+2*BORDER_SIZE][PICT_WIDTH+2*BORDER_SIZE],
  u8 RefCu[(64 + 7) * (64 + 7)],
  u8 idx,
  u8 sideMax,
  i32 xOffset,
  i32 yOffset);

void getmvinfo_dpb_32_luma_orcc(
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT+2*BORDER_SIZE][PICT_WIDTH+2*BORDER_SIZE],
  u8 RefCu[(32 + 7) * (32 + 7)],
  u8 idx,
  u8 sideMax,
  i32 xOffset,
  i32 yOffset);

void getmvinfo_dpb_16_luma_orcc(
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT+2*BORDER_SIZE][PICT_WIDTH+2*BORDER_SIZE],
  u8 RefCu[(16 + 7) * (16 + 7)],
  u8 idx,
  u8 sideMax,
  i32 xOffset,
  i32 yOffset);

void getmvinfo_dpb_64_chroma_orcc(
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT/2+2*BORDER_SIZE][PICT_WIDTH/2+2*BORDER_SIZE],
  u8 RefCu[(32 + 3) * (32 + 3)],
  u8 idx,
  u8 sideMax,
  i32 xOffset,
  i32 yOffset);

void getmvinfo_dpb_32_chroma_orcc(
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT/2+2*BORDER_SIZE][PICT_WIDTH/2+2*BORDER_SIZE],
  u8 RefCu[(16 + 3) * (16 + 3)],
  u8 idx,
  u8 sideMax,
  i32 xOffset,
  i32 yOffset);

void getmvinfo_dpb_16_chroma_orcc(
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT/2+2*BORDER_SIZE][PICT_WIDTH/2+2*BORDER_SIZE],
  u8 RefCu[(8 + 3) * (8 + 3)],
  u8 idx,
  u8 sideMax,
  i32 xOffset,
  i32 yOffset);

void fillBorder_luma_orcc(
	u8 pictureBuffer[17][2304][4352],
	i8 lastIdx,
	int xSize,
	int ySize,
	u16 border_size);

void fillBorder_chroma_orcc(
	u8 pictureBuffer[17][1280][2304],
	i8 lastIdx,
	int xSize,
	int ySize,
	u16 border_size);


/***********************/
/* Weighted Prediction */
/***********************/

/* Weighted Pred Mono */

void ff_hevc_weighted_pred_mono2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono4_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono8_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono16_8_sse(u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);

void (*weighted_pred_mono[4])(
        u8 denom,
        i16 wlxFlag,
        i16 olxFlag, i16 ol1Flag,
        u8 *_dst, int _dststride,
        i16 *src,
        int srcstride,
        int width, int height);

void weighted_pred_mono_orcc (int logWD , int weightCu[2], int offsetCu[2],
		i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64]);


/* put_unweighted_predz_zscan */

void ff_hevc_put_unweighted_pred_zscan2_2_8_sse (u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_put_unweighted_pred_zscan4_4_8_sse (u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_put_unweighted_pred_zscan8_4_8_sse (u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_put_unweighted_pred_zscan16_4_8_sse(u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_put_unweighted_pred_zscan4_2_8_sse (u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_put_unweighted_pred_zscan8_2_8_sse (u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_put_unweighted_pred_zscan16_2_8_sse(u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);

void (*put_unweighted_pred_zscan[2][4])(
        u8 *_dst, int _dststride,
        i16 *src,
        int srcstride,
        int width, int height);

void put_unweighted_pred_zscan_orcc (i16 _src[2][64*64], int _width, int _height, u8 rdList, u8 _dst[64*64], int iComp);


/* put_weighted_pred_avg_zscan */

void ff_hevc_put_weighted_pred_avg_zscan2_2_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);
void ff_hevc_put_weighted_pred_avg_zscan4_4_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);
void ff_hevc_put_weighted_pred_avg_zscan8_4_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);
void ff_hevc_put_weighted_pred_avg_zscan16_4_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);
void ff_hevc_put_weighted_pred_avg_zscan4_2_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);
void ff_hevc_put_weighted_pred_avg_zscan8_2_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);
void ff_hevc_put_weighted_pred_avg_zscan16_2_8_sse (u8 *_dst, int _dststride, i16 * src1, i16 * src, int srcstride, int width, int height);

void (*put_weighted_pred_avg_zscan[2][4])(
		u8 *_dst, int _dststride,
		i16 * src1,
		i16 * src,
		int srcstride,
		int width, int height);

void put_weighted_pred_avg_zscan_orcc (i16 src[2][64*64], int width, int height, u8 dst[64*64], int iComp);


/* weighted_pred_zscan */

void ff_hevc_weighted_pred_zscan2_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_zscan4_4_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_zscan8_4_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_zscan16_4_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_zscan4_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_zscan8_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_zscan16_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);

void (*weighted_pred_zscan[2][4])(
		u8 denom,
		i16 wlxFlag, i16 olxFlag,
		u8 *_dst, int _dststride,
		i16 *src, int srcstride,
		int width, int height);

void weighted_pred_zscan_orcc (int logWD, int weightCu[2], int offsetCu[2], i16 src[2][64*64], int width, int height, u8 rdList, u8 dst[64*64], int iComp);


/* weighted_pred_avg_zscan */

void ff_hevc_weighted_pred_avg_zscan2_2_8_sse (u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_avg_zscan4_4_8_sse (u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_avg_zscan8_4_8_sse (u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_avg_zscan16_4_8_sse(u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_avg_zscan4_2_8_sse (u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_avg_zscan8_2_8_sse (u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_avg_zscan16_2_8_sse(u8 denom, i16 wlxFlag, i16 wl1Flag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src1, i16 *src, int srcstride, int width, int height);

void (*weighted_pred_avg_zscan[2][4])(
        u8 denom,
        i16 wlxFlag, i16 wl1Flag,
        i16 olxFlag, i16 ol1Flag,
        u8 *_dst, int _dststride,
        i16 *src1, i16 *src,
        int srcstride,
        int width, int height);

void weighted_pred_avg_zscan_orcc (int logWD , int weightCu[2], int offsetCu[2], i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64], int iComp);


/* weighted_pred_mono_zscan */

void ff_hevc_weighted_pred_mono_zscan2_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono_zscan4_4_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono_zscan8_4_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono_zscan16_4_8_sse(u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono_zscan4_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono_zscan8_2_8_sse (u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);
void ff_hevc_weighted_pred_mono_zscan16_2_8_sse(u8 denom, i16 wlxFlag, i16 olxFlag, i16 ol1Flag, u8 *_dst, int _dststride, i16 *src, int srcstride, int width, int height);

void (*weighted_pred_mono_zscan[2][4])(
        u8 denom,
        i16 wlxFlag,
        i16 olxFlag, i16 ol1Flag,
        u8 *_dst, int _dststride,
        i16 *src,
        int srcstride,
        int width, int height);

void weighted_pred_mono_zscan_orcc (int logWD , int weightCu[2], int offsetCu[2],
		i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64], int iComp);


/********************/
/* Intra Prediction */
/********************/

/* pred_angular */

void pred_angular_4_8_sse(uint8_t *_src, const uint8_t *_top, const uint8_t *_left, ptrdiff_t stride, int c_idx, int mode);
void pred_angular_8_8_sse(uint8_t *_src, const uint8_t *_top, const uint8_t *_left, ptrdiff_t stride, int c_idx, int mode);
void pred_angular_16_8_sse(uint8_t *_src, const uint8_t *_top, const uint8_t *_left, ptrdiff_t stride, int c_idx, int mode);
void pred_angular_32_8_sse(uint8_t *_src, const uint8_t *_top, const uint8_t *_left, ptrdiff_t stride, int c_idx, int mode);

void (*pred_angular[4])(
        uint8_t *_src,
        const uint8_t *_top,
        const uint8_t *_left,
        ptrdiff_t _stride,
        int c_idx, int mode);

void pred_angular_orcc(u8 _src[4096], u8 _top[129], u8 _left[129],
        i32 stride, i32 idx, u8 mode, i32 log2size);


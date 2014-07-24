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

void copy_8_8_8_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 outputStride,
    u32 inputStride);

void copy_8_8_16_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 outputStride,
    u32 inputStride);

void copy_8_8_8_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 outputStride,
    u32 inputStride);

void copy_8_8_64_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 outputStride,
    u32 inputStride);

void copy_8_8_256_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 outputStride,
    u32 inputStride);

void copy_8_8_1024_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 outputStride,
    u32 inputStride);

void copy_8_8_var_orcc(
    u8 * outputSample,
    u8 * inputSample,
    u32 idxBlkStride,
    u8 size);

void add_8_16_clip_8_orcc(
	u8 * predSample,
	i16 * resSample,
	u8 * Sample,
	u16 offsetPred,
	u16 offsetRes,
	u16 OffsetOut);

void add_8_16_clip_16_orcc(
	u8 * predSample,
	i16 * resSample,
	u8 * Sample,
	u16 offsetPred,
	u16 offsetRes,
	u16 OffsetOut);

void add_8_16_clip_32_orcc(
	u8 * predSample,
	i16 * resSample,
	u8 * Sample,
	u16 offsetPred,
	u16 offsetRes,
	u16 OffsetOut);

void add_8_16_clip_64_orcc(
	u8 * predSample,
	i16 * resSample,
	u8 * Sample,
	u16 offsetPred,
	u16 offsetRes,
	u16 OffsetOut);

void add_8_16_clip_256_orcc(
	u8 * predSample,
	i16 * resSample,
	u8 * Sample,
	u16 offsetPred,
	u16 offsetRes,
	u16 OffsetOut);

void add_8_16_clip_1024_orcc(
	u8 * predSample,
	i16 * resSample,
	u8 * Sample,
	u16 offsetPred,
	u16 offsetRes,
	u16 OffsetOut);

/*************************/
/* DecodingPictureBuffer */
/*************************/

void copy_cu_dpb_luma_x64_orcc(
	u8 samp[4096],
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT + 2 * BORDER_SIZE][PICT_WIDTH + 2 * BORDER_SIZE],
	i32 xPixIdx,
	i32 yPixIdx,
	i8  lastIdx);

void copy_cu_dpb_luma_x32_orcc(
	u8 samp[1024],
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT + 2 * BORDER_SIZE][PICT_WIDTH + 2 * BORDER_SIZE],
	i32 xPixIdx,
	i32 yPixIdx,
	i8  lastIdx);

void copy_cu_dpb_luma_x16_orcc(
    u8 samp[256],
    u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT+2*BORDER_SIZE][PICT_WIDTH+2*BORDER_SIZE],
    i32 xPixIdx,
    i32 yPixIdx,
    i8  lastIdx);

void copy_cu_dpb_chroma_x64_orcc(
    u8 samp[1024],
    u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT/2+2*BORDER_SIZE][PICT_WIDTH/2+2*BORDER_SIZE],
    i32 xPixIdx,
    i32 yPixIdx,
    i8  lastIdx);

void copy_cu_dpb_chroma_x32_orcc(
	u8 samp[256],
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT / 2 + 2 * BORDER_SIZE][PICT_WIDTH / 2 + 2 * BORDER_SIZE],
	i32 xPixIdx,
	i32 yPixIdx,
	i8  lastIdx);


void copy_cu_dpb_chroma_x16_orcc(
	u8 samp[64],
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT / 2 + 2 * BORDER_SIZE][PICT_WIDTH / 2 + 2 * BORDER_SIZE],
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

extern void (*weighted_pred_mono[4])(
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

/* xIT */

void ff_hevc_transform0_4x4_2_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride, u8 offset);

void ff_hevc_transform0_4x4_4_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride, u8 offset);

void (* ff_hevc_transform0_4x4_8_zscan_sse4_orcc[2])(
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride, u8 offset);

void ff_hevc_transform0_8x8_2_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void ff_hevc_transform0_8x8_4_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void (* ff_hevc_transform0_8x8_8_zscan_sse4_orcc[2])(
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void ff_hevc_transform0_16x16_2_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void ff_hevc_transform0_16x16_4_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void (* ff_hevc_transform0_16x16_8_zscan_sse4_orcc[2])(
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void ff_hevc_transform0_32x32_2_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void ff_hevc_transform0_32x32_4_8_zscan_sse4 (
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

void (* ff_hevc_transform0_32x32_8_zscan_sse4_orcc[2])(
    i16 *_dst, i16 *coeffs, ptrdiff_t _stride);

/* DBF */
#define LFC_FUNC(DIR, DEPTH, OPT)                                        \
void ff_hevc_ ## DIR ## _loop_filter_chroma_ ## DEPTH ## _ ## OPT(uint8_t *_pix, ptrdiff_t _stride, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

#define LFL_FUNC(DIR, DEPTH, OPT)                                        \
void ff_hevc_ ## DIR ## _loop_filter_luma_ ## DEPTH ## _ ## OPT(uint8_t *_pix, ptrdiff_t stride, int *_beta, int *_tc, \
uint8_t *_no_p, uint8_t *_no_q);

#define LFC_FUNCS(type, depth) \
LFC_FUNC(h, depth, sse2)  \
LFC_FUNC(v, depth, sse2)

#define LFL_FUNCS(type, depth) \
LFL_FUNC(h, depth, ssse3)  \
LFL_FUNC(v, depth, ssse3)

LFC_FUNCS(uint8_t,   8)
// LFC_FUNCS(uint8_t,  10)
LFL_FUNCS(uint8_t,   8)
// LFL_FUNCS(uint8_t,  10)

void (* hevc_h_loop_filter_luma)(
  uint8_t *_pix, ptrdiff_t _stride, int *_beta, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_v_loop_filter_luma)(
  uint8_t *_pix, ptrdiff_t _stride, int *_beta, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_h_loop_filter_chroma)(
  uint8_t *_pix, ptrdiff_t _stride, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_v_loop_filter_chroma)(
  uint8_t *_pix, ptrdiff_t _stride, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_h_loop_filter_luma_c)(
  uint8_t *_pix, ptrdiff_t _stride, int *_beta, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_v_loop_filter_luma_c)(
  uint8_t *_pix, ptrdiff_t _stride, int *_beta, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_h_loop_filter_chroma_c)(
  uint8_t *_pix, ptrdiff_t _stride, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

void (* hevc_v_loop_filter_chroma_c)(
  uint8_t *_pix, ptrdiff_t _stride, int *_tc, uint8_t *_no_p, uint8_t *_no_q);

/* DBF - GenerateBS */

#if HAVE_SSE4

/* 32 bit motion vectors */

void boundaryStrength_mv32_orcc(
  u8 bS[1],
  i32 * mvP0,
  i32 * mvQ0,
  i16 * refPocP0,
  i16 * refPocQ0);

void boundaryStrength0_mv32_orcc(
  u8 bS[1],
  i32 * mvP0,
  i32 * mvQ0);

void boundaryStrength1_mv32_orcc(
  u8 bS[1],
  i32 * mvP0,
  i32 * mvQ0);

void boundaryStrength2_mv32_orcc(
  u8 bS[1],
  i32 * mvP0,
  i32 * mvQ0);

void boundaryStrength3_mv32_orcc(
  u8 * bS,
  u8 predModeP0,
  u8 predModeQ0,
  i32 * mvP0,
  i32 * mvQ0);

#endif // HAVE_SSE4

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

#include "types.h"


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

#include <emmintrin.h>
#ifdef __SSSE3__
#include <tmmintrin.h>
#endif
#ifdef __SSE4_1__
#include <smmintrin.h>
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

/***********************************************************************************************************************************
 DecodingPictureBuffer 
 ***********************************************************************************************************************************/

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

/***********************************************************************************************************************************
 Weighted prediction
 ***********************************************************************************************************************************/

#define WEIGHTED_LOAD2()                                                       \
    r1 = _mm_loadl_epi64((__m128i *) &src[x    ])
#define WEIGHTED_LOAD4()                                                       \
    WEIGHTED_LOAD2()
#define WEIGHTED_LOAD8()                                                       \
    r1 = _mm_loadu_si128((__m128i *) &src[x    ])
#define WEIGHTED_LOAD16()                                                      \
    WEIGHTED_LOAD8();                                                          \
    r2 = _mm_loadu_si128((__m128i *) &src[x + 8])

#define WEIGHTED_LOAD2_1()                                                     \
    r3 = _mm_loadl_epi64((__m128i *) &src1[x    ])
#define WEIGHTED_LOAD4_1()                                                     \
    WEIGHTED_LOAD2_1()
#define WEIGHTED_LOAD8_1()                                                     \
    r3 = _mm_loadu_si128((__m128i *) &src1[x    ])
#define WEIGHTED_LOAD16_1()                                                    \
    WEIGHTED_LOAD8_1();                                                        \
    r4 = _mm_loadu_si128((__m128i *) &src1[x + 8])

#define WEIGHTED_STORE2_8()                                                    \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((short *) (dst + x)) = _mm_extract_epi16(r1, 0)
#define WEIGHTED_STORE4_8()                                                    \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    *((u32 *) &dst[x]) =_mm_cvtsi128_si32(r1)
#define WEIGHTED_STORE8_8()                                                    \
    r1 = _mm_packus_epi16(r1, r1);                                             \
    _mm_storel_epi64((__m128i *) &dst[x], r1)

#define WEIGHTED_STORE16_8()                                                   \
    r1 = _mm_packus_epi16(r1, r2);                                             \
    _mm_store_si128((__m128i *) &dst[x], r1)

#define WEIGHTED_INIT_0_8()                                                    \
    const int dststride = _dststride;                                          \
    u8 *dst = (u8 *) _dst

#define WEIGHTED_INIT_4(H, D)                                                  \
    const int log2Wd = denom + 14 - D;                                         \
    const int shift2 = log2Wd + 1;                                             \
    const int o0     = olxFlag << (D - 8);                                     \
    const int o1     = ol1Flag << (D - 8);                                     \
    const __m128i m1 = _mm_set1_epi16(wlxFlag);                                \
    const __m128i m3 = _mm_set1_epi32((o0 + o1 + 1) << log2Wd);                \
    __m128i s1, s2, s3, s4, s5, s6;                                            \
    WEIGHTED_INIT_0_ ## D()

#define WEIGHTED_COMPUTE_3_8(reg1, reg2)                                       \
    s1   = _mm_mullo_epi16(reg1, m1);                                          \
    s2   = _mm_mulhi_epi16(reg1, m1);                                          \
    s3   = _mm_mullo_epi16(reg2, m2);                                          \
    s4   = _mm_mulhi_epi16(reg2, m2);                                          \
    s5   = _mm_unpacklo_epi16(s1, s2);                                         \
    s6   = _mm_unpacklo_epi16(s3, s4);                                         \
    reg1 = _mm_unpackhi_epi16(s1, s2);                                         \
    reg2 = _mm_unpackhi_epi16(s3, s4);                                         \
    reg1 = _mm_add_epi32(reg1, reg2);                                          \
    reg2 = _mm_add_epi32(s5, s6);                                              \
    reg1 = _mm_srai_epi32(_mm_add_epi32(reg1, m3), shift2);                    \
    reg2 = _mm_srai_epi32(_mm_add_epi32(reg2, m3), shift2);                    \
    reg2 = _mm_packus_epi32(reg2, reg1)

#define WEIGHTED_COMPUTE_4_8(reg1, reg2)                                       \
    s1   = _mm_mullo_epi16(reg2, m1);                                          \
    s2   = _mm_mulhi_epi16(reg2, m1);                                          \
    s5   = _mm_unpacklo_epi16(s1, s2);                                         \
    reg1 = _mm_unpackhi_epi16(s1, s2);                                         \
    reg1 = _mm_srai_epi32(_mm_add_epi32(reg1, m3), shift2);                    \
    reg2 = _mm_srai_epi32(_mm_add_epi32(s5, m3), shift2);                      \
    reg2 = _mm_packus_epi32(reg2, reg1)

#define WEIGHTED_COMPUTE2_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE4_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE8_4(D)                                                 \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1)
#define WEIGHTED_COMPUTE16_4(D)                                                \
    WEIGHTED_COMPUTE_4_ ## D(r3, r1);                                          \
    WEIGHTED_COMPUTE_4_ ## D(r4, r2)

#define WEIGHTED_PRED_MONO(H, D)                                               \
void ff_hevc_weighted_pred_mono ## H ## _ ## D ##_sse(                         \
                                    u8 denom,                                  \
                                    i16 wlxFlag,                               \
                                    i16 olxFlag, i16 ol1Flag,                  \
                                    u8 *_dst, int _dststride,                  \
                                    i16 *src,                                  \
                                    int srcstride,                             \
                                    int width, int height) {                   \
    int x, y;                                                                  \
    __m128i r1, r2, r3, r4;                                                    \
    WEIGHTED_INIT_4(H, D);                                                     \
    for (y = 0; y < height; y++) {                                             \
        for (x = 0; x < width; x += H) {                                       \
            WEIGHTED_LOAD ## H();                                              \
            WEIGHTED_COMPUTE ## H ## _4(D);                                    \
            WEIGHTED_STORE ## H ## _ ## D();                                   \
        }                                                                      \
        dst  += dststride;                                                     \
        src  += srcstride;                                                     \
    }                                                                          \
}

#ifdef __SSE4_1__
WEIGHTED_PRED_MONO( 2, 8)
WEIGHTED_PRED_MONO( 4, 8)
WEIGHTED_PRED_MONO( 8, 8)
WEIGHTED_PRED_MONO(16, 8)
#endif // #ifdef __SSE4_1__

void weighted_pred_mono_orcc (int logWD , int weightCu[2], int offsetCu[2],
		i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64]);

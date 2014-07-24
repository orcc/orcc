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
#include <string.h>

#include "hevc_sse.h"

#include "config.h"

#if HAVE_SSE2
#include <emmintrin.h>
#endif
#if HAVE_SSE3
#include <tmmintrin.h>
#endif
#if HAVE_SSE4
#include <smmintrin.h>
#endif
#if HAVE_AVX2
#include <immintrin.h>
#endif

void (*weighted_pred_mono[4])(
    u8 denom,
    i16 wlxFlag,
    i16 olxFlag, i16 ol1Flag,
    u8 *_dst, int _dststride,
    i16 *src,
    int srcstride,
    int width, int height);

/*****************************************************************************************************************/


/* SELECT_CU */

/* H = number of elements, J = size of each element (bits), K = vector size (bits, SSE --> 128) */

/* Number of vectors to be processed */
#define BLKSIZE_DIV_VECTSIZE(H, J, K) ((H * J) / K)

/* Number of remaining bytes to be processed */
#define BLKSIZE_MOD_VECTSIZE(H, J, K) ((H * J) & (K - 1))


/* copy K H-bits contiguous elements into an array of J-bits elements */
#define COPY(H, J, K)                                                                   \
void copy_ ## H ## _ ## J ## _ ## K ## _orcc(                                           \
	u ## J * outputSample,                                                              \
	u ## H * inputSample,                                                               \
	u32 outputOffset,                                                                   \
    u32 inputOffset)                                                                    \
{                                                                                       \
	int i = 0;                                                                          \
	__m128i * pm128iInputSample = (__m128i *) &inputSample[inputOffset];                \
	__m128i * pm128iOutputSample = (__m128i *) &outputSample[outputOffset];             \
	__m128i m128iInputSample;                                                           \
	for (i = 0; i < BLKSIZE_DIV_VECTSIZE(K, H, 128); i++)                               \
	{                                                                                   \
		m128iInputSample = _mm_loadu_si128(pm128iInputSample + i);                      \
		_mm_storeu_si128(pm128iOutputSample + i, m128iInputSample);                     \
	}                                                                                   \
	if (BLKSIZE_MOD_VECTSIZE(K, H, 128) == 64)                                           \
	{                                                                                   \
		m128iInputSample = _mm_loadl_epi64(pm128iInputSample + i);                  	\
		_mm_storel_epi64(pm128iOutputSample + i, m128iInputSample);                 	\
	}                                                                               	\
}

#if HAVE_SSE2
// Declare more functions if needed
COPY(8, 8,    8)
COPY(8, 8,   16)
COPY(8, 8,   32)
COPY(8, 8,   64)
COPY(8, 8,  256)
COPY(8, 8, 1024)
COPY(8, 8, 4096)
#endif

/* copy KxL H-bits non-contiguous elements into an array of J-bits elements */
#define COPY_2D(H, J, K, L)                                                                \
void copy_2D_ ## H ## _ ## J ## _ ## K ## x ## L ## _orcc(                                 \
	u ## J * outputSample,                                                              \
	u ## H * inputSample,                                                               \
	u32 outputOffset,                                                                   \
    u32 inputOffset,                                                                    \
    u32 outputStride,                                                                   \
    u32 inputStride)                                                                    \
{                                                                                       \
	int i = 0, j = 0;                                                                          \
	__m128i * pm128iInputSample = (__m128i *) &inputSample[inputOffset];                \
	__m128i * pm128iOutputSample = (__m128i *) &outputSample[outputOffset];             \
	__m128i m128iInputSample;                                                           \
	int inputVectorStride = H * inputStride / 128;                                                           \
	int outputVectorStride = H * outputStride / 128;                                                           \
	for (i = 0; i < L; i++)                                                             \
	{                                                                                   \
	  for (j = 0; j < BLKSIZE_DIV_VECTSIZE(K, H, 128); j++)                               \
	  {                                                                                   \
		m128iInputSample = _mm_loadu_si128(pm128iInputSample + j);                      \
		_mm_storeu_si128(pm128iOutputSample + j, m128iInputSample);                     \
	  }                                                                                   \
	  if (BLKSIZE_MOD_VECTSIZE(K, H, 128) == 64)                                          \
	  {                                                                                   \
		m128iInputSample = _mm_loadl_epi64(pm128iInputSample + j);                  	\
		_mm_storel_epi64(pm128iOutputSample + j, m128iInputSample);                 	\
	  }                                                                               	\
	  pm128iInputSample += inputVectorStride;                                                 \
	  pm128iOutputSample += outputVectorStride;                                               \
	}                                                                               	\
}

#if HAVE_SSE2
// Declare more functions if needed
COPY_2D(8, 8,  8,  8)
COPY_2D(8, 8, 16, 16)
COPY_2D(8, 8, 32, 32)
COPY_2D(8, 8, 64, 64)
#endif

/* Copy with variable number of elements to be copied */
#define MEMCPY(H)                                                                          \
void memcpy_ ## H ## _orcc(                                                                \
	u ## H * outputSample,                                                                 \
	u ## H * inputSample,                                                                  \
	u32 offsetOut,                                                                         \
	u32 offsetIn,                                                                          \
	int count)                                                                             \
{                                                                                          \
	memcpy(&outputSample[offsetOut], &inputSample[offsetIn], count * sizeof(u ## H));      \
}

MEMCPY(8)
MEMCPY(16)
MEMCPY(32)

/* memset */
#define MEMSET(H)                                                                          \
void memset_ ## H ## _orcc(                                                                \
	u ## H * Sample,                                                                       \
	u8 value,                                                                              \
	int size)                                                                              \
{                                                                                          \
	memset(&Sample[0], value, size * sizeof(u ## H));                                      \
}

MEMSET(8)
MEMSET(16)
MEMSET(32)


/* add H 8-bits elements to 16-bits elements and clip */
#define ADD_8_16_CLIP(H)                                                                                 \
void add_8_16_clip_ ## H ## _orcc(                                                                       \
	u8 * predSample,                                                                                     \
	i16 * resSample,                                                                                     \
	u8 * Sample,                                                                                         \
	u16 offsetPred,                                                                                      \
    u16 offsetRes,                                                                                       \
    u16 offsetOut)                                                                                       \
{                                                                                                        \
	int i = 0;                                                                                           \
	                                                                                                     \
	__m128i * pm128iPredSamp = (__m128i *) &predSample[offsetPred + 0];                                  \
	__m128i m128itmp_predSamp;                                                                           \
	__m128i * pm128iResSample = (__m128i *) &resSample[offsetRes];                                       \
	__m128i m128itmp_ResidualSample;                                                                     \
	__m128i m128itmp_add_i16_0, m128itmp_add_i16_1;                                                      \
	__m128i * pm128iSample = (__m128i *) &Sample[offsetOut];                                             \
	__m128i m128iZero = _mm_set1_epi16(0);                                                               \
	                                                                                                     \
	for (i = 0; i < BLKSIZE_DIV_VECTSIZE(H, 8, 128); i++)                                                \
	{                                                                                                    \
		m128itmp_predSamp =                                                                              \
		_mm_unpacklo_epi8(                                                                               \
		_mm_loadu_si128(pm128iPredSamp + i),                                                             \
		m128iZero);                                                                                      \
		m128itmp_ResidualSample = _mm_loadu_si128(pm128iResSample + (i << 1));                           \
		m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_predSamp, m128itmp_ResidualSample);                  \
		                                                                                                 \
		m128itmp_predSamp =                                                                              \
		_mm_unpackhi_epi8(                                                                               \
		_mm_loadu_si128(pm128iPredSamp + i),                                                             \
		m128iZero);                                                                                      \
		m128itmp_ResidualSample = _mm_loadu_si128(pm128iResSample + (i << 1) + 1);                       \
		m128itmp_add_i16_1 = _mm_add_epi16(m128itmp_predSamp, m128itmp_ResidualSample);                  \
		                                                                                                 \
		_mm_storeu_si128(pm128iSample + i, _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1));    \
	}                                                                                                    \
	if (BLKSIZE_MOD_VECTSIZE(H, 8, 128) == 64)                                                            \
	{                                                                                                    \
		m128itmp_predSamp =                                                                              \
		_mm_unpacklo_epi8(                                                                               \
		_mm_loadl_epi64(pm128iPredSamp + i),                                                             \
		m128iZero);                                                                                      \
		m128itmp_ResidualSample = _mm_loadu_si128(pm128iResSample + (i << 1));                           \
		m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_predSamp, m128itmp_ResidualSample);                  \
		                                                                                                 \
		m128itmp_add_i16_1 = _mm_set1_epi16(0);                                                          \
		                                                                                                 \
		_mm_storel_epi64(pm128iSample + i, _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1));    \
	}                                                                                                    \
}

#if HAVE_SSE2
// Declare more functions if needed
ADD_8_16_CLIP(   8)
ADD_8_16_CLIP(  16)
ADD_8_16_CLIP(  32)
ADD_8_16_CLIP(  64)
ADD_8_16_CLIP( 256)
ADD_8_16_CLIP(1024)
#endif

i32 clip_i32(i32 Value, i32 minVal, i32 maxVal) {
	i32 tmp_if;

	if (Value > maxVal) {
		tmp_if = maxVal;
	}
	else {
		if (Value < minVal) {
			tmp_if = minVal;
		}
		else {
			tmp_if = Value;
		}
	}
	return tmp_if;
}

/* DBF - GenerateBS */

#if HAVE_SSE4

/* 32 bit motion vectors */

void boundaryStrength_mv32_orcc(
	u8 bS[1],
	i32 * mvP0,
	i32 * mvQ0,
	i16 * refPocP0,
	i16 * refPocQ0)
{
	__m128i x0, x1, x2;

	if (refPocQ0[0] == refPocP0[0] &&
		refPocQ0[0] == refPocQ0[1] &&
		refPocP0[0] == refPocP0[1]) {
		x0 = _mm_loadu_si128((__m128i *) mvP0);
		x1 = _mm_loadu_si128((__m128i *) mvQ0);
		x2 = _mm_shuffle_epi32(x0, 0x4E);
		x0 = _mm_sub_epi32(x0, x1);
		x2 = _mm_sub_epi32(x2, x1);
		x1 = _mm_set1_epi32(4);
		x0 = _mm_abs_epi32(x0);
		x2 = _mm_abs_epi32(x2);
		x0 = _mm_cmplt_epi32(x0, x1);
		x2 = _mm_cmplt_epi32(x2, x1);
		bS[0] = !(_mm_test_all_ones(x0) || _mm_test_all_ones(x2));
	}
	else if (refPocP0[0] == refPocQ0[0] &&
		refPocP0[1] == refPocQ0[1]) {
		x0 = _mm_loadu_si128((__m128i *) mvP0);
		x1 = _mm_loadu_si128((__m128i *) mvQ0);
		x0 = _mm_sub_epi32(x0, x1);
		x1 = _mm_set1_epi32(4);
		x0 = _mm_abs_epi32(x0);
		x0 = _mm_cmplt_epi32(x0, x1);
		bS[0] = !(_mm_test_all_ones(x0));
	}
	else if (refPocP0[1] == refPocQ0[0] &&
		refPocP0[0] == refPocQ0[1]) {
		x0 = _mm_loadu_si128((__m128i *) mvP0);
		x1 = _mm_loadu_si128((__m128i *) mvQ0);
		x2 = _mm_shuffle_epi32(x0, 0x4E);
		x2 = _mm_sub_epi32(x2, x1);
		x1 = _mm_set1_epi32(4);
		x2 = _mm_abs_epi32(x2);
		x2 = _mm_cmplt_epi32(x2, x1);
		bS[0] = !(_mm_test_all_ones(x2));
	}
	else {
		bS[0] = 1;
	}
}

void boundaryStrength0_mv32_orcc(
	u8 bS[1],
	i32 * mvP0,
	i32 * mvQ0)
{
	__m128i x0, x1, x2;
	x0 = _mm_loadu_si128((__m128i *) mvP0);
	x1 = _mm_loadu_si128((__m128i *) mvQ0);
	x2 = _mm_shuffle_epi32(x0, 0x4E);
	x0 = _mm_sub_epi32(x0, x1);
	x2 = _mm_sub_epi32(x2, x1);
	x1 = _mm_set1_epi32(4);
	x0 = _mm_abs_epi32(x0);
	x2 = _mm_abs_epi32(x2);
	x0 = _mm_cmplt_epi32(x0, x1);
	x2 = _mm_cmplt_epi32(x2, x1);
	bS[0] = !(_mm_test_all_ones(x0) || _mm_test_all_ones(x2));
}

void boundaryStrength1_mv32_orcc(
	u8 bS[1],
	i32 * mvP0,
	i32 * mvQ0)
{
	__m128i x0, x1;
	x0 = _mm_loadu_si128((__m128i *) mvP0);
	x1 = _mm_loadu_si128((__m128i *) mvQ0);
	x0 = _mm_sub_epi32(x0, x1);
	x1 = _mm_set1_epi32(4);
	x0 = _mm_abs_epi32(x0);
	x0 = _mm_cmplt_epi32(x0, x1);
	bS[0] = !(_mm_test_all_ones(x0));
}

void boundaryStrength2_mv32_orcc(
	u8 bS[1],
	i32 * mvP0,
	i32 * mvQ0)
{
	__m128i x0, x1, x2;
	x0 = _mm_loadu_si128((__m128i *) mvP0);
	x1 = _mm_loadu_si128((__m128i *) mvQ0);
	x2 = _mm_shuffle_epi32(x0, 0x4E);
	x2 = _mm_sub_epi32(x2, x1);
	x1 = _mm_set1_epi32(4);
	x2 = _mm_abs_epi32(x2);
	x2 = _mm_cmplt_epi32(x2, x1);
	bS[0] = !(_mm_test_all_ones(x2));
}

void boundaryStrength3_mv32_orcc(
	u8 * bS,
	u8 predModeP0,
	u8 predModeQ0,
	i32 * mvP0,
	i32 * mvQ0)
{
	__m128i x0, x1;
	x0 = _mm_loadl_epi64((__m128i *) &mvP0[predModeP0 << 1]);
	x1 = _mm_loadl_epi64((__m128i *) &mvQ0[predModeQ0 << 1]);
	x0 = _mm_sub_epi32(x0, x1);
	x1 = _mm_set1_epi32(4);
	x0 = _mm_abs_epi32(x0);
	x0 = _mm_cmplt_epi32(x0, x1);
	bS[0] = !(_mm_test_all_ones(x0));
}

#endif // HAVE_SSE4

/* DECODING PICTURE BUFFER */

#define COPY_CU_DPB(H)                                                                                 \
void copy_cu_dpb_x ## H ## _orcc(                                                                      \
	u8 * samp,                                                                                         \
	u8 * pictureBuffer,                                                                                \
	i32 xPixIdx,                                                                                       \
	i32 yPixIdx,                                                                                       \
	i8  lastIdx,                                                                                       \
	u16 width,                                                                                         \
	u16 height)                                                                                        \
{                                                                                                      \
	int y;                                                                                             \
	u32 offsetOut = lastIdx * height * width + yPixIdx * width;                                        \
                                                                                                       \
	for (y = 0; y < H; y++)                                                                            \
	{                                                                                                  \
		copy_8_8_ ## H ## _orcc(                                                                       \
			&pictureBuffer[offsetOut + y * width],                                                     \
			samp + y * H,                                                                              \
			xPixIdx,                                                                                   \
            0);                                                                                        \
	}                                                                                                  \
}

COPY_CU_DPB( 8)
COPY_CU_DPB(16)
COPY_CU_DPB(32)
COPY_CU_DPB(64)


#define GETMVINFO_DPB()                                                                       \
void getmvinfo_dpb_orcc(                                                                      \
	u8 * pictureBuffer,                                                                       \
	u8 * RefCu,                                                                               \
	u8 idx,                                                                                   \
	u8 sideMax,                                                                               \
	i32 xOffset,                                                                              \
	i32 yOffset,                                                                              \
    u16 width,                                                                                \
    u16 height)                                                                               \
{                                                                                             \
	int y;                                                                                    \
	u32 offsetOut = idx * height * width + yOffset * width;                                   \
	for (y = 0; y < sideMax; y++)                                                             \
	{                                                                                         \
		memcpy_8_orcc(                                                                        \
		  &RefCu[y * (sideMax)],                                                              \
		  &pictureBuffer[offsetOut + y * width],                                              \
		  0,                                                                                  \
		  xOffset,                                                                            \
		  sideMax);                                                                           \
	}                                                                                         \
}

GETMVINFO_DPB()


#if HAVE_SSE2
void fillBorder_luma_orcc(
u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT + 2 * BORDER_SIZE][PICT_WIDTH + 2 * BORDER_SIZE],
i8 lastIdx,
int xSize,
int ySize,
u16 border_size)
{
	int y, x;
	u8 tmp_pictureBuffer;
	u8 tmp_pictureBuffer0;

	__m128i * __restrict pm128iPictureBuffer;
	__m128i * __restrict pm128iPictureBuffer0;
	__m128i * __restrict pm128iPictureBuffer1;
	__m128i * __restrict pm128iPictureBuffer2;
	__m128i m128iWord, m128iWord0;

	u8 * pucPictureBuffer1 = &pictureBuffer[lastIdx][border_size][border_size];
	u8 * pucPictureBuffer2 = &pictureBuffer[lastIdx][ySize + border_size - 1][border_size];
	u8 * pucPictureBuffer = &pictureBuffer[lastIdx][0][border_size];
	u8 * pucPictureBuffer0 = &pictureBuffer[lastIdx][0 + ySize + border_size][border_size];

	int iLoopCount = (xSize >> 4) - 1;

	y = 0;
	while (y <= border_size - 1) {
		pm128iPictureBuffer1 = (__m128i *) pucPictureBuffer1;
		pm128iPictureBuffer2 = (__m128i *) pucPictureBuffer2;
		pm128iPictureBuffer = (__m128i *) pucPictureBuffer;
		pm128iPictureBuffer0 = (__m128i *) pucPictureBuffer0;
		x = 0;
		while (x <= iLoopCount) {
			m128iWord = _mm_loadu_si128(pm128iPictureBuffer1);
			m128iWord0 = _mm_loadu_si128(pm128iPictureBuffer2);
			_mm_storeu_si128(pm128iPictureBuffer, m128iWord);
			_mm_storeu_si128(pm128iPictureBuffer0, m128iWord0);
			pm128iPictureBuffer1++;
			pm128iPictureBuffer2++;
			pm128iPictureBuffer++;
			pm128iPictureBuffer0++;
			x = x + 1;
		}
		pucPictureBuffer += (PICT_WIDTH + 2 * BORDER_SIZE);
		pucPictureBuffer0 += (PICT_WIDTH + 2 * BORDER_SIZE);
		y = y + 1;
	}

	pucPictureBuffer1 = &pictureBuffer[lastIdx][0][border_size];
	pucPictureBuffer2 = &pictureBuffer[lastIdx][0][xSize + border_size - 1];
	pucPictureBuffer = &pictureBuffer[lastIdx][0][0];
	pucPictureBuffer0 = &pictureBuffer[lastIdx][0][0 + xSize + border_size];

	iLoopCount = (border_size >> 4) - 1;
	y = 0;
	while (y <= ySize + 2 * border_size - 1) {
		tmp_pictureBuffer = pucPictureBuffer1[0];
		tmp_pictureBuffer0 = pucPictureBuffer2[0];
		pm128iPictureBuffer = (__m128i *) &pucPictureBuffer[0];
		pm128iPictureBuffer0 = (__m128i *) &pucPictureBuffer0[0];
		m128iWord = _mm_set1_epi8(tmp_pictureBuffer);
		m128iWord0 = _mm_set1_epi8(tmp_pictureBuffer0);
		x = 0;
		while (x <= iLoopCount) {
			_mm_storeu_si128(pm128iPictureBuffer, m128iWord);
			_mm_storeu_si128(pm128iPictureBuffer0, m128iWord0);
			pm128iPictureBuffer++;
			pm128iPictureBuffer0++;
			x = x + 1;
		}
		pucPictureBuffer1 += (PICT_WIDTH + 2 * BORDER_SIZE);
		pucPictureBuffer2 += (PICT_WIDTH + 2 * BORDER_SIZE);
		pucPictureBuffer += (PICT_WIDTH + 2 * BORDER_SIZE);
		pucPictureBuffer0 += (PICT_WIDTH + 2 * BORDER_SIZE);
		y = y + 1;
	}
}


void fillBorder_chroma_orcc(
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT / 2 + 2 * BORDER_SIZE][PICT_WIDTH / 2 + 2 * BORDER_SIZE],
	i8 lastIdx,
	int xSize,
	int ySize,
	u16 border_size)
{
	int y, x;
	u8 tmp_pictureBuffer;
	u8 tmp_pictureBuffer0;

	__m128i * __restrict pm128iPictureBuffer;
	__m128i * __restrict pm128iPictureBuffer0;
	__m128i * __restrict pm128iPictureBuffer1;
	__m128i * __restrict pm128iPictureBuffer2;
	__m128i m128iWord, m128iWord0;

	u8 * pucPictureBuffer1 = &pictureBuffer[lastIdx][border_size][border_size];
	u8 * pucPictureBuffer2 = &pictureBuffer[lastIdx][ySize + border_size - 1][border_size];
	u8 * pucPictureBuffer = &pictureBuffer[lastIdx][0][border_size];
	u8 * pucPictureBuffer0 = &pictureBuffer[lastIdx][0 + ySize + border_size][border_size];

	int iLoopCount = (xSize >> 4) - 1;

	y = 0;
	while (y <= border_size - 1) {
		pm128iPictureBuffer1 = (__m128i *) pucPictureBuffer1;
		pm128iPictureBuffer2 = (__m128i *) pucPictureBuffer2;
		pm128iPictureBuffer = (__m128i *) pucPictureBuffer;
		pm128iPictureBuffer0 = (__m128i *) pucPictureBuffer0;
		x = 0;
		while (x <= iLoopCount) {
			m128iWord = _mm_loadu_si128(pm128iPictureBuffer1);
			m128iWord0 = _mm_loadu_si128(pm128iPictureBuffer2);
			_mm_storeu_si128(pm128iPictureBuffer, m128iWord);
			_mm_storeu_si128(pm128iPictureBuffer0, m128iWord0);
			pm128iPictureBuffer1++;
			pm128iPictureBuffer2++;
			pm128iPictureBuffer++;
			pm128iPictureBuffer0++;
			x = x + 1;
		}
		pucPictureBuffer += (PICT_WIDTH / 2 + 2 * BORDER_SIZE);
		pucPictureBuffer0 += (PICT_WIDTH / 2 + 2 * BORDER_SIZE);
		y = y + 1;
	}

	pucPictureBuffer1 = &pictureBuffer[lastIdx][0][border_size];
	pucPictureBuffer2 = &pictureBuffer[lastIdx][0][xSize + border_size - 1];
	pucPictureBuffer = &pictureBuffer[lastIdx][0][0];
	pucPictureBuffer0 = &pictureBuffer[lastIdx][0][0 + xSize + border_size];

	iLoopCount = (border_size >> 4) - 1;
	y = 0;
	while (y <= ySize + 2 * border_size - 1) {
		tmp_pictureBuffer = pucPictureBuffer1[0];
		tmp_pictureBuffer0 = pucPictureBuffer2[0];
		pm128iPictureBuffer = (__m128i *) &pucPictureBuffer[0];
		pm128iPictureBuffer0 = (__m128i *) &pucPictureBuffer0[0];
		m128iWord = _mm_set1_epi8(tmp_pictureBuffer);
		m128iWord0 = _mm_set1_epi8(tmp_pictureBuffer0);
		x = 0;
		while (x <= iLoopCount)
		{
			_mm_storeu_si128(pm128iPictureBuffer, m128iWord);
			_mm_storeu_si128(pm128iPictureBuffer0, m128iWord0);
			pm128iPictureBuffer++;
			pm128iPictureBuffer0++;
			x = x + 1;
		}
		pucPictureBuffer1 += (PICT_WIDTH / 2 + 2 * BORDER_SIZE);
		pucPictureBuffer2 += (PICT_WIDTH / 2 + 2 * BORDER_SIZE);
		pucPictureBuffer += (PICT_WIDTH / 2 + 2 * BORDER_SIZE);
		pucPictureBuffer0 += (PICT_WIDTH / 2 + 2 * BORDER_SIZE);
		y = y + 1;
	}
}
#endif

/* DISPLAY */

#define INT_MAXIM(a, b)  ((a) > (b) ? (a) : (b))
#define INT_MINIM(a, b)  ((a) < (b) ? (a) : (b))

#define MAX_WIDTH 4096
#define MAX_HEIGHT 2048

#define DISPLAYYUV_CROP(H)                                                                  \
void displayYUV_crop_ ## H ## _orcc(                                                    \
	u8 * Bytes,                                                                             \
	u8 * pictureBuffer,                                                                     \
	u16 xMin,                                                                               \
	u16 xMax,                                                                               \
	u16 yMin,                                                                               \
	u16 yMax,                                                                               \
	u16 xIdx,                                                                               \
	u16 yIdx,                                                                               \
	u16 cropPicWth)                                                                         \
{                                                                                           \
	u16 xIdxMin = INT_MAXIM(xIdx, xMin);                                                    \
	u16 xIdxMax = INT_MINIM(xIdx + H - 1, xMax);                                            \
	u16 yIdxMin = INT_MAXIM(yIdx, yMin);                                                    \
	u16 yIdxMax = INT_MINIM(yIdx + H - 1, yMax);                                            \
	int x, y;                                                                               \
	int iPictureBufferOffset = (yIdxMin - yMin) * cropPicWth + xIdxMin - xMin;              \
	int iPictureBuffer1Offset = (yIdxMin - yIdx) * H + xIdxMin - xIdx;                      \
	                                                                                        \
	__m128i * __restrict pm128iPictureBuffer = NULL;                                        \
	__m128i * __restrict pm128iPictureBuffer1 = NULL;                                       \
	__m128i m128iWord;                                                                      \
	                                                                                        \
	int iLoopCount = (xIdxMax - xIdxMin + 1) >> 4;                                          \
	                                                                                        \
	for (y = yIdxMin; y < yIdxMax + 1; y++)                                                 \
	{                                                                                       \
		pm128iPictureBuffer = (__m128i *) &pictureBuffer[iPictureBufferOffset];             \
		pm128iPictureBuffer1 = (__m128i *) &Bytes[iPictureBuffer1Offset];                   \
		for (x = 0; x < iLoopCount; x++)                                                    \
		{                                                                                   \
			m128iWord = _mm_loadu_si128(pm128iPictureBuffer1);                              \
			_mm_storeu_si128(pm128iPictureBuffer, m128iWord);                               \
			pm128iPictureBuffer++;                                                          \
			pm128iPictureBuffer1++;                                                         \
		}                                                                                   \
		for (x = (iLoopCount << 4); x < xIdxMax - xIdxMin + 1; x++)                         \
		{                                                                                   \
			pictureBuffer[iPictureBufferOffset + x] = Bytes[iPictureBuffer1Offset + x];     \
		}                                                                                   \
		iPictureBufferOffset += cropPicWth;                                                 \
		iPictureBuffer1Offset += H;                                                         \
	}                                                                                       \
}                                                                                           \

#if HAVE_SSE2
// Declare more functions if needed
DISPLAYYUV_CROP(8)
DISPLAYYUV_CROP(16)
DISPLAYYUV_CROP(32)
DISPLAYYUV_CROP(64)
#endif

/* Gather non-contiguous elements from memory */

#if HAVE_SSE2
void gather32_4x4_orcc(
	u8 * outputSample,
	u8 * inputSample,
	u16 offsetOut,
	u16 offsetIn,
	u8 strideOut,
	u8 strideIn)
{
	__m128i * __restrict pm128iOutputSample = (__m128i *) &outputSample[offsetOut];
#if !HAVE_AVX2
	u8 * pucInputSample = &inputSample[offsetIn];
	__m128i * __restrict pm128iInputSample;
	__m128i m128iWord0, m128iWord1, m128iWord2, m128iWord3;
	int i0, i1, i2, i3;

	m128iWord0 = _mm_loadu_si128((__m128i *) &pucInputSample[0 * strideIn]);
	m128iWord1 = _mm_loadu_si128((__m128i *) &pucInputSample[1 * strideIn]);
	m128iWord2 = _mm_loadu_si128((__m128i *) &pucInputSample[2 * strideIn]);
	m128iWord3 = _mm_loadu_si128((__m128i *) &pucInputSample[3 * strideIn]);

	i0 = _mm_cvtsi128_si32(m128iWord0);
	i1 = _mm_cvtsi128_si32(m128iWord1);
	i2 = _mm_cvtsi128_si32(m128iWord2);
	i3 = _mm_cvtsi128_si32(m128iWord3);

	m128iWord0 = _mm_setr_epi32(i0, i1, i2, i3);
#else // !HAVE_AVX2
	int scale = strideIn >> 2;
	__m128i m128iWord0;
	__m128i vindex = _mm_setr_epi32(0, 1 * scale, 2 * scale, 3 * scale);
	m128iWord0 = _mm_i32gather_epi32((int const *)&inputSample[offsetIn], vindex, 1);
#endif // !defined HAVE_AVX2

	_mm_storeu_si128(pm128iOutputSample, m128iWord0);
}
#endif


int sse_init_context()
{
#if HAVE_SSE4
	weighted_pred_mono[0] = ff_hevc_weighted_pred_mono2_8_sse;
	weighted_pred_mono[1] = ff_hevc_weighted_pred_mono4_8_sse;
	weighted_pred_mono[2] = ff_hevc_weighted_pred_mono8_8_sse;
	weighted_pred_mono[3] = ff_hevc_weighted_pred_mono16_8_sse;

	pred_angular[0] = pred_angular_4_8_sse;
	pred_angular[1] = pred_angular_8_8_sse;
	pred_angular[2] = pred_angular_16_8_sse;
	pred_angular[3] = pred_angular_32_8_sse;

	/* Luma */
	put_unweighted_pred_zscan[0][0] = ff_hevc_put_unweighted_pred_zscan2_2_8_sse;
	put_unweighted_pred_zscan[0][1] = ff_hevc_put_unweighted_pred_zscan4_4_8_sse;
	put_unweighted_pred_zscan[0][2] = ff_hevc_put_unweighted_pred_zscan8_4_8_sse;
	put_unweighted_pred_zscan[0][3] = ff_hevc_put_unweighted_pred_zscan16_4_8_sse;

	put_weighted_pred_avg_zscan[0][0] = ff_hevc_put_weighted_pred_avg_zscan2_2_8_sse;
	put_weighted_pred_avg_zscan[0][1] = ff_hevc_put_weighted_pred_avg_zscan4_4_8_sse;
	put_weighted_pred_avg_zscan[0][2] = ff_hevc_put_weighted_pred_avg_zscan8_4_8_sse;
	put_weighted_pred_avg_zscan[0][3] = ff_hevc_put_weighted_pred_avg_zscan16_4_8_sse;

	weighted_pred_zscan[0][0] = ff_hevc_weighted_pred_zscan2_2_8_sse;
	weighted_pred_zscan[0][1] = ff_hevc_weighted_pred_zscan4_4_8_sse;
	weighted_pred_zscan[0][2] = ff_hevc_weighted_pred_zscan8_4_8_sse;
	weighted_pred_zscan[0][3] = ff_hevc_weighted_pred_zscan16_4_8_sse;

	weighted_pred_avg_zscan[0][0] = ff_hevc_weighted_pred_avg_zscan2_2_8_sse;
	weighted_pred_avg_zscan[0][1] = ff_hevc_weighted_pred_avg_zscan4_4_8_sse;
	weighted_pred_avg_zscan[0][2] = ff_hevc_weighted_pred_avg_zscan8_4_8_sse;
	weighted_pred_avg_zscan[0][3] = ff_hevc_weighted_pred_avg_zscan16_4_8_sse;

	weighted_pred_mono_zscan[0][0] = ff_hevc_weighted_pred_mono_zscan2_2_8_sse;
	weighted_pred_mono_zscan[0][1] = ff_hevc_weighted_pred_mono_zscan4_4_8_sse;
	weighted_pred_mono_zscan[0][2] = ff_hevc_weighted_pred_mono_zscan8_4_8_sse;
	weighted_pred_mono_zscan[0][3] = ff_hevc_weighted_pred_mono_zscan16_4_8_sse;

	/* Chroma */
	put_unweighted_pred_zscan[1][0] = ff_hevc_put_unweighted_pred_zscan2_2_8_sse;
	put_unweighted_pred_zscan[1][1] = ff_hevc_put_unweighted_pred_zscan4_2_8_sse;
	put_unweighted_pred_zscan[1][2] = ff_hevc_put_unweighted_pred_zscan8_2_8_sse;
	put_unweighted_pred_zscan[1][3] = ff_hevc_put_unweighted_pred_zscan16_2_8_sse;

	put_weighted_pred_avg_zscan[1][0] = ff_hevc_put_weighted_pred_avg_zscan2_2_8_sse;
	put_weighted_pred_avg_zscan[1][1] = ff_hevc_put_weighted_pred_avg_zscan4_2_8_sse;
	put_weighted_pred_avg_zscan[1][2] = ff_hevc_put_weighted_pred_avg_zscan8_2_8_sse;
	put_weighted_pred_avg_zscan[1][3] = ff_hevc_put_weighted_pred_avg_zscan16_2_8_sse;

	weighted_pred_zscan[1][0] = ff_hevc_weighted_pred_zscan2_2_8_sse;
	weighted_pred_zscan[1][1] = ff_hevc_weighted_pred_zscan4_2_8_sse;
	weighted_pred_zscan[1][2] = ff_hevc_weighted_pred_zscan8_2_8_sse;
	weighted_pred_zscan[1][3] = ff_hevc_weighted_pred_zscan16_2_8_sse;

	weighted_pred_avg_zscan[1][0] = ff_hevc_weighted_pred_avg_zscan2_2_8_sse;
	weighted_pred_avg_zscan[1][1] = ff_hevc_weighted_pred_avg_zscan4_2_8_sse;
	weighted_pred_avg_zscan[1][2] = ff_hevc_weighted_pred_avg_zscan8_2_8_sse;
	weighted_pred_avg_zscan[1][3] = ff_hevc_weighted_pred_avg_zscan16_2_8_sse;

	weighted_pred_mono_zscan[1][0] = ff_hevc_weighted_pred_mono_zscan2_2_8_sse;
	weighted_pred_mono_zscan[1][1] = ff_hevc_weighted_pred_mono_zscan4_2_8_sse;
	weighted_pred_mono_zscan[1][2] = ff_hevc_weighted_pred_mono_zscan8_2_8_sse;
	weighted_pred_mono_zscan[1][3] = ff_hevc_weighted_pred_mono_zscan16_2_8_sse;

	/* xIT */
	ff_hevc_transform0_4x4_8_zscan_sse4_orcc[0] = ff_hevc_transform0_4x4_2_8_zscan_sse4;
	ff_hevc_transform0_4x4_8_zscan_sse4_orcc[1] = ff_hevc_transform0_4x4_4_8_zscan_sse4;

	ff_hevc_transform0_8x8_8_zscan_sse4_orcc[0] = ff_hevc_transform0_8x8_2_8_zscan_sse4;
	ff_hevc_transform0_8x8_8_zscan_sse4_orcc[1] = ff_hevc_transform0_8x8_4_8_zscan_sse4;

	ff_hevc_transform0_16x16_8_zscan_sse4_orcc[0] = ff_hevc_transform0_16x16_2_8_zscan_sse4;
	ff_hevc_transform0_16x16_8_zscan_sse4_orcc[1] = ff_hevc_transform0_16x16_4_8_zscan_sse4;

	ff_hevc_transform0_32x32_8_zscan_sse4_orcc[0] = ff_hevc_transform0_32x32_2_8_zscan_sse4;
	ff_hevc_transform0_32x32_8_zscan_sse4_orcc[1] = ff_hevc_transform0_32x32_4_8_zscan_sse4;
#endif // HAVE_SSE4

	return 0;
}

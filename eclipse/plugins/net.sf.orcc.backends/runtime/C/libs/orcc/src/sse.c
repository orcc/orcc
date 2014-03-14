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

#include "sse.h"

/*****************************************************************************************************************/


/* SELECT_CU */

#define SCU_SIZE_DIV16(H) (H >> 4)
#define SCU_SIZE_MOD16(H) (H & 0x0F)

// copy 8-bits elements into a 8-bits array, for H elements
#define COPY_8_8(H, J, K)                                                                                                              \
void copy_8_8_ ## H ## _ ## J ## x ## K ## _orcc(                                                                                      \
  u8 outputSample[J * K],                                                                                                              \
  u8 inputSample[H],                                                                                                                   \
  u32 idxBlkStride)                                                                                                                    \
{                                                                                                                                      \
  int i = 0;                                                                                                                           \
  __m128i * pm128iInputSample = (__m128i *) &inputSample[0];                                                                           \
  __m128i * pm128iOutputSample = (__m128i *) &outputSample[idxBlkStride + 0];                                                          \
  __m128i m128iInputSample;                                                                                                            \
                                                                                                                                       \
  for (i = 0; i < SCU_SIZE_DIV16(H); i++)                                                                                              \
  {                                                                                                                                    \
    m128iInputSample = _mm_loadu_si128(pm128iInputSample + i);                                                                         \
    _mm_storeu_si128(pm128iOutputSample + i, m128iInputSample);                                                                        \
  }                                                                                                                                    \
                                                                                                                                       \
  if (SCU_SIZE_MOD16(H) == 8)                                                                                                          \
  {                                                                                                                                    \
    m128iInputSample = _mm_loadl_epi64(pm128iInputSample + i);                                                                         \
    _mm_storel_epi64(pm128iOutputSample + i, m128iInputSample);                                                                        \
  }                                                                                                                                    \
}

// Declare more functions if needed
COPY_8_8(16, 64,   64)
COPY_8_8(16, 32,   32)
COPY_8_8(16,  1, 4352)
COPY_8_8( 8,  1, 2304)

// Variable number of elements to be copied
#define COPY_8_8_VAR()                                                                                                                 \
void copy_8_8_var_orcc(                                                                                                                \
  u8 * outputSample,                                                                                                                   \
  u8 * inputSample,                                                                                                                    \
  u32 idxBlkStride,                                                                                                                    \
  u8 size)                                                                                                                             \
{                                                                                                                                      \
  int i = 0;                                                                                                                           \
  __m128i * pm128iInputSample = (__m128i *) &inputSample[idxBlkStride + 0];                                                            \
  __m128i * pm128iOutputSample = (__m128i *) &outputSample[0];                                                                         \
  __m128i m128iInputSample;                                                                                                            \
  int size1 = SCU_SIZE_MOD16(size);                                                                                                    \
  int x;                                                                                                                               \
                                                                                                                                       \
  for (i = 0; i < SCU_SIZE_DIV16(size); i++)                                                                                           \
  {                                                                                                                                    \
    m128iInputSample = _mm_loadu_si128(pm128iInputSample + i);                                                                         \
    _mm_storeu_si128(pm128iOutputSample + i, m128iInputSample);                                                                        \
  }                                                                                                                                    \
                                                                                                                                       \
  if (size1 > 7)                                                                                                                       \
  {                                                                                                                                    \
    m128iInputSample = _mm_loadl_epi64(pm128iInputSample + i);                                                                         \
    _mm_storel_epi64(pm128iOutputSample + i, m128iInputSample);                                                                        \
    size1 -= 8;                                                                                                                        \
  }                                                                                                                                    \
                                                                                                                                       \
  memcpy(&outputSample[size - size1], &inputSample[idxBlkStride + size - size1], size1 * sizeof(u8));                                  \
}

COPY_8_8_VAR()


// add 8-bits elements to 16-bits elements and clip, for H elements. First array (pred) is K * J.
#define ADD_8_16_CLIP(H, K, J)                                                                                                         \
void add_8_16_clip_ ## H ## _ ## K ## x ## J ## _orcc(                                                                                 \
  u8 predSample[K * J],                                                                                                                \
  i16 resSample[H],                                                                                                                    \
  u8 Sample[H],                                                                                                                        \
  u16 idxBlkStride)                                                                                                                    \
{                                                                                                                                      \
  int i = 0;                                                                                                                           \
                                                                                                                                       \
  __m128i * pm128iPredSamp = (__m128i *) &predSample[idxBlkStride + 0];                                                                \
  __m128i m128itmp_predSamp;                                                                                                           \
  __m128i * pm128iResSample = (__m128i *) &resSample[0];                                                                               \
  __m128i m128itmp_ResidualSample;                                                                                                     \
  __m128i m128itmp_add_i16_0, m128itmp_add_i16_1;                                                                                      \
  __m128i * pm128iSample = (__m128i *) &Sample[0];                                                                                     \
  __m128i m128iZero = _mm_set1_epi16(0);                                                                                               \
                                                                                                                                       \
  for (i = 0; i < SCU_SIZE_DIV16(H); i++)                                                                                              \
  {                                                                                                                                    \
    m128itmp_predSamp =                                                                                                                \
      _mm_unpacklo_epi8(                                                                                                               \
      _mm_loadu_si128(pm128iPredSamp + i),                                                                                             \
      m128iZero);                                                                                                                      \
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iResSample + (i << 1));                                                             \
    m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_predSamp, m128itmp_ResidualSample);                                                    \
                                                                                                                                       \
    m128itmp_predSamp =                                                                                                                \
      _mm_unpackhi_epi8(                                                                                                               \
      _mm_loadu_si128(pm128iPredSamp + i),                                                                                             \
      m128iZero);                                                                                                                      \
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iResSample + (i << 1) + 1);                                                         \
    m128itmp_add_i16_1 = _mm_add_epi16(m128itmp_predSamp, m128itmp_ResidualSample);                                                    \
                                                                                                                                       \
    _mm_storeu_si128(pm128iSample + i, _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1));                                      \
  }                                                                                                                                    \
                                                                                                                                       \
  if (SCU_SIZE_MOD16(H) == 8)                                                                                                          \
  {                                                                                                                                    \
    m128itmp_predSamp =                                                                                                                \
      _mm_unpacklo_epi8(                                                                                                               \
      _mm_loadl_epi64(pm128iPredSamp + i),                                                                                             \
      m128iZero);                                                                                                                      \
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iResSample + (i << 1));                                                             \
    m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_predSamp, m128itmp_ResidualSample);                                                    \
                                                                                                                                       \
    m128itmp_add_i16_1 = _mm_set1_epi16(0);                                                                                            \
                                                                                                                                       \
    _mm_storel_epi64(pm128iSample + i, _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1));                                      \
  }                                                                                                                                    \
}

// Declare more functions if needed
ADD_8_16_CLIP(  16,  1, 16)
ADD_8_16_CLIP(   8, 64, 64)

ADD_8_16_CLIP(  16, 64, 64)
ADD_8_16_CLIP(  32, 64, 64)
ADD_8_16_CLIP(  64, 64, 64)
ADD_8_16_CLIP( 256, 64, 64)

ADD_8_16_CLIP(1024, 64, 64)

ADD_8_16_CLIP(  16, 32, 32)
ADD_8_16_CLIP(  64, 32, 32)
ADD_8_16_CLIP( 256, 32, 32)

static i32 clip_i32(i32 Value, i32 minVal, i32 maxVal) {
	i32 tmp_if;

	if (Value > maxVal) {
		tmp_if = maxVal;
	} else {
		if (Value < minVal) {
			tmp_if = minVal;
		} else {
			tmp_if = Value;
		}
	}
	return tmp_if;
}

// For shared_memory
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
  i16 residual[8192][6144])
{
  u32 xOffDbf = puAddr[0] + blkAddr[0];
  u32 yOffDbf = puAddr[1] + blkAddr[1];
  u32 xOffLumaPred = tuAddr[0] + blkAddr[0];
  u32 yOffLumaPred = tuAddr[1] + blkAddr[1];
  u32 offRes = blkAddrRes[0] * 64 + blkAddrRes[1];

  u32 xOffChrDbf = puAddrChr[0] + blkAddrChr[0];
  u32 yOffChrDbf = puAddrChr[1] + blkAddrChr[1];
  u32 xOffChrLumaPred = tuAddrChr[0] + blkAddrChr[0];
  u32 yOffChrLumaPred = tuAddrChr[1] + blkAddrChr[1];
  u32 offChrRes[3];
  int x, y;
  u32 compIdx = 0;

  offChrRes[0] = 0;
  offChrRes[1] = 64*64 + blkAddrResChr[0] * 32 + blkAddrResChr[1];
  offChrRes[2] = 64*64 + 32*32 + blkAddrResChr[0] * 32 + blkAddrResChr[1];

  switch (numBlkSide)
  {
    case 1:
    {
      for (x = 0; x < 4 * 1; x++)
      {
        for (y = 0; y < 4 * 1; y++)
    	{
    	  dbfPict[dbfIdx][0][xOffDbf + x][yOffDbf + y] =
    	    clip_i32(lumaPred[intraIdx][xOffLumaPred + x][yOffLumaPred + y] +
            residual[idxRes][offRes + x * 64 + y], 0, 255);
        }
      }
      for (compIdx = 1; compIdx <= 2; compIdx++)
      {
        for (x = 0; x < 2 * 1; x++)
        {
          for (y = 0; y < 2 * 1; y++)
          {
            dbfPict[dbfIdx][compIdx][xOffChrDbf + x][yOffChrDbf + y] =
              clip_i32(chromaPred[intraIdx][compIdx-1][xOffChrLumaPred + x]
                [yOffChrLumaPred + y] + residual[idxRes][offChrRes[compIdx] +
                x * 32 + y], 0, 255);
          }
        }
      }
      break;
    }
    case 2:
      {
        for (x = 0; x < 4 * 2; x++)
        {
          add_8_16_clip_8_64x64_orcc(
            &lumaPred[intraIdx][xOffLumaPred + x][yOffLumaPred],
            &residual[idxRes][offRes + x * 64 + 0],
            &dbfPict[dbfIdx][0][xOffDbf + x][yOffDbf + 0],
            0);
        }
        for (compIdx = 1; compIdx <= 2; compIdx++)
        {
          for (x = 0; x < 2 * 2; x++)
          {
            for (y = 0; y < 2 * 2; y++)
            {
              dbfPict[dbfIdx][compIdx][xOffChrDbf + x][yOffChrDbf + y] =
                clip_i32(chromaPred[intraIdx][compIdx-1][xOffChrLumaPred + x]
                  [yOffChrLumaPred + y] + residual[idxRes][offChrRes[compIdx] +
                  x * 32 + y], 0, 255);
            }
          }
        }
        break;
      }
    case 4:
      {
        for (x = 0; x < 4 * 4; x++)
        {
          add_8_16_clip_16_64x64_orcc(
            &lumaPred[intraIdx][xOffLumaPred + x][yOffLumaPred],
            &residual[idxRes][offRes + x * 64 + 0],
            &dbfPict[dbfIdx][0][xOffDbf + x][yOffDbf + 0],
            0);
        }
        for (compIdx = 1; compIdx <= 2; compIdx++)
        {
          for (x = 0; x < 2 * 4; x++)
          {
            add_8_16_clip_8_64x64_orcc(
              &chromaPred[intraIdx][compIdx - 1][xOffChrLumaPred + x][yOffChrLumaPred],
              &residual[idxRes][offChrRes[compIdx] + x * 32 + 0],
              &dbfPict[dbfIdx][compIdx][xOffChrDbf + x][yOffChrDbf + 0],
              0);
          }
        }
        break;
      }
    case 8:
      {
        for (x = 0; x < 4 * 8; x++)
        {
          add_8_16_clip_32_64x64_orcc(
            &lumaPred[intraIdx][xOffLumaPred + x][yOffLumaPred],
            &residual[idxRes][offRes + x * 64 + 0],
            &dbfPict[dbfIdx][0][xOffDbf + x][yOffDbf + 0],
            0);
        }
        for (compIdx = 1; compIdx <= 2; compIdx++)
        {
          for (x = 0; x < 2 * 8; x++)
          {
            add_8_16_clip_16_64x64_orcc(
              &chromaPred[intraIdx][compIdx - 1][xOffChrLumaPred + x][yOffChrLumaPred],
              &residual[idxRes][offChrRes[compIdx] + x * 32 + 0],
              &dbfPict[dbfIdx][compIdx][xOffChrDbf + x][yOffChrDbf + 0],
              0);
          }
        }
        break;
      }
	default:
	  break;
  }
}

/* DECODING PICTURE BUFFER */

void copy_cu_dpb_luma_orcc(
  u8 samp[256],
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT + 2 * BORDER_SIZE][PICT_WIDTH + 2 * BORDER_SIZE],
  i32 xPixIdx,
  i32 yPixIdx,
  i8  lastIdx)
{
  int y;

  for (y = 0; y < 16; y++)
  {
    copy_8_8_16_1x4352_orcc(
      pictureBuffer[lastIdx][yPixIdx+y],
      samp + y * 16,
  	  xPixIdx);
  }
}

void copy_cu_dpb_chroma_orcc(
  u8 samp[64],
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT / 2 + 2 * BORDER_SIZE][PICT_WIDTH / 2 + 2 * BORDER_SIZE],
  i32 xPixIdx,
  i32 yPixIdx,
  i8  lastIdx)
{
  int y;

  for (y = 0; y < 8; y++)
  {
    copy_8_8_8_1x2304_orcc(
      pictureBuffer[lastIdx][yPixIdx+y],
      &samp[y * 8],
  	  xPixIdx);
  }
}

#define GETMVINFO_DPB_LUMA(H)                                                                                       \
void getmvinfo_dpb_ ## H ## _luma_orcc(                                                                             \
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT + 2 * BORDER_SIZE][PICT_WIDTH + 2 * BORDER_SIZE],                          \
  u8 RefCu[(H + 7) * (H + 7)],                                                                                      \
  u8 idx,                                                                                                           \
  u8 sideMax,                                                                                                       \
  i32 xOffset,                                                                                                      \
  i32 yOffset)                                                                                                      \
{                                                                                                                   \
  int y;                                                                                                            \
                                                                                                                    \
  for (y = 0; y < sideMax; y++)                                                                                     \
  {                                                                                                                 \
	copy_8_8_var_orcc(                                                                                              \
      &RefCu[y * (sideMax)],                                                                                        \
      pictureBuffer[idx][y+yOffset],                                                                                \
      xOffset,                                                                                                      \
  	  sideMax);                                                                                                     \
  }                                                                                                                 \
}

GETMVINFO_DPB_LUMA(64)
GETMVINFO_DPB_LUMA(32)
GETMVINFO_DPB_LUMA(16)
GETMVINFO_DPB_LUMA(8)

#define GETMVINFO_DPB_CHROMA(H)                                                                                     \
void getmvinfo_dpb_ ## H ## _chroma_orcc(                                                                           \
  u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT / 2 + 2 * BORDER_SIZE][PICT_WIDTH / 2 + 2 * BORDER_SIZE],                  \
  u8 RefCu[(H / 2 + 3) * (H / 2 + 3)],                                                                              \
  u8 idx,                                                                                                           \
  u8 sideMax,                                                                                                       \
  i32 xOffset,                                                                                                      \
  i32 yOffset)                                                                                                      \
{                                                                                                                   \
  int y;                                                                                                            \
                                                                                                                    \
  for (y = 0; y < sideMax; y++)                                                                                     \
  {                                                                                                                 \
	copy_8_8_var_orcc(                                                                                              \
      &RefCu[y * (sideMax)],                                                                                        \
      pictureBuffer[idx][y+yOffset],                                                                                \
      xOffset,                                                                                                      \
  	  sideMax);                                                                                                     \
  }                                                                                                                 \
}

GETMVINFO_DPB_CHROMA(64)
GETMVINFO_DPB_CHROMA(32)
GETMVINFO_DPB_CHROMA(16)


void fillBorder_luma_orcc(
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT+2*BORDER_SIZE][PICT_WIDTH+2*BORDER_SIZE],
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

  int iLoopCount = (xSize >> 4) - 1;

  y = 0;
  while (y <= border_size - 1) {
    pm128iPictureBuffer1 = (__m128i *) &pictureBuffer[lastIdx][border_size][border_size];
    pm128iPictureBuffer2 = (__m128i *) &pictureBuffer[lastIdx][ySize + border_size - 1][border_size];
    pm128iPictureBuffer  = (__m128i *) &pictureBuffer[lastIdx][y][border_size];
    pm128iPictureBuffer0 = (__m128i *) &pictureBuffer[lastIdx][y + ySize + border_size][border_size];
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
    y = y + 1;
  }

  iLoopCount = (border_size >> 4) - 1;
  y = 0;
  while (y <= ySize + 2 * border_size - 1) {
	tmp_pictureBuffer = pictureBuffer[lastIdx][y][border_size];
	tmp_pictureBuffer0 = pictureBuffer[lastIdx][y][xSize + border_size - 1];
	pm128iPictureBuffer = (__m128i *) &pictureBuffer[lastIdx][y][0];
	pm128iPictureBuffer0 = (__m128i *) &pictureBuffer[lastIdx][y][0 + xSize + border_size];
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
    y = y + 1;
  }
}


void fillBorder_chroma_orcc(
	u8 pictureBuffer[DPB_SIZE][PICT_HEIGHT/2+2*BORDER_SIZE][PICT_WIDTH/2+2*BORDER_SIZE],
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

  int iLoopCount = (xSize >> 4) - 1;

  y = 0;
  while (y <= border_size - 1) {
    pm128iPictureBuffer1 = (__m128i *) &pictureBuffer[lastIdx][border_size][border_size];
    pm128iPictureBuffer2 = (__m128i *) &pictureBuffer[lastIdx][ySize + border_size - 1][border_size];
    pm128iPictureBuffer  = (__m128i *) &pictureBuffer[lastIdx][y][border_size];
    pm128iPictureBuffer0 = (__m128i *) &pictureBuffer[lastIdx][y + ySize + border_size][border_size];
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
    y = y + 1;
  }

  iLoopCount = (border_size >> 4) - 1;
  y = 0;
  while (y <= ySize + 2 * border_size - 1) {
    tmp_pictureBuffer = pictureBuffer[lastIdx][y][border_size];
    tmp_pictureBuffer0 = pictureBuffer[lastIdx][y][xSize + border_size - 1];
    pm128iPictureBuffer = (__m128i *) &pictureBuffer[lastIdx][y][0];
    pm128iPictureBuffer0 = (__m128i *) &pictureBuffer[lastIdx][y][0 + xSize + border_size];
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
    y = y + 1;
  }
}


/* WEIGHTED PREDICTION */

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


/* DISPLAY */

#define INT_MAXIM(a, b)  ((a) > (b) ? (a) : (b))
#define INT_MINIM(a, b)  ((a) < (b) ? (a) : (b))

#define MAX_WIDTH 4096
#define MAX_HEIGHT 2048

#define DISPLAYYUV_CROP(H)                                                                                                             \
void displayYUV_crop_ ## H ## _orcc(                                                                                                   \
  u8 Bytes[H * H],                                                                                                                     \
  u8 pictureBuffer[MAX_WIDTH * MAX_HEIGHT],                                                                                            \
  u16 xMin,                                                                                                                            \
  u16 xMax,                                                                                                                            \
  u16 yMin,                                                                                                                            \
  u16 yMax,                                                                                                                            \
  u16 xIdx,                                                                                                                            \
  u16 yIdx,                                                                                                                            \
  u16 cropPicWth)                                                                                                                      \
{                                                                                                                                      \
  u16 xIdxMin = INT_MAXIM(xIdx, xMin);                                                                                                 \
  u16 xIdxMax = INT_MINIM(xIdx + H - 1, xMax);                                                                                         \
  u16 yIdxMin = INT_MAXIM(yIdx, yMin);                                                                                                 \
  u16 yIdxMax = INT_MINIM(yIdx + H - 1, yMax);                                                                                         \
  int x, y;                                                                                                                            \
                                                                                                                                       \
  __m128i * __restrict pm128iPictureBuffer = (__m128i *) &pictureBuffer[(yIdxMin - yMin) * cropPicWth + xIdxMin - xMin];               \
  __m128i * __restrict pm128iPictureBuffer1 = (__m128i *) &Bytes[(yIdxMin - yIdx) * H + xIdxMin - xIdx];                               \
  __m128i m128iWord;                                                                                                                   \
                                                                                                                                       \
  int iLoopCount = (xIdxMax - xIdxMin + 1) >> 4;                                                                                       \
                                                                                                                                       \
  for (y = yIdxMin; y < yIdxMax + 1; y++)                                                                                              \
  {                                                                                                                                    \
    pm128iPictureBuffer = (__m128i *) &pictureBuffer[(y - yMin) * cropPicWth + xIdxMin - xMin];                                        \
    pm128iPictureBuffer1 = (__m128i *) &Bytes[(y - yIdx) *  H + xIdxMin - xIdx];                                                       \
    for (x = 0; x < iLoopCount; x++)                                                                                                   \
    {                                                                                                                                  \
      m128iWord = _mm_loadu_si128(pm128iPictureBuffer1);                                                                               \
      _mm_storeu_si128(pm128iPictureBuffer, m128iWord);                                                                                \
      pm128iPictureBuffer++;                                                                                                           \
      pm128iPictureBuffer1++;                                                                                                          \
    }                                                                                                                                  \
    for (x = xIdxMin + (iLoopCount << 4); x < xIdxMax + 1; x++)                                                                        \
    {                                                                                                                                  \
      pictureBuffer[(y - yMin) * cropPicWth + x - xMin] = Bytes[(y - yIdx) *  H + x - xIdx];                                           \
    }                                                                                                                                  \
  }                                                                                                                                    \
}                                                                                                                                      \

// Declare more functions if needed
DISPLAYYUV_CROP(16)
DISPLAYYUV_CROP(64)
DISPLAYYUV_CROP( 8)
DISPLAYYUV_CROP(32)



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
        i16 _src[2][64*64], int _width, int _height, u8 _dst[64*64])
{
  i16 * src = _src[0];
  u8 * dst = _dst;
  u8 width = _width + 1;
  u8 height = _height + 1;
  int wX = weightCu[0] + weightCu[1];
  int oX;
  int locLogWD = logWD - 14 + 8;
  int idx = lookup_tab_openhevc_function[_width];

#ifdef __SSE4_1__
  weighted_pred_mono[idx](locLogWD, wX, offsetCu[0], offsetCu[1], dst, width, src, width, width, height);
#else // #ifdef __SSE4_1__
  int x, y;
  oX = offsetCu[0] + offsetCu[1] + 1;
  locLogWD = logWD + 1;
  for (y = 0; y < height; y++)
  {
      for (x = 0; y < width; x++)
      {
        src[x + y * (width + 1)] = ((src[x + y * (width + 1)]*wX + (oX << (locLogWD - 1))) >> locLogWD);
        dst[x + y * (width + 1)] = clip_i32(src[x + y * (width + 1)], 0 , 255);
      }
  }
#endif // #ifdef __SSE4_1__
}

int sse_init_context()
{
#ifdef __SSE4_1__
    weighted_pred_mono[0] = ff_hevc_weighted_pred_mono2_8_sse;
    weighted_pred_mono[1] = ff_hevc_weighted_pred_mono4_8_sse;
    weighted_pred_mono[2] = ff_hevc_weighted_pred_mono8_8_sse;
    weighted_pred_mono[3] = ff_hevc_weighted_pred_mono16_8_sse;
#endif // #ifdef __SSE4_1__

    return 0;
}

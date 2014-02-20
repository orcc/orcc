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

#include "sse.h"

#include "emmintrin.h"

/***********************************************************************************************************************************
 SelectCu 
 ***********************************************************************************************************************************/
#define SCU_SIZE 16

void getCuSample_isIntra_orcc(
    u8 tokens_IntraSample[SCU_SIZE],
    i16 tokens_ResidualSample[SCU_SIZE],
    u8 tokens_Sample[SCU_SIZE],
    u8 scu_size)
{
  int i = 0;
  u8 ucScuSize = scu_size;
  u8 * __restrict pucTokensIntraSample = tokens_IntraSample;
  i16 * __restrict psTokensResidualSample = tokens_ResidualSample;
  u8 * __restrict pucTokensSample = tokens_Sample;

  __m128i * pm128iTokensIntraSample = (__m128i *) &pucTokensIntraSample[0];
  __m128i m128itmp_IntraSample;
  __m128i * pm128iTokensResidualSample = (__m128i *) &psTokensResidualSample[0];
  __m128i m128itmp_ResidualSample;
  __m128i m128itmp_add_i16_0, m128itmp_add_i16_1;
  __m128i * pm128iTokensSample = (__m128i *) &pucTokensSample[0];
  __m128i m128iZero = _mm_set1_epi16(0);

  i = 0;
  while (ucScuSize >= 16)
  {
    m128itmp_IntraSample = 
      _mm_unpacklo_epi8( // 8 unsigned char --> 8 short
      _mm_loadu_si128(pm128iTokensIntraSample + i), // 16 unsigned char
      m128iZero); // 8 shorts
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iTokensResidualSample + i); // 8 short
    m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_IntraSample, m128itmp_ResidualSample); // 8 short

    m128itmp_IntraSample = 
      _mm_unpackhi_epi8( // 8 unsigned char --> 8 short
      _mm_loadu_si128(pm128iTokensIntraSample + i), // 16 unsigned char (same as above)
      m128iZero); // 8 shorts
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iTokensResidualSample + i + 1); // 8 short
    m128itmp_add_i16_1 = _mm_add_epi16(m128itmp_IntraSample, m128itmp_ResidualSample); // 8 short

    _mm_storeu_si128(pm128iTokensSample + (i >> 1), _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1)); // u8

    ucScuSize -= 16;
    i = i + 2;
  }

  if (ucScuSize == 8)
  {
    m128itmp_IntraSample = 
      _mm_unpacklo_epi8( // 8 unsigned char --> 8 short
      _mm_loadl_epi64(pm128iTokensIntraSample + (i >> 1)), // 8 unsigned char (lower 64 bits, higher set to 0)
      m128iZero);
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iTokensResidualSample + i);
    m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_IntraSample, m128itmp_ResidualSample);

    m128itmp_add_i16_1 = _mm_set1_epi16(0);

    _mm_storel_epi64(pm128iTokensSample + (i >> 1), _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1)); // u8
  }
}


void getCuSample_isInter_orcc(
  i16 tokens_InterSample[SCU_SIZE],
  u8 interSamp[16][16][SCU_SIZE],
  u32 xIdx,
  u32 xOff,
  u32 yIdx,
  u32 yOff,
  u8 scu_size)
{
  int i = 0;
  u8 ucScuSize = scu_size;
  __m128i * pm128iInterSamp = (__m128i *) &interSamp[xIdx + xOff][yIdx + yOff][0];
  __m128i * pm128iTokensInterSample = (__m128i *) &tokens_InterSample[0];
  __m128i m128iTokensInterSample;

  i = 0;
  while (ucScuSize >= 16)
  {
    m128iTokensInterSample = _mm_loadu_si128(pm128iTokensInterSample + i);
    _mm_storeu_si128(pm128iInterSamp + i, m128iTokensInterSample); // u8

    ucScuSize -= 16;
    i = i + 1;
  }

  if (ucScuSize == 8)
  {
    pm128iInterSamp = (__m128i *) &interSamp[xIdx][yIdx][scu_size - 8];
    pm128iTokensInterSample = (__m128i *) &tokens_InterSample[scu_size - 8];
    m128iTokensInterSample = _mm_loadl_epi64(pm128iTokensInterSample);
    _mm_storel_epi64(pm128iInterSamp, m128iTokensInterSample); // u8
  }
}


void getCuSample_isInterRes_orcc(
  u8 interSamp[16][16][SCU_SIZE],
  i16 tokens_ResidualSample[SCU_SIZE],
  u8 tokens_Sample[SCU_SIZE],
  u16 interIdx[2],
  u8 scu_size)
{
  int i = 0;
  u8 ucScuSize = scu_size;

  __m128i * pm128iInterSamp = (__m128i *) &interSamp[interIdx[0]][interIdx[1]][0];
  __m128i m128itmp_interSamp;
  __m128i * pm128iTokensResidualSample = (__m128i *) &tokens_ResidualSample[0];
  __m128i m128itmp_ResidualSample;
  __m128i m128itmp_add_i16_0, m128itmp_add_i16_1;
  __m128i * pm128iTokensSample = (__m128i *) &tokens_Sample[0];
  __m128i m128iZero = _mm_set1_epi16(0);

  i = 0;
  while (ucScuSize >= 16)
  {
    m128itmp_interSamp = 
      _mm_unpacklo_epi8( // 8 unsigned char --> 8 short
      _mm_loadu_si128(pm128iInterSamp + i), // 16 unsigned char
      m128iZero); // 8 shorts
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iTokensResidualSample + i); // 8 short
    m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_interSamp, m128itmp_ResidualSample); // 8 short

    m128itmp_interSamp = 
      _mm_unpackhi_epi8( // 8 unsigned char --> 8 short
      _mm_loadu_si128(pm128iInterSamp + i), // 16 unsigned char
      m128iZero); // 8 shorts
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iTokensResidualSample + i + 1); // 8 short
    m128itmp_add_i16_1 = _mm_add_epi16(m128itmp_interSamp, m128itmp_ResidualSample); // 8 short

    _mm_storeu_si128(pm128iTokensSample + (i >> 1), _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1)); // u8

    ucScuSize -= 16;
    i = i + 2;
  }

  if (ucScuSize == 8)
  {
    m128itmp_interSamp = 
      _mm_unpacklo_epi8( // 8 unsigned char --> 8 short
      _mm_loadl_epi64(pm128iInterSamp + (i >> 1)), // 8 unsigned char (lower 64 bits, higher set to 0)
      m128iZero);
    m128itmp_ResidualSample = _mm_loadu_si128(pm128iTokensResidualSample + i);
    m128itmp_add_i16_0 = _mm_add_epi16(m128itmp_interSamp, m128itmp_ResidualSample);

    m128itmp_add_i16_1 = _mm_set1_epi16(0);

    _mm_storel_epi64(pm128iTokensSample + (i >> 1), _mm_packus_epi16(m128itmp_add_i16_0, m128itmp_add_i16_1)); // u8
  }
}

/***********************************************************************************************************************************
 DecodingPictureBuffer 
 ***********************************************************************************************************************************/
#define BORDER_SIZE 128

void getCuPixDone_cal(
	u8 pictureBuffer[17][2304][4352],
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
    pm128iPictureBuffer = (__m128i *) &pictureBuffer[lastIdx][y][border_size];
    pm128iPictureBuffer0 = (__m128i *) &pictureBuffer[lastIdx][y][border_size + xSize + border_size];
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
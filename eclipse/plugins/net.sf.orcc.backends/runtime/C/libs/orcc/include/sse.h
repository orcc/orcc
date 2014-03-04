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

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

/***********************************************************************************************************************************
 SelectCu 
 ***********************************************************************************************************************************/
void copy_8_8_16_orcc(
  u8 inputSample[16],
  u8 outputSample[16]);

void copy_8_8_16_output1616_orcc(
  u8 inputSample[16],
  u8 outputSample[16][16][16],
  u32 xIdx,
  u32 xOff,
  u32 yIdx,
  u32 yOff);

void add_8_16_clip_8_orcc(
  u8 predSample[8],
  i16 resSample[8],
  u8 Sample[8]);

void add_8_16_clip_16_orcc(
  u8 predSample[16],
  i16 resSample[16],
  u8 Sample[16]);

void add_8_16_clip_24_orcc(
  u8 predSample[24],
  i16 resSample[24],
  u8 Sample[24]);

void add_8_16_clip_32_orcc(
  u8 predSample[32],
  i16 resSample[32],
  u8 Sample[32]);

void add_8_16_clip_64_orcc(
  u8 predSample[64],
  i16 resSample[64],
  u8 Sample[64]);

/***********************************************************************************************************************************
 DecodingPictureBuffer 
 ***********************************************************************************************************************************/

#define BORDER_SIZE 128

void getCuPixDone_luma_orcc(
	u8 pictureBuffer[17][2304][4352],
	i8 lastIdx,
	int xSize,
	int ySize,
	u16 border_size);

void getCuPixDone_chroma_orcc(
	u8 pictureBuffer[17][768][1280],
	i8 lastIdx,
	int xSize,
	int ySize,
	u16 border_size);

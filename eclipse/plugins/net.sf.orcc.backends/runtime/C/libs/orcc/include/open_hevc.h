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

#ifndef _ORCC_OPEN_HEVC_H_
#define _ORCC_OPEN_HEVC_H_

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

#include "types.h"

int openhevc_init_context();

void put_hevc_qpel_orcc(i16 _dst[2][64*64], u8 listIdx, u8 _src[71*71], u8 srcstride, u8 width, u8 height, i32 mx, i32 my);

void put_hevc_epel_orcc(i16 _dst[2][64*64], u8 listIdx, u8 _src[71*71], u8 srcstride, u8 width, u8 height, i32 mx, i32 my);

void put_unweighted_pred_orcc(u8 _dst[2][64*64], i16 _src[2][64*64], u8 width, u8 height, u8 rdList);

void put_unweighted_pred_avg_orcc(u8 _dst[2][64*64], i16 _src[2][64*64], u8 width, u8 height);

void pred_planar_orcc(u8 _src[4096], u8 _top[129], u8 _left[129], i32 stride, i32 log2size);

void pred_angular_orcc(u8 _src[4096], u8 _top[129], u8 _left[129], i32 stride, i32 idx, u8 mode, i32 log2size);




#endif  /* _ORCC_OPEN_HEVC_H_ */

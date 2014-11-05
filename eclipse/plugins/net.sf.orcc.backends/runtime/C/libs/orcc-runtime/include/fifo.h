/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
#ifndef _ORCC_FIFO_H_
#define _ORCC_FIFO_H_

#include "types.h"

#ifndef CACHELINE_SIZE
#define CACHELINE_SIZE 64 // Standard size for x86 processors
#endif

// Declare the FIFO structure with a size equal to (size)
#define DECLARE_FIFO(type, size, count, readersnb) static type array_##count[(size)]; \
static unsigned int read_inds_##count[readersnb] = {0}; \
static FIFO_T(type) fifo_##count = {{0}, read_inds_##count, {0}, 0, {0}, array_##count};

#define FIFO_T(T) FIFO_T_EXPAND(T)
#define FIFO_T_EXPAND(T) fifo_##T##_t

#define FIFO_GET_ROOM(T) FIFO_GET_ROOM_EXPAND(T)
#define FIFO_GET_ROOM_EXPAND(T) fifo_ ## T ## _get_room

#define FIFO_GET_NUM_TOKENS(T) FIFO_GET_NUM_TOKENS_EXPAND(T)
#define FIFO_GET_NUM_TOKENS_EXPAND(T) fifo_ ## T ## _get_num_tokens

/* Define structure and methods for all types thanks to macro expansion */

#define T i8
#include "generic_fifo.h"
#undef T

#define T i16
#include "generic_fifo.h"
#undef T

#define T i32
#include "generic_fifo.h"
#undef T

#define T i64
#include "generic_fifo.h"
#undef T

#define T u8
#include "generic_fifo.h"
#undef T

#define T u16
#include "generic_fifo.h"
#undef T

#define T u32
#include "generic_fifo.h"
#undef T

#define T u64
#include "generic_fifo.h"
#undef T

#define T float
#include "generic_fifo.h"
#undef T

#endif  /* _ORCC_FIFO_H_ */

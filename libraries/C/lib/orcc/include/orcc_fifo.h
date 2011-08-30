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
#ifndef FIFO_H
#define FIFO_H

enum reasons {
	starved,
	full
};

struct schedinfo_s {
	int num_firings;
	enum reasons reason;
	int ports; /** contains a mask that indicate the ports affected */
};

// declare FIFO with a size equal to (size)
#define DECLARE_FIFO(type, size, count, readersnb) static type array_##count[(size)]; \
static unsigned int read_inds_##count[readersnb] = {0}; \
static struct FIFO_S(type) fifo_##count = { (size), array_##count, readersnb, read_inds_##count, 0 };

#define FIFO_CLEAR(T) FIFO_CLEAR_EXPAND(T)
#define FIFO_CLEAR_EXPAND(T) fifo_ ## T ## _clear

#define FIFO_S(T) FIFO_S_EXPAND(T)
#define FIFO_S_EXPAND(T) fifo_##T##_s

#define FIFO_GET_ROOM(T) FIFO_GET_ROOM_EXPAND(T)
#define FIFO_GET_ROOM_EXPAND(T) fifo_ ## T ## _get_room

#define FIFO_HAS_ROOM(T) FIFO_HAS_ROOM_EXPAND(T)
#define FIFO_HAS_ROOM_EXPAND(T) fifo_ ## T ## _has_room

#define FIFO_HAS_TOKENS(T) FIFO_HAS_TOKENS_EXPAND(T)
#define FIFO_HAS_TOKENS_EXPAND(T) fifo_ ## T ## _has_tokens

#define FIFO_GET_NUM_TOKENS(T) FIFO_GET_NUM_TOKENS_EXPAND(T)
#define FIFO_GET_NUM_TOKENS_EXPAND(T) fifo_ ## T ## _get_num_tokens

#define FIFO_READ(T) FIFO_READ_EXPAND(T)
#define FIFO_READ_EXPAND(T) fifo_ ## T ## _read_1

#define FIFO_WRITE(T) FIFO_WRITE_EXPAND(T)
#define FIFO_WRITE_EXPAND(T) fifo_ ## T ## _write_1

#define T i8
#include "orcc_generic_fifo.h"
#undef T

#define T i16
#include "orcc_generic_fifo.h"
#undef T

#define T i32
#include "orcc_generic_fifo.h"
#undef T

#define T i64
#include "orcc_generic_fifo.h"
#undef T

#define T u8
#include "orcc_generic_fifo.h"
#undef T

#define T u16
#include "orcc_generic_fifo.h"
#undef T

#define T u32
#include "orcc_generic_fifo.h"
#undef T

#define T u64
#include "orcc_generic_fifo.h"
#undef T

#endif

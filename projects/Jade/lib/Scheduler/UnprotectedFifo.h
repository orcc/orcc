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
#ifndef UNPROTECTEDFIFO_H
#define UNPROTECTEDFIFO_H

typedef unsigned char u_char;
typedef unsigned short u_short;
typedef unsigned int u_int;

// declare FIFO with a size equal to (size)
#define DECLARE_FIFO(type, size, count) static type array_##count[(size)]; \
static struct FIFO_S(type) fifo_##count = { (size), array_##count, 0, 0 };

#define FIFO_S(T) FIFO_S_EXPAND(T)
#define FIFO_S_EXPAND(T) fifo_##T##_s

#define FIFO_HAS_ROOM(T) FIFO_HAS_ROOM_EXPAND(T)
#define FIFO_HAS_ROOM_EXPAND(T) fifo_ ## T ## _has_room

#define FIFO_HAS_TOKENS(T) FIFO_HAS_TOKENS_EXPAND(T)
#define FIFO_HAS_TOKENS_EXPAND(T) fifo_ ## T ## _has_tokens

#define FIFO_PEEK(T) FIFO_PEEK_EXPAND(T)
#define FIFO_PEEK_EXPAND(T) fifo_ ## T ## _peek

#define FIFO_READ(T) FIFO_READ_EXPAND(T)
#define FIFO_READ_EXPAND(T) fifo_ ## T ## _read

#define FIFO_READ_END(T) FIFO_READ_END_EXPAND(T)
#define FIFO_READ_END_EXPAND(T) fifo_ ## T ## _read_end

#define FIFO_WRITE(T) FIFO_WRITE_EXPAND(T)
#define FIFO_WRITE_EXPAND(T) fifo_ ## T ## _write

#define FIFO_WRITE_END(T) FIFO_WRITE_END_EXPAND(T)
#define FIFO_WRITE_END_EXPAND(T) fifo_ ## T ## _write_end

#define T char
#include "unprotectedFifo_generic.h"
#undef T

#define T u_char
#include "unprotectedFifo_generic.h"
#undef T

#define T short
#include "unprotectedFifo_generic.h"
#undef T

#define T u_short
#include "unprotectedFifo_generic.h"
#undef T

#define T int
#include "unprotectedFifo_generic.h"
#undef T

#define T u_int
#include "unprotectedFifo_generic.h"
#undef T

#endif

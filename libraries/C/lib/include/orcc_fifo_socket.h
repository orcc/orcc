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
#define DECL static
#ifndef FIFO_SOCKET_H
#define FIFO_SOCKET_H

#include "socket.h"


DECL void socket_init() {
	#ifdef WIN32
		WSADATA wsa;
		int err = WSAStartup(MAKEWORD(2, 2), &wsa);
		if(err < 0) {
			fprintf(stderr,"WSAStartup failed !");
			exit(EXIT_FAILURE);
		}
	#endif
}


DECL void socket_end() {
	#ifdef WIN32
		WSACleanup();
	#endif
}

// declare FIFO with a size equal to (size)
#define DECLARE_FIFO_SOCKET(type, size, count, readersnb) static type array_##count[size]; \
static unsigned int read_inds_##count[1] = {0}; \
static struct FIFO_SOCKET_S(type) fifo_##count = {size, array_##count, 0, 1, read_inds_##count, 0, 0};

#define FIFO_SOCKET_S(T) FIFO_SOCKET_S_EXPAND(T)
#define FIFO_SOCKET_S_EXPAND(T) fifo_socket_##T##_s

#define FIFO_SOCKET_GET_ROOM(T) FIFO_SOCKET_GET_ROOM_EXPAND(T)
#define FIFO_SOCKET_GET_ROOM_EXPAND(T) fifo_socket_ ## T ## _get_room

#define FIFO_SOCKET_HAS_ROOM(T) FIFO_SOCKET_HAS_ROOM_EXPAND(T)
#define FIFO_SOCKET_HAS_ROOM_EXPAND(T) fifo_socket_ ## T ## _has_room

#define FIFO_SOCKET_GET_NUM_TOKENS(T) FIFO_SOCKET_GET_NUM_TOKENS_EXPAND(T)
#define FIFO_SOCKET_GET_NUM_TOKENS_EXPAND(T) fifo_socket_ ## T ## _get_num_tokens

#define FIFO_SOCKET_PEEK(T) FIFO_SOCKET_PEEK_EXPAND(T)
#define FIFO_SOCKET_PEEK_EXPAND(T) fifo_socket_ ## T ## _peek

#define FIFO_SOCKET_READ(T) FIFO_SOCKET_READ_EXPAND(T)
#define FIFO_SOCKET_READ_EXPAND(T) fifo_socket_ ## T ## _read

#define FIFO_SOCKET_READ_COPY(T) FIFO_SOCKET_READ_COPY_EXPAND(T)
#define FIFO_SOCKET_READ_COPY_EXPAND(T) fifo_socket_ ## T ## _read_copy

#define FIFO_SOCKET_READ_END(T) FIFO_SOCKET_READ_END_EXPAND(T)
#define FIFO_SOCKET_READ_END_EXPAND(T) fifo_socket_ ## T ## _read_end

#define FIFO_SOCKET_WRITE(T) FIFO_SOCKET_WRITE_EXPAND(T)
#define FIFO_SOCKET_WRITE_EXPAND(T) fifo_socket_ ## T ## _write

#define FIFO_SOCKET_WRITE_END(T) FIFO_SOCKET_WRITE_END_EXPAND(T)
#define FIFO_SOCKET_WRITE_END_EXPAND(T) fifo_socket_ ## T ## _write_end

#define FIFO_SOCKET_INIT(T) FIFO_SOCKET_INIT_EXPAND(T)
#define FIFO_SOCKET_INIT_EXPAND(T) fifo_socket_ ## T ## _init

#define T i8
#include "orcc_generic_fifo_socket.h"
#undef T

#define T i16
#include "orcc_generic_fifo_socket.h"
#undef T

#define T i32
#include "orcc_generic_fifo_socket.h"
#undef T

#define T i64
#include "orcc_generic_fifo_socket.h"
#undef T

#define T u8
#include "orcc_generic_fifo_socket.h"
#undef T

#define T u16
#include "orcc_generic_fifo_socket.h"
#undef T

#define T u32
#include "orcc_generic_fifo_socket.h"
#undef T

#define T u64
#include "orcc_generic_fifo_socket.h"
#undef T

#endif

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

#include <string.h>
#include <stdlib.h>

DECL unsigned int FIFO_HAS_TOKENS(T)(struct FIFO_S(T) *fifo, unsigned int reader_id, unsigned int n) {
	return fifo->write_ind - fifo->read_inds[reader_id] >= n;
}

DECL unsigned int FIFO_GET_NUM_TOKENS(T)(struct FIFO_S(T) *fifo, unsigned int reader_id) {
	return fifo->write_ind - fifo->read_inds[reader_id];
}

DECL unsigned int FIFO_HAS_ROOM(T)(struct FIFO_S(T) *fifo, unsigned int num_readers, unsigned int n) {
	unsigned int i;
	for(i = 0; i < num_readers; i++) {
		if (fifo->size + 1 - (fifo->write_ind - fifo->read_inds[i]) <= n) {
			return 0;
		}
	}

	return 1;
}

DECL unsigned int FIFO_GET_ROOM(T)(struct FIFO_S(T) *fifo, unsigned int num_readers) {
	unsigned int i;
	unsigned int max_num_tokens = 0;

	for (i = 0; i < num_readers; i++) {
		unsigned int num_tokens = fifo->write_ind - fifo->read_inds[i];
		max_num_tokens = max_num_tokens > num_tokens ? max_num_tokens : num_tokens;
	}

	return fifo->size - max_num_tokens;
}

DECL void FIFO_CLEAR(T)(struct FIFO_S(T) *fifo) {
	unsigned int i;
	fifo->write_ind = 0;
	for (i = 0; i < fifo->readers_nb; i++) {
		fifo->read_inds[i] = 0;
	}
}

DECL T FIFO_READ(T)(struct FIFO_S(T) *fifo, unsigned int reader_id) {
	T value = fifo->contents[fifo->read_inds[reader_id] & (fifo->size - 1)];
	fifo->read_inds[reader_id]++;
	return value;
}

DECL void FIFO_WRITE(T)(struct FIFO_S(T) *fifo, T value) {
	fifo->contents[fifo->write_ind & (fifo->size - 1)] = value;
	fifo->write_ind++;
}

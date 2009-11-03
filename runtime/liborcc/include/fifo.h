/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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

#include <string.h>

struct fifo_s {
	int elt_size;
	int size;
	char *contents;
	int read_ptr;
	int write_ptr;
};

#define contents(fifo, ptr) (& (fifo)->contents[(ptr) * (fifo)->elt_size])

static void *getPeekPtr(struct fifo_s *fifo, int n) {
	return contents(fifo, fifo->read_ptr);
}

static void *getReadPtr(struct fifo_s *fifo, int n) {
	int ptr = fifo->read_ptr;
	fifo->read_ptr += n;
	return contents(fifo, ptr);
}

static int hasRoom(struct fifo_s *fifo, int n) {
	int num_free = fifo->size - fifo->write_ptr;
	int res = (num_free >= n);
	if (!res) {
		// the FIFO is full, check if it is artificial
		int num_tokens = fifo->write_ptr - fifo->read_ptr;
		if (fifo->read_ptr >= num_tokens + n) {
			// there is room to copy the not-read-yet tokens at the beginning of the FIFO
			if (num_tokens > 0) {
				// only copy if there are tokens
				memcpy(fifo->contents, contents(fifo, fifo->read_ptr), num_tokens * fifo->elt_size);
			}
			fifo->read_ptr = 0;
			fifo->write_ptr = num_tokens;

			res = ((fifo->size - num_tokens) >= n);
		}
	}
	return res;
}

static int hasTokens(struct fifo_s *fifo, int n) {
	int num_tokens = fifo->write_ptr - fifo->read_ptr;
	int res = (num_tokens >= n);
	if (!res) {
		if (num_tokens == 0) {
			// there are no tokens in the FIFO, just resets the read/write pointers
			// this might allow fireable actions with no room on output ports to fire
			fifo->read_ptr = 0;
			fifo->write_ptr = 0;
		}
	}

	return res;
}

static void *getWritePtr(struct fifo_s *fifo, int n) {
	int ptr = fifo->write_ptr;
	fifo->write_ptr += n;
	return contents(fifo, ptr);
}

static void writeEnd(struct fifo_s *fifo) {
}

static void readEnd(struct fifo_s *fifo) {
}

#endif

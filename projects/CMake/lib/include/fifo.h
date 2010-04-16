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

struct fifo_s {
	int elt_size;
	int size;
	char *contents;
	int read_ptr;
	int write_ptr;
};

#define DECLARE_FIFO(type, size, count) static char array_##count[(size) * sizeof(type)]; \
static struct fifo_s fifo_##count = { sizeof(type), size, array_##count, 0, 0 };

#define contents(fifo, ptr) (& (fifo)->contents[(ptr) * (fifo)->elt_size])

#ifdef DEBUG
	extern void *getPeekPtr(struct fifo_s *fifo, int n);
	extern void *getReadPtr(struct fifo_s *fifo, int n);
	extern void *getWritePtr(struct fifo_s *fifo, int n);
	extern int hasRoom(struct fifo_s *fifo, int n);
	extern int hasTokens(struct fifo_s *fifo, int n);
	extern void setReadEnd(struct fifo_s *fifo);
	extern void setWriteEnd(struct fifo_s *fifo);
#else
	#include "fifo.inc.h"
#endif

#endif

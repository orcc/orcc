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
#ifndef TRACE_H
#define TRACE_H

#define DECLARE_TRACE(name) FILE* trace_##name = NULL; 

#define FIFO_TRACE(T) FIFO_TRACE_EXPAND(T)
#define FIFO_TRACE_EXPAND(T) fifo_ ## T ## _trace

#define SIGNED_PRINT "%d\n"
#define UNSIGNED_PRINT "%d\n"

#define PRINT_FORMAT SIGNED_PRINT

#define T char
#include "trace.inl"
#undef T

#define T short
#include "trace.inl"
#undef T

#define T int
#include "trace.inl"
#undef T
#undef PRINT_FORMAT

#define PRINT_FORMAT UNSIGNED_PRINT
#define T u_char
#include "trace.inl"
#undef T

#define T u_short
#include "trace.inl"
#undef T

#define T u_int
#include "trace.inl"
#undef T
#undef PRINT_FORMAT


#endif

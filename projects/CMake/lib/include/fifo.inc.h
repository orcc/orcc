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
#define DECL static

#include <string.h>
#include <stdlib.h>

DECL int hasTokens(struct fifo_s *fifo, int n) {
	return fifo->fill_count >= n;
}

DECL int hasRoom(struct fifo_s *fifo, int n) {
	// Test if distance between the read ptr and the write is longer than n
	return (fifo->size - fifo->fill_count) >= n;
}

DECL void *getPeekPtr(struct fifo_s *fifo, int n) {
	if (fifo->read_ind + n > fifo->size) {
		int num_end = fifo->size - fifo->read_ind;
		int num_beginning = n - num_end;

		// Copy the end of the fifo
		if (num_end != 0) {
			memcpy(fifo->fifo_buffer, contents(fifo, fifo->read_ind), fifo->elt_size * num_end);
		}

		// Copy the rest of the data at the beginning of the FIFO
		if (num_beginning != 0) {
			memcpy(&fifo->fifo_buffer[fifo->elt_size * num_end], fifo->contents, fifo->elt_size * num_beginning);
		}

		fifo->fifo_buffer_ptr = fifo->fifo_buffer;
		return fifo->fifo_buffer;
	} else {
		fifo->fifo_buffer_ptr = NULL;
		return contents(fifo, fifo->read_ind);
	}
}

DECL void *getReadPtr(struct fifo_s *fifo, int n) {
	return getPeekPtr(fifo, n);
}

DECL void setReadEnd(struct fifo_s *fifo, int n) {
	fifo->fill_count -= n;
	if (fifo->fifo_buffer_ptr == NULL) {
		fifo->read_ind += n;
	} else {
		int num_end = fifo->size - fifo->read_ind;
		int num_beginning = n - num_end;

		fifo->fifo_buffer_ptr = NULL;
		fifo->read_ind = num_beginning;
	}
}

DECL void *getWritePtr(struct fifo_s *fifo, int n) {
	if (fifo->write_ind + n > fifo->size) {
		fifo->fifo_buffer_ptr = fifo->fifo_buffer;
		return fifo->fifo_buffer;
	} else {
		fifo->fifo_buffer_ptr = NULL;
		return contents(fifo, fifo->write_ind);
	}
}

DECL void setWriteEnd(struct fifo_s *fifo, int n) {
	fifo->fill_count += n;
	if (fifo->fifo_buffer_ptr == NULL) {
		fifo->write_ind += n;
	} else {
		int num_end = fifo->size - fifo->write_ind;
		int num_beginning = n - num_end;

		// Copy data at the end of the FIFO
		if (num_end != 0) {
			memcpy(contents(fifo, fifo->write_ind), fifo->fifo_buffer_ptr, fifo->elt_size * num_end);
		}

		// Copy the rest of data at the beginning of the FIFO
		if (num_beginning) {
			memcpy(fifo->contents, &fifo->fifo_buffer_ptr[fifo->elt_size * num_end], fifo->elt_size * num_beginning);
		}

		fifo->fifo_buffer_ptr = NULL;
		fifo->write_ind = num_beginning;
	}
}

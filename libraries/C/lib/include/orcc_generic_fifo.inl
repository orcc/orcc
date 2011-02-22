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

DECL int FIFO_HAS_TOKENS(T)(struct FIFO_S(T) *fifo, int n) {
	// return fifo->fill_count >= n;
	if (fifo->write_ind >= fifo->read_ind) {
		return fifo->write_ind - fifo->read_ind >= n;
	} else {
		return fifo->size - fifo->read_ind + fifo->write_ind >= n;
	}
}

DECL int FIFO_GET_NUM_TOKENS(T)(struct FIFO_S(T) *fifo) {
	// return fifo->fill_count;
	if (fifo->write_ind >= fifo->read_ind) {
		return fifo->write_ind - fifo->read_ind;
	} else {
		return fifo->size - fifo->read_ind + fifo->write_ind;
	}
}

DECL int FIFO_HAS_ROOM(T)(struct FIFO_S(T) *fifo, int n) {
	// return (fifo->size - fifo->fill_count) >= n;
	if (fifo->read_ind > fifo->write_ind) {
		return (fifo->read_ind - fifo->write_ind) > n;
	} else {
		return (fifo->size - fifo->write_ind + fifo->read_ind) > n;
	}
}

DECL int FIFO_GET_ROOM(T)(struct FIFO_S(T) *fifo) {
	// return fifo->size - fifo->fill_count;
	if (fifo->read_ind > fifo->write_ind) {
		return fifo->read_ind - fifo->write_ind;
	} else {
		return fifo->size - fifo->write_ind + fifo->read_ind;
	}
}

DECL T *FIFO_PEEK(T)(struct FIFO_S(T) *fifo, T *buffer, int n) {
	if (fifo->read_ind + n <= fifo->size) {
		return &fifo->contents[fifo->read_ind];
	} else {
		int num_end = fifo->size - fifo->read_ind;
		int num_beginning = n - num_end;

		// Copy the end of the fifo
		if (num_end != 0) {
			memcpy(buffer, &fifo->contents[fifo->read_ind], num_end * sizeof(T));
		}

		// Copy the rest of the data at the beginning of the FIFO
		if (num_beginning != 0) {
			memcpy(&buffer[num_end], fifo->contents, num_beginning * sizeof(T));
		}

		// returns supplied buffer
		return buffer;
	}
}

DECL void FIFO_READ_COPY(T)(struct FIFO_S(T) *fifo, T *buffer, int n) {
	if (fifo->read_ind + n <= fifo->size) {
		memcpy(buffer, &fifo->contents[fifo->read_ind], n * sizeof(T));
	} else {
		int num_end = fifo->size - fifo->read_ind;
		int num_beginning = n - num_end;

		// Copy the end of the fifo
		if (num_end != 0) {
			memcpy(buffer, &fifo->contents[fifo->read_ind], num_end * sizeof(T));
		}

		// Copy the rest of the data at the beginning of the FIFO
		if (num_beginning != 0) {
			memcpy(&buffer[num_end], fifo->contents, num_beginning * sizeof(T));
		}
	}
}

DECL T *FIFO_READ(T)(struct FIFO_S(T) *fifo, T *buffer, int n) {
	return FIFO_PEEK(T)(fifo, buffer, n);
}

DECL void FIFO_READ_END(T)(struct FIFO_S(T) *fifo, int n) {
	int read_pos = fifo->read_ind + n;
	if (read_pos < fifo->size) {
		fifo->read_ind += n;
	} else if (read_pos == fifo->size) {
		fifo->read_ind = 0;
	} else {
		fifo->read_ind = read_pos - fifo->size;
	}

	// fifo->fill_count -= n;
}

DECL T *FIFO_WRITE(T)(struct FIFO_S(T) *fifo, T *buffer, int n) {
	if (fifo->write_ind + n <= fifo->size) {
		return &fifo->contents[fifo->write_ind];
	} else {
		// returns supplied buffer, we will copy its contents in write_end
		return buffer;
	}
}

DECL void FIFO_WRITE_END(T)(struct FIFO_S(T) *fifo, T *buffer, int n) {
	if (fifo->write_ind + n < fifo->size) {
		fifo->write_ind += n;
	} else if (fifo->write_ind + n == fifo->size) {
		fifo->write_ind = 0;
	} else {
		int num_end = fifo->size - fifo->write_ind;
		int num_beginning = n - num_end;

		// Copy data at the end of the FIFO
		if (num_end != 0) {
			memcpy(&fifo->contents[fifo->write_ind], buffer, num_end * sizeof(T));
		}

		// Copy the rest of data at the beginning of the FIFO
		if (num_beginning) {
			memcpy(fifo->contents, &buffer[num_end], num_beginning * sizeof(T));
		}

		fifo->write_ind = num_beginning;
	}

	// fifo->fill_count += n;
}

DECL void FIFO_CLEAR(T)(struct FIFO_S(T) *fifo) {
	fifo->write_ind = 0;
	fifo->read_ind = 0;
}

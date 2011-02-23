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

DECL int FIFO_HAS_TOKENS( T) (struct FIFO_S(T) *fifo, int reader_id, int n) {
	// return fifo->fill_count >= n;
	if (fifo->write_ind >= fifo->read_inds[reader_id]) {
		return fifo->write_ind - fifo->read_inds[reader_id] >= n;
	} else {
		return fifo->size - fifo->read_inds[reader_id] + fifo->write_ind >= n;
	}
}

DECL int FIFO_GET_NUM_TOKENS( T) (struct FIFO_S(T) *fifo, int reader_id) {
	// return fifo->fill_count;
	if (fifo->write_ind >= fifo->read_inds[reader_id]) {
		return fifo->write_ind - fifo->read_inds[reader_id];
	} else {
		return fifo->size - fifo->read_inds[reader_id] + fifo->write_ind;
	}
}

DECL int FIFO_HAS_ROOM( T) (struct FIFO_S(T) *fifo, int n) {
	// return (fifo->size - fifo->fill_count) >= n;
	int i;
	for(i = 0; i < fifo->readers_nb; i++) {
		if (fifo->read_inds[i] > fifo->write_ind) {
			if((fifo->read_inds[i] - fifo->write_ind) <= n) {
				return 0;
			}
		} else {
			if((fifo->size - fifo->write_ind + fifo->read_inds[i]) <= n) {
				return 0;
			}
		}
	}
	return 1;
}

DECL int FIFO_GET_ROOM( T)(struct FIFO_S(T) *fifo) {
	// return fifo->size - fifo->fill_count;
	int i, tmp, free_room = fifo->size;
	for(i = 0; i < fifo->readers_nb; i++) {
		if (fifo->read_inds[i] = fifo->write_ind) {
			return 0;
		}
		else if (fifo->read_inds[i] > fifo->write_ind) {
			tmp = fifo->read_inds[i] - fifo->write_ind;
		} else {
			tmp = fifo->size - fifo->write_ind + fifo->read_inds[i];
		}
		free_room = tmp < free_room ? tmp : free_room;
	}
	return free_room;
}

DECL T *FIFO_PEEK( T)(struct FIFO_S(T) *fifo, T *buffer, int reader_id, int n) {
	if (fifo->read_inds[reader_id] + n <= fifo->size) {
		return &fifo->contents[fifo->read_inds[reader_id]];
	} else {
		int num_end = fifo->size - fifo->read_inds[reader_id];
		int num_beginning = n - num_end;

		// Copy the end of the fifo
		if (num_end != 0) {
			memcpy(buffer, &fifo->contents[fifo->read_inds[reader_id]], num_end * sizeof(T));
		}

		// Copy the rest of the data at the beginning of the FIFO
		if (num_beginning != 0) {
			memcpy(&buffer[num_end], fifo->contents, num_beginning * sizeof(T));
		}

		// returns supplied buffer
		return buffer;
	}
}

DECL void FIFO_READ_COPY(T)(struct FIFO_S(T) *fifo, T *buffer, int reader_id, int n) {
	if (fifo->read_inds[reader_id] + n <= fifo->size) {
		memcpy(buffer, &fifo->contents[fifo->read_inds[reader_id]], n * sizeof(T));
	} else {
		int num_end = fifo->size - fifo->read_inds[reader_id];
		int num_beginning = n - num_end;

		// Copy the end of the fifo
		if (num_end != 0) {
			memcpy(buffer, &fifo->contents[fifo->read_inds[reader_id]], num_end * sizeof(T));
		}

		// Copy the rest of the data at the beginning of the FIFO
		if (num_beginning != 0) {
			memcpy(&buffer[num_end], fifo->contents, num_beginning * sizeof(T));
		}
	}
}

DECL T *FIFO_READ( T) (struct FIFO_S(T) *fifo, T *buffer, int reader_id, int n) {
	return FIFO_PEEK(T)(fifo, buffer, reader_id, n);
}

DECL void FIFO_READ_END( T) (struct FIFO_S(T) *fifo, int reader_id, int n) {
	int read_pos = fifo->read_inds[reader_id] + n;
	if (read_pos < fifo->size) {
		fifo->read_inds[reader_id] += n;
	} else if (read_pos == fifo->size) {
		fifo->read_inds[reader_id] = 0;
	} else {
		fifo->read_inds[reader_id] = read_pos - fifo->size;
	}

	// fifo->fill_count -= n;
}

DECL T *FIFO_WRITE( T) (struct FIFO_S(T) *fifo, T *buffer, int n) {
	if (fifo->write_ind + n <= fifo->size) {
		return &fifo->contents[fifo->write_ind];
	} else {
		// returns supplied buffer, we will copy its contents in write_end
		return buffer;
	}
}

DECL void FIFO_WRITE_END( T) (struct FIFO_S(T) *fifo, T *buffer, int n) {
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

DECL void FIFO_CLEAR( T) (struct FIFO_S(T) *fifo) {
	int i;
	fifo->write_ind = 0;
	for(i = 0; i < fifo->readers_nb; i++) {
		fifo->read_inds[i] = 0;
	}
}

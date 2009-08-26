#ifndef FIFO_H
#define FIFO_H

#include <string.h>

struct fifo_s {
	int size;
	int *contents;
	int read_ptr;
	int peek_ptr;
	int write_ptr;
};

static int *readFifo(struct fifo_s *fifo, int n) {
	int ptr = fifo->read_ptr;
	fifo->read_ptr += n;
	return &fifo->contents[ptr];
}

static int hasRoom(struct fifo_s *fifo, int n) {
	int num_free = fifo->size - fifo->write_ptr;
	int res = (num_free >= n);
	if (!res) {
		int num_tokens = fifo->write_ptr - fifo->read_ptr;
		if (fifo->read_ptr >= num_tokens + n) {
			memcpy(fifo->contents, &fifo->contents[fifo->read_ptr], num_tokens);
			fifo->read_ptr = 0;
			fifo->peek_ptr = 0;
			fifo->write_ptr = num_tokens;

			return 1;
		}
	}
	return res;
}

static int hasTokens(struct fifo_s *fifo, int n) {
	int num_tokens = fifo->write_ptr - fifo->read_ptr;
	int res = (num_tokens >= n);
	if (!res) {
		memcpy(fifo->contents, &fifo->contents[fifo->read_ptr], num_tokens);
		fifo->read_ptr = 0;
		fifo->peek_ptr = 0;
		fifo->write_ptr = num_tokens;
	}
	return res;
}

static int *writeFifo(struct fifo_s *fifo, int n) {
	int ptr = fifo->write_ptr;
	fifo->write_ptr += n;
	return &fifo->contents[ptr];
}

#endif

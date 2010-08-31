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

#include "orcc.h"
#include "orcc_fifo.h"

extern struct fifo_i8_s *B;
extern struct fifo_i16_s *WIDTH;
extern struct fifo_i16_s *HEIGHT;


static int init = 0;
static void set_video(int width, int height);
static void set_init();
void write_mb(unsigned char tokens[384]);


void scheduler() {
	int i = 0;

	while (1) {
		if (fifo_i16_has_tokens(WIDTH, 1) && fifo_i16_has_tokens(HEIGHT, 1)) {
			short *ptr, width, height;

			ptr = fifo_i16_read(WIDTH, 1);
			width = ptr[0] * 16;
			fifo_i16_read_end(WIDTH, 1);

			ptr = fifo_i16_read(HEIGHT, 1);
			height = ptr[0] * 16;
			fifo_i16_read_end(HEIGHT, 1);

			set_video(width, height);
			i++;
		}

		if (fifo_i8_has_tokens(B, 384)) {
			if (!init) {
				set_init();
				init = 1;
			}

			write_mb(fifo_i8_read(B, 384));
			fifo_i8_read_end(B, 384);
			i++;
		} else {
			break;
		}
	}

}

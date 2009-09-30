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

#include "fifo.h"
#include "orcc_util.h"

// from APR
/* Ignore Microsoft's interpretation of secure development
 * and the POSIX string handling API
 */
#if defined(_MSC_VER) && _MSC_VER >= 1400
#ifndef _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_DEPRECATE
#endif
#pragma warning(disable: 4996)
#endif


extern struct fifo_s *Compare_B;
extern struct fifo_s *Compare_WIDTH;
extern struct fifo_s *Compare_HEIGHT;

#define MAX_WIDTH 704
#define MAX_HEIGHT 576


static int m_x;
static int m_y;
static int m_width;
static int m_height;
static int FrameCounter = 0;
static int NumberOfFrames; 
static unsigned char img_buf_y[MAX_WIDTH * MAX_HEIGHT];
static unsigned char img_buf_u[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char img_buf_v[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char Y[MAX_WIDTH * MAX_HEIGHT];
static unsigned char U[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char V[MAX_WIDTH * MAX_HEIGHT / 4];



void Compare_write_mb(short tokens[384]) {
	int i, j, x, y, cnt, base, idx;

	//printf("display_write_mb (%i, %i)\n", m_x, m_y);

	cnt = 0;
	base = m_y * m_width + m_x;

	for (y = 0; y < 2; y++) {
		for (x = 0; x < 2; x++) {
			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {
					int tok = tokens[cnt];
					cnt++;

					if (tok < 0) {
						tok = 0;
					} else if (tok > 255) {
						tok = 255;
					}

					idx = base + (i + 8 * y) * m_width + (j + 8 * x);
					img_buf_y[idx] = tok;
				}
			}
		}
	}

	base = m_y / 2 * m_width / 2 + m_x / 2;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			cnt++;

			if (tok < 0) {
				tok = 0;
			} else if (tok > 255) {
				tok = 255;
			}

			idx = base + i * m_width / 2 + j;
			img_buf_u[idx] = tok;
		}
	}

	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			cnt++;

			if (tok < 0) {
				tok = 0;
			} else if (tok > 255) {
				tok = 255;
			}

			idx = base + i * m_width / 2 + j;
			img_buf_v[idx] = tok;
		}
	}

	m_x += 16;
	if (m_x == m_width) {
		m_x = 0;
		m_y += 16;
	}

	if (m_y == m_height) {
		m_x = 0;
		m_y = 0;
		printf("Frame number %d \n", FrameCounter);
		Read_YUV(Y, U, V);
		//DiffUcharImage(m_width, m_height, Y, img_buf_y);
		DiffUcharImage(m_width >> 1, m_height >> 1, U, img_buf_u);
		//DiffUcharImage(m_width >> 1, m_height >> 1, V, img_buf_v);
		FrameCounter ++;
		if ( NumberOfFrames == FrameCounter){
			exit(666);
		}
	}
}

static int init = 0;

static void Compare_init(int width, int height) {

	if ( !init){
		m_width = width;
		m_height = height;
		NumberOfFrames = Read_YUV_init_with_input_filename (width, height, yuv_file);
		init = 1;
	}
}


int Compare_scheduler() {
	int res = 1;
	while (res) {
		if (hasTokens(Compare_WIDTH, 1) && hasTokens(Compare_HEIGHT, 1)) {
			short *ptr, width, height;
			ptr = getReadPtr(Compare_WIDTH, 1);
			width = ptr[0] * 16;
			ptr = getReadPtr(Compare_HEIGHT, 1);
			height = ptr[0] * 16;
			Compare_init(width, height);
		}

		if (hasTokens(Compare_B, 384)) {
			Compare_write_mb(getReadPtr(Compare_B, 384));
			res = 1;
		} else {
			res = 0;
		}
	}

	return 0;
}

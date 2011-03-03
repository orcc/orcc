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
#include <sys/stat.h>

#include <stdio.h>
#include <stdlib.h>

#include "orcc.h"
#include "orcc_fifo.h"
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


extern struct fifo_i8_s *Compare_B;
extern struct fifo_i16_s *Compare_WIDTH;
extern struct fifo_i16_s *Compare_HEIGHT;

static unsigned int fifo_Compare_B_id;
static unsigned int fifo_Compare_WIDTH_id;
static unsigned int fifo_Compare_HEIGHT_id;

#define MAX_WIDTH 704
#define MAX_HEIGHT 576


static int m_x;
static int m_y;
static int m_width;
static int m_height;
static int FrameCounter;
static int NumberOfFrames; 
static unsigned char img_buf_y[MAX_WIDTH * MAX_HEIGHT];
static unsigned char img_buf_u[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char img_buf_v[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char Y[MAX_WIDTH * MAX_HEIGHT];
static unsigned char U[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char V[MAX_WIDTH * MAX_HEIGHT / 4];
static FILE *ptfile ;
static int  xsize_int;
static int  ysize_int;
static int  images;

static int Filesize(FILE *f) {
	struct stat st;
	fstat(fileno(f), &st);
	return st.st_size;
}

static void Read_YUV_init(int xsize, int ysize, char * filename) {
	ptfile = fopen(filename, "rb");
	if (ptfile == NULL) {
		printf("Cannot open yuv_file concatenated input file '%s' for reading\n", filename);
		exit(-1);
	}

	if ((xsize_int = xsize) == 0) {
		printf("xsize %d invalid\n", xsize);
		exit(-2);
	}
	if ((ysize_int = ysize) == 0) {
		printf("ysize %d invalid\n", ysize);
		exit(-3);
	}

	NumberOfFrames = Filesize(ptfile) / (xsize * ysize + xsize * ysize / 2);
}

static void Read_YUV(unsigned char *Y, unsigned char *U, unsigned char *V) {
	fread(Y, sizeof(unsigned char), xsize_int * ysize_int, ptfile);
	fread(U, sizeof(unsigned char), xsize_int * ysize_int / 4, ptfile);
	fread(V, sizeof(unsigned char), xsize_int * ysize_int / 4, ptfile);
	images++;

	if (images == NumberOfFrames) {
		fseek(ptfile, 0, SEEK_SET);
		images = 0 ;
	}
}

static void DiffUcharImage(const int x_size, const int y_size, const unsigned char *true_img_uchar, const unsigned char *test_img_uchar, unsigned char SizeMbSide, char Component_Type) {
	int pix_x, pix_y,blk_x,blk_y;
	int error = 0;
	int WidthSzInBlk  = x_size / SizeMbSide;
	int HeightSzInBlk = y_size / SizeMbSide;

	for(blk_y = 0 ; blk_y < HeightSzInBlk ; blk_y++)
	{
		for(blk_x = 0 ; blk_x < WidthSzInBlk ; blk_x++)
		{
			for (pix_y = 0; pix_y < SizeMbSide; pix_y++)
			{
				for (pix_x = 0; pix_x < SizeMbSide; pix_x++)
				{
					int Idx_pix = (blk_y * SizeMbSide + pix_y) * x_size + (blk_x * SizeMbSide + pix_x);

					if (true_img_uchar[Idx_pix] - test_img_uchar[Idx_pix] != 0)
					{
						error++;

						if (error < 100)
						{
							printf("error %3d instead of %3d at position : mb = (%d ; %d) , loc_in_mb = (%d ; %d)\n",
								test_img_uchar[Idx_pix] , true_img_uchar[Idx_pix], blk_x, blk_y, pix_x, pix_y);
						}
					}
				}
			}
		}
	}

	if (error != 0) {
		printf("%d error(s) in %c Component !!!!!!!!!!!!!\n", error, Component_Type);
		//system("pause");
	}
	//  else
	//   printf("OK\n");
}

void Compare_write_mb(unsigned char tokens[384]) {
	int i, j, cnt, base, idx;

	//printf("display_write_mb (%i, %i)\n", m_x, m_y);

	cnt = 0;
	base = m_y * m_width + m_x;

	for (i = 0; i < 16; i++) {
		for (j = 0; j < 16; j++) {
			int tok = tokens[cnt];
			cnt++;

			idx = base + i * m_width + j;
			img_buf_y[idx] = tok;
		}
	}

	base = m_y / 2 * m_width / 2 + m_x / 2;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			cnt++;

			idx = base + i * m_width / 2 + j;
			img_buf_u[idx] = tok;
		}
	}

	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			cnt++;
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
		DiffUcharImage(m_width, m_height, Y, img_buf_y,16, 'Y');
		DiffUcharImage(m_width >> 1, m_height >> 1, U, img_buf_u,8,'U');
		DiffUcharImage(m_width >> 1, m_height >> 1, V, img_buf_v,8, 'V');
		if (NumberOfFrames == FrameCounter){
			printf("\nThat's all folks\n");
			exit(0);
		}
		FrameCounter ++;
	}
}

static void Compare_init(int width, int height) {
	m_width = width;
	m_height = height;
	Read_YUV_init (width, height, yuv_file);
}

static int init;

void Compare_initialize(unsigned int fifo_B_id, unsigned int fifo_WIDTH_id, unsigned int fifo_HEIGHT_id){
	m_x = 0;
	m_y = 0;
	init = 1;
	FrameCounter = 0;
	images = 0;
	fifo_Compare_B_id = fifo_B_id; 
	fifo_Compare_WIDTH_id = fifo_WIDTH_id; 
	fifo_Compare_HEIGHT_id = fifo_HEIGHT_id;
}

void Compare_close(){
	if(ptfile != NULL){
		fclose(ptfile);
	}
}

void Compare_scheduler(struct schedinfo_s *si) {
	int i = 0;

	while (1) {
		if (fifo_i16_has_tokens(Compare_WIDTH, fifo_Compare_WIDTH_id, 1) && fifo_i16_has_tokens(Compare_HEIGHT, fifo_Compare_HEIGHT_id, 1)) {
			short *ptr, width, height;
			i16 Compare_HEIGHT_buf[1], Compare_WIDTH_buf[1];

			ptr = fifo_i16_read(Compare_WIDTH, Compare_WIDTH_buf, fifo_Compare_WIDTH_id, 1);
			width = ptr[0] * 16;
			fifo_i16_read_end(Compare_WIDTH, fifo_Compare_WIDTH_id, 1);

			ptr = fifo_i16_read(Compare_HEIGHT, Compare_HEIGHT_buf, fifo_Compare_HEIGHT_id, 1);
			height = ptr[0] * 16;
			fifo_i16_read_end(Compare_HEIGHT, fifo_Compare_HEIGHT_id, 1);

			if (init == 1) {
				Compare_init(width, height);
				init = 0;
			}

			i++;
		}

		if (fifo_i8_has_tokens(Compare_B, fifo_Compare_B_id, 384) && init == 0) {
			i8 Compare_B_buf[384];
			Compare_write_mb(fifo_i8_read(Compare_B, Compare_B_buf, fifo_Compare_B_id, 384));
			fifo_i8_read_end(Compare_B, fifo_Compare_B_id, 384);
			i++;
		} else {
			break;
		}
	}

	si->num_firings = i;
	si->reason = starved;
	si->ports = 0x07; // FIFOs connected to first three input ports are empty
}

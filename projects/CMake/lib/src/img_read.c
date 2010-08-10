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
#include <stdio.h>
#include <stdlib.h>
#include "SDL.h"
#include "SDL_image.h"

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

extern struct fifo_i16_s *img_read_WIDTH;
extern struct fifo_i16_s *img_read_HEIGHT;
extern struct fifo_i8_s *img_read_RED;
extern struct fifo_i8_s *img_read_GREEN;
extern struct fifo_i8_s *img_read_BLUE;

static SDL_Surface *image;
static SDL_PixelFormat *format;
static int count;

// Called before any *_scheduler function.
void img_read_initialize() {
	unsigned short *ptr;

	if (input_file == NULL) {
		print_usage();
		fprintf(stderr, "No input file given!\n");
		wait_for_key();
		exit(1);
	}

	image = IMG_Load(input_file);
	if (!image) {
		printf("IMG_Load: %s\n", IMG_GetError());
		wait_for_key();
		exit(1);
	}

	format = image->format;
	count = image->w * image->h;

	ptr = fifo_i16_write(img_read_WIDTH, 1);
	ptr[0] = image->w;
	fifo_i16_write_end(img_read_WIDTH, 1);

	ptr = fifo_i16_write(img_read_HEIGHT, 1);
	ptr[0] = image->h;
	fifo_i16_write_end(img_read_HEIGHT, 1);
}

static int idx_pixel = 0;

static void write_pixels(struct schedinfo_s *si) {
	int ports = 0;

	int num_red = fifo_i8_get_room(img_read_RED);
	int num_green = fifo_i8_get_room(img_read_GREEN);
	int num_blue = fifo_i8_get_room(img_read_BLUE);

	int num_colors = min(min(min(num_red, num_green), num_blue), count - idx_pixel);

	unsigned char *ptr_red = fifo_i8_write(img_read_RED, num_colors);
	unsigned char *ptr_green = fifo_i8_write(img_read_GREEN, num_colors);
	unsigned char *ptr_blue = fifo_i8_write(img_read_BLUE, num_colors);

	int i;
	for (i = 0; i < num_colors; i++) {
		int pixel = * (int *) & ((char *)image->pixels)[idx_pixel * image->format->BytesPerPixel];

		unsigned char red = (pixel & format->Rmask) >> format->Rshift;
		unsigned char green = (pixel & format->Gmask) >> format->Gshift;
		unsigned char blue = (pixel & format->Bmask) >> format->Bshift;

		ptr_red[i] = red;
		ptr_green[i] = green;
		ptr_blue[i] = blue;

		idx_pixel++;
	}

	fifo_i8_write_end(img_read_RED, num_colors);
	fifo_i8_write_end(img_read_GREEN, num_colors);
	fifo_i8_write_end(img_read_BLUE, num_colors);

	if (num_red <= num_colors) {
		ports |= 0x01;
	}
	if (num_green <= num_colors) {
		ports |= 0x02;
	}
	if (num_blue <= num_colors) {
		ports |= 0x04;
	}

	si->num_firings = i;
	si->reason = full;
	si->ports = ports;
}

void img_read_scheduler(struct schedinfo_s *si) {
	if (idx_pixel < count) {
		write_pixels(si);
	} else {
		si->num_firings = 0;
		si->reason = full;
		si->ports = 0x07;
	}
}

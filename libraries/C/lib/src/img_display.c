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
#include "SDL.h"
#include "SDL_image.h"

#include "orcc_types.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

extern struct fifo_i16_s *img_display_WIDTH;
extern struct fifo_i16_s *img_display_HEIGHT;
extern struct fifo_i8_s *img_display_RED;
extern struct fifo_i8_s *img_display_GREEN;
extern struct fifo_i8_s *img_display_BLUE;

static SDL_Surface *m_screen;

static unsigned short m_width;
static unsigned short m_height;
static SDL_Surface *m_screen;
static SDL_Surface *m_image;
static int m_count;

void img_display_initialize() {
	m_count = m_width * m_height;

	/* First, initialize SDL's video subsystem. */
	if(SDL_Init(SDL_INIT_VIDEO) < 0) {
		/* Failed, exit. */
		fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		wait_for_key();
		exit(1);
	}

	m_screen = SDL_SetVideoMode(m_width, m_height, 24, 0);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", m_width, m_height,
			SDL_GetError());
		wait_for_key();
		exit(1);
	}

	SDL_WM_SetCaption("img_display", NULL);

	atexit(SDL_Quit);

	m_image = SDL_CreateRGBSurface(SDL_SWSURFACE, m_width, m_height, m_screen->format->BitsPerPixel,
		m_screen->format->Rmask, m_screen->format->Gmask, m_screen->format->Bmask, m_screen->format->Amask);
}

static int idx_pixel = 0;

static void read_pixel() {
	unsigned char *ptr, red, green, blue;
	i8 img_display_RED_buf[1], img_display_GREEN_buf[1], img_display_BLUE_buf[1];
	int pixel;

	SDL_PixelFormat *format = m_image->format;

	if (SDL_MUSTLOCK(m_image)) {
		SDL_LockSurface(m_image);
	}

	ptr = fifo_i8_read(img_display_RED, img_display_RED_buf, 0, 1);
	red = ptr[0];
	fifo_i8_read_end(img_display_RED, 0, 1);

	ptr = fifo_i8_read(img_display_GREEN, img_display_GREEN_buf, 0, 1);
	green = ptr[0];
	fifo_i8_read_end(img_display_GREEN, 0, 1);

	ptr = fifo_i8_read(img_display_BLUE, img_display_BLUE_buf, 0, 1);
	blue = ptr[0];
	fifo_i8_read_end(img_display_BLUE, 0, 1);

	pixel = (red << format->Rshift) & format->Rmask
		| (green << format->Gshift) & format->Gmask
		| (blue << format->Bshift) & format->Bmask;

	* (int *) &((char *)m_image->pixels)[idx_pixel * format->BytesPerPixel] = pixel;
	idx_pixel++;

	if (SDL_MUSTLOCK(m_image)) {
		SDL_UnlockSurface(m_image);
	}
}

void img_display_scheduler(struct schedinfo_s *si) {
	SDL_Event event;
	int ports = 0x1f; // FIFOs connected to first five input ports are empty

	int i = 0;
	if (fifo_i16_has_tokens(img_display_WIDTH, 0, 1) && fifo_i16_has_tokens(img_display_HEIGHT, 0, 1)) {
		i16 img_display_HEIGHT_buf[1], img_display_WIDTH_buf[1];
		short *ptr = fifo_i16_read(img_display_HEIGHT, img_display_HEIGHT_buf, 0, 1);
		m_height = ptr[0];
		fifo_i16_read_end(img_display_HEIGHT, 0, 1);

		ptr = fifo_i16_read(img_display_WIDTH, img_display_WIDTH_buf, 0, 1);
		m_width = ptr[0];
		fifo_i16_read_end(img_display_WIDTH, 0, 1);

		img_display_initialize();
	}

	while (idx_pixel < m_count) {
		if (fifo_i8_has_tokens(img_display_RED, 0, 1) && fifo_i8_has_tokens(img_display_GREEN, 0, 1) && fifo_i8_has_tokens(img_display_BLUE, 0, 1)) {
			read_pixel();
			i++;
		} else {
			ports = 0x07; // FIFOs connected to first three input ports are empty
			break;
		}
	}

	// Draws the image on the screen
	if (m_screen != NULL) {
		SDL_BlitSurface(m_image, NULL, m_screen, NULL);
		SDL_Flip(m_screen);
	}

	while (SDL_PollEvent(&event)) {
		switch (event.type) {
			case SDL_QUIT:
				exit(0);
				break;
			default:
				break;
		}
	}

	si->num_firings = i;
	si->reason = starved;
	si->ports = ports;
}

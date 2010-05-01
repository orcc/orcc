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

#include "fifo.h"

extern struct fifo_short_s *img_display_width;
extern struct fifo_short_s *img_display_height;
extern struct fifo_char_s *img_display_red;
extern struct fifo_char_s *img_display_green;
extern struct fifo_char_s *img_display_blue;

static SDL_Surface *m_screen;

static int init = 0;

static void press_a_key(int code) {
	char buf[2];
	printf("Press enter to continue\n");
	fgets(buf, 2, stdin);
	exit(code);
}

static unsigned short m_width;
static unsigned short m_height;
static SDL_Surface *m_screen;
static SDL_Surface *m_image;
static int m_count;

static void img_display_init() {
	m_count = m_width * m_height;

	/* First, initialize SDL's video subsystem. */
	if(SDL_Init(SDL_INIT_VIDEO) < 0) {
		/* Failed, exit. */
		fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	m_screen = SDL_SetVideoMode(m_width, m_height, 24, SDL_HWSURFACE | SDL_DOUBLEBUF);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", m_width, m_height,
			SDL_GetError());
		press_a_key(-1);
	}

	SDL_WM_SetCaption("img_display", NULL);

	atexit(SDL_Quit);

	m_image = SDL_CreateRGBSurface(SDL_SWSURFACE, m_width, m_height, m_screen->format->BitsPerPixel,
		m_screen->format->Rmask, m_screen->format->Gmask, m_screen->format->Bmask, m_screen->format->Amask);

	init = 1;
}

enum states {
	s_width,
	s_height,
	s_contents,
	s_done
};

enum states _FSM_state = s_width;

static int width_scheduler() {
	int res;
	unsigned short *ptr;

	if (fifo_short_has_tokens(img_display_width, 1)) {
		ptr = fifo_short_read(img_display_width, 1);
		m_width = ptr[0];
		fifo_short_read_end(img_display_width, 1);
		_FSM_state = s_height;
		res = 1;
	} else {
		res = 0;
	}

	return res;
}

static int height_scheduler() {
	int res;
	unsigned short *ptr;

	if (fifo_short_has_tokens(img_display_height, 1)) {
		ptr = fifo_short_read(img_display_height, 1);
		m_height = ptr[0];
		fifo_short_read_end(img_display_height, 1);
		_FSM_state = s_contents;

		img_display_init();

		res = 1;
	} else {
		res = 0;
	}

	return res;
}

static int contents_scheduler() {
	int res;
	unsigned char *ptr, red, green, blue;
	int pixel;
	static int i = 0;

	while (i < m_count && fifo_char_has_tokens(img_display_red, 1) && fifo_char_has_tokens(img_display_green, 1) && fifo_char_has_tokens(img_display_blue, 1)) {
		SDL_PixelFormat *format = m_image->format;

		ptr = fifo_char_read(img_display_red, 1);
		red = ptr[0];
		fifo_char_read_end(img_display_red, 1);

		ptr = fifo_char_read(img_display_green, 1);
		green = ptr[0];
		fifo_char_read_end(img_display_green, 1);

		ptr = fifo_char_read(img_display_blue, 1);
		blue = ptr[0];
		fifo_char_read_end(img_display_blue, 1);

		pixel = (red << format->Rshift) & format->Rmask
			| (green << format->Gshift) & format->Gmask
			| (blue << format->Bshift) & format->Bmask;

		* (int *) &((char *)m_image->pixels)[i * format->BytesPerPixel] = pixel;
		i++;
	}

	// Draws the image on the screen:
	SDL_BlitSurface(m_image, NULL, m_screen, NULL);
	SDL_Flip(m_screen);

	if (i == m_count) {
		_FSM_state = s_done;
		res = 1;
	} else {
		res = 0;
	}

	return res;
}

int img_display_scheduler() {
	int res;

	do {
		switch (_FSM_state) {
			case s_width:
				res = width_scheduler();
				break;
			case s_height:
				res = height_scheduler();
				break;
			case s_contents:
				res = contents_scheduler();
				break;
			case s_done:
				res = 0;
				break;
			default:
				res = 0;
				break;
		}
	} while (res);

	return 0;
}

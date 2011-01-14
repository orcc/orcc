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
#include <sys/types.h>
#include <sys/stat.h>
#include "SDL.h"

#include "llvm/Support/CommandLine.h"

static SDL_Surface *m_screen;
static SDL_Overlay *m_overlay = NULL;

#define MAX_WIDTH 720
#define MAX_HEIGHT 576
/*
#if defined(__LP64__) || defined(_LP64)
#define fstat(op1, op2) fstat64(op1, op2)
#define teststat stat64
#else
#define teststat stat
#endif
*/
static int m_x;
static int m_y;
static int m_width;
static int m_height;

static unsigned char img_buf_y[MAX_WIDTH * MAX_HEIGHT];
static unsigned char img_buf_u[MAX_WIDTH * MAX_HEIGHT / 4];
static unsigned char img_buf_v[MAX_WIDTH * MAX_HEIGHT / 4];

extern llvm::cl::opt<int> StopAt;

//Exit function of the decoder
void (*exit_decoder)(int);

static void press_a_key(int code) {
	char buf[2];
	printf("Press enter to continue\n");
	fgets(buf, 2, stdin);
	exit_decoder(code);
}

static Uint32 start_time;
static int num_images_start;
static int num_images_end;
bool verboseDisplay;

void print_fps_avg(void) {
	Uint32 t = SDL_GetTicks();

	printf("%i images in %f seconds: %f FPS\n",
		num_images_end, (float) t / 1000.0f,
		1000.0f * (float)num_images_end / (float)t);
}

static Uint32 t;

void emptyFunc(){

}

void initT(){
	start_time = SDL_GetTicks();
	t = start_time;
}

void display_show_image(void) {
	SDL_Rect rect = { 0, 0, m_width, m_height };

	int t2;
	SDL_Event event;

	///////////////////////////////////////////////////////////////////////////////////////////////
	if (SDL_LockYUVOverlay(m_overlay) < 0) {
		fprintf(stderr, "Can't lock screen: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	memcpy(m_overlay->pixels[0], img_buf_y, m_width * m_height );
	memcpy(m_overlay->pixels[1], img_buf_u, m_width * m_height / 4 );
	memcpy(m_overlay->pixels[2], img_buf_v, m_width * m_height / 4 );

	SDL_UnlockYUVOverlay(m_overlay);
	SDL_DisplayYUVOverlay(m_overlay, &rect);

	///////////////////////////////////////////////////////////////////////////////////////////////
	num_images_end++;
	t2 = SDL_GetTicks();
	if ((t2 - t > 3000)&&(verboseDisplay)) {
		printf("%f images/sec\n",
			1000.0f * (float)(num_images_end - num_images_start) / (float)(t2 - t));

		t = t2;
		num_images_start = num_images_end;
	}

	if (num_images_end == StopAt){
		exit(1);
	}

	/* Grab all the events off the queue. */
	while (SDL_PollEvent(&event)) {
		switch (event.type) {
			case SDL_QUIT:
				m_x = 0;
				m_y = 0;
				m_width = 0;
				m_height = 0;
				pthread_exit(NULL);
				break;
			default:
				break;
		}
	}
}

void display_write_mb(unsigned char tokens[384]) {
	int i, j, cnt, base;

	//printf("display_write_mb (%i, %i)\n", m_x, m_y);

	cnt = 0;
	base = m_y * m_width + m_x;

	for (i = 0; i < 16; i++) {
		for (j = 0; j < 16; j++) {
			int tok = tokens[cnt];
			int idx = base + i * m_width + j;
			cnt++;
			img_buf_y[idx] = tok;
		}
	}

	base = m_y / 2 * m_width / 2 + m_x / 2;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			int idx = base + i * m_width / 2 + j;
			cnt++;
			img_buf_u[idx] = tok;
		}
	}

	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			int idx = base + i * m_width / 2 + j;
			cnt++;
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
		if (m_overlay != NULL){
			display_show_image();
		}else{
			num_images_end++;
			int t2 = SDL_GetTicks();
			if ((t2 - t > 3000)&&(verboseDisplay)) {
				printf("%f images/sec\n",
					1000.0f * (float)(num_images_end - num_images_start) / (float)(t2 - t));

				t = t2;
				num_images_start = num_images_end;
			}
		}
	}
}

static void display_init() {
	// First, initialize SDL's video subsystem.
	if (SDL_Init( SDL_INIT_VIDEO ) < 0) {
		fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	SDL_WM_SetCaption("display", NULL);

	start_time = SDL_GetTicks();
	t = start_time;
	
	atexit(print_fps_avg);
}

static void display_set_video(int width, int height) {
	if (width == m_width && height == m_height) {
		// video mode is already good
		return;
	}

	m_width = width;
	m_height = height;

	if (verboseDisplay){
		printf("set display to %ix%i\n", width, height);
	}

	m_screen = SDL_SetVideoMode(m_width, m_height, 24, SDL_HWSURFACE | SDL_DOUBLEBUF);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", m_width, m_height,
			SDL_GetError());
		press_a_key(-1);
	}

	if (m_overlay != NULL) {
		SDL_FreeYUVOverlay(m_overlay);
	}

	m_overlay = SDL_CreateYUVOverlay(m_width, m_height, SDL_IYUV_OVERLAY, m_screen);
	if (m_overlay == NULL) {
		fprintf(stderr, "Couldn't create overlay: %s\n", SDL_GetError());
		press_a_key(-1);
	}
}
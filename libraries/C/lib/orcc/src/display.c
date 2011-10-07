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
#include "orcc_util.h"

static SDL_Surface *m_screen;
static SDL_Overlay *m_overlay;

static int init = 0;

static int x, y , onclick = 0;
static SDL_Rect rect;


static void press_a_key(int code) {
	char buf[2];
	char *ptrBuff = NULL;

	printf("Press a key to continue\n");
	ptrBuff = fgets(buf, 2, stdin);
	if (ptrBuff == NULL) {
		fprintf(stderr, "error when using fgets\n");
	}
	exit(code);
}


char displayYUV_getFlags(){
	return display_flags + DISPLAY_READY;
}


static void displayYUV_setSize(int width, int height) {
	printf("set display to %ix%i\n", width, height);

	m_screen = SDL_SetVideoMode(width, height, 32, SDL_HWSURFACE);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", width,
				height, SDL_GetError());
		press_a_key(-1);
	}

	if (m_overlay != NULL) {
		SDL_FreeYUVOverlay(m_overlay);
	}

	m_overlay = SDL_CreateYUVOverlay(width, height, SDL_YV12_OVERLAY, m_screen);
	if (m_overlay == NULL) {
		fprintf(stderr, "Couldn't create overlay: %s\n", SDL_GetError());
		press_a_key(-1);
	}
}

void displayYUV_displayPicture(unsigned char *pictureBufferY,
		unsigned char *pictureBufferU, unsigned char *pictureBufferV,
		unsigned int   pictureWidth, unsigned int pictureHeight) {
	static unsigned int lastWidth = 0;
	static unsigned int lastHeight = 0;
	SDL_Event event;
	//SDL_Rect rect = { 0, 0, pictureWidth, pictureHeight };
	rect.x = 0;
	rect.y = 0;
	rect.w = pictureWidth;
	rect.h = pictureHeight;

	

	if ((pictureHeight != lastHeight) || (pictureWidth != lastWidth)) {
		displayYUV_setSize(pictureWidth, pictureHeight);
		lastHeight = pictureHeight;
		lastWidth = pictureWidth;
	}

	if (SDL_LockYUVOverlay(m_overlay) < 0) {
		fprintf(stderr, "Can't lock screen: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	memcpy(m_overlay->pixels[0], pictureBufferY, pictureWidth * pictureHeight);
	memcpy(m_overlay->pixels[1], pictureBufferV, pictureWidth * pictureHeight / 4);
	memcpy(m_overlay->pixels[2], pictureBufferU, pictureWidth * pictureHeight / 4);

	SDL_UnlockYUVOverlay(m_overlay);
	SDL_DisplayYUVOverlay(m_overlay, &rect);

	/* Grab all the events off the queue. */
	while (SDL_PollEvent(&event)) {
		switch (event.type) {
		case SDL_QUIT:
			exit(0);
			break;
		default:
			break;
		}
	}
}

void displayYUV_init() {
	if (!init) {
		init = 1;

		// First, initialize SDL's video subsystem.
		if (SDL_Init(SDL_INIT_VIDEO) < 0) {
			fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
			press_a_key(-1);
		}

		SDL_WM_SetCaption("display", NULL);
		atexit(SDL_Quit);
	}
}

static void displayYUV_setSize2(int winWidth, int winHeight, int pictureWidth, int pictureHeight) {
	printf("set display to %ix%i\n", winWidth, winHeight);

	m_screen = SDL_SetVideoMode(winWidth, winHeight, 32, SDL_HWSURFACE);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", winWidth,
				winHeight, SDL_GetError());
		press_a_key(-1);
	}

	if (m_overlay != NULL) {
		SDL_FreeYUVOverlay(m_overlay);
	}

	m_overlay = SDL_CreateYUVOverlay(pictureWidth, pictureHeight, SDL_YV12_OVERLAY, m_screen);
	if (m_overlay == NULL) {
		fprintf(stderr, "Couldn't create overlay: %s\n", SDL_GetError());
		press_a_key(-1);
	}
}

void displayYUV_initImage(int winWidth, int winHeight, int pictureWidth, int pictureHeight) {
  if (!init) {
    init = 1;
    // First, initialize SDL's video subsystem.
    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
      fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
      press_a_key(-1);
    }  
    SDL_WM_SetCaption("display", NULL);
    atexit(SDL_Quit);
    displayYUV_setSize2(winWidth, winHeight, pictureWidth, pictureHeight);
    x = 0;
    y = 0;
    onclick = 0;
  }
}


void displayYUV_displayPicture2(unsigned char *pictureBufferY,
		unsigned char *pictureBufferU, unsigned char *pictureBufferV,
		unsigned int   pictureWidth, unsigned int pictureHeight) {
  rect.x = x;
  rect.y = y;
  rect.w = pictureWidth;
  rect.h = pictureHeight;
  
  if (SDL_LockYUVOverlay(m_overlay) < 0) {
    fprintf(stderr, "Can't lock screen: %s\n", SDL_GetError());
    press_a_key(-1);
  }
  
  memcpy(m_overlay->pixels[0], pictureBufferY, pictureWidth * pictureHeight);
  memcpy(m_overlay->pixels[1], pictureBufferV, pictureWidth * pictureHeight / 4);
  memcpy(m_overlay->pixels[2], pictureBufferU, pictureWidth * pictureHeight / 4);
  
  SDL_UnlockYUVOverlay(m_overlay);
  SDL_DisplayYUVOverlay(m_overlay, &rect);
}

void displayYUV_getEvent() {
  SDL_Event event;
  /* Grab all the events off the queue. */
  while (SDL_PollEvent(&event)) {
    switch (event.type) {
    case SDL_QUIT:
      SDL_FreeSurface(m_screen);
      SDL_Quit();
      exit(0);
      break;
    case SDL_MOUSEBUTTONDOWN : onclick = 1; break;
    case SDL_MOUSEBUTTONUP   : onclick = 0; break;
    case SDL_MOUSEMOTION     :
      if (onclick == 1) {
	x += event.motion.xrel;
	y += event.motion.yrel;
	rect.x = x ; rect.y = y;
	SDL_FillRect(m_screen,NULL,SDL_MapRGB(m_screen->format,0,0,0));
	SDL_Flip(m_screen);
	SDL_DisplayYUVOverlay(m_overlay, &rect);
      }
      break;
    default:
      break;
    }
  }
}



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
#include <iostream>
#include <stdlib.h>

#ifndef NO_DISPLAY
#include <SDL/SDL.h>
static SDL_Surface *m_screen;
static SDL_Overlay *m_overlay;
#else
#include "timer.h"
static Timer timer;
#endif

#ifndef NO_DISPLAY
static void press_a_key(int code) {
	char buf[2];
	char *ptrBuff = NULL;

	printf("Press a key to continue\n");
	ptrBuff=fgets(buf, 2, stdin);
	if(ptrBuff == NULL) {
		fprintf(stderr,"error when using fgets\n");
	}
	exit(code);
}
#endif

static unsigned int startTime;
static unsigned int relativeStartTime;
static int lastNumPic;
static int numPicturesDecoded;


void displayYUV_setSize(int width, int height)
{
#ifndef NO_DISPLAY
	//std::cout << "set display to " << width << " x " << height << std::endl;
	m_screen = SDL_SetVideoMode(width, height, 0, SDL_HWSURFACE);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set video mode!\n");
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
#endif
}

void displayYUV_displayPicture(unsigned char pictureBufferY[], unsigned char pictureBufferU[], unsigned char pictureBufferV[], short pictureWidth, short pictureHeight) 
{
#ifndef NO_DISPLAY
	static unsigned short lastWidth = 0;
	static unsigned short lastHeight = 0;

	SDL_Rect rect = { 0, 0, pictureWidth, pictureHeight };

	SDL_Event event;

	if((pictureHeight != lastHeight) || (pictureWidth != lastWidth)) {
		displayYUV_setSize(pictureWidth, pictureHeight);
		lastHeight = pictureHeight;
		lastWidth  = pictureWidth;
	}
	if (SDL_LockYUVOverlay(m_overlay) < 0) {
		fprintf(stderr, "Can't lock screen: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	memcpy(m_overlay->pixels[0], pictureBufferY, pictureWidth * pictureHeight );
	memcpy(m_overlay->pixels[1], pictureBufferV, pictureWidth * pictureHeight / 4 );
	memcpy(m_overlay->pixels[2], pictureBufferU, pictureWidth * pictureHeight / 4 );

	SDL_UnlockYUVOverlay(m_overlay);
	SDL_DisplayYUVOverlay(m_overlay, &rect);

	/* Grab all the events off the queue. */
	while (SDL_PollEvent(&event)) {
		switch (event.type) {
		case SDL_KEYDOWN:
		case SDL_QUIT:
			exit(0);
			break;
		default:
			break;
		}
	}
#endif
}

void displayYUV_init()
{
#ifndef NO_DISPLAY
	// First, initialize SDL's video subsystem.
	if (SDL_Init( SDL_INIT_VIDEO ) < 0) {
		fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	SDL_WM_SetCaption("display", NULL);

	atexit(SDL_Quit);
#endif
}

unsigned char displayYUV_getFlags()
{
	return 3;
}

void compareYUV_compareComponent(const int x_size, const int y_size, 
	const unsigned char *true_img_uchar, const unsigned char *test_img_uchar,
	unsigned char SizeMbSide, char Component_Type) 
{
}

void compareYUV_init()
{
}

void compareYUV_readComponent(unsigned char **Component, unsigned short width, unsigned short height, char sizeChanged)
{
}

void compareYUV_comparePicture(unsigned char pictureBufferY[], unsigned char pictureBufferU[],
	unsigned char pictureBufferV[], short pictureWidth,
	short pictureHeight)
{
}


static void print_fps_avg(void) {

#ifndef NO_DISPLAY
	unsigned int endTime = SDL_GetTicks();
#else
	unsigned int endTime = timer.getMilliseconds();
#endif
	printf("%i images in %f seconds: %f FPS\n", numPicturesDecoded,
		(float) (endTime - startTime)/ 1000.0f,
		1000.0f * (float) numPicturesDecoded / (float) (endTime -startTime));
}

void fpsPrintInit() {
#ifndef NO_DISPLAY
	startTime = SDL_GetTicks();
#else
	timer.reset();
	startTime = timer.getMilliseconds();
#endif
	relativeStartTime = startTime;
	numPicturesDecoded = 0;
	lastNumPic = 0;
	atexit(print_fps_avg);
}

void fpsPrintNewPicDecoded(void) {
	unsigned int endTime;
	numPicturesDecoded++;
#ifndef NO_DISPLAY
	endTime = SDL_GetTicks();
#else
	endTime = timer.getMilliseconds();
#endif
	if (endTime - relativeStartTime > 5000) {
		printf("%f images/sec\n", 1000.0f * (float) (numPicturesDecoded - lastNumPic) / (float) (endTime - relativeStartTime));
		relativeStartTime = endTime;
		lastNumPic = numPicturesDecoded;
	}
}

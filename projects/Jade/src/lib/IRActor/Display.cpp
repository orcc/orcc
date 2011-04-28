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

/**
@brief Implementation of class Display
@author Jerome Gorin
@file Display.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include <iostream>
#include "SDL.h"

#include <pthread.h>
#include "Jade/Actor/Display.h"

#include "llvm/Support/CommandLine.h"
//------------------------------

using namespace std;

//Initialize static member of display
SDL_Surface* Display::m_screen = NULL;
SDL_Overlay* Display::m_overlay = NULL;
int Display::m_width = 0;
int Display::m_height = 0;
bool Display::init = false;
int Display::boundedDisplays = 0;
pthread_mutex_t Display::mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t Display::cond_mutex = PTHREAD_COND_INITIALIZER;

void Display::press_a_key(int code) {
	char buf[2];
	printf("Press enter to continue\n");
	fgets(buf, 2, stdin);
	exit(code);
}

Display::Display(int id, bool printFps){
	this->id = id;
	this->outputFps = outputFps;
	this->frameDecoded = 0;
	width = 0;
	height = 0;
	x = 0;
	y = 0;
	init = false;
	boundedDisplays++;
	bench = fopen("bench.txt", "w");
	if (!init){
		display_init();
	}
}

Display::~Display(){
	boundedDisplays--;
	fclose(bench);
	if (boundedDisplays == 0){
		SDL_Quit();
	}
}

void Display::forceStop(pthread_t* thread){
	SDL_Event sdlEvent;
	sdlEvent.type = SDL_QUIT;
	SDL_PushEvent(&sdlEvent);
	pthread_join (*thread, NULL);
	SDL_Quit();
}

void Display::waitForFirstFrame(){
	clock_t start = clock ();
	
	//Wait for the first image to be display
	pthread_mutex_lock( &mutex );
	pthread_cond_wait( &cond_mutex, &mutex );
	pthread_mutex_unlock( &mutex );

	cout << "---> First image arrived after " << (clock () - start) * 1000 / CLOCKS_PER_SEC <<" ms after decoder start.\n";
}

void Display::sendFirstFrameEvent(){
	pthread_mutex_lock( &mutex );
	pthread_cond_signal( &cond_mutex);
	pthread_mutex_unlock( &mutex );
}


void Display::display_write_mb(unsigned char tokens[384]) {
	int i, j, cnt, base;

	if (t == 0){
		t = SDL_GetTicks();
	}
	cnt = 0;
	base = y * m_width + x;

	for (i = 0; i < 16; i++) {
		for (j = 0; j < 16; j++) {
			int tok = tokens[cnt];
			int idx = base + i * m_width + j;
			cnt++;
			img_buf_y[idx] = tok;
		}
	}

	base = y / 2 * m_width / 2 + x / 2;
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

	x += 16;
	if (x == width) {
		x = 0;
		y += 16;
	}

	if (y == height) {
		// image received
		x = 0;
		y = 0;
		
		frameDecoded++;

		if (frameDecoded == stopAfter){
			exit(1);
		}else if (frameDecoded ==1 ){
			sendFirstFrameEvent();
		}else if (outputFps){
			printFps();
		}
printFps();
		//Write resulting image
		display_show_image(this);
	}
}

void print_fps_avg(void) {
	/*Uint32 t = SDL_GetTicks();

	printf("%i images in %f seconds: %f FPS\n",
		num_images_end, (float) firstFrame / 1000.0f,
		1000.0f * (float)firstFrame / (float)t);*/
}

void Display::printFps(){
	int t2 = SDL_GetTicks();

	if (t2 - t > 3000) {
		fprintf(bench, "%f images/sec\n",
			1000.0f * (float)(frameDecoded - frameStart) / (float)(t2 - t));
		printf("%f images/sec\n",
			1000.0f * (float)(frameDecoded - frameStart) / (float)(t2 - t));

		t = t2;
		frameStart = frameDecoded;
	}

}

void Display::display_show_image(Display* display) {

	///////////////////////////////////////////////////////////////////////////////////////////////
	if (SDL_LockYUVOverlay(m_overlay) < 0) {
		fprintf(stderr, "Can't lock screen: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	//Get display information
	int width = display->getWidth();
	int height = display->getHeight();
	void* img_buf_y = display->getBuf_Y();
	void* img_buf_u = display->getBuf_U();
	void* img_buf_v = display->getBuf_V();

	//Preparing surface
	SDL_Rect rect = { 0, 0, m_width, m_height };

	memcpy(m_overlay->pixels[0], img_buf_y, width * height );
	memcpy(m_overlay->pixels[1], img_buf_u, width * height / 4 );
	memcpy(m_overlay->pixels[2], img_buf_v, width * height / 4 );

	SDL_UnlockYUVOverlay(m_overlay);
	SDL_DisplayYUVOverlay(m_overlay, &rect);

	///////////////////////////////////////////////////////////////////////////////////////////////
	SDL_Event event;
	
	/* Grab all the events off the queue. */
	while (SDL_PollEvent(&event)) {
		switch (event.type) {
			case SDL_QUIT:
				m_width = 0;
				m_height = 0;
				pthread_exit(NULL);
				break;
			default:
				break;
		}
	}
}

void stop(pthread_t* thread){
	SDL_Event sdlEvent;
	sdlEvent.type = SDL_QUIT;
	SDL_PushEvent(&sdlEvent);
	pthread_join (*thread, NULL);
	SDL_Quit();
}

void Display::display_init() {
	//Display is initialized
	init = true;

	// First, initialize SDL's video subsystem.
	if (SDL_Init( SDL_INIT_VIDEO ) < 0) {
		fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
		press_a_key(-1);
	}

	SDL_WM_SetCaption("display", NULL);
/*
	start_time = SDL_GetTicks();
	t = start_time;
	
	atexit(print_fps_avg);*/
}

void Display::setSize(int width, int height){
	this->width = width * 16;
	this->height = height * 16;

	display_set_video(this);
}

void Display::display_set_video(Display* display) {
	int width = display->getWidth();
	int height = display->getHeight();

	if (width <= m_width && height <= m_height) {
		// video mode is already good
		return;
	}

	m_width = width;
	m_height = height;

	if (display->printFpsEnable()){
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
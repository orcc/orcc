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

#include <SDL.h>

#include "util.h"
#include "options.h"
#include "trace.h"

static SDL_Window        *pWindow1;
static SDL_Renderer      *pRenderer1;
static SDL_Texture       *bmpTex1;
static uint8_t           *pixels1;
static int               pitch1, size1;

static int init = 0;

char displayYUV_getFlags(){
    return opt->display_flags;
}

static void displayYUV_setSize(int width, int height) {
    print_orcc_trace(ORCC_VL_VERBOSE_1, "set display to %ix%i", width, height);

    // allocate window, renderer, texture
    pWindow1    = SDL_CreateWindow( "display", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED,
                    width, height, SDL_WINDOW_SHOWN | SDL_WINDOW_OPENGL);
    pRenderer1  = SDL_CreateRenderer(pWindow1, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
    bmpTex1     = SDL_CreateTexture(pRenderer1, SDL_PIXELFORMAT_YV12,
                    SDL_TEXTUREACCESS_STREAMING, width, height);
    if(pWindow1==NULL || pRenderer1==NULL || bmpTex1==NULL) {
        fprintf(stderr, "Could not open window1\n");
    }
}

void displayYUV_displayPicture(unsigned char *pictureBufferY,
                               unsigned char *pictureBufferU, unsigned char *pictureBufferV,
                               unsigned int   pictureWidth,   unsigned int   pictureHeight) {
    static unsigned int lastWidth = 0;
    static unsigned int lastHeight = 0;
    SDL_Event event;

    if ((pictureHeight != lastHeight) || (pictureWidth != lastWidth)) {
        displayYUV_setSize(pictureWidth, pictureHeight);
        lastHeight = pictureHeight;
        lastWidth = pictureWidth;
    }

    size1 = pictureWidth * pictureHeight;

    SDL_LockTexture(bmpTex1, NULL, (void **)&pixels1, &pitch1);
    memcpy(pixels1,             pictureBufferY, size1  );
    memcpy(pixels1 + size1,     pictureBufferV, size1/4);
    memcpy(pixels1 + size1*5/4, pictureBufferU, size1/4);
    SDL_UnlockTexture(bmpTex1);
    SDL_UpdateTexture(bmpTex1, NULL, pixels1, pitch1);
    // refresh screen
    //    SDL_RenderClear(pRenderer1);
    SDL_RenderCopy(pRenderer1, bmpTex1, NULL, NULL);
    SDL_RenderPresent(pRenderer1);

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
        }

        atexit(SDL_Quit);
    }
}

/**
 * @brief Return the number of frames the user want to decode before exiting the application.
 * If user didn't use the -f flag, it returns -1 (DEFAULT_INFINITEà).
 * @return The
 */
int displayYUV_getNbFrames() {
    return opt->nbFrames;
}

void display_close() {
    SDL_Quit();
}

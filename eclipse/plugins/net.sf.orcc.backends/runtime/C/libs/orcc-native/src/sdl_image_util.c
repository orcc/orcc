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

#include <SDL_image.h>
#include <SDL.h>
#include "math.h"
#include "sdl_image_util.h"
#include "util.h"
#include "types.h"

//static SDL_Surface *image;

static SDL_Surface *m_screen;
static SDL_Surface *m_image;
static SDL_Surface *rectangle;
static SDL_Surface *get_image;
static SDL_Overlay *m_overlay;

static int init = 0;
static int iImgIndex = 0;

static int x, y, xS, yS, onclick = 0;
static SDL_Rect rect;
static SDL_Rect rectSelected;
static u32 pixelS;
static u8 r, g, b;

Uint32 GetPixel(SDL_Surface *surface, int x, int y)
{
	int bpp = surface->format->BytesPerPixel;
	/* Here p is the address to the pixel we want to retrieve */
	Uint8 *p = (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;

	switch (bpp) {
	case 1:
		return *p;
		break;

	case 2:
		return *(Uint16 *)p;
		break;

	case 3:
		if (SDL_BYTEORDER == SDL_BIG_ENDIAN)
			return p[0] << 16 | p[1] << 8 | p[2];
		else
			return p[0] | p[1] << 8 | p[2] << 16;
		break;

	case 4:
		return *(Uint32 *)p;
		break;

	default:
		return 0;       /* shouldn't happen, but avoids warnings */
	}
}

typedef struct
{
	char key[SDLK_LAST];
	int mousex, mousey;
	int mousexrel, mouseyrel;
	char mousebuttons[8];
	char quit;
} Input;


void UpdateEvents(Input* in)
{
	SDL_Event event;
	while (SDL_PollEvent(&event))
	{
		switch (event.type)
		{
		case SDL_KEYDOWN:
			in->key[event.key.keysym.sym] = 1;
			break;
		case SDL_KEYUP:
			in->key[event.key.keysym.sym] = 0;
			break;
		case SDL_MOUSEMOTION:
			in->mousex = event.motion.x;
			in->mousey = event.motion.y;
			in->mousexrel = event.motion.xrel;
			in->mouseyrel = event.motion.yrel;
			break;
		case SDL_MOUSEBUTTONDOWN:
			in->mousebuttons[event.button.button] = 1;
			break;
		case SDL_MOUSEBUTTONUP:
			in->mousebuttons[event.button.button] = 0;
			break;
		case SDL_QUIT:
			in->quit = 1;
			break;
		default:
			break;
		}
	}
}

void get_mouse_position(int *position)
{
	Input in;
	// init SDL, chargement, tout ce que vous faites avant la boucle.
	memset(&in, 0, sizeof(in));
	while (!in.key[SDLK_ESCAPE] && !in.quit)
	{
		UpdateEvents(&in);
		if (in.mousebuttons[SDL_BUTTON_LEFT])
		{
			in.mousebuttons[SDL_BUTTON_LEFT] = 0;
			// fait une seule fois.
			printf("mouse position = x%d, y%d \n", in.mousex, in.mousey);
			position[0] = in.mousex;
			position[1] = in.mousey;
			in.quit = 1;
		}

		if (in.key[SDLK_UP] && in.key[SDLK_LEFT])
		{
			// simplification de la gestion des touches
		}

	}

}

void readRGBpicture(u8 *R, u8 *G, u8 *B){
	//////////////////////////special case//////////////////////////////////
	// special case supporting 2 digits starting from 01.jpg to 71
    char *pathDir = "/home/asanchez/sequences/videodata/datasets/animal";
    char *strExt = ".jpg";
    char *path = NULL;
    int iDigits;

    if (iImgIndex == 71) {
        iImgIndex = 0;
    }
    iImgIndex++;

    iDigits = floor(log10(abs(iImgIndex))) + 1;
    path = (char*)malloc(sizeof(char)*(strlen(pathDir)+iDigits+strlen(strExt)+2));
    sprintf(path,"%s/%d%s", pathDir, iImgIndex, strExt);

	//////////////////////////////////////////////////////////////////////////
    SDL_RWops *rwop = SDL_RWFromFile(path, "r");
    if (rwop != NULL) {
        get_image = IMG_LoadJPG_RW(rwop);
        if (!get_image) {
            printf("IMG_LoadJPG_RW: %s\n", IMG_GetError());
        } else {
            for (yS = 0; yS < get_image->h; yS++){
                for (xS = 0; xS < get_image->w; xS++){
                    pixelS = GetPixel(get_image, xS, yS);
                    SDL_GetRGB(pixelS, get_image->format, &r, &g, &b);
                    R[xS + yS*get_image->w] = r;
                    G[xS + yS*get_image->w] = g;
                    B[xS + yS*get_image->w] = b;
                }
            }
            SDL_FreeSurface(get_image);
            rwop->close(rwop);
        }
    }
}

int get_pict_width(){
    if (get_image) {
        return get_image->w;
    } else {
        return 0;
    }
}

int get_pict_height(){
    if (get_image) {
        return get_image->h;
    } else {
        return 0;
    }
}

void drawEmptyRect(SDL_Surface * surf, int posX, int posY, int width, int length, int R, int G, int B)
{
	SDL_Rect lineHigh;
	lineHigh.x = posX - 1;
	lineHigh.y = posY - 1;
	lineHigh.w = length;
	lineHigh.h = 1;

	SDL_FillRect(surf, &lineHigh, SDL_MapRGB(surf->format, R, G, B));

	SDL_Rect lineRight;
	lineRight.x = posX + length - 1;
	lineRight.y = posY - 1;
	lineRight.w = 1;
	lineRight.h = width;

	SDL_FillRect(surf, &lineRight, SDL_MapRGB(surf->format, R, G, B));

	SDL_Rect lineLeft;
	lineLeft.x = posX - 1;
	lineLeft.y = posY - 1;
	lineLeft.w = 1;
	lineLeft.h = width;

	SDL_FillRect(surf, &lineLeft, SDL_MapRGB(surf->format, R, G, B));

	SDL_Rect lineDown;
	lineDown.x = posX - 1;
	lineDown.y = posY + width - 1;
	lineDown.w = length;
	lineDown.h = 1;

	SDL_FillRect(surf, &lineDown, SDL_MapRGB(surf->format, R, G, B));
}
/*******************************************************************************
* displayYUV444_displayPicture
******************************************************************************/
void displayRGB_displayPicture(u8 pictureBufferR[70400], u8 pictureBufferG[70400], u8 pictureBufferB[70400], i16 pictureWidth, i16 pictureHeight) {

	unsigned int h, w;
	int red, green, blue;
	int pixel, idx_pixel;
	SDL_PixelFormat *format = m_image->format;
	rect.x = x;
	rect.y = y;
	rect.w = pictureWidth;
	rect.h = pictureHeight;

	for (h = 0; h < pictureHeight; h++) {
		for (w = 0; w < pictureWidth; w++) {    //start from lower-left corner
			idx_pixel = h * pictureWidth + w;
			red   = pictureBufferR[idx_pixel];
			green = pictureBufferG[idx_pixel];
			blue  = pictureBufferB[idx_pixel];
			
			pixel = ((clip255(red) << format->Rshift) & format->Rmask) |
				((clip255(green) << format->Gshift) & format->Gmask) |
				((clip255(blue) << format->Bshift) & format->Bmask);
			*(int *)&((char *)m_image->pixels)[idx_pixel * format->BytesPerPixel] = pixel;
		}
	}
	SDL_BlitSurface(m_image, NULL, m_screen, &rect);
	SDL_UpdateRects(m_screen, 1, &rect);
	
}

void displayRGB_displayRect(u8 pictureBufferR[70400], u8 pictureBufferG[70400], u8 pictureBufferB[70400], i16 pictureWidth, i16 pictureHeight) {

	unsigned int h, w;
	int red, green, blue;
	int pixel, idx_pixel;
	
	if (rectangle != NULL) {
		SDL_FreeSurface(rectangle);
	}
	rectangle = SDL_CreateRGBSurface(SDL_SWSURFACE, pictureWidth, pictureHeight, m_screen->format->BitsPerPixel,
		m_screen->format->Rmask, m_screen->format->Gmask, m_screen->format->Bmask, m_screen->format->Amask);
	SDL_PixelFormat *format = rectangle->format;
	rectSelected.x = x + 360;
	rectSelected.y = y;
	rectSelected.w = pictureWidth;
	rectSelected.h = pictureHeight;

	for (h = 0; h < pictureHeight; h++) {
		for (w = 0; w < pictureWidth; w++) {    //start from lower-left corner
			idx_pixel = h * pictureWidth + w;
			red = pictureBufferR[idx_pixel];
			green = pictureBufferG[idx_pixel];
			blue = pictureBufferB[idx_pixel];

			pixel = ((clip255(red) << format->Rshift) & format->Rmask) |
				((clip255(green) << format->Gshift) & format->Gmask) |
				((clip255(blue) << format->Bshift) & format->Bmask);
			*(int *)&((char *)rectangle->pixels)[idx_pixel * format->BytesPerPixel] = pixel;
		}
	}
	SDL_BlitSurface(rectangle, NULL, m_screen, &rectSelected);
	SDL_UpdateRects(m_screen, 1, &rectSelected);

}

void displayTrackRect(int posx1, int posy1, int posx2, int posy2){
	int w = 0;
	int h = 0;
	int arg1 = 0;
	int arg2 = 0;

	if ((posx1 >= posx2) && (posy1 >= posy2)){
		w = posx1 - posx2;
		h = posy1 - posy2;
		arg1 = posx2;
		arg2 = posy2;
	}
	else if ((posx1 >= posx2) && (posy1 < posy2)){
		w = posx1 - posx2;
		h = posy2 - posy1;
		arg1 = posx2;
		arg2 = posy1;
	}

	else if ((posx1 < posx2) && (posy1 >= posy2)){
		w = posx2 - posx1;
		h = posy1 - posy2;
		arg1 = posx1;
		arg2 = posy2;
	}
	else{
		w = posx2 - posx1;
		h = posy2 - posy1;
		arg1 = posx1;
		arg2 = posy1;
	}
	
	drawEmptyRect(m_image, arg1, arg2, h, w, 255, 0, 0);
	SDL_BlitSurface(m_image, NULL, m_screen, &rect);
	SDL_UpdateRects(m_screen, 1, &rect);
}
/*******************************************************************************
* displayRGB_setSize
******************************************************************************/
void displayRGB_setSize(int winWidth, int winHeight, int pictureWidth, int pictureHeight) {
	printf("set display to %ix%i\n", winWidth, winHeight);

	m_screen = SDL_SetVideoMode(winWidth, winHeight, 24, SDL_ANYFORMAT);
	if (m_screen == NULL) {
		fprintf(stderr, "Couldn't set %ix%ix24 video mode: %s\n", winWidth,
			winHeight, SDL_GetError());
		exit(-1);
	}
	if (m_image != NULL) {
		SDL_FreeSurface(m_image);
	}

	m_image = SDL_CreateRGBSurface(SDL_SWSURFACE, pictureWidth, pictureHeight, m_screen->format->BitsPerPixel,
		m_screen->format->Rmask, m_screen->format->Gmask, m_screen->format->Bmask, m_screen->format->Amask);

	if (m_image == NULL) {
		fprintf(stderr, "Couldn't create overlay: %s\n", SDL_GetError());
		exit(-1);
	}
}

/*******************************************************************************
* displayRGB_init
******************************************************************************/
void displayRGB_init(int winWidth, int winHeight, int pictureWidth, int pictureHeight) {
	if (!init) {
		m_overlay = NULL;
        init = 1;
		// First, initialize SDL's video subsystem.
		if (SDL_Init(SDL_INIT_VIDEO) < 0) {
			fprintf(stderr, "Video initialization failed: %s\n", SDL_GetError());
			exit(-1);
		}
		SDL_WM_SetCaption("display", NULL);
		atexit(SDL_Quit);
		displayRGB_setSize(pictureWidth * 2, pictureHeight, pictureWidth, pictureHeight);
		x = 0;
		y = 0;
		onclick = 0;
	}
}

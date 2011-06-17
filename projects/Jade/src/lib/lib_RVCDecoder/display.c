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
#include <string.h>

#include "display.h"

#define DISPLAY_READY 1
#define DISPLAY_ENABLE 2

static RVCFRAME Frame;
extern int safeguardFrameEmpty;

extern int* stopVar;

char* outBuffer;

extern int bufferBusy;

char display_flag;


void displayYUV_setOutBufferAddr(char* Address){
	outBuffer = Address;
	display_flag = DISPLAY_READY + DISPLAY_ENABLE;

	if (!safeguardFrameEmpty){
		memcpy(outBuffer, Frame.pY[0], Frame.Width * Frame.Height); 
		memcpy(outBuffer + Frame.Width * Frame.Height, Frame.pU[0], Frame.Width * Frame.Height/4);
		memcpy(outBuffer + 5*Frame.Width * Frame.Height/4, Frame.pV[0], Frame.Width * Frame.Height/4);

		safeguardFrameEmpty = 1;
		bufferBusy = 1;
	}
}

void displayYUV_displayPicture(unsigned char *pictureBufferY, unsigned char *pictureBufferU,
							   unsigned char *pictureBufferV, unsigned short pictureWidth,
							   unsigned short pictureHeight){

	

	if(!bufferBusy){
		bufferBusy = 1;

		memcpy(outBuffer, pictureBufferY, pictureWidth * pictureHeight); 
		memcpy(outBuffer + pictureWidth * pictureHeight, pictureBufferU, pictureWidth * pictureHeight/4);
		memcpy(outBuffer + 5*pictureWidth * pictureHeight/4, pictureBufferV, pictureWidth * pictureHeight/4);

	}else{
		safeguardFrameEmpty = 0;

		Frame.Width = pictureWidth;
		Frame.Height = pictureHeight;

		*Frame.pY = pictureBufferY;
		*Frame.pU = pictureBufferU;
		*Frame.pV = pictureBufferV;

		display_flag = DISPLAY_ENABLE;
		*stopVar = 1;
	}
}

char displayYUV_getFlags(){
	return display_flag;
}

void displayYUV_init(){}
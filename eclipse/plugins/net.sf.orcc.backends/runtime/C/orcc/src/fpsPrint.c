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
#include <SDL.h>
#include <time.h>

static unsigned int startTime;
static unsigned int relativeStartTime;
static int lastNumPic;
static int numPicturesDecoded;


static void print_fps_avg(void) {
	unsigned int endTime = SDL_GetTicks();

	printf("%i images in %f seconds: %f FPS\n", numPicturesDecoded,
		(float) (endTime - startTime)/ 1000.0f,
		1000.0f * (float) numPicturesDecoded / (float) (endTime -startTime));
}

void fpsPrintInit() {
	startTime = SDL_GetTicks();
	relativeStartTime = startTime;
	numPicturesDecoded = 0;
	lastNumPic = 0;
	atexit(print_fps_avg);
}

void fpsPrintNewPicDecoded(void) {
	unsigned int endTime;
	numPicturesDecoded++;
	endTime = SDL_GetTicks();
	if ((endTime - relativeStartTime) / 1000.0f >= 5) {
		printf("%f images/sec\n",
				1000.0f * (float) (numPicturesDecoded - lastNumPic)
						/ (float) (endTime - relativeStartTime));

		relativeStartTime = endTime;
		lastNumPic = numPicturesDecoded;
	}
}

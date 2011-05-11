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
@brief Implementation of class GpacDisp
@author Olivier Labois
@file GpacDisp.cpp
@version 1.0
@date 06/05/2011
*/

//------------------------------
#include <iostream>
#include "SDL.h"

#include <pthread.h>
#include "Jade/Actor/GpacDisp.h"

#include "llvm/Support/CommandLine.h"
//------------------------------

using namespace std;


GpacDisp::GpacDisp(int id) : Display(id){
	this->id = id;

	width = 0;
	height = 0;
	x = 0;
	y = 0;
}

GpacDisp::~GpacDisp(){
}


void GpacDisp::display_write_mb(unsigned char tokens[384]) {
	int i, j, cnt, base;

	cnt = 0;
	base = y * width + x;

	for (i = 0; i < 16; i++) {
		for (j = 0; j < 16; j++) {
			int tok = tokens[cnt];
			int idx = base + i * width + j;
			cnt++;
			img_buf_y[idx] = tok;
		}
	}

	base = y / 2 * width / 2 + x / 2;
	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			int idx = base + i * width / 2 + j;
			cnt++;
			img_buf_u[idx] = tok;
		}
	}

	for (i = 0; i < 8; i++) {
		for (j = 0; j < 8; j++) {
			int tok = tokens[cnt];
			int idx = base + i * width / 2 + j;
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
		
		//Write resulting image
		*rvcFrame->pY = img_buf_y;
		*rvcFrame->pU = img_buf_u;
		*rvcFrame->pV = img_buf_v;

		rvcFrame->Width = width;
		rvcFrame->Height = height;

		*stopSchVal = 1;

	}
}


void GpacDisp::setSize(int width, int height){
	this->width = width * 16;
	this->height = height * 16;
}
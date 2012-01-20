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
@brief Implementation of the source
@author Olivier Labois
@file source.c
@version 1.0
@date 29/06/2011
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "orcc_util.h"
#include "source.h"

// Start code
static unsigned char StartCode[4];
static int startCodeSize;

static int data_length;
static unsigned char* data;
static int nbTokenSend;
extern int nalState;

int* stopVar;

void printSpeed(void) {
}

// Called before any *_scheduler function.
void source_init() {
	startCodeSize = 0;
	nbTokenSend = 0;
	nalState = NAL_NOT_READ;
}

int source_sizeOfFile() { 
	if(!data_length){
		//Stop scheduler
		*stopVar = 1;
		return 0;
	}else{
		return data_length + startCodeSize; 
	}
}


void source_rewind() {
}

void source_exit(int exitCode) {
}

unsigned int source_getNbLoop(void)
{
    return nbLoops;
}

unsigned int source_readByte(){
    return 0;
}

void source_readNBytes(unsigned char *outTable, unsigned short nbTokenToRead){
	// Set start code
	if(startCodeSize > 0 && nbTokenSend == 0){
		memcpy(outTable, StartCode, startCodeSize);
		nbTokenToRead -= startCodeSize;
		nbTokenSend += startCodeSize;
		outTable += startCodeSize;
	}

	// Set data
	memcpy(outTable, data + nbTokenSend - startCodeSize, nbTokenToRead);
	nbTokenSend += nbTokenToRead;

	// Stop transfer
	if (nbTokenSend == data_length + startCodeSize){
		nalState = NAL_IS_READ;
		data_length = 0;
		nbTokenSend = 0;
	}
}


void source_prepare(unsigned char* nal, int nal_length){
	data = nal;

	if(nalState == NAL_NOT_READ){
		nalState = NAL_TODO_READ;
		data_length = nal_length;

	}else if(nalState == NAL_ALREADY_READ){
		nalState = NAL_IS_READ;
		data_length = 0;

	}else{
		data_length = nal_length;
	}
}

void source_isAVCFile(){
	unsigned char AVCStartCode[4] = {0x00, 0x00, 0x00, 0x01};
	startCodeSize = 4;
	memcpy(StartCode, AVCStartCode, startCodeSize);
}

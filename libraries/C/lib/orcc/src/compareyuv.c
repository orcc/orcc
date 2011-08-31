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
#include <sys/stat.h>

#include <stdio.h>
#include <stdlib.h>

#include "orcc_types.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

// from APR
/* Ignore Microsoft's interpretation of secure development
* and the POSIX string handling API
*/

#if defined(_MSC_VER) && _MSC_VER >= 1400
#ifndef _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_DEPRECATE
#endif
#pragma warning(disable: 4996)
#endif

static FILE        *ptrFile ;
static unsigned int fileSize;
static char         useCompare;

static int compareYUV_compareComponent(const int x_size, const int y_size, 
               const unsigned char *true_img_uchar, const unsigned char *test_img_uchar,
               unsigned char SizeMbSide, char Component_Type) {
	int pix_x, pix_y,blk_x,blk_y;
	int error = 0;
	int WidthSzInBlk  = x_size / SizeMbSide;
	int HeightSzInBlk = y_size / SizeMbSide;

	for(blk_y = 0 ; blk_y < HeightSzInBlk ; blk_y++)
	{
		for(blk_x = 0 ; blk_x < WidthSzInBlk ; blk_x++)
		{
			for (pix_y = 0; pix_y < SizeMbSide; pix_y++)
			{
				for (pix_x = 0; pix_x < SizeMbSide; pix_x++)
				{
					int Idx_pix = (blk_y * SizeMbSide + pix_y) * x_size + (blk_x * SizeMbSide + pix_x);

					if (true_img_uchar[Idx_pix] - test_img_uchar[Idx_pix] != 0)
					{
						error++;
						if (error < 50)
						{
							printf("\nerror %3d instead of %3d at position : mb = (%d ; %d) , loc_in_mb = (%d ; %d)",
								test_img_uchar[Idx_pix] , true_img_uchar[Idx_pix], blk_x, blk_y, pix_x, pix_y);
						}
					}
				}
			}
		}
	}

	if (error != 0) {
		printf("\n%d error(s) in %c Component !!!!!!!!!!!!!\n", error, Component_Type);
	}
	return error;
}

void compareYUV_init()
{
	struct stat st;

	//Fix me!! Dirty but it's the only way for the moment.
	if (yuv_file == NULL) {
		useCompare = 0;
		return;
	}
	useCompare = 1;
	ptrFile = fopen(yuv_file, "rb");
	if (ptrFile == NULL) {
		printf("Cannot open yuv_file concatenated input file '%s' for reading\n", yuv_file);
		exit(-1);
	}

	fstat(fileno(ptrFile), &st);
	fileSize = st.st_size;
}

static void compareYUV_readComponent(unsigned char **Component, unsigned short width, unsigned short height, char sizeChanged) {
	size_t numByteRead;

	if(*Component == NULL) {
		*Component = (unsigned char*)malloc(width*height * sizeof(unsigned char));
	}
	else {
		if(sizeChanged) {
			*Component =  (unsigned char*)realloc(*Component, width*height * sizeof(unsigned char));
		}
	}
	if(*Component == NULL) {
		fprintf(stderr,"Problem when allocating memory.\n");
		exit(-5);
	}
	numByteRead = fread(*Component, sizeof(unsigned char),  width*height, ptrFile);
	if(numByteRead != (width*height)) {
		fprintf(stderr, "Error when using fread\n");
		exit(-6);
	}
}

void compareYUV_comparePicture(unsigned char *pictureBufferY, unsigned char *pictureBufferU,
                               unsigned char *pictureBufferV, unsigned short pictureWidth,
                               unsigned short pictureHeight) {
	static unsigned int frameNumber = 0;
	static int prevXSize = 0;
	static int prevYSize = 0;

	static unsigned char *Y = NULL;
	static unsigned char *U = NULL;
	static unsigned char *V = NULL;

	char sizeChanged;

	if(useCompare) {
		int numErrors = 0;

		printf("Frame number %d", frameNumber);
		frameNumber++;

		sizeChanged = ((prevXSize*prevYSize) != (pictureWidth*pictureHeight)) ? 1 : 0;
		compareYUV_readComponent(&Y, pictureWidth,   pictureHeight,   sizeChanged);
		compareYUV_readComponent(&U, pictureWidth/2, pictureHeight/2, sizeChanged);
		compareYUV_readComponent(&V, pictureWidth/2, pictureHeight/2, sizeChanged);

		numErrors += compareYUV_compareComponent(pictureWidth, pictureHeight, Y, pictureBufferY,16, 'Y');
		numErrors += compareYUV_compareComponent(pictureWidth >> 1, pictureHeight >> 1, U, pictureBufferU,8,'U');
		numErrors += compareYUV_compareComponent(pictureWidth >> 1, pictureHeight >> 1, V, pictureBufferV,8, 'V');

		if(numErrors == 0)
		{
			printf("; no error detected !\n");
		}

		if(ftell(ptrFile) == fileSize) {
			rewind(ptrFile);
			frameNumber = 0;
		}
		prevXSize = pictureWidth;
		prevYSize = pictureHeight;
	}
}

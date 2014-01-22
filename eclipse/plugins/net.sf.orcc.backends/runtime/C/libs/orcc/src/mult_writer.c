/*
* Copyright (c) 2012, EPFL
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
*   * Neither the name of the EPFL nor the names of its
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

// Author : Damien de Saint Jorre

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "types.h"
#include "fifo.h"
#include "util.h"

static char stop = 0;
static int cnt = 0; 

int writer_open(char* fileName) {
	FILE* file = NULL;
	if (write_file == NULL) {
		file = fopen(fileName, "wb");
		if (file == NULL) {
            fprintf(stderr, "could not open file \"%s\"\n", fileName);
			exit(1);
		}
	}
	else {
		char fullPathName[256];
		if(strlen(fileName)+strlen(write_file)>=256) {
			fprintf(stderr, "Path too long : input_directory : %s ; fileName : %s\n", write_file, fileName);
			exit(-1);
		}
		strcpy(fullPathName, write_file);
		strcat(fullPathName, fileName);
		file = fopen(fullPathName, "wb");
		if (file == NULL) {
            fprintf(stderr, "could not open file \"%s\"\n", fullPathName);
			exit(1);
		}
	}
	stop = 0;
	cnt++;

	return (long)file;
}

void writer_writeNTokens(int writeFile, u8 *byte, unsigned short numTokens){
    fwrite((void*) byte,sizeof(u8), numTokens, (FILE*) (long) writeFile);
}

void writer_closeAndQuit(int writeFile){
	if(writeFile != 0) {
        fclose((FILE*) (long) writeFile);
		cnt--;
		if(cnt==0) {
			exit(0);
		}
	}
}

char writer_getStop() {
	return stop;
}

void writer_setStop(char stopVal) {
	stop = stopVal;
}

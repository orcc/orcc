/*
* Copyright (c) 2011, EPFL
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

// Author : Damien de Saint Jorre (damien.desaintjorre@epfl.ch)

#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>

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

long openFile(char* filename) {
	FILE* fd = NULL;
	fd = fopen(filename,"r+b");
	if(fd == NULL) {
		fprintf(stderr, "Error during opening of %s\n", filename);
		exit(-2);
	}
	return (long)fd;
}

void writeByte(long desc, char value ) {
	FILE* fd = (FILE*) desc;

	fwrite(&value, 1, 1, fd);
}

int closeFile(long desc) {
	FILE* fd = (FILE*) desc;
	return fclose(fd);
}

int sizeOfFile(long desc){
	FILE* fd = (FILE*) desc;
	struct stat st; 
	fstat(fileno(fd), &st); 
	return st.st_size;
}

unsigned char readByte(long desc){
	FILE* fd = (FILE*) desc;
	unsigned char buf[1];
	int n = fread(&buf, 1, 1,fd);
	if (n < 1) {
		fprintf(stderr,"Problem when reading input file.\n");
	}
	return buf[0];
}

/*
* Copyright (c) 2009, EPFL
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

// Author : Endri Bezati (endri.bezati@epfl.ch)

#include <stdio.h>
#include <stdlib.h>

#include "orcc_types.h"
#include "orcc_util.h"

FILE *F = NULL;
static int cnt = 0; 

void Writer_init() {
	
	if (write_file == NULL) {
		print_usage();
		fprintf(stderr, "No write file given!\n");
		//wait_for_key();
		exit(1);
	}

	F = fopen(write_file, "wb");
	if (F == NULL) {
		if (write_file == NULL) {
			write_file = "<null>";
		}

		fprintf(stderr, "could not open file \"%s\"\n", write_file);
		//wait_for_key();
		exit(1);
	}else{
		fseek(F,0,SEEK_SET);	
	}
}

void Writer_write(u8 byte){

	fseek(F,sizeof(u8)*cnt,SEEK_SET);
	fwrite(&byte,sizeof(u8),1,F);
	cnt++;	
}

void Writer_close(){
	fclose(F);
	exit(666);
}

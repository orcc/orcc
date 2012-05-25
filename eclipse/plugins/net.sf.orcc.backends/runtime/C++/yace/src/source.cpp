/**
 * \file
 * \author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * \version 1.0
 *
 * \section LICENSE
 *
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
 * 
 * \section DESCRIPTION
 *
 */

#define _CRT_SECURE_NO_WARNINGS

#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <string>

#include "get_opt.h"
#include "scheduler.h"

static FILE *file = NULL;
static int nb;
static int stop;

void source_init() 
{
	stop = 0;
	nb = 0;

	if (input_file.c_str() == NULL)
	{
		std::cerr << "No input file given!" << std::endl;
		exit(1);
	}

	file = fopen(input_file.c_str(), "rb");
	if (file == NULL) {
		if (input_file.c_str() == NULL) 
		{
			input_file = "<null>";
		}

		std::cerr << "could not open file "<<  input_file << std::endl;
//		wait_for_key();
		exit(1);
	}
}

int source_sizeOfFile() { 
	long curr, end;
	curr = ftell (file);
	fseek (file, 0, 2);
	end = ftell (file);
	fseek (file, curr, 0);
	return end;
}

int source_is_stopped() {
	return stop;
}

void source_active_genetic() {
}

void source_rewind() {
	if(file != NULL) {
		rewind(file);
	}
}

unsigned int source_readByte(){
	unsigned char buf[1];
	int n = fread(&buf, 1, 1, file);

	if (n < 1) {
		if (feof(file)) {
			//std::cout << "warning" << std::endl;
			rewind(file);
			n = fread(&buf, 1, 1, file);
			nb++;
		}
		else {
			std::cerr << "Problem when reading input file" << std::endl;
		}
	}
	return buf[0];
}


void source_readNBytes(unsigned char outTable[], unsigned short nbTokenToRead){
	int n = fread(outTable, 1, nbTokenToRead, file);

	if(n < nbTokenToRead) {
		fprintf(stderr,"Problem when reading input file.\n");
		exit(-4);
	}
}

unsigned int source_getNbLoop(void)
{
	return nbLoops;
}

void source_exit(int exitCode)
{
	//fclose(file);
	Scheduler* current_thread = (Scheduler*) Thread::currentThread();
	current_thread->done();
}

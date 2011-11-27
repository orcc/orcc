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

// for MSVC
#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_NONSTDC_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "orcc_util.h"

// define to 1 if your system has the termios.h header
#define HAS_TERMIOS 0

#ifdef _WIN32
	#include <conio.h>
#else
	#if HAS_TERMIOS
		#include <termios.h>
	#endif
#endif

#define DISPLAY_DISABLE 0
#define DISPLAY_READY 1
#define DISPLAY_ENABLE 2

extern char	*optarg;
extern int getopt(int nargc, char * const *nargv, const char *ostr);

//Nb Loops
unsigned int nbLoops = 1;

// input file
char *input_file;

// output YUV file
char *yuv_file;

// write file
char *write_file;

// mapping file
char *mapping_file;

// output file of genetic algorithm
char *output_genetic;

// deactivate display
char display_flags = DISPLAY_ENABLE;

// Pause function
void wait_for_key() {
#ifdef _WIN32
	printf("Press a key to continue\n");
	_getch();
#else
	#if HAS_TERMIOS
		// the user has termios.h
		struct termios oldT, newT;
		char c;

		printf("Press a key to continue\n");

		// save current terminal mode
		ioctl(0, TCGETS, &oldT);

		// echo off, echo newline off, canonical mode off, 
		// extended input processing off, signal chars off
		newT.c_lflag &= ~(ECHO | ECHONL | ICANON | IEXTEN | ISIG);

		ioctl(0, TCSETS, &newT); // set new terminal mode
		read(0, &c, 1); // read 1 char at a time from stdin
		ioctl(0, TCSETS, &oldT); // restore previous terminal mode
	#else
		// just revert to standard getc
		printf("Press Enter to continue\n");
		getc(stdin);
	#endif
#endif
}

// print APR error and exit
void print_and_exit(const char *msg) {
	wait_for_key();
	exit(1);
}

static const char *usage = "%s: -i <file> [-o <file>] [-w <file>] [-l <number of loop iterations>...\n";
static char *program;

void print_usage() {
	printf(usage, program);
}

///////////////////////////////////////////////////////////////////////////////
// initializes APR and parses options
void init_orcc(int argc, char *argv[]) {
	const char *ostr = "g:i:l:m:n:o:w:";
	int c;

	program = argv[0];
	
	c = getopt(argc, argv, ostr);
	while (c != -1) {
		switch (c) {
		case '?': // BADCH
			fprintf(stderr, "unknown argument\n");
			exit(1);
		case ':': // BADARG
			fprintf(stderr, "missing argument\n");
			exit(1);
		case 'g':
			output_genetic = strdup(optarg);
			break;
		case 'i':
			input_file = strdup(optarg);
			break;
		case 'l':
			nbLoops = strtoul(optarg, NULL, 10);
			if(nbLoops == 0) {
				nbLoops = 1;
			}
			break;
		case 'm':
			mapping_file = strdup(optarg);
			break;
		case 'n':
			display_flags = DISPLAY_DISABLE;
			break;
		case 'o':
			yuv_file = strdup(optarg);
			break;
		case 'w':
			write_file = strdup(optarg);
			break;
		default:
			fprintf(stderr, "skipping option -%c\n", c);
			break;
		}

		c = getopt(argc, argv, ostr);
	}
}

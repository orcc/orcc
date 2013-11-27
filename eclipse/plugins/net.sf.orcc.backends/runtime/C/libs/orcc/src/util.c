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

#include "util.h"
#include "options.h"
#include "trace.h"

// define to 1 if your system has the termios.h header
#define HAS_TERMIOS 0

#ifdef _WIN32
	#include <conio.h>
#else
	#if HAS_TERMIOS
		#include <termios.h>
	#endif
#endif

extern char *optarg;
extern int getopt(int nargc, char * const *nargv, const char *ostr);

// Directory for input files.
char *input_directory = NULL;

// input file
char *input_file;

// output YUV file
char *yuv_file;

// Profiling file
char *profiling_file;

// write file
char *write_file;

// mapping file
char *mapping_file;

// output file of genetic algorithm
char *output_genetic;

// deactivate display
char display_flags = DISPLAY_ENABLE;

// compute number of errors in the program
int compareErrors = 0;

// Nb times the input file is read
int nbLoops = DEFAULT_INFINITE; // -1: infinite loop.

// Nb frames to display
int nbFrames = DEFAULT_INFINITE;

// Number of executing threads to create
int nbThreads = 1;

// Strategy for the actor mapping
int mapping_strategy = 0;

// Number of frames to display before remapping application
int nbProfiledFrames = 10;

// Repetition of the actor remapping
int mapping_repetition = REMAP_ONCE;

// Pause function
void wait_for_key() {
#ifdef _WIN32
	printf("Press a key to continue\n");
	_getch();
#elsemapping_repetition
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

static char *program;
static const char *usage =
	"\nUsage: %s [arguments]\n"
	"Mandatory arguments:\n"
	"-i <file>                  Specify an input file.\n"
	"-d <directory>             Specify an input directory if the application requires several input files.\n"

	"\nOptional arguments:\n"
	"-m <mapping file>          Define actors partitioning on multi-core platform using a mapping file following the XCF format.\n"
	// "-t <trace directory>       Specify an output directory for the FIFO trace files.\n"

	"\nOther specific arguments:\n"
	"Depending on how the application has been designed, one of these arguments can be used.\n"
	"-n                         Ensure that the display will not be initialized (useful on non-graphic terminals).\n"
	"-o <reference file>        Check the output stream with a reference file (usually YUV).\n"
	"-f <nb frames to decode>   Set the number of frames to decode before closing the application.\n"
	"-l <nb input reading>      Set the number of times the input file is read before closing the application.\n"
    "-g <output file>           Specify an output file for the genetic algorithm.\n"
    "-b <output file>           Specify an output file for instrumention.\n"
    "-c <nb threads>            Set the number of execut500ing threads to run.\n"
    "-s <mapping strategy>      Specify the strategy for the actor mapping\n"
    "-v <level>                 Set the verbosity";
    // We need to document folowing options:
	//"-w <file>                  TBD...\n"

void print_usage() {
	printf(usage, program);
	fflush(stdout);
}

/////////////////////////////////////
// initializes APR and parses options
void init_orcc(int argc, char *argv[]) {
	// every command line option must be followed by ':' if it takes an
	// argument, and '::' if this argument is optional
    const char *ostr = "i:no:d:m:f:w:g:l:r:ac:s:v:b:";
    int c;

	program = argv[0];
	
	while ((c = getopt(argc, argv, ostr)) != -1) {
		switch (c) {
		case '?': // BADCH
			fprintf(stderr, "unknown argument\n");
			exit(1);
		case ':': // BADARG
			fprintf(stderr, "missing argument\n");
			exit(1);
		case 'd':
			input_directory = strdup(optarg);
			break;
		case 'g':
			output_genetic = strdup(optarg);
			break;
		case 'i':
			input_file = strdup(optarg);
			break;
		case 'l':
			nbLoops = strtoul(optarg, NULL, 10);
			break;
		case 'f':
			nbFrames = strtoul(optarg, NULL, 10);
            break;
        case 'c':
            nbThreads = strtoul(optarg, NULL, 10);
            break;
        case 's':
            mapping_strategy = strtoul(optarg, NULL, 10);
            break;
		case 'm':
			mapping_file = strdup(optarg);
			break;
        case 'r':
            nbProfiledFrames = strtoul(optarg, NULL, 10);
            break;
        case 'a':
            mapping_repetition = REMAP_ALWAYS;
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
        case 'b':
            profiling_file = strdup(optarg);
            break;
        case 'v':
            set_trace_level(strtoul(optarg, NULL, 10));
            break;
        default:
			fprintf(stderr, "Skipping option -%c\n", c);
			break;
		}
	}
}

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
#include <apr_lib.h>
#include <apr_getopt.h>

// define to 1 if your system has the termios.h header
#define HAS_TERMIOS 0

#ifdef _WIN32
	#include <conio.h>
#else
	#if HAS_TERMIOS
		#include <termios.h>
	#endif
#endif

// input file
char *input_file;

// output YUV file
char *yuv_file;

// option structure
// static, but could be made global if deemed necessary
static apr_getopt_t *os;

// Pause function
void pause() {
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
void print_and_exit(apr_status_t statcode) {
	char buf[1024];
	apr_strerror(statcode, buf, 1024);
	puts(buf);
	pause();
	exit(1);
}

static const char *usage = "%s: -i <file> ...\n";
static struct apr_getopt_option_t options[] = {
	{ "input", 'i', 1, "INPUT\tSets the input file" },
	{ "yuv", 256, 1, "YUV\t\tSets the YUV file to compare the video against" }
};
static int num_options = sizeof(options) / sizeof(struct apr_getopt_option_t);

// print_usage prints msg and the different options.
void print_usage() {
	int i;
	
	printf(usage, apr_filepath_name_get(os->argv[0]));

	for (i = 0; i < num_options; i++) {
		struct apr_getopt_option_t option = options[i];
		if (option.optch < 256) {
			if (option.name == NULL) {
				printf("  -%c ", option.optch);
			} else {
				printf("  -%c, --%s", option.optch, option.name);
				if (option.has_arg) {
					printf("=");
				} else {
					printf(" ");
				}
			}
		} else {
			printf("      --%s", option.name);
			if (option.has_arg) {
				printf("=");
			} else {
				printf(" ");
			}
		}

		printf("%s\n", option.description);
	}
}

// Parses command-line options: -i, --yuv
static void parse_options() {
	int option_ch;
	char *option_arg;
	apr_status_t statcode;

	statcode = apr_getopt_long(os, options, &option_ch, &option_arg);
	while (statcode == APR_SUCCESS) {
		if (option_ch == 'i') {
			input_file = strdup(option_arg);
		} else if (option_ch == 256) {
			yuv_file = strdup(option_arg);
		}

		statcode = apr_getopt_long(os, options, &option_ch, &option_arg);
	}

	if (statcode == APR_BADCH || statcode == APR_BADARG) {
		print_usage();
		print_and_exit(statcode);
	}
}

///////////////////////////////////////////////////////////////////////////////
// initializes APR and parses options
void init_orcc(int argc, char *argv[], char *env[]) {
	apr_pool_t *pool;
	apr_status_t statcode;
	
	statcode = apr_app_initialize(&argc, &argv, &env);
	if (statcode != APR_SUCCESS) {
		print_and_exit(statcode);
	}

	// will call apr_terminate when program exits
	atexit(apr_terminate);

	statcode = apr_pool_create(&pool, NULL);
	if (statcode != APR_SUCCESS) {
		print_and_exit(statcode);
	}

	statcode = apr_getopt_init(&os, pool, argc, argv);
	if (statcode != APR_SUCCESS) {
		print_and_exit(statcode);
	}

	parse_options();
}
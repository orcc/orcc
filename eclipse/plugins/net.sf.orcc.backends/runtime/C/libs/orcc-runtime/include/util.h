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

#ifndef _ORCC_UTIL_H_
#define _ORCC_UTIL_H_

#include "orcc.h"

extern options_t *opt;

// compute number of errors in the program
extern int compareErrors;

// initialize APR and parse command-line options
options_t* init_orcc(int argc, char *argv[]);

// print usage
void print_usage();

void atexit_actions();

#define DISPLAY_DISABLE 0
// DISPLAY_ENABLE is set to 3 for the moment, for backward compatibility.
// When applications will be updated for some time, this flag will be
// set to its real value: 1
#define DISPLAY_ENABLE 3

#define DEFAULT_INFINITE -1

#define DEFAULT_NB_PROFILED_FRAMES

#define REMAP_ONCE 0
#define REMAP_ALWAYS 1

// specific to Microsoft Visual Studio
// disable warnings about fopen
#define _CRT_SECURE_NO_WARNINGS
#pragma warning (disable:4996)

#endif  /* _ORCC_UTIL_H_ */

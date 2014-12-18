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
#include <time.h>
#include <string.h>

#include "orcc.h"
#include "native_util.h"
#include "util.h"
#include "options.h"

#define LOOP_NUMBER 1

const int PRINT_SPEED = 0;

static FILE *file = NULL;
static int stop;
static clock_t startTime;
static unsigned int nbByteRead = 0;


/*****************************
 	 Read from stdin
 *****************************/
#ifdef _WIN32
#include <Winsock2.h>

char getLastUserChar() {
    HANDLE tui_handle = GetStdHandle(STD_INPUT_HANDLE);
    DWORD tui_evtc;
    char retVal = 0;
    INPUT_RECORD tui_inrec;
    DWORD tui_numread;
    bool tui_havehappened = false;

    GetNumberOfConsoleInputEvents(tui_handle,&tui_evtc);
    while (tui_evtc > 0) {
        ReadConsoleInput(tui_handle,&tui_inrec,1,&tui_numread);
        if (tui_inrec.EventType == KEY_EVENT) {
            if (tui_inrec.Event.KeyEvent.bKeyDown) {
                retVal = tui_inrec.Event.KeyEvent.uChar.AsciiChar;
                tui_havehappened = true;
            }
        }
        GetNumberOfConsoleInputEvents(tui_handle,&tui_evtc);
    }

    return retVal;
}

#elif __linux

char getLastUserChar() {
	char val = 0;
    fd_set rfds;
    struct timeval tv;
    int retval;

    FD_ZERO(&rfds);
    FD_SET(0, &rfds);

    tv.tv_sec = 0;
    tv.tv_usec = 1;

   retval = select(1, &rfds, NULL, NULL, &tv);

   if (retval > 0) {
        val = getchar();
    }
	return val;
}

#endif


/*****************************
 	 Read from file
 *****************************/

// count number of times file were read
// This variable is deprecated and will be removed in the future. Please don't use it anymore.
int loopsCount;

void printSpeed(void) {
    double executionTime;
    double speed;

    executionTime = (double)(clock() - startTime)/CLOCKS_PER_SEC;
    speed = nbByteRead / executionTime;
    speed /= 1024;
    printf("Speed : %f Kib/s\n",speed);
}

// Called before any *_scheduler function.
void source_init() {
    stop = 0;

    if (opt->input_file == NULL) {
        print_usage();
        fprintf(stderr, "No input file given!\n");
        exit(1);
    }

    file = fopen(opt->input_file, "rb");
    if (file == NULL) {
        if (opt->input_file == NULL) {
            opt->input_file = "<null>";
        }

        fprintf(stderr, "could not open file \"%s\"\n", opt->input_file);
        exit(1);
    }
    if(PRINT_SPEED) {
        atexit(printSpeed);
    }
    startTime = clock();
    loopsCount = opt->nbLoops;
}

long long source_open(char* fileName) {
    char fullPathName[256];
    FILE *file = NULL;

    stop = 0;
    if(opt->input_directory == NULL) {
        file = fopen(fileName, "rb");
        if (file == NULL) {
            fprintf(stderr, "could not open file \"%s\"\n", fileName);
            exit(1);
        }
    }
    else {
        if(strlen(fileName)+strlen(opt->input_directory)>=256) {
            fprintf(stderr, "Path too long : input_directory : %s ; fileName : %s\n", opt->input_directory, fileName);
            exit(-1);
        }
        strcpy(fullPathName, opt->input_directory);
        strcat(fullPathName, fileName);
        file = fopen(fullPathName, "rb");
        if (file == NULL) {
            fprintf(stderr, "could not open file \"%s\"\n", fullPathName);
            exit(1);
        }
    }

    if(PRINT_SPEED) {
        atexit(printSpeed);
    }
    startTime = clock();
    loopsCount = opt->nbLoops;
    return (long long)file;
}

unsigned int source_getNbLoop(void)
{
    return opt->nbLoops;
}

void source_exit(int exitCode)
{
    if(exitCode != 0){
        exit(exitCode);
    } else {
        // compareErrors' default value is 0
        exit(compareErrors);
    }
}

unsigned int source_sizeOfFile() {
    return fsize(file);
}

int source_sizeOfFileFd(long long fdVal) {
    FILE* fd = (FILE*) fdVal;
    return fsize(fd);
}

int source_is_stopped() {
    return stop;
}

void source_rewind() {
    if(file != NULL) {
        rewind(file);
    }
}

void source_rewindFd(long long fdVal) {
    FILE* fd = (FILE*) fdVal;
    if(fd != NULL) {
        rewind(fd);
    }
}

void source_close() {
    if(file != NULL) {
        int n = fclose(file);
    }
}

void source_closeFd(long long fdVal) {
    FILE* fd = (FILE*) fdVal;
    if(fd != NULL) {
        int n = fclose(fd);
    }
}

unsigned int source_readByte(){
    unsigned char buf[1];
    int n = fread(&buf, 1, 1, file);

    if (n < 1) {
        if (feof(file)) {
            printf("warning\n");
            rewind(file);
            n = fread(&buf, 1, 1, file);
        }
        else {
            fprintf(stderr,"Problem when reading input file.\n");
        }
    }
    nbByteRead += 8;
    return buf[0];
}


void source_readNBytes(unsigned char *outTable, unsigned int nbTokenToRead){
    int n = fread(outTable, 1, nbTokenToRead, file);

    if(n < nbTokenToRead) {
        fprintf(stderr,"Problem when reading input file.\n");
        exit(-4);
    }
    nbByteRead += nbTokenToRead * 8;
}


void source_readNBytesFd(long long fdVal, unsigned char *outTable, unsigned int nbTokenToRead){
    FILE* fd = (FILE*) fdVal;
    int n = fread(outTable, 1, nbTokenToRead, fd);

    if(n < nbTokenToRead) {
        fprintf(stderr,"Problem when reading input file.\n");
        exit(-4);
    }
    nbByteRead += nbTokenToRead * 8;
}

// This function is deprecated and will be removed in the future. Please don't use it anymore.
void source_decrementNbLoops(){
    --loopsCount;
}

// This function is deprecated and will be removed in the future. Please don't use it anymore.
int source_isMaxLoopsReached(){
    return opt->nbLoops != DEFAULT_INFINITE && loopsCount <= 0;
}

/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 *
 * Redistribution and use in compare and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of compare code must retain the above copyright notice,
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

#include "orcc.h"
#include "util.h"
#include "options.h"

static FILE        *cmpFile ;

// Called before any *_scheduler function.
void compare_init() {
    if (opt->yuv_file == NULL) {
        print_usage();
        fprintf(stderr, "No compare file given!\n");
        exit(1);
    }
    if (cmpFile == NULL) {
        cmpFile = fopen(opt->yuv_file, "rb");
    }
    if (cmpFile == NULL) {
        if (opt->yuv_file == NULL) {
            opt->yuv_file = "<null>";
        }
        fprintf(stderr, "could not open file \"%s\"\n", opt->yuv_file);
        exit(1);
    }
}

void compare_close() {
    if(cmpFile != NULL) {
        int n = fclose(cmpFile);
    }
}

void compare_byte(unsigned char in){
    unsigned char buf[1];
    int n = fread(&buf, 1, 1, cmpFile);
    if (n < 1) {
        fprintf(stderr,"Problem when reading compare file.\n");
        compare_close();
        exit(-4);
    }
    if( buf[0] != in ) {
        fprintf(stderr,"Compare Error.\n");
        compare_close();
        exit(-5);
    }
}

void compare_NBytes(unsigned char * inTable, unsigned short nbTokenToRead){
    unsigned char *outTable = (unsigned char *) malloc(nbTokenToRead);
    int n = fread(outTable, 1, nbTokenToRead, cmpFile);
    if(n < nbTokenToRead) {
        fprintf(stderr,"Problem when reading compare file.\n");
        compare_close();
        exit(-5);
    }
    while(n>0) {
        if( outTable[n-1] != inTable[n-1] ) {
            fprintf(stderr,"Compare Error.\n");
            exit(-5);
        }
        n--;
    }
    free(outTable);
}

void compare_NBytesNext(unsigned char * inTable, unsigned short nbTokenToRead, unsigned short display){
    unsigned char *outTable = (unsigned char *) malloc(nbTokenToRead);
    int n;
    int noEqual = 1;
    while (noEqual == 1) {
        n = fread(outTable, 1, nbTokenToRead, cmpFile);
        if(n < nbTokenToRead) {
            fprintf(stderr,"Problem when reading compare file.\n");
            compare_close();
            exit(-5);
        }
        noEqual = 0;
        while(n>0) {
            if( outTable[n-1] != inTable[n-1] ) {
                noEqual = 1;
            }
            n--;
        }
        if (noEqual == 1 && display != 0) printf("%d \n",display);
    }
    free(outTable);
}

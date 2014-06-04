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

#include "options.h"
#include "trace.h"

#include <time.h>

static clock_t startTime;
static clock_t mappingTime;
static clock_t relativeStartTime;
static int lastNumPic;
static int numPicturesDecoded;
static int numAlreadyDecoded;
static int partialNumPicturesDecoded;

static void print_fps_avg() {
    clock_t endTime = clock();

    float decodingTime = (endTime - startTime) / CLOCKS_PER_SEC;
    float framerate = numPicturesDecoded / decodingTime;

    print_orcc_trace(ORCC_VL_QUIET, "%i images in %f seconds: %f FPS",
                     numPicturesDecoded, decodingTime, framerate);
}

static void print_fps_mapping() {
    clock_t endTime = clock();

    int numPicturesDecodedMapping = numPicturesDecoded - numAlreadyDecoded;
    float decodingTime = (endTime - mappingTime) / CLOCKS_PER_SEC;
    float framerate = numPicturesDecodedMapping / decodingTime;

    print_orcc_trace(ORCC_VL_QUIET, "PostMapping : %i images in %f seconds: %f FPS",
                     numPicturesDecodedMapping, decodingTime, framerate);
}

void fpsPrintInit() {
    startTime = clock();
    numPicturesDecoded = 0;
    partialNumPicturesDecoded = 0;
    lastNumPic = 0;
    atexit(print_fps_avg);
    relativeStartTime = startTime;
}

void fpsPrintInit_mapping() {
    mappingTime = clock();
    numAlreadyDecoded = numPicturesDecoded;
    atexit(print_fps_mapping);
}


void fpsPrintNewPicDecoded(void) {
    unsigned int endTime;
    numPicturesDecoded++;
    partialNumPicturesDecoded++;
    endTime = clock();

    float relativeTime = (endTime - relativeStartTime) / CLOCKS_PER_SEC;

    if(relativeTime >= 5) {
        float framerate = (numPicturesDecoded - lastNumPic) / relativeTime;
        print_orcc_trace(ORCC_VL_QUIET, "%f images/sec", framerate);

        relativeStartTime = endTime;
        lastNumPic = numPicturesDecoded;
    }
}

int get_partialNumPicturesDecoded() {
    return partialNumPicturesDecoded;
}

void reset_partialNumPicturesDecoded() {
    partialNumPicturesDecoded = 0;
}

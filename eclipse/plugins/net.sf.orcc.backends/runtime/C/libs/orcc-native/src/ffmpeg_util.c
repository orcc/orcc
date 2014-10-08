/*
 * Copyright (c) 2014, INSA of Rennes
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
 *   * Neither the name of the INSA of Rennes nor the names of its
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
#include <string.h>

#include "ffmpeg_util.h"

#include "orcc.h"
#include "util.h"
#include "options.h"

#include <stdio.h>
#include <string.h>

#include <libavformat/avformat.h>

static AVFormatContext *pFormatCtx = NULL;
static AVPacket *pPacket = NULL;

void ffmpeg_initSource() {
    if (opt->input_file == NULL) {
        print_usage();
        fprintf(stderr, "No input file given!\n");
        exit(1);
    }

    pPacket = av_malloc(sizeof(AVPacket));
    av_register_all();
    pFormatCtx = avformat_alloc_context();

    if (avformat_open_input(&pFormatCtx, opt->input_file, NULL, NULL) != 0) {
        printf("%s", opt->input_file);
        exit(1); // Couldn't open file
    }
    av_dump_format(pFormatCtx, 0, opt->input_file, 0);
}

void ffmpeg_closeSource() {
    if(pPacket != NULL) {
        av_free(pPacket);
    }
}

void ffmpeg_readFrame(u8 data[MAX_FRAME_DELAY][MAX_FRAME_SIZE], int size[MAX_FRAME_DELAY], int index) {
    int n = av_read_frame(pFormatCtx, pPacket);

    if(n == AVERROR_EOF) {
        // Restart the reading from the beginning
        avformat_close_input(&pFormatCtx);
        avformat_open_input(&pFormatCtx, opt->input_file, NULL, NULL);
        av_dump_format(pFormatCtx, 0, opt->input_file, 0);
        n = av_read_frame(pFormatCtx, pPacket);

    }
    if(n != 0) {
        fprintf(stderr,"Problem when reading input file.\n");
        exit(-4);
    }
    if(pPacket->size > MAX_FRAME_SIZE) {
        fprintf(stderr,"Frame size exceeds the maximum value.\n");
        exit(-4);
    }

    memcpy(data[index], pPacket->data, pPacket->size);
    size[index] = pPacket->size;
}

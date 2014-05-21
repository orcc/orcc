/*
 * Copyright (c) 2013, EPFL
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
 
#include <SDL.h>
#include "util.h"

#define AUDIO_BUFFER_SIZE 128000 * 64

extern void audio_fill(void *udata, Uint8 *stream, int len); 
static Uint8 * audio_chunk; 
static Uint32 audio_len = AUDIO_BUFFER_SIZE; 
static Uint8 * audio_pos; 
SDL_AudioSpec wanted; 
SDL_AudioSpec wav_spec; 
SDL_AudioCVT cvt; 
int nBytesRead = 0;

Uint16 audio_checkFormat(unsigned short bitPS){
    
    if (bitPS == 8)
        return AUDIO_U8;
    else if (bitPS == 16)
        return AUDIO_S16;
    else {
        fprintf(stderr, "Format of the file is incorrect (only 8 or 16 bits per sample are supported).");
        getchar();
        exit(0);
    }
}

void audio_initAudioFormat(unsigned int SampleRate, unsigned short SampleSizeInBits, unsigned short Channels){
    SDL_Init(SDL_INIT_AUDIO); 
    wanted.freq = SampleRate; 
    wanted.format = audio_checkFormat(SampleSizeInBits); 
    wanted.channels = Channels;
    wanted.samples = 1024;  /* Good low-latency value for callback */ 
    wanted.callback = audio_fill; 
    wanted.userdata = NULL; 
    if ( SDL_OpenAudio(&wanted, NULL) < 0 ) { 
        fprintf(stderr, "Couldn't open audio: %s\n", SDL_GetError()); 
        return;
    } 
    SDL_PauseAudio(1); 
    SDL_BuildAudioCVT(&cvt, audio_checkFormat(SampleSizeInBits), Channels, SampleRate, wanted.format, wanted.channels, wanted.freq);
    audio_chunk = (Uint8 *)malloc(audio_len * sizeof(Uint8));
    memset(audio_chunk, 0, audio_len);
}

void audio_receive(unsigned char data){
    audio_chunk[nBytesRead] = data;
    nBytesRead++; 
}

void audio_play(){
    audio_len = nBytesRead;
    cvt.buf = (Uint8 *)malloc(audio_len * cvt.len_mult); 
    memcpy(cvt.buf, audio_chunk, audio_len); 
    SDL_ConvertAudio(&cvt);  
    memset(audio_chunk, 0, audio_len);
    audio_pos = cvt.buf; 
    SDL_PauseAudio(0); 
    while ( audio_len > 0 ) { 
        SDL_Delay(100);         /* Sleep 1/10 second */ 
    } 
    audio_len = AUDIO_BUFFER_SIZE;
    nBytesRead = 0;
    SDL_PauseAudio(1); 
}

void audio_close(){
    free(audio_chunk);
    free(cvt.buf);
    SDL_CloseAudio(); 
    SDL_Quit(); 
}

int audio_bufferFull(){
    if (nBytesRead == audio_len) {
        return 1;
    } else {
        return 0;
    }
}

void audio_fill(void * udata, Uint8 * stream, int len) 
{ 
  if ( audio_len == 0 ) 
    return; 
  len = ( len > audio_len ? audio_len : len ); 
  SDL_MixAudio(stream, audio_pos, len, SDL_MIX_MAXVOLUME); 
  audio_pos += len; 
  audio_len -= len; 
}

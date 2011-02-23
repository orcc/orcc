/*
 * Author : Endri Bezati (endri.bezati@epfl.ch)
 * Copyright (c) 2009, EPFL SCI-STI-MM
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

#include "orcc.h"
#include "orcc_fifo.h"
#include "orcc_util.h"

// from APR
/* Ignore Microsoft's interpretation of secure development
 * and the POSIX string handling API
 */
#if defined(_MSC_VER) && _MSC_VER >= 1400
#ifndef _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_DEPRECATE
#endif
#pragma warning(disable: 4996)
#endif

static FILE *F = NULL;
static int cnt = 0;

// Called before any *_scheduler function.
void writer_initialize() {
	if (write_file == NULL) {
		print_usage();
		fprintf(stderr, "No write file given!\n");
		wait_for_key();
		exit(1);
	}

	F = fopen(write_file, "wb");
	if (F == NULL) {
		if (write_file == NULL) {
			write_file = "<null>";
		}

		fprintf(stderr, "could not open file \"%s\"\n", write_file);
		wait_for_key();
		exit(1);
	}else{
		fseek(F,0,SEEK_SET);	
	}
}

extern struct fifo_u8_s *writer_In;
extern struct fifo_i32_s *writer_EOF;

enum states {
	s_Test = 0,
	s_Write
};

static char *stateNames[] = {
	"s_Test",
	"s_Write"
};

static enum states _FSM_state = s_Test;

void writer_scheduler(struct schedinfo_s *si) {
	int i = 0;
	i32 EOF_buf[1];
	i32 *pEOF;
	i32 eof_1;
	u8 In_buf[1];
	u8 *In;
	u8 wr;
	

	// jump to FSM state 
	switch (_FSM_state) {
	case s_Test:
		goto l_Test;
	case s_Write:
		goto l_Write;
	default:
		printf("unknown state: %s\n", stateNames[_FSM_state]);
		wait_for_key();
		exit(1);
	}

	// FSM transitions

l_Test:
	if ( fifo_i32_has_tokens(writer_EOF, 0, 1) ) {
		pEOF = fifo_i32_read(writer_EOF, EOF_buf, 0, 1);
		eof_1 = pEOF[0];
		if (eof_1 == 0) {				
			i++;
			fifo_i32_read_end(writer_EOF, 0, 1);
			goto l_Write;
		}else{
			//Exit the program, EOF is reached
			fclose(F);
			fifo_i32_read_end(writer_EOF, 0, 1);
			exit(666);
		}
	} else {
		_FSM_state = s_Test;
		si->num_firings = i;
		si->reason = starved;
		si->ports = 0x02;
		return;
	}

l_Write:
	if (fifo_u8_has_tokens(writer_In, 0, 1)) {
		In = fifo_u8_read(writer_In, In_buf, 0, 1);
		wr = In[0];
	
		fseek(F,sizeof(u8)*cnt,SEEK_SET);
		fwrite(&wr,sizeof(u8),1,F);
		cnt++;	
		
		fifo_u8_read_end(writer_In, 0, 1);
		
		i++;
		goto l_Test;
	} else {
		_FSM_state = s_Write;
		si->num_firings = i;
		si->reason = starved;
		si->ports = 0x01;
		return;
	}

}



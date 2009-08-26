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

#include "fifo.h"
#include "json_reader.h"

extern int token_producer_NUM_TOKENS;
extern char * token_producer_FILE_NAME;
extern char * token_producer_PORT_NAMES[8];

extern struct fifo_s *token_producer_output01;
extern struct fifo_s *token_producer_output02;
extern struct fifo_s *token_producer_output03;
extern struct fifo_s *token_producer_output04;
extern struct fifo_s *token_producer_output05;
extern struct fifo_s *token_producer_output06;
extern struct fifo_s *token_producer_output07;
extern struct fifo_s *token_producer_output08;

static struct fifo_s *fifos[8];

static json_t *token = NULL;

void source_set_file_name(const char *file_name) {
	// does not do anything.
}

static void init() {
	token = read_json(token_producer_FILE_NAME);
	fifos[0] = token_producer_output01;
	fifos[1] = token_producer_output02;
	fifos[2] = token_producer_output03;
	fifos[3] = token_producer_output04;

	fifos[4] = token_producer_output05;
	fifos[5] = token_producer_output06;
	fifos[6] = token_producer_output07;
	fifos[7] = token_producer_output08;
}

int token_producer_scheduler() {
	static int numTokens = 0;
	static int finished = 0;
	int res = 1;
	int *ptr;

	if (!finished) {
		if (token == NULL) {
			init();
			printf("token_producer init finished\n");
		}
		
		while (res && numTokens < token_producer_NUM_TOKENS) {
			json_t *portNumber = token->child;
			json_t *tokenValue = portNumber->next;

			int fifoNumber = atoi(portNumber->text);
			struct fifo_s *fifo = fifos[fifoNumber];
			int value;
			
			if (tokenValue->type == JSON_FALSE) {
				value = 0;
			} else if (tokenValue->type == JSON_TRUE) {
				value = 1;
			} else {
				value = atoi(tokenValue->text);
			}

			if ( fifoNumber == 3)
				value += 0;

			if (hasRoom(fifo, 1)) {
				ptr = getWritePtr(fifo, 1);
				if (fifo->elt_size == 1) {
					((char *) ptr)[0] = value;
				} else if (fifo->elt_size == 2) {
					((short *) ptr)[0] = value;
				} else if (fifo->elt_size == 4) {
					((int *) ptr)[0] = value;
				} else if (fifo->elt_size == 8) {
					((long long *) ptr)[0] = value;
				}
				numTokens++;

				// point to the next record.
				token = token->next;
			} else {
				res = 0;
			}
		}

		finished = (numTokens == token_producer_NUM_TOKENS);
		if (finished) {
			printf("token_producer finished\n");
		}
	}

	return 0;
}

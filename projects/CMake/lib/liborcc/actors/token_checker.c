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

extern int token_checker_NUM_TOKENS;
extern char * token_checker_FILE_NAME;
extern char * token_checker_PORT_NAMES[8];

extern void pause();

extern struct fifo_s *token_checker_input01;
extern struct fifo_s *token_checker_input02;
extern struct fifo_s *token_checker_input03;
extern struct fifo_s *token_checker_input04;
extern struct fifo_s *token_checker_input05;
extern struct fifo_s *token_checker_input06;
extern struct fifo_s *token_checker_input07;
extern struct fifo_s *token_checker_input08;

static struct fifo_s *fifos[8];

static json_t *token = NULL;

static void init() {
	token = read_json(token_checker_FILE_NAME);
	fifos[0] = token_checker_input01;
	fifos[1] = token_checker_input02;
	fifos[2] = token_checker_input03;
	fifos[3] = token_checker_input04;

	fifos[4] = token_checker_input05;
	fifos[5] = token_checker_input06;
	fifos[6] = token_checker_input07;
	fifos[7] = token_checker_input08;
}

#define check(fifoNumber, numTokens, expected, value) \
	if (expected != value) { \
		printf("port %i, token number %i, got %i, expected %i\n", \
			fifoNumber, numTokens, value, expected); \
		pause(); \
		exit(-1); \
	}

int token_checker_scheduler() {
	static int numTokens = 0;
	static int finished = 0;
	int res = 1;
	void *ptr;

	if (!finished) {
		if (token == NULL) {
			init();
			printf("token_checker init finished\n");
		}
	
		while (res && numTokens < token_checker_NUM_TOKENS) {
			json_t *portNumber = token->child;
			json_t *tokenValue = portNumber->next;

			int fifoNumber = atoi(portNumber->text);
			struct fifo_s *fifo = fifos[fifoNumber];
			int expected;

			
			
			if (tokenValue->type == JSON_FALSE) {
				expected = 0;
			} else if (tokenValue->type == JSON_TRUE) {
				expected = 1;
			} else {
				expected = atoi(tokenValue->text);
			}


			if (hasTokens(fifo, 1)) {
				ptr = getReadPtr(fifo, 1);
				if (fifo->elt_size == 1) {
					check(fifoNumber, numTokens, expected, ((char *) ptr)[0]);
				} else if (fifo->elt_size == 2) {
					check(fifoNumber, numTokens, expected, ((short *) ptr)[0]);
				} else if (fifo->elt_size == 4) {
					check(fifoNumber, numTokens, expected, ((int *) ptr)[0]);
				} else if (fifo->elt_size == 8) {
					check(fifoNumber, numTokens, expected, ((long long *) ptr)[0]);
				}

				


				numTokens++;

				// point to the next record.
				token = token->next;
			} else {
				res = 0;
			}
		}

		finished = (numTokens == token_checker_NUM_TOKENS);
		//printf("num of token %d \n", numTokens);
		if (finished) {
			printf("token_checker finished\n");
		}
	}

	return 0;
}

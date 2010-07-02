#include <stdio.h>
#include <stdlib.h>

#include "fifo.h"

int out;
struct fifo_s* fifoIn;
struct fifo_s** fifoOut;


int broadcast_sched() {
	short *tok_input;
	short *tok_output;
	int process;
	int i;
	struct fifo_s** fifoO;
	
	process = hasTokens(fifoIn, 1);
	
	if (process == 1){
		for (fifoO = fifoOut, i=0; i < out; i++, fifoO++){
			process = process && hasRoom(*fifoO, 1);
		}

		while (process == 1) {
			tok_input = (short*)getReadPtr(fifoIn, 1);
			for (fifoO = fifoOut, i=0; i < out; i++, fifoO++){
				tok_output = (short*)getWritePtr(*fifoO, 1);

				*tok_output = *tok_input;
		
				setWriteEnd(*fifoO);
			}
			setReadEnd(fifoIn);
			process = hasTokens(fifoIn, 1);
			for (fifoO = fifoOut, i=0; i < out; i++, fifoO++){
				process = process && hasRoom(*fifoO, 1);
			}
		}			
	}

	return 0;
}

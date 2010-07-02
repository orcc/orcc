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
#ifndef FIFO_H
#define FIFO_H

#include <string.h>

/** lock free fifo ring buffer structure */
static struct fifo_s {
	/** the size of an element */
	int elt_size;
	/** size of the ringbuffer (in elements) */
	int size;
	/** the memory containing the ringbuffer */
	char *contents;
	/** the current position of the reader */
	int read_ind;
	/** the current position of the writer */
	int write_ind;
	/** the next position of the reader */
	int end_read;
	/** the next position of the writer */
	int end_write;
	/** write malloc pointer */
	char* malloc_ptrW;
	/** read malloc pointer */
	char* malloc_ptrR;
	/** peek malloc pointer */
	char* malloc_ptrP;
};



#define contents(fifo, ptr) (& (fifo)->contents[(ptr) * (fifo)->elt_size])

static void *getPeekPtr(struct fifo_s *fifo, int n) {
	int end_read;
	end_read = fifo->read_ind + n;
	
	if (end_read > fifo->size) {
		//Data access end of the fifo size
		unsigned int nEltSize;
		
		//Set the location to the begin of the fifo
		end_read = end_read - fifo->size;
		
		//Size of the data in the end of the fifo
		nEltSize = (unsigned int) ((fifo->size - fifo->read_ind) * fifo->elt_size) ;

		//Free previous allocation of peek
		if (fifo->malloc_ptrP != NULL){
			free (fifo->malloc_ptrP);
			fifo->malloc_ptrP = NULL;
		}
		//Allocate the data out
		fifo->malloc_ptrP = malloc(fifo->elt_size * n);

		//Copy the end of the fifo
		memcpy (fifo->malloc_ptrP,((char *)fifo->contents) + (fifo->read_ind * fifo->elt_size),
			 nEltSize);
		
		//Copy the rest of the data in the fifo 's beginning
		memcpy (((char *)fifo->malloc_ptrP) + nEltSize, ((char *)fifo->contents),
			 fifo->elt_size * end_read );

		return fifo->malloc_ptrP;

	} else {
		return contents(fifo, fifo->read_ind);
	}
}

static void *getReadPtr(struct fifo_s *fifo, int n) {
	
	//Set the next read index
	fifo->end_read = fifo->read_ind + n;
	
	if (fifo->end_read > fifo->size){
		//Data access end of the fifo size
		unsigned int nEltSize;
		
		//Set the location to the begin of the fifo
		fifo->end_read = fifo->end_read - fifo->size;
		
		//Size of the data in the end of the fifo
		nEltSize = (unsigned int) ((fifo->size - fifo->read_ind) * fifo->elt_size) ;

		//Allocate the data out
		fifo->malloc_ptrR = malloc(fifo->elt_size * n);

		//Copy the end of the fifo
		memcpy (fifo->malloc_ptrR,((char *)fifo->contents) + (fifo->read_ind * fifo->elt_size),
			 nEltSize);
		
		//Copy the rest of the data in the fifo 's beginning
		memcpy (((char *)fifo->malloc_ptrR) + nEltSize, ((char *)fifo->contents),
			 fifo->elt_size * fifo->end_read );

		return fifo->malloc_ptrR;

	}else {
		
		//Return data
		return contents(fifo, fifo->read_ind);
	}
}


static void setReadEnd(struct fifo_s *fifo) {
	//Update read index
	fifo->read_ind = fifo->end_read;
	
	if (fifo->malloc_ptrR != NULL){
		free(fifo->malloc_ptrR);
		fifo->malloc_ptrR = NULL;
	}
}

static int hasRoom(struct fifo_s *fifo, int n) {
	//Test if distance between the read ptr and the write is longer than n
	if (fifo->read_ind >  fifo->write_ind) {
		return fifo->read_ind - fifo->write_ind > n;
	} else {
		return fifo->size - fifo->write_ind + fifo->read_ind > n;
	}
}

static int hasTokens(struct fifo_s *fifo, int n) {
	//Test if distance between the read ptr and the write is shorter than n
	if (fifo->write_ind >= fifo->read_ind) {
		return  fifo->write_ind - fifo->read_ind >= n;
	} else {
		return fifo->size - fifo->read_ind + fifo->write_ind >= n;
	}
}

static void *getWritePtr(struct fifo_s *fifo, int n) {
	//Set the next write index
	fifo->end_write = fifo->write_ind + n;
	
	if (fifo->end_write > fifo->size){
	
		//Set the location to the begin of the fifo
		fifo->end_write = fifo->end_write - fifo->size;

		//Allocate the data out
		fifo->malloc_ptrW = malloc(fifo->elt_size * n);

		return fifo->malloc_ptrW;
	}else {
		//Return fifo adress for data
		return contents(fifo, fifo->write_ind);
	}
}

static void setWriteEnd(struct fifo_s *fifo) {
	if (fifo->malloc_ptrW != NULL) {
		//Data access end of the fifo size
		unsigned int nEltSize;

		//Size of the data in the end of the fifo
		nEltSize = (unsigned int) ((fifo->size - fifo->write_ind) * fifo->elt_size) ;
			
		//Copy data int the end of the fifo
		memcpy (((char *)fifo->contents) + (fifo->write_ind * fifo->elt_size),
			fifo->malloc_ptrW, nEltSize);
		
		//Copy the rest of data in the fifo's beginning
		memcpy ((char *)fifo->contents, ((char *)fifo->malloc_ptrW) + nEltSize, fifo->elt_size * fifo->end_write);

		//Free malloc
		free(fifo->malloc_ptrW);
		fifo->malloc_ptrW = NULL;
	}

	//Update write index
	fifo->write_ind = fifo->end_write;
	
}

#endif

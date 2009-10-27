// basic scheduler: weird FIFO management

#include <string.h>
#include <stdlib.h>

#include "defs.h"

static struct fifo_s {
	int elt_size;
	int size;
	char *contents;
	int read_ind;
	int end_read;
	int write_ind;
	int end_write;
	char* malloc_ptrW;
	char* malloc_ptrR;
	char* malloc_ptrP;
};

#define SIZE 10000


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
		fifo->malloc_ptrP = NULL;
		return contents(fifo, fifo->read_ind);
	}
}

static void setEndPeek(struct fifo_s *fifo) {
	if (fifo->malloc_ptrP != NULL){
		free(fifo->malloc_ptrP);
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
		fifo->malloc_ptrR = NULL;
		return contents(fifo, fifo->read_ind);
	}
}


static void setEndRead(struct fifo_s *fifo) {
	//Update read index
	fifo->read_ind = fifo->end_read;
	
	if (fifo->malloc_ptrR != NULL){
		free(fifo->malloc_ptrR);
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
		fifo->malloc_ptrW = NULL;
		return contents(fifo, fifo->write_ind);
	}
}

static void setEndWrite(struct fifo_s *fifo) {
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
	}

	//Update write index
	fifo->write_ind = fifo->end_write;
	
}

static int array_0[SIZE * sizeof(int)];
static struct fifo_s fifo_0 = { sizeof(int), SIZE, (char *)array_0, 0, 0 };

static int array_1[SIZE * sizeof(int)];
static struct fifo_s fifo_1 = { sizeof(int), SIZE, (char *)array_1, 0, 0 };

static struct fifo_s *source_O = &fifo_0;
static struct fifo_s *compute_I = &fifo_0;
static struct fifo_s *compute_O = &fifo_1;
static struct fifo_s *sink_I = &fifo_1;

static void action_source() {
	int i;
	int *ptr = getWritePtr(source_O, n_token);
	
	for (i=0; i < n_token; i++) {
		ptr[i] = source_X;
	}
	setEndWrite(source_O);
	source_X++;
}

static int source_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (source_X < N) {
			if (hasRoom(source_O, n_token)) {
				action_source();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_compute() {
	int i;
	int *rptr = getReadPtr(compute_I, n_token);
	int *wptr = getWritePtr(compute_O, n_token);
	
	for (i=0; i <n_token; i++){
		wptr[i] = rptr[i] + i;
	}
	setEndRead(compute_I);
	setEndWrite(compute_O);
}

static int compute_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (hasTokens(compute_I, n_token)) {
			if (hasRoom(compute_O, n_token)) {
				action_compute();
				res = 1;
				i++;
			}
		}
	}

	return i;
}

static void action_sink() {
	getReadPtr(sink_I,1);
	setEndRead(sink_I);
}

static int sink_scheduler() {
	int res = 1, i = 0;
	while (res) {
		res = 0;
		if (hasTokens(sink_I, n_token)) {
			action_sink();			
			res = 1;
			i++;
		}
	}

	return i;
}

void scheduler_v5() {
	int go_on = 1;
	while (go_on) {
		go_on = 0;
		go_on += source_scheduler();
		go_on += compute_scheduler();
		go_on += sink_scheduler();
	}
}

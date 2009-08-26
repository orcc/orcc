#ifndef __LOCK_FREE_FIFO_H__
#define __LOCK_FREE_FIFO_H__

#include <string.h>

/** lock free fifo ring buffer structure */
typedef struct lock_free_fifo {
  /** Size of the ringbuffer (in elements) */
  unsigned int size;
  /** the memory containing the ringbuffer */
  void * data;
  /** the size of an element */
  size_t object_size;
  /** the current position of the reader */
  unsigned int read_index;
  /** the current position of the writer */
  unsigned int write_index;
} lff_t;

static void
lff_init (lff_t * lff, unsigned int size, size_t object_size)
{
	lff->size = size;
	lff->object_size = object_size;
	lff->read_index = 0;
	lff->write_index = 0;
	lff->data = malloc (object_size * size);
}

static lff_t *
lff_new (unsigned int size, size_t object_size)
{
	lff_t * lff;

	lff = (lff_t*)malloc (sizeof (lff_t));

	lff_init (lff, size, object_size);

	return lff;
}

static void
lff_free (lff_t * lff)
{
	free (lff->data);
}

static void
lff_destroy (lff_t * lff)
{
	lff_free (lff);
	free (lff);
}

static void lff_read (lff_t * lff, void * data) {
	memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
		 lff->object_size);
	lff->read_index++;
	if(lff->read_index==lff->size)
		lff->read_index=0;
}

static void lff_read_n (lff_t * lff, void * data, unsigned int n) {

	if(n <= lff->size - lff->read_index){
		memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
			 lff->object_size*n);
		/* FIXME: is this safe? */
		lff->read_index+=n;
		if(lff->read_index==lff->size)
			lff->read_index=0;
	}else{
		memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
			 lff->object_size * (lff->size - lff->read_index));
		memcpy (((char *)data) + (lff->size - lff->read_index) * lff->object_size,((char *)lff->data),
			 lff->object_size * (n - (lff->size - lff->read_index)));
		lff->read_index = n- (lff->size - lff->read_index);
	}
}


static void lff_peek_n (lff_t * lff, void * data, unsigned int n) {
	if(n <= lff->size - lff->read_index){
		memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
			 lff->object_size*n);
	}else{
		memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
			 lff->object_size * (lff->size - lff->read_index));
		memcpy (((char *)data) + (lff->size - lff->read_index) * lff->object_size,((char *)lff->data),
			 lff->object_size * (n - (lff->size - lff->read_index)));
	}
}

static void lff_peek(lff_t * lff, void * data) {
	memcpy (data,((char *)lff->data) + (lff->read_index * lff->object_size),
		 lff->object_size);
}

static int lff_hasTokens (lff_t * lff, unsigned int nbTokens) {
	static unsigned int wi;

	wi=lff->write_index;
	if ((wi > lff->read_index && wi - lff->read_index >= nbTokens) 
		|| (lff->read_index > wi && lff->size - lff->read_index + wi >= nbTokens)) { 
		return 1;
	} else {
		return 0;
	}
}

static void lff_write (lff_t * lff, void * data) {
	
	memcpy (((char *)lff->data) + (lff->write_index * lff->object_size),
		data, lff->object_size);
	/* FIXME: is this safe? */
	lff->write_index++;
	if(lff->write_index==lff->size)
		lff->write_index=0;
}

static void lff_write_n (lff_t * lff, void * data, unsigned int n) {

	if(n <= lff->size - lff->write_index){
		memcpy (((char *)lff->data) + (lff->write_index * lff->object_size),
			data, lff->object_size * n);
		/* FIXME: is this safe? */
		lff->write_index+=n;
		if(lff->write_index==lff->size)
			lff->write_index=0;
	}else{		
		memcpy (((char *)lff->data) + (lff->write_index * lff->object_size),
			data, lff->object_size * (lff->size - lff->write_index));
		memcpy (((char *)lff->data),
			((char *)data) + (lff->size - lff->write_index)* lff->object_size, lff->object_size * (n - (lff->size - lff->write_index)));
		lff->write_index = n- (lff->size - lff->write_index);
	}
}

static int lff_hasRoom (lff_t * lff, unsigned int room) {
	static unsigned int ri;

	/* got to read read_index only once for safety */
	ri = lff->read_index;

	/* lots of logic for when we're allowed to write to the fifo which basically
	boils down to "don't write if we're one element behind the read index" */  
	if ((ri > lff->write_index && ri - lff->write_index > room) 
		|| (lff->write_index >= ri && lff->size - lff->write_index + ri > room)) { 
		return 1;
	} else {
		return 0;
	}
}

#endif /* __LOCK_FREE_FIFO_H__ */

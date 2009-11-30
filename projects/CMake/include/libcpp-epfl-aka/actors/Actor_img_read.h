/**
 * Generated from "readBMP"
 */
#ifndef __Actor_img_read_H__
#define __Actor_img_read_H__

#include "actoraka.h"

#include <SDL.h>

enum img_read_Iports{
img_read_IPORT_SIZE
};

enum readBMP_Oports{
img_read_R,
img_read_G,
img_read_B,
img_read_WIDTH,
img_read_HEIGHT,
img_read_OPORT_SIZE
};



class Actor_img_read : public ActorGen 
{

private:
	char * fname;

	int height;

	int width;

	SDL_Surface * img;

	char * ptr;

	int pixel_depth;

	int count;

	int _FSM_state;

public:
	Actor_img_read();
	
	void initializeActor();
	void process();

private:


	enum {
		s_s0,
		s_s1,
		s_s2,
		s_s3
	};

	void header();
	void data();
	void fill();
	bool isSchedulable_header();
	bool isSchedulable_data();
	bool isSchedulable_fill();

	bool s0_state_scheduler();
	bool s1_state_scheduler();

};

#endif

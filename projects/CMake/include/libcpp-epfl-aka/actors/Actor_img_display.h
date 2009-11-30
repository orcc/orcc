/**
 * Generated from "dispPicture"
 */
#ifndef __Actor_img_display_H__
#define __Actor_img_display_H__

#include "actoraka.h"

#include <iostream>

#include <SDL.h>

enum img_display_Iports{
img_display_R,
img_display_G,
img_display_B,
img_display_WIDTH,
img_display_HEIGHT,
img_display_IPORT_SIZE
};

enum img_display_Oports{
img_display_OPORT_SIZE
};

class Actor_img_display : public ActorGen 
{

	// State variables of the actor
private:

	enum {
		s_s0,
		s_s1,
		s_s2
	};

	int m_width;

	int m_height;

	SDL_Surface * screen;
	SDL_Surface * img;
	SDL_Event event;
	char * pRd;

	int m_count;

	int _FSM_state;

public:
	// Default constructor
	Actor_img_display();
	
/*	// Init
	init();*/
	
private:
	// Functions/procedures
	// Actions

	void get_width();

	bool isSchedulable_get_width();

	void get_height();

	bool isSchedulable_get_height();

	void get_contents();

	bool isSchedulable_get_contents();

	void show_image();

	bool isSchedulable_show_image();

public:

	void initialize();
	
	// Action scheduler
	private:

	bool s0_state_scheduler();

	bool s1_state_scheduler();

	bool s2_state_scheduler();

	void img_display_init();

public:
	void initializeActor();

	void process();


};

#endif



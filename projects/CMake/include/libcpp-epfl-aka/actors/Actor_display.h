/* 
Declaration and definition of "display" 
*/

#ifndef __ACTOR_display_H__
#define __ACTOR_display_H__

#include "SDL.h"

#include "actoraka.h"


enum display_Iports{
display_B,
display_WIDTH,
display_HEIGHT,
display_IPORT_SIZE
};

enum display_Oports{
display_OPORT_SIZE
};

#define MAX_WIDTH 704
#define MAX_HEIGHT 576

class Actor_display : public ActorGen 
{

	// State variables of the actor
private:
	static SDL_Surface *m_screen;
	static SDL_Overlay *m_overlay;

	static int m_x;
	static int m_y;
	static int m_width;
	static int m_height;
	static int init;

	unsigned char img_buf_y[MAX_WIDTH * MAX_HEIGHT];
	unsigned char img_buf_u[MAX_WIDTH * MAX_HEIGHT / 4];
	unsigned char img_buf_v[MAX_WIDTH * MAX_HEIGHT / 4];

	static Uint32 start_time;
	static int num_images_start;
	static int num_images_end;


public:
	// Default constructor
	Actor_display();
	
	
private:
	// Functions/procedures
	// Actions
	void press_a_key(int code);
	static void print_fps_avg();
	void display_show_image();
	void display_write_mb(short tokens[384]);
	void display_init();
	void display_set_video(int width, int height);
	
public:
	
	void process();

};

#endif

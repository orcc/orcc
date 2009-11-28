/**
 * Generated from "img_display"
 */

#include <iostream>

#include "Actor_img_display.h"

Actor_img_display::Actor_img_display():ActorGen(img_display_IPORT_SIZE, img_display_OPORT_SIZE)
{
	m_width = 0;
	m_height = 0;
	m_count = 0;
	_FSM_state = 0;
}

void Actor_img_display::initializeActor()
{
#ifdef __TRACE_TOKENS__
	std::string strTrace("");
	strTrace = __FILE__;
	unsigned uDesc = g_oTracer.createFileDescriptor(strTrace);

	for(unsigned uIdx = 0; uIdx < img_display_IPORT_SIZE; uIdx++)
	{
	  strTrace = "m_poTabIn[" + toString(uIdx) + "] = " + toString((unsigned)m_poTabIn[uIdx]);
	  g_oTracer.addPort(uDesc, strTrace);
	}
#endif
}
	
/*Actor_img_display::init(){
	 width = 256;
	 height = 256;
}*/

	// Functions/procedures
	// Actions

void Actor_img_display::get_width() {
	int WIDTH[1];

	m_poTabIn[img_display_WIDTH]->get(WIDTH, 1);
	m_width = WIDTH[0];
}

bool Actor_img_display::isSchedulable_get_width() {
	bool _tmp1_1;
	bool _tmp0_1;
	bool _tmp0_2;
	bool _tmp0_3;

	_tmp1_1 = m_poTabIn[img_display_WIDTH]->hasTokens(1);
	if (_tmp1_1) {
		_tmp0_1 = true;
		_tmp0_2 = _tmp0_1;
	} else {
		_tmp0_3 = false;
		_tmp0_2 = _tmp0_3;
	}
	return _tmp0_2;
}

void Actor_img_display::get_height() {
	int HEIGHT[1];

	m_poTabIn[img_display_HEIGHT]->get(HEIGHT, 1);
	m_height = HEIGHT[0];
	img_display_init();
}

bool Actor_img_display::isSchedulable_get_height() {
	bool _tmp1_1;
	bool _tmp0_1;
	bool _tmp0_2;
	bool _tmp0_3;

	_tmp1_1 = m_poTabIn[img_display_HEIGHT]->hasTokens(1);
	if (_tmp1_1) {
		_tmp0_1 = true;
		_tmp0_2 = _tmp0_1;
	} else {
		_tmp0_3 = false;
		_tmp0_2 = _tmp0_3;
	}
	return _tmp0_2;
}

void Actor_img_display::get_contents() {
	short R[1];
	short G[1];
	short B[1];

	m_poTabIn[img_display_R]->get(R, 1);
	m_poTabIn[img_display_G]->get(G, 1);
	m_poTabIn[img_display_B]->get(B, 1);
	pRd[2] = (char)R[0];
	pRd[1] = (char)G[0];
	pRd[0] = (char)B[0];

	pRd += 3;
	m_count++;
}

bool Actor_img_display::isSchedulable_get_contents() {
	bool _tmp1_1;
	bool _tmp2_1;
	bool _tmp3_1;
	bool _tmp0_1;
	bool _tmp0_2;
	bool _tmp0_3;

	_tmp1_1 = m_poTabIn[img_display_R]->hasTokens(1);
	_tmp2_1 = m_poTabIn[img_display_G]->hasTokens(1);
	_tmp3_1 = m_poTabIn[img_display_B]->hasTokens(1);
	if (_tmp1_1 && _tmp2_1 && _tmp3_1) {
		_tmp0_1 = m_count < m_width * m_height;
		_tmp0_2 = _tmp0_1;
	} else {
		_tmp0_3 = false;
		_tmp0_2 = _tmp0_3;
	}
	return _tmp0_2;
}

bool Actor_img_display::isSchedulable_show_image() {
	bool _tmp0_1;
	bool _tmp0_2;
	bool _tmp0_3;

	if (true) {
		_tmp0_1 = m_count == m_width * m_height;
		_tmp0_2 = _tmp0_1;
	} else {
		_tmp0_3 = false;
		_tmp0_2 = _tmp0_3;
	}
	return _tmp0_2;
}

void Actor_img_display::initialize() {
}

	// Action scheduler

bool Actor_img_display::s0_state_scheduler() {
	bool res = false;
	if (isSchedulable_get_width()) {
		get_width();
		_FSM_state = s_s1;
		res = true;
	}
	return res;
}

bool Actor_img_display::s1_state_scheduler() {
	bool res = false;
	if (isSchedulable_get_height()) {
		get_height();
		_FSM_state = s_s2;
		res = true;
	}
	return res;
}

bool Actor_img_display::s2_state_scheduler() {
	bool res = false;
	if (isSchedulable_show_image()) {
		show_image();
		_FSM_state = s_s0;
		res = true;
	} else if (isSchedulable_get_contents()) {
		get_contents();
		_FSM_state = s_s2;
		res = true;
	}
	return res;
}


void Actor_img_display::process() {
	bool res = true;
	int i = 0;

	while (res) {
		res = false;
		switch (_FSM_state) {
		case s_s0:
			res = s0_state_scheduler();
			if (res) {
				i++;
			}
			break;
		case s_s1:
			res = s1_state_scheduler();
			if (res) {
				i++;
			}
			break;
		case s_s2:
			res = s2_state_scheduler();
			if (res) {
				i++;
			}
			break;

		default:
			std::cout << "unknown state:"+_FSM_state << std::endl;
			break;
		}
	}

	//return i;
}

void Actor_img_display::img_display_init()
{	
	screen = SDL_SetVideoMode(m_width, m_height, 24, SDL_DOUBLEBUF);
	if (screen == NULL) 
	{
		printf("Unable to set video mode: %s\n", SDL_GetError());
	}
	img = SDL_CreateRGBSurface(SDL_SWSURFACE, m_width, m_height, 24, 0,0,0,0);
	if(img == NULL)
	{
        fprintf(stderr, "CreateRGBSurface failed: %s\n", SDL_GetError());
    }
	pRd = (char *)img->pixels;

}

void Actor_img_display::show_image() {

   	SDL_BlitSurface(img, NULL, screen, NULL);
	
	//Flip the backbuffer to the primary
	SDL_Flip(screen);
	
	do {
		SDL_WaitEvent(&event);
	} while(event.type != SDL_KEYDOWN);

	//Release the surface
	SDL_FreeSurface(img);
	exit(-1);
}
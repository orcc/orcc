/**
 * Generated from "img_read"
 */

#include <iostream>
#include "Actor_img_read.h"

#ifdef _WIN32
#include <Windows.h> 
#else
#include <sys/time.h> 
typedef double LARGE_INTEGER;
#endif

extern "C" {
#include "orcc_util.h"
};


LARGE_INTEGER freq;
LARGE_INTEGER tstart;   // A point in time
LARGE_INTEGER tend; 



Actor_img_read::Actor_img_read():ActorGen(img_read_IPORT_SIZE, img_read_OPORT_SIZE)
{
	 height = 0;
	 width = 0;
	 count = 0;
	 _FSM_state = s_s0;

}
	
void Actor_img_read::initializeActor()
{
#ifdef __TRACE_TOKENS__
	std::string strTrace("");
	strTrace = __FILE__;
	unsigned uDesc = g_oTracer.createFileDescriptor(strTrace);

	for(unsigned uIdx = 0; uIdx < img_read_IPORT_SIZE; uIdx++)
	{
	  strTrace = "m_poTabIn[" + toString(uIdx) + "] = " + toString((unsigned)m_poTabIn[uIdx]);
	  g_oTracer.addPort(uDesc, strTrace);
	}
#endif
}


void Actor_img_read::header()
{
	int WIDTH[1], HEIGHT[1];

	img = SDL_LoadBMP(input_file);

	ptr = (char *)img->pixels;

	WIDTH[0] = width = img->w;
	HEIGHT[0] = height = img->h;

	m_poTabOut[img_read_WIDTH]->put(WIDTH, 1);
	m_poTabOut[img_read_HEIGHT]->put(HEIGHT, 1);

}

void Actor_img_read::data()
{
	short R[1], G[1], B[1];

	R[0] = (unsigned char)ptr[2];
	m_poTabOut[img_read_R]->put(R, 1);
	G[0] = (unsigned char)ptr[1];
	m_poTabOut[img_read_G]->put(G, 1);
	B[0] = (unsigned char)ptr[0];
	m_poTabOut[img_read_B]->put(B, 1);
	ptr += 3;
	count++;
}

void Actor_img_read::fill()
{
	short R[1], G[1], B[1];

	R[0] = 100;
	m_poTabOut[img_read_R]->put(R, 1);
	G[0] = 100;
	m_poTabOut[img_read_G]->put(G, 1);
	B[0] = 100;
	m_poTabOut[img_read_B]->put(B, 1);
	count++;
}


bool Actor_img_read::isSchedulable_header() {
	if (true) {
	}
	return true;
}

bool Actor_img_read::isSchedulable_data() {
	bool _tmp0_1;
	bool _tmp0_2;
	bool _tmp0_3;

	if (true) {
		_tmp0_1 = count < width * height;
		_tmp0_2 = _tmp0_1;
	} else {
		_tmp0_3 = false;
		_tmp0_2 = _tmp0_3;
	}
	return _tmp0_2;
}

bool Actor_img_read::isSchedulable_fill() {
	bool _tmp0_1;
	bool _tmp0_2;
	bool _tmp0_3;

	if (true) {
		_tmp0_1 = (count > width * height - 1) && (count < (width+1) * height + 3);
		_tmp0_2 = _tmp0_1;
	} else {
		_tmp0_3 = false;
		_tmp0_2 = _tmp0_3;
	}
	return _tmp0_2;
}


bool Actor_img_read::s0_state_scheduler() {
	bool res = false;
	if (isSchedulable_header()) {
		if (m_poTabOut[img_read_HEIGHT]->hasRooms(1) && m_poTabOut[img_read_WIDTH]->hasRooms(1)) {
			header();
			_FSM_state = s_s1;
			res = true;
		}
	}
	return res;
}

bool Actor_img_read::s1_state_scheduler() {
	bool res = false;
	if (isSchedulable_data()) {
		if (m_poTabOut[img_read_B]->hasRooms(1) && m_poTabOut[img_read_G]->hasRooms(1) && m_poTabOut[img_read_R]->hasRooms(1)) {
			data();
			_FSM_state = s_s1;
			res = true;
		}
	} else if (isSchedulable_fill()) {
		if (m_poTabOut[img_read_R]->hasRooms(1) && m_poTabOut[img_read_G]->hasRooms(1) && m_poTabOut[img_read_B]->hasRooms(1)) {
			fill();
			_FSM_state = s_s1;
			res = true;
		}
	}
	return res;
}


void Actor_img_read::process() {
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
			default:
				std::cout << "unknown state: %s\n" + _FSM_state << std::endl;
				break;
			}
		}
	

	//return i;
}

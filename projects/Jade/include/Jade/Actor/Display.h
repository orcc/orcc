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

/**
@brief Description of the Display class interface
@author Jerome Gorin
@file Display.h
@version 1.0
@date 03/02/2011
*/

//------------------------------
#ifndef DISPLAY_H
#define DISPLAY_H

struct SDL_Surface;
struct SDL_Overlay;
//------------------------------

//Max SDL windows size
#define MAX_WIDTH 720
#define MAX_HEIGHT 576

/**
 * @brief  This class represents a reconfiguration of a decoder
 * 
 * @author Jerome Gorin
 * 
 */
class Display {
public:
	Display(int id, bool outputFps = false);
	~Display();

	/**
     *  @brief Return the width of the current display
	 *
	 * @return the display width
	 *
     */
	int getWidth(){return width;};

	/**
     *  @brief Return the height of the current display
	 *
	 * @return the display height
	 *
     */
	int getHeight(){return height;};

	/**
     *  @brief Set the size of the current display
	 *
	 * @param width : the new width
	 *
	 * @param height : the new height
     */
	void setSize(int width, int height);

	/**
     *  @brief Return the Y buffer of the current display
	 *
	 * @return the Y buffer
	 *
     */
	void* getBuf_Y(){return img_buf_y;};

	/**
     *  @brief Return the Y buffer of the current display
	 *
	 * @return the Y buffer
	 *
     */
	void* getBuf_U(){return img_buf_u;};

	/**
     *  @brief Return the Y buffer of the current display
	 *
	 * @return the Y buffer
	 *
     */
	void* getBuf_V(){return img_buf_v;};

	/**
     *  @brief Write YUV value in the current display
	 *
	 * @param tokens : an array represention of YUV values
     */
	void display_write_mb(unsigned char tokens[384]);

	void forceStop(pthread_t* thread);

	bool printFpsEnable(){return outputFps;};

	void waitForFirstFrame();
private:
	void printFps();
	void setFirstFrame();

	/** Buffers of display */
	unsigned char img_buf_y[MAX_WIDTH * MAX_HEIGHT];
	unsigned char img_buf_u[MAX_WIDTH * MAX_HEIGHT / 4];
	unsigned char img_buf_v[MAX_WIDTH * MAX_HEIGHT / 4];

	/** Display id */
	int id;

	/** Display size */
	int width;
	int height;

	/** Current pointer position */
	int x;
	int y;

	/** Fps information */
	int frameDecoded;
	int frameStart;
	bool outputFps;
	int t;
	
	/** Thread mutex */
	pthread_mutex_t mutex;
	pthread_cond_t cond_mutex;

	/** Static functions of display */
	static void display_init();
	static void display_show_image(Display* display);
	static void display_set_video(Display* display);
	static void press_a_key(int code);

	/** Static member of display */
	static SDL_Surface *m_screen;
	static SDL_Overlay *m_overlay;
	static int m_width;
	static int m_height;
	static bool init;
	static int boundedDisplays;
	static int stopAfter;
};

static void set_video(void* ptrDisplay, int width, int height){
	Display* display = (Display*) ptrDisplay;
	display->setSize(width, height);
};

static void write_mb(void* ptrDisplay, unsigned char* tokens) {
	Display* display = (Display*) ptrDisplay;
	display->display_write_mb(tokens);
};
#endif
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
@brief Description of the JadeDisp class interface
@author Jerome Gorin
@file JadeDisp.h
@version 1.0
@date 03/02/2011
*/

//------------------------------
#ifndef JADEDISP_H
#define JADEDISP_H

#include "Jade/Actor/Display.h"

struct SDL_Surface;
struct SDL_Overlay;
//------------------------------


/**
 * @brief  This class represents a reconfiguration of a decoder
 * 
 * @author Jerome Gorin
 * 
 */
class JadeDisp : public Display {
public:
	JadeDisp(int id, bool outputFps = false);
	~JadeDisp();

	
	/**
     *  @brief Set the size of the current JadeDisp
	 *
	 * @param width : the new width
	 *
	 * @param height : the new height
     */
	void setSize(int width, int height);	

	/**
     *  @brief Write YUV value in the current JadeDisp
	 *
	 * @param tokens : an array represention of YUV values
     */
	void display_write_mb(unsigned char tokens[384]);

	void forceStop(pthread_t* thread);

	bool printFpsEnable(){return outputFps;};

	static void waitForFirstFrame();


private:
	void printFps();
	FILE* bench;

	/** Fps information */
	int frameDecoded;
	int frameStart;
	bool outputFps;
	int t;
	
	/** Thread mutex */
	static pthread_mutex_t mutex;
	static pthread_cond_t cond_mutex;

	/** Static functions of JadeDisp */
	static void display_init();
	static void display_show_image(JadeDisp* jadeDisp);
	static void display_set_video(JadeDisp* jadeDisp);
	static void press_a_key(int code);
	static void sendFirstFrameEvent();

	/** Static member of JadeDisp */
	static SDL_Surface *m_screen;
	static SDL_Overlay *m_overlay;
	static bool init;
	static int boundedDisplays;
	static int m_width;
	static int m_height;
};

#endif
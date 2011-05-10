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
@brief Description of the GpacDisp class interface
@author Olivier Labois
@file GpacDisp.h
@version 1.0
@date 06/05/2011
*/

//------------------------------
#ifndef GpacDisp_H
#define GpacDisp_H

#include "Jade/Actor/Display.h"
#include "Jade/lib_RVCDecoder/RVCDecoder.h"

struct SDL_Surface;
struct SDL_Overlay;
//------------------------------


/**
 * @brief  This class represents a reconfiguration of a decoder
 * 
 * @author Olivier Labois
 * 
 */
class GpacDisp : public Display {
public:
	GpacDisp(int id);
	~GpacDisp();

	
	/**
     *  @brief Set the size of the current GpacDisp
	 *
	 * @param width : the new width
	 *
	 * @param height : the new height
     */
	void setSize(int width, int height);	

	/**
     *  @brief Write YUV value in the current GpacDisp
	 *
	 * @param tokens : an array represention of YUV values
     */
	void display_write_mb(unsigned char tokens[384]);

	/**
     *  @brief Set pointer of value which can stop the scheduler
	 *
	 *  This value is continiously tested by the scheduler, it MUST be an int.
	 *	The scheduler only stop when this value is set to 1, otherwise the scheduler
	 *	continuously test firing rules of actors
     */
	void setStopSchPtr(int* stopSchVal) {this->stopSchVal = stopSchVal;}

	void setFramePtr(RVCFRAME* frame);


private:

	/** This is the value which can stop the scheduler */
	int* stopSchVal;
};

#endif
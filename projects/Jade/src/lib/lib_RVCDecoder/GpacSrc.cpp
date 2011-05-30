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
@brief Implementation of class Source
@author Olivier Labois
@file Source.cpp
@version 1.0
@date 03/02/2011
*/

//------------------------------
#include "Jade/Actor/GpacSrc.h"
//------------------------------

using namespace std;

GpacSrc::GpacSrc(int id) : Source(id) {
	this->id = id;
	this->cnt = 0;
	this->nal = NULL;
	this->nal_length = 0;
	this->stopSchVal = 0;
	this->saveNal = NULL;
}

GpacSrc::~GpacSrc(){
}

void GpacSrc::setNal(unsigned char* nal, int nal_length, bool AVCFile){
	this->nal = nal;
	this->nal_length = nal_length;
	this->cnt = 0;

	if(AVCFile){
		setAVCStartCode();
	}
}

void GpacSrc::source_get_src(unsigned char* tokens){
	if(*saveNal){
		setNalFifo();
	}
	
	if(!inFifo.empty()){
		*tokens = getNalFifo();
	}else if(cnt < nal_length){
		*tokens = nal[cnt];
		cnt++;
	} 
	
	//Stop scheduler
	if(cnt == nal_length || *saveNal) {
		stopSchVal = 1;
	}
}

void GpacSrc::setNalFifo(){
	for(int i = cnt ; i < nal_length; i++){
		inFifo.push_back(nal[i]);
	}
}

unsigned char GpacSrc::getNalFifo(){
	unsigned char tmp = inFifo.front();
	
	inFifo.pop_front();

	return tmp;
}

void GpacSrc::setAVCStartCode(){
	inFifo.push_back(0x00);
	inFifo.push_back(0x00);
	inFifo.push_back(0x00);
	inFifo.push_back(0x01);
}
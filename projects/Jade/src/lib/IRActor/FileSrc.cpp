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
@brief Implementation of class Display
@author Jerome Gorin
@file Source.cpp
@version 1.0
@date 03/02/2011
*/

//------------------------------
#include <iostream>
#include <cstdio>
#include <cstdlib>

#include "Jade/Actor/FileSrc.h"
//------------------------------

using namespace std;

FileSrc::FileSrc(int id) : Source(id) {
	this->id = id;
	this->cnt = 0;
	this->file = NULL;

}

FileSrc::~FileSrc(){
	if (file != NULL){
		fclose(file);
	}
}

void FileSrc::setStimulus(std::string stimulus){
		this->stimulus = stimulus;

		if (file != NULL){
			fclose(file);
		}

		file = fopen(stimulus.c_str(), "rb");
		if (file == NULL) {
			cout << "could not open file " << stimulus.c_str() << "\n.";
			exit(1);
		}
}

void FileSrc::source_get_src(unsigned char* tokens){
	int n;
	
	if (feof(file)) {
		fseek(file, 0, 0);
	}

	n = fread(tokens, 1, 1, file);
	if (n < 1) {	
		fseek(file, 0, 0);
		cnt = 0;
		n = fread(tokens, 1, 1, file);
	}
}

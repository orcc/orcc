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
@brief Description of the Source class interface
@author Jerome Gorin
@file FileSrc.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef FILESRC_H
#define FILESRC_H
#include "Jade/Actor/Source.h"

//------------------------------


/**
 * @class FileSrc
 *
 * @brief  This class represents a source that open and read a file.
 * 
 * @author Jerome Gorin
 * 
 */
class FileSrc : public Source {
public:
	/**
     *  @brief Create a new file reader for the decoder 
	 *   
	 *  @param id : the id of the decoder
     */
	FileSrc(int id);

	/**
     *  @brief Set the name of stimulus (input file)
	 *   
	 *  @param tokens : the name of stimulus
     */
	void setStimulus(std::string stimulus);

	~FileSrc();

	/**
     *  @brief Make an injection in the decoder of data from input file 
	 *   
	 *  @param tokens : the adress where data must be injected
     */
	void source_get_src(unsigned char* tokens);

protected:
	/** input stimulus */
	std::string stimulus;

	/** input file */
	FILE* file;

	/** byte read counter */
	int cnt;
};

#endif
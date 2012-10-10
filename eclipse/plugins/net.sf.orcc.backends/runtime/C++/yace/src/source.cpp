/**
 * \file
 * \author  Ghislain Roquier <ghislain.roquier@epfl.ch>
 * \version 1.0
 *
 * \section LICENSE
 *
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
 * 
 * \section DESCRIPTION
 *
 */

#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <fstream>
#include <string>

#include "get_opt.h"
#include "scheduler.h"

static std::ifstream file;

static int loopsCount;

void source_init() 
{
	if (input_file.c_str() == NULL)
	{
		std::cerr << "No input file given!" << std::endl;
		exit(1);
	}

	file.open(input_file.c_str(), std::ios::binary);
	if (file.bad())
	{
		std::cerr << "could not open file "<<  input_file << std::endl;
		exit(1);
	}

	loopsCount = nbLoops;
}

int source_sizeOfFile()
{ 
	file.seekg(0L, std::ios::end);
	long size = file.tellg();
	file.seekg(0L, std::ios::beg);
	return size;
}


void source_rewind()
{
	file.clear();
	file.seekg(0, std::ios::beg);
}

unsigned int source_readByte()
{
	return file.get();
}


void source_readNBytes(unsigned char outTable[], unsigned int nbTokenToRead)
{
	file.read((char *)outTable, nbTokenToRead);
}

unsigned int source_getNbLoop(void)
{
	return nbLoops;
}

void source_decrementNbLoops()
{
	--loopsCount;
}

bool source_isMaxLoopsReached()
{
	return nbLoops != -1 && loopsCount <= 0;
}

void source_exit(int exitCode)
{
	file.close();
	Scheduler* current_thread = (Scheduler*) Thread::currentThread();
	current_thread->done();
}

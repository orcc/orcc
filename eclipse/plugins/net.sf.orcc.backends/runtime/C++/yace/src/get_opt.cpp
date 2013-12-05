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

#include "get_opt.h"

std::string config_file;
std::string input_file;
std::string write_file;

int nbLoops = -1;
int nbFrames = -1;


GetOpt::GetOpt(int argc, char* argv[])
{
	parse(argc, argv);
}

GetOpt::~GetOpt()
{
}

void GetOpt::parse(int argc, char* argv[])
{
	std::vector<std::string> currOptionValues;
	std::string optionName;
	for (int i = 1; i < argc; i++)
	{
		if (argv[i][0] == '-')
		{
			optionName = &argv[i][1];
		}
		else
		{
			tokens[optionName].push_back(&argv[i][0]);
		}
	}
}

void GetOpt::getOptions()
{
	this->getOptionAs<std::string>("i", input_file);
	this->getOptionAs<std::string>("p", config_file);
	this->getOptionAs<std::string>("w", write_file);
	bool exists = this->getOptionAs<int>("l", nbLoops);
	if(!exists)
		nbLoops = -1;
	exists = this->getOptionAs<int>("f", nbFrames);
	if(!exists)
		nbFrames = -1;
}

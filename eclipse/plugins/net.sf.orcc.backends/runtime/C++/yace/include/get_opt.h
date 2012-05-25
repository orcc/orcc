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

#ifndef __YACE_GET_OPT_H__
#define __YACE_GET_OPT_H__

#include <string>
#include <sstream>
#include <vector>
#include <map>

typedef std::map<std::string, std::vector<std::string> > Tokens;
typedef std::map<std::string, std::vector<std::string> >::const_iterator TokensIterator;

extern std::string input_file;
extern std::string write_file;
extern std::string config_file;
extern int nbLoops;


template<typename T>
inline void convert(const std::string& s, T& res)
{
	std::stringstream ss(s);
	ss >> res;
	if (ss.fail() || !ss.eof())
	{
	}
}

/* specialization for bool */
template <> 
inline void convert(const std::string& s, bool& res)
{
	if(s == "true")
		res = true;
	else if(s == "false")
		res = false;
	else
	{
	}
}

/* specialization for std::string */
template<>
inline void convert(const std::string& s, std::string& res)
{
	res = s;
}

template<typename T> class Options;

class GetOpt
{
public:
	GetOpt(int argc, char* argv[]);
	~GetOpt();

	void parse(int argc, char* argv[]);

	template<typename T> bool getOptionAs(const std::string&, T&);

	const Tokens& getTokens() const {return tokens;};	

	void getOptions();
private:
	Tokens tokens;
};

template<typename T>
bool GetOpt::getOptionAs(const std::string& s, T& res)
{
	return Options<T>(this)(s, res);
}

template<typename T>
class Options
{
public:
	Options<T>(const GetOpt* options) : options(options) {}
	
	bool operator () (const std::string& s, T& res)
	{
		TokensIterator it = options->getTokens().find(s);
		if(it != options->getTokens().end())
		{
			convert<T>((it->second)[0], res);
			return true;
		}
		else
		{
			return false;
		}
	}
private:
	const GetOpt* options;
};

template<typename T>
class Options<std::vector<T> >
{
public:
	Options<std::vector<T> >(const GetOpt* options) : options(options) {}

	void operator () (const std::string& s, std::vector<T>& res)
	{
		Tokens tokens = options->getTokens();
		TokensIterator it = tokens.find(s);
		if(it != tokens.end())
		{
			std::vector<std::string>::iterator vec_it;
			for(vec_it = it->second.begin(); vec_it != it->second.end(); vec_it++)
			{
				T item;
				convert<T>(*vec_it, item);
				res.push_back(item);
			}
		}
		else
		{
			// option not found
		}
	}
private:
	const GetOpt* options;

};

#endif

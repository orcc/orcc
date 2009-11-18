/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne / AKATECH SA
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
 *   * Neither the name of theEcole Polytechnique Fédérale de Lausanne / AKATECH SA nor the names of its
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

#ifndef __STRING_AKA_H__
#define __STRING_AKA_H__

#include <sstream>

template <class T>
inline std::string toString(const T& t)
{
	std::ostringstream o;
	o << t;
	return o.str();
}


template <typename T> 
bool fromString(const std::string& s, T* pValue)
{
    std::stringstream ss(s);
    ss >> *pValue;
	if (ss.rdstate()==std::ios_base::failbit) return false;
    return true;
}

/*template<class T>
inline std::string toHexString(const T& t)
{
	std::ostringstream stream;

	stream << std::hex << (unsigned)t;
	std::string str(8-stream.str().length(), '0');
	return str.append(stream.str());
}*/

template<class T>
inline std::string toHexString(const T& t)
{
	char tmp[9] = "";
	unsigned uVal = (unsigned) t;
	sprintf(tmp, "%08x", uVal);
	std::string str(tmp);
	return str;
}


template <typename T> 
inline std::string toStringHeightChar(const T& d)
{
	std::string strTmp="";
	unsigned size = sizeof(unsigned)<<1;
	unsigned uTmp = (unsigned)d;
	char cChar = 0;

	for(unsigned uIdx = 0; uIdx < size; uIdx++)
	{
		cChar = (char)((uTmp>>((size - (uIdx+1))<<2)) & 0x0F);
		if (cChar < 0x0a)
		{
			cChar += 0x30;
		}
		else
		{
			cChar = cChar - 0x0a + 0x61;
		}
		strTmp.append(1, cChar);
	}
	return strTmp.c_str();
}
#endif
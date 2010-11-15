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
@brief Interface of XDFParser
@author Jerome Gorin
@file XDFParser.h
@version 1.0
@date 2010/04/12
*/

//------------------------------
#ifndef XDFPARSER_H
#define XDFPARSER_H

#include <string>
namespace llvm{
	class LLVMContext;
}

#include "Jade/Core/Network.h"
//------------------------------

/**
*
* @class XDFParser
* @brief This class defines a parser of XDF files.
*
* XDFParser is a class that parses an xdf file given, as a string filename in constructor, and
* produce a network class representing the structure of the network.
*
* @author Jerome Gorin
*
*/
class XDFParser {
private:
	std::string xdfFile; /**<XDF file to parse*/

public:
	/**
     *  @brief Constructor of the class XDFParser
     *
     *  @param	filename : name of the XDF file to parse
     */
	XDFParser (std::string filename);

	 /**
     *  @brief Destructor of the class XDFParser
     */
	~XDFParser ();

   /**
     *  @brief Start Parsing XDF file
     *
     *  @return a network class that describe the network of the dataflow, NULL if parsing failed
     *  
     */
	Network* ParseXDF (llvm::LLVMContext& C);

};

#endif

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
@brief Description of the IRUnwriter class interface
@author Jerome Gorin
@file IRUnwriter.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef IRUNWRITER_H
#define IRUNWRITER_H

#include <map>

namespace llvm{
	class ConstantInt;
	class Module;
}

class Decoder;
class JIT;
class LLVMWriter;

#include "Jade/Core/Actor.h"
#include "Jade/Core/Instance.h"
//------------------------------


/**
 * @class IRUnwriter
 *
 * @brief This class defines a unwriter that removes an instance from a decoder.
 *
 * @author Jerome Gorin
 * 
 */
class IRUnwriter{
public:

	/**
	 * Creates an instance writer on the given actor.
	 * 
	 * @param instance : Instance to write
	 */
	IRUnwriter(Decoder* decoder);

	/**
	 * Write the instance inside the given decoder.
	 * 
	 * @param decoder: the decoder to write the instance into
     *
	 * @return true if the actor is written, otherwise false
	 */
	int remove(Instance* instance);

	~IRUnwriter();

private:
	
	/** Decoder where instance is unwrite*/
	Decoder* decoder;

};

#endif
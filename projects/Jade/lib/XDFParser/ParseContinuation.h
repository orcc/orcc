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

//------------------------------
#ifndef PARSECONTINUATION_H
#define PARSECONTINUATION_H

#include "Jade/TinyXML/TinyXml.h"
//------------------------------

/**
@brief Implementation of ExprParser
@author Jerome Gorin
@file ParseContinuation.h
@version 1.0
@date 15/11/2010
*/

/*! 
*	@class ParseContinuation
*	@brief  This class defines a parse continuation, by storing the next node that shall
* be parsed along with the result already computed.
*
*/
template <class T>
class ParseContinuation {

public:
	/**
	 * @brief Creates a new parse continuation
	 *
	 * Creates a new parse continuation with the given TiXmlNode and result. The
	 * constructor stores the next sibling of node.
	 * 
	 * @param node: a TiXmlNode that will be used to resume parsing after the result
	 *            has been stored
	 * @param result : the result
	 */
	ParseContinuation (TiXmlNode* node, T result){
		if (node == NULL){
			this->node = NULL;
		}else{
			this->node = node->NextSibling();
		}
		this->result = result;
	};

	/*!
     *  @brief Destructor of the class NetworkParser
     *
     */
	~ParseContinuation (){};

	/**
	 * @brief Returns the TiXmlNode stored in this continuation.
	 * 
	 * @return the TiXmlNode stored in this continuation
	 */
	TiXmlNode* getNode() {
		return node;
	}

	/**
	 * Returns the result stored in this continuation.
	 * 
	 * @return the result stored in this continuation
	 */
	T getResult() {
		return result;
	}

private:
	TiXmlNode* node;
	T result;
};

#endif

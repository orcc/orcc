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
#ifndef TypePARSER_H
#define TypePARSER_H

#include <stdio.h>
#include <string>

#include <libxml/parser.h>
#include <libxml/tree.h>

#include "Jade/Network/Network.h"
#include "Jade/Entry/Entry.h"

#include "ExprParser.h"

namespace llvm{
	class LLVMContext;
	class Constant;
	class ConstantInt;
	class IntegerType;
	class Type;
}
//------------------------------

/*!
 * @file TypeParser.h
 * @brief Interface of TypeParser
 * @author Jerome Gorin
 * @version 0.1
 */
class TypeParser {

public:
	/*!
     *  @brief Constructor of the class TypeParser.
     *
     */
	TypeParser (llvm::LLVMContext& C);

	/*!
     *  @brief Destructor of the class NetworkParser
     *
     */
	~TypeParser ();

	/*!
     *  @brief Parses the given xmlNode as a Type element.
     *
	 *	Parses the given xmlNode element as a Type, and returns
	 * the corresponding Type.
	 *
     *  @param root : xmlNode representation of Type element
	 *
	 *  @return  a Type structure corresponding to a Type. 	 
     */
	llvm::Type* parseType(xmlNode* element);

private:
	ExprParser* exprParser;
	std::map<std::string, Entry*>* parseTypeEntries(xmlNode* element);
	llvm::IntegerType* parseTypeSize(std::map<std::string, Entry*>* entries);

	llvm::LLVMContext &Context;

};

#endif

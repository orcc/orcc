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
@brief Interface of IRConstant
@author Jerome Gorin
@file IRConstant.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef XDFCONSTANT_H
#define XDFCONSTANT_H

//------------------------------

/**
*
* @class XDFConstant
* @brief Constants XDF network fields.
*
* @author Jerome Gorin
*
*/
class XDFNetwork {
public:

	static const char* XDF_ROOT;
	static const char* NAME;
	static const char* IRAttribute;
	static const char* CONNECTION;
	static const char* CONNECTION_SRC;
	static const char* CONNECTION_SRC_PORT;
	static const char* CONNECTION_DST;
	static const char* CONNECTION_DST_PORT;
	static const char* DECL;
	static const char* ENTRY;
	static const char* EXPR;
	static const char* EXPR_OP;
	static const char* INSTANCE;
	static const char* INSTANCE_CLASS;
	static const char* INSTANCE_ID;
	static const char* INSTANCE_PARAMETER;
	static const char* KIND;
	static const char* KIND_BINOPSEQ;
	static const char* KIND_CUSTOM;
	static const char* KIND_FLAG;
	static const char* KIND_LITERAL;
	static const char* KIND_LIST;
	static const char* KIND_STRING;
	static const char* KIND_TYPE;
	static const char* KIND_UNARYOP;
	static const char* KIND_VALUE;
	static const char* KIND_VAR;
	static const char* LITERAL_BOOL;
	static const char* LITERAL_CHAR;
	static const char* LITERAL_INT;
	static const char* LITERAL_KIND;
	static const char* LITERAL_REAL;
	static const char* LITERAL_STRING;
	static const char* LITERAL_VALUE;
	static const char* PACKAGE;
	static const char* PORT;
	static const char* TYPE;
	static const char* TYPE_BOOL;
	static const char* TYPE_INT;
	static const char* TYPE_LIST;
	static const char* TYPE_STRING;
	static const char* TYPE_UINT;
};

#endif

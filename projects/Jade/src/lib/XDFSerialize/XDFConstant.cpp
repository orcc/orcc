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
@brief Implementation of XDF file keyword
@author Olivier Labois
@file XDFConstant.cpp
@version 1.0
@date 31/03/2011
*/

//------------------------------
#include "XDFConstant.h"
//------------------------------

// Define static key for XDF parsing
const char* XDFNetwork::XDF_ROOT = "XDF";
const char* XDFNetwork::NAME = "name";
const char* XDFNetwork::IRAttribute = "Attribute";
const char* XDFNetwork::CONNECTION = "Connection";
const char* XDFNetwork::CONNECTION_SRC = "src";
const char* XDFNetwork::CONNECTION_SRC_PORT = "src-port";
const char* XDFNetwork::CONNECTION_DST = "dst";
const char* XDFNetwork::CONNECTION_DST_PORT = "dst-port";
const char* XDFNetwork::DECL = "Decl";
const char* XDFNetwork::ENTRY = "Entry";
const char* XDFNetwork::EXPR = "Expr";
const char* XDFNetwork::EXPR_OP = "Op";
const char* XDFNetwork::INSTANCE = "Instance";
const char* XDFNetwork::INSTANCE_CLASS = "Class";
const char* XDFNetwork::INSTANCE_ID = "id";
const char* XDFNetwork::INSTANCE_PARAMETER = "Parameter";
const char* XDFNetwork::KIND = "kind";
const char* XDFNetwork::KIND_BINOPSEQ = "BinOpSeq";
const char* XDFNetwork::KIND_CUSTOM = "Custom";
const char* XDFNetwork::KIND_FLAG = "Flag";
const char* XDFNetwork::KIND_LIST = "List";
const char* XDFNetwork::KIND_LITERAL = "Literal";
const char* XDFNetwork::KIND_STRING = "String";
const char* XDFNetwork::KIND_TYPE = "Type";
const char* XDFNetwork::KIND_UNARYOP = "UnaryOp";
const char* XDFNetwork::KIND_VALUE = "Value";
const char* XDFNetwork::KIND_VAR = "Var";
const char* XDFNetwork::LITERAL_BOOL = "Boolean";
const char* XDFNetwork::LITERAL_CHAR = "Character";
const char* XDFNetwork::LITERAL_INT = "Integer";
const char* XDFNetwork::LITERAL_KIND = "literal-kind";
const char* XDFNetwork::LITERAL_REAL = "Real";
const char* XDFNetwork::LITERAL_STRING = "String";
const char* XDFNetwork::LITERAL_VALUE = "value";
const char* XDFNetwork::PACKAGE = "Package";
const char* XDFNetwork::PORT = "Port";
const char* XDFNetwork::TYPE = "Type";
const char* XDFNetwork::TYPE_BOOL = "bool";
const char* XDFNetwork::TYPE_INT = "int";
const char* XDFNetwork::TYPE_LIST = "List";
const char* XDFNetwork::TYPE_STRING = "String";
const char* XDFNetwork::TYPE_UINT = "uint";
const char* XDFNetwork::TYPE_SIZE = "size";
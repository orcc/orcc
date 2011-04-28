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
@brief Implementation of XDFParser
@author Jerome Gorin
@file XDFParser.cpp
@version 1.0
@date 15/11/2010
*/

//------------------------------
#include "NetworkParser.h"
#include "Jade/XDFSerialize/XDFParser.h"
#include "Jade/Util/CompressionMng.h"
//------------------------------

using namespace std;


XDFParser::XDFParser (string filename, bool verbose){
	this->verbose = verbose;
	
	//Uncompress XDF if it is compressed
	if(CompressionMng::IsGZipName(filename)){
		xdfFile = CompressionMng::uncompressGZip(filename);
	}else{
		xdfFile = filename;
	}
}

Network* XDFParser::parseXDF (llvm::LLVMContext& C){
	NetworkParser networkParser(C, xdfFile);
	return networkParser.parseNetwork();
}

Network* XDFParser::parseXDF (char* XDF, llvm::LLVMContext& C){
	NetworkParser networkParser(C, xdfFile);
	return networkParser.parseNetwork(XDF);
}


XDFParser::~XDFParser (){
}

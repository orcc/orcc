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
@brief Implementation of class CompressionMng
@author Olivier Labois
@file CompressionMng.cpp
@version 1.0
@date 15/03/2011
*/

//------------------------------
#include "Jade/Util/CompressionMng.h"


//#include <stdio.h>
#include <algorithm>
#include <iostream>
//------------------------------

#define BUFLEN	16384

using namespace std;
using namespace llvm;

void CompressionMng::compressFile(string file, string compressLevel){
	FILE* in;
	gzFile out;
	
	//Open file
	CompressionMng::checkFile(file);
	in = fopen(file.c_str(), "rb");

	//Create output file
	string outFile = file + ".gz";

	string mode = "wb" + compressLevel;
	out = gzopen(outFile.c_str(), mode.c_str());

	CompressionMng::checkFile(outFile);

	//Compress and set file in output file
	CompressionMng::compress(in, out);

	//Remove original file
	unlink(file.c_str());
}

void CompressionMng::uncompressGZip(string file){
	FILE* out;
	gzFile in;
	string outFile = file;

	//Initializes names of files (input file 
	// can be give without the .gz extension)
	if(file.find(".gz") != string::npos){
		outFile.erase(outFile.end()-3, outFile.end());
	}else{
		file += ".gz";
	}

	//Open file
	CompressionMng::checkFile(file);
	in = gzopen(file.c_str(), "rb");

	//Create output file
	out = fopen(outFile.c_str(), "wb");
	CompressionMng::checkFile(outFile);

	//Uncompress and set file in output file
	CompressionMng::uncompress(in, out);

	//Remove original file
	unlink(file.c_str());
}

void CompressionMng::checkFile(string file){
	sys::Path filePath(file);

	if(!filePath.exists()){
		//File doesn't exist
		cout << "File" << filePath.c_str() << " does not exists or be create.'\n";
		exit(0);
	}
}

void CompressionMng::compress(FILE* in, gzFile out){
	char buf[BUFLEN];
    int len;

    for (;;) {
		len = (int)fread(buf, 1, sizeof(buf), in);
        if (ferror(in)) {
            CompressionMng::error("compress : failed fread");
        }
        if (len == 0) break;

		if (gzwrite(out, buf, (unsigned)len) != len){
			CompressionMng::error("compress : failed gzwrite");
		}
    }

    fclose(in);
	if (gzclose(out) != 0){
		CompressionMng::error("compress : failed gzclose");
	}
}

void CompressionMng::uncompress(gzFile in, FILE* out){
	char buf[BUFLEN];
    int len;

    for (;;) {
        len = gzread(in, buf, sizeof(buf));
        if (len < 0){
			CompressionMng::error("uncompress : failed gzread");
		}
        if (len == 0) break;

        if ((int)fwrite(buf, 1, (unsigned)len, out) != len){
			CompressionMng::error("uncompress : failed fwrite");
		}
    }
	if (fclose(out)){
		CompressionMng::error("uncompress : failed fclose");
	}

	if (gzclose(in) != Z_OK){
		CompressionMng::error("uncompress : failed gzclose");
	}
}

void CompressionMng::error(string msg){
	cerr << msg;
	exit(1);
}
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
@brief Description of the CompressionMng class interface 
@author Olivier Labois
@file CompressionMng.h
@version 1.0
@date 15/03/2011
*/

//------------------------------
#ifndef COMPRESSIONMNG_H
#define COMPRESSIONMNG_H

#include "Jade/Compression/zlib.h"

#include "llvm/System/Path.h"

#include <string>
//------------------------------

/**
 * @class CompressionMng
 *
 *  This class contains methods for managing compression.
 *
 * @author Olivier Labois
 * 
 */
class CompressionMng {
public:
	/**
     * @brief Compress the file given in the GZip format
	 *  
	 *  The original file is remove
	 *
	 * @param file : name of the file
	 * 
	 * @param compressLevel : the compress level [1..9], 6 by default.
	 *
     */
	static void compressFile(std::string file, std::string compressLevel = "6");

	/**
     * @brief Uncompress the GZip file given.
	 *  
	 *  The original file is remove
	 *
	 * @param file : name of the file
	 *
     */
	static void uncompressGZip(std::string file);

	/**
     * @brief See if the file given is a GZip file.
	 *
	 * @param file : name of the file
	 *
	 * @return return true if the file given is a GZip file, otherwise false.
	 *
     */
	static bool IsGZipFile(std::string file) {
		llvm::sys::Path GZipFile(file + ".gz");
		return GZipFile.exists();
	};


private:

	/**
     * @brief Check if the file exists
	 *
	 *  If the file does not exists, program stop with the code 0
	 *
	 * @param file : name of the file
	 *
     */
	static void checkFile(std::string file);

	/**
     * @brief Compress in to out
	 *
	 * @param in : datas of the input file
	 *
	 * @param out : datas of the output file
	 *
     */
	static void compress(FILE* in, gzFile out);

	/**
     * @brief Uncompress in to out
	 *
	 * @param in : datas of the input file
	 *
	 * @param out : datas of the output file
	 *
     */
	static void uncompress(gzFile in, FILE* out);

	/**
     * @brief Shown the messages given and stop program whith the code 1
	 *
	 * @param msg : string of the message
	 *
     */
	static void error(std::string msg);

};

#endif
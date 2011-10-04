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
@brief Declaration Native Functions
@author Jerome Gorin
@file NativeDecl.h
@version 1.0
@date 30/05/2011
*/

//------------------------------
#ifndef NATIVEDECL_H
#define NATIVEDECL_H

#include <map>
#include <string>
//------------------------------

//External functions of natives provedures
extern "C"{
	//External functions for display
	extern void displayYUV_init();
	extern void displayYUV_displayPicture(unsigned char *pictureBufferY,
		unsigned char *pictureBufferU, unsigned char *pictureBufferV,
		unsigned short pictureWidth, unsigned short pictureHeight);
	extern char displayYUV_getFlags();


	//External functions for compare
	extern void compareYUV_init();
	extern void compareYUV_comparePicture(unsigned char *pictureBufferY, unsigned char *pictureBufferU,
                               unsigned char *pictureBufferV, unsigned short pictureWidth,
                               unsigned short pictureHeight);

	//External functions for source
	extern void source_init();
	extern void source_readNBytes(unsigned char *outTable, unsigned short nbTokenToRead);
	extern int source_sizeOfFile();
	extern void source_rewind();
	extern void printSpeed();
	extern unsigned int source_getNbLoop(void);
	extern void source_exit(int exitCode);
	extern unsigned int source_readByte();

	//Extern functions for writer
	extern void Writer_init();
	extern void Writer_write(unsigned char byte);
	extern void Writer_close();
	
	//Extern functions for fpsPrint
	extern void fpsPrintInit();
	extern void fpsPrintNewPicDecoded(void);

}

std::map<std::string, void*> createNativeMap()
{
	std::map<std::string, void*> native;

	// Link external function with their native names
	
	native["displayYUV_init"] = (void*)displayYUV_init;
	native["displayYUV_displayPicture"] = (void*)displayYUV_displayPicture;
	native["displayYUV_getFlags"] = (void*)displayYUV_getFlags;
	
	native["compareYUV_init"] = (void*)compareYUV_init;
	native["compareYUV_comparePicture"] = (void*)compareYUV_comparePicture;
	
	native["source_init"] = (void*)source_init;
	native["source_readNBytes"] = (void*)source_readNBytes;
	native["source_sizeOfFile"] = (void*)source_sizeOfFile;
	native["source_rewind"] = (void*)source_rewind;
	native["printSpeed"] = (void*)printSpeed;
	native["source_getNbLoop"] = (void*)source_getNbLoop;
	native["source_exit"] = (void*)source_exit;
	native["source_readByte"] = (void*)source_readByte;
	
	native["Writer_init"] = (void*)Writer_init;
	native["Writer_write"] = (void*)Writer_write;
	native["Writer_close"] = (void*)Writer_close;
	
	native["fpsPrintInit"] = (void*)fpsPrintInit;
	native["fpsPrintNewPicDecoded"] = (void*)fpsPrintNewPicDecoded;
	native["print"] = (void*)printf;

	return native;
}


std::map<std::string,void*> nativeMap = createNativeMap();

#endif

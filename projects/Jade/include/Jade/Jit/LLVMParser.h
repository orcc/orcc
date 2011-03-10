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
@brief Description of the LLVMParser interface
@author Jerome Gorin
@file LLVMParser.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef LLVMPARSER_H
#define LLVMPARSER_H

#include "llvm/Module.h"
#include "llvm/LLVMContext.h"
#include "llvm/System/Signals.h"
#include "llvm/Bitcode/Archive.h"
#include "Jade/Core/Package.h"
//------------------------------


/**
 * @brief  This class manages the LLVM infrastructure to parse an actor in LLVM representation
 * 
 * @author Jerome Gorin
 * 
 */
class LLVMParser {
public:
	
	/**
     *  @brief Create a new LLVMParser
	 *
	 * @param C : the LLVM::Context
	 *
	 * @param directory : default directory of the module
	 *
     */
	LLVMParser(llvm::LLVMContext& C, std::string directory, bool verbose = false);
	
	/**
     *  @brief Load and parse the module file
     *
	 *  Read the specified file as an LLVM module. 
	 *
	 * @param package : the package that contains the actor
	 *
	 * @param file : file to parse
	 *
	 * @return the corresponding llvm::Module
	 *
     */
	llvm::Module* loadModule(Package* package, std::string file);

	 /**
     *  @brief Load and parse the bitcode file in an archive
	 *
	 *  Open and load archive then find and read the specified bitcode file of the current 
	 *  actor and return the corresponding module. 
	 *
	 * @param archive : archive where is file
	 * @param file : file to parse
	 *
	 * @return the corresponding llvm::Module
	 *
     */
	llvm::Module* loadBitcodeInArchive(llvm::Archive* archive, llvm::sys::Path file);

private:

	/** default directory of the actor */
	std::string directory;

	/** LLVM Context */
	llvm::LLVMContext &Context;

	/** Print information about actor taken*/
	bool verbose;
	
};

#endif
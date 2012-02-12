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
@brief Description of the LLVMArmFix interface
@author Jerome Gorin
@file LLVMArmFix.h
@version 1.0
@date 31/01/2012
*/

//------------------------------
#ifndef LLVMARMFIX_H
#define LLVMARMFIX_H

#include "Jade/Jit/LLVMExecution.h"

namespace llvm{
	class tool_output_file;
	namespace sys {
		class Path;
	}
}

//------------------------------

/**
 * @brief  This class manages the LLVM infrastructure to write elements
 * 
 * @author Jerome Gorin
 * 
 */
class LLVMArmFix : public LLVMExecution {
public:

	/**
     *  @brief Constructor
     *
	 *	Initialize the execution engine
	 *
	 *  @param C : the llvm::Context
	 *
	 *  @param decoder: the decoder to execute
	 *
	 *  @param verbose: verbose actions taken
	 *
     */
	LLVMArmFix(llvm::LLVMContext& C, Decoder* decoder, bool verbose = false);

	/**
     *  @brief Destructor
     *
	 *	Delete the execution engione
     */
	~LLVMArmFix();

	/**
     *  @brief Run the current decoder
	 *
	 *  Initialize and run the decoder in an infinite loop. 
	 *    No need to call the initialize function of LLVM execution.
     */
	void run();

	/**
     *  @brief Initialize the decoder before the execution
	 *
	 *  @return address of the stop variable
     */
	int* initialize() {return NULL;};

	/**
     *  @brief Generate native code for the current decoder
	 *
     */
	llvm::tool_output_file* generateNativeCode(llvm::sys::Path IntermediateAssemblyFile);

	/**
     *  @brief Link the output file and generate binary code
	 *
     */
	void compileAndLink(llvm::sys::Path IntermediateAssemblyFile, llvm::sys::Path IntermediateDecoderFile);

private:
	/**
	 *  @brief Copy the compiling environnement
	 *
	 */
	char ** CopyEnv(char ** const envp);

	/**
	 *  @brief Remove the specified environment variable from the environment array.
	 *
	 */
	void RemoveEnv(const char * name, char ** const envp);

	/**
	 *  @brief Output command to launch GCC
	 *
	 */
	void PrintCommand(const std::vector<const char*> &args);
};

#endif

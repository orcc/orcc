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
@brief Description of the LLVMWriter interface
@author Jerome Gorin
@file LLVMWriter.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef LLVMWRITER_H
#define LLVMWRITER_H

namespace llvm{
	struct ClonedCodeInfo;
}

class AbstractFifo;
class Decoder;

#include "llvm/Module.h"
#include "llvm/DerivedTypes.h"
#include "llvm/Value.h"
#include "llvm/Instructions.h"
#include "llvm/Transforms/Utils/ValueMapper.h"
//------------------------------


/**
 * @brief  This class manages the LLVM infrastructure to write elements
 * 
 * @author Jerome Gorin
 * 
 */
class LLVMWriter {
public:

	/**
     *  @brief Constructor
     *
	 *	Initialize the JIT engine
	 *
     */
	LLVMWriter(std::string prefix, Decoder* decoder);

	llvm::GlobalVariable* createVariable(llvm::GlobalVariable* variable);
	llvm::Function* createFunction(llvm::Function* function);

	llvm::Function* addFunctionProtosExternal(const llvm::Function* function);
	llvm::Function* addFunctionProtosInternal(const llvm::Function* function);
	bool linkProcedureBody(llvm::Function* function);

	/**
     *  @brief Add a new llvm::Type in the given decoder
     *
	 *  Insert an entry in the decoder Type table mapping. If there is already an entry 
	 *   for this name, true is returned and the symbol table of the decoder is not modified. 
	 *
	 * @param name : name of the type
	 *
	 * @param type : llvm::Type to add
	 *
	 * @return True ff there is already an entry for this name, otherwise false.
	 *
     */
	bool addType(std::string name, const llvm::Type* type);

private:

	/**
     *  @brief Add a new llvm::GlobalVariable in the given decoder
     *
	 *  Insert the given global variable into the decoder. This global variable
	 *   can either represent an actor port, state, parameter or internal variable.
	 *
	 * @param variable : llvm::GlobalVariable to add
	 *
	 * @param decoder : decoder to had the variable
	 *
	 * @return True if successfull, otherwise false
     */
	llvm::GlobalVariable* addVariable(llvm::GlobalVariable* variable);

	void CopyGVAttributes(llvm::GlobalValue *DestGV, const llvm::GlobalValue *SrcGV);
	bool LinkGlobalInits(llvm::GlobalVariable* variable);
	void linkFunctionBody(llvm::Function *NewFunc, const llvm::Function *OldFunc,
		llvm::ValueToValueMapTy &VMap,
                       bool ModuleLevelChanges,
					   llvm::SmallVectorImpl<llvm::ReturnInst*> &Returns,
					   AbstractFifo* fifo,
                       const char *NameSuffix = "", 
					   llvm::ClonedCodeInfo *CodeInfo = 0);

	/** Module to write element into */
	llvm::Module* module;

	/** Written values*/
	llvm::ValueToValueMapTy ValueMap;

	Decoder* decoder;
	std::string prefix;

};

#endif
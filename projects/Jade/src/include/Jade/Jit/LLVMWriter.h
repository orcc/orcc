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
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef LLVMWRITER_H
#define LLVMWRITER_H
#include <map>

namespace llvm{
	struct ClonedCodeInfo;
}

class AbstractFifo;
class Decoder;
class Port;



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

	
	/**
     *  @brief Create a new GlobalVariable in the decoder
	 *
	 * @param variable : the GlobalVariable to add
	 *
	 * @return the created GlobalVariable.
	 *
     */
	llvm::GlobalVariable* createVariable(llvm::GlobalVariable* variable);
	
	/**
     *  @brief Create a new Function in the decoder
	 *
	 * @param function : the llvm::Function to add
	 *
	 * @return the created llvm::Function.
     */
	llvm::Function* createFunction(llvm::Function* function);

	/**
     *  @brief Add a new external Function prototype in the decoder
	 *
	 * @param function : the llvm::Function to add
	 *
	 * @return the created prototype.
     */
	llvm::Function* addFunctionProtosExternal(const llvm::Function* function);
	
	/**
     *  @brief Add a new internal Function prototype in the decoder
	 *
	 * @param function : the llvm::Function to add
	 *
	 * @return the created prototype.
     */
	llvm::Function* addFunctionProtosInternal(const llvm::Function* function);
	
	/**
     *  @brief Link the body of a Function in the decoder
	 *
	 * @param function : the llvm::Function to link
	 *
	 * @return true if the function is linked otherwise false
     */
	bool linkProcedureBody(llvm::Function* function);

	/**
     *  @brief Add a new GlobalVariable representing a Port in the given decoder
	 *
	 * @param port : the Port to add
	 *
	 * @return the created GlobalVariable.
	 *
     */
	llvm::GlobalVariable* createPortVariable(Port* port);

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
	bool addType(std::string name, llvm::StructType* type);

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

	/**
     *  @brief Copy global variable attribute.
     *
	 * @param SrcGV : the source llvm::GlobalVariable to get attributes from
	 *
	 * @param DestGV : the destination llvm::GlobalVariable to write attributes to
     */
	void CopyGVAttributes(llvm::GlobalValue *DestGV, const llvm::GlobalValue *SrcGV);
	
	/**
     *  @brief Links llvm::GlobalVariable initializer.
     *
	 * @param variable : the llvm::GlobalVariable to link
	 *
	 * @return True if successfull, otherwise false
     */
	bool LinkGlobalInits(llvm::GlobalVariable* variable);

	/**
     *  @brief Link the body of a Function in the decoder
	 *
	 * @param NewFunc : the new llvm::Function
	 *
	 * @param OldFunc : the old llvm::Function
	 *
	 * @param VMap : the Value Map
	 *
	 * @param ModuleLevelChanges : Whether or not the Module Level has Changed
	 *
	 * @param Returns : Return instructions
	 *
	 * @param NameSuffix : the suffix name
	 *
	 * @param CodeInfo : the llvm::ClonedCodeInfo
     */
	void linkFunctionBody(llvm::Function *NewFunc, const llvm::Function *OldFunc,
		llvm::ValueToValueMapTy &VMap,
                       bool ModuleLevelChanges,
					   llvm::SmallVectorImpl<llvm::ReturnInst*> &Returns,
					   //AbstractConnector* fifo,
                       const char *NameSuffix = "", 
					   llvm::ClonedCodeInfo *CodeInfo = 0);

	/** Module to write element into */
	llvm::Module* module;

	/** Written values*/
	llvm::ValueToValueMapTy ValueMap;

	Decoder* decoder;
	std::string prefix;
	std::map<std::string, llvm::Function*>* fifoFns;

};

#endif
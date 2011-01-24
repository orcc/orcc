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
@brief Description of the FifoMng class interface
@author Jerome Gorin
@file FifoMng.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef FIFOMNG_H
#define FIFOMNG_H

#include <string>
#include <list>

#include "llvm/DerivedTypes.h"
#include "llvm/Function.h"

#include "Jade/Fifo/FifoSelection.h"
#include "Jade/Fifo/AbstractFifo.h"

namespace llvm{
	class Module;
}

class Actor;
class Decoder;
//------------------------------

/**
 * @class FifoMng
 *
 *  This class contains methods for managing fifos.
 *
 * @author Jerome Gorin
 * 
 */
class FifoMng {
public:

	static void setFifoTy(FifoTy fifoTy, std::string packageFld);

	static std::string getFifoFilename(){return fifoFiles[fifoTy];};

	static std::string getExternFnFilename(){return externFnFile;};

	static std::string getPackageFolder(){return packageFolder;};

	static llvm::StructType* getFifoType(llvm::IntegerType* type);

	/**
     *  @brief return true if the given name correspong to a function name
     *
	 *  @param name : std::string of function to look for
	 *
	 *	@Return true if the given name correspond to a fifo function, otherwise false.
     */
	static bool isFifoFunction(std::string name);

	/**
     *  @brief Getter of peek function
     *
	 *	@Return the llvm::Function of peek function in the final decoder
     */
	static llvm::Function* getPeekFunction(llvm::Type* type);

	/**
     *  @brief Getter of read function
     *
	 *	@Return the llvm::Function of read function in the final decoder
     */
	static llvm::Function* getReadFunction(llvm::Type* type);

	/**
     *  @brief Getter of write function
     *
	 *	@Return the llvm::Function of write function in the final decoder
     */
	static llvm::Function* getWriteFunction(llvm::Type* type);

	/**
     *  @brief Getter of hasToken function
     *
	 *	@Return the llvm::Function of hasToken function in the final decoder
     */
	static llvm::Function* getHasTokenFunction(llvm::Type* type);

	/**
     *  @brief Getter of hasRoom function
     *
	 *	@Return the llvm::Function of hasRoom function in the final decoder
     */
	static llvm::Function* getHasRoomFunction(llvm::Type* type);

	/**
     *  @brief Getter of writeEnd function
     *
	 *	@Return the llvm::Function of writeEnd function in the final decoder
     */
	static llvm::Function* getWriteEndFunction(llvm::Type* type);

	/**
     *  @brief Getter of readEnd function
     *
	 *	@Return the llvm::Function of readEnd function in the final decoder
     */
	static llvm::Function* getReadEndFunction(llvm::Type* type);

	/**
	 *  @brief Getter of fifo structure
	 *
	 *      Return the llvm::Type of the fifo structure
	 *
	 *  @return llvm::Type of the fifo
	 *
	 */
	static std::map<std::string, llvm::Type*>* getFifoTypes(){
		return &structAcces;
	};

	static AbstractFifo* getFifo(llvm::LLVMContext& C, Decoder* decoder, llvm::Type* type, int size);

	/**
	 *  @brief refine Fifos of the actor
	 *     
	 *  Set Abstract fifos from the current actor into the Fifo
	 *
	 *  @param actor: the Actor to refine
	 */
	static void refineActor(Actor* actor);
	static void addFifoHeader(Decoder* decoder);
	static void addFifoType(Decoder* decoder);
	static void addFunctions(Decoder* decoder);

private:


	/**
    * @brief parses Fifos
	*
	* Parse modules declared in an Abstract Fifo to get the fifo information
    */
	static void parseFifos();

	/**
    * @brief Fifo function name
    */
	static void fifoMap();

	/**
    * @brief Fifo structure name
    */
	static void structMap();

	/**
    * @brief Parse extern functions
    */
	static void parseExternFunctions();

	/**
    * @brief Parse fifo structures
    */
	static void parseFifoStructs();

	/**
    * @brief Parse fifo functions
    */
	static void parseFifoFunctions();

	/**
    * @brief Set a fifo structure
    */
	static void setFifoStruct(std::string name, llvm::Type* type);

	/**
    * @brief Set a fifo function
    */
	static void setFifoFunction(std::string name, llvm::Function* function);

	/**
    * @brief Defines the name of a fifo according to the bitwidth
    */
	static std::string funcName(llvm::IntegerType* type, std::string func);

	/**
	 *  @brief refine fifo structures of the actor
	 */
	static void refineStructures(Actor* actor);

	/**
	 *  @brief refine fifo functions of the actor
	 */
	static void refineFunctions(Actor* actor);

	/** Fifo type */
	static FifoTy fifoTy;
	
	/** Fifo filename */
	static std::string fifoFiles[];
	
	/** Extern functions filename */
	static std::string externFnFile;

	/** Path to package folder */
	static std::string packageFolder;

	/** Module that contains the fifo */
	static llvm::Module* headerMd;

	/** Module that contains the extern functions */
	static llvm::Module* externFnMd;

	/** Extern functions */
	static std::map<std::string,llvm::Function*> externFunct;

	/** Extern structs */
	static std::list<llvm::Function*> externStruct;

	/** Fifo structs and their corresponding name*/
	static std::map<std::string,std::string> structName;

	/** Fifo structs and their corresponding llvm::struct */
	static std::map<std::string,llvm::Type*> structAcces;

	/** Fifo functions and their corresponding name */
	static std::map<std::string,std::string> fifoFunct;

	/**Fifo functions and their corresponding llvm::function */
	static std::map<std::string,llvm::Function*> fifoAccess;
};

#endif
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
	class MDNode;
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
private:
	/**
	* @class FifoMng
	*
	*  This class is internal to FifoMng and is used to store every fifo access
	*
	* @author Jerome Gorin
	* 
	*/
	class FifoAccess{
	public:
		FifoAccess(int tokenSize, std::string structName, llvm::StructType* structType, std::map<std::string, llvm::Function*>* signedFn, std::map<std::string, llvm::Function*>* unsignedFn){
			this->tokenSize = tokenSize;
			this->structType = structType;
			this->structName = structName;
			this->signedFn = signedFn;
			this->unsignedFn = unsignedFn;
		}

		int getTokenSize(){return tokenSize;};

		std::map<std::string, llvm::Function*>* getSignedFn(){return signedFn;};
		std::map<std::string, llvm::Function*>* getUnsignedFn(){return unsignedFn;};
		llvm::StructType* getStructType(){return structType;};

	private:
		int tokenSize;
		std::string structName;
		llvm::StructType* structType;
		std::map<std::string, llvm::Function*>* signedFn;	
		std::map<std::string, llvm::Function*>* unsignedFn;
	};

public:

	/**
     *  @brief Initializer of FifoMng
	 *
	 *	This class MUST be called before any call to static elements,
	 *    it initializes all structure needed to manage fifos
     *
	 *	@param fifoTy : the FifoTy to use for decoders
	 *
	 *	@param packageFld : the VTL folder
     */
	static void setFifoTy(FifoTy fifoTy, std::string packageFld, int defaultFifoSize, std::string outputDir = "");

	/**
     *  @brief Filename of the fifo to parse
	 *
	 *	@return filename of the fifo to parse
     */
	static std::string getFifoFilename(){return fifoFiles[fifoTy];};

	/**
     *  @brief Filename of the extern function module to parse
	 *
	 *	@return filename of the extern function module to parse
     */
	static std::string getExternFnFilename(){return externFnFile;};

	/**
     *  @brief Return the folder of the VTL
	 *
	 *	@return folder of the VTL
     */
	static std::string getPackageFolder(){return packageFolder;};

	/**
     *  @brief Return the llvm::StructType for a fifo
	 *
	 *   Return the llvm::StructType for fifo corresponding to the given llvm::IntegerType
	 *
	 *	@param type : the llvm::IntegerType token size of the fifo
	 *
	 *	@return the corresponding llvm::StructType
     */
	static llvm::StructType* getFifoType(llvm::IntegerType* type);

	/**
     *  @brief Getter of peek function
     *
	 *	@Return the llvm::Function of peek function in the final decoder
     */
	static llvm::Function* getPeekFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Getter of read function
     *
	 *	@Return the llvm::Function of read function in the final decoder
     */
	static llvm::Function* getReadFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Getter of write function
     *
	 *	@Return the llvm::Function of write function in the final decoder
     */
	static llvm::Function* getWriteFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Getter of hasToken function
     *
	 *	@Return the llvm::Function of hasToken function in the final decoder
     */
	static llvm::Function* getHasTokenFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Getter of hasRoom function
     *
	 *	@Return the llvm::Function of hasRoom function in the final decoder
     */
	static llvm::Function* getHasRoomFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Getter of writeEnd function
     *
	 *	@Return the llvm::Function of writeEnd function in the final decoder
     */
	static llvm::Function* getWriteEndFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Getter of readEnd function
     *
	 *	@Return the llvm::Function of readEnd function in the final decoder
     */
	static llvm::Function* getReadEndFunction(llvm::IntegerType* type, Decoder* decoder);

	/**
     *  @brief Create a new fifo
	 *
	 *	Return a new fifo corresponding to the one selected with the given parameter
     *
	 *	@param C : the llvm::LLVMContext
	 *
	 *  @param decoder : the Decoder used
	 *
	 *  @param type : the fifo type
	 *
	 *  @param connection : the Connection where fifo is created
	 *
	 *	@Return the new Fifo
     */
	static AbstractFifo* getFifo(llvm::LLVMContext& C, Decoder* decoder, llvm::Type* type, Connection* connection);
	
	/**
	 *  @brief Add fifo header
	 *     
	 *  Add fifo header in the given decoder
	 *
	 *  @param decoder: the Decoder where header is add
	 *
	 *	@return the corresponding functions in the decoder
	 */
	static std::map<std::string, llvm::Function*>* addFifoHeader(Decoder* decoder);

	/**
	 *  @brief Return the default fifo size
	 *
	 *	@return the default fifo size
	 */
	static int getDefaultFifoSize();

private:
	/**
	 *  @brief Add fifo functions
	 *     
	 *  Add fifo functions in the given decoder
	 *
	 *  @param decoder: the Decoder where functions are add
	 *
	 *	@return the corresponding functions in the decoder
	 */
	static std::map<std::string, llvm::Function*>* addFifoFunctions(Decoder* decoder);
	/**
	 *  @brief Add fifo type
	 *     
	 *  Add fifo types in the given decoder
	 *
	 *  @param decoder: the Decoder where types are add
	 */
	static void addFifoType(Decoder* decoder);

	/**
    * @brief parses the Modules required
	*
	* Parse modules declared in an Abstract Fifo to get the fifo information
    */
	static void parseModules();

	/**
    * @brief parses the Fifos contained in the module
    */
	static void parseFifos();

	/**
    * @brief parses the FifoFunction
    */
	static std::map<std::string, llvm::Function*>* parseFifoFn(llvm::MDNode* functionMD);

	/**
    * @brief parses the FifoStruct
    */
	static std::pair<std::string, llvm::StructType*> parseFifoStruct(llvm::MDNode* structMD);

	/**
    * @brief parses a Fifo
    */
	static FifoAccess* parseFifo(llvm::MDNode* node);

	/**
    * @brief Defines the name of a fifo according to the bitwidth
    */
	static FifoMng::FifoAccess* getFifoAccess(llvm::IntegerType* type);

	/** Fifo type */
	static FifoTy fifoTy;
	
	/** Fifo filename */
	static std::string fifoFiles[];

	/** Fifo class */
	static AbstractFifo* fifo;
	
	/** Extern functions filename */
	static std::string externFnFile;

	/** Path to package folder */
	static std::string packageFolder;

	/** Path to writing directoru */
	static std::string outputDir;

	/** Module that contains the fifo */
	static llvm::Module* headerMd;

	/** Size of default fifo */
	static int defaultFifoSize;

	/** Extern structs */
	static std::list<llvm::Function*> externStruct;

	/** Fifo structs and their corresponding llvm::struct */
	static std::map<std::string, const llvm::StructType*> fifoStructs;

	/**Fifo functions and their corresponding llvm::function */
	static std::map<int, FifoAccess*> fifoAccesses;
};

#endif
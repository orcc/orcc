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
@brief Description of the FifoCircular class interface
@author Jerome Gorin
@file FifoCircular.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef CIRCULARFIFO_H
#define CIRCULARFIFO_H

#include <list>
#include <map>

namespace llvm{
	class Function;
	class IntegerType;
	class LLVMContext;
	class Module;
}

class Actor;
class Decoder;
class JIT;
class Connection;

#include "llvm/DerivedTypes.h"

//------------------------------
/**
 * @brief  This class defines AbstractFifo.
 * 
 * @author Jerome Gorin
 * 
 */
class AbstractFifo{
protected:
	/** Fifo function name */
	virtual std::map<std::string,std::string> fifoMap() = 0;

	/** Fifo function name */
	virtual std::map<std::string,std::string> structMap() = 0;

public:
	/**
     *  @brief Constructor
     *
	 *	Load and add fifo declaration inside the given decoder
	 *
	 *  @param jit : JIT use to load bitcoder
	 *
     */
	AbstractFifo(){
	};

	~AbstractFifo(){};

	/**
     *  @brief Getter of fifo structure
     *
	 *	Return the llvm::Type of the fifo structure
	 *
	 *  @return llvm::Type of the fifo
	 *
     */
	virtual void addFifoHeader(Decoder* decoder);

	/**
     *  @brief Getter of fifo structure
     *
	 *	Return the llvm::Type of the fifo structure
	 *
	 *  @return llvm::Type of the fifo
	 *
     */
	std::map<std::string, llvm::Type*>* getFifoTypes(){
		return &structAcces;
	};

	/**
     *  @brief Getter of peek function
     *
	 *	@Return the llvm::Function of peek function in the final decoder
     */
	llvm::Function* getPeekFunction(llvm::Type* type);

	/**
     *  @brief Getter of read function
     *
	 *	@Return the llvm::Function of read function in the final decoder
     */
	llvm::Function* getReadFunction(llvm::Type* type);

	/**
     *  @brief Getter of write function
     *
	 *	@Return the llvm::Function of write function in the final decoder
     */
	llvm::Function* getWriteFunction(llvm::Type* type);

	/**
     *  @brief Getter of hasToken function
     *
	 *	@Return the llvm::Function of hasToken function in the final decoder
     */
	llvm::Function* getHasTokenFunction(llvm::Type* type);

	/**
     *  @brief Getter of hasRoom function
     *
	 *	@Return the llvm::Function of hasRoom function in the final decoder
     */
	llvm::Function* getHasRoomFunction(llvm::Type* type);

	/**
     *  @brief Getter of writeEnd function
     *
	 *	@Return the llvm::Function of writeEnd function in the final decoder
     */
	llvm::Function* getWriteEndFunction(llvm::Type* type);
	/**
     *  @brief Getter of readEnd function
     *
	 *	@Return the llvm::Function of readEnd function in the final decoder
     */
	llvm::Function* getReadEndFunction(llvm::Type* type);

	
	/**
     *  @brief Getter of printf function
     *
	 *	@Return the llvm::Function of printf function in the final decoder
     */
	llvm::Function* getPrintfFunction(){
		return fifoAccess[fifoFunct["printf"]];
	};

	/**
     *  @brief return true if the given name correspong to a function name
     *
	 *  @param name : std::string of function to look for
	 *
	 *	@Return true if the given name correspond to a fifo function, otherwise false.
     */
	bool isFifoFunction(std::string name){
		if (fifoAccess.find(name)==fifoAccess.end())
			return false;
		
		return true;
	};


	/**
     *  @brief get the llvm::function from the given fifo function
     *
	 *  @param name : std::string of the fifo function name
	 *
	 *  @return function : llvm::function corresponding to the fifo function name
     */
	llvm::Function* getFifoFunction(std::string name){
		return fifoAccess[name];
	};

	/**
     *  @brief return true if the given name correspong to a extern function name
     *
	 *  @param name : std::string of function to look for
	 *
	 *	@Return true if the given name correspond to a extern function, otherwise false.
     */
	bool isExternFunction(std::string name){
		if (externFunct.find(name)==externFunct.end())
			return false;
		
		return true;
	};


	/**
     *  @brief get the llvm::function from the given extern function
     *
	 *  @param name : std::string of the fifo function name
	 *
	 *  @return function : llvm::function corresponding to the extern function name
     */
	llvm::Function* getExternFunction(std::string name){
		return externFunct[name];
	};


	/**
     *  @brief Getter of fifo access
	 *
	 *  @return a map of fifo function name and their corresponding llvm::function
     */
	std::map<std::string,llvm::Function*>* getFifoAccess(){
		return &fifoAccess;
	};

	/**
     *  @brief refine Fifos of the actor
	 *	
	 *  Set Abstract fifos from the current actor into the Fifo
	 *
	 *  @param actor: the Actor to refine
     */
	void refineActor(Actor* actor);

	/**
    * @brief add fifo structure
	*
	* @return llvm::Type of the fifo structure
    */
	void addFifoType(Decoder* decoder);


	virtual void addFunctions(Decoder* decoder) =0;

	virtual llvm::StructType* getFifoType(llvm::IntegerType* type)=0;
	
	/**
	 * @brief print connections in the given decoder.
	 *
	 */
	virtual void setConnections(Decoder* decoder);

	/**
	 * @brief remove connections from the given decoder.
	 *
	 */
	virtual void unsetConnections(Decoder* decoder);

protected:
	/** module of the fifo */
	llvm::Module* header;

	/** a map relying fifo struct and their name in the header */
	std::map<std::string,std::string> structName;

	/** a map relying fifo struct and their llvm::struct equivalentin the header */
	std::map<std::string,llvm::Type*> structAcces;

	/** a map relying fifo function and their name in the header */
	std::map<std::string,std::string> fifoFunct;

	/** a map relying fifo function and their llvm::function equivalent in the header */
	std::map<std::string,llvm::Function*> fifoAccess;

	/** Extern functions */
	std::map<std::string,llvm::Function*> externFunct;

	/** Extern structs */
	std::list<llvm::Function*> externStruct;

	/**
    *  @brief Initialize fifo access map
    */
	void createFifoMap();

	/**
    *  @brief Initialize struct access map
    */
	void createStructMap();

	/**
     *  @brief set an llvm::function to a fifo function
     *
	 *  @param name : std::string of the fifo function name
	 *
	 *  @param function : llvm::function corresponding to the fifo function name
     */
	void setFifoFunction(std::string name, llvm::Function* function);

	void setFifoStruct(std::string name, llvm::Type* type);

	virtual void setConnection(Connection* connection, Decoder* decoder)=0;

	virtual void unsetConnection(Connection* connection, Decoder* decoder)=0;

	/**
     *  @brief set fifo function name
     */
	std::string funcName(llvm::IntegerType* type, std::string func);
};

#endif
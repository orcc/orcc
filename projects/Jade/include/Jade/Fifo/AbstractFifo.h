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
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef CIRCULARFIFO_H
#define CIRCULARFIFO_H

#include <list>
#include <map>

#include "llvm/Module.h"

namespace llvm{
	class LLVMContext;
}

class Decoder;
class JIT;
class Connection;
//------------------------------

/**
 * @brief  This class defines AbstractFifo.
 * 
 * @author Jerome Gorin
 * 
 */
class AbstractFifo{
private:
	/** Fifo function name */
	virtual std::map<std::string,std::string> fifoMap() = 0;

public:
	/**
     *  @brief Constructor
     *
	 *	Load and add fifo declaration inside the given decoder
	 *
	 *  @param jit : JIT use to load bitcoder
	 *
     */
	AbstractFifo(){};

	~AbstractFifo(){};

	/**
     *  @brief Getter of fifo structure
     *
	 *	Return the llvm::Type of the fifo structure
	 *
	 *  @return llvm::Type of the fifo
	 *
     */
	virtual void addFifoHeader(Decoder* decoder) = 0;

	/**
     *  @brief Getter of fifo structure
     *
	 *	Return the llvm::Type of the fifo structure
	 *
	 *  @return llvm::Type of the fifo
	 *
     */
	llvm::StructType* getFifoType(){
		return type;
	};

	/**
     *  @brief Getter of peek function
     *
	 *	@Return the llvm::Function of peek function in the final decoder
     */
	llvm::Function* getPeekFunction(){
		return fifoAccess[fifoFunct["peek"]];
	};

	/**
     *  @brief Getter of read function
     *
	 *	@Return the llvm::Function of read function in the final decoder
     */
	llvm::Function* getReadFunction(){
		return fifoAccess[fifoFunct["read"]];
	};

	/**
     *  @brief Getter of write function
     *
	 *	@Return the llvm::Function of write function in the final decoder
     */
	llvm::Function* getWriteFunction(){
		return fifoAccess[fifoFunct["write"]];
	};

	/**
     *  @brief Getter of hasToken function
     *
	 *	@Return the llvm::Function of hasToken function in the final decoder
     */
	llvm::Function* getHasTokenFunction(){
		return fifoAccess[fifoFunct["hasToken"]];
	};

	/**
     *  @brief Getter of hasRoom function
     *
	 *	@Return the llvm::Function of hasRoom function in the final decoder
     */
	llvm::Function* getHasRoomFunction(){
		return fifoAccess[fifoFunct["hasRoom"]];
	};

	/**
     *  @brief Getter of writeEnd function
     *
	 *	@Return the llvm::Function of writeEnd function in the final decoder
     */
	llvm::Function* getWriteEndFunction(){
		return fifoAccess[fifoFunct["writeEnd"]];
	};

	/**
     *  @brief Getter of readEnd function
     *
	 *	@Return the llvm::Function of readEnd function in the final decoder
     */
	llvm::Function* getReadEndFunction(){
		return fifoAccess[fifoFunct["readEnd"]];
	};

	
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
     *  @brief set an llvm::function to a fifo function
     *
	 *  @param name : std::string of the fifo function name
	 *
	 *  @param function : llvm::function corresponding to the fifo function name
     */
	void setFifoFunction(std::string name, llvm::Function* function){
		std::map<std::string,llvm::Function*>::iterator it;

		it = fifoAccess.find(name);

		if (it == fifoAccess.end()){
			fprintf(stderr,"Error when setting circular fifo");
			exit(0);
		}
	
		(*it).second = function;
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
     *  @brief Getter of fifo access
	 *
	 *  @return a map of fifo function name and their corresponding llvm::function
     */
	std::map<std::string,llvm::Function*>* getFifoAccess(){
		return &fifoAccess;
	};


	virtual void setConnection(Connection* connection)=0;
	

protected:

	/**
    * @brief a map relying fifo function and their name in the headeer
    */
	std::map<std::string,std::string> fifoFunct;

	/**
    * @brief a map relying fifo function and their llvm::function equilent in the header
    */
	std::map<std::string,llvm::Function*> fifoAccess;

	/** structure type of the fifo */
	llvm::StructType* type;
};

#endif
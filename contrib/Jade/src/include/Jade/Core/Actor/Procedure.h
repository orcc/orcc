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
@brief Description of the Procedure class interface
@author Jerome Gorin
@file Procedure.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef PROCEDURE_H
#define PROCEDURE_H

#include <string>

namespace llvm {
	class Function;
	class ConstantInt;
}
//------------------------------

/**
 * @brief  This class defines a Procedure for the Functional Unit.
 * 
 * @author Jerome Gorin
 * 
 */
class Procedure {
public:
	/**
	 *
	 * @brief constructor
	 *
	 * Construcs a new procedure.
	 * 
	 * @param name		: name of the procedure.
	 *
	 * @param external	: whether it is external or not.
	 *
	 * @param function : the llvm::Function of this procedure.
	 */
	Procedure(std::string name, llvm::ConstantInt* external,
		llvm::Function* function) {
		this->external = external;
		this->name = name;
		this->function = function;
	}

	~Procedure();

	/**
     *  @brief Getter of function
     *
	 *	Return the llvm::function bound to this procedure
	 *
	 *	@return llvm::Function corresponding to the procedure
     */
	llvm::Function* getFunction(){return function;};

	/**
     *  @brief Getter of the function's name
	 *
	 *	@return name of the function
     */
	std::string getName(){return name;};

	/**
     *  @brief Getter of external
	 *
	 *	@return llvm::ConstantInt about external function
     */
	llvm::ConstantInt* getExternal(){return external;};

	/**
     *  @brief Return true if procedure external
	 *
	 *	@return true if procedure is external otherwise false
     */
	bool isExternal();

private:
	std::string name;
	llvm::ConstantInt* external;
	llvm::Function* function;
};

#endif
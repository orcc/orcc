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
@brief Description of the Variable class interface
@author Jerome Gorin
@file Variable.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef VARIABLE_H
#define VARIABLE_H

#include <string>

namespace llvm {
	class Type;
	class GlobalVariable;
}

class Location;
//------------------------------

/**
 * @class Variable
 *
 * @brief  This class defines a variable
 *
 * This class represents a variable for actor parameters. A variable has a a type, a name.
 * 
 * @author Jerome Gorin
 * 
 */
class Variable {
public:
	/**
	 * Creates a new variable with the given type, and name.
	 *
	 * @param type :	Type of the variable
	 *
	 * @param name :	string on the variable name
	 */
	Variable(llvm::Type* type, std::string name, bool global, llvm::GlobalVariable* variable){
		this->type = type;
		this->name = name;
		this->global = global;
		this->variable = variable;
	};

	~Variable();


	/**
	 * @brief Getter of name
	 * 
	 * Returns the name of this variable.
	 * 
	 * @return the name of this variable
	 */
	std::string getName() {
		return name;
	}


	/**
	 * @brief Getter of type
	 *
	 * Returns the type of this variable.
	 * 
	 * @return the type of this variable
	 */
	llvm::Type* getType() {
		return type;
	}

	/**
	 * @brief Getter of variable
	 *
	 * Returns the llvm::GlobalVariable bounds to this variable.
	 * 
	 * @return llvm::GlobalVariable bounds to this variable
	 */
	llvm::GlobalVariable* getGlobalVariable() {
		return variable;
	}


	/**
	 * Returns true if this variable is global.
	 * 
	 * @return true if this variable is global
	 */
	bool isGlobal() {return global;};

private:
	/** variable type */
	llvm::Type* type;

	/** variable name */
	std::string name;

	/** true if this variable is global */
	bool global;

	/** llvm::GlobalVariable bounds to this Variable */
	llvm::GlobalVariable* variable;
};

#endif
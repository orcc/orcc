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
@version 1.0
@date 15/11/2010
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
class Expr;
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
	Variable(llvm::Type* type, std::string name, bool global, bool assignable, llvm::GlobalVariable* variable){
		this->type = type;
		this->name = name;
		this->global = global;
		this->variable = variable;
		this->assignable = assignable;
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
	 * @brief Getter of global variable
	 *
	 * Returns the llvm::GlobalVariable bounds to this variable.
	 * 
	 * @return llvm::GlobalVariable bounds to this variable
	 */
	llvm::GlobalVariable* getGlobalVariable() {
		return variable;
	}

	/**
	 * @brief Setter of global variable
	 *
	 * Set the llvm::GlobalVariable bounds to this variable.
	 * 
	 * @param  llvm::GlobalVariable bounds to this variable
	 */
	void setGlobalVariable(llvm::GlobalVariable* variable) {
		this->variable = variable;
	}


	/**
	 * Returns true if this variable is global.
	 * 
	 * @return true if this variable is global
	 */
	bool isGlobal() {return global;};

	/**
	 * Returns true if this variable is a state variable.
	 * 
	 * @return true if this variable is global
	 */
	virtual bool isStateVar() {return false;};

	/**
	 * Returns true if this variable is assignable.
	 * 
	 * @return true if this variable is assignable
	 */
	bool isAssignable() {return assignable;};

	/**
	 * @brief Returns the initial expression of this variable.
	 * 
	 * @return the initial expression of this variable
	 */
	Expr* getInitialValue() {
		return initialValue;
	}

	/**
	 * @brief Returns the initial expression of this variable.
	 * 
	 * @return the initial expression of this variable
	 */
	void setInitialValue(Expr* expr) {
		this->initialValue = expr;
	}

	/**
	 * @brief Returns true if the variable has an initial value.
	 * 
	 * @return true if the variable has an initial value otherwise false
	 */
	bool hasInitialValue() {
		return initialValue != NULL;
	}

protected:
	/** Initial value */
	Expr* initialValue;

	/** variable type */
	llvm::Type* type;

	/** variable name */
	std::string name;

	/** true if this variable is global */
	bool global;

	/** true if this variable is global */
	bool assignable;

	/** llvm::GlobalVariable bounds to this Variable */
	llvm::GlobalVariable* variable;
};

#endif
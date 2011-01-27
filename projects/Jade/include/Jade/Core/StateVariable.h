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
@brief Description of the StateVar class interface
@author Jerome Gorin
@file StateVar.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef STATEVARIABLE_H
#define STATEVARIABLE_H

#include "Jade/Core/Expression.h"
#include "Jade/Core/Variable.h"
//------------------------------

/**
 * @class Variable
 *
 * @brief  This class defines a state variable
 *
 * This class represents a state variable. A state variable is a global variable
 * that can be assigned.
 * 
 * @author Jerome Gorin
 * 
 */
class StateVar : public Variable {
public:

	/**
	 * @brief create a state variable
	 *
	 * Creates a new state variable with the given type and name.
	 * 
	 * @param location : the state variable location
	 *
	 * @param type : the state variable type
	 *
	 * @param name : the state variable name
	 *
	 * @param assignable : whether this state variable is assignable or not.
	 *
	 * @param variable : llvm::GlobalVariable bound to this variable.
	 */
	StateVar(llvm::Type* type, std::string name, bool assignable, llvm::GlobalVariable* variable) 
		: Variable(type, name, true, assignable, variable)
	{
		this->initialValue = NULL;
	};

	/**
	 * @brief create a state variable with an initial value
	 *
	 *  Creates a new state variable with the given location, type, name and
	 *     initial value.
	 * 
	 * @param location : the state variable location
	 *
	 * @param type : the state variable type
	 *
	 * @param name : the state variable name
	 *
	 * @param assignable : whether this state variable is assignable or not.
	 *
	 * @param variable : llvm::GlobalVariable bound to this variable.
	 *
	 * @param initialValue : the initial value
	 */
	StateVar(llvm::Type* type, std::string name, bool assignable, llvm::GlobalVariable* variable, Expr* initialValue) 
		: Variable(type, name, true, assignable, variable)
	{
		this->initialValue = initialValue;
	};


	~StateVar();

	/**
	 * @brief Returns the initial expression of this variable.
	 * 
	 * @return the initial expression of this variable
	 */
	Expr* getInitialValue() {
		return initialValue;
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
};

#endif
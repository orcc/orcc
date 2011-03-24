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
@brief Description of the Action interface
@author Jerome Gorin
@file Pattern.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef PATTERN_H
#define PATTERN_H
#include <set>
#include <map>

namespace llvm {
	class ConstantInt;
}

class Port;
class Variable;
//------------------------------

/**
 * @brief  This class defines a pattern. A pattern contains a map of port ports and the number
 * of tokens produced/consumed by each of them along with their corresponding variable in the actor.
 * 
 * @author Jerome Gorin
 * 
 */
class Pattern {
public:

	/**
	 *
	 * @brief Constructor
	 *
	 * Creates a new Pattern with the given information.
	 * 
	 * @param numTokensMap : a Map of production/Consumption
	 *
	 * @param variableMap: a Map of Port and their associated variables
	 *
	 * @param peekedMap : a Map of Peeked port and  their associated variables
	 */
	Pattern(std::map<Port*, llvm::ConstantInt*>* numTokensMap, std::map<Port*, Variable*>* variableMap, std::map<Port*, Variable*>* peekedMap);

	/**
	 *
	 * @brief Constructor
	 *
	 * Creates a new Pattern.
	 */
	Pattern();

	~Pattern();

	/**
	 * @brief Returns true if this pattern is empty.
	 * 
	 * @return true if this pattern is empty
	 */
	bool isEmpty() {
		return ports.empty();
	}

	/**
	 * Clears this pattern.
	 */
	void clear();

	/**
	 * @brief Returns the number of tokens map.
	 * 
	 * @return the number of tokens map
	 */
	std::map<Port*, llvm::ConstantInt*>* getNumTokensMap() {
		return numTokensMap;
	}

	/**
	 * @brief Returns the peeked map.
	 * 
	 * @return the peeked map
	 */
	std::map<Port*, Variable*>* getPeekedMap() {
		return peekedMap;
	}

	/**
	 * @brief Returns the variable map.
	 * 
	 * @return the variable map
	 */
	std::map<Port*, Variable*>* getVariableMap() {
		return variableMap;
	}

	/**
	 * Removes the given port from this pattern.
	 * 
	 * @param port : a port
	 */
	void remove(Port* port);

	/**
	 * @brief Sets the number of tokens produced (or consumed) by the given port.
	 * 
	 * @param port : a port
	 *
	 * @param numTokens :  number of tokens produced (or consumed) by the given port
	 */
	void setNumTokens(Port* port, llvm::ConstantInt* numTokens);

	/**
	 * @brief Sets the variable in which tokens are peeked from the given port.
	 * 
	 * @param port : a port
	 *
	 * @param peeked : a variable that contains tokens peeked by the given port
	 */
	void setPeeked(Port* port, Variable* peeked);

	/**
	 * @brief Sets the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @param port : a port
	 * @param variable : the variable that contains tokens produced (or consumed) by
	 *            the given port
	 */
	void setVariable(Port* port, Variable* variable);

	/**
	 * @brief Returns the number of tokens produced (or consumed) by the given port.
	 * 
	 * @return the number of tokens produced (or consumed) by the given port
	 */
	llvm::ConstantInt* getNumTokens(Port* port);
private:

	/**
	 * @brief Check port presence in the pattern
	 *
	 * Checks if the given port is present in the ports list, and adds
	 * it if necessary.
	 * 
	 * @param port : a port
	 */
	void checkPortPresence(Port* port);

	/** Num tokens map */
	std::map<Port*, llvm::ConstantInt*>* numTokensMap;
	
	/** Peeked map */
	std::map<Port*, Variable*>* peekedMap;

	/** Variable map */
	std::map<Port*, Variable*>* variableMap;
	
	/** Set of ports */
	std::set<Port*> ports;
};

#endif
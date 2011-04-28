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
@brief Description of the Tag interface
@author Jerome Gorin
@file Tag.h
@version 1.0
@date 15/11/2010
*/

//------------------------------
#ifndef ACTIONTAG_H
#define ACTIONTAG_H

#include <list>
#include <string>
//------------------------------

/**
 * @brief  This class defines an action tag as a list of strings.
 * 
 * @author Jerome Gorin
 * 
 */
class ActionTag {
public:
	
	/**
	 * @brief Creates an empty tag.
	 */
	ActionTag(){
		
	};

	~ActionTag(){};

	/**
	 * @brief Creates a tag.
	 *
	 * Creates an empty tag with a given initial size.
	 * 
	 * @param size : initial size of the tag
	 * 
	 */
	ActionTag(int size) {	
	}

	/**
	 * @brief Adds an identifier.
	 *
	 * Adds an identifier to this tag.
	 * 
	 * @param identifier : string of the identifier
	 */
	void add(std::string identifier) {
		identifiers.push_back(identifier);
	}

	/**
	 * @brief Get identifiers of the tag.
	 * 
	 * @return a list of identifier
	 */
	std::list<std::string>* getIdentifiers() {
		return &identifiers;
	}


	/**
	 * @brief Get a string identifier of the tag.
	 * 
	 * @return a string identifier of the tag
	 */
	std::string getIdentifier() {
		std::list<std::string> ::iterator it;
		std::string identifier;
		for ( it=identifiers.begin() ; it != identifiers.end(); it++ ){
			identifier.append(*it);
		}	
		return identifier;
	}

	/**
	 * @brief Returns true if the tag is empty.
	 * 
	 * @return true if this tag is empty
	 */
	bool isEmpty() {
		return identifiers.empty();
	}

	/**
	 * @brief Returns the number of identifiers in this tag.
	 * 
	 * @return the number of identifiers in this tag
	 */
	int size(){ return identifiers.size();};

private:
	std::list<std::string> identifiers;
};

#endif
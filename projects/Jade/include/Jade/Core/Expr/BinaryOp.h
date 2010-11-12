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
@brief Description of the Binary Operation type
@author Jerome Gorin
@file BinaryOp.h
@version 0.1
@date 22/03/2010
*/

//------------------------------
#ifndef BINARYOP_H
#define BINARYOP_H

#include <string>
//------------------------------


/**
 * @class BinaryOp
 *
 * @brief  This class defines a Binary Operation of Expression
 *
 * This class represents an Binary Operation that can be used for Binary expression in a network. 
 * This class intend to be used with BinExpr class.
 * 
 * @author Jerome Gorin
 * 
 */
class BinaryOp{

private:
	
	/** Binary operation type */
	enum OPTYPE{ 
		/** bitand <code>&</code> */
		BITAND,

		/** bitor <code>|</code> */
		BITOR,

		/** bitxor <code>^</code> */
		BITXOR,

		/** division <code>/</code> */
		DIV,

		/** integer division <code>div</code> */
		DIV_INT,

		/** equal <code>==</code> */
		EQ,

		/** exponentiation <code>**</code> */
		EXP,

		/** greater than or equal <code>&gt;=</code> */
		GE,

		/** greater than <code>&gt;</code> */
		GT,

		/** less than or equal <code>&lt;=</code> */
		LE,

		/** logical and <code>&&</code> */
		LOGIC_AND,

		/** logical or <code>||</code> */
		LOGIC_OR,

		/** less than <code>&lt;</code> */
		LT,

		/** minus <code>-</code> */
		MINUS,

		/** modulo <code>%</code> */
		MOD,

		/** not equal <code>!=</code> */
		NE,

		/** plus <code>+</code> */
		PLUS,

		/** shift left <code>&lt;&lt;</code> */
		SHIFT_LEFT,

		/** shift right <code>&gt;&gt;</code> */
		SHIFT_RIGHT,

		/** times <code>*</code> */
		TIMES,

		/** unkown operator */
		UNKNOW
	}; 

public:

	/**
     *  @brief Constructor
     *
	 * Creates a new binary operator with the given precedence.
	 *
	 * @param opName : string kind of the operation
	 *
     */
	BinaryOp(std::string opName){
		if(opName.compare("+")==0){
			op = PLUS;
		}else if(opName.compare("*")==0){
			op = TIMES;
		}else{
			fprintf(stderr,"Unkow binary");
			exit(0);
		}
	};

	~BinaryOp();

	/**
	 * Returns this operator's precedence. An operator O1 that has a lower
	 * precedence than another operator O2 means that the operation involving O1
	 * is to be evaluated first.
	 * 
	 * @return this operator's precedence
	 */
	int getPrecedence() {
		return precedence;
	}

	/**
	 * Returns true if this operator is right-to-left associative.
	 * 
	 * @return true if this operator is right-to-left associative
	 */
	bool isRightAssociative() {
		return rightAssociative;
	}

private:
	OPTYPE op;
	int precedence;
	bool rightAssociative;

};

#endif
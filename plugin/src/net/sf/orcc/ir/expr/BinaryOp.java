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
package net.sf.orcc.ir.expr;

/**
 * This class defines the binary operators of the IR.
 * 
 * @author Matthieu Wipliez
 * 
 */
public enum BinaryOp {

	/**
	 * binary and.
	 */
	BAND(5, false),

	/**
	 * binary inclusive or.
	 */
	BOR(3, false),

	/**
	 * binary exclusive or.
	 */
	BXOR(4, false),

	/**
	 * division.
	 */
	DIV(10, false),

	/**
	 * integer division.
	 */
	DIV_INT(10, false),

	/**
	 * equal.
	 */
	EQ(6, false),

	/**
	 * exponentiation.
	 */
	EXP(11, true),

	/**
	 * greater than or equal.
	 */
	GE(7, false),

	/**
	 * greater than.
	 */
	GT(7, false),

	/**
	 * logical and.
	 */
	LAND(2, false),

	/**
	 * less than or equal.
	 */
	LE(7, false),

	/**
	 * logical or.
	 */
	LOR(1, false),

	/**
	 * less than.
	 */
	LT(7, false),

	/**
	 * minus.
	 */
	MINUS(9, false),

	/**
	 * modulo.
	 */
	MOD(10, false),

	/**
	 * not equal.
	 */
	NE(6, false),

	/**
	 * plus.
	 */
	PLUS(9, false),

	/**
	 * shift left.
	 */
	SHIFT_LEFT(8, false),

	/**
	 * shift right.
	 */
	SHIFT_RIGHT(8, false),

	/**
	 * times.
	 */
	TIMES(10, false);

	/**
	 * priority of this operator
	 */
	private int priority;

	/**
	 * true if this operator is right-to-left associative.
	 */
	private boolean rightAssociative;

	/**
	 * Creates a new binary operator with the given priority.
	 * 
	 * @param priority
	 *            the operator's priority
	 */
	private BinaryOp(int priority, boolean rightAssociative) {
		this.priority = priority;
		this.rightAssociative = rightAssociative;
	}

	/**
	 * Returns this operator's priority.
	 * 
	 * @return this operator's priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Returns true if this operator is right-to-left associative.
	 * 
	 * @return true if this operator is right-to-left associative
	 */
	public boolean isRightAssociative() {
		return rightAssociative;
	}

}

/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.math.BigInteger;

/**
 * This class defines an integer expression.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model extends="net.sf.orcc.ir.Expression"
 */
public interface ExprInt extends Expression {

	/**
	 * Adds the given integer expression to this integer expression.
	 * 
	 * @param expr
	 *            an integer expression
	 * @return the sum of this integer expression and the given integer
	 *         expression
	 */
	ExprInt add(ExprInt expr);

	/**
	 * Bitands this integer expression and the given integer expression.
	 * 
	 * @param expr
	 *            an integer expression
	 * @return the bitand of this integer expression and the given integer
	 *         expression
	 */
	ExprInt and(ExprInt expr);

	/**
	 * Divides this integer expression by the given integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return the dividend of this integer expression and the given integer
	 *         expression
	 */
	ExprInt divide(ExprInt expr);

	/**
	 * Compares the given integer expression to this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return <code>true</code> if this expression is greater than or equal to
	 *         the given expression
	 */
	ExprBool ge(ExprInt expr);

	/**
	 * Returns the value of this integer expression truncated as an
	 * <code>int</code> .
	 * 
	 * @return the value of this integer expression truncated as an
	 *         <code>int</code>
	 */
	int getIntValue();

	/**
	 * Returns the value of this integer expression as a <code>long</code>.
	 * 
	 * @return the value of this integer expression as a <code>long</code>
	 */
	long getLongValue();

	/**
	 * Returns the value of this integer expression.
	 * 
	 * @return the value of this integer expression
	 * @model
	 */
	BigInteger getValue();

	/**
	 * Compares the given integer expression to this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return <code>true</code> if this expression is greater than the given
	 *         expression
	 */
	ExprBool gt(ExprInt expr);

	/**
	 * Returns true if this integer number needs the "long" storage type.
	 * 
	 * @return true if this integer number needs the "long" storage type
	 */
	boolean isLong();

	/**
	 * Compares the given integer expression to this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return <code>true</code> if this expression is less than or equal to the
	 *         given expression
	 */
	ExprBool le(ExprInt expr);

	/**
	 * Compares the given integer expression to this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return <code>true</code> if this expression is less than the given
	 *         expression
	 */
	ExprBool lt(ExprInt expr);

	/**
	 * Divides this integer expression by the given integer expression and
	 * return the modulo.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return the modulo of the division of this integer expression by the
	 *         given integer expression
	 */
	ExprInt mod(ExprInt expr);

	/**
	 * Multiplies this integer expression by the given integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return the product of this integer expression and the given integer
	 *         expression
	 */
	ExprInt multiply(ExprInt expr);

	/**
	 * Negates this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return the negation of this integer expression
	 */
	ExprInt negate();

	/**
	 * Bitnots this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return the 1's complement of this integer expression
	 */
	ExprInt not();

	/**
	 * Bitors this integer expression.
	 * 
	 * @param expr
	 *            a integer expression
	 * @return the 1's complement of this integer expression
	 */
	ExprInt or(ExprInt expr);

	void setValue(BigInteger value);

	ExprInt shiftLeft(ExprInt expr);

	ExprInt shiftRight(ExprInt expr);

	ExprInt subtract(ExprInt expr);

	ExprInt xor(ExprInt expr);

}

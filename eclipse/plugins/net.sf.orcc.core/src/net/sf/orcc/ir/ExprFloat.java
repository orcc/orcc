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
package net.sf.orcc.ir;

import java.math.BigDecimal;

/**
 * This class defines an integer expression.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model extends="net.sf.orcc.ir.Expression"
 */
public interface ExprFloat extends Expression {

	/**
	 * Adds the given float expression to this float expression.
	 * 
	 * @param expr
	 *            a float expression
	 * @return the sum of this float expression and the given float expression
	 */
	ExprFloat add(ExprFloat expr);

	int compareTo(ExprFloat expr);

	/**
	 * Divides the given float expression to this float expression.
	 * 
	 * @param expr
	 *            a float expression
	 * @return the dividend of this float expression and the given float
	 *         expression
	 */
	ExprFloat divide(ExprFloat expr);

	/**
	 * Returns the value of this integer expression.
	 * 
	 * @return the value of this integer expression
	 * @model
	 */
	BigDecimal getValue();

	/**
	 * Multiplies this float expression by the given float expression.
	 * 
	 * @param expr
	 *            a float expression
	 * @return the product of this float expression and the given float
	 *         expression
	 */
	ExprFloat multiply(ExprFloat expr);

	/**
	 * Sets the value of this float expression.
	 * 
	 * @param value
	 *            the value of this float expression
	 */
	void setValue(BigDecimal value);

	/**
	 * Subtracts the given float expression to this float expression.
	 * 
	 * @param expr
	 *            a float expression
	 * @return the difference of this float expression and the given float
	 *         expression
	 */
	ExprFloat subtract(ExprFloat expr);

}

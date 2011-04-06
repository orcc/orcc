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

/**
 * This class defines a binary expression.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model extends="net.sf.orcc.ir.Expression"
 */
public interface ExprBinary extends Expression {

	/**
	 * Returns the first operand of this binary expression as an expression.
	 * 
	 * @return the first operand of this binary expression
	 * @model containment="true"
	 */
	Expression getE1();

	/**
	 * Returns the second operand of this binary expression as an expression.
	 * 
	 * @return the second operand of this binary expression
	 * @model containment="true"
	 */
	Expression getE2();

	/**
	 * Returns the operator of this binary expression.
	 * 
	 * @return the operator of this binary expression
	 * @model
	 */
	OpBinary getOp();

	/**
	 * Returns the type of this expression.
	 * 
	 * @return the type of this expression
	 * @model containment="true"
	 */
	Type getType();

	/**
	 * Sets the first operand of this binary expression as an expression.
	 * 
	 * @param e1
	 *            the first operand of this binary expression
	 */
	void setE1(Expression e1);

	/**
	 * Sets the second operand of this binary expression as an expression.
	 * 
	 * @param e2
	 *            the second operand of this binary expression
	 */
	void setE2(Expression e2);

	/**
	 * Sets the operator of this binary expression.
	 * 
	 * @param op
	 *            the operator of this binary expression
	 */
	void setOp(OpBinary op);

	/**
	 * Sets the type of this expression.
	 * 
	 * @param type
	 *            the type of this expression
	 */
	void setType(Type type);

}

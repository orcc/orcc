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

import java.math.BigDecimal;

import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.impl.ExpressionImpl;

/**
 * This class defines an integer expression.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class FloatExpr extends ExpressionImpl {

	private BigDecimal value;

	private FloatExpr(BigDecimal value) {
		this.value = value;
	}

	/**
	 * Creates a new integer expression.
	 * 
	 * @param value
	 *            an integer value.
	 */
	public FloatExpr(float value) {
		this.value = BigDecimal.valueOf(value);
	}

	@Override
	public Object accept(ExpressionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(ExpressionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public FloatExpr add(FloatExpr expr) {
		return new FloatExpr(value.add(expr.value));
	}

	public int compareTo(FloatExpr expr) {
		return value.compareTo(expr.value);
	}

	public FloatExpr divide(FloatExpr expr) {
		return new FloatExpr(value.divide(expr.value));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FloatExpr) {
			return value.equals(((FloatExpr) obj).value);
		} else {
			return false;
		}
	}

	@Override
	public Type getType() {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	/**
	 * Returns the value of this integer expression.
	 * 
	 * @return the value of this integer expression
	 */
	public float getValue() {
		return value.floatValue();
	}

	@Override
	public boolean isIntExpr() {
		return true;
	}

	public FloatExpr multiply(FloatExpr expr) {
		return new FloatExpr(value.multiply(expr.value));
	}

	public void setValue(float value) {
		this.value = BigDecimal.valueOf(value);
	}

	public FloatExpr subtract(FloatExpr expr) {
		return new FloatExpr(value.subtract(expr.value));
	}

	@Override
	public String toString() {
		return value.toString();
	}

}

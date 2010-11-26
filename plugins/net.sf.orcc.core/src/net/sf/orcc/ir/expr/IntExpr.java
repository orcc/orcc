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

import java.math.BigInteger;

import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;

/**
 * This class defines an integer expression.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class IntExpr extends ExpressionImpl {

	/**
	 * Returns the number of bits in the two's-complement representation of the
	 * given number, <i>including</i> a sign bit.
	 * 
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the
	 *         given number, <i>including</i> a sign bit
	 */
	public static int getSize(BigInteger number) {
		return number.bitLength() + 1;
	}

	/**
	 * Returns the number of bits in the two's-complement representation of the
	 * given number, <i>including</i> a sign bit.
	 * 
	 * @param number
	 *            a number
	 * @return the number of bits in the two's-complement representation of the
	 *         given number, <i>including</i> a sign bit
	 */
	public static int getSize(long number) {
		return getSize(BigInteger.valueOf(number));
	}

	private BigInteger value;

	public IntExpr(BigInteger value) {
		this.value = value;
	}

	/**
	 * Creates a new integer expression.
	 * 
	 * @param value
	 *            an integer value.
	 */
	public IntExpr(long value) {
		this.value = BigInteger.valueOf(value);
	}

	@Override
	public Object accept(ExpressionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(ExpressionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public IntExpr add(IntExpr expr) {
		return new IntExpr(value.add(expr.value));
	}

	public IntExpr and(IntExpr expr) {
		return new IntExpr(value.and(expr.value));
	}

	public IntExpr divide(IntExpr expr) {
		return new IntExpr(value.divide(expr.value));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IntExpr) {
			return value.equals(((IntExpr) obj).value);
		} else {
			return false;
		}
	}

	public BoolExpr ge(IntExpr expr) {
		return new BoolExpr(value.compareTo(expr.value) >= 0);
	}

	/**
	 * Returns the value of this integer expression truncated as an
	 * <code>int</code> .
	 * 
	 * @return the value of this integer expression truncated as an
	 *         <code>int</code>
	 */
	public int getIntValue() {
		return value.intValue();
	}

	/**
	 * Returns the value of this integer expression as a <code>long</code>.
	 * 
	 * @return the value of this integer expression as a <code>long</code>
	 */
	public long getLongValue() {
		return value.longValue();
	}

	@Override
	public Type getType() {
		return IrFactory.eINSTANCE.createTypeInt(getSize(value));
	}

	/**
	 * Returns the value of this integer expression.
	 * 
	 * @return the value of this integer expression
	 */
	public long getValue() {
		return value.longValue();
	}

	public BoolExpr gt(IntExpr expr) {
		return new BoolExpr(value.compareTo(expr.value) > 0);
	}

	@Override
	public boolean isIntExpr() {
		return true;
	}

	/**
	 * Returns true if this integer number needs the "long" storage type.
	 * 
	 * @return true if this integer number needs the "long" storage type
	 */
	public boolean isLong() {
		return getIntValue() != getLongValue();
	}

	public BoolExpr le(IntExpr expr) {
		return new BoolExpr(value.compareTo(expr.value) <= 0);
	}

	public BoolExpr lt(IntExpr expr) {
		return new BoolExpr(value.compareTo(expr.value) < 0);
	}

	public IntExpr mod(IntExpr expr) {
		return new IntExpr(value.mod(expr.value));
	}

	public IntExpr multiply(IntExpr expr) {
		return new IntExpr(value.multiply(expr.value));
	}

	public IntExpr negate() {
		return new IntExpr(value.negate());
	}

	public IntExpr not() {
		return new IntExpr(value.not());
	}

	public IntExpr or(IntExpr expr) {
		return new IntExpr(value.or(expr.value));
	}

	public void setValue(long value) {
		this.value = BigInteger.valueOf(value);
	}

	public IntExpr shiftLeft(IntExpr expr) {
		return new IntExpr(value.shiftLeft(expr.value.intValue()));
	}

	public IntExpr shiftRight(IntExpr expr) {
		return new IntExpr(value.shiftRight(expr.value.intValue()));
	}

	public IntExpr subtract(IntExpr expr) {
		return new IntExpr(value.subtract(expr.value));
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public IntExpr xor(IntExpr expr) {
		return new IntExpr(value.xor(expr.value));
	}

}

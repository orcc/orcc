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

import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Type;

/**
 * This class defines an integer expression.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public class IntExpr extends AbstractExpression {

	/**
	 * Returns the size in bits needed to store the given number as an int.
	 * 
	 * @param number
	 *            a number
	 * @return the size in bits needed to store the given number as an int
	 */
	public static int getSize(long number) {
		long v;
		if (number >= 0) {
			v = number + 1;
		} else {
			v = -number;
		}

		int size = (int) Math.ceil(Math.log(v) / Math.log(2)) + 1;
		return size;
	}

	private long value;

	/**
	 * Creates a new integer expression with a location.
	 * 
	 * @param location
	 *            a location
	 * @param value
	 *            an integer value.
	 */
	public IntExpr(Location location, long value) {
		super(location);
		this.value = value;
	}

	/**
	 * Creates a new integer expression with a dummy location.
	 * 
	 * @param value
	 *            an integer value.
	 */
	public IntExpr(long value) {
		this(new Location(), value);
	}

	@Override
	public Object accept(ExpressionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(ExpressionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IntExpr) {
			return (value == ((IntExpr) obj).value);
		} else {
			return false;
		}
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
		return value;
	}

	@Override
	public boolean isIntExpr() {
		return true;
	}

	public void setValue(long value) {
		this.value = value;
	}

}

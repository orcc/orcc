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

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.consts.AbstractConst;
import net.sf.orcc.util.INameable;

/**
 * This class represents a global variable. A global variable is a variable that
 * is not assignable, may have a value as an expression, which should be
 * constant when evaluated.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GlobalVariable extends Variable implements INameable {

	/**
	 * variable constant value.
	 */
	protected Constant constantValue;

	/**
	 * variable value
	 */
	private Expression value;

	/**
	 * Creates a new global variable from the given global variable.
	 * 
	 * @param variable
	 *            a global variable
	 */
	public GlobalVariable(GlobalVariable variable) {
		super(variable);
		this.constantValue = variable.constantValue;
		this.value = variable.value;
	}

	/**
	 * Creates a new global variable with the given location, type, and name.
	 * The global variable created will have no value.
	 * 
	 * @param location
	 *            the global variable location
	 * @param type
	 *            the global variable type
	 * @param name
	 *            the global variable name
	 */
	public GlobalVariable(Location location, Type type, String name) {
		this(location, type, name, null);
	}

	/**
	 * Creates a new global variable with the given location, type, name, and
	 * initial value.
	 * 
	 * @param location
	 *            the global variable location
	 * @param type
	 *            the global variable type
	 * @param name
	 *            the global variable name
	 * @param value
	 *            the value of the global variable
	 */
	public GlobalVariable(Location location, Type type, String name,
			Expression value) {
		super(location, type, name, true);
		this.value = value;
	}

	/**
	 * Evaluates this variable's value to a constant.
	 * 
	 * @throws OrccException
	 *             if the value could not be evaluated to a constant
	 */
	public void evaluate() throws OrccException {
		Expression expr = value.evaluate();
		constantValue = AbstractConst.evaluate(expr);
	}

	/**
	 * Returns the constant value of this global variable. If this variable has
	 * no constant value, {@link #evaluate()} is called first.
	 * 
	 * @return a constant, or <code>null</code> if this variable has no constant
	 *         value.
	 */
	public Constant getConstantValue() throws OrccException {
		if (constantValue == null) {
			evaluate();
		}

		return constantValue;
	}

	/**
	 * Returns the value of this global variable.
	 * 
	 * @return an expression, or <code>null</code> if this variable has no value
	 */
	public Expression getValue() {
		return value;
	}

	/**
	 * Returns <code>true</code> if this global variable has a value.
	 * 
	 * @return <code>true</code> if this global variable has a value
	 */
	public boolean hasValue() {
		return (value != null);
	}

	/**
	 * Sets the value of this global variable to the given expression.
	 * 
	 * @param value
	 *            an expression
	 */
	public void setValue(Expression value) {
		this.value = value;
	}

}

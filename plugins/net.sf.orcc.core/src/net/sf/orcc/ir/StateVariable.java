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

/**
 * This class represents a state variable. A state variable is a variable that
 * may have an initial value.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StateVariable extends Variable {

	private Expression initialValue;

	/**
	 * Creates a new state variable with the given location, type, name and
	 * initial value expressed as an expression.
	 * 
	 * @param location
	 *            the state variable location
	 * @param type
	 *            the state variable type
	 * @param name
	 *            the state variable name
	 * @param value
	 *            initial value
	 */
	public StateVariable(Location location, Type type, String name,
			boolean assignable) {
		this(location, type, name, assignable, null);
	}

	/**
	 * Creates a new state variable with the given location, type, name and
	 * initial value.
	 * 
	 * @param location
	 *            the state variable location
	 * @param type
	 *            the state variable type
	 * @param name
	 *            the state variable name
	 * @param value
	 *            initial value
	 */
	public StateVariable(Location location, Type type, String name,
			boolean assignable, Expression initialValue) {
		super(location, type, name, true, assignable);
		this.initialValue = initialValue;
	}

	/**
	 * Returns the initial value of this state variable, or <code>null</code> if
	 * this variable has no initial constant value. The initial value may be a
	 * boolean, an integer, a list or a string.
	 * 
	 * @return an object, or <code>null</code> if this variable has no constant
	 *         value
	 */
	public Expression getConstantValue() {
		return initialValue;
	}

	/**
	 * Returns <code>true</code> if this state variable has an initial value.
	 * 
	 * @return <code>true</code> if this state variable has an initial value
	 */
	public boolean isInitialized() {
		return (initialValue != null);
	}

	/**
	 * Sets the initial value of this state variable. The initial value may be a
	 * boolean, an integer, a list or a string.
	 * 
	 * @param initialValue
	 *            an object, or <code>null</code> if this variable has no
	 *            constant value
	 */
	public void setConstantValue(Expression initialValue) {
		this.initialValue = initialValue;
	}

}

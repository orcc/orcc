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
package net.sf.orcc.common;

import net.sf.orcc.ir.consts.IConst;
import net.sf.orcc.ir.type.IType;

/**
 * This class represents a global variable. A global variable may have a
 * constant value.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class GlobalVariable {

	/**
	 * variable location
	 */
	private Location location;

	/**
	 * variable name
	 */
	private String name;

	/**
	 * variable type
	 */
	private IType type;

	/**
	 * variable constant value
	 */
	private IConst value;

	/**
	 * Creates a new global variable from the given global variable.
	 * 
	 * @param var
	 *            a global variable
	 */
	public GlobalVariable(GlobalVariable var) {
		this.location = var.location;
		this.type = var.type;
		this.name = var.name;
		this.value = var.value;
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
	public GlobalVariable(Location location, IType type, String name) {
		this.location = location;
		this.type = type;
		this.name = name;
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
	 * @param value
	 *            the constant value of the global variable
	 */
	public GlobalVariable(Location location, IType type, String name,
			IConst value) {
		this.location = location;
		this.type = type;
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns the location of this port;
	 * 
	 * @return the location of this port
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Returns the name of this port.
	 * 
	 * @return the name of this port
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this port.
	 * 
	 * @return the type of this port
	 */
	public IType getType() {
		return type;
	}

	/**
	 * Returns the value of this global variable as a constant.
	 * 
	 * @return a constant, or <code>null</code> if this variable has no value
	 */
	public IConst getValue() {
		return value;
	}

	/**
	 * Sets the name of this port.
	 * 
	 * @param name
	 *            the new name of this port
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the value of this global variable to the given constant.
	 * 
	 * @param value
	 *            a constant value
	 */
	public void setValue(IConst value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}

}

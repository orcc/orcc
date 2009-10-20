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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.type.IType;

/**
 * This class represents a variable. A variable has a location, a type and a
 * name.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Variable {

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
	 * uses of this variable.
	 */
	private List<Use> uses;

	/**
	 * Creates a new variable with the given location, type, and name.
	 * 
	 * @param location
	 *            the variable location
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 */
	public Variable(Location location, IType type, String name) {
		this.location = location;
		this.type = type;
		this.name = name;
		this.uses = new ArrayList<Use>();
	}

	/**
	 * Creates a new variable from the given variable.
	 * 
	 * @param var
	 *            a variable
	 */
	public Variable(Variable var) {
		this.location = var.location;
		this.type = var.type;
		this.name = var.name;
		this.uses = var.uses;
	}

	/**
	 * Adds the given use of this variable to this variable's use list.
	 * 
	 * @param use
	 *            a use of this variable
	 */
	public void addUse(Use use) {
		uses.add(use);
	}

	/**
	 * Returns the location of this variable.
	 * 
	 * @return the location of this variable
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Returns the name of this variable.
	 * 
	 * @return the name of this variable
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this variable.
	 * 
	 * @return the type of this variable
	 */
	public IType getType() {
		return type;
	}

	/**
	 * Removes the given use of this variable from this variable's use list.
	 * 
	 * @param use
	 *            a use of this variable
	 */
	public void removeUse(Use use) {
		uses.remove(use);
	}

	/**
	 * Sets the location of this variable.
	 * 
	 * @param name
	 *            the new location of this variable
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Sets the name of this variable.
	 * 
	 * @param name
	 *            the new name of this variable
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the type of this variable.
	 * 
	 * @param type
	 *            the new type of this variable
	 */
	public void setType(IType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return name;
	}

}

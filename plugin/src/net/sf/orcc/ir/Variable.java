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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.util.INameable;

/**
 * This class represents a variable. A variable has a location, a type, a name
 * and a list of uses.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class Variable implements INameable {

	/**
	 * true if this variable is global
	 */
	private boolean global;

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
	private Type type;

	/**
	 * variable possible assign expression
	 */
	protected Expression expression;

	/**
	 * variable value
	 */
	private Object value;
	
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
	 * @param global
	 *            whether this variable is global
	 */
	public Variable(Location location, Type type, String name, boolean global, Expression expression) {
		this.location = location;
		this.type = type;
		this.name = name;
		this.uses = new ArrayList<Use>();
		this.expression = expression;
	}
	
	/**
	 * Creates a new variable with the given location, type, and name.
	 * 
	 * @param location
	 *            the variable location
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param global
	 *            whether this variable is global
	 */
	public Variable(Location location, Type type, String name, boolean global) {
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
	 * Adds a new use of this variable.
	 */
	public void addUse() {
		new Use(this);
	}

	/**
	 * Adds a new use of this variable in the given node.
	 * 
	 * @param node
	 *            a node that uses this variable
	 */
	public void addUse(Instruction node) {
		new Use(this, node);
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
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this variable.
	 * 
	 * @return the type of this variable
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns the list of uses of this variable. The list is a reference.
	 * 
	 * @return the list of uses of this variable.
	 */
	public List<Use> getUses() {
		return uses;
	}

	/**
	 * Returns <code>true</code> if this variable is global.
	 * 
	 * @return <code>true</code> if this variable is global
	 */
	public boolean isGlobal() {
		return global;
	}

	/**
	 * Returns true if this variable is used at least once.
	 * 
	 * @return true if this variable is used at least once.
	 */
	public boolean isUsed() {
		return !uses.isEmpty();
	}

	/**
	 * Removes the uses of this variable that reference the given node.
	 * 
	 * @param node
	 *            a node
	 */
	public void removeUse(Instruction node) {
		ListIterator<Use> it = uses.listIterator();
		while (it.hasNext()) {
			Use use = it.next();
			if (use.getNode().equals(node)) {
				it.remove();
			}
		}
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
	 * @param location
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
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Gets the current valuing expression of this variable.
	 * 
	 */
	public Expression getExpression() {
		return expression;
	}
	
	/**
	 * Sets the valuing expression of this variable.
	 * 
	 * @param expression
	 *            the valuing expression of this variable
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	/**
	 * Gets the current value of this variable.
	 * 
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets the value of this variable.
	 * 
	 * @param value
	 *            the typed value of this variable
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		// WARNING: this is used as-is by templates, etc. BE CAREFUL when
		// changing this function!
		return name;
	}

}

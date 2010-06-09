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

import net.sf.orcc.ir.instructions.AbstractFifoInstruction;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.util.INameable;

/**
 * This class represents a variable. A variable has a location, a type, a name
 * and a list of uses. It may be global or not, assignable or not. It has a list
 * of instructions where it is assigned (for local variables this list has one
 * entry). Finally, it has a value that is only used by the interpreter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Variable implements INameable {

	/**
	 * whether the variable is assignable.
	 */
	private boolean assignable;

	/**
	 * true if this variable is global
	 */
	private boolean global;

	/**
	 * the instruction where the variable is assigned.
	 */
	private Instruction instruction;

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
	 * uses of this variable.
	 */
	private List<Use> uses;

	/**
	 * variable value
	 */
	private Object value;

	/**
	 * Creates a new variable with the given location, type, and name. The
	 * variable may be global or local, and is created as non-assignable.
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
		this(location, type, name, global, false);
	}

	/**
	 * Creates a new variable with the given location, type, and name, and
	 * whether it is assignable or not.
	 * 
	 * @param location
	 *            the variable location
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param global
	 *            whether this variable is global
	 * @param assignable
	 *            <code>true</code> if this variable can be assigned
	 */
	public Variable(Location location, Type type, String name, boolean global,
			boolean assignable) {
		this.location = location;
		this.type = type;
		this.name = name;
		this.global = global;
		this.assignable = assignable;

		this.uses = new ArrayList<Use>();
	}

	/**
	 * Adds a new use of this variable in the given instruction.
	 * 
	 * @param instruction
	 *            an instruction that uses this variable
	 */
	public void addUse(Instruction instruction) {
		new Use(this, instruction);
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
	 * Returns the instruction where this variable is defined, or
	 * <code>null</code> if zero or several instructions use this variable as a
	 * target.
	 * 
	 * @return the instruction where this variable is defined, or
	 *         <code>null</code>
	 */
	public Instruction getInstruction() {
		return instruction;
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
	 * Gets the current value of this variable.
	 * 
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns <code>true</code> if this variable can be assigned to.
	 * 
	 * @return <code>true</code> if this variable can be assigned to
	 */
	public boolean isAssignable() {
		return assignable;
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
	 * Returns true if this variable is assigned to by one instruction that is a
	 * peek, read(end), write(end).
	 * 
	 * @return true if this variable is assigned to by one instruction that is a
	 *         peek, read(end), write(end)
	 */
	public boolean isPort() {
		if (instruction instanceof AbstractFifoInstruction
				&& !(instruction instanceof HasTokens)) {
			return true;
		}

		return false;
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
	 * Removes the uses of this variable that reference the given instruction.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	public void removeUse(Instruction instruction) {
		ListIterator<Use> it = uses.listIterator();
		while (it.hasNext()) {
			Use use = it.next();
			if (use.getNode().equals(instruction)) {
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
	 * Sets this variable as assignable or not.
	 * 
	 * @param assignable
	 *            <code>true</code> if the variable is assignable
	 */
	public void setAssignable(boolean assignable) {
		this.assignable = assignable;
	}

	/**
	 * Sets the instruction where this variable is defined. This is valid if and
	 * only if this variable is only assigned to once.
	 * 
	 * @param instruction
	 *            the instruction where this local variable is defined
	 */
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
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
		return getName();
	}

}

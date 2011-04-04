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

import java.lang.String;
import org.eclipse.emf.ecore.EObject;
import java.util.List;

/**
 * This interface represents a variable. A variable has a location, a type, a
 * name and a list of uses. It may be global or not, assignable or not. It has a
 * list of instructions where it is assigned (for local variables this list has
 * one entry). Finally, it has a value that is only used by the interpreter.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Var extends EObject {

	/**
	 * Adds the given instruction from the list of instructions that have this
	 * variable on their left-hand side.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	void addInstruction(Instruction instruction);

	/**
	 * Adds a new use of this variable in the given instruction.
	 * 
	 * @param instruction
	 *            an instruction that uses this variable
	 */
	void addUse(Instruction instruction);

	/**
	 * Adds the given use of this variable to this variable's use list.
	 * 
	 * @param use
	 *            a use of this variable
	 */
	void addUse(Use use);

	/**
	 * Returns the base name of this variable, which is the original name of the
	 * variable, without index.
	 * 
	 * @return the base name of this variable
	 */
	String getBaseName();

	/**
	 * Returns the SSA index of this variable. Valid only for local scalar
	 * variables.
	 * 
	 * @return the SSA index of this variable
	 * @model
	 */
	int getIndex();

	/**
	 * Returns the initial expression of this variable.
	 * 
	 * @return the initial expression of this variable
	 * @model containment="true"
	 */
	Expression getInitialValue();

	/**
	 * Returns the instruction where this variable is defined, or
	 * <code>null</code> if zero or several instructions use this variable as a
	 * target. Only valid is this variable is local.
	 * 
	 * @return the instruction where this variable is defined, or
	 *         <code>null</code>
	 */
	Instruction getInstruction();

	/**
	 * Returns the instructions where this variable appears on the left-hand
	 * side, or <code>null</code> if there are no such instructions, or if this
	 * variable is local (in which case, see {@link #getInstruction()}).
	 * 
	 * @return the list of instructions that have this variable on their
	 *         left-hand side, or <code>null</code>
	 */
	List<Instruction> getInstructions();

	/**
	 * Returns the location of this variable.
	 * 
	 * @return the location of this variable
	 * @model containment="true"
	 */
	Location getLocation();

	/**
	 * Returns the name of this variable.
	 * 
	 * @return the name of this variable
	 * @model
	 */
	String getName();

	/**
	 * Returns the type of this variable.
	 * 
	 * @return the type of this variable
	 * @model containment="true"
	 */
	Type getType();

	/**
	 * Returns the list of uses of this variable. The list is a reference.
	 * 
	 * @return the list of uses of this variable.
	 */
	List<Use> getUses();

	/**
	 * Returns the current value of this variable.
	 * 
	 * @return the current value of this variable
	 * @model containment="true"
	 */
	Expression getValue();

	/**
	 * Returns <code>true</code> if this variable can be assigned to.
	 * 
	 * @return <code>true</code> if this variable can be assigned to
	 * @model
	 */
	boolean isAssignable();

	/**
	 * Returns <code>true</code> if this variable is global.
	 * 
	 * @return <code>true</code> if this variable is global
	 * @model
	 */
	boolean isGlobal();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Var#isGlobal <em>Global</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Global</em>' attribute.
	 * @see #isGlobal()
	 * @generated
	 */
	void setGlobal(boolean value);

	/**
	 * Returns true if this variable has been assigned to an SSA index of this
	 * variable.
	 * 
	 * @return true if the variable has an index otherwise false.
	 */
	boolean isIndexed();

	/**
	 * Returns <code>true</code> if this state variable has an initial value.
	 * 
	 * @return <code>true</code> if this state variable has an initial value
	 */
	boolean isInitialized();

	/**
	 * Returns true if this variable is used at least once.
	 * 
	 * @return true if this variable is used at least once.
	 */
	boolean isUsed();

	/**
	 * Removes the given instruction from the list of instructions that have
	 * this variable on their left-hand side.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	void removeInstruction(Instruction instruction);

	/**
	 * Removes the uses of this variable that reference the given instruction.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	void removeUse(Instruction instruction);

	/**
	 * Removes the given use of this variable from this variable's use list.
	 * 
	 * @param use
	 *            a use of this variable
	 */
	void removeUse(Use use);

	/**
	 * Sets this variable as assignable or not.
	 * 
	 * @param assignable
	 *            <code>true</code> if the variable is assignable
	 */
	void setAssignable(boolean assignable);

	/**
	 * Sets the SSA index of this variable. Valid only for local scalar
	 * variables.
	 * 
	 * @param index
	 *            the SSA index of this variable
	 */
	void setIndex(int index);

	/**
	 * Sets the initial expression of this variable.
	 * 
	 * @param expression
	 *            the initial expression of this variable
	 */
	void setInitialValue(Expression expression);

	/**
	 * Sets the instruction where this variable is defined. This is valid if and
	 * only if this variable is only assigned to once.
	 * 
	 * @param instruction
	 *            the instruction where this local variable is defined
	 */
	void setInstruction(Instruction instruction);

	/**
	 * Sets the location of this variable.
	 * 
	 * @param location
	 *            the new location of this variable
	 */
	void setLocation(Location location);

	/**
	 * Sets the name of this variable.
	 * 
	 * @param name
	 *            the new name of this variable
	 */
	void setName(String name);

	/**
	 * Sets the type of this variable.
	 * 
	 * @param type
	 *            the new type of this variable
	 */
	void setType(Type type);

	/**
	 * Sets the value of this variable.
	 * 
	 * @param value
	 *            the typed value of this variable
	 */
	void setValue(Expression value);

}

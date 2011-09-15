/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

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
	 * If it exists, returns the annotation with the given name, otherwise
	 * returns <code>null</code>.
	 * 
	 * @param name
	 *            name of an annotation
	 * @return the annotation with the given name, or <code>null</code>
	 */
	Annotation getAnnotation(String name);

	/**
	 * Returns the annotations that may be associated with this variable.
	 * 
	 * @return the annotations that may be associated with this variable
	 * @model containment="true"
	 */
	EList<Annotation> getAnnotations();

	/**
	 * Returns the definitions of this variable. A definition is an instruction
	 * whose target is this variable. If this variable is a local scalar, at
	 * most one definition is expected.
	 * 
	 * @return the definitions of this variable
	 * @model
	 */
	EList<Def> getDefs();

	/**
	 * Returns the SSA index of this variable. Valid only for local scalar
	 * variables.
	 * 
	 * @return the SSA index of this variable
	 * @model
	 */
	int getIndex();

	/**
	 * Returns the indexed name of this variable, which is the name of the
	 * variable concatenated with "_" and its index, or if index is 0 is the
	 * name of variable.
	 * 
	 * @return the indexed name of this variable
	 */
	String getIndexedName();

	/**
	 * Returns the initial value of this variable as an expression. Only valid
	 * for global variables.
	 * 
	 * @return the initial value of this variable as an expression
	 * @model containment="true"
	 */
	Expression getInitialValue();

	/**
	 * Returns the line number of this variable.
	 * 
	 * @return the line number of this variable
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns the name of this variable.
	 * 
	 * @return the name of this variable
	 * @model dataType="org.eclipse.emf.ecore.EString"
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
	 * @model
	 */
	EList<Use> getUses();

	/**
	 * Returns the current value of this variable. Used by the interpreter.
	 * 
	 * @return the current value of this variable
	 * @model transient="true"
	 */
	Object getValue();

	/**
	 * Returns <code>true</code> if this variable can be assigned to.
	 * 
	 * @return <code>true</code> if this variable can be assigned to
	 * @model
	 */
	boolean isAssignable();

	/**
	 * Returns true if this variable is defined at least once.
	 * 
	 * @return true if this variable is defined at least once.
	 */
	boolean isDefined();

	/**
	 * Returns <code>true</code> if this variable is global.
	 * 
	 * @return <code>true</code> if this variable is global
	 * @model
	 */
	boolean isGlobal();

	/**
	 * Returns <code>true</code> if this state variable has an initial value.
	 * 
	 * @return <code>true</code> if this state variable has an initial value
	 */
	boolean isInitialized();

	/**
	 * Returns <code>true</code> if this variable is local.
	 * 
	 * @return <code>true</code> if this variable is local
	 */
	boolean isLocal();

	/**
	 * Returns true if this variable is used at least once.
	 * 
	 * @return true if this variable is used at least once.
	 */
	boolean isUsed();

	/**
	 * Sets this variable as assignable or not.
	 * 
	 * @param assignable
	 *            <code>true</code> if the variable is assignable
	 */
	void setAssignable(boolean assignable);

	/**
	 * Sets this variable as global or not.
	 * 
	 * @param global
	 *            <code>true</code> if this variable should be global
	 */
	void setGlobal(boolean global);

	/**
	 * Sets the SSA index of this variable. Valid only for local scalar
	 * variables.
	 * 
	 * @param index
	 *            the SSA index of this variable
	 */
	void setIndex(int index);

	/**
	 * Sets the initial value of this variable as an expression.
	 * 
	 * @param expression
	 *            the initial value of this variable as an expression
	 */
	void setInitialValue(Expression expression);

	/**
	 * Sets the line number of this variable.
	 * 
	 * @param newLineNumber
	 *            the line number of this variable
	 */
	public void setLineNumber(int newLineNumber);

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
	 * Sets the value of this variable. Used by the interpreter.
	 * 
	 * @param value
	 *            the value of this variable
	 */
	void setValue(Object value);

}

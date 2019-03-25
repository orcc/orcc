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

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines a type.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model abstract="true"
 * 
 */
public interface Type extends EObject {

	/**
	 * Returns the value of the '<em><b>Dyn</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dyn</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dyn</em>' attribute.
	 * @see #setDyn(boolean)
	 * @see net.sf.orcc.ir.IrPackage#getType_Dyn()
	 * @model default="false"
	 * @generated
	 */
	boolean isDyn();

	/**
	 * Returns a list of dimensions of this type. Returns an empty list if the
	 * type is not a list.
	 * 
	 * @return the list of dimensions of this type if it is a list
	 */
	public List<Integer> getDimensions();

	/**
	 * Returns a list of dimensions of this type. Returns an empty list if the
	 * type is not a list.
	 * 
	 * @return the list of dimensions of this type if it is a list
	 */
	public List<Expression> getDimensionsExpr();

	/**
	 * Returns the size in bits of this type. If this type is a list, the size
	 * returned is that of the innermost type.
	 * 
	 * @return the size in bits of this type
	 */
	public int getSizeInBits();

	/**
	 * Returns true if this type is <tt>bool</tt>.
	 * 
	 * @return true if this type is <tt>bool</tt>
	 */
	public boolean isBool();

	/**
	 * Returns true if this type is <tt>float</tt>.
	 * 
	 * @return true if this type is <tt>float</tt>
	 */
	public boolean isFloat();

	/**
	 * Returns true if this type is <tt>int</tt>.
	 * 
	 * @return true if this type is <tt>int</tt>
	 */
	public boolean isInt();

	/**
	 * Returns true if this type is <tt>List</tt>.
	 * 
	 * @return true if this type is <tt>List</tt>
	 */
	public boolean isList();

	/**
	 * Returns true if this type is <tt>String</tt>.
	 * 
	 * @return true if this type is <tt>String</tt>
	 */
	public boolean isString();

	/**
	 * Returns true if this type is <tt>uint</tt>.
	 * 
	 * @return true if this type is <tt>uint</tt>
	 */
	public boolean isUint();

	/**
	 * Returns true if this type is <tt>void</tt>.
	 * 
	 * @return true if this type is <tt>void</tt>
	 */
	public boolean isVoid();

	/**
	 * Sets the size of this type. Does nothing for types that have no notion of
	 * size, such as bool or void.
	 * 
	 * @param size
	 *            the size of this type
	 */
	void setSize(int size);

	/**
	 * Returns if the the list is dynamic
	 *
	 * @return the type of the list
	 */
	boolean getDyn();

	/**
	 * Set if the list is dynamic.
	 *
	 * @param dyn
	 *            the new status
	 */
	void setDyn(boolean dyn);

}

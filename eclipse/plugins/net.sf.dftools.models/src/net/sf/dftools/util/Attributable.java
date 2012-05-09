/*
 * Copyright (c) 2012, Synflow
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
package net.sf.dftools.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 *<!-- begin-user-doc -->This class defines an
 *         attributable object.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.util.Attributable#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.util.UtilPackage#getAttributable()
 * @model abstract="true"
 * @generated
 */
public interface Attributable extends EObject {

	/**
	 * Returns the nth attribute. If no such attribute exists, throws an
	 * IndexOutOfBoundsException.
	 * 
	 * @param index
	 *            index of an attribute
	 * @return an attribute
	 * @throws IndexOutOfBoundsException
	 */
	Attribute getAttribute(int index);

	/**
	 * Returns the first attribute that has the given name. If no such attribute
	 * exists, return <code>null</code>.
	 * 
	 * @param name
	 *            name of an attribute
	 * @return an attribute or <code>null</code>
	 */
	Attribute getAttribute(String name);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.dftools.util.Attribute}.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see net.sf.dftools.util.UtilPackage#getAttributable_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the value of the first attribute that has the given name. If no
	 * such attribute exists, return <code>null</code>.
	 * 
	 * @param name
	 *            name of an attribute
	 * @return the value of an attribute or <code>null</code>
	 */
	<T> T getValue(String name);

	/**
	 * Returns <code>true</code> if this object has an attribute with the given
	 * name. This can be used to quickly check if a "flag" attribute is present.
	 * 
	 * @param name
	 *            name of an attribute
	 * @return <code>true</code> if there is an attribute with the given name
	 */
	boolean hasAttribute(String name);

	/**
	 * Removes the first attribute that has the given name.
	 * 
	 * @param name
	 *            name of an attribute
	 */
	void removeAttribute(String name);

	/**
	 * Sets the value of the attribute with the given name to the given value.
	 * If no attribute exists with the given name, a new attribute is created
	 * and inserted at the beginning of the attribute list. This makes it easier
	 * to reference attributes by their index (the last attribute that was added
	 * will be at index 0, not index getAttributes().size() - 1).
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            an EMF object
	 */
	void setAttribute(String name, EObject value);

	/**
	 * Sets the value of the attribute with the given name to the given value.
	 * If no attribute exists with the given name, a new attribute is created
	 * and inserted at the beginning of the attribute list. This makes it easier
	 * to reference attributes by their index (the last attribute that was added
	 * will be at index 0, not index getAttributes().size() - 1).
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            a POJO
	 */
	void setAttribute(String name, Object value);

}

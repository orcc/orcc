/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> This class defines an argument passed by reference.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.ArgByRef#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.ArgByRef#getUse <em>Use</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.ir.IrPackage#getArgByRef()
 * @model
 * @generated
 */
public interface ArgByRef extends Arg {

	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Expression}.
	 * <!-- begin-user-doc --> Returns the (possibly empty) list of indexes of
	 * this argument by reference.
	 * 
	 * @return the (possibly empty) list of indexes of this argument by
	 *         reference<!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference list.
	 * @see net.sf.orcc.ir.IrPackage#getArgByRef_Indexes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getIndexes();

	/**
	 * Returns the value of the '<em><b>Use</b></em>' containment reference.
	 * <!-- begin-user-doc --> Returns the use associated with this argument by
	 * reference.
	 * 
	 * @return the use associated with this argument by reference<!--
	 *         end-user-doc -->
	 * @return the value of the '<em>Use</em>' containment reference.
	 * @see #setUse(Use)
	 * @see net.sf.orcc.ir.IrPackage#getArgByRef_Use()
	 * @model containment="true"
	 * @generated
	 */
	Use getUse();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.ArgByRef#getUse <em>Use</em>}' containment reference.
	 * <!-- begin-user-doc --> Sets the use referenced with this argument.
	 * 
	 * @param variable
	 *            the use referenced with this argument<!-- end-user-doc -->
	 * @param value the new value of the '<em>Use</em>' containment reference.
	 * @see #getUse()
	 * @generated
	 */
	void setUse(Use value);

}

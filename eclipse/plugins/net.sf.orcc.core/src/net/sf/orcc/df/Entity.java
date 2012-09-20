/**
 * <copyright>
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
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
 * </copyright>
 */
package net.sf.orcc.df;

import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Var;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.Entity#getIncomingPortMap <em>Incoming Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Entity#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.Entity#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.Entity#getOutgoingPortMap <em>Outgoing Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Entity#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.Entity#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getEntity()
 * @model
 * @generated
 */
public interface Entity extends EObject {
	/**
	 * Returns the value of the '<em><b>Incoming Port Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Port Map</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Port Map</em>' attribute.
	 * @see net.sf.orcc.df.DfPackage#getEntity_IncomingPortMap()
	 * @model dataType="net.sf.orcc.df.Map<net.sf.orcc.df.Port, net.sf.orcc.df.Connection>" changeable="false"
	 * @generated
	 */
	Map<Port, Connection> getIncomingPortMap();

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inputs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inputs</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getEntity_Inputs()
	 * @model changeable="false" derived="true"
	 * @generated
	 */
	EList<Port> getInputs();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see net.sf.orcc.df.DfPackage#getEntity_Name()
	 * @model changeable="false"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Outgoing Port Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Port Map</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Port Map</em>' attribute.
	 * @see net.sf.orcc.df.DfPackage#getEntity_OutgoingPortMap()
	 * @model dataType="net.sf.orcc.df.Map<net.sf.orcc.df.Port, net.sf.orcc.df.List<net.sf.orcc.df.Connection>>" changeable="false"
	 * @generated
	 */
	Map<Port, List<Connection>> getOutgoingPortMap();

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputs</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getEntity_Outputs()
	 * @model changeable="false" derived="true"
	 * @generated
	 */
	EList<Port> getOutputs();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Var}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getEntity_Parameters()
	 * @model changeable="false" derived="true"
	 * @generated
	 */
	EList<Var> getParameters();

	/**
	 * Returns the last component of the qualified name returned by
	 * {@link #getName()}.
	 * 
	 * @return an unqualified name
	 */
	String getSimpleName();

} // Entity

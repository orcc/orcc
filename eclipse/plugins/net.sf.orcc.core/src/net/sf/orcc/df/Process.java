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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Process</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.Process#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.Process#getIncomingPortMap <em>Incoming Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Process#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.Process#getOutgoingPortMap <em>Outgoing Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Process#getOutputs <em>Outputs</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getProcess()
 * @model
 * @generated
 */
public interface Process extends EObject {

	/**
	 * Returns the value of the '<em><b>Incoming Port Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * The incoming port map is a map that associates each input port of this
	 * process with the connection that is connected to this port (if any). The
	 * type of values is a connection, because an input port can have at most
	 * one incoming connection.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Port Map</em>' attribute.
	 * @see #setIncomingPortMap(Map)
	 * @see net.sf.orcc.df.DfPackage#getProcess_IncomingPortMap()
	 * @model dataType="net.sf.orcc.df.Map<net.sf.orcc.df.Port, net.sf.orcc.df.Connection>"
	 * @generated
	 */
	Map<Port, Connection> getIncomingPortMap();

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' reference list. The
	 * list contents are of type {@link net.sf.orcc.df.Port}. <!--
	 * begin-user-doc -->
	 * <p>
	 * The list of input ports of this process.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Inputs</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getProcess_Inputs()
	 * @model
	 * @generated
	 */
	EList<Port> getInputs();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * The name of this process.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.df.DfPackage#getProcess_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Outgoing Port Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * The outgoing port map is a map that associates each output port of this
	 * process with the list of connections that are connected to this port. The
	 * type of values is a list because an output port can have several outgoing
	 * connections to broadcast data.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Port Map</em>' attribute.
	 * @see #setOutgoingPortMap(Map)
	 * @see net.sf.orcc.df.DfPackage#getProcess_OutgoingPortMap()
	 * @model dataType="net.sf.orcc.df.Map<net.sf.orcc.df.Port, net.sf.orcc.df.List<net.sf.orcc.df.Connection>>"
	 * @generated
	 */
	Map<Port, List<Connection>> getOutgoingPortMap();

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' reference list. The
	 * list contents are of type {@link net.sf.orcc.df.Port}. <!--
	 * begin-user-doc -->
	 * <p>
	 * The list of output ports of this process.
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Outputs</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getProcess_Outputs()
	 * @model
	 * @generated
	 */
	EList<Port> getOutputs();

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Process#getIncomingPortMap <em>Incoming Port Map</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Incoming Port Map</em>' attribute.
	 * @see #getIncomingPortMap()
	 * @generated
	 */
	void setIncomingPortMap(Map<Port, Connection> value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Process#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Process#getOutgoingPortMap <em>Outgoing Port Map</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Outgoing Port Map</em>' attribute.
	 * @see #getOutgoingPortMap()
	 * @generated
	 */
	void setOutgoingPortMap(Map<Port, List<Connection>> value);

} // Process

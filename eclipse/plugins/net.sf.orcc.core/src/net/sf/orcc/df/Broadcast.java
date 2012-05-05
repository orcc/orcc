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
package net.sf.orcc.df;

import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a broadcast as a particular entity.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model extends="Entity"
 */
public interface Broadcast extends Vertex {

	Map<Port, Connection> getIncomingPortMap();

	Port getInput();

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' containment reference
	 * list. The list contents are of type {@link net.sf.orcc.df.Port}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inputs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Inputs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getBroadcast_Inputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getInputs();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.df.DfPackage#getBroadcast_Name()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	Map<Port, List<Connection>> getOutgoingPortMap();

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	Port getOutput(String name);

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference
	 * list. The list contents are of type {@link net.sf.orcc.df.Port}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getBroadcast_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getOutputs();

	String getSimpleName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Broadcast#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

}

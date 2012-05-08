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
package net.sf.orcc.backends.llvm.tta.architecture;

import net.sf.dftools.graph.Vertex;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Processor</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getGcu <em>Gcu</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getBuses <em>Buses</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getBridges <em>Bridges</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getSockets <em>Sockets</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getFunctionUnits <em>Function Units</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getRegisterFiles <em>Register Files</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getProgram <em>Program</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getData <em>Data</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getMappedActors <em>Mapped Actors</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor()
 * @model
 * @generated
 */
public interface Processor extends Component {
	/**
	 * Returns the value of the '<em><b>Bridges</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link net.sf.orcc.backends.llvm.tta.architecture.Bridge}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bridges</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bridges</em>' containment reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Bridges()
	 * @model containment="true"
	 * @generated
	 */
	EList<Bridge> getBridges();

	/**
	 * Returns the value of the '<em><b>Buses</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link net.sf.orcc.backends.llvm.tta.architecture.Bus}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Buses</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Buses</em>' containment reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Buses()
	 * @model containment="true"
	 * @generated
	 */
	EList<Bus> getBuses();

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' attribute.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration
	 * @see #setConfiguration(ProcessorConfiguration)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Configuration()
	 * @model
	 * @generated
	 */
	ProcessorConfiguration getConfiguration();

	/**
	 * Returns the value of the '<em><b>Data</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.AddressSpace}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' containment reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Data()
	 * @model containment="true"
	 * @generated
	 */
	EList<AddressSpace> getData();

	/**
	 * Returns the value of the '<em><b>Function Units</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Units</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Function Units</em>' containment reference
	 *         list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_FunctionUnits()
	 * @model containment="true"
	 * @generated
	 */
	EList<FunctionUnit> getFunctionUnits();

	/**
	 * Returns the value of the '<em><b>Gcu</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gcu</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gcu</em>' containment reference.
	 * @see #setGcu(GlobalControlUnit)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Gcu()
	 * @model containment="true"
	 * @generated
	 */
	GlobalControlUnit getGcu();

	/**
	 * Returns the value of the '<em><b>Mapped Actors</b></em>' reference list.
	 * The list contents are of type {@link net.sf.dftools.graph.Vertex}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Actors</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mapped Actors</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_MappedActors()
	 * @model
	 * @generated
	 */
	EList<Vertex> getMappedActors();

	/**
	 * Returns the value of the '<em><b>Program</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Program</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Program</em>' containment reference.
	 * @see #setProgram(AddressSpace)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Program()
	 * @model containment="true"
	 * @generated
	 */
	AddressSpace getProgram();

	/**
	 * Returns the value of the '<em><b>Register Files</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.orcc.backends.llvm.tta.architecture.RegisterFile}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Register Files</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Register Files</em>' containment reference
	 *         list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_RegisterFiles()
	 * @model containment="true"
	 * @generated
	 */
	EList<RegisterFile> getRegisterFiles();

	/**
	 * Returns the value of the '<em><b>Sockets</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link net.sf.orcc.backends.llvm.tta.architecture.Socket}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sockets</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sockets</em>' containment reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getProcessor_Sockets()
	 * @model containment="true"
	 * @generated
	 */
	EList<Socket> getSockets();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(ProcessorConfiguration value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getGcu <em>Gcu</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Gcu</em>' containment reference.
	 * @see #getGcu()
	 * @generated
	 */
	void setGcu(GlobalControlUnit value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Processor#getProgram <em>Program</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Program</em>' containment reference.
	 * @see #getProgram()
	 * @generated
	 */
	void setProgram(AddressSpace value);

} // Processor

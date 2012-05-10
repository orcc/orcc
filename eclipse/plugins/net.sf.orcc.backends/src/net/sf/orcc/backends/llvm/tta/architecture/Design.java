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

import net.sf.orcc.graph.Graph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Design</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getComponents <em>Components</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getProcessors <em>Processors</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getBuffers <em>Buffers</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getSignals <em>Signals</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getHardwareDatabase <em>Hardware Database</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign()
 * @model
 * @generated
 */
public interface Design extends Graph {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Name()
	 * @model
	 * @generated
	 */
	String getName();

	void addOutput(Port port);

	void addInput(Port port);

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Components</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Components()
	 * @model
	 * @generated
	 */
	EList<Component> getComponents();

	/**
	 * Returns the value of the '<em><b>Processors</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Processor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processors</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Processors()
	 * @model
	 * @generated
	 */
	EList<Processor> getProcessors();

	/**
	 * Returns the value of the '<em><b>Buffers</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Buffer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Buffers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buffers</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Buffers()
	 * @model
	 * @generated
	 */
	EList<Buffer> getBuffers();

	/**
	 * Returns the value of the '<em><b>Signals</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Signal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signals</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signals</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Signals()
	 * @model
	 * @generated
	 */
	EList<Signal> getSignals();

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inputs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inputs</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Inputs()
	 * @model
	 * @generated
	 */
	EList<Port> getInputs();

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputs</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Outputs()
	 * @model
	 * @generated
	 */
	EList<Port> getOutputs();

	/**
	 * Returns the value of the '<em><b>Hardware Database</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link net.sf.orcc.backends.llvm.tta.architecture.Implementation},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hardware Database</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hardware Database</em>' map.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_HardwareDatabase()
	 * @model mapType="net.sf.orcc.backends.llvm.tta.architecture.TypeToImplMapEntry<org.eclipse.emf.ecore.EString, net.sf.orcc.backends.llvm.tta.architecture.Implementation>"
	 * @generated
	 */
	EMap<String, Implementation> getHardwareDatabase();

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' attribute.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.llvm.tta.architecture.DesignConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.DesignConfiguration
	 * @see #setConfiguration(DesignConfiguration)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getDesign_Configuration()
	 * @model
	 * @generated
	 */
	DesignConfiguration getConfiguration();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Design#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.DesignConfiguration
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(DesignConfiguration value);
} // Design

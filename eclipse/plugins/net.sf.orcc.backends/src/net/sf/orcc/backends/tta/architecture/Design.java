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
package net.sf.orcc.backends.tta.architecture;

import net.sf.dftools.graph.Graph;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Design</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Design#getComponents <em>Components</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Design#getProcessors <em>Processors</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Design#getFifos <em>Fifos</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Design#getSignals <em>Signals</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Design#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Design#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign()
 * @model
 * @generated
 */
public interface Design extends Graph {

	/**
	 * Returns the value of the '<em><b>Components</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign_Components()
	 * @model
	 * @generated
	 */
	EList<Component> getComponents();

	/**
	 * Returns the value of the '<em><b>Processors</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Processor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processors</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processors</em>' reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign_Processors()
	 * @model
	 * @generated
	 */
	EList<Processor> getProcessors();

	/**
	 * Returns the value of the '<em><b>Fifos</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Fifo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fifos</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fifos</em>' reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign_Fifos()
	 * @model
	 * @generated
	 */
	EList<Fifo> getFifos();

	/**
	 * Returns the value of the '<em><b>Signals</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Signal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signals</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signals</em>' reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign_Signals()
	 * @model
	 * @generated
	 */
	EList<Signal> getSignals();

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.ExternalPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign_Ports()
	 * @model
	 * @generated
	 */
	EList<ExternalPort> getPorts();

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' attribute.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.tta.architecture.DesignConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.DesignConfiguration
	 * @see #setConfiguration(DesignConfiguration)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getDesign_Configuration()
	 * @model
	 * @generated
	 */
	DesignConfiguration getConfiguration();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.Design#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.DesignConfiguration
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(DesignConfiguration value);
} // Design

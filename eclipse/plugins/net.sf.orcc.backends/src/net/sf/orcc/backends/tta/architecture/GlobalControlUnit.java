/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.tta.architecture;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Global Control Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getProgram <em>Program</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getOperations <em>Operations</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getDelaySlots <em>Delay Slots</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getGuardLatency <em>Guard Latency</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getGlobalControlUnit()
 * @model
 * @generated
 */
public interface GlobalControlUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getGlobalControlUnit_Ports()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Program</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Program</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Program</em>' reference.
	 * @see #setProgram(AdressSpace)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getGlobalControlUnit_Program()
	 * @model
	 * @generated
	 */
	AdressSpace getProgram();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getProgram <em>Program</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Program</em>' reference.
	 * @see #getProgram()
	 * @generated
	 */
	void setProgram(AdressSpace value);

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' reference.
	 * @see #setOperations(OperationCtrl)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getGlobalControlUnit_Operations()
	 * @model
	 * @generated
	 */
	OperationCtrl getOperations();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getOperations <em>Operations</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operations</em>' reference.
	 * @see #getOperations()
	 * @generated
	 */
	void setOperations(OperationCtrl value);

	/**
	 * Returns the value of the '<em><b>Delay Slots</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delay Slots</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delay Slots</em>' attribute.
	 * @see #setDelaySlots(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getGlobalControlUnit_DelaySlots()
	 * @model
	 * @generated
	 */
	int getDelaySlots();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getDelaySlots <em>Delay Slots</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delay Slots</em>' attribute.
	 * @see #getDelaySlots()
	 * @generated
	 */
	void setDelaySlots(int value);

	/**
	 * Returns the value of the '<em><b>Guard Latency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard Latency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard Latency</em>' attribute.
	 * @see #setGuardLatency(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getGlobalControlUnit_GuardLatency()
	 * @model
	 * @generated
	 */
	int getGuardLatency();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getGuardLatency <em>Guard Latency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard Latency</em>' attribute.
	 * @see #getGuardLatency()
	 * @generated
	 */
	void setGuardLatency(int value);

} // GlobalControlUnit

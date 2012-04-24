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
package net.sf.orcc.backends.llvm.tta.architecture;

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
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getReturnAddress <em>Return Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getAddressSpace <em>Address Space</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getOperations <em>Operations</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getDelaySlots <em>Delay Slots</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getGuardLatency <em>Guard Latency</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit()
 * @model
 * @generated
 */
public interface GlobalControlUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.FuPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit_Ports()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<FuPort> getPorts();

	/**
	 * Returns the value of the '<em><b>Return Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Address</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Address</em>' containment reference.
	 * @see #setReturnAddress(FuPort)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit_ReturnAddress()
	 * @model containment="true"
	 * @generated
	 */
	FuPort getReturnAddress();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getReturnAddress <em>Return Address</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Address</em>' containment reference.
	 * @see #getReturnAddress()
	 * @generated
	 */
	void setReturnAddress(FuPort value);

	/**
	 * Returns the value of the '<em><b>Address Space</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address Space</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address Space</em>' reference.
	 * @see #setAddressSpace(AddressSpace)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit_AddressSpace()
	 * @model
	 * @generated
	 */
	AddressSpace getAddressSpace();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getAddressSpace <em>Address Space</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address Space</em>' reference.
	 * @see #getAddressSpace()
	 * @generated
	 */
	void setAddressSpace(AddressSpace value);

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.llvm.tta.architecture.Operation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit_Operations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operation> getOperations();

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
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit_DelaySlots()
	 * @model
	 * @generated
	 */
	int getDelaySlots();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getDelaySlots <em>Delay Slots</em>}' attribute.
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
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getGlobalControlUnit_GuardLatency()
	 * @model
	 * @generated
	 */
	int getGuardLatency();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit#getGuardLatency <em>Guard Latency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard Latency</em>' attribute.
	 * @see #getGuardLatency()
	 * @generated
	 */
	void setGuardLatency(int value);

} // GlobalControlUnit

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

import net.sf.orcc.df.Connection;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Buffer</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getMinAddress <em>Min Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getMaxAddress <em>Max Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getDepth <em>Depth</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getWordWidth <em>Word Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getAddrWidth <em>Addr Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getMappedConnections <em>Mapped Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getMemory()
 * @model
 * @generated
 */
public interface Memory extends Link {
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
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getMemory_Name()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Min Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Address</em>' attribute.
	 * @see #setMinAddress(int)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getMemory_MinAddress()
	 * @model
	 * @generated
	 */
	int getMinAddress();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getMinAddress <em>Min Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Address</em>' attribute.
	 * @see #getMinAddress()
	 * @generated
	 */
	void setMinAddress(int value);

	/**
	 * Returns the value of the '<em><b>Max Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Address</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getMemory_MaxAddress()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getMaxAddress();

	/**
	 * Returns the value of the '<em><b>Depth</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Depth</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Depth</em>' attribute.
	 * @see #setDepth(int)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getBuffer_Depth()
	 * @model
	 * @generated
	 */
	int getDepth();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getDepth <em>Depth</em>}' attribute. The value 
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depth</em>' attribute.
	 * @see #getDepth()
	 */
	void setDepth(int value);

	/**
	 * Returns the value of the '<em><b>Word Width</b></em>' attribute.
	 * The default value is <code>"32"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Word Width</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Word Width</em>' attribute.
	 * @see #setWordWidth(int)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getMemory_WordWidth()
	 * @model default="32"
	 * @generated
	 */
	int getWordWidth();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.Memory#getWordWidth <em>Word Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Word Width</em>' attribute.
	 * @see #getWordWidth()
	 * @generated
	 */
	void setWordWidth(int value);

	/**
	 * Returns the value of the '<em><b>Addr Width</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Addr Width</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Addr Width</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getBuffer_AddrWidth()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	int getAddrWidth();

	/**
	 * Returns the value of the '<em><b>Mapped Connections</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Connection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Connections</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped Connections</em>' reference list.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getMemory_MappedConnections()
	 * @model
	 * @generated
	 */
	EList<Connection> getMappedConnections();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isShared();

} // Buffer

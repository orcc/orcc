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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fu Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getInputSocket <em>Input Socket</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getOutputSocket <em>Output Socket</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#isTrigger <em>Trigger</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#isOpcodeSelector <em>Opcode Selector</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort()
 * @model
 * @generated
 */
public interface FuPort extends EObject {
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
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Input Socket</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Socket</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Socket</em>' reference.
	 * @see #isSetInputSocket()
	 * @see #unsetInputSocket()
	 * @see #setInputSocket(Socket)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort_InputSocket()
	 * @model unsettable="true"
	 * @generated
	 */
	Socket getInputSocket();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getInputSocket <em>Input Socket</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input Socket</em>' reference.
	 * @see #isSetInputSocket()
	 * @see #unsetInputSocket()
	 * @see #getInputSocket()
	 * @generated
	 */
	void setInputSocket(Socket value);

	/**
	 * Unsets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getInputSocket <em>Input Socket</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInputSocket()
	 * @see #getInputSocket()
	 * @see #setInputSocket(Socket)
	 * @generated
	 */
	void unsetInputSocket();

	/**
	 * Returns whether the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getInputSocket <em>Input Socket</em>}' reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Input Socket</em>' reference is set.
	 * @see #unsetInputSocket()
	 * @see #getInputSocket()
	 * @see #setInputSocket(Socket)
	 * @generated
	 */
	boolean isSetInputSocket();

	/**
	 * Returns the value of the '<em><b>Output Socket</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Socket</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Socket</em>' reference.
	 * @see #isSetOutputSocket()
	 * @see #unsetOutputSocket()
	 * @see #setOutputSocket(Socket)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort_OutputSocket()
	 * @model unsettable="true"
	 * @generated
	 */
	Socket getOutputSocket();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getOutputSocket <em>Output Socket</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Socket</em>' reference.
	 * @see #isSetOutputSocket()
	 * @see #unsetOutputSocket()
	 * @see #getOutputSocket()
	 * @generated
	 */
	void setOutputSocket(Socket value);

	/**
	 * Unsets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getOutputSocket <em>Output Socket</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOutputSocket()
	 * @see #getOutputSocket()
	 * @see #setOutputSocket(Socket)
	 * @generated
	 */
	void unsetOutputSocket();

	/**
	 * Returns whether the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getOutputSocket <em>Output Socket</em>}' reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Output Socket</em>' reference is set.
	 * @see #unsetOutputSocket()
	 * @see #getOutputSocket()
	 * @see #setOutputSocket(Socket)
	 * @generated
	 */
	boolean isSetOutputSocket();

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(int)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort_Width()
	 * @model
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Returns the value of the '<em><b>Trigger</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trigger</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trigger</em>' attribute.
	 * @see #setTrigger(boolean)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort_Trigger()
	 * @model
	 * @generated
	 */
	boolean isTrigger();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#isTrigger <em>Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trigger</em>' attribute.
	 * @see #isTrigger()
	 * @generated
	 */
	void setTrigger(boolean value);

	/**
	 * Returns the value of the '<em><b>Opcode Selector</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opcode Selector</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opcode Selector</em>' attribute.
	 * @see #setOpcodeSelector(boolean)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getFuPort_OpcodeSelector()
	 * @model
	 * @generated
	 */
	boolean isOpcodeSelector();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.FuPort#isOpcodeSelector <em>Opcode Selector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opcode Selector</em>' attribute.
	 * @see #isOpcodeSelector()
	 * @generated
	 */
	void setOpcodeSelector(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void connect(Socket socket);

} // FuPort

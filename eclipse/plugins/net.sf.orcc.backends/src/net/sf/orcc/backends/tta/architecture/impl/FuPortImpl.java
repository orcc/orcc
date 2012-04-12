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
package net.sf.orcc.backends.tta.architecture.impl;

import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.FuPort;
import net.sf.orcc.backends.tta.architecture.Socket;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fu Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.FuPortImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.FuPortImpl#getInputSocket <em>Input Socket</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.FuPortImpl#getOutputSocket <em>Output Socket</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.FuPortImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.FuPortImpl#isTrigger <em>Trigger</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.FuPortImpl#isOpcodeSelector <em>Opcode Selector</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FuPortImpl extends EObjectImpl implements FuPort {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInputSocket() <em>Input Socket</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputSocket()
	 * @generated
	 * @ordered
	 */
	protected Socket inputSocket;

	/**
	 * This is true if the Input Socket reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean inputSocketESet;

	/**
	 * The cached value of the '{@link #getOutputSocket() <em>Output Socket</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputSocket()
	 * @generated
	 * @ordered
	 */
	protected Socket outputSocket;

	/**
	 * This is true if the Output Socket reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean outputSocketESet;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #isTrigger() <em>Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTrigger()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRIGGER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTrigger() <em>Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTrigger()
	 * @generated
	 * @ordered
	 */
	protected boolean trigger = TRIGGER_EDEFAULT;

	/**
	 * The default value of the '{@link #isOpcodeSelector() <em>Opcode Selector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOpcodeSelector()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPCODE_SELECTOR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOpcodeSelector() <em>Opcode Selector</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOpcodeSelector()
	 * @generated
	 * @ordered
	 */
	protected boolean opcodeSelector = OPCODE_SELECTOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FuPortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.FU_PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FU_PORT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Socket getInputSocket() {
		if (inputSocket != null && inputSocket.eIsProxy()) {
			InternalEObject oldInputSocket = (InternalEObject) inputSocket;
			inputSocket = (Socket) eResolveProxy(oldInputSocket);
			if (inputSocket != oldInputSocket) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.FU_PORT__INPUT_SOCKET,
							oldInputSocket, inputSocket));
			}
		}
		return inputSocket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Socket basicGetInputSocket() {
		return inputSocket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputSocket(Socket newInputSocket) {
		Socket oldInputSocket = inputSocket;
		inputSocket = newInputSocket;
		boolean oldInputSocketESet = inputSocketESet;
		inputSocketESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FU_PORT__INPUT_SOCKET, oldInputSocket,
					inputSocket, !oldInputSocketESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInputSocket() {
		Socket oldInputSocket = inputSocket;
		boolean oldInputSocketESet = inputSocketESet;
		inputSocket = null;
		inputSocketESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					ArchitecturePackage.FU_PORT__INPUT_SOCKET, oldInputSocket,
					null, oldInputSocketESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInputSocket() {
		return inputSocketESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Socket getOutputSocket() {
		if (outputSocket != null && outputSocket.eIsProxy()) {
			InternalEObject oldOutputSocket = (InternalEObject) outputSocket;
			outputSocket = (Socket) eResolveProxy(oldOutputSocket);
			if (outputSocket != oldOutputSocket) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.FU_PORT__OUTPUT_SOCKET,
							oldOutputSocket, outputSocket));
			}
		}
		return outputSocket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Socket basicGetOutputSocket() {
		return outputSocket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputSocket(Socket newOutputSocket) {
		Socket oldOutputSocket = outputSocket;
		outputSocket = newOutputSocket;
		boolean oldOutputSocketESet = outputSocketESet;
		outputSocketESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FU_PORT__OUTPUT_SOCKET,
					oldOutputSocket, outputSocket, !oldOutputSocketESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOutputSocket() {
		Socket oldOutputSocket = outputSocket;
		boolean oldOutputSocketESet = outputSocketESet;
		outputSocket = null;
		outputSocketESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					ArchitecturePackage.FU_PORT__OUTPUT_SOCKET,
					oldOutputSocket, null, oldOutputSocketESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOutputSocket() {
		return outputSocketESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FU_PORT__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTrigger() {
		return trigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrigger(boolean newTrigger) {
		boolean oldTrigger = trigger;
		trigger = newTrigger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FU_PORT__TRIGGER, oldTrigger, trigger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOpcodeSelector() {
		return opcodeSelector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpcodeSelector(boolean newOpcodeSelector) {
		boolean oldOpcodeSelector = opcodeSelector;
		opcodeSelector = newOpcodeSelector;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FU_PORT__OPCODE_SELECTOR,
					oldOpcodeSelector, opcodeSelector));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void connect(Socket socket) {
		if (socket.isInput()) {
			inputSocket = socket;
		} else {
			outputSocket = socket;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.FU_PORT__NAME:
			return getName();
		case ArchitecturePackage.FU_PORT__INPUT_SOCKET:
			if (resolve)
				return getInputSocket();
			return basicGetInputSocket();
		case ArchitecturePackage.FU_PORT__OUTPUT_SOCKET:
			if (resolve)
				return getOutputSocket();
			return basicGetOutputSocket();
		case ArchitecturePackage.FU_PORT__WIDTH:
			return getWidth();
		case ArchitecturePackage.FU_PORT__TRIGGER:
			return isTrigger();
		case ArchitecturePackage.FU_PORT__OPCODE_SELECTOR:
			return isOpcodeSelector();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ArchitecturePackage.FU_PORT__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.FU_PORT__INPUT_SOCKET:
			setInputSocket((Socket) newValue);
			return;
		case ArchitecturePackage.FU_PORT__OUTPUT_SOCKET:
			setOutputSocket((Socket) newValue);
			return;
		case ArchitecturePackage.FU_PORT__WIDTH:
			setWidth((Integer) newValue);
			return;
		case ArchitecturePackage.FU_PORT__TRIGGER:
			setTrigger((Boolean) newValue);
			return;
		case ArchitecturePackage.FU_PORT__OPCODE_SELECTOR:
			setOpcodeSelector((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.FU_PORT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.FU_PORT__INPUT_SOCKET:
			unsetInputSocket();
			return;
		case ArchitecturePackage.FU_PORT__OUTPUT_SOCKET:
			unsetOutputSocket();
			return;
		case ArchitecturePackage.FU_PORT__WIDTH:
			setWidth(WIDTH_EDEFAULT);
			return;
		case ArchitecturePackage.FU_PORT__TRIGGER:
			setTrigger(TRIGGER_EDEFAULT);
			return;
		case ArchitecturePackage.FU_PORT__OPCODE_SELECTOR:
			setOpcodeSelector(OPCODE_SELECTOR_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.FU_PORT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case ArchitecturePackage.FU_PORT__INPUT_SOCKET:
			return isSetInputSocket();
		case ArchitecturePackage.FU_PORT__OUTPUT_SOCKET:
			return isSetOutputSocket();
		case ArchitecturePackage.FU_PORT__WIDTH:
			return width != WIDTH_EDEFAULT;
		case ArchitecturePackage.FU_PORT__TRIGGER:
			return trigger != TRIGGER_EDEFAULT;
		case ArchitecturePackage.FU_PORT__OPCODE_SELECTOR:
			return opcodeSelector != OPCODE_SELECTOR_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", width: ");
		result.append(width);
		result.append(", trigger: ");
		result.append(trigger);
		result.append(", opcodeSelector: ");
		result.append(opcodeSelector);
		result.append(')');
		return result.toString();
	}

} //FuPortImpl

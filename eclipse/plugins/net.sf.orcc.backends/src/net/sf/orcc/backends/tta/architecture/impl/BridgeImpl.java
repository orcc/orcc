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
package net.sf.orcc.backends.tta.architecture.impl;

import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.Bridge;
import net.sf.orcc.backends.tta.architecture.Bus;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bridge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BridgeImpl#getInputBus <em>Input Bus</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BridgeImpl#getOutputBus <em>Output Bus</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BridgeImpl extends EObjectImpl implements Bridge {
	/**
	 * The cached value of the '{@link #getInputBus() <em>Input Bus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputBus()
	 * @generated
	 * @ordered
	 */
	protected Bus inputBus;

	/**
	 * The cached value of the '{@link #getOutputBus() <em>Output Bus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputBus()
	 * @generated
	 * @ordered
	 */
	protected Bus outputBus;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BridgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.BRIDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bus getInputBus() {
		if (inputBus != null && inputBus.eIsProxy()) {
			InternalEObject oldInputBus = (InternalEObject) inputBus;
			inputBus = (Bus) eResolveProxy(oldInputBus);
			if (inputBus != oldInputBus) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.BRIDGE__INPUT_BUS, oldInputBus,
							inputBus));
			}
		}
		return inputBus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bus basicGetInputBus() {
		return inputBus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputBus(Bus newInputBus) {
		Bus oldInputBus = inputBus;
		inputBus = newInputBus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.BRIDGE__INPUT_BUS, oldInputBus,
					inputBus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bus getOutputBus() {
		if (outputBus != null && outputBus.eIsProxy()) {
			InternalEObject oldOutputBus = (InternalEObject) outputBus;
			outputBus = (Bus) eResolveProxy(oldOutputBus);
			if (outputBus != oldOutputBus) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.BRIDGE__OUTPUT_BUS,
							oldOutputBus, outputBus));
			}
		}
		return outputBus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bus basicGetOutputBus() {
		return outputBus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputBus(Bus newOutputBus) {
		Bus oldOutputBus = outputBus;
		outputBus = newOutputBus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.BRIDGE__OUTPUT_BUS, oldOutputBus,
					outputBus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.BRIDGE__INPUT_BUS:
			if (resolve)
				return getInputBus();
			return basicGetInputBus();
		case ArchitecturePackage.BRIDGE__OUTPUT_BUS:
			if (resolve)
				return getOutputBus();
			return basicGetOutputBus();
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
		case ArchitecturePackage.BRIDGE__INPUT_BUS:
			setInputBus((Bus) newValue);
			return;
		case ArchitecturePackage.BRIDGE__OUTPUT_BUS:
			setOutputBus((Bus) newValue);
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
		case ArchitecturePackage.BRIDGE__INPUT_BUS:
			setInputBus((Bus) null);
			return;
		case ArchitecturePackage.BRIDGE__OUTPUT_BUS:
			setOutputBus((Bus) null);
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
		case ArchitecturePackage.BRIDGE__INPUT_BUS:
			return inputBus != null;
		case ArchitecturePackage.BRIDGE__OUTPUT_BUS:
			return outputBus != null;
		}
		return super.eIsSet(featureID);
	}

} //BridgeImpl

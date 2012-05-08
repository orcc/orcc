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
package net.sf.orcc.backends.llvm.tta.architecture.impl;

import java.util.Collection;

import net.sf.orcc.backends.llvm.tta.architecture.AddressSpace;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.llvm.tta.architecture.FuPort;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Operation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Function Unit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.FunctionUnitImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.FunctionUnitImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.FunctionUnitImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.FunctionUnitImpl#getAddressSpace <em>Address Space</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.FunctionUnitImpl#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionUnitImpl extends PortImpl implements FunctionUnit {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<Operation> operations;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<FuPort> ports;

	/**
	 * The cached value of the '{@link #getAddressSpace() <em>Address Space</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getAddressSpace()
	 * @generated
	 * @ordered
	 */
	protected AddressSpace addressSpace;

	/**
	 * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected String implementation = IMPLEMENTATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.FUNCTION_UNIT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FUNCTION_UNIT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Operation> getOperations() {
		if (operations == null) {
			operations = new EObjectContainmentEList<Operation>(
					Operation.class, this,
					ArchitecturePackage.FUNCTION_UNIT__OPERATIONS);
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FuPort> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentEList<FuPort>(FuPort.class, this,
					ArchitecturePackage.FUNCTION_UNIT__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AddressSpace getAddressSpace() {
		if (addressSpace != null && addressSpace.eIsProxy()) {
			InternalEObject oldAddressSpace = (InternalEObject) addressSpace;
			addressSpace = (AddressSpace) eResolveProxy(oldAddressSpace);
			if (addressSpace != oldAddressSpace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.FUNCTION_UNIT__ADDRESS_SPACE,
							oldAddressSpace, addressSpace));
			}
		}
		return addressSpace;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AddressSpace basicGetAddressSpace() {
		return addressSpace;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddressSpace(AddressSpace newAddressSpace) {
		AddressSpace oldAddressSpace = addressSpace;
		addressSpace = newAddressSpace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FUNCTION_UNIT__ADDRESS_SPACE,
					oldAddressSpace, addressSpace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementation() {
		return implementation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(String newImplementation) {
		String oldImplementation = implementation;
		implementation = newImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.FUNCTION_UNIT__IMPLEMENTATION,
					oldImplementation, implementation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ArchitecturePackage.FUNCTION_UNIT__OPERATIONS:
			return ((InternalEList<?>) getOperations()).basicRemove(otherEnd,
					msgs);
		case ArchitecturePackage.FUNCTION_UNIT__PORTS:
			return ((InternalEList<?>) getPorts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.FUNCTION_UNIT__NAME:
			return getName();
		case ArchitecturePackage.FUNCTION_UNIT__OPERATIONS:
			return getOperations();
		case ArchitecturePackage.FUNCTION_UNIT__PORTS:
			return getPorts();
		case ArchitecturePackage.FUNCTION_UNIT__ADDRESS_SPACE:
			if (resolve)
				return getAddressSpace();
			return basicGetAddressSpace();
		case ArchitecturePackage.FUNCTION_UNIT__IMPLEMENTATION:
			return getImplementation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ArchitecturePackage.FUNCTION_UNIT__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.FUNCTION_UNIT__OPERATIONS:
			getOperations().clear();
			getOperations().addAll((Collection<? extends Operation>) newValue);
			return;
		case ArchitecturePackage.FUNCTION_UNIT__PORTS:
			getPorts().clear();
			getPorts().addAll((Collection<? extends FuPort>) newValue);
			return;
		case ArchitecturePackage.FUNCTION_UNIT__ADDRESS_SPACE:
			setAddressSpace((AddressSpace) newValue);
			return;
		case ArchitecturePackage.FUNCTION_UNIT__IMPLEMENTATION:
			setImplementation((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.FUNCTION_UNIT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.FUNCTION_UNIT__OPERATIONS:
			getOperations().clear();
			return;
		case ArchitecturePackage.FUNCTION_UNIT__PORTS:
			getPorts().clear();
			return;
		case ArchitecturePackage.FUNCTION_UNIT__ADDRESS_SPACE:
			setAddressSpace((AddressSpace) null);
			return;
		case ArchitecturePackage.FUNCTION_UNIT__IMPLEMENTATION:
			setImplementation(IMPLEMENTATION_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.FUNCTION_UNIT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case ArchitecturePackage.FUNCTION_UNIT__OPERATIONS:
			return operations != null && !operations.isEmpty();
		case ArchitecturePackage.FUNCTION_UNIT__PORTS:
			return ports != null && !ports.isEmpty();
		case ArchitecturePackage.FUNCTION_UNIT__ADDRESS_SPACE:
			return addressSpace != null;
		case ArchitecturePackage.FUNCTION_UNIT__IMPLEMENTATION:
			return IMPLEMENTATION_EDEFAULT == null ? implementation != null
					: !IMPLEMENTATION_EDEFAULT.equals(implementation);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", implementation: ");
		result.append(implementation);
		result.append(')');
		return result.toString();
	}

} // FunctionUnitImpl

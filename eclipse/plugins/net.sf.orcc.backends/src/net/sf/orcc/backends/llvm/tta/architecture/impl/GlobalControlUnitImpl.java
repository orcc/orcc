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

import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.llvm.tta.architecture.FuPort;
import net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Operation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Global Control Unit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getReturnAddress <em>Return Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getAddressSpace <em>Address Space</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getDelaySlots <em>Delay Slots</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.GlobalControlUnitImpl#getGuardLatency <em>Guard Latency</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GlobalControlUnitImpl extends EObjectImpl implements
		GlobalControlUnit {
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
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<FuPort> ports;

	/**
	 * The cached value of the '{@link #getReturnAddress() <em>Return Address</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getReturnAddress()
	 * @generated
	 * @ordered
	 */
	protected FuPort returnAddress;

	/**
	 * The cached value of the '{@link #getAddressSpace() <em>Address Space</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getAddressSpace()
	 * @generated
	 * @ordered
	 */
	protected Memory addressSpace;

	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<Operation> operations;

	/**
	 * The default value of the '{@link #getDelaySlots() <em>Delay Slots</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDelaySlots()
	 * @generated
	 * @ordered
	 */
	protected static final int DELAY_SLOTS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDelaySlots() <em>Delay Slots</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDelaySlots()
	 * @generated
	 * @ordered
	 */
	protected int delaySlots = DELAY_SLOTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuardLatency() <em>Guard Latency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getGuardLatency()
	 * @generated
	 * @ordered
	 */
	protected static final int GUARD_LATENCY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getGuardLatency() <em>Guard Latency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getGuardLatency()
	 * @generated
	 * @ordered
	 */
	protected int guardLatency = GUARD_LATENCY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalControlUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.GLOBAL_CONTROL_UNIT;
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
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FuPort> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentEList<FuPort>(FuPort.class, this,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FuPort getReturnAddress() {
		return returnAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturnAddress(FuPort newReturnAddress,
			NotificationChain msgs) {
		FuPort oldReturnAddress = returnAddress;
		returnAddress = newReturnAddress;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS,
					oldReturnAddress, newReturnAddress);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnAddress(FuPort newReturnAddress) {
		if (newReturnAddress != returnAddress) {
			NotificationChain msgs = null;
			if (returnAddress != null)
				msgs = ((InternalEObject) returnAddress)
						.eInverseRemove(
								this,
								EOPPOSITE_FEATURE_BASE
										- ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS,
								null, msgs);
			if (newReturnAddress != null)
				msgs = ((InternalEObject) newReturnAddress)
						.eInverseAdd(
								this,
								EOPPOSITE_FEATURE_BASE
										- ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS,
								null, msgs);
			msgs = basicSetReturnAddress(newReturnAddress, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS,
					newReturnAddress, newReturnAddress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Memory getAddressSpace() {
		if (addressSpace != null && addressSpace.eIsProxy()) {
			InternalEObject oldAddressSpace = (InternalEObject) addressSpace;
			addressSpace = (Memory) eResolveProxy(oldAddressSpace);
			if (addressSpace != oldAddressSpace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(
							this,
							Notification.RESOLVE,
							ArchitecturePackage.GLOBAL_CONTROL_UNIT__ADDRESS_SPACE,
							oldAddressSpace, addressSpace));
			}
		}
		return addressSpace;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Memory basicGetAddressSpace() {
		return addressSpace;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddressSpace(Memory newAddressSpace) {
		Memory oldAddressSpace = addressSpace;
		addressSpace = newAddressSpace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__ADDRESS_SPACE,
					oldAddressSpace, addressSpace));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Operation> getOperations() {
		if (operations == null) {
			operations = new EObjectContainmentEList<Operation>(
					Operation.class, this,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS);
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getDelaySlots() {
		return delaySlots;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setDelaySlots(int newDelaySlots) {
		int oldDelaySlots = delaySlots;
		delaySlots = newDelaySlots;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS,
					oldDelaySlots, delaySlots));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getGuardLatency() {
		return guardLatency;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuardLatency(int newGuardLatency) {
		int oldGuardLatency = guardLatency;
		guardLatency = newGuardLatency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY,
					oldGuardLatency, guardLatency));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			return ((InternalEList<?>) getPorts()).basicRemove(otherEnd, msgs);
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS:
			return basicSetReturnAddress(null, msgs);
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			return ((InternalEList<?>) getOperations()).basicRemove(otherEnd,
					msgs);
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
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__NAME:
			return getName();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			return getPorts();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS:
			return getReturnAddress();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__ADDRESS_SPACE:
			if (resolve)
				return getAddressSpace();
			return basicGetAddressSpace();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			return getOperations();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS:
			return getDelaySlots();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY:
			return getGuardLatency();
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
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			getPorts().clear();
			getPorts().addAll((Collection<? extends FuPort>) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS:
			setReturnAddress((FuPort) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__ADDRESS_SPACE:
			setAddressSpace((Memory) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			getOperations().clear();
			getOperations().addAll((Collection<? extends Operation>) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS:
			setDelaySlots((Integer) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY:
			setGuardLatency((Integer) newValue);
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
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			getPorts().clear();
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS:
			setReturnAddress((FuPort) null);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__ADDRESS_SPACE:
			setAddressSpace((Memory) null);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			getOperations().clear();
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS:
			setDelaySlots(DELAY_SLOTS_EDEFAULT);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY:
			setGuardLatency(GUARD_LATENCY_EDEFAULT);
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
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			return ports != null && !ports.isEmpty();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__RETURN_ADDRESS:
			return returnAddress != null;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__ADDRESS_SPACE:
			return addressSpace != null;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			return operations != null && !operations.isEmpty();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS:
			return delaySlots != DELAY_SLOTS_EDEFAULT;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY:
			return guardLatency != GUARD_LATENCY_EDEFAULT;
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
		result.append(", delaySlots: ");
		result.append(delaySlots);
		result.append(", guardLatency: ");
		result.append(guardLatency);
		result.append(')');
		return result.toString();
	}

} // GlobalControlUnitImpl

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

import java.util.Collection;

import net.sf.orcc.backends.tta.architecture.AddressSpace;
import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.tta.architecture.OperationCtrl;
import net.sf.orcc.backends.tta.architecture.Port;

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
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl#getPorts
 * <em>Ports</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl#getProgram
 * <em>Program</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl#getOperations
 * <em>Operations</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl#getDelaySlots
 * <em>Delay Slots</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl#getGuardLatency
 * <em>Guard Latency</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class GlobalControlUnitImpl extends EObjectImpl implements
		GlobalControlUnit {
	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getProgram() <em>Program</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProgram()
	 * @generated
	 * @ordered
	 */
	protected AddressSpace program;

	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected OperationCtrl operations;

	/**
	 * The default value of the '{@link #getDelaySlots() <em>Delay Slots</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDelaySlots()
	 * @generated
	 * @ordered
	 */
	protected static final int DELAY_SLOTS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDelaySlots() <em>Delay Slots</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDelaySlots()
	 * @generated
	 * @ordered
	 */
	protected int delaySlots = DELAY_SLOTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuardLatency()
	 * <em>Guard Latency</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getGuardLatency()
	 * @generated
	 * @ordered
	 */
	protected static final int GUARD_LATENCY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getGuardLatency()
	 * <em>Guard Latency</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getGuardLatency()
	 * @generated
	 * @ordered
	 */
	protected int guardLatency = GUARD_LATENCY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected GlobalControlUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.GLOBAL_CONTROL_UNIT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentEList<Port>(Port.class, this,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AddressSpace getProgram() {
		if (program != null && program.eIsProxy()) {
			InternalEObject oldProgram = (InternalEObject) program;
			program = (AddressSpace) eResolveProxy(oldProgram);
			if (program != oldProgram) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.GLOBAL_CONTROL_UNIT__PROGRAM,
							oldProgram, program));
			}
		}
		return program;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AddressSpace basicGetProgram() {
		return program;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProgram(AddressSpace newProgram) {
		AddressSpace oldProgram = program;
		program = newProgram;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__PROGRAM,
					oldProgram, program));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OperationCtrl getOperations() {
		if (operations != null && operations.eIsProxy()) {
			InternalEObject oldOperations = (InternalEObject) operations;
			operations = (OperationCtrl) eResolveProxy(oldOperations);
			if (operations != oldOperations) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(
							this,
							Notification.RESOLVE,
							ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS,
							oldOperations, operations));
			}
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OperationCtrl basicGetOperations() {
		return operations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOperations(OperationCtrl newOperations) {
		OperationCtrl oldOperations = operations;
		operations = newOperations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS,
					oldOperations, operations));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getDelaySlots() {
		return delaySlots;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
	 * @generated
	 */
	public int getGuardLatency() {
		return guardLatency;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			return ((InternalEList<?>) getPorts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			return getPorts();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PROGRAM:
			if (resolve)
				return getProgram();
			return basicGetProgram();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			if (resolve)
				return getOperations();
			return basicGetOperations();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS:
			return getDelaySlots();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY:
			return getGuardLatency();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			getPorts().clear();
			getPorts().addAll((Collection<? extends Port>) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PROGRAM:
			setProgram((AddressSpace) newValue);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			setOperations((OperationCtrl) newValue);
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
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			getPorts().clear();
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PROGRAM:
			setProgram((AddressSpace) null);
			return;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			setOperations((OperationCtrl) null);
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
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PORTS:
			return ports != null && !ports.isEmpty();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__PROGRAM:
			return program != null;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__OPERATIONS:
			return operations != null;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__DELAY_SLOTS:
			return delaySlots != DELAY_SLOTS_EDEFAULT;
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT__GUARD_LATENCY:
			return guardLatency != GUARD_LATENCY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (delaySlots: ");
		result.append(delaySlots);
		result.append(", guardLatency: ");
		result.append(guardLatency);
		result.append(')');
		return result.toString();
	}

} // GlobalControlUnitImpl

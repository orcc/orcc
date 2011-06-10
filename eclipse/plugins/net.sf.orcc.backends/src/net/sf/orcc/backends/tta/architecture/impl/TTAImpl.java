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
import net.sf.orcc.backends.tta.architecture.Bus;
import net.sf.orcc.backends.tta.architecture.FunctionalUnit;
import net.sf.orcc.backends.tta.architecture.RegisterFile;
import net.sf.orcc.backends.tta.architecture.Socket;
import net.sf.orcc.backends.tta.architecture.TTA;

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
 * <em><b>TTA</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getName <em>
 * Name</em>}</li>
 * <li>{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getBuses <em>
 * Buses</em>}</li>
 * <li>{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getSockets <em>
 * Sockets</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getFunctionalUnits
 * <em>Functional Units</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getRegisterFiles
 * <em>Register Files</em>}</li>
 * <li>{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getProgram <em>
 * Program</em>}</li>
 * <li>{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl#getData <em>
 * Data</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TTAImpl extends EObjectImpl implements TTA {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBuses() <em>Buses</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBuses()
	 * @generated
	 * @ordered
	 */
	protected EList<Bus> buses;

	/**
	 * The cached value of the '{@link #getSockets() <em>Sockets</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSockets()
	 * @generated
	 * @ordered
	 */
	protected EList<Socket> sockets;

	/**
	 * The cached value of the '{@link #getFunctionalUnits()
	 * <em>Functional Units</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFunctionalUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<FunctionalUnit> functionalUnits;

	/**
	 * The cached value of the '{@link #getRegisterFiles()
	 * <em>Register Files</em>}' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getRegisterFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<RegisterFile> registerFiles;

	/**
	 * The cached value of the '{@link #getProgram() <em>Program</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProgram()
	 * @generated
	 * @ordered
	 */
	protected AddressSpace program;

	/**
	 * The cached value of the '{@link #getData() <em>Data</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getData()
	 * @generated
	 * @ordered
	 */
	protected AddressSpace data;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TTAImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.TTA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.TTA__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Bus> getBuses() {
		if (buses == null) {
			buses = new EObjectContainmentEList<Bus>(Bus.class, this,
					ArchitecturePackage.TTA__BUSES);
		}
		return buses;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Socket> getSockets() {
		if (sockets == null) {
			sockets = new EObjectContainmentEList<Socket>(Socket.class, this,
					ArchitecturePackage.TTA__SOCKETS);
		}
		return sockets;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<FunctionalUnit> getFunctionalUnits() {
		if (functionalUnits == null) {
			functionalUnits = new EObjectContainmentEList<FunctionalUnit>(
					FunctionalUnit.class, this,
					ArchitecturePackage.TTA__FUNCTIONAL_UNITS);
		}
		return functionalUnits;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RegisterFile> getRegisterFiles() {
		if (registerFiles == null) {
			registerFiles = new EObjectContainmentEList<RegisterFile>(
					RegisterFile.class, this,
					ArchitecturePackage.TTA__REGISTER_FILES);
		}
		return registerFiles;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AddressSpace getProgram() {
		return program;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetProgram(AddressSpace newProgram,
			NotificationChain msgs) {
		AddressSpace oldProgram = program;
		program = newProgram;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ArchitecturePackage.TTA__PROGRAM,
					oldProgram, newProgram);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProgram(AddressSpace newProgram) {
		if (newProgram != program) {
			NotificationChain msgs = null;
			if (program != null)
				msgs = ((InternalEObject) program).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.TTA__PROGRAM, null, msgs);
			if (newProgram != null)
				msgs = ((InternalEObject) newProgram).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.TTA__PROGRAM, null, msgs);
			msgs = basicSetProgram(newProgram, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.TTA__PROGRAM, newProgram, newProgram));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AddressSpace getData() {
		return data;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetData(AddressSpace newData,
			NotificationChain msgs) {
		AddressSpace oldData = data;
		data = newData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ArchitecturePackage.TTA__DATA, oldData,
					newData);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setData(AddressSpace newData) {
		if (newData != data) {
			NotificationChain msgs = null;
			if (data != null)
				msgs = ((InternalEObject) data).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - ArchitecturePackage.TTA__DATA,
						null, msgs);
			if (newData != null)
				msgs = ((InternalEObject) newData).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - ArchitecturePackage.TTA__DATA,
						null, msgs);
			msgs = basicSetData(newData, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.TTA__DATA, newData, newData));
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
		case ArchitecturePackage.TTA__BUSES:
			return ((InternalEList<?>) getBuses()).basicRemove(otherEnd, msgs);
		case ArchitecturePackage.TTA__SOCKETS:
			return ((InternalEList<?>) getSockets())
					.basicRemove(otherEnd, msgs);
		case ArchitecturePackage.TTA__FUNCTIONAL_UNITS:
			return ((InternalEList<?>) getFunctionalUnits()).basicRemove(
					otherEnd, msgs);
		case ArchitecturePackage.TTA__REGISTER_FILES:
			return ((InternalEList<?>) getRegisterFiles()).basicRemove(
					otherEnd, msgs);
		case ArchitecturePackage.TTA__PROGRAM:
			return basicSetProgram(null, msgs);
		case ArchitecturePackage.TTA__DATA:
			return basicSetData(null, msgs);
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
		case ArchitecturePackage.TTA__NAME:
			return getName();
		case ArchitecturePackage.TTA__BUSES:
			return getBuses();
		case ArchitecturePackage.TTA__SOCKETS:
			return getSockets();
		case ArchitecturePackage.TTA__FUNCTIONAL_UNITS:
			return getFunctionalUnits();
		case ArchitecturePackage.TTA__REGISTER_FILES:
			return getRegisterFiles();
		case ArchitecturePackage.TTA__PROGRAM:
			return getProgram();
		case ArchitecturePackage.TTA__DATA:
			return getData();
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
		case ArchitecturePackage.TTA__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.TTA__BUSES:
			getBuses().clear();
			getBuses().addAll((Collection<? extends Bus>) newValue);
			return;
		case ArchitecturePackage.TTA__SOCKETS:
			getSockets().clear();
			getSockets().addAll((Collection<? extends Socket>) newValue);
			return;
		case ArchitecturePackage.TTA__FUNCTIONAL_UNITS:
			getFunctionalUnits().clear();
			getFunctionalUnits().addAll(
					(Collection<? extends FunctionalUnit>) newValue);
			return;
		case ArchitecturePackage.TTA__REGISTER_FILES:
			getRegisterFiles().clear();
			getRegisterFiles().addAll(
					(Collection<? extends RegisterFile>) newValue);
			return;
		case ArchitecturePackage.TTA__PROGRAM:
			setProgram((AddressSpace) newValue);
			return;
		case ArchitecturePackage.TTA__DATA:
			setData((AddressSpace) newValue);
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
		case ArchitecturePackage.TTA__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.TTA__BUSES:
			getBuses().clear();
			return;
		case ArchitecturePackage.TTA__SOCKETS:
			getSockets().clear();
			return;
		case ArchitecturePackage.TTA__FUNCTIONAL_UNITS:
			getFunctionalUnits().clear();
			return;
		case ArchitecturePackage.TTA__REGISTER_FILES:
			getRegisterFiles().clear();
			return;
		case ArchitecturePackage.TTA__PROGRAM:
			setProgram((AddressSpace) null);
			return;
		case ArchitecturePackage.TTA__DATA:
			setData((AddressSpace) null);
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
		case ArchitecturePackage.TTA__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case ArchitecturePackage.TTA__BUSES:
			return buses != null && !buses.isEmpty();
		case ArchitecturePackage.TTA__SOCKETS:
			return sockets != null && !sockets.isEmpty();
		case ArchitecturePackage.TTA__FUNCTIONAL_UNITS:
			return functionalUnits != null && !functionalUnits.isEmpty();
		case ArchitecturePackage.TTA__REGISTER_FILES:
			return registerFiles != null && !registerFiles.isEmpty();
		case ArchitecturePackage.TTA__PROGRAM:
			return program != null;
		case ArchitecturePackage.TTA__DATA:
			return data != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // TTAImpl

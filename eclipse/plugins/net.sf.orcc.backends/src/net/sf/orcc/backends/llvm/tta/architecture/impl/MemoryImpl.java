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
package net.sf.orcc.backends.llvm.tta.architecture.impl;

import java.util.Collection;

import net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.df.Connection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Memory</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getMinAddress <em>Min Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getMaxAddress <em>Max Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getDepth <em>Depth</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getWordWidth <em>Word Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getAddrWidth <em>Addr Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.MemoryImpl#getMappedConnections <em>Mapped Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MemoryImpl extends LinkImpl implements Memory {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getMinAddress() <em>Min Address</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMinAddress()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_ADDRESS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinAddress() <em>Min Address</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMinAddress()
	 * @generated
	 * @ordered
	 */
	protected int minAddress = MIN_ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxAddress() <em>Max Address</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMaxAddress()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_ADDRESS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxAddress() <em>Max Address</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMaxAddress()
	 * @generated
	 * @ordered
	 */
	protected int maxAddress = MAX_ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDepth() <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDepth()
	 * @generated
	 * @ordered
	 */
	protected static final int DEPTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDepth() <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDepth()
	 * @generated
	 * @ordered
	 */
	protected int depth = DEPTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getWordWidth() <em>Word Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWordWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WORD_WIDTH_EDEFAULT = 32;

	/**
	 * The cached value of the '{@link #getWordWidth() <em>Word Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWordWidth()
	 * @generated
	 * @ordered
	 */
	protected int wordWidth = WORD_WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getAddrWidth() <em>Addr Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAddrWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int ADDR_WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMappedConnections() <em>Mapped Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> mappedConnections;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected MemoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.MEMORY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public String getName() {
		return getLabel();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void setName(String newName) {
		setLabel(newName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinAddress() {
		return minAddress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinAddress(int newMinAddress) {
		int oldMinAddress = minAddress;
		minAddress = newMinAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.MEMORY__MIN_ADDRESS, oldMinAddress,
					minAddress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxAddress() {
		return maxAddress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxAddress(int newMaxAddress) {
		int oldMaxAddress = maxAddress;
		maxAddress = newMaxAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.MEMORY__MAX_ADDRESS, oldMaxAddress,
					maxAddress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setDepth(int newDepth) {
		int oldDepth = depth;
		depth = newDepth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.MEMORY__DEPTH, oldDepth, depth));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getWordWidth() {
		return wordWidth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setWordWidth(int newWordWidth) {
		int oldWordWidth = wordWidth;
		wordWidth = newWordWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.MEMORY__WORD_WIDTH, oldWordWidth,
					wordWidth));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public int getAddrWidth() {
		return BackendUtil.closestPow_2(Math.log(getDepth()) / Math.log(2));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getMappedConnections() {
		if (mappedConnections == null) {
			mappedConnections = new EObjectResolvingEList<Connection>(
					Connection.class, this,
					ArchitecturePackage.MEMORY__MAPPED_CONNECTIONS);
		}
		return mappedConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.MEMORY__NAME:
			return getName();
		case ArchitecturePackage.MEMORY__MIN_ADDRESS:
			return getMinAddress();
		case ArchitecturePackage.MEMORY__MAX_ADDRESS:
			return getMaxAddress();
		case ArchitecturePackage.MEMORY__DEPTH:
			return getDepth();
		case ArchitecturePackage.MEMORY__WORD_WIDTH:
			return getWordWidth();
		case ArchitecturePackage.MEMORY__ADDR_WIDTH:
			return getAddrWidth();
		case ArchitecturePackage.MEMORY__MAPPED_CONNECTIONS:
			return getMappedConnections();
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
		case ArchitecturePackage.MEMORY__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.MEMORY__MIN_ADDRESS:
			setMinAddress((Integer) newValue);
			return;
		case ArchitecturePackage.MEMORY__MAX_ADDRESS:
			setMaxAddress((Integer) newValue);
			return;
		case ArchitecturePackage.MEMORY__DEPTH:
			setDepth((Integer) newValue);
			return;
		case ArchitecturePackage.MEMORY__WORD_WIDTH:
			setWordWidth((Integer) newValue);
			return;
		case ArchitecturePackage.MEMORY__MAPPED_CONNECTIONS:
			getMappedConnections().clear();
			getMappedConnections().addAll(
					(Collection<? extends Connection>) newValue);
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
		case ArchitecturePackage.MEMORY__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.MEMORY__MIN_ADDRESS:
			setMinAddress(MIN_ADDRESS_EDEFAULT);
			return;
		case ArchitecturePackage.MEMORY__MAX_ADDRESS:
			setMaxAddress(MAX_ADDRESS_EDEFAULT);
			return;
		case ArchitecturePackage.MEMORY__DEPTH:
			setDepth(DEPTH_EDEFAULT);
			return;
		case ArchitecturePackage.MEMORY__WORD_WIDTH:
			setWordWidth(WORD_WIDTH_EDEFAULT);
			return;
		case ArchitecturePackage.MEMORY__MAPPED_CONNECTIONS:
			getMappedConnections().clear();
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
		case ArchitecturePackage.MEMORY__NAME:
			return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT
					.equals(getName());
		case ArchitecturePackage.MEMORY__MIN_ADDRESS:
			return minAddress != MIN_ADDRESS_EDEFAULT;
		case ArchitecturePackage.MEMORY__MAX_ADDRESS:
			return maxAddress != MAX_ADDRESS_EDEFAULT;
		case ArchitecturePackage.MEMORY__DEPTH:
			return depth != DEPTH_EDEFAULT;
		case ArchitecturePackage.MEMORY__WORD_WIDTH:
			return wordWidth != WORD_WIDTH_EDEFAULT;
		case ArchitecturePackage.MEMORY__ADDR_WIDTH:
			return getAddrWidth() != ADDR_WIDTH_EDEFAULT;
		case ArchitecturePackage.MEMORY__MAPPED_CONNECTIONS:
			return mappedConnections != null && !mappedConnections.isEmpty();
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
		result.append(" (minAddress: ");
		result.append(minAddress);
		result.append(", maxAddress: ");
		result.append(maxAddress);
		result.append(", depth: ");
		result.append(depth);
		result.append(", wordWidth: ");
		result.append(wordWidth);
		result.append(')');
		return result.toString();
	}

} // MemoryImpl

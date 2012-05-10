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
import net.sf.orcc.backends.llvm.tta.architecture.Buffer;
import net.sf.orcc.backends.util.BackendUtil;
import net.sf.orcc.df.Connection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Buffer</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link net.sf.orcc.backends.llvm.tta.architecture.impl.BufferImpl#getDepth
 * <em>Depth</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.llvm.tta.architecture.impl.BufferImpl#getWordWidth
 * <em>Word Width</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.llvm.tta.architecture.impl.BufferImpl#getAddrWidth
 * <em>Addr Width</em>}</li>
 * <li>
 * {@link net.sf.orcc.backends.llvm.tta.architecture.impl.BufferImpl#getMappedConnections
 * <em>Mapped Connections</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class BufferImpl extends LinkImpl implements Buffer {
	/**
	 * The default value of the '{@link #getDepth() <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDepth()
	 * @generated
	 * @ordered
	 */
	protected static final int DEPTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDepth() <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDepth()
	 * @generated
	 * @ordered
	 */
	protected int depth = DEPTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getWordWidth() <em>Word Width</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWordWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WORD_WIDTH_EDEFAULT = 32;

	/**
	 * The cached value of the '{@link #getWordWidth() <em>Word Width</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWordWidth()
	 * @generated
	 * @ordered
	 */
	protected int wordWidth = WORD_WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getAddrWidth() <em>Addr Width</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAddrWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int ADDR_WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMappedConnections()
	 * <em>Mapped Connections</em>}' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMappedConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> mappedConnections;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BufferImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.BUFFER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDepth(int newDepth) {
		int oldDepth = depth;
		depth = newDepth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.BUFFER__DEPTH, oldDepth, depth));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getWordWidth() {
		return wordWidth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setWordWidth(int newWordWidth) {
		int oldWordWidth = wordWidth;
		wordWidth = newWordWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.BUFFER__WORD_WIDTH, oldWordWidth,
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
	 * 
	 * @generated
	 */
	public EList<Connection> getMappedConnections() {
		if (mappedConnections == null) {
			mappedConnections = new EObjectResolvingEList<Connection>(
					Connection.class, this,
					ArchitecturePackage.BUFFER__MAPPED_CONNECTIONS);
		}
		return mappedConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.BUFFER__DEPTH:
			return getDepth();
		case ArchitecturePackage.BUFFER__WORD_WIDTH:
			return getWordWidth();
		case ArchitecturePackage.BUFFER__ADDR_WIDTH:
			return getAddrWidth();
		case ArchitecturePackage.BUFFER__MAPPED_CONNECTIONS:
			return getMappedConnections();
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
		case ArchitecturePackage.BUFFER__DEPTH:
			setDepth((Integer) newValue);
			return;
		case ArchitecturePackage.BUFFER__WORD_WIDTH:
			setWordWidth((Integer) newValue);
			return;
		case ArchitecturePackage.BUFFER__MAPPED_CONNECTIONS:
			getMappedConnections().clear();
			getMappedConnections().addAll(
					(Collection<? extends Connection>) newValue);
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
		case ArchitecturePackage.BUFFER__DEPTH:
			setDepth(DEPTH_EDEFAULT);
			return;
		case ArchitecturePackage.BUFFER__WORD_WIDTH:
			setWordWidth(WORD_WIDTH_EDEFAULT);
			return;
		case ArchitecturePackage.BUFFER__MAPPED_CONNECTIONS:
			getMappedConnections().clear();
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
		case ArchitecturePackage.BUFFER__DEPTH:
			return depth != DEPTH_EDEFAULT;
		case ArchitecturePackage.BUFFER__WORD_WIDTH:
			return wordWidth != WORD_WIDTH_EDEFAULT;
		case ArchitecturePackage.BUFFER__ADDR_WIDTH:
			return getAddrWidth() != ADDR_WIDTH_EDEFAULT;
		case ArchitecturePackage.BUFFER__MAPPED_CONNECTIONS:
			return mappedConnections != null && !mappedConnections.isEmpty();
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
		result.append(" (depth: ");
		result.append(depth);
		result.append(", wordWidth: ");
		result.append(wordWidth);
		result.append(')');
		return result.toString();
	}

} // BufferImpl

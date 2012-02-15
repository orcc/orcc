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

import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.Implementation;
import net.sf.orcc.backends.tta.architecture.Port;
import net.sf.orcc.backends.tta.architecture.RegisterFile;

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
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Register File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getSize <em>Size</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getMaxReads <em>Max Reads</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getMaxWrites <em>Max Writes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RegisterFileImpl extends EObjectImpl implements RegisterFile {
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
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected int size = SIZE_EDEFAULT;

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
	 * The default value of the '{@link #getMaxReads() <em>Max Reads</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxReads()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_READS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxReads() <em>Max Reads</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxReads()
	 * @generated
	 * @ordered
	 */
	protected int maxReads = MAX_READS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxWrites() <em>Max Writes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxWrites()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_WRITES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxWrites() <em>Max Writes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxWrites()
	 * @generated
	 * @ordered
	 */
	protected int maxWrites = MAX_WRITES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected Implementation implementation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegisterFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.REGISTER_FILE;
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
					ArchitecturePackage.REGISTER_FILE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSize(int newSize) {
		int oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.REGISTER_FILE__SIZE, oldSize, size));
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
					ArchitecturePackage.REGISTER_FILE__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxReads() {
		return maxReads;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxReads(int newMaxReads) {
		int oldMaxReads = maxReads;
		maxReads = newMaxReads;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.REGISTER_FILE__MAX_READS, oldMaxReads,
					maxReads));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxWrites() {
		return maxWrites;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxWrites(int newMaxWrites) {
		int oldMaxWrites = maxWrites;
		maxWrites = newMaxWrites;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.REGISTER_FILE__MAX_WRITES,
					oldMaxWrites, maxWrites));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentEList<Port>(Port.class, this,
					ArchitecturePackage.REGISTER_FILE__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Implementation getImplementation() {
		if (implementation != null && implementation.eIsProxy()) {
			InternalEObject oldImplementation = (InternalEObject) implementation;
			implementation = (Implementation) eResolveProxy(oldImplementation);
			if (implementation != oldImplementation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ArchitecturePackage.REGISTER_FILE__IMPLEMENTATION,
							oldImplementation, implementation));
			}
		}
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Implementation basicGetImplementation() {
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementation(Implementation newImplementation) {
		Implementation oldImplementation = implementation;
		implementation = newImplementation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.REGISTER_FILE__IMPLEMENTATION,
					oldImplementation, implementation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ArchitecturePackage.REGISTER_FILE__PORTS:
			return ((InternalEList<?>) getPorts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.REGISTER_FILE__NAME:
			return getName();
		case ArchitecturePackage.REGISTER_FILE__SIZE:
			return getSize();
		case ArchitecturePackage.REGISTER_FILE__WIDTH:
			return getWidth();
		case ArchitecturePackage.REGISTER_FILE__MAX_READS:
			return getMaxReads();
		case ArchitecturePackage.REGISTER_FILE__MAX_WRITES:
			return getMaxWrites();
		case ArchitecturePackage.REGISTER_FILE__PORTS:
			return getPorts();
		case ArchitecturePackage.REGISTER_FILE__IMPLEMENTATION:
			if (resolve)
				return getImplementation();
			return basicGetImplementation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ArchitecturePackage.REGISTER_FILE__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.REGISTER_FILE__SIZE:
			setSize((Integer) newValue);
			return;
		case ArchitecturePackage.REGISTER_FILE__WIDTH:
			setWidth((Integer) newValue);
			return;
		case ArchitecturePackage.REGISTER_FILE__MAX_READS:
			setMaxReads((Integer) newValue);
			return;
		case ArchitecturePackage.REGISTER_FILE__MAX_WRITES:
			setMaxWrites((Integer) newValue);
			return;
		case ArchitecturePackage.REGISTER_FILE__PORTS:
			getPorts().clear();
			getPorts().addAll((Collection<? extends Port>) newValue);
			return;
		case ArchitecturePackage.REGISTER_FILE__IMPLEMENTATION:
			setImplementation((Implementation) newValue);
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
		case ArchitecturePackage.REGISTER_FILE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.REGISTER_FILE__SIZE:
			setSize(SIZE_EDEFAULT);
			return;
		case ArchitecturePackage.REGISTER_FILE__WIDTH:
			setWidth(WIDTH_EDEFAULT);
			return;
		case ArchitecturePackage.REGISTER_FILE__MAX_READS:
			setMaxReads(MAX_READS_EDEFAULT);
			return;
		case ArchitecturePackage.REGISTER_FILE__MAX_WRITES:
			setMaxWrites(MAX_WRITES_EDEFAULT);
			return;
		case ArchitecturePackage.REGISTER_FILE__PORTS:
			getPorts().clear();
			return;
		case ArchitecturePackage.REGISTER_FILE__IMPLEMENTATION:
			setImplementation((Implementation) null);
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
		case ArchitecturePackage.REGISTER_FILE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case ArchitecturePackage.REGISTER_FILE__SIZE:
			return size != SIZE_EDEFAULT;
		case ArchitecturePackage.REGISTER_FILE__WIDTH:
			return width != WIDTH_EDEFAULT;
		case ArchitecturePackage.REGISTER_FILE__MAX_READS:
			return maxReads != MAX_READS_EDEFAULT;
		case ArchitecturePackage.REGISTER_FILE__MAX_WRITES:
			return maxWrites != MAX_WRITES_EDEFAULT;
		case ArchitecturePackage.REGISTER_FILE__PORTS:
			return ports != null && !ports.isEmpty();
		case ArchitecturePackage.REGISTER_FILE__IMPLEMENTATION:
			return implementation != null;
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
		result.append(", size: ");
		result.append(size);
		result.append(", width: ");
		result.append(width);
		result.append(", maxReads: ");
		result.append(maxReads);
		result.append(", maxWrites: ");
		result.append(maxWrites);
		result.append(')');
		return result.toString();
	}

} //RegisterFileImpl

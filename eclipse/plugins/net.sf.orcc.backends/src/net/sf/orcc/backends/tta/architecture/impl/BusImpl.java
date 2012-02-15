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
import net.sf.orcc.backends.tta.architecture.Bus;

import net.sf.orcc.backends.tta.architecture.Guard;
import net.sf.orcc.backends.tta.architecture.Segment;
import net.sf.orcc.backends.tta.architecture.ShortImmediate;
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
 * An implementation of the model object '<em><b>Bus</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl#getGuards <em>Guards</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl#getSegments <em>Segments</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl#getShortImmediate <em>Short Immediate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BusImpl extends EObjectImpl implements Bus {
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
	 * The cached value of the '{@link #getGuards() <em>Guards</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuards()
	 * @generated
	 * @ordered
	 */
	protected EList<Guard> guards;

	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<Segment> segments;

	/**
	 * The cached value of the '{@link #getShortImmediate() <em>Short Immediate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortImmediate()
	 * @generated
	 * @ordered
	 */
	protected ShortImmediate shortImmediate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BusImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.BUS;
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
					ArchitecturePackage.BUS__NAME, oldName, name));
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
					ArchitecturePackage.BUS__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Guard> getGuards() {
		if (guards == null) {
			guards = new EObjectContainmentEList<Guard>(Guard.class, this,
					ArchitecturePackage.BUS__GUARDS);
		}
		return guards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Segment> getSegments() {
		if (segments == null) {
			segments = new EObjectContainmentEList<Segment>(Segment.class,
					this, ArchitecturePackage.BUS__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShortImmediate getShortImmediate() {
		return shortImmediate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShortImmediate(
			ShortImmediate newShortImmediate, NotificationChain msgs) {
		ShortImmediate oldShortImmediate = shortImmediate;
		shortImmediate = newShortImmediate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ArchitecturePackage.BUS__SHORT_IMMEDIATE,
					oldShortImmediate, newShortImmediate);
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
	public void setShortImmediate(ShortImmediate newShortImmediate) {
		if (newShortImmediate != shortImmediate) {
			NotificationChain msgs = null;
			if (shortImmediate != null)
				msgs = ((InternalEObject) shortImmediate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.BUS__SHORT_IMMEDIATE,
						null, msgs);
			if (newShortImmediate != null)
				msgs = ((InternalEObject) newShortImmediate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.BUS__SHORT_IMMEDIATE,
						null, msgs);
			msgs = basicSetShortImmediate(newShortImmediate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.BUS__SHORT_IMMEDIATE,
					newShortImmediate, newShortImmediate));
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
		case ArchitecturePackage.BUS__GUARDS:
			return ((InternalEList<?>) getGuards()).basicRemove(otherEnd, msgs);
		case ArchitecturePackage.BUS__SEGMENTS:
			return ((InternalEList<?>) getSegments()).basicRemove(otherEnd,
					msgs);
		case ArchitecturePackage.BUS__SHORT_IMMEDIATE:
			return basicSetShortImmediate(null, msgs);
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
		case ArchitecturePackage.BUS__NAME:
			return getName();
		case ArchitecturePackage.BUS__WIDTH:
			return getWidth();
		case ArchitecturePackage.BUS__GUARDS:
			return getGuards();
		case ArchitecturePackage.BUS__SEGMENTS:
			return getSegments();
		case ArchitecturePackage.BUS__SHORT_IMMEDIATE:
			return getShortImmediate();
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
		case ArchitecturePackage.BUS__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.BUS__WIDTH:
			setWidth((Integer) newValue);
			return;
		case ArchitecturePackage.BUS__GUARDS:
			getGuards().clear();
			getGuards().addAll((Collection<? extends Guard>) newValue);
			return;
		case ArchitecturePackage.BUS__SEGMENTS:
			getSegments().clear();
			getSegments().addAll((Collection<? extends Segment>) newValue);
			return;
		case ArchitecturePackage.BUS__SHORT_IMMEDIATE:
			setShortImmediate((ShortImmediate) newValue);
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
		case ArchitecturePackage.BUS__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.BUS__WIDTH:
			setWidth(WIDTH_EDEFAULT);
			return;
		case ArchitecturePackage.BUS__GUARDS:
			getGuards().clear();
			return;
		case ArchitecturePackage.BUS__SEGMENTS:
			getSegments().clear();
			return;
		case ArchitecturePackage.BUS__SHORT_IMMEDIATE:
			setShortImmediate((ShortImmediate) null);
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
		case ArchitecturePackage.BUS__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case ArchitecturePackage.BUS__WIDTH:
			return width != WIDTH_EDEFAULT;
		case ArchitecturePackage.BUS__GUARDS:
			return guards != null && !guards.isEmpty();
		case ArchitecturePackage.BUS__SEGMENTS:
			return segments != null && !segments.isEmpty();
		case ArchitecturePackage.BUS__SHORT_IMMEDIATE:
			return shortImmediate != null;
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
		result.append(')');
		return result.toString();
	}

} //BusImpl

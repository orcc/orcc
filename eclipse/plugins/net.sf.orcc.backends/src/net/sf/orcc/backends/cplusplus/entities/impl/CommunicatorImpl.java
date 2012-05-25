/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities.impl;

import net.sf.orcc.backends.cplusplus.entities.Communicator;
import net.sf.orcc.backends.cplusplus.entities.Interface;
import net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Communicator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.CommunicatorImpl#getIntf <em>Intf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CommunicatorImpl extends EObjectImpl implements Communicator {
	/**
	 * The cached value of the '{@link #getIntf() <em>Intf</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntf()
	 * @generated
	 * @ordered
	 */
	protected Interface intf;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunicatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return YaceEntitiesPackage.Literals.COMMUNICATOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface getIntf() {
		if (intf != null && intf.eIsProxy()) {
			InternalEObject oldIntf = (InternalEObject)intf;
			intf = (Interface)eResolveProxy(oldIntf);
			if (intf != oldIntf) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, YaceEntitiesPackage.COMMUNICATOR__INTF, oldIntf, intf));
			}
		}
		return intf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface basicGetIntf() {
		return intf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntf(Interface newIntf) {
		Interface oldIntf = intf;
		intf = newIntf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.COMMUNICATOR__INTF, oldIntf, intf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case YaceEntitiesPackage.COMMUNICATOR__INTF:
				if (resolve) return getIntf();
				return basicGetIntf();
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
			case YaceEntitiesPackage.COMMUNICATOR__INTF:
				setIntf((Interface)newValue);
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
			case YaceEntitiesPackage.COMMUNICATOR__INTF:
				setIntf((Interface)null);
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
			case YaceEntitiesPackage.COMMUNICATOR__INTF:
				return intf != null;
		}
		return super.eIsSet(featureID);
	}

} //CommunicatorImpl

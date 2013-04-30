/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities.impl;

import net.sf.orcc.backends.cplusplus.entities.Communicator;
import net.sf.orcc.backends.cplusplus.entities.Interface;
import net.sf.orcc.backends.cplusplus.entities.Sender;
import net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage;

import net.sf.orcc.df.Port;

import net.sf.orcc.df.impl.ActorImpl;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sender</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.SenderImpl#getIntf <em>Intf</em>}</li>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.SenderImpl#getInput <em>Input</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SenderImpl extends ActorImpl implements Sender {
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
	 * The cached value of the '{@link #getInput() <em>Input</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected Port input;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SenderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return YaceEntitiesPackage.Literals.SENDER;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, YaceEntitiesPackage.SENDER__INTF, oldIntf, intf));
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
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.SENDER__INTF, oldIntf, intf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port getInput() {
		if (input != null && input.eIsProxy()) {
			InternalEObject oldInput = (InternalEObject)input;
			input = (Port)eResolveProxy(oldInput);
			if (input != oldInput) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, YaceEntitiesPackage.SENDER__INPUT, oldInput, input));
			}
		}
		return input;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port basicGetInput() {
		return input;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInput(Port newInput) {
		Port oldInput = input;
		input = newInput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.SENDER__INPUT, oldInput, input));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case YaceEntitiesPackage.SENDER__INTF:
				if (resolve) return getIntf();
				return basicGetIntf();
			case YaceEntitiesPackage.SENDER__INPUT:
				if (resolve) return getInput();
				return basicGetInput();
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
			case YaceEntitiesPackage.SENDER__INTF:
				setIntf((Interface)newValue);
				return;
			case YaceEntitiesPackage.SENDER__INPUT:
				setInput((Port)newValue);
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
			case YaceEntitiesPackage.SENDER__INTF:
				setIntf((Interface)null);
				return;
			case YaceEntitiesPackage.SENDER__INPUT:
				setInput((Port)null);
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
			case YaceEntitiesPackage.SENDER__INTF:
				return intf != null;
			case YaceEntitiesPackage.SENDER__INPUT:
				return input != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Communicator.class) {
			switch (derivedFeatureID) {
				case YaceEntitiesPackage.SENDER__INTF: return YaceEntitiesPackage.COMMUNICATOR__INTF;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Communicator.class) {
			switch (baseFeatureID) {
				case YaceEntitiesPackage.COMMUNICATOR__INTF: return YaceEntitiesPackage.SENDER__INTF;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //SenderImpl

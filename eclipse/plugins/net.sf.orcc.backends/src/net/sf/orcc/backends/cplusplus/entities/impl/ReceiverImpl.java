/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities.impl;

import net.sf.orcc.backends.cplusplus.entities.Communicator;
import net.sf.orcc.backends.cplusplus.entities.Interface;
import net.sf.orcc.backends.cplusplus.entities.Receiver;
import net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage;

import net.sf.orcc.df.Port;

import net.sf.orcc.df.impl.ActorImpl;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Receiver</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.ReceiverImpl#getIntf <em>Intf</em>}</li>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.ReceiverImpl#getOutput <em>Output</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReceiverImpl extends ActorImpl implements Receiver {
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
	 * The cached value of the '{@link #getOutput() <em>Output</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutput()
	 * @generated
	 * @ordered
	 */
	protected Port output;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReceiverImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return YaceEntitiesPackage.Literals.RECEIVER;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, YaceEntitiesPackage.RECEIVER__INTF, oldIntf, intf));
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
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.RECEIVER__INTF, oldIntf, intf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port getOutput() {
		if (output != null && output.eIsProxy()) {
			InternalEObject oldOutput = (InternalEObject)output;
			output = (Port)eResolveProxy(oldOutput);
			if (output != oldOutput) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, YaceEntitiesPackage.RECEIVER__OUTPUT, oldOutput, output));
			}
		}
		return output;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port basicGetOutput() {
		return output;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutput(Port newOutput) {
		Port oldOutput = output;
		output = newOutput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.RECEIVER__OUTPUT, oldOutput, output));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case YaceEntitiesPackage.RECEIVER__INTF:
				if (resolve) return getIntf();
				return basicGetIntf();
			case YaceEntitiesPackage.RECEIVER__OUTPUT:
				if (resolve) return getOutput();
				return basicGetOutput();
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
			case YaceEntitiesPackage.RECEIVER__INTF:
				setIntf((Interface)newValue);
				return;
			case YaceEntitiesPackage.RECEIVER__OUTPUT:
				setOutput((Port)newValue);
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
			case YaceEntitiesPackage.RECEIVER__INTF:
				setIntf((Interface)null);
				return;
			case YaceEntitiesPackage.RECEIVER__OUTPUT:
				setOutput((Port)null);
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
			case YaceEntitiesPackage.RECEIVER__INTF:
				return intf != null;
			case YaceEntitiesPackage.RECEIVER__OUTPUT:
				return output != null;
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
				case YaceEntitiesPackage.RECEIVER__INTF: return YaceEntitiesPackage.COMMUNICATOR__INTF;
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
				case YaceEntitiesPackage.COMMUNICATOR__INTF: return YaceEntitiesPackage.RECEIVER__INTF;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ReceiverImpl

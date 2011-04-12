/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>QSDF Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.moc.impl.QSDFMoCImpl#getConfigurations <em>Configurations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QSDFMoCImpl extends MoCImpl implements QSDFMoC {
	/**
	 * The cached value of the '{@link #getConfigurations() <em>Configurations</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EMap<Action, SDFMoC> configurations;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected QSDFMoCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MocPackage.QSDF_MO_C__CONFIGURATIONS:
				return getConfigurations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MocPackage.QSDF_MO_C__CONFIGURATIONS:
				return configurations != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MocPackage.QSDF_MO_C__CONFIGURATIONS:
				setConfigurations((EMap<Action, SDFMoC>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MocPackage.Literals.QSDF_MO_C;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MocPackage.QSDF_MO_C__CONFIGURATIONS:
				setConfigurations((EMap<Action, SDFMoC>)null);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Set<Action> getActions() {
		return configurations.keySet();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Action, SDFMoC> getConfigurations() {
		return configurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigurations(EMap<Action, SDFMoC> newConfigurations) {
		EMap<Action, SDFMoC> oldConfigurations = configurations;
		configurations = newConfigurations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MocPackage.QSDF_MO_C__CONFIGURATIONS, oldConfigurations, configurations));
	}

	@Override
	public SDFMoC getStaticClass(Action action) {
		return configurations.get(action);
	}

	@Override
	public boolean isQuasiStatic() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (configurations: ");
		result.append(configurations);
		result.append(')');
		return result.toString();
	}

} // QSDFMoCImpl

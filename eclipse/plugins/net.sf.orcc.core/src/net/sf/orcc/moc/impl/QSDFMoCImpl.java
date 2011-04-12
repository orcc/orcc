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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

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
	 * The cached value of the '{@link #getConfigurations() <em>Configurations</em>}' map.
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
				if (coreType) return getConfigurations();
				else return getConfigurations().map();
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
				return configurations != null && !configurations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MocPackage.QSDF_MO_C__CONFIGURATIONS:
				((EStructuralFeature.Setting)getConfigurations()).set(newValue);
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
				getConfigurations().clear();
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
		if (configurations == null) {
			configurations = new EcoreEMap<Action,SDFMoC>(MocPackage.Literals.ACTION_TO_SDF_MO_CMAP_ENTRY, ActionToSDFMoCMapEntryImpl.class, this, MocPackage.QSDF_MO_C__CONFIGURATIONS);
		}
		return configurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MocPackage.QSDF_MO_C__CONFIGURATIONS:
				return ((InternalEList<?>)getConfigurations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public SDFMoC getStaticClass(Action action) {
		return configurations.get(action);
	}

	@Override
	public boolean isQuasiStatic() {
		return true;
	}

} // QSDFMoCImpl

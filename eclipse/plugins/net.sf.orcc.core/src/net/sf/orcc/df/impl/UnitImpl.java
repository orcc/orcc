/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.Collection;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Unit;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Unit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getProcedures <em>Procedures</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UnitImpl extends EntityImpl implements Unit {
	/**
	 * The cached value of the '{@link #getConstants() <em>Constants</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConstants()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> constants;

	/**
	 * The cached value of the '{@link #getProcedures() <em>Procedures</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProcedures()
	 * @generated
	 * @ordered
	 */
	protected EList<Procedure> procedures;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected UnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DfPackage.UNIT__CONSTANTS:
				return getConstants();
			case DfPackage.UNIT__PROCEDURES:
				return getProcedures();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DfPackage.UNIT__CONSTANTS:
				return ((InternalEList<?>)getConstants()).basicRemove(otherEnd, msgs);
			case DfPackage.UNIT__PROCEDURES:
				return ((InternalEList<?>)getProcedures()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DfPackage.UNIT__CONSTANTS:
				return constants != null && !constants.isEmpty();
			case DfPackage.UNIT__PROCEDURES:
				return procedures != null && !procedures.isEmpty();
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
			case DfPackage.UNIT__CONSTANTS:
				getConstants().clear();
				getConstants().addAll((Collection<? extends Var>)newValue);
				return;
			case DfPackage.UNIT__PROCEDURES:
				getProcedures().clear();
				getProcedures().addAll((Collection<? extends Procedure>)newValue);
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
		return DfPackage.Literals.UNIT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DfPackage.UNIT__CONSTANTS:
				getConstants().clear();
				return;
			case DfPackage.UNIT__PROCEDURES:
				getProcedures().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getConstants() {
		if (constants == null) {
			constants = new EObjectContainmentEList<Var>(Var.class, this, DfPackage.UNIT__CONSTANTS);
		}
		return constants;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Procedure> getProcedures() {
		if (procedures == null) {
			procedures = new EObjectContainmentEList<Procedure>(Procedure.class, this, DfPackage.UNIT__PROCEDURES);
		}
		return procedures;
	}

	@Override
	public boolean isUnit() {
		return true;
	}

} // UnitImpl

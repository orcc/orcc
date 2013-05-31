/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import net.sf.orcc.moc.DPNMoC;
import net.sf.orcc.moc.MocPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>DPN Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class DPNMoCImpl extends MoCImpl implements DPNMoC {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected DPNMoCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MocPackage.Literals.DPN_MO_C;
	}

	@Override
	public boolean isDPN() {
		return true;
	}

	@Override
	public String toString() {
		return "DPN";
	}

} // DPNMoCImpl

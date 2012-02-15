/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MocPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mo C</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class MoCImpl extends EObjectImpl implements MoC {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MoCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MocPackage.Literals.MO_C;
	}

	@Override
	public boolean isCSDF() {
		return false;
	}

	@Override
	public boolean isDPN() {
		return false;
	}

	@Override
	public boolean isKPN() {
		return false;
	}

	@Override
	public boolean isQuasiStatic() {
		return false;
	}

	@Override
	public boolean isSDF() {
		return false;
	}

} //MoCImpl

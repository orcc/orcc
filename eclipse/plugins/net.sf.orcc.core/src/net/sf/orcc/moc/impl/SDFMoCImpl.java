/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>SDF Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class SDFMoCImpl extends CSDFMoCImpl implements SDFMoC {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SDFMoCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MocPackage.Literals.SDF_MO_C;
	}

	@Override
	public String getShortName() {
		return "SDF";
	}

	@Override
	public boolean isSDF() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SDF => ");
		builder.append(getInputPattern());
		builder.append(", ");
		builder.append(getOutputPattern());
		return builder.toString();
	}

} // SDFMoCImpl

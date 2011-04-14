/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.instructions.impl;

import net.sf.orcc.backends.instructions.InstSplit;
import net.sf.orcc.backends.instructions.InstructionsPackage;

import net.sf.orcc.ir.impl.InstSpecificImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inst Split</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class InstSplitImpl extends InstSpecificImpl implements InstSplit {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstSplitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InstructionsPackage.Literals.INST_SPLIT;
	}

	@Override
	public String toString() {
		return "<split>";
	}

} //InstSplitImpl

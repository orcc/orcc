/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.util.InstructionVisitor;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Inst Specific</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class InstSpecificImpl extends InstructionImpl implements InstSpecific {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InstSpecificImpl() {
		super();
	}

	@Override
	public void accept(InstructionVisitor visitor) {
		System.err.println("InstSpecificImpl.accept(visitor): Please switch to the EMF-based API");
		visitor.visit(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.INST_SPECIFIC;
	}

} // InstSpecificImpl

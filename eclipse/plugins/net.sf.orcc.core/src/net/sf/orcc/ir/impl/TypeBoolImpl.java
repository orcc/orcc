/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.TypeBool;

import org.eclipse.emf.ecore.EClass;

/**
 * This class defines a boolean type.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class TypeBoolImpl extends TypeImpl implements TypeBool {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeBoolImpl() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TypeBool);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE_BOOL;
	}

	@Override
	public int getSizeInBits() {
		return 1;
	}

	@Override
	public boolean isBool() {
		return true;
	}

	@Override
	public String toString() {
		return "bool";
	}

}

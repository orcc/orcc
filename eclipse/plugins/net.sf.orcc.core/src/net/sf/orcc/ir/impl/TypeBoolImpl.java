/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;
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

	/**
	 * Return 0 if obj is an instance of TypeBool, -2 if not
	 * 
	 * @param obj
	 *            Type to compare to
	 * @return 0 or -2
	 */
	@Override
	public int compareTo(Type obj) {
		int result;
		if (obj instanceof TypeBool) {
			result = 0;
		} else {
			result = -2;
		}

		return result;
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

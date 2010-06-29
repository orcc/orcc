/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;


import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.ir.type.TypePackage;
import net.sf.orcc.ir.type.TypeVisitor;

import org.eclipse.emf.ecore.EClass;

/**
 * This class defines a boolean type.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypePackage.Literals.BOOL_TYPE;
	}

	@Override
	public Object accept(TypeInterpreter interpreter) {
		return interpreter.interpret(this);
	}

	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TypeBool);
	}

	@Override
	public boolean isBool() {
		return true;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

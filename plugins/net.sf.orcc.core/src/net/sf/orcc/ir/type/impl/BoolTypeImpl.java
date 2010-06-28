/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.type.impl;

import net.sf.orcc.ir.impl.TypeImpl;

import net.sf.orcc.ir.type.BoolType;
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
public class BoolTypeImpl extends TypeImpl implements BoolType {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected BoolTypeImpl() {
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
		return (obj instanceof BoolType);
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

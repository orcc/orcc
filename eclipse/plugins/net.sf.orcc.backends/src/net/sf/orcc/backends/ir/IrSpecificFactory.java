/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see net.sf.orcc.backends.ir.IrSpecificPackage
 * @generated
 */
public interface IrSpecificFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	IrSpecificFactory eINSTANCE = net.sf.orcc.backends.ir.impl.IrSpecificFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Inst Assign Index</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Assign Index</em>'.
	 * @generated
	 */
	InstAssignIndex createInstAssignIndex();

	InstAssignIndex createInstAssignIndex(Var indexVar,
			List<Expression> listIndex, Type listType);

	/**
	 * Returns a new object of class '<em>Inst Cast</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Inst Cast</em>'.
	 * @generated
	 */
	InstCast createInstCast();

	InstCast createInstCast(Var source, Var target);

	/**
	 * Returns a new object of class '<em>Inst Ternary</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Ternary</em>'.
	 * @generated
	 */
	InstTernary createInstTernary();

	/**
	 * Returns a new object of class '<em>Block For</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Block For</em>'.
	 * @generated
	 */
	BlockFor createBlockFor();

	InstTernary createInstTernary(Var target, Expression condition,
			Expression trueValue, Expression falseValue);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IrSpecificPackage getIrSpecificPackage();

} // IrSpecificFactory

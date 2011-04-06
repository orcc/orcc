/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.instructions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.instructions.InstructionsPackage
 * @generated
 */
public interface InstructionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InstructionsFactory eINSTANCE = net.sf.orcc.backends.instructions.impl.InstructionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Ternary Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ternary Operation</em>'.
	 * @generated
	 */
	TernaryOperation createTernaryOperation();

	/**
	 * Returns a new object of class '<em>Assign Index</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Assign Index</em>'.
	 * @generated
	 */
	AssignIndex createAssignIndex();

	/**
	 * Returns a new object of class '<em>Split Instruction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Split Instruction</em>'.
	 * @generated
	 */
	SplitInstruction createSplitInstruction();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	InstructionsPackage getInstructionsPackage();

} //InstructionsFactory

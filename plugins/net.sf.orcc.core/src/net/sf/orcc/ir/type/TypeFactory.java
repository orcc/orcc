/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.type;

import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see net.sf.orcc.ir.type.TypePackage
 * @generated
 */
public interface TypeFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	TypeFactory eINSTANCE = net.sf.orcc.ir.type.impl.TypeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Bool Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Bool Type</em>'.
	 * @generated
	 */
	BoolType createTypeBool();

	/**
	 * Returns a new object of class '<em>Float Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Float Type</em>'.
	 * @generated
	 */
	FloatType createTypeFloat();

	/**
	 * Returns a new object of class '<em>Int Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Int Type</em>'.
	 * @generated
	 */
	IntType createTypeInt();

	/**
	 * Creates a new integer with the given size.
	 * 
	 * @param size
	 *            the size of this integer type
	 */
	IntType createTypeInt(int size);

	/**
	 * Returns a new object of class '<em>List Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>List Type</em>'.
	 * @generated
	 */
	ListType createTypeList();

	/**
	 * Creates a new list type with the given size and element type.
	 * 
	 * @param size
	 *            the size of this list type
	 * @param type
	 *            the type of this list's elements
	 */
	ListType createTypeList(int size, Type type);

	/**
	 * Returns a new object of class '<em>String Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>String Type</em>'.
	 * @generated
	 */
	StringType createTypeString();

	/**
	 * Returns a new object of class '<em>Uint Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Uint Type</em>'.
	 * @generated
	 */
	UintType createTypeUint();

	/**
	 * Returns a new object of class '<em>Void Type</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Void Type</em>'.
	 * @generated
	 */
	VoidType createTypeVoid();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	TypePackage getTypePackage();

} // TypeFactory

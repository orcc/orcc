/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see net.sf.orcc.ir.IrPackage
 * @generated
 */
public interface IrFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	IrFactory eINSTANCE = net.sf.orcc.ir.impl.IrFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Type Bool</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Bool</em>'.
	 * @generated
	 */
	TypeBool createTypeBool();

	/**
	 * Returns a new object of class '<em>Type Float</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Float</em>'.
	 * @generated
	 */
	TypeFloat createTypeFloat();

	/**
	 * Returns a new object of class '<em>Type Int</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Int</em>'.
	 * @generated
	 */
	TypeInt createTypeInt();

	/**
	 * Creates a new int type with the given size.
	 * 
	 * @param size
	 *            the size of this int type
	 */
	TypeInt createTypeInt(int size);

	/**
	 * Returns a new object of class '<em>Type List</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type List</em>'.
	 * @generated
	 */
	TypeList createTypeList();

	/**
	 * Creates a new list type with the given size and element type.
	 * 
	 * @param size
	 *            the size of this list type
	 * @param type
	 *            the type of this list's elements
	 */
	TypeList createTypeList(Expression size, Type type);

	/**
	 * Creates a new list type with the given size and element type.
	 * 
	 * @param size
	 *            the size of this list type
	 * @param type
	 *            the type of this list's elements
	 */
	TypeList createTypeList(int size, Type type);

	/**
	 * Returns a new object of class '<em>Type String</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type String</em>'.
	 * @generated
	 */
	TypeString createTypeString();

	/**
	 * Returns a new object of class '<em>Type Uint</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Uint</em>'.
	 * @generated
	 */
	TypeUint createTypeUint();

	/**
	 * Creates a new uint type with the given size.
	 * 
	 * @param size
	 *            the size of this uint type
	 */
	TypeUint createTypeUint(int size);

	/**
	 * Returns a new object of class '<em>Type Void</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Void</em>'.
	 * @generated
	 */
	TypeVoid createTypeVoid();

	/**
	 * Returns a new object of class '<em>Node Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Block</em>'.
	 * @generated
	 */
	NodeBlock createNodeBlock();

	/**
	 * Returns a new object of class '<em>Node If</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node If</em>'.
	 * @generated
	 */
	NodeIf createNodeIf();

	/**
	 * Returns a new object of class '<em>Node While</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node While</em>'.
	 * @generated
	 */
	NodeWhile createNodeWhile();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IrPackage getIrPackage();

} // IrFactory

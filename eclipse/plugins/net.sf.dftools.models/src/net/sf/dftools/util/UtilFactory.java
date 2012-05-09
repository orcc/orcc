/**
 */
package net.sf.dftools.util;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see net.sf.dftools.util.UtilPackage
 * @generated
 */
public interface UtilFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	UtilFactory eINSTANCE = net.sf.dftools.util.impl.UtilFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Creates a new attribute with the given name and POJO value.
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            a POJO
	 * @return a new attribute
	 */
	Attribute createAttribute(String name, Object value);

	/**
	 * Creates a new attribute with the given name and value. If the given value
	 * has no container, the new attribute becomes its new container.
	 * 
	 * @param name
	 *            name of the attribute
	 * @param value
	 *            an EMF EObject
	 * @return a new attribute
	 */
	Attribute createAttribute(String name, EObject value);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UtilPackage getUtilPackage();

} // UtilFactory

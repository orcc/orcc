/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage
 * @generated
 */
public interface YaceEntitiesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	YaceEntitiesFactory eINSTANCE = net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Sender</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sender</em>'.
	 * @generated
	 */
	Sender createSender();

	/**
	 * Returns a new object of class '<em>Receiver</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Receiver</em>'.
	 * @generated
	 */
	Receiver createReceiver();

	/**
	 * Returns a new object of class '<em>Interface Ethernet</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interface Ethernet</em>'.
	 * @generated
	 */
	InterfaceEthernet createInterfaceEthernet();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	YaceEntitiesPackage getYaceEntitiesPackage();

} //YaceEntitiesFactory

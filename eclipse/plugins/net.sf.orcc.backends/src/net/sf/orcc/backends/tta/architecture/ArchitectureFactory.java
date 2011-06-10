/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage
 * @generated
 */
public interface ArchitectureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArchitectureFactory eINSTANCE = net.sf.orcc.backends.tta.architecture.impl.ArchitectureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TTA</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TTA</em>'.
	 * @generated
	 */
	TTA createTTA();

	/**
	 * Returns a new object of class '<em>Bus</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bus</em>'.
	 * @generated
	 */
	Bus createBus();

	/**
	 * Returns a new object of class '<em>Global Control Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Control Unit</em>'.
	 * @generated
	 */
	GlobalControlUnit createGlobalControlUnit();

	/**
	 * Returns a new object of class '<em>Functional Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Functional Unit</em>'.
	 * @generated
	 */
	FunctionalUnit createFunctionalUnit();

	/**
	 * Returns a new object of class '<em>Register File</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Register File</em>'.
	 * @generated
	 */
	RegisterFile createRegisterFile();

	/**
	 * Returns a new object of class '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port</em>'.
	 * @generated
	 */
	Port createPort();

	/**
	 * Returns a new object of class '<em>Socket</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Socket</em>'.
	 * @generated
	 */
	Socket createSocket();

	/**
	 * Returns a new object of class '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation</em>'.
	 * @generated
	 */
	Operation createOperation();

	/**
	 * Returns a new object of class '<em>Operation Ctrl</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Ctrl</em>'.
	 * @generated
	 */
	OperationCtrl createOperationCtrl();

	/**
	 * Returns a new object of class '<em>Adress Space</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adress Space</em>'.
	 * @generated
	 */
	AdressSpace createAdressSpace();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ArchitecturePackage getArchitecturePackage();

} //ArchitectureFactory

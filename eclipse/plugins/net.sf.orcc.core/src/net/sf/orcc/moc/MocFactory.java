/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc;

import net.sf.orcc.df.Action;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see net.sf.orcc.moc.MocPackage
 * @generated
 */
public interface MocFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	MocFactory eINSTANCE = net.sf.orcc.moc.impl.MocFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>CSDF Mo C</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>CSDF Mo C</em>'.
	 * @generated
	 */
	CSDFMoC createCSDFMoC();

	/**
	 * Returns a new object of class '<em>DPN Mo C</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>DPN Mo C</em>'.
	 * @generated
	 */
	DPNMoC createDPNMoC();

	/**
	 * Returns a new object of class '<em>Invocation</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Invocation</em>'.
	 * @generated
	 */
	Invocation createInvocation();

	/**
	 * Returns a new invocation of the given action.
	 * 
	 * @param action
	 *            an action to invoke
	 * @return a new invocation of the given action
	 */
	Invocation createInvocation(Action action);

	/**
	 * Returns a new object of class '<em>KPN Mo C</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>KPN Mo C</em>'.
	 * @generated
	 */
	KPNMoC createKPNMoC();

	/**
	 * Returns a new object of class '<em>QSDF Mo C</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>QSDF Mo C</em>'.
	 * @generated
	 */
	QSDFMoC createQSDFMoC();

	/**
	 * Returns a new object of class '<em>SDF Mo C</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>SDF Mo C</em>'.
	 * @generated
	 */
	SDFMoC createSDFMoC();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MocPackage getMocPackage();

} // MocFactory

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Communicator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.Communicator#getIntf <em>Intf</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getCommunicator()
 * @model abstract="true"
 * @generated
 */
public interface Communicator extends EObject {
	/**
	 * Returns the value of the '<em><b>Intf</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intf</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intf</em>' reference.
	 * @see #setIntf(Interface)
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getCommunicator_Intf()
	 * @model
	 * @generated
	 */
	Interface getIntf();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.cplusplus.entities.Communicator#getIntf <em>Intf</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intf</em>' reference.
	 * @see #getIntf()
	 * @generated
	 */
	void setIntf(Interface value);

} // Communicator

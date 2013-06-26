/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.util.Adaptable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sender</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.Sender#getInput <em>Input</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getSender()
 * @model
 * @generated
 */
public interface Sender extends Actor, Communicator, Adaptable {
	/**
	 * Returns the value of the '<em><b>Input</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' reference.
	 * @see #setInput(Port)
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getSender_Input()
	 * @model
	 * @generated
	 */
	Port getInput();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.cplusplus.entities.Sender#getInput <em>Input</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input</em>' reference.
	 * @see #getInput()
	 * @generated
	 */
	void setInput(Port value);

} // Sender

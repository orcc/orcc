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
 * A representation of the model object '<em><b>Receiver</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.Receiver#getOutput <em>Output</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getReceiver()
 * @model
 * @generated
 */
public interface Receiver extends Actor, Communicator, Adaptable {
	/**
	 * Returns the value of the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' reference.
	 * @see #setOutput(Port)
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getReceiver_Output()
	 * @model
	 * @generated
	 */
	Port getOutput();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.cplusplus.entities.Receiver#getOutput <em>Output</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' reference.
	 * @see #getOutput()
	 * @generated
	 */
	void setOutput(Port value);

} // Receiver

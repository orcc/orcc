/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bridge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Bridge#getInputBus <em>Input Bus</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Bridge#getOutputBus <em>Output Bus</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getBridge()
 * @model
 * @generated
 */
public interface Bridge extends EObject {
	/**
	 * Returns the value of the '<em><b>Input Bus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Bus</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Bus</em>' reference.
	 * @see #setInputBus(Bus)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getBridge_InputBus()
	 * @model
	 * @generated
	 */
	Bus getInputBus();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.Bridge#getInputBus <em>Input Bus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input Bus</em>' reference.
	 * @see #getInputBus()
	 * @generated
	 */
	void setInputBus(Bus value);

	/**
	 * Returns the value of the '<em><b>Output Bus</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Bus</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Bus</em>' reference.
	 * @see #setOutputBus(Bus)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getBridge_OutputBus()
	 * @model
	 * @generated
	 */
	Bus getOutputBus();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.Bridge#getOutputBus <em>Output Bus</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Bus</em>' reference.
	 * @see #getOutputBus()
	 * @generated
	 */
	void setOutputBus(Bus value);

} // Bridge

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Term Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TermUnit#getFunctionUnit <em>Function Unit</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TermUnit#getPort <em>Port</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTermUnit()
 * @model
 * @generated
 */
public interface TermUnit extends Term {
	/**
	 * Returns the value of the '<em><b>Function Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Unit</em>' reference.
	 * @see #setFunctionUnit(FunctionUnit)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTermUnit_FunctionUnit()
	 * @model
	 * @generated
	 */
	FunctionUnit getFunctionUnit();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TermUnit#getFunctionUnit <em>Function Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Unit</em>' reference.
	 * @see #getFunctionUnit()
	 * @generated
	 */
	void setFunctionUnit(FunctionUnit value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(Port)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTermUnit_Port()
	 * @model
	 * @generated
	 */
	Port getPort();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TermUnit#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(Port value);

} // TermUnit

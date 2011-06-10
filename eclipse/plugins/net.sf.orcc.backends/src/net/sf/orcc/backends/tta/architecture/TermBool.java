/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Term Bool</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TermBool#getRegister <em>Register</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TermBool#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTermBool()
 * @model
 * @generated
 */
public interface TermBool extends Term {
	/**
	 * Returns the value of the '<em><b>Register</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Register</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Register</em>' reference.
	 * @see #setRegister(RegisterFile)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTermBool_Register()
	 * @model
	 * @generated
	 */
	RegisterFile getRegister();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TermBool#getRegister <em>Register</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Register</em>' reference.
	 * @see #getRegister()
	 * @generated
	 */
	void setRegister(RegisterFile value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTermBool_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TermBool#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // TermBool

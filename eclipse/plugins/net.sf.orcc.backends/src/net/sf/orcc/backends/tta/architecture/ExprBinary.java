/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expr Binary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ExprBinary#getOperator <em>Operator</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ExprBinary#getE1 <em>E1</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ExprBinary#getE2 <em>E2</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprBinary()
 * @model
 * @generated
 */
public interface ExprBinary extends Guard {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.tta.architecture.OpBinary}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.OpBinary
	 * @see #setOperator(OpBinary)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprBinary_Operator()
	 * @model default=""
	 * @generated
	 */
	OpBinary getOperator();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ExprBinary#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.OpBinary
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OpBinary value);

	/**
	 * Returns the value of the '<em><b>E1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>E1</em>' reference.
	 * @see #setE1(ExprUnary)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprBinary_E1()
	 * @model
	 * @generated
	 */
	ExprUnary getE1();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ExprBinary#getE1 <em>E1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>E1</em>' reference.
	 * @see #getE1()
	 * @generated
	 */
	void setE1(ExprUnary value);

	/**
	 * Returns the value of the '<em><b>E2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>E2</em>' reference.
	 * @see #setE2(ExprUnary)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprBinary_E2()
	 * @model
	 * @generated
	 */
	ExprUnary getE2();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ExprBinary#getE2 <em>E2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>E2</em>' reference.
	 * @see #getE2()
	 * @generated
	 */
	void setE2(ExprUnary value);

} // ExprBinary

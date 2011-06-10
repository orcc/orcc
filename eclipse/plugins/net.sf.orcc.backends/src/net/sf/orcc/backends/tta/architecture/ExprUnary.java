/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expr Unary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ExprUnary#getOperator <em>Operator</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ExprUnary#getTerm <em>Term</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprUnary()
 * @model
 * @generated
 */
public interface ExprUnary extends Guard {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.tta.architecture.OpUnary}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.OpUnary
	 * @see #setOperator(OpUnary)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprUnary_Operator()
	 * @model default=""
	 * @generated
	 */
	OpUnary getOperator();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ExprUnary#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.OpUnary
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OpUnary value);

	/**
	 * Returns the value of the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' reference.
	 * @see #setTerm(Term)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExprUnary_Term()
	 * @model
	 * @generated
	 */
	Term getTerm();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ExprUnary#getTerm <em>Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' reference.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Term value);

} // ExprUnary

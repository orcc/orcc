/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstSpecific;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inst Ternary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.InstTernary#getConditionValue <em>Condition Value</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstTernary#getTrueValue <em>True Value</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstTernary#getFalseValue <em>False Value</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstTernary#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstTernary()
 * @model
 * @generated
 */
public interface InstTernary extends InstSpecific {
	/**
	 * Returns the value of the '<em><b>Condition Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition Value</em>' containment reference.
	 * @see #setConditionValue(Expression)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstTernary_ConditionValue()
	 * @model containment="true"
	 * @generated
	 */
	Expression getConditionValue();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstTernary#getConditionValue <em>Condition Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition Value</em>' containment reference.
	 * @see #getConditionValue()
	 * @generated
	 */
	void setConditionValue(Expression value);

	/**
	 * Returns the value of the '<em><b>True Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>True Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>True Value</em>' containment reference.
	 * @see #setTrueValue(Expression)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstTernary_TrueValue()
	 * @model containment="true"
	 * @generated
	 */
	Expression getTrueValue();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstTernary#getTrueValue <em>True Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>True Value</em>' containment reference.
	 * @see #getTrueValue()
	 * @generated
	 */
	void setTrueValue(Expression value);

	/**
	 * Returns the value of the '<em><b>False Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>False Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>False Value</em>' containment reference.
	 * @see #setFalseValue(Expression)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstTernary_FalseValue()
	 * @model containment="true"
	 * @generated
	 */
	Expression getFalseValue();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstTernary#getFalseValue <em>False Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>False Value</em>' containment reference.
	 * @see #getFalseValue()
	 * @generated
	 */
	void setFalseValue(Expression value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Def)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstTernary_Target()
	 * @model containment="true"
	 * @generated
	 */
	Def getTarget();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstTernary#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Def value);

	/**
	 * Return <code>true</code> if the instruction is a ternary instruction
	 * 
	 * @return <code>true</code> if the instruction is a ternary instruction
	 */
	public boolean isInstTernary();

} // InstTernary

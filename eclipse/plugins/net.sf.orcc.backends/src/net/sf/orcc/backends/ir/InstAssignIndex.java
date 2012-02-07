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
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inst Assign Index</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.InstAssignIndex#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstAssignIndex#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstAssignIndex#getListType <em>List Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstAssignIndex()
 * @model
 * @generated
 */
public interface InstAssignIndex extends InstSpecific {
	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference list.
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstAssignIndex_Indexes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getIndexes();

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
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstAssignIndex_Target()
	 * @model containment="true"
	 * @generated
	 */
	Def getTarget();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstAssignIndex#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Def value);

	/**
	 * Return <code>true</code> if the instruction is an assign index
	 * instruction
	 * 
	 * @return <code>true</code> if the instruction is an assign index
	 *         instruction
	 */
	public boolean isInstAssignIndex();
	
	/**
	 * Returns the value of the '<em><b>List Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>List Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>List Type</em>' containment reference.
	 * @see #setListType(Type)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstAssignIndex_ListType()
	 * @model containment="true"
	 * @generated
	 */
	Type getListType();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstAssignIndex#getListType <em>List Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>List Type</em>' containment reference.
	 * @see #getListType()
	 * @generated
	 */
	void setListType(Type value);

} // InstAssignIndex

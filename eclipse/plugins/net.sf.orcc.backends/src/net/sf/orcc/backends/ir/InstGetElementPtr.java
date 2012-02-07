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
import net.sf.orcc.ir.Use;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inst Get Element Ptr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.InstGetElementPtr#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstGetElementPtr#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.InstGetElementPtr#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstGetElementPtr()
 * @model
 * @generated
 */
public interface InstGetElementPtr extends InstSpecific {
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
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstGetElementPtr_Indexes()
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
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstGetElementPtr_Target()
	 * @model containment="true"
	 * @generated
	 */
	Def getTarget();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstGetElementPtr#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Def value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(Use)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getInstGetElementPtr_Source()
	 * @model containment="true"
	 * @generated
	 */
	Use getSource();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.InstGetElementPtr#getSource <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Use value);
	
	/**
	 * Returns true if the current instruction is GEP (which is always true for
	 * the current instruction). This method is only useable to detect this
	 * specific instruction in template document.
	 * 
	 * @return true if current instruction is GEP
	 */
	public boolean isGep();

	@Override
	public boolean isLoad();

} // InstGetElementPtr

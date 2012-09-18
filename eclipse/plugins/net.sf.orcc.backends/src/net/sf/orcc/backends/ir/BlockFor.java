/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir;

import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node For</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.BlockFor#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.BlockFor#getJoinNode <em>Join Node</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.BlockFor#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.BlockFor#getNodes <em>Nodes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.BlockFor#getStep <em>Step</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.BlockFor#getInit <em>Init</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor()
 * @model
 * @generated
 */
public interface BlockFor extends Block {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Expression)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor_Condition()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondition();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.BlockFor#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * Returns the value of the '<em><b>Join Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Node</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Node</em>' containment reference.
	 * @see #setJoinNode(BlockBasic)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor_JoinNode()
	 * @model containment="true"
	 * @generated
	 */
	BlockBasic getJoinNode();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.BlockFor#getJoinNode <em>Join Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Node</em>' containment reference.
	 * @see #getJoinNode()
	 * @generated
	 */
	void setJoinNode(BlockBasic value);

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor_LineNumber()
	 * @model default="0"
	 * @generated
	 */
	int getLineNumber();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.BlockFor#getLineNumber <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Block}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Block> getNodes();

	/**
	 * Returns the value of the '<em><b>Step</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Step</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Step</em>' containment reference.
	 * @see #setStep(Instruction)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor_Step()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Instruction getStep();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.BlockFor#getStep <em>Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Step</em>' containment reference.
	 * @see #getStep()
	 * @generated
	 */
	void setStep(Instruction value);

	/**
	 * Returns the value of the '<em><b>Init</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init</em>' containment reference.
	 * @see #setInit(Instruction)
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#getBlockFor_Init()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Instruction getInit();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.ir.BlockFor#getInit <em>Init</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init</em>' containment reference.
	 * @see #getInit()
	 * @generated
	 */
	void setInit(Instruction value);

	public boolean isNodeFor();

} // NodeFor

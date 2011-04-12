/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.util;

import java.util.List;

import java.util.Map;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.ir.IrPackage
 * @generated
 */
public class IrSwitch<T2> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IrPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IrSwitch() {
		if (modelPackage == null) {
			modelPackage = IrPackage.eINSTANCE;
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseAction(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Actor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Actor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseActor(Actor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Def</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseDef(Def object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr Binary</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr Binary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprBinary(ExprBinary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr Bool</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr Bool</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprBool(ExprBool object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr Float</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr Float</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprFloat(ExprFloat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr Int</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr Int</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprInt(ExprInt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprList(ExprList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprString(ExprString object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr Unary</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr Unary</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprUnary(ExprUnary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expr Var</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expr Var</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseExprVar(ExprVar object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FSM</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FSM</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseFSM(FSM object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comparable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comparable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T> T2 caseComparable(Comparable<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EMap</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EMap</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T, T1> T2 caseEMap(EMap<T, T1> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Var To Port Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var To Port Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseVarToPortMapEntry(Map.Entry<Var, Port> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port To EInteger Object Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port To EInteger Object Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 casePortToEIntegerObjectMapEntry(Map.Entry<Port, Integer> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port To Var Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port To Var Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 casePortToVarMapEntry(Map.Entry<Port, Var> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Assign</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Assign</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstAssign(InstAssign object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Call</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstCall(InstCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Load</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Load</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstLoad(InstLoad object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Phi</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Phi</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstPhi(InstPhi object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Return</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Return</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstReturn(InstReturn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Instruction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Instruction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstruction(Instruction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Specific</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Specific</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstSpecific(InstSpecific object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inst Store</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inst Store</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseInstStore(InstStore object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseLocation(Location object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseNodeBlock(NodeBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node If</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node If</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseNodeIf(NodeIf object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node While</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node While</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseNodeWhile(NodeWhile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 casePattern(Pattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 casePort(Port object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Procedure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Procedure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseProcedure(Procedure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTag(Tag object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseType(Type object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Bool</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Bool</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeBool(TypeBool object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Float</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Float</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeFloat(TypeFloat object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Int</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Int</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeInt(TypeInt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeList(TypeList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeString(TypeString object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Uint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Uint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeUint(TypeUint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Void</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Void</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseTypeVoid(TypeVoid object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Use</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Use</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseUse(Use object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Var</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T2 caseVar(Var object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T2 defaultCase(EObject object) {
		return null;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T2 doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T2 doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T2 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case IrPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T2 result = caseExpression(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE: {
				Type type = (Type)theEObject;
				T2 result = caseType(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_BOOL: {
				TypeBool typeBool = (TypeBool)theEObject;
				T2 result = caseTypeBool(typeBool);
				if (result == null) result = caseType(typeBool);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_FLOAT: {
				TypeFloat typeFloat = (TypeFloat)theEObject;
				T2 result = caseTypeFloat(typeFloat);
				if (result == null) result = caseType(typeFloat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_INT: {
				TypeInt typeInt = (TypeInt)theEObject;
				T2 result = caseTypeInt(typeInt);
				if (result == null) result = caseType(typeInt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_LIST: {
				TypeList typeList = (TypeList)theEObject;
				T2 result = caseTypeList(typeList);
				if (result == null) result = caseType(typeList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_STRING: {
				TypeString typeString = (TypeString)theEObject;
				T2 result = caseTypeString(typeString);
				if (result == null) result = caseType(typeString);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_UINT: {
				TypeUint typeUint = (TypeUint)theEObject;
				T2 result = caseTypeUint(typeUint);
				if (result == null) result = caseType(typeUint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TYPE_VOID: {
				TypeVoid typeVoid = (TypeVoid)theEObject;
				T2 result = caseTypeVoid(typeVoid);
				if (result == null) result = caseType(typeVoid);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INSTRUCTION: {
				Instruction instruction = (Instruction)theEObject;
				T2 result = caseInstruction(instruction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.NODE_BLOCK: {
				NodeBlock nodeBlock = (NodeBlock)theEObject;
				T2 result = caseNodeBlock(nodeBlock);
				if (result == null) result = caseNode(nodeBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.NODE_IF: {
				NodeIf nodeIf = (NodeIf)theEObject;
				T2 result = caseNodeIf(nodeIf);
				if (result == null) result = caseNode(nodeIf);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.NODE_WHILE: {
				NodeWhile nodeWhile = (NodeWhile)theEObject;
				T2 result = caseNodeWhile(nodeWhile);
				if (result == null) result = caseNode(nodeWhile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.NODE: {
				Node node = (Node)theEObject;
				T2 result = caseNode(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.PROCEDURE: {
				Procedure procedure = (Procedure)theEObject;
				T2 result = caseProcedure(procedure);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_ASSIGN: {
				InstAssign instAssign = (InstAssign)theEObject;
				T2 result = caseInstAssign(instAssign);
				if (result == null) result = caseInstruction(instAssign);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_CALL: {
				InstCall instCall = (InstCall)theEObject;
				T2 result = caseInstCall(instCall);
				if (result == null) result = caseInstruction(instCall);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_LOAD: {
				InstLoad instLoad = (InstLoad)theEObject;
				T2 result = caseInstLoad(instLoad);
				if (result == null) result = caseInstruction(instLoad);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_PHI: {
				InstPhi instPhi = (InstPhi)theEObject;
				T2 result = caseInstPhi(instPhi);
				if (result == null) result = caseInstruction(instPhi);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_RETURN: {
				InstReturn instReturn = (InstReturn)theEObject;
				T2 result = caseInstReturn(instReturn);
				if (result == null) result = caseInstruction(instReturn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_SPECIFIC: {
				InstSpecific instSpecific = (InstSpecific)theEObject;
				T2 result = caseInstSpecific(instSpecific);
				if (result == null) result = caseInstruction(instSpecific);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.INST_STORE: {
				InstStore instStore = (InstStore)theEObject;
				T2 result = caseInstStore(instStore);
				if (result == null) result = caseInstruction(instStore);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.LOCATION: {
				Location location = (Location)theEObject;
				T2 result = caseLocation(location);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.VAR: {
				Var var = (Var)theEObject;
				T2 result = caseVar(var);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.USE: {
				Use use = (Use)theEObject;
				T2 result = caseUse(use);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_BINARY: {
				ExprBinary exprBinary = (ExprBinary)theEObject;
				T2 result = caseExprBinary(exprBinary);
				if (result == null) result = caseExpression(exprBinary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_BOOL: {
				ExprBool exprBool = (ExprBool)theEObject;
				T2 result = caseExprBool(exprBool);
				if (result == null) result = caseExpression(exprBool);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_FLOAT: {
				ExprFloat exprFloat = (ExprFloat)theEObject;
				T2 result = caseExprFloat(exprFloat);
				if (result == null) result = caseExpression(exprFloat);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_INT: {
				ExprInt exprInt = (ExprInt)theEObject;
				T2 result = caseExprInt(exprInt);
				if (result == null) result = caseExpression(exprInt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_LIST: {
				ExprList exprList = (ExprList)theEObject;
				T2 result = caseExprList(exprList);
				if (result == null) result = caseExpression(exprList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_STRING: {
				ExprString exprString = (ExprString)theEObject;
				T2 result = caseExprString(exprString);
				if (result == null) result = caseExpression(exprString);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_UNARY: {
				ExprUnary exprUnary = (ExprUnary)theEObject;
				T2 result = caseExprUnary(exprUnary);
				if (result == null) result = caseExpression(exprUnary);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.EXPR_VAR: {
				ExprVar exprVar = (ExprVar)theEObject;
				T2 result = caseExprVar(exprVar);
				if (result == null) result = caseExpression(exprVar);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.DEF: {
				Def def = (Def)theEObject;
				T2 result = caseDef(def);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.ACTION: {
				Action action = (Action)theEObject;
				T2 result = caseAction(action);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.ACTOR: {
				Actor actor = (Actor)theEObject;
				T2 result = caseActor(actor);
				if (result == null) result = caseComparable(actor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.PATTERN: {
				Pattern pattern = (Pattern)theEObject;
				T2 result = casePattern(pattern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.PORT: {
				Port port = (Port)theEObject;
				T2 result = casePort(port);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.TAG: {
				Tag tag = (Tag)theEObject;
				T2 result = caseTag(tag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.FSM: {
				FSM fsm = (FSM)theEObject;
				T2 result = caseFSM(fsm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.VAR_TO_PORT_MAP_ENTRY: {
				@SuppressWarnings("unchecked") Map.Entry<Var, Port> varToPortMapEntry = (Map.Entry<Var, Port>)theEObject;
				T2 result = caseVarToPortMapEntry(varToPortMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.PORT_TO_EINTEGER_OBJECT_MAP_ENTRY: {
				@SuppressWarnings("unchecked") Map.Entry<Port, Integer> portToEIntegerObjectMapEntry = (Map.Entry<Port, Integer>)theEObject;
				T2 result = casePortToEIntegerObjectMapEntry(portToEIntegerObjectMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IrPackage.PORT_TO_VAR_MAP_ENTRY: {
				@SuppressWarnings("unchecked") Map.Entry<Port, Var> portToVarMapEntry = (Map.Entry<Port, Var>)theEObject;
				T2 result = casePortToVarMapEntry(portToVarMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

} //IrSwitch

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.util;

import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.CfgNode;
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
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.Procedure;
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
import net.sf.orcc.util.Adaptable;
import net.sf.orcc.util.Attributable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

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
public class IrSwitch<T> extends Switch<T> {
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
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
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
	public T caseDef(Def object) {
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
	public T caseExprBinary(ExprBinary object) {
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
	public T caseExprBool(ExprBool object) {
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
	public T caseExpression(Expression object) {
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
	public T caseExprFloat(ExprFloat object) {
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
	public T caseExprInt(ExprInt object) {
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
	public T caseExprList(ExprList object) {
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
	public T caseExprString(ExprString object) {
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
	public T caseExprUnary(ExprUnary object) {
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
	public T caseExprVar(ExprVar object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Predicate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Predicate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePredicate(Predicate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cfg</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cfg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCfg(Cfg object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cfg Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cfg Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCfgNode(CfgNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attributable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attributable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributable(Attributable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adaptable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adaptable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdaptable(Adaptable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vertex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVertex(Vertex object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGraph(Graph object) {
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
	public T caseInstAssign(InstAssign object) {
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
	public T caseInstCall(InstCall object) {
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
	public T caseInstLoad(InstLoad object) {
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
	public T caseInstPhi(InstPhi object) {
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
	public T caseInstReturn(InstReturn object) {
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
	public T caseInstruction(Instruction object) {
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
	public T caseInstSpecific(InstSpecific object) {
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
	public T caseInstStore(InstStore object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arg</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arg</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArg(Arg object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arg By Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arg By Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArgByRef(ArgByRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arg By Val</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arg By Val</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArgByVal(ArgByVal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlock(Block object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockBasic(BlockBasic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block If</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block If</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockIf(BlockIf object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block While</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block While</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockWhile(BlockWhile object) {
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
	public T caseProcedure(Procedure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Param</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Param</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParam(Param object) {
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
	public T caseType(Type object) {
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
	public T caseTypeBool(TypeBool object) {
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
	public T caseTypeFloat(TypeFloat object) {
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
	public T caseTypeInt(TypeInt object) {
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
	public T caseTypeList(TypeList object) {
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
	public T caseTypeString(TypeString object) {
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
	public T caseTypeUint(TypeUint object) {
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
	public T caseTypeVoid(TypeVoid object) {
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
	public T caseUse(Use object) {
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
	public T caseVar(Var object) {
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
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case IrPackage.PROCEDURE: {
			Procedure procedure = (Procedure) theEObject;
			T result = caseProcedure(procedure);
			if (result == null)
				result = caseAttributable(procedure);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.PARAM: {
			Param param = (Param) theEObject;
			T result = caseParam(param);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.BLOCK: {
			Block block = (Block) theEObject;
			T result = caseBlock(block);
			if (result == null)
				result = caseAttributable(block);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.BLOCK_BASIC: {
			BlockBasic blockBasic = (BlockBasic) theEObject;
			T result = caseBlockBasic(blockBasic);
			if (result == null)
				result = caseBlock(blockBasic);
			if (result == null)
				result = caseAttributable(blockBasic);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.BLOCK_IF: {
			BlockIf blockIf = (BlockIf) theEObject;
			T result = caseBlockIf(blockIf);
			if (result == null)
				result = caseBlock(blockIf);
			if (result == null)
				result = caseAttributable(blockIf);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.BLOCK_WHILE: {
			BlockWhile blockWhile = (BlockWhile) theEObject;
			T result = caseBlockWhile(blockWhile);
			if (result == null)
				result = caseBlock(blockWhile);
			if (result == null)
				result = caseAttributable(blockWhile);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INSTRUCTION: {
			Instruction instruction = (Instruction) theEObject;
			T result = caseInstruction(instruction);
			if (result == null)
				result = caseAttributable(instruction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_ASSIGN: {
			InstAssign instAssign = (InstAssign) theEObject;
			T result = caseInstAssign(instAssign);
			if (result == null)
				result = caseInstruction(instAssign);
			if (result == null)
				result = caseAttributable(instAssign);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_CALL: {
			InstCall instCall = (InstCall) theEObject;
			T result = caseInstCall(instCall);
			if (result == null)
				result = caseInstruction(instCall);
			if (result == null)
				result = caseAttributable(instCall);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_LOAD: {
			InstLoad instLoad = (InstLoad) theEObject;
			T result = caseInstLoad(instLoad);
			if (result == null)
				result = caseInstruction(instLoad);
			if (result == null)
				result = caseAttributable(instLoad);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_PHI: {
			InstPhi instPhi = (InstPhi) theEObject;
			T result = caseInstPhi(instPhi);
			if (result == null)
				result = caseInstruction(instPhi);
			if (result == null)
				result = caseAttributable(instPhi);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_RETURN: {
			InstReturn instReturn = (InstReturn) theEObject;
			T result = caseInstReturn(instReturn);
			if (result == null)
				result = caseInstruction(instReturn);
			if (result == null)
				result = caseAttributable(instReturn);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_SPECIFIC: {
			InstSpecific instSpecific = (InstSpecific) theEObject;
			T result = caseInstSpecific(instSpecific);
			if (result == null)
				result = caseInstruction(instSpecific);
			if (result == null)
				result = caseAttributable(instSpecific);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.INST_STORE: {
			InstStore instStore = (InstStore) theEObject;
			T result = caseInstStore(instStore);
			if (result == null)
				result = caseInstruction(instStore);
			if (result == null)
				result = caseAttributable(instStore);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.ARG: {
			Arg arg = (Arg) theEObject;
			T result = caseArg(arg);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.ARG_BY_REF: {
			ArgByRef argByRef = (ArgByRef) theEObject;
			T result = caseArgByRef(argByRef);
			if (result == null)
				result = caseArg(argByRef);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.ARG_BY_VAL: {
			ArgByVal argByVal = (ArgByVal) theEObject;
			T result = caseArgByVal(argByVal);
			if (result == null)
				result = caseArg(argByVal);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPRESSION: {
			Expression expression = (Expression) theEObject;
			T result = caseExpression(expression);
			if (result == null)
				result = caseAttributable(expression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_BINARY: {
			ExprBinary exprBinary = (ExprBinary) theEObject;
			T result = caseExprBinary(exprBinary);
			if (result == null)
				result = caseExpression(exprBinary);
			if (result == null)
				result = caseAttributable(exprBinary);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_BOOL: {
			ExprBool exprBool = (ExprBool) theEObject;
			T result = caseExprBool(exprBool);
			if (result == null)
				result = caseExpression(exprBool);
			if (result == null)
				result = caseAttributable(exprBool);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_FLOAT: {
			ExprFloat exprFloat = (ExprFloat) theEObject;
			T result = caseExprFloat(exprFloat);
			if (result == null)
				result = caseExpression(exprFloat);
			if (result == null)
				result = caseAttributable(exprFloat);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_INT: {
			ExprInt exprInt = (ExprInt) theEObject;
			T result = caseExprInt(exprInt);
			if (result == null)
				result = caseExpression(exprInt);
			if (result == null)
				result = caseAttributable(exprInt);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_LIST: {
			ExprList exprList = (ExprList) theEObject;
			T result = caseExprList(exprList);
			if (result == null)
				result = caseExpression(exprList);
			if (result == null)
				result = caseAttributable(exprList);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_STRING: {
			ExprString exprString = (ExprString) theEObject;
			T result = caseExprString(exprString);
			if (result == null)
				result = caseExpression(exprString);
			if (result == null)
				result = caseAttributable(exprString);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_UNARY: {
			ExprUnary exprUnary = (ExprUnary) theEObject;
			T result = caseExprUnary(exprUnary);
			if (result == null)
				result = caseExpression(exprUnary);
			if (result == null)
				result = caseAttributable(exprUnary);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.EXPR_VAR: {
			ExprVar exprVar = (ExprVar) theEObject;
			T result = caseExprVar(exprVar);
			if (result == null)
				result = caseExpression(exprVar);
			if (result == null)
				result = caseAttributable(exprVar);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE: {
			Type type = (Type) theEObject;
			T result = caseType(type);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_BOOL: {
			TypeBool typeBool = (TypeBool) theEObject;
			T result = caseTypeBool(typeBool);
			if (result == null)
				result = caseType(typeBool);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_FLOAT: {
			TypeFloat typeFloat = (TypeFloat) theEObject;
			T result = caseTypeFloat(typeFloat);
			if (result == null)
				result = caseType(typeFloat);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_INT: {
			TypeInt typeInt = (TypeInt) theEObject;
			T result = caseTypeInt(typeInt);
			if (result == null)
				result = caseType(typeInt);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_LIST: {
			TypeList typeList = (TypeList) theEObject;
			T result = caseTypeList(typeList);
			if (result == null)
				result = caseType(typeList);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_STRING: {
			TypeString typeString = (TypeString) theEObject;
			T result = caseTypeString(typeString);
			if (result == null)
				result = caseType(typeString);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_UINT: {
			TypeUint typeUint = (TypeUint) theEObject;
			T result = caseTypeUint(typeUint);
			if (result == null)
				result = caseType(typeUint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.TYPE_VOID: {
			TypeVoid typeVoid = (TypeVoid) theEObject;
			T result = caseTypeVoid(typeVoid);
			if (result == null)
				result = caseType(typeVoid);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.DEF: {
			Def def = (Def) theEObject;
			T result = caseDef(def);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.VAR: {
			Var var = (Var) theEObject;
			T result = caseVar(var);
			if (result == null)
				result = caseAttributable(var);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.USE: {
			Use use = (Use) theEObject;
			T result = caseUse(use);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.PREDICATE: {
			Predicate predicate = (Predicate) theEObject;
			T result = casePredicate(predicate);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.CFG: {
			Cfg cfg = (Cfg) theEObject;
			T result = caseCfg(cfg);
			if (result == null)
				result = caseGraph(cfg);
			if (result == null)
				result = caseVertex(cfg);
			if (result == null)
				result = caseAttributable(cfg);
			if (result == null)
				result = caseAdaptable(cfg);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IrPackage.CFG_NODE: {
			CfgNode cfgNode = (CfgNode) theEObject;
			T result = caseCfgNode(cfgNode);
			if (result == null)
				result = caseVertex(cfgNode);
			if (result == null)
				result = caseAttributable(cfgNode);
			if (result == null)
				result = caseAdaptable(cfgNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

} //IrSwitch

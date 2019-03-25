/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.util.UtilPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.ir.IrFactory
 * @model kind="package"
 * @generated
 */
public interface IrPackage extends EPackage {
	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.DefImpl <em>Def</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.DefImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getDef()
		 * @generated
		 */
		EClass DEF = eINSTANCE.getDef();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEF__VARIABLE = eINSTANCE.getDef_Variable();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprBinaryImpl <em>Expr Binary</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprBinaryImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprBinary()
		 * @generated
		 */
		EClass EXPR_BINARY = eINSTANCE.getExprBinary();

		/**
		 * The meta object literal for the '<em><b>E1</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_BINARY__E1 = eINSTANCE.getExprBinary_E1();

		/**
		 * The meta object literal for the '<em><b>E2</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_BINARY__E2 = eINSTANCE.getExprBinary_E2();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPR_BINARY__OP = eINSTANCE.getExprBinary_Op();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_BINARY__TYPE = eINSTANCE.getExprBinary_Type();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprBoolImpl <em>Expr Bool</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprBoolImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprBool()
		 * @generated
		 */
		EClass EXPR_BOOL = eINSTANCE.getExprBool();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPR_BOOL__VALUE = eINSTANCE.getExprBool_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprFloatImpl <em>Expr Float</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprFloatImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprFloat()
		 * @generated
		 */
		EClass EXPR_FLOAT = eINSTANCE.getExprFloat();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPR_FLOAT__VALUE = eINSTANCE.getExprFloat_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprIntImpl <em>Expr Int</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprIntImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprInt()
		 * @generated
		 */
		EClass EXPR_INT = eINSTANCE.getExprInt();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPR_INT__VALUE = eINSTANCE.getExprInt_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_INT__TYPE = eINSTANCE.getExprInt_Type();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprListImpl <em>Expr List</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprListImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprList()
		 * @generated
		 */
		EClass EXPR_LIST = eINSTANCE.getExprList();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_LIST__VALUE = eINSTANCE.getExprList_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprStringImpl <em>Expr String</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprStringImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprString()
		 * @generated
		 */
		EClass EXPR_STRING = eINSTANCE.getExprString();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPR_STRING__VALUE = eINSTANCE.getExprString_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprUnaryImpl <em>Expr Unary</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprUnaryImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprUnary()
		 * @generated
		 */
		EClass EXPR_UNARY = eINSTANCE.getExprUnary();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_UNARY__EXPR = eINSTANCE.getExprUnary_Expr();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPR_UNARY__OP = eINSTANCE.getExprUnary_Op();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_UNARY__TYPE = eINSTANCE.getExprUnary_Type();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExprVarImpl <em>Expr Var</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExprVarImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprVar()
		 * @generated
		 */
		EClass EXPR_VAR = eINSTANCE.getExprVar();

		/**
		 * The meta object literal for the '<em><b>Use</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPR_VAR__USE = eINSTANCE.getExprVar_Use();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExpressionImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstAssignImpl <em>Inst Assign</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstAssignImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstAssign()
		 * @generated
		 */
		EClass INST_ASSIGN = eINSTANCE.getInstAssign();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN__TARGET = eINSTANCE.getInstAssign_Target();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN__VALUE = eINSTANCE.getInstAssign_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstCallImpl <em>Inst Call</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstCallImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstCall()
		 * @generated
		 */
		EClass INST_CALL = eINSTANCE.getInstCall();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_CALL__ARGUMENTS = eINSTANCE.getInstCall_Arguments();

		/**
		 * The meta object literal for the '<em><b>Procedure</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_CALL__PROCEDURE = eINSTANCE.getInstCall_Procedure();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_CALL__TARGET = eINSTANCE.getInstCall_Target();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstLoadImpl <em>Inst Load</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstLoadImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstLoad()
		 * @generated
		 */
		EClass INST_LOAD = eINSTANCE.getInstLoad();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_LOAD__INDEXES = eINSTANCE.getInstLoad_Indexes();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_LOAD__SOURCE = eINSTANCE.getInstLoad_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_LOAD__TARGET = eINSTANCE.getInstLoad_Target();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstPhiImpl <em>Inst Phi</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstPhiImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstPhi()
		 * @generated
		 */
		EClass INST_PHI = eINSTANCE.getInstPhi();

		/**
		 * The meta object literal for the '<em><b>Old Variable</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_PHI__OLD_VARIABLE = eINSTANCE.getInstPhi_OldVariable();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_PHI__TARGET = eINSTANCE.getInstPhi_Target();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_PHI__VALUES = eINSTANCE.getInstPhi_Values();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstReturnImpl <em>Inst Return</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstReturnImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstReturn()
		 * @generated
		 */
		EClass INST_RETURN = eINSTANCE.getInstReturn();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_RETURN__VALUE = eINSTANCE.getInstReturn_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstStoreImpl <em>Inst Store</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstStoreImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstStore()
		 * @generated
		 */
		EClass INST_STORE = eINSTANCE.getInstStore();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_STORE__INDEXES = eINSTANCE.getInstStore_Indexes();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_STORE__TARGET = eINSTANCE.getInstStore_Target();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_STORE__VALUE = eINSTANCE.getInstStore_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ArgImpl <em>Arg</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ArgImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getArg()
		 * @generated
		 */
		EClass ARG = eINSTANCE.getArg();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ArgByRefImpl <em>Arg By Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ArgByRefImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getArgByRef()
		 * @generated
		 */
		EClass ARG_BY_REF = eINSTANCE.getArgByRef();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARG_BY_REF__INDEXES = eINSTANCE.getArgByRef_Indexes();

		/**
		 * The meta object literal for the '<em><b>Use</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARG_BY_REF__USE = eINSTANCE.getArgByRef_Use();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ArgByValImpl <em>Arg By Val</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ArgByValImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getArgByVal()
		 * @generated
		 */
		EClass ARG_BY_VAL = eINSTANCE.getArgByVal();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARG_BY_VAL__VALUE = eINSTANCE.getArgByVal_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstructionImpl <em>Instruction</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstructionImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstruction()
		 * @generated
		 */
		EClass INSTRUCTION = eINSTANCE.getInstruction();

		/**
		 * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTRUCTION__PREDICATE = eINSTANCE
				.getInstruction_Predicate();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTRUCTION__LINE_NUMBER = eINSTANCE
				.getInstruction_LineNumber();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.PredicateImpl <em>Predicate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.PredicateImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getPredicate()
		 * @generated
		 */
		EClass PREDICATE = eINSTANCE.getPredicate();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREDICATE__EXPRESSIONS = eINSTANCE
				.getPredicate_Expressions();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.CfgImpl <em>Cfg</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.CfgImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getCfg()
		 * @generated
		 */
		EClass CFG = eINSTANCE.getCfg();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CFG__ENTRY = eINSTANCE.getCfg_Entry();

		/**
		 * The meta object literal for the '<em><b>Exit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CFG__EXIT = eINSTANCE.getCfg_Exit();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.CfgNodeImpl <em>Cfg Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.CfgNodeImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getCfgNode()
		 * @generated
		 */
		EClass CFG_NODE = eINSTANCE.getCfgNode();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CFG_NODE__NODE = eINSTANCE.getCfgNode_Node();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.OpBinary
		 * <em>Op Binary</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see net.sf.orcc.ir.OpBinary
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getOpBinary()
		 * @generated
		 */
		EEnum OP_BINARY = eINSTANCE.getOpBinary();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.OpUnary
		 * <em>Op Unary</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see net.sf.orcc.ir.OpUnary
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getOpUnary()
		 * @generated
		 */
		EEnum OP_UNARY = eINSTANCE.getOpUnary();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ProcedureImpl <em>Procedure</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ProcedureImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getProcedure()
		 * @generated
		 */
		EClass PROCEDURE = eINSTANCE.getProcedure();

		/**
		 * The meta object literal for the '<em><b>Locals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__LOCALS = eINSTANCE.getProcedure_Locals();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCEDURE__NAME = eINSTANCE.getProcedure_Name();

		/**
		 * The meta object literal for the '<em><b>Native</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCEDURE__NATIVE = eINSTANCE.getProcedure_Native();

		/**
		 * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__BLOCKS = eINSTANCE.getProcedure_Blocks();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__PARAMETERS = eINSTANCE.getProcedure_Parameters();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCEDURE__LINE_NUMBER = eINSTANCE.getProcedure_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__RETURN_TYPE = eINSTANCE.getProcedure_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Cfg</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__CFG = eINSTANCE.getProcedure_Cfg();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ParamImpl <em>Param</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ParamImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getParam()
		 * @generated
		 */
		EClass PARAM = eINSTANCE.getParam();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAM__VARIABLE = eINSTANCE.getParam_Variable();

		/**
		 * The meta object literal for the '<em><b>By Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAM__BY_REF = eINSTANCE.getParam_ByRef();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.BlockImpl <em>Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.BlockImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlock()
		 * @generated
		 */
		EClass BLOCK = eINSTANCE.getBlock();

		/**
		 * The meta object literal for the '<em><b>Cfg Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK__CFG_NODE = eINSTANCE.getBlock_CfgNode();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.BlockBasicImpl <em>Block Basic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.BlockBasicImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlockBasic()
		 * @generated
		 */
		EClass BLOCK_BASIC = eINSTANCE.getBlockBasic();

		/**
		 * The meta object literal for the '<em><b>Instructions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_BASIC__INSTRUCTIONS = eINSTANCE
				.getBlockBasic_Instructions();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.BlockIfImpl <em>Block If</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.BlockIfImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlockIf()
		 * @generated
		 */
		EClass BLOCK_IF = eINSTANCE.getBlockIf();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_IF__CONDITION = eINSTANCE.getBlockIf_Condition();

		/**
		 * The meta object literal for the '<em><b>Else Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_IF__ELSE_BLOCKS = eINSTANCE.getBlockIf_ElseBlocks();

		/**
		 * The meta object literal for the '<em><b>Join Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_IF__JOIN_BLOCK = eINSTANCE.getBlockIf_JoinBlock();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_IF__LINE_NUMBER = eINSTANCE.getBlockIf_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Then Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_IF__THEN_BLOCKS = eINSTANCE.getBlockIf_ThenBlocks();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.BlockWhileImpl <em>Block While</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.BlockWhileImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlockWhile()
		 * @generated
		 */
		EClass BLOCK_WHILE = eINSTANCE.getBlockWhile();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_WHILE__CONDITION = eINSTANCE.getBlockWhile_Condition();

		/**
		 * The meta object literal for the '<em><b>Join Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_WHILE__JOIN_BLOCK = eINSTANCE
				.getBlockWhile_JoinBlock();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_WHILE__LINE_NUMBER = eINSTANCE
				.getBlockWhile_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_WHILE__BLOCKS = eINSTANCE.getBlockWhile_Blocks();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Dyn</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__DYN = eINSTANCE.getType_Dyn();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeBoolImpl <em>Type Bool</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeBoolImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeBool()
		 * @generated
		 */
		EClass TYPE_BOOL = eINSTANCE.getTypeBool();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeFloatImpl <em>Type Float</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeFloatImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeFloat()
		 * @generated
		 */
		EClass TYPE_FLOAT = eINSTANCE.getTypeFloat();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_FLOAT__SIZE = eINSTANCE.getTypeFloat_Size();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeIntImpl <em>Type Int</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeIntImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeInt()
		 * @generated
		 */
		EClass TYPE_INT = eINSTANCE.getTypeInt();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INT__SIZE = eINSTANCE.getTypeInt_Size();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeListImpl <em>Type List</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeListImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeList()
		 * @generated
		 */
		EClass TYPE_LIST = eINSTANCE.getTypeList();

		/**
		 * The meta object literal for the '<em><b>Size Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference TYPE_LIST__SIZE_EXPR = eINSTANCE.getTypeList_SizeExpr();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_LIST__TYPE = eINSTANCE.getTypeList_Type();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeStringImpl <em>Type String</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeStringImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeString()
		 * @generated
		 */
		EClass TYPE_STRING = eINSTANCE.getTypeString();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_STRING__SIZE = eINSTANCE.getTypeString_Size();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeUintImpl <em>Type Uint</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeUintImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeUint()
		 * @generated
		 */
		EClass TYPE_UINT = eINSTANCE.getTypeUint();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_UINT__SIZE = eINSTANCE.getTypeUint_Size();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeVoidImpl <em>Type Void</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeVoidImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeVoid()
		 * @generated
		 */
		EClass TYPE_VOID = eINSTANCE.getTypeVoid();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.UseImpl <em>Use</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.UseImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getUse()
		 * @generated
		 */
		EClass USE = eINSTANCE.getUse();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE__VARIABLE = eINSTANCE.getUse_Variable();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.VarImpl <em>Var</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.VarImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getVar()
		 * @generated
		 */
		EClass VAR = eINSTANCE.getVar();

		/**
		 * The meta object literal for the '<em><b>Assignable</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__ASSIGNABLE = eINSTANCE.getVar_Assignable();

		/**
		 * The meta object literal for the '<em><b>Defs</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR__DEFS = eINSTANCE.getVar_Defs();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__LINE_NUMBER = eINSTANCE.getVar_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Local</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__LOCAL = eINSTANCE.getVar_Local();

		/**
		 * The meta object literal for the '<em><b>Global</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__GLOBAL = eINSTANCE.getVar_Global();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__INDEX = eINSTANCE.getVar_Index();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference VAR__INITIAL_VALUE = eINSTANCE.getVar_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__NAME = eINSTANCE.getVar_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR__TYPE = eINSTANCE.getVar_Type();

		/**
		 * The meta object literal for the '<em><b>Uses</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR__USES = eINSTANCE.getVar_Uses();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR__VALUE = eINSTANCE.getVar_Value();

	}

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.DefImpl <em>Def</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.DefImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getDef()
	 * @generated
	 */
	int DEF = 33;

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	IrPackage eINSTANCE = net.sf.orcc.ir.impl.IrPackageImpl.init();

	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ir";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.ir";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/model/2011/Ir";

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprBinaryImpl
	 * <em>Expr Binary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.ExprBinaryImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprBinary()
	 * @generated
	 */
	int EXPR_BINARY = 17;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprBoolImpl <em>Expr Bool</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ExprBoolImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprBool()
	 * @generated
	 */
	int EXPR_BOOL = 18;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprFloatImpl
	 * <em>Expr Float</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.ExprFloatImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprFloat()
	 * @generated
	 */
	int EXPR_FLOAT = 19;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprIntImpl <em>Expr Int</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ExprIntImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprInt()
	 * @generated
	 */
	int EXPR_INT = 20;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprListImpl <em>Expr List</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ExprListImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprList()
	 * @generated
	 */
	int EXPR_LIST = 21;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprStringImpl
	 * <em>Expr String</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.ExprStringImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprString()
	 * @generated
	 */
	int EXPR_STRING = 22;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprUnaryImpl
	 * <em>Expr Unary</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.ExprUnaryImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprUnary()
	 * @generated
	 */
	int EXPR_UNARY = 23;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExprVarImpl <em>Expr Var</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ExprVarImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExprVar()
	 * @generated
	 */
	int EXPR_VAR = 24;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExpressionImpl
	 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.ExpressionImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 16;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstructionImpl
	 * <em>Instruction</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.InstructionImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstruction()
	 * @generated
	 */
	int INSTRUCTION = 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstAssignImpl
	 * <em>Inst Assign</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.InstAssignImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstAssign()
	 * @generated
	 */
	int INST_ASSIGN = 7;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstCallImpl <em>Inst Call</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.InstCallImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstCall()
	 * @generated
	 */
	int INST_CALL = 8;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstLoadImpl <em>Inst Load</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.InstLoadImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstLoad()
	 * @generated
	 */
	int INST_LOAD = 9;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstPhiImpl <em>Inst Phi</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.InstPhiImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstPhi()
	 * @generated
	 */
	int INST_PHI = 10;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstReturnImpl
	 * <em>Inst Return</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.InstReturnImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstReturn()
	 * @generated
	 */
	int INST_RETURN = 11;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstStoreImpl
	 * <em>Inst Store</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.InstStoreImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstStore()
	 * @generated
	 */
	int INST_STORE = 12;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.OpBinary <em>Op Binary</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.OpBinary
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getOpBinary()
	 * @generated
	 */
	int OP_BINARY = 39;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.OpUnary <em>Op Unary</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.OpUnary
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getOpUnary()
	 * @generated
	 */
	int OP_UNARY = 40;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ProcedureImpl <em>Procedure</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ProcedureImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getProcedure()
	 * @generated
	 */
	int PROCEDURE = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__LINE_NUMBER = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Locals</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__LOCALS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__NAME = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Native</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__NATIVE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__BLOCKS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__PARAMETERS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__RETURN_TYPE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Cfg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__CFG = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Procedure</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROCEDURE_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ParamImpl <em>Param</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ParamImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getParam()
	 * @generated
	 */
	int PARAM = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM__VARIABLE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>By Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM__BY_REF = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Param</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.BlockImpl <em>Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.BlockImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Cfg Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK__CFG_NODE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.BlockBasicImpl <em>Block Basic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.BlockBasicImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlockBasic()
	 * @generated
	 */
	int BLOCK_BASIC = 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_BASIC__ATTRIBUTES = BLOCK__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Cfg Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_BASIC__CFG_NODE = BLOCK__CFG_NODE;

	/**
	 * The feature id for the '<em><b>Instructions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_BASIC__INSTRUCTIONS = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block Basic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_BASIC_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.BlockIfImpl <em>Block If</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.BlockIfImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlockIf()
	 * @generated
	 */
	int BLOCK_IF = 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__ATTRIBUTES = BLOCK__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Cfg Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__CFG_NODE = BLOCK__CFG_NODE;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__CONDITION = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Else Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__ELSE_BLOCKS = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Join Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__JOIN_BLOCK = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__LINE_NUMBER = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Then Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF__THEN_BLOCKS = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Block If</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_IF_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.BlockWhileImpl <em>Block While</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.BlockWhileImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getBlockWhile()
	 * @generated
	 */
	int BLOCK_WHILE = 5;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__ATTRIBUTES = BLOCK__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Cfg Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__CFG_NODE = BLOCK__CFG_NODE;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__CONDITION = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__JOIN_BLOCK = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__LINE_NUMBER = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE__BLOCKS = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Block While</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_WHILE_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION__LINE_NUMBER = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION__PREDICATE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Instruction</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__ATTRIBUTES = INSTRUCTION__ATTRIBUTES;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.PredicateImpl <em>Predicate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.PredicateImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getPredicate()
	 * @generated
	 */
	int PREDICATE = 36;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__PREDICATE = INSTRUCTION__PREDICATE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__TARGET = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN__VALUE = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inst Assign</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CALL__ATTRIBUTES = INSTRUCTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CALL__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CALL__PREDICATE = INSTRUCTION__PREDICATE;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CALL__ARGUMENTS = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Procedure</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__PROCEDURE = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL__TARGET = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Call</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_CALL_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__ATTRIBUTES = INSTRUCTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__PREDICATE = INSTRUCTION__PREDICATE;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__INDEXES = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__SOURCE = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD__TARGET = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Load</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_LOAD_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_PHI__ATTRIBUTES = INSTRUCTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_PHI__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_PHI__PREDICATE = INSTRUCTION__PREDICATE;

	/**
	 * The feature id for the '<em><b>Old Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI__OLD_VARIABLE = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI__TARGET = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_PHI__VALUES = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Phi</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_PHI_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RETURN__ATTRIBUTES = INSTRUCTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RETURN__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RETURN__PREDICATE = INSTRUCTION__PREDICATE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RETURN__VALUE = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inst Return</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RETURN_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_STORE__ATTRIBUTES = INSTRUCTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_STORE__LINE_NUMBER = INSTRUCTION__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_STORE__PREDICATE = INSTRUCTION__PREDICATE;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_STORE__INDEXES = INSTRUCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INST_STORE__TARGET = INSTRUCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_STORE__VALUE = INSTRUCTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Store</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_STORE_FEATURE_COUNT = INSTRUCTION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ArgImpl <em>Arg</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ArgImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getArg()
	 * @generated
	 */
	int ARG = 13;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Arg</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ArgByRefImpl <em>Arg By Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ArgByRefImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getArgByRef()
	 * @generated
	 */
	int ARG_BY_REF = 14;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_REF__ATTRIBUTES = ARG__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_REF__INDEXES = ARG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Use</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_REF__USE = ARG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Arg By Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_REF_FEATURE_COUNT = ARG_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ArgByValImpl <em>Arg By Val</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ArgByValImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getArgByVal()
	 * @generated
	 */
	int ARG_BY_VAL = 15;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_VAL__ATTRIBUTES = ARG__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_VAL__VALUE = ARG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Arg By Val</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARG_BY_VAL_FEATURE_COUNT = ARG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>E1</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__E1 = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>E2</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__E2 = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__OP = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY__TYPE = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Expr Binary</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_BINARY_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_BOOL__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BOOL__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Bool</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_BOOL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_FLOAT__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_FLOAT__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Float</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_FLOAT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_INT__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_INT__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_INT__TYPE = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Expr Int</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_INT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr List</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_LIST_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_STRING__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_STRING__VALUE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr String</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_STRING_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__EXPR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY__TYPE = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Expr Unary</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_UNARY_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_VAR__ATTRIBUTES = EXPRESSION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Use</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_VAR__USE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expr Var</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPR_VAR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 25;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__DYN = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeBoolImpl <em>Type Bool</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeBoolImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeBool()
	 * @generated
	 */
	int TYPE_BOOL = 26;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__DYN = TYPE__DYN;

	/**
	 * The number of structural features of the '<em>Type Bool</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeFloatImpl
	 * <em>Type Float</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.TypeFloatImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeFloat()
	 * @generated
	 */
	int TYPE_FLOAT = 27;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__DYN = TYPE__DYN;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Float</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeIntImpl <em>Type Int</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeIntImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeInt()
	 * @generated
	 */
	int TYPE_INT = 28;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__DYN = TYPE__DYN;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Int</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_INT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeListImpl <em>Type List</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeListImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeList()
	 * @generated
	 */
	int TYPE_LIST = 29;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__DYN = TYPE__DYN;

	/**
	 * The feature id for the '<em><b>Size Expr</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__SIZE_EXPR = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__TYPE = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type List</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST_FEATURE_COUNT = TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeStringImpl
	 * <em>Type String</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.ir.impl.TypeStringImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeString()
	 * @generated
	 */
	int TYPE_STRING = 30;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__DYN = TYPE__DYN;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type String</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeUintImpl <em>Type Uint</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeUintImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeUint()
	 * @generated
	 */
	int TYPE_UINT = 31;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__DYN = TYPE__DYN;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Uint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeVoidImpl <em>Type Void</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeVoidImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeVoid()
	 * @generated
	 */
	int TYPE_VOID = 32;

	/**
	 * The feature id for the '<em><b>Dyn</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__DYN = TYPE__DYN;

	/**
	 * The number of structural features of the '<em>Type Void</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEF__VARIABLE = 0;

	/**
	 * The number of structural features of the '<em>Def</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEF_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.UseImpl <em>Use</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.UseImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getUse()
	 * @generated
	 */
	int USE = 35;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.VarImpl <em>Var</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.VarImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getVar()
	 * @generated
	 */
	int VAR = 34;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Assignable</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__ASSIGNABLE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Defs</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__DEFS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Global</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__GLOBAL = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__INDEX = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR__INITIAL_VALUE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR__LINE_NUMBER = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Local</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR__LOCAL = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__NAME = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__TYPE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Uses</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR__USES = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR__VALUE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Var</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USE__VARIABLE = 0;

	/**
	 * The number of structural features of the '<em>Use</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int USE_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDICATE__EXPRESSIONS = 0;

	/**
	 * The number of structural features of the '<em>Predicate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREDICATE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.CfgImpl <em>Cfg</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.CfgImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getCfg()
	 * @generated
	 */
	int CFG = 37;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__ATTRIBUTES = GraphPackage.GRAPH__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__LABEL = GraphPackage.GRAPH__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__NUMBER = GraphPackage.GRAPH__NUMBER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__INCOMING = GraphPackage.GRAPH__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__OUTGOING = GraphPackage.GRAPH__OUTGOING;

	/**
	 * The feature id for the '<em><b>Connecting</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__CONNECTING = GraphPackage.GRAPH__CONNECTING;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__PREDECESSORS = GraphPackage.GRAPH__PREDECESSORS;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__SUCCESSORS = GraphPackage.GRAPH__SUCCESSORS;

	/**
	 * The feature id for the '<em><b>Neighbors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__NEIGHBORS = GraphPackage.GRAPH__NEIGHBORS;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__EDGES = GraphPackage.GRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__VERTICES = GraphPackage.GRAPH__VERTICES;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__ENTRY = GraphPackage.GRAPH_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG__EXIT = GraphPackage.GRAPH_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Cfg</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_FEATURE_COUNT = GraphPackage.GRAPH_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.CfgNodeImpl <em>Cfg Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.CfgNodeImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getCfgNode()
	 * @generated
	 */
	int CFG_NODE = 38;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__ATTRIBUTES = GraphPackage.VERTEX__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__LABEL = GraphPackage.VERTEX__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Connecting</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__CONNECTING = GraphPackage.VERTEX__CONNECTING;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__PREDECESSORS = GraphPackage.VERTEX__PREDECESSORS;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__SUCCESSORS = GraphPackage.VERTEX__SUCCESSORS;

	/**
	 * The feature id for the '<em><b>Neighbors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__NEIGHBORS = GraphPackage.VERTEX__NEIGHBORS;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE__NODE = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Cfg Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFG_NODE_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Def <em>Def</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Def</em>'.
	 * @see net.sf.orcc.ir.Def
	 * @generated
	 */
	EClass getDef();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.Def#getVariable <em>Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see net.sf.orcc.ir.Def#getVariable()
	 * @see #getDef()
	 * @generated
	 */
	EReference getDef_Variable();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprBinary <em>Expr Binary</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Binary</em>'.
	 * @see net.sf.orcc.ir.ExprBinary
	 * @generated
	 */
	EClass getExprBinary();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ExprBinary#getE1 <em>E1</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>E1</em>'.
	 * @see net.sf.orcc.ir.ExprBinary#getE1()
	 * @see #getExprBinary()
	 * @generated
	 */
	EReference getExprBinary_E1();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ExprBinary#getE2 <em>E2</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>E2</em>'.
	 * @see net.sf.orcc.ir.ExprBinary#getE2()
	 * @see #getExprBinary()
	 * @generated
	 */
	EReference getExprBinary_E2();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.ExprBinary#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see net.sf.orcc.ir.ExprBinary#getOp()
	 * @see #getExprBinary()
	 * @generated
	 */
	EAttribute getExprBinary_Op();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.ExprBinary#getType <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.ir.ExprBinary#getType()
	 * @see #getExprBinary()
	 * @generated
	 */
	EReference getExprBinary_Type();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprBool <em>Expr Bool</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Bool</em>'.
	 * @see net.sf.orcc.ir.ExprBool
	 * @generated
	 */
	EClass getExprBool();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.ExprBool#isValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see net.sf.orcc.ir.ExprBool#isValue()
	 * @see #getExprBool()
	 * @generated
	 */
	EAttribute getExprBool_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see net.sf.orcc.ir.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprFloat <em>Expr Float</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Float</em>'.
	 * @see net.sf.orcc.ir.ExprFloat
	 * @generated
	 */
	EClass getExprFloat();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.ExprFloat#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see net.sf.orcc.ir.ExprFloat#getValue()
	 * @see #getExprFloat()
	 * @generated
	 */
	EAttribute getExprFloat_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprInt <em>Expr Int</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Int</em>'.
	 * @see net.sf.orcc.ir.ExprInt
	 * @generated
	 */
	EClass getExprInt();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.ExprInt#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see net.sf.orcc.ir.ExprInt#getValue()
	 * @see #getExprInt()
	 * @generated
	 */
	EAttribute getExprInt_Value();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ExprInt#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.ir.ExprInt#getType()
	 * @see #getExprInt()
	 * @generated
	 */
	EReference getExprInt_Type();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprList <em>Expr List</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr List</em>'.
	 * @see net.sf.orcc.ir.ExprList
	 * @generated
	 */
	EClass getExprList();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.ir.ExprList#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Value</em>'.
	 * @see net.sf.orcc.ir.ExprList#getValue()
	 * @see #getExprList()
	 * @generated
	 */
	EReference getExprList_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprString <em>Expr String</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr String</em>'.
	 * @see net.sf.orcc.ir.ExprString
	 * @generated
	 */
	EClass getExprString();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.ExprString#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see net.sf.orcc.ir.ExprString#getValue()
	 * @see #getExprString()
	 * @generated
	 */
	EAttribute getExprString_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprUnary <em>Expr Unary</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Unary</em>'.
	 * @see net.sf.orcc.ir.ExprUnary
	 * @generated
	 */
	EClass getExprUnary();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.ExprUnary#getExpr <em>Expr</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see net.sf.orcc.ir.ExprUnary#getExpr()
	 * @see #getExprUnary()
	 * @generated
	 */
	EReference getExprUnary_Expr();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.ExprUnary#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see net.sf.orcc.ir.ExprUnary#getOp()
	 * @see #getExprUnary()
	 * @generated
	 */
	EAttribute getExprUnary_Op();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ExprUnary#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.ir.ExprUnary#getType()
	 * @see #getExprUnary()
	 * @generated
	 */
	EReference getExprUnary_Type();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ExprVar <em>Expr Var</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Var</em>'.
	 * @see net.sf.orcc.ir.ExprVar
	 * @generated
	 */
	EClass getExprVar();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ExprVar#getUse <em>Use</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Use</em>'.
	 * @see net.sf.orcc.ir.ExprVar#getUse()
	 * @see #getExprVar()
	 * @generated
	 */
	EReference getExprVar_Use();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.InstAssign <em>Inst Assign</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Assign</em>'.
	 * @see net.sf.orcc.ir.InstAssign
	 * @generated
	 */
	EClass getInstAssign();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstAssign#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see net.sf.orcc.ir.InstAssign#getTarget()
	 * @see #getInstAssign()
	 * @generated
	 */
	EReference getInstAssign_Target();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.InstAssign#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.ir.InstAssign#getValue()
	 * @see #getInstAssign()
	 * @generated
	 */
	EReference getInstAssign_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.InstCall <em>Inst Call</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Call</em>'.
	 * @see net.sf.orcc.ir.InstCall
	 * @generated
	 */
	EClass getInstCall();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.InstCall#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see net.sf.orcc.ir.InstCall#getArguments()
	 * @see #getInstCall()
	 * @generated
	 */
	EReference getInstCall_Arguments();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstCall#getProcedure <em>Procedure</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Procedure</em>'.
	 * @see net.sf.orcc.ir.InstCall#getProcedure()
	 * @see #getInstCall()
	 * @generated
	 */
	EReference getInstCall_Procedure();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstCall#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see net.sf.orcc.ir.InstCall#getTarget()
	 * @see #getInstCall()
	 * @generated
	 */
	EReference getInstCall_Target();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.InstLoad <em>Inst Load</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Load</em>'.
	 * @see net.sf.orcc.ir.InstLoad
	 * @generated
	 */
	EClass getInstLoad();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.ir.InstLoad#getIndexes <em>Indexes</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Indexes</em>'.
	 * @see net.sf.orcc.ir.InstLoad#getIndexes()
	 * @see #getInstLoad()
	 * @generated
	 */
	EReference getInstLoad_Indexes();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstLoad#getSource <em>Source</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see net.sf.orcc.ir.InstLoad#getSource()
	 * @see #getInstLoad()
	 * @generated
	 */
	EReference getInstLoad_Source();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstLoad#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see net.sf.orcc.ir.InstLoad#getTarget()
	 * @see #getInstLoad()
	 * @generated
	 */
	EReference getInstLoad_Target();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.InstPhi <em>Inst Phi</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Phi</em>'.
	 * @see net.sf.orcc.ir.InstPhi
	 * @generated
	 */
	EClass getInstPhi();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.InstPhi#getOldVariable <em>Old Variable</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Old Variable</em>'.
	 * @see net.sf.orcc.ir.InstPhi#getOldVariable()
	 * @see #getInstPhi()
	 * @generated
	 */
	EReference getInstPhi_OldVariable();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstPhi#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see net.sf.orcc.ir.InstPhi#getTarget()
	 * @see #getInstPhi()
	 * @generated
	 */
	EReference getInstPhi_Target();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.ir.InstPhi#getValues <em>Values</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Values</em>'.
	 * @see net.sf.orcc.ir.InstPhi#getValues()
	 * @see #getInstPhi()
	 * @generated
	 */
	EReference getInstPhi_Values();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.InstReturn <em>Inst Return</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Return</em>'.
	 * @see net.sf.orcc.ir.InstReturn
	 * @generated
	 */
	EClass getInstReturn();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.InstReturn#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.ir.InstReturn#getValue()
	 * @see #getInstReturn()
	 * @generated
	 */
	EReference getInstReturn_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Instruction <em>Instruction</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instruction</em>'.
	 * @see net.sf.orcc.ir.Instruction
	 * @generated
	 */
	EClass getInstruction();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.Instruction#getPredicate <em>Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Predicate</em>'.
	 * @see net.sf.orcc.ir.Instruction#getPredicate()
	 * @see #getInstruction()
	 * @generated
	 */
	EReference getInstruction_Predicate();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Instruction#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.ir.Instruction#getLineNumber()
	 * @see #getInstruction()
	 * @generated
	 */
	EAttribute getInstruction_LineNumber();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Predicate <em>Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Predicate</em>'.
	 * @see net.sf.orcc.ir.Predicate
	 * @generated
	 */
	EClass getPredicate();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.Predicate#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expressions</em>'.
	 * @see net.sf.orcc.ir.Predicate#getExpressions()
	 * @see #getPredicate()
	 * @generated
	 */
	EReference getPredicate_Expressions();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Cfg <em>Cfg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cfg</em>'.
	 * @see net.sf.orcc.ir.Cfg
	 * @generated
	 */
	EClass getCfg();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.Cfg#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry</em>'.
	 * @see net.sf.orcc.ir.Cfg#getEntry()
	 * @see #getCfg()
	 * @generated
	 */
	EReference getCfg_Entry();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.Cfg#getExit <em>Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exit</em>'.
	 * @see net.sf.orcc.ir.Cfg#getExit()
	 * @see #getCfg()
	 * @generated
	 */
	EReference getCfg_Exit();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.CfgNode <em>Cfg Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cfg Node</em>'.
	 * @see net.sf.orcc.ir.CfgNode
	 * @generated
	 */
	EClass getCfgNode();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.CfgNode#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see net.sf.orcc.ir.CfgNode#getNode()
	 * @see #getCfgNode()
	 * @generated
	 */
	EReference getCfgNode_Node();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.InstStore <em>Inst Store</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Store</em>'.
	 * @see net.sf.orcc.ir.InstStore
	 * @generated
	 */
	EClass getInstStore();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.ir.InstStore#getIndexes <em>Indexes</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Indexes</em>'.
	 * @see net.sf.orcc.ir.InstStore#getIndexes()
	 * @see #getInstStore()
	 * @generated
	 */
	EReference getInstStore_Indexes();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.InstStore#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see net.sf.orcc.ir.InstStore#getTarget()
	 * @see #getInstStore()
	 * @generated
	 */
	EReference getInstStore_Target();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.InstStore#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.ir.InstStore#getValue()
	 * @see #getInstStore()
	 * @generated
	 */
	EReference getInstStore_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Arg <em>Arg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arg</em>'.
	 * @see net.sf.orcc.ir.Arg
	 * @generated
	 */
	EClass getArg();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ArgByRef <em>Arg By Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arg By Ref</em>'.
	 * @see net.sf.orcc.ir.ArgByRef
	 * @generated
	 */
	EClass getArgByRef();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.ArgByRef#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.ir.ArgByRef#getIndexes()
	 * @see #getArgByRef()
	 * @generated
	 */
	EReference getArgByRef_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ArgByRef#getUse <em>Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Use</em>'.
	 * @see net.sf.orcc.ir.ArgByRef#getUse()
	 * @see #getArgByRef()
	 * @generated
	 */
	EReference getArgByRef_Use();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ArgByVal <em>Arg By Val</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arg By Val</em>'.
	 * @see net.sf.orcc.ir.ArgByVal
	 * @generated
	 */
	EClass getArgByVal();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.ArgByVal#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.ir.ArgByVal#getValue()
	 * @see #getArgByVal()
	 * @generated
	 */
	EReference getArgByVal_Value();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IrFactory getIrFactory();

	/**
	 * Returns the meta object for enum '{@link net.sf.orcc.ir.OpBinary <em>Op Binary</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Op Binary</em>'.
	 * @see net.sf.orcc.ir.OpBinary
	 * @generated
	 */
	EEnum getOpBinary();

	/**
	 * Returns the meta object for enum '{@link net.sf.orcc.ir.OpUnary <em>Op Unary</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Op Unary</em>'.
	 * @see net.sf.orcc.ir.OpUnary
	 * @generated
	 */
	EEnum getOpUnary();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Procedure <em>Procedure</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Procedure</em>'.
	 * @see net.sf.orcc.ir.Procedure
	 * @generated
	 */
	EClass getProcedure();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.ir.Procedure#getLocals <em>Locals</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Locals</em>'.
	 * @see net.sf.orcc.ir.Procedure#getLocals()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Locals();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.ir.Procedure#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Name</em>'.
	 * @see net.sf.orcc.ir.Procedure#getName()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.Procedure#isNative <em>Native</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Native</em>'.
	 * @see net.sf.orcc.ir.Procedure#isNative()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_Native();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.Procedure#getBlocks <em>Blocks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Blocks</em>'.
	 * @see net.sf.orcc.ir.Procedure#getBlocks()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Blocks();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.ir.Procedure#getParameters <em>Parameters</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Parameters</em>'.
	 * @see net.sf.orcc.ir.Procedure#getParameters()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Procedure#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.ir.Procedure#getLineNumber()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_LineNumber();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.Procedure#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Return Type</em>'.
	 * @see net.sf.orcc.ir.Procedure#getReturnType()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_ReturnType();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.Procedure#getCfg <em>Cfg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cfg</em>'.
	 * @see net.sf.orcc.ir.Procedure#getCfg()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Cfg();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Param <em>Param</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Param</em>'.
	 * @see net.sf.orcc.ir.Param
	 * @generated
	 */
	EClass getParam();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.Param#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Variable</em>'.
	 * @see net.sf.orcc.ir.Param#getVariable()
	 * @see #getParam()
	 * @generated
	 */
	EReference getParam_Variable();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Param#isByRef <em>By Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>By Ref</em>'.
	 * @see net.sf.orcc.ir.Param#isByRef()
	 * @see #getParam()
	 * @generated
	 */
	EAttribute getParam_ByRef();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Block <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block</em>'.
	 * @see net.sf.orcc.ir.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.Block#getCfgNode <em>Cfg Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cfg Node</em>'.
	 * @see net.sf.orcc.ir.Block#getCfgNode()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_CfgNode();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.BlockBasic <em>Block Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Basic</em>'.
	 * @see net.sf.orcc.ir.BlockBasic
	 * @generated
	 */
	EClass getBlockBasic();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.BlockBasic#getInstructions <em>Instructions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instructions</em>'.
	 * @see net.sf.orcc.ir.BlockBasic#getInstructions()
	 * @see #getBlockBasic()
	 * @generated
	 */
	EReference getBlockBasic_Instructions();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.BlockIf <em>Block If</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block If</em>'.
	 * @see net.sf.orcc.ir.BlockIf
	 * @generated
	 */
	EClass getBlockIf();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.BlockIf#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see net.sf.orcc.ir.BlockIf#getCondition()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_Condition();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.BlockIf#getElseBlocks <em>Else Blocks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Else Blocks</em>'.
	 * @see net.sf.orcc.ir.BlockIf#getElseBlocks()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_ElseBlocks();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.BlockIf#getJoinBlock <em>Join Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Block</em>'.
	 * @see net.sf.orcc.ir.BlockIf#getJoinBlock()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_JoinBlock();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.BlockIf#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.ir.BlockIf#getLineNumber()
	 * @see #getBlockIf()
	 * @generated
	 */
	EAttribute getBlockIf_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.BlockIf#getThenBlocks <em>Then Blocks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Then Blocks</em>'.
	 * @see net.sf.orcc.ir.BlockIf#getThenBlocks()
	 * @see #getBlockIf()
	 * @generated
	 */
	EReference getBlockIf_ThenBlocks();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.BlockWhile <em>Block While</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block While</em>'.
	 * @see net.sf.orcc.ir.BlockWhile
	 * @generated
	 */
	EClass getBlockWhile();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.BlockWhile#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see net.sf.orcc.ir.BlockWhile#getCondition()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EReference getBlockWhile_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.BlockWhile#getJoinBlock <em>Join Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Block</em>'.
	 * @see net.sf.orcc.ir.BlockWhile#getJoinBlock()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EReference getBlockWhile_JoinBlock();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.BlockWhile#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.ir.BlockWhile#getLineNumber()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EAttribute getBlockWhile_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.BlockWhile#getBlocks <em>Blocks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Blocks</em>'.
	 * @see net.sf.orcc.ir.BlockWhile#getBlocks()
	 * @see #getBlockWhile()
	 * @generated
	 */
	EReference getBlockWhile_Blocks();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Type <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see net.sf.orcc.ir.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isDyn <em>Dyn</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dyn</em>'.
	 * @see net.sf.orcc.ir.Type#isDyn()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Dyn();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeBool <em>Type Bool</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Bool</em>'.
	 * @see net.sf.orcc.ir.TypeBool
	 * @generated
	 */
	EClass getTypeBool();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeFloat <em>Type Float</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Float</em>'.
	 * @see net.sf.orcc.ir.TypeFloat
	 * @generated
	 */
	EClass getTypeFloat();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeFloat#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeFloat#getSize()
	 * @see #getTypeFloat()
	 * @generated
	 */
	EAttribute getTypeFloat_Size();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeInt <em>Type Int</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Int</em>'.
	 * @see net.sf.orcc.ir.TypeInt
	 * @generated
	 */
	EClass getTypeInt();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.TypeInt#getSize <em>Size</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeInt#getSize()
	 * @see #getTypeInt()
	 * @generated
	 */
	EAttribute getTypeInt_Size();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeList <em>Type List</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type List</em>'.
	 * @see net.sf.orcc.ir.TypeList
	 * @generated
	 */
	EClass getTypeList();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.TypeList#getSizeExpr <em>Size Expr</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Size Expr</em>
	 *         '.
	 * @see net.sf.orcc.ir.TypeList#getSizeExpr()
	 * @see #getTypeList()
	 * @generated
	 */
	EReference getTypeList_SizeExpr();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.TypeList#getType <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.ir.TypeList#getType()
	 * @see #getTypeList()
	 * @generated
	 */
	EReference getTypeList_Type();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeString <em>Type String</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type String</em>'.
	 * @see net.sf.orcc.ir.TypeString
	 * @generated
	 */
	EClass getTypeString();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.TypeString#getSize <em>Size</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeString#getSize()
	 * @see #getTypeString()
	 * @generated
	 */
	EAttribute getTypeString_Size();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeUint <em>Type Uint</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Uint</em>'.
	 * @see net.sf.orcc.ir.TypeUint
	 * @generated
	 */
	EClass getTypeUint();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.TypeUint#getSize <em>Size</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeUint#getSize()
	 * @see #getTypeUint()
	 * @generated
	 */
	EAttribute getTypeUint_Size();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeVoid <em>Type Void</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Void</em>'.
	 * @see net.sf.orcc.ir.TypeVoid
	 * @generated
	 */
	EClass getTypeVoid();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Use <em>Use</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Use</em>'.
	 * @see net.sf.orcc.ir.Use
	 * @generated
	 */
	EClass getUse();

	/**
	 * Returns the meta object for the container reference '
	 * {@link net.sf.orcc.ir.Use#getVariable <em>Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Variable</em>'.
	 * @see net.sf.orcc.ir.Use#getVariable()
	 * @see #getUse()
	 * @generated
	 */
	EReference getUse_Variable();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Var <em>Var</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var</em>'.
	 * @see net.sf.orcc.ir.Var
	 * @generated
	 */
	EClass getVar();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.ir.Var#isAssignable <em>Assignable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Assignable</em>'.
	 * @see net.sf.orcc.ir.Var#isAssignable()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Assignable();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.ir.Var#getDefs <em>Defs</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Defs</em>'.
	 * @see net.sf.orcc.ir.Var#getDefs()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_Defs();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Var#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.ir.Var#getLineNumber()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_LineNumber();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Var#isLocal <em>Local</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local</em>'.
	 * @see net.sf.orcc.ir.Var#isLocal()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Local();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Var#isGlobal <em>Global</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Global</em>'.
	 * @see net.sf.orcc.ir.Var#isGlobal()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Global();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Var#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see net.sf.orcc.ir.Var#getIndex()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Index();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.ir.Var#getInitialValue <em>Initial Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '
	 *         <em>Initial Value</em>'.
	 * @see net.sf.orcc.ir.Var#getInitialValue()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_InitialValue();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Var#getName <em>Name</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.ir.Var#getName()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Name();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.Var#getType <em>Type</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.ir.Var#getType()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_Type();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.ir.Var#getUses <em>Uses</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Uses</em>'.
	 * @see net.sf.orcc.ir.Var#getUses()
	 * @see #getVar()
	 * @generated
	 */
	EReference getVar_Uses();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Var#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see net.sf.orcc.ir.Var#getValue()
	 * @see #getVar()
	 * @generated
	 */
	EAttribute getVar_Value();

} // IrPackage

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir;

import net.sf.orcc.ir.IrPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.ir.IrSpecificFactory
 * @model kind="package"
 * @generated
 */
public interface IrSpecificPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ir";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/backends/ir";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.backends.ir";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IrSpecificPackage eINSTANCE = net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.InstAssignIndexImpl <em>Inst Assign Index</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.InstAssignIndexImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstAssignIndex()
	 * @generated
	 */
	int INST_ASSIGN_INDEX = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX__ATTRIBUTES = IrPackage.INST_SPECIFIC__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX__LINE_NUMBER = IrPackage.INST_SPECIFIC__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX__PREDICATE = IrPackage.INST_SPECIFIC__PREDICATE;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX__INDEXES = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX__TARGET = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>List Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX__LIST_TYPE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Assign Index</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_ASSIGN_INDEX_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.InstCastImpl <em>Inst Cast</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.InstCastImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstCast()
	 * @generated
	 */
	int INST_CAST = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CAST__ATTRIBUTES = IrPackage.INST_SPECIFIC__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CAST__LINE_NUMBER = IrPackage.INST_SPECIFIC__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CAST__PREDICATE = IrPackage.INST_SPECIFIC__PREDICATE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CAST__TARGET = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CAST__SOURCE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inst Cast</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_CAST_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl <em>Inst Ternary</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.InstTernaryImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstTernary()
	 * @generated
	 */
	int INST_TERNARY = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__ATTRIBUTES = IrPackage.INST_SPECIFIC__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__LINE_NUMBER = IrPackage.INST_SPECIFIC__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__PREDICATE = IrPackage.INST_SPECIFIC__PREDICATE;

	/**
	 * The feature id for the '<em><b>Condition Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__CONDITION_VALUE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>True Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__TRUE_VALUE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>False Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__FALSE_VALUE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY__TARGET = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Inst Ternary</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_TERNARY_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.BlockForImpl <em>Block For</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.BlockForImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getBlockFor()
	 * @generated
	 */
	int BLOCK_FOR = 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__ATTRIBUTES = IrPackage.BLOCK__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Cfg Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__CFG_NODE = IrPackage.BLOCK__CFG_NODE;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__CONDITION = IrPackage.BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__JOIN_BLOCK = IrPackage.BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__LINE_NUMBER = IrPackage.BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Blocks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__BLOCKS = IrPackage.BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Step</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__STEP = IrPackage.BLOCK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR__INIT = IrPackage.BLOCK_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Block For</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FOR_FEATURE_COUNT = IrPackage.BLOCK_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.ExprNullImpl <em>Expr Null</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.ExprNullImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getExprNull()
	 * @generated
	 */
	int EXPR_NULL = 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_NULL__ATTRIBUTES = IrPackage.EXPRESSION__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Expr Null</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPR_NULL_FEATURE_COUNT = IrPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.InstAssignIndex <em>Inst Assign Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Assign Index</em>'.
	 * @see net.sf.orcc.backends.ir.InstAssignIndex
	 * @generated
	 */
	EClass getInstAssignIndex();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.InstAssignIndex#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.backends.ir.InstAssignIndex#getIndexes()
	 * @see #getInstAssignIndex()
	 * @generated
	 */
	EReference getInstAssignIndex_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstAssignIndex#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.ir.InstAssignIndex#getTarget()
	 * @see #getInstAssignIndex()
	 * @generated
	 */
	EReference getInstAssignIndex_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstAssignIndex#getListType <em>List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>List Type</em>'.
	 * @see net.sf.orcc.backends.ir.InstAssignIndex#getListType()
	 * @see #getInstAssignIndex()
	 * @generated
	 */
	EReference getInstAssignIndex_ListType();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.InstCast <em>Inst Cast</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Cast</em>'.
	 * @see net.sf.orcc.backends.ir.InstCast
	 * @generated
	 */
	EClass getInstCast();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstCast#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.ir.InstCast#getTarget()
	 * @see #getInstCast()
	 * @generated
	 */
	EReference getInstCast_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstCast#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see net.sf.orcc.backends.ir.InstCast#getSource()
	 * @see #getInstCast()
	 * @generated
	 */
	EReference getInstCast_Source();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.InstTernary <em>Inst Ternary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Ternary</em>'.
	 * @see net.sf.orcc.backends.ir.InstTernary
	 * @generated
	 */
	EClass getInstTernary();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstTernary#getConditionValue <em>Condition Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition Value</em>'.
	 * @see net.sf.orcc.backends.ir.InstTernary#getConditionValue()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_ConditionValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstTernary#getTrueValue <em>True Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>True Value</em>'.
	 * @see net.sf.orcc.backends.ir.InstTernary#getTrueValue()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_TrueValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstTernary#getFalseValue <em>False Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>False Value</em>'.
	 * @see net.sf.orcc.backends.ir.InstTernary#getFalseValue()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_FalseValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstTernary#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.ir.InstTernary#getTarget()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_Target();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.BlockFor <em>Block For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block For</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor
	 * @generated
	 */
	EClass getBlockFor();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.BlockFor#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor#getCondition()
	 * @see #getBlockFor()
	 * @generated
	 */
	EReference getBlockFor_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.BlockFor#getJoinBlock <em>Join Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Block</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor#getJoinBlock()
	 * @see #getBlockFor()
	 * @generated
	 */
	EReference getBlockFor_JoinBlock();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.ir.BlockFor#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor#getLineNumber()
	 * @see #getBlockFor()
	 * @generated
	 */
	EAttribute getBlockFor_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.BlockFor#getBlocks <em>Blocks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Blocks</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor#getBlocks()
	 * @see #getBlockFor()
	 * @generated
	 */
	EReference getBlockFor_Blocks();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.BlockFor#getStep <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Step</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor#getStep()
	 * @see #getBlockFor()
	 * @generated
	 */
	EReference getBlockFor_Step();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.BlockFor#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init</em>'.
	 * @see net.sf.orcc.backends.ir.BlockFor#getInit()
	 * @see #getBlockFor()
	 * @generated
	 */
	EReference getBlockFor_Init();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.ExprNull <em>Expr Null</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expr Null</em>'.
	 * @see net.sf.orcc.backends.ir.ExprNull
	 * @generated
	 */
	EClass getExprNull();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IrSpecificFactory getIrSpecificFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.InstAssignIndexImpl <em>Inst Assign Index</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.InstAssignIndexImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstAssignIndex()
		 * @generated
		 */
		EClass INST_ASSIGN_INDEX = eINSTANCE.getInstAssignIndex();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN_INDEX__INDEXES = eINSTANCE
				.getInstAssignIndex_Indexes();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN_INDEX__TARGET = eINSTANCE
				.getInstAssignIndex_Target();

		/**
		 * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN_INDEX__LIST_TYPE = eINSTANCE
				.getInstAssignIndex_ListType();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.InstCastImpl <em>Inst Cast</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.InstCastImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstCast()
		 * @generated
		 */
		EClass INST_CAST = eINSTANCE.getInstCast();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_CAST__TARGET = eINSTANCE.getInstCast_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_CAST__SOURCE = eINSTANCE.getInstCast_Source();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl <em>Inst Ternary</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.InstTernaryImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstTernary()
		 * @generated
		 */
		EClass INST_TERNARY = eINSTANCE.getInstTernary();

		/**
		 * The meta object literal for the '<em><b>Condition Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__CONDITION_VALUE = eINSTANCE
				.getInstTernary_ConditionValue();

		/**
		 * The meta object literal for the '<em><b>True Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__TRUE_VALUE = eINSTANCE
				.getInstTernary_TrueValue();

		/**
		 * The meta object literal for the '<em><b>False Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__FALSE_VALUE = eINSTANCE
				.getInstTernary_FalseValue();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__TARGET = eINSTANCE.getInstTernary_Target();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.BlockForImpl <em>Block For</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.BlockForImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getBlockFor()
		 * @generated
		 */
		EClass BLOCK_FOR = eINSTANCE.getBlockFor();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_FOR__CONDITION = eINSTANCE.getBlockFor_Condition();

		/**
		 * The meta object literal for the '<em><b>Join Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_FOR__JOIN_BLOCK = eINSTANCE.getBlockFor_JoinBlock();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_FOR__LINE_NUMBER = eINSTANCE.getBlockFor_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Blocks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_FOR__BLOCKS = eINSTANCE.getBlockFor_Blocks();

		/**
		 * The meta object literal for the '<em><b>Step</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_FOR__STEP = eINSTANCE.getBlockFor_Step();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_FOR__INIT = eINSTANCE.getBlockFor_Init();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.ExprNullImpl <em>Expr Null</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.ExprNullImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getExprNull()
		 * @generated
		 */
		EClass EXPR_NULL = eINSTANCE.getExprNull();

	}

} //IrSpecificPackage

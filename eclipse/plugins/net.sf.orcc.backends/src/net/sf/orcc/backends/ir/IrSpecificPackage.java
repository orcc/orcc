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
	IrSpecificPackage eINSTANCE = net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl.init();

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
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.InstGetElementPtrImpl <em>Inst Get Element Ptr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.InstGetElementPtrImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstGetElementPtr()
	 * @generated
	 */
	int INST_GET_ELEMENT_PTR = 2;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_GET_ELEMENT_PTR__LINE_NUMBER = IrPackage.INST_SPECIFIC__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_GET_ELEMENT_PTR__PREDICATE = IrPackage.INST_SPECIFIC__PREDICATE;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_GET_ELEMENT_PTR__INDEXES = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_GET_ELEMENT_PTR__TARGET = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_GET_ELEMENT_PTR__SOURCE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inst Get Element Ptr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_GET_ELEMENT_PTR_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl <em>Inst Ternary</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.InstTernaryImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstTernary()
	 * @generated
	 */
	int INST_TERNARY = 3;

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
	 * The meta object id for the '{@link net.sf.orcc.backends.ir.impl.NodeForImpl <em>Node For</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.ir.impl.NodeForImpl
	 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getNodeFor()
	 * @generated
	 */
	int NODE_FOR = 4;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR__CONDITION = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR__JOIN_NODE = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR__LINE_NUMBER = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR__NODES = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Loop Counter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR__LOOP_COUNTER = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR__INIT = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Node For</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FOR_FEATURE_COUNT = IrPackage.NODE_SPECIFIC_FEATURE_COUNT + 6;

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
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.InstGetElementPtr <em>Inst Get Element Ptr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Get Element Ptr</em>'.
	 * @see net.sf.orcc.backends.ir.InstGetElementPtr
	 * @generated
	 */
	EClass getInstGetElementPtr();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.InstGetElementPtr#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.backends.ir.InstGetElementPtr#getIndexes()
	 * @see #getInstGetElementPtr()
	 * @generated
	 */
	EReference getInstGetElementPtr_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstGetElementPtr#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.ir.InstGetElementPtr#getTarget()
	 * @see #getInstGetElementPtr()
	 * @generated
	 */
	EReference getInstGetElementPtr_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.InstGetElementPtr#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see net.sf.orcc.backends.ir.InstGetElementPtr#getSource()
	 * @see #getInstGetElementPtr()
	 * @generated
	 */
	EReference getInstGetElementPtr_Source();

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
	 * Returns the meta object for class '{@link net.sf.orcc.backends.ir.NodeFor <em>Node For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node For</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor
	 * @generated
	 */
	EClass getNodeFor();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.NodeFor#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor#getCondition()
	 * @see #getNodeFor()
	 * @generated
	 */
	EReference getNodeFor_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.ir.NodeFor#getJoinNode <em>Join Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Node</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor#getJoinNode()
	 * @see #getNodeFor()
	 * @generated
	 */
	EReference getNodeFor_JoinNode();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.ir.NodeFor#getLineNumber <em>Line Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor#getLineNumber()
	 * @see #getNodeFor()
	 * @generated
	 */
	EAttribute getNodeFor_LineNumber();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.NodeFor#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor#getNodes()
	 * @see #getNodeFor()
	 * @generated
	 */
	EReference getNodeFor_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.NodeFor#getLoopCounter <em>Loop Counter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Loop Counter</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor#getLoopCounter()
	 * @see #getNodeFor()
	 * @generated
	 */
	EReference getNodeFor_LoopCounter();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.ir.NodeFor#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init</em>'.
	 * @see net.sf.orcc.backends.ir.NodeFor#getInit()
	 * @see #getNodeFor()
	 * @generated
	 */
	EReference getNodeFor_Init();

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
		EReference INST_ASSIGN_INDEX__INDEXES = eINSTANCE.getInstAssignIndex_Indexes();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN_INDEX__TARGET = eINSTANCE.getInstAssignIndex_Target();

		/**
		 * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_ASSIGN_INDEX__LIST_TYPE = eINSTANCE.getInstAssignIndex_ListType();

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
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.InstGetElementPtrImpl <em>Inst Get Element Ptr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.InstGetElementPtrImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getInstGetElementPtr()
		 * @generated
		 */
		EClass INST_GET_ELEMENT_PTR = eINSTANCE.getInstGetElementPtr();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_GET_ELEMENT_PTR__INDEXES = eINSTANCE.getInstGetElementPtr_Indexes();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_GET_ELEMENT_PTR__TARGET = eINSTANCE.getInstGetElementPtr_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_GET_ELEMENT_PTR__SOURCE = eINSTANCE.getInstGetElementPtr_Source();

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
		EReference INST_TERNARY__CONDITION_VALUE = eINSTANCE.getInstTernary_ConditionValue();

		/**
		 * The meta object literal for the '<em><b>True Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__TRUE_VALUE = eINSTANCE.getInstTernary_TrueValue();

		/**
		 * The meta object literal for the '<em><b>False Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__FALSE_VALUE = eINSTANCE.getInstTernary_FalseValue();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_TERNARY__TARGET = eINSTANCE.getInstTernary_Target();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.ir.impl.NodeForImpl <em>Node For</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.ir.impl.NodeForImpl
		 * @see net.sf.orcc.backends.ir.impl.IrSpecificPackageImpl#getNodeFor()
		 * @generated
		 */
		EClass NODE_FOR = eINSTANCE.getNodeFor();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_FOR__CONDITION = eINSTANCE.getNodeFor_Condition();

		/**
		 * The meta object literal for the '<em><b>Join Node</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_FOR__JOIN_NODE = eINSTANCE.getNodeFor_JoinNode();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_FOR__LINE_NUMBER = eINSTANCE.getNodeFor_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_FOR__NODES = eINSTANCE.getNodeFor_Nodes();

		/**
		 * The meta object literal for the '<em><b>Loop Counter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_FOR__LOOP_COUNTER = eINSTANCE.getNodeFor_LoopCounter();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_FOR__INIT = eINSTANCE.getNodeFor_Init();

	}

} //IrSpecificPackage

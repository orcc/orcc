/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.instructions;

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
 * @see net.sf.orcc.backends.instructions.InstructionsFactory
 * @model kind="package"
 * @generated
 */
public interface InstructionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "instructions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/backends/instructions/Instructions";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.backends.instructions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InstructionsPackage eINSTANCE = net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstTernaryImpl <em>Inst Ternary</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstTernaryImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstTernary()
	 * @generated
	 */
	int INST_TERNARY = 8;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl <em>Inst Assign Index</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstAssignIndex()
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
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstSplitImpl <em>Inst Split</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstSplitImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstSplit()
	 * @generated
	 */
	int INST_SPLIT = 7;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstRamImpl <em>Inst Ram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstRamImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRam()
	 * @generated
	 */
	int INST_RAM = 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstRamReadImpl <em>Inst Ram Read</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstRamReadImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRamRead()
	 * @generated
	 */
	int INST_RAM_READ = 4;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstRamSetAddressImpl <em>Inst Ram Set Address</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstRamSetAddressImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRamSetAddress()
	 * @generated
	 */
	int INST_RAM_SET_ADDRESS = 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstRamWriteImpl <em>Inst Ram Write</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstRamWriteImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRamWrite()
	 * @generated
	 */
	int INST_RAM_WRITE = 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstGetElementPtrImpl <em>Inst Get Element Ptr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstGetElementPtrImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstGetElementPtr()
	 * @generated
	 */
	int INST_GET_ELEMENT_PTR = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.InstCastImpl <em>Inst Cast</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.InstCastImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstCast()
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
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM__LINE_NUMBER = IrPackage.INST_SPECIFIC__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM__PREDICATE = IrPackage.INST_SPECIFIC__PREDICATE;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM__PORT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM__SOURCE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Inst Ram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_READ__LINE_NUMBER = INST_RAM__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_READ__PREDICATE = INST_RAM__PREDICATE;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_READ__PORT = INST_RAM__PORT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_READ__SOURCE = INST_RAM__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_READ__TARGET = INST_RAM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inst Ram Read</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_READ_FEATURE_COUNT = INST_RAM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_SET_ADDRESS__LINE_NUMBER = INST_RAM__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_SET_ADDRESS__PREDICATE = INST_RAM__PREDICATE;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_SET_ADDRESS__PORT = INST_RAM__PORT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_SET_ADDRESS__SOURCE = INST_RAM__SOURCE;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_SET_ADDRESS__INDEXES = INST_RAM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inst Ram Set Address</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_SET_ADDRESS_FEATURE_COUNT = INST_RAM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_WRITE__LINE_NUMBER = INST_RAM__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_WRITE__PREDICATE = INST_RAM__PREDICATE;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_WRITE__PORT = INST_RAM__PORT;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_WRITE__SOURCE = INST_RAM__SOURCE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_WRITE__VALUE = INST_RAM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Inst Ram Write</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_RAM_WRITE_FEATURE_COUNT = INST_RAM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_SPLIT__LINE_NUMBER = IrPackage.INST_SPECIFIC__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_SPLIT__PREDICATE = IrPackage.INST_SPECIFIC__PREDICATE;

	/**
	 * The number of structural features of the '<em>Inst Split</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INST_SPLIT_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

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
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstTernary <em>Inst Ternary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Ternary</em>'.
	 * @see net.sf.orcc.backends.instructions.InstTernary
	 * @generated
	 */
	EClass getInstTernary();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstTernary#getConditionValue <em>Condition Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition Value</em>'.
	 * @see net.sf.orcc.backends.instructions.InstTernary#getConditionValue()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_ConditionValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstTernary#getTrueValue <em>True Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>True Value</em>'.
	 * @see net.sf.orcc.backends.instructions.InstTernary#getTrueValue()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_TrueValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstTernary#getFalseValue <em>False Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>False Value</em>'.
	 * @see net.sf.orcc.backends.instructions.InstTernary#getFalseValue()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_FalseValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstTernary#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.InstTernary#getTarget()
	 * @see #getInstTernary()
	 * @generated
	 */
	EReference getInstTernary_Target();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstAssignIndex <em>Inst Assign Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Assign Index</em>'.
	 * @see net.sf.orcc.backends.instructions.InstAssignIndex
	 * @generated
	 */
	EClass getInstAssignIndex();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.instructions.InstAssignIndex#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.backends.instructions.InstAssignIndex#getIndexes()
	 * @see #getInstAssignIndex()
	 * @generated
	 */
	EReference getInstAssignIndex_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstAssignIndex#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.InstAssignIndex#getTarget()
	 * @see #getInstAssignIndex()
	 * @generated
	 */
	EReference getInstAssignIndex_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstAssignIndex#getListType <em>List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>List Type</em>'.
	 * @see net.sf.orcc.backends.instructions.InstAssignIndex#getListType()
	 * @see #getInstAssignIndex()
	 * @generated
	 */
	EReference getInstAssignIndex_ListType();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstSplit <em>Inst Split</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Split</em>'.
	 * @see net.sf.orcc.backends.instructions.InstSplit
	 * @generated
	 */
	EClass getInstSplit();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstRam <em>Inst Ram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Ram</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRam
	 * @generated
	 */
	EClass getInstRam();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.instructions.InstRam#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRam#getPort()
	 * @see #getInstRam()
	 * @generated
	 */
	EAttribute getInstRam_Port();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstRam#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRam#getSource()
	 * @see #getInstRam()
	 * @generated
	 */
	EReference getInstRam_Source();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstRamRead <em>Inst Ram Read</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Ram Read</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRamRead
	 * @generated
	 */
	EClass getInstRamRead();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstRamRead#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRamRead#getTarget()
	 * @see #getInstRamRead()
	 * @generated
	 */
	EReference getInstRamRead_Target();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstRamSetAddress <em>Inst Ram Set Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Ram Set Address</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRamSetAddress
	 * @generated
	 */
	EClass getInstRamSetAddress();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.instructions.InstRamSetAddress#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRamSetAddress#getIndexes()
	 * @see #getInstRamSetAddress()
	 * @generated
	 */
	EReference getInstRamSetAddress_Indexes();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstRamWrite <em>Inst Ram Write</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Ram Write</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRamWrite
	 * @generated
	 */
	EClass getInstRamWrite();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstRamWrite#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.backends.instructions.InstRamWrite#getValue()
	 * @see #getInstRamWrite()
	 * @generated
	 */
	EReference getInstRamWrite_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstGetElementPtr <em>Inst Get Element Ptr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Get Element Ptr</em>'.
	 * @see net.sf.orcc.backends.instructions.InstGetElementPtr
	 * @generated
	 */
	EClass getInstGetElementPtr();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.instructions.InstGetElementPtr#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.backends.instructions.InstGetElementPtr#getIndexes()
	 * @see #getInstGetElementPtr()
	 * @generated
	 */
	EReference getInstGetElementPtr_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstGetElementPtr#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.InstGetElementPtr#getTarget()
	 * @see #getInstGetElementPtr()
	 * @generated
	 */
	EReference getInstGetElementPtr_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstGetElementPtr#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see net.sf.orcc.backends.instructions.InstGetElementPtr#getSource()
	 * @see #getInstGetElementPtr()
	 * @generated
	 */
	EReference getInstGetElementPtr_Source();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.InstCast <em>Inst Cast</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inst Cast</em>'.
	 * @see net.sf.orcc.backends.instructions.InstCast
	 * @generated
	 */
	EClass getInstCast();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstCast#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.InstCast#getTarget()
	 * @see #getInstCast()
	 * @generated
	 */
	EReference getInstCast_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.InstCast#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see net.sf.orcc.backends.instructions.InstCast#getSource()
	 * @see #getInstCast()
	 * @generated
	 */
	EReference getInstCast_Source();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InstructionsFactory getInstructionsFactory();

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
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstTernaryImpl <em>Inst Ternary</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstTernaryImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstTernary()
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
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl <em>Inst Assign Index</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstAssignIndex()
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
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstSplitImpl <em>Inst Split</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstSplitImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstSplit()
		 * @generated
		 */
		EClass INST_SPLIT = eINSTANCE.getInstSplit();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstRamImpl <em>Inst Ram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstRamImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRam()
		 * @generated
		 */
		EClass INST_RAM = eINSTANCE.getInstRam();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INST_RAM__PORT = eINSTANCE.getInstRam_Port();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_RAM__SOURCE = eINSTANCE.getInstRam_Source();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstRamReadImpl <em>Inst Ram Read</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstRamReadImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRamRead()
		 * @generated
		 */
		EClass INST_RAM_READ = eINSTANCE.getInstRamRead();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_RAM_READ__TARGET = eINSTANCE.getInstRamRead_Target();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstRamSetAddressImpl <em>Inst Ram Set Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstRamSetAddressImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRamSetAddress()
		 * @generated
		 */
		EClass INST_RAM_SET_ADDRESS = eINSTANCE.getInstRamSetAddress();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_RAM_SET_ADDRESS__INDEXES = eINSTANCE.getInstRamSetAddress_Indexes();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstRamWriteImpl <em>Inst Ram Write</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstRamWriteImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstRamWrite()
		 * @generated
		 */
		EClass INST_RAM_WRITE = eINSTANCE.getInstRamWrite();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INST_RAM_WRITE__VALUE = eINSTANCE.getInstRamWrite_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstGetElementPtrImpl <em>Inst Get Element Ptr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstGetElementPtrImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstGetElementPtr()
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
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.InstCastImpl <em>Inst Cast</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.InstCastImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getInstCast()
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

	}

} //InstructionsPackage

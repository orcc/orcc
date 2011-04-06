/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.instructions;

import net.sf.orcc.ir.IrPackage;

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
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.TernaryOperationImpl <em>Ternary Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.TernaryOperationImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getTernaryOperation()
	 * @generated
	 */
	int TERNARY_OPERATION = 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_OPERATION__LOCATION = IrPackage.INST_SPECIFIC__LOCATION;

	/**
	 * The feature id for the '<em><b>Condition Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_OPERATION__CONDITION_VALUE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>True Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_OPERATION__TRUE_VALUE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>False Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_OPERATION__FALSE_VALUE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_OPERATION__TARGET = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Ternary Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERNARY_OPERATION_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 4;


	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.AssignIndexImpl <em>Assign Index</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.AssignIndexImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getAssignIndex()
	 * @generated
	 */
	int ASSIGN_INDEX = 1;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGN_INDEX__LOCATION = IrPackage.INST_SPECIFIC__LOCATION;

	/**
	 * The feature id for the '<em><b>Indexes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGN_INDEX__INDEXES = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGN_INDEX__TARGET = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>List Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGN_INDEX__LIST_TYPE = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Assign Index</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGN_INDEX_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.instructions.impl.SplitInstructionImpl <em>Split Instruction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.instructions.impl.SplitInstructionImpl
	 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getSplitInstruction()
	 * @generated
	 */
	int SPLIT_INSTRUCTION = 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPLIT_INSTRUCTION__LOCATION = IrPackage.INST_SPECIFIC__LOCATION;

	/**
	 * The number of structural features of the '<em>Split Instruction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPLIT_INSTRUCTION_FEATURE_COUNT = IrPackage.INST_SPECIFIC_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.TernaryOperation <em>Ternary Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ternary Operation</em>'.
	 * @see net.sf.orcc.backends.instructions.TernaryOperation
	 * @generated
	 */
	EClass getTernaryOperation();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.TernaryOperation#getConditionValue <em>Condition Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition Value</em>'.
	 * @see net.sf.orcc.backends.instructions.TernaryOperation#getConditionValue()
	 * @see #getTernaryOperation()
	 * @generated
	 */
	EReference getTernaryOperation_ConditionValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.TernaryOperation#getTrueValue <em>True Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>True Value</em>'.
	 * @see net.sf.orcc.backends.instructions.TernaryOperation#getTrueValue()
	 * @see #getTernaryOperation()
	 * @generated
	 */
	EReference getTernaryOperation_TrueValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.TernaryOperation#getFalseValue <em>False Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>False Value</em>'.
	 * @see net.sf.orcc.backends.instructions.TernaryOperation#getFalseValue()
	 * @see #getTernaryOperation()
	 * @generated
	 */
	EReference getTernaryOperation_FalseValue();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.TernaryOperation#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.TernaryOperation#getTarget()
	 * @see #getTernaryOperation()
	 * @generated
	 */
	EReference getTernaryOperation_Target();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.AssignIndex <em>Assign Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assign Index</em>'.
	 * @see net.sf.orcc.backends.instructions.AssignIndex
	 * @generated
	 */
	EClass getAssignIndex();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.instructions.AssignIndex#getIndexes <em>Indexes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Indexes</em>'.
	 * @see net.sf.orcc.backends.instructions.AssignIndex#getIndexes()
	 * @see #getAssignIndex()
	 * @generated
	 */
	EReference getAssignIndex_Indexes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.AssignIndex#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see net.sf.orcc.backends.instructions.AssignIndex#getTarget()
	 * @see #getAssignIndex()
	 * @generated
	 */
	EReference getAssignIndex_Target();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.instructions.AssignIndex#getListType <em>List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>List Type</em>'.
	 * @see net.sf.orcc.backends.instructions.AssignIndex#getListType()
	 * @see #getAssignIndex()
	 * @generated
	 */
	EReference getAssignIndex_ListType();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.instructions.SplitInstruction <em>Split Instruction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Split Instruction</em>'.
	 * @see net.sf.orcc.backends.instructions.SplitInstruction
	 * @generated
	 */
	EClass getSplitInstruction();

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
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.TernaryOperationImpl <em>Ternary Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.TernaryOperationImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getTernaryOperation()
		 * @generated
		 */
		EClass TERNARY_OPERATION = eINSTANCE.getTernaryOperation();

		/**
		 * The meta object literal for the '<em><b>Condition Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_OPERATION__CONDITION_VALUE = eINSTANCE.getTernaryOperation_ConditionValue();

		/**
		 * The meta object literal for the '<em><b>True Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_OPERATION__TRUE_VALUE = eINSTANCE.getTernaryOperation_TrueValue();

		/**
		 * The meta object literal for the '<em><b>False Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_OPERATION__FALSE_VALUE = eINSTANCE.getTernaryOperation_FalseValue();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERNARY_OPERATION__TARGET = eINSTANCE.getTernaryOperation_Target();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.AssignIndexImpl <em>Assign Index</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.AssignIndexImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getAssignIndex()
		 * @generated
		 */
		EClass ASSIGN_INDEX = eINSTANCE.getAssignIndex();

		/**
		 * The meta object literal for the '<em><b>Indexes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGN_INDEX__INDEXES = eINSTANCE.getAssignIndex_Indexes();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGN_INDEX__TARGET = eINSTANCE.getAssignIndex_Target();

		/**
		 * The meta object literal for the '<em><b>List Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGN_INDEX__LIST_TYPE = eINSTANCE.getAssignIndex_ListType();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.instructions.impl.SplitInstructionImpl <em>Split Instruction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.instructions.impl.SplitInstructionImpl
		 * @see net.sf.orcc.backends.instructions.impl.InstructionsPackageImpl#getSplitInstruction()
		 * @generated
		 */
		EClass SPLIT_INSTRUCTION = eINSTANCE.getSplitInstruction();

	}

} //InstructionsPackage

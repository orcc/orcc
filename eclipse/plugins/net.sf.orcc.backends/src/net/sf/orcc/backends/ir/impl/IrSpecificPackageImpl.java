/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.impl;

import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.InstAssignIndex;
import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.backends.ir.IrSpecificFactory;
import net.sf.orcc.backends.ir.IrSpecificPackage;
import net.sf.orcc.ir.IrPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IrSpecificPackageImpl extends EPackageImpl implements
		IrSpecificPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instAssignIndexEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instCastEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instTernaryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockForEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.orcc.backends.ir.IrSpecificPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IrSpecificPackageImpl() {
		super(eNS_URI, IrSpecificFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link IrSpecificPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IrSpecificPackage init() {
		if (isInited)
			return (IrSpecificPackage) EPackage.Registry.INSTANCE
					.getEPackage(IrSpecificPackage.eNS_URI);

		// Obtain or create and register package
		IrSpecificPackageImpl theIrSpecificPackage = (IrSpecificPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof IrSpecificPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new IrSpecificPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		IrPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIrSpecificPackage.createPackageContents();

		// Initialize created meta-data
		theIrSpecificPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIrSpecificPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IrSpecificPackage.eNS_URI,
				theIrSpecificPackage);
		return theIrSpecificPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstAssignIndex() {
		return instAssignIndexEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstAssignIndex_Indexes() {
		return (EReference) instAssignIndexEClass.getEStructuralFeatures().get(
				0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstAssignIndex_Target() {
		return (EReference) instAssignIndexEClass.getEStructuralFeatures().get(
				1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstAssignIndex_ListType() {
		return (EReference) instAssignIndexEClass.getEStructuralFeatures().get(
				2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstCast() {
		return instCastEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstCast_Target() {
		return (EReference) instCastEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstCast_Source() {
		return (EReference) instCastEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstTernary() {
		return instTernaryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_ConditionValue() {
		return (EReference) instTernaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_TrueValue() {
		return (EReference) instTernaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_FalseValue() {
		return (EReference) instTernaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_Target() {
		return (EReference) instTernaryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBlockFor() {
		return blockForEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBlockFor_Condition() {
		return (EReference) blockForEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBlockFor_JoinNode() {
		return (EReference) blockForEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBlockFor_LineNumber() {
		return (EAttribute) blockForEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBlockFor_Nodes() {
		return (EReference) blockForEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBlockFor_Step() {
		return (EReference) blockForEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBlockFor_Init() {
		return (EReference) blockForEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IrSpecificFactory getIrSpecificFactory() {
		return (IrSpecificFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		instAssignIndexEClass = createEClass(INST_ASSIGN_INDEX);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__INDEXES);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__TARGET);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__LIST_TYPE);

		instCastEClass = createEClass(INST_CAST);
		createEReference(instCastEClass, INST_CAST__TARGET);
		createEReference(instCastEClass, INST_CAST__SOURCE);

		instTernaryEClass = createEClass(INST_TERNARY);
		createEReference(instTernaryEClass, INST_TERNARY__CONDITION_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__TRUE_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__FALSE_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__TARGET);

		blockForEClass = createEClass(BLOCK_FOR);
		createEReference(blockForEClass, BLOCK_FOR__CONDITION);
		createEReference(blockForEClass, BLOCK_FOR__JOIN_NODE);
		createEAttribute(blockForEClass, BLOCK_FOR__LINE_NUMBER);
		createEReference(blockForEClass, BLOCK_FOR__NODES);
		createEReference(blockForEClass, BLOCK_FOR__STEP);
		createEReference(blockForEClass, BLOCK_FOR__INIT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IrPackage theIrPackage = (IrPackage) EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		instAssignIndexEClass.getESuperTypes().add(
				theIrPackage.getInstSpecific());
		instCastEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instTernaryEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		blockForEClass.getESuperTypes().add(theIrPackage.getBlock());

		// Initialize classes and features; add operations and parameters
		initEClass(instAssignIndexEClass, InstAssignIndex.class,
				"InstAssignIndex", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstAssignIndex_Indexes(),
				theIrPackage.getExpression(), null, "indexes", null, 0, -1,
				InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssignIndex_Target(), theIrPackage.getDef(),
				null, "target", null, 0, 1, InstAssignIndex.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getInstAssignIndex_ListType(), theIrPackage.getType(),
				null, "listType", null, 0, 1, InstAssignIndex.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(instCastEClass, InstCast.class, "InstCast", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstCast_Target(), theIrPackage.getDef(), null,
				"target", null, 0, 1, InstCast.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCast_Source(), theIrPackage.getUse(), null,
				"source", null, 0, 1, InstCast.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instTernaryEClass, InstTernary.class, "InstTernary",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstTernary_ConditionValue(),
				theIrPackage.getExpression(), null, "conditionValue", null, 0,
				1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_TrueValue(),
				theIrPackage.getExpression(), null, "trueValue", null, 0, 1,
				InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_FalseValue(),
				theIrPackage.getExpression(), null, "falseValue", null, 0, 1,
				InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_Target(), theIrPackage.getDef(), null,
				"target", null, 0, 1, InstTernary.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockForEClass, BlockFor.class, "BlockFor", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockFor_Condition(), theIrPackage.getExpression(),
				null, "condition", null, 0, 1, BlockFor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockFor_JoinNode(), theIrPackage.getBlockBasic(),
				null, "joinNode", null, 0, 1, BlockFor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockFor_LineNumber(), theEcorePackage.getEInt(),
				"lineNumber", "0", 0, 1, BlockFor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getBlockFor_Nodes(), theIrPackage.getBlock(), null,
				"nodes", null, 0, -1, BlockFor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockFor_Step(), theIrPackage.getInstruction(), null,
				"step", null, 0, 1, BlockFor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getBlockFor_Init(), theIrPackage.getInstruction(), null,
				"init", null, 0, 1, BlockFor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //IrSpecificPackageImpl

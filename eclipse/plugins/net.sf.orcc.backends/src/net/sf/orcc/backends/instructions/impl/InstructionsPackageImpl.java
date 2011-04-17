/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.instructions.impl;

import net.sf.orcc.backends.instructions.InstAssignIndex;
import net.sf.orcc.backends.instructions.InstCast;
import net.sf.orcc.backends.instructions.InstGetElementPtr;
import net.sf.orcc.backends.instructions.InstRam;
import net.sf.orcc.backends.instructions.InstRamRead;
import net.sf.orcc.backends.instructions.InstRamSetAddress;
import net.sf.orcc.backends.instructions.InstRamWrite;
import net.sf.orcc.backends.instructions.InstructionsFactory;
import net.sf.orcc.backends.instructions.InstructionsPackage;
import net.sf.orcc.backends.instructions.InstSplit;
import net.sf.orcc.backends.instructions.InstTernary;

import net.sf.orcc.ir.IrPackage;

import net.sf.orcc.moc.MocPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InstructionsPackageImpl extends EPackageImpl implements InstructionsPackage {
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
	private EClass instAssignIndexEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instSplitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instRamEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instRamReadEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instRamSetAddressEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instRamWriteEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instGetElementPtrEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instCastEClass = null;

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
	 * @see net.sf.orcc.backends.instructions.InstructionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InstructionsPackageImpl() {
		super(eNS_URI, InstructionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link InstructionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InstructionsPackage init() {
		if (isInited) return (InstructionsPackage)EPackage.Registry.INSTANCE.getEPackage(InstructionsPackage.eNS_URI);

		// Obtain or create and register package
		InstructionsPackageImpl theInstructionsPackage = (InstructionsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof InstructionsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new InstructionsPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		IrPackage.eINSTANCE.eClass();
		MocPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theInstructionsPackage.createPackageContents();

		// Initialize created meta-data
		theInstructionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInstructionsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InstructionsPackage.eNS_URI, theInstructionsPackage);
		return theInstructionsPackage;
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
		return (EReference)instTernaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_TrueValue() {
		return (EReference)instTernaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_FalseValue() {
		return (EReference)instTernaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstTernary_Target() {
		return (EReference)instTernaryEClass.getEStructuralFeatures().get(3);
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
		return (EReference)instAssignIndexEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstAssignIndex_Target() {
		return (EReference)instAssignIndexEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstAssignIndex_ListType() {
		return (EReference)instAssignIndexEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstSplit() {
		return instSplitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstRam() {
		return instRamEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInstRam_Port() {
		return (EAttribute)instRamEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstRam_Variable() {
		return (EReference)instRamEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstRamRead() {
		return instRamReadEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstRamRead_Target() {
		return (EReference)instRamReadEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstRamSetAddress() {
		return instRamSetAddressEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstRamSetAddress_Indexes() {
		return (EReference)instRamSetAddressEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstRamWrite() {
		return instRamWriteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstRamWrite_Value() {
		return (EReference)instRamWriteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstGetElementPtr() {
		return instGetElementPtrEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstGetElementPtr_Indexes() {
		return (EReference)instGetElementPtrEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstGetElementPtr_Target() {
		return (EReference)instGetElementPtrEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstGetElementPtr_Source() {
		return (EReference)instGetElementPtrEClass.getEStructuralFeatures().get(2);
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
		return (EReference)instCastEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstCast_Source() {
		return (EReference)instCastEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstructionsFactory getInstructionsFactory() {
		return (InstructionsFactory)getEFactoryInstance();
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		instTernaryEClass = createEClass(INST_TERNARY);
		createEReference(instTernaryEClass, INST_TERNARY__CONDITION_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__TRUE_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__FALSE_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__TARGET);

		instAssignIndexEClass = createEClass(INST_ASSIGN_INDEX);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__INDEXES);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__TARGET);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__LIST_TYPE);

		instSplitEClass = createEClass(INST_SPLIT);

		instRamEClass = createEClass(INST_RAM);
		createEAttribute(instRamEClass, INST_RAM__PORT);
		createEReference(instRamEClass, INST_RAM__VARIABLE);

		instRamReadEClass = createEClass(INST_RAM_READ);
		createEReference(instRamReadEClass, INST_RAM_READ__TARGET);

		instRamSetAddressEClass = createEClass(INST_RAM_SET_ADDRESS);
		createEReference(instRamSetAddressEClass, INST_RAM_SET_ADDRESS__INDEXES);

		instRamWriteEClass = createEClass(INST_RAM_WRITE);
		createEReference(instRamWriteEClass, INST_RAM_WRITE__VALUE);

		instGetElementPtrEClass = createEClass(INST_GET_ELEMENT_PTR);
		createEReference(instGetElementPtrEClass, INST_GET_ELEMENT_PTR__INDEXES);
		createEReference(instGetElementPtrEClass, INST_GET_ELEMENT_PTR__TARGET);
		createEReference(instGetElementPtrEClass, INST_GET_ELEMENT_PTR__SOURCE);

		instCastEClass = createEClass(INST_CAST);
		createEReference(instCastEClass, INST_CAST__TARGET);
		createEReference(instCastEClass, INST_CAST__SOURCE);
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IrPackage theIrPackage = (IrPackage)EPackage.Registry.INSTANCE.getEPackage(IrPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		instTernaryEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instAssignIndexEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instSplitEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instRamEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instRamReadEClass.getESuperTypes().add(this.getInstRam());
		instRamSetAddressEClass.getESuperTypes().add(this.getInstRam());
		instRamWriteEClass.getESuperTypes().add(this.getInstRam());
		instGetElementPtrEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instCastEClass.getESuperTypes().add(theIrPackage.getInstSpecific());

		// Initialize classes and features; add operations and parameters
		initEClass(instTernaryEClass, InstTernary.class, "InstTernary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstTernary_ConditionValue(), theIrPackage.getExpression(), null, "conditionValue", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_TrueValue(), theIrPackage.getExpression(), null, "trueValue", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_FalseValue(), theIrPackage.getExpression(), null, "falseValue", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instAssignIndexEClass, InstAssignIndex.class, "InstAssignIndex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstAssignIndex_Indexes(), theIrPackage.getExpression(), null, "indexes", null, 0, -1, InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssignIndex_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssignIndex_ListType(), theIrPackage.getType(), null, "listType", null, 0, 1, InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instSplitEClass, InstSplit.class, "InstSplit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(instRamEClass, InstRam.class, "InstRam", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInstRam_Port(), ecorePackage.getEInt(), "port", null, 0, 1, InstRam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstRam_Variable(), theIrPackage.getVar(), null, "variable", null, 0, 1, InstRam.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instRamReadEClass, InstRamRead.class, "InstRamRead", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstRamRead_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstRamRead.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instRamSetAddressEClass, InstRamSetAddress.class, "InstRamSetAddress", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstRamSetAddress_Indexes(), theIrPackage.getExpression(), null, "indexes", null, 0, -1, InstRamSetAddress.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instRamWriteEClass, InstRamWrite.class, "InstRamWrite", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstRamWrite_Value(), theIrPackage.getExpression(), null, "value", null, 0, 1, InstRamWrite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instGetElementPtrEClass, InstGetElementPtr.class, "InstGetElementPtr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstGetElementPtr_Indexes(), theIrPackage.getExpression(), null, "indexes", null, 0, -1, InstGetElementPtr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstGetElementPtr_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstGetElementPtr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstGetElementPtr_Source(), theIrPackage.getUse(), null, "source", null, 0, 1, InstGetElementPtr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instCastEClass, InstCast.class, "InstCast", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstCast_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstCast.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCast_Source(), theIrPackage.getUse(), null, "source", null, 0, 1, InstCast.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //InstructionsPackageImpl

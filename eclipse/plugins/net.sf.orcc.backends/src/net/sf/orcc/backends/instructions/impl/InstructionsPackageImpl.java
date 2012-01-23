/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.instructions.impl;

import net.sf.orcc.backends.instructions.InstAssignIndex;
import net.sf.orcc.backends.instructions.InstCast;
import net.sf.orcc.backends.instructions.InstGetElementPtr;
import net.sf.orcc.backends.instructions.InstTernary;
import net.sf.orcc.backends.instructions.InstructionsFactory;
import net.sf.orcc.backends.instructions.InstructionsPackage;
import net.sf.orcc.ir.IrPackage;

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
		instAssignIndexEClass = createEClass(INST_ASSIGN_INDEX);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__INDEXES);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__TARGET);
		createEReference(instAssignIndexEClass, INST_ASSIGN_INDEX__LIST_TYPE);

		instCastEClass = createEClass(INST_CAST);
		createEReference(instCastEClass, INST_CAST__TARGET);
		createEReference(instCastEClass, INST_CAST__SOURCE);

		instGetElementPtrEClass = createEClass(INST_GET_ELEMENT_PTR);
		createEReference(instGetElementPtrEClass, INST_GET_ELEMENT_PTR__INDEXES);
		createEReference(instGetElementPtrEClass, INST_GET_ELEMENT_PTR__TARGET);
		createEReference(instGetElementPtrEClass, INST_GET_ELEMENT_PTR__SOURCE);

		instTernaryEClass = createEClass(INST_TERNARY);
		createEReference(instTernaryEClass, INST_TERNARY__CONDITION_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__TRUE_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__FALSE_VALUE);
		createEReference(instTernaryEClass, INST_TERNARY__TARGET);
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
		instAssignIndexEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instCastEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instGetElementPtrEClass.getESuperTypes().add(theIrPackage.getInstSpecific());
		instTernaryEClass.getESuperTypes().add(theIrPackage.getInstSpecific());

		// Initialize classes and features; add operations and parameters
		initEClass(instAssignIndexEClass, InstAssignIndex.class, "InstAssignIndex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstAssignIndex_Indexes(), theIrPackage.getExpression(), null, "indexes", null, 0, -1, InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssignIndex_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssignIndex_ListType(), theIrPackage.getType(), null, "listType", null, 0, 1, InstAssignIndex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instCastEClass, InstCast.class, "InstCast", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstCast_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstCast.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCast_Source(), theIrPackage.getUse(), null, "source", null, 0, 1, InstCast.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instGetElementPtrEClass, InstGetElementPtr.class, "InstGetElementPtr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstGetElementPtr_Indexes(), theIrPackage.getExpression(), null, "indexes", null, 0, -1, InstGetElementPtr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstGetElementPtr_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstGetElementPtr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstGetElementPtr_Source(), theIrPackage.getUse(), null, "source", null, 0, 1, InstGetElementPtr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instTernaryEClass, InstTernary.class, "InstTernary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstTernary_ConditionValue(), theIrPackage.getExpression(), null, "conditionValue", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_TrueValue(), theIrPackage.getExpression(), null, "trueValue", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_FalseValue(), theIrPackage.getExpression(), null, "falseValue", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstTernary_Target(), theIrPackage.getDef(), null, "target", null, 0, 1, InstTernary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //InstructionsPackageImpl

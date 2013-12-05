/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.impl.DfPackageImpl;
import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.impl.IrPackageImpl;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DPNMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.KPNMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.util.UtilPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class MocPackageImpl extends EPackageImpl implements MocPackage {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moCEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass csdfMoCEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dpnMoCEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kpnMoCEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass qsdfMoCEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sdfMoCEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invocationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.orcc.moc.MocPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MocPackageImpl() {
		super(eNS_URI, MocFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link MocPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MocPackage init() {
		if (isInited)
			return (MocPackage) EPackage.Registry.INSTANCE
					.getEPackage(MocPackage.eNS_URI);

		// Obtain or create and register package
		MocPackageImpl theMocPackage = (MocPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof MocPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new MocPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UtilPackage.eINSTANCE.eClass();
		GraphPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		DfPackageImpl theDfPackage = (DfPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI) instanceof DfPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI) : DfPackage.eINSTANCE);
		IrPackageImpl theIrPackage = (IrPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI) instanceof IrPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI) : IrPackage.eINSTANCE);

		// Create package meta-data objects
		theMocPackage.createPackageContents();
		theDfPackage.createPackageContents();
		theIrPackage.createPackageContents();

		// Initialize created meta-data
		theMocPackage.initializePackageContents();
		theDfPackage.initializePackageContents();
		theIrPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMocPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MocPackage.eNS_URI, theMocPackage);
		return theMocPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMoC() {
		return moCEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCSDFMoC() {
		return csdfMoCEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCSDFMoC_InputPattern() {
		return (EReference) csdfMoCEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCSDFMoC_NumberOfPhases() {
		return (EAttribute) csdfMoCEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCSDFMoC_OutputPattern() {
		return (EReference) csdfMoCEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCSDFMoC_Invocations() {
		return (EReference) csdfMoCEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCSDFMoC_DelayPattern() {
		return (EReference) csdfMoCEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDPNMoC() {
		return dpnMoCEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getKPNMoC() {
		return kpnMoCEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQSDFMoC() {
		return qsdfMoCEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSDFMoC() {
		return sdfMoCEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInvocation() {
		return invocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInvocation_Action() {
		return (EReference) invocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public MocFactory getMocFactory() {
		return (MocFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		moCEClass = createEClass(MO_C);

		csdfMoCEClass = createEClass(CSDF_MO_C);
		createEReference(csdfMoCEClass, CSDF_MO_C__INPUT_PATTERN);
		createEAttribute(csdfMoCEClass, CSDF_MO_C__NUMBER_OF_PHASES);
		createEReference(csdfMoCEClass, CSDF_MO_C__OUTPUT_PATTERN);
		createEReference(csdfMoCEClass, CSDF_MO_C__INVOCATIONS);
		createEReference(csdfMoCEClass, CSDF_MO_C__DELAY_PATTERN);

		dpnMoCEClass = createEClass(DPN_MO_C);

		kpnMoCEClass = createEClass(KPN_MO_C);

		qsdfMoCEClass = createEClass(QSDF_MO_C);

		sdfMoCEClass = createEClass(SDF_MO_C);

		invocationEClass = createEClass(INVOCATION);
		createEReference(invocationEClass, INVOCATION__ACTION);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		DfPackage theDfPackage = (DfPackage) EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		csdfMoCEClass.getESuperTypes().add(this.getMoC());
		dpnMoCEClass.getESuperTypes().add(this.getMoC());
		kpnMoCEClass.getESuperTypes().add(this.getMoC());
		qsdfMoCEClass.getESuperTypes().add(this.getMoC());
		sdfMoCEClass.getESuperTypes().add(this.getCSDFMoC());

		// Initialize classes and features; add operations and parameters
		initEClass(moCEClass, MoC.class, "MoC", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(csdfMoCEClass, CSDFMoC.class, "CSDFMoC", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCSDFMoC_InputPattern(), theDfPackage.getPattern(),
				null, "inputPattern", null, 0, 1, CSDFMoC.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCSDFMoC_NumberOfPhases(), ecorePackage.getEInt(),
				"numberOfPhases", null, 0, 1, CSDFMoC.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getCSDFMoC_OutputPattern(), theDfPackage.getPattern(),
				null, "outputPattern", null, 0, 1, CSDFMoC.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getCSDFMoC_Invocations(), this.getInvocation(), null,
				"invocations", null, 0, -1, CSDFMoC.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCSDFMoC_DelayPattern(), theDfPackage.getPattern(),
				null, "delayPattern", null, 0, 1, CSDFMoC.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dpnMoCEClass, DPNMoC.class, "DPNMoC", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(kpnMoCEClass, KPNMoC.class, "KPNMoC", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(qsdfMoCEClass, QSDFMoC.class, "QSDFMoC", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sdfMoCEClass, SDFMoC.class, "SDFMoC", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(invocationEClass, Invocation.class, "Invocation",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInvocation_Action(), theDfPackage.getAction(), null,
				"action", null, 0, 1, Invocation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // MocPackageImpl

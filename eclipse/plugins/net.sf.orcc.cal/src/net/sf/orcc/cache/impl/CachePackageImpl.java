/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import java.util.Map;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheFactory;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.moc.MocPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class CachePackageImpl extends EPackageImpl implements CachePackage {
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
	 * This method is used to initialize {@link CachePackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CachePackage init() {
		if (isInited) return (CachePackage)EPackage.Registry.INSTANCE.getEPackage(CachePackage.eNS_URI);

		// Obtain or create and register package
		CachePackageImpl theCachePackage = (CachePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CachePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CachePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		IrPackage.eINSTANCE.eClass();
		MocPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCachePackage.createPackageContents();

		// Initialize created meta-data
		theCachePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCachePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CachePackage.eNS_URI, theCachePackage);
		return theCachePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cacheEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cacheManagerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectToExpressionMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectToEObjectMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectToTypeMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

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
	 * @see net.sf.orcc.cache.CachePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CachePackageImpl() {
		super(eNS_URI, CacheFactory.eINSTANCE);
	}

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
		cacheEClass = createEClass(CACHE);
		createEReference(cacheEClass, CACHE__EXPRESSIONS_MAP);
		createEReference(cacheEClass, CACHE__IR_MAP);
		createEReference(cacheEClass, CACHE__TYPES_MAP);

		cacheManagerEClass = createEClass(CACHE_MANAGER);

		eObjectToExpressionMapEntryEClass = createEClass(EOBJECT_TO_EXPRESSION_MAP_ENTRY);
		createEReference(eObjectToExpressionMapEntryEClass, EOBJECT_TO_EXPRESSION_MAP_ENTRY__KEY);
		createEReference(eObjectToExpressionMapEntryEClass, EOBJECT_TO_EXPRESSION_MAP_ENTRY__VALUE);

		eObjectToEObjectMapEntryEClass = createEClass(EOBJECT_TO_EOBJECT_MAP_ENTRY);
		createEReference(eObjectToEObjectMapEntryEClass, EOBJECT_TO_EOBJECT_MAP_ENTRY__KEY);
		createEReference(eObjectToEObjectMapEntryEClass, EOBJECT_TO_EOBJECT_MAP_ENTRY__VALUE);

		eObjectToTypeMapEntryEClass = createEClass(EOBJECT_TO_TYPE_MAP_ENTRY);
		createEReference(eObjectToTypeMapEntryEClass, EOBJECT_TO_TYPE_MAP_ENTRY__KEY);
		createEReference(eObjectToTypeMapEntryEClass, EOBJECT_TO_TYPE_MAP_ENTRY__VALUE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCache() {
		return cacheEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCache_ExpressionsMap() {
		return (EReference)cacheEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCache_IrMap() {
		return (EReference)cacheEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCache_TypesMap() {
		return (EReference)cacheEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CacheFactory getCacheFactory() {
		return (CacheFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCacheManager() {
		return cacheManagerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectToExpressionMapEntry() {
		return eObjectToExpressionMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToExpressionMapEntry_Key() {
		return (EReference)eObjectToExpressionMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToExpressionMapEntry_Value() {
		return (EReference)eObjectToExpressionMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectToEObjectMapEntry() {
		return eObjectToEObjectMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToEObjectMapEntry_Key() {
		return (EReference)eObjectToEObjectMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToEObjectMapEntry_Value() {
		return (EReference)eObjectToEObjectMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectToTypeMapEntry() {
		return eObjectToTypeMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToTypeMapEntry_Key() {
		return (EReference)eObjectToTypeMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToTypeMapEntry_Value() {
		return (EReference)eObjectToTypeMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		IrPackage theIrPackage = (IrPackage)EPackage.Registry.INSTANCE.getEPackage(IrPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(cacheEClass, Cache.class, "Cache", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCache_ExpressionsMap(), this.getEObjectToExpressionMapEntry(), null, "expressionsMap", null, 0, -1, Cache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCache_IrMap(), this.getEObjectToEObjectMapEntry(), null, "irMap", null, 0, -1, Cache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCache_TypesMap(), this.getEObjectToTypeMapEntry(), null, "typesMap", null, 0, -1, Cache.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cacheManagerEClass, CacheManager.class, "CacheManager", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eObjectToExpressionMapEntryEClass, Map.Entry.class, "EObjectToExpressionMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectToExpressionMapEntry_Key(), theEcorePackage.getEObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEObjectToExpressionMapEntry_Value(), theIrPackage.getExpression(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectToEObjectMapEntryEClass, Map.Entry.class, "EObjectToEObjectMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectToEObjectMapEntry_Key(), theEcorePackage.getEObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEObjectToEObjectMapEntry_Value(), theEcorePackage.getEObject(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectToTypeMapEntryEClass, Map.Entry.class, "EObjectToTypeMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectToTypeMapEntry_Key(), theEcorePackage.getEObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEObjectToTypeMapEntry_Value(), theIrPackage.getType(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // CachePackageImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache;

import org.eclipse.emf.ecore.EClass;
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
 * @see net.sf.orcc.cache.CacheFactory
 * @model kind="package"
 * @generated
 */
public interface CachePackage extends EPackage {
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
		 * The meta object literal for the '
		 * {@link net.sf.orcc.cache.impl.CacheImpl <em>Cache</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.cache.impl.CacheImpl
		 * @see net.sf.orcc.cache.impl.CachePackageImpl#getCache()
		 * @generated
		 */
		EClass CACHE = eINSTANCE.getCache();
		/**
		 * The meta object literal for the '<em><b>Expressions Map</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CACHE__EXPRESSIONS_MAP = eINSTANCE.getCache_ExpressionsMap();
		/**
		 * The meta object literal for the '<em><b>Ir Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CACHE__IR_MAP = eINSTANCE.getCache_IrMap();
		/**
		 * The meta object literal for the '<em><b>Types Map</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CACHE__TYPES_MAP = eINSTANCE.getCache_TypesMap();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.cache.impl.CacheManagerImpl <em>Manager</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.cache.impl.CacheManagerImpl
		 * @see net.sf.orcc.cache.impl.CachePackageImpl#getCacheManager()
		 * @generated
		 */
		EClass CACHE_MANAGER = eINSTANCE.getCacheManager();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.cache.impl.EObjectToExpressionMapEntryImpl <em>EObject To Expression Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.cache.impl.EObjectToExpressionMapEntryImpl
		 * @see net.sf.orcc.cache.impl.CachePackageImpl#getEObjectToExpressionMapEntry()
		 * @generated
		 */
		EClass EOBJECT_TO_EXPRESSION_MAP_ENTRY = eINSTANCE.getEObjectToExpressionMapEntry();
		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_EXPRESSION_MAP_ENTRY__KEY = eINSTANCE.getEObjectToExpressionMapEntry_Key();
		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_EXPRESSION_MAP_ENTRY__VALUE = eINSTANCE.getEObjectToExpressionMapEntry_Value();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.cache.impl.EObjectToEObjectMapEntryImpl <em>EObject To EObject Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.cache.impl.EObjectToEObjectMapEntryImpl
		 * @see net.sf.orcc.cache.impl.CachePackageImpl#getEObjectToEObjectMapEntry()
		 * @generated
		 */
		EClass EOBJECT_TO_EOBJECT_MAP_ENTRY = eINSTANCE.getEObjectToEObjectMapEntry();
		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_EOBJECT_MAP_ENTRY__KEY = eINSTANCE.getEObjectToEObjectMapEntry_Key();
		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_EOBJECT_MAP_ENTRY__VALUE = eINSTANCE.getEObjectToEObjectMapEntry_Value();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.cache.impl.EObjectToTypeMapEntryImpl <em>EObject To Type Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.cache.impl.EObjectToTypeMapEntryImpl
		 * @see net.sf.orcc.cache.impl.CachePackageImpl#getEObjectToTypeMapEntry()
		 * @generated
		 */
		EClass EOBJECT_TO_TYPE_MAP_ENTRY = eINSTANCE.getEObjectToTypeMapEntry();
		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_TYPE_MAP_ENTRY__KEY = eINSTANCE.getEObjectToTypeMapEntry_Key();
		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_TO_TYPE_MAP_ENTRY__VALUE = eINSTANCE.getEObjectToTypeMapEntry_Value();

	}

	/**
	 * The meta object id for the '{@link net.sf.orcc.cache.impl.CacheImpl <em>Cache</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.cache.impl.CacheImpl
	 * @see net.sf.orcc.cache.impl.CachePackageImpl#getCache()
	 * @generated
	 */
	int CACHE = 0;

	/**
	 * The feature id for the '<em><b>Expressions Map</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE__EXPRESSIONS_MAP = 0;

	/**
	 * The feature id for the '<em><b>Ir Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CACHE__IR_MAP = 1;

	/**
	 * The feature id for the '<em><b>Types Map</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE__TYPES_MAP = 2;

	/**
	 * The number of structural features of the '<em>Cache</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.cache.impl.CacheManagerImpl <em>Manager</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.cache.impl.CacheManagerImpl
	 * @see net.sf.orcc.cache.impl.CachePackageImpl#getCacheManager()
	 * @generated
	 */
	int CACHE_MANAGER = 1;

	/**
	 * The number of structural features of the '<em>Manager</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CACHE_MANAGER_FEATURE_COUNT = 0;

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	CachePackage eINSTANCE = net.sf.orcc.cache.impl.CachePackageImpl.init();

	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "cache";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.cal.cache";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/cache";

	/**
	 * The meta object id for the '{@link net.sf.orcc.cache.impl.EObjectToExpressionMapEntryImpl <em>EObject To Expression Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.cache.impl.EObjectToExpressionMapEntryImpl
	 * @see net.sf.orcc.cache.impl.CachePackageImpl#getEObjectToExpressionMapEntry()
	 * @generated
	 */
	int EOBJECT_TO_EXPRESSION_MAP_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_EXPRESSION_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_EXPRESSION_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EObject To Expression Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_EXPRESSION_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.cache.impl.EObjectToEObjectMapEntryImpl <em>EObject To EObject Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.cache.impl.EObjectToEObjectMapEntryImpl
	 * @see net.sf.orcc.cache.impl.CachePackageImpl#getEObjectToEObjectMapEntry()
	 * @generated
	 */
	int EOBJECT_TO_EOBJECT_MAP_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_EOBJECT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_EOBJECT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EObject To EObject Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_EOBJECT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.cache.impl.EObjectToTypeMapEntryImpl <em>EObject To Type Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.cache.impl.EObjectToTypeMapEntryImpl
	 * @see net.sf.orcc.cache.impl.CachePackageImpl#getEObjectToTypeMapEntry()
	 * @generated
	 */
	int EOBJECT_TO_TYPE_MAP_ENTRY = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_TYPE_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_TYPE_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EObject To Type Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_TO_TYPE_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.cache.Cache <em>Cache</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cache</em>'.
	 * @see net.sf.orcc.cache.Cache
	 * @generated
	 */
	EClass getCache();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.cache.Cache#getExpressionsMap <em>Expressions Map</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Expressions Map</em>'.
	 * @see net.sf.orcc.cache.Cache#getExpressionsMap()
	 * @see #getCache()
	 * @generated
	 */
	EReference getCache_ExpressionsMap();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.cache.Cache#getIrMap <em>Ir Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Ir Map</em>'.
	 * @see net.sf.orcc.cache.Cache#getIrMap()
	 * @see #getCache()
	 * @generated
	 */
	EReference getCache_IrMap();

	/**
	 * Returns the meta object for the map '
	 * {@link net.sf.orcc.cache.Cache#getTypesMap <em>Types Map</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>Types Map</em>'.
	 * @see net.sf.orcc.cache.Cache#getTypesMap()
	 * @see #getCache()
	 * @generated
	 */
	EReference getCache_TypesMap();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CacheFactory getCacheFactory();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.cache.CacheManager <em>Manager</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manager</em>'.
	 * @see net.sf.orcc.cache.CacheManager
	 * @generated
	 */
	EClass getCacheManager();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EObject To Expression Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject To Expression Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EObject"
	 *        valueType="net.sf.orcc.ir.Expression"
	 * @generated
	 */
	EClass getEObjectToExpressionMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToExpressionMapEntry()
	 * @generated
	 */
	EReference getEObjectToExpressionMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToExpressionMapEntry()
	 * @generated
	 */
	EReference getEObjectToExpressionMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EObject To EObject Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject To EObject Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EObject"
	 *        valueType="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EClass getEObjectToEObjectMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToEObjectMapEntry()
	 * @generated
	 */
	EReference getEObjectToEObjectMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToEObjectMapEntry()
	 * @generated
	 */
	EReference getEObjectToEObjectMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EObject To Type Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject To Type Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.ecore.EObject"
	 *        valueType="net.sf.orcc.ir.Type"
	 * @generated
	 */
	EClass getEObjectToTypeMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToTypeMapEntry()
	 * @generated
	 */
	EReference getEObjectToTypeMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEObjectToTypeMapEntry()
	 * @generated
	 */
	EReference getEObjectToTypeMapEntry_Value();

} // CachePackage

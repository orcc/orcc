/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Cache</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getExpressionsMap <em>Expressions Map</em>}</li>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getIrMap <em>Ir Map</em>}</li>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getTypesMap <em>Types Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacheImpl extends EObjectImpl implements Cache {
	/**
	 * The cached value of the '{@link #getExpressionsMap()
	 * <em>Expressions Map</em>}' map. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getExpressionsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, Expression> expressionsMap;

	/**
	 * The cached value of the '{@link #getIrMap() <em>Ir Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIrMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, EObject> irMap;

	/**
	 * The cached value of the '{@link #getTypesMap() <em>Types Map</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypesMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, Type> typesMap;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CacheImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				if (coreType) return getExpressionsMap();
				else return getExpressionsMap().map();
			case CachePackage.CACHE__IR_MAP:
				if (coreType) return getIrMap();
				else return getIrMap().map();
			case CachePackage.CACHE__TYPES_MAP:
				if (coreType) return getTypesMap();
				else return getTypesMap().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				return ((InternalEList<?>)getExpressionsMap()).basicRemove(otherEnd, msgs);
			case CachePackage.CACHE__IR_MAP:
				return ((InternalEList<?>)getIrMap()).basicRemove(otherEnd, msgs);
			case CachePackage.CACHE__TYPES_MAP:
				return ((InternalEList<?>)getTypesMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				return expressionsMap != null && !expressionsMap.isEmpty();
			case CachePackage.CACHE__IR_MAP:
				return irMap != null && !irMap.isEmpty();
			case CachePackage.CACHE__TYPES_MAP:
				return typesMap != null && !typesMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				((EStructuralFeature.Setting)getExpressionsMap()).set(newValue);
				return;
			case CachePackage.CACHE__IR_MAP:
				((EStructuralFeature.Setting)getIrMap()).set(newValue);
				return;
			case CachePackage.CACHE__TYPES_MAP:
				((EStructuralFeature.Setting)getTypesMap()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CachePackage.Literals.CACHE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				getExpressionsMap().clear();
				return;
			case CachePackage.CACHE__IR_MAP:
				getIrMap().clear();
				return;
			case CachePackage.CACHE__TYPES_MAP:
				getTypesMap().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<EObject, Expression> getExpressionsMap() {
		if (expressionsMap == null) {
			expressionsMap = new EcoreEMap<EObject,Expression>(CachePackage.Literals.EOBJECT_TO_EXPRESSION_MAP_ENTRY, EObjectToExpressionMapEntryImpl.class, this, CachePackage.CACHE__EXPRESSIONS_MAP);
		}
		return expressionsMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<EObject, EObject> getIrMap() {
		if (irMap == null) {
			irMap = new EcoreEMap<EObject,EObject>(CachePackage.Literals.EOBJECT_TO_EOBJECT_MAP_ENTRY, EObjectToEObjectMapEntryImpl.class, this, CachePackage.CACHE__IR_MAP);
		}
		return irMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<EObject, Type> getTypesMap() {
		if (typesMap == null) {
			typesMap = new EcoreEMap<EObject,Type>(CachePackage.Literals.EOBJECT_TO_TYPE_MAP_ENTRY, EObjectToTypeMapEntryImpl.class, this, CachePackage.CACHE__TYPES_MAP);
		}
		return typesMap;
	}

}

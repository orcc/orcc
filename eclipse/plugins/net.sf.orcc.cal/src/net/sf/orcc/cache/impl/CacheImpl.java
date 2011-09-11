/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import java.util.Collection;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Cache</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getExpressionsMap <em>Expressions Map</em>}</li>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getTypesMap <em>Types Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacheImpl extends EObjectImpl implements Cache {
	/**
	 * The cached value of the '{@link #getExpressions() <em>Expressions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExpressions()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> expressions;

	/**
	 * The cached value of the '{@link #getExpressionsMap()
	 * <em>Expressions Map</em>}' map. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getExpressionsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Expression> expressionsMap;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;
	/**
	 * The cached value of the '{@link #getTypesMap() <em>Types Map</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypesMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Type> typesMap;

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
			case CachePackage.CACHE__EXPRESSIONS:
				return getExpressions();
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				if (coreType) return getExpressionsMap();
				else return getExpressionsMap().map();
			case CachePackage.CACHE__TYPES:
				return getTypes();
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
			case CachePackage.CACHE__EXPRESSIONS:
				return ((InternalEList<?>)getExpressions()).basicRemove(otherEnd, msgs);
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				return ((InternalEList<?>)getExpressionsMap()).basicRemove(otherEnd, msgs);
			case CachePackage.CACHE__TYPES:
				return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
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
			case CachePackage.CACHE__EXPRESSIONS:
				return expressions != null && !expressions.isEmpty();
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				return expressionsMap != null && !expressionsMap.isEmpty();
			case CachePackage.CACHE__TYPES:
				return types != null && !types.isEmpty();
			case CachePackage.CACHE__TYPES_MAP:
				return typesMap != null && !typesMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CachePackage.CACHE__EXPRESSIONS:
				getExpressions().clear();
				getExpressions().addAll((Collection<? extends Expression>)newValue);
				return;
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				((EStructuralFeature.Setting)getExpressionsMap()).set(newValue);
				return;
			case CachePackage.CACHE__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>)newValue);
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
			case CachePackage.CACHE__EXPRESSIONS:
				getExpressions().clear();
				return;
			case CachePackage.CACHE__EXPRESSIONS_MAP:
				getExpressionsMap().clear();
				return;
			case CachePackage.CACHE__TYPES:
				getTypes().clear();
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
	public EList<Expression> getExpressions() {
		if (expressions == null) {
			expressions = new EObjectContainmentEList<Expression>(Expression.class, this, CachePackage.CACHE__EXPRESSIONS);
		}
		return expressions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Expression> getExpressionsMap() {
		if (expressionsMap == null) {
			expressionsMap = new EcoreEMap<String,Expression>(CachePackage.Literals.ESTRING_TO_EXPRESSION_MAP_ENTRY, EStringToExpressionMapEntryImpl.class, this, CachePackage.CACHE__EXPRESSIONS_MAP);
		}
		return expressionsMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Type> getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, CachePackage.CACHE__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Type> getTypesMap() {
		if (typesMap == null) {
			typesMap = new EcoreEMap<String,Type>(CachePackage.Literals.ESTRING_TO_TYPE_MAP_ENTRY, EStringToTypeMapEntryImpl.class, this, CachePackage.CACHE__TYPES_MAP);
		}
		return typesMap;
	}

}

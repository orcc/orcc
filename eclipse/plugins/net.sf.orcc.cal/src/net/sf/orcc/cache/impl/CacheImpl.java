/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.cal.type.Typer;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cache</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.cache.impl.CacheImpl#getTypeMap <em>Type Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacheImpl extends EObjectImpl implements Cache {
	/**
	 * The cached value of the '{@link #getTypeMap() <em>Type Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Type> typeMap;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CacheImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CachePackage.Literals.CACHE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Type> getTypeMap() {
		if (typeMap == null) {
			typeMap = new EcoreEMap<String,Type>(CachePackage.Literals.ESTRING_TO_TYPE_MAP_ENTRY, EStringToTypeMapEntryImpl.class, this, CachePackage.CACHE__TYPE_MAP);
		}
		return typeMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CachePackage.CACHE__TYPE_MAP:
				return ((InternalEList<?>)getTypeMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CachePackage.CACHE__TYPE_MAP:
				if (coreType) return getTypeMap();
				else return getTypeMap().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CachePackage.CACHE__TYPE_MAP:
				((EStructuralFeature.Setting)getTypeMap()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CachePackage.CACHE__TYPE_MAP:
				getTypeMap().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CachePackage.CACHE__TYPE_MAP:
				return typeMap != null && !typeMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public Type getType(EObject eObject) {
		URI uri = EcoreUtil.getURI(eObject);
		String fragment = uri.fragment();
		Type type = getTypeMap().get(fragment);
		if (type == null) {
			type = new Typer(null).doSwitch(eObject);
			getTypeMap().put(fragment, type);
		}
		
		return type;
	}

}

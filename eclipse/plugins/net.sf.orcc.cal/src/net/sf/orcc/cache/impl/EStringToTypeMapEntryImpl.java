/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.cache.impl;

import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>EString To Type Map Entry</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.cache.impl.EStringToTypeMapEntryImpl#getTypedKey <em>Key</em>}</li>
 *   <li>{@link net.sf.orcc.cache.impl.EStringToTypeMapEntryImpl#getTypedValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EStringToTypeMapEntryImpl extends EObjectImpl implements
		BasicEMap.Entry<String,Type> {
	/**
	 * The default value of the '{@link #getTypedKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypedKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected int hash = -1;

	/**
	 * The cached value of the '{@link #getTypedKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypedKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTypedValue() <em>Value</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypedValue()
	 * @generated
	 * @ordered
	 */
	protected Type value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EStringToTypeMapEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetTypedValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__KEY:
				return getTypedKey();
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__VALUE:
				if (resolve) return getTypedValue();
				return basicGetTypedValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__VALUE:
				return value != null;
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
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__KEY:
				setTypedKey((String)newValue);
				return;
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__VALUE:
				setTypedValue((Type)newValue);
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
		return CachePackage.Literals.ESTRING_TO_TYPE_MAP_ENTRY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__KEY:
				setTypedKey(KEY_EDEFAULT);
				return;
			case CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__VALUE:
				setTypedValue((Type)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EMap<String, Type> getEMap() {
		EObject container = eContainer();
		return container == null ? null : (EMap<String, Type>)container.eGet(eContainmentFeature());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getHash() {
		if (hash == -1) {
			Object theKey = getKey();
			hash = (theKey == null ? 0 : theKey.hashCode());
		}
		return hash;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return getTypedKey();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypedKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getTypedValue() {
		if (value != null && value.eIsProxy()) {
			InternalEObject oldValue = (InternalEObject)value;
			value = (Type)eResolveProxy(oldValue);
			if (value != oldValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__VALUE, oldValue, value));
			}
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getValue() {
		return getTypedValue();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setHash(int hash) {
		this.hash = hash;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String key) {
		setTypedKey(key);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypedKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypedValue(Type newValue) {
		Type oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CachePackage.ESTRING_TO_TYPE_MAP_ENTRY__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type setValue(Type value) {
		Type oldValue = getValue();
		setTypedValue(value);
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (key: ");
		result.append(key);
		result.append(')');
		return result.toString();
	}

} // EStringToTypeMapEntryImpl

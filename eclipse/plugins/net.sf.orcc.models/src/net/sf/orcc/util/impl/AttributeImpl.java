/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.util.impl;

import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.UtilPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Attribute</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link net.sf.orcc.util.impl.AttributeImpl#getName <em>Name</em>}</li>
 * <li>{@link net.sf.orcc.util.impl.AttributeImpl#getContainedValue <em>
 * Contained Value</em>}</li>
 * <li>{@link net.sf.orcc.util.impl.AttributeImpl#getObjectValue <em>Object
 * Value</em>}</li>
 * <li>{@link net.sf.orcc.util.impl.AttributeImpl#getReferencedValue <em>
 * Referenced Value</em>}</li>
 * <li>{@link net.sf.orcc.util.impl.AttributeImpl#getStringValue <em>String
 * Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class AttributeImpl extends AttributableImpl implements Attribute {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainedValue()
	 * <em>Contained Value</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContainedValue()
	 * @generated
	 * @ordered
	 */
	protected EObject containedValue;

	/**
	 * The default value of the '{@link #getObjectValue() <em>Object Value</em>}
	 * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getObjectValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object OBJECT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectValue() <em>Object Value</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getObjectValue()
	 * @generated
	 * @ordered
	 */
	protected Object objectValue = OBJECT_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferencedValue()
	 * <em>Referenced Value</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getReferencedValue()
	 * @generated
	 * @ordered
	 */
	protected EObject referencedValue;

	/**
	 * The default value of the '{@link #getStringValue() <em>String Value</em>}
	 * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringValue() <em>String Value</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected String stringValue = STRING_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject basicGetReferencedValue() {
		return referencedValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetContainedValue(EObject newContainedValue,
			NotificationChain msgs) {
		EObject oldContainedValue = containedValue;
		containedValue = newContainedValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, UtilPackage.ATTRIBUTE__CONTAINED_VALUE,
					oldContainedValue, newContainedValue);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UtilPackage.ATTRIBUTE__NAME:
			return getName();
		case UtilPackage.ATTRIBUTE__CONTAINED_VALUE:
			return getContainedValue();
		case UtilPackage.ATTRIBUTE__OBJECT_VALUE:
			return getObjectValue();
		case UtilPackage.ATTRIBUTE__REFERENCED_VALUE:
			if (resolve) {
				return getReferencedValue();
			}
			return basicGetReferencedValue();
		case UtilPackage.ATTRIBUTE__STRING_VALUE:
			return getStringValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UtilPackage.ATTRIBUTE__CONTAINED_VALUE:
			return basicSetContainedValue(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UtilPackage.ATTRIBUTE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case UtilPackage.ATTRIBUTE__CONTAINED_VALUE:
			return containedValue != null;
		case UtilPackage.ATTRIBUTE__OBJECT_VALUE:
			return OBJECT_VALUE_EDEFAULT == null ? objectValue != null
					: !OBJECT_VALUE_EDEFAULT.equals(objectValue);
		case UtilPackage.ATTRIBUTE__REFERENCED_VALUE:
			return referencedValue != null;
		case UtilPackage.ATTRIBUTE__STRING_VALUE:
			return STRING_VALUE_EDEFAULT == null ? stringValue != null
					: !STRING_VALUE_EDEFAULT.equals(stringValue);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UtilPackage.ATTRIBUTE__NAME:
			setName((String) newValue);
			return;
		case UtilPackage.ATTRIBUTE__CONTAINED_VALUE:
			setContainedValue((EObject) newValue);
			return;
		case UtilPackage.ATTRIBUTE__OBJECT_VALUE:
			setObjectValue(newValue);
			return;
		case UtilPackage.ATTRIBUTE__REFERENCED_VALUE:
			setReferencedValue((EObject) newValue);
			return;
		case UtilPackage.ATTRIBUTE__STRING_VALUE:
			setStringValue((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UtilPackage.Literals.ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UtilPackage.ATTRIBUTE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case UtilPackage.ATTRIBUTE__CONTAINED_VALUE:
			setContainedValue((EObject) null);
			return;
		case UtilPackage.ATTRIBUTE__OBJECT_VALUE:
			setObjectValue(OBJECT_VALUE_EDEFAULT);
			return;
		case UtilPackage.ATTRIBUTE__REFERENCED_VALUE:
			setReferencedValue((EObject) null);
			return;
		case UtilPackage.ATTRIBUTE__STRING_VALUE:
			setStringValue(STRING_VALUE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject getContainedValue() {
		return containedValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject getReferencedValue() {
		if (referencedValue != null && referencedValue.eIsProxy()) {
			InternalEObject oldReferencedValue = (InternalEObject) referencedValue;
			referencedValue = eResolveProxy(oldReferencedValue);
			if (referencedValue != oldReferencedValue) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UtilPackage.ATTRIBUTE__REFERENCED_VALUE,
							oldReferencedValue, referencedValue));
				}
			}
		}
		return referencedValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setContainedValue(EObject newContainedValue) {
		if (newContainedValue != containedValue) {
			NotificationChain msgs = null;
			if (containedValue != null) {
				msgs = ((InternalEObject) containedValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- UtilPackage.ATTRIBUTE__CONTAINED_VALUE, null,
						msgs);
			}
			if (newContainedValue != null) {
				msgs = ((InternalEObject) newContainedValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- UtilPackage.ATTRIBUTE__CONTAINED_VALUE, null,
						msgs);
			}
			msgs = basicSetContainedValue(newContainedValue, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					UtilPackage.ATTRIBUTE__CONTAINED_VALUE, newContainedValue,
					newContainedValue));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getObjectValue() {
		return objectValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setObjectValue(Object newObjectValue) {
		Object oldObjectValue = objectValue;
		objectValue = newObjectValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					UtilPackage.ATTRIBUTE__OBJECT_VALUE, oldObjectValue,
					objectValue));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					UtilPackage.ATTRIBUTE__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setReferencedValue(EObject newReferencedValue) {
		EObject oldReferencedValue = referencedValue;
		referencedValue = newReferencedValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					UtilPackage.ATTRIBUTE__REFERENCED_VALUE,
					oldReferencedValue, referencedValue));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStringValue(String newStringValue) {
		String oldStringValue = stringValue;
		stringValue = newStringValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					UtilPackage.ATTRIBUTE__STRING_VALUE, oldStringValue,
					stringValue));
		}
	}

	@Override
	public void setEObjectValue(EObject value) {
		if (value != null && value.eContainer() == null) {
			// if value is not contained, add it to the contained value
			setContainedValue(value);
		}
		setReferencedValue(value);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", objectValue: ");
		result.append(objectValue);
		result.append(", stringValue: ");
		result.append(stringValue);
		result.append(')');
		return result.toString();
	}

}

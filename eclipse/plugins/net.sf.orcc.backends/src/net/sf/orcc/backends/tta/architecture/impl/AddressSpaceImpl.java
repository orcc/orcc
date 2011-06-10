/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture.impl;

import net.sf.orcc.backends.tta.architecture.AddressSpace;
import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Address Space</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.AddressSpaceImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.AddressSpaceImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.AddressSpaceImpl#getMinAddress <em>Min Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.AddressSpaceImpl#getMaxAddress <em>Max Address</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AddressSpaceImpl extends EObjectImpl implements AddressSpace {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinAddress() <em>Min Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinAddress()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_ADDRESS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinAddress() <em>Min Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinAddress()
	 * @generated
	 * @ordered
	 */
	protected int minAddress = MIN_ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxAddress() <em>Max Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxAddress()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_ADDRESS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxAddress() <em>Max Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxAddress()
	 * @generated
	 * @ordered
	 */
	protected int maxAddress = MAX_ADDRESS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AddressSpaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.ADDRESS_SPACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ADDRESS_SPACE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ADDRESS_SPACE__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinAddress() {
		return minAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinAddress(int newMinAddress) {
		int oldMinAddress = minAddress;
		minAddress = newMinAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ADDRESS_SPACE__MIN_ADDRESS, oldMinAddress, minAddress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxAddress() {
		return maxAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxAddress(int newMaxAddress) {
		int oldMaxAddress = maxAddress;
		maxAddress = newMaxAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ADDRESS_SPACE__MAX_ADDRESS, oldMaxAddress, maxAddress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchitecturePackage.ADDRESS_SPACE__NAME:
				return getName();
			case ArchitecturePackage.ADDRESS_SPACE__WIDTH:
				return getWidth();
			case ArchitecturePackage.ADDRESS_SPACE__MIN_ADDRESS:
				return getMinAddress();
			case ArchitecturePackage.ADDRESS_SPACE__MAX_ADDRESS:
				return getMaxAddress();
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
			case ArchitecturePackage.ADDRESS_SPACE__NAME:
				setName((String)newValue);
				return;
			case ArchitecturePackage.ADDRESS_SPACE__WIDTH:
				setWidth((Integer)newValue);
				return;
			case ArchitecturePackage.ADDRESS_SPACE__MIN_ADDRESS:
				setMinAddress((Integer)newValue);
				return;
			case ArchitecturePackage.ADDRESS_SPACE__MAX_ADDRESS:
				setMaxAddress((Integer)newValue);
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
			case ArchitecturePackage.ADDRESS_SPACE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ArchitecturePackage.ADDRESS_SPACE__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case ArchitecturePackage.ADDRESS_SPACE__MIN_ADDRESS:
				setMinAddress(MIN_ADDRESS_EDEFAULT);
				return;
			case ArchitecturePackage.ADDRESS_SPACE__MAX_ADDRESS:
				setMaxAddress(MAX_ADDRESS_EDEFAULT);
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
			case ArchitecturePackage.ADDRESS_SPACE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ArchitecturePackage.ADDRESS_SPACE__WIDTH:
				return width != WIDTH_EDEFAULT;
			case ArchitecturePackage.ADDRESS_SPACE__MIN_ADDRESS:
				return minAddress != MIN_ADDRESS_EDEFAULT;
			case ArchitecturePackage.ADDRESS_SPACE__MAX_ADDRESS:
				return maxAddress != MAX_ADDRESS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", width: ");
		result.append(width);
		result.append(", minAddress: ");
		result.append(minAddress);
		result.append(", maxAddress: ");
		result.append(maxAddress);
		result.append(')');
		return result.toString();
	}

} //AddressSpaceImpl

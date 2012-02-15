/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.WrapperXml;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wrapper Xml</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.WrapperXmlImpl#getXml <em>Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WrapperXmlImpl extends EObjectImpl implements WrapperXml {
	/**
	 * The default value of the '{@link #getXml() <em>Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXml()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXml() <em>Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXml()
	 * @generated
	 * @ordered
	 */
	protected String xml = XML_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WrapperXmlImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.WRAPPER_XML;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXml(String newXml) {
		String oldXml = xml;
		xml = newXml;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.WRAPPER_XML__XML, oldXml, xml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.WRAPPER_XML__XML:
			return getXml();
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
		case DfPackage.WRAPPER_XML__XML:
			setXml((String) newValue);
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
		case DfPackage.WRAPPER_XML__XML:
			setXml(XML_EDEFAULT);
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
		case DfPackage.WRAPPER_XML__XML:
			return XML_EDEFAULT == null ? xml != null : !XML_EDEFAULT
					.equals(xml);
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (xml: ");
		result.append(xml);
		result.append(')');
		return result.toString();
	}

} //WrapperXmlImpl

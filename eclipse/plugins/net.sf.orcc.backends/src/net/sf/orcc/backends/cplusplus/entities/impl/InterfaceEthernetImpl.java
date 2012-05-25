/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities.impl;

import net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet;
import net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interface Ethernet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl#getIp <em>Ip</em>}</li>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl#getPortNumber <em>Port Number</em>}</li>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl#isServer <em>Server</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InterfaceEthernetImpl extends InterfaceImpl implements InterfaceEthernet {
	/**
	 * The default value of the '{@link #getIp() <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIp()
	 * @generated
	 * @ordered
	 */
	protected static final String IP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIp() <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIp()
	 * @generated
	 * @ordered
	 */
	protected String ip = IP_EDEFAULT;

	/**
	 * The default value of the '{@link #getPortNumber() <em>Port Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int PORT_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPortNumber() <em>Port Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortNumber()
	 * @generated
	 * @ordered
	 */
	protected int portNumber = PORT_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #isServer() <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isServer()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SERVER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isServer() <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isServer()
	 * @generated
	 * @ordered
	 */
	protected boolean server = SERVER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InterfaceEthernetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return YaceEntitiesPackage.Literals.INTERFACE_ETHERNET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIp(String newIp) {
		String oldIp = ip;
		ip = newIp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.INTERFACE_ETHERNET__IP, oldIp, ip));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortNumber(int newPortNumber) {
		int oldPortNumber = portNumber;
		portNumber = newPortNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.INTERFACE_ETHERNET__PORT_NUMBER, oldPortNumber, portNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isServer() {
		return server;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServer(boolean newServer) {
		boolean oldServer = server;
		server = newServer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, YaceEntitiesPackage.INTERFACE_ETHERNET__SERVER, oldServer, server));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case YaceEntitiesPackage.INTERFACE_ETHERNET__IP:
				return getIp();
			case YaceEntitiesPackage.INTERFACE_ETHERNET__PORT_NUMBER:
				return getPortNumber();
			case YaceEntitiesPackage.INTERFACE_ETHERNET__SERVER:
				return isServer();
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
			case YaceEntitiesPackage.INTERFACE_ETHERNET__IP:
				setIp((String)newValue);
				return;
			case YaceEntitiesPackage.INTERFACE_ETHERNET__PORT_NUMBER:
				setPortNumber((Integer)newValue);
				return;
			case YaceEntitiesPackage.INTERFACE_ETHERNET__SERVER:
				setServer((Boolean)newValue);
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
			case YaceEntitiesPackage.INTERFACE_ETHERNET__IP:
				setIp(IP_EDEFAULT);
				return;
			case YaceEntitiesPackage.INTERFACE_ETHERNET__PORT_NUMBER:
				setPortNumber(PORT_NUMBER_EDEFAULT);
				return;
			case YaceEntitiesPackage.INTERFACE_ETHERNET__SERVER:
				setServer(SERVER_EDEFAULT);
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
			case YaceEntitiesPackage.INTERFACE_ETHERNET__IP:
				return IP_EDEFAULT == null ? ip != null : !IP_EDEFAULT.equals(ip);
			case YaceEntitiesPackage.INTERFACE_ETHERNET__PORT_NUMBER:
				return portNumber != PORT_NUMBER_EDEFAULT;
			case YaceEntitiesPackage.INTERFACE_ETHERNET__SERVER:
				return server != SERVER_EDEFAULT;
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
		result.append(" (ip: ");
		result.append(ip);
		result.append(", portNumber: ");
		result.append(portNumber);
		result.append(", server: ");
		result.append(server);
		result.append(')');
		return result.toString();
	}

} //InterfaceEthernetImpl

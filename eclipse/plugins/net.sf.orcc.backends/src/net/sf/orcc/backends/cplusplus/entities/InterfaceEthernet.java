/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface Ethernet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getIp <em>Ip</em>}</li>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getPortNumber <em>Port Number</em>}</li>
 *   <li>{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#isServer <em>Server</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getInterfaceEthernet()
 * @model
 * @generated
 */
public interface InterfaceEthernet extends Interface {
	/**
	 * Returns the value of the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ip</em>' attribute.
	 * @see #setIp(String)
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getInterfaceEthernet_Ip()
	 * @model
	 * @generated
	 */
	String getIp();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getIp <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ip</em>' attribute.
	 * @see #getIp()
	 * @generated
	 */
	void setIp(String value);

	/**
	 * Returns the value of the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port Number</em>' attribute.
	 * @see #setPortNumber(long)
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getInterfaceEthernet_PortNumber()
	 * @model
	 * @generated
	 */
	long getPortNumber();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getPortNumber <em>Port Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port Number</em>' attribute.
	 * @see #getPortNumber()
	 * @generated
	 */
	void setPortNumber(long value);

	/**
	 * Returns the value of the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Server</em>' attribute.
	 * @see #setServer(boolean)
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#getInterfaceEthernet_Server()
	 * @model
	 * @generated
	 */
	boolean isServer();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#isServer <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server</em>' attribute.
	 * @see #isServer()
	 * @generated
	 */
	void setServer(boolean value);

} // InterfaceEthernet

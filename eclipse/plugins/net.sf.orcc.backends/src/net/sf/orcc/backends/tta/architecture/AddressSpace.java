/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Address Space</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getMinAddress <em>Min Address</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getMaxAddress <em>Max Address</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getAddressSpace()
 * @model
 * @generated
 */
public interface AddressSpace extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getAddressSpace_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getAddressSpace_Width()
	 * @model
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Returns the value of the '<em><b>Min Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Address</em>' attribute.
	 * @see #setMinAddress(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getAddressSpace_MinAddress()
	 * @model
	 * @generated
	 */
	int getMinAddress();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getMinAddress <em>Min Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Address</em>' attribute.
	 * @see #getMinAddress()
	 * @generated
	 */
	void setMinAddress(int value);

	/**
	 * Returns the value of the '<em><b>Max Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Address</em>' attribute.
	 * @see #setMaxAddress(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getAddressSpace_MaxAddress()
	 * @model
	 * @generated
	 */
	int getMaxAddress();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.AddressSpace#getMaxAddress <em>Max Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Address</em>' attribute.
	 * @see #getMaxAddress()
	 * @generated
	 */
	void setMaxAddress(int value);

} // AddressSpace

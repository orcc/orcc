/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.util;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Attribute</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.util.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.util.Attribute#getContainedValue <em>Contained Value</em>}</li>
 *   <li>{@link net.sf.orcc.util.Attribute#getObjectValue <em>Object Value</em>}</li>
 *   <li>{@link net.sf.orcc.util.Attribute#getReferencedValue <em>Referenced Value</em>}</li>
 *   <li>{@link net.sf.orcc.util.Attribute#getStringValue <em>String Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.util.UtilPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {

	/**
	 * Returns the value of the '<em><b>Contained Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->Returns the value contained in this attribute. A value may be associated
	 * with this attribute but not necessarily contained in it, see
	 * {@link #getReferencedValue()} for that.<!-- end-user-doc -->
	 * @return the value of the '<em>Contained Value</em>' containment reference.
	 * @see #setContainedValue(EObject)
	 * @see net.sf.orcc.util.UtilPackage#getAttribute_ContainedValue()
	 * @model containment="true"
	 * @generated
	 */
	EObject getContainedValue();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.util.UtilPackage#getAttribute_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Value</em>' attribute.
	 * @see #setObjectValue(Object)
	 * @see net.sf.orcc.util.UtilPackage#getAttribute_ObjectValue()
	 * @model
	 * @generated
	 */
	Object getObjectValue();

	/**
	 * Returns the value of the '<em><b>Referenced Value</b></em>' reference.
	 * <!-- begin-user-doc -->Returns the value referenced by this attribute. This value may or may not
	 * return the same value as {@link #getContainedValue()}.<!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Value</em>' reference.
	 * @see #setReferencedValue(EObject)
	 * @see net.sf.orcc.util.UtilPackage#getAttribute_ReferencedValue()
	 * @model
	 * @generated
	 */
	EObject getReferencedValue();

	/**
	 * Returns the value of the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Value</em>' attribute.
	 * @see #setStringValue(String)
	 * @see net.sf.orcc.util.UtilPackage#getAttribute_StringValue()
	 * @model
	 * @generated
	 */
	String getStringValue();

	/**
	 * Sets the value of the '{@link net.sf.orcc.util.Attribute#getContainedValue <em>Contained Value</em>}' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Contained Value</em>' containment reference.
	 * @see #getContainedValue()
	 * @generated
	 */
	void setContainedValue(EObject value);

	/**
	 * Sets the new value of this attribute. If the given value has no
	 * container, this attribute becomes its new container.
	 * 
	 * @param value
	 *            the new EObject value of this attribute
	 */
	void setEObjectValue(EObject value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.util.Attribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.util.Attribute#getObjectValue <em>Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Value</em>' attribute.
	 * @see #getObjectValue()
	 * @generated
	 */
	void setObjectValue(Object value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.util.Attribute#getReferencedValue <em>Referenced Value</em>}' reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Value</em>' reference.
	 * @see #getReferencedValue()
	 * @generated
	 */
	void setReferencedValue(EObject value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.util.Attribute#getStringValue <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Value</em>' attribute.
	 * @see #getStringValue()
	 * @generated
	 */
	void setStringValue(String value);

}

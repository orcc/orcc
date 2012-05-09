/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.util;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Attribute</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.util.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getContainedValue <em>Contained Value</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getPojoValue <em>Pojo Value</em>}</li>
 *   <li>{@link net.sf.dftools.util.Attribute#getReferencedValue <em>Referenced Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.util.UtilPackage#getAttribute()
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
	 * @see net.sf.dftools.util.UtilPackage#getAttribute_ContainedValue()
	 * @model containment="true"
	 * @generated
	 */
	EObject getContainedValue();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.dftools.util.UtilPackage#getAttribute_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Pojo Value</b></em>' attribute.
	 * <!-- begin-user-doc -->Returns the POJO value contained in this attribute. Note: if this
	 * attribute is serialized, this value will be serialized as well.<!-- end-user-doc -->
	 * @return the value of the '<em>Pojo Value</em>' attribute.
	 * @see #setPojoValue(Object)
	 * @see net.sf.dftools.util.UtilPackage#getAttribute_PojoValue()
	 * @model
	 * @generated
	 */
	Object getPojoValue();

	/**
	 * Returns the value of the '<em><b>Referenced Value</b></em>' reference.
	 * <!-- begin-user-doc -->Returns the value referenced by this attribute. This value may or may not
	 * return the same value as {@link #getContainedValue()}.<!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Value</em>' reference.
	 * @see #setReferencedValue(EObject)
	 * @see net.sf.dftools.util.UtilPackage#getAttribute_ReferencedValue()
	 * @model
	 * @generated
	 */
	EObject getReferencedValue();

	/**
	 * Returns the value contained in this attribute, looking in the following
	 * order: 1) POJO, 2) referenced 3) contained.
	 * 
	 * @return the value
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link net.sf.dftools.util.Attribute#getContainedValue <em>Contained Value</em>}' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Contained Value</em>' containment reference.
	 * @see #getContainedValue()
	 * @generated
	 */
	void setContainedValue(EObject value);

	/**
	 * Sets the value of the '{@link net.sf.dftools.util.Attribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Sets the value of the '{@link net.sf.dftools.util.Attribute#getPojoValue <em>Pojo Value</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Pojo Value</em>' attribute.
	 * @see #getPojoValue()
	 * @generated
	 */
	void setPojoValue(Object value);

	/**
	 * Sets the value of the '{@link net.sf.dftools.util.Attribute#getReferencedValue <em>Referenced Value</em>}' reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Value</em>' reference.
	 * @see #getReferencedValue()
	 * @generated
	 */
	void setReferencedValue(EObject value);

	/**
	 * Sets the new value of this attribute. If the given value has no
	 * container, this attribute becomes its new container.
	 * 
	 * @param value
	 *            the new EObject value of this attribute
	 */
	void setValue(EObject value);

}

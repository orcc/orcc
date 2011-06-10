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
 * A representation of the model object '<em><b>Short Immediate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ShortImmediate#getExtension <em>Extension</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.ShortImmediate#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getShortImmediate()
 * @model
 * @generated
 */
public interface ShortImmediate extends EObject {
	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.tta.architecture.Extension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.Extension
	 * @see #setExtension(Extension)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getShortImmediate_Extension()
	 * @model
	 * @generated
	 */
	Extension getExtension();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ShortImmediate#getExtension <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension</em>' attribute.
	 * @see net.sf.orcc.backends.tta.architecture.Extension
	 * @see #getExtension()
	 * @generated
	 */
	void setExtension(Extension value);

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
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getShortImmediate_Width()
	 * @model
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.ShortImmediate#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

} // ShortImmediate

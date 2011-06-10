/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.FunctionUnit#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.FunctionUnit#getOperations <em>Operations</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.FunctionUnit#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.FunctionUnit#getAddressSpace <em>Address Space</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getFunctionUnit()
 * @model
 * @generated
 */
public interface FunctionUnit extends EObject {
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
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getFunctionUnit_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.FunctionUnit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Operation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getFunctionUnit_Operations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operation> getOperations();

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getFunctionUnit_Ports()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Address Space</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address Space</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address Space</em>' reference.
	 * @see #setAddressSpace(AddressSpace)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getFunctionUnit_AddressSpace()
	 * @model
	 * @generated
	 */
	AddressSpace getAddressSpace();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.FunctionUnit#getAddressSpace <em>Address Space</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Address Space</em>' reference.
	 * @see #getAddressSpace()
	 * @generated
	 */
	void setAddressSpace(AddressSpace value);

} // FunctionUnit

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
 * A representation of the model object '<em><b>Register File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getSize <em>Size</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getWidth <em>Width</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxReads <em>Max Reads</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxWrites <em>Max Writes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getPorts <em>Ports</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile()
 * @model
 * @generated
 */
public interface RegisterFile extends EObject {
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
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile_Size()
	 * @model
	 * @generated
	 */
	int getSize();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(int value);

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
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile_Width()
	 * @model
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Returns the value of the '<em><b>Max Reads</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Reads</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Reads</em>' attribute.
	 * @see #setMaxReads(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile_MaxReads()
	 * @model
	 * @generated
	 */
	int getMaxReads();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxReads <em>Max Reads</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Reads</em>' attribute.
	 * @see #getMaxReads()
	 * @generated
	 */
	void setMaxReads(int value);

	/**
	 * Returns the value of the '<em><b>Max Writes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Writes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Writes</em>' attribute.
	 * @see #setMaxWrites(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile_MaxWrites()
	 * @model
	 * @generated
	 */
	int getMaxWrites();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxWrites <em>Max Writes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Writes</em>' attribute.
	 * @see #getMaxWrites()
	 * @generated
	 */
	void setMaxWrites(int value);

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
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getRegisterFile_Ports()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Port> getPorts();

} // RegisterFile

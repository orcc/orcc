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
 * A representation of the model object '<em><b>TTA</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getBuses <em>Buses</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getSockets <em>Sockets</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getFunctionalUnits <em>Functional Units</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getRegisterFiles <em>Register Files</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getProgram <em>Program</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.TTA#getData <em>Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA()
 * @model
 * @generated
 */
public interface TTA extends EObject {
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
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TTA#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Buses</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Bus}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Buses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buses</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_Buses()
	 * @model containment="true"
	 * @generated
	 */
	EList<Bus> getBuses();

	/**
	 * Returns the value of the '<em><b>Sockets</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.Socket}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sockets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sockets</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_Sockets()
	 * @model containment="true"
	 * @generated
	 */
	EList<Socket> getSockets();

	/**
	 * Returns the value of the '<em><b>Functional Units</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.FunctionalUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functional Units</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functional Units</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_FunctionalUnits()
	 * @model containment="true"
	 * @generated
	 */
	EList<FunctionalUnit> getFunctionalUnits();

	/**
	 * Returns the value of the '<em><b>Register Files</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.backends.tta.architecture.RegisterFile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Register Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Register Files</em>' containment reference list.
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_RegisterFiles()
	 * @model containment="true"
	 * @generated
	 */
	EList<RegisterFile> getRegisterFiles();

	/**
	 * Returns the value of the '<em><b>Program</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Program</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Program</em>' containment reference.
	 * @see #setProgram(AdressSpace)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_Program()
	 * @model containment="true"
	 * @generated
	 */
	AdressSpace getProgram();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TTA#getProgram <em>Program</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Program</em>' containment reference.
	 * @see #getProgram()
	 * @generated
	 */
	void setProgram(AdressSpace value);

	/**
	 * Returns the value of the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' containment reference.
	 * @see #setData(AdressSpace)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getTTA_Data()
	 * @model containment="true"
	 * @generated
	 */
	AdressSpace getData();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.TTA#getData <em>Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data</em>' containment reference.
	 * @see #getData()
	 * @generated
	 */
	void setData(AdressSpace value);

} // TTA

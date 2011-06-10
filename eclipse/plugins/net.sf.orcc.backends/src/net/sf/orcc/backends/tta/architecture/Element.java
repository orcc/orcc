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
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Element#getStartCycle <em>Start Cycle</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.Element#getCycles <em>Cycles</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Element extends EObject {
	/**
	 * Returns the value of the '<em><b>Start Cycle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Cycle</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Cycle</em>' attribute.
	 * @see #setStartCycle(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getElement_StartCycle()
	 * @model
	 * @generated
	 */
	int getStartCycle();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.Element#getStartCycle <em>Start Cycle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Cycle</em>' attribute.
	 * @see #getStartCycle()
	 * @generated
	 */
	void setStartCycle(int value);

	/**
	 * Returns the value of the '<em><b>Cycles</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cycles</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cycles</em>' attribute.
	 * @see #setCycles(int)
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getElement_Cycles()
	 * @model required="true"
	 * @generated
	 */
	int getCycles();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.tta.architecture.Element#getCycles <em>Cycles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cycles</em>' attribute.
	 * @see #getCycles()
	 * @generated
	 */
	void setCycles(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isReads();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isWrites();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isResource();

} // Element

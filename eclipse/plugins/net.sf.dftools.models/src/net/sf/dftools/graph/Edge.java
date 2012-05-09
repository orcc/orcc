/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph;

import net.sf.dftools.util.Attributable;

/**
 * <!-- begin-user-doc -->This class defines an edge. An edge has a source vertex and a target vertex,
 * as well as a list of attributes.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.dftools.graph.Edge#getLabel <em>Label</em>}</li>
 *   <li>{@link net.sf.dftools.graph.Edge#getSource <em>Source</em>}</li>
 *   <li>{@link net.sf.dftools.graph.Edge#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.dftools.graph.GraphPackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends Attributable {

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see net.sf.dftools.graph.GraphPackage#getEdge_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link net.sf.dftools.graph.Vertex#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Vertex)
	 * @see net.sf.dftools.graph.GraphPackage#getEdge_Source()
	 * @see net.sf.dftools.graph.Vertex#getOutgoing
	 * @model opposite="outgoing"
	 * @generated
	 */
	Vertex getSource();

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link net.sf.dftools.graph.Vertex#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Vertex)
	 * @see net.sf.dftools.graph.GraphPackage#getEdge_Target()
	 * @see net.sf.dftools.graph.Vertex#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	Vertex getTarget();

	/**
	 * Sets the value of the '{@link net.sf.dftools.graph.Edge#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Sets the value of the '{@link net.sf.dftools.graph.Edge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Vertex value);

	/**
	 * Sets the value of the '{@link net.sf.dftools.graph.Edge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Vertex value);

}

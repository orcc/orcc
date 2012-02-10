/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df;

import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Vertex;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Element</b></em>'. <!-- end-user-doc -->
 * 
 * 
 * @see net.sf.orcc.df.DfPackage#getElement()
 * @model abstract="true"
 * @generated
 */
public interface Element extends Vertex {

	List<Entity> getHierarchy();

	String getHierarchicalName();

	List<String> getHierarchicalId();

	Object getTemplateData();

	boolean isEntity();

	boolean isInstance();

	boolean isPort();

	void setTemplateData(Object templateData);

	Map<Port, List<Connection>> getOutgoingPortMap();

	Map<Port, Connection> getIncomingPortMap();
} // Element

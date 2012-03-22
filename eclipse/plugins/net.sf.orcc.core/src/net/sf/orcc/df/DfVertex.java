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
 * <em><b>Vertex</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.DfVertex#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getDfVertex()
 * @model abstract="true"
 * @generated
 */
public interface DfVertex extends Vertex {

	List<String> getHierarchicalId();

	String getHierarchicalName();

	List<Entity> getHierarchy();

	Map<Port, Connection> getIncomingPortMap();

	/**
	 * Returns the qualified name of this vertex.
	 * 
	 * @return the qualified name of this vertex
	 * @model
	 */
	String getName();

	/**
	 * Sets the qualified name of this vertex.
	 * 
	 * @param name
	 *            the qualified name of this vertex
	 */
	void setName(String name);

	Map<Port, List<Connection>> getOutgoingPortMap();

	String getPackage();

	String getSimpleName();

	Object getTemplateData();

	boolean isEntity();

	boolean isInstance();

	void setTemplateData(Object templateData);

}

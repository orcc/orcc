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
import net.sf.dftools.util.Nameable;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Vertex</b></em>'. <!-- end-user-doc -->
 *
 *
 * @see net.sf.orcc.df.DfPackage#getDfVertex()
 * @model abstract="true"
 * @generated
 */
public interface DfVertex extends Vertex, Nameable {

	List<String> getHierarchicalId();

	String getHierarchicalName();

	List<Entity> getHierarchy();

	Map<Port, Connection> getIncomingPortMap();

	Map<Port, List<Connection>> getOutgoingPortMap();

	Object getTemplateData();

	boolean isEntity();

	boolean isInstance();

	void setTemplateData(Object templateData);

}

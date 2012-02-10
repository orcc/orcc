/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.DfVertex;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Port;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class DfVertexImpl extends VertexImpl implements DfVertex {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DfVertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.DF_VERTEX;
	}
	
	/**
	 * holds template-specific data.
	 */
	private Object templateData;

	
	@Override
	public Object getTemplateData() {
		return templateData;
	}

	@Override
	public boolean isEntity() {
		return false;
	}

	@Override
	public boolean isInstance() {
		return false;
	}

	@Override
	public boolean isPort() {
		return false;
	}
	
	@Override
	public Map<Port, Connection> getIncomingPortMap() {
		Map<Port, Connection> map = new HashMap<Port, Connection>();
		for (Edge edge : getIncoming()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				map.put(connection.getTargetPort(), connection);
			}
		}
		return map;
	}
	
	@Override
	public Map<Port, List<Connection>> getOutgoingPortMap() {
		Map<Port, List<Connection>> map = new HashMap<Port, List<Connection>>();
		for (Edge edge : getOutgoing()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				Port source = connection.getSourcePort();
				List<Connection> conns = map.get(source);
				if (conns == null) {
					conns = new ArrayList<Connection>(1);
					map.put(source, conns);
				}
				conns.add(connection);
			}
		}
		return map;
	}

	@Override
	public void setTemplateData(Object templateData) {
		this.templateData = templateData;
	}
	
	@Override
	public List<String> getHierarchicalId() {
		List<String> ids = new ArrayList<String>();
		for (Entity entity : getHierarchy()) {
			ids.add(entity.getName());
		}
		ids.add(getName());
		return ids;
	}

	@Override
	public String getHierarchicalName() {
		StringBuilder builder = new StringBuilder();
		for (Entity entity : getHierarchy()) {
			builder.append(entity.getName());
			builder.append('_');
		}
		builder.append(getName());
		return builder.toString();
	}

	@Override
	public List<Entity> getHierarchy() {
		List<Entity> entities = new ArrayList<Entity>();
		EObject obj = eContainer();
		while (obj != null) {
			entities.add(0, (Entity) obj);
			obj = obj.eContainer();
		}
		return entities;
	}

} //DfVertexImpl

/**
 * <copyright>
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * </copyright>
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.impl.AttributableImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Entity</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getIncomingPortMap <em>Incoming Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getOutgoingPortMap <em>Outgoing Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntityImpl extends AttributableImpl implements Entity {

	/**
	 * This class clears the value of incomingPortMap or outgoingPortMap when
	 * the list of incoming or outgoing connections of the "vertex" field
	 * changes.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConnectionListAdapter extends AdapterImpl {

		@Override
		public void notifyChanged(Notification msg) {
			Object feature = msg.getFeature();
			if (incomingPortMap != null) {
				if (feature == GraphPackage.Literals.VERTEX__INCOMING) {
					incomingPortMap = null;
				}
			}

			if (outgoingPortMap != null) {
				if (feature == GraphPackage.Literals.VERTEX__OUTGOING) {
					outgoingPortMap = null;
				}
			}
		}

	}

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncomingPortMap() <em>Incoming Port Map</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getIncomingPortMap()
	 * @generated
	 * @ordered
	 */
	protected Map<Port, Connection> incomingPortMap;

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputs;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoingPortMap() <em>Outgoing Port Map</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getOutgoingPortMap()
	 * @generated
	 * @ordered
	 */
	protected Map<Port, List<Connection>> outgoingPortMap;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputs;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> parameters;

	private Vertex vertex;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->Creates a new Entity with the given name, input
	 * ports, output ports, parameters. This constructor should only be used to
	 * build a temporary entity, or an entity that will NOT be used to compute
	 * incoming/outgoing port map (see the other constructor).<!-- end-user-doc
	 * -->
	 * 
	 * @param name
	 *            name of the entity
	 * @param inputs
	 *            a list of input ports
	 * @param outputs
	 *            a list of output ports
	 * @param parameters
	 *            a list of parameters
	 */
	public EntityImpl(String name, EList<Port> inputs, EList<Port> outputs,
			EList<Var> parameters) {
		super();

		this.name = name;
		this.inputs = inputs;
		this.outputs = outputs;
		this.parameters = parameters;
	}

	/**
	 * <!-- begin-user-doc -->Creates a new Entity on the given vertex, with the
	 * given input ports, output ports, parameters. This constructor can be used
	 * to create an entity that will be used to compute the incoming/outgoing
	 * port map.<!-- end-user-doc -->
	 * 
	 * @param name
	 *            name of the entity
	 * @param inputs
	 *            a list of input ports
	 * @param outputs
	 *            a list of output ports
	 * @param parameters
	 *            a list of parameters
	 */
	public EntityImpl(Vertex vertex, EList<Attribute> attributes,
			EList<Port> inputs, EList<Port> outputs, EList<Var> parameters) {
		super();

		this.vertex = vertex;
		this.attributes = attributes;
		this.inputs = inputs;
		this.outputs = outputs;
		this.parameters = parameters;

		vertex.eAdapters().add(new ConnectionListAdapter());
	}

	/**
	 * <!-- begin-user-doc -->Creates a new Entity on the given vertex, with the
	 * inputs, outputs, parameters of the given entity. This constructor MUST be
	 * used when wrapping another entity so that it can be used to compute the
	 * incoming/outgoing port map.<!-- end-user-doc -->
	 * 
	 * @param vertex
	 *            the vertex to use when computing incoming/outgoing port map
	 * @param entity
	 *            the entity that the new entity will be based on
	 */
	protected EntityImpl(Vertex vertex, Entity entity) {
		this(vertex, entity.getAttributes(), entity.getInputs(), entity
				.getOutputs(), entity.getParameters());
	}

	/**
	 * Computes the incoming port map of vertex.
	 */
	private void computeIncomingPortMap() {
		incomingPortMap = new HashMap<Port, Connection>();
		if (vertex == null) {
			throw new IllegalArgumentException("cannot compute incoming port "
					+ "map on an entity that is not associated with a vertex");
		}

		for (Edge edge : vertex.getIncoming()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				incomingPortMap.put(connection.getTargetPort(), connection);
			}
		}
	}

	/**
	 * Computes the outgoing port map of vertex.
	 */
	private void computeOutgoingPortMap() {
		outgoingPortMap = new HashMap<Port, List<Connection>>();
		if (vertex == null) {
			throw new IllegalArgumentException("cannot compute outgoing port "
					+ "map on an entity that is not associated with a vertex");
		}

		for (Edge edge : vertex.getOutgoing()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				Port source = connection.getSourcePort();
				List<Connection> conns = outgoingPortMap.get(source);
				if (conns == null) {
					conns = new ArrayList<Connection>(1);
					outgoingPortMap.put(source, conns);
				}
				conns.add(connection);
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.ENTITY__INCOMING_PORT_MAP:
			return getIncomingPortMap();
		case DfPackage.ENTITY__INPUTS:
			return getInputs();
		case DfPackage.ENTITY__NAME:
			return getName();
		case DfPackage.ENTITY__OUTGOING_PORT_MAP:
			return getOutgoingPortMap();
		case DfPackage.ENTITY__OUTPUTS:
			return getOutputs();
		case DfPackage.ENTITY__PARAMETERS:
			return getParameters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DfPackage.ENTITY__INCOMING_PORT_MAP:
			return incomingPortMap != null;
		case DfPackage.ENTITY__INPUTS:
			return inputs != null && !inputs.isEmpty();
		case DfPackage.ENTITY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case DfPackage.ENTITY__OUTGOING_PORT_MAP:
			return outgoingPortMap != null;
		case DfPackage.ENTITY__OUTPUTS:
			return outputs != null && !outputs.isEmpty();
		case DfPackage.ENTITY__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.ENTITY;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Class<T> type) {
		if (type.isAssignableFrom(getClass())) {
			return (T) this;
		}

		// by default an entity cannot be adapted to anything else
		// subclasses should extend this method
		return null;
	}

	@Override
	public Map<Port, Connection> getIncomingPortMap() {
		if (incomingPortMap == null) {
			computeIncomingPortMap();
		}
		return incomingPortMap;
	}

	@Override
	public Port getInput(String name) {
		for (Port port : getInputs()) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getInputs() {
		if (inputs == null) {
			inputs = new EObjectResolvingEList<Port>(Port.class, this,
					DfPackage.ENTITY__INPUTS);
		}
		return inputs;
	}

	@Override
	public String getName() {
		if (vertex == null) {
			// if vertex is null, name is valid
			return name;
		} else {
			// otherwise the name is given by the vertex
			return vertex.getLabel();
		}
	}

	@Override
	public Map<Port, List<Connection>> getOutgoingPortMap() {
		if (outgoingPortMap == null) {
			computeOutgoingPortMap();
		}
		return outgoingPortMap;
	}

	@Override
	public Port getOutput(String name) {
		for (Port port : getOutputs()) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectResolvingEList<Port>(Port.class, this,
					DfPackage.ENTITY__OUTPUTS);
		}
		return outputs;
	}

	@Override
	public Var getParameter(String name) {
		for (Var var : getParameters()) {
			if (var.getName().equals(name)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getParameters() {
		if (parameters == null) {
			parameters = new EObjectResolvingEList<Var>(Var.class, this,
					DfPackage.ENTITY__PARAMETERS);
		}
		return parameters;
	}

	@Override
	public String getSimpleName() {
		return DfUtil.getSimpleName(getName());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (incomingPortMap: ");
		result.append(incomingPortMap);
		result.append(", name: ");
		result.append(name);
		result.append(", outgoingPortMap: ");
		result.append(outgoingPortMap);
		result.append(')');
		return result.toString();
	}

} // EntityImpl

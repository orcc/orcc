/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.transformations.NetworkClassifier;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.df.transformations.SolveParametersTransform;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.EntityImpl;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.tools.merger.ActorMerger;
import net.sf.orcc.tools.normalizer.ActorNormalizer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * This class defines a hierarchical XDF network. It contains several maps so
 * templates can walk through the graph of the network.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @generated
 */
public class NetworkImpl extends EntityImpl implements Network {

	private Map<Connection, Integer> connectionMap;

	private Map<Connection, Integer> connectionMapWithoutBroadcast;

	private Map<Instance, Map<Port, Connection>> incomingMap;

	/**
	 * @generated
	 */
	protected EList<Port> inputs;

	/**
	 * The cached value of the '{@link #getMoC() <em>Mo C</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMoC()
	 * @generated
	 * @ordered
	 */
	protected MoC moC;

	private Map<Instance, Map<Port, List<Connection>>> outgoingMap;

	/**
	 * @generated
	 */
	protected EList<Port> outputs;

	/**
	 * @generated
	 */
	protected EList<Var> parameters;

	private Map<Instance, Map<Port, Instance>> predecessorsMap;

	private Map<Instance, Map<Port, List<Instance>>> successorsMap;

	/**
	 * @generated
	 */
	protected EList<Var> variables;

	/**
	 * The cached value of the '{@link #getConnections() <em>Connections</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> connections;

	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> instances;

	/**
	 * The cached value of the '{@link #getVertices() <em>Vertices</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVertices()
	 * @generated
	 * @ordered
	 */
	protected EList<Vertex> vertices;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NetworkImpl() {
		super();
	}

	@Override
	public void addConnection(Vertex source, Vertex target,
			Connection connection) {
		connection.setSource(source);
		connection.setTarget(target);
		getConnections().add(connection);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMoC(MoC newMoC, NotificationChain msgs) {
		MoC oldMoC = moC;
		moC = newMoC;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DfPackage.NETWORK__MO_C, oldMoC, newMoC);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Classifies this network.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void classify() throws OrccException {
		new NetworkClassifier().transform(this);
	}

	private void computeIncomingOutgoingMaps() {
		incomingMap = new HashMap<Instance, Map<Port, Connection>>();
		outgoingMap = new HashMap<Instance, Map<Port, List<Connection>>>();
		for (Vertex vertex : getVertices()) {
			if (vertex.isInstance()) {
				// incoming edges
				List<Connection> connections = vertex.getIncomingEdges();
				Map<Port, Connection> incoming = new HashMap<Port, Connection>();
				for (Connection connection : connections) {
					incoming.put(connection.getTargetPort(), connection);
				}
				incomingMap.put(vertex.getInstance(), incoming);

				// outgoing edges
				connections = vertex.getOutgoingEdges();
				Map<Port, List<Connection>> outgoing = new HashMap<Port, List<Connection>>();
				for (Connection connection : connections) {
					Port source = connection.getSourcePort();
					List<Connection> conns = outgoing.get(source);
					if (conns == null) {
						conns = new ArrayList<Connection>(1);
						outgoing.put(source, conns);
					}
					conns.add(connection);
				}
				outgoingMap.put(vertex.getInstance(), outgoing);
			}
		}
	}

	private void computePredecessorsSuccessorsMaps() {
		predecessorsMap = new HashMap<Instance, Map<Port, Instance>>();
		successorsMap = new HashMap<Instance, Map<Port, List<Instance>>>();

		// for each instance
		for (Vertex vertex : getVertices()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					computePredSucc(vertex, actor.getInputs(),
							actor.getOutputs());
				} else if (instance.isBroadcast()) {
					Broadcast bcast = instance.getBroadcast();
					computePredSucc(vertex, bcast.getInputs(),
							bcast.getOutputs());
				} else if (instance.isNetwork()) {
					Network network = instance.getNetwork();
					computePredSucc(vertex, network.getInputs(),
							network.getOutputs());
				}
			}
		}
	}

	private void computePredSucc(Vertex vertex, List<Port> inputs,
			List<Port> outputs) {
		Map<Port, Instance> predMap = new LinkedHashMap<Port, Instance>();
		predecessorsMap.put(vertex.getInstance(), predMap);
		List<Connection> incoming = vertex.getIncomingEdges();
		for (Port port : inputs) {
			for (Connection connection : incoming) {
				if (port.equals(connection.getTargetPort())) {
					predMap.put(port, connection.getSource().getInstance());
				}
			}
		}

		Map<Port, List<Instance>> succMap = new LinkedHashMap<Port, List<Instance>>();
		successorsMap.put(vertex.getInstance(), succMap);
		List<Connection> outgoing = vertex.getOutgoingEdges();
		for (Port port : outputs) {
			for (Connection connection : outgoing) {
				if (port.equals(connection.getSourcePort())) {
					List<Instance> instances = succMap.get(port);
					if (instances == null) {
						instances = new ArrayList<Instance>(1);
						succMap.put(port, instances);
					}
					instances.add(connection.getTarget().getInstance());
				}
			}
		}
	}

	/**
	 * Computes the source map and target maps that associate each connection to
	 * its source vertex (respectively target vertex).
	 */
	public void computeTemplateMaps() {
		int i, j;

		// Compute template maps of subnetworks
		for (Instance instance : getInstances()) {
			if (instance.isNetwork()) {
				instance.getNetwork().computeTemplateMaps();
			}
		}

		connectionMap = new HashMap<Connection, Integer>();
		i = 0;
		for (Connection connection : getConnections()) {
			connectionMap.put(connection, i++);
		}

		computeIncomingOutgoingMaps();

		computePredecessorsSuccessorsMaps();

		connectionMapWithoutBroadcast = new HashMap<Connection, Integer>();
		i = 0;
		for (Map<Port, List<Connection>> map : outgoingMap.values()) {
			for (List<Connection> connections : map.values()) {
				j = 0;
				for (Connection connection : connections) {
					connectionMapWithoutBroadcast.put(connection, i);
					connection.setFifoId(j);
					j++;
				}
				i++;
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
			case DfPackage.NETWORK__INPUTS:
				return getInputs();
			case DfPackage.NETWORK__MO_C:
				return getMoC();
			case DfPackage.NETWORK__OUTPUTS:
				return getOutputs();
			case DfPackage.NETWORK__PARAMETERS:
				return getParameters();
			case DfPackage.NETWORK__VARIABLES:
				return getVariables();
			case DfPackage.NETWORK__CONNECTIONS:
				return getConnections();
			case DfPackage.NETWORK__INSTANCES:
				return getInstances();
			case DfPackage.NETWORK__VERTICES:
				return getVertices();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DfPackage.NETWORK__INPUTS:
				return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
			case DfPackage.NETWORK__MO_C:
				return basicSetMoC(null, msgs);
			case DfPackage.NETWORK__OUTPUTS:
				return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
			case DfPackage.NETWORK__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case DfPackage.NETWORK__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case DfPackage.NETWORK__CONNECTIONS:
				return ((InternalEList<?>)getConnections()).basicRemove(otherEnd, msgs);
			case DfPackage.NETWORK__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
			case DfPackage.NETWORK__VERTICES:
				return ((InternalEList<?>)getVertices()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DfPackage.NETWORK__INPUTS:
				return inputs != null && !inputs.isEmpty();
			case DfPackage.NETWORK__MO_C:
				return moC != null;
			case DfPackage.NETWORK__OUTPUTS:
				return outputs != null && !outputs.isEmpty();
			case DfPackage.NETWORK__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case DfPackage.NETWORK__VARIABLES:
				return variables != null && !variables.isEmpty();
			case DfPackage.NETWORK__CONNECTIONS:
				return connections != null && !connections.isEmpty();
			case DfPackage.NETWORK__INSTANCES:
				return instances != null && !instances.isEmpty();
			case DfPackage.NETWORK__VERTICES:
				return vertices != null && !vertices.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DfPackage.NETWORK__INPUTS:
				getInputs().clear();
				getInputs().addAll((Collection<? extends Port>)newValue);
				return;
			case DfPackage.NETWORK__MO_C:
				setMoC((MoC)newValue);
				return;
			case DfPackage.NETWORK__OUTPUTS:
				getOutputs().clear();
				getOutputs().addAll((Collection<? extends Port>)newValue);
				return;
			case DfPackage.NETWORK__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Var>)newValue);
				return;
			case DfPackage.NETWORK__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Var>)newValue);
				return;
			case DfPackage.NETWORK__CONNECTIONS:
				getConnections().clear();
				getConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case DfPackage.NETWORK__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends Instance>)newValue);
				return;
			case DfPackage.NETWORK__VERTICES:
				getVertices().clear();
				getVertices().addAll((Collection<? extends Vertex>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.NETWORK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DfPackage.NETWORK__INPUTS:
				getInputs().clear();
				return;
			case DfPackage.NETWORK__MO_C:
				setMoC((MoC)null);
				return;
			case DfPackage.NETWORK__OUTPUTS:
				getOutputs().clear();
				return;
			case DfPackage.NETWORK__PARAMETERS:
				getParameters().clear();
				return;
			case DfPackage.NETWORK__VARIABLES:
				getVariables().clear();
				return;
			case DfPackage.NETWORK__CONNECTIONS:
				getConnections().clear();
				return;
			case DfPackage.NETWORK__INSTANCES:
				getInstances().clear();
				return;
			case DfPackage.NETWORK__VERTICES:
				getVertices().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * Flattens this network.
	 */
	public void flatten() {
		new SolveParametersTransform().transform(this);
		new NetworkFlattener().transform(this);
	}

	/**
	 * Returns the list of actors referenced by the graph of this network. This
	 * is different from the list of instances of this network: There are
	 * typically more instances than there are actors, because an actor may be
	 * instantiated several times.
	 * 
	 * <p>
	 * The list is computed on the fly by adding all the actors referenced in a
	 * set.
	 * </p>
	 * 
	 * @return a list of actors
	 */
	public List<Actor> getActors() {
		Set<Actor> actors = new HashSet<Actor>();
		for (Instance instance : getInstances()) {
			if (instance.isActor()) {
				Actor actor = instance.getActor();
				actors.add(actor);
			} else if (instance.isNetwork()) {
				Network network = instance.getNetwork();
				actors.addAll(network.getActors());
			}
		}

		List<Actor> list = new ArrayList<Actor>(actors);
		Collections.sort(list, new Comparator<Actor>() {

			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return list;
	}

	/**
	 * Returns a map that associates each connection to a unique integer.
	 * 
	 * @return a map that associates each connection to a unique integer
	 */
	public Map<Connection, Integer> getConnectionMap() {
		return connectionMap;
	}

	public Map<Connection, Integer> getConnectionMapWithoutBroadcast() {
		return connectionMapWithoutBroadcast;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getConnections() {
		if (connections == null) {
			connections = new EObjectContainmentEList<Connection>(Connection.class, this, DfPackage.NETWORK__CONNECTIONS);
		}
		return connections;
	}

	/**
	 * Returns a map that associates each instance to the list of its incoming
	 * edges.
	 * 
	 * @return a map that associates each instance to the list of its incoming
	 *         edges
	 */
	public Map<Instance, Map<Port, Connection>> getIncomingMap() {
		return incomingMap;
	}

	/**
	 * Returns the input port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an input port whose name matches the given name
	 */
	public Port getInput(String name) {
		for (Port port : inputs) {
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
			inputs = new EObjectContainmentEList<Port>(Port.class, this, DfPackage.NETWORK__INPUTS);
		}
		return inputs;
	}

	@Override
	public Instance getInstance(String id) {
		for (Instance instance : getInstances()) {
			if (instance.getId().equals(id)) {
				return instance;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance> getInstances() {
		if (instances == null) {
			instances = new EObjectContainmentEList<Instance>(Instance.class, this, DfPackage.NETWORK__INSTANCES);
		}
		return instances;
	}

	/**
	 * Returns the list of instances of the given actor in the graph.
	 * 
	 * @param actor
	 *            the actor to get the instance of
	 * 
	 * @return a list of instances
	 */
	public List<Instance> getInstancesOf(Actor actor) {
		List<Instance> instances = new ArrayList<Instance>();

		for (Instance instance : getInstances()) {
			if (instance.isActor() && instance.getActor() == actor) {
				instances.add(instance);
			} else if (instance.isNetwork()) {
				Network network = instance.getNetwork();
				instances.addAll(network.getInstancesOf(actor));
			}
		}

		return instances;
	}

	/**
	 * Returns the MoC of the network.
	 * 
	 * @return the network MoC.
	 * @generated
	 */
	public MoC getMoC() {
		return moC;
	}

	/**
	 * Returns the list of networks referenced by the graph of this network.
	 * This is different from the list of instances of this network: There are
	 * typically more instances than there are networks, because a network may
	 * be instantiated several times.
	 * 
	 * <p>
	 * The list is computed on the fly by adding all the networks referenced in
	 * a set.
	 * </p>
	 * 
	 * @return a list of networks
	 */
	public List<Network> getNetworks() {
		Set<Network> networks = new HashSet<Network>();
		for (Instance instance : getInstances()) {
			if (instance.isNetwork()) {
				Network network = instance.getNetwork();
				networks.add(network);
				networks.addAll(network.getNetworks());
			}
		}

		return Arrays.asList(networks.toArray(new Network[0]));
	}

	/**
	 * Returns a map that associates each instance to the list of its outgoing
	 * edges.
	 * 
	 * @return a map that associates each instance to the list of its outgoing
	 *         edges
	 */
	public Map<Instance, Map<Port, List<Connection>>> getOutgoingMap() {
		return outgoingMap;
	}

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	public Port getOutput(String name) {
		for (Port port : outputs) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}

	/**
	 * Returns the list of this network's output ports
	 * 
	 * @return the list of this network's output ports
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectContainmentEList<Port>(Port.class, this, DfPackage.NETWORK__OUTPUTS);
		}
		return outputs;
	}

	/**
	 * Returns the list of this network's parameters
	 * 
	 * @return the list of this network's parameters
	 * @generated
	 */
	public EList<Var> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Var>(Var.class, this, DfPackage.NETWORK__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * Returns a map that associates a port to the list of its predecessors.
	 * 
	 * @return a map that associates a port to the list of its predecessors
	 */
	public Map<Instance, Map<Port, Instance>> getPredecessorsMap() {
		return predecessorsMap;
	}

	/**
	 * Returns a map that associates a port to the list of its successors.
	 * 
	 * @return a map that associates a port to the list of its successors
	 */
	public Map<Instance, Map<Port, List<Instance>>> getSuccessorsMap() {
		return successorsMap;
	}

	/**
	 * Returns the list of this network's variables
	 * 
	 * @return the list of this network's variables
	 * @generated
	 */
	public EList<Var> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Var>(Var.class, this, DfPackage.NETWORK__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Vertex> getVertices() {
		if (vertices == null) {
			vertices = new EObjectContainmentEList<Vertex>(Vertex.class, this, DfPackage.NETWORK__VERTICES);
		}
		return vertices;
	}

	/**
	 * Merges actors of this network. Note that for this transformation to work
	 * properly, actors must have been classified and normalized first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void mergeActors() throws OrccException {
		new ActorMerger().transform(this);
	}

	/**
	 * Normalizes actors of this network so they can later be merged. Note that
	 * for this transformation to work properly, actors must have been
	 * classified first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void normalizeActors() throws OrccException {
		for (Actor actor : getActors()) {
			new ActorNormalizer().doSwitch(actor);
		}
	}

	/**
	 * Sets the MoC of this network.
	 * 
	 * @param moc
	 *            the new MoC of this network
	 * @generated
	 */
	public void setMoC(MoC newMoC) {
		if (newMoC != moC) {
			NotificationChain msgs = null;
			if (moC != null)
				msgs = ((InternalEObject)moC).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DfPackage.NETWORK__MO_C, null, msgs);
			if (newMoC != null)
				msgs = ((InternalEObject)newMoC).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DfPackage.NETWORK__MO_C, null, msgs);
			msgs = basicSetMoC(newMoC, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DfPackage.NETWORK__MO_C, newMoC, newMoC));
	}

	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();
		return name;
	}

	/**
	 * Computes the hierarchical identifier of each instance.
	 */
	public void updateIdentifiers() {
		List<String> identifiers = new ArrayList<String>(1);
		identifiers.add(name);
		updateIdentifiers(identifiers);
	}

	private void updateIdentifiers(List<String> identifiers) {
		for (Instance instance : getInstances()) {
			instance.getHierarchicalId().addAll(0, identifiers);
			if (instance.isNetwork()) {
				List<String> subNetworkId = new ArrayList<String>(identifiers);
				subNetworkId.add(instance.getId());
				((NetworkImpl) instance.getNetwork())
						.updateIdentifiers(subNetworkId);
			}
		}
	}

}

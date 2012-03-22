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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.GraphPackage;
import net.sf.dftools.graph.Vertex;
import net.sf.orcc.OrccException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transformations.NetworkClassifier;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.tools.merger.ActorMerger;
import net.sf.orcc.tools.normalizer.ActorNormalizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
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
	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> edges;

	/**
	 * The cached value of the '{@link #getVertices() <em>Vertices</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVertices()
	 * @generated
	 * @ordered
	 */
	protected EList<Vertex> vertices;

	/**
	 * The cached value of the '{@link #getMoC() <em>Mo C</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMoC()
	 * @generated
	 * @ordered
	 */
	protected MoC moC;

	/**
	 * @generated
	 */
	protected EList<Var> variables;

	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> instances;

	/**
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntities() <em>Entities</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<Entity> entities;

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputs;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputs;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NetworkImpl() {
		super();
	}

	@Override
	public void add(Vertex vertex) {
		if (vertex instanceof Entity) {
			getEntities().add((Entity) vertex);
		} else if (vertex instanceof Instance) {
			getInstances().add((Instance) vertex);
		}
		getVertices().add(vertex);
	}

	@Override
	public void addInput(Port port) {
		getVertices().add(port);
		getInputs().add(port);
	}

	@Override
	public void addOutput(Port port) {
		getVertices().add(port);
		getOutputs().add(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMoC(MoC newMoC, NotificationChain msgs) {
		MoC oldMoC = moC;
		moC = newMoC;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, DfPackage.NETWORK__MO_C, oldMoC, newMoC);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
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
		new NetworkClassifier().doSwitch(this);
	}

	/**
	 * Computes the source map and target maps that associate each connection to
	 * its source vertex (respectively target vertex).
	 */
	public void computeTemplateMaps() {
		// Compute template maps of subnetworks
		for (Instance instance : getInstances()) {
			if (instance.isNetwork()) {
				instance.getNetwork().computeTemplateMaps();
			}
		}

		int i = 0;
		for (Connection connection : getConnections()) {
			connection.setAttribute("id", i++);
		}

		i = 0;
		for (Instance instance : getInstances()) {
			Map<Port, List<Connection>> map = instance.getOutgoingPortMap();
			for (List<Connection> connections : map.values()) {
				int j = 0;
				for (Connection connection : connections) {
					connection.setAttribute("idNoBcast", i);
					connection.setAttribute("fifoId", j);
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Graph.class) {
			switch (derivedFeatureID) {
			case DfPackage.NETWORK__EDGES:
				return GraphPackage.GRAPH__EDGES;
			case DfPackage.NETWORK__VERTICES:
				return GraphPackage.GRAPH__VERTICES;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Graph.class) {
			switch (baseFeatureID) {
			case GraphPackage.GRAPH__EDGES:
				return DfPackage.NETWORK__EDGES;
			case GraphPackage.GRAPH__VERTICES:
				return DfPackage.NETWORK__VERTICES;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.NETWORK__EDGES:
			return getEdges();
		case DfPackage.NETWORK__VERTICES:
			return getVertices();
		case DfPackage.NETWORK__MO_C:
			return getMoC();
		case DfPackage.NETWORK__VARIABLES:
			return getVariables();
		case DfPackage.NETWORK__INSTANCES:
			return getInstances();
		case DfPackage.NETWORK__FILE_NAME:
			return getFileName();
		case DfPackage.NETWORK__ENTITIES:
			return getEntities();
		case DfPackage.NETWORK__INPUTS:
			return getInputs();
		case DfPackage.NETWORK__OUTPUTS:
			return getOutputs();
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
		case DfPackage.NETWORK__EDGES:
			return ((InternalEList<?>) getEdges()).basicRemove(otherEnd, msgs);
		case DfPackage.NETWORK__VERTICES:
			return ((InternalEList<?>) getVertices()).basicRemove(otherEnd,
					msgs);
		case DfPackage.NETWORK__MO_C:
			return basicSetMoC(null, msgs);
		case DfPackage.NETWORK__VARIABLES:
			return ((InternalEList<?>) getVariables()).basicRemove(otherEnd,
					msgs);
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
		case DfPackage.NETWORK__EDGES:
			return edges != null && !edges.isEmpty();
		case DfPackage.NETWORK__VERTICES:
			return vertices != null && !vertices.isEmpty();
		case DfPackage.NETWORK__MO_C:
			return moC != null;
		case DfPackage.NETWORK__VARIABLES:
			return variables != null && !variables.isEmpty();
		case DfPackage.NETWORK__INSTANCES:
			return instances != null && !instances.isEmpty();
		case DfPackage.NETWORK__FILE_NAME:
			return FILE_NAME_EDEFAULT == null ? fileName != null
					: !FILE_NAME_EDEFAULT.equals(fileName);
		case DfPackage.NETWORK__ENTITIES:
			return entities != null && !entities.isEmpty();
		case DfPackage.NETWORK__INPUTS:
			return inputs != null && !inputs.isEmpty();
		case DfPackage.NETWORK__OUTPUTS:
			return outputs != null && !outputs.isEmpty();
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
		case DfPackage.NETWORK__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends Edge>) newValue);
			return;
		case DfPackage.NETWORK__VERTICES:
			getVertices().clear();
			getVertices().addAll((Collection<? extends Vertex>) newValue);
			return;
		case DfPackage.NETWORK__MO_C:
			setMoC((MoC) newValue);
			return;
		case DfPackage.NETWORK__VARIABLES:
			getVariables().clear();
			getVariables().addAll((Collection<? extends Var>) newValue);
			return;
		case DfPackage.NETWORK__INSTANCES:
			getInstances().clear();
			getInstances().addAll((Collection<? extends Instance>) newValue);
			return;
		case DfPackage.NETWORK__FILE_NAME:
			setFileName((String) newValue);
			return;
		case DfPackage.NETWORK__ENTITIES:
			getEntities().clear();
			getEntities().addAll((Collection<? extends Entity>) newValue);
			return;
		case DfPackage.NETWORK__INPUTS:
			getInputs().clear();
			getInputs().addAll((Collection<? extends Port>) newValue);
			return;
		case DfPackage.NETWORK__OUTPUTS:
			getOutputs().clear();
			getOutputs().addAll((Collection<? extends Port>) newValue);
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
		case DfPackage.NETWORK__EDGES:
			getEdges().clear();
			return;
		case DfPackage.NETWORK__VERTICES:
			getVertices().clear();
			return;
		case DfPackage.NETWORK__MO_C:
			setMoC((MoC) null);
			return;
		case DfPackage.NETWORK__VARIABLES:
			getVariables().clear();
			return;
		case DfPackage.NETWORK__INSTANCES:
			getInstances().clear();
			return;
		case DfPackage.NETWORK__FILE_NAME:
			setFileName(FILE_NAME_EDEFAULT);
			return;
		case DfPackage.NETWORK__ENTITIES:
			getEntities().clear();
			return;
		case DfPackage.NETWORK__INPUTS:
			getInputs().clear();
			return;
		case DfPackage.NETWORK__OUTPUTS:
			getOutputs().clear();
			return;
		}
		super.eUnset(featureID);
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
	public List<Actor> getAllActors() {
		Set<Actor> actors = new HashSet<Actor>();
		for (Entity entity : getAllEntities()) {
			if (entity.isActor()) {
				actors.add((Actor) entity);
			} else if (entity.isNetwork()) {
				actors.addAll(((Network) entity).getAllActors());
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
	 * Returns all the entities in this network (contained in "entities" and
	 * referenced by "instances").
	 * 
	 * @return an iterable of Entity
	 */
	private List<Entity> getAllEntities() {
		List<Entity> entities = new ArrayList<Entity>(getEntities());
		for (Instance instance : getInstances()) {
			entities.add(instance.getEntity());
		}
		return entities;
	}

	@Override
	public List<Network> getAllNetworks() {
		Set<Network> networks = new HashSet<Network>();
		for (Entity entity : getAllEntities()) {
			if (entity.isNetwork()) {
				Network network = (Network) entity;
				networks.add(network);
				networks.addAll(network.getAllNetworks());
			}
		}

		return new ArrayList<Network>(networks);
	}

	@Override
	@SuppressWarnings("unchecked")
	public EList<Connection> getConnections() {
		return (EList<Connection>) (EList<?>) getEdges();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getEdges() {
		if (edges == null) {
			edges = new EObjectContainmentEList<Edge>(Edge.class, this,
					DfPackage.NETWORK__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Entity> getEntities() {
		if (entities == null) {
			entities = new EObjectResolvingEList<Entity>(Entity.class, this,
					DfPackage.NETWORK__ENTITIES);
		}
		return entities;
	}

	@Override
	public Vertex getEntry() {
		Vertex first = null;
		for (Vertex vertex : getVertices()) {
			if (vertex.getIncoming().isEmpty()) {
				if (first != null) {
					// first vertex already found, returns null
					return null;
				}
				first = vertex;
			}
		}
		return first;
	}

	@Override
	public Vertex getExit() {
		Vertex last = null;
		for (Vertex vertex : getVertices()) {
			if (vertex.getOutgoing().isEmpty()) {
				if (last != null) {
					// last vertex already found, returns null
					return null;
				}
				last = vertex;
			}
		}
		return last;
	}

	@Override
	public IFile getFile() {
		String fileName = getFileName();
		if (fileName == null) {
			return null;
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFile(new Path(fileName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getInputs() {
		if (inputs == null) {
			inputs = new EObjectResolvingEList<Port>(Port.class, this,
					DfPackage.NETWORK__INPUTS);
		}
		return inputs;
	}

	@Override
	public Instance getInstance(String id) {
		for (Instance instance : getInstances()) {
			if (instance.getName().equals(id)) {
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
			instances = new EObjectResolvingEList<Instance>(Instance.class,
					this, DfPackage.NETWORK__INSTANCES);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectResolvingEList<Port>(Port.class, this,
					DfPackage.NETWORK__OUTPUTS);
		}
		return outputs;
	}

	@Override
	public Var getVariable(String name) {
		for (Var var : getVariables()) {
			if (var.getName().equals(name)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * Returns the list of this network's variables
	 * 
	 * @return the list of this network's variables
	 * @generated
	 */
	public EList<Var> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Var>(Var.class, this,
					DfPackage.NETWORK__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Vertex> getVertices() {
		if (vertices == null) {
			vertices = new EObjectContainmentEList<Vertex>(Vertex.class, this,
					DfPackage.NETWORK__VERTICES);
		}
		return vertices;
	}

	@Override
	public boolean isNetwork() {
		return true;
	}

	/**
	 * Merges actors of this network. Note that for this transformation to work
	 * properly, actors must have been classified and normalized first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void mergeActors() throws OrccException {
		new ActorMerger().doSwitch(this);
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
		for (Actor actor : getAllActors()) {
			new ActorNormalizer().doSwitch(actor);
		}
	}

	@Override
	public void remove(Edge edge) {
		edge.setSource(null);
		edge.setTarget(null);

		getEdges().remove(edge);
	}

	@Override
	public void remove(Entity entity) {
		getEntities().remove(entity);
		remove((Vertex) entity);
	}

	@Override
	public void remove(Vertex vertex) {
		removeEdges(new ArrayList<Edge>(vertex.getIncoming()));
		removeEdges(new ArrayList<Edge>(vertex.getOutgoing()));

		getVertices().remove(vertex);
	}

	@Override
	public void removeEdges(List<? extends Edge> edges) {
		for (Edge transition : edges) {
			transition.setSource(null);
			transition.setTarget(null);
		}
		getEdges().removeAll(edges);
	}

	@Override
	public void removeVertices(List<? extends Vertex> vertices) {
		for (Vertex vertex : vertices) {
			removeEdges(new ArrayList<Edge>(vertex.getIncoming()));
			removeEdges(new ArrayList<Edge>(vertex.getOutgoing()));
		}
		getVertices().removeAll(vertices);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.NETWORK__FILE_NAME, oldFileName, fileName));
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
				msgs = ((InternalEObject) moC).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DfPackage.NETWORK__MO_C, null,
						msgs);
			if (newMoC != null)
				msgs = ((InternalEObject) newMoC).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DfPackage.NETWORK__MO_C, null,
						msgs);
			msgs = basicSetMoC(newMoC, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.NETWORK__MO_C, newMoC, newMoC));
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();
		return name;
	}

}

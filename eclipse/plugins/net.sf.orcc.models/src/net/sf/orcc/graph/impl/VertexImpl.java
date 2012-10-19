/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.impl.AttributableImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Vertex</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getNumber <em>Number</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getConnecting <em>Connecting</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getPredecessors <em>Predecessors</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getSuccessors <em>Successors</em>}</li>
 *   <li>{@link net.sf.orcc.graph.impl.VertexImpl#getNeighbors <em>Neighbors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VertexImpl extends AttributableImpl implements Vertex {

	private static class PredSuccAdapter extends AdapterImpl {

		@Override
		public void notifyChanged(Notification msg) {
			Object feature = msg.getFeature();

			VertexImpl vertex = (VertexImpl) target;
			if (vertex.predecessors != null) {
				if (feature == GraphPackage.Literals.VERTEX__INCOMING) {
					vertex.predecessors = null;
					vertex.neighbors = null;
					vertex.connecting = null;
				}
			}

			if (vertex.successors != null) {
				if (feature == GraphPackage.Literals.VERTEX__OUTGOING) {
					vertex.successors = null;
					vertex.neighbors = null;
					vertex.connecting = null;
				}
			}
		}

	}

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected int number = NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> incoming;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> outgoing;

	/**
	 * The cached value of the '{@link #getConnecting() <em>Connecting</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnecting()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> connecting;

	/**
	 * The cached value of the '{@link #getPredecessors() <em>Predecessors</em>}
	 * ' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPredecessors()
	 * @ordered
	 */
	protected EList<Vertex> predecessors;

	/**
	 * The cached value of the '{@link #getSuccessors() <em>Successors</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSuccessors()
	 * @ordered
	 */
	protected EList<Vertex> successors;

	/**
	 * The cached value of the '{@link #getNeighbors() <em>Neighbors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNeighbors()
	 * @generated
	 * @ordered
	 */
	protected EList<Vertex> neighbors;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected VertexImpl() {
		super();
		eAdapters().add(new PredSuccAdapter());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.VERTEX__LABEL:
			return getLabel();
		case GraphPackage.VERTEX__NUMBER:
			return getNumber();
		case GraphPackage.VERTEX__INCOMING:
			return getIncoming();
		case GraphPackage.VERTEX__OUTGOING:
			return getOutgoing();
		case GraphPackage.VERTEX__CONNECTING:
			return getConnecting();
		case GraphPackage.VERTEX__PREDECESSORS:
			return getPredecessors();
		case GraphPackage.VERTEX__SUCCESSORS:
			return getSuccessors();
		case GraphPackage.VERTEX__NEIGHBORS:
			return getNeighbors();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.VERTEX__INCOMING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncoming())
					.basicAdd(otherEnd, msgs);
		case GraphPackage.VERTEX__OUTGOING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoing())
					.basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.VERTEX__INCOMING:
			return ((InternalEList<?>) getIncoming()).basicRemove(otherEnd,
					msgs);
		case GraphPackage.VERTEX__OUTGOING:
			return ((InternalEList<?>) getOutgoing()).basicRemove(otherEnd,
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
		case GraphPackage.VERTEX__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT
					.equals(label);
		case GraphPackage.VERTEX__NUMBER:
			return number != NUMBER_EDEFAULT;
		case GraphPackage.VERTEX__INCOMING:
			return incoming != null && !incoming.isEmpty();
		case GraphPackage.VERTEX__OUTGOING:
			return outgoing != null && !outgoing.isEmpty();
		case GraphPackage.VERTEX__CONNECTING:
			return connecting != null && !connecting.isEmpty();
		case GraphPackage.VERTEX__PREDECESSORS:
			return predecessors != null && !predecessors.isEmpty();
		case GraphPackage.VERTEX__SUCCESSORS:
			return successors != null && !successors.isEmpty();
		case GraphPackage.VERTEX__NEIGHBORS:
			return neighbors != null && !neighbors.isEmpty();
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
		case GraphPackage.VERTEX__LABEL:
			setLabel((String) newValue);
			return;
		case GraphPackage.VERTEX__NUMBER:
			setNumber((Integer) newValue);
			return;
		case GraphPackage.VERTEX__INCOMING:
			getIncoming().clear();
			getIncoming().addAll((Collection<? extends Edge>) newValue);
			return;
		case GraphPackage.VERTEX__OUTGOING:
			getOutgoing().clear();
			getOutgoing().addAll((Collection<? extends Edge>) newValue);
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
		return GraphPackage.Literals.VERTEX;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GraphPackage.VERTEX__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case GraphPackage.VERTEX__NUMBER:
			setNumber(NUMBER_EDEFAULT);
			return;
		case GraphPackage.VERTEX__INCOMING:
			getIncoming().clear();
			return;
		case GraphPackage.VERTEX__OUTGOING:
			getOutgoing().clear();
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Class<T> type) {
		if (type.isAssignableFrom(getClass())) {
			return (T) this;
		}

		// by default a vertex cannot be adapted to anything else
		// subclasses should extend this method
		return null;
	}

	@Override
	public Graph getGraph() {
		return (Graph) eContainer();
	}

	@Override
	public List<Graph> getHierarchy() {
		List<Graph> graphs = new ArrayList<Graph>();
		Graph parent = getGraph();
		while (parent != null) {
			graphs.add(parent);
			parent = parent.getGraph();
		}
		Collections.reverse(graphs);
		return graphs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Edge>(Edge.class,
					this, GraphPackage.VERTEX__INCOMING,
					GraphPackage.EDGE__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<Edge>(Edge.class,
					this, GraphPackage.VERTEX__OUTGOING,
					GraphPackage.EDGE__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public EList<Edge> getConnecting() {
		if (connecting == null) {
			connecting = new EObjectEList<Edge>(Edge.class, this,
					GraphPackage.VERTEX__CONNECTING);
			for (Edge edge : getIncoming()) {
				if (!connecting.contains(edge)) {
					connecting.add(edge);
				}
			}
			for (Edge edge : getOutgoing()) {
				if (!connecting.contains(edge)) {
					connecting.add(edge);
				}
			}
		}
		return connecting;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public EList<Vertex> getPredecessors() {
		if (predecessors == null) {
			predecessors = new EObjectEList<Vertex>(Vertex.class, this,
					GraphPackage.VERTEX__PREDECESSORS);

			for (Edge edge : getIncoming()) {
				Vertex source = edge.getSource();
				if (!predecessors.contains(source)) {
					predecessors.add(source);
				}
			}
		}
		return predecessors;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public EList<Vertex> getSuccessors() {
		if (successors == null) {
			successors = new EObjectEList<Vertex>(Vertex.class, this,
					GraphPackage.VERTEX__SUCCESSORS);

			for (Edge edge : getOutgoing()) {
				Vertex target = edge.getTarget();
				if (!successors.contains(target)) {
					successors.add(target);
				}
			}
		}
		return successors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public EList<Vertex> getNeighbors() {
		if (neighbors == null) {
			neighbors = new EObjectEList<Vertex>(Vertex.class, this,
					GraphPackage.VERTEX__NEIGHBORS);
			for (Vertex successor : getSuccessors()) {
				if (!neighbors.contains(successor)) {
					neighbors.add(successor);
				}
			}
			for (Vertex predecessor : getPredecessors()) {
				if (!neighbors.contains(predecessor)) {
					neighbors.add(predecessor);
				}
			}
		}
		return neighbors;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GraphPackage.VERTEX__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumber(int newNumber) {
		int oldNumber = number;
		number = newNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GraphPackage.VERTEX__NUMBER, oldNumber, number));
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer();
		if (eIsSet(GraphPackage.VERTEX__NUMBER)) {
			result.append("(");
			result.append(getNumber());
			result.append(") ");
		}
		if (eIsSet(GraphPackage.VERTEX__LABEL)) {
			result.append(getLabel());
		} else {
			result.append(super.toString());
		}
		return result.toString();
	}

}

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph.impl;

import java.util.Collection;
import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.GraphFactory;
import net.sf.dftools.graph.GraphPackage;
import net.sf.dftools.graph.Vertex;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Graph</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.graph.impl.GraphImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.GraphImpl#getVertices <em>Vertices</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GraphImpl extends VertexImpl implements Graph {

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GraphImpl() {
		super();
	}

	@Override
	public void add(Edge edge) {
		getEdges().add(edge);
	}

	@Override
	public void add(Vertex vertex) {
		getVertices().add(vertex);
	}

	@Override
	public Edge add(Vertex source, Vertex target) {
		Edge edge = GraphFactory.eINSTANCE.createEdge();
		edge.setSource(source);
		edge.setTarget(target);
		getEdges().add(edge);
		return edge;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.GRAPH__EDGES:
			return getEdges();
		case GraphPackage.GRAPH__VERTICES:
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
		case GraphPackage.GRAPH__EDGES:
			return ((InternalEList<?>) getEdges()).basicRemove(otherEnd, msgs);
		case GraphPackage.GRAPH__VERTICES:
			return ((InternalEList<?>) getVertices()).basicRemove(otherEnd,
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
		case GraphPackage.GRAPH__EDGES:
			return edges != null && !edges.isEmpty();
		case GraphPackage.GRAPH__VERTICES:
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
		case GraphPackage.GRAPH__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends Edge>) newValue);
			return;
		case GraphPackage.GRAPH__VERTICES:
			getVertices().clear();
			getVertices().addAll((Collection<? extends Vertex>) newValue);
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
		return GraphPackage.Literals.GRAPH;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GraphPackage.GRAPH__EDGES:
			getEdges().clear();
			return;
		case GraphPackage.GRAPH__VERTICES:
			getVertices().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getEdges() {
		if (edges == null) {
			edges = new EObjectContainmentEList<Edge>(Edge.class, this,
					GraphPackage.GRAPH__EDGES);
		}
		return edges;
	}

	@Override
	public Vertex getFirst() {
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
	public Vertex getLast() {
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

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Vertex> getVertices() {
		if (vertices == null) {
			vertices = new EObjectContainmentEList<Vertex>(Vertex.class, this,
					GraphPackage.GRAPH__VERTICES);
		}
		return vertices;
	}

	@Override
	public void remove(Edge edge) {
		edge.setSource(null);
		edge.setTarget(null);

		getEdges().remove(edge);
	}

	@Override
	public void remove(Vertex vertex) {
		removeEdgesOf(vertex);
		getVertices().remove(vertex);
	}

	@Override
	public void removeEdges(List<? extends Edge> edges) {
		// disconnect the edges to remove them from incoming/outgoing
		// trick so that this method works without the need to copy edges
		int i = 0;
		int size = edges.size();
		while (i < edges.size()) {
			Edge edge = edges.get(i);

			// disconnect this edge
			edge.setSource(null);
			edge.setTarget(null);

			// remove edge from this graph
			getEdges().remove(edge);

			// only increment if the list's size did not change
			if (size == edges.size()) {
				i++;
			}
		}
	}

	@Override
	public void removeEdgesOf(Vertex vertex) {
		removeEdges(vertex.getIncoming());
		removeEdges(vertex.getOutgoing());
	}

	@Override
	public void removeVertices(List<? extends Vertex> vertices) {
		for (Vertex vertex : vertices) {
			removeEdgesOf(vertex);
		}
		getVertices().removeAll(vertices);
	}

} // GraphImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.dftools.graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.GraphPackage;
import net.sf.dftools.graph.Vertex;
import net.sf.dftools.util.impl.AttributableImpl;
import net.sf.dftools.util.util.EcoreHelper;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Vertex</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getNumber <em>Number</em>}</li>
 *   <li>{@link net.sf.dftools.graph.impl.VertexImpl#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VertexImpl extends AttributableImpl implements Vertex {

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> incoming;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected int number = NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> outgoing;

	private List<Vertex> predecessors;

	private int predecessorsModCount;

	private List<Vertex> successors;

	private int successorsModCount;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected VertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.VERTEX__INCOMING:
			return getIncoming();
		case GraphPackage.VERTEX__LABEL:
			return getLabel();
		case GraphPackage.VERTEX__NUMBER:
			return getNumber();
		case GraphPackage.VERTEX__OUTGOING:
			return getOutgoing();
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
		case GraphPackage.VERTEX__INCOMING:
			return incoming != null && !incoming.isEmpty();
		case GraphPackage.VERTEX__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT
					.equals(label);
		case GraphPackage.VERTEX__NUMBER:
			return number != NUMBER_EDEFAULT;
		case GraphPackage.VERTEX__OUTGOING:
			return outgoing != null && !outgoing.isEmpty();
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
		case GraphPackage.VERTEX__INCOMING:
			getIncoming().clear();
			getIncoming().addAll((Collection<? extends Edge>) newValue);
			return;
		case GraphPackage.VERTEX__LABEL:
			setLabel((String) newValue);
			return;
		case GraphPackage.VERTEX__NUMBER:
			setNumber((Integer) newValue);
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
		case GraphPackage.VERTEX__INCOMING:
			getIncoming().clear();
			return;
		case GraphPackage.VERTEX__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case GraphPackage.VERTEX__NUMBER:
			setNumber(NUMBER_EDEFAULT);
			return;
		case GraphPackage.VERTEX__OUTGOING:
			getOutgoing().clear();
			return;
		}
		super.eUnset(featureID);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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

	@Override
	public List<Vertex> getPredecessors() {
		// some dark magic to avoid creating this list all the time
		int modCount = EcoreHelper.getModCount(getIncoming());
		if (predecessors != null && modCount == predecessorsModCount) {
			return predecessors;
		}

		predecessorsModCount = modCount;
		predecessors = new ArrayList<Vertex>();
		for (Edge edge : getIncoming()) {
			Vertex source = edge.getSource();
			if (!predecessors.contains(source)) {
				predecessors.add(source);
			}
		}
		return predecessors;
	}

	@Override
	public List<Vertex> getSuccessors() {
		// some dark magic to avoid creating this list all the time
		int modCount = EcoreHelper.getModCount(getOutgoing());
		if (successors != null && modCount == successorsModCount) {
			return successors;
		}

		successorsModCount = modCount;
		successors = new ArrayList<Vertex>();
		for (Edge edge : getOutgoing()) {
			Vertex target = edge.getTarget();
			if (!successors.contains(target)) {
				successors.add(target);
			}
		}
		return successors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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

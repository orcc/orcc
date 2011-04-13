/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Transition;
import net.sf.orcc.util.UniqueEdge;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>FSM</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.FSMImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.FSMImpl#getStates <em>States</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.FSMImpl#getTransitionsMap <em>Transitions Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FSMImpl extends EObjectImpl implements FSM {

	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected State initialState;
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;
	/**
	 * The cached value of the '{@link #getTransitionsMap() <em>Transitions Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<State, Transition> transitionsMap;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected FSMImpl() {
		super();
	}

	@Override
	public void addTransition(State source, Action action, State target) {
		Transition transition = getTransition(source);
		transition.getTargetActions().add(action);
		transition.getTargetStates().add(target);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.FSM__INITIAL_STATE:
				if (resolve) return getInitialState();
				return basicGetInitialState();
			case IrPackage.FSM__STATES:
				return getStates();
			case IrPackage.FSM__TRANSITIONS_MAP:
				if (coreType) return getTransitionsMap();
				else return getTransitionsMap().map();
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
			case IrPackage.FSM__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case IrPackage.FSM__TRANSITIONS_MAP:
				return ((InternalEList<?>)getTransitionsMap()).basicRemove(otherEnd, msgs);
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
			case IrPackage.FSM__INITIAL_STATE:
				return initialState != null;
			case IrPackage.FSM__STATES:
				return states != null && !states.isEmpty();
			case IrPackage.FSM__TRANSITIONS_MAP:
				return transitionsMap != null && !transitionsMap.isEmpty();
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
			case IrPackage.FSM__INITIAL_STATE:
				setInitialState((State)newValue);
				return;
			case IrPackage.FSM__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
				return;
			case IrPackage.FSM__TRANSITIONS_MAP:
				((EStructuralFeature.Setting)getTransitionsMap()).set(newValue);
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
		return IrPackage.Literals.FSM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.FSM__INITIAL_STATE:
				setInitialState((State)null);
				return;
			case IrPackage.FSM__STATES:
				getStates().clear();
				return;
			case IrPackage.FSM__TRANSITIONS_MAP:
				getTransitionsMap().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public DirectedGraph<State, UniqueEdge> getGraph() {
		DirectedGraph<State, UniqueEdge> graph = new DirectedMultigraph<State, UniqueEdge>(
				UniqueEdge.class);
		for (State source : getStates()) {
			graph.addVertex(source);
			Transition transition = getTransition(source);

			Iterator<Action> itA = transition.getTargetActions().iterator();
			Iterator<State> itS = transition.getTargetStates().iterator();
			while (itA.hasNext() && itS.hasNext()) {
				State target = itS.next();
				graph.addVertex(target);
				graph.addEdge(source, target, new UniqueEdge(itA.next()));
			}
		}

		return graph;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public State getInitialState() {
		if (initialState != null && initialState.eIsProxy()) {
			InternalEObject oldInitialState = (InternalEObject)initialState;
			initialState = (State)eResolveProxy(oldInitialState);
			if (initialState != oldInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IrPackage.FSM__INITIAL_STATE, oldInitialState, initialState));
			}
		}
		return initialState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<State>(State.class, this, IrPackage.FSM__STATES);
		}
		return states;
	}

	@Override
	public List<Action> getTargetActions(State state) {
		return getTransition(state).getTargetActions();
	}

	@Override
	public List<State> getTargetStates(State state) {
		return getTransition(state).getTargetStates();
	}

	@Override
	public Transition getTransition(State state) {
		return getTransitionsMap().get(state);
	}

	@Override
	public List<Transition> getTransitions() {
		return new ArrayList<Transition>(getTransitionsMap().values());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<State, Transition> getTransitionsMap() {
		if (transitionsMap == null) {
			transitionsMap = new EcoreEMap<State,Transition>(IrPackage.Literals.STATE_TO_TRANSITION_MAP_ENTRY, StateToTransitionMapEntryImpl.class, this, IrPackage.FSM__TRANSITIONS_MAP);
		}
		return transitionsMap;
	}

	@Override
	public void printGraph(File file) throws OrccException {
		try {
			OutputStream out = new FileOutputStream(file);
			DOTExporter<State, UniqueEdge> exporter = new DOTExporter<State, UniqueEdge>(
					new StringNameProvider<State>(), null,
					new StringEdgeNameProvider<UniqueEdge>());
			exporter.export(new OutputStreamWriter(out), getGraph());
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	@Override
	public void removeTransition(State source, Action action) {
		Transition transition = getTransition(source);
		ListIterator<Action> it = transition.getTargetActions().listIterator();
		while (it.hasNext()) {
			Action candidate = it.next();
			if (candidate == action) {
				int index = it.previousIndex();
				it.remove();
				transition.getTargetStates().remove(index);
				return;
			}
		}
	}

	@Override
	public void replaceTarget(State source, Action action, State target) {
		Transition transition = getTransition(source);
		ListIterator<Action> it = transition.getTargetActions().listIterator();
		while (it.hasNext()) {
			Action candidate = it.next();
			if (candidate == action) {
				int index = it.previousIndex();
				// updates target state of this transition
				transition.getTargetStates().set(index, target);
				return;
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialState(State newInitialState) {
		State oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.FSM__INITIAL_STATE, oldInitialState, initialState));
	}

} // FSMImpl

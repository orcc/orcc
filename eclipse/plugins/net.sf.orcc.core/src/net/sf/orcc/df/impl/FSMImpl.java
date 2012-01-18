/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Edge;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>FSM</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link net.sf.orcc.df.impl.FSMImpl#getInitialState <em>Initial State
 * </em>}</li>
 * <li>{@link net.sf.orcc.df.impl.FSMImpl#getStates <em>States</em>}</li>
 * <li>{@link net.sf.orcc.df.impl.FSMImpl#getTransitions <em>Transitions</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FSMImpl extends EObjectImpl implements FSM {

	/**
	 * The cached value of the '{@link #getInitialState()
	 * <em>Initial State</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected State initialState;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;
	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> transitions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	protected FSMImpl() {
		super();
	}

	@Override
	public Transition addTransition(State source, Action action, State target) {
		Transition transition = DfFactory.eINSTANCE.createTransition(source,
				action, target);
		getTransitions().add(transition);
		return transition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State basicGetInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.FSM__INITIAL_STATE:
			if (resolve)
				return getInitialState();
			return basicGetInitialState();
		case DfPackage.FSM__STATES:
			return getStates();
		case DfPackage.FSM__TRANSITIONS:
			return getTransitions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DfPackage.FSM__STATES:
			return ((InternalEList<?>) getStates()).basicRemove(otherEnd, msgs);
		case DfPackage.FSM__TRANSITIONS:
			return ((InternalEList<?>) getTransitions()).basicRemove(otherEnd,
					msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DfPackage.FSM__INITIAL_STATE:
			return initialState != null;
		case DfPackage.FSM__STATES:
			return states != null && !states.isEmpty();
		case DfPackage.FSM__TRANSITIONS:
			return transitions != null && !transitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DfPackage.FSM__INITIAL_STATE:
			setInitialState((State) newValue);
			return;
		case DfPackage.FSM__STATES:
			getStates().clear();
			getStates().addAll((Collection<? extends State>) newValue);
			return;
		case DfPackage.FSM__TRANSITIONS:
			getTransitions().clear();
			getTransitions()
					.addAll((Collection<? extends Transition>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.FSM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.FSM__INITIAL_STATE:
			setInitialState((State) null);
			return;
		case DfPackage.FSM__STATES:
			getStates().clear();
			return;
		case DfPackage.FSM__TRANSITIONS:
			getTransitions().clear();
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public DirectedGraph<State, Transition> getGraph() {
		DirectedGraph<State, Transition> graph = new DirectedMultigraph<State, Transition>(
				Transition.class);
		for (State source : getStates()) {
			graph.addVertex(source);

			for (Edge edge : source.getOutgoing()) {
				Transition transition = (Transition) edge;
				State target = (State) transition.getTarget();
				graph.addVertex(target);
				graph.addEdge(source, target, transition);
			}
		}

		return graph;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State getInitialState() {
		if (initialState != null && initialState.eIsProxy()) {
			InternalEObject oldInitialState = (InternalEObject) initialState;
			initialState = (State) eResolveProxy(oldInitialState);
			if (initialState != oldInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DfPackage.FSM__INITIAL_STATE, oldInitialState,
							initialState));
			}
		}
		return initialState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<State>(State.class, this,
					DfPackage.FSM__STATES);
		}
		return states;
	}

	@Override
	public List<Action> getTargetActions(State source) {
		List<Action> actions = new ArrayList<Action>();
		for (Edge edge : source.getOutgoing()) {
			Transition transition = (Transition) edge;
			actions.add(transition.getAction());
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Transition> getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList<Transition>(
					Transition.class, this, DfPackage.FSM__TRANSITIONS);
		}
		return transitions;
	}

	@Override
	public void removeTransition(State source, Action action) {
		Iterator<Edge> it = source.getOutgoing().iterator();
		while (it.hasNext()) {
			Transition transition = (Transition) it.next();
			Action candidate = transition.getAction();
			if (candidate == action) {
				it.remove();
				return;
			}
		}
	}

	@Override
	public void replaceTarget(State source, Action action, State target) {
		Iterator<Edge> it = source.getOutgoing().iterator();
		while (it.hasNext()) {
			Transition transition = (Transition) it.next();
			Action candidate = transition.getAction();
			if (candidate == action) {
				// updates target state of this transition
				transition.setTarget(target);
				return;
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInitialState(State newInitialState) {
		State oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.FSM__INITIAL_STATE, oldInitialState, initialState));
	}

} // FSMImpl

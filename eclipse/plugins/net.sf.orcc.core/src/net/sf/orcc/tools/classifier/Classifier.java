/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.classifier;

import static net.sf.orcc.moc.MocFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.graph.visit.BFS;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * This class defines an actor classifier that uses symbolic execution to
 * classify an actor as static, cyclo-static, quasi-static, or dynamic.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Classifier extends DfVisitor<Void> {

	private Actor actor;

	/**
	 * Creates a new classifier
	 */
	public Classifier() {
	}

	/**
	 * Tries to evaluate the guards to check if they are compatible.
	 * 
	 * @param previous
	 *            the action that occurs before <code>action</code>
	 * @param action
	 *            an action
	 * @return <code>true</code> if the guards of the given actions are
	 *         compatible
	 */
	private boolean areGuardsCompatible(Action previous, Action action) {
		GuardSatChecker checker = new GuardSatChecker(actor);
		try {
			if (checker.checkSat(previous, action)) {
				System.out.println(actor.getName() + ": guards of actions "
						+ previous.getName() + " and " + action.getName()
						+ " are compatible");
				return true;
			}

			return false;
		} catch (OrccRuntimeException e) {
			System.err.println(actor.getName()
					+ ": could not check time-dependency");
			e.printStackTrace();
			return true;
		}
	}

	@Override
	public Void caseActor(Actor actor) {
		try {
			this.actor = actor;

			actor.resetTokenConsumption();
			actor.resetTokenProduction();

			classify();
		} catch (Exception e) {
			System.out.println("An exception occurred when classifying actor "
					+ actor.getName() + ": " + e);
			System.out.println("MoC set to DPN");
			System.out.println();
			actor.setMoC(MocFactory.eINSTANCE.createDPNMoC());
		} finally {
			this.actor = null;
		}

		return null;
	}

	/**
	 * Classifies the actor as dynamic, quasi-static, or static.
	 * 
	 * @return the class of the actor
	 */
	private void classify() {
		try {
			IMarker[] markers = actor.getFile().findMarkers(IMarker.PROBLEM,
					true, IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				if (marker.getAttribute(IMarker.SEVERITY,
						IMarker.SEVERITY_ERROR) == IMarker.SEVERITY_INFO) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
		}

		// checks for empty actors
		List<Action> actions = actor.getActions();
		if (actions.isEmpty()) {
			System.out.println("actor " + actor.getName()
					+ " does not contain any actions, defaults to dynamic");
			actor.setMoC(MocFactory.eINSTANCE.createKPNMoC());
			return;
		}

		// checks for actors with time-dependent behavior
		MoC moc;
		if (isTimeDependent()) {
			moc = MocFactory.eINSTANCE.createDPNMoC();
			showMarker();
		} else {
			// merges actions with the same input/output pattern together
			// new SDFActionsMerger().visit(actor);

			// first tries SDF with *all* the actions of the actor
			moc = classifySDF(actions);
			if (!moc.isSDF()) {
				try {
					// not SDF, tries CSDF
					moc = classifyCSDF();
				} catch (OrccRuntimeException e) {
					// data-dependent behavior
				}

				if (!moc.isCSDF()) {
					// not CSDF, tries QSDF
					if (actor.hasFsm()) {
						try {
							moc = classifyQSDF();
						} catch (OrccRuntimeException e) {
							// data-dependent behavior
						}
					}
				}
			}
		}

		// set and print MoC
		actor.setMoC(moc);
		System.out.println("MoC of " + actor.getName() + ": " + moc);
	}

	/**
	 * Tries to classify an actor as CSDF. Classification works only on actor
	 * with a non-empty state. The state consists of all scalar state variables
	 * that have an initial value.
	 * 
	 * @return an actor class
	 */
	private MoC classifyCSDF() {
		AbstractInterpreter interpreter = new AbstractInterpreter(actor);

		ActorState state = new ActorState(interpreter.getActor());
		if (state.isEmpty()) {
			FSM fsm = actor.getFsm();
			if (fsm == null || !isCycloStaticFsm(fsm)) {
				// no state, no cyclo-static FSM => dynamic
				return MocFactory.eINSTANCE.createKPNMoC();
			}
		}

		// creates the MoC
		CSDFMoC csdfMoc = MocFactory.eINSTANCE.createCSDFMoC();
		int nbPhases = 0;

		// loops until the actor goes back to the initial state, or there is a
		// data-dependent condition
		final int MAX_PHASES = 16384;
		State initialState = interpreter.getFsmState();
		do {
			interpreter.schedule();
			Action latest = interpreter.getExecutedAction();
			Invocation invocation = eINSTANCE.createInvocation(latest);
			csdfMoc.getInvocations().add(invocation);
			nbPhases++;
		} while ((!state.isInitialState() || interpreter.getFsmState() != initialState)
				&& nbPhases < MAX_PHASES);

		if (nbPhases == MAX_PHASES) {
			return MocFactory.eINSTANCE.createKPNMoC();
		}

		interpreter.setTokenRates(csdfMoc);
		csdfMoc.setNumberOfPhases(nbPhases);

		return csdfMoc;
	}

	/**
	 * Classify the FSM of the actor this unroller was created with, starting
	 * from the given initial state, and using the given configuration.
	 * 
	 * @param configuration
	 *            a configuration
	 * @return a static class
	 */
	private SDFMoC classifyFsmConfiguration(Map<String, Object> configuration) {
		SDFMoC sdfMoc = MocFactory.eINSTANCE.createSDFMoC();

		AbstractInterpreter interpreter = new AbstractInterpreter(actor);
		State initialState = interpreter.getFsmState();
		interpreter.setConfiguration(configuration);

		// schedule the actor
		int nbPhases = 0;
		final int MAX_PHASES = 16384;
		do {
			interpreter.schedule();
			Action latest = interpreter.getExecutedAction();
			Invocation invocation = eINSTANCE.createInvocation(latest);
			sdfMoc.getInvocations().add(invocation);
			nbPhases++;
		} while (!interpreter.getFsmState().equals(initialState)
				&& nbPhases < MAX_PHASES);

		if (nbPhases == MAX_PHASES) {
			throw new OrccRuntimeException("too many phases");
		}

		interpreter.setTokenRates(sdfMoc);

		return sdfMoc;
	}

	/**
	 * Tries to classify this actor with an FSM as QSDF.
	 * 
	 * @return an MoC
	 */
	private MoC classifyQSDF() {
		FSM fsm = actor.getFsm();
		String name = actor.getName();
		if (!isQuasiStaticFsm(fsm)) {
			System.out.println(name
					+ " has an FSM that is NOT compatible with quasi-static");
			return MocFactory.eINSTANCE.createKPNMoC();
		}

		State initialState = fsm.getInitialState();

		// analyze the configuration of this actor
		List<Port> ports = findConfigurationPorts(fsm);
		if (ports.isEmpty()) {
			System.out.println("no configuration ports found for " + name);
			return MocFactory.eINSTANCE.createKPNMoC();
		}

		Map<Action, Map<String, Object>> configurations = findConfigurationValues(
				fsm, ports);
		if (configurations.isEmpty()) {
			System.out.println("no configurations for " + name);
			return MocFactory.eINSTANCE.createKPNMoC();
		}

		// will unroll for each branch departing from the initial state
		QSDFMoC quasiStatic = MocFactory.eINSTANCE.createQSDFMoC();

		for (Action action : fsm.getTargetActions(initialState)) {
			Map<String, Object> configuration = configurations.get(action);
			if (configuration == null) {
				System.out.println("no configuration for " + action.getName());
				return MocFactory.eINSTANCE.createKPNMoC();
			}
			SDFMoC staticClass = classifyFsmConfiguration(configuration);

			quasiStatic.setSDFMoC(action, staticClass);
		}

		return quasiStatic;
	}

	/**
	 * Tries to classify an actor as SDF. An actor is SDF if it has one action.
	 * If an actor has many actions (in a given state if it has an FSM) with the
	 * same input/output patterns, then these actions are merged by the
	 * SDFActionsMerger transformation.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return a Model of Computation
	 */
	private MoC classifySDF(List<Action> actions) {
		int numActions = actions.size();
		if (numActions != 1) {
			// two cases: 1) an empty actor is considered dynamic because the
			// only actors with no actions are system actors such as source and
			// display

			// 2) an actor with many actions after the SDFActionMerger
			// transformation has been applied is dynamic
			return MocFactory.eINSTANCE.createKPNMoC();
		}

		// schedule
		SDFMoC sdfMoc = MocFactory.eINSTANCE.createSDFMoC();
		AbstractInterpreter interpreter = new AbstractInterpreter(actor);
		Action initializeAction = interpreter.getExecutedAction();
		if (initializeAction != null) {
			// If there was an initialize action, the tokens produced
			// are delays in the sdf moc.
			sdfMoc.getDelayPattern().updatePattern(
					(initializeAction.getOutputPattern()));
			// Reset output production
			interpreter.getActor().resetTokenProduction();
		}
				
		interpreter.schedule();
		Action action = interpreter.getExecutedAction();
		Invocation invocation = eINSTANCE.createInvocation(action);
		sdfMoc.getInvocations().add(invocation);

		interpreter.setTokenRates(sdfMoc);

		return sdfMoc;
	}

	/**
	 * Finds the configuration ports of this actor, if any.
	 */
	private List<Port> findConfigurationPorts(FSM fsm) {
		List<Set<Port>> actionPorts = new ArrayList<Set<Port>>();

		State initialState = fsm.getInitialState();

		// visits the scheduler of each action departing from the initial state
		for (Action action : fsm.getTargetActions(initialState)) {
			Set<Port> candidates = new HashSet<Port>();
			candidates.addAll(action.getPeekPattern().getPorts());
			actionPorts.add(candidates);
		}

		// add all ports peeked
		Set<Port> candidates = new HashSet<Port>();
		for (Set<Port> set : actionPorts) {
			candidates.addAll(set);
		}

		// and then only retains the ones that are common to every action
		for (Set<Port> set : actionPorts) {
			if (!set.isEmpty()) {
				candidates.retainAll(set);
			}
		}

		// copies the candidates to the ports list
		return new ArrayList<Port>(candidates);
	}

	/**
	 * For each action departing from the initial state, visits its guards and
	 * stores a constrained variable that will contain the value to read from
	 * the configuration port when solved.
	 */
	private Map<Action, Map<String, Object>> findConfigurationValues(FSM fsm,
			List<Port> ports) {
		Map<Action, Map<String, Object>> configurations = new HashMap<Action, Map<String, Object>>();
		List<Action> previous = new ArrayList<Action>();

		// visits the scheduler of each action departing from the initial state
		State initialState = fsm.getInitialState();
		for (Action targetAction : fsm.getTargetActions(initialState)) {
			// create the configuration for this action
			GuardSatChecker checker = new GuardSatChecker(actor);
			Map<String, Object> configuration = checker.computeTokenValues(
					ports, previous, targetAction);

			// add the configuration
			configurations.put(targetAction, configuration);

			// add current action to "previous" list
			previous.add(targetAction);
		}

		return configurations;
	}

	/**
	 * Returns <code>true</code> if the given FSM looks like the FSM of a
	 * cyclo-static actor, <code>false</code> otherwise. A potentially
	 * cyclo-static actor is an actor with an FSM that cycles back to its
	 * initial state.
	 * 
	 * @param fsm
	 *            a Finite State Machine
	 * @return <code>true</code> if the given FSM has cyclo-static form
	 */
	private boolean isCycloStaticFsm(FSM fsm) {
		State initialState = fsm.getInitialState();
		for (Vertex vertex : initialState.getSuccessors()) {
			BFS bfs = new BFS(vertex);
			if (!bfs.getVertices().contains(initialState)) {
				// no path back to initial state: cannot be cyclo-static
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns <code>true</code> if the given FSM looks like the FSM of a
	 * quasi-static actor, <code>false</code> otherwise. We simply check that
	 * for each outgoing edge of the initial state, there is a path back to the
	 * initial state.
	 * 
	 * @param fsm
	 *            a Finite State Machine
	 * @return <code>true</code> if the given FSM has quasi-static form
	 */
	private boolean isQuasiStaticFsm(FSM fsm) {
		State initialState = fsm.getInitialState();
		List<Edge> edges = initialState.getOutgoing();
		for (Edge edge : edges) {
			State target = (State) edge.getTarget();

			BFS bfs = new BFS(target);
			if (!bfs.getVertices().contains(initialState)) {
				// if no vertex goes back to the initial state, return false
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if this actor has a time-dependent behavior.
	 * 
	 * @return <code>true</code> if this actor has a time-dependent behavior
	 */
	private boolean isTimeDependent() {
		if (actor.hasFsm()) {
			FSM fsm = actor.getFsm();
			for (State state : fsm.getStates()) {
				// add anonymous actions
				List<Action> actions = new ArrayList<Action>(
						actor.getActionsOutsideFsm());
				for (Edge edge : state.getOutgoing()) {
					Transition transition = (Transition) edge;
					actions.add(transition.getAction());
				}

				if (isTimeDependent(actions)) {
					return true;
				}
			}

			return false;
		} else {
			return isTimeDependent(actor.getActionsOutsideFsm());
		}
	}

	/**
	 * Returns <code>true</code> if the given list of actions has a
	 * time-dependent behavior.
	 * 
	 * @param actions
	 *            a list of actions
	 * @return <code>true</code> if the given list of actions has a
	 *         time-dependent behavior
	 */
	private boolean isTimeDependent(List<Action> actions) {
		Iterator<Action> it = actions.iterator();

		Pattern higherPriorityPattern = DfFactory.eINSTANCE.createPattern();
		List<Action> higherPriorityActions = new ArrayList<Action>();

		if (it.hasNext()) {
			// initialization with the first, higher priority action
			Action higherPriorityAction = it.next();
			higherPriorityPattern.updatePattern(higherPriorityAction
					.getInputPattern());
			higherPriorityActions.add(higherPriorityAction);

			// other actions are compared to the higher priority action and
			// pattern
			while (it.hasNext()) {
				Action lowerPriorityAction = it.next();
				Pattern lowerPriorityPattern = lowerPriorityAction
						.getInputPattern();

				if (!lowerPriorityPattern.isSupersetOf(higherPriorityPattern)) {
					for (Action action : higherPriorityActions) {
						Pattern pattern = action.getInputPattern();
						if (!lowerPriorityPattern.isSupersetOf(pattern)) {
							if (areGuardsCompatible(action, lowerPriorityAction)) {
								return true;
							}
						}
					}
				}

				// Add the current action to higherPriorityActions
				higherPriorityActions.add(lowerPriorityAction);
				higherPriorityPattern.updatePattern(lowerPriorityPattern);
			}
		}

		return false;
	}

	private void showMarker() {
		try {
			IMarker marker = actor.getFile().createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
			marker.setAttribute(IMarker.MESSAGE,
					"Actor " + actor.getSimpleName() + " is time-dependent");
		} catch (CoreException e) {
		}
	}

	@Override
	public String toString() {
		return actor.toString();
	}

}

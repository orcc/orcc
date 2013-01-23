/*
 * Copyright (c) 2011, Abo Akademi University
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
 *   * Neither the name of the Abo Akademi University nor the names of its
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

package net.sf.orcc.backends.promela.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Var;
import net.sf.orcc.tools.classifier.GuardSatChecker;

/**
 * 
 * 
 * @author Johan Ersfolk
 * 
 */

public class PromelaSchedulabilityTest extends DfVisitor<Void> {

	private Set<Port> schedulingPorts;

	private Set<Port> peekPorts = new HashSet<Port>();

	private Set<Transition> transitionsInCycle = new HashSet<Transition>();

	private NetworkStateDefExtractor netStateDef;

	private Map<Var, Action> localVarToAction = new HashMap<Var, Action>();

	private Set<State> choiceStatesSet = new HashSet<State>();

	private List<State> visitedStatesList = new LinkedList<State>();

	private List<Transition> transitionSequenceList = new LinkedList<Transition>();

	private Map<Action, Set<Var>> actionGuardVarMap = new HashMap<Action, Set<Var>>();

	private Set<Var> inputPortVars = new HashSet<Var>();

	private Map<Var, Port> inputVarToPortMap = new HashMap<Var, Port>();

	private FSM fsm;

	public PromelaSchedulabilityTest(NetworkStateDefExtractor netStateDef) {
		super();
		this.netStateDef = netStateDef;
		this.schedulingPorts = netStateDef.getPortsUsedInScheduling();
	}

	@Override
	public Void caseAction(Action action) {
		// identify the control output ports set by this action
		Set<Port> controlOutputs = new HashSet<Port>();
		for (Port port : action.getOutputPattern().getPorts()) {
			if (schedulingPorts.contains(port)) {
				controlOutputs.add(port);
			}
		}
		// check how the control output is generated
		for (Port port : controlOutputs) {
			Var var = action.getOutputPattern().getVariable(port);
			Set<Var> tc = new HashSet<Var>();
			netStateDef.getTransitiveClosure(var, tc, true);
			// depends on ??
		}
		//
		for (Var var : action.getBody().getLocals()) {
			if (netStateDef.getVarsUsedInScheduling().contains(var)) {
				Set<Var> tc = new HashSet<Var>();
				netStateDef.getTransitiveClosure(var, tc, true);
				localVarToAction.put(var, action);
			}
		}
		// how is this action scheduled
		Set<Var> guardVars = new HashSet<Var>();
		actionGuardVarMap.put(action, guardVars);
		for (Var var : action.getScheduler().getLocals()) {
			// find on which variables this scheduler depends
			Set<Var> tc = new HashSet<Var>();
			netStateDef.getTransitiveClosure(var, tc, true);
			for (Var v : tc) {
				if (v.isGlobal()) {
					guardVars.add(v);
				}
			}
		}
		return null;
	}

	@Override
	public Void caseInstance(Instance instance) {
		System.out.println("\n" + instance.getActor().getName());
		this.actor = instance.getActor();
		this.fsm = instance.getActor().getFsm();
		if (fsm == null) {
			return null;
		}
		// find states where actions are input dependent + initial state
		choiceStatesSet.add(fsm.getInitialState());
		for (State state : fsm.getStates()) {
			for (Action action : fsm.getTargetActions(state)) {
				if (!action.getPeekPattern().getPorts().isEmpty()) {
					choiceStatesSet.add(state);
					peekPorts.addAll(action.getPeekPattern().getPorts());
				}
			}
		}
		// find variables corresponding to input ports
		for (Action action : instance.getActor().getActions()) {
			inputPortVars.addAll(action.getInputPattern().getVariables());
			for (Var var : action.getInputPattern().getVariables()) {
				inputVarToPortMap.put(var, action.getInputPattern()
						.getPort(var));
			}
		}
		// how does actions affect scheduling
		for (Action action : instance.getActor().getActions()) {
			doSwitch(action);
		}
		// step through FSM, which transitions belong to cycles
		for (State state : choiceStatesSet) {
			fsmCycleSearch(state);
		}
		// check for nondeterministic cycles in the fsm
		for (State state : instance.getActor().getFsm().getStates()) {
			fsmFindInputdepCycles(state);
		}
		// check the scenarios
		for (State state : choiceStatesSet) {
			fsmScenarioSearch(state);
		}
		if (instance.getActor().hasFsm()
				&& !instance.getActor().getActionsOutsideFsm().isEmpty()) {
			System.out.println(" == Actor has actions outside FSM:");

			for (Action action : instance.getActor().getActionsOutsideFsm()) {
				if (!action.getInputPattern().isEmpty()) {
					System.out.println("Input on port;"
							+ action.getInputPattern());
				}
				if (!action.getOutputPattern().isEmpty()) {
					System.out.println("Output on port;"
							+ action.getOutputPattern());
				}
				if (!action.getPeekPattern().isEmpty()) {
					System.out.println("Peek on port;"
							+ action.getPeekPattern());
				}
			}
		}
		return null;
	}

	private void findPeekValues(State state, Action targetAction) {
		List<Action> previous = new ArrayList<Action>();
		List<Port> peekPorts = new ArrayList<Port>();
		for (Action action : fsm.getTargetActions(state)) {
			for (Port port : action.getPeekPattern().getPorts()) {
				if (!peekPorts.contains(port)) {
					peekPorts.add(port);
				}
			}
		}
		if (peekPorts.isEmpty()) {
			return;
		}
		for (Action action : fsm.getTargetActions(state)) {
			if (action == targetAction) {
				GuardSatChecker checker = new GuardSatChecker(actor);
				Map<String, Object> configuration = null;
				try {
					configuration = checker.computeTokenValues(peekPorts,
							previous, targetAction);
				} catch (OrccRuntimeException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Peek value: " + configuration);
				break;
			} else {
				previous.add(action);
			}
		}
		return;
	}

	private void fsmCycleSearch(State state) {
		visitedStatesList.add(state);
		for (Edge edge : state.getOutgoing()) {
			Transition transition = (Transition) edge;
			transitionSequenceList.add(transition);
			// also visit the successor states
			State target = transition.getTarget();
			if (choiceStatesSet.contains(target)) {
			} else if (visitedStatesList.contains(target)) {
				for (int i = visitedStatesList.indexOf(target); i < visitedStatesList
						.size(); i++) {
					transitionsInCycle.add(transitionSequenceList.get(i));
				}
			} else {
				fsmCycleSearch(target);
			}
			transitionSequenceList.remove(transition);
		}
		visitedStatesList.remove(state); // only keep track of predecessors
		return;
	}

	private void fsmFindInputdepCycles(State state) {
		boolean inputDep = false;
		boolean cycle = false;
		boolean choice = (state.getOutgoing().size() > 1) ? true : false;
		Set<Port> inputPorts = new HashSet<Port>();
		for (Edge edge : state.getOutgoing()) {
			Transition transition = (Transition) edge;
			if (transitionsInCycle.contains(transition)) {
				cycle = true;
			}
			for (Var var : actionGuardVarMap.get(transition.getAction())) {
				boolean temp = hasInputDep(var, true);
				inputDep = temp || inputDep;
				if (temp) {
					inputPorts.addAll(getInputDep(var, true));
				}
			}

		}
		if (inputDep && cycle && choice) {
			System.out.println("State: " + state.getName()
					+ " == repetition depends on input value from Port(s): "
					+ inputPorts);
		}
	}

	private void fsmScenarioSearch(State state) {
		boolean choiceState = choiceStatesSet.contains(state);
		boolean hasVarLoop = false;
		// boolean hasChoice = (state.getOutgoing().size() > 1) ? true : false;
		boolean hasInputDep = false;
		boolean hasIIR = false;
		// boolean hasControlOutput = false;
		for (Edge edge : state.getOutgoing()) {
			Transition transition = (Transition) edge;
			for (Var var : actionGuardVarMap.get(transition.getAction())) {
				hasVarLoop = hasVarLoop(var) || hasVarLoop;
				hasInputDep = hasInputDep(var, true) || hasInputDep;
				hasIIR = hasVarLoop(var) && hasInputDep(var, true) || hasIIR;
			}
			if (choiceState) {
				System.out.println("Scenario starting at state "
						+ state.getName() + " with action "
						+ transition.getAction().getName());
			}
			// System.out.println("State has choice " + hasChoice);
			// System.out.println("Transition in cycle " +
			// transitionsInCycle.contains(transition));
			// System.out.println("Var Loop: " + hasVarLoop);
			// System.out.println("Indirect input dep: " + hasInputDep);
			// System.out.println("IIR: " + hasIIR);
			// Follow what happens on PEEK Ports
			for (Port port : transition.getAction().getInputPattern().getPorts()) {
				boolean portUsed = netStateDef.getVarsUsedInScheduling()
						.contains(
								transition.getAction().getInputPattern()
										.getVariable(port));
				if (peekPorts.contains(port)) {
					System.out.print(" -> Reads control port: "
							+ port.getName());
					if (getPeeksOfState(state).contains(port)) {
						System.out.print(" (peeked value, ");
						System.out.print("only relevant for the guard: "
								+ !portUsed + ") ");
					} else {
						System.out
								.print(" (not peek value, value used in scheduling: "
										+ portUsed + ")");
					}
					if (transitionsInCycle.contains(transition)) {
						System.out.print("(X-times)");
					}
					findPeekValues(state, transition.getAction());
					System.out.print("\n");
				}
			}
			// Control tokens generation
			for (Port port : transition.getAction().getOutputPattern()
					.getPorts()) {
				if (schedulingPorts.contains(port)) {
					// hasControlOutput = true;
					System.out.print(" -> Writes to control port: "
							+ port.getName());
					// how is it generated?
					Var var = transition.getAction().getOutputPattern()
							.getVariable(port);
					System.out.print(", scenario specific constant: "
							+ !(hasVarLoop(var) || hasInputDep(var, true)));
					// System.out.print(", loop " + hasVarLoop(var));
					if (hasInputDep(var, true)) {
						System.out.print(", depends on input (through if:"
								+ !hasInputDep(var, false) + ")");
					}
					// System.out.print(", iir " + (hasVarLoop(var) &&
					// hasInputDep(var, true)));
					System.out.print("\n");
				}
			}
			// System.out.println("Has control output " + hasControlOutput);
			// also visit the successor states
			State target = transition.getTarget();
			if (choiceStatesSet.contains(target)) {
				// System.out.println("Reached end of branch");
				continue;
			}
			if (visitedStatesList.contains(target)) {
				// System.out.println("Repetition branch detected");
				continue;
			}
			visitedStatesList.add(state);
			fsmScenarioSearch(target);

			if (choiceStatesSet.contains(state)) {
				visitedStatesList.clear(); // each scenario separately
				System.out.println("");
			}
		}
		visitedStatesList.remove(state); // only keep track of predecessors
		return;
	}

	Set<Port> getInputDep(Var var, boolean includeIfCond) {
		Set<Var> tc = new HashSet<Var>();
		Set<Port> p = new HashSet<Port>();
		netStateDef.getTransitiveClosure(var, tc, includeIfCond);
		for (Var v : tc) {
			if (inputPortVars.contains(v)) {
				p.add(inputVarToPortMap.get(v));
			}
		}
		return p;
	}

	Set<Port> getPeeksOfState(State state) {
		Set<Port> statePeeks = new HashSet<Port>();
		for (Edge edge : state.getOutgoing()) {
			statePeeks.addAll(((Transition) edge).getAction().getPeekPattern()
					.getPorts());
		}
		return statePeeks;
	}

	boolean hasInputDep(Var var, boolean includeIfCond) {
		Set<Var> tc = new HashSet<Var>();
		netStateDef.getTransitiveClosure(var, tc, includeIfCond);
		boolean hasDep = false;
		for (Var v : tc) {
			if (inputPortVars.contains(v)) {
				hasDep = true;
			}
		}
		return hasDep;
	}

	boolean hasVarLoop(Var var) {
		return netStateDef.hasLoop(var);
	}

}

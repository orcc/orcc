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
import java.util.Iterator;
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
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.tools.classifier.ActorState;
import net.sf.orcc.tools.classifier.GuardSatChecker;

/**
 * 
 * 
 * @author Johan Ersfolk
 * 
 */

public class PromelaSchedulabilityTest extends DfVisitor<Void> {
	
	private Scheduler scheduler;

	private Set<Port> schedulingPorts;

	private Set<Port> peekPorts = new HashSet<Port>();

	private Set<Transition> transitionsInCycle = new HashSet<Transition>();

	private NetworkStateDefExtractor netStateDef;

	private Set<State> choiceStatesSet = new HashSet<State>();

	private List<State> visitedStatesList = new LinkedList<State>();

	private List<Transition> transitionSequenceList = new LinkedList<Transition>();

	private Map<Action, Set<Var>> actionGuardVarMap = new HashMap<Action, Set<Var>>();

	private Set<Var> inputPortVars = new HashSet<Var>();

	private Map<Var, Port> inputVarToPortMap = new HashMap<Var, Port>();

	private FSM fsm;
	
	private Action action;
	
	public PromelaSchedulabilityTest(NetworkStateDefExtractor netStateDef) {
		super();
		this.netStateDef = netStateDef;
		this.schedulingPorts = netStateDef.getPortsUsedInScheduling();
		this.irVisitor = new InnerIrVisitor();
	}

	@Override
	public Void caseAction(Action action) {
		this.action = action;
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
		doSwitch(action.getBody());
		return null;
	}

	@Override
	public Void caseInstance(Instance instance) {
		System.out.println("\n" + instance.getActor().getName());
		this.actor = instance.getActor();
		this.fsm = instance.getActor().getFsm();
		this.scheduler = new Scheduler(instance, this.fsm);
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
		((InnerIrVisitor)this.irVisitor).resolveDeps();
		if (fsm != null) {
			// find states where actions are input dependent + initial state
			choiceStatesSet.add(fsm.getInitialState());
			for (State state : fsm.getStates()) {
				identifyChoiceStates(state);
				for (Action action : fsm.getTargetActions(state)) {
					if (!action.getPeekPattern().getPorts().isEmpty()) {
						peekPorts.addAll(action.getPeekPattern().getPorts());
						choiceStatesSet.add(state);
					}
				}
			}
			// check for nondeterministic cycles in the fsm
			for (State state : instance.getActor().getFsm().getStates()) {
				fsmFindInputdepCycles(state);
			}
			if (!instance.getActor().getActionsOutsideFsm().isEmpty()) {
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

		} else {
			scheduler.addSchedule(new Schedule());
		}
		
		
		boolean stopChecking;
		boolean nullGuardFound;
		do {
			if (fsm != null) {
				scheduler.getSchedules().clear();
				for (State state : choiceStatesSet) {
					fsmPathSearch(state);
				}
			}
			if (actor.hasMoC() && actor.getMoC().isDPN()) {
				// this is time-dependent, stop the show
				System.out.println("Actor " + actor.getSimpleName() + " is time-dependent, we skip it for now..");
				scheduler.makeDummyFSM();
				break;
			}
			stopChecking = false;
			nullGuardFound = false;
			scheduler.buildSchedulingCases();
			
			// Abstract interpretation Start----------------------------------------
		
			INTERP: for (List<Schedule> sl : scheduler.getScheduleCases()) {
				PromelaAbstractInterpreter interpreter = new PromelaAbstractInterpreter(actor);
				ActorState actorstate = new ActorState(interpreter.getActor());

				final int MAX_PHASES = 1024;
				//State initialState = interpreter.getFsmState();
				for (int index = 0; index < sl.size(); index++) {
					Schedule schedule = sl.get(index);
					int nbPhases = 0;
					try {
						if (schedule.getEnablingAction() != null) {
							interpreter.setNextPath(schedule.getEnablingAction());
						}
						do {
							interpreter.schedule();
							Action latest = interpreter.getExecutedAction();
							if (index==sl.size()-1) {
								schedule.getSequence().add(latest);
							}
							nbPhases++;
						} while (((actor.hasFsm() && interpreter.getFsmStateOrig() != schedule.getEndState()) 
								|| (!actor.hasFsm() && !actorstate.isInitialState())) 
								&& nbPhases < MAX_PHASES);
						if (!actor.hasFsm() && nbPhases == MAX_PHASES) {
							scheduler.makeDummyFSM();
							stopChecking=true;
						}
					}catch (OrccRuntimeException e){ //should only happen on native calls
						if (actor.hasFsm()) {
							choiceStatesSet.add(interpreter.getFsmStateOrig());
							nullGuardFound=true;
						} else {
							scheduler.makeDummyFSM();
							stopChecking=true;
						}
						break INTERP;
					}
				}
			}
		
		// Abstract interpretation End----------------------------------------
			if (!stopChecking && !nullGuardFound) {
				stopChecking = areSchedulesComplete();
			}
		} while (!stopChecking);
		
		for (Schedule s : scheduler.getSchedules()) {
			generateScheduleInfo(s);
		}
		
		return null;
	}
	
	private boolean areSchedulesComplete() {
		// does the schedule itself reset the variables before it is used?
		boolean allResolved = true;
		for (Schedule schedule : scheduler.getSchedules()) {
			for (State state : schedule.getPotentialChoiseStates()) {
				Set<Var> guardFull = new HashSet<Var>();
				Set<Var> guardDirect = new HashSet<Var>();
				Set<Action> cActions = new HashSet<Action>();
				// The set of variables that can have an impact on the guards on this state
				for (Edge edge : state.getOutgoing()) {
					cActions.add(((Transition)edge).getAction());
					guardFull.addAll(actionGuardVarMap.get(((Transition)edge).getAction()));
					guardDirect.addAll(actionGuardVarMap.get(((Transition)edge).getAction()));
					Set<Var> temp = new HashSet<Var>();
					for (Var g : guardFull) {
						netStateDef.getTransitiveClosure(g, temp, true);
					}
					guardFull.addAll(temp);
				}
				removeLocalAndConstantVars(guardFull);
				Set<Var> unresolvedVars = new HashSet<Var>(guardFull);
				Set<Var> resolvedVars = new HashSet<Var>();
				for (Action action : schedule.getSequence()) {
					if (cActions.contains(action)) {break;}
					for (Var gVar : guardFull) {
						if (scheduler.getSchedVarReset().containsKey(gVar) 
								&& scheduler.getSchedVarReset().get(gVar).contains(action)) {
							unresolvedVars.remove(gVar);
							resolvedVars.add(gVar);
						}
						// if the variable is updated from a var that is dirty, this variable is also dirty
						if (scheduler.getSchedVarUpdate().containsKey(gVar)
								&& scheduler.getSchedVarUpdate().get(gVar).contains(action)
								&& resolvedVars.contains(gVar)){
							Set<Var> temp = scheduler.getLocalVarDep(action, gVar);
							for (Var depOn : temp) {
								if (unresolvedVars.contains(depOn)) {
									resolvedVars.remove(gVar);
									unresolvedVars.add(gVar);
								}
							}
						}
					}
				}
				// now remove the scheduling vars not directly used in the guard
				Iterator<Var> iter = unresolvedVars.iterator();
				while (iter.hasNext()) {
					if (!guardDirect.contains(iter.next())) {
						iter.remove();
					}
				}
				if (unresolvedVars.isEmpty()) {
					schedule.getPotentialChoiseStates().remove(state);
				}
			}
			if (!schedule.getPotentialChoiseStates().isEmpty()) {
				choiceStatesSet.addAll(schedule.getPotentialChoiseStates());
				allResolved=false;
			} 
		}
		return allResolved;
	}
	

	private Map<String, Object> findPeekValues(State state, Action targetAction) {
		List<Action> previous = new ArrayList<Action>();
		Set<Port> peekPorts = new HashSet<Port>();
		for (Schedule schedule : scheduler.getSchedulesStartingAt(state)) {
			Action action = schedule.getSequence().get(0);//only first actions of a schedule
			peekPorts.addAll(action.getPeekPattern().getPorts());
		}
		if (peekPorts.isEmpty()) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> configuration = null;
		for (Action action : fsm.getTargetActions(state)) {
			if (action == targetAction) {
				GuardSatChecker checker = new GuardSatChecker(actor);
				try {
					configuration = checker.computeTokenValues(new ArrayList<Port>(peekPorts),
							previous, targetAction);
				} catch (OrccRuntimeException e) {
					System.out.println(e.getMessage());
				}
				break;
			} else {
				previous.add(action);
			}
		}
		return configuration;
	}

	private Schedule currentSchedule = null;
	
	/**
	 *  Identifies paths between input dependent states
	 * @param state
	 */
	private void fsmPathSearch(State state) {
		visitedStatesList.add(state);
		for (Edge edge : state.getOutgoing()) {
			Transition transition = (Transition) edge;
			transitionSequenceList.add(transition);
			if (choiceStatesSet.contains(state)) {
				currentSchedule = new Schedule(state, transition.getAction());
				scheduler.addSchedule(currentSchedule);
			} else if (state.getOutgoing().size() > 1) {
				currentSchedule.addPotentialChoiseState(state);
			}
			// check successor states
			State target = transition.getTarget();
			if (choiceStatesSet.contains(target)) {
				// this is not a cycle as it ends the current schedule
				currentSchedule.setEndState(target);
				currentSchedule.incNrLeaves();
				
			} else if (visitedStatesList.contains(target)) {
				// non-input dependent cycle inside schedule
				for (int i = visitedStatesList.indexOf(target); i < visitedStatesList
						.size(); i++) {
					transitionsInCycle.add(transitionSequenceList.get(i));
				}
			} else {
				fsmPathSearch(target);
			}
			transitionSequenceList.remove(transition);
		}
		visitedStatesList.remove(state); // only keep track of predecessors
		return;
	}

	private void identifyChoiceStates(State state) {
		for (Edge edge : state.getOutgoing()) {
			Transition transition = (Transition) edge;
			for (Var var : actionGuardVarMap.get(transition.getAction())) {
				if (hasInputDep(var, true)) {
					choiceStatesSet.add(state);
					return;
				}
			}
		}
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
	

	

	private void generateScheduleInfo(Schedule schedule) {
		State initState=schedule.getInitState();
		Map<String, List<Object>> portPeeks = schedule.getPortPeeks();
		Map<String, List<Object>> portReads = schedule.getPortReads();
		Map<String, List<Object>> portWrites = schedule.getPortWrites();
		
		Map<String, Object> configuration = findPeekValues(initState, schedule.getEnablingAction());
		for (String key : configuration.keySet()) {
			if (!portPeeks.containsKey(key)) {
				portPeeks.put(key, new ArrayList<Object>());
			}
			portPeeks.get(key).add(configuration.get(key));
		}
		for (Action action : schedule.getSequence()) {
			for (Port port : action.getInputPattern().getPorts()) {
				boolean portUsed = netStateDef.getVarsUsedInScheduling()
						.contains(action.getInputPattern().getVariable(port));
				if (peekPorts.contains(port)) {
					if (getPeeksOfState(initState).contains(port)) {
						System.out.print(" (peeked value, ");
						System.out.print("only relevant for the guard: "+ !portUsed + ") ");
					} else {
						System.out.print(" (not peek value, value used in scheduling: "+ portUsed + ")");
					}
					System.out.print("\n");
				}
				if (!portReads.containsKey(port.getName())) {
					portReads.put(port.getName(), new ArrayList<Object>());
				}
				portReads.get(port.getName()).add(new Integer(0));
			}
		}
		System.out.println("Schedule input: "+portReads);
		System.out.print("\n");
		// Control tokens generation
		for (Action action : schedule.getSequence()) {
			for (Port port : action.getOutputPattern().getPorts()) {
				if (schedulingPorts.contains(port)) {
					// hasControlOutput = true;
					System.out.print(" -> Writes to control port: "
						+ port.getName());
					// how is it generated?
					Var var = schedule.getEnablingAction().getOutputPattern()
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
				if (!portWrites.containsKey(port.getName())) {
					portWrites.put(port.getName(), new ArrayList<Object>());
				}
				portWrites.get(port.getName()).add(new Integer(0));
			}
		}
		System.out.println("Schedule output: "+portWrites);
		System.out.print("\n");
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
	
	boolean isBasedOnConstants(Var var) {
		Set<Var> tc = new HashSet<Var>();
		netStateDef.getTransitiveClosure(var, tc, true);
		for (Var v : tc) {
			if (v.isLocal()) {
				continue;
			} else
			if (netStateDef.getVariableDependency().containsKey(v)) {
				return false;
			}
		}
		return true;
	}
	
	void removeLocalAndConstantVars(Set<Var> varSet) {
		Iterator<Var> i = varSet.iterator();
		while (i.hasNext()) {
			Var v = i.next();
			if (v.isLocal() || !v.isAssignable()) {
				i.remove();
			}
		}
	}
	
	private class InnerIrVisitor extends AbstractIrVisitor<Void> {
		
		private Map<InstStore, Var> instToVarMap = new HashMap<InstStore,Var>();
		
		private Map<InstStore, Action> instToActionMap = new HashMap<InstStore, Action>();
		
		private Map<InstStore, List<Var>> instToLocalsMap = new HashMap<InstStore, List<Var>>();
		
		public InnerIrVisitor() {
			super(false); //only expressions of store
		}
		private List<Var> localVars;
		
		@Override
		public Void caseInstStore(InstStore store) {
			if (!netStateDef.getVarsUsedInScheduling().contains(store.getTarget().getVariable())) {
				return null;
			}
			instToVarMap.put(store, store.getTarget().getVariable());
			instToActionMap.put(store, action);
			localVars = new ArrayList<Var>();
			instToLocalsMap.put(store, localVars);
			doSwitch(store.getValue());
			return null;
		}
		
		@Override
		public Void caseExprVar(ExprVar var) {
			localVars.add(var.getUse().getVariable());
			return null;
		}
		
		public void resolveDeps() {
			for (InstStore inst : instToLocalsMap.keySet()) {
				boolean hasLoop = false;
				boolean isConstant = true;
				Set<Var> localDep = new HashSet<Var>();
				for (Var local : instToLocalsMap.get(inst)) {
					if (hasVarLoop(local)) {
						hasLoop = true;
					}
					if (!isBasedOnConstants(local)){
						isConstant = false;
					}
					netStateDef.getActionLocalTransitiveClosure(local, localDep);
				}
				if (hasLoop) {
					scheduler.addvarUpdate(inst.getTarget().getVariable(), instToActionMap.get(inst));
				} 
				if (isConstant) {
					scheduler.addvarReset(inst.getTarget().getVariable(), instToActionMap.get(inst));
				}
				if (!hasLoop && !isConstant) {
					//System.out.println("Found a scheduling variable which is updated through a action sequence, for now consider it is a pure update");
					scheduler.addvarUpdate(inst.getTarget().getVariable(), instToActionMap.get(inst));
				}
				removeLocalAndConstantVars(localDep);
				scheduler.addLocalVarDep(instToActionMap.get(inst), inst.getTarget().getVariable(), localDep);
			}
		}
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}
}

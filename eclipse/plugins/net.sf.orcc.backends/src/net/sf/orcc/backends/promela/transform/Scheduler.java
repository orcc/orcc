package net.sf.orcc.backends.promela.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Var;

public class Scheduler {

	private Set<Schedule> schedules = new HashSet<Schedule>();

	private Set<List<Schedule>> scheduleCases;

	private State initialState = null;

	private Map<Var, List<Action>> schedVarUpdate = new HashMap<Var, List<Action>>();

	private Map<Var, List<Action>> schedVarReset = new HashMap<Var, List<Action>>();

	private Map<Action, Map<Var, Set<Var>>> localVarDep = new HashMap<Action, Map<Var, Set<Var>>>();

	private Map<State, Set<Var>> stateToRelevantVars = new HashMap<State, Set<Var>>();
	
	private Set<Var> schedulingVars = null;
	
	public Set<Var> getSchedulingVars() {
		return schedulingVars;
	}

	private Actor actor;

	public Scheduler(Actor actor, FSM fsm) {
		this.actor = actor;
		if (fsm != null) {
			this.initialState = fsm.getInitialState();
		}
	}

	public void addLocalVarDep(Action action, Var var, Set<Var> localDep) {
		if (!localVarDep.containsKey(action)) {
			localVarDep.put(action, new HashMap<Var, Set<Var>>());
		}
		if (!localVarDep.get(action).containsKey(var)) {
			localVarDep.get(action).put(var, new HashSet<Var>());
		}
		localVarDep.get(action).get(var).addAll(localDep);
	}

	public void addSchedule(Schedule s) {
		schedules.add(s);
	}

	public void addvarReset(Var var, Action action) {
		if (!schedVarReset.containsKey(var)) {
			schedVarReset.put(var, new ArrayList<Action>());
		}
		schedVarReset.get(var).add(action);
	}

	public void addvarUpdate(Var var, Action action) {
		if (!schedVarUpdate.containsKey(var)) {
			schedVarUpdate.put(var, new ArrayList<Action>());
		}
		schedVarUpdate.get(var).add(action);
	}

	public void buildSchedulingCases() {
		/*for (Schedule schedule : schedules) {
			if (schedule.getPotentialEndStates().size()==1) {
				for (State state : schedule.getPotentialEndStates()) {
					schedule.setEndState(state);
				}
			}
		}*/
		scheduleCases = new HashSet<List<Schedule>>();
		Set<State> toVisit = new HashSet<State>();
		toVisit.add(initialState);
		Set<State> visitedStates = new HashSet<State>();
		while (!toVisit.isEmpty()) {
			visitedStates.addAll(toVisit);
			Set<State> newStates = new HashSet<State>();
			for (State s : toVisit) {
				for (Schedule sched : getSchedulesStartingAt(s)) {
					List<Schedule> schedCase;
					if (s != initialState) {
						schedCase = getSchedCaseEndingAt(s);
					} else {
						schedCase = new ArrayList<Schedule>();
					}
					if (!visitedStates.contains(sched.getEndState())) {
						newStates.add(sched.getEndState());
					}
					schedCase.add(sched);
					scheduleCases.add(schedCase);
				}
			}
			toVisit = newStates;
		}
	}

	public State getInitialState() {
		return initialState;
	}

	public Actor getActor() {
		return actor;
	}

	public Set<Var> getLocalVarDep(Action action, Var var) {
		if (localVarDep.containsKey(action)
				&& localVarDep.get(action).containsKey(var)) {
			return localVarDep.get(action).get(var);
		} else {
			return new HashSet<Var>();
		}
	}

	private List<Schedule> getSchedCaseEndingAt(State state) {
		for (List<Schedule> list : scheduleCases) {
			if (state == list.get(list.size() - 1).getEndState())
				return new ArrayList<Schedule>(list);
		}
		return null;
	}

	public Set<List<Schedule>> getScheduleCases() {
		return scheduleCases;
	}

	public Set<Schedule> getSchedules() {
		return schedules;
	}

	public Set<Schedule> getSchedulesStartingAt(State state) {
		Set<Schedule> set = new HashSet<Schedule>();
		for (Schedule s : this.getSchedules()) {
			if (s.getInitState() == state) {
				set.add(s);
			}
		}
		return set;
	}

	public Map<Var, List<Action>> getSchedVarReset() {
		return schedVarReset;
	}

	public Map<Var, List<Action>> getSchedVarUpdate() {
		return schedVarUpdate;
	}

	public void makeDummyFSM() {
		schedules.clear();
		if (actor.hasFsm()) {
			for (State state : actor.getFsm().getStates()) {
				for (Edge edge : state.getOutgoing()) {
					Transition trans = (Transition) edge;
					Schedule s = new Schedule(state, trans.getAction());
					s.getSequence().add(trans.getAction());
					s.setEndState((State) edge.getTarget());
					schedules.add(s);
				}
				// States outside FSM is in every state..
				for (Action action : actor.getActionsOutsideFsm()) {
					Schedule s = new Schedule(state, action);
					s.getSequence().add(action);
					s.setEndState(state);
					schedules.add(s);
				}
			}
		} else {
			for (Action a : actor.getActions()) {
				Schedule s = new Schedule(null, a);
				s.getSequence().add(a);
				schedules.add(s);
			}
		}
	}

	public String toString() {
		String s = "<fsm initial= \"" + this.initialState + "\">\n";
		for (Schedule sched : schedules) {
			s += "<transition action=\"" + sched.getEnablingAction()
					+ "\" dst=\"" + sched.getEndState() + "\" src=\""
					+ sched.getInitState() + "\"/>\n";
		}
		s += "</fsm>\n";
		return s;
	}
	
	public Map<State, Set<Var>> getStateToRelevantVars() {
		return stateToRelevantVars;
	}

	public void setSchedulingVars(Set<Var> stateVars) {
		schedulingVars=stateVars;		
	}
}
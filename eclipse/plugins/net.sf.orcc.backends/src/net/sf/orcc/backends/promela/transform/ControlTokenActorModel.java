package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.backends.promela.transform.ControlTokenLinkModel;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Var;

public class ControlTokenActorModel {
	private Actor actor;
	private Map<Port, ControlTokenLinkModel> inLinks = new HashMap<Port, ControlTokenLinkModel>();
	private Map<ControlTokenLinkModel, Port> outLinks = new HashMap<ControlTokenLinkModel, Port>();
	private Map<Var, Set<Var>> variableDependency = new HashMap<Var, Set<Var>>();
	private Map<Port, Set<Var>> outputPortToVarsMap = new HashMap<Port, Set<Var>>();
	private Map<Var, Port> varToInputPortMap = new HashMap<Var, Port>();
	private Set<Var> localSchedulingVars = new HashSet<Var>();
	private Set<Var> extSchedulingVars = new HashSet<Var>();
	private Set<Var> schedOutPortVars = new HashSet<Var>();

	public ControlTokenActorModel(Actor actor) {
		this.actor = actor;
		// make some helper maps
		for (Action action : actor.getActions()) {
			for (Port port : action.getOutputPattern().getPorts()) {
				if (!outputPortToVarsMap.containsKey(port)) {
					outputPortToVarsMap.put(port, new HashSet<Var>());
				}
				outputPortToVarsMap.get(port).add(
						action.getOutputPattern().getVariable(port));
			}
			for (Port port : action.getInputPattern().getPorts()) {
				varToInputPortMap.put(action.getInputPattern()
						.getVariable(port), port);
			}
			for (Port port : action.getPeekPattern().getPorts()) {
				varToInputPortMap.put(
						action.getPeekPattern().getVariable(port), port);
			}
		}
	}

	public void addExtSchedulingDepVars(Port outputPort) {
		for (Var v : outputPortToVarsMap.get(outputPort)) {
			this.schedOutPortVars.add(v);
			this.extSchedulingVars.add(v);
			this.extSchedulingVars.addAll(getReachableVars(v));
		}
		buildInterActorDependencies();
	}

	public void addInLink(ControlTokenLinkModel link, Port port) {
		inLinks.put(port, link);
	}

	public void addOutLink(ControlTokenLinkModel link, Port port) {
		outLinks.put(link, port);
	}

	public void addVarDep(Var target, Var source) {
		if (!variableDependency.containsKey(target)) {
			variableDependency.put(target, new HashSet<Var>());
		}
		variableDependency.get(target).add(source);
	}

	public void addVarUsedByGuard(Var var) {
		localSchedulingVars.add(var);
	}

	public void buildInterActorDependencies() {
		Set<Var> depsSet = new HashSet<Var>();
		for (Var var : localSchedulingVars) {
			depsSet.addAll(getReachableVars(var));
		}
		localSchedulingVars.addAll(depsSet);
		for (Port ip : getSchedulingInputPorts()) {
			inLinks.get(ip).setControlLink(true);
			ControlTokenActorModel remoteActor = inLinks.get(ip).getSource();
			Port remotePort = inLinks.get(ip).getConnection().getSourcePort();
			if (remoteActor != null) {
				remoteActor.addExtSchedulingDepVars(remotePort);
			}
		}
	}

	public Actor getActor() {
		return actor;
	}

	public Set<Var> getExtSchedulingVars() {
		return extSchedulingVars;
	}

	public Set<Var> getLocalSchedulingVars() {
		return localSchedulingVars;
	}

	public Set<Var> getReachableVars(Var var) {
		Set<Var> vars = new HashSet<Var>();
		getTransitiveClosure(var, vars);
		return vars;
	}

	public Set<Var> getSchedOutPortVars() {
		return schedOutPortVars;
	}

	private Set<Port> getSchedulingInputPorts() {
		Set<Port> ports = new HashSet<Port>();
		for (Var v : localSchedulingVars) {
			if (varToInputPortMap.containsKey(v)) {
				ports.add(varToInputPortMap.get(v));
			}
		}
		for (Var v : extSchedulingVars) {
			if (varToInputPortMap.containsKey(v)) {
				ports.add(varToInputPortMap.get(v));
			}
		}
		return ports;
	}

	private void getTransitiveClosure(Var variable, Set<Var> tc) {
		if (variableDependency.containsKey(variable)) {
			for (Var v : variableDependency.get(variable)) {
				if (!tc.contains(v)) {
					tc.add(v);
					getTransitiveClosure(v, tc);
				}
			}
		}
	}

	public boolean isPort(Var var) {
		if (varToInputPortMap.containsKey(var)) {
			return true;
		}
		for (Set<Var> vars : outputPortToVarsMap.values()) {
			if (vars.contains(var)) {
				return true;
			}
		}
		return false;
	}

}
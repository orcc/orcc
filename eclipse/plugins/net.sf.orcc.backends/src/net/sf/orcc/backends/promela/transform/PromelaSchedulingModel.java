package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Var;

public class PromelaSchedulingModel {

	private Set<ControlTokenActorModel> actorModels = new HashSet<ControlTokenActorModel>();
	private Set<ControlTokenLinkModel> linkModels = new HashSet<ControlTokenLinkModel>();
	//private Set<SVariable> sVariables = new HashSet<SVariable>();
	
	private Map<Actor, ControlTokenActorModel> actorToModelMap = new HashMap<Actor, ControlTokenActorModel>();
	private Map<Connection, ControlTokenLinkModel> connectiontoModelMap = new HashMap<Connection, ControlTokenLinkModel>();

	
	
	public PromelaSchedulingModel(Network network) {
		for (Actor a : network.getAllActors()) {
			ControlTokenActorModel am = new ControlTokenActorModel(a);
			actorModels.add(am);
			actorToModelMap.put(a, am);
			for (Edge e : a.getIncoming()) {
				Connection c = (Connection)e;
				ControlTokenLinkModel lm;
				if (!connectiontoModelMap.containsKey(c)) {
					lm = new ControlTokenLinkModel((Connection)c);
					linkModels.add(lm);
					connectiontoModelMap.put(c, lm);
				}
				lm = connectiontoModelMap.get(c);
				am.addInLink(lm, c.getTargetPort());
				lm.setTarget(am);
			}
			for (Edge e : a.getOutgoing()) {
				Connection c = (Connection)e;
				ControlTokenLinkModel lm;
				if (!connectiontoModelMap.containsKey(c)) {
					lm = new ControlTokenLinkModel((Connection)c);
					linkModels.add(lm);
					connectiontoModelMap.put(c, lm);
				}
				lm = connectiontoModelMap.get(c);
				am.addOutLink(lm, c.getSourcePort());
				lm.setSource(am);
			}
		}
	}
	
	public Set<Var> getAllSchedulingVars() {
		Set<Var> variables = new HashSet<Var>();
		for (ControlTokenActorModel am : actorModels) {
			variables.addAll(am.getLocalSchedulingVars());
			variables.addAll(am.getExtSchedulingVars());
		}
		return variables;
	}
	
	
	public void addVarDep(Actor a, Var target, Var source) {
		actorToModelMap.get(a).addVarDep(target, source);
	}
	
	
	public void addVarDep(Actor a, Var target, Set<Var> source, boolean isIfCondition) {
		if(isIfCondition)return;
		for (Var s : source) {
			actorToModelMap.get(a).addVarDep(target, s);
		}
	}
	
	public void addVarUsedInScheduler(Actor a, Var var) {
		actorToModelMap.get(a).addVarUsedByGuard(var);
	}
	
	public ControlTokenActorModel getActorModel(Actor actor) {
		return actorToModelMap.get(actor);
	}

	
	/**
	 * Rather long method that prints the model in dot format. Should at some point be replaced by something more neat.
	 */
	public void printDependencyGraph() {
		for (ControlTokenActorModel am : actorModels) {
			am.buildInterActorDependencies();
		}
		System.out.println("digraph G {");
		for (ControlTokenActorModel am : actorModels) {
			Set<Var> actorVars = new HashSet<Var>();
			actorVars.addAll(am.getLocalSchedulingVars());
			actorVars.addAll(am.getSchedOutPortVars());
			System.out.println(am.getActor().getSimpleName()+"_grds [label=guards, shape=box]");
			for (Var v : am.getLocalSchedulingVars()) {
				if ((v.isGlobal()||am.isPort(v)) && v.isAssignable()) {
					if (am.isPort(v)) {
						System.out.println(am.getActor().getSimpleName() + "_" +  v.getName() + "[label="+ v.getName() + ", style=rounded,filled, shape=diamond]");
					} else {
						System.out.println(am.getActor().getSimpleName() + "_" +  v.getName() + "[label="+ v.getName() + "]");
					}
					System.out.println(am.getActor().getSimpleName()+"_grds ->" + am.getActor().getSimpleName() + "_" +  v.getName());
					// All scheduling vars
					for (Var dep : am.getReachableVars(v)) {
						if ((dep.isGlobal()||am.isPort(dep)) && dep.isAssignable()) {
							actorVars.add(dep);
							if (am.isPort(dep)) {
								System.out.println(am.getActor().getSimpleName() + "_" +dep.getName() + "[label="+ dep.getName() + ",style=rounded,filled, shape=diamond]");
							} else {
								System.out.println(am.getActor().getSimpleName() + "_" +dep.getName() + "[label="+ dep.getName() +"]");
							}
							System.out.println(am.getActor().getSimpleName() + "_" + v.getName() + " -> " + am.getActor().getSimpleName() + "_" +dep.getName());
							// if the vars depend on input or them selves: draw some more arrows
							for (Var depdep : am.getReachableVars(dep)) {
								if (dep == depdep || am.isPort(depdep)) {
									System.out.println(am.getActor().getSimpleName() + "_" + dep.getName() + " -> " + am.getActor().getSimpleName() + "_" +depdep.getName());
								}
							}
						}
					}
				}
			}
			for (Var v : am.getSchedOutPortVars()) {
				System.out.println(am.getActor().getSimpleName() + "_" +  v.getName() + "[label="+ v.getName() + ",style=rounded,filled, shape=diamond]");
				for (Var dep : am.getReachableVars(v)) {
					if ((dep.isGlobal()||am.isPort(dep)) && dep.isAssignable()) {
						actorVars.add(dep);
						if (am.isPort(dep)) {
							System.out.println(am.getActor().getSimpleName() + "_" +  dep.getName() + "[label="+ dep.getName() + ",style=rounded,filled, shape=diamond]");
						} else {
							System.out.println(am.getActor().getSimpleName() + "_" +  dep.getName() + "[label="+ dep.getName() + "]");
						}
						System.out.println(am.getActor().getSimpleName() + "_" + v.getName() + " -> " + am.getActor().getSimpleName() + "_" +dep.getName());
					}
				}
			}
			System.out.print("subgraph cluster_" +am.getActor().getSimpleName()+ " {"+am.getActor().getSimpleName()+ "_grds ");
			for (Var var : actorVars) {
				if ((var.isGlobal()||am.isPort(var)) && var.isAssignable()) {
					System.out.print(" " +am.getActor().getSimpleName() + "_" +  var.getName() + " ");
				}
			}
			System.out.println("; label = " + am.getActor().getSimpleName() + " } ");
		}
		// print the inter actor links
		for (ControlTokenLinkModel lm : linkModels) {
			if (lm.isControlLink()) {
				if (lm.getTarget() != null) {
					System.out.print(lm.getTarget().getActor().getSimpleName()+"_"+lm.getTargetPort().getName());
				} else {
					System.out.print("network_out ");
				} 
				System.out.print(" -> ");
				if (lm.getSource() != null) {
					System.out.println(lm.getSource().getActor().getSimpleName()+"_"+lm.getSourcePort().getName());
				} else {
					System.out.println(" network_in");
				} 
				
			}
		}
		System.out.println("}");
	}
	
}
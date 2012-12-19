package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Var;

public class PromelaSchedulingModel {

	private Set<ActorModel> actorModels = new HashSet<ActorModel>();
	private Set<LinkModel> linkModels = new HashSet<LinkModel>();
	//private Set<SVariable> sVariables = new HashSet<SVariable>();
	
	private Map<Actor, ActorModel> actorToModelMap = new HashMap<Actor, ActorModel>();
	private Map<Connection, LinkModel> connectiontoModelMap = new HashMap<Connection, LinkModel>();

	
	
	@SuppressWarnings("deprecation")
	public PromelaSchedulingModel(Network network) {
		for (Instance i : network.getInstances()) {
			Actor a = i.getActor();
			ActorModel am = new ActorModel(a);
			actorModels.add(am);
			actorToModelMap.put(a, am);
			for (Edge e : i.getIncoming()) {
				Connection c = (Connection)e;
				LinkModel lm;
				if (!connectiontoModelMap.containsKey(c)) {
					lm = new LinkModel((Connection)c);
					linkModels.add(lm);
					connectiontoModelMap.put(c, lm);
				}
				lm = connectiontoModelMap.get(c);
				am.addInLink(lm, c.getTargetPort());
				lm.setTarget(am);
			}
			for (Edge e : i.getOutgoing()) {
				Connection c = (Connection)e;
				LinkModel lm;
				if (!connectiontoModelMap.containsKey(c)) {
					lm = new LinkModel((Connection)c);
					linkModels.add(lm);
					connectiontoModelMap.put(c, lm);
				}
				lm = connectiontoModelMap.get(c);
				am.addOutLink(lm, c.getSourcePort());
				lm.setSource(am);
			}
		}
	}
	
	
	public void addVarDep(Actor a, Var target, Var source) {
		actorToModelMap.get(a).addVarDep(target, source);
	}
	
	public void addVarDep(Actor a, Var target, Set<Var> source) {
		for (Var s : source) {
			actorToModelMap.get(a).addVarDep(target, s);
		}
	}
	
	public void addVarUsedInScheduler(Actor a, Var var) {
		actorToModelMap.get(a).addVarUsedByGuard(var);
	}
	
	private class ActorModel {
		private Actor actor;
		private Map<Port, LinkModel> inLinks = new HashMap<Port, LinkModel>();
		private Map<LinkModel, Port> outLinks = new HashMap<LinkModel, Port>();
		private Map<Var, Set<Var>> variableDependency = new HashMap<Var, Set<Var>>();
		private Map<Port, Set<Var>> outputPortToVarsMap = new HashMap<Port, Set<Var>>();
		private Map<Var, Port> varToInputPortMap = new HashMap<Var, Port>();
		private Set<Var> localSchedulingVars = new HashSet<Var>();
		private Set<Var> extSchedulingVars = new HashSet<Var>();
		private Set<Var> schedOutPortVars = new HashSet<Var>(); 
		
		public boolean isPort(Var var) {
			if (varToInputPortMap.containsKey(var)) {
				return true;
			} 
			for (Set<Var> vars : outputPortToVarsMap.values()) {
				if(vars.contains(var)) {
					return true;
				}
			}
			return false;
		}
		
		private Set<Port> getSchedulingInputPorts() {
			Set<Port> ports = new HashSet<Port>();
			for (Var v: localSchedulingVars) {
				if (varToInputPortMap.containsKey(v)) {
					ports.add(varToInputPortMap.get(v));
				}
			}
			for (Var v: extSchedulingVars) {
				if (varToInputPortMap.containsKey(v)) {
					ports.add(varToInputPortMap.get(v));
				}
			}
			return ports;
		}
		
		public void buildInterActorDependencies() {
			Set<Var> depsSet = new HashSet<Var>();
			for (Var var : localSchedulingVars) {
				depsSet.addAll(getReachableVars(var));
			}
			localSchedulingVars.addAll(depsSet);
			for (Port ip : getSchedulingInputPorts()) {
				inLinks.get(ip).setControlLink(true);
				ActorModel remoteActor = inLinks.get(ip).getSource();
				Port remotePort = inLinks.get(ip).getConnection().getSourcePort();
				if (remoteActor != null) {remoteActor.addExtSchedulingDepVars(remotePort);}
			}
		}
		
		@SuppressWarnings("unused")
		public Set<Var> getExtSchedulingVars() {
			return extSchedulingVars;
		}

		public void addExtSchedulingDepVars(Port outputPort) {
			for (Var v : outputPortToVarsMap.get(outputPort)) {
				this.schedOutPortVars.add(v);
				this.extSchedulingVars.add(v);
				this.extSchedulingVars.addAll(getReachableVars(v));
			}
			buildInterActorDependencies();
		}

		public Set<Var> getLocalSchedulingVars() {
			return localSchedulingVars;
		}
		
		public Set<Var> getSchedOutPortVars() {
			return schedOutPortVars;
		}
		
		/*public Set<Port> getConnectedInputPorts(Port outputPort) {
			Set<Var> vars = outputPortToVarsMap.get(outputPort);
			Set<Port> inPorts = new HashSet<Port>();
			for (Var portVar : vars) {
				for (Var var : getReachableVars(portVar)) {
					if (varToInputPortMap.containsKey(var)) {
						inPorts.add(varToInputPortMap.get(var));
					}
				}
			}
			return inPorts;
		}*/

		public ActorModel(Actor actor) {
			this.actor = actor;
			// make some helper maps
			for (Action action : actor.getActions()) {
				for (Port port : action.getOutputPattern().getPorts()) {
					if (!outputPortToVarsMap.containsKey(port)) {
						outputPortToVarsMap.put(port, new HashSet<Var>());
					}
					outputPortToVarsMap.get(port).add(action.getOutputPattern().getVariable(port));
				}
				for (Port port : action.getInputPattern().getPorts()) {
					varToInputPortMap.put(action.getInputPattern().getVariable(port), port);
				}
				for (Port port : action.getPeekPattern().getPorts()) {
					varToInputPortMap.put(action.getPeekPattern().getVariable(port), port);
				}
			}
		}
		
		public void addInLink(LinkModel link, Port port){
			inLinks.put(port, link);			
		}
		
		public void addOutLink(LinkModel link, Port port){
			outLinks.put(link, port);			
		}
		
		public Actor getActor() {
			return actor;
		}
		
		public void addVarDep(Var target, Var source) {
			if (!variableDependency.containsKey(target)){
				variableDependency.put(target, new HashSet<Var>());
			}
			variableDependency.get(target).add(source);
		}
		
		public void addVarUsedByGuard(Var var) {
			localSchedulingVars.add(var);
		}
		
		public Set<Var> getReachableVars(Var var) {
			Set<Var> vars = new HashSet<Var>();
			getTransitiveClosure(var, vars);
			return vars;
		}
		
		//public Set<Var> getReachableVars(Port port) {
		//	Set<Var> vars = new HashSet<Var>();
		//	for (Var var : outputPortToVarsMap.get(port)) {
		//		getTransitiveClosure(var, vars);
		//	}
		//	return vars;
		//}
		
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
		
	}
	
	private class LinkModel {
		private Connection connection;
		private ActorModel source, target;
		private boolean controlLink = false;
		
		public boolean isControlLink() {
			return controlLink;
		}

		public void setControlLink(boolean carriesControl) {
			this.controlLink = carriesControl;
		}

		public Connection getConnection() {
			return connection;
		}
		
		//private int maxputrate = 0;
		//private int maxgetrate = 0;
		
		public LinkModel(Connection c) {
			this.connection = c;
		}

		public ActorModel getSource() {
			return this.source;
		}
		
		public Port getSourcePort() {
			return this.connection.getSourcePort();
		}
		
		public void setSource(ActorModel source) {
			this.source = source;
		}

		public ActorModel getTarget() {
			return target;
		}

		public void setTarget(ActorModel target) {
			this.target = target;
		}
		
		public Port getTargetPort() {
			return this.connection.getTargetPort();
		}
	}
	
	/*private class Scenario {
		private State initState;	
	}*/
	
	/**
	 * Rather long method that prints the model in dot format. Should at some point be replaced by something more neat.
	 */
	public void printDependencyGraph() {
		for (ActorModel am : actorModels) {
			am.buildInterActorDependencies();
		}
		System.out.println("digraph G {");
		for (ActorModel am : actorModels) {
			Set<Var> actorVars = new HashSet<Var>();
			actorVars.addAll(am.getLocalSchedulingVars());
			actorVars.addAll(am.getSchedOutPortVars());
			System.out.println(am.getActor().getSimpleName()+"_grds [label=guards, shape=box]");
			for (Var v : am.getLocalSchedulingVars()) {
				if ((v.isGlobal()||am.isPort(v)) && v.isAssignable()) {
					System.out.println(am.getActor().getSimpleName() + "_" +  v.getName() + "[label="+ v.getName() + "]");
					System.out.println(am.getActor().getSimpleName()+"_grds ->" + am.getActor().getSimpleName() + "_" +  v.getName());
					for (Var dep : am.getReachableVars(v)) {
						if ((dep.isGlobal()||am.isPort(dep)) && dep.isAssignable()) {
							actorVars.add(dep);
							System.out.println(am.getActor().getSimpleName() + "_" + v.getName() + " -> " + am.getActor().getSimpleName() + "_" +dep.getName());
						}
					}
				}
			}
			for (Var v : am.getSchedOutPortVars()) {
				//if (v.isAssignable()) {
					System.out.println(am.getActor().getSimpleName() + "_" +  v.getName() + "[label="+ v.getName() + "]");
					for (Var dep : am.getReachableVars(v)) {
						if ((dep.isGlobal()||am.isPort(dep)) && dep.isAssignable()) {
							actorVars.add(dep);
							System.out.println(am.getActor().getSimpleName() + "_" + v.getName() + " -> " + am.getActor().getSimpleName() + "_" +dep.getName());
						}
					}
				//}
			}
			System.out.print("subgraph cluster_" +am.getActor().getSimpleName()+ " {"+am.getActor().getSimpleName()+ "_grds ");
			for (Var var : actorVars) {
				if ((var.isGlobal()||am.isPort(var)) && var.isAssignable()) {
					System.out.print(", " +am.getActor().getSimpleName() + "_" +  var.getName() + " ");
				}
			}
			System.out.println("; label = " + am.getActor().getSimpleName() + " } ");
		}
		// print the inter actor links
		for (LinkModel lm : linkModels) {
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
/*
 * Copyright (c) 2013, Abo Akademi University
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * 
 * 
 * @author Johan Ersfolk
 * 
 */

public class IdentifyStatelessActors extends DfVisitor<Void> {
	
	private class ActionBeh {
		
		private Set<Write> writes = new HashSet<Write>();
		
		private Set<Var> resetVars = new HashSet<Var>();

		private Set<Var> outputVars = new HashSet<Var>();
		
		private Set<Var> inputVars = new HashSet<Var>();
		
		private Set<Var> updateVars = new HashSet<Var>();
		
		private Set<Var> usedVars = new HashSet<Var>();
		
		public void buildStats() {
			for (Write w : writes) {
				if (!w.isPort(w.target)) {
					if (w.sources.size()==0) { resetVars.add(w.target); }
					for (Var v : w.sources) {
						if (w.isPort(v)) { inputVars.add(w.target); }
						else if (w.target==v) { updateVars.add(v); }
					}
				} else { 
					for (Var v : w.sources) {
						if (!w.isPort(v)) { outputVars.add(v);}
					}
				}
				usedVars.addAll(w.sources);
			}
		}

	}
	
	private class Write {
		Action action;
		Var target;
		Set<Var> sources;
		
		public Write(Action action, Var target) {
			this.action=action;
			this.target=target;
			sources = new HashSet<Var>();
		}
		
		public boolean isPort(Var var){
			if (action.getOutputPattern().getVariables().contains(var)) {
				return true;
			} else if (action.getInputPattern().getVariables().contains(var)){
				return true;
			}
			return false;
		}
		
		public void removeLocalAndConstantVars() {
			Iterator<Var> i = sources.iterator();
			while (i.hasNext()) {
				Var v = i.next();
				if (!(isPort(v) || actor.getStateVars().contains(v)&&v.isAssignable())) {
					i.remove();
				}
			}
		}
		
		@Override
		public String toString() {
			String s = "Action "+action.getName()+" Store-var: "+target.getName();
			if (isPort(target)) {
				s+="(P) -> {";
			} else {
				s+="(V) -> {";
			}		
			for (Var v : sources) {
				s+=v.getName();
				if (isPort(v)) {
					s+="(P), ";
				} else {
					s+="(V), ";
				}
			}
			s+="}";
			return s;
		}
	}

	private class InnerIrVisitor extends AbstractIrVisitor<Void> {

		private Write current;
		
		public InnerIrVisitor() {
			super(false);
		}

		@Override
		public Void caseExprVar(ExprVar var) {
			current.sources.add(var.getUse().getVariable());
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			current=new Write(action, store.getTarget().getVariable());
			doSwitch(store.getValue());
			Set<Var>tempSet=new HashSet<Var>();
			for (Var v : current.sources) {
				tempSet.addAll(varGraph.getReachableVars(v));
			}
			current.sources.addAll(tempSet);
			current.removeLocalAndConstantVars();
			if (!actionBehaviourMap.containsKey(action)) {
				actionBehaviourMap.put(action, new ActionBeh());
			}
			actionBehaviourMap.get(action).writes.add(current);
			return null;
		}
	}
	
	private VariableGraph varGraph;

	private Action action;
	
	private Map<Action, ActionBeh> actionBehaviourMap = new HashMap<Action, ActionBeh>();

	private Set<Var> pureControlVars;
	
	public IdentifyStatelessActors(VariableGraph varGraph) {
		super();
		this.varGraph=varGraph;
		this.irVisitor = new InnerIrVisitor();
	}

	@Override
	public Void caseAction(Action action) {
		this.action = action;
		actionBehaviourMap.put(action, new ActionBeh());
		doSwitch(action.getBody());
		for (Var var : action.getOutputPattern().getVariables()) {
			Write current=new Write(action, var);
			current.sources.addAll(varGraph.getReachableVars(var));
			current.removeLocalAndConstantVars();
			actionBehaviourMap.get(action).writes.add(current);
		}
		actionBehaviourMap.get(action).buildStats();
		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		this.actor = actor;
		
		for (Action action : actor.getActions()) {
			doSwitch(action);
			//System.out.println(actionToStateChanges.get(action));
		}
		// find the vars not connected to output
		pureControlVars = new HashSet<Var>(actor.getStateVars());
		for (ActionBeh ab : actionBehaviourMap.values()) {
			pureControlVars.removeAll(ab.outputVars);
		}
		isStatelessLevel0();
		isStatelessLevel1();

		return null;
	}
	
	private boolean isStatelessLevel0() {
		if (actor.getStateVars().size()==0) {
			System.out.println("Actor "+actor.getName()+" Is stateless level 0 (no state vars)");
			return true;
		}
		return false;
	}
	
	private boolean isStatelessLevel1() {
		for (Action action : actionBehaviourMap.keySet()) {
			for (Write sc : actionBehaviourMap.get(action).writes){
				if (sc.isPort(sc.target)) {
					for (Var var : sc.sources) {
						if (!sc.isPort(var)) {
							return false;
						}
					}
				}
			}
		}
		System.out.println("Actor "+actor.getName()+" Is stateless level 1 (output does not depend on state vars)");
		return true;
	}

	
	private Set<Var> getStateTransfer(Action action1, Action action2) {
		Set<Var> state1 = new HashSet<Var>();
		Set<Var> state2 = new HashSet<Var>();
		state1.addAll(actionBehaviourMap.get(action1).resetVars);
		state1.addAll(actionBehaviourMap.get(action1).updateVars);
		state1.addAll(actionBehaviourMap.get(action1).inputVars);
		state2.addAll(actionBehaviourMap.get(action2).usedVars);
		state1.retainAll(state2);
		return state1;
	}
	
	public boolean isActionSequenceStateless(List<Action> actions) {
		// the variables that the first sets and the second uses
		for (int i = 0, j = 1; j < actions.size(); i++, j++) {
			Action action1 = actions.get(i);
			Action action2 = actions.get(j);
			Set<Var> transfer = getStateTransfer(action1, action2);
			for (Var var : transfer) {
				if (actionBehaviourMap.get(action1).inputVars.contains(var)) {
					System.out.println("Actions "+ action1.getName() + " and " + action2.getName() + " cannot run in parallel");
					return false;
				}
			}
			System.out.println("Actions "+ action1.getName() + " and " + action2.getName() + " share state "+transfer);
		}
		return false;
	}

}

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * This class extracts the variables/ports needed to schedule an actor.
 * 
 * @author Johan Ersfolk
 * 
 */
public class VariableGraph extends DfVisitor<Void> {

	private Stack<Set<Var>> ifConditionVars = new Stack<Set<Var>>();

	private Stack<Set<Var>> whileConditionVars = new Stack<Set<Var>>();
	
	private Var currentTargetVar = null;

	private boolean inIfCondition = false;

	private boolean inWhileCondition = false;

	private boolean inScheduler = false;

	protected Map<Var, Set<Var>> variableDependency = new HashMap<Var, Set<Var>>();

	protected Map<Var, Set<Var>> variableDependencyNoIf = new HashMap<Var, Set<Var>>();

	protected Set<Var> localSchedulingVars = new HashSet<Var>();

	private Set<Var> varsFromNativeProcedures = new HashSet<Var>();
	
	@SuppressWarnings("unused")
	private class Dependency {
		Var source;
		Var target;
		boolean isIf;
		boolean isPort;
	}
	

	public VariableGraph(Actor actor) {
		this.irVisitor = new InnerIrVisitor();
		this.actor = actor;
		doSwitch(actor);
	}

	/*
	 * Also adds "target" 'depends on' condition, this is used if the variable
	 * in set within a loop or if-statement as the value of the target variable
	 * the also depends on the condition.
	 */
	private void addTargetVar(Var target) {
		currentTargetVar = target;
		if (!variableDependency.containsKey(target)) {
			variableDependency.put(target, new HashSet<Var>());
			variableDependencyNoIf.put(target, new HashSet<Var>());
		}
		for (Set<Var> s : whileConditionVars) {
			variableDependency.get(target).addAll(s);
			variableDependencyNoIf.get(target).addAll(s);
		}
		for (Set<Var> s : ifConditionVars) {
			variableDependency.get(target).addAll(s);
		}
	}

	/*
	 * Adds the "target" 'depends on' "source" to the relation.
	 */
	private void addVariableDep(Var target, Var source) {
		if (!variableDependency.containsKey(target)) {
			variableDependency.put(target, new HashSet<Var>());
			variableDependencyNoIf.put(target, new HashSet<Var>());
		}
		variableDependency.get(target).add(source);
		variableDependencyNoIf.get(target).add(source);
	}

	@Override
	public Void caseAction(Action action) {
		// solve the port dependency, procedures and functions should also be
		// handled
		inScheduler = true;
		doSwitch(action.getScheduler());
		doSwitch(action.getPeekPattern());
		inScheduler = false;
		doSwitch(action.getBody());
		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		for (Action init : actor.getInitializes()) {
			doSwitch(init);
		}
		for (Action action : actor.getActions()) {
			doSwitch(action);
		}
		return null;
	}


	@Override
	public Void casePattern(Pattern pattern) {
		// Only Peek patterns will end up here, however this is done on visit actor
		//inputPortsUsedInScheduling.addAll(pattern.getPorts());
		localSchedulingVars.addAll(pattern.getVariables());
		return null;
	}
	
	public Set<Var> getReachableVars(Var var) {
		Set<Var> vars = new HashSet<Var>();
		getTransitiveClosure(var, vars, true);
		return vars;
	}

	public void getTransitiveClosure(Var variable, Set<Var> transitiveClosure, boolean includeIfConditions) {
		Map<Var, Set<Var>> varDep = includeIfConditions ? variableDependency : variableDependencyNoIf;
		if (varDep.containsKey(variable)) {
			for (Var v : varDep.get(variable)) {
				if (!transitiveClosure.contains(v)) {
					transitiveClosure.add(v);
					getTransitiveClosure(v, transitiveClosure, includeIfConditions);
				}
			}
		}
	}
	
	/**
	 * @return the variableDependency
	 */
	public Map<Var, Set<Var>> getVariableDependency() {
		return variableDependency;
	}

	public boolean hasLoop(Var var) {
		Set<Var> tc = new HashSet<Var>();
		getTransitiveClosure(var, tc, true);
		if (tc.contains(var)) {return true;}
		return false;
	}
	
	/**
	 * @return the varsUsedInScheduling
	 */
	public Set<Var> getVarsDirectlyUsedInScheduling() {
		return localSchedulingVars;
	}

	public Set<Var> getAllReacableSchedulingVars() {
		Set<Var> variables = new HashSet<Var>(localSchedulingVars);
		for (Var v : localSchedulingVars) {
			Set<Var> tc = new HashSet<Var>();
			getTransitiveClosure(v, tc, true);
			variables.addAll(tc);
		}
		return variables;
	}
	
	public Set<Var> getVarsFromNativeProcedures() {
		return varsFromNativeProcedures;
	}

	
	private class InnerIrVisitor extends AbstractIrVisitor<Void> {
		public InnerIrVisitor() {
			super(true);
		}

		@Override
		public Void caseBlockIf(BlockIf nodeIf) {
			ifConditionVars.push(new HashSet<Var>());
			inIfCondition = true;
			doSwitch(nodeIf.getCondition());
			inIfCondition = false;
			doSwitch(nodeIf.getThenBlocks());
			doSwitch(nodeIf.getElseBlocks());
			ifConditionVars.pop();
			doSwitch(nodeIf.getJoinBlock());
			return null;
		}

		@Override
		public Void caseBlockWhile(BlockWhile nodeWhile) {
			whileConditionVars.push(new HashSet<Var>());
			inWhileCondition = true;
			doSwitch(nodeWhile.getCondition());
			inWhileCondition = false;
			doSwitch(nodeWhile.getBlocks());
			whileConditionVars.pop();
			doSwitch(nodeWhile.getJoinBlock());
			return null;
		}

		@Override
		public Void caseExprVar(ExprVar var) {
			if (inIfCondition) {
				ifConditionVars.peek().add(var.getUse().getVariable());
			} else if (inWhileCondition) {
				whileConditionVars.peek().add(var.getUse().getVariable());
			} else {
				addVariableDep(currentTargetVar, var.getUse().getVariable());
			}
			return null;
		}
		
		@Override
		public Void caseInstAssign(InstAssign assign) {
			addTargetVar(assign.getTarget().getVariable());
			super.caseInstAssign(assign);
			currentTargetVar = null;
			return null; 
		}
		
		@Override
		public Void caseInstReturn(InstReturn inst) {
			// skip returns
			return null;
		}

		@Override
		public Void caseInstCall(InstCall call) {
			if (call.hasResult()) {
				addTargetVar(call.getTarget().getVariable());
				if (call.getProcedure().isNative()) {
					varsFromNativeProcedures.add(call.getTarget().getVariable());
				}
			}
			return super.caseInstCall(call);
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			addVariableDep(load.getTarget().getVariable(), load.getSource()
					.getVariable());
			for (Expression e : load.getIndexes()) {
				doSwitch(e);
			}
			if (inScheduler) {
				// this might not be needed as 'casePattern' should do the same
				localSchedulingVars.add(load.getSource().getVariable());
			}
			return null;
		}

		@Override
		public Void caseInstPhi(InstPhi phi) {
			addTargetVar(phi.getTarget().getVariable());
			return super.caseInstPhi(phi);
		}

		@Override
		public Void caseInstStore(InstStore store) {
			addTargetVar(store.getTarget().getVariable());
			doSwitch(store.getValue());
			for (Expression e : store.getIndexes()) {
				doSwitch(e);
			}
			return null;
		}
	}
	
}

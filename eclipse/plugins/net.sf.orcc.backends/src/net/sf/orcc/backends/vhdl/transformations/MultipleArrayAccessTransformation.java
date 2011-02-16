/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.IfNode;

/**
 * This transformation transforms an actor so that there is at most one access
 * (read or write) per each given array accessed by an action. This pass should
 * be run after inline.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class MultipleArrayAccessTransformation extends ActionSplitter {

	/**
	 * This class extends the abstract branch visitor by splitting "if"
	 * conditionals if the "then" branch or the "else" branch contain more than
	 * one access to a given array.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConditionalSplitter extends AbstractBranchVisitor {

		private Map<Variable, Integer> numRW;

		public ConditionalSplitter(String sourceName, String targetName) {
			super(sourceName, targetName);
			numRW = new HashMap<Variable, Integer>();
		}

		/**
		 * Returns true if one of the values is greater than one, which means an
		 * array has been found to be accessed more than once.
		 * 
		 * @return true if one of the values is greater than one
		 */
		private boolean hasManyAccesses(Map<?, Integer> map) {
			for (Integer value : map.values()) {
				if (value > 1) {
					return true;
				}
			}

			return false;
		}

		/**
		 * Splits the given IfNode into three different actions: one containing
		 * the "then" branch, one containing the "else" branch, and the final
		 * one containing the rest.
		 * 
		 * @param ifNode
		 */
		private void splitIfNode(IfNode ifNode) {
			String oldTargetName = targetName;

			// replaces existing transition by a transition to a "fork" state
			String forkSourceName = sourceName + "_fork";
			replaceTarget(sourceName, currentAction, forkSourceName);

			// remove this IfNode from action before "fork"
			itNode.remove();

			// add cond branches to a "join" state
			String joinTargetName = targetName + "_join";

			splitNodes(ifNode.getValue(), ifNode.getThenNodes().listIterator());
			addTransition(forkSourceName, joinTargetName, nextAction);

			splitNodes(new BoolExpr(true), ifNode.getElseNodes().listIterator());
			addTransition(forkSourceName, joinTargetName, nextAction);

			// add join to original target state
			splitNodes(new BoolExpr(true), itNode);
			addTransition(joinTargetName, oldTargetName, nextAction);
			sourceName = oldTargetName;
			targetName = oldTargetName;
		}

		private void splitNodes(Expression condition,
				ListIterator<CFGNode> itNode) {
			String newActionName = getNewStateName();
			nextAction = createNewAction(condition, newActionName);
			new CodeMover().moveNodes(itNode, nextAction.getBody());
		}

		@Override
		public void visit(IfNode ifNode) {
			ListIterator<CFGNode> localItNode = itNode;

			// visit then
			numRW.clear();
			visit(ifNode.getThenNodes());
			boolean thenNeedSplit = hasManyAccesses(numRW);

			// visit else
			numRW.clear();
			visit(ifNode.getElseNodes());
			boolean elseNeedSplit = hasManyAccesses(numRW);

			// check number of accesses
			if (thenNeedSplit || elseNeedSplit) {
				itNode = localItNode;
				splitIfNode(ifNode);
			}
		}

		@Override
		public void visit(Load load) {
			visitLoadStore(load.getSource().getVariable(), load.getIndexes());
		}

		@Override
		public void visit(Store store) {
			visitLoadStore(store.getTarget(), store.getIndexes());
		}

		/**
		 * Visits a load or a store, and updates the numRW map.
		 * 
		 * @param variable
		 *            a variable
		 * @param indexes
		 *            a list of indexes
		 */
		private void visitLoadStore(Variable variable, List<Expression> indexes) {
			if (!indexes.isEmpty()) {
				Integer numAccesses = numRW.get(variable);
				if (numAccesses == null) {
					numRW.put(variable, 1);
				} else {
					numRW.put(variable, numAccesses + 1);
				}
			}
		}

	}

	/**
	 * This class extends the abstract branch visitor by splitting any action
	 * that contain more than one access to a given array.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class UnconditionalSplitter extends AbstractBranchVisitor {

		private Map<Variable, Integer> numRW;

		public UnconditionalSplitter(String sourceName, String targetName) {
			super(sourceName, targetName);
			numRW = new HashMap<Variable, Integer>();
		}

		@Override
		public void visit(Load load) {
			visitLoadStore(load.getSource().getVariable(), load.getIndexes());
		}

		@Override
		public void visit(Procedure procedure) {
			numRW.clear();
			super.visit(procedure);
		}

		@Override
		public void visit(Store store) {
			visitLoadStore(store.getTarget(), store.getIndexes());
		}

		/**
		 * Visits a load or a store, and updates the numRW map.
		 * 
		 * @param variable
		 *            a variable
		 * @param indexes
		 *            a list of indexes
		 */
		private void visitLoadStore(Variable variable, List<Expression> indexes) {
			if (!indexes.isEmpty()) {
				Integer numAccesses = numRW.get(variable);
				if (numAccesses == null) {
					numRW.put(variable, 1);
				} else {
					splitAction();
				}
			}
		}

	}

	private boolean conditionalPhase;

	@Override
	public void transform(Actor actor) {
		super.transform(actor);

		// first we split conditional branches that contain multiple accesses
		conditionalPhase = true;
		visitAllActions();

		// then we split any action that contain multiple accesses
		conditionalPhase = false;
		visitAllActions();
	}

	@Override
	protected void visit(String sourceName, String targetName, Action action) {
		if (conditionalPhase) {
			new ConditionalSplitter(sourceName, targetName).visit(action);
		} else {
			new UnconditionalSplitter(sourceName, targetName).visit(action);
		}
	}

}

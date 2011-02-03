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
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
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

	private class BranchVisitor extends AbstractBranchVisitor {

		private Map<Variable, Integer> numRW;

		private Expression splitCondition;

		public BranchVisitor(String sourceName, String targetName) {
			super(sourceName, targetName);
			numRW = new HashMap<Variable, Integer>();
		}

		/**
		 * Returns true if one of the values is greater than one, which means an
		 * array has been found to be accessed more than once.
		 * 
		 * @return true if one of the values is greater than one
		 */
		private boolean hasManyAccesses() {
			for (Integer value : numRW.values()) {
				if (value > 1) {
					return true;
				}
			}

			return false;
		}

		@Override
		public void visit(Action action) {
			this.branchName = targetName + "_" + action.getName();
			nextAction = action;
			visitInBranch();
		}

		/**
		 * Visits the next action(s) without updating the branch name.
		 */
		private void visitInBranch() {
			while (nextAction != null) {
				currentAction = nextAction;
				nextAction = null;
				numRW.clear();

				visit(currentAction.getBody());
			}
		}

		@Override
		public void visit(IfNode ifNode) {
			// record condition
			Expression oldCondition = splitCondition;
			splitCondition = ifNode.getValue();

			// visit then
			numRW.clear();
			visit(ifNode.getThenNodes());
			if (hasManyAccesses()) {
				visitInBranch();

				// no need for "else" to have a split condition (because the
				// transition will be added after)
				splitCondition = null;
			} else {
				// invert split condition
				splitCondition = new UnaryExpr(UnaryOp.LOGIC_NOT,
						splitCondition, IrFactory.eINSTANCE.createTypeBool());
			}

			// visit else
			numRW.clear();
			visit(ifNode.getElseNodes());
			if (hasManyAccesses()) {
				visitInBranch();
			}

			// restore condition
			splitCondition = oldCondition;

			// join: not supposed to have any array there
			numRW.clear();
			visit(ifNode.getJoinNode());
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

					splitAction();
				}
			}
		}

	}

	@Override
	protected void visit(String sourceName, String targetName, Action action) {
		new BranchVisitor(sourceName, targetName).visit(action);
	}

}

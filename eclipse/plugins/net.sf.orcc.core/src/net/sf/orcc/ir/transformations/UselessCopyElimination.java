/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.ir.transformations;

import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.df.Action;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a transformation which remove useless copies of list
 * variables.
 * 
 * @author Hervé Yviquel
 * 
 */
public class UselessCopyElimination extends AbstractActorVisitor<Object> {

	private Action action;

	public Object caseAction(Action action) {
		this.action = action;
		doSwitch(action.getBody());
		return null;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		// Don't check complex loop
		EList<Node> nodes = nodeWhile.getNodes();
		if (!(nodes.size() == 1 && nodes.get(0).isNodeBlock())) {
			return null;
		}

		EList<Instruction> instructions = ((NodeBlock) nodes.get(0))
				.getInstructions();
		if (!(instructions.size() == 3)) {
			return null;
		}

		// Check type of inner instructions
		Instruction inst0 = instructions.get(0);
		Instruction inst1 = instructions.get(1);
		Instruction inst2 = instructions.get(2);
		if (!(inst0.isLoad() && inst1.isStore() && inst2.isAssign())) {
			return null;
		}

		// Remove only direct assignment loop
		InstLoad load = (InstLoad) inst0;
		InstStore store = (InstStore) inst1;
		InstAssign assign = (InstAssign) inst2;
		if (!(store.getValue().isExprVar()
				&& ((ExprVar) store.getValue()).getUse().getVariable() == load
						.getTarget().getVariable()
				&& store.getIndexes().size() == 1 && load.getIndexes().size() == 1)) {
			return null;
		}

		Expression index0 = store.getIndexes().get(0);
		Expression index1 = load.getIndexes().get(0);
		if (!(index0.isExprVar()
				&& index1.isExprVar()
				&& ((ExprVar) index0).getUse().getVariable() == ((ExprVar) index1)
						.getUse().getVariable() && ((ExprVar) index0).getUse()
				.getVariable() == assign.getTarget().getVariable())) {
			return null;
		}

		// TODO: Check the dimension

		Var source = load.getSource().getVariable();
		Var target = store.getTarget().getVariable();

		// Input variable cannot be used directly by a procedure in most of
		// the case
		if (action != null && action.getInputPattern().contains(source)) {
			for (Use use : target.getUses()) {
				if (EcoreHelper.getContainerOfType(use, Instruction.class)
						.isCall()) {
					return null;
				}
			}
		}

		// FIXME: Eliminate copy of output variables too
		if (action == null || !action.getOutputPattern().contains(target)) {
			// Remove the loop and propagate variable
			IrUtil.delete(nodeWhile);
			EList<Use> uses = target.getUses();
			while (!uses.isEmpty()) {
				uses.get(0).setVariable(source);
			}
		}

		return null;
	}
}

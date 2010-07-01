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
package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class converts the given actor to SSA form.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SSATransformation extends AbstractActorTransformation {

	/**
	 * This class replaces uses of a given variables by another variable
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class UseUpdater extends AbstractExpressionVisitor {

		@Override
		public void visit(VarExpr expr, Object... args) {
			Use use = expr.getVar();
			LocalVariable oldVar = (LocalVariable) use.getVariable();
			LocalVariable newVar = uses.get(oldVar.getBaseName());
			use.setVariable(newVar);
		}

	}

	/**
	 * ith branch (or 0 if we are not in a branch)
	 */
	private int branch;

	/**
	 * maps a variable name to a local variable (used when creating new
	 * definitions)
	 */
	private Map<String, LocalVariable> definitions;

	/**
	 * join node (if any)
	 */
	private BlockNode join;

	/**
	 * maps a variable name to a local variable (used when replacing uses)
	 */
	private Map<String, LocalVariable> uses;

	/**
	 * Creates a new SSA transformation.
	 */
	public SSATransformation() {
		definitions = new HashMap<String, LocalVariable>();
		uses = new HashMap<String, LocalVariable>();
	}

	/**
	 * Inserts a phi in the (current) join node.
	 * 
	 * @param oldVar
	 *            old variable
	 * @param newVar
	 *            new variable
	 */
	private void insertPhi(LocalVariable oldVar, LocalVariable newVar) {
		String name = oldVar.getBaseName();
		PhiAssignment phi = null;
		for (Instruction instruction : join.getInstructions()) {
			if (instruction.isPhi()) {
				PhiAssignment tempPhi = (PhiAssignment) instruction;
				if (tempPhi.getTarget().getBaseName().equals(name)) {
					phi = tempPhi;
					break;
				}
			}
		}

		if (phi == null) {
			LocalVariable target = newDefinition(oldVar);
			List<Use> uses = new ArrayList<Use>(2);
			phi = new PhiAssignment(new Location(), target, uses);
			phi.setOldVariable(oldVar);
			join.add(phi);

			Use use = new Use(oldVar, phi);
			uses.add(use);
			use = new Use(oldVar, phi);
			uses.add(use);
		}

		// replace use
		phi.getVars().get(branch - 1).remove();
		Use use = new Use(newVar, phi);
		phi.getVars().set(branch - 1, use);
	}

	/**
	 * Creates a new definition based on the given old variable.
	 * 
	 * @param oldVar
	 *            a variable
	 * @return a new definition based on the given old variable
	 */
	private LocalVariable newDefinition(LocalVariable oldVar) {
		String name = oldVar.getBaseName();

		// get index
		int index;
		if (definitions.containsKey(name)) {
			index = definitions.get(name).getIndex() + 1;
		} else {
			index = 1;
		}

		// create new variable
		LocalVariable newVar = new LocalVariable(oldVar.isAssignable(), index,
				oldVar.getLocation(), name, oldVar.getType());
		procedure.getLocals().put(newVar.getName(), newVar);
		definitions.put(name, newVar);
		uses.put(name, newVar);

		return newVar;
	}

	/**
	 * Replaces uses in the given expression.
	 * 
	 * @param expression
	 *            an expression
	 */
	private void replaceUses(Expression expression) {
		expression.accept(new UseUpdater());
	}

	/**
	 * Replaces uses of oldVar by newVar in the given expressions.
	 * 
	 * @param expressions
	 *            a list of expressions
	 */
	private void replaceUses(List<Expression> expressions) {
		for (Expression value : expressions) {
			value.accept(new UseUpdater());
		}
	}

	/**
	 * Restore variables that were concerned by phi assignments.
	 */
	private void restoreVariables() {
		for (Instruction instruction : join.getInstructions()) {
			PhiAssignment phi = (PhiAssignment) instruction;
			LocalVariable oldVar = phi.getOldVariable();
			uses.put(oldVar.getBaseName(), oldVar);
		}
	}

	@Override
	public void visit(Assign assign, Object... args) {
		replaceUses(assign.getValue());

		LocalVariable target = assign.getTarget();
		LocalVariable newTarget = newDefinition(target);
		assign.setTarget(newTarget);

		if (branch != 0) {
			insertPhi(target, newTarget);
		}
	}

	@Override
	public void visit(Call call, Object... args) {
		replaceUses(call.getParameters());

		LocalVariable target = call.getTarget();
		if (target != null) {
			LocalVariable newTarget = newDefinition(target);
			call.setTarget(newTarget);

			if (branch != 0) {
				insertPhi(target, newTarget);
			}
		}
	}

	@Override
	public void visit(HasTokens hasTokens, Object... args) {
		LocalVariable target = (LocalVariable) hasTokens.getTarget();
		LocalVariable newTarget = newDefinition(target);
		hasTokens.setTarget(newTarget);

		if (branch != 0) {
			insertPhi(target, newTarget);
		}
	}

	@Override
	public void visit(IfNode ifNode, Object... args) {
		BlockNode outerJoin = join;
		int outerBranch = branch;

		replaceUses(ifNode.getValue());

		join = ifNode.getJoinNode();

		branch = 1;
		visit(ifNode.getThenNodes());

		// restore variables used in phi assignments
		restoreVariables();

		branch = 2;
		visit(ifNode.getElseNodes());

		branch = 0;
		visit(ifNode.getJoinNode(), args);

		// commit phi
		BlockNode innerJoin = join;
		join = outerJoin;
		branch = outerBranch;
		commitPhi(innerJoin);
	}

	/**
	 * Commits the phi assignments in the given join node.
	 * 
	 * @param innerJoin
	 *            a BlockNode that contains phi assignments
	 */
	private void commitPhi(BlockNode innerJoin) {
		for (Instruction instruction : innerJoin.getInstructions()) {
			PhiAssignment phi = (PhiAssignment) instruction;
			LocalVariable oldVar = phi.getOldVariable();
			LocalVariable newVar = phi.getTarget();

			// updates the current value of "var"
			uses.put(oldVar.getBaseName(), newVar);

			if (join != null) {
				insertPhi(oldVar, newVar);
			}
		}
	}

	@Override
	public void visit(Load load, Object... args) {
		replaceUses(load.getIndexes());

		LocalVariable target = load.getTarget();
		LocalVariable newTarget = newDefinition(target);
		load.setTarget(newTarget);

		if (branch != 0) {
			insertPhi(target, newTarget);
		}
	}

	@Override
	public void visit(Return returnInstr, Object... args) {
		replaceUses(returnInstr.getValue());
	}

	@Override
	public void visit(Store store, Object... args) {
		replaceUses(store.getIndexes());
		replaceUses(store.getValue());
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		definitions.clear();
		uses.clear();
		super.visitProcedure(procedure);
	}

}

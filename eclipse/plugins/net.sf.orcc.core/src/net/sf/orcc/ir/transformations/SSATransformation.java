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
package net.sf.orcc.ir.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class converts the given actor to SSA form.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SSATransformation extends AbstractActorVisitor {

	/**
	 * This class replaces uses of a given variables by another variable
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class UseUpdater extends AbstractExpressionVisitor {

		@Override
		public void visit(ExprVar expr, Object... args) {
			Use use = expr.getUse();
			Var oldVar = use.getVariable();
			if (!oldVar.isGlobal()) {
				Var newVar = uses.get(oldVar.getBaseName());
				if (newVar != null) {
					// newVar may be null if oldVar is a function parameter for
					// instance
					use.setVariable(newVar);
				}
			}
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
	private Map<String, Var> definitions;

	/**
	 * join node (if any)
	 */
	private NodeBlock join;

	/**
	 * contains the current while node being treated (if any)
	 */
	private NodeWhile loop;

	/**
	 * maps a variable name to a local variable (used when replacing uses)
	 */
	private Map<String, Var> uses;

	/**
	 * Creates a new SSA transformation.
	 */
	public SSATransformation() {
		definitions = new HashMap<String, Var>();
		uses = new HashMap<String, Var>();
	}

	/**
	 * Commits the phi assignments in the given join node.
	 * 
	 * @param innerJoin
	 *            a NodeBlock that contains phi assignments
	 */
	private void commitPhi(NodeBlock innerJoin) {
		for (Instruction instruction : innerJoin.getInstructions()) {
			InstPhi phi = (InstPhi) instruction;
			Var oldVar = phi.getOldVariable();
			Var newVar = phi.getTarget();

			// updates the current value of "var"
			uses.put(oldVar.getBaseName(), newVar);

			if (join != null) {
				insertPhi(oldVar, newVar);
			}
		}
	}

	/**
	 * Find all the CFG nodes in the given node, and puts them in the given set.
	 * 
	 * @param nodes
	 *            resultant set of nodes
	 * @param node
	 *            a CFG node
	 */
	private void findNodes(Set<Node> nodes, Node node) {
		nodes.add(node);
		if (node.isIfNode()) {
			NodeIf nodeIf = (NodeIf) node;
			for (Node subNode : nodeIf.getThenNodes()) {
				findNodes(nodes, subNode);
			}

			for (Node subNode : nodeIf.getElseNodes()) {
				findNodes(nodes, subNode);
			}

			nodes.add(nodeIf.getJoinNode());
		} else if (node.isWhileNode()) {
			NodeWhile nodeWhile = (NodeWhile) node;
			for (Node subNode : nodeWhile.getNodes()) {
				findNodes(nodes, subNode);
			}
			nodes.add(nodeWhile.getJoinNode());
		}
	}

	/**
	 * Inserts a phi in the (current) join node.
	 * 
	 * @param oldVar
	 *            old variable
	 * @param newVar
	 *            new variable
	 */
	private void insertPhi(Var oldVar, Var newVar) {
		String name = oldVar.getBaseName();
		InstPhi phi = null;
		for (Instruction instruction : join.getInstructions()) {
			if (instruction.isPhi()) {
				InstPhi tempPhi = (InstPhi) instruction;
				if (tempPhi.getTarget().getBaseName().equals(name)) {
					phi = tempPhi;
					break;
				}
			}
		}

		if (phi == null) {
			Var target = newDefinition(oldVar);
			List<Expression> values = new ArrayList<Expression>(2);
			values.add(IrFactory.eINSTANCE.createExprVar(oldVar));
			values.add(IrFactory.eINSTANCE.createExprVar(oldVar));

			phi = IrFactory.eINSTANCE.createInstPhi(target, values);
			phi.setOldVariable(oldVar);
			join.add(phi);

			if (loop != null) {
				replaceUsesInLoop(oldVar, target);
			}
		}

		// replace use
		phi.getValues().set(branch - 1,
				IrFactory.eINSTANCE.createExprVar(newVar));
	}

	/**
	 * Creates a new definition based on the given old variable.
	 * 
	 * @param oldVar
	 *            a variable
	 * @return a new definition based on the given old variable
	 */
	private Var newDefinition(Var oldVar) {
		String name = oldVar.getBaseName();

		// get index
		int index;
		if (definitions.containsKey(name)) {
			index = definitions.get(name).getIndex() + 1;
		} else {
			index = 1;
		}

		// create new variable
		Var newVar = IrFactory.eINSTANCE.createVar(oldVar.getLocation(),
				oldVar.getType(), name, oldVar.isAssignable(), index);
		procedure.getLocals().put(newVar.getName(), newVar);
		definitions.put(name, newVar);

		return newVar;
	}

	/**
	 * Replaces the definition created by the given local target container.
	 * 
	 * @param cter
	 *            a local target container
	 */
	private Var replaceDef(Var target) {
		if (target == null) {
			return target;
		} else {
			String name = target.getBaseName();

			// v_old is the value of the variable before the assignment
			Var oldVar = uses.get(name);
			if (oldVar == null) {
				// may be null if the variable is used without having been
				// assigned first
				// happens with function parameters for instance
				oldVar = target;
			}

			Var newTarget = newDefinition(target);
			uses.put(name, newTarget);

			if (branch != 0) {
				insertPhi(oldVar, newTarget);
			}

			return newTarget;
		}
	}

	/**
	 * Replaces uses in the given expression.
	 * 
	 * @param expression
	 *            an expression
	 */
	private void replaceUses(Expression expression) {
		if (expression != null) {
			expression.accept(new UseUpdater());
		}
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
	 * Replaces uses of the given variable <code>oldVar</code> by uses of the
	 * given variable <code>newVar</code> in the current loop.
	 * 
	 * @param oldVar
	 *            old variable
	 * @param newVar
	 *            new variable
	 */
	private void replaceUsesInLoop(Var oldVar, Var newVar) {
		List<Use> uses = new ArrayList<Use>(oldVar.getUses());
		Set<Node> nodes = new HashSet<Node>();
		findNodes(nodes, loop);

		for (Use use : uses) {
			Node node = EcoreHelper.getContainerOfType(use, Node.class);

			// only changes uses that are in the loop
			if (node != join && nodes.contains(node)) {
				use.setVariable(newVar);
			}
		}
	}

	/**
	 * Restore variables that were concerned by phi assignments.
	 */
	private void restoreVariables() {
		for (Instruction instruction : join.getInstructions()) {
			InstPhi phi = (InstPhi) instruction;
			Var oldVar = phi.getOldVariable();
			uses.put(oldVar.getBaseName(), oldVar);
		}
	}

	@Override
	public void visit(InstAssign assign) {
		replaceUses(assign.getValue());
		assign.setTarget(replaceDef(assign.getTarget()));
	}

	@Override
	public void visit(InstCall call) {
		replaceUses(call.getParameters());
		call.setTarget(replaceDef(call.getTarget()));
	}

	@Override
	public void visit(NodeIf nodeIf) {
		int outerBranch = branch;
		NodeBlock outerJoin = join;
		NodeWhile outerLoop = loop;

		replaceUses(nodeIf.getCondition());

		join = nodeIf.getJoinNode();
		loop = null;

		branch = 1;
		visit(nodeIf.getThenNodes());

		// restore variables used in phi assignments
		restoreVariables();

		branch = 2;
		visit(nodeIf.getElseNodes());

		// commit phi
		NodeBlock innerJoin = join;
		branch = outerBranch;
		join = outerJoin;
		loop = outerLoop;
		commitPhi(innerJoin);
	}

	@Override
	public void visit(InstLoad load) {
		replaceUses(load.getIndexes());
		load.setTarget(replaceDef(load.getTarget()));
	}

	@Override
	public void visit(Procedure procedure) {
		definitions.clear();
		uses.clear();
		super.visit(procedure);
	}

	@Override
	public void visit(InstReturn returnInstr) {
		replaceUses(returnInstr.getValue());
	}

	@Override
	public void visit(InstStore store) {
		replaceUses(store.getIndexes());
		replaceUses(store.getValue());
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		int outerBranch = branch;
		NodeBlock outerJoin = join;
		NodeWhile outerLoop = loop;

		replaceUses(nodeWhile.getCondition());

		branch = 2;
		join = nodeWhile.getJoinNode();
		loop = nodeWhile;

		visit(nodeWhile.getNodes());

		// commit phi
		NodeBlock innerJoin = join;
		branch = outerBranch;
		join = outerJoin;
		loop = outerLoop;
		commitPhi(innerJoin);
	}

}

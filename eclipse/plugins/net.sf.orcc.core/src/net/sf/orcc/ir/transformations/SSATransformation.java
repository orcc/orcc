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
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.User;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;

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
		public void visit(VarExpr expr, Object... args) {
			Use use = expr.getVar();
			Variable oldVar = use.getVariable();
			if (!oldVar.isGlobal()) {
				LocalVariable newVar = uses.get(((LocalVariable) oldVar)
						.getBaseName());
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
	private Map<String, LocalVariable> definitions;

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
	private Map<String, LocalVariable> uses;

	/**
	 * Creates a new SSA transformation.
	 */
	public SSATransformation() {
		definitions = new HashMap<String, LocalVariable>();
		uses = new HashMap<String, LocalVariable>();
	}

	/**
	 * Commits the phi assignments in the given join node.
	 * 
	 * @param innerJoin
	 *            a NodeBlock that contains phi assignments
	 */
	private void commitPhi(NodeBlock innerJoin) {
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
			List<Expression> values = new ArrayList<Expression>(2);
			phi = new PhiAssignment(new Location(), target, values);
			phi.setOldVariable(oldVar);
			join.add(phi);

			Use use = new Use(oldVar, phi);
			values.add(new VarExpr(use));
			use = new Use(oldVar, phi);
			values.add(new VarExpr(use));

			if (loop != null) {
				replaceUsesInLoop(oldVar, target);
			}
		}

		// replace use
		VarExpr varExpr = (VarExpr) phi.getValues().get(branch - 1);
		varExpr.getVar().remove();
		Use use = new Use(newVar, phi);
		phi.getValues().set(branch - 1, new VarExpr(use));
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

		return newVar;
	}

	/**
	 * Replaces the definition created by the given local target container.
	 * 
	 * @param cter
	 *            a local target container
	 */
	private void replaceDef(LocalTargetContainer cter) {
		LocalVariable target = cter.getTarget();
		if (target != null) {
			String name = target.getBaseName();

			// v_old is the value of the variable before the assignment
			LocalVariable oldVar = uses.get(name);
			if (oldVar == null) {
				// may be null if the variable is used without having been
				// assigned first
				// happens with function parameters for instance
				oldVar = target;
			}

			LocalVariable newTarget = newDefinition(target);
			cter.setTarget(newTarget);

			uses.put(name, newTarget);

			if (branch != 0) {
				insertPhi(oldVar, newTarget);
			}
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
	private void replaceUsesInLoop(LocalVariable oldVar, LocalVariable newVar) {
		List<Use> uses = new ArrayList<Use>(oldVar.getUses());
		Set<Node> nodes = new HashSet<Node>();
		findNodes(nodes, loop);

		for (Use use : uses) {
			User user = use.getNode();
			Node node;
			if (user.isCFGNode()) {
				node = (Node) user;
			} else {
				Instruction instruction = (Instruction) user;
				node = instruction.getBlock();
			}

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
			PhiAssignment phi = (PhiAssignment) instruction;
			LocalVariable oldVar = phi.getOldVariable();
			uses.put(oldVar.getBaseName(), oldVar);
		}
	}

	@Override
	public void visit(Assign assign) {
		replaceUses(assign.getValue());
		replaceDef(assign);
	}

	@Override
	public void visit(Call call) {
		replaceUses(call.getParameters());
		replaceDef(call);
	}

	@Override
	public void visit(NodeIf nodeIf) {
		int outerBranch = branch;
		NodeBlock outerJoin = join;
		NodeWhile outerLoop = loop;

		replaceUses(nodeIf.getValue());

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
	public void visit(Load load) {
		replaceUses(load.getIndexes());
		replaceDef(load);
	}

	@Override
	public void visit(Procedure procedure) {
		definitions.clear();
		uses.clear();
		super.visit(procedure);
	}

	@Override
	public void visit(Return returnInstr) {
		replaceUses(returnInstr.getValue());
	}

	@Override
	public void visit(Store store) {
		replaceUses(store.getIndexes());
		replaceUses(store.getValue());
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		int outerBranch = branch;
		NodeBlock outerJoin = join;
		NodeWhile outerLoop = loop;

		replaceUses(nodeWhile.getValue());

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

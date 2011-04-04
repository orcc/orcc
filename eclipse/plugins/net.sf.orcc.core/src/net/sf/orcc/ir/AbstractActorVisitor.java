/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionVisitor;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.impl.InstructionVisitor;
import net.sf.orcc.ir.impl.NodeVisitor;

/**
 * This abstract class implements a no-op visitor on an actor. This class should
 * be extended by classes that implement actor visitors and transformations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractActorVisitor implements ActorVisitor,
		ExpressionVisitor, InstructionVisitor, NodeVisitor {

	/**
	 * current action being visited (if any).
	 */
	protected Action action;

	protected Actor actor;

	protected ListIterator<Action> itAction;

	protected ListIterator<Instruction> itInstruction;

	protected ListIterator<Node> itNode;

	/**
	 * current procedure being visited
	 */
	protected Procedure procedure;

	private final boolean visitFull;

	/**
	 * Creates a new abstract actor visitor that visits all nodes and
	 * instructions of all procedures (including the ones referenced by
	 * actions).
	 */
	public AbstractActorVisitor() {
		visitFull = false;
	}

	/**
	 * Creates a new abstract actor visitor that visits all nodes and
	 * instructions of all procedures (including the ones referenced by
	 * actions), and may also visit all the expressions if
	 * <code>visitFull</code> is <code>true</code>.
	 * 
	 * @param visitFull
	 *            when <code>true</code>, visits all the expressions
	 */
	public AbstractActorVisitor(boolean visitFull) {
		this.visitFull = visitFull;
	}

	/**
	 * Returns <code>true</code> if the variable is not in the locals nor
	 * parameters of the current procedure.
	 * 
	 * @param variable
	 * @return <code>true</code> if the variable is not in the locals nor
	 *         parameters of the current procedure
	 */
	final public boolean isPort(Var variable) {
		if (action != null) {
			return action.getInputPattern().contains(variable)
					|| action.getOutputPattern().contains(variable);
		}

		return false;
	}

	/**
	 * Visits the given action.
	 * 
	 * @param action
	 *            an action
	 */
	public void visit(Action action) {
		this.action = action;
		visit(action.getInputPattern());
		visit(action.getOutputPattern());
		visit(action.getScheduler());
		visit(action.getBody());
		this.action = null;
	}

	@Override
	public void visit(Actor actor) {
		this.actor = actor;

		for (Procedure proc : actor.getProcs()) {
			visit(proc);
		}

		itAction = actor.getActions().listIterator();
		while (itAction.hasNext()) {
			Action action = itAction.next();
			visit(action);
		}

		itAction = actor.getInitializes().listIterator();
		while (itAction.hasNext()) {
			Action action = itAction.next();
			visit(action);
		}
	}

	@Override
	public void visit(InstAssign assign) {
		if (visitFull) {
			assign.getValue().accept(this);
		}
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		expr.getE1().accept(this, args);
		expr.getE2().accept(this, args);
	}

	@Override
	public void visit(NodeBlock nodeBlock) {
		ListIterator<Instruction> it = nodeBlock.listIterator();
		while (it.hasNext()) {
			Instruction instruction = it.next();
			itInstruction = it;
			instruction.accept(this);
		}
	}

	@Override
	public void visit(BoolExpr expr, Object... args) {
	}

	@Override
	public void visit(InstCall call) {
		if (visitFull) {
			for (Expression expr : call.getParameters()) {
				expr.accept(this);
			}
		}
	}

	@Override
	public void visit(FloatExpr expr, Object... args) {
	}

	@Override
	public void visit(NodeIf nodeIf) {
		if (visitFull) {
			nodeIf.getCondition().accept(this);
		}

		visit(nodeIf.getThenNodes());
		visit(nodeIf.getElseNodes());
		visit(nodeIf.getJoinNode());
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
	}

	/**
	 * Visits the nodes of the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes that belong to a procedure
	 * @param args
	 *            arguments
	 */
	public void visit(List<Node> nodes) {
		ListIterator<Node> it = nodes.listIterator();
		while (it.hasNext()) {
			Node node = it.next();
			itNode = it;
			node.accept(this);
		}
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
	}

	@Override
	public void visit(InstLoad load) {
		if (visitFull) {
			for (Expression expr : load.getIndexes()) {
				expr.accept(this);
			}
		}
	}

	/**
	 * Visits the given pattern.
	 * 
	 * @param pattern
	 *            an pattern
	 */
	public void visit(Pattern pattern) {
	}

	@Override
	public void visit(InstPhi phi) {
		if (visitFull) {
			for (Expression expr : phi.getValues()) {
				expr.accept(this);
			}
		}
	}

	/**
	 * Visits the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public void visit(Procedure procedure) {
		this.procedure = procedure;
		List<Node> nodes = procedure.getNodes();
		visit(nodes);
	}

	@Override
	public void visit(InstReturn returnInstr) {
		if (visitFull) {
			Expression expr = returnInstr.getValue();
			if (expr != null) {
				expr.accept(this);
			}
		}
	}

	@Override
	public void visit(InstSpecific node) {
		// default implementation does nothing
	}

	@Override
	public void visit(InstStore store) {
		if (visitFull) {
			for (Expression expr : store.getIndexes()) {
				expr.accept(this);
			}

			store.getValue().accept(this);
		}
	}

	@Override
	public void visit(StringExpr expr, Object... args) {
	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		expr.getExpr().accept(this, args);
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		if (visitFull) {
			nodeWhile.getCondition().accept(this);
		}

		visit(nodeWhile.getNodes());
		visit(nodeWhile.getJoinNode());
	}

}

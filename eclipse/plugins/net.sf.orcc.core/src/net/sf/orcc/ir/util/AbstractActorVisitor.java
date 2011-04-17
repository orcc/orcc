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
package net.sf.orcc.ir.util;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;

/**
 * This abstract class implements a no-op visitor on an actor. This class should
 * be extended by classes that implement actor visitors and transformations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractActorVisitor<T> extends IrSwitch<T> implements
		ActorVisitor<T>, ExpressionVisitor, InstructionVisitor, NodeVisitor {

	protected static final Object NULL = new Object();

	/**
	 * current action being visited (if any).
	 */
	protected Action action;

	protected Actor actor;

	protected int indexInst;

	protected int indexNode;

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

	@Override
	public T caseAction(Action action) {
		doSwitch(action.getInputPattern());
		doSwitch(action.getOutputPattern());
		doSwitch(action.getScheduler());
		doSwitch(action.getBody());

		return null;
	}

	@Override
	public T caseActor(Actor actor) {
		for (Var parameter : actor.getParameters()) {
			doSwitch(parameter);
		}

		for (Var stateVar : actor.getStateVars()) {
			doSwitch(stateVar);
		}

		for (Procedure procedure : actor.getProcs()) {
			doSwitch(procedure);
		}

		for (Action action : actor.getActions()) {
			doSwitch(action);
		}

		for (Action initialize : actor.getInitializes()) {
			doSwitch(initialize);
		}

		return null;
	}

	@Override
	public T caseExprBinary(ExprBinary expr) {
		doSwitch(expr.getE1());
		doSwitch(expr.getE2());
		return null;
	}

	@Override
	public T caseExprBool(ExprBool expr) {
		return null;
	}

	@Override
	public T caseExprFloat(ExprFloat expr) {
		return null;
	}

	@Override
	public T caseExprInt(ExprInt expr) {
		return null;
	}

	@Override
	public T caseExprList(ExprList expr) {
		return null;
	}

	@Override
	public T caseExprString(ExprString expr) {
		return null;
	}

	@Override
	public T caseExprUnary(ExprUnary expr) {
		doSwitch(expr.getExpr());
		return null;
	}

	@Override
	public T caseExprVar(ExprVar var) {
		return null;
	}

	@Override
	public T caseInstAssign(InstAssign assign) {
		if (visitFull) {
			doSwitch(assign.getValue());
		}
		return null;
	}

	@Override
	public T caseInstCall(InstCall call) {
		if (visitFull) {
			for (Expression expr : call.getParameters()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstLoad(InstLoad load) {
		if (visitFull) {
			for (Expression expr : load.getIndexes()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstPhi(InstPhi phi) {
		if (visitFull) {
			for (Expression expr : phi.getValues()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstReturn(InstReturn returnInstr) {
		if (visitFull) {
			Expression expr = returnInstr.getValue();
			if (expr != null) {
				doSwitch(expr);
			}
		}
		return null;
	}

	@Override
	public T caseInstSpecific(InstSpecific inst) {
		// default implementation does nothing
		return null;
	}

	@Override
	public T caseInstStore(InstStore store) {
		if (visitFull) {
			for (Expression expr : store.getIndexes()) {
				doSwitch(expr);
			}

			doSwitch(store.getValue());
		}
		return null;
	}

	@Override
	public T caseNodeBlock(NodeBlock block) {
		int oldIndexInst = indexInst;
		List<Instruction> instructions = block.getInstructions();
		T result = null;
		for (indexInst = 0; indexInst < instructions.size() && result == null; indexInst++) {
			doSwitch(instructions.get(indexInst));
		}

		// restore old index
		indexInst = oldIndexInst;
		return result;
	}

	@Override
	public T caseNodeIf(NodeIf nodeIf) {
		if (visitFull) {
			doSwitch(nodeIf.getCondition());
		}

		doSwitch(nodeIf.getThenNodes());
		doSwitch(nodeIf.getElseNodes());
		doSwitch(nodeIf.getJoinNode());
		return null;
	}

	@Override
	public T caseNodeWhile(NodeWhile nodeWhile) {
		if (visitFull) {
			doSwitch(nodeWhile.getCondition());
		}

		doSwitch(nodeWhile.getNodes());
		doSwitch(nodeWhile.getJoinNode());
		return null;
	}

	@Override
	public T casePattern(Pattern pattern) {
		return null;
	}

	@Override
	public T caseProcedure(Procedure procedure) {
		this.procedure = procedure;
		return doSwitch(procedure.getNodes());
	}

	@Override
	public final T doSwitch(Actor actor) {
		return doSwitch((EObject) actor);
	}

	@Override
	public final T doSwitch(EObject theEObject) {
		if (theEObject == null) {
			return null;
		}
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Visits the nodes of the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes that belong to a procedure
	 */
	public T doSwitch(List<Node> nodes) {
		int oldIndexNode = indexNode;
		T result = null;
		for (indexNode = 0; indexNode < nodes.size() && result == null; indexNode++) {
			result = doSwitch(nodes.get(indexNode));
		}

		indexNode = oldIndexNode;
		return result;
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
		System.err
				.println("isPort(variable): Please switch to the EMF-based API");
		if (action != null) {
			return action.getInputPattern().contains(variable)
					|| action.getOutputPattern().contains(variable)
					|| action.getPeekPattern().contains(variable);
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
		System.err.println("visit(Action): Please switch to the EMF-based API");
		this.action = action;
		visit(action.getInputPattern());
		visit(action.getOutputPattern());
		visit(action.getPeekPattern());
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
	public void visit(ExprBinary expr, Object... args) {
		expr.getE1().accept(this, args);
		expr.getE2().accept(this, args);
	}

	@Override
	public void visit(ExprBool expr, Object... args) {
	}

	@Override
	public void visit(ExprFloat expr, Object... args) {
	}

	@Override
	public void visit(ExprInt expr, Object... args) {
	}

	@Override
	public void visit(ExprList expr, Object... args) {
	}

	@Override
	public void visit(ExprString expr, Object... args) {
	}

	@Override
	public void visit(ExprUnary expr, Object... args) {
		expr.getExpr().accept(this, args);
	}

	@Override
	public void visit(ExprVar expr, Object... args) {
	}

	@Override
	public void visit(InstAssign assign) {
		if (visitFull) {
			assign.getValue().accept(this);
		}
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
	public void visit(InstLoad load) {
		if (visitFull) {
			for (Expression expr : load.getIndexes()) {
				expr.accept(this);
			}
		}
	}

	@Override
	public void visit(InstPhi phi) {
		if (visitFull) {
			for (Expression expr : phi.getValues()) {
				expr.accept(this);
			}
		}
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
	public void visit(InstSpecific inst) {
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

	/**
	 * Visits the nodes of the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes that belong to a procedure
	 */
	public void visit(List<Node> nodes) {
		System.err
				.println("visit(List<Node>): Please switch to the EMF-based API");
		ListIterator<Node> oldItNode = itNode;
		itNode = nodes.listIterator();
		while (itNode.hasNext()) {
			Node node = itNode.next();
			node.accept(this);
		}

		// restore old iterator
		itNode = oldItNode;
	}

	@Override
	public void visit(NodeBlock nodeBlock) {
		itInstruction = nodeBlock.listIterator();
		while (itInstruction.hasNext()) {
			Instruction instruction = itInstruction.next();
			instruction.accept(this);
		}
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
	public void visit(NodeWhile nodeWhile) {
		if (visitFull) {
			nodeWhile.getCondition().accept(this);
		}

		visit(nodeWhile.getNodes());
		visit(nodeWhile.getJoinNode());
	}

	/**
	 * Visits the given pattern.
	 * 
	 * @param pattern
	 *            an pattern
	 */
	public void visit(Pattern pattern) {
		System.err
				.println("visit(Pattern): Please switch to the EMF-based API");
	}

	/**
	 * Visits the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public void visit(Procedure procedure) {
		System.err
				.println("visit(Procedure): Please switch to the EMF-based API");
		this.procedure = procedure;
		List<Node> nodes = procedure.getNodes();
		visit(nodes);
	}

}

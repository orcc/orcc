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

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
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
		ActorVisitor<T> {

	protected Actor actor;

	protected int indexInst;

	protected int indexNode;

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
		doSwitch(action.getPeekPattern());
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
	public T caseArgByRef(ArgByRef arg) {
		return null;
	}

	@Override
	public T caseArgByVal(ArgByVal arg) {
		doSwitch(arg.getValue());
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
			for (Arg arg : call.getParameters()) {
				doSwitch(arg);
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
			Instruction inst = instructions.get(indexInst);
			result = doSwitch(inst);
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
			Node node = nodes.get(indexNode);
			result = doSwitch(node);
		}

		indexNode = oldIndexNode;
		return result;
	}

	/**
	 * Returns the value of the <code>actor</code> attribute. This may be
	 * <code>null</code> if the visitor did not set it.
	 * 
	 * @return the value of the <code>actor</code> attribute
	 */
	final public Actor getActor() {
		return actor;
	}

}

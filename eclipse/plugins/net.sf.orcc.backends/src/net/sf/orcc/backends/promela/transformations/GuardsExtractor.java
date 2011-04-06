/*
 * Copyright (c) 2009, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.promela.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.util.AbstractActorVisitor;


/**
 * 
 * @author Ghislain Roquier
 * 
 */
public class GuardsExtractor extends AbstractActorVisitor {

	private Action currAction;

	private Map<Action, List<Expression>> guards;

	private List<Expression> guardList;

	private List<Load> loadList;

	public GuardsExtractor(Map<Action, List<Expression>> guards) {
		this.guards = guards;
	}

	// takes the guard from the previous action and negates it and adds it to
	// the guard of this action which has lower priority
	private void addPriorityToGuard(Action action, List<Expression> prevGuards) {
		Expression prty;
		// if there is no guard add the guard "true"
		if (guards.get(action).isEmpty()) {
			guards.get(action).add(0, new BoolExpr(true));
		}
		// add the guards from the previous actions as not guard
		for (Expression expr : prevGuards) {
			prty = new UnaryExpr(UnaryOp.LOGIC_NOT, expr, expr.getType());
			guards.get(action).add(prty);
		}
		prevGuards.add(guards.get(action).get(0)); // TODO: if the original
													// guard is more than 1 expr
													// this will not work
	}

	// If the local variable derived from this variable is used in an expression
	// then the variable is put directly in the expression instead
	private void removeLoads() {
		ListIterator<Load> loadIter = loadList.listIterator();
		while (loadIter.hasNext()) {
			Load ld = loadIter.next();
			ListIterator<Expression> itr = guardList.listIterator();
			while (itr.hasNext()) {
				Expression element = itr.next();
				replaceVarInExpr(element, ld);
			}
		}
	}

	// recursively searches through the expression and finds if the local
	// variable derived from the Load is present
	private void replaceVarInExpr(Expression expr, Load ld) {
		if (expr.isBinaryExpr()) {
			replaceVarInExpr(((BinaryExpr) expr).getE1(), ld);
			replaceVarInExpr(((BinaryExpr) expr).getE2(), ld);
		} else if (expr.isVarExpr()) {
			if (((VarExpr) expr).getVar().getVariable() == ld.getTarget()) {
				// Here I modify the model.. Should we work with a copy??
				((VarExpr) expr).setVar(ld.getSource());
			}
		}
	}

	@Override
	public void visit(Actor actor) {
		for (Action action : actor.getActions()) {
			currAction = action;
			guardList = new ArrayList<Expression>();
			loadList = new ArrayList<Load>();
			guards.put(currAction, guardList);
			visit(action.getScheduler());
			removeLoads();
		}
		// Get the priorities. The list of actions are in decreasing priority
		// order.
		// The easy way is to for each action add the "not guard" of the
		// previous action.
		// As the guard of the action only needs one list position we can use
		// the rest of the list for the priorities

		// In each action there is an action scheduler
		// actions not in a FSM are present in the "actions" list and appear in
		// decreasing priority order.
		if (!actor.getActionScheduler().hasFsm()) {
			List<Expression> prevGuards = new ArrayList<Expression>();
			for (Action action : actor.getActionScheduler().getActions()) {
				addPriorityToGuard(action, prevGuards);
			}
		}
		// Actions in the FSM appear in
		// actionScheduler->transition<List>->nextStateinfo<List> also in oder
		// of priority
		else {
			for (Transition trans : actor.getActionScheduler().getFsm()
					.getTransitions()) {
				List<Expression> prevGuards = new ArrayList<Expression>();
				for (NextStateInfo nsi : trans.getNextStateInfo()) {
					addPriorityToGuard(nsi.getAction(), prevGuards);
				}
			}
		}
	}

	@Override
	public void visit(Assign assign) {
		// we should also consider other cases but this is enough for now
		if (!assign.getValue().isBooleanExpr()) {
			guardList.add(assign.getValue());
		}
	}

	@Override
	public void visit(Load load) {
		loadList.add(load);
	}

}

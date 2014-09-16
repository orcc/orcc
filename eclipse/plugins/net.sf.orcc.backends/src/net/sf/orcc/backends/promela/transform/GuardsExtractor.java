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
package net.sf.orcc.backends.promela.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.util.AbstractIrVisitor;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 * @author Ghislain Roquier
 * 
 */
public class GuardsExtractor extends DfVisitor<Void> {

	private class InnerIrVisitor extends AbstractIrVisitor<Void> {
		@Override
		public Void caseInstAssign(InstAssign assign) {
			// we should also consider other cases but this is enough for now
			if (!assign.getValue().isExprBool()) {
				guardList.add(assign.getValue());
			}
			return null;
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			loadList.add(load);
			return null;
		}
	}

	private Action currAction;

	private List<Expression> guardList;

	private Map<Action, List<Expression>> guards;

	private List<InstLoad> loadList;

	private Map<Action, List<InstLoad>> loadPeeks;

	private int peekCnt = 0;

	private Map<EObject, List<Action>> priority;

	private List<InstLoad> usedLoadsList;

	public GuardsExtractor(Map<Action, List<Expression>> guards,
			Map<EObject, List<Action>> priority,
			Map<Action, List<InstLoad>> loadPeeks) {
		this.guards = guards;
		this.priority = priority;
		this.loadPeeks = loadPeeks;
		this.irVisitor = new InnerIrVisitor();
	}

	@Override
	public Void caseActor(Actor actor) {
		// reset peek index
		peekCnt = 0;
		for (Action action : actor.getActions()) {
			currAction = action;
			guardList = new ArrayList<Expression>();
			loadList = new ArrayList<InstLoad>();
			usedLoadsList = new ArrayList<InstLoad>();
			guards.put(currAction, guardList);
			loadPeeks.put(currAction, usedLoadsList);
			doSwitch(action.getScheduler());
			removeLoads();
		}
		// Get the priorities. The list of actions are in decreasing priority
		// order.
		// The easy way is to for each action add the "not guard" of the
		// previous action, this is now done in the stringtemplate, here
		// we only list the actions with higher priority.
		if (!actor.getActionsOutsideFsm().isEmpty()) {
			List<Action> prevActions = new ArrayList<Action>();
			for (Action action : actor.getActionsOutsideFsm()) {
				priority.put(action, new ArrayList<Action>());
				for (Action a : prevActions) {
					priority.get(action).add(a);
				}
				prevActions.add(action);
			}
		}
		// Actions in the FSM appear in
		// actionScheduler->transition<List> also in order
		// of priority
		if (actor.hasFsm()) {
			for (State state : actor.getFsm().getStates()) {
				List<Action> prevActions = new ArrayList<Action>();
				for (Edge edge : state.getOutgoing()) {
					Transition trans = (Transition) edge;
					priority.put(trans, new ArrayList<Action>());
					for (Action a : prevActions) {
						priority.get(trans).add(a);
					}
					prevActions.add(trans.getAction());
				}
			}
		}

		return null;
	}

	private boolean isFromPeek(InstLoad ld) {
		return currAction.getPeekPattern().contains(
				ld.getSource().getVariable());
	}

	// If the local variable derived from this variable is used in an expression
	// then the variable is put directly in the expression instead
	private void removeLoads() {
		ListIterator<InstLoad> loadIter = loadList.listIterator();
		while (loadIter.hasNext()) {
			InstLoad ld = loadIter.next();
			// do not remove the Loads related to Peeks
			if (isFromPeek(ld)) {
				usedLoadsList.add(ld);
				ld.getTarget()
						.getVariable()
						.setName(
								ld.getTarget().getVariable().getName()
										+ "_peek_" + peekCnt++);
				continue;
			}
			ListIterator<Expression> itr = guardList.listIterator();
			while (itr.hasNext()) {
				Expression element = itr.next();
				replaceVarInExpr(element, ld);
			}
		}
	}

	// recursively searches through the expression and finds if the local
	// variable derived from the Load is present
	private void replaceVarInExpr(Expression expr, InstLoad ld) {
		if (expr.isExprBinary()) {
			replaceVarInExpr(((ExprBinary) expr).getE1(), ld);
			replaceVarInExpr(((ExprBinary) expr).getE2(), ld);
		} else if (expr.isExprVar()) {
			if (((ExprVar) expr).getUse().getVariable() == ld.getTarget()
					.getVariable()) {
				((ExprVar) expr).setUse(IrFactory.eINSTANCE.createUse(ld
						.getSource().getVariable()));
			}
		}
	}

}

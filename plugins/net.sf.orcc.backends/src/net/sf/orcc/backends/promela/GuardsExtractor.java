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
package net.sf.orcc.backends.promela;


import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * 
 * @author Ghislain Roquier
 * 
 */
public class GuardsExtractor extends AbstractActorTransformation {

	Map<Action, List<Expression>> guards;
	
	Action currAction;

	List<Expression> list;
	List<Load> loads;
	List<Peek> peeks;

	public GuardsExtractor(Map<Action, List<Expression>> guards) {
		this.guards = guards;
	}
	
	@Override
	public void transform(Actor actor) throws OrccException {
		for (Action action : actor.getActions()) {
			currAction = action;
			list = new ArrayList<Expression>();
			loads = new ArrayList<Load>();
			peeks = new ArrayList<Peek>(); // this is not used at the moment
			guards.put(currAction, list);
			visitProcedure(action.getScheduler());
			removeLoads();
		}
	}
	
	@Override
	public void visit(Load load) { 
		loads.add(load);
	}

	@Override
	public void visit(Peek peek) {
		peeks.add(peek);
	}
	
	@Override
	public void visit(Assign assign) {
		// we should also consider other cases but this is enough for now
		if (!assign.getValue().isBooleanExpr()) {
			list.add(assign.getValue());
		}
	}
	
	
	// If the local variable derived from this variable is used in an expression
	// then the variable is put directly in the expression instead
	private void removeLoads() {
		ListIterator<Load> loadIter = loads.listIterator();
		while(loadIter.hasNext()) {
			Load ld = loadIter.next();
			ListIterator<Expression> itr = list.listIterator();
			while(itr.hasNext()) {
				Expression element = itr.next();
				replaceVarInExpr(element, ld);
			}
		}
	}
	
	// recursively searches through the expression and finds if the local variable derived from the Load is present 
	private void replaceVarInExpr(Expression expr, Load ld) {
		if (expr.isBinaryExpr()) {
			replaceVarInExpr(((BinaryExpr)expr).getE1(), ld);
			replaceVarInExpr(((BinaryExpr)expr).getE2(), ld);
		} 
		else if (expr.isVarExpr()){
			if (((VarExpr)expr).getVar().getVariable() == ld.getTarget()) {
				((VarExpr)expr).setVar(ld.getSource()); // Here I modify the model.. Should we work with a copy??
			}
		}
	}
	
	
}
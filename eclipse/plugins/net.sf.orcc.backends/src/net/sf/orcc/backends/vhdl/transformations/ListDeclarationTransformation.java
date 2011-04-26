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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines an actor transformation that transforms declarations of
 * multi-dimensional lists to declarations of lists with a single dimension.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * @author Herve Yviquel
 * 
 */
public class ListDeclarationTransformation extends AbstractActorVisitor<Object> {

	/**
	 * Flattens a multi-dimensional list expression to a list with a single
	 * dimension.
	 * 
	 * @param expression
	 *            an expression
	 * @param list
	 *            a list expression
	 */
	private void flattenList(Expression expression, ExprList list) {
		if (expression.isListExpr()) {
			List<Expression> expressions = ((ExprList) expression).getValue();
			while (!expressions.isEmpty()) {
				Expression subExpr = expressions.get(0);
				flattenList(subExpr, list);
			}
		} else {
			list.getValue().add(expression);
		}
	}

	@Override
	public Object caseActor(Actor actor) {
		// VHDL synthesizers don't support multi-dimensional memory yet
		for (Var variable : actor.getStateVars()) {
			if (variable.getType().isList() && variable.isInitialized()) {
				ExprList list = IrFactory.eINSTANCE.createExprList();
				flattenList(variable.getValue(), list);
				variable.setValue(list);
			}
		}
		return null;
	}

}

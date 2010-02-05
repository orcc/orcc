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
package net.sf.orcc.backends.vhdl.transforms;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.ir.type.BoolType;

/**
 * This class defines an actor transformation that transform the simple
 * condition tests <code>var</code> and <code>not var</code> to
 * <code>var = true</code> and to <code>var = false</code> respectively.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class TransformConditionals extends AbstractActorTransformation {

	private Expression changeConditional(Expression expr) {
		if (expr.getTypeOf() == Expression.VAR) {
			VarExpr varExpr = (VarExpr) expr;
			return new BinaryExpr(varExpr, BinaryOp.EQ, new BoolExpr(true),
					new BoolType());
		} else if (expr.getTypeOf() == Expression.UNARY) {
			UnaryExpr unaryExpr = (UnaryExpr) expr;
			if (unaryExpr.getOp() == UnaryOp.LOGIC_NOT) {
				return new BinaryExpr(unaryExpr.getExpr(), BinaryOp.EQ,
						new BoolExpr(false), new BoolType());
			}
		}

		return expr;
	}

	@Override
	public void visit(IfNode node, Object... args) {
		node.setValue(changeConditional(node.getValue()));
		super.visit(node, args);
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		node.setValue(changeConditional(node.getValue()));
		super.visit(node, args);
	}

}

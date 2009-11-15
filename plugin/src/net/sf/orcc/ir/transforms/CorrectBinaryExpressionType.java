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
package net.sf.orcc.ir.transforms;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.Util;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;

/**
 * Split expression and effective node.
 * 
 * @author Jérôme Gorin
 * 
 */
public class CorrectBinaryExpressionType extends AbstractActorTransformation {

	public Type checkType(BinaryExpr expr, Object... args) {
		Type type;

		if ((expr.getOp() == BinaryOp.EQ) || (expr.getOp() == BinaryOp.GE)
				|| (expr.getOp() == BinaryOp.GT)
				|| (expr.getOp() == BinaryOp.LE)
				|| (expr.getOp() == BinaryOp.LT)
				|| (expr.getOp() == BinaryOp.NE)) {
			type = new BoolType();
		} else if ((expr.getE1().getType() == Expression.VAR)
				&& (expr.getE2().getType() == Expression.VAR)) {
			Type typeE1 = ((VarExpr) expr.getE1()).getVar().getVariable()
					.getType();
			Type typeE2 = ((VarExpr) expr.getE1()).getVar().getVariable()
					.getType();

			if (sizeOf(typeE1) > sizeOf(typeE2)) {
				type = typeE1;
			} else {
				type = typeE2;
			}

		} else if (expr.getE1().getType() == Expression.VAR) {
			type = ((VarExpr) expr.getE1()).getVar().getVariable().getType();
		} else if (expr.getE2().getType() == Expression.VAR) {
			type = ((VarExpr) expr.getE2()).getVar().getVariable().getType();
		} else {
			type = expr.getUnderlyingType();
		}

		return type;
	}

	private int sizeOf(Type type) {
		int size = 0;

		if (type.getType() == Type.INT) {
			try {
				return Util.evaluateAsInteger(((IntType) type).getSize());
			} catch (OrccException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type.getType() == Type.BOOLEAN) {
			size = 1;
		}

		return size;
	}

	@Override
	public void visit(Assign node, Object... args) {
		Expression value = node.getValue();

		if (value.getType() == Expression.BINARY) {
			BinaryExpr expr = (BinaryExpr) value;
			Type type = checkType(expr);
			expr.setType(type);
			node.getTarget().setType(type);
		}
	}

	@Override
	public Object visit(IfNode node, Object... args) {
		visit(node.getThenNodes());
		visit(node.getElseNodes());
		return null;
	}

	@Override
	public Object visit(WhileNode node, Object... args) {
		visit(node.getNodes());
		return null;
	}

}

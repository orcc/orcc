/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.xlim.experimental.transform;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.AbstractExpressionInterpreter;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * 
 * This class defines a transformation that transforms literals used in
 * instructions into variables. This transformation is needed since XLIM ports
 * cannot contain literals.
 * 
 * @author Ghislain Roquier
 * 
 */

public class MoveLiteralIntegers extends AbstractActorTransformation {

	private int index;

	private OrderedMap<Variable> locals;

	private ExpressionInterpreter exprInterpreter = new AbstractExpressionInterpreter() {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);
			return new BinaryExpr(e1, expr.getOp(), e2, expr.getType());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			String name = "lit_bool_" + index++;
			LocalVariable var = new LocalVariable(true, 0, new Location(),
					name, null, IrFactory.eINSTANCE.createTypeBool());
			locals.add(var.getName(), var);
			it.previous();
			it.add(new Assign(var, expr));
			it.next();
			return new VarExpr(new Use(var));
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object interpret(IntExpr expr, Object... args) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			String name = "lit_int_" + index++;
			LocalVariable var = new LocalVariable(true, 0, new Location(),
					name, null, IrFactory.eINSTANCE.createTypeInt(32));
			locals.add(var.getName(), var);
			it.previous();
			it.add(new Assign(var, expr));
			it.next();
			return new VarExpr(new Use(var));
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			Expression e = (Expression) expr.getExpr().accept(this, args);
			return new UnaryExpr(expr.getOp(), e, expr.getType());
		}
	};

	@Override
	public void visit(Assign assign, Object... args) {
		assign.setValue((Expression) assign.getValue().accept(exprInterpreter,
				args));
	}

	@Override
	public void visit(BlockNode node, Object... args) {
		ListIterator<Instruction> it = node.getInstructions().listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	@Override
	public void visit(Store store, Object... args) {
		ListIterator<Expression> it = store.getIndexes().listIterator();
		while (it.hasNext()) {
			it.set((Expression) it.next().accept(exprInterpreter, args));
		}
		store.setValue((Expression) store.getValue().accept(exprInterpreter,
				args));
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		index = 0;
		locals = procedure.getLocals();
		List<CFGNode> nodes = procedure.getNodes();
		visit(nodes);
	}

}

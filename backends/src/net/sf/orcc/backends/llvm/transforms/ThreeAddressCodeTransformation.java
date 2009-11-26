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
package net.sf.orcc.backends.llvm.transforms;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.ir.type.IntType;

/**
 * Split expression and effective node.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ThreeAddressCodeTransformation extends AbstractActorTransformation {

	private class ExpressionSplitter implements ExpressionInterpreter {

		private ListIterator<Instruction> it;

		/**
		 * Creates a new expression splitter with the given list iterator. The
		 * iterator must be placed immediately before the expression to be
		 * translated is used.
		 * 
		 * @param it
		 *            iterator on a list of instructions
		 */
		public ExpressionSplitter(ListIterator<Instruction> it) {
			this.it = it;
		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);

			Location location = expr.getLocation();
			BinaryOp op = expr.getOp();
			Type type = expr.getUnderlyingType();

			LocalVariable target = newVariable();
			Assign assign = new Assign(block, location, target, new BinaryExpr(
					location, e1, op, e2, type));
			it.add(assign);

			return new VarExpr(location, new Use(target));
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			return expr;
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			return expr;
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			throw new OrccRuntimeException("list expression not supported");
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			return expr;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			Expression constExpr;
			Expression binary;
			Expression exprE1 = expr.getExpr();
			Location loc = expr.getLocation();
			Type type = expr.getUnderlyingType();

			switch (expr.getOp()) {
			case MINUS:
				constExpr = new IntExpr(new Location(), 0);
				binary = new BinaryExpr(loc, constExpr, BinaryOp.MINUS, exprE1,
						type);
				return binary.accept(this, args);
			case LOGIC_NOT:
				constExpr = new IntExpr(new Location(), 0);
				binary = new BinaryExpr(loc, exprE1, BinaryOp.NE, constExpr,
						type);
				return binary.accept(this, args);
			case BITNOT:
				binary = new BinaryExpr(loc, exprE1, BinaryOp.BITXOR, exprE1,
						type);
				return binary.accept(this, args);
			default:
				throw new OrccRuntimeException("unsupported operator");
			}
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			return expr;
		}

		/**
		 * Creates a new local variable with type int(size=32).
		 * 
		 * @return a new local variable with type int(size=32)
		 */
		private LocalVariable newVariable() {
			return new LocalVariable(true, tempVarCount++, new Location(),
					"expr", null, null, new IntType(new IntExpr(32)));
		}

	}

	private BlockNode block;

	private int tempVarCount;

	/**
	 * Returns an iterator over the last instruction of the previous block. A
	 * new block is created if there is no previous one.
	 * 
	 * @param it
	 * @return
	 */
	private ListIterator<Instruction> getItr(ListIterator<CFGNode> it) {
		it.previous();
		if (it.hasPrevious()) {
			// get previous and restore iterator's position
			CFGNode previous = it.previous();
			it.next();

			if (previous instanceof BlockNode) {
				block = ((BlockNode) previous);
			} else if (previous instanceof IfNode) {
				block = ((IfNode) previous).getJoinNode();
			} else {
				block = ((WhileNode) previous).getJoinNode();
			}
		} else {
			// no previous block, create and add a new one
			block = new BlockNode();
			it.add(block);
		}
		it.next();

		return block.lastListIterator();
	}

	@Override
	public void visit(Assign assign, Object... args) {
		block = assign.getBlock();
		assign.setValue(visitExpression(assign.getValue(), args[0]));
	}

	@Override
	public void visit(Call call, Object... args) {
		block = call.getBlock();
		visitExpressions(call.getParameters(), args[0]);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object visit(IfNode ifNode, Object... args) {
		ListIterator<CFGNode> it = (ListIterator<CFGNode>) args[0];
		ifNode.setValue(visitExpression(ifNode.getValue(), getItr(it)));

		return super.visit(ifNode, args);
	}

	@Override
	public void visit(Load load, Object... args) {
		block = load.getBlock();
		visitExpressions(load.getIndexes(), args[0]);
	}

	@Override
	public void visit(Return returnInstr, Object... args) {
		block = returnInstr.getBlock();
		if (returnInstr.getValue() != null) {
			returnInstr.setValue(visitExpression(returnInstr.getValue(), args[0]));
		}
	}

	@Override
	public void visit(Store store, Object... args) {
		block = store.getBlock();
		visitExpressions(store.getIndexes(), args[0]);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object visit(WhileNode whileNode, Object... args) {
		ListIterator<CFGNode> it = (ListIterator<CFGNode>) args[0];
		whileNode.setValue(visitExpression(whileNode.getValue(), getItr(it)));

		return super.visit(whileNode, args);
	}

	@SuppressWarnings("unchecked")
	private Expression visitExpression(Expression value, Object arg) {
		ListIterator<Instruction> it = (ListIterator<Instruction>) arg;
		return (Expression) value.accept(new ExpressionSplitter(it));
	}

	@SuppressWarnings("unchecked")
	private void visitExpressions(List<Expression> expressions, Object arg) {
		ListIterator<Instruction> it = (ListIterator<Instruction>) arg;
		ListIterator<Expression> pit = expressions.listIterator();
		while (pit.hasNext()) {
			Expression value = pit.next();
			pit.set((Expression) value.accept(new ExpressionSplitter(it)));
		}
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		tempVarCount = 1;
		super.visitProcedure(procedure);
	}

}

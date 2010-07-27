/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * Split expression and effective node.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class SizeRedimension extends AbstractActorTransformation {

	private class ExpressionRedimension implements ExpressionInterpreter {

		/**
		 * type of the target variable
		 */
		private Type type;

		/**
		 * Creates a Expression redimension with the given target size.
		 * 
		 * @param size
		 *            The size of the target
		 */
		public ExpressionRedimension(Type syze) {
			this.type = syze;
		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Type newType = type;
			expr.setType(newType);
			return expr;
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
			return expr;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			return expr;
		}
	}

	@SuppressWarnings("unused")
	private BlockNode block;

	@Override
	public void transform(Actor actor) {
		// Visit procedure
		for (Procedure proc : actor.getProcs()) {
			visitProcedure(proc);
		}

		for (Action action : actor.getActions()) {
			visitProcedure(action.getBody());
			visitProcedure(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProcedure(action.getBody());
			visitProcedure(action.getScheduler());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Assign assign, Object... args) {
		block = assign.getBlock();
		ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
		Type size = assign.getTarget().getType();
		it.previous(); 
		assign.setValue(visitExpression(assign.getValue(), size));
		it.next();
	}

	private Expression visitExpression(Expression value, Type syze) {
		return (Expression) value.accept(new ExpressionRedimension(syze));
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		// set the label counter to prevent new nodes from having the same label
		// as existing nodes
		List<CFGNode> nodes = procedure.getNodes();
		if (nodes.size() > 0) {
			CFGNode lastNode = nodes.get(nodes.size() - 1);
			AbstractNode.setLabelCount(lastNode.getLabel() + 1);
		}
		super.visitProcedure(procedure);
	}

}

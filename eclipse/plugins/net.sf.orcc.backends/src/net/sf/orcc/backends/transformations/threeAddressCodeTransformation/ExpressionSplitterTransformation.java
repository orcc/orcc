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
package net.sf.orcc.backends.transformations.threeAddressCodeTransformation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.impl.NodeImpl;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.AbstractExpressionInterpreter;

/**
 * Split expression and effective node that contains more than one fundamental
 * operation into a series of simple expressions.
 * 
 * @author Jerome GORIN
 * @author Matthieu Wipliez
 * 
 */
public class ExpressionSplitterTransformation extends AbstractActorVisitor {

	private class ExpressionSplitter extends AbstractExpressionInterpreter {

		@Override
		public Object interpret(ExprBinary expr, Object... args) {
			Type type = expr.getType();
			OpBinary op = expr.getOp();
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);

			expr.setE1(e1);
			expr.setE2(e2);

			// Create a new binary expression
			Expression binExpr = IrFactory.eINSTANCE.createExprBinary(e1, op,
					e2, type);

			// Make a new assignment to the binary expression
			Var target = procedure.newTempLocalVariable(type,
					procedure.getName() + "_" + "expr");
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target,
					binExpr);

			// Add assignment to instruction's list
			instrs.add(assign);

			return IrFactory.eINSTANCE.createExprVar(target);
		}

		@Override
		public Object interpret(ExprUnary expr, Object... args) {
			Expression varExpr = expr.getExpr();
			Type type = expr.getType();

			varExpr = (Expression) varExpr.accept(this, args);

			ExprBinary binaryExpr = transformUnaryExpr(expr.getOp(), varExpr);

			// Make a new assignment to the binary expression
			Var target = procedure.newTempLocalVariable(type,
					procedure.getName() + "_" + "expr");
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target,
					binaryExpr);

			// Add assignment to instruction's list
			instrs.add(assign);

			return IrFactory.eINSTANCE.createExprVar(target);
		}

		public ExprBinary transformUnaryExpr(OpUnary op, Expression expr) {
			Expression constExpr;
			Type type = expr.getType();

			switch (op) {
			case MINUS:
				constExpr = IrFactory.eINSTANCE.createExprInt(0);
				return IrFactory.eINSTANCE.createExprBinary(constExpr,
						OpBinary.MINUS, expr, type);
			case LOGIC_NOT:
				constExpr = IrFactory.eINSTANCE.createExprBool(false);
				return IrFactory.eINSTANCE.createExprBinary(expr, OpBinary.EQ,
						constExpr, type);
			case BITNOT:
				return IrFactory.eINSTANCE.createExprBinary(expr,
						OpBinary.BITXOR, expr, type);
			default:
				throw new OrccRuntimeException("unsupported operator");
			}
		}

	}

	private ExpressionSplitter expressionSplitter;

	private List<Instruction> instrs;

	public ExpressionSplitterTransformation() {
		expressionSplitter = new ExpressionSplitter();
		instrs = new ArrayList<Instruction>();
	}

	/**
	 * Returns an iterator over the last instruction of the previous block. A
	 * new block is created.
	 * 
	 * @param it
	 * @return
	 */
	private ListIterator<Instruction> getItr(ListIterator<Node> it) {
		// Create a new basic block
		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();

		// Add it before the current node
		it.previous();
		it.add(block);

		return block.listIterator();
	}

	@Override
	public void visit(Actor actor) {
		super.visit(actor);
	}

	@Override
	public void visit(InstAssign assign) {
		Expression value = assign.getValue();

		if (value.isBinaryExpr()) {
			ExprBinary binExpr = (ExprBinary) value;
			Expression e1 = binExpr.getE1();
			Expression e2 = binExpr.getE2();

			if (e1.isBinaryExpr() || e1.isUnaryExpr()) {
				// Split expression e1
				itInstruction.previous();
				binExpr.setE1(visitExpression(e1, itInstruction));
				itInstruction.next();
			}

			if (e2.isBinaryExpr() || e2.isUnaryExpr()) {
				// Split expression e2
				itInstruction.previous();
				binExpr.setE2(visitExpression(e2, itInstruction));
				itInstruction.next();
			}
		} else if (value.isUnaryExpr()) {
			ExprUnary unaryExpr = (ExprUnary) value;
			itInstruction.previous();

			// Transform unary expression into binary expression
			Expression newExpr = visitExpression(unaryExpr.getExpr(),
					itInstruction);
			assign.setValue(expressionSplitter.transformUnaryExpr(
					unaryExpr.getOp(), newExpr));

			itInstruction.next();
		}
	}

	@Override
	public void visit(InstCall call) {
		List<Expression> parameters = call.getParameters();
		for (Expression parameter : parameters) {
			if (parameter.isBinaryExpr() || parameter.isUnaryExpr()) {
				itInstruction.previous();
				Expression newParameter = visitExpression(parameter,
						itInstruction);
				parameters.set(parameters.indexOf(parameter), newParameter);
				itInstruction.next();
			}
		}
	}

	@Override
	public void visit(NodeIf nodeIf) {
		Expression condition = nodeIf.getCondition();
		if ((condition.isBinaryExpr()) || (condition.isUnaryExpr())) {
			Expression newCondition = visitExpression(condition, getItr(itNode));
			nodeIf.setCondition(newCondition);
		}
		super.visit(nodeIf);
	}

	@Override
	public void visit(InstLoad load) {
		itInstruction.previous();
		visitIndexes(load.getIndexes(), itInstruction);
		itInstruction.next();
	}

	@Override
	public void visit(Procedure procedure) {
		// set the label counter to prevent new nodes from having the same label
		// as existing nodes
		List<Node> nodes = procedure.getNodes();
		if (nodes.size() > 0) {
			Node lastNode = nodes.get(nodes.size() - 1);
			NodeImpl.setLabelCount(lastNode.getLabel() + 2);
		}
		super.visit(procedure);
	}

	@Override
	public void visit(InstReturn returnInstr) {
		if (returnInstr.getValue() != null) {
			itInstruction.previous();
			Expression newValue = visitExpression(returnInstr.getValue(),
					itInstruction);
			returnInstr.setValue(newValue);
			itInstruction.next();
		}
	}

	@Override
	public void visit(InstStore store) {
		Expression value = store.getValue();

		itInstruction.previous();

		visitIndexes(store.getIndexes(), itInstruction);

		if ((value.isBinaryExpr()) || (value.isUnaryExpr())) {
			Expression newValue = visitExpression(value, itInstruction);
			store.setValue(newValue);
		}

		itInstruction.next();
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		ListIterator<Instruction> it = nodeWhile.getJoinNode().listIterator();

		// Go to the end of joinNode
		while (it.hasNext()) {
			it.next();
		}

		Expression condition = nodeWhile.getCondition();
		if ((condition.isBinaryExpr()) || (condition.isUnaryExpr())) {
			Expression newCondition = visitExpression(condition, it);
			nodeWhile.setCondition(newCondition);
		}

		super.visit(nodeWhile);
	}

	private Expression visitExpression(Expression value,
			ListIterator<Instruction> it) {
		instrs.clear();
		Expression expr = (Expression) value.accept(expressionSplitter);

		for (Instruction instr : instrs) {
			it.add(instr);
		}

		return expr;
	}

	private void visitIndexes(List<Expression> indexes,
			ListIterator<Instruction> it) {
		for (Expression value : indexes) {
			if ((value.isBinaryExpr()) || (value.isUnaryExpr())) {
				Expression newValue = visitExpression(value, it);
				indexes.set(indexes.indexOf(value), newValue);
			}
		}
	}
}

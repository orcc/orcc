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
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.AbstractExpressionInterpreter;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.impl.NodeImpl;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.util.AbstractActorVisitor;

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
		public Object interpret(BinaryExpr expr, Object... args) {
			Type type = expr.getType();
			BinaryOp op = expr.getOp();
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);

			expr.setE1(e1);
			expr.setE2(e2);

			// Create a new binary expression
			Expression binExpr = new BinaryExpr(e1, op, e2, type);

			// Make a new assignment to the binary expression
			VarLocal target = procedure.newTempLocalVariable(file, type,
					procedure.getName() + "_" + "expr");
			Assign assign = new Assign(target, binExpr);

			// Add assignment to instruction's list
			instrs.add(assign);

			return new VarExpr(new Use(target));
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			Expression varExpr = expr.getExpr();
			Type type = expr.getType();

			varExpr = (Expression) varExpr.accept(this, args);

			BinaryExpr binaryExpr = transformUnaryExpr(expr.getOp(), varExpr);

			// Make a new assignment to the binary expression
			VarLocal target = procedure.newTempLocalVariable(file, type,
					procedure.getName() + "_" + "expr");
			Assign assign = new Assign(target, binaryExpr);

			// Add assignment to instruction's list
			instrs.add(assign);

			return new VarExpr(new Use(target));
		}

		public BinaryExpr transformUnaryExpr(UnaryOp op, Expression expr) {
			Expression constExpr;
			Type type = expr.getType();

			switch (op) {
			case MINUS:
				constExpr = new IntExpr(0);
				return new BinaryExpr(constExpr, BinaryOp.MINUS, expr, type);
			case LOGIC_NOT:
				constExpr = new BoolExpr(false);
				return new BinaryExpr(expr, BinaryOp.EQ, constExpr, type);
			case BITNOT:
				return new BinaryExpr(expr, BinaryOp.BITXOR, expr, type);
			default:
				throw new OrccRuntimeException("unsupported operator");
			}
		}

	}

	private ExpressionSplitter expressionSplitter;

	private String file;

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
		this.file = actor.getFile();
		super.visit(actor);
	}

	@Override
	public void visit(Assign assign) {
		Expression value = assign.getValue();

		if (value.isBinaryExpr()) {
			BinaryExpr binExpr = (BinaryExpr) value;
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
			Use.addUses(assign, assign.getValue());
		} else if (value.isUnaryExpr()) {
			UnaryExpr unaryExpr = (UnaryExpr) value;
			itInstruction.previous();

			// Transform unary expression into binary expression
			Expression newExpr = visitExpression(unaryExpr.getExpr(),
					itInstruction);
			assign.setValue(expressionSplitter.transformUnaryExpr(
					unaryExpr.getOp(), newExpr));

			itInstruction.next();
			Use.addUses(assign, assign.getValue());
		}
	}

	@Override
	public void visit(Call call) {
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
		Use.addUses(call, call.getParameters());
	}

	@Override
	public void visit(NodeIf nodeIf) {
		Expression value = nodeIf.getValue();
		if ((value.isBinaryExpr()) || (value.isUnaryExpr())) {
			Expression newValue = visitExpression(nodeIf.getValue(),
					getItr(itNode));
			nodeIf.setValue(newValue);
			Use.addUses(nodeIf, newValue);
		}
		super.visit(nodeIf);
	}

	@Override
	public void visit(Load load) {
		itInstruction.previous();
		visitIndexes(load.getIndexes(), itInstruction);
		Use.addUses(load, load.getIndexes());
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
	public void visit(Return returnInstr) {
		if (returnInstr.getValue() != null) {
			itInstruction.previous();
			Expression newValue = visitExpression(returnInstr.getValue(),
					itInstruction);
			returnInstr.setValue(newValue);
			Use.addUses(returnInstr, newValue);
			itInstruction.next();
		}
	}

	@Override
	public void visit(Store store) {
		Expression value = store.getValue();

		itInstruction.previous();

		visitIndexes(store.getIndexes(), itInstruction);
		Use.addUses(store, store.getIndexes());

		if ((value.isBinaryExpr()) || (value.isUnaryExpr())) {
			Expression newValue = visitExpression(value, itInstruction);
			store.setValue(newValue);
			Use.addUses(store, newValue);
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

		Expression value = nodeWhile.getValue();
		if ((value.isBinaryExpr()) || (value.isUnaryExpr())) {
			Expression expr = visitExpression(nodeWhile.getValue(), it);
			nodeWhile.setValue(expr);
			Use.addUses(nodeWhile, expr);
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

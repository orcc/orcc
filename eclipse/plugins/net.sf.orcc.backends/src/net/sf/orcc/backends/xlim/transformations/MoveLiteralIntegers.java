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
package net.sf.orcc.backends.xlim.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.instructions.InstTernary;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.AbstractExpressionInterpreter;
import net.sf.orcc.ir.util.ExpressionInterpreter;

/**
 * 
 * This class defines a transformation that transforms literals used in
 * instructions into variables. This transformation is needed since XLIM ports
 * cannot contain literals.
 * 
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * 
 */
public class MoveLiteralIntegers extends AbstractActorVisitor {

	private ExpressionInterpreter exprInterpreter = new AbstractExpressionInterpreter() {

		@Override
		public Object interpret(ExprBinary expr, Object... args) {
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);
			return IrFactory.eINSTANCE.createExprBinary(e1, expr.getOp(), e2,
					expr.getType());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object interpret(ExprBool expr, Object... args) {
			Type type = expr.getType();
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			Var var = procedure.newTempLocalVariable(type, procedure.getName()
					+ "_" + "litteral_integer");
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(var, expr);

			// Add assignment to instruction's list
			if (it.hasPrevious()) {
				it.previous();
			}
			it.add(assign);
			if (it.hasNext()) {
				it.next();
			}
			return IrFactory.eINSTANCE.createExprVar(var);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object interpret(ExprInt expr, Object... args) {
			Type type = expr.getType();
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			Var var = procedure.newTempLocalVariable(type, procedure.getName()
					+ "_" + "litteral_integer");
			InstAssign assign = IrFactory.eINSTANCE.createInstAssign(var, expr);

			// Add assignment to instruction's list
			if (it.hasPrevious()) {
				it.previous();
			}
			it.add(assign);
			if (it.hasNext()) {
				it.next();
			}
			return IrFactory.eINSTANCE.createExprVar(var);
		}

		@Override
		public Object interpret(ExprUnary expr, Object... args) {
			Expression e = (Expression) expr.getExpr().accept(this, args);
			return IrFactory.eINSTANCE.createExprUnary(expr.getOp(), e,
					expr.getType());
		}
	};

	@Override
	public void visit(Actor actor) {
		super.visit(actor);
	}

	@Override
	public void visit(InstAssign assign) {
		assign.setValue((Expression) assign.getValue().accept(exprInterpreter,
				itInstruction));
	}

	@Override
	public void visit(NodeIf nodeIf) {
		// Check the presence of a NodeBlock before and create one if needed
		if (itInstruction == null) {
			Procedure procedure = nodeIf.getProcedure();
			int index = procedure.getNodes().indexOf(nodeIf);
			NodeBlock newBlock = IrFactoryImpl.eINSTANCE.createNodeBlock();
			procedure.getNodes().add(index, newBlock);
			itInstruction = newBlock.getInstructions().listIterator();
		}
		ListIterator<Instruction> instructionIteratorBackup = itInstruction;
		nodeIf.setCondition((Expression) nodeIf.getCondition().accept(
				exprInterpreter, itInstruction));
		visit(nodeIf.getThenNodes());
		itInstruction = instructionIteratorBackup;
		visit(nodeIf.getElseNodes());
		itInstruction = instructionIteratorBackup;
		// Unusually visit method to doesn't add new instructions in join node
		for (Instruction instr : nodeIf.getJoinNode().getInstructions()) {
			instr.accept(this);
		}
	}

	@Override
	public void visit(List<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			node.accept(this);
			i = nodes.indexOf(node);
		}
	}

	@Override
	public void visit(InstPhi phi) {
		ListIterator<Expression> it = new ArrayList<Expression>(phi.getValues()).listIterator();
		while (it.hasNext()) {
			it.set((Expression) it.next()
					.accept(exprInterpreter, itInstruction));
		}
	}

	@Override
	public void visit(Procedure procedure) {
		super.visit(procedure);
	}

	@Override
	public void visit(InstStore store) {
		store.setValue((Expression) store.getValue().accept(exprInterpreter,
				itInstruction));
	}

	@Override
	public void visit(InstSpecific node) {
		if (node instanceof InstTernary) {
			InstTernary ternaryOperation = (InstTernary) node;

			ternaryOperation
					.setConditionValue((Expression) ternaryOperation
							.getConditionValue().accept(exprInterpreter,
									itInstruction));
			ternaryOperation.setTrueValue((Expression) ternaryOperation
					.getTrueValue().accept(exprInterpreter, itInstruction));
			ternaryOperation.setFalseValue((Expression) ternaryOperation
					.getFalseValue().accept(exprInterpreter, itInstruction));
		}
	}

	@Override
	public void visit(NodeWhile nodeWhile) {
		// Check the presence of a NodeBlock before and create one if needed
		if (itInstruction == null) {
			Procedure procedure = nodeWhile.getProcedure();
			int index = procedure.getNodes().indexOf(nodeWhile);
			NodeBlock newBlock = IrFactoryImpl.eINSTANCE.createNodeBlock();
			procedure.getNodes().add(index, newBlock);
			itInstruction = newBlock.getInstructions().listIterator();
		}
		ListIterator<Instruction> instructionIteratorBackup = itInstruction;
		nodeWhile.setCondition((Expression) nodeWhile.getCondition().accept(
				exprInterpreter, itInstruction));
		visit(nodeWhile.getNodes());
		itInstruction = instructionIteratorBackup;
		// Unusually visit method to doesn't add new instructions in join node
		for (Instruction instr : nodeWhile.getJoinNode().getInstructions()) {
			instr.accept(this);
		}
	}

}

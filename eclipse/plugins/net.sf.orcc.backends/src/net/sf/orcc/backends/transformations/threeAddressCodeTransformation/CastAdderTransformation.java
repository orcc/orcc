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

import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.AbstractExpressionInterpreter;

/**
 * Add cast in IR in the form of assign instruction where target's type differs
 * from source type.
 * 
 * @author Jerome GORIN
 * @author Herve Yviquel
 * 
 */
public class CastAdderTransformation extends AbstractActorVisitor {

	private class CastExprInterpreter extends AbstractExpressionInterpreter {

		@Override
		public Object interpret(ExprBinary expr, Object... args) {
			OpBinary op = expr.getOp();
			Expression e1 = expr.getE1();
			Expression e2 = expr.getE2();

			if (isSpecificOperation(op)) {
				Cast specificCast = new Cast(e1.getType(), e2.getType());

				if (specificCast.isExtended()) {
					expr.setType(e2.getType());
				} else if (specificCast.isTrunced()) {
					expr.setType(e1.getType());
				}
			}

			if (op.isComparison()) {
				// Check coherence between e1 and e2
				Cast castExprs = new Cast(e1.getType(), e2.getType());

				if (castExprs.isExtended()) {
					// Take e2 as the reference type
					e1 = (Expression) e1.accept(this, e2.getType());
				} else if (castExprs.isTrunced()) {
					// Take e1 as the reference type
					e2 = (Expression) e2.accept(this, e1.getType());
				}
			} else {
				// Check coherence of the overall expression
				e1 = (Expression) e1.accept(this, expr.getType());
				e2 = (Expression) e2.accept(this, expr.getType());
			}

			// Set expressions
			expr.setE1(e1);
			expr.setE2(e2);

			return expr;
		}

		@Override
		public Object interpret(ExprVar expr, Object... args) {
			Type type = (Type) args[0];

			// Check coherence between expression and type
			Cast cast = new Cast(expr.getType(), type);

			if (cast.isExtended() || cast.isTrunced()) {
				// Make a new assignment to the binary expression
				Var newVar = procedure.newTempLocalVariable(cast.getTarget(),
						procedure.getName() + "_" + "expr");

				newVar.setIndex(1);

				InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
						newVar, expr);

				// Add assignement to instruction's list
				itInstruction.add(assign);

				return IrFactory.eINSTANCE.createExprVar(newVar);
			}

			return expr;
		}

		private boolean isSpecificOperation(OpBinary op) {
			return op == OpBinary.MOD;
		}

	}

	private boolean castType;

	public CastAdderTransformation(boolean castType) {
		this.castType = castType;
	}

	private Var castTarget(Var target, Type type) {
		Cast castTarget = new Cast(target.getType(), type);

		if (castType & castTarget.isDifferent()) {
			Location location = target.getLocation();

			// Make a new assignment to the binary expression
			Var transitionVar = procedure.newTempLocalVariable(
					castTarget.getTarget(), procedure.getName() + "_" + "expr");

			transitionVar.setIndex(1);

			ExprVar varExpr = IrFactory.eINSTANCE.createExprVar(transitionVar);

			InstAssign newAssign = IrFactory.eINSTANCE.createInstAssign(
					location, target, varExpr);

			// Add assignement to instruction's list
			itInstruction.add(newAssign);

			return transitionVar;
		} else if (castTarget.isExtended() || castTarget.isTrunced()) {
			Location location = target.getLocation();

			// Make a new assignment to the binary expression
			Var transitionVar = procedure.newTempLocalVariable(
					castTarget.getTarget(), procedure.getName() + "_" + "expr");

			transitionVar.setIndex(1);

			ExprVar varExpr = IrFactory.eINSTANCE.createExprVar(transitionVar);
			InstAssign newAssign = IrFactory.eINSTANCE.createInstAssign(
					location, target, varExpr);

			// Add assignement to instruction's list
			itInstruction.add(newAssign);

			return transitionVar;
		}

		return target;
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

			itInstruction.previous();

			Expression expr = (Expression) binExpr.accept(
					new CastExprInterpreter(), binExpr.getType());

			if (expr != binExpr) {
				assign.setValue(expr);
			}

			itInstruction.next();

			if (!binExpr.getOp().isComparison()) {
				Var newVar = castTarget(assign.getTarget().getVariable(),
						binExpr.getType());
				assign.setTarget(IrFactory.eINSTANCE.createDef(newVar));
			}
		}
	}

	@Override
	public void visit(InstCall call) {
		List<Expression> parameters = call.getParameters();
		Procedure procedure = call.getProcedure();
		if (!procedure.isNative()) {
			List<Var> variables = call.getProcedure().getParameters();

			for (Expression parameter : parameters) {
				Var var = variables.get(parameters.indexOf(parameter));
				itInstruction.previous();
				Expression newParam = (Expression) parameter.accept(
						new CastExprInterpreter(), var.getType());
				parameters.set(parameters.indexOf(parameter), newParam);
				itInstruction.next();
			}
		}
	}

	@Override
	public void visit(InstLoad load) {
		Var target = load.getTarget().getVariable();
		Use use = load.getSource();

		Var newVar = castTarget(target, use.getVariable().getType());

		load.setTarget(IrFactory.eINSTANCE.createDef(newVar));
	}

	@Override
	public void visit(InstPhi phi) {
		List<Expression> values = phi.getValues();
		Type type = phi.getTarget().getVariable().getType();

		for (Expression value : values) {
			int indexValue = values.indexOf(value);
			Node node = phi.getBlock().getPredecessors().get(indexValue);

			if (node.isBlockNode()) {
				itInstruction = ((NodeBlock) node).lastListIterator();
			} else if (node.isIfNode()) {
				itInstruction = ((NodeIf) node).getJoinNode()
						.lastListIterator();
			} else {
				itInstruction = ((NodeWhile) node).getJoinNode()
						.lastListIterator();
			}

			Expression newValue = (Expression) value.accept(
					new CastExprInterpreter(), type);
			values.set(indexValue, newValue);
		}
	}

	@Override
	public void visit(InstReturn returnInstr) {
		Type returnType = procedure.getReturnType();

		if ((returnType != null) && (!returnType.isVoid())) {
			itInstruction.previous();
			Expression value = returnInstr.getValue();

			// Check if value is not void
			if (value != null) {
				Expression newValue = (Expression) value.accept(
						new CastExprInterpreter(), returnType);
				returnInstr.setValue(newValue);
			}

			itInstruction.next();
		}
	}

	@Override
	public void visit(InstStore store) {
		Expression value = store.getValue();
		Var target = store.getTarget().getVariable();

		itInstruction.previous();

		Expression newValue = (Expression) value.accept(
				new CastExprInterpreter(), target.getType());

		if (value != newValue) {
			store.setValue(newValue);
		}

		itInstruction.next();
	}
}
